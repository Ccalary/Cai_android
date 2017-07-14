package com.bc.caibiao.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.BCL;
import com.bc.caibiao.utils.PhotoUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * @author wangkai
 * @Description: 裁剪页面
 * create at 2015/11/27 15:26
 */
public class CropImageActivity extends BaseActivity {
    private ClipImageLayout cli;
    private String mPath;
    private TopBarLayout tblTitle;
    private ClipImageLayout cil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        BCL.e("============");
        mPath = getIntent().getStringExtra("path");
        this.tblTitle = (TopBarLayout) findViewById(R.id.tblTitle);
        this.tblTitle.getLlRight().setOnClickListener(this);
        this.cli = (ClipImageLayout) findViewById(R.id.cil);
        cli.setImage(mContext, mPath);
    }


    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.llRight:
                compressImage();
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void compressImage() {
        showLoading("正在裁剪...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                startCompress();
            }
        }).start();
    }

    private void startCompress() {
        try {
            File imageFile = new File(BaseApplication.SD_SAVEDIR + File.separator + PhotoUtil.setFileName());
            Bitmap photo;
            photo = cli.clip();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            imageFile = PhotoUtil.getImageFile(imageFile, stream);
            String newPath = BaseApplication.SD_SAVEDIR + File.separator + PhotoUtil.setFileName();
            imageFile.renameTo(new File(newPath));
            imageFile = new File(newPath);
            photo.recycle();
            photo = null;
            Message message = new Message();
            message.what = 1;
            message.obj = newPath;
            handler.sendMessage(message);
        } catch (Exception e) {
            handler.sendEmptyMessage(2);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    dismissLoading();
                    String newPath = (String) msg.obj;
                    Intent intent = new Intent();
                    intent.putExtra("path", newPath);
                    setResult(RESULT_OK, intent);
                    System.gc();
                    finish();
                    break;
                case 2:
                    dismissLoading();
                    finish();
                    break;
            }
        }
    };


}
