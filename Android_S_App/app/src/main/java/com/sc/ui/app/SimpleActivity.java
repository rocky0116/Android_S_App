package com.sc.ui.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.Observable;
import rx.Subscriber;

/*****************************************************
 * author:      wz
 * email:       wangzhong0116@foxmail.com
 * version:     1.0
 * date:        2016/9/29 09:45
 * description:
 *****************************************************/

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // 观察事件发生
    Observable.OnSubscribe mObservableAction = new Observable.OnSubscribe<String>() {
        @Override public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext(sayMyName()); // 发送事件
            subscriber.onCompleted(); // 完成事件
        }
    };


    // 创建字符串
    private String sayMyName() {
        return "Hello, I am your friend, Spike!";
    }
}
