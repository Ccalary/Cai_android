package com.bc.caibiao.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bc.caibiao.R;

/**
 * @author wangkai
 * @create 16/9/6 下午1:22
 * @Description :TopBar
 */
public class EditTextLayout extends LinearLayout {
    private String digits, hint;
    private int image, maxLength, inputType;

    private ImageView ivLeft;
    private android.widget.EditText etCommon;

    public EditTextLayout(Context context) {
        super(context);
    }


    public EditTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(attrs);
        initView(context);
    }


    public EditTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("InflateParams")
    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout llET = (LinearLayout) inflater.inflate(R.layout.layout_common_edittext, null);
        this.etCommon = (EditText) llET.findViewById(R.id.etCommon);
        this.ivLeft = (ImageView) llET.findViewById(R.id.ivLeft);
        etCommon.setHint(hint);
        etCommon.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        etCommon.setSingleLine(true);
        switch (inputType) {
            case InputType.TYPE_TEXT_VARIATION_PASSWORD:
                etCommon.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case InputType.TYPE_CLASS_PHONE:
                etCommon.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case InputType.TYPE_CLASS_NUMBER:
                etCommon.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            default:
                break;
        }

        if (!TextUtils.isEmpty(digits)) {
            etCommon.setKeyListener(DigitsKeyListener.getInstance(digits));
        }
        ivLeft.setImageResource(image);
        addView(llET);
    }

    @SuppressLint("Recycle")
    private void initAttributeSet(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CommonET);
        //限制文字
        digits = typedArray.getString(R.styleable.CommonET_digits);
        //提示文字
        hint = typedArray.getString(R.styleable.CommonET_hint);
        //输入方式
        inputType = typedArray.getInt(R.styleable.CommonET_inputType, InputType.TYPE_NULL);
        //最长长度
        maxLength = typedArray.getInteger(R.styleable.CommonET_maxLength, Integer.MAX_VALUE);
        //左侧图片
        image = typedArray.getResourceId(R.styleable.CommonET_image, 0);
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public EditText getEtCommon() {
        return etCommon;
    }
}
