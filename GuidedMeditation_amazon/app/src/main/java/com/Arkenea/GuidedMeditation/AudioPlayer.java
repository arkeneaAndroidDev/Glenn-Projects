package com.Arkenea.GuidedMeditation;


import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


import android.R.color;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;


public class AudioPlayer extends Activity implements OnClickListener {

    //data declare globle previously
    boolean durationCheckBoolean = true;
    double seekbar = 1;
    int getSeekPosition;
    boolean toKeepTrackValue = false;
    boolean setimageblack = true;
    int imp = 0;
    int check_audio_track = 0;

    int d;

    static Boolean remainingDuration = true;
    int finalDuration;


    //   static boolean countCheck=tru
    ImageView play, muteButton;
    videoplayerapplication application;
    TextView progressview, titleTextView, audio_duration;
    AudioManager audioManager;
    SeekBar bar;
    Boolean mute = true;
    //TextView textview;
    CountDownTimer count;
    Button setwakeupButton;
    TimePickerDialog mTimePickerDialog;
    int position = 0;
    double duration;
    int i = 0;
    double conversion = 0;
    int prog;
    // static boolean dur=true;
    boolean change = true;
    SeekBar volControl;
    int volValue;
    Double getDuration;
    LinearLayout layout;
    LinearLayout layoutui1;
    LinearLayout layoutui2;
    private Calendar c = null;
    int hour;
    boolean newcount = true;
    // int newposition;
    int minute;

    ImageView loop_btn;
    boolean loop_setting_tap;
    //int position = 0;
    // int checkAudioTrackFinish = 1;
    //  static boolean newNewtrackchangeAfterrestart=true;
    SharedPreferences MeditationPreference_loop;
    private final Handler handler = new Handler();

