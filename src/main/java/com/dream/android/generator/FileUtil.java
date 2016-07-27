package com.dream.android.generator;

import java.io.*;
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
public class FileUtil {

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    // 复制目录到指定目录,将目录以及目录下的文件和子目录全部复制到目标目录
    public static void copyDir(String toPath, String fromPath) {
        File targetFile = new File(toPath);// 创建文件
        createFile(targetFile, false);// 创建目录
        File file = new File(fromPath);// 创建文件
        if (targetFile.isDirectory() && file.isDirectory()) {// 如果传入是目录
            copyFileToDir(targetFile.getAbsolutePath() + "/" + file.getName(),
                    listFile(file));// 复制文件到指定目录
        }
    }

    // 将目录下的文件和子目录全部复制到目标目录
    public static void copyDir2(String toPath, String fromPath) {
        File targetFile = new File(toPath);// 创建文件
        File file = new File(fromPath);// 创建文件
        if (targetFile.isDirectory() && file.isDirectory()) {// 如果传入是目录
            copyFileToDir(targetFile.getAbsolutePath(),
                    listFile(file));// 复制文件到指定目录
        }
    }

    public static void copyFile(File toFile, File fromFile) {// 复制文件
        if (toFile.exists()) {// 判断目标目录中文件是否存在
            System.out.println("文件" + toFile.getAbsolutePath() + "已经存在，跳过该文件！");
            return;
        } else {
            createFile(toFile, true);// 创建文件
        }
        System.out.println("复制文件" + fromFile.getAbsolutePath() + "到"
                + toFile.getAbsolutePath());
        try {
            if(fromFile.getName().endsWith(".java")) {
                BufferedReader br = new BufferedReader(new FileReader(fromFile));// 创建文件输入流
                BufferedWriter bw = new BufferedWriter(new FileWriter(toFile));
                String line = "";
                while ((line = br.readLine()) != null) {
                    line = line.replaceAll("com.wafa.android.pei", Constants.PACKAGE);
                    bw.write(line);
                    bw.newLine();
                }
                bw.flush();
                br.close();// 输入流关闭
                bw.close();// 输出流关闭
            } else {
                InputStream is = new FileInputStream(fromFile);// 创建文件输入流
                FileOutputStream fos = new FileOutputStream(toFile);// 文件输出流
                byte[] buffer = new byte[1024];// 字节数组
                int byteread;
                while ((byteread = is.read(buffer)) != -1) {// 将文件内容写到文件中
                    fos.write(buffer, 0, byteread);
                }
                is.close();// 输入流关闭
                fos.close();// 输出流关闭
            }
        } catch (FileNotFoundException e) {// 捕获文件不存在异常
            e.printStackTrace();
        } catch (IOException e) {// 捕获异常
            e.printStackTrace();
        }
    }

    // 复制一组文件到指定目录。targetDir是目标目录，filePath是需要复制的文件路径
    public static void copyFileToDir(String toDir, String[] filePath) {
        if (toDir == null || "".equals(toDir)) {// 目录路径为空
            System.out.println("参数错误，目标路径不能为空");
            return;
        }
        File targetFile = new File(toDir);
        if (!targetFile.exists()) {// 如果指定目录不存在
            targetFile.mkdir();// 新建目录
        } else {
            if (!targetFile.isDirectory()) {// 如果不是目录
                System.out.println("参数错误，目标路径指向的不是一个目录！");
                return;
            }
        }
        for (int i = 0; i < filePath.length; i++) {// 遍历需要复制的文件路径
            File file = new File(filePath[i]);// 创建文件
            if (file.isDirectory()) {// 判断是否是目录
                copyFileToDir(toDir + "/" + file.getName(), listFile(file));// 递归调用方法获得目录下的文件
                System.out.println("复制文件 " + file);
            } else {
                copyFileToDir(toDir, file, "");// 复制文件到指定目录
            }
        }
    }

    public static void copyFileToDir(String toDir, File file, String newName) {// 复制文件到指定目录
        String newFile = "";
        if (newName != null && !"".equals(newName)) {
            newFile = toDir + "/" + newName;
        } else {
            newFile = toDir + "/" + file.getName();
        }
        File tFile = new File(newFile);
        copyFile(tFile, file);// 调用方法复制文件
    }

    public static void createFile(File file, boolean isFile) {// 创建文件
        if (!file.exists()) {// 如果文件不存在
            if (!file.getParentFile().exists()) {// 如果文件父目录不存在
                createFile(file.getParentFile(), false);
            } else {// 存在文件父目录
                if (isFile) {// 创建文件
                    try {
                        file.createNewFile();// 创建新文件
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    file.mkdir();// 创建目录
                }
            }
        }
    }

    public static String[] listFile(File dir) {// 获取文件绝对路径
        String absolutPath = dir.getAbsolutePath();// 声获字符串赋值为路传入文件的路径
        String[] paths = dir.list();// 文件名数组
        String[] files = new String[paths.length];// 声明字符串数组，长度为传入文件的个数
        for (int i = 0; i < paths.length; i++) {// 遍历显示文件绝对路径
            files[i] = absolutPath + "/" + paths[i];
        }
        return files;
    }

    public static void copyFileWithParams(File toFile, File fromFile, Map<String, String> params) {// 复制文件
        if (toFile.exists()) {// 判断目标目录中文件是否存在
            System.out.println("文件" + toFile.getAbsolutePath() + "已经存在，跳过该文件！");
            return;
        } else {
            createFile(toFile, true);// 创建文件
        }
        System.out.println("复制文件" + fromFile.getAbsolutePath() + "到"
                + toFile.getAbsolutePath());
        try {
            BufferedReader br = new BufferedReader(new FileReader(fromFile));// 创建文件输入流
            BufferedWriter bw = new BufferedWriter(new FileWriter(toFile));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = genStringWithParam(line, params);
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
            br.close();// 输入流关闭
            bw.close();// 输出流关闭
        } catch (FileNotFoundException e) {// 捕获文件不存在异常
            e.printStackTrace();
        } catch (IOException e) {// 捕获异常
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
