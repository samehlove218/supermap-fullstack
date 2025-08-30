package com.lab.poc.full;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.i("DeviceAdminDemo","Device admin enabled (demo).");
    }
    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.i("DeviceAdminDemo","Device admin disabled (demo).");
    }
}
