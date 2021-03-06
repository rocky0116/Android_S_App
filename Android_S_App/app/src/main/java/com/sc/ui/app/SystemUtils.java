package com.sc.ui.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.InputStream;
import java.lang.reflect.Field;

public class SystemUtils {

	/**
	 * 获取一个自定义风格的Dialog
	 * 
	 * @param activity
	 *            上下文对象
	 * @param style
	 *            风格
	 * @param customView
	 *            自定义view
	 * @return dialog
	 */
	public static Dialog getCustomeDialog(Activity activity, int style,
										  View customView) {
		Dialog dialog = new Dialog(activity, style);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(customView);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		lp.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		lp.x = 0;
		lp.y = 0;
		window.setAttributes(lp);
		return dialog;
	}

	public static Dialog getCustomeDialog(Activity activity, int style,
										  int customView) {
		Dialog dialog = new Dialog(activity, style);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(customView);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		lp.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		lp.x = 0;
		lp.y = 0;
		window.setAttributes(lp);
		return dialog;
	}

	/**
	 * 获取手机屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 获取手机屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 反射方法获取状态栏高度
	 * 
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int statusBarHeight = 20;
		try {
			Class<?> _class = Class.forName("com.android.internal.R$dimen");
			Object object = _class.newInstance();
			Field field = _class.getField("status_bar_height");
			int restult = Integer.parseInt(field.get(object).toString());
			statusBarHeight = context.getResources()
					.getDimensionPixelSize(restult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Toast.makeText(getActivity(), "StatusBarHeight = " + statusBarHeight,
		// Toast.LENGTH_SHORT).show();
		return statusBarHeight;
	}

	/**
	 * 以最省内存的方式读取本地资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		// opt.inPreferredConfig = Bitmap.Config.RGB_565;
		// opt.inPurgeable = true;
		// opt.inInputShareable = true;
		opt.inSampleSize = 2;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getDisplayWidth(activity);
		int height = getDisplayHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getDisplayWidth(activity);
		int height = getDisplayHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width,
				height - statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}
}
