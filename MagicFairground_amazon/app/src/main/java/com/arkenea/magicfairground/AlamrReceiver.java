
package com.arkenea.magicfairground;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlamrReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent service1 = new Intent(context, MyAlarmService.class);
//        context.startService(service1);
        Intent intentStopAlarm = new Intent(context,StopAlarmDialogActivity.class);
        intentStopAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentStopAlarm);
    }

}
