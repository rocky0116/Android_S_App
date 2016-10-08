package com.wz.zklibrary.animation;

import android.animation.Animator;
import android.view.View;

/*****************************************************
 * author:      wz
 * email:       wangzhong0116@foxmail.com
 * version:     1.0
 * date:        2016/10/8 14:04
 * description:
 *****************************************************/

public interface BaseAnimation {
    Animator[] getAnimators(View view);
}
