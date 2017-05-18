
package com.Arkenea.TheHealingWhiteLight;

import android.os.Bundle;
import android.view.WindowManager;

import com.android.glenn_library.HelpActivity;

public class HelpMeditation extends HelpActivity {
    
    public static final String URL = "http://www.glennharrold.com/faqs/index.html";
            
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        bg_image.setBackgroundResource(R.drawable.backgroundimage);
        folder_name = getResources().getString(R.string.sd_card_dir_name);
        appName =getResources().getString(R.string.app_name);
        support_email.setTextColor(getResources().getColor(R.color.email_link_color));
        faqUrl = URL;
        setPlayStoreTypeAppName(false, appName);
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
