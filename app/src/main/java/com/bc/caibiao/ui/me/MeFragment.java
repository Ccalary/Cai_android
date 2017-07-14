package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.BaseTestModel;
import com.bc.caibiao.model.CornerMarkModel;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.model.MemberAuthApply;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.BaseSubscribe;
import com.bc.caibiao.request.subscribe.ProgressSubscribe;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BaseFragment;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.shangbiao.ResultActivity;
import com.bc.caibiao.utils.BCL;
import com.bc.caibiao.utils.ImageLoad;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;


public class MeFragment extends BaseFragment {

    public static final String TAG = "MeFragment";
    private LinearLayout llReward;
    private LinearLayout mMyTast;
    private LinearLayout mLlImMaster;
    private RelativeLayout mRlMyOrder;//我的订单
    private RelativeLayout mRlBrandRecheck;//复查结果
    private RelativeLayout mRlCollectMark;//商标收藏
    private RelativeLayout mRlMessage;//消息
    private RelativeLayout mRlMyInfo;//个人资料
    private RelativeLayout mRlSetting;//设置
    private RelativeLayout mRlShareing;//设置
    public static MeFragment getInstance() {
        return new MeFragment();
    }

    LinearLayout llPersonalInfo;
    LinearLayout llMyContribute;
    TextView tvMeRight7;
    RelativeLayout rlTouGaoRenRenZheng;
    SimpleDraweeView ivMemberLogo;
    TextView tvMemberNickName;
    private Member member;

    RelativeLayout rlWallet;


    TextView tv_reward_corner_mark_count,tv_myContribute_corner_mark_count,tv_myMasterTask_corner_mark_count,tv_imMaster_corner_mark_count
            ,tvMeRight2,tvMeRight4;

    @Override
    public void OnClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.rlTouGaoRenRenZheng:
                //投稿人认证
                intent.setClass(getActivity(),MemberAuthActivity.class);
                startActivity(intent);
                break;

