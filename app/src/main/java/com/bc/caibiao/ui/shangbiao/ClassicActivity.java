package com.bc.caibiao.ui.shangbiao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.ShangbiaoResultAdapter;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.BaseTestModel;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.MarkModel.MarkSearchResult;
import com.bc.caibiao.model.MarkModel.MarkSearchResultList;
import com.bc.caibiao.model.MarkModel.TaskCategory;
import com.bc.caibiao.model.MarkModel.TaskCategoryList;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.ServerException;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.login.LoginActivity;
import com.bc.caibiao.ui.me.ZhuCeShangBiaoActivity;
import com.bc.caibiao.utils.MarkModuleUtil;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.SimpleAnimation;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.widget.ChoiceView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * 搜索结果页面(文字和图片搜索)
 *
 * @author chengyandfang
 * */

public class ClassicActivity extends BaseActivity{
    @Bind(R.id.pop_gridview)
    GridView mGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_gridview);
        ButterKnife.bind(this);
        initView();


        loadData();
    }

    private void initView() {

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSparseBooleanArray= mGridView.getCheckedItemPositions();
                StringBuilder stringBuilder=new StringBuilder("");
                int count=0;
                for(int i=0;i<mSparseBooleanArray.size();i++){
                    if(mSparseBooleanArray.valueAt(i)){
                        if(i<10){
                            stringBuilder.append("0"+i);
                        }
                        else{
                            stringBuilder.append(""+i);
                        }
                        stringBuilder.append(",");
                        count++;
                    }
                }
                if(count==mSparseBooleanArray.size()){
                    curCxcls="";
                    Intent data=new Intent();
                    data.putExtra("classic",curCxcls);
                    setResult(Activity.RESULT_OK,data);
                    finish();
                }
                else if(count==0){
                    ToastUtils.showShort(ClassicActivity.this,"请至少选择一项");
                }
                else{
                    curCxcls=stringBuilder.deleteCharAt(stringBuilder.length()-1).toString();
                    Intent data=new Intent();
                    data.putExtra("classic",curCxcls);
                    setResult(Activity.RESULT_OK,data);
                    finish();
                }
            }
        });
    }

    private String curCxcls="";
    /**
     * 进行搜索
     * */
    private void loadData(){
        showProgressHUD(this,"加载中...");
        BCHttpRequest.getQiMingInterface().getTaskCategoryListApi("1","","100","1")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TaskCategoryList>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TaskCategoryList taskPriceList) {
                        dismissProgressHUD();
                        List<String> data=new ArrayList<String>();
                        for(TaskCategory category:taskPriceList.data){

                            data.add(category.getId()+category.getCategory_name());
                        }
                        createSelectorView1(data);
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


    private SparseBooleanArray mSparseBooleanArray=new SparseBooleanArray();
    private void createSelectorView1(List<String> data) {
        MyAdapter adapter=new MyAdapter(data,this);
        mGridView.setAdapter(adapter);
        for(int i=0;i<adapter.getCount();i++){
            mGridView.setItemChecked(i,mSparseBooleanArray.valueAt(i));
        }
    }

    class MyAdapter extends BaseAdapter{
        private List<String> data;
        private LayoutInflater mInflate;
        public MyAdapter(List<String> data,Context context){
            this.data=data;
            this.mInflate = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=mInflate.inflate(
                        R.layout.pop_grid_item, parent, false);
            }
            TextView textView=(TextView) convertView.findViewById(R.id.title);
            textView.setText(data.get(position));
            return convertView;
        }
    }
}
