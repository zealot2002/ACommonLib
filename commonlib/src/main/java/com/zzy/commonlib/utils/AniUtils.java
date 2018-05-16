package com.zzy.commonlib.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * @author zzy
 * @date 2018/4/12
 */

public class AniUtils {
    /**
     * 渐隐
     */
    public static void startHideAnimation(final View view, int duration ){
        if( null == view || duration < 0 ){
            return;
        }
        AlphaAnimation ani = new AlphaAnimation(1.0f, 0.0f);
        ani.setDuration( duration );
        ani.setFillAfter( true );
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation( ani );
    }
    /**
     * 渐显
     */
    public static void startShowAnimation(final View view, int duration){
        if( null == view || duration < 0 ){
            return;
        }
        view.setVisibility(View.VISIBLE);
        AlphaAnimation ani = new AlphaAnimation(0.0f, 1.0f);
        ani.setDuration( duration );
        ani.setFillAfter( true );
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation( ani );
    }

    public static int[] getAniRes(Context context, int arrayId) {
        TypedArray typedArray = context.getResources().obtainTypedArray(arrayId);
        int len = typedArray.length();
        int[] resId = new int[len];
        for (int i = 0; i < len; i++) {
            resId[i] = typedArray.getResourceId(i, -1);
        }
        typedArray.recycle();
        return resId;
    }

}
