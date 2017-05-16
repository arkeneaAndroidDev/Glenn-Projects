package com.Arkenea.GuidedMeditation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.glenn_library.BaseActivity;
import com.android.glenn_library.AllBooksActivity;
import com.android.glenn_library.classes.Ebookdata;






public class AudioTrackActivity extends BaseActivity{
	ImageView ebook_bg_image;
	private ProgressDialog mProgressDialog;
	final int DIALOG_DOWNLOAD_PROGRESS = 0;
	int lenghtOfFile;
	String progressString="";
	int track_position;
	ArrayList<Ebookdata> ebook_list;
	ListView ebook_list_view;
	ebookListAdapter adapter;
	SharedPreferences MeditationPreference, MeditationPreference_loop;
	String[] audio_tracks_name, audio_names;
	String[] audio_tracks_serever_urls;
	long[] filessizes = {0, 41596500, 18601800};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allbooks_layout);
		ebook_bg_image = (ImageView) findViewById(R.id.ebook_bg_image);
		ebook_list_view = (ListView) findViewById(R.id.ebook_list_view);
		ebook_bg_image.setBackgroundResource(R.drawable.backgroundimage);
		ebook_list = new ArrayList<Ebookdata>();
		
		Resources res = getResources();
		audio_names= res.getStringArray(R.array.audio_names);
		audio_tracks_name = res.getStringArray(R.array.audio_tracks_name);
		audio_tracks_serever_urls = res.getStringArray(R.array.audio_tracks_server_urls);
		for(int i = 0; i< audio_names.length;i++){
			ebook_list.add(new Ebookdata(""+audio_names[i].trim(), ""+audio_tracks_name[i].trim(), false));
		}
		
						adapter = new ebookListAdapter();
						ebook_list_view.setAdapter(adapter);
						MeditationPreference = getSharedPreferences("MEDITATIONPREFERENCES", MODE_PRIVATE);
						MeditationPreference_loop = getSharedPreferences("MEDITATIONPREFERENCES_LOOP", MODE_PRIVATE);
						if(MeditationPreference.getString("DATA", "NO_DATA").equalsIgnoreCase("NO_DATA")){
							SharedPreferences.Editor editor= MeditationPreference.edit();
							editor.putString("DATA", "DATA_PRESENT");
							editor.putInt("audio1", 0);
							editor.putInt("audio2", 0);
							editor.commit();
							
							SharedPreferences.Editor editor_loop= MeditationPreference_loop.edit();
							editor_loop.putInt("audio1", 1);
							editor_loop.putInt("audio2", 1);
							editor_loop.commit();
						}

		RelativeLayout.LayoutParams Lp = (RelativeLayout.LayoutParams) ebook_list_view.getLayoutParams();
		Lp.topMargin = (int) Math.round(height * 0.26);
		Lp.width = (int) Math.round(width * 0.6);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
public class ebookListAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return ebook_list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View v = convertView;
			try {
				if (v == null) {
					LayoutInflater vi;
					vi = LayoutInflater.from(AudioTrackActivity.this);
					v = vi.inflate(
							R.layout.ebook_item_layout, null);
				}
				LinearLayout purchase_history_list_item_container = (LinearLayout) v.findViewById(R.id.ebook_list_item_container);
				ImageView ebook_list_item_icon = (ImageView) v.findViewById(R.id.ebook_list_item_icon);
				TextView purchase_history_list_item_order_no_txt = (TextView) v.findViewById(R.id.ebook_list_item_txt);
				purchase_history_list_item_order_no_txt.setText(""+ebook_list.get(position).getBook_name());
				
				
				if(position == 0){
				purchase_history_list_item_container.setPadding(0, 0, 0, 10);
				}else{
					purchase_history_list_item_container.setPadding(50, 0, 0, 0);
				}
					if(ebook_list.get(position).isSelected()){
						
							ebook_list_item_icon.setImageResource(R.drawable.audio_h);
							purchase_history_list_item_order_no_txt.setTextColor(Color.parseColor("#000000"));
						
					}else{
						ebook_list_item_icon.setImageResource(R.drawable.audio);
						purchase_history_list_item_order_no_txt.setTextColor(Color.parseColor("#ffffff"));
						try {  
				    		XmlResourceParser xrp = getResources().getXml(R.xml.txtcolor);
				        	ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);  
				        	purchase_history_list_item_order_no_txt.setTextColor(csl);  
				        
				    	} catch (Exception e) {  
				    		e.printStackTrace();
				    	}
						
					}
					
					
				
				purchase_history_list_item_container.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(position != 0 ){
						track_position = position;
						switch (position) {
						case 1:
							audiohandler(position);
							break;
						case 2:
							audiohandler(position);
							break;
						
						default:
							break;
						}
					}
						for(int i = 0; i < ebook_list.size(); i++){
			        		ebook_list.get(i).setSelected(false);
			        	}
			        	
			        	ebook_list.get(position).setSelected(true);
			        	adapter.notifyDataSetChanged();
					
						
					}

				
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
				return v;
		}
	}

