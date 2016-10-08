package com.sc.ui.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sc.ui.app.event.MsgEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/*****************************************************
 * author:      wz
 * email:       wangzhong0116@foxmail.com
 * version:     1.0
 * date:        2016/10/8 10:10
 * description:
 *****************************************************/

public class BaseActiviity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        l();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        l();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBase(MsgEvent event) {
        l();
    }

    protected void l() {
        Exception exception = new Exception();
        final StackTraceElement[] stackTrace = exception.getStackTrace();
        final StackTraceElement stackTraceElement = stackTrace[1];
        final String className = stackTraceElement.getClassName();
        final String classNamePre = stackTrace[2].getClassName();
        final String methodName = stackTraceElement.getMethodName();
        final int lineNumber = stackTraceElement.getLineNumber();

        Log.i(System.currentTimeMillis() + "-->", String.format("class:%s %s method:%s:%d", classNamePre, className, methodName, lineNumber));
    }

    protected void start(Class activity) {
        final Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
