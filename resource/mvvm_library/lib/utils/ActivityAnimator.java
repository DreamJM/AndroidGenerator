package com.wafa.android.pei.lib.utils;

import android.app.Activity;
import com.wafa.android.pei.lib.R;

/**
 * Description:Activity跳转动画工具类
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author jiangm
 * @date 16/5/27
 */
public class ActivityAnimator
{
	/**
	 * 水平翻转动画
     */
	public void flipHorizontalAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.flip_horizontal_in, R.anim.flip_horizontal_out);
	}

	/**
	 * 垂直翻转动画
	 */
	public void flipVerticalAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.flip_vertical_in, R.anim.flip_vertical_out);
	}

	/**
	 * 淡入淡出动画
	 */
	public void fadeAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	/**
	 * 左上退出动画
	 */
	public void disappearTopLeftAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.disappear_top_left_in, R.anim.disappear_top_left_out);
	}

	/**
	 * 左上出现动画
     */
	public void appearTopLeftAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.appear_top_left_in, R.anim.appear_top_left_out);
	}

	/**
	 * 右下退出动画
	 */
	public void disappearBottomRightAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.disappear_bottom_right_in, R.anim.disappear_bottom_right_out);
	}

	/**
	 * 右下出现动画
	 */
	public void appearBottomRightAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.appear_bottom_right_in, R.anim.appear_bottom_right_out);
	}

	/**
	 * 缩放切换动画
     */
	public void unzoomAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.unzoom_in, R.anim.unzoom_out);
	}

	/**
	 * 右进左出动画
     */
    public void PullRightPushLeft(Activity a)
    {
        a.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

	/**
	 * 左进右出动画
     */
    public void PullLeftPushRight(Activity a)
    {
        a.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

	/**
	 * 下方进入动画
     */
	public void BottomIn(Activity a)
	{
		a.overridePendingTransition(R.anim.dialog_bottom_enter, R.anim.hold);
	}

	/**
	 * 下方退出动画
     */
	public void BottomExit(Activity a)
	{
		a.overridePendingTransition(R.anim.hold, R.anim.dialog_bottom_exit);
	}
}
