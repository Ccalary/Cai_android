package com.bc.caibiao.utils;

import com.bc.caibiao.model.Member;

/**
 * Created by chengyanfang on 2017/4/16.
 *
 * 商标模块统一用到的接口类，是对上位开发着没有集成的工具方法做的一个集成
 */

public class MarkModuleUtil {
    /**
     * 判断是否已经登录
     * */
    public static boolean isLogin(){
        Member member;
        try {
            member =  SP.getInstance().getMemberSP();
        } catch (Exception e) {
            member =  null;
        }

        return member != null;
    }



}
