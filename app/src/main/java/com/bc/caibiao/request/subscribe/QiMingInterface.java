package com.bc.caibiao.request.subscribe;

import com.bc.caibiao.adapter.QiMingModule.IndustryList;
import com.bc.caibiao.model.BaseResponse;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.model.HomePageModel.DashiDetailModel;
import com.bc.caibiao.model.HomePageModel.HomePageModel;
import com.bc.caibiao.model.HomePageModel.MyTaskList;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModel;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModelZFB;
import com.bc.caibiao.model.HomePageModel.SeacherBrandByKey;
import com.bc.caibiao.model.HomePageModel.SignByHYOrKeyResultList;
import com.bc.caibiao.model.HomePageModel.SignPriceList;
import com.bc.caibiao.model.HomePageModel.TaskDetailModel;
import com.bc.caibiao.model.HomePageModel.TaskParentModel;
import com.bc.caibiao.model.MarkModel.BigTeacherList;
import com.bc.caibiao.model.MarkModel.TagList;
import com.bc.caibiao.model.MarkModel.TaskCategoryList;
import com.bc.caibiao.model.MarkModel.TaskDateList;
import com.bc.caibiao.model.MarkModel.TaskPriceList;
import com.bc.caibiao.model.TradeMarkResponse;
import com.bc.caibiao.model.VersionInfo;
import com.bc.caibiao.model.VersionModel;
import com.bc.caibiao.model.VersionModelInfo;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by chengyanfang on 2017/4/17.
 */

public interface QiMingInterface {

    /**
     * 起名首页
     * */
    @FormUrlEncoded
    @POST("task/listHomeAll")
    Observable<BaseResponse<HomePageModel>> getQimingHomeDataApi(@Field("numDashi") int numDashi,@Field("numTask") int numTask);

    /**
     * 大师列表
     * */
    @FormUrlEncoded
    @POST("member/listDaShiMembers")
    Observable<BaseResponse<BigTeacherList>> getDaShiListApi(@Field("pageSize") String pageSize, @Field("pageNo") String pageNo);

    /**
     * 获取行业列表
     * */
    @FormUrlEncoded
    @POST("version/listDictiionaryItems")
    Observable<IndustryList> getIndustryDataApi(@Field("groupCode") String groupCode);

    /**
     * 获取行业列表
     * */
    @FormUrlEncoded
    @POST("tradeMark/getCompanyTypes")
    Observable<TradeMarkResponse> getIndustryDataNewApi(@Field("groupCode") String groupCode);

     /**
     * 获取智能起名结果《行业》
     * */
    @FormUrlEncoded
    @POST("trademark/qiMingByHangYe")
    Observable<BaseResponse<SignByHYOrKeyResultList>> getSignNameByHangYeApi(@Field("company_type_id") String company_type_id, @Field("pagenumber") String pagenumber, @Field("page") String page);


    /**
     * 获取智能起名结果《关键字》
     * */
    @FormUrlEncoded
    @POST("trademark/qiMingByKeyword")
    Observable<SeacherBrandByKey> getSignNameByKeyApi(@Field("title") String title, @Field("pagenumber") String pagenumber, @Field("page") String page);


    /**
     * 免费查询商标
     * */
    @FormUrlEncoded
    @POST("product/buyTrademark")
    Observable<BlankModel> getbuyTrademarkApi(@Field("buy_remarks") String buy_remarks, @Field("buy_name") String buy_name, @Field("buy_phone") String buy_phone);

    /**
     * 任务广场
     * */
    @FormUrlEncoded
    @POST("task/listTasks")
    Observable<TaskParentModel> getTaskPlayListApiWithCategory(@Field("orderBy") String orderBy, @Field("pageSize") String pageSize, @Field("pageNo") String pageNo,@Field("namedCategory") String namedCategory);


    /**
     * 任务广场
     * */
    @FormUrlEncoded
    @POST("task/listTasks")
    Observable<TaskParentModel> getTaskPlayListApi(@Field("orderBy") String orderBy, @Field("pageSize") String pageSize, @Field("pageNo") String pageNo);


    /**
     * 任务广场
     * */
    @FormUrlEncoded
    @POST("version/getVersionInfo")
    Observable<VersionModelInfo> getVersionInfoApi(@Field("deviceType") String deviceType, @Field("build") String build);


    /**
     * 获取任务标签
     * */
    @FormUrlEncoded
    @POST("version/listDictiionaryItems")
    Observable<TagList> getTagListApi(@Field("groupCode") String groupCode);


