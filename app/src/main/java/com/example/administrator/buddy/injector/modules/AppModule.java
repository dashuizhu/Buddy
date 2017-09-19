package com.example.administrator.buddy.injector.modules;

import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.network.IHttpAPI;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhuj on 16/12/30.
 */

@Module public class AppModule {

  public static final String DOMAIN = "http://47.92.49.151:8080";

  public static final int CACHE_TIME = 7 * 24 * 60 * 60; //接口缓存七天
  /**
   * 获取缓存
   * @see AppModule#CACHE_TIME
   */
  public final static String CHACHE = "public, max-stale="+ AppModule.CACHE_TIME;
  /**
   * 获取网络
   */
  public final static String NO_CACHE = "max-age=0";

  /**
   * 统一超时时间 ,单位秒
   */
  private final static int DEFAULT_TIMEOUT = 15;
  private final MyApplication mAppApplication;
  private Retrofit mRetrofit;
  private OkHttpClient.Builder mHttpClientBuilder;
  private OkHttpClient mOkHttpClient;

  public AppModule(MyApplication appApplication) {
    this.mAppApplication = appApplication;
    File httpCacheDirectory = new File(appApplication.getCacheDir(), "responses");
    int cacheSize = 15 * 1024 * 1024; // 15 MiB
    Cache cache = new Cache(httpCacheDirectory, cacheSize);

    //手动创建一个OkHttpClient并设置超时时间
    mHttpClientBuilder = new OkHttpClient.Builder();
    mHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    mHttpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    mHttpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    //-------------------------------------------
    //添加拦截器，切记注意是否有特殊接口会被影响，是否需要额外处理不做拦截
    //-------------------------------------------
    mOkHttpClient = mHttpClientBuilder.addInterceptor(LOG_INTERCEPTOR)
            .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
            .cache(cache)
            .build();
    mRetrofit = new Retrofit.Builder().client(mOkHttpClient)
        .baseUrl(DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
    @Override public Response intercept(Chain chain) throws IOException {
      Request originalRequest = chain.request();
      Request.Builder request = originalRequest.newBuilder();
      if (originalRequest.header("force_cache") != null) {
        //只访问缓存
        request.cacheControl(CacheControl.FORCE_CACHE);
      }
      Response originalResponse = chain.proceed(chain.request());
      originalResponse.newBuilder()
              .removeHeader("Pragma")
              .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_TIME)
              .build();
      return originalResponse;
    }
  };

  /**
   * 日志拦截器
   */
  private static final Interceptor LOG_INTERCEPTOR = new Interceptor() {
    @Override public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      //LogUtils.dWriteLog("interface", request.url().toString(), "interface");
      Response originalResponse = chain.proceed(request);
      okhttp3.MediaType mediaType = originalResponse.body().contentType();
      okhttp3.ResponseBody rb = originalResponse.body();
      byte[] buff = rb.bytes();
      //LogUtils.dWriteLog("interface", new  String(buff).toString(), "interface");
      return originalResponse.newBuilder()
          .body(okhttp3.ResponseBody.create(mediaType, buff))
          .build();
    }
  };

  ///**
  // * 自定义超时时间
  // * @param timeOut
  // * @return
  // */
  //public static IHttpAPI getIHttpApi(int timeOut) {
  //  //手动创建一个OkHttpClient并设置超时时间
  //  OkHttpClient.Builder builder = new OkHttpClient.Builder();
  //  builder.connectTimeout(timeOut, TimeUnit.SECONDS);
  //  builder.readTimeout(timeOut, TimeUnit.SECONDS);
  //  builder.writeTimeout(timeOut, TimeUnit.SECONDS);
  //  //-------------------------------------------
  //  //添加拦截器，切记注意是否有特殊接口会被影响，是否需要额外处理不做拦截
  //  //-------------------------------------------
  //  OkHttpClient okHttpClient = builder.build();
  //  //okHttpClient = builder.addInterceptor(LOG_INTERCEPTOR).build();
  //  return new Retrofit.Builder().client(okHttpClient)
  //      .baseUrl(DOMAIN)
  //      .addConverterFactory(GsonConverterFactory.create())
  //      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
  //      .build()
  //      .create(IHttpAPI.class);
  //}

  ///**
  // * 自定义超时时间和拦截器
  // * @param timeOut
  // * @param interceptor
  // * @return
  // */
  //public static IHttpApi getDownloadIHttpApi(int timeOut, DownloadInterceptor interceptor) {
  //  //手动创建一个OkHttpClient并设置超时时间
  //  OkHttpClient.Builder builder = new OkHttpClient.Builder();
  //  builder.connectTimeout(timeOut, TimeUnit.SECONDS);
  //  builder.readTimeout(timeOut, TimeUnit.SECONDS);
  //  builder.writeTimeout(timeOut, TimeUnit.SECONDS);
  //  //-------------------------------------------
  //  //添加拦截器，切记注意是否有特殊接口会被影响，是否需要额外处理不做拦截
  //  //-------------------------------------------
  //  builder.addInterceptor(interceptor);
  //  OkHttpClient okHttpClient = builder.build();
  //  //okHttpClient = builder.addInterceptor(LOG_INTERCEPTOR).build();
  //  return new Retrofit.Builder().client(okHttpClient)
  //          .baseUrl(DOMAIN)
  //          .addConverterFactory(GsonConverterFactory.create())
  //          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
  //          .build()
  //          .create(IHttpApi.class);
  //}

  @Provides @Singleton MyApplication provideAppApplicationContext() {
    return mAppApplication;
  }

  @Provides @Singleton IHttpAPI provideAuthenticationService() {
    return mRetrofit.create(IHttpAPI.class);
  }

  @Provides @Singleton Retrofit provideRetrofit() {
    return mRetrofit;
  }

  @Provides @Singleton OkHttpClient.Builder provideOkHttpClientBuilder() {
    return mHttpClientBuilder;
  }

  /**
   * 获得缓存控制header值
   * @param isCache  是否需要缓存
   * @return
   */
  public static String getCacheControlString(boolean isCache) {
    String cacheControl = isCache ? AppModule.CHACHE : AppModule.NO_CACHE;
    return cacheControl;
  }

  /**
   * 缓存一年
   * @param isCache
   * @return
   */
  public static String getCacheControlStringAllTime(boolean isCache) {
    String cache = "public, max-stale="+ 365 * 24 * 60 * 60;
    String cacheControl = isCache ? AppModule.CHACHE : AppModule.NO_CACHE;
    return cacheControl;
  }

}
