package com.wz.zklibrary.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/*****************************************************
 * author:      wz
 * email:       wangzhong0116@foxmail.com
 * version:     1.0
 * date:        2016/10/8 14:09
 * description:
 *****************************************************/

public class SlideInLeftAnimation implements BaseAnimation {

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[] {
                ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
        };
    }
}
