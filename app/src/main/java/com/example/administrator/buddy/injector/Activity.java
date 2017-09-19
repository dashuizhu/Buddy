/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.administrator.buddy.injector;

import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 统一管理注解生命周期
 * SOURCE:在源文件中有效（即源文件保留）
 * CLASS:在class文件中有效（即class保留）
 * RUNTIME:在运行时有效（即运行时保留）
 */
@Scope @Retention(RUNTIME) public @interface Activity {
}