    /**
     * 获取任务日期
     * */
    @FormUrlEncoded
    @POST("version/listDictiionaryItems")
    Observable<TaskDateList> getTaskDateListApi(@Field("groupCode") String groupCode);

    /**
     * 获取任务金额
     * */
    @FormUrlEncoded
    @POST("version/listDictiionaryItems")
    Observable<TaskPriceList> getTaskPriceListApi(@Field("groupCode") String groupCode);


    /**
     * 获取行业分类
     * */
    @FormUrlEncoded
    @POST("tradeMark/listCategoriesTree")
    Observable<TaskCategoryList> getTaskCategoryListApi(@Field("memberId") String memberId);


    /**
     * 获取行业分类
     * */
    @FormUrlEncoded
    @POST("tradeMark/listCategoriesTree")
    Observable<TaskCategoryList> getTaskCategoryListApi(@Field("filter_level") String filterLevel,@Field("filter_parent_id") String filterParentId,@Field("pageSize") String pageSize,@Field("pageNo") String pageNo);


    /**
     * 获取行业分类
     * */
    @FormUrlEncoded
    @POST("tradeMark/listCategoriesTree")
    Observable<TaskCategoryList> getTaskCategoryListApiByName(@Field("filter_name") String filtername,@Field("pageSize") String pageSize,@Field("pageNo") String pageNo);

    /**
     * 获取起名套餐
     * */
    @FormUrlEncoded
    @POST("version/listDashiPackages")
    Observable<SignPriceList> getSignPriceListApi(@Field("memberId") String memberId);

    /**
     * 获取大师详情
     * */
    @FormUrlEncoded
    @POST("member/viewMember")
    Observable<DashiDetailModel> getTeacherDetail(@Field("memberId") String memberId);

    /**
     * 获取任务详情
     * */
    @FormUrlEncoded
    @POST("task/viewTask")
    Observable<TaskDetailModel> getTaskDetail(@Field("taskId") String taskId);


    /**
     * 发布任务<微信>
     * */
    @FormUrlEncoded
    @POST("task/addTask")
    Observable<PublishTaskResultModel> publishTaskByWXApi(
            @Field("price") String price,
            @Field("payMode") String payMode,
            @Field("taskTitle") String taskTitle,
            @Field("namedCategory") String namedCategory,
            @Field("categoryId") String categoryId,
            @Field("requireDetail") String requireDetail,
            @Field("itemContents") String itemContents,
            @Field("memberId") String memberId,
            @Field("expireDays") String expireDays,
            @Field("taskMode") String taskMode,
            @Field("categoryName") String categoryName,
            @Field("clientIp") String clientIp
    );


    /**
     * 发布任务<微信>
     * */
    @FormUrlEncoded
    @POST("task/addTask")
    Observable<PublishTaskResultModelZFB> publishTaskByZFBApi(
            @Field("price") String price,
            @Field("payMode") String payMode,
            @Field("taskTitle") String taskTitle,
            @Field("namedCategory") String namedCategory,
            @Field("categoryId") String categoryId,
            @Field("requireDetail") String requireDetail,
            @Field("itemContents") String itemContents,
            @Field("memberId") String memberId,
            @Field("expireDays") String expireDays,
            @Field("taskMode") String taskMode,
            @Field("categoryName") String categoryName,
            @Field("clientIp") String clientIp
    );


    /**
     * 请大师起名<微信>
     * */
    @FormUrlEncoded
    @POST("task/addTask")
    Observable<PublishTaskResultModel> pleaseTeacherSign(
            @Field("categoryName") String categoryName,
            @Field("clientIp") String clientIp,
            @Field("namedCategory") String namedCategory,
            @Field("payMode") String payMode,
            @Field("taskTitle") String taskTitle,
            @Field("taskMode") String taskMode,
            @Field("price") String price,
            @Field("memberId") String memberId,
            @Field("birthday") String birthday,
            @Field("birthhour") String birthhour,
            @Field("bossName") String bossName,
            @Field("wordsNum") String wordsNum,
            @Field("requireDetail") String requireDetail,
            @Field("dashiMemberId") String dashiMemberId
    );


    /**
     * 请大师起名<支付宝>
     * */
    @FormUrlEncoded
    @POST("task/addTask")
    Observable<PublishTaskResultModelZFB> pleaseTeacherSignZFB(
            @Field("categoryName") String categoryName,
            @Field("clientIp") String clientIp,
            @Field("namedCategory") String namedCategory,
            @Field("payMode") String payMode,
            @Field("taskTitle") String taskTitle,
            @Field("taskMode") String taskMode,
            @Field("price") String price,
            @Field("memberId") String memberId,
            @Field("birthday") String birthday,
            @Field("birthhour") String birthhour,
            @Field("bossName") String bossName,
            @Field("wordsNum") String wordsNum,
            @Field("requireDetail") String requireDetail,
            @Field("dashiMemberId") String dashiMemberId
    );

