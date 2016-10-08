package com.sc.ui.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/*****************************************************
 * author:      wz
 * email:       wangzhong0116@foxmail.com
 * version:     1.0
 * date:        2016/9/29 09:20
 * description:
 *****************************************************/

public class IndexActivity extends AppCompatActivity {

    private static final String TAG = "RxAndroidSamples";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        EventBus.getDefault().register(this);


        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndexActivity.this, MainActivity.class));
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndexActivity.this, TestActivity.class));
                //调用发送事件
                 EventBus.getDefault().post(new ClassEvent());
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndexActivity.this, HttpTestActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //在注册了的Activity中,声明处理事件的方法
    @Subscribe(threadMode = ThreadMode.BACKGROUND) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onUserEvent(ClassEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
    }



    //在任意地方,注册事件类
    public static class ClassEvent{
    } //定义一个事件, 可以不包含成员变量,和成员方法

//    //当然你也可以定义包含成员变量的事件, 用来传递参数
//    public class MsgEvent {
//        public String jsonData;
//        public MsgEvent(String jsonData) {
//            this.jsonData = jsonData;
//        }
//    }
}
