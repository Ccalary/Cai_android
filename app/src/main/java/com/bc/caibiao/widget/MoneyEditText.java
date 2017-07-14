package com.bc.caibiao.widget;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;

public class MoneyEditText extends EditText {
    public MoneyEditText(Context context) {
        super(context);
        init();
    }

    public MoneyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoneyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFilters(new InputFilter[]{lengthFilter});
    }

    /**
     * 输入框小数的位数
     */
    private static final int DECIMAL_DIGITS = 2;

    private InputFilter lengthFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            // source:当前输入的字符
            // start:输入字符的开始位置
            // end:输入字符的结束位置
            // dest：当前已显示的内容
            // dstart:当前光标开始位置
            // dent:当前光标结束位置
            if (dest.length() == 0 && source.equals(".")) {
                return "0.";
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                if (dotValue.length() == DECIMAL_DIGITS) {
                    return "";
                }
            }
            return null;
        }

    };


}