    /**
     * 获取任务标签
     * */
    @FormUrlEncoded
    @POST("version/listDictiionaryItems")
    Observable<TagList> getCheckPriceListApi(@Field("groupCode") String groupCode);


    /**
     * 查询商标《微信》
     * */
    @FormUrlEncoded
    @POST("task/addBrandRecheck")
    Observable<PublishTaskResultModel> checkBrandWX(
            @Field("memberId") String memberId,
            @Field("memberName") String memberName,
            @Field("payMode") String payMode,
            @Field("price") String price,
            @Field("brandName") String brandName,
            @Field("clientIp") String clientIp
    );

    /**
     * 查询商标《支付宝》
     * */
    @FormUrlEncoded
    @POST("task/addBrandRecheck")
    Observable<PublishTaskResultModelZFB> checkBrandWZFB(
            @Field("memberId") String memberId,
            @Field("memberName") String memberName,
            @Field("payMode") String payMode,
            @Field("price") String price,
            @Field("brandName") String brandName
    );


    /**
     * 查询商标《微信》
     * */
    @FormUrlEncoded
    @POST("order/addOrder")
    Observable<PublishTaskResultModel> buyProductWX(
            @Field("memberId") String memberId,
            @Field("product_id") String product_id,
            @Field("product_quantity") String product_quantity,
            @Field("ip") String ip,
            @Field("payment_code") String payment_code
    );

    /**
     * 查询商标《支付宝》
     * */
    @FormUrlEncoded
    @POST("order/addOrder")
    Observable<PublishTaskResultModelZFB> buyProductZFB(
            @Field("memberId") String memberId,
            @Field("product_id") String product_id,
            @Field("product_quantity") String product_quantity,
            @Field("ip") String ip,
            @Field("payment_code") String payment_code,
            @Field("payment_address") String address
    );


    /**
     * 获取悬赏任务
     * */
    @FormUrlEncoded
    @POST("task/listMyTasks")
    Observable<MyTaskList> getMyTaskApi(@Field("memberId") String memberId,
                                        @Field("searchState") String searchState,
                                        @Field("pageNo") String pageNo,
                                        @Field("pageSize") String pageSize,
                                        @Field("taskMode") String taskMode);

    /**
     * 获取我的投稿
     * */
    @FormUrlEncoded
    @POST("task/listMyTouGaoXuanShangTasks")
    Observable<MyTaskList> getMyContributeApi(@Field("memberId") String memberId,
                                        @Field("searchState") String searchState,
                                        @Field("pageNo") String pageNo,
                                        @Field("pageSize") String pageSize);


    /**
     * 获取我是大师
     * */
    @FormUrlEncoded
    @POST("task/listMyReceivedDashiTasks ")
    Observable<MyTaskList> getImMasterList(@Field("memberId") String memberId,
                                        @Field("pageNo") String pageNo,
                                        @Field("pageSize") String pageSize);


    /**
     * 获取任务详情
     * */
    @FormUrlEncoded
    @POST("task/viewTask")
    Observable<TaskDetailModel> getDashiTaskDetail(@Field("taskId") String taskId,@Field("memberId") String memberId);



    /**
     * 进行投稿
     * */
    @Multipart
    @POST("task/touGao")
    Observable<BlankModel> touGaoApiNew(
            @PartMap Map<String, RequestBody> params);

    /**
     * 编辑投稿
     * */
    @Multipart
    @POST("task/modifyTouGao")
    Observable<BlankModel> editTouGaoApiNew(
            @PartMap Map<String, RequestBody> params);

    /**
     * 删除投稿 touGaoId
     * */
    @FormUrlEncoded
    @POST("task/removeTouGao")
    Observable<BlankModel> deleteTouGaoApi(@Field("touGaoId") String touGaoId);


    /**
     * 提问或者回复
     * */
    @FormUrlEncoded
    @POST("task/askAnswer")
    Observable<BlankModel> askOrAnswerTouGaoApi(@Field("memberId") String memberId,@Field("type") String type,@Field("content") String content,@Field("touGaoId") String touGaoId);


    /**
     * 采纳
     * */
    @FormUrlEncoded
    @POST("task/useTouGao")
    Observable<BlankModel> acceptTouGao(@Field("touGaoId") String touGaoId);
}
