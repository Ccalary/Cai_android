package com.bc.caibiao.ui.qiming;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.holder.SmartAdapter;
import com.bc.caibiao.adapter.holder.SmartKeyAdapter;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.model.HomePageModel.SeacherBrandByKey;
import com.bc.caibiao.model.HomePageModel.SignByHYOrKeyResult;
import com.bc.caibiao.model.HomePageModel.SignByHYOrKeyResultList;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ProgressSubscribe;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.PtrFrameLayout;
import com.bc.caibiao.view.TopBarLayout;
import com.bc.caibiao.view.popupwindow.AskForSignPop;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SmartLsitActivity extends BaseActivity {

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    @Bind(R.id.id_tablayout)
    TopBarLayout mTopLayout;

    PtrFrameLayout mPtrFrameLayout;
    SmartAdapter mAdapter;
    SmartKeyAdapter mSmartKeyAdapter;

    //1:行业 2.关键字
    int searchType = 1;
    int currentPage = 1;

    String companyType = "";
    String title = "";

    private ArrayList<SignByHYOrKeyResult> mShowItems = new ArrayList();

    private ArrayList<String> mShowItemsStr = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_qiming_new);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTopLayout.showLeft();
        mTopLayout.setTitle("智能取名");
        mPtrFrameLayout = new PtrFrameLayout(this);
        mContainerLayout.addView(mPtrFrameLayout.getView());
        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                requestData();
            }
        });
    }

    private void initData(){
        searchType = getIntent().getIntExtra("searchType",1);
        if(searchType == 1){
            companyType = getIntent().getStringExtra("companyType");
        }else{
            title = getIntent().getStringExtra("title");
        }
        requestData();
    }

    private void requestData(){

        if(searchType == 1){
            BCHttpRequest.getQiMingInterface().getSignNameByHangYeApi(companyType,String.valueOf(10),String.valueOf(currentPage))
                    .compose(HttpResponseHelper.<SignByHYOrKeyResultList>getAllData())
                    .subscribe(new ProgressSubscribe<SignByHYOrKeyResultList>(mContext) {
                        @Override
                        protected void _onNext(SignByHYOrKeyResultList signByHYOrKeyResultList) {

                            if(currentPage == 1){
                                mShowItems.clear();
                            }

                            mShowItems.addAll(signByHYOrKeyResultList.data);

                            loadAdapter();

                            if(signByHYOrKeyResultList.data.size() < 10){
                                mAdapter.canLoadMore(false);
                            }else{
                                mAdapter.canLoadMore(true);
                            }

                            mPtrFrameLayout.stopPtrRefresh();
                        }
                    });
        }else{
            BCHttpRequest.getQiMingInterface().getSignNameByKeyApi(title,String.valueOf(10),String.valueOf(currentPage))
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<SeacherBrandByKey>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(SeacherBrandByKey signByHYOrKeyResultList) {

                            if(!"0".equals(signByHYOrKeyResultList.state)  && signByHYOrKeyResultList.fieldErrors.size()>0){
                                ToastUtils.showShort(SmartLsitActivity.this,signByHYOrKeyResultList.fieldErrors.get(0).getMessage());
                                return;
                            }

                            if(currentPage == 1){
                                mShowItemsStr.clear();
                            }

                            mShowItemsStr.addAll(signByHYOrKeyResultList.data.data);

                            loadAdapter();

                            if(signByHYOrKeyResultList.data.data.size() < 10){
                                mSmartKeyAdapter.canLoadMore(false);
                            }else{
                                mSmartKeyAdapter.canLoadMore(true);
                            }

                            mPtrFrameLayout.stopPtrRefresh();
                        }
                    });
        }


    }


    private void loadAdapter(){

        if(searchType == 1){
            if (mAdapter == null) {
                mAdapter = new SmartAdapter(this, mShowItems);
                mPtrFrameLayout.setAdapter(mAdapter);
                mAdapter.setLoadMoreListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {

                    @Override
                    public void OnLoadMore() {
                        currentPage++;
                        requestData();
                    }
                });
                mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                        showPopAtIndex(mShowItems.get(arg2).name);
                    }
                });
            } else {
                mAdapter.setData(mShowItems);
                mAdapter.notifyDataSetChanged();
            }
        }else{

            if (mSmartKeyAdapter == null) {
                mSmartKeyAdapter = new SmartKeyAdapter(this, mShowItemsStr);
                mPtrFrameLayout.setAdapter(mSmartKeyAdapter);
                mSmartKeyAdapter.setLoadMoreListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {

                    @Override
                    public void OnLoadMore() {
                        currentPage++;
                        requestData();
                    }
                });
                mSmartKeyAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                        showPopAtIndex(mShowItemsStr.get(arg2));
                    }
                });
            } else {
                mSmartKeyAdapter.setData(mShowItemsStr);
                mSmartKeyAdapter.notifyDataSetChanged();
            }

        }







    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    private void showPopAtIndex(String name){
        new AskForSignPop(this, mContainerLayout,name, new AskForSignPop.FreeCheckCallback() {
            @Override
            public void doCheck(String brandName, String contactName, String mobile) {
                doBuyCheck(brandName,contactName,mobile);
            }
        }).show();
    }


    /**
     * 发起请求申请
     * */
    private void doBuyCheck(String brandName, String contactName, String mobile){
        BCHttpRequest.getQiMingInterface().getbuyTrademarkApi(brandName,contactName,mobile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BlankModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BlankModel result) {
                        if(result.fieldErrors.isEmpty()){
                            ToastUtils.showShort(SmartLsitActivity.this,"提交成功");
                        }else{
                            ToastUtils.showShort(SmartLsitActivity.this,result.fieldErrors.get(0).getMessage());
                        }
                    }
                });
    }

}
