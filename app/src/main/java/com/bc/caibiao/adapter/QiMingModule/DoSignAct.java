package com.bc.caibiao.adapter.QiMingModule;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.widget.MNoScrollGridView;
import com.bc.caibiao.widget.MyScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
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

/**
 * Created by chengyanfang on 2017/4/22.
 */

public class DoSignAct extends BaseActivity {

    public static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    public static final int REQUEST_CODE_CHOOSE_PHOTO_IDLEFT = 1000;

    @Bind(R.id.etTaskTitle)
    EditText title;

    @Bind(R.id.etDemand)
    EditText content;

    @Bind(R.id.scrollview)
    MyScrollView myScrollView;

    @Bind(R.id.grid_user_center)
    MNoScrollGridView mNoScrollGridView;

    SignPhotoAdapter mAdapter;
    ArrayList<String> photoPath = new ArrayList<>();
    ArrayList<File> photoPathFile = new ArrayList<>();

    String mTougaoId;
    String mType;
    String mTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_sign_act);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initView() {
        findViewById(R.id.ll_qiming).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if("addNew".equals(mType)){
                    onClickUpload();
                }else{
                    requestEditTougao();
                }
            }
        });
    }


    private void initData() {
        mTaskId = getIntent().getStringExtra("mTaskId");
        mType = getIntent().getStringExtra("type");
        mTougaoId = getIntent().getStringExtra("tougaoId");

        if (mType.equals("addNew")) {
            photoPath.add("拍照");
        }else{
            title.setText(getIntent().getStringExtra("title"));
            content.setText(getIntent().getStringExtra("content"));
            photoPath.addAll(getIntent().getStringArrayListExtra("picPaths"));
            loadImgFile(photoPath);
            photoPath.add("拍照");
        }

        mNoScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == photoPath.size() - 1) {
                    selectPhoto();
                }
            }
        });
        setAdapter();


    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new SignPhotoAdapter(this, photoPath);
            mNoScrollGridView.setAdapter(mAdapter);
            mAdapter.setmDeletePhotoCallBack(new SignPhotoAdapter.DeletePhotoCallBack() {
                @Override
                public void deleteAt(int index) {
                    photoPath.remove(index);
                    photoPathFile.remove(index);
                    setAdapter();
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 选择照片
     */
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void selectPhoto() {
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
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE_PHOTO_IDLEFT) {
                photoPath.remove(photoPath.size() - 1);
                photoPath.add(BGAPhotoPickerActivity.getSelectedImages(data).get(0));
                photoPath.add("拍照");
                doCompress(BGAPhotoPickerActivity.getSelectedImages(data).get(0));
                setAdapter();
            }
        }
    }

    private void doCompress(final String filePath) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Luban.get(DoSignAct.this)
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

    private void loadImgFile(final ArrayList<String> photoPath){
        for (int i = 0;i<photoPath.size();i++){
            new getImageCacheAsyncTask(DoSignAct.this).execute(URLConfig.baseUrl_pic_oss+photoPath.get(i));
        }
    }



    private void onClickUpload() {

        if (TextUtils.isEmpty(title.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入标题");
            return;
        }

        if (TextUtils.isEmpty(content.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入释义");
            return;
        }
        showProgressHUD(this, "提交中....");

        final Map<String, RequestBody> bodyMap = new HashMap<>();
        for (int i = 0; i < photoPathFile.size(); i++) {
            bodyMap.put("appPhoto" + "\"; filename=\"" + photoPathFile.get(i).getName() + ".png", RequestBody.create(MediaType.parse("image/png"), photoPathFile.get(i)));
        }

        bodyMap.put("memberId", RequestBody.create(MediaType.parse("text/plain"), SP.getInstance().getString(SPTag.TAG_MEMBER_ID)));
        bodyMap.put("brandName", RequestBody.create(MediaType.parse("text/plain"), title.getText().toString()));
        bodyMap.put("taskId", RequestBody.create(MediaType.parse("text/plain"), mTaskId));
        bodyMap.put("reason", RequestBody.create(MediaType.parse("text/plain"), content.getText().toString()));
        bodyMap.put("isCheck", RequestBody.create(MediaType.parse("text/plain"), "0"));

        BCHttpRequest.getQiMingInterface().touGaoApiNew(bodyMap)
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
                            ToastUtils.showShort(DoSignAct.this, "发布成功");
                            finish();
                        } else {
                            ToastUtils.showShort(DoSignAct.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });
    }


    private void requestEditTougao(){
        if (TextUtils.isEmpty(title.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入标题");
            return;
        }

        if (TextUtils.isEmpty(content.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入释义");
            return;
        }
        showProgressHUD(this, "提交中....");

        final Map<String, RequestBody> bodyMap = new HashMap<>();
        for (int i = 0; i < photoPathFile.size(); i++) {
            bodyMap.put("appPhoto" + "\"; filename=\"" + photoPathFile.get(i).getName() + ".png", RequestBody.create(MediaType.parse("image/png"), photoPathFile.get(i)));
        }

        bodyMap.put("brandName", RequestBody.create(MediaType.parse("text/plain"), title.getText().toString()));
        bodyMap.put("touGaoId", RequestBody.create(MediaType.parse("text/plain"), mTougaoId));
        bodyMap.put("reason", RequestBody.create(MediaType.parse("text/plain"), content.getText().toString()));

        BCHttpRequest.getQiMingInterface().editTouGaoApiNew(bodyMap)
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
                            ToastUtils.showShort(DoSignAct.this, "编辑成功");
                            finish();
                        } else {
                            ToastUtils.showShort(DoSignAct.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });
    }


    /**
     * 下载图片
     * */
    private class getImageCacheAsyncTask extends AsyncTask<String, Void, File> {
        private final Context context;

        public getImageCacheAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected File doInBackground(String... params) {
            String imgUrl =  params[0];
            try {
                return Glide.with(context)
                        .load(imgUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(File result) {
            if (result == null) {
                return;
            }
            photoPathFile.add(result);
        }
    }

}
