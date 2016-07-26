package com.dream.android.generator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * <p>
 * Copyright: Copyright (c) 2016, All rights reserved.
 * <p>
 * Company:中配联电子商务南京有限公司
 *
 * @author snovajiang
 * @date 16/5/26
 */
public class BaseBuilder {

    private static BaseBuilder instance;

    public File baseDir;

    public File appDir;

    public File libDir;

    public File resourceDir;

    private static final String DIR_PREFIX = "result/";

    private static final String MVP_PREFIX = "mvp_";

    private static final String MVVM_PREFIX = "mvvm_";

    public synchronized static BaseBuilder getInstance() {
        if (instance == null) {
            instance = new BaseBuilder();
        }
        return instance;
    }

    private BaseBuilder() {
        resourceDir = new File("resource");
    }

    /**
     * 准备工程（固定项目）
     */
    public void prepareProject() {
        baseDir = new File(DIR_PREFIX + Constants.PROJECT_NAME);
        if (baseDir.exists() && baseDir.isDirectory()) {
            FileUtil.deleteDir(baseDir);
        }
        baseDir.mkdirs(); //创建工程根目录
        FileUtil.copyFileToDir(baseDir.getAbsolutePath(),
                new String[]{new File(resourceDir, "settings.gradle").getAbsolutePath()});
        FileUtil.copyFileToDir(baseDir.getAbsolutePath(), new String[]{
                        new File(resourceDir, "android.keystore").getAbsolutePath(),
                        new File(resourceDir, "debug.keystore").getAbsolutePath(),
                        new File(resourceDir, "proguard-rules.txt").getAbsolutePath()});
        //生成根目录下的gradle文件
        Map<String, String> params = new HashMap<>();
        params.put("GRADLE_VERSION", Constants.GRADLE_VERSION);
        File srcFile = new File(resourceDir, "base_build.txt");
        File targetSrcFile = new File(baseDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);
        createDaoGenerator();
    }

