package com.bc.caibiao.ui.me;

import android.content.Context;

import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.CityBean;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.model.MemberAuthApply;
import com.bc.caibiao.model.ProvinceBean;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ProgressSubscribe;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.AlertDialogUtils;
import com.bc.caibiao.utils.PhotoUtil;
import com.bc.caibiao.utils.SP;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;

public class AddNewPropleAuthPresenter
        extends BasePresenter<AddNewPeopleAuthContract.ActivityView>
        implements AddNewPeopleAuthContract.ActivityImpl {

    private AddNewPeopleAuthActivity activity;
    private File imageFile;
    private MemberAuthApply memberAuthApply;

    private List<ProvinceBean> provinceList;
    private List<CityBean> cityBeanList;
    private int cityId=-1;
    private String cityName="";
    private int provinceId=-1;
    private String provinceName="";

    public List<CityBean> getCityBeanList() {
        return cityBeanList;
    }

    public void setCityBeanList(List<CityBean> cityBeanList) {
        this.cityBeanList = cityBeanList;
    }

    public List<ProvinceBean> getProvinceList() {
        return provinceList;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setProvinceList(List<ProvinceBean> provinceList) {
        this.provinceList = provinceList;
    }

    public AddNewPropleAuthPresenter(Context context, AddNewPeopleAuthActivity activity) {
        super(context);
        this.activity = activity;
    }

    @Override
    public void submitAndNewRoom(String absPicUrl,String trueName,String goodAt) {
        Member member=null;
        try {
            member=SP.getInstance().getMemberSP();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File tmpFile = new File(absPicUrl);
        okhttp3.RequestBody photoRequestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("image/png"), tmpFile);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("original1", tmpFile.getName(), photoRequestBody);
        BCHttpRequest.getMemberInterface().addMemberAuthApply(
                okhttp3.RequestBody.create(null, member.getMemberId() + ""),
                okhttp3.RequestBody.create(null, member.getMemberName()),
                photo,
                okhttp3.RequestBody.create(null, "1"),
                okhttp3.RequestBody.create(null, trueName),
                okhttp3.RequestBody.create(null, provinceId+""),
                okhttp3.RequestBody.create(null, provinceName),
                okhttp3.RequestBody.create(null, cityId+""),
                okhttp3.RequestBody.create(null, cityName),
                okhttp3.RequestBody.create(null, goodAt)
        ).compose(HttpResponseHelper.<String>getAllData())
                .subscribe(new ResolveFailSubscribe<String>(mContext) {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        mView.showToast(fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(String s) {
                        mView.showToast("提交成功");
                        mView.delayFinish(0);
                    }
                });
    }

    @Override
    public void selectPic() {
        showChooseDialog();
    }

    @Override
    public void getProvinceInfo() {
        BCHttpRequest.getOtherInterface().listLocationForTree("udhfi")
                    .compose(HttpResponseHelper.<List<ProvinceBean>>getAllData())
                    .subscribe(new ResolveFailSubscribe<List<ProvinceBean>>(mContext) {
                        @Override
                        protected void _onFail(FieldError fieldError) {
                        }

                        @Override
                        protected void _onNext(List<ProvinceBean> province) {
                            provinceList=province;
//                            DatabaseHelper helper = new DatabaseHelper(mContext);
//                            helper.writeToTable(province);
                        }
                    });
    }

    @Override
    public void resetCitySelection() {
        cityId=-1;
        cityName="";
    }

    public void getViewMemberAuth(){
        Member member=null;
        try {
            member=SP.getInstance().getMemberSP();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BCHttpRequest.getMemberInterface().viewMemberAuthApply(
                member.getMemberId()
        ).compose(HttpResponseHelper.<MemberAuthApply>getAllData())
                .subscribe(new ResolveFailSubscribe<MemberAuthApply>(mContext, "加载中") {

                    @Override
                    protected void _onFail(FieldError fieldError) {
                        mView.showToast(fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(MemberAuthApply memberAuthApply) {
                        setMemberAuthApply(memberAuthApply);
                        mView.goToActivity();
                    }
                });
    }

    private void showChooseDialog() {
        new AlertDialogUtils(mContext, new AlertDialogUtils.onClickResult() {
            @Override
            public void onResult(Object result) {
                int tag = (int) result;
                if (tag == AlertDialogUtils.DIALOG_PHOTO) {
                    if (PhotoUtil.sdCardState()) {
                        PhotoUtil.getPhoto(imageFile, activity);
                    }
                } else if (tag == AlertDialogUtils.DIALOG_ALBUM) {
                    if (PhotoUtil.sdCardState()) {
                        PhotoUtil.getPicture(activity);
                    }
                }
            }
        }).ShowPhoto();
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public MemberAuthApply getMemberAuthApply() {
        return memberAuthApply;
    }

    public void setMemberAuthApply(MemberAuthApply memberAuthApply) {
        this.memberAuthApply = memberAuthApply;
    }


}
