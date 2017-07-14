package com.bc.caibiao.request;

import com.bc.caibiao.base.URLConfig;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangkai
 * @Description : 请求配置
 * create at 16/8/15 下午2:06
 */
public class ApiConfig {
    private static Retrofit mRetrofit;

    /**
     * @return 获得Retrofit基础配置
     */
    public static Retrofit getDefault() {
        if (mRetrofit == null) {
            //配置请求log
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            //配置OkHttp
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(logging).build();
            builder.connectTimeout(60, TimeUnit.SECONDS);//链接超时20s
            builder.readTimeout(20,TimeUnit.SECONDS);//写入缓存超时20s
            builder.writeTimeout(20,TimeUnit.SECONDS);//读取缓存超时20s
            builder.retryOnConnectionFailure(true);//错误重连
            OkHttpClient client = builder.build();

            mRetrofit = new Retrofit.Builder()
                    //配置请求的基础请求地址，以“/”结尾
                    .baseUrl(URLConfig.BASEADDRESS)
                    //配置成用Gson解析
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加RxJava支持
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return mRetrofit;
    }

    public static Map<String, RequestBody> buildMap(File file, String params) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Map<String, RequestBody> map = new HashMap<String, RequestBody>();
        map.put(params + "\";filename =\"" + file.getName() + "", requestBody);
        return map;
    }

}
