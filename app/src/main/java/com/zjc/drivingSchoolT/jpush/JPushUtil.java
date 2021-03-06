package com.zjc.drivingSchoolT.jpush;

import com.mobo.mobolibrary.logs.Logs;
import com.zjc.drivingSchoolT.app.MApp;
import com.zjc.drivingSchoolT.db.SharePreferences.SharePreferencesUtil;
import com.zjc.drivingSchoolT.db.models.UserInfo;

import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;

/**
 * 推送工具类
 */
public class JPushUtil {

    /**
     * 推送标记
     */
    //极光推送，科室标识
    public static final String JP_DEPT_FLAG = "D";
    //极光推送，机构标识
    public static final String JP_ORGANIZATION_FLAG = "O";

    /**
     * 上线版标记
     */
    //极光推送，科室标识
    public static final String APP_DEBUG = "debug";
    //极光推送，机构标识
    public static final String APP_RELEASE = "release";


    public static void initJPush() {
        //推送
        JPushInterface.init(MApp.getInstance().getApplicationContext());
        setAliasAndTags();
    }

    /**
     * 拼接推送标记
     *
     * @param depart
     * @return
     */
    public static String getJPushTagByDepart(int depart) {
        if (Logs.issIsLogEnabled()) {
            return APP_DEBUG + "_D_" + depart;
        } else {
            return APP_RELEASE + "_D_" + depart;
        }
    }

    /**
     * 设置别名和标记
     */
    public static void setAliasAndTags() {
        try {
            //调用JPush API设置Alias
            if (SharePreferencesUtil.getInstance().isLogin()) {
                UserInfo userInfo = SharePreferencesUtil.getInstance().readUser();

            } else if (SharePreferencesUtil.getInstance().isLogin()) {
                //如果认证中
                JPushInterface.setAliasAndTags(MApp.getInstance().getApplicationContext(), SharePreferencesUtil.getInstance().readUser().getUid() + "", new HashSet<String>(), null);
            } else {
                //如果未登录，未认证
                JPushInterface.setAliasAndTags(MApp.getInstance().getApplicationContext(), "", new HashSet<String>(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
