package com.bc.caibiao.request;

import com.bc.caibiao.model.BaseResponse;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.model.MemberAuthApply;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * @author wangkai
 * @Description : 请求接口
 * create at 16/8/15 下午2:05
 */
public interface MemberInterface {

    @FormUrlEncoded
    @POST("member/login")
    Observable<BaseResponse<Member>> login(
            @Field("username") String mobilePhone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("member/loginByWeixinOpenId")
    Observable<BaseResponse<Member>> loginByWeixinOpenId(
            @Field("open_id") String open_id,
            @Field("social_type") String social_type,
            @Field("firstname") String firstname,
            @Field("image") String image
    );

    @FormUrlEncoded
    @POST("task/listDaShiMembers")
    Observable<BaseResponse<Member>> master(
            @Field("pageOn") int pageOn,
            @Field("pageSize") int pageSize
    );


    @FormUrlEncoded
    @POST("member/resetPassword")
    Observable<BaseResponse<Void>> resetPassword(
            @Field("telephone") String telephone,
            @Field("telephone_veri") String telephone_veri
    );


    @FormUrlEncoded
    @POST("member/bindRegistInfo")
    Observable<BaseResponse<Void>> bindRegistInfo(
            @Field("social_open_id") String social_open_id,
            @Field("social_type") String social_type,
            @Field("firstname") String firstname,
            @Field("telephone") String telephone,
            @Field("telephone_veri") String telephone_veri,
            @Field("image") String image
    );


    @FormUrlEncoded
    @POST("member/getPhoneVerify")
    Observable<BaseResponse<Void>> getPhoneVerify(
            @Field("type") String type,
            @Field("telephone") String telephone
    );


    @FormUrlEncoded
    @POST("member/register")
    Observable<BaseResponse<Member>> register(
            @Field("firstname") String firstname,
            @Field("telephone") String telephone,
            @Field("password") String password,
            @Field("telephone_veri") String telephone_veri

    );


//    @FormUrlEncoded
//    @POST("member/resetPassword")
//    Observable<BaseResponse> resetPassword(
//            @Field("telephone") String telephone,
//            @Field("telephone_veri") String telephone_veri
//    );


    @FormUrlEncoded
    @POST("member/viewMemberAuthApply")
    Observable<BaseResponse<MemberAuthApply>> viewMemberAuthApply(
            @Field("memberId") int memberId
    );

    @FormUrlEncoded
    @POST("member/viewMember")
    Observable<BaseResponse<Member>> viewMember(
            @Field("memberId") int memberId
    );

    @Multipart
    @POST("member/addMemberAuthApply")
    Observable<BaseResponse<String>> addMemberAuthApply(
            @Part("memberId") okhttp3.RequestBody memberId,
            @Part("memberName") okhttp3.RequestBody liveTitle,
            @Part MultipartBody.Part original1,
            @Part("authType") okhttp3.RequestBody authType,
            @Part("trueName") okhttp3.RequestBody trueName,
            @Part("provinceId") okhttp3.RequestBody provinceId,
            @Part("provinceName") okhttp3.RequestBody provinceName,
            @Part("cityId") okhttp3.RequestBody cityId,
            @Part("cityName") okhttp3.RequestBody cityName,
            @Part("attr1") okhttp3.RequestBody attr1
    );

    /**
     * 修改头像
     */
    @Multipart
    @POST("member/modifyMember")
    Observable<BlankModel> modifyUserBaseInfo(
            @PartMap Map<String, RequestBody> params);


}






