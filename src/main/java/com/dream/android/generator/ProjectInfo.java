/*
 * Copyright (C) 2016 Meng Jiang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dream.android.generator;

public class ProjectInfo {

    private String type;

    private String projectName;

    private String packageName;

    private String gradleVer;

    private int minSdkVer;

    private int targetSdkVer;

    private String buildToolsVersion;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getGradleVer() {
        return gradleVer;
    }

    public void setGradleVer(String gradleVer) {
        this.gradleVer = gradleVer;
    }

    public int getMinSdkVer() {
        return minSdkVer;
    }

    public void setMinSdkVer(int minSdkVer) {
        this.minSdkVer = minSdkVer;
    }

    public int getTargetSdkVer() {
        return targetSdkVer;
    }

    public void setTargetSdkVer(int targetSdkVer) {
        this.targetSdkVer = targetSdkVer;
    }

    public String getBuildToolsVersion() {
        return buildToolsVersion;
    }

    public void setBuildToolsVersion(String buildToolsVersion) {
        this.buildToolsVersion = buildToolsVersion;
    }
}
