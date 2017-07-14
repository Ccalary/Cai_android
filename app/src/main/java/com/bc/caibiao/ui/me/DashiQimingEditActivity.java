package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.InterpretationImageAdapter;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.shangbiao.PicSearchActivity;
import com.bc.caibiao.utils.AlertDialogUtils;
import com.bc.caibiao.utils.BCL;
import com.bc.caibiao.utils.ImageLoad;
import com.bc.caibiao.utils.PhotoUtil;
import com.bc.caibiao.view.ClearEditText;
import com.bc.caibiao.view.MyGridView;
import com.bc.caibiao.view.TopBarLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;

/**
 * Created by Administrator on 2017/3/23.
 */

public class DashiQimingEditActivity extends BaseActivity {

    private com.bc.caibiao.view.TopBarLayout topBar;
    private com.bc.caibiao.view.ClearEditText etName;
    private android.widget.EditText etInterpretation;
    private com.bc.caibiao.view.MyGridView gvInterpretation;
    private android.widget.TextView tvSubmit;

    private InterpretationImageAdapter interpretationImageAdapter;
    private File imageFile;
    private Bitmap bitmap;
    private String selectedPicPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashi_qiming);
        this.tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        this.gvInterpretation = (MyGridView) findViewById(R.id.gvInterpretation);
        this.etInterpretation = (EditText) findViewById(R.id.etInterpretation);
        this.etName = (ClearEditText) findViewById(R.id.etName);
        this.topBar = (TopBarLayout) findViewById(R.id.topBar);
        initView();
        initData();
    }

    private void initView(){
        this.gvInterpretation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    showChooseDialog();
                }
            }
        });

    }

    @Override
    public void OnClick(View v) {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initData(){
        interpretationImageAdapter=new InterpretationImageAdapter(this);
        interpretationImageAdapter.addItemAndRefresh("");
        gvInterpretation.setAdapter(interpretationImageAdapter);
    }


    private void showChooseDialog() {
        new AlertDialogUtils(mContext, new AlertDialogUtils.onClickResult() {
            @Override
            public void onResult(Object result) {
                int tag = (int) result;
                if (tag == AlertDialogUtils.DIALOG_PHOTO) {
                    if (PhotoUtil.sdCardState()) {
                        PhotoUtil.getPhoto(imageFile, DashiQimingEditActivity.this);
                    }
                } else if (tag == AlertDialogUtils.DIALOG_ALBUM) {
                    if (PhotoUtil.sdCardState()) {
                        PhotoUtil.getPicture(DashiQimingEditActivity.this);
                    }
                }
            }
        }).ShowPhoto();
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
                    this.imageFile=imageFile;
                }
                File imageFile = Compressor.getDefault(mContext).compressToFile(this.imageFile);
                selectedPicPath = imageFile.getAbsolutePath();
                //显示图片到界面上
                for(String str:interpretationImageAdapter.getList()){

                }
                interpretationImageAdapter.addItemAndRefresh(selectedPicPath);
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
                        File file = Compressor.getDefault(mContext).compressToFile(new File(path));
                        selectedPicPath = file.getAbsolutePath();
                        //显示图片到界面上
                        interpretationImageAdapter.addItemAndRefresh(selectedPicPath);
                    } else {
                        String path = uri.getPath();
                        File file = Compressor.getDefault(mContext).compressToFile(new File(path));
                        selectedPicPath = file.getAbsolutePath();
                        //显示图片到界面上
                        interpretationImageAdapter.addItemAndRefresh(selectedPicPath);
                    }
                } catch (Exception e) {
                    BCL.e(e);
                }
                break;
        }
    }
}
