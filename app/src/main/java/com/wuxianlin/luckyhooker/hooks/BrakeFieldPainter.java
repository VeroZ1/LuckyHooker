package com.wuxianlin.luckyhooker.hooks;


import com.wuxianlin.luckyhooker.Hook;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by VeroZ on 2018/5/1.
 */

public class BrakeFieldPainter implements Hook {

    public static final String hookPackageName = "com.brakefield.painter";

    @Override
    public boolean canHook(String packageName) {
        return hookPackageName.equals(packageName);
    }

    @Override
    public void startHook(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("start Hook BrakeField Painter");
        XposedHelpers.findAndHookMethod("com.brakefield.painter.PurchaseManager", lpparam.classLoader, "hasMaster", XC_MethodReplacement.returnConstant(true));
    }

    @Override
    public void startHook(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {

    }
}
