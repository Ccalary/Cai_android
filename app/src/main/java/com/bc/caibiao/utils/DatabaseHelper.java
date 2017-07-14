package com.bc.caibiao.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


import com.bc.caibiao.model.CityBean;
import com.bc.caibiao.model.LocationDistrictBean;
import com.bc.caibiao.model.ProvinceBean;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "inno.db"; //数据库名称
    private static final int DB_VERSION = 1; //数据库版本

    private static final String TABLE_NAME = "Location";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PARENT_ID = "parent_id";
    private static final String COLUMN_PARENT_NAME = "parent_name";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER not null," +
                COLUMN_NAME + " varchar(30) not null , " +
                COLUMN_PARENT_ID + " INTEGER not null," +
                COLUMN_PARENT_NAME + " varchar(30));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==2 && oldVersion==1){
        }
    }

    /**
     * 清除表数据
     */
    public void clearLocationTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

    /**
     * 写入省市区数据
     *
     * @param provinceBeanList
     */
    public void writeToTable(List<ProvinceBean> provinceBeanList) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        boolean result = true;
        db.execSQL("DELETE FROM " + TABLE_NAME);
        for (ProvinceBean province : provinceBeanList) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, province.getProvinceId());
            values.put(COLUMN_NAME, province.getProvinceName());
            values.put(COLUMN_PARENT_ID, -1);
            if (db.insert(TABLE_NAME, null, values) < 0) {
                result = false;
                break;
            }
            List<CityBean> cityBeanList = province.getCities();
            for (CityBean city : cityBeanList) {
                ContentValues values1 = new ContentValues();
                values1.put(COLUMN_ID, city.getCityId());
                values1.put(COLUMN_NAME, city.getCityName());
                values1.put(COLUMN_PARENT_ID, province.getProvinceId());
                values1.put(COLUMN_PARENT_NAME, province.getProvinceName());
                db.insert(TABLE_NAME, null, values1);
                List<LocationDistrictBean> locationDistrictBeanList = city.getLocationDistricts();
                for (LocationDistrictBean location : locationDistrictBeanList) {
                    ContentValues values2 = new ContentValues();
                    values2.put(COLUMN_ID, location.getLocationDistrictId());
                    values2.put(COLUMN_NAME, location.getDistrictName());
                    values2.put(COLUMN_PARENT_ID, city.getCityId());
                    values2.put(COLUMN_PARENT_NAME, city.getCityName());
                    db.insert(TABLE_NAME, null, values2);
                }
            }
        }
        if (result) {
            db.setTransactionSuccessful();
        }
        db.endTransaction();
        db.close();
    }

    public List<ProvinceBean> getProvince() {
        List<ProvinceBean> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_PARENT_ID + " =?", new String[]{"-1"});
        if (cursor.moveToFirst()) {
            do {
                ProvinceBean bean = new ProvinceBean();
                bean.setProvinceId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                bean.setProvinceName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                list.add(bean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<CityBean> getCity(int provinceId) {
        List<CityBean> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_PARENT_ID + " =?", new String[]{String.valueOf(provinceId)});
        if (cursor.moveToFirst()) {
            do {
                CityBean bean = new CityBean();
                bean.setCityId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                bean.setCityName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                list.add(bean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<LocationDistrictBean> getLocationArea(int cityId) {
        List<LocationDistrictBean> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_PARENT_ID + " =?", new String[]{String.valueOf(cityId)});
        if (cursor.moveToFirst()) {
            do {
                LocationDistrictBean bean = new LocationDistrictBean();
                bean.setLocationDistrictId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                bean.setDistrictName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                list.add(bean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

}
