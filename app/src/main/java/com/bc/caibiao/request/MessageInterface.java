package com.bc.caibiao.request;

import com.bc.caibiao.model.BaseResponse;
import com.bc.caibiao.model.MemberAuthApply;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface MessageInterface {

    @FormUrlEncoded
    @POST("message/setMessageDeviceInfo")
    Observable<BaseResponse<String>> setMessageDeviceInfo(
            @Field("receiverId") String receiverId,
            @Field("deviceId") String deviceId,
            @Field("deviceType") Integer deviceType
    );
}
