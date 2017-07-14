package com.bc.caibiao.request;


import com.bc.caibiao.model.AccountFlowModel;
import com.bc.caibiao.model.BaseResponse;
import com.bc.caibiao.model.CashAppliesModel;
import com.bc.caibiao.model.CashApplyModel;
import com.bc.caibiao.model.DataPage;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModel;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModelZFB;
import com.bc.caibiao.model.Order;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by panghuan on 2017/4/19.
 *
 * 账户模块接口
 */

public interface OrderInterface {

    /**
     * 获取订单列表
     * */
    @FormUrlEncoded
    @POST("order/listOrders")
    Observable<BaseResponse<DataPage<Order>>> getOrderListApi(@Field("memberId") int memberId, @Field("pageNo") String pageNo);

    /**
     * 订单详情
     * */
    @FormUrlEncoded
    @POST("order/viewOrder")
    Observable<BaseResponse<Order>> getOrderDetailApi(@Field("order_id") String order_id, @Field("memberId") int memberId);


    /**
     * 账户流水
     * */
    @FormUrlEncoded
    @POST("account/listMemberAccountFlows")
    Observable<BaseResponse<AccountFlowModel>> searchAccountFlowsApi(@Field("memberId") int memberId, @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);


    /**
     * 提现
     * */
    @POST("account/addMemberWithdrawCashApply")
    Observable<CashApplyModel> addMemberWithdrawCashApplyApi(@Field("memberId") int memberId, @Field("applyAmount") int applyAmount, @Field("alipayAccount") String alipayAccount, @Field("ownerName") String ownerName);


    /**
     * 提现记录
     * */
    @FormUrlEncoded
    @POST("account/listWithdrawCashApplies")
    Observable<BaseResponse<CashAppliesModel>> listWithdrawCashAppliesApi(@Field("memberId") int memberId, @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);

    /**
     * 付款(支付宝)
     * */
    @FormUrlEncoded
    @POST("order/toPay")
    Observable<PublishTaskResultModelZFB> payOrderProductZFB(
            @Field("order_id") String order_id,
            @Field("memberId") int memberId,
            @Field("payment_code") String payment_code,
            @Field("ip") String ip
    );

    /**
     * 付款(微信)
     * */
    @FormUrlEncoded
    @POST("order/toPay")
    Observable<PublishTaskResultModel> payOrderlProductWX(
            @Field("order_id") String order_id,
            @Field("memberId") int memberId,
            @Field("payment_code") String payment_code,
            @Field("ip") String ip
    );
}
