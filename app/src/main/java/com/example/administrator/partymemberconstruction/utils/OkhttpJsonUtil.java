package com.example.administrator.partymemberconstruction.utils;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/31.
 */
public class OkhttpJsonUtil {
    private static OkhttpJsonUtil okhttpUtils;
    private OkHttpClient client;

    public interface TextCallBack<T> {
        void getResult(T result);
    }

    public OkhttpJsonUtil(int i) {
        client = new OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build();
    }

    private OkhttpJsonUtil() {
        client = new OkHttpClient();
    }

    public static OkhttpJsonUtil getInstance() {
        if (okhttpUtils == null) {
            synchronized (OkhttpJsonUtil.class) {
                if (okhttpUtils == null) {
                    okhttpUtils = new OkhttpJsonUtil();
                }
            }
        }
        return okhttpUtils;
    }

    public <T> T getByExecute(String url, Class<T> clazz) {
        Request request = new Request.Builder()
                .url(url).get().build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String json = response.body().string();
                T t = new Gson().fromJson(json, clazz);
                return t;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }


    public <T> void getByEnqueue(String url, final Activity activity, final Class<T> clazz, final TextCallBack<T> callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final T t = new Gson().fromJson(response.body().string(), clazz);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (call != null) {

                            callback.getResult(t);
                        }

                    }
                });
            }
        });
    }

    public <T> T postByExecute(HashMap<String, String> map, String url, Class<T> clazz) {
        FormBody.Builder builder = new FormBody.Builder();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            builder.add(key, map.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        try {
            String json = client.newCall(request).execute().body().string();
            T t = new Gson().fromJson(json, clazz);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public <T> void postByEnqueue(final Activity activity, String url, HashMap<String, String> map,
                                  final Class<T> clazz, final TextCallBack<T> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        Set<String> keyset = map.keySet();
        for (String key : keyset) {
            builder.add(key, map.get(key));
        }

        Request request = new Request.Builder()
                .post(builder.build())
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Looper.prepare();
                //Looper.loop();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("2", "失败");
                        //MyApplication.showToast("获取网络失败111！",0);
                        callback.getResult(null);
                    }
                });
            }

            T t;

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rr = response.body().string();
                Log.e("2",rr);
                t = null;
                try {
                    t = new Gson().fromJson(rr, clazz);
                } catch (Exception e) {
                } finally {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.getResult(t);
                        }
                    });
                }

            }

        });


    }


}
