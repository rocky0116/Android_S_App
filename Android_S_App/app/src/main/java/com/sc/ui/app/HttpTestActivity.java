package com.sc.ui.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.sc.ui.app.R.id.get_request_btton;

/*****************************************************
 * author:      wz
 * email:       wangzhong0116@foxmail.com
 * version:     1.0
 * date:        2016/9/29 10:18
 * description:
 *****************************************************/

public class HttpTestActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    public static final String BASE_URL = "http://192.168.0.57:98/server/services/";
    public static final String GET_PROD_MSG = BASE_URL + "lsSuppliOrderSvc/getProdMsg";

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        initOkhttp();
        textView = (TextView) findViewById(R.id.textView11);

        findViewById(get_request_btton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getReqeust(GET_PROD_MSG);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private void initOkhttp() {
//        client.setConnectTimeout(15000, TimeUnit.SECONDS);
//        client.setReadTimeout(15000, TimeUnit.SECONDS);
//        client.setWriteTimeout(15000, TimeUnit.SECONDS);
//        client.setRetryOnConnectionFailure(true);
    }


    private void get(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("lskey", "10F327A1F43AF80FF32EB7E83EF517F02CA5EEE1D5814ED3C594396E")
                .addHeader("response", "application/json")
                .addHeader("num", "0")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            Log.i("WY", "打印GET响应的数据：" + response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }

//        return response.body().string();
    }

    private void getReqeust(String url) throws IOException {

        //缓存控制
        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(10 * 60, TimeUnit.MILLISECONDS);
        CacheControl cache1 = builder.build();

        String urlls = url + "?lskey=123&response=application/json&num=0";
        Request request = new Request.Builder().cacheControl(cache1)
                .url(urlls)
                .get()
                .build();

        //判断网络是否连接
//        boolean connected =  SystemTool.checkNet(this);
//        if (!connected) {
//            System.out.println("没有网络的情况下~");
//            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//        }

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("########123########");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("TAG", json);
                //textView.setText(json);
            }
        });

        //设置缓存路径
        //File httpCacheDirectory = new File(getExternalCacheDir(), "responses");
        // 暂时设置缓存 10M
        //Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        //OkHttpClient mOkHttpClient = client.addInterceptor(interceptor).cache(cache).build();


    }

    //拦截器
    private final Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            boolean connected = SystemTool.checkNet(HttpTestActivity.this);
            if (!connected) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);
            if (connected) {
                int maxAge = 1 * 60; // read from cache for 1 minute
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();

            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    };

}
