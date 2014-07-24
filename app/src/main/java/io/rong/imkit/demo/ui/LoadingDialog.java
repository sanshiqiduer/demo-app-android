package io.rong.imkit.demo.ui;

import io.rong.imkit.demo.R;
import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class LoadingDialog extends Dialog {

	private TextView mTextView;
//	private AnimationDrawable mAnimationDrawable;

	public LoadingDialog(Context context) {
		super(context, R.style.WinDialog);

		setContentView(R.layout.ui_dialog_loading);
		mTextView = (TextView) findViewById(android.R.id.message);

	}

	@Override
	public void show() {
		super.show();
		// mTextView.post(new Runnable() {
		//
		// @Override
		// public void run() {
		// mAnimationDrawable = (AnimationDrawable)
		// (((ImageView)findViewById(R.id.loading_image)).getDrawable());
		// mAnimationDrawable.start();
		// }
		// });

	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
		// if(mAnimationDrawable!=null)
		// mAnimationDrawable.stop();
	}

	public void setText(String s) {
		if (mTextView != null) {
			mTextView.setText(s);
			mTextView.setVisibility(View.VISIBLE);
		}
	}

	public void setText(int res) {
		if (mTextView != null) {
			mTextView.setText(res);
			mTextView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return false;
		}
		return super.onTouchEvent(event);
	}

}
