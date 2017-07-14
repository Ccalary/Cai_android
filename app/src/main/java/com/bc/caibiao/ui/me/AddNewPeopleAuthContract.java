package com.bc.caibiao.ui.me;

import com.bc.caibiao.ui.BaseView;


public interface AddNewPeopleAuthContract {

    interface ActivityView extends BaseView{
        /**
         * 获取要上传的图片地址
         * @return
         */
        String getSelectedLivePicAbsUrl();

        void resetCitySelection();

        /**
         * 跳转新页面
         */
        void goToActivity();

    }

    interface ActivityImpl {
        /**
         * 提交数据并且创建新的房间
         */
        void submitAndNewRoom(String absPicUrl,String trueName,String goodAt);

        void selectPic();

        void getProvinceInfo();

        void resetCitySelection();
    }
}
