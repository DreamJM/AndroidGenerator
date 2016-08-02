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

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class CommandLine {

    private static final String DEFAULT_GRADLE_VERSION = "2.8";
    private static final String DEFAULT_TOOLS_VERSION = "23.0.1";
    private static final int DEFAULT_MIN_SDK = 14;
    private static final int DEFAULT_TARGET_SDK = 23;


    protected static ProjectInfo generateFromCommand(BufferedReader in) throws IOException {
        ProjectInfo info = new ProjectInfo();

        System.out.println("====Please input project type====");
        System.out.println("1.mvvm(*default)  2.mvp  3.normal");
        String type = in.readLine().trim();
        info.setType(type);

        String projectName = "";
        do {
            System.out.println("====Please input project name====");
            projectName = in.readLine().trim();
        } while ("".equals(projectName));
        info.setProjectName(projectName);

        String packageName = "";
        do {
            System.out.println("====Please input package name====");
            packageName = in.readLine().trim().toLowerCase();
            Pattern pattern = Pattern.compile("^(([a-z][0-9a-z]*)[.])*([a-z][0-9a-z]*)$");
            if (!pattern.matcher(packageName).matches()) {
                packageName = "";
                System.out.println("package format error!");
            }
        } while ("".equals(packageName));
        info.setPackageName(packageName);

        String gradleVersion = "";
        do {
            System.out.println("====Please input your local gradle version(default value is 2.8)====");
            gradleVersion = in.readLine().trim();
            if ("".equals(gradleVersion)) {
                gradleVersion = DEFAULT_GRADLE_VERSION;
            } else {
                Pattern pattern = Pattern.compile("^(([0-9]*)[.])*([0-9]*)$");
                if (!pattern.matcher(gradleVersion).matches()) {
                    gradleVersion = "";
                    System.out.println("version format error!");
                }
            }
        } while ("".equals(gradleVersion));
        info.setGradleVer(gradleVersion);

        String buildToolsVer = "";
        do {
            System.out.println("====Please input your local build tools version(default value is 23.0.1)====");
            buildToolsVer = in.readLine().trim();
            if ("".equals(buildToolsVer)) {
                buildToolsVer = DEFAULT_TOOLS_VERSION;
            } else {
                Pattern pattern = Pattern.compile("^(([0-9]*)[.])*([0-9]*)$");
                if (!pattern.matcher(buildToolsVer).matches()) {
                    buildToolsVer = "";
                    System.out.println("version format error!");
                }
            }
        } while ("".equals(buildToolsVer));
        info.setBuildToolsVersion(buildToolsVer);

        int minSdkVer = 0;
        do {
            System.out.println("====Please input minimal sdk version(default value is 14)====");
            String inputVersion = in.readLine().trim();
            if ("".equals(inputVersion)) {
                minSdkVer = DEFAULT_MIN_SDK;
            } else {
                try {
                    minSdkVer = Integer.parseInt(inputVersion);
                    if (minSdkVer <= 13) {
                        System.out.println("minimal sdk version is 14");
                    }
                } catch (Exception ex) {
                    System.out.println("version format error!");
                }
            }
        } while (minSdkVer < 14);
        info.setMinSdkVer(minSdkVer);

        int targetSdkVer = 0;
        do {
            System.out.println("====Please input target sdk version(default value is 23)====");
            String inputVersion = in.readLine().trim();
            if ("".equals(inputVersion)) {
                targetSdkVer = DEFAULT_TARGET_SDK;
            } else {
                try {
                    targetSdkVer = Integer.parseInt(inputVersion);
                    if (targetSdkVer < info.getMinSdkVer()) {
                        System.out.println("target sdk version must be greater than minimal sdk version");
                    }
                } catch (Exception ex) {
                    System.out.println("version format error!");
                }
            }
        } while (targetSdkVer < info.getMinSdkVer());
        info.setTargetSdkVer(targetSdkVer);

        boolean java8Enabled = false;
        System.out.println("====Whether use java 8 for development(y/n)====");
        String input = in.readLine().trim();
        if ("y".equals(input.toLowerCase()) || "yes".equals(input.toLowerCase())) {
            java8Enabled = true;
        }
        info.setJava8Enabled(java8Enabled);

        return info;
    }

}
