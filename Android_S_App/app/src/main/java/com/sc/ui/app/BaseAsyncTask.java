package com.sc.ui.app;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.Timer;
import java.util.TimerTask;

/*****************************************************
 * author:      wz
 * email:       wangzhong0116@foxmail.com
 * version:     1.0
 * date:        2016/9/26 17:58
 * description:
 *****************************************************/
public class BaseAsyncTask extends AsyncTask<Void, Integer, Boolean> {

    private MyProgressDialog progressDialog = null;
    public static final int TIME_OUT_LONG = 45 * 1000;

    protected boolean result = false;
    protected boolean finished = false;
    //protected AsyncHttpClient httpClient = null;
    //protected RequestParams param = null;

    public BaseAsyncTask(Activity activity, boolean showProgressDialog) {
        cancelSelfWhenTimeOut();

        if (showProgressDialog) {
            this.progressDialog = new MyProgressDialog(activity);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

//		httpClient = new AsyncHttpClient();
//		httpClient.setTimeout(ConstantFiled.TIME_OUT);
//		param = new RequestParams();
    }

    public BaseAsyncTask(Activity activity, boolean showProgressDialog,
                         boolean noTimeOut) {
        if (!noTimeOut) {
            cancelSelfWhenTimeOut();
        }

        if (showProgressDialog) {
            this.progressDialog = new MyProgressDialog(activity);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

//		httpClient = new AsyncHttpClient();
//		httpClient.setTimeout(ConstantFiled.TIME_OUT);
//		param = new RequestParams();
    }

    public BaseAsyncTask() {
        cancelSelfWhenTimeOut();

//		httpClient = new AsyncHttpClient();
//		httpClient.setTimeout(ConstantFiled.TIME_OUT);
//		param = new RequestParams();
    }

    public BaseAsyncTask(boolean noTimeOut) {
        if (!noTimeOut) {
            cancelSelfWhenTimeOut();
        }

//		httpClient = new AsyncHttpClient();
//		httpClient.setTimeout(ConstantFiled.TIME_OUT);
//		param = new RequestParams();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        try {
            if (this.progressDialog != null
                    && this.progressDialog.isShowing()) {
                this.progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancelled() {
        try {
            if (this.progressDialog != null
                    && this.progressDialog.isShowing()) {
                this.progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cancelSelfWhenTimeOut() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (BaseAsyncTask.this.isCancelled()) {
                        return;
                    }

                    BaseAsyncTask.this.cancel(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, TIME_OUT_LONG);
    }

}
