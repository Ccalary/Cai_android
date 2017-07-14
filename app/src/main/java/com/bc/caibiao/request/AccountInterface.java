package com.bc.caibiao.request;


import com.bc.caibiao.model.AccountFlowModel;
import com.bc.caibiao.model.BaseResponse;
import com.bc.caibiao.model.CashAppliesModel;
import com.bc.caibiao.model.CashApplyModel;
import com.bc.caibiao.model.MemberAccount;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by panghuan on 2017/4/19.
 *
 * 账户模块接口
 */

public interface AccountInterface {

    /**
     * 查看账户信息
     * */
    @FormUrlEncoded
    @POST("account/viewMemberAccount")
    Observable<BaseResponse<MemberAccount>> searchAccountInfoApi(@Field("memberId") int memberId);


    /**
     * 账户流水
     * */
    @FormUrlEncoded
    @POST("account/listMemberAccountFlows")
    Observable<BaseResponse<AccountFlowModel>> searchAccountFlowsApi(@Field("memberId") int memberId, @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);


    /**
     * 提现
     * */
    @FormUrlEncoded
    @POST("account/addMemberWithdrawCashApply")
    Observable<CashApplyModel> addMemberWithdrawCashApplyApi(
            @Field("memberId") int memberId,
            @Field("applyAmount") int applyAmount,
            @Field("alipayAccount") String alipayAccount,
            @Field("ownerName") String ownerName);


    /**
     * 提现记录
     * */
    @FormUrlEncoded
    @POST("account/listWithdrawCashApplies")
    Observable<BaseResponse<CashAppliesModel>> listWithdrawCashAppliesApi(@Field("memberId") int memberId, @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);
}
