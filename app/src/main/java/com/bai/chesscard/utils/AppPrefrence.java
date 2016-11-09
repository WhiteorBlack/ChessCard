package com.bai.chesscard.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefrence {

    private static SharedPreferences setting;

    private static SharedPreferences getSp(Context context) {
        SharedPreferences sp = context.getSharedPreferences("carSchool",
                context.MODE_PRIVATE);
        return sp;
    }

    private static final String ISLOGIN = "isLogin";
    private static final String USERNAME = "userName";
    private static final String USERPWD = "userPwd";
    private static final String TOKEN = "token";
    private static final String USERPHONE = "userPhone";
    private static final String ISNOTIFY = "isNotify";
    private static final String BACKSOUND = "backSound";
    private static final String CLICKSOUND = "clickSound";
    private static final String USERNO = "userNo";

    public static void setIsPush(Context context, boolean isNotify) {
        setting = getSp(context.getApplicationContext());
        setting.edit().putBoolean(CLICKSOUND, isNotify).apply();
    }

    public static boolean getIsPush(Context context) {
        setting = getSp(context.getApplicationContext());
        return setting.getBoolean(CLICKSOUND, false);
    }

    public static void setIsBack(Context context, boolean isNotify) {
        setting = getSp(context.getApplicationContext());
        setting.edit().putBoolean(BACKSOUND, isNotify).apply();
    }

    public static boolean getIsBack(Context context) {
        setting = getSp(context.getApplicationContext());
        return setting.getBoolean(BACKSOUND, true);
    }

    public static void setIsNotify(Context context, boolean isNotify) {
        setting = getSp(context.getApplicationContext());
        setting.edit().putBoolean(ISNOTIFY, isNotify).apply();
    }

    public static boolean getIsNotify(Context context) {
        setting = getSp(context.getApplicationContext());
        return setting.getBoolean(ISNOTIFY, true);
    }

    public static void setUserPhone(Context context, String phone) {
        setting = getSp(context.getApplicationContext());
        setting.edit().putString(USERPHONE, phone).apply();
    }

    public static String getUserPhone(Context context) {
        setting = getSp(context.getApplicationContext());
        return setting.getString(USERPHONE, "");
    }

    public static void setUserNo(Context context, String token) {
        setting = getSp(context.getApplicationContext());
        setting.edit().putString(USERNO, token).apply();
    }

    public static String getUserNo(Context context) {
        setting = getSp(context.getApplicationContext());
        return setting.getString(USERNO, "");
    }

    public static void setToken(Context context, String token) {
        setting = getSp(context.getApplicationContext());
        setting.edit().putString(TOKEN, token).apply();
    }

    public static String getToken(Context context) {
        setting = getSp(context.getApplicationContext());
        return setting.getString(TOKEN, "");
    }

    /**
     * 用户密码
     *
     * @param context
     */
    public static void setUserPwd(Context context, String pwd) {
        setting = getSp(context);
        setting.edit().putString(USERPWD, pwd).commit();
    }

    public static String getUserPwd(Context context) {
        setting = getSp(context);
        return setting.getString(USERPWD, "");
    }

    /**
     * 用户登录名
     *
     * @param context
     * @param name
     */
    public static void setUserName(Context context, String name) {
        setting = getSp(context);
        setting.edit().putString(USERNAME, name).commit();
    }

    public static String getUserName(Context context) {
        setting = getSp(context);
        return setting.getString(USERNAME, "");
    }

    public static void setIsLogin(Context context, boolean isLogin) {
        setting = getSp(context);
        setting.edit().putBoolean(ISLOGIN, isLogin).commit();
    }

    public static boolean getIsLogin(Context context) {
        setting = getSp(context);
        return setting.getBoolean(ISLOGIN, false);
    }
}
