
package com.Arkenea.SelfConfidance;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;

public class videoplayerapplication extends Application {

    MediaPlayer mp;
    Uri uri;
    int position;
    double duration;
    int i, d;
    Thread t;
    boolean change = true;
    int checkaudio = 1;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stubpac
        super.onCreate();
    }

    // gets the duration of the currently running player...
    public double getDuration() {

        return mp.getDuration();
    }

    // releases the media files....
    public void destroy()
    {
        if (mp != null)
        {
            mp.release();
        }
    }

    // plays the player..
    public void play()
    {

        if (checkaudio == 1)
        {
            checkaudio = 2;
        }
        mp.start();
    }

    public void mute()
    {
        mp.setVolume(0, 0);
    }

    public void unmute()
    {
        mp.setVolume(1, 1);
    }

    // player seek forward with particular time w.r.t to user.....
    public void seekforward(int progress)
    {
        mp.seekTo(progress * 1000);
    }

    public void pause()
    {
        mp.pause();
    }

    public double getcurrentduration()
    {
        return mp.getCurrentPosition();
    }

}
