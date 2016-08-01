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

import java.io.*;
import java.util.Map;

public class FileUtil {

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void copyDir(String toPath, String fromPath, String packageName) {
        File targetFile = new File(toPath);
        createFile(targetFile, false);
        File file = new File(fromPath);
        if (targetFile.isDirectory() && file.isDirectory()) {
            copyFileToDir(targetFile.getAbsolutePath() + "/" + file.getName(),
                    listFile(file), packageName);
        }
    }

    public static void copyDir2(String toPath, String fromPath, String packageName) {
        File targetFile = new File(toPath);
        File file = new File(fromPath);
        if (targetFile.isDirectory() && file.isDirectory()) {
            copyFileToDir(targetFile.getAbsolutePath(),
                    listFile(file), packageName);
        }
    }

    public static void copyFile(File toFile, File fromFile, String packageName) {
        if (toFile.exists()) {
            System.out.println("file " + toFile.getAbsolutePath() + " already exists, skip！");
            return;
        } else {
            createFile(toFile, true);
        }
        System.out.println("copy file " + fromFile.getAbsolutePath() + " to "
                + toFile.getAbsolutePath());
        try {
            if(fromFile.getName().endsWith(".java") || fromFile.getName().endsWith(".xml")) {
                BufferedReader br = new BufferedReader(new FileReader(fromFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(toFile));
                String line = "";
                while ((line = br.readLine()) != null) {
                    line = line.replaceAll("com.dream.android.sample", packageName);
                    bw.write(line);
                    bw.newLine();
                }
                bw.flush();
                br.close();
                bw.close();
            } else {
                InputStream is = new FileInputStream(fromFile);
                FileOutputStream fos = new FileOutputStream(toFile);
                byte[] buffer = new byte[1024];
                int byteread;
                while ((byteread = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteread);
                }
                is.close();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFileToDir(String toDir, String[] filePath, String packageName) {
        if (toDir == null || "".equals(toDir)) {
            System.out.println("parameter error, target directory can't be null");
            return;
        }
        File targetFile = new File(toDir);
        if (!targetFile.exists()) {
            targetFile.mkdir();
        } else {
            if (!targetFile.isDirectory()) {
                System.out.println("parameter error, target file is not a directory");
                return;
            }
        }
        for (int i = 0; i < filePath.length; i++) {
            File file = new File(filePath[i]);
            if (file.isDirectory()) {
                copyFileToDir(toDir + "/" + file.getName(), listFile(file), packageName);
                System.out.println("copy file " + file);
            } else {
                copyFileToDir(toDir, file, "", packageName);
            }
        }
    }

    public static void copyFileToDir(String toDir, File file, String newName, String packageName) {
        String newFile = "";
        if (newName != null && !"".equals(newName)) {
            newFile = toDir + "/" + newName;
        } else {
            newFile = toDir + "/" + file.getName();
        }
        File tFile = new File(newFile);
        copyFile(tFile, file, packageName);
    }

    public static void createFile(File file, boolean isFile) {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                createFile(file.getParentFile(), false);
            } else {
                if (isFile) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    file.mkdir();
                }
            }
        }
    }

    public static String[] listFile(File dir) {
        String absolutPath = dir.getAbsolutePath();
        String[] paths = dir.list();
        String[] files = new String[paths.length];
        for (int i = 0; i < paths.length; i++) {
            files[i] = absolutPath + "/" + paths[i];
        }
        return files;
    }

    public static void copyFileWithParams(File toFile, File fromFile, Map<String, String> params) {
        if (toFile.exists()) {
            System.out.println("file " + toFile.getAbsolutePath() + "already exists, skip！");
            return;
        } else {
            createFile(toFile, true);
        }
        System.out.println("copy file " + fromFile.getAbsolutePath() + " to "
                + toFile.getAbsolutePath());
        try {
            BufferedReader br = new BufferedReader(new FileReader(fromFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(toFile));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = genStringWithParam(line, params);
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
            br.close();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String genStringWithParam(String source, Map<String, String> params) {
        for(Map.Entry<String, String> param : params.entrySet()) {
            source = source.replaceAll(getFormatKey(param.getKey()), param.getValue());
        }
        return source;
    }

    public static String getFormatKey(String key) {
        return String.format("<jm %1$s>", key);
    }
}
