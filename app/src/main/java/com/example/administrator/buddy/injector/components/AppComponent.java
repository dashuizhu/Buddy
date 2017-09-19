/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.example.administrator.buddy.injector.components;

import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.injector.modules.AppModule;
import com.example.administrator.buddy.network.IHttpAPI;
import dagger.Component;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 应用程序组件
 */
@Singleton @Component(modules = AppModule.class) public interface AppComponent {
  MyApplication app();
  IHttpAPI getHttpApi();
  Retrofit getRetrofit();
  OkHttpClient.Builder getOkHttpClientBuilder();
}
