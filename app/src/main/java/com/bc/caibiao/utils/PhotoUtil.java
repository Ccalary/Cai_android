package com.bc.caibiao.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.view.CropImageActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * @author wangkai
 * @Description: 拍照相关
 * create at 2015/11/16 16:38
 */
public class PhotoUtil {

    /**
     * 把图片写出去
     *
     * @param file
     * @param stream
     * @return
     */
    public static File getImageFile(File file, ByteArrayOutputStream stream) {
        byte[] bitmapData = stream.toByteArray();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e1) {
            BCL.e(e1);
        }
        return file;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }


    /**
     * 用当前时间给取得的图片命名
     */
    public static String setFileName() {
        Date date = new Date(System.currentTimeMillis());
        return TimeUtil.getStringFromTime(date, TimeUtil.FORMAT_PICTURE) + ".png";
    }

    /**
     * 检查SD卡状态
     *
     * @return
     */
    public static boolean sdCardState() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取相册照片
     */
    public static void getPicture(Activity activity) {
        if (activity == null) {
            return;
        }
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        activity.startActivityForResult(intent, Constant.ALBUM);
    }

    /**
     * 获取相机拍照
     */
    public static void getPhoto(File imageFile, Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            BaseApplication.ImagePath = setFileName();
            imageFile = new File(BaseApplication.SD_SAVEDIR, BaseApplication.ImagePath);
            Uri uri = Uri.fromFile(imageFile);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, Constant.PHOTO);
        } catch (Exception e) {
            BCL.e(e);
        }
    }


    public static void goToCrop(String path, Activity activity) {
        goToCrop(path, activity, false);
    }

    @Deprecated
    public static void goToCrop(String path, Activity activity, boolean fromPushLiveStream) {
        if (activity == null) {
            return;
        }
        try {
            Intent intent = new Intent(activity, CropImageActivity.class);
            intent.putExtra("path", path);
            if (fromPushLiveStream)
                intent.putExtra("fromPushLiveStream", true);
            activity.startActivityForResult(intent, Constant.CROP);
        } catch (Exception e) {
            BCL.e(e);
        }
    }


    /**
     * 处理从相册返回的数据
     *
     * @param activity
     * @param data
     */
    public static void fromAlbum(Activity activity, Intent data) {
        fromAlbum(activity, data, false);
    }

    /**
     * 处理从相册返回的数据V2
     *
     * @param activity
     * @param data
     * @deprecated
     */
    @Deprecated
    public static void fromAlbum(Activity activity, Intent data, boolean fromPushLiveStream) {
        try {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                Cursor cursor = activity.getContentResolver().query(uri,
                        new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (null == cursor) {
                    return;
                }
                cursor.moveToFirst();
                String path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
                Intent intent1 = new Intent(activity, CropImageActivity.class);
                intent1.putExtra("path", path);
                if (fromPushLiveStream)
                    intent1.putExtra("fromPushLiveStream", true);
                activity.startActivityForResult(intent1, Constant.CROP);
            } else {
                Intent intent2 = new Intent(activity, CropImageActivity.class);
                intent2.putExtra("path", uri.getPath());
                if (fromPushLiveStream)
                    intent2.putExtra("fromPushLiveStream", true);
                activity.startActivityForResult(intent2, Constant.CROP);
            }
        } catch (Exception e) {
            BCL.e(e);
        }
    }


    /**
     * 处理裁剪页面回来的数据
     */
    public static File fromCrop(Intent data, File imageFile) {

        try {
            final String path = data.getStringExtra("path");
            Bitmap photo = BitmapFactory.decodeFile(path);
            imageFile = new File(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            imageFile = getImageFile(imageFile, stream);
            String newPath = BaseApplication.SD_SAVEDIR + File.separator + setFileName();
            imageFile.renameTo(new File(newPath));
            imageFile = new File(newPath);
            photo.recycle();
        } catch (Exception e) {
            BCL.e(e);
        }

        return imageFile;
    }

    /**
     * 处理裁剪页面回来的数据
     *
     * @param imageFile
     * @param path
     * @return
     */
    public static File CompressPath(String path, File imageFile) {

        try {
            imageFile = new File(BaseApplication.SD_SAVEDIR + File.separator + setFileName());
            Bitmap photo = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            imageFile = getImageFile(imageFile, stream);
            String newPath = BaseApplication.SD_SAVEDIR + File.separator + setFileName();
            imageFile.renameTo(new File(newPath));
            imageFile = new File(newPath);
            photo.recycle();
        } catch (Exception e) {
            BCL.e(e);
        }
        return imageFile;
    }
}
