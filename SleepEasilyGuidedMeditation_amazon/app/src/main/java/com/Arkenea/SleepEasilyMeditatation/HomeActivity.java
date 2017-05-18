
package com.Arkenea.SleepEasilyMeditatation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.glenn_library.BaseActivity;
import com.android.glenn_library.MyShareDialog;
import com.android.glenn_library.MySocialShare;

//import com.helpshift.Helpshift;

public class HomeActivity extends BaseActivity implements OnClickListener {
    LinearLayout audio_container, ebook_container, social_container_fb, share_container,social_container_twitt,
            review_container, more_container, help_container, fullversion_container;

    ImageView audio_img, ebook_img, social_img_fb, share_img, review_img, more_img, help_img,social_img_twitt, fullversion_img;

    CustomeTextView audio_txt, ebook_txt, social_txt_fb, share_txt, review_txt, more_txt, help_txt,social_txt_twitt, fullversion_txt;

    SharedPreferences MeditationPreference, MeditationPreference_loop;

    long audio_filessizes = 37507197;//37507197

    private ProgressDialog mProgressDialog;

    final int DIALOG_DOWNLOAD_PROGRESS = 0;

    String progressString = "";

    Boolean mCheckContainer;

    public  static final int DOWNLOAD_PDF_PERMISSION = 11;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        MeditationPreference = getSharedPreferences("MEDITATIONPREFERENCES", MODE_PRIVATE);
        MeditationPreference_loop = getSharedPreferences("MEDITATIONPREFERENCES_LOOP", MODE_PRIVATE);
        
        if (MeditationPreference.getString("DATA", "NO_DATA").equalsIgnoreCase("NO_DATA")) {
            SharedPreferences.Editor editor = MeditationPreference.edit();
            editor.putString("DATA", "DATA_PRESENT");
            editor.putInt("audio1", 0);
            editor.commit();

            SharedPreferences.Editor editor_loop = MeditationPreference_loop.edit();
            editor_loop.putInt("audio1", 0);
            editor_loop.commit();
        }
        
        setContentView(R.layout.homeactivity);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        audio_container = (LinearLayout) findViewById(R.id.audio_container);
        ebook_container = (LinearLayout) findViewById(R.id.ebook_container);
        social_container_fb = (LinearLayout) findViewById(R.id.social_container_fb);
        social_container_twitt = (LinearLayout) findViewById(R.id.social_container_twitt);
        share_container = (LinearLayout) findViewById(R.id.share_container);
        review_container = (LinearLayout) findViewById(R.id.review_container);
        more_container = (LinearLayout) findViewById(R.id.more_container);
        help_container = (LinearLayout) findViewById(R.id.help_container);
        help_container.setVisibility(View.GONE);
        fullversion_container = (LinearLayout) findViewById(R.id.fullversion_container);
        
        audio_img = (ImageView) findViewById(R.id.audio_img);
        ebook_img = (ImageView) findViewById(R.id.ebook_img);
        social_img_fb = (ImageView) findViewById(R.id.social_img_fb);
        social_img_twitt = (ImageView) findViewById(R.id.social_img_twitt);
        share_img = (ImageView) findViewById(R.id.share_img);
        review_img = (ImageView) findViewById(R.id.review_img);
        more_img = (ImageView) findViewById(R.id.more_img);
        help_img = (ImageView) findViewById(R.id.help_img);
        fullversion_img = (ImageView) findViewById(R.id.fullversion_img);

        audio_txt = (CustomeTextView) findViewById(R.id.audio_txt);
        ebook_txt = (CustomeTextView) findViewById(R.id.ebook_txt);
        social_txt_fb = (CustomeTextView) findViewById(R.id.social_txt_fb);
        social_txt_twitt = (CustomeTextView) findViewById(R.id.social_txt_twitt);
        share_txt = (CustomeTextView) findViewById(R.id.share_txt);
        review_txt = (CustomeTextView) findViewById(R.id.review_txt);
        more_txt = (CustomeTextView) findViewById(R.id.more_txt);
        help_txt = (CustomeTextView) findViewById(R.id.help_txt);
        fullversion_txt = (CustomeTextView) findViewById(R.id.fullversion_txt);

