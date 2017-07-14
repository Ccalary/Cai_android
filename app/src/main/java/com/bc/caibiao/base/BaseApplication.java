package com.bc.caibiao.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bc.caibiao.BuildConfig;
import com.bc.caibiao.R;
import com.bc.caibiao.ui.qiming.SimpleOutLinkAct;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.view.CYFFloatView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.jiongbull.jlog.JLog;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * @author wangkai
 * @Description : BaseApplication
 * create at 2016/1/17 23:32
 */
public class BaseApplication extends MultiDexApplication
{

    public static BaseApplication instance = null;
    private static SharedPreferences mSharePreference;

    private Context mContext;
    public static String SHARENAME = "caibiao";
    public static String SD_SAVEDIR;
    public static String PIC_CACHE_PATH;
    public static String ImagePath = "";

    /**
     * 视频推流的时候activity与service共享对象
     */
    public static Surface mPreviewSurface;
    /**
     * 指示：0 - 当前直播；1 - 往期直播
     */
    public static int flag = 0;


    public static BaseApplication getInstance() {
        if (instance == null) {
            synchronized (BaseApplication.class) {
                if (instance == null) {
                    instance = new BaseApplication();
                }
            }
        }
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(this);
        instance = this;
        mContext = this;
        initSDPath();
        initFresco();
        initFile();
        initSharedPreference();
        JLog.init(this)
                .setDebug(BuildConfig.DEBUG)
                .setPackagedLevel(1);

        ShareSDK.initSDK(this);


        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initSDPath() {
        SD_SAVEDIR = Environment.getExternalStorageDirectory() + "/caibiao";
        PIC_CACHE_PATH = SD_SAVEDIR + "/" + "cache";
    }

    /**
     * 初始化sharedPreference
     */
    public void initSharedPreference() {
        mSharePreference = getSharedPreferences(SHARENAME, 0);
    }

    /**
     * 初始化文件夹
     */
    private void initFile() {
        File file = new File(BaseApplication.SD_SAVEDIR);
        file.mkdir();
        File file2 = new File(BaseApplication.PIC_CACHE_PATH);
        file2.mkdir();
    }


    /**
     * 初始化Fresco图片加载
     */
    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);
    }

    public static SharedPreferences getSharedPreferences() {
        return mSharePreference;
    }


//    @Override
//    public void uncaughtException(Thread thread, Throwable ex) {
//        Intent i = new Intent(this, SplashActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pi = PendingIntent.getActivity(
//                this.getApplicationContext(), 0, i,
//                Intent.FLAG_ACTIVITY_NEW_TASK);
//        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pi);
//        System.exit(0);
//    }


    /***
     *
     * 客服悬浮图标
     *
     * */
    private WindowManager wm = null;
    CYFFloatView mSafeFloatView;
    int imageHeight;
    int imageWidth;
    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
    public WindowManager.LayoutParams getMywmParams() {
        return wmParams;
    }

    private Activity activity = null;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void createFloatView() {
        mSafeFloatView = new CYFFloatView(getActivity().getApplicationContext());

        /**背景图*/
        final ImageView bgView = new ImageView(this);
        bgView.setImageResource(R.drawable.pic_kefu);
        mSafeFloatView.addView(bgView);

        //获取WindowManager
        wm = (WindowManager) getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数
        wmParams = BaseApplication.getInstance().getMywmParams();

        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;   //设置window type
        wmParams.format = PixelFormat.RGBA_8888;   //设置图片格式，效果为背景透明

        //设置Window flag
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        wmParams.gravity = Gravity.LEFT | Gravity.TOP;   //调整悬浮窗口至左上角
        //以屏幕左上角为原点，设置x、y初始值
        wmParams.x = AppUtil.getWidth(getActivity());
        wmParams.y = AppUtil.getHeight(getActivity()) - AppUtil.dip2px(getActivity(),170);
                //dip2px(getActivity(), 250);

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        bgView.measure(w, h);
        imageHeight = bgView.getMeasuredHeight();
        imageWidth = bgView.getMeasuredWidth();

        //设置悬浮窗口长宽数据
        wmParams.width = AppUtil.dip2px(getActivity(), (imageWidth/2));
        wmParams.height = AppUtil.dip2px(getActivity(), (imageHeight/2));
    }

    public void addFloatView() {
        if (mSafeFloatView == null) {
            try {
                createFloatView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            wm.addView(mSafeFloatView, wmParams);
            isAddedFloatView = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFloatView() {
        try {
            wm.removeView(mSafeFloatView);
            mSafeFloatView = null;
            isAddedFloatView = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  boolean isAddedFloatView = false;

    public void forwardTokefuAct(){
        Intent aIntent = new Intent(this, SimpleOutLinkAct.class);
        if(TextUtils.isEmpty(SP.getInstance().getString(SPTag.TAG_KEFU_URL))){
            aIntent.putExtra("openUrl", URLConfig.KEFU);
        }else{
            aIntent.putExtra("openUrl", SP.getInstance().getString(SPTag.TAG_KEFU_URL));
        }
        aIntent.putExtra("title","客服");
        activity.startActivity(aIntent);
    }

}
