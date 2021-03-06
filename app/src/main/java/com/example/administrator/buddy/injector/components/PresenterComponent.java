/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.example.administrator.buddy.injector.components;

import com.example.administrator.buddy.injector.Activity;
import com.example.administrator.buddy.injector.modules.ModelModule;
import com.example.administrator.buddy.presenter.LoginPresenter;
import com.example.administrator.buddy.ui.BindDevice.presenter.DeviceBindPresenter;
import com.example.administrator.buddy.ui.device.presenter.DeviceContactsPresenter;
import com.example.administrator.buddy.ui.device.presenter.DeviceWiFiPresenter;
import com.example.administrator.buddy.ui.device.presenter.UserConcernsPresenter;
import com.example.administrator.buddy.ui.habit.HabitPresenter;
import dagger.Component;

/**
 * 身份验证组件
 */
@Activity @Component(modules = { ModelModule.class }) public interface PresenterComponent {

  LoginPresenter getLoginPresenter();
  HabitPresenter getHabitPresenter();
  DeviceContactsPresenter getDeviceContactsPresenter();
  DeviceWiFiPresenter getDeviceWiFiPresenter();
  UserConcernsPresenter getUserConcernsPresenter();
  DeviceBindPresenter getDeviceBindPresenter();
}
