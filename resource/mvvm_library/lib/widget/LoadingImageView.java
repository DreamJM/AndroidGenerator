package com.wafa.android.pei.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wafa.android.pei.lib.R;

/**
 * Description:图片加载控件，带有转菊花的加载效果
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/6/1
 */
public class LoadingImageView extends FrameLayout {

    /**
     * 加载图片类型定义（根据类型来显示不同默认图片）
     */
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_AVATAR = 1;

    /**
     * 图片形状给定义（矩形、原型、圆角矩形）
     */
    private static final int SHAPE_RECT = 0;
    private static final int SHAPE_CIRCLE = 1;
    private static final int SHAPE_ROUND_CORNER = 2;

    /**
     * 图片剪裁式样
     */
    private static final int SCALE_CROP = 0;
    private static final int SCALE_CENTER = 1;
    private static final int SCALE_INSIDE = 2;

    private ImageView imageView;

    private View loadingView;

    private TextView tvLoading;

    private int errorResId = R.drawable.default_image;

    private int type = TYPE_NORMAL;

    private int shape = SHAPE_RECT;

    private int scale = SCALE_CROP;

    private String loadingText;

    /**
     * 图片加载监听器
     */
    ImageLoadingListener listener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String s, View view) {
            loadingView.setVisibility(View.VISIBLE);//显示加载中
        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
            imageView.setImageResource(errorResId);
            loadingView.setVisibility(View.GONE);
        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            loadingView.setVisibility(View.GONE);
        }

        @Override
        public void onLoadingCancelled(String s, View view) {
            loadingView.setVisibility(View.GONE);
        }
    };

    public LoadingImageView(Context context) {
        super(context);
        initView(null);
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    public void setImageResource(int resId) {
        loadingView.setVisibility(View.GONE);
        imageView.setImageResource(resId);
    }

    private void initView(AttributeSet attrs) {
        type = TYPE_NORMAL;
        shape = SHAPE_RECT;
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingImageView);
            type = ta.getInt(R.styleable.LoadingImageView_imageType, TYPE_NORMAL);
            shape = ta.getInt(R.styleable.LoadingImageView_imageShape, SHAPE_RECT);
            loadingText = ta.getString(R.styleable.LoadingImageView_loadingText);
            scale = ta.getInt(R.styleable.LoadingImageView_imageScale, SCALE_CROP);
            ta.recycle();
        }
        setType(type);
        View rootView = LayoutInflater.from(getContext()).inflate(shape == SHAPE_CIRCLE ? R.layout.loading_circle_image : R.layout.loading_image, this, false);
        imageView = (ImageView) rootView.findViewById(R.id.iv_pic);
        switch (scale) {
            case SCALE_CENTER:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case SCALE_INSIDE:
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
            default:
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
        }
        loadingView = rootView.findViewById(R.id.layout_loading);
        tvLoading = (TextView) rootView.findViewById(R.id.tv_loading);
        if (loadingText != null) {
            tvLoading.setText(loadingText);
        } else {
            tvLoading.setVisibility(View.GONE);
        }
        addView(rootView);
    }

    public void setType(int type) {
        this.type = type;
        switch (type) {
            case TYPE_AVATAR:
                errorResId = R.drawable.default_avatar;
                break;
            default:
                errorResId = R.drawable.default_image;
                break;
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void loadImage(String url) {
        if (url == null) {
            imageView.setImageResource(errorResId);
            loadingView.setVisibility(View.GONE);
        } else {
            if (shape == SHAPE_ROUND_CORNER) {
                DisplayImageOptions roundCornerOptions = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .displayer(new RoundedBitmapDisplayer(20))
                        .build();
                ImageLoader.getInstance().displayImage(url, imageView, roundCornerOptions, listener);
            } else {
                ImageLoader.getInstance().displayImage(url, imageView, listener);
            }
        }
    }
}
