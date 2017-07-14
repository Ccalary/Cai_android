package com.bc.caibiao.request;

import com.bc.caibiao.model.BaseResponse;
import com.bc.caibiao.model.VersionInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface VersionInterface {
    /**
     * 检测更新
     */
    @FormUrlEncoded
    @POST("version/getVersionInfo")
    Observable<BaseResponse<VersionInfo>> checkUpdate(
            @Field("versionInfoId") int versionInfoId
    );
}