/*private void resetaudiopreferences() {
	final Dialog logout_dialog = new Dialog(AudioTrackActivity.this);
	
	 logout_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 logout_dialog.getWindow().setSoftInputMode(
		 WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
	 logout_dialog.getWindow().setBackgroundDrawableResource(R.drawable.tranperent);
	 //logout_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	 logout_dialog.setContentView(R.layout.audio_conformation_dialog);
	 CustomeTextView stream_dialog_txt = (CustomeTextView) logout_dialog.findViewById(R.id.stream_dialog_txt);
	 CustomeTextView stream_txt = (CustomeTextView) logout_dialog.findViewById(R.id.stream_txt);
	 CustomeTextView download_txt = (CustomeTextView) logout_dialog.findViewById(R.id.download_txt);
	 stream_dialog_txt.setText("This will clear your audio settings. Are you sure you want to proceed ?");
	 stream_txt.setText("Yes");
	 download_txt.setText("No");
	 XmlResourceParser xrp = getResources().getXml(R.drawable.dialog_txtcolor);  
    	try {  
    	    ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);  
    	    stream_txt.setTextColor(csl); 
    	    download_txt.setTextColor(csl);  
    	} catch (Exception e) { 
    		e.printStackTrace();
    	}
    	
    	
    	stream_txt.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			SharedPreferences.Editor editor= MeditationPreference.edit();
			editor.putString("DATA", "DATA_PRESENT");
			editor.putInt("audio1", 0);
			editor.putInt("audio2", 0);
			editor.putInt("audio3", 0);
			editor.commit();
			logout_dialog.dismiss();
		}
    	});
	 
    	download_txt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				logout_dialog.dismiss();
			}
		});
	 logout_dialog.show();
}*/