        audio_container.setOnClickListener(this);
        ebook_container.setOnClickListener(this);
        social_container_fb.setOnClickListener(this);
        social_container_twitt.setOnClickListener(this);
        share_container.setOnClickListener(this);
        review_container.setOnClickListener(this);
        more_container.setOnClickListener(this);
        help_container.setOnClickListener(this);
        fullversion_container.setOnClickListener(this);

        LinearLayout homeActivity_ll_1 = (LinearLayout) findViewById(R.id.homeActivity_ll_1);
        RelativeLayout.LayoutParams Lp = (RelativeLayout.LayoutParams) homeActivity_ll_1.getLayoutParams();
        Lp.topMargin = (int) Math.round(height * 0.25);
        Lp.leftMargin=(int )Math.round(width * 0.22);
        Lp.width = (int) Math.round(width * 0.7);
        homeActivity_ll_1.setLayoutParams(Lp);
    }

    @Override
    public void onClick(View v) {
        textcolorhandler();
        switch (v.getId()) {
            case R.id.audio_container:
                mCheckContainer = true;
                audio_img.setImageResource(R.drawable.audio_h);
                audio_txt.setTextColor(Color.parseColor("#000000"));
                //startActivity(new Intent(HomeActivity.this, AudioTrackActivity.class));
                if(checkPermission())
                    audiohandler(0);
                break;
            case R.id.fullversion_container:
            	fullversion_img.setImageResource(R.drawable.more_h);
                fullversion_txt.setTextColor(Color.parseColor("#000000"));
                try {
                    Intent ifullversion = new Intent(Intent.ACTION_VIEW);
                    //TODO check playstore link and amazon link while uploading
                    ifullversion.setData(Uri.parse(("amzn://apps/android?p=com.arkenea.sleepeasily.fullversion")));
                    //ifullversion.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.arkenea.sleepeasily.fullversion"));
                    startActivity(ifullversion);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;


            case R.id.ebook_container:
                mCheckContainer = false;
            	ebook_img.setImageResource(R.drawable.all_ebooks_h);
                ebook_txt.setTextColor(Color.parseColor("#000000"));
                if(checkPermission())
                    startActivity(new Intent(HomeActivity.this, EbooksActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.social_container_fb:

                social_img_fb.setImageResource(R.drawable.fb_h);
                social_txt_fb.setTextColor(Color.parseColor("#000000"));
                MySocialShare social_dialog_fb = new MySocialShare(HomeActivity.this, getResources().getString(R.string.connect_to_shazzie_fb),
                        getResources().getString(R.string.connect_to_ali_fb));
                social_dialog_fb.show();
                break;
                
            case R.id.social_container_twitt:

                social_img_twitt.setImageResource(R.drawable.twitter_h);
                social_txt_twitt.setTextColor(Color.parseColor("#000000"));
                MySocialShare social_dialog_twitt = new MySocialShare(HomeActivity.this, getResources().getString(R.string.connect_to_shazzie_twitt),
                        getResources().getString(R.string.connect_to_ali_twitt));
                social_dialog_twitt.show();
                break;

            case R.id.share_container:

                share_img.setImageResource(R.drawable.share_this_app_h);
                share_txt.setTextColor(Color.parseColor("#000000"));
                String share_massage = "Shazzie helps you relax and go fully into a deep relaxing sleep through her Sleep Easily App.";
                
                MyShareDialog share_dialog = new MyShareDialog(HomeActivity.this, share_massage,false);
                share_dialog.show();
                break;

            case R.id.review_container:

                review_img.setImageResource(R.drawable.review_this_app_h);
                review_txt.setTextColor(Color.parseColor("#000000"));
                Intent i = new Intent(Intent.ACTION_VIEW);
                //TODO check playstore link and amazon link while uploading
                i.setData(Uri.parse(("amzn://apps/android?p=com.Arkenea.SleepEasilyMeditatation")));
                //i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                startActivity(i);
                break;

            case R.id.more_container:
                more_img.setImageResource(R.drawable.more_h);
                more_txt.setTextColor(Color.parseColor("#000000"));
                /*
                String uri = String.format("https://play.google.com/store/apps/"
                        + "developer?id=Diviniti+Publishing+Ltd");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);*/
            	//TODO check playstore and amazon purchase while uploading
                //startActivity(new Intent(HomeActivity.this, PurchasePlayActivity.class));
                startActivity(new Intent(HomeActivity.this, PurchaseAmazonActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

           /* case R.id.help_container:

                help_img.setImageResource(R.drawable.all_ebooks_h);
                help_txt.setTextColor(Color.parseColor("#000000"));
                // Helpshift.install(getApplication(),
                // "9aa62e948ed6b09c0537f672ac303fae", "diviniti.helpshift.com",
                // "diviniti_platform_20150507104852272-79b70fd4bf4f4fb");

                startActivity(new Intent(HomeActivity.this, HelpMeditation.class));
                break;*/

            default:
                break;
        }

    }

    private void textcolorhandler() {
        audio_img.setImageResource(R.drawable.audio);
        ebook_img.setImageResource(R.drawable.ebook);
        social_img_fb.setImageResource(R.drawable.fb);
        social_img_twitt.setImageResource(R.drawable.twitter_n);
        share_img.setImageResource(R.drawable.share);
        review_img.setImageResource(R.drawable.review);
        more_img.setImageResource(R.drawable.more);
        help_img.setImageResource(R.drawable.ebook);
        fullversion_img.setImageResource(R.drawable.more);
        try {
            XmlResourceParser xrp = getResources().getXml(R.xml.txtcolor);
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            audio_txt.setTextColor(csl);
            ebook_txt.setTextColor(csl);
            social_txt_fb.setTextColor(csl);
            social_txt_twitt.setTextColor(csl);
            share_txt.setTextColor(csl);
            review_txt.setTextColor(csl);
            more_txt.setTextColor(csl);
            help_txt.setTextColor(csl);
            fullversion_txt.setTextColor(csl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    private void audiohandler(final int pos) {
        if (MeditationPreference.getInt("audio" + pos, 0) == 0) {

            final Dialog download_dialog = new Dialog(HomeActivity.this);

            download_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            download_dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            download_dialog.getWindow().setBackgroundDrawableResource(R.drawable.tranperent);
            // logout_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            download_dialog.setContentView(R.layout.audio_conformation_dialog);
            CustomeTextView download_dialog_txt = (CustomeTextView) download_dialog
                    .findViewById(R.id.download_dialog_txt);
            CustomeTextView download_txt = (CustomeTextView) download_dialog
                    .findViewById(R.id.download_txt);
            CustomeTextView not_now_txt = (CustomeTextView) download_dialog
                    .findViewById(R.id.not_now_txt);
            download_dialog_txt
                    .setText("The audio needs to be downloaded the first time to play. Continue to download now.");
            download_txt.setText("Continue");
            not_now_txt.setText("Not Now");
            XmlResourceParser xrp = getResources().getXml(R.xml.dialog_txtcolor);
            try {
                ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
                download_txt.setTextColor(csl);
                not_now_txt.setTextColor(csl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            download_txt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    File folder = new File(Environment.getExternalStorageDirectory()
                            + getResources().getString(R.string.sd_card_dir_name));
                    Log.i("foldername", "" + folder);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    File folder1 = new File(Environment.getExternalStorageDirectory()
                            + getResources().getString(R.string.sd_card_dir_name) + "/"
                            + getResources().getString(R.string.audio_tracks_name));
                    Log.i("folder1name", "" + folder);
                    if (!folder1.exists()) {
                        if (isNetworkAvailable(HomeActivity.this)) {
                            new DownloadFileAsync().execute(getResources().getString(R.string.audio_tracks_server_urls));
                            SharedPreferences.Editor editor = MeditationPreference.edit();
                            editor.putInt("audio" + pos, 2);
                            editor.commit();
                        } else {
                            Toast.makeText(HomeActivity.this,
                                    "Please check your Internet connection.", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } else {
                        File folder1_ = new File(Environment.getExternalStorageDirectory()
                                + getResources().getString(R.string.sd_card_dir_name) + "/"
                                + getResources().getString(R.string.audio_tracks_name));
                        Log.i("File size", "" + folder1.length());
                        if (folder1_.length() >= audio_filessizes) {
                            Intent intent = new Intent(HomeActivity.this,
                                    AudioPlayerDownloadFile.class);
                            intent.putExtra("POSITION", pos);
                            intent.putExtra("FILE_NAME_ONLY", "" + getResources().getString(R.string.audio_names));
                            intent.putExtra("FILE_NAME", "" + getResources().getString(R.string.audio_tracks_name));
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            SharedPreferences.Editor editor = MeditationPreference.edit();
                            editor.putInt("audio" + pos, 2);
                            editor.commit();
                        } else {
                            if (isNetworkAvailable(HomeActivity.this)) {
                                folder1.delete();
                                new DownloadFileAsync()
                                        .execute(getResources().getString(R.string.audio_tracks_server_urls));
                                SharedPreferences.Editor editor = MeditationPreference.edit();
                                editor.putInt("audio" + pos, 2);
                                editor.commit();
                            } else {
                                Toast.makeText(HomeActivity.this,
                                        "Please check your Internet connection.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    download_dialog.dismiss();
                }
            });

            not_now_txt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    download_dialog.dismiss();
                }
            });
            download_dialog.show();

        } else if (MeditationPreference.getInt("audio" + pos, 0) == 1) {
            if (isNetworkAvailable(HomeActivity.this)) {
                Intent intent = new Intent(HomeActivity.this, StreamingMp3Player.class);
                intent.putExtra("POSITION", pos);
                intent.putExtra("FILE_NAME_ONLY", "" + getResources().getString(R.string.audio_names));
                intent.putExtra("FILE_NAME_URL", "" + getResources().getString(R.string.audio_tracks_server_urls));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            } else {
                Toast.makeText(HomeActivity.this, "Please check your Internet connection.",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (MeditationPreference.getInt("audio" + pos, 0) == 2) {
            File folder1 = new File(Environment.getExternalStorageDirectory()
                    + getResources().getString(R.string.sd_card_dir_name) + "/"
                    + getResources().getString(R.string.audio_tracks_name));
            Log.i("File size", "" + folder1.length());
            if (folder1.length() >= audio_filessizes) {
                Intent intent = new Intent(HomeActivity.this, AudioPlayerDownloadFile.class);
                intent.putExtra("POSITION", pos);
                intent.putExtra("FILE_NAME_ONLY", "" + getResources().getString(R.string.audio_names));
                intent.putExtra("FILE_NAME", "" + getResources().getString(R.string.audio_tracks_name));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            } else {
                if (isNetworkAvailable(HomeActivity.this)) {
                    folder1.delete();
                    new DownloadFileAsync().execute(getResources().getString(R.string.audio_tracks_server_urls));
                } else {
                    Toast.makeText(HomeActivity.this,
                            "Please check your Internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    
    class DownloadFileAsync extends AsyncTask<String, String, String> {
        Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            try {
                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(new File(
                        Environment.getExternalStorageDirectory()
                                + getResources().getString(R.string.sd_card_dir_name)
                        , getResources().getString(R.string.audio_tracks_name)));

                byte data[] = new byte[1024];
                int count = 0;
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
            progressString = progress[0];

        }

        @Override
        protected void onPostExecute(String unused) {
            if (mProgressDialog.isShowing())
            {
                mProgressDialog.setProgress(0);

                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            }
            if (progressString.equalsIgnoreCase("100"))
            {
                Intent intent = new Intent(HomeActivity.this, AudioPlayerDownloadFile.class);
                intent.putExtra("FILE_NAME_ONLY", "" + getResources().getString(R.string.audio_names));
                intent.putExtra("POSITION", 0);
                intent.putExtra("FILE_NAME", "" + getResources().getString(R.string.audio_tracks_name));
                startActivity(intent);
            }
        }
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS: // we set this to 0
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);

                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }

    }

    
    public boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private boolean checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DOWNLOAD_PDF_PERMISSION);
            return  false;
        }
        else
            return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case DOWNLOAD_PDF_PERMISSION:{
                if(grantResults.length != 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if(mCheckContainer)
                            audiohandler(0);
                        else
                            startActivity(new Intent(HomeActivity.this, EbooksActivity.class));

                    } else {
                        Log.i("HomeActivity", "permission rejected");
                    }
                }
            }
        }
    }
}
