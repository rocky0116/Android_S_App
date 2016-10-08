package com.sc.ui.app.event;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sc.ui.app.R;
import com.sc.ui.app.base.BaseActiviity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class Main3Activity extends BaseActiviity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);

        findViewById(R.id.get_request_btton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MsgEvent("From Main3"));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                EventBus.getDefault().postSticky(new MsgEvent("From Main3 With Sticky"));
            }
        });

    }

    /**
     * threadMode 表示方法在什么线程执行   (Android更新UI只能在主线程, 所以如果需要操作UI, 需要设置ThreadMode.MainThread)
     * sticky     表示是否是一个粘性事件   (如果你使用postSticky发送一个事件,那么需要设置为true才能接受到事件)
     * priority   优先级                 (如果有多个对象同时订阅了相同的事件, 那么优先级越高,会优先被调用.)
     * */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void onEvent(MsgEvent event){
        Log.i("-->", "Main3 onEvent");
    }
}
