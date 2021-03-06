package com.bc.caibiao.widget.ConvenientBanner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 *
 * @author:
 * @:
 */

public class StarDetailConvenientBanner <T> extends LinearLayout {

    private CBViewHolderCreator holderCreator;
    private List<T> mDatas;
    private int[] page_indicatorId;
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    private CBPageChangeListener pageChangeListener;
    private StarDetailPageAdapter pageAdapter;
    private CBLoopViewPager viewPager;
    private ViewGroup loPageTurningPoint;
    private long autoTurningTime;
    private boolean turning;
    private boolean canTurn = false;
    private boolean manualPageable = true;

    Context mContext;

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    public enum Transformer {
        DefaultTransformer("DefaultTransformer"), AccordionTransformer("AccordionTransformer"), BackgroundToForegroundTransformer(
                "BackgroundToForegroundTransformer"), CubeInTransformer("CubeInTransformer"), CubeOutTransformer("CubeOutTransformer"), DepthPageTransformer(
                "DepthPageTransformer"), FlipHorizontalTransformer("FlipHorizontalTransformer"), FlipVerticalTransformer("FlipVerticalTransformer"), ForegroundToBackgroundTransformer(
                "ForegroundToBackgroundTransformer"), RotateDownTransformer("RotateDownTransformer"), RotateUpTransformer("RotateUpTransformer"), StackTransformer(
                "StackTransformer"), TabletTransformer("TabletTransformer"), ZoomInTransformer("ZoomInTransformer"), ZoomOutSlideTransformer(
                "ZoomOutSlideTransformer"), ZoomOutTranformer("ZoomOutTranformer");

        private final String className;

        // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
        Transformer(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }

    }

    private Handler timeHandler = new Handler();
    private Runnable adSwitchTask = new Runnable() {
        @Override
        public void run() {
            if (viewPager != null && turning) {
                int page = viewPager.getCurrentItem() + 1;
                viewPager.setCurrentItem(page);
                timeHandler.postDelayed(adSwitchTask, autoTurningTime);
            }
        }
    };

    public StarDetailConvenientBanner(Context context) {
        this(context, null);
    }

    public StarDetailConvenientBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(R.layout.include_viewpager, this, true);
        viewPager = (CBLoopViewPager) hView.findViewById(R.id.cbLoopViewPager);
        loPageTurningPoint = (ViewGroup) hView.findViewById(R.id.loPageTurningPoint);
        initViewPagerScroll();
    }

    public StarDetailConvenientBanner setPages(CBViewHolderCreator holderCreator, List<T> datas, String header) {
        this.mDatas = datas;
        this.holderCreator = holderCreator;
        pageAdapter = new StarDetailPageAdapter(holderCreator, mDatas, header);
        viewPager.setAdapter(pageAdapter);
        viewPager.setBoundaryCaching(true);

        if (page_indicatorId != null)
            setPageIndicator(page_indicatorId);
        return this;
    }

    /**
     * 通知数据变化
     */
    public void notifyDataSetChanged() {
        viewPager.getAdapter().notifyDataSetChanged();
        if (page_indicatorId != null)
            setPageIndicator(page_indicatorId);
    }

    /**
     * 设置底部指示器是否可见
     *
     * @param visible
     */
    public StarDetailConvenientBanner setPointViewVisible(boolean visible) {
        loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 底部指示器资源图片
     *
     * @param page_indicatorId
     */
    public StarDetailConvenientBanner setPageIndicator(int[] page_indicatorId) {
        loPageTurningPoint.removeAllViews();
        mPointViews.clear();
        this.page_indicatorId = page_indicatorId;
        if (mDatas == null)
            return this;
        for (int count = 0; count < mDatas.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 0, 5, 0);
            if (mPointViews.isEmpty())
                pointView.setImageResource(page_indicatorId[1]);
            else
                pointView.setImageResource(page_indicatorId[0]);
            mPointViews.add(pointView);
            loPageTurningPoint.addView(pointView);
        }
        pageChangeListener = new CBPageChangeListener(mPointViews, page_indicatorId);
        viewPager.setOnPageChangeListener(pageChangeListener);

        return this;
    }

    /**
     * 指示器的方向
     *
     * @param align
     *            三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中
     *            （RelativeLayout.CENTER_HORIZONTAL），居右
     *            （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */
    public StarDetailConvenientBanner setPageIndicatorAlign(PageIndicatorAlign align) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) loPageTurningPoint.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, align == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, align ==PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, align == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
        loPageTurningPoint.setLayoutParams(layoutParams);
        return this;
    }

    /***
     * 开始翻页
     *
     * @param autoTurningTime
     *            自动翻页时间
     * @return
     */
    public StarDetailConvenientBanner startTurning(long autoTurningTime) {
        // 如果是正在翻页的话先停掉
        if (turning) {
            stopTurning();
        }
        // 设置可以翻页并开启翻页
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        timeHandler.postDelayed(adSwitchTask, autoTurningTime);
        return this;
    }

    public void stopTurning() {
        turning = false;
        timeHandler.removeCallbacks(adSwitchTask);
    }

    /**
     * 自定义翻页动画效果
     *
     * @param transformer
     * @return
     */
    public StarDetailConvenientBanner setPageTransformer(ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
        return this;
    }

    /**
     * 自定义翻页动画效果，使用已存在的效果
     *
     * @param transformer
     * @return
     */
    public StarDetailConvenientBanner setPageTransformer(Transformer transformer) {
        try {
            String pkName = getClass().getPackage().getName();
            viewPager.setPageTransformer(true, (ViewPager.PageTransformer) Class.forName(pkName + ".transforms." + transformer.getClassName()).newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置ViewPager的滑动速度
     * */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(viewPager.getContext());
            // scroller.setScrollDuration(1500);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            // 开始翻页
            if (canTurn)
                startTurning(autoTurningTime);
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn)
                stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }
}