            case R.id.llPersonalInfo:
                //点击头部的头像区域
                intent.setClass(getActivity(),MyInfoActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_reward:
                setUnReadCountToZero("1");

                intent.setClass(getActivity(),RewardActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_myContribute:
                setUnReadCountToZero("3");
                intent.setClass(getActivity(),MyContributeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_myMasterTask:
                setUnReadCountToZero("2");
                intent.setClass(getActivity(),MyMasterTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_imMaster:
                setUnReadCountToZero("4");
                intent.setClass(getActivity(),ImMasterActivity.class);
                startActivity(intent);
                break;
            case R.id.rlWallet:
                intent.setClass(getActivity(),WalletActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_myOrder:
                intent.setClass(getActivity(),MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_collectTrademark:
                intent.setClass(getActivity(),CollectTradeMarkActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_message:
                setUnReadCountToZero("0");
                intent.setClass(getActivity(),MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_myinfo:
                intent.setClass(getActivity(),MyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting:
                intent.setClass(getActivity(),SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_share:
                intent.setClass(getActivity(),ShareAct.class);
                startActivity(intent);
                break;
            case R.id.brand_recheck:
                setUnReadCountToZero("5");
                intent.setClass(getActivity(),BrandRecheckActivity.class);
                startActivity(intent);
                break;

        }

      /*  if(v==rlTouGaoRenRenZheng){
            //投稿人认证
            MemberAuthApply authApply=member.getMemberAuthApply();
            if(authApply==null || authApply.getAuthType()==MemberAuthApply.CHECKRESULT_DISAPPROVED){
                //审核不通过
                Intent intent=new Intent(getActivity(),AddNewPeopleAuthActivity.class);
                startActivity(intent);
            } else {
                Intent intent=new Intent(getActivity(),PeopleAuthActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("MemberAuthApply",authApply);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } else if(v==llPersonalInfo){
            //头部的头像区域
        }*/
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        llPersonalInfo = (LinearLayout) view.findViewById(R.id.llPersonalInfo);
        llPersonalInfo.setOnClickListener(this);
        tvMeRight7=(TextView) view.findViewById(R.id.tvMeRight7);
        rlTouGaoRenRenZheng=(RelativeLayout) view.findViewById(R.id.rlTouGaoRenRenZheng);
        rlTouGaoRenRenZheng.setOnClickListener(this);
        ivMemberLogo=(SimpleDraweeView) view.findViewById(R.id.ivMemberLogo);
        tvMemberNickName=(TextView) view.findViewById(R.id.tvMemberNickName);

        llReward = (LinearLayout) view.findViewById(R.id.ll_reward);//悬赏图标
        llReward.setOnClickListener(this);

        llMyContribute = (LinearLayout) view.findViewById(R.id.ll_myContribute);//我的投稿
        llMyContribute.setOnClickListener(this);

        mMyTast = (LinearLayout) view.findViewById(R.id.ll_myMasterTask);//我的大师任务
        mMyTast.setOnClickListener(this);

        mLlImMaster = (LinearLayout) view.findViewById(R.id.ll_imMaster);//我是大师
        mLlImMaster.setOnClickListener(this);

        rlWallet = (RelativeLayout) view.findViewById(R.id.rlWallet);
        rlWallet.setOnClickListener(this);

        mRlMyOrder = (RelativeLayout) view.findViewById(R.id.rl_myOrder);
        mRlMyOrder.setOnClickListener(this);

        mRlCollectMark = (RelativeLayout) view.findViewById(R.id.rl_collectTrademark);
        mRlCollectMark.setOnClickListener(this);

        mRlMessage = (RelativeLayout) view.findViewById(R.id.rl_message);
        mRlMessage.setOnClickListener(this);

        mRlMyInfo = (RelativeLayout) view.findViewById(R.id.rl_myinfo);
        mRlMyInfo.setOnClickListener(this);

        mRlSetting = (RelativeLayout) view.findViewById(R.id.rl_setting);
        mRlSetting.setOnClickListener(this);

        mRlBrandRecheck = (RelativeLayout) view.findViewById(R.id.brand_recheck);
        mRlBrandRecheck.setOnClickListener(this);

        mRlShareing = (RelativeLayout) view.findViewById(R.id.rl_share);
        mRlShareing.setOnClickListener(this);

        tv_reward_corner_mark_count=(TextView)view.findViewById(R.id.tv_reward_corner_mark_count);
        tv_myContribute_corner_mark_count=(TextView)view.findViewById(R.id.tv_myContribute_corner_mark_count);
        tv_myMasterTask_corner_mark_count=(TextView)view.findViewById(R.id.tv_myMasterTask_corner_mark_count);
        tv_imMaster_corner_mark_count=(TextView)view.findViewById(R.id.tv_imMaster_corner_mark_count);

        tvMeRight2=(TextView)view.findViewById(R.id.tvMeRight2);
        tvMeRight4=(TextView)view.findViewById(R.id.tvMeRight4);


        return view;
    }

    private void setUnReadCountToZero(String type){
        BCHttpRequest.getCornerMarkInterface().setUnRedMsgNumToZero(SP.getInstance().getString(SPTag.TAG_MEMBER_ID), type)
                .compose(HttpResponseHelper.<String>getAllData())
                .subscribe(new BaseSubscribe<String>(mContext) {
                    @Override
                    protected void _onNext(String s) {
                        BCL.e(s);
                    }
                });
    }

    private void loadData(){
//        BCHttpRequest.getCornerMarkInterface().getUnRedMsgNum(SP.getInstance().getString(SPTag.TAG_MEMBER_ID))

        BCHttpRequest.getCornerMarkInterface().getUnRedMsgNum(SP.getInstance().getString(SPTag.TAG_MEMBER_ID))
                .compose(HttpResponseHelper.<CornerMarkModel>getAllData())
                .subscribe(new ResolveFailSubscribe<CornerMarkModel>(mContext, "处理中") {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        ToastUtils.showShort(mContext,fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(CornerMarkModel cornerMarkModel) {
                        setUnReadCountUI(tv_reward_corner_mark_count,cornerMarkModel.getXuanShangTaskMsgNum(),"");
                        setUnReadCountUI(tv_myContribute_corner_mark_count,cornerMarkModel.getMyTouGaoMsgNum(),"");
                        setUnReadCountUI(tv_myMasterTask_corner_mark_count,cornerMarkModel.getDaShiTaskMsgNum(),"");
                        setUnReadCountUI(tv_imMaster_corner_mark_count,cornerMarkModel.getiAmDaShiMsgNum(),"");

                        setUnReadCountUI(tvMeRight2,cornerMarkModel.getRecheckMsgNum(),"条新结果");
                        setUnReadCountUI(tvMeRight4,cornerMarkModel.getMessageNum(),"条新消息");
                    }
                });
    }

    private void setUnReadCountUI(TextView tv,int count,String str){
        if(count==0){
            tv.setVisibility(View.GONE);
        }
        else{
            tv.setVisibility(View.VISIBLE);
        }
        tv.setText(count+str);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getMember()!=null){
            member= getMember();
        }
        getMemberInfo();
        setupViewState(member);

        loadData();

        super.onResume();
    }

    private void setupViewState(Member member){
        ImageLoad.loadURL(ivMemberLogo,member.getPortrait());
        tvMemberNickName.setText(member.getMemberName());
        MemberAuthApply authApply=member.getMemberAuthApply();
        if(authApply==null || authApply.getAuthType()==MemberAuthApply.CHECKRESULT_DISAPPROVED){
            //审核不通过
            //tvMeRight7.setText("未认证");
        } else {
           // tvMeRight7.setText("已认证");
        }
    }


    private void getMemberInfo(){
        BCHttpRequest.getMemberInterface().viewMember(
                member.getMemberId()
        ).compose(HttpResponseHelper.<Member>getAllData())
                .subscribe(new ProgressSubscribe<Member>(mContext) {
                    @Override
                    protected void _onNext(Member member) {
                        SP.getInstance().saveString(SPTag.TAG_MEMBER,member.toString());
                        setupViewState(member);
                    }
                });
    }


}
