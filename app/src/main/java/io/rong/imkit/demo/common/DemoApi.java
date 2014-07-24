package io.rong.imkit.demo.common;

import io.rong.imkit.demo.model.Status;
import io.rong.imkit.demo.model.User;
import io.rong.imkit.demo.parser.GsonArrayParser;
import io.rong.imkit.demo.parser.GsonParser;
import io.rong.imkit.demo.parser.RegisterParser;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.sea_monster.core.network.AbstractHttpRequest;
import com.sea_monster.core.network.ApiCallback;
import com.sea_monster.core.network.ApiReqeust;
import com.sea_monster.core.network.BaseApi;
import com.sea_monster.core.network.HttpHandler;

public class DemoApi extends BaseApi {

	private final static String HOST = "http://nav.cn.rong.io:8080/";

	private final static String DEMO_REG = "reg";
	private final static String DEMO_LOGIN = "login";
	private final static String DEMO_FRIENDS = "friends";

	public DemoApi(HttpHandler handler, Context context) {
		super(handler, context);
	}


    /**
     * 登录 demo server
     * @param email
     * @param password
     * @param deviceId
     * @param callback
     * @return
     */
	public AbstractHttpRequest<User> login(String email, String password, String deviceId, ApiCallback<User> callback) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("deviceid", deviceId));

		ApiReqeust<User> apiReqeust = new DefaultApiReqeust<User>(ApiReqeust.POST_METHOD, URI.create(HOST + DEMO_LOGIN), nameValuePairs, callback);
		AbstractHttpRequest<User> httpRequest = apiReqeust.obtainRequest(new GsonParser<User>(User.class), null, null);
		handler.executeRequest(httpRequest);

		return httpRequest;
	}

	/**
	 * 
	 * demo server 注册新用户
	 * 
	 * @param email
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @param callback
	 * 
	 * @return
	 */
	public AbstractHttpRequest<Status> register(String email, String username, String password, ApiCallback<Status> callback) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", password));

		ApiReqeust<Status> apiReqeust = new DefaultApiReqeust<Status>(ApiReqeust.POST_METHOD, URI.create(HOST + DEMO_REG), nameValuePairs, callback);
		AbstractHttpRequest<Status> httpRequest = apiReqeust.obtainRequest(new RegisterParser(), null, null);
		handler.executeRequest(httpRequest);

		return httpRequest;

	}

	/**
	 * 
	 * demo server 获取好友
	 * 
	 * @param cookie
	 * 
	 * @param callback
	 * 
	 * @return
	 */

	public AbstractHttpRequest<ArrayList<User>> getFriends(String cookie, ApiCallback<ArrayList<User>> callback) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("cookie", cookie));

		ApiReqeust<ArrayList<User>> apiReqeust = new DefaultApiReqeust<ArrayList<User>>(ApiReqeust.POST_METHOD, URI.create(HOST + DEMO_FRIENDS), nameValuePairs, callback);
		AbstractHttpRequest<ArrayList<User>> httpRequest = apiReqeust.obtainRequest(new GsonArrayParser<User>(new TypeToken<ArrayList<User>>(){}), null, null);
		handler.executeRequest(httpRequest);

		return httpRequest;

	}



}