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
public class NoContentView extends FrameLayout {

    private TextView tvContent;

    private TextView tvFunc;

    private EmptyListener listener;

    public NoContentView(Context context) {
        super(context);
        init(context, null);
    }

    public NoContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NoContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_no_content, this);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvFunc = (TextView) findViewById(R.id.btn_func);
        String title = null;
        int color = getContext().getResources().getColor(R.color.font_gray);
        float titleSize = 16;
        Drawable drawable = null;
        Drawable btnDrawable = null;
        String func = null;
        if(attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NoContentView);
            title = ta.getString(R.styleable.NoContentView_ncContent);
            color = ta.getColor(R.styleable.NoContentView_ncColor, getContext().getResources().getColor(R.color.font_gray));
            titleSize = ta.getDimension(R.styleable.NoContentView_ncSize, 16);
            drawable = ta.getDrawable(R.styleable.NoContentView_ncImage);
            func = ta.getString(R.styleable.NoContentView_ncBtnText);
            btnDrawable = ta.getDrawable(R.styleable.NoContentView_ncBtnBg);
            ta.recycle();
        }
        if(title == null) title = getContext().getString(R.string.default_no_content_hint);
        tvContent.setText(title);
        tvContent.setTextColor(color);
        tvContent.setTextSize(titleSize);
        if(drawable != null) tvContent.setCompoundDrawables(null, drawable, null, null);
        tvFunc.setVisibility(func != null || btnDrawable != null ? View.VISIBLE : View.INVISIBLE);
        if(func != null) tvFunc.setText(func);
        if(btnDrawable != null) tvFunc.setBackgroundDrawable(btnDrawable);
        tvFunc.setOnClickListener(onClickListener);
        setOnClickListener(onClickListener);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(R.id.btn_func == v.getId()) {
                if(listener != null) {
                    listener.onEmptyClicked();
                }
            }
        }
    };

    public void setImageResource(int resId) {
        tvContent.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
    }

    public void setBtnText(String btnText) {
        if(btnText == null) {
            tvFunc.setVisibility(View.INVISIBLE);
        } else {
            tvFunc.setVisibility(View.VISIBLE);
            tvFunc.setText(btnText);
        }
    }

    public void setListener(EmptyListener listener) {
        this.listener = listener;
    }

    public interface EmptyListener {
        void onEmptyClicked();
    }
}
