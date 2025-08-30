package com.lab.poc.full;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.i("BootReceiverDemo", "Boot completed - starting MainService (demo).");
            Intent service = new Intent(context, MainService.class);
            context.startService(service);
        }
    }
}
