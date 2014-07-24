package io.rong.imkit.demo;

import android.app.Application;

/**
 * Created by zhjchen on 14-3-20.
 */
public class DemoApplication extends Application {

	private static final String IS_FIRST = "is_first";

	@Override
	public void onCreate() {
		super.onCreate();
		DemoContext.getInstance().init(this);
	}

}
