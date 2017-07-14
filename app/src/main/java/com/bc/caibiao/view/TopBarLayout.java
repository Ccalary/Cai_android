package com.bc.caibiao.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;

/**
 * @author wangkai
 * @create 16/9/6 下午1:22
 * @Description :TopBar
 */
public class TopBarLayout extends RelativeLayout {
    private LinearLayout llLeft, llRight;
    private TextView tvLeft, tvTitle, tvRight;
    private ImageView ivLeft, ivRight;
    private View vTop;
    private String strTitle;
    private String strLeft;
    private String strRight;
    private int resLeft;
    private int resRight;
    private int resRightColor;
    private int textRightSize;
    private int textTitleColor;
    private int bgColor;
    private boolean leftShow, rightShow;

    public TopBarLayout(Context context) {
        super(context);
    }


    public TopBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(attrs);
        initView(context);
    }


    public TopBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("InflateParams")
    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout llTop = (LinearLayout) inflater.inflate(R.layout.layout_top_bar, null);
        llLeft = (LinearLayout) llTop.findViewById(R.id.llLeft);
        llRight = (LinearLayout) llTop.findViewById(R.id.llRight);
        tvLeft = (TextView) llTop.findViewById(R.id.tvLeft);
        tvTitle = (TextView) llTop.findViewById(R.id.tvTitle);
        tvRight = (TextView) llTop.findViewById(R.id.tvRight);
        ivLeft = (ImageView) llTop.findViewById(R.id.ivLeft);
        ivRight = (ImageView) llTop.findViewById(R.id.ivRight);
        vTop = llTop.findViewById(R.id.vTop);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            ViewGroup.LayoutParams params = vTop.getLayoutParams();
//            params.height = ScreenUtils.getStatusHeight(context);
//            vTop.setLayoutParams(params);
//        } else {
//        vTop.setVisibility(View.GONE);
//        }
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setText(strTitle);
        }
        if (!TextUtils.isEmpty(strLeft)) {
            tvLeft.setText(strLeft);
        }
        if (!TextUtils.isEmpty(strRight)) {
            tvRight.setText(strRight);
        }
        ivLeft.setImageResource(resLeft);

        if (resRight != 0) {
            ivRight.setImageResource(resRight);
        }
        if (resRightColor != 0) {
            tvRight.setTextColor(getResources().getColor(resRightColor));
        }
        if (textRightSize != 0) {
            tvRight.setTextSize(textRightSize);
        }
        if(textTitleColor!=0){
            tvTitle.setTextColor(getResources().getColor(textTitleColor));
        }
        if(bgColor!=0){
            llTop.setBackgroundColor(getResources().getColor(bgColor));
        }
        llLeft.setVisibility(leftShow ? View.VISIBLE : View.INVISIBLE);
        llRight.setVisibility(rightShow ? View.VISIBLE : View.INVISIBLE);
        addView(llTop);
    }

    @SuppressLint("Recycle")
    private void initAttributeSet(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TopBar);
        //标题文字
        strTitle = typedArray.getString(R.styleable.TopBar_textTitle);
        //左侧文字
        strLeft = typedArray.getString(R.styleable.TopBar_textLeft);
        //右侧文字
        strRight = typedArray.getString(R.styleable.TopBar_textRight);
        //左侧图片
        resLeft = typedArray.getResourceId(R.styleable.TopBar_imageLeft, R.drawable.back);
        //右侧图片
        resRight = typedArray.getResourceId(R.styleable.TopBar_imageRight, 0);
        //左侧是否显示
        leftShow = typedArray.getBoolean(R.styleable.TopBar_leftShow, true);
        //右侧是否显示
        rightShow = typedArray.getBoolean(R.styleable.TopBar_rightShow, true);
        //右侧文字颜色
        resRightColor = typedArray.getResourceId(R.styleable.TopBar_textRightColor, 0);
        //右侧文字大小
        textRightSize = typedArray.getDimensionPixelSize(R.styleable.TopBar_textRightSize, 0);
        //标题颜色
        textTitleColor = typedArray.getResourceId(R.styleable.TopBar_textTitleColor, 0);
        //标题背景色
        bgColor = typedArray.getResourceId(R.styleable.TopBar_bgColor, 0);
    }

    public LinearLayout getLlLeft() {
        return llLeft;
    }

    public LinearLayout getLlRight() {
        return llRight;
    }

    @NonNull
    public TextView getTvLeft() {
        return tvLeft;
    }

    @NonNull
    public TextView getTvTitle() {
        return tvTitle;
    }


    @NonNull
    public TextView getTvRight() {
        return tvRight;
    }

    @NonNull
    public ImageView getIvLeft() {
        return ivLeft;
    }

    @NonNull
    public ImageView getIvRight() {
        return ivRight;
    }

    public void showLeft(){
        llLeft.setVisibility(VISIBLE);
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

}
