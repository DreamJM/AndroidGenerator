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

    private static final String JAVA_8_VERSION = "JavaVersion.VERSION_1_8";
    private static final String JAVA_7_VERSION = "JavaVersion.VERSION_1_7";

    private static final int TYPE_MVVM = 1;
    private static final int TYPE_MVP = 2;
    private static final int TYPE_NORMAL = 3;

    private ProjectInfo info;

    private File baseDir;

    private File resourceDir;

    private static final String DIR_PREFIX = "result/";

    private static final String MVP_PREFIX = "mvp_";

    private static final String MVVM_PREFIX = "mvvm_";

    private static final String NORMAL_PREFIX = "normal_";

    public ProjectBuilder(ProjectInfo info) {
        this.info = info;
        resourceDir = new File("resource");
        baseDir = new File(DIR_PREFIX + info.getProjectName());
    }

    public void build() {
        prepareProject();
        //generate project via type
        int type = getType();
        prepareBaseLibrary();
        createLibrary(type);
        prepareBaseApp();
        createApp(type);
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
                new String[]{new File(resourceDir, "settings.gradle").getAbsolutePath(),
                             new File(resourceDir, "gradle.properties").getAbsolutePath()}, info.getPackageName());
        FileUtil.copyFileToDir(baseDir.getAbsolutePath(), new String[]{
                        new File(resourceDir, "android.keystore").getAbsolutePath(),
                        new File(resourceDir, "debug.keystore").getAbsolutePath(),
                        new File(resourceDir, "proguard-rules.txt").getAbsolutePath()}, info.getPackageName());
        Map<String, String> params = new HashMap<>();
        params.put("GRADLE_VERSION", info.getGradleVer());
        params.put("LAMBDA_DEPENDENCY", info.isJava8Enabled() ? "\n        classpath 'me.tatarka:gradle-retrolambda:3.2.5'\n" +
                "        //resolve lambda lint error\n" +
                "        // @See:https://github.com/evant/gradle-retrolambda/issues/96\n" +
                "        classpath 'me.tatarka.retrolambda.projectlombok:lombok.ast:0.2.3.a2'":"");
        params.put("LAMBDA_CONFIG", info.isJava8Enabled() ? "\n" +
                "    // Exclude the version that the android plugin depends on.\n" +
                "    configurations.classpath.exclude group: 'com.android.tools.external.lombok'":"");
        File srcFile = new File(resourceDir, "base_build.txt");
        File targetSrcFile = new File(baseDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);
        createDaoGenerator();


    }

    /**
     * Prepare base library module
     */
    private void prepareBaseLibrary() {
        File libDir = new File(baseDir, "library");
        libDir.mkdirs();
        File androidBaseDir = new File(baseDir, "library/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, "base_lib/res").getAbsolutePath(), info.getPackageName());

        File amSrcFile = new File(resourceDir, "base_lib/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "library/src/main/AndroidManifest.xml");

        Map<String, String> params = new HashMap<>();
        params.put("PACKAGE", info.getPackageName() + ".lib");
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);
        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir(srcDir.getAbsolutePath(), new File(resourceDir, "base_lib/lib").getAbsolutePath(), info.getPackageName());
    }

    /**
     * Prepare base android module
     */
    private void prepareBaseApp() {
        File appDir = new File(baseDir, "app");
        appDir.mkdirs();
        File androidBaseDir = new File(baseDir, "app/src/main");
        androidBaseDir.mkdirs();

        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, "base_app/res").getAbsolutePath(), info.getPackageName());

        File amSrcFile = new File(resourceDir, "base_app/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "app/src/main/AndroidManifest.xml");
        Map<String, String> params = new HashMap<>();
        params.put("PACKAGE", info.getPackageName());
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);

        File stringSrcFile = new File(resourceDir, "base_app/res/values/strings.xml");
        File stringTargetFile = new File(baseDir, "app/src/main/res/values/strings.xml");
        if(stringTargetFile.exists()) stringTargetFile.delete();
        params.clear();
        params.put("PROJECT_NAME", info.getProjectName());
        FileUtil.copyFileWithParams(stringTargetFile, stringSrcFile, params);

        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir2(srcDir.getAbsolutePath(), new File(resourceDir, "base_app/src").getAbsolutePath(), info.getPackageName());
    }

    /**
     * Create library module
     */
    public void createLibrary(int type) {
        String prefix = getTypePrefix(type);
        File libDir = new File(baseDir, "library");
        libDir.mkdirs();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(info.getMinSdkVer()));
        params.put("TARGET_SDK_VERSION", String.valueOf(info.getTargetSdkVer()));
        params.put("JAVA_VERSION", info.isJava8Enabled() ? JAVA_8_VERSION : JAVA_7_VERSION);
        params.put("BUILD_TOOLS_VERSION", info.getBuildToolsVersion());
        if(TYPE_MVVM != type) { //no need of butterknife lib for mvvm pattern
            params.put("BUTTER_KNIFE_ENABLE", "\n    compile 'com.jakewharton:butterknife:8.0.1'\n");
        } else {
            params.put("BUTTER_KNIFE_ENABLE", "");
        }
        params.put("LAMBDA_PLUGIN", info.isJava8Enabled() ? "apply plugin: 'me.tatarka.retrolambda'" : "");
        File srcFile = new File(resourceDir, "lib_build.txt");
        File targetSrcFile = new File(libDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "library/src/main");
        androidBaseDir.mkdirs();

        File srcDir = new File(androidBaseDir, "java/" + getPackagePath() + "/lib");
        srcDir.mkdirs();

        FileUtil.copyDir(srcDir.getAbsolutePath(), new File(resourceDir, prefix + "library/lib/base").getAbsolutePath(), info.getPackageName());
    }

    /**
     * Create android module
     */
    public void createApp(int type) {
        String prefix = getTypePrefix(type);
        File appDir = new File(baseDir, "app");
        appDir.mkdirs();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(info.getMinSdkVer()));
        params.put("TARGET_SDK_VERSION", String.valueOf(info.getTargetSdkVer()));
        params.put("JAVA_VERSION", info.isJava8Enabled() ? JAVA_8_VERSION : JAVA_7_VERSION);
        params.put("BUILD_TOOLS_VERSION", info.getBuildToolsVersion());
        if(TYPE_MVVM == type) { //no need of butterknife lib for mvvm pattern
            params.put("DATA_BINDING_ENABLE", "\n    dataBinding {\n" +
                    "        enabled true\n" +
                    "    }\n");
            params.put("MVVM_REPLACEMENT","apt 'com.google.guava:guava:19.0'");
        } else {
            params.put("DATA_BINDING_ENABLE", "");
            params.put("MVVM_REPLACEMENT", "apt 'com.jakewharton:butterknife-compiler:8.0.1'");
        }
        params.put("LAMBDA_PLUGIN", info.isJava8Enabled() ? "apply plugin: 'me.tatarka.retrolambda'" : "");
        File srcFile = new File(resourceDir, "app_build.txt");
        File targetSrcFile = new File(appDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "app/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(new File(androidBaseDir.getAbsolutePath(), "res").getAbsolutePath(), new File(resourceDir, prefix + "app/res/layout").getAbsolutePath(), info.getPackageName());

        File appBaseDir = new File(androidBaseDir, "java/" + getPackagePath());
        appBaseDir.mkdirs();
        FileUtil.copyDir(appBaseDir.getAbsolutePath(), new File(resourceDir, prefix + "app/src/base").getAbsolutePath(), info.getPackageName());
        FileUtil.copyDir(appBaseDir.getAbsolutePath(), new File(resourceDir, prefix + "app/src/feature").getAbsolutePath(), info.getPackageName());
        FileUtil.copyDir(appBaseDir.getAbsolutePath(), new File(resourceDir, prefix + "app/src/model").getAbsolutePath(), info.getPackageName());
    }

    private int getType() {
        int type = 1; //default mvvm
        try {
            type = Integer.valueOf(info.getType());
        } catch (Exception ex) {}
        return type;
    }

    private String getTypePrefix(int type) {
        switch (type) {
            case 2:
                return MVP_PREFIX;
            case 3:
                return NORMAL_PREFIX;
            default:
                return MVVM_PREFIX;
        }
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
