
package com.arkenea.magicfairground;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.glenn_library.HelpActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

public class HelpMeditation extends HelpActivity {

    public static final String URL = "http://www.glennharrold.com/faqs/index.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bg_image.setBackgroundResource(R.drawable.backgroundimage);
        appName="Magic Fairground";
        folder_name = getResources().getString(R.string.sd_card_dir_name);
        faqUrl = URL;
        setPlayStoreTypeAppName(false, appName);

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }






    /*public static final String URL = "http://www.glennharrold.com/faqs/index.html";
            
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        bg_image.setBackgroundResource(R.drawable.backgroundimge);
        folder_name = getResources().getString(R.string.sd_card_dir_name);
        appName =getResources().getString(R.string.app_name);
        support_email.setTextColor(getResources().getColor(R.color.email_link_color));
        layout_help.setBackgroundResource(R.drawable.box);
        layout_one.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()), 
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()), 0, 0);
        layout_two.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()),
                0, 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_two.getLayoutParams();
        layoutParams.setMargins((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()), 0);
        layout_two.setLayoutParams(layoutParams);
        faqUrl = URL;
        
    }*/

//    public ImageView bg_image;
//    TextView help_description;
//    public TextView support_email;
//    TextView middle_txt;
//    LinearLayout help_how_to_use_app_container;
//    TextView help_how_to_use_app_txt;
//    LinearLayout help_frequesntly_ask_ques_container;
//    TextView help_frequesntly_ask_ques_txt;
//    LinearLayout help_feedback_container;
//    TextView help_feedback_txt;
//    public String folder_name;
//    public String appName;
//    public String faqUrl = null;
//    public static final int DOWNLOAD_PDF_PERMISSION = 11;
//    ImageView help_how_to_use_app_img, help_frequesntly_ask_ques_img, help_feedback_img;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onCreate(savedInstanceState);
//        setContentView(com.android.glenn_library.R.layout.help_layout);
//        bg_image = (ImageView) findViewById(com.android.glenn_library.R.id.bg_image);
//        help_description = (TextView) findViewById(com.android.glenn_library.R.id.help_description);
//        support_email = (TextView) findViewById(com.android.glenn_library.R.id.support_email);
//        support_email.setText(Html.fromHtml("<u>" + getResources().getString(com.android.glenn_library.R.string.support_mail_id) + "</u>"));
//        middle_txt = (TextView) findViewById(com.android.glenn_library.R.id.middle_txt);
//        help_how_to_use_app_container = (LinearLayout) findViewById(com.android.glenn_library.R.id.help_how_to_use_app_container);
//        help_how_to_use_app_txt = (TextView) findViewById(com.android.glenn_library.R.id.help_how_to_use_app_txt);
//        help_frequesntly_ask_ques_container = (LinearLayout) findViewById(com.android.glenn_library.R.id.help_frequesntly_ask_ques_container);
//        help_frequesntly_ask_ques_txt = (TextView) findViewById(com.android.glenn_library.R.id.help_frequesntly_ask_ques_txt);
//        help_feedback_container = (LinearLayout) findViewById(com.android.glenn_library.R.id.help_feedback_container);
//        help_feedback_txt = (TextView) findViewById(com.android.glenn_library.R.id.help_feedback_txt);
//
//        help_how_to_use_app_img = (ImageView) findViewById(com.android.glenn_library.R.id.help_how_to_use_app_img);
//        help_frequesntly_ask_ques_img = (ImageView) findViewById(com.android.glenn_library.R.id.help_frequesntly_ask_ques_img);
//        help_feedback_img = (ImageView) findViewById(com.android.glenn_library.R.id.help_feedback_img);
//
//
//        help_frequesntly_ask_ques_container.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                textcolorhandler();
//                help_frequesntly_ask_ques_img.setImageResource(com.android.glenn_library.R.drawable.all_ebooks_h);
//                help_frequesntly_ask_ques_txt.setTextColor(Color.parseColor("#000000"));
//                String url = faqUrl;
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//
//            }
//        });
//
//
//        support_email.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                //startApplication("com.google.android.gm");
//                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                final PackageManager pm = getPackageManager();
//                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
//                ResolveInfo best = null;
//                for (final ResolveInfo info : matches)
//                    if (info.activityInfo.packageName.endsWith(".gm") ||
//                            info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
//                if (best != null)
//                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Report a problem with Assert Yourself standalone and Android app");
//                //intent.putExtra(Intent.EXTRA_SUBJECT, "Report a problem with Assert Yourself standalone and Amazon app");
//                intent.setData(Uri.parse("sales@hypnosisaudio.com"));
//                intent.putExtra(Intent.EXTRA_TEXT, "");
//                startActivity(intent);
//
//            }
//        });
//
//        help_how_to_use_app_container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkPermission()) {
//                    readPDF();
//                }
//            }
//        });
//
//
//        help_feedback_container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textcolorhandler();
//                help_feedback_img.setImageResource(com.android.glenn_library.R.drawable.feedback_h);
//                help_feedback_txt.setTextColor(Color.parseColor("#000000"));
//                HashMap config = new HashMap();
//                config.put("requireEmail", true);
//
//                //Helpshift.showConversation(HelpActivity.this, config);
//            }
//        });
//
//    }
//
//
//    @SuppressWarnings("deprecation")
//    private void CopyAssets(String filename) {
//
//        AssetManager assetManager = getAssets();
//
//        InputStream in = null;
//        OutputStream out = null;
//        File dir = new File(Environment.getExternalStorageDirectory() + folder_name);
//        if (!dir.exists()) {
//            dir.mkdir();
//        }
//
//
//        File file = new File(dir, "/" + filename);
//        if (!file.exists()) {
//            try {
//                in = assetManager.open(filename + ".pdf");
//                Log.i("file data", "" + in);
//                // out = openFileOutput(file.getPath(), Context.MODE_WORLD_READABLE);
//                out = new FileOutputStream(file);
//                copyFile(in, out);
//                in.close();
//                in = null;
//                out.flush();
//                out.close();
//                out = null;
//            } catch (Exception e) {
//                Log.e("tag", e.getMessage());
//            }
//
//        }
//
//
//    }
//
//    private void copyFile(InputStream in, OutputStream out) throws IOException {
//        byte[] buffer = new byte[1024];
//        int read;
//
//        while ((read = in.read(buffer)) != -1) {
//            out.write(buffer, 0, read);
//        }
//    }
//
//
//    private void textcolorhandler() {
//        help_how_to_use_app_img.setImageResource(com.android.glenn_library.R.drawable.ebook);
//        help_frequesntly_ask_ques_img.setImageResource(com.android.glenn_library.R.drawable.ebook);
//        help_feedback_img.setImageResource(com.android.glenn_library.R.drawable.feedback);
//
//
//        try {
//            XmlResourceParser xrp = getResources().getXml(R.xml.txtcolor);
//            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
//            help_how_to_use_app_txt.setTextColor(csl);
//            help_frequesntly_ask_ques_txt.setTextColor(csl);
//            help_feedback_txt.setTextColor(csl);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void readPDF() {
//
//        textcolorhandler();
//        help_how_to_use_app_img.setImageResource(com.android.glenn_library.R.drawable.all_ebooks_h);
//        help_how_to_use_app_txt.setTextColor(Color.parseColor("#000000"));
//
//        String filename = "How_To_Use_This_App";
//        File existing_file = new File(Environment.getExternalStorageDirectory() + folder_name + "/" + filename);
//        existing_file.delete();
//        CopyAssets(filename);
//        try {
//            File file1 = new File(Environment.getExternalStorageDirectory() + folder_name + "/" + filename);
//            Uri path1 = Uri.fromFile(file1);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(
//                    path1,
//                    "application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            AlertDialog.Builder alert = new AlertDialog.Builder(HelpActivity.this);
//            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                public void onClick(DialogInterface dialog, int which) {
//
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.adobe.reader&feature=more_from_developer#?t=W251bGwsMSwxLDEwMiwiY29tLmFkb2JlLnJlYWRlciJd"));
//                    startActivity(i);
//                }
//            });
//            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            alert.setTitle("No PDF viewer available");
//            alert.setMessage("Do you want to download now?");
//            alert.show();
//        } catch (Exception otherException) {
//            Toast.makeText(HelpActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    private boolean checkPermission() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DOWNLOAD_PDF_PERMISSION);
//            return false;
//        } else
//            return true;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case DOWNLOAD_PDF_PERMISSION:
//                if (grantResults.length != 0) {
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        readPDF();
//                    }
//                }
//        }
//
//    }
}
