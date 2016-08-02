/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dream.android.sample.di.component;

import android.content.Context;
import com.dream.android.sample.data.GithubRepository;
import com.dream.android.sample.di.module.ApplicationModule;
import com.dream.android.sample.lib.executor.JobExecutor;
import com.dream.android.sample.lib.executor.UIThread;
import dagger.Component;

import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  Context context();
  UIThread uiThread();
  JobExecutor jobExecutor();
  GithubRepository testRepository();
}
