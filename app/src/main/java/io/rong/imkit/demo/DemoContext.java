package io.rong.imkit.demo;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.GetUserInfoProvider;
import io.rong.imkit.demo.common.DemoApi;
import io.rong.imkit.demo.model.User;
import io.rong.imlib.RongIMClient.UserInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import uk.co.senab.bitmapcache.BitmapLruCache;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.sea_monster.core.common.Const;
import com.sea_monster.core.network.DefaultHttpHandler;
import com.sea_monster.core.network.HttpHandler;
import com.sea_monster.core.resource.ResourceManager;
import com.sea_monster.core.resource.cache.ResourceCacheWrapper;
import com.sea_monster.core.resource.compress.IResourceCompressHandler;
import com.sea_monster.core.resource.compress.ResourceCompressHandler;
import com.sea_monster.core.resource.io.FileSysHandler;
import com.sea_monster.core.resource.io.IFileSysHandler;
import com.sea_monster.core.resource.io.ResourceRemoteWrapper;
import com.sea_monster.core.utils.FileUtils;

public class DemoContext {

    private static final String TAG = "DemoContext";
    private static final String NOMEDIA = ".nomedia";

    private static DemoContext self;
    private DemoApi mDemoApi;

    private BlockingQueue<Runnable> mWorkQueue;
    private ThreadFactory mThreadFactory;
    private static ThreadPoolExecutor sExecutor;

    private IFileSysHandler mFileSysHandler;
    private static HttpHandler mHttpHandler;
    private IResourceCompressHandler mCompressHandler;

    private SharedPreferences sharedPreferences;

    public Context mContext;

    private String mResourceDir;

    private User currentUser;
    private ArrayList<UserInfo> mUserInfos;

    public static DemoContext getInstance() {

        if (self == null) {
            self = new DemoContext();
        }

        return self;
    }

    public DemoContext() {
    }

    public DemoContext(Context context) {
        self = this;
    }

    public void init(Context context) {

        mContext = context;

        /**
         * IMKit SDK调用第一步 初始化
         * 第一个参数，  context上下文
         * 第二个参数，APPKey换成自己的appkey
         * 第三个参数，push消息通知所要打个的action页面
         * 第四个参数，push消息中可以自定义push图标
         */
        RongIM.init(context, "官网注册apkkey", "io.rong.imkit.conversationList.action", R.drawable.ic_launcher);

        //http初始化 用于登录、注册使用
        initHttp();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        mDemoApi = new DemoApi(mHttpHandler, context);

    }

    void initHttp() {

        mWorkQueue = new PriorityBlockingQueue<Runnable>(Const.SYS.WORK_QUEUE_MAX_COUNT);
        mThreadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                return new Thread(r, "RongCloudTask #" + mCount.getAndIncrement());
            }
        };

        sExecutor = new ThreadPoolExecutor(Const.SYS.DEF_THREAD_WORDER_COUNT, Const.SYS.MAX_THREAD_WORKER_COUNT, 1, TimeUnit.SECONDS, mWorkQueue, mThreadFactory);

        sExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        File location;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            location = Environment.getExternalStorageDirectory();
        } else {
        	location = mContext.getFilesDir();
        }
