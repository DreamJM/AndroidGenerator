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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProjectBuilder {

    private static final String JAVA_VERSION = "JavaVersion.VERSION_1_8";

    private ProjectInfo info;

    private File baseDir;

    private File resourceDir;

    private static final String DIR_PREFIX = "result/";

    private static final String MVP_PREFIX = "mvp_";

    private static final String MVVM_PREFIX = "mvvm_";

    public ProjectBuilder(ProjectInfo info) {
        this.info = info;
        resourceDir = new File("resource");
        baseDir = new File(DIR_PREFIX + info.getProjectName());
    }

    public void build() {
        prepareProject();
        //generate project via type
        switch (info.getType()) {
            case "2": //mvp
                createMVPLibrary();
                createMVPApp();
                break;
            case "3": //normal
                createLibrary();
                createApp();
                break;
            default: //mvvm
                createMVVMLibrary();
                createMVVMApp();
        }
    }

    /**
     * Prepare base project files and directories:
     *
     * 1.Root Build Gradle File
     * 2.Proguard Rules File
     * 3.Project Temporary Release Keystore
     * 4.Project Temporary Debug Keystore
     */
    public void prepareProject() {
        if (baseDir.exists() && baseDir.isDirectory()) {
            FileUtil.deleteDir(baseDir);
        }
        baseDir.mkdirs();
        FileUtil.copyFileToDir(baseDir.getAbsolutePath(),
                new String[]{new File(resourceDir, "settings.gradle").getAbsolutePath()}, info.getPackageName());
        FileUtil.copyFileToDir(baseDir.getAbsolutePath(), new String[]{
                        new File(resourceDir, "android.keystore").getAbsolutePath(),
                        new File(resourceDir, "debug.keystore").getAbsolutePath(),
                        new File(resourceDir, "proguard-rules.txt").getAbsolutePath()}, info.getPackageName());
        Map<String, String> params = new HashMap<>();
        params.put("GRADLE_VERSION", info.getGradleVer());
        File srcFile = new File(resourceDir, "base_build.txt");
        File targetSrcFile = new File(baseDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);
        createDaoGenerator();
    }

    /**
     * Create library module for "mvp" project
     */
    public void createMVPLibrary() {
        File libDir = new File(baseDir, "library");
        libDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(info.getMinSdkVer()));
        params.put("TARGET_SDK_VERSION", String.valueOf(info.getTargetSdkVer()));
        params.put("JAVA_VERSION", JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", info.getBuildToolsVersion());
        File srcFile = new File(resourceDir, MVP_PREFIX + "library/lib_build.txt");
        File targetSrcFile = new File(libDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "library/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, MVP_PREFIX + "library/res").getAbsolutePath(), info.getPackageName());

        File amSrcFile = new File(resourceDir, MVP_PREFIX + "library/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "library/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", info.getPackageName() + ".lib");
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);
        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir(srcDir.getAbsolutePath(), new File(resourceDir, MVP_PREFIX + "library/lib").getAbsolutePath(), info.getPackageName());
    }

    /**
     * Create android module for "mvp" project
     */
    public void createMVPApp() {
        File appDir = new File(baseDir, "app");
        appDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(info.getMinSdkVer()));
        params.put("TARGET_SDK_VERSION", String.valueOf(info.getTargetSdkVer()));
        params.put("JAVA_VERSION", JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", info.getBuildToolsVersion());
        File srcFile = new File(resourceDir, MVP_PREFIX + "app/app_build.txt");
        File targetSrcFile = new File(appDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "app/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, MVP_PREFIX + "app/res").getAbsolutePath(), info.getPackageName());

        File amSrcFile = new File(resourceDir, MVP_PREFIX + "app/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "app/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", info.getPackageName());
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);

        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir2(srcDir.getAbsolutePath(), new File(resourceDir, MVP_PREFIX + "app/src").getAbsolutePath(), info.getPackageName());
    }

    /**
     * Create library module for "normal" project
     */
    public void createLibrary() {
        File libDir = new File(baseDir, "library");
        libDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(info.getMinSdkVer()));
        params.put("TARGET_SDK_VERSION", String.valueOf(info.getTargetSdkVer()));
        params.put("JAVA_VERSION", JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", info.getBuildToolsVersion());
        File srcFile = new File(resourceDir, "library/lib_build.txt");
        File targetSrcFile = new File(libDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "library/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, "library/res").getAbsolutePath(), info.getPackageName());

        File amSrcFile = new File(resourceDir, "library/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "library/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", info.getPackageName() + ".lib");
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);
        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir(srcDir.getAbsolutePath(), new File(resourceDir, "library/lib").getAbsolutePath(), info.getPackageName());
    }

    /**
     * Create android module for "normal" project
     */
    public void createApp() {
        File appDir = new File(baseDir, "app");
        appDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(info.getMinSdkVer()));
        params.put("TARGET_SDK_VERSION", String.valueOf(info.getTargetSdkVer()));
        params.put("JAVA_VERSION", JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", info.getBuildToolsVersion());
        File srcFile = new File(resourceDir, "app/app_build.txt");
        File targetSrcFile = new File(appDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "app/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, "app/res").getAbsolutePath(), info.getPackageName());

        File amSrcFile = new File(resourceDir, "app/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "app/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", info.getPackageName());
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);

        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir2(srcDir.getAbsolutePath(), new File(resourceDir, "app/src").getAbsolutePath(), info.getPackageName());
    }

    /**
     * Create library module for "mvvm" project
     */
    public void createMVVMLibrary() {
        File libDir = new File(baseDir, "library");
        libDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(info.getMinSdkVer()));
        params.put("TARGET_SDK_VERSION", String.valueOf(info.getTargetSdkVer()));
        params.put("JAVA_VERSION", JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", info.getBuildToolsVersion());
        File srcFile = new File(resourceDir, MVVM_PREFIX + "library/lib_build.txt");
        File targetSrcFile = new File(libDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "library/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, MVVM_PREFIX + "library/res").getAbsolutePath(), info.getPackageName());

        File amSrcFile = new File(resourceDir, MVVM_PREFIX + "library/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "library/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", info.getPackageName() + ".lib");
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);
        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir(srcDir.getAbsolutePath(), new File(resourceDir, MVVM_PREFIX + "library/lib").getAbsolutePath(), info.getPackageName());
    }

    /**
     * Create android module for "mvvm" project
     */
    public void createMVVMApp() {
        File appDir = new File(baseDir, "app");
        appDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(info.getMinSdkVer()));
        params.put("TARGET_SDK_VERSION", String.valueOf(info.getTargetSdkVer()));
        params.put("JAVA_VERSION", JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", info.getBuildToolsVersion());
        File srcFile = new File(resourceDir, MVVM_PREFIX + "app/app_build.txt");
        File targetSrcFile = new File(appDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "app/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, MVVM_PREFIX + "app/res").getAbsolutePath(), info.getPackageName());

        File amSrcFile = new File(resourceDir, MVVM_PREFIX + "app/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "app/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", info.getPackageName());
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);

        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir2(srcDir.getAbsolutePath(), new File(resourceDir, MVVM_PREFIX + "app/src").getAbsolutePath(), info.getPackageName());
    }

    private void createDaoGenerator() {
        File daoDir = new File(baseDir, "DaoGenerator");
        File daoSrcDir = new File(daoDir, "src/main/java/test");
        daoSrcDir.mkdirs();
        File buildFile = new File(resourceDir, "DaoGenerator/build.gradle");
        File targetFile = new File(daoDir, "build.gradle");

        FileUtil.copyFile(targetFile, buildFile, info.getPackageName());

        Map<String, String> params = new HashMap<>();
        params.put("PACKAGE", info.getPackageName());
        params.put("PACKAGE_PATH", getPackagePath());
        File srcFile = new File(resourceDir, "DaoGenerator/DaoGen.java");
        File targetSrcFile = new File(daoSrcDir, "DaoGen.java");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);
    }

    private String getPackagePath() {
        return info.getPackageName().replaceAll("[/.]", "/");
    }
}
