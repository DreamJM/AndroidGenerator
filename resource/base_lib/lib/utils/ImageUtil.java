package com.dream.android.sample.lib.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:Image Tools
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class ImageUtil {

    public static final int SUCCESS = 0;

    public static final int ERROR_UNSUPPORTED = 1;

    public static final int ERROR_COMPRESS = 2;

    public static final int NO_NEED_COMPRESS = 3;

    private static final int COMPRESS_WIDTH = 800;
    private static final int COMPRESS_HEIGHT = 600;

    private static final long MAX_SIZE = 350 * 1024;

    public static int compressImageAndSave(String srcPath, String targetPath) {
        File srcFile = new File(srcPath);
        if(srcFile.length() > MAX_SIZE) {
            int fileType = MediaFile.getFileType(srcPath);
            if(fileType != MediaFile.FILE_TYPE_PNG && fileType != MediaFile.FILE_TYPE_JPEG) {
                return ERROR_UNSUPPORTED;
            }
            Bitmap resultBitmap = compressToBitmap(srcPath);
            return saveBitmap(resultBitmap, targetPath, MediaFile.getFileType(srcPath), 75);
        } else {
            return NO_NEED_COMPRESS;
        }
    }

    public static int getBitmapDegree(String imgPath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int degree = 0;
        if (exif != null) {
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }
        }
        return degree;
    }

    public static Bitmap setBitmapDegree(Bitmap bitmap, int degree){
        if (degree != 0) {
            Matrix m = new Matrix();
            m.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
        }
        return bitmap;
    }

    public static Bitmap compressToBitmap(String srcPath) {
        int degree = getBitmapDegree(srcPath);
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        float w = newOpts.outWidth;
        float h = newOpts.outHeight;
        float ww = COMPRESS_WIDTH;
        float hh = COMPRESS_HEIGHT;
        float xScale = w / ww;
        float yScale = h / hh;
        float maxScale = Math.max(xScale, yScale);
        float scale = w / maxScale / w;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return setBitmapDegree(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true), degree);
    }

    public static int saveBitmap(Bitmap mBitmap, String targetPath, int type, int quality) {
        Bitmap.CompressFormat format;
        if (type == MediaFile.FILE_TYPE_JPEG) {
            format = Bitmap.CompressFormat.JPEG;
        } else if (type == MediaFile.FILE_TYPE_PNG) {
            format = Bitmap.CompressFormat.PNG;
        } else {
            return ERROR_UNSUPPORTED;
        }
        File tempFile = new File(targetPath);
        if(!tempFile.exists() || tempFile.delete()) {
            FileOutputStream fout;
            try {
                tempFile.createNewFile();
                fout = new FileOutputStream(tempFile);
                mBitmap.compress(format, quality, fout);
                fout.flush();
                fout.close();
                return SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ERROR_COMPRESS;
    }
}
