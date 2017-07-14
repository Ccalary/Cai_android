package com.bc.caibiao.request;


import com.bc.caibiao.model.BaseResponse;
import com.bc.caibiao.model.BaseTestModel;
import com.bc.caibiao.model.CollectMarkModel;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.model.MarkModel.AdvertiseList;
import com.bc.caibiao.model.MarkModel.MarkDetail;
import com.bc.caibiao.model.MarkModel.MarkDetailModel;
import com.bc.caibiao.model.MarkModel.MarkModelData;
import com.bc.caibiao.model.MarkModel.MarkSearchResultList;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by chengyanfang on 2017/4/16.
 * <p>
 * 商标模块接口
 */

public interface MarkInterface {

    /**
     * 商标首页
     */
    @FormUrlEncoded
    @POST("product/listFeaturedProducts")
    Observable<BaseResponse<MarkModelData>> markHomeProductListApi(@Field("test") int test);

    @FormUrlEncoded
    @POST("ad/listAds")
    Observable<AdvertiseList> markHomeProductListNewApi(@Field("adPosition") String adPosition);


    /**
     * 按文字检索商标
     */
    @FormUrlEncoded
    @POST("trademark/listTrademarksByWord")
    Observable<BaseResponse<MarkSearchResultList>> searchMarkListByKeyApi(@Field("cxcls") String cxcls,@Field("cxtype") int cxtype, @Field("key") String key, @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);


    /**
     * 按图片检索商标
     */
    @POST("trademark/listTrademarksByPic")
    Observable<BaseResponse<MarkSearchResultList>> searchMarkListByImgApi(@Body RequestBody imgs);


    /**
     * 获取商标详情页面
     */
    @FormUrlEncoded
    @POST("trademark/viewTrademark")
    Observable<BaseResponse<MarkDetail>> getMarkDetailApi(@Field("memberId") String memberId, @Field("cxkey") String cxkey, @Field("intcls") String intcls);

    /**
     * 出售商标
     */
    @FormUrlEncoded
    @POST("product/transferTrademark")
    Observable<BaseResponse<BaseTestModel>> getSellMarkApi(@Field("transfer_product_name") String transfer_product_name
            , @Field("transfer_product_price") String transfer_product_price
            , @Field("transfer_name") String transfer_name
            , @Field("transfer_phone") String transfer_phone);


    /**
     * 收藏商标
     */
    @FormUrlEncoded
    @POST("trademark/collectTrademark")
    Observable<BaseResponse<BaseTestModel>> getFollowMarkApi(@Field("memberId") String memberId, @Field("cxkey") String cxkey, @Field("intcls") String intcls);

    /**
     * 取消收藏商标
     */
    @FormUrlEncoded
    @POST("trademark/unCollectTrademark")
    Observable<BaseResponse<BaseTestModel>> dismissFollowMarkApi(@Field("memberId") String memberId, @Field("cxkey") String cxkey, @Field("intcls") String intcls);

    /**
     * 获取出售商标列表
     */
    @FormUrlEncoded
    @POST("product/listProductServices")
    Observable<BaseResponse<MarkModelData>> markCategoryedProductListApi(@Field("pageNo") int pageNo, @Field("pageSize") int pageSize, @Field("category_buy") int category_buy);


    /**
     * 获取收藏列表
     */
    @FormUrlEncoded
    @POST("trademark/listMyCollectTrademark")
    Observable<CollectMarkModel> getCollectTradeMarkListApi(@Field("memberId") int memberId);

    /**
     * 获取收藏列表
     */
    @FormUrlEncoded
    @POST("product/viewProductDetail")
    Observable<MarkDetailModel> getMarkDetail(@Field("product_id") String product_id);


    /**
     * 申请商标
     */
    @Multipart
    @POST("trademark/trademarkApply")
    Observable<BlankModel> trademarkApplyApi(
            @PartMap Map<String, RequestBody> params);

}
