
package com.Arkenea.TheHealingWhiteLight;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.R.color;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

public class AudioPlayerDownloadFile extends Activity implements OnClickListener, OnTouchListener,
        OnCompletionListener {
    final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    TextView progressview, titleTextView, audio_duration;
    AudioManager audioManager;
    private int lengthOfAudio;
    Boolean mute = true;
    // TextView textview;
    CustomeTextView setwakeupButton;
    TimePickerDialog mTimePickerDialog;
    static Boolean change = true;
    SeekBar volControl;
    int volValue;
    Double getDuration;
    LinearLayout layout;
    LinearLayout layoutui1;
    LinearLayout layoutui2;
    private Calendar c = null;
    int hour;
    static boolean newcount = true;
    // int newposition;
    int minute;
    int chktrack;

    private ImageView buttonPlayPause, muteButton;
    private SeekBar seekBarProgress;

    String Streaming_audio = "";

    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler();
    private final Runnable r = new Runnable() {

        @Override
        public void run() {
            if (!close) {
                updateSeekProgress();
            } else {
                finish();
            }
        }
    };
    boolean close = false;
    ImageView loop_btn;
    int position = 0;
    boolean loop_setting_tap;
    SharedPreferences MeditationPreference_loop;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Streaming_audio = "";// getIntent().getStringExtra("FILE_NAME_URL");
        position = getIntent().getIntExtra("POSITION", 0);
        MeditationPreference_loop = getSharedPreferences("MEDITATIONPREFERENCES_LOOP", MODE_PRIVATE);
        setContentView(R.layout.audioplayernew);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        // init();
        mediaPlayer = new MediaPlayer();
        // mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        // new loadview().execute("");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();

        titleTextView = (TextView) findViewById(R.id.Title);
        titleTextView.setText(/*
                               * getResources().getString(R.string.
                               * audio_track_header)
                               */getIntent().getStringExtra("FILE_NAME_ONLY"));
        audio_duration = (TextView) findViewById(R.id.audio_duration);

        layout = (LinearLayout) findViewById(R.id.audioplayerlayout);
        layoutui1 = (LinearLayout) findViewById(R.id.audiolayoutui1);
        layoutui2 = (LinearLayout) findViewById(R.id.audiolayoutui2);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxMediaVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (true)
        {
            float volumeFactor = (float) 0.5; // value between 0 and 1
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    (int) (maxMediaVolume * volumeFactor), 0);

        }

        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volControl = (SeekBar) findViewById(R.id.seekbarVolume);

        volControl.setMax(maxMediaVolume);
        volControl.setProgress(curVolume);

        muteButton = (ImageView) findViewById(R.id.mutebutton);
        muteButton.setOnClickListener(this);
        muteButton.setBackgroundColor(color.transparent);
        setwakeupButton = (CustomeTextView) findViewById(R.id.WakeupAlarmButtom);
        setwakeupButton.setBackgroundColor(Color.TRANSPARENT);
        setwakeupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                mTimePickerDialog = new TimePickerDialog(AudioPlayerDownloadFile.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);
                                Intent intent = new Intent(
                                        AudioPlayerDownloadFile.this,
                                        AlamrReceiver.class);
                                PendingIntent pi = PendingIntent.getBroadcast(
                                        AudioPlayerDownloadFile.this, 0, intent, 0);
                                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                long timeToAlarm = c.getTimeInMillis();
                                if (c.getTimeInMillis() + (60 * 1000) < System
                                        .currentTimeMillis()) {
                                    timeToAlarm += (24 * 60 * 60 * 1000);
                                }
                                am.set(AlarmManager.RTC_WAKEUP,
                                        timeToAlarm, pi);

                            }
                        }, hour, minute, true);
                mTimePickerDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                mTimePickerDialog.show();
            }
        });

        volControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressvol,
                                          boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progressvol, 50);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
        });
        initView();
        loop_btn = (ImageView) findViewById(R.id.loop_btn);
        loop_btn.setImageResource(R.drawable.switch_no);
        loop_btn.setOnClickListener(this);
        startmediaplayer();

    }

    private void startmediaplayer() {
        try {
            String filelocation = "";

            filelocation = Environment.getExternalStorageDirectory()
                    + getResources().getString(R.string.sd_card_dir_name) + "/"
                    + getIntent().getStringExtra("FILE_NAME");

            mediaPlayer.setDataSource(this, Uri.parse(filelocation));
            mediaPlayer.prepare();
            lengthOfAudio = mediaPlayer.getDuration();
            mediaPlayer.start();
            updateSeekProgress();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        buttonPlayPause = (ImageView) findViewById(R.id.btnplay);
        buttonPlayPause.setOnClickListener(this);
        buttonPlayPause.setBackgroundColor(color.transparent);
        buttonPlayPause = (ImageView) findViewById(R.id.btnplay);
        buttonPlayPause.setOnClickListener(this);
        seekBarProgress = (SeekBar) findViewById(R.id.seekbartrack);
        seekBarProgress.setOnTouchListener(this);
    }

    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        // seekBarProgress.setSecondaryProgress(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        buttonPlayPause.setEnabled(true);
        seekBarProgress.setProgress(0);
        double timeRemaining = mediaPlayer.getDuration()/*
                                                         * - mediaPlayer.
                                                         * getCurrentPosition()
                                                         */;
        audio_duration.setText(String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining),
                TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                        .toMinutes((long) timeRemaining))));

        buttonPlayPause.setImageResource(R.drawable.play);
        mediaPlayer.seekTo((lengthOfAudio / 100) * seekBarProgress.getProgress());
        mediaPlayer.pause();
        change = false;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // if (mediaPlayer.isPlaying()) {
        SeekBar tmpSeekBar = (SeekBar) v;
        mediaPlayer.seekTo((lengthOfAudio / 100) * tmpSeekBar.getProgress());
        // }
        return false;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnplay) {
            if (change) {

                buttonPlayPause.setImageResource(R.drawable.play);
                mediaPlayer.pause();
                change = false;
            }
            else if (change == false) {

                buttonPlayPause.setImageResource(R.drawable.pause);
                mediaPlayer.start();
                change = true;
            }
        }
        if (v.getId() == R.id.mutebutton) {
            if (mute) {
                // muteButton.setImageResource(R.drawable.volume_low);
                volValue = volControl.getProgress();
                mediaPlayer.setVolume(0, 0);
                mute = false;
                volControl.setProgress(0);
            }
            else if (mute == false) {
                // muteButton.setImageResource(R.drawable.volume_heigh);
                mediaPlayer.setVolume(1, 1);
                mute = true;
                volControl.setProgress(volValue);
            }
        }

        if (v.getId() == R.id.loop_btn) {
            if (loop_setting_tap) {
                loop_btn.setImageResource(R.drawable.switch_no);
                loop_setting_tap = false;
                mediaPlayer.setLooping(false);

            } else {
                loop_setting_tap = true;
                loop_btn.setImageResource(R.drawable.switch_yes);
                mediaPlayer.setLooping(true);
            }
        }
        updateSeekProgress();
    }

    private void updateSeekProgress() {
        // if (mediaPlayer.isPlaying()) {
        seekBarProgress
                .setProgress((int) (((float) mediaPlayer.getCurrentPosition() / lengthOfAudio) * 100));

        double timeRemaining = mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition();
        audio_duration.setText(String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining),
                TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                                .toMinutes((long) timeRemaining))));
        // audio_duration.setText(String.valueOf((float)mediaPlayer.getDuration()/1000)
        // + "m");

        handler.postDelayed(r, 1000);
        // }
    }

    @Override
    public void onBackPressed() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            close = true;
        }
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    class loadview extends AsyncTask<String, String, String> {
        Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
            setContentView(R.layout.audioplayernew);
        }

        @Override
        protected String doInBackground(String... aurl) {
            try {
                mediaPlayer.setDataSource(Streaming_audio);
                mediaPlayer.prepare();
                lengthOfAudio = mediaPlayer.getDuration();

                updateSeekProgress();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {

            if (mProgressDialog.isShowing())
            {
                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            }
            mediaPlayer.start();
            updateSeekProgress();

            buttonPlayPause.setImageResource(R.drawable.pause);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS: // we set this to 0
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }
}