private void audiohandler(final int position) {
	if(MeditationPreference.getInt("audio"+position, 0) == 0){
		/*AlertDialog.Builder alert= new AlertDialog.Builder(AudioTrackActivity.this);
		alert.setPositiveButton("Stream",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(isNetworkAvailable(AudioTrackActivity.this)){
				SharedPreferences.Editor editor= MeditationPreference.edit();
				editor.putInt("audio"+position, 1);
				editor.commit();
				Intent intent= new Intent(AudioTrackActivity.this, StreamingMp3Player.class);
				intent.putExtra("POSITION", position);
				intent.putExtra("FILE_NAME_ONLY", ""+audio_names[position]);
				intent.putExtra("FILE_NAME_URL", ""+audio_tracks_serever_urls[position]);
				startActivity(intent);
				//retriveFromSharedPrefrences(AllOptionActivity.this);
				}else{
					Toast.makeText(AudioTrackActivity.this, "Please check your Internet connection.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		alert.setNegativeButton("Download",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				File folder = new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name));
				Log.i("foldername", ""+folder);
				if (!folder.exists()) {
					folder.mkdir();
				}
				File folder1 = new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name)+"/"+audio_tracks_name[position]);
				Log.i("folder1name", ""+folder);
				if (!folder1.exists()) {
					if(isNetworkAvailable(AudioTrackActivity.this)){
					new DownloadFileAsync().execute(audio_tracks_serever_urls[position]);
					SharedPreferences.Editor editor= MeditationPreference.edit();
					editor.putInt("audio"+position, 2);
					editor.commit();
					}else{
						Toast.makeText(AudioTrackActivity.this, "Please check your Internet connection.", Toast.LENGTH_SHORT).show();
					}
				}else{
					File folder1_ = new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name)+"/"+audio_tracks_name[position]);
					Log.i("File size", ""+folder1.length());
					if(folder1_.length() >= filessizes[position]){
					Intent intent= new Intent(AudioTrackActivity.this, AudioPlayerDownloadFile.class);
					intent.putExtra("POSITION", position);
					intent.putExtra("FILE_NAME_ONLY", ""+audio_names[position]);
					intent.putExtra("FILE_NAME", ""+audio_tracks_name[position]);
					startActivity(intent);
					}else{
						if(isNetworkAvailable(AudioTrackActivity.this)){
						folder1.delete();
						new DownloadFileAsync().execute(audio_tracks_serever_urls[position]);
						SharedPreferences.Editor editor= MeditationPreference.edit();
						editor.putInt("audio"+position, 2);
						editor.commit();
						}else{
							Toast.makeText(AudioTrackActivity.this, "Please check your Internet connection.", Toast.LENGTH_SHORT).show();
						}
					}
				}
				
			}
		});
		alert.setTitle("Select how you would like to receive the audio");
		alert.show();*/
		
		
		
		
		
		final Dialog download_dialog = new Dialog(AudioTrackActivity.this);
		
		download_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		download_dialog.getWindow().setSoftInputMode(
			 WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			
		download_dialog.getWindow().setBackgroundDrawableResource(R.drawable.tranperent);
		 //logout_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		download_dialog.setContentView(R.layout.audio_conformation_dialog);
		 CustomeTextView download_dialog_txt = (CustomeTextView) download_dialog.findViewById(R.id.download_dialog_txt);
		 CustomeTextView download_txt = (CustomeTextView) download_dialog.findViewById(R.id.download_txt);
		 CustomeTextView not_now_txt = (CustomeTextView) download_dialog.findViewById(R.id.not_now_txt);
		 download_dialog_txt.setText("The audio needs to be downloaded the first time to play. Continue to download now.");
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
				File folder = new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name));
				Log.i("foldername", ""+folder);
				if (!folder.exists()) {
					folder.mkdir();
				}
				File folder1 = new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name)+"/"+audio_tracks_name[position]);
				Log.i("folder1name", ""+folder);
				if (!folder1.exists()) {
					if(isNetworkAvailable(AudioTrackActivity.this)){
					new DownloadFileAsync().execute(audio_tracks_serever_urls[position]);
					SharedPreferences.Editor editor= MeditationPreference.edit();
					editor.putInt("audio"+position, 2);
					editor.commit();
					}else{
						Toast.makeText(AudioTrackActivity.this, "Please check your Internet connection.", Toast.LENGTH_SHORT).show();
					}
				}else{
					File folder1_ = new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name)+"/"+audio_tracks_name[position]);
					Log.i("File size", ""+folder1.length());
					if(folder1_.length() >= filessizes[position]){
					Intent intent= new Intent(AudioTrackActivity.this, AudioPlayerDownloadFile.class);
					intent.putExtra("POSITION", position);
					intent.putExtra("FILE_NAME_ONLY", ""+audio_names[position]);
					intent.putExtra("FILE_NAME", ""+audio_tracks_name[position]);
					startActivity(intent);
					SharedPreferences.Editor editor= MeditationPreference.edit();
					editor.putInt("audio"+position, 2);
					editor.commit();
					}else{
						if(isNetworkAvailable(AudioTrackActivity.this)){
						folder1.delete();
						new DownloadFileAsync().execute(audio_tracks_serever_urls[position]);
						SharedPreferences.Editor editor= MeditationPreference.edit();
						editor.putInt("audio"+position, 2);
						editor.commit();
						}else{
							Toast.makeText(AudioTrackActivity.this, "Please check your Internet connection.", Toast.LENGTH_SHORT).show();
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
		
		
	}else if(MeditationPreference.getInt("audio"+position, 0) == 1){
		if(isNetworkAvailable(AudioTrackActivity.this)){
		Intent intent = new Intent(AudioTrackActivity.this, StreamingMp3Player.class);
		intent.putExtra("POSITION", position);
		intent.putExtra("FILE_NAME_ONLY", ""+audio_names[position]);
		intent.putExtra("FILE_NAME_URL", ""+audio_tracks_serever_urls[position]);
		startActivity(intent);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}else{
			Toast.makeText(AudioTrackActivity.this, "Please check your Internet connection.", Toast.LENGTH_SHORT).show();
		}
	}else if(MeditationPreference.getInt("audio"+position, 0) == 2){
		File folder1 = new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name)+"/"+audio_tracks_name[position]);
		Log.i("File size", ""+folder1.length());
		if(folder1.length() >= filessizes[position]){
		Intent intent= new Intent(AudioTrackActivity.this, AudioPlayerDownloadFile.class);
		intent.putExtra("POSITION", position);
		intent.putExtra("FILE_NAME_ONLY", ""+audio_names[position]);
		intent.putExtra("FILE_NAME", ""+audio_tracks_name[position]);      
		startActivity(intent);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}else{
			if(isNetworkAvailable(AudioTrackActivity.this)){
			folder1.delete();
			new DownloadFileAsync().execute(audio_tracks_serever_urls[position]);
			}else{
				Toast.makeText(AudioTrackActivity.this, "Please check your Internet connection.", Toast.LENGTH_SHORT).show();
			}
		}
	}
}