    /**
     * 生成MVP基础库
     */
    public void createMVPLibrary() {
        libDir = new File(baseDir, "library");
        libDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(Constants.MIN_SDK_VERSION));
        params.put("TARGET_SDK_VERSION", String.valueOf(Constants.TARGET_SDK_VERSION));
        params.put("JAVA_VERSION", Constants.JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", Constants.BUILD_TOOLS_VERSION);
        File srcFile = new File(resourceDir, MVP_PREFIX + "library/lib_build.txt");
        File targetSrcFile = new File(libDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "library/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, MVP_PREFIX + "library/res").getAbsolutePath());

        File amSrcFile = new File(resourceDir, MVP_PREFIX + "library/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "library/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", Constants.PACKAGE + ".lib");
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);
        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir(srcDir.getAbsolutePath(), new File(resourceDir, MVP_PREFIX + "library/lib").getAbsolutePath());
    }

    /**
     * 生成基础库文件（正常和mvvm所使用）
     */
    public void createLibrary() {
        libDir = new File(baseDir, "library");
        libDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(Constants.MIN_SDK_VERSION));
        params.put("TARGET_SDK_VERSION", String.valueOf(Constants.TARGET_SDK_VERSION));
        params.put("JAVA_VERSION", Constants.JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", Constants.BUILD_TOOLS_VERSION);
        File srcFile = new File(resourceDir, "library/lib_build.txt");
        File targetSrcFile = new File(libDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "library/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, "library/res").getAbsolutePath());

        File amSrcFile = new File(resourceDir, "library/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "library/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", Constants.PACKAGE + ".lib");
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);
        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir(srcDir.getAbsolutePath(), new File(resourceDir, "library/lib").getAbsolutePath());
    }

    /**
     * 生成mvp示例app
     */
    public void createMVPApp() {
        appDir = new File(baseDir, "app");
        appDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(Constants.MIN_SDK_VERSION));
        params.put("TARGET_SDK_VERSION", String.valueOf(Constants.TARGET_SDK_VERSION));
        params.put("JAVA_VERSION", Constants.JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", Constants.BUILD_TOOLS_VERSION);
        File srcFile = new File(resourceDir, MVP_PREFIX + "app/app_build.txt");
        File targetSrcFile = new File(appDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "app/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, MVP_PREFIX + "app/res").getAbsolutePath());

        File amSrcFile = new File(resourceDir, MVP_PREFIX + "app/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "app/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", Constants.PACKAGE);
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);

        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir2(srcDir.getAbsolutePath(), new File(resourceDir, MVP_PREFIX + "app/src").getAbsolutePath());
    }

    /**
     * 生成基础APP
     */
    public void createApp() {
        appDir = new File(baseDir, "app");
        appDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(Constants.MIN_SDK_VERSION));
        params.put("TARGET_SDK_VERSION", String.valueOf(Constants.TARGET_SDK_VERSION));
        params.put("JAVA_VERSION", Constants.JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", Constants.BUILD_TOOLS_VERSION);
        File srcFile = new File(resourceDir, "app/app_build.txt");
        File targetSrcFile = new File(appDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "app/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, "app/res").getAbsolutePath());

        File amSrcFile = new File(resourceDir, "app/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "app/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", Constants.PACKAGE);
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);

        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir2(srcDir.getAbsolutePath(), new File(resourceDir, "app/src").getAbsolutePath());
    }

    /**
     * 生成mvvm基础APP
     */
    public void createMVVMApp() {
        appDir = new File(baseDir, "app");
        appDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(Constants.MIN_SDK_VERSION));
        params.put("TARGET_SDK_VERSION", String.valueOf(Constants.TARGET_SDK_VERSION));
        params.put("JAVA_VERSION", Constants.JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", Constants.BUILD_TOOLS_VERSION);
        File srcFile = new File(resourceDir, MVVM_PREFIX + "app/app_build.txt");
        File targetSrcFile = new File(appDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "app/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, MVVM_PREFIX + "app/res").getAbsolutePath());

        File amSrcFile = new File(resourceDir, MVVM_PREFIX + "app/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "app/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", Constants.PACKAGE);
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);

        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir2(srcDir.getAbsolutePath(), new File(resourceDir, MVVM_PREFIX + "app/src").getAbsolutePath());
    }

    /**
     * 生成MVVM基础库
     */
    public void createMVVMLibrary() {
        libDir = new File(baseDir, "library");
        libDir.mkdir();
        Map<String, String> params = new HashMap<>();
        params.put("MIN_SDK_VERSION", String.valueOf(Constants.MIN_SDK_VERSION));
        params.put("TARGET_SDK_VERSION", String.valueOf(Constants.TARGET_SDK_VERSION));
        params.put("JAVA_VERSION", Constants.JAVA_VERSION);
        params.put("BUILD_TOOLS_VERSION", Constants.BUILD_TOOLS_VERSION);
        File srcFile = new File(resourceDir, MVVM_PREFIX + "library/lib_build.txt");
        File targetSrcFile = new File(libDir, "build.gradle");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);

        File androidBaseDir = new File(baseDir, "library/src/main");
        androidBaseDir.mkdirs();
        FileUtil.copyDir(androidBaseDir.getAbsolutePath(), new File(resourceDir, MVVM_PREFIX + "library/res").getAbsolutePath());

        File amSrcFile = new File(resourceDir, MVVM_PREFIX + "library/AndroidManifest.xml");
        File amTargetSrcFile = new File(baseDir, "library/src/main/AndroidManifest.xml");
        params.clear();
        params.put("PACKAGE", Constants.PACKAGE + ".lib");
        FileUtil.copyFileWithParams(amTargetSrcFile, amSrcFile, params);
        File srcDir = new File(androidBaseDir, "java/" + getPackagePath());
        srcDir.mkdirs();
        FileUtil.copyDir(srcDir.getAbsolutePath(), new File(resourceDir, MVVM_PREFIX + "library/lib").getAbsolutePath());
    }

    /**
     * 使用GreenDao进行数据库管理
     */
    private void createDaoGenerator() {
        File daoDir = new File(baseDir, "DaoGenerator");
        File daoSrcDir = new File(daoDir, "src/main/java/test");
        daoSrcDir.mkdirs();
        File buildFile = new File(resourceDir, "DaoGenerator/build.gradle");
        File targetFile = new File(daoDir, "build.gradle");

        FileUtil.copyFile(targetFile, buildFile);

        Map<String, String> params = new HashMap<>();
        params.put("PACKAGE", Constants.PACKAGE);
        params.put("PACKAGE_PATH", getPackagePath());
        File srcFile = new File(resourceDir, "DaoGenerator/DaoGen.java");
        File targetSrcFile = new File(daoSrcDir, "DaoGen.java");
        FileUtil.copyFileWithParams(targetSrcFile, srcFile, params);
    }

    private String getPackagePath() {
        return Constants.PACKAGE.replaceAll("[/.]", "/");
    }
}
