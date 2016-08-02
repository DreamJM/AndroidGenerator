package com.dream.android.sample.lib.widget.hint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.dream.android.sample.lib.R;

/**
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/6/1
 */
public class ErrorView extends FrameLayout {

    private TextView tvContent;

    private TextView tvFunc;

    private ErrorListener listener;

    public ErrorView(Context context) {
        super(context);
        init(context, null);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_error, this);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvFunc = (TextView) findViewById(R.id.btn_func);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ErrorView);
        String title = ta.getString(R.styleable.ErrorView_errorContent);
        int color = ta.getColor(R.styleable.ErrorView_errorColor, getContext().getResources().getColor(R.color.font_gray));
        float titleSize = ta.getDimension(R.styleable.ErrorView_errorColor, 16);
        Drawable drawable = ta.getDrawable(R.styleable.ErrorView_errorImage);
        String func = ta.getString(R.styleable.ErrorView_errorBtnText);
        ta.recycle();
        if(title == null) {
            title = getContext().getString(R.string.default_error_hint);
        }
        tvContent.setText(title);
        tvContent.setTextColor(color);
        tvContent.setTextSize(titleSize);
        if(drawable != null) tvContent.setCompoundDrawables(null, drawable, null, null);
        tvFunc.setVisibility(func != null ? View.VISIBLE : View.INVISIBLE);
        if(func != null) tvFunc.setText(func);
        tvFunc.setOnClickListener(onClickListener);
        setOnClickListener(onClickListener);
    }

    public void setErrorContent(String content) {
        tvContent.setText(content);
    }

    public void setErrorButtonText(String text) {
        if(text != null) {
            tvFunc.setText(text);
            tvFunc.setVisibility(View.VISIBLE);
        } else {
            tvFunc.setVisibility(View.INVISIBLE);
        }
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(R.id.btn_func == v.getId()) {
                if(listener != null) {
                    listener.onErrorClicked();
                }
            }
        }
    };

    public void setListener(ErrorListener listener) {
        this.listener = listener;
    }

    public interface ErrorListener {
        void onErrorClicked();
    }
}
