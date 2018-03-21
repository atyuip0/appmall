package com.zhh.http;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhang.haihe
 */
@Component
public class OkHttpService {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType XML
            = MediaType.parse("application/xml; charset=utf-8");

    @Autowired
    private OkHttpClient okHttpClient;

    public String get(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if(response==null || response.body()==null){
                return null;
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String post(String url, MediaType contentType, String content) {
        RequestBody body = RequestBody.create(contentType, content);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
