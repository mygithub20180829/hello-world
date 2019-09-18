package com.liuwang.myok.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {
    /**
     * 保持数据
     * @param context
     * @param key
     * @param values
     */
    public static void putString(Context context,String key,String values){
        SharedPreferences sharedPreferences=context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,values).commit();
    }

    public static String getString(Context context,String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
