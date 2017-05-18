package com.Arkenea.GuidedMeditation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.glenn_library.BaseActivity;
import com.android.glenn_library.MyShareDialog;
import com.android.glenn_library.MySocialMediaDialog;
import com.android.glenn_library.ViewWebsiteDialog;

//import com.helpshift.Helpshift;

public class HomeActivity extends BaseActivity implements OnClickListener {
    LinearLayout audio_container, ebook_container, social_container, share_container, review_container, more_container, help_container, website_container;

    ImageView audio_img, ebook_img, social_img, share_img, review_img, more_img, help_img, website_img;

    CustomeTextView audio_txt, ebook_txt, social_txt, share_txt, review_txt, more_txt, help_txt, website_txt;

    public static final int DOWNLOAD_PDF_PERMISSION = 11;

    boolean mCheckContainer,mCheckBookConatainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        audio_container = (LinearLayout) findViewById(R.id.audio_container);
        ebook_container = (LinearLayout) findViewById(R.id.ebook_container);
        social_container = (LinearLayout) findViewById(R.id.social_container);
        share_container = (LinearLayout) findViewById(R.id.share_container);
        review_container = (LinearLayout) findViewById(R.id.review_container);
        more_container = (LinearLayout) findViewById(R.id.more_container);
        help_container = (LinearLayout) findViewById(R.id.help_container);
        website_container = (LinearLayout) findViewById(R.id.website_container);

        audio_img = (ImageView) findViewById(R.id.audio_img);
        ebook_img = (ImageView) findViewById(R.id.ebook_img);
        social_img = (ImageView) findViewById(R.id.social_img);
        share_img = (ImageView) findViewById(R.id.share_img);
        review_img = (ImageView) findViewById(R.id.review_img);
        more_img = (ImageView) findViewById(R.id.more_img);
        help_img = (ImageView) findViewById(R.id.help_img);
        website_img = (ImageView) findViewById(R.id.website_img);


        audio_txt = (CustomeTextView) findViewById(R.id.audio_txt);
        ebook_txt = (CustomeTextView) findViewById(R.id.ebook_txt);
        social_txt = (CustomeTextView) findViewById(R.id.social_txt);
        share_txt = (CustomeTextView) findViewById(R.id.share_txt);
        review_txt = (CustomeTextView) findViewById(R.id.review_txt);
        more_txt = (CustomeTextView) findViewById(R.id.more_txt);
        help_txt = (CustomeTextView) findViewById(R.id.help_txt);
        website_txt = (CustomeTextView) findViewById(R.id.website_txt);

        audio_container.setOnClickListener(this);
        ebook_container.setOnClickListener(this);
        social_container.setOnClickListener(this);
        share_container.setOnClickListener(this);
        review_container.setOnClickListener(this);
        more_container.setOnClickListener(this);
        help_container.setOnClickListener(this);
        website_container.setOnClickListener(this);

        LinearLayout homeActivity_ll_1 = (LinearLayout) findViewById(R.id.homeActivity_ll_1);
        RelativeLayout.LayoutParams Lp = (RelativeLayout.LayoutParams) homeActivity_ll_1.getLayoutParams();
        Lp.topMargin = (int) Math.round(height * 0.26);
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
                if (checkPermission())
                    startActivity(new Intent(HomeActivity.this, AudioTrackActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.ebook_container:
                mCheckContainer = false;
                ebook_img.setImageResource(R.drawable.all_ebooks_h);
                ebook_txt.setTextColor(Color.parseColor("#000000"));
                if (checkPermission())
                    startActivity(new Intent(HomeActivity.this, EbooksActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.social_container:
                social_img.setImageResource(R.drawable.fb_h);
                social_txt.setTextColor(Color.parseColor("#000000"));
                MySocialMediaDialog social_dialog = new MySocialMediaDialog(HomeActivity.this);
                social_dialog.show();
                break;

            case R.id.share_container:
                share_img.setImageResource(R.drawable.share_this_app_h);
                share_txt.setTextColor(Color.parseColor("#000000"));
                String share_massage = "Download Guided Meditation by Glenn Harrold.";
                MyShareDialog share_dialog = new MyShareDialog(HomeActivity.this, share_massage,true);
                share_dialog.show();
                break;

            case R.id.review_container:
                review_img.setImageResource(R.drawable.review_this_app_h);
                review_txt.setTextColor(Color.parseColor("#000000"));
                Intent i = new Intent(Intent.ACTION_VIEW);
                //i.setData(Uri.parse(("amzn://apps/android?p=com.Arkenea.GuidedMeditation")));
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                startActivity(i);
                break;

            case R.id.more_container:
                more_img.setImageResource(R.drawable.more_h);
                more_txt.setTextColor(Color.parseColor("#000000"));
                //String uri = String.format("amzn://apps/android?p=com.Arkenea.GuidedMeditation&showAll=1");
                String uri = String.format("https://play.google.com/store/apps/" + "developer?id=Diviniti+Publishing+Ltd");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                break;

            case R.id.help_container:
                help_img.setImageResource(R.drawable.all_ebooks_h);
                help_txt.setTextColor(Color.parseColor("#000000"));
                startActivity(new Intent(HomeActivity.this, HelpMeditation.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.website_container:
                website_img.setImageResource(R.drawable.website_h);
                website_txt.setTextColor(Color.parseColor("#000000"));
                ViewWebsiteDialog website_dialog = new ViewWebsiteDialog(HomeActivity.this);
                website_dialog.show();
                break;

            default:
                break;
        }

    }

    private void textcolorhandler() {
        audio_img.setImageResource(R.drawable.audio);
        ebook_img.setImageResource(R.drawable.ebook);
        social_img.setImageResource(R.drawable.fb);
        share_img.setImageResource(R.drawable.share);
        review_img.setImageResource(R.drawable.review);
        more_img.setImageResource(R.drawable.more);
        help_img.setImageResource(R.drawable.ebook);
        website_img.setImageResource(R.drawable.website);


        try {
            XmlResourceParser xrp = getResources().getXml(R.xml.txtcolor);
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            audio_txt.setTextColor(csl);
            ebook_txt.setTextColor(csl);
            social_txt.setTextColor(csl);
            share_txt.setTextColor(csl);
            review_txt.setTextColor(csl);
            more_txt.setTextColor(csl);
            help_txt.setTextColor(csl);
            website_txt.setTextColor(csl);
        } catch (Exception e) {
            e.printStackTrace();
        }

		/*audio_txt.setTextColor(Color.parseColor("#ffffff"));
		.setTextColor(Color.parseColor("#ffffff"));
		social_txt.setTextColor(Color.parseColor("#ffffff"));
		share_txt.setTextColor(Color.parseColor("#ffffff"));
		review_txt.setTextColor(Color.parseColor("#ffffff"));
		more_txt.setTextColor(Color.parseColor("#ffffff"));
		help_txt.setTextColor(Color.parseColor("#ffffff"));*/
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
                        if (mCheckContainer) {
                            startActivity(new Intent(HomeActivity.this, AudioTrackActivity.class));
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                                startActivity(new Intent(HomeActivity.this, EbooksActivity.class));
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                        }
                    }
                }
        }

    }

}
