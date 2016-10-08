package com.sc.ui.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MyProgressDialog extends Dialog {

	private Context mContext;
	private Window mWindow;
	private Activity mActivity;

	private int osHeight = -1;
	private int osWidth = -1;

	public MyProgressDialog(Context context) {
		super(context, R.style.MyDialogStyle);

		this.mContext = context;
		this.mWindow = this.getWindow();
		if (mContext instanceof Activity) {
			mActivity = (Activity) mContext;
		}

		mWindow.requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.progress_dialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private void setProperty() {
		WindowManager.LayoutParams wl = mWindow.getAttributes();
		wl.x = 0;
		wl.y = 0;

		if (mActivity != null) {
			osHeight = SystemUtils.getDisplayHeight(mActivity);
			osWidth = SystemUtils.getDisplayWidth(mActivity);
			int min = Math.min(osHeight, osWidth);
			wl.width = Math.min((int) (min * 0.85),
					changeDip2Pixel(mContext, 350));
		}

		mWindow.setAttributes(wl);
	}

	public static int changeDip2Pixel(Context context, int dip) {
		return (int) (dip * getDensity(context) + 0.5f);
	}

	public static float getDensity(Context context) {
		DisplayMetrics dm = context.getApplicationContext().getResources()
				.getDisplayMetrics();
		float density = dm.density;
		return density;
	}

	@Override
	public void show() {
		try {
			setProperty();
			super.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
