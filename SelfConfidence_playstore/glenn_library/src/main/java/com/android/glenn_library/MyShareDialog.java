package com.android.glenn_library;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyShareDialog{
    public Dialog custom_dialog;
    Context mycontext;
    public LinearLayout facebook_share_container, twitter_share_container, email_share_container;
    public ImageView close_share_dialog_btn;
    String filePath;
    MyTextView facebook_share_txt, twitter_share_txt, email_share_txt;
    private boolean mGooglePlay = false;
    private String mShareText;

    public MyShareDialog(Context context, final String shareText, boolean googlePlay) {
        super();
        mycontext = context;
        custom_dialog = new Dialog(context, R.style.MyCustomTheme);
        mGooglePlay = googlePlay;
        mShareText = shareText;

        custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        custom_dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        custom_dialog.setCancelable(false);
        custom_dialog.setCanceledOnTouchOutside(false);
        custom_dialog.getWindow().setBackgroundDrawableResource(R.drawable.tranperent);
        custom_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        custom_dialog.setContentView(R.layout.share_dialog_layout);
        facebook_share_container = (LinearLayout) custom_dialog.findViewById(R.id.facebook_share_container);
        twitter_share_container = (LinearLayout) custom_dialog.findViewById(R.id.twitter_share_container);
        email_share_container = (LinearLayout) custom_dialog.findViewById(R.id.email_share_container);
        close_share_dialog_btn = (ImageView) custom_dialog.findViewById(R.id.close_share_dialog_btn);
        filePath = "android.resource://com.glennapp.adroid.meditationn/drawable/ic_launcher.png";  //optional //internal storage

        facebook_share_txt = (MyTextView) custom_dialog.findViewById(R.id.facebook_share_txt);
        twitter_share_txt = (MyTextView) custom_dialog.findViewById(R.id.twitter_share_txt);
        email_share_txt = (MyTextView) custom_dialog.findViewById(R.id.email_share_txt);


        try {
            XmlResourceParser xrp = mycontext.getResources().getXml(R.xml.dialog_txtcolor);
            ColorStateList csl = ColorStateList.createFromXml(mycontext.getResources(), xrp);
            facebook_share_txt.setTextColor(csl);
            twitter_share_txt.setTextColor(csl);
            email_share_txt.setTextColor(csl);

        } catch (Exception e) {
            e.printStackTrace();
        }

        facebook_share_container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookShare();
            }
        });

        twitter_share_container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                twitterShare();
            }
        });

        email_share_container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                emailShare();

            }
        });

        close_share_dialog_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public void show() {
        custom_dialog.show();
    }

    public void dismiss() {
        custom_dialog.dismiss();
    }


    private void emailShare() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        setExtraTextToshare(emailIntent);
        mycontext.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        dismiss();
    }

    private void twitterShare() {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        setExtraTextToshare(tweetIntent);
        tweetIntent.setType("text/plain");

        PackageManager packManager = mycontext.getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")
                    || resolveInfo.activityInfo.packageName.contains("twitter")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            mycontext.startActivity(tweetIntent);
        } else {
            Toast.makeText(mycontext, "You don't have twitter application. Please download app to share.", Toast.LENGTH_LONG).show();
        }
        dismiss();
    }

    private void facebookShare() {
        final Intent fbintent = new Intent(android.content.Intent.ACTION_SEND);
        fbintent.setType("text/plain");
        final PackageManager fbpm = mycontext.getPackageManager();
        final List<ResolveInfo> fbmatches = fbpm.queryIntentActivities(fbintent, 0);
        ResolveInfo fbbest = null;
        for (final ResolveInfo info : fbmatches)
            if (info.activityInfo.packageName.endsWith(".facebook") ||
                    info.activityInfo.name.toLowerCase().contains("facebook"))
                fbbest = info;
        if (fbbest != null) {
            fbintent.setClassName(fbbest.activityInfo.packageName, fbbest.activityInfo.name);
            fbintent.putExtra(Intent.EXTRA_SUBJECT, "Hello");
            setExtraTextToshare(fbintent);

            mycontext.startActivity(fbintent);
        } else {
            Toast.makeText(mycontext, "You don't have facebook application. Please download app to share.", Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }

    private void setExtraTextToshare(Intent extraTextToshare) {
        if(mGooglePlay)
            extraTextToshare.putExtra(Intent.EXTRA_TEXT, mShareText + " https://play.google.com/store/apps/details?id=" + mycontext.getPackageName());
        else
            extraTextToshare.putExtra(Intent.EXTRA_TEXT, mShareText + "\n http://www.amazon.com/gp/mas/dl/android?p=" + mycontext.getPackageName());
    }
}