HttpURLConnection connection ;
//////////////////////////////
class DownloadFileAsync extends AsyncTask<String, String, String> {
	Context context;

	/* private DownloadFileAsync(Context context) {
	        this.context = context.getApplicationContext();
	    }*/


	protected void onPreExecute() {
		super.onPreExecute();
		showDialog(DIALOG_DOWNLOAD_PROGRESS);
	}

	protected String doInBackground(String... aurl) {
		try {
			//connecting to url
			/*URL u = new URL(aurl[0]);
			connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			lenghtOfFile = connection.getContentLength();

			FileOutputStream f = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name)
					, audio_tracks_name[track_position]));
			InputStream in = connection.getInputStream();
			//here's the download code
			byte[] buffer = new byte[1024];
			int len1 = 0;
			long total = 0;
			while ((len1 = in.read(buffer)) > 0) {
				total += len1; //total = total + len1
				publishProgress("" + (int)((total*100)/lenghtOfFile));
				f.write(buffer, 0, len1);
			}
			f.close();
			*/
			URL url = new URL(aurl[0]);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			int lenghtOfFile = conexion.getContentLength();
			Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

			InputStream input = new BufferedInputStream(url.openStream());
			
		/*File file_dir = new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name)+audio_tracks_name[track_position]);
		  if(file_dir.exists()){
		  	file_dir.mkdir();
		  }*/
		  	
			OutputStream output = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.sd_card_dir_name)
					, audio_tracks_name[track_position]));

			byte data[] = new byte[1024];
			int count = 0;
			long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress(""+(int)((total*100)/lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			
		} catch (Exception e) {
		}
		return null;
	}

	protected void onProgressUpdate(String... progress) {
		mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		progressString=progress[0];
		//	Toast.makeText(getApplicationContext(), ""+progress[0].toString(), Toast.LENGTH_LONG).show();

	}


	protected void onPostExecute(String unused) {
		if(mProgressDialog.isShowing())
		{
			mProgressDialog.setProgress(0);

			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		}
		if(progressString.equalsIgnoreCase("100"))
		{
			Intent intent= new Intent(AudioTrackActivity.this, AudioPlayerDownloadFile.class);
			intent.putExtra("FILE_NAME_ONLY", ""+audio_names[track_position]);
			intent.putExtra("POSITION", track_position);
			intent.putExtra("FILE_NAME", ""+audio_tracks_name[track_position]);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}
	}
}
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_DOWNLOAD_PROGRESS: // we set this to 0
				mProgressDialog = new ProgressDialog(this,AlertDialog.THEME_HOLO_LIGHT);
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
}

	