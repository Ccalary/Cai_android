package com.bc.caibiao.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public final class SimpleAnimation {
    private static Animation animation_alpha, animation_scale, animation_translate, animation_rotate;
    private static AnimationSet animationSet;

    public static void initAnimation(View mView) {
        // 透明度控制动画效果 alpha
        animation_alpha = new AlphaAnimation(0.1f, 1.0f);
        // 第一个参数fromAlpha为 动画开始时候透明度
        // 第二个参数toAlpha为 动画结束时候透明度
        animation_alpha.setRepeatCount(0);// 设置循环
        animation_alpha.setDuration(1000);// 设置时间持续时间为 1000毫秒
        // 旋转效果rotate
        animation_rotate = new RotateAnimation(0, -720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        // 第一个参数fromDegrees为动画起始时的旋转角度 //第二个参数toDegrees为动画旋转到的角度
        // 第三个参数pivotXType为动画在X轴相对于物件位置类型 //第四个参数pivotXValue为动画相对于物件的X坐标的开始位置
        // 第五个参数pivotXType为动画在Y轴相对于物件位置类型 //第六个参数pivotYValue为动画相对于物件的Y坐标的开始位置
        animation_rotate.setRepeatCount(0);
        animation_rotate.setDuration(5000);// 设置时间持续时间为 1000毫秒

        // 尺寸伸缩动画效果 scale
        animation_scale = new ScaleAnimation(0.1f, 3.0f, 0.1f, 3.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // 第一个参数fromX为动画起始时 X坐标上的伸缩尺寸
        // 第二个参数toX为动画结束时 X坐标上的伸缩尺寸
        // 第三个参数fromY为动画起始时Y坐标上的伸缩尺寸
        // 第四个参数toY为动画结束时Y坐标上的伸缩尺寸
		/*
		 * 说明: 以上四种属性值 0.0表示收缩到没有 1.0表示正常无伸缩 值小于1.0表示收缩 值大于1.0表示放大
		 */
        // 第五个参数pivotXType为动画在X轴相对于物件位置类型
        // 第六个参数pivotXValue为动画相对于物件的X坐标的开始位置
        // 第七个参数pivotXType为动画在Y轴相对于物件位置类型
        // 第八个参数pivotYValue为动画相对于物件的Y坐标的开始位置
        animation_scale.setRepeatCount(0);
        animation_scale.setDuration(1000);// 设置时间持续时间为 1000毫秒

        // 移动动画效果translate
        animation_translate = new TranslateAnimation(-20f, 300f, -20f, 300f);
        // 第一个参数fromXDelta为动画起始时 X坐标上的移动位置
        // 第二个参数toXDelta为动画结束时 X坐标上的移动位置
        // 第三个参数fromYDelta为动画起始时Y坐标上的移动位置
        // 第三个参数toYDelta为动画结束时Y坐标上的移动位置
        animation_translate.setRepeatCount(0);// 设置动画执行多少次，如果是-1的话就是一直重复
        animation_translate.setDuration(5000);// 设置时间持续时间为 1000毫秒
        animationSet = new AnimationSet(true);

        animationSet.addAnimation(animation_alpha);// 透明度
        animationSet.addAnimation(animation_rotate);// 旋转
        animationSet.addAnimation(animation_scale);// 尺寸伸缩
        animationSet.addAnimation(animation_translate);// 移动
        mView.startAnimation(animationSet);// 开始播放
    }

    public static int getPopWindowAnimationStyle() {
        return android.R.style.Animation;
    }
	/*
	 * //首先定义显示效果的动画文件： <?xml version="1.0" encoding="utf-8"?> <set
	 * xmlns:android="http://schemas.android.com/apk/res/android"> <translate
	 * android:fromXDelta="0" android:toXDelta="0" android:fromYDelta="120"
	 * android:toYDelta="0" android:duration="500" /> </set>
	 */
	/*
	 * 然后定义消失效果的动画文件：
	 *
	 *
	 * <?xml version="1.0" encoding="utf-8"?> <set
	 * xmlns:android="http://schemas.android.com/apk/res/android"> <translate
	 * android:fromXDelta="0" android:toXDelta="0" android:fromYDelta="0"
	 * android:toYDelta="120" android:duration="500" /> </set>
	 */
	/*
	 * //PopupWindow的动画显示效果是通过setAnimationStyle(int id)方法设置的，其中id为一个style的id，
	 * //所以我们要在styles.xml文件中设置一个动画样式： <style name="popwin_anim_style"> <item
	 * name="android:windowEnterAnimation">@anim/menushow</item> <item
	 * name="android:windowExitAnimation">@anim/menuhide</item> </style>
	 */
}
