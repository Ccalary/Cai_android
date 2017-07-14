package com.bc.caibiao.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.URLConfig;
import com.facebook.drawee.view.SimpleDraweeView;

import java.net.URI;

/**
 * @author wangkai
 * @Description: 图片加载工具类
 * create at 16/4/25 下午3:21
 */
public class ImageLoad {

    /**
     * fresco 加载图片
     */
    public static void loadURL(final SimpleDraweeView iv, String picPath) {
        if (TextUtils.isEmpty(picPath)) {
            return;
        }
        String url = picPath.contains("http://") || picPath.contains("https://") ? picPath : URLConfig.baseUrl_pic_oss + picPath;
        if (picPath.contains("res:") || picPath.contains("file:")) {
            url = picPath;
        }

        Uri uri = Uri.parse(url);
        iv.setImageURI(uri);
    }

    /**
     * fresco 加载本地图片
     */
    public static void loadRes(SimpleDraweeView iv, int resId) {
        String url = "res://" + BaseApplication.getInstance().getPackageName() + "/" + resId;
        Uri uri = Uri.parse(url);
        iv.setImageURI(uri);
    }

    /**
     * fresco 加载本地图片
     */
    public static void loadFile(SimpleDraweeView iv, String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        String url = "file://" + BaseApplication.getInstance().getPackageName() + "/" + path;
        Uri uri = Uri.parse(url);
        iv.setImageURI(uri);
    }


    public static void loadLocalFile(SimpleDraweeView iv, String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return;
        }
        String url = "file://" +  absPath;
        Uri uri = Uri.parse(url);
        iv.setImageURI(uri);
    }

}
