package com.dream.android.sample.lib.widget.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import com.dream.android.sample.lib.widget.pullrefresh.header.PtrCustomDefaultHeader;

public class PtrCustomFrameLayout extends PtrFrameLayout {

    private PtrCustomDefaultHeader mPtrClassicHeader;

    public PtrCustomFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrCustomFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrCustomFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new PtrCustomDefaultHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
    }

    public PtrCustomDefaultHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }
}
