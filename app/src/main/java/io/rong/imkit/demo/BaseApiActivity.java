package io.rong.imkit.demo;

import com.sea_monster.core.exception.BaseException;
import com.sea_monster.core.network.AbstractHttpRequest;
import com.sea_monster.core.network.ApiCallback;

public abstract class BaseApiActivity extends BaseActivity implements ApiCallback {

	public abstract void onCallApiSuccess(AbstractHttpRequest request, Object obj);

	public abstract void onCallApiFailure(AbstractHttpRequest request, BaseException e);

	@Override
	public void onComplete(final AbstractHttpRequest request, final Object obj) {

		runOnUiThread(new Runnable() {
			public void run() {
				onCallApiSuccess(request, obj);
			}
		});

	}

	@Override
	public void onFailure(final AbstractHttpRequest request, final BaseException e) {

		runOnUiThread(new Runnable() {
			public void run() {
				onCallApiFailure(request, e);
			}
		});

	}

}
