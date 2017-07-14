package com.bc.caibiao.ui.search;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.IndustryList;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.model.HomePageModel.TaskParentModel;
import com.bc.caibiao.model.MarkModel.TagList;
import com.bc.caibiao.model.VersionModel;
import com.bc.caibiao.model.VersionModelInfo;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseFragment;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.login.LoginActivity;
import com.bc.caibiao.ui.qiming.SimpleOutLinkAct;
import com.bc.caibiao.ui.shangbiao.ResultActivity;
import com.bc.caibiao.utils.Config;
import com.bc.caibiao.utils.MarkModuleUtil;
import com.bc.caibiao.utils.PackageUtil;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.popupwindow.UpdatePopWindow;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class SearchHomeFragment  extends BaseFragment {

    public static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    public static final int REQUEST_CODE_CHOOSE_PHOTO_IDLEFT = 1000;
    private String selectedPicPath;

    @Bind(R.id.etBrandName)
    EditText mBrandEt;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null)
        {
            mView = inflater.inflate(R.layout.fragment_search_layout, container, false);
            ButterKnife.bind(this,mView);
        }
        isCreateView = true;
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        super.initView();

        mView.findViewById(R.id.tv_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aIntent = new Intent(getActivity(), SimpleOutLinkAct.class);
                aIntent.putExtra("openUrl", URLConfig.BRAND_SEARCH);
                aIntent.putExtra("title","商标分类");
                startActivity(aIntent);
            }
        });

        mView.findViewById(R.id.rlv_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });

        mView.findViewById(R.id.search_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSearch();
            }
        });

    }

    UpdatePopWindow mUpdatePopWindow;

    @Override
    protected void initData() {
        super.initData();

        searchKefuURL();



        BCHttpRequest.getQiMingInterface().getVersionInfoApi("1", ""+PackageUtil.getVersionCode(getActivity()))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VersionModelInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final VersionModelInfo updateModel) {
                        if (0 != updateModel.data.updateState) {
                            mUpdatePopWindow = new UpdatePopWindow(getActivity(), mView, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    doVersionUpdate(updateModel.data.downLoadPath);
                                }
                            }, updateModel.data.versionDesc, updateModel.data.updateState == 2?"1":"0");
                            mUpdatePopWindow.show();
                        }
                    }
                });
    }

    private void doVersionUpdate(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            this.startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void onClickSearch(){
        if(TextUtils.isEmpty(mBrandEt.getText().toString())){
            ToastUtils.showShort(getActivity(),"请输入商品名称");
            return;
        }

        //是否登录
        if(!MarkModuleUtil.isLogin()){
            Intent aIntent = new Intent(getActivity(), LoginActivity.class);
            startActivity(aIntent);
            return;
        }

        //跳转到搜索页面
        Intent aIntent = new Intent(getActivity(),ResultActivity.class);
        aIntent.putExtra("searchKey",mBrandEt.getText().toString().trim());
        aIntent.putExtra("searchType",1);
        startActivity(aIntent);
    }



    /**
     * 选择照片
     * */
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void selectPhoto(){
        //是否登录
        if(!MarkModuleUtil.isLogin()){
            Intent aIntent = new Intent(getActivity(), LoginActivity.class);
            aIntent.putExtra("isNeedBack",true);
            startActivity(aIntent);
        }
        else{
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if (EasyPermissions.hasPermissions(mContext, perms)) {
                File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
                startActivityForResult(BGAPhotoPickerActivity.newIntent(mContext, takePhotoDir, 1, null, false), REQUEST_CODE_CHOOSE_PHOTO_IDLEFT);
            } else {
                EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
            }
        }
    }

    /**
     * 处理选择的照片
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CODE_CHOOSE_PHOTO_IDLEFT){
                selectedPicPath = BGAPhotoPickerActivity.getSelectedImages(data).get(0);
                Intent aIntent = new Intent(getActivity(),ResultActivity.class);
                aIntent.putExtra("searchImgFile",selectedPicPath);
                aIntent.putExtra("searchType",2);
                startActivity(aIntent);
            }
        }
    }


    private void searchKefuURL(){

        BCHttpRequest.getQiMingInterface().getIndustryDataApi("dg_kefu_link")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IndustryList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IndustryList industryList) {
                        SP.getInstance().saveString(SPTag.TAG_KEFU_URL,industryList.data.get(0).itemContent);
                    }
                });

    }


}
