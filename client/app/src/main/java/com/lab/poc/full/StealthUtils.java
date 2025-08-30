package com.lab.poc.full;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public class StealthUtils {
    // Demo: toggle launcher icon (educational). Requires caution in real devices.
    public static void hideIcon(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        pm.setComponentEnabledSetting(
            new ComponentName(ctx, MainActivity.class),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        );
        Log.i("StealthUtils","hideIcon called (demo)");
    }
    public static void showIcon(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        pm.setComponentEnabledSetting(
            new ComponentName(ctx, MainActivity.class),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        );
        Log.i("StealthUtils","showIcon called (demo)");
    }
}
