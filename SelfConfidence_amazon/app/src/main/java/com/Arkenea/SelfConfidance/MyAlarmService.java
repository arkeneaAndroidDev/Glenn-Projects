
package com.Arkenea.SelfConfidance;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyAlarmService extends Service
{

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        mManager = (NotificationManager) this.getApplicationContext().getSystemService(
                this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(/*
                                     * this.getApplicationContext(),HomeActivity.
                                     * class
                                     */);

        Notification notification = new Notification(R.drawable.ic_launcher, "Wake up call!",
                System.currentTimeMillis());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
                this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        /*notification.setLatestEventInfo(this.getApplicationContext(), ""
                + getResources().getString(R.string.app_name), "Wake up call!",
                pendingNotificationIntent);
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ;
        notification.sound = Uri
                .parse("android.resource://" + getPackageName() + "/" + R.raw.alarm);
        notification.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
        mManager.notify(0, notification);*/

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("Wake up call!")
                .setAutoCancel(true)
                .setPriority(notification.PRIORITY_HIGH)
                .setSound(Uri
                        .parse("android.resource://" + getPackageName() + "/" + R.raw.alarm))
                .setContentIntent(pendingNotificationIntent);

        mManager.notify(0, notificationBuilder.build());

        Intent alarm = new Intent(this, AlamrReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, alarm, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);
        this.stopSelf();


    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