//        location.mkdirs();

        // New Handler
        mFileSysHandler = new FileSysHandler(sExecutor, location, "RongCloud", "img");
        mHttpHandler = new DefaultHttpHandler(mContext, sExecutor);
        mCompressHandler = new ResourceCompressHandler(mContext, mFileSysHandler);

        // Build Cache
        File baseDir = new File(location, "RongCloud");
        File cacheDir = new File(baseDir, "cache");
        FileUtils.createDirectory(cacheDir, true);
        BitmapLruCache.Builder builder = new BitmapLruCache.Builder(mContext);
        builder.setMemoryCacheEnabled(true).setMemoryCacheMaxSizeUsingHeapSize();
        builder.setDiskCacheEnabled(true).setDiskCacheLocation(cacheDir);
        BitmapLruCache cache = builder.build();

        ResourceCacheWrapper cacheWrapper = new ResourceCacheWrapper(mContext, cache, mFileSysHandler, mCompressHandler);
        ResourceRemoteWrapper remoteWrapper = new ResourceRemoteWrapper(mContext, mFileSysHandler, mHttpHandler);
        ResourceManager.init(mContext, remoteWrapper, cacheWrapper);
    }

    /**
     * 临时存放用户数据
     *
     * @param userInfos
     */
    public void setFriends(ArrayList<UserInfo> userInfos) {

        this.mUserInfos = userInfos;

        /**
         * 好友列表的提供者。 RongIM 本身不保存 App 的好友关系，
         * 如果在聊天中需要使用好友关系时（如：需要选择好友加入群聊），
         * RongIM 将调用此 Provider 获取好友列表信息
         *
         */
        RongIM.setGetFriendsProvider(new RongIM.GetFriendsProvider() {

            @Override
            public List<UserInfo> getFriends() {
                return mUserInfos;
            }
        });

        /**
         * 用户信息的提供者。 如果在聊天中遇到的聊天对象是没有登录过的用户（即没有通过融云服务器鉴权过的），
         * RongIM 是不知道用户信息的，RongIM 将调用此 Provider 获取用户信息
         */
        RongIM.setGetUserInfoProvider(new GetUserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {
                return getUserInfoById(userId);
            }

        }, false);


    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    public UserInfo getUserInfoById(String userId) {

        UserInfo userInfoReturn = null;

        if (!TextUtils.isEmpty(userId) && mUserInfos != null) {
            for (UserInfo userInfo : mUserInfos) {

                if (userId.equals(userInfo.getUserId())) {
                    userInfoReturn = userInfo;
                    break;
                }

            }
        }
        return userInfoReturn;
    }

    public DemoApi getDemoApi() {
        return mDemoApi;
    }


    private final String getFileSysDir(String dir) {

        if (!TextUtils.isEmpty(mResourceDir)) {
            return mResourceDir;
        }

        File environmentPath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            environmentPath = Environment.getExternalStorageDirectory();
        } else {
            environmentPath = mContext.getFilesDir();
        }

        File baseDirectory = new File(environmentPath, dir);

        createDirectory(baseDirectory);

        return mResourceDir = baseDirectory.getAbsolutePath();

    }

    private static final void createDirectory(File storageDirectory) {

        if (!storageDirectory.exists()) {

            Log.d(TAG, "Trying to create storageDirectory: " + String.valueOf(storageDirectory.mkdirs()));

            Log.d(TAG, "Exists: " + storageDirectory + " " + String.valueOf(storageDirectory.exists()));
            Log.d(TAG, "State: " + Environment.getExternalStorageState());
            Log.d(TAG, "Isdir: " + storageDirectory + " " + String.valueOf(storageDirectory.isDirectory()));
            Log.d(TAG, "Readable: " + storageDirectory + " " + String.valueOf(storageDirectory.canRead()));
            Log.d(TAG, "Writable: " + storageDirectory + " " + String.valueOf(storageDirectory.canWrite()));

            File tmp = storageDirectory.getParentFile();

            Log.d(TAG, "Exists: " + tmp + " " + String.valueOf(tmp.exists()));
            Log.d(TAG, "Isdir: " + tmp + " " + String.valueOf(tmp.isDirectory()));
            Log.d(TAG, "Readable: " + tmp + " " + String.valueOf(tmp.canRead()));
            Log.d(TAG, "Writable: " + tmp + " " + String.valueOf(tmp.canWrite()));

            tmp = tmp.getParentFile();

            Log.d(TAG, "Exists: " + tmp + " " + String.valueOf(tmp.exists()));
            Log.d(TAG, "Isdir: " + tmp + " " + String.valueOf(tmp.isDirectory()));
            Log.d(TAG, "Readable: " + tmp + " " + String.valueOf(tmp.canRead()));
            Log.d(TAG, "Writable: " + tmp + " " + String.valueOf(tmp.canWrite()));

            File nomediaFile = new File(storageDirectory, NOMEDIA);

            if (!nomediaFile.exists()) {
                try {
                    Log.d(TAG, "Created file: " + nomediaFile + " " + String.valueOf(nomediaFile.createNewFile()));
                } catch (IOException e) {
                    Log.d(TAG, "Unable to create .nomedia file for some reason.", e);
                    throw new IllegalStateException("Unable to create nomedia file.");
                }
            }

            if (!(storageDirectory.isDirectory() && nomediaFile.exists())) {
                throw new RuntimeException("Unable to create storage directory and nomedia file.");
            }
        }

    }



    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }





}
