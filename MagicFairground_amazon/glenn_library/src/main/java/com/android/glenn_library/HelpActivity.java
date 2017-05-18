package com.android.glenn_library;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
//import com.helpshift.Helpshift;

public class HelpActivity extends BaseActivity {
    public ImageView bg_image;
    TextView help_description;
    public TextView support_email;
    TextView middle_txt;
    LinearLayout help_how_to_use_app_container;
    TextView help_how_to_use_app_txt;
    LinearLayout help_frequesntly_ask_ques_container;
    TextView help_frequesntly_ask_ques_txt;
    LinearLayout help_feedback_container;
    TextView help_feedback_txt;
    public String folder_name;
    public String appName;
    public String faqUrl = null;
    public static final int DOWNLOAD_PDF_PERMISSION = 11;
    ImageView help_how_to_use_app_img, help_frequesntly_ask_ques_img, help_feedback_img;
    private boolean mGooglePlay = false;
    private String mAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);
        bg_image = (ImageView) findViewById(R.id.bg_image);
        help_description = (TextView) findViewById(R.id.help_description);
        support_email = (TextView) findViewById(R.id.support_email);
        support_email.setText(Html.fromHtml("<u>" + getResources().getString(R.string.support_mail_id) + "</u>"));
        middle_txt = (TextView) findViewById(R.id.middle_txt);
        help_how_to_use_app_container = (LinearLayout) findViewById(R.id.help_how_to_use_app_container);
        help_how_to_use_app_txt = (TextView) findViewById(R.id.help_how_to_use_app_txt);
        help_frequesntly_ask_ques_container = (LinearLayout) findViewById(R.id.help_frequesntly_ask_ques_container);
        help_frequesntly_ask_ques_txt = (TextView) findViewById(R.id.help_frequesntly_ask_ques_txt);
        help_feedback_container = (LinearLayout) findViewById(R.id.help_feedback_container);
        help_feedback_txt = (TextView) findViewById(R.id.help_feedback_txt);

        help_how_to_use_app_img = (ImageView) findViewById(R.id.help_how_to_use_app_img);
        help_frequesntly_ask_ques_img = (ImageView) findViewById(R.id.help_frequesntly_ask_ques_img);
        help_feedback_img = (ImageView) findViewById(R.id.help_feedback_img);


        help_frequesntly_ask_ques_container.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                textcolorhandler();
                help_frequesntly_ask_ques_img.setImageResource(R.drawable.all_ebooks_h);
                help_frequesntly_ask_ques_txt.setTextColor(Color.parseColor("#000000"));
                String url = faqUrl;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        support_email.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","sales@hypnosisaudio.com", null));
                if(mGooglePlay)
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report a problem with "+mAppName+" standalone Android app");
                    else
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report a problem with "+mAppName+" standalone Amazon app");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));


            }
        });

        help_how_to_use_app_container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    readPDF();
                }
            }
        });


        help_feedback_container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                textcolorhandler();
                help_feedback_img.setImageResource(R.drawable.feedback_h);
                help_feedback_txt.setTextColor(Color.parseColor("#000000"));
                HashMap config = new HashMap();
                config.put("requireEmail", true);
                //Helpshift.showConversation(HelpActivity.this, config);
            }
        });

        LinearLayout ll_1 = (LinearLayout) findViewById(R.id.ll_1);
        RelativeLayout.LayoutParams Lp = (RelativeLayout.LayoutParams) ll_1.getLayoutParams();
        Lp.topMargin = (int) Math.round(height * 0.25);
       // Lp.width = (int) Math.round(width * 0.8);
        Lp.leftMargin = (int) Math.round(width * 0.03);
        Lp.rightMargin = (int) Math.round(width * 0.03);
        ll_1.setLayoutParams(Lp);
        LinearLayout ll_2 = (LinearLayout) findViewById(R.id.ll_2);
        RelativeLayout.LayoutParams Lp2 = (RelativeLayout.LayoutParams) ll_2.getLayoutParams();
        //Lp2.width = (int) Math.round(width * 0.8);
        Lp.leftMargin = (int) Math.round(width * 0.03);
        Lp.rightMargin = (int) Math.round(width * 0.03);
        ll_2.setLayoutParams(Lp2);
    }

        public void setPlayStoreTypeAppName(boolean playStoreType, String appName){
            mGooglePlay = playStoreType;
            mAppName = appName;
            Log.i("Google PlayStore" , ""+mGooglePlay);
        }

    @SuppressWarnings("deprecation")
    private void CopyAssets(String filename) {

        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File dir = new File(Environment.getExternalStorageDirectory() + folder_name);
        if (!dir.exists()) {
            dir.mkdir();
        }


        File file = new File(dir, "/" + filename);
        if (!file.exists()) {
            try {
                in = assetManager.open(filename + ".pdf");
                Log.i("file data", "" + in);
                // out = openFileOutput(file.getPath(), Context.MODE_WORLD_READABLE);
                out = new FileOutputStream(file);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }

        }


    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;

        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }


    private void textcolorhandler() {
        help_how_to_use_app_img.setImageResource(R.drawable.ebook);
        help_frequesntly_ask_ques_img.setImageResource(R.drawable.ebook);
        help_feedback_img.setImageResource(R.drawable.feedback);


        try {
            XmlResourceParser xrp = getResources().getXml(R.xml.txtcolor);
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            help_how_to_use_app_txt.setTextColor(csl);
            help_frequesntly_ask_ques_txt.setTextColor(csl);
            help_feedback_txt.setTextColor(csl);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void readPDF() {

        textcolorhandler();
        help_how_to_use_app_img.setImageResource(R.drawable.all_ebooks_h);
        help_how_to_use_app_txt.setTextColor(Color.parseColor("#000000"));

        String filename = "How_To_Use_This_App";
        File existing_file = new File(Environment.getExternalStorageDirectory() + folder_name + "/" + filename);
        existing_file.delete();

        CopyReadAssets(filename);
    }


    private void CopyReadAssets(String pdfFile)
    {
        AssetManager assetManager = getAssets();
        Log.i("file name", "" + pdfFile);
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), pdfFile+".pdf");
        try
        {
            in = assetManager.open(pdfFile+".pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(
                    Uri.parse("file://" + getFilesDir() + "/" + pdfFile + ".pdf"),
                    "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent chooserIntent = Intent.createChooser(intent, "Open File");
            startActivity(chooserIntent);
        } catch (ActivityNotFoundException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(HelpActivity.this);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if(mGooglePlay) {//google playStore pdf download link
                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.adobe.reader&feature=more_from_developer#?t=W251bGwsMSwxLDEwMiwiY29tLmFkb2JlLnJlYWRlciJd"));
                    }else{//amazon pdf download link
                        i.setData(Uri.parse("https://www.amazon.com/Adobe-Acrobat-Reader-Reader-more/dp/B004SD5GZ4/ref=sr_1_1?s=mobile-apps&ie=UTF8&qid=1479549404&sr=1-1&keywords=adobe+pdf+reader"));
                    }
                    startActivity(i);
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.setTitle("No PDF viewer available");
            alert.setMessage("Do you want to download now?");
            alert.show();
        } catch (Exception otherException) {
            Toast.makeText(HelpActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DOWNLOAD_PDF_PERMISSION);
            return false;
        } else
            return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case DOWNLOAD_PDF_PERMISSION:
                if (grantResults.length != 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        readPDF();
                    }
                }
        }

    }
}
