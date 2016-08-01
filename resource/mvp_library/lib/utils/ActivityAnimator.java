package com.dream.android.sample.lib.utils;

import android.app.Activity;
import com.dream.android.sample.lib.R;

/**
 * Description:Tools for activity transition Animation
 *
 * Copyright: Copyright (c) 2016, All rights reserved.
 *
 * @author Dream
 * @date 16/5/27
 */
public class ActivityAnimator {

	public void flipHorizontalAnimation(Activity a) {
		a.overridePendingTransition(R.anim.flip_horizontal_in, R.anim.flip_horizontal_out);
	}

	public void flipVerticalAnimation(Activity a) {
		a.overridePendingTransition(R.anim.flip_vertical_in, R.anim.flip_vertical_out);
	}

	public void fadeAnimation(Activity a) {
		a.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	public void disappearTopLeftAnimation(Activity a) {
		a.overridePendingTransition(R.anim.disappear_top_left_in, R.anim.disappear_top_left_out);
	}

	public void appearTopLeftAnimation(Activity a) {
		a.overridePendingTransition(R.anim.appear_top_left_in, R.anim.appear_top_left_out);
	}

	public void disappearBottomRightAnimation(Activity a) {
		a.overridePendingTransition(R.anim.disappear_bottom_right_in, R.anim.disappear_bottom_right_out);
	}

	public void appearBottomRightAnimation(Activity a) {
		a.overridePendingTransition(R.anim.appear_bottom_right_in, R.anim.appear_bottom_right_out);
	}

	public void unzoomAnimation(Activity a) {
		a.overridePendingTransition(R.anim.unzoom_in, R.anim.unzoom_out);
	}

    public void PullRightPushLeft(Activity a) {
        a.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void PullLeftPushRight(Activity a) {
        a.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

	public void BottomIn(Activity a) {
		a.overridePendingTransition(R.anim.dialog_bottom_enter, R.anim.hold);
	}

	public void BottomExit(Activity a) {
		a.overridePendingTransition(R.anim.hold, R.anim.dialog_bottom_exit);
	}
}
