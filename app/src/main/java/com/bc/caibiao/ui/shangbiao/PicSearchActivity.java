package com.bc.caibiao.ui.shangbiao;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.login.LoginActivity;
import com.bc.caibiao.utils.AlertDialogUtils;
import com.bc.caibiao.utils.BCL;
import com.bc.caibiao.utils.ImageLoad;
import com.bc.caibiao.utils.MarkModuleUtil;
import com.bc.caibiao.utils.PhotoUtil;
import com.bc.caibiao.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import id.zelory.compressor.Compressor;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PicSearchActivity extends BaseActivity  {

    public static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    public static final int REQUEST_CODE_CHOOSE_PHOTO_IDLEFT = 1000;

    private String selectedPicPath;
    private ImageView ivPhoto;
    private android.widget.FrameLayout llUploadPic;
    private android.widget.TextView tvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangbiao_pic_search);
        this.tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(this);
        this.llUploadPic = (FrameLayout) findViewById(R.id.llUploadPic);
        this.ivPhoto = (ImageView) findViewById(R.id.ivPhoto);

        initView();
    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.llUploadPic:
                selectPhoto();
                break;
            case R.id.tvSubmit:
                forwardToSearch();
                break;
        }
    }

    private void initView() {
        this.llUploadPic.setOnClickListener(this);
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    /**
     * 选择照片
     * */
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void selectPhoto(){
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
            startActivityForResult(BGAPhotoPickerActivity.newIntent(mContext, takePhotoDir, 1, null, false), REQUEST_CODE_CHOOSE_PHOTO_IDLEFT);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }


    /**
     * 处理选择的照片
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CODE_CHOOSE_PHOTO_IDLEFT){
                selectedPicPath = BGAPhotoPickerActivity.getSelectedImages(data).get(0);
                Glide.with(mContext).load(selectedPicPath).into(ivPhoto);
            }
        }
    }

    /**
     * 前去搜索页面
     * */
    private void forwardToSearch(){

        if(TextUtils.isEmpty(selectedPicPath)){
            ToastUtils.showShort(this,"请选择搜索图片");
            return;
        }

        //是否登录
        if(!MarkModuleUtil.isLogin()){
            Intent aIntent = new Intent(this, LoginActivity.class);
            aIntent.putExtra("isNeedBack",true);
            startActivity(aIntent);
        }

        //跳转到搜索页面
        Intent aIntent = new Intent(this,ResultActivity.class);
        aIntent.putExtra("searchImgFile",selectedPicPath);
        aIntent.putExtra("searchType",2);
        startActivity(aIntent);

    }
}
