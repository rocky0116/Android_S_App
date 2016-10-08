package com.sc.ui.app.event;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sc.ui.app.R;
import com.sc.ui.app.base.BaseActiviity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends BaseActiviity {

    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(Main2Activity.class);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(Main2Activity.class);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(Main3Activity.class);
                EventBus.getDefault().post(new MsgEvent("发送咸菜"));
            }
        });

        button3=(Button)findViewById(R.id.button3);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MsgEvent event) {
        Log.i("-->", "主线程 onEvent");
        button3.setText(event.jsonData);
    }
}
