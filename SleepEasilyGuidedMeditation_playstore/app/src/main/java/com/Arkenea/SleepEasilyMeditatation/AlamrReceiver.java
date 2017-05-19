
package com.Arkenea.SleepEasilyMeditatation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlamrReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, MyAlarmService.class);
        context.startService(service1);
    }

}
