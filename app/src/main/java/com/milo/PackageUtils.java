package com.milo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Package  工具类
 *
 * @author Milo
 *         Email:303767416@qq.com
 * @version 2018/4/9
 */
public class PackageUtils {

    /**
     * 获取AndroidManifest 中 metavalue
     *
     * @param context
     * @param name
     * @return
     */
    public static String getMetaValue(Context context, String name) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo("com.renfenqi.shop", PackageManager.GET_META_DATA);
            return info.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }


}
