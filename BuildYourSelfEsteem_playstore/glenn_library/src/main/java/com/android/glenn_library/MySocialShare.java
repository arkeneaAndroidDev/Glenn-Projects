
package com.android.glenn_library;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MySocialShare {

    public Dialog custom_dialog;
    Context mycontext;
    public LinearLayout facebook_share_container, twitter_share_container;
    public ImageView close_dialog_btn;
    MyTextView facebook_social_txt, twitter_social_txt;
    EditText first_name_edt;
    EditText last_name_edt;
    EditText email_edt;
    int contact_id = 0;
    LoadingDialog loading;
    String fb_txt,twitt_txt;

    public MySocialShare(Context context, String fb_txt,String twitt_txt) {
        super();
        this.fb_txt = fb_txt;
        this.twitt_txt = twitt_txt;
        mycontext = context;
        custom_dialog = new Dialog(context);
        loading = new LoadingDialog(mycontext);
        custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        custom_dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        custom_dialog.setCancelable(false);
        custom_dialog.setCanceledOnTouchOutside(false);
        custom_dialog.getWindow().setBackgroundDrawableResource(R.drawable.tranperent);
        custom_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        custom_dialog.setContentView(R.layout.dialog_connect);
        facebook_share_container = (LinearLayout) custom_dialog
                .findViewById(R.id.facebook_social_container);
        twitter_share_container = (LinearLayout) custom_dialog
                .findViewById(R.id.twitter_social_container);
        close_dialog_btn = (ImageView) custom_dialog.findViewById(R.id.close_dialog_btn);

        facebook_social_txt = (MyTextView) custom_dialog.findViewById(R.id.facebook_social_txt);
        twitter_social_txt = (MyTextView) custom_dialog.findViewById(R.id.twitter_social_txt);
        facebook_social_txt.setText(fb_txt);
        twitter_social_txt.setText(twitt_txt);
        ImageView fb_image = (ImageView) custom_dialog.findViewById(R.id.facebook_social_img);
        ImageView twitt_image = (ImageView) custom_dialog.findViewById(R.id.twitter_social_img);
        if(fb_txt.contains("Connect")){
            fb_image.setImageResource(R.drawable.fb_social_icon);
            twitt_image.setImageResource(R.drawable.fb_social_icon);
        }
        else {
            fb_image.setImageResource(R.drawable.twitter_social_icon);
            twitt_image.setImageResource(R.drawable.twitter_social_icon);
        }

        try {
            XmlResourceParser xrp = mycontext.getResources().getXml(R.xml.dialog_txtcolor);
            ColorStateList csl = ColorStateList.createFromXml(mycontext.getResources(), xrp);
            facebook_social_txt.setTextColor(csl);
            twitter_social_txt.setTextColor(csl);

        } catch (Exception e) {
            e.printStackTrace();
        }

        facebook_share_container.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String url;
                if(MySocialShare.this.fb_txt.contains("Connect")){
                    url = "https://facebook.com/ShazzieFB";
                }
                else {
                    url = "https://twitter.com/Shazzie?lang=en";
                }
               
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mycontext.startActivity(i);
                dismiss();

            }
        });

        twitter_share_container.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String url;
                if(MySocialShare.this.fb_txt.contains("Connect")){
                    url = "https://facebook.com/AnimaMusic";
                }
                else {
                    url = "https://twitter.com/AnimaCreations";
                }
               
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mycontext.startActivity(i);
                dismiss();
            }
        });

      
        close_dialog_btn.setOnClickListener(new OnClickListener() {

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

 
}
