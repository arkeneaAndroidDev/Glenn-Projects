package com.android.glenn_library;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by admin on 09-08-2016.
 */
public class ViewWebsiteDialog {

    public Dialog custom_dialog;
    Context mycontext;
    public LinearLayout diviniti_publication_container, glenn_harrold_container;
    public ImageView close_share_dialog_btn;
    String filePath;
    MyTextView diviniti_publication_txt, glenn_harrold_txt;

    public ViewWebsiteDialog(Context context) {
        super();
        mycontext = context;
        custom_dialog = new Dialog(context, R.style.MyCustomTheme);
        custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        custom_dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        custom_dialog.setCancelable(false);
        custom_dialog.setCanceledOnTouchOutside(false);
        custom_dialog.getWindow().setBackgroundDrawableResource(R.drawable.tranperent);
        custom_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        custom_dialog.setContentView(R.layout.website_dialog_layout);
        diviniti_publication_container = (LinearLayout) custom_dialog.findViewById(R.id.diviniti_publication_container);
        glenn_harrold_container = (LinearLayout) custom_dialog.findViewById(R.id.glenn_harrold_container);
        close_share_dialog_btn = (ImageView) custom_dialog.findViewById(R.id.close_share_dialog_btn);
        filePath = "android.resource://com.glennapp.adroid.meditationn/drawable/ic_launcher.png";  //optional //internal storage

        diviniti_publication_txt = (MyTextView) custom_dialog.findViewById(R.id.website_diviniti);
        glenn_harrold_txt = (MyTextView) custom_dialog.findViewById(R.id.website_glenn);

        try {
            XmlResourceParser xrp = mycontext.getResources().getXml(R.xml.dialog_txtcolor);
            ColorStateList csl = ColorStateList.createFromXml(mycontext.getResources(), xrp);
            diviniti_publication_txt.setTextColor(csl);
            glenn_harrold_txt.setTextColor(csl);

        } catch (Exception e) {
            e.printStackTrace();
        }

        diviniti_publication_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.hypnosisaudio.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mycontext.startActivity(i);
                dismiss();
            }
        });

        glenn_harrold_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.glennharrold.com/index.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mycontext.startActivity(i);
                dismiss();
            }
        });

        close_share_dialog_btn.setOnClickListener(new View.OnClickListener() {

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

