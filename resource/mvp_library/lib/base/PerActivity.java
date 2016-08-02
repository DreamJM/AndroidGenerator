package com.dream.android.sample.lib.base;

import javax.inject.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description: Lifecycle bind with Activity
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
