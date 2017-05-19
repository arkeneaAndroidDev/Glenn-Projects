package com.arkenea.magicfairground;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by admin on 10-08-2016.
 */
public class StopAlarmDialogActivity extends Activity {
    AlarmManager alarmManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Uri alarmSound = (Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm));
        final Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
        r.play();

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Stop Alarm");
        alert.setCancelable(false);
        alert.setNegativeButton(this.getResources().getString(R.string.Cancel),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        r.stop();
                        finish();
                    }
                });
        AlertDialog builder = alert.create();
        builder.show();

    }


}
