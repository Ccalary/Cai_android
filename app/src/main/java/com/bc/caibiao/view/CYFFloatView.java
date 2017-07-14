package com.bc.caibiao.view;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.utils.AppUtil;

/**
 * Created by chengyanfang on 2017/5/2.
 */

public class CYFFloatView  extends RelativeLayout {
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    Context mContext;

    private WindowManager wm=(WindowManager)getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

    //此wmParams为获取的全局变量，用以保存悬浮窗口的属性
    private WindowManager.LayoutParams wmParams = BaseApplication.getInstance().getMywmParams();

    public CYFFloatView(Context context) {
        super(context);
        mContext = context;
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY()-25;   //25是系统状态栏的高度
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取相对View的坐标，即以此View左上角为原点
                mTouchStartX =  event.getX();
                mTouchStartY =  event.getY();
                Log.d("Tag","ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:

                boolean condition1 =  Math.abs(event.getX()-mTouchStartX) == 0 &&  Math.abs(event.getY()-mTouchStartY)<AppUtil.dip2px(mContext,50);
                boolean condition3 =  Math.abs(event.getX()-mTouchStartX)< AppUtil.dip2px(mContext,10) &&  Math.abs(event.getY()-mTouchStartY)<AppUtil.dip2px(mContext,10);

                if (condition3 || condition1)
                {
                    BaseApplication.getInstance().forwardTokefuAct();
                }else
                {
                    moveViewPosition();
                    mTouchStartX=mTouchStartY=0;
                }
                break;
        }
        return true;
    }

    private void updateViewPosition(){
        //更新浮动窗口位置参数
        wmParams.x=(int)( x-mTouchStartX);
        wmParams.y=(int) (y-mTouchStartY);
        wm.updateViewLayout(this, wmParams);
    }

    private void moveViewPosition(){

        if ((x-mTouchStartX) > AppUtil.getWidth(getContext())/2 )
        {
            wmParams.x=(int)AppUtil.getWidth(getContext());
        }else
        {
            wmParams.x=0;
        }
        wmParams.y=(int) (y-mTouchStartY);
        wm.updateViewLayout(this, wmParams);
    }

}
