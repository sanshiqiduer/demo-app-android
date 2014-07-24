package io.rong.imkit.demo;

import io.rong.imkit.demo.model.Status;
import io.rong.imkit.demo.model.User;
import io.rong.imkit.demo.ui.ActionBarDemo;
import io.rong.imkit.demo.ui.WinToast;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.sea_monster.core.exception.BaseException;
import com.sea_monster.core.network.AbstractHttpRequest;

public class RegisterActivity extends BaseApiActivity implements OnClickListener {
	private static final String TAG = "RegisterActivity";

	private EditText mEmailEditText;
	private EditText mPasswordEditText;
	private EditText mNickNameEditText;
	private Button mRegisterButton;

	private AbstractHttpRequest<Status> httpRequest;

	private ActionBarDemo actionBar;

	@Override
	protected int setContentViewResId() {
		return R.layout.activity_register;
	}

	@Override
	protected void initView() {
		mEmailEditText = getViewById(android.R.id.text1);
		mPasswordEditText = getViewById(android.R.id.text2);
		mNickNameEditText = getViewById(R.id.nick_name);
		mRegisterButton = getViewById(android.R.id.button1);
		actionBar = getViewById(R.id.mutil_user_select_action_bar);

	}

	@Override
	protected void initData() {
		mRegisterButton.setOnClickListener(this);

		actionBar.getLogTextView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		actionBar.getReturnTextView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {

		String email = mEmailEditText.getText().toString();
		String password = mPasswordEditText.getText().toString();
		String nickname = mNickNameEditText.getText().toString();

		if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(nickname)) {
			WinToast.toast(this, R.string.register_is_null);
			return;
		} else if (!isEmail(email)) {
			WinToast.toast(this, R.string.register_email_error);
			return;
		}

		httpRequest = DemoContext.getInstance().getDemoApi().register(email, nickname, password, this);

	}

	@Override
	public void onCallApiSuccess(AbstractHttpRequest request, Object obj) {

		if (httpRequest == request) {

			if (obj instanceof Status) {
				Status status = (Status) obj;

				Log.d(TAG + "--onCallApiSuccess--code:", "" + status.getCode());

				if (status.getCode() == 200) {
					WinToast.toast(this, R.string.register_success);

					Intent intent = new Intent();
					intent.putExtra(LoginActivity.INTENT_EMAIL, mEmailEditText.getText().toString());
					intent.putExtra(LoginActivity.INTENT_PASSWORD, mPasswordEditText.getText().toString());
					setResult(Activity.RESULT_OK, intent);
					finish();
				} else {
					WinToast.toast(this, R.string.register_failure);
				}
			}
		}
	}

	@Override
	public void onCallApiFailure(AbstractHttpRequest request, BaseException e) {
		Log.d(TAG + "--onCallApiFailure:", "onCallApiFailure");
	}

	public static boolean isEmail(String email) {

		if (TextUtils.isEmpty(email))
			return false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);

		if (matcher.matches())
			return true;
		else
			return false;

	}

}
