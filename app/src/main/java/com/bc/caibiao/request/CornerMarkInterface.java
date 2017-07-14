package com.bc.caibiao.request;

import com.bc.caibiao.model.BaseResponse;
import com.bc.caibiao.model.CornerMarkModel;
import com.bc.caibiao.model.DataPage;
import com.bc.caibiao.model.Order;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/5/4.
 */

public interface CornerMarkInterface {
    @FormUrlEncoded
    @POST("message/getUnRedMsgNum")
    abstract Observable<BaseResponse<CornerMarkModel>> getUnRedMsgNum(@Field("memberId") String memberId);

    @FormUrlEncoded
    @POST("message/modifyMsgToZero")
    abstract Observable<BaseResponse<String>> setUnRedMsgNumToZero(@Field("memberId") String memberId,@Field("type") String type);
}
