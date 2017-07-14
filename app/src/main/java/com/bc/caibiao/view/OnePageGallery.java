package com.bc.caibiao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * Created by chengyanfang on 2017/4/26.
 */

public class OnePageGallery  extends Gallery {
    public OnePageGallery(Context context) {
        super(context);
    }

    public OnePageGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnePageGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        int kEvent;
        if(isScrollingLeft(e1, e2)){ //Check if scrolling left
            kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        } else{ //Otherwise scrolling right
            kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(kEvent, null);

        return true;
    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2){
        return e2.getX() > e1.getX();

    }
}
