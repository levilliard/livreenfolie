package com.esoxjem.movieguide.network;

import com.esoxjem.movieguide.BuildConfig;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ivan on 8/20/2017.
 */

@Singleton
public class RequestInterceptor implements Interceptor {

    @Inject
    public RequestInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", "LVRE_EN_FOLIE_API_KEY_XXXXXXXXX")
                .build();

        Request request = original.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
