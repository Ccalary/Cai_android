package com.bc.caibiao.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;

/**
 * Created by Administrator on 2017/4/22.
 */

public class BindPhoneActivity extends BaseActivity {




    private final static String EXTRA_ID = "id";
    private final static String EXTRA_TYPE = "type";
    private final static String EXTRA_NAME = "name";
    private final static String EXTRA_IMG = "img";

    @Override
    public BasePresenter initPresenter() {
        return new BindPhonePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
    }


    private void initView(){

    }

    public static void StartMe(Context context, String id, String type, String name, String img) {

        Intent intent = new Intent(context, BindPhoneActivity.class);

        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_IMG, img);

        context.startActivity(intent);


    }

}