    @Override
    protected void onResume() {
        bar.setProgress((int) (conversion / 10000));
        double timeRemaining1 = application.mp.getDuration() - application.mp.getCurrentPosition();
        audio_duration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining1), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining1))));
        super.onResume();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate","Audioplayer");

        position = getIntent().getIntExtra("POSITION", 0);
        Log.i("position", "" + position);
        MeditationPreference_loop = getSharedPreferences("MEDITATIONPREFERENCES_LOOP", MODE_PRIVATE);
        setContentView(R.layout.audioplayernew);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();

        titleTextView = (TextView) findViewById(R.id.Title);
        titleTextView.setText(/*getResources().getString(R.string.audio_track_header)*/"" + getIntent().getStringExtra("FILE_NAME_ONLY"));
        audio_duration = (TextView) findViewById(R.id.audio_duration);
        layout = (LinearLayout) findViewById(R.id.audioplayerlayout);
        layoutui1 = (LinearLayout) findViewById(R.id.audiolayoutui1);
        layoutui2 = (LinearLayout) findViewById(R.id.audiolayoutui2);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxMediaVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (true) {
            float volumeFactor = (float) 0.5; // value between 0 and 1
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (maxMediaVolume * volumeFactor), 0);
        }


        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volControl = (SeekBar) findViewById(R.id.seekbarVolume);

        volControl.setMax(maxMediaVolume);
        volControl.setProgress(curVolume);

        muteButton = (ImageView) findViewById(R.id.mutebutton);
        muteButton.setOnClickListener(this);
        muteButton.setBackgroundColor(color.transparent);
        setwakeupButton = (Button) findViewById(R.id.WakeupAlarmButtom);
        setwakeupButton.setBackgroundColor(Color.TRANSPARENT);
        setwakeupButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                mTimePickerDialog = new TimePickerDialog(AudioPlayer.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);
                                Intent intent = new Intent(
                                        AudioPlayer.this,
                                        AlamrReceiver.class);
                                PendingIntent pi = PendingIntent.getBroadcast(
                                        AudioPlayer.this, 0, intent, 0);
                                AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                                am.set(AlarmManager.RTC_WAKEUP,
                                        c.getTimeInMillis(), pi);

                            }
                        }, hour, minute, true);
                mTimePickerDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                mTimePickerDialog.show();
            }
        });

        play = (ImageView) findViewById(R.id.btnplay);
        bar = (SeekBar) findViewById(R.id.seekbartrack);
        play.setOnClickListener(this);
        play.setBackgroundColor(color.transparent);
        application = (videoplayerapplication) getApplication();
        check_audio_play();

        application.play();


        application.mp.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                bar.setProgress(0);
                bar.setEnabled(true);
                bar.setActivated(true);
                d = (int) (application.getcurrentduration() / 1000);
                countdown(d);
                check_audio_play();
                application.play();
                application.pause();
                audio_duration.setText("" + d);
                play.setImageResource(R.drawable.play);
                count.cancel();
                change = false;
                bar.setEnabled(true);

            }

        });


        volControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progressvol,
                                          boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progressvol, 50);

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }
        });


        duration = ((application.getDuration() - application.getcurrentduration()) / 1000);
        d = (int) (duration);
        bar.setMax(d);
        countdown(d);


        bar.setEnabled(true);
        bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // check whether the user has touched the bar
                if (fromUser) {
                    application.seekforward(progress);
                    i = progress;
                    prog = progress;
                    count.cancel();
                    newcount = false;
                    play.setImageResource(R.drawable.pause);
                    countdown((application.getDuration() / 1000) - progress);
                    durationCheckBoolean = true;
                    //bar.setProgress((int)(((float)mediaPlayer.getCurrentPosition() / lengthOfAudio) * 100));

                    double timeRemaining = application.mp.getDuration() - application.mp.getCurrentPosition();

                    audio_duration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

                }
            }
        });
        if (!application.mp.isPlaying()) {
            getmaxDuration();
        }

        loop_btn = (ImageView) findViewById(R.id.loop_btn);
        loop_btn.setImageResource(R.drawable.switch_no);
        loop_btn.setOnClickListener(this);
        if (MeditationPreference_loop.getInt("audio" + position, 0) == 0) {
            application.mp.setLooping(false);
            loop_btn.setImageResource(R.drawable.switch_no);
        } else {
            application.mp.setLooping(true);
            loop_btn.setImageResource(R.drawable.switch_yes);
        }
    }


    public void countdown(double convert) {
        count = new CountDownTimer((long) (convert * 1000), 1000) {
            @Override
            public void onTick(long millisecond) {
                // if(dur==true)
                conversion = millisecond;
                conversion = application.mp.getDuration() - application.mp.getCurrentPosition();
                String b = "";
                b = ("" + conversion / 60000);

                bar.setProgress((int) (conversion / 10000));
                double timeRemaining1 = application.mp.getDuration() - application.mp.getCurrentPosition();
                audio_duration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining1), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining1))));

                int seconds = (int) ((millisecond / 1000) % 60);

                String trackString = "";
                //trackString= (String) textview.getText();

                if (trackString.contains(".") || durationCheckBoolean == false) {
                    //textview.setText(b.substring(0, 1)+":"+seconds);
                    durationCheckBoolean = false;
                } else if (durationCheckBoolean) {
                    //textview.setText(""+b.substring(0, 2)+":"+seconds);
                }

                if (toKeepTrackValue == false) {
                    //i=(int) application.getcurrentduration();
                    //bar.setProgress((int) (conversion / 10000));
                    bar.setProgress(i++);
                    double timeRemaining = application.mp.getDuration() - application.mp.getCurrentPosition();
                    audio_duration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

                    // i++;
                    // i=i+0.5;
                    // d=(int) (conversion/60000);
                    //bar.setProgress(getSeekPosition++);
                }
                if (toKeepTrackValue == true) {

                    getSeekPosition = getSeekPosition / 10000;
                    bar.setProgress(getSeekPosition++);
                    double timeRemaining = application.mp.getDuration() - application.mp.getCurrentPosition();
                    audio_duration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

                    toKeepTrackValue = false;
                }

            }

            @Override
            public void onFinish() {
                Log.i("Finish", "finish");
                i = 0;
                setimageblack = false;
                //textview.setText("");
                newcount = false;
                imp = 0;
                // ThinkPositivelyActivity.check_audio_track=0;
                /*if(MeditationPreference_loop.getInt("audio"+position, 0) == 1){
					bar.setProgress(0);
					bar.setEnabled(true);
					bar.setActivated(true);
					check_audio_play();
				}*/

            }
        }.start();


    }


    @Override
    protected void onPause() {
        super.onPause();
		/*if (application.mp != null) {
			application.mp.stop();
		}
		finish();*/
    }

    @Override
    public void onBackPressed() {
        if (application.mp != null) {
            application.mp.stop();
        }
        finish();
    }

    public static void clearAndExit(Context ctx) {
        if (!(ctx instanceof AudioPlayer)) {
            Intent intent = new Intent(ctx, AudioPlayer.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putBoolean("exit", true);
            intent.putExtras(bundle);
            ctx.startActivity(intent);
        } else {
            ((Activity) ctx).finish();
        }
    }


    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            application.mp.stop();
            finish();
            return true;
        }

        return false;
    }*/
    public void onClick(View v) {
        if (v.getId() == R.id.btnplay) {
            if (change) {
                play.setImageResource(R.drawable.play);
                application.pause();
                count.cancel();
                change = false;
                bar.setEnabled(false);
            } else if (change == false) {
                play.setImageResource(R.drawable.pause);
                application.play();
                countdown(conversion / 1000);
                change = true;
                bar.setEnabled(true);
            }
        }
        if (v.getId() == R.id.mutebutton) {
            if (mute) {
                //muteButton.setImageResource(R.drawable.volume_low);
                volValue = volControl.getProgress();
                application.mute();
                mute = false;
                volControl.setProgress(0);

            } else if (mute == false) {
                //muteButton.setImageResource(R.drawable.volume_heigh);
                application.unmute();
                mute = true;
                volControl.setProgress(volValue);
            }
        }

        if (v.getId() == R.id.loop_btn) {
            SharedPreferences.Editor editor_loop = MeditationPreference_loop.edit();
            if (MeditationPreference_loop.getInt("audio" + position, 0) == 0) {
                editor_loop.putInt("audio" + position, 1);
                application.mp.setLooping(true);
                loop_btn.setImageResource(R.drawable.switch_yes);
            } else {
                editor_loop.putInt("audio" + position, 0);
                application.mp.setLooping(false);
                loop_btn.setImageResource(R.drawable.switch_no);
            }
            editor_loop.commit();
        }
    }


    public void getmaxDuration() {
        conversion = application.mp.getDuration() - application.mp.getCurrentPosition();
        String b = "";
        b = ("" + conversion / 60000);

        bar.setProgress((int) (conversion / 10000));
        int seconds = (int) ((conversion / 1000) % 60);
        //textview.setText(b.substring(0, 2)+":"+seconds);
        double timeRemaining = application.mp.getDuration() - application.mp.getCurrentPosition();
        audio_duration.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

    }

    public void check_audio_play() {
        String filelocation = "";

        filelocation = Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name) + "/" + getIntent().getStringExtra("FILE_NAME");
        bar.setProgress(0);

        check_audio_track = 1;
        //ThinkPositivelyActivity.imp=1;
        if (imp == 1 || imp == 0) {
            if (application.mp != null) {
                application.mp.stop();
            }
        }


        application.mp = new MediaPlayer();
        if (!application.mp.isPlaying()) {
            try {
                application.mp.setDataSource(application, Uri.parse(filelocation));
                application.mp.prepare();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            {
                duration = ((application.getDuration() - application.getcurrentduration()) / 1000);
                d = (int) (application.getcurrentduration() / 1000);
                bar.setMax(d);
                countdown(d);
            }
            application.play();
        }
    }


}