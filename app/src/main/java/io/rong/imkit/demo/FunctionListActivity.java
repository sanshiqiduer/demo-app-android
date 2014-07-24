package io.rong.imkit.demo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class FunctionListActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView mListView;
    private FunctionListAdapter mFunctionListAdapter;
    private Button mLogout;


    @Override
    protected int setContentViewResId() {
        return R.layout.activity_functioan_list;
    }

    @Override
    protected void initView() {

        mListView = getViewById(android.R.id.list);
        View headerView = LayoutInflater.from(this).inflate(R.layout.view_list_header, null);
        mListView.addHeaderView(headerView);
        mLogout = getViewById(android.R.id.button1);
        mLogout.setOnClickListener(this);

    }

    @Override
    protected void initData() {

        String[] titleNameArray = this.getResources().getStringArray(R.array.function_list);
        mFunctionListAdapter = new FunctionListAdapter(this, titleNameArray);
        mListView.setAdapter(mFunctionListAdapter);
        mFunctionListAdapter.notifyDataSetChanged();
        mListView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == 1) {

            /**
             * 打开会话列表
             *
             * 如果您需要会话列表界面，可以启动聊天会话列表，在聊天会话列表中，用户可以通过点击右上角的加号按钮创建聊天会话
             *
             * API详见 http://docs.rongcloud.cn/android.html
             */
            RongIM.getInstance().startConversationList(this, new RongIM.OnConversationListStartedListener() {
                @Override
                public void onCreated() {
                    Log.d("FunctioanListActivity", "----startConversationList----onCreated--------");
                }

                @Override
                public void onDestroyed() {
                    Log.d("FunctioanListActivity", "----startConversationList----onDestroyed--------");
                }
            });

        } else if (position == 2) {

            /**
             * 打开客服聊天页面
             *
             * API详见 http://docs.rongcloud.cn/android.html
             */
            RongIM.getInstance().startCustomerServiceChat(this, "rongcloud.net.kefu.service112", "融云小秘书", new RongIM.OnConversationStartedListener() {
                @Override
                public void onCreated(RongIMClient.ConversationType conversationType, String targetId) {
                    Log.d("FunctioanListActivity", "----startCustomerServiceChat----onCreated--------");

                }

                @Override
                public void onDestroyed() {
                    Log.d("FunctioanListActivity", "----startCustomerServiceChat----onDestroyed--------");
                }

                @Override
                public void onClickUserPortrait(RongIMClient.UserInfo user) {
                    Log.d("FunctioanListActivity", "----startCustomerServiceChat----onClickUserPortrait--------");
                }

                @Override
                public void onClickMessage(RongIMClient.Message message) {
                    Log.d("FunctioanListActivity", "----startCustomerServiceChat----onClickMessage--------");
                }
            });


        } else if (position == 3) {


            /**
             * 打开二人会话页面
             *
             * API详见 http://docs.rongcloud.cn/android.html
             */
            RongIM.getInstance().startPrivateChat(this, "40", "光头强",
                    new RongIM.OnConversationStartedListener() {

                        @Override
                        public void onCreated(RongIMClient.ConversationType conversationType, String targetId) {
                            Log.d("FunctioanListActivity", "----startPrivateChat----onCreated--------");
                        }

                        @Override
                        public void onDestroyed() {
                            Log.d("FunctioanListActivity", "----startPrivateChat----onDestroyed--------");
                        }

                        @Override
                        public void onClickUserPortrait(RongIMClient.UserInfo user) {
                            Log.d("FunctioanListActivity", "----startPrivateChat----onClickUserPortrait--------");
                        }

                        @Override
                        public void onClickMessage(RongIMClient.Message message) {
                            Log.d("FunctioanListActivity", "----startPrivateChat----onClickMessage--------");
                        }
                    }
            );
        }
    }

    @Override
    public void onClick(View v) {

        if (v == mLogout) {

            /**
             * 断开与服务器的连接
             *
             * API详见 http://docs.rongcloud.cn/android.html
             */
            RongIM.getInstance().disconnect();


            finish();
        }

    }
}
