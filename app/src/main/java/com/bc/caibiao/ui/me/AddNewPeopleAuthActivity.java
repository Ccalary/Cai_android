package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.model.CityBean;
import com.bc.caibiao.model.MemberAuthApply;
import com.bc.caibiao.model.ProvinceBean;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.utils.BCL;
import com.bc.caibiao.utils.ImageLoad;
import com.bc.caibiao.view.TopBarLayout;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import id.zelory.compressor.Compressor;

public class AddNewPeopleAuthActivity
        extends BaseActivity<AddNewPeopleAuthContract.ActivityView, AddNewPropleAuthPresenter>
        implements AddNewPeopleAuthContract.ActivityView {

    private com.bc.caibiao.view.TopBarLayout topPanel;
    private android.widget.EditText etName;
    private android.widget.EditText etGoodAt;
    private android.widget.TextView tvWordCount;
    private com.facebook.drawee.view.SimpleDraweeView ivPhoto;
    private android.widget.TextView tvSubmit;

    private Bitmap bitmap;
    private String selectedPicPath;

    TextView tvProvince;
    TextView tvCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_people_auth);
        this.tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(this);
        this.ivPhoto = (SimpleDraweeView) findViewById(R.id.ivPhoto);
        ivPhoto.setOnClickListener(this);
        this.tvWordCount = (TextView) findViewById(R.id.tvWordCount);
        this.etGoodAt = (EditText) findViewById(R.id.etGoodAt);
        this.etName = (EditText) findViewById(R.id.etName);
        this.topPanel = (TopBarLayout) findViewById(R.id.topPanel);

        tvProvince = (TextView) findViewById(R.id.tvProvince);
        tvProvince.setOnClickListener(this);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvCity.setOnClickListener(this);

        etGoodAt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = charSequence.length();
                tvWordCount.setText(len +  "/200");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPresenter.getProvinceInfo();
    }


    @Override
    public void OnClick(View v) {
        if (v == tvSubmit) {
            if (TextUtils.isEmpty(etName.getText().toString())) {
                showToast("输入真实姓名");
                return;
            }
            if(mPresenter.getProvinceId()<0 || mPresenter.getCityId()<0){
                showToast("请选择省和市");
                return;
            }
            if(TextUtils.isEmpty(selectedPicPath)){
                showToast("请选择身份证图片");
                return;
            }
            mPresenter.submitAndNewRoom(selectedPicPath, etName.getText().toString(), etGoodAt.getText().toString());
        } else if (v == ivPhoto) {
            mPresenter.selectPic();
        } else if(v==tvProvince){
            ProvinceDialogFragment fragment=ProvinceDialogFragment.getInstance(mPresenter.getProvinceList());
            fragment.setL(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProvinceBean bean=mPresenter.getProvinceList().get(position);
                    mPresenter.setProvinceName(bean.getProvinceName());
                    mPresenter.setProvinceId(bean.getProvinceId());
                    tvProvince.setText(bean.getProvinceName());
                    resetCitySelection();
                }
            });
            fragment.show(getSupportFragmentManager(),"tag1");
        } else if(v==tvCity){
            if(mPresenter.getProvinceId()<0){
                showToast("请选择省份");
            } else {
                for(int i=0;i<mPresenter.getProvinceList().size();i++){
                    ProvinceBean bean=mPresenter.getProvinceList().get(i);
                    if(bean.getProvinceId()==mPresenter.getProvinceId()){
                        mPresenter.setCityBeanList(mPresenter.getProvinceList().get(i).getCities());
                        CityDialogFragment fragment=CityDialogFragment.getInstance(mPresenter.getCityBeanList());
                        fragment.setL(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                CityBean bean=mPresenter.getCityBeanList().get(position);
                                mPresenter.setCityName(bean.getCityName());
                                mPresenter.setCityId(bean.getCityId());
                                tvCity.setText(bean.getCityName());
                            }
                        });
                        fragment.show(getSupportFragmentManager(),"tag2");
                        break;
                    }
                }
            }
        }
    }

    @Override
    public AddNewPropleAuthPresenter initPresenter() {
        return new AddNewPropleAuthPresenter(mContext, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.PHOTO:
                if (resultCode != RESULT_OK) {
                    return;
                }
                if (data == null || data.getExtras() == null) {
                    File imageFile = new File(BaseApplication.SD_SAVEDIR, BaseApplication.ImagePath);
                    mPresenter.setImageFile(imageFile);
                }
                File imageFile = Compressor.getDefault(mContext).compressToFile(mPresenter.getImageFile());
                selectedPicPath = imageFile.getAbsolutePath();
                //显示图片到界面上
                bitmap = BitmapFactory.decodeFile(selectedPicPath);
                ImageLoad.loadLocalFile(ivPhoto, selectedPicPath);
                break;
            case Constant.ALBUM:
                if (resultCode != RESULT_OK || data == null) {
                    return;
                }
                try {
                    Uri uri = data.getData();
                    if (!TextUtils.isEmpty(uri.getAuthority())) {
                        Cursor cursor = getContentResolver().query(uri,
                                new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                        if (null == cursor) {
                            return;
                        }
                        cursor.moveToFirst();
                        String path = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));
                        cursor.close();
                        mPresenter.setImageFile(new File(path));
                        File file = Compressor.getDefault(mContext).compressToFile(mPresenter.getImageFile());
                        selectedPicPath = file.getAbsolutePath();
                        //显示图片到界面上
                        bitmap = BitmapFactory.decodeFile(selectedPicPath);
                        ImageLoad.loadLocalFile(ivPhoto, selectedPicPath);
                    } else {
                        String path = uri.getPath();
                        mPresenter.setImageFile(new File(path));
                        File file = Compressor.getDefault(mContext).compressToFile(mPresenter.getImageFile());
                        selectedPicPath = file.getAbsolutePath();
                        //显示图片到界面上
                        bitmap = BitmapFactory.decodeFile(selectedPicPath);
                        ImageLoad.loadLocalFile(ivPhoto, selectedPicPath);
                    }
                } catch (Exception e) {
                    BCL.e(e);
                }
                break;
        }
    }

    @Override
    public String getSelectedLivePicAbsUrl() {
        return selectedPicPath;
    }

    @Override
    public void resetCitySelection() {
        tvCity.setText("选择市");
        mPresenter.setCityId(-1);
        mPresenter.setCityName("");
    }

    @Override
    public void goToActivity() {
    }
}
