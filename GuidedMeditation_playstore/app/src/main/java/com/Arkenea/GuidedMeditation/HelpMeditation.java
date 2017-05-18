package com.Arkenea.GuidedMeditation;
import android.os.Bundle;

import com.android.glenn_library.HelpActivity;

public class HelpMeditation extends HelpActivity{
	public static final String URL = "http://www.glennharrold.com/faqs/index.html";
@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	bg_image.setBackgroundResource(R.drawable.backgroundimage);
	folder_name = getResources().getString(R.string.sd_card_dir_name);
	faqUrl = URL;
	setPlayStoreTypeAppName(true, appName);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
}
