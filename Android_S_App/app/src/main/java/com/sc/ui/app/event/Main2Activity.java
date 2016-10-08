package com.sc.ui.app.event;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sc.ui.app.R;
import com.sc.ui.app.base.BaseActiviity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class Main2Activity extends BaseActiviity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("ABCD");
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String json){
        ((Button)findViewById(R.id.button3)).setText(json);
    }
}
