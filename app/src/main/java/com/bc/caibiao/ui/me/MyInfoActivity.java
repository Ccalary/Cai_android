package com.bc.caibiao.ui.me;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.ServerException;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.ImageLoad;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.utils.PhotoUtil;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.ChooseDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created  on 2017/4/19.
 * 个人资料
 */

public class MyInfoActivity extends BaseActivity implements ChooseDialog.OnClickItemPosition {
    private Member mMember;
    /**
     * UI元素
     */
    private SimpleDraweeView mUserIcon;
    private TextView mUserName;

    private RelativeLayout mChangeTouXiang;
    private RelativeLayout mChangeUserName;
    private ChooseDialog choosePhoneDialog;
    private List<String> mPhoneStr = new ArrayList<>();
    private File imageFile = null;

    ArrayList<String> photoPath = new ArrayList<>();
    ArrayList<File> photoPathFile = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_info);
        mUserIcon = (SimpleDraweeView) findViewById(R.id.user_icon);
        mUserName = (TextView) findViewById(R.id.username);
        mChangeTouXiang = (RelativeLayout) findViewById(R.id.change_touxiang);
        mChangeTouXiang.setOnClickListener(this);
        mChangeUserName = (RelativeLayout) findViewById(R.id.change_username);
        mChangeUserName.setOnClickListener(this);


        mPhoneStr.add("上传图片");
        mPhoneStr.add("照相");
        mPhoneStr.add("取消");
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData() {
        showLoading("加载中");
        BCHttpRequest.getOtherInterface().getMyInfoApi(Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)))
                .compose(HttpResponseHelper.<Member>getAllData())
                .subscribe(new Observer<Member>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        if(e instanceof ServerException){
                            ToastUtils.showShort(MyInfoActivity.this, "网络错误");
                        }
                    }

                    @Override
                    public void onNext(Member member) {
                        mMember = member;
                        dismissLoading();
                        refreshUI();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.change_touxiang:
                showPhoneDialog();
                break;
            case R.id.change_username:
                Intent intent = new Intent(this, ResetUsernameActivity.class);
                intent.putExtra("USERNAME", mMember.getMemberName());
                startActivity(intent);
                break;
        }
    }

    private void showPhoneDialog() {
        if (choosePhoneDialog == null) {
            choosePhoneDialog = new ChooseDialog(this);
            choosePhoneDialog.setDatas(mPhoneStr);
            choosePhoneDialog.setOnClickItemPosition(this);
        }
        choosePhoneDialog.show();
    }

    /**
     * 刷新页面
     */
    private void refreshUI() {
        //用户头像
        ImageLoader.progressiveLoad(mMember.getPortrait(), mUserIcon);

        //用户姓名
        mUserName.setText(mMember.getMemberName());
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClickItem(int position, ChooseDialog chooseDialog) {
        if (chooseDialog == choosePhoneDialog) {
            switch (position) {
                case 0:
                    //图库
                    selectPic();
                    break;
                case 1:
                    //相机
                    if (PhotoUtil.sdCardState()) {
                        PhotoUtil.getPhoto(imageFile, this);
                    }
                    break;
                case 2:
                    if (choosePhoneDialog != null) {
                        choosePhoneDialog.dismiss();
                    }
                    break;
            }
        }
    }

    /**
     * 处理选择的照片
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoPath.clear();
        switch (requestCode) {
            case Constant.REQUEST_CODE_CHOOSE_PHOTO_IDLEFT:
                if (resultCode != RESULT_OK) {
                    return;
                }
                photoPath = BGAPhotoPickerActivity.getSelectedImages(data);
                if (photoPath.size() <= 0) {
                    return;
                }
                doCompress(BGAPhotoPickerActivity.getSelectedImages(data).get(0));
                break;
            case Constant.PHOTO:
                if (resultCode != RESULT_OK) {
                    return;
                }
                if (data == null || data.getExtras() == null) {
                    imageFile = new File(BaseApplication.SD_SAVEDIR, BaseApplication.ImagePath);
                }
                File imageFileTmp = Compressor.getDefault(mContext).compressToFile(imageFile);
                photoPath.add(imageFileTmp.getAbsolutePath());
                doCompress(photoPath.get(0));
                break;
        }
    }

    //修改头像请求
    private void modifyUserIconRequest() {
        final Map<String, RequestBody> bodyMap = new HashMap<>();
        if (photoPathFile.size() > 0) {
            for (int i = 0; i < photoPathFile.size(); i++) {
                bodyMap.put("picture" + "\"; filename=\"" + photoPathFile.get(i).getName() + ".png", RequestBody.create(MediaType.parse("image/png"), photoPathFile.get(i)));
            }
        }

        bodyMap.put("memberId", RequestBody.create(MediaType.parse("text/plain"), SP.getInstance().getString(SPTag.TAG_MEMBER_ID)));
        bodyMap.put("firstname", RequestBody.create(MediaType.parse("text/plain"), SP.getInstance().getString(SPTag.TAG_MEMBER_NAME)));

        BCHttpRequest.getMemberInterface().modifyUserBaseInfo(bodyMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BlankModel>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(BlankModel result) {
                        dismissProgressHUD();
                        if (result.fieldErrors.isEmpty()) {
                            ToastUtils.showShort(MyInfoActivity.this, "发布成功");
                            showBitmapToImg();
                        } else {
                            ToastUtils.showShort(MyInfoActivity.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });
    }

    //显示图片到界面上
    private void showBitmapToImg() {
        if(photoPath.size() > 0) {
            ImageLoad.loadLocalFile(mUserIcon, photoPath.get(0));
        }
    }


    /**
     * 选择照片
     */
    @AfterPermissionGranted(Constant.REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void selectPic() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
            startActivityForResult(BGAPhotoPickerActivity.newIntent(mContext, takePhotoDir, 1, null, false), Constant.REQUEST_CODE_CHOOSE_PHOTO_IDLEFT);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", Constant.REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    private void doCompress(final String filePath) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Luban.get(MyInfoActivity.this)
                        .load(new File(filePath))         //传人要压缩的图片
                        .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                        .setCompressListener(new OnCompressListener() { //设置回调

                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {
                                photoPathFile.add(file);
                                modifyUserIconRequest();
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        }).launch();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (choosePhoneDialog != null) {
            choosePhoneDialog.dismiss();
        }
    }
}
