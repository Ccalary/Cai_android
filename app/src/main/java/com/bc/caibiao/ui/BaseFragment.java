package com.bc.caibiao.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.ui.qiming.SimpleOutLinkAct;
import com.bc.caibiao.utils.NoDoubleClickUtils;
import com.bc.caibiao.utils.ProgressDialogUtils;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;

/**
 * @author wangkai
 * @create 16/9/1 下午4:51
 * @Description :
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment implements View.OnClickListener, BaseView {
    public T mPresenter;
    public Context mContext;
    public ProgressDialogUtils mDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mPresenter = initPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.bindView((V) this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destroy();
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (NoDoubleClickUtils.isFastDoubleClick()) {
            return;
        }
        hideInput();
        OnClick(v);
    }

    /**
     * 隐藏键盘
     */
    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void OnClick(View v){};

    public abstract T initPresenter();

    /**
     * 统一实现progress
     *
     * @param tipMsg 提示文字
     */
    @Override
    public void showLoading(String tipMsg) {
        if (mDialog == null) {
            mDialog = new ProgressDialogUtils(mContext, tipMsg);
        }
        mDialog.show();
    }

    @Override
    public void dismissLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void showToast(String tipMsg) {
        ToastUtils.showShort(mContext, tipMsg);
    }

    @Override
    public void delayFinish(int time) {

    }

    public Member getMember() {
        try {
            return SP.getInstance().getMemberSP();
        } catch (Exception e) {
            return new Member();
        }
    }


    /***
     * Add By Chengyanfang
     * */

    protected View mView;

    protected boolean isCreateView = false;


    /**
     * view创建完毕后开始配置View控件
     * */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    protected void initData(){}

    protected void initView(){};


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateView) {
            isCreateView = false;
            lazyLoad();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            lazyLoad();
        }
    }

    protected void lazyLoad(){}

    protected void forwardTokefuAct(){
        Intent aIntent = new Intent(getActivity(), SimpleOutLinkAct.class);
        if(TextUtils.isEmpty(SP.getInstance().getString(SPTag.TAG_KEFU_URL))){
            aIntent.putExtra("openUrl", URLConfig.KEFU);
        }else{
            aIntent.putExtra("openUrl", SP.getInstance().getString(SPTag.TAG_KEFU_URL));
        }
        aIntent.putExtra("title","客服");
        startActivity(aIntent);
    }

}
