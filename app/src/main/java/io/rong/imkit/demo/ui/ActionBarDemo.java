package io.rong.imkit.demo.ui;

import io.rong.imkit.demo.R;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhjchen on 14-4-4.
 */
public class ActionBarDemo extends BaseFrameLayout implements View.OnClickListener {

	public ActionBarDemo(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public ActionBarDemo(Context context) {
		super(context);
		initView(context);
	}

	public ActionBarDemo(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private ImageView mReturnTextView;
	private TextView mLogTextView;
	private TextView mTitleTextView;
	private TextView mSeachTextView;
	private TextView mMoreTextView;
	private TextView mButtonTextView;

	private ActionBarLinstener mActionBarLintener;

	private void initView(Context context) {
		mContext = context;

		View view = LayoutInflater.from(context).inflate(R.layout.view_action_bar_demo, this);

		// mReturnTextView = getView(view, "action_bar_back");
		// mLogTextView = getView(view, "action_bar_logo");
		// mTitleTextView = getView(view, "action_bar_title");
		// mSeachTextView = getView(view, "action_bar_right_two");
		// mMoreTextView = getView(view, "action_bar_right_one");
		// mButtonTextView = getView(view, "action_bar_right_three");
		mReturnTextView = getView(view, R.id.action_bar_back);
		mLogTextView = getView(view, R.id.action_bar_logo);
		mTitleTextView = getView(view, R.id.action_bar_title);
		mSeachTextView = getView(view, R.id.action_bar_right_two);
		mMoreTextView = getView(view, R.id.action_bar_right_one);
		mButtonTextView = getView(view, R.id.action_bar_right_three);

		Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");

		// mReturnTextView.setTypeface(iconfont);
		// mReturnTextView.setTextColor(getContext().getResources().getColor(android.R.color.white));
		// mReturnTextView.setTextScaleX(0);

		mLogTextView.setTypeface(iconfont);
		mLogTextView.setTextColor(getContext().getResources().getColor(android.R.color.white));

		mSeachTextView.setTypeface(iconfont);
		mSeachTextView.setTextColor(getContext().getResources().getColor(android.R.color.white));

		mMoreTextView.setTypeface(iconfont);
		mMoreTextView.setTextColor(getContext().getResources().getColor(android.R.color.white));

		mTitleTextView.setTextColor(getContext().getResources().getColor(android.R.color.white));

		mReturnTextView.setOnClickListener(this);
		mLogTextView.setOnClickListener(this);
		mTitleTextView.setOnClickListener(this);
		mSeachTextView.setOnClickListener(this);
		mMoreTextView.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		if (mActionBarLintener == null) {
			return;
		}

		if (v == mReturnTextView) {
			mActionBarLintener.onReturnClick(v);
		} else if (v == mTitleTextView) {
			mActionBarLintener.onTitleClick(v);
		} else if (v == mSeachTextView) {
			mActionBarLintener.onSeachClick(v);
		} else if (v == mMoreTextView) {
			mActionBarLintener.onMoreClick(v);
		}
	}

	interface ActionBarLinstener {

		public void onReturnClick(View v);

		public void onTitleClick(View v);

		public void onSeachClick(View v);

		public void onMoreClick(View v);
	}

	public void setmActionBarLintener(ActionBarLinstener mActionBarLintener) {
		this.mActionBarLintener = mActionBarLintener;
	}

	public ImageView getReturnTextView() {
		return mReturnTextView;
	}

	public TextView getLogTextView() {
		return mLogTextView;
	}

	public TextView getTitleTextView() {
		return mTitleTextView;
	}

	public TextView getSeachTextView() {
		return mSeachTextView;
	}

	public TextView getMoreTextView() {
		return mMoreTextView;
	}

	public TextView getButtonTextView() {
		return mButtonTextView;
	}
}
