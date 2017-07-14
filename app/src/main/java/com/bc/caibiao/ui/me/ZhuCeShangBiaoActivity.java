package com.bc.caibiao.ui.me;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.DoSignAct;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.BaseTestModel;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.qiming.SearchServiceAct;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.bc.caibiao.R.id.etName;
import static com.bc.caibiao.R.id.etUserName;

/**
 * Created  on 2017/4/19.
 * 复查详情
 */

public class ZhuCeShangBiaoActivity extends BaseActivity {
    /**
     * UI元素
     */
    //商标名称
    @Bind(etName)
    EditText mEtName;

    //产品
    @Bind(R.id.etProduct)
    EditText mEtProduct;

    //联系人
    @Bind(etUserName)
    EditText mEtUserName;

    //联系电话
    @Bind(R.id.etTelphone)
    EditText mEtTelphone;

    //上传
    @Bind(R.id.zhuce_shangchuan)
    ImageView mIvZhuCeShangChuan;

    //申请注册
    @Bind(R.id.apply_zc)
    TextView mTvApplyZC;

    //复查
    @Bind(R.id.recheck)
    TextView mTvRecheck;
    ArrayList<String> photoPath = new ArrayList<>();
    ArrayList<File> photoPathFile = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce_shangbiao);
        initView();
    }

    private void initView() {
        mEtName = (EditText) findViewById(etName);
        mEtProduct = (EditText) findViewById(R.id.etProduct);
        mEtUserName = (EditText) findViewById(etUserName);
        mEtTelphone = (EditText) findViewById(R.id.etTelphone);
        mIvZhuCeShangChuan = (ImageView) findViewById(R.id.zhuce_shangchuan);
        mIvZhuCeShangChuan.setOnClickListener(this);
        mTvApplyZC = (TextView) findViewById(R.id.apply_zc);
        mTvApplyZC.setOnClickListener(this);
        mTvRecheck = (TextView) findViewById(R.id.recheck);
        mTvRecheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhuce_shangchuan:
                selectPhoto();
                break;
            case R.id.apply_zc:
                doApplyRegister();
                break;
            case R.id.recheck:
                Intent intent = new Intent(this, SearchServiceAct.class);
                intent.putExtra("ShangBiaoName", mEtName.getText().toString().trim());
                startActivity(intent);
                break;
        }
    }

    private void doApplyRegister() {
        if (TextUtils.isEmpty(mEtName.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入您需要注册的商标名称");
            return;
        }

        if (TextUtils.isEmpty(mEtUserName.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入您的姓名");
            return;
        }

        if (TextUtils.isEmpty(mEtTelphone.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入您的电话");
            return;
        }


        final Map<String, RequestBody> bodyMap = new HashMap<>();
        if(photoPathFile.size() > 1){
            for (int i = 0; i < photoPathFile.size(); i++) {
                bodyMap.put("business_license" + "\"; filename=\"" + photoPathFile.get(i).getName() + ".png", RequestBody.create(MediaType.parse("image/png"), photoPathFile.get(i)));
            }
        }

        bodyMap.put("customer_name", RequestBody.create(MediaType.parse("text/plain"), mEtUserName.getText().toString().trim()));
        bodyMap.put("customer_telephone", RequestBody.create(MediaType.parse("text/plain"), mEtTelphone.getText().toString().trim()));
        bodyMap.put("trademark_name", RequestBody.create(MediaType.parse("text/plain"), mEtName.getText().toString().trim()));
        bodyMap.put("remarks", RequestBody.create(MediaType.parse("text/plain"), mEtProduct.getText().toString().trim()));

        BCHttpRequest.getMarkInterface().trademarkApplyApi(bodyMap)
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
                            ToastUtils.showShort(ZhuCeShangBiaoActivity.this, "发布成功");
                            finish();
                        } else {
                            ToastUtils.showShort(ZhuCeShangBiaoActivity.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });
    }

    private void doCompress(final String filePath) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Luban.get(ZhuCeShangBiaoActivity.this)
                        .load(new File(filePath))                     //传人要压缩的图片
                        .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                        .setCompressListener(new OnCompressListener() { //设置回调

                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {
                                photoPathFile.add(file);
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        }).launch();
            }
        });
    }

    /**
     * 选择照片
     */
    @AfterPermissionGranted(Constant.REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void selectPhoto() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
            startActivityForResult(BGAPhotoPickerActivity.newIntent(mContext, takePhotoDir, 1, null, false), Constant.REQUEST_CODE_CHOOSE_PHOTO_IDLEFT);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", Constant.REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    /**
     * 处理选择的照片
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_CHOOSE_PHOTO_IDLEFT) {
                photoPath = BGAPhotoPickerActivity.getSelectedImages(data);
                if (photoPath.size() > 0) {
                    File file = new File(photoPath.get(0));
                    Glide.with(mContext).load(file).into(mIvZhuCeShangChuan);
                    doCompress(BGAPhotoPickerActivity.getSelectedImages(data).get(0));
                }
            }
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
