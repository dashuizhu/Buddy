/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.example.administrator.buddy.injector.modules;

import android.content.Context;
import com.example.administrator.buddy.injector.Activity;
import com.example.administrator.buddy.request.HabitMdoel;
import com.example.administrator.buddy.request.LoginMdoel;
import com.example.administrator.buddy.request.RegisterMdel;
import com.example.administrator.buddy.request.SetupMdoel;
import com.example.administrator.buddy.request.VerificationMdoel;
import com.example.administrator.buddy.view.IBaseView;
import dagger.Module;
import dagger.Provides;

@Module public class PresenterModule {
  private Context mContext;
  private IBaseView mBaseView;

  public PresenterModule(IBaseView baseView) {
    mBaseView = baseView;
  }

  public PresenterModule(Context context, IBaseView baseView) {
    mContext = context;
    mBaseView = baseView;
  }

  @Provides @Activity Context provideActivityContext() {
    return mContext;
  }

  @Provides @Activity IBaseView provideBaseView() {
    return mBaseView;
  }

  @Provides @Activity LoginMdoel provideLoginModel() {
    return new LoginMdoel();
  }
  @Provides @Activity RegisterMdel provideRegisterModel() {
    return new RegisterMdel();
  }
  @Provides @Activity HabitMdoel provideHabitModel() {
    return new HabitMdoel();
  }
  @Provides @Activity SetupMdoel provideSetupModel() {
    return new SetupMdoel();
  }
  @Provides @Activity VerificationMdoel provideVerificationModel() {
    return new VerificationMdoel();
  }

}
