package com.bc.caibiao.request;

import com.bc.caibiao.model.Advertisement;
import com.bc.caibiao.model.BaseResponse;
import com.bc.caibiao.model.BrandRecheck;
import com.bc.caibiao.model.BrandRecheckModel;
import com.bc.caibiao.model.DictionaryItem;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.model.MessageModel;
import com.bc.caibiao.model.ProvinceBean;
import com.bc.caibiao.model.SystemOption;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface OtherInterface {
    @FormUrlEncoded
    @POST("location/listLocationForTree")
    Observable<BaseResponse<List<ProvinceBean>>> listLocationForTree(
            @Field("stringhahahaha") String sss
    );


    @FormUrlEncoded
    @POST("ad/listAds")
    Observable<BaseResponse<List<Advertisement>>> listAds(
            @Field("adPosition") int adPosition
    );


    /**
     * 任务要求标签
     *
     * @return
     */
    @FormUrlEncoded
    @POST("version/listDictiionaryItems")
    Observable<BaseResponse<List<DictionaryItem>>> listDictiionaryItems(
            @Field("groupCode") String groupCode
    );

    /**
     * 获取消息列表
     * */
    @FormUrlEncoded
    @POST("message/listMessages")
    Observable<MessageModel> getMessageListApi(@Field("memberId") int memberId, @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);

    /**
     * 获取个人资料
     * */
    @FormUrlEncoded
    @POST("member/viewMember")
    Observable<BaseResponse<Member>> getMyInfoApi(@Field("memberId") int memberId);

    /**
     * 获取帮助或关于我们
     * */
    @FormUrlEncoded
    @POST("version/getOtherSystemOptionByEnName")
    Observable<BaseResponse<SystemOption>> getAboutUsApi(@Field("enName") String enName);

    /**
     * 复查列表(进行或结束)
     * */
    @FormUrlEncoded
    @POST("task/listBrandRechecks")
    Observable<BaseResponse<BrandRecheckModel>> getListBrandRecheckApi(@Field("memberId") int memberId,
                                                                       @Field("state") short state,
                                                                       @Field("pageNo") int pageNo,
                                                                       @Field("pageSize") int pageSize);

    /**
     * 全部复查列表
     * */
    @FormUrlEncoded
    @POST("task/listBrandRechecks")
    Observable<BaseResponse<BrandRecheckModel>> getAllListBrandRecheckApi(@Field("memberId") int memberId,
                                                                       @Field("pageNo") int pageNo,
                                                                       @Field("pageSize") int pageSize);

    /**
     * 复查详情
     * */
    @FormUrlEncoded
    @POST("task/viewBrandRecheck")
    Observable<BaseResponse<BrandRecheck>> getBrandRecheckDetailApi(@Field("recheckId") int recheckId);

}
