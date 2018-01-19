package com.wuxianlin.luckyhooker.hooks;

import android.widget.RelativeLayout;

import com.wuxianlin.luckyhooker.Hook;

import java.io.File;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by VeroZ on 2018/1/19.
 */

public class XiaoHaiZi implements Hook {

    public static final String hookPackageName = "com.xiaohaizi.ui";

    @Override
    public boolean canHook(String packageName) {
        return hookPackageName.equals(packageName);
    }

    @Override
    public void startHook(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("start Hook HaiXing:" + lpparam.packageName);
        XposedHelpers.findAndHookMethod("android.app.SharedPreferencesImpl", lpparam.classLoader, "getInt", String.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                File mFile = (File) XposedHelpers.getObjectField(param.thisObject, "mFile");
                if (!mFile.getName().equals("com.xiaohaizi.ui.xml")) return;
                String key = (String) param.args[0];
                if (key.equals("user_vip_type")) {
                    param.setResult(3);
                } else if (key.equals("user_coins")) {
                    param.setResult(999999);
                } else if (key.equals("user_vip_red_coins")) {
                    param.setResult(999999);
                }

            }
        });
    }

    @Override
    public void startHook(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        resparam.res.hookLayout(hookPackageName, "layout", "activity_loading", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                RelativeLayout loadingRelativeLayout = (RelativeLayout) liparam.view;
                RelativeLayout adRelativeLayout = loadingRelativeLayout.findViewById(liparam.res.getIdentifier("act_loading_layout_ad", "id", hookPackageName));
                RelativeLayout skipAdRelativeLayout = adRelativeLayout.findViewById(liparam.res.getIdentifier("act_loading_layout_btn_go_to_main", "id", hookPackageName));
                skipAdRelativeLayout.performClick();
            }
        });
    }

}
