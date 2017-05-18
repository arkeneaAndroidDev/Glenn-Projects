package com.Arkenea.GuidedMeditation;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SplashScreen extends Activity{
	private final static int NUMBER_OF_LAUNCH = 5;//Min number of launches
	private final static String APP_TITLE = "Assert Yourself With Confidence";// App Name
	private final static String APP_PKGNAME = "com.Arkenea.GuidedMeditation";// Package Name
	static boolean backFromLink = false;
	static boolean rateApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		final Intent i = new Intent(SplashScreen.this, HomeActivity.class);


		new Handler().postDelayed(new Runnable() {
			// Using handler with postDelayed called runnable run method
			@Override
			public void run() {
				app_launched();
			}
		}, 2 * 1000);

		//finish();
	}

	public void app_launched() {
		Intent i = new Intent(SplashScreen.this, HomeActivity.class);
		SharedPreferences prefs = getSharedPreferences("apprater", 0);
		if (prefs.getBoolean("dontshowagain", false)) {
		}

		SharedPreferences.Editor editor = prefs.edit();

		// Increment launch counter
		long launch_count = prefs.getLong("launch_count", 0) + 1;
		editor.putLong("launch_count", launch_count);

		// Wait at least n time before opening
		if(launch_count > NUMBER_OF_LAUNCH){
			if(rateApp == true){
				startActivity(i);
				finish();
			}else{
				showReviewDialog(SplashScreen.this, editor);
				editor.putLong("launch_count", 0);
			}
		}else{
			startActivity(i);
			finish();
		}
		editor.commit();
	}

	public  void showReviewDialog(final Context mContext, final SharedPreferences.Editor editor) {
		final Intent i = new Intent(mContext, HomeActivity.class);
		final Dialog dialog = new Dialog(mContext);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialog.setContentView(R.layout.rateappdialog);

		Button mReview = (Button) dialog.findViewById(R.id.review);
		Button mRemind = (Button) dialog.findViewById(R.id.remind);
		Button mNothanks = (Button) dialog.findViewById(R.id.nothanks);

		mReview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				backFromLink = true;
				rateApp = true;
				mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + APP_PKGNAME)));
				//mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("amzn://apps/android?p=com.Arkenea.GuidedMeditation")));
				dialog.dismiss();
			}
		});

		mRemind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				mContext.startActivity(i);
				SplashScreen.this.finish();
			}
		});

		mNothanks.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rateApp = true;
				if (editor != null) {
					editor.putBoolean("dontshowagain", false);
					editor.commit();
				}
				dialog.dismiss();
				mContext.startActivity(i);
				SplashScreen.this.finish();
			}
		});

		dialog.show();
		Window window = dialog.getWindow();
		window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(backFromLink == true){
			Intent i = new Intent(SplashScreen.this, HomeActivity.class);
			startActivity(i);
			backFromLink = false;
			finish();
		}
	}

}
