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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.ShangbiaoResultAdapter;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.BaseTestModel;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.HomePageModel.TaskSectionType;
import com.bc.caibiao.model.MarkModel.MarkSearchResult;
import com.bc.caibiao.model.MarkModel.MarkSearchResultList;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;

/**
 *
 * 搜索结果页面(文字和图片搜索)
 *
 * @author chengyandfang
 * */

public class ResultActivity extends BaseActivity implements Observer<MarkSearchResultList>{

    //当前页数
    int currentPage = 1;

    //文字搜索关键字
    String mSearchKeyword = "";

    //图片搜索图片地址
    String selectedPicPath = "";

    //搜索类型:1、文字检索   2.图片检索
    int mSerachType = 1;

    ArrayList<MarkSearchResult> mMarkSearchResults = new ArrayList<>();
    ShangbiaoResultAdapter mAdapter;
    GridView mResultGridView;

    PtrFrameLayout mPtrFrameLayout;

    //结果数量
    TextView mResultCountTv;

    boolean isLoadmore = false;

    boolean isCanLoadMore = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        initData();
        initView();

        showProgressHUD(this,"加载中...");
        startSearch();
    }

    private void initData() {
        mSerachType = getIntent().getIntExtra("searchType",1);
        if(mSerachType == 1){
            mSearchKeyword = getIntent().getStringExtra("searchKey");
        }else{
            selectedPicPath = getIntent().getStringExtra("searchImgFile");
        }
    }

    private void initView() {
        mResultCountTv = (TextView)findViewById(R.id.tv_result_count);
        mResultGridView = (GridView) findViewById(R.id.gvSuggestion);
        mPtrFrameLayout = (PtrFrameLayout)findViewById(R.id.ptrFrameLayout);

        findViewById(R.id.signBorrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MarkModuleUtil.isLogin()){
                    Intent aIntent = new Intent(ResultActivity.this, LoginActivity.class);
                    startActivity(aIntent);
                    return;
                }

                Intent intent = new Intent(ResultActivity.this, ZhuCeShangBiaoActivity.class);
                startActivity(intent);
            }
        });


        mPtrFrameLayout.setResistance(1.7f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
        mPtrFrameLayout.setPullToRefresh(false);
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);

        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage = 1;
                startSearch();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        findViewById(R.id.ivMall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent classifyIntent=new Intent(ResultActivity.this,ShangbiaoClassificationActivity.class);
                startActivity(classifyIntent);
            }
        });




        mSelector1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelector1();
            }
        });

        mSelector2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelector2();
            }
        });


        mSelectorTv2.setText("全部");
    }

    private String curCxcls="";
    /**
     * 进行搜索
     * */
    private void startSearch(){

        if(mSerachType == 1){//关键字搜索
            BCHttpRequest.getMarkInterface().searchMarkListByKeyApi(curCxcls,currentIndex,mSearchKeyword,currentPage,10)
                    .compose(HttpResponseHelper.<MarkSearchResultList>getAllData())
                    .subscribe(this);
        }else{
            MultipartBody.Builder requestBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //图片
            File img = new File(selectedPicPath);
            requestBuilder.addFormDataPart("file", img.getName(), RequestBody.create(MediaType.parse("image/*"), img));
            //参数
            requestBuilder.addFormDataPart("cxcls", curCxcls);
            requestBuilder.addFormDataPart("cxtype", currentIndex+"");
            requestBuilder.addFormDataPart("pageNo", String.valueOf(currentPage));
            requestBuilder.addFormDataPart("pageSize", String.valueOf(10));
            RequestBody requestBody = requestBuilder.build();

            BCHttpRequest.getMarkInterface().searchMarkListByImgApi(requestBody)
                    .compose(HttpResponseHelper.<MarkSearchResultList>getAllData())
                    .subscribe(this);
        }
    }


    private void setAdapter(){

        dismissProgressHUD();

        if(mAdapter == null){
            mAdapter = new ShangbiaoResultAdapter(this);
            mAdapter.addList(mMarkSearchResults);
            mResultGridView.setAdapter(mAdapter);

            mAdapter.setLoadmoreInterface(new ShangbiaoResultAdapter.LoadmoreInterface() {
                @Override
                public void loadmore() {
                    if(!isCanLoadMore){
                        ToastUtils.showShort(ResultActivity.this,"已全部加载完毕");
                        return;
                    }
                    if(!isLoadmore){
                        isLoadmore = true;
                        showProgressHUD(ResultActivity.this,"加载中...");
                        currentPage = currentPage + 1;
                        startSearch();
                    }
                }
            });

            mAdapter.setmOnClickAtItemCallback(new ShangbiaoResultAdapter.OnClickAtItemCallback() {
                @Override
                public void onClickAt(int position) {

                    //是否登录
                    if(!MarkModuleUtil.isLogin()){
                        Intent aIntent = new Intent(ResultActivity.this, LoginActivity.class);
                        startActivity(aIntent);
                        return;
                    }



                    Intent aIntent = new Intent(ResultActivity.this, ShangbiaoDetailActivity.class);
                    aIntent.putExtra("cxkeyNum",mMarkSearchResults.get(position).cxkey);
                    aIntent.putExtra("intcls",mMarkSearchResults.get(position).intcls);
                    startActivity(aIntent);
                }
            });

            mAdapter.mFollowInterface = new ShangbiaoResultAdapter.FollowInterface() {
                @Override
                public void doChangeFollow(int index) {
                    MarkSearchResult markSearchResult = mMarkSearchResults.get(index);

                    if(markSearchResult.follow.equals("false")){
                        addFlow(index,markSearchResult.cxkey,markSearchResult.intcls);
                    }else{
                        dismissFlow(index,markSearchResult.cxkey,markSearchResult.intcls);
                    }
                }
            };
        }else{
            mAdapter.clearList();
            mAdapter.addList(mMarkSearchResults);
            mAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void OnClick(View v) {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    //Observe Imp
    @Override
    public void onCompleted() {
        dismissProgressHUD();
        isLoadmore = false;
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressHUD();
        if(e instanceof ServerException){
            ToastUtils.showShort(ResultActivity.this,"网络错误");
        }
        isLoadmore = false;
    }

    @Override
    public void onNext(MarkSearchResultList markSearchResultList) {

        if(currentPage == 1){
            mMarkSearchResults.clear();
            mPtrFrameLayout.refreshComplete();
        }

        if(markSearchResultList.data.size() == 10){
            isCanLoadMore = true;
        }else{
            isCanLoadMore = false;
        }

        mMarkSearchResults.addAll(markSearchResultList.data);

        mResultCountTv.setText("共"+mMarkSearchResults.size()+"条");

        setAdapter();

        isLoadmore = false;
    }


    /**
     * 进行收藏
     * */
    private void addFlow(final int index, String cxkey, String intcls){
        BCHttpRequest.getMarkInterface().getFollowMarkApi(SP.getInstance().getString(SPTag.TAG_MEMBER_ID),cxkey,intcls)
                .compose(HttpResponseHelper.<BaseTestModel>getAllData())
                .subscribe(new ResolveFailSubscribe<BaseTestModel>(mContext, "处理中") {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        ToastUtils.showShort(ResultActivity.this,fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(BaseTestModel member) {
                        mMarkSearchResults.get(index).follow = "true";
                        setAdapter();
                    }
                });
    }

    /**
     * 取消收藏
     * */
    private void dismissFlow(final int index,String cxkey,String intcls){
        BCHttpRequest.getMarkInterface().dismissFollowMarkApi(SP.getInstance().getString(SPTag.TAG_MEMBER_ID),cxkey,intcls)
                .compose(HttpResponseHelper.<BaseTestModel>getAllData())
                .subscribe(new ResolveFailSubscribe<BaseTestModel>(mContext, "处理中") {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        ToastUtils.showShort(ResultActivity.this,fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(BaseTestModel member) {
                        mMarkSearchResults.get(index).follow = "false";
                        setAdapter();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==100){
                curCxcls=data.getStringExtra("classic");
                showProgressHUD(this,"加载中...");
                startSearch();
            }
        }
    }

    @Bind(R.id.tv_selector1)
    TextView mSelectorTv1;

    @Bind(R.id.tv_selector2)
    TextView mSelectorTv2;

    @Bind(R.id.iv_arrow1)
    ImageView mArrow1;

    @Bind(R.id.iv_arrow2)
    ImageView mArrow2;

    @Bind(R.id.llv_selector_layout)
    LinearLayout mSelectorLayout;

    @Bind(R.id.selector1)
    RelativeLayout mSelector1;

    @Bind(R.id.selector2)
    RelativeLayout mSelector2;

    private void showSelector1() {
        startActivityForResult(new Intent(this,ClassicActivity.class),100);
    }

    private void showSelector2(){
        createPopupWindow(mSelectorLayout,createSelectorView2(),mArrow2);
    }

    private PopupWindow mPopupWindow;
    private void createPopupWindow(View parent,View content,final ImageView imageView) {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(content, parent.getWidth(), ViewGroup.LayoutParams.MATCH_PARENT);
        }
        mPopupWindow.setFocusable(true);// 使其聚集
        mPopupWindow.setOutsideTouchable(true);// 设置是否允许在外点击消失
        // mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);//设置弹出窗体需要软键盘
        // mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//再设置模式，和Activity的一样，覆盖，调整大小。
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());// 响应返回键必须的语句。
        mPopupWindow.setAnimationStyle(SimpleAnimation.getPopWindowAnimationStyle());
        mPopupWindow.update();
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                RotateAnimation rotateAnimation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(500);
                rotateAnimation.setFillAfter(true);
                imageView.startAnimation(rotateAnimation);
                mPopupWindow=null;
            }
        });
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        imageView.startAnimation(rotateAnimation);
        mPopupWindow.showAsDropDown(parent);
        // mPopupWindow.showAtLocation(parent,Gravity.CENTER | Gravity.CENTER,
        // 0,0);
    }

    private int currentIndex;
    private View createSelectorView2() {
        View view = View.inflate(mContext, R.layout.pop_listview, null);
        final ListView mListView = (ListView) view.findViewById(R.id.pop_listview);
        String[] dataStrs={"全部","注册号码","商标名称","申请人"};
        final List<String> data=new ArrayList();
        for(int i=0;i<dataStrs.length;i++){
            data.add(dataStrs[i]);
        }
       MyAdapter adapter=new MyAdapter(data,this);
        mListView.setAdapter(adapter);
        mListView.setItemChecked(currentIndex,true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mPopupWindow!=null){
                    mPopupWindow.dismiss();
                }
                currentIndex=position;
                mSelectorTv2.setText(data.get(position));
                showProgressHUD(ResultActivity.this,"加载中...");
                startSearch();
            }
        });
        return view;
    }

    class MyAdapter extends BaseAdapter {
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
                        R.layout.pop_list_item, parent, false);
            }
            TextView textView=(TextView) convertView.findViewById(R.id.title);
            textView.setText(data.get(position));
            return convertView;
        }
    }
}
