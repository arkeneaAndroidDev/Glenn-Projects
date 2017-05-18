package com.Arkenea.SleepEasilyMeditatation;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Arkenea.SleepEasilyMeditation.amazon_purchase.MySku;
import com.Arkenea.SleepEasilyMeditation.amazon_purchase.PurchaseAmazon;
import com.Arkenea.SleepEasilyMeditation.amazon_purchase.PurchaseStatus;
import com.amazon.device.iap.PurchasingService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PurchaseAmazonActivity extends Activity implements OnClickListener,PurchaseStatus{
	//Button clickButton;
	//Button buyButton;
  
    ListView applist;
    ArrayList<purchaseitemclass> purchaseitemslist;
    PurchaseItemAdapter adapter;
    int Height, Width;
    PurchaseAmazon purchaseAmazon;
    private boolean level2ProductAvailable;
    private boolean level3ProductAvailable;
    private boolean level4ProductAvailable;
    public boolean level2Purchased = false;
    public boolean level3Purchased = false;
    public boolean level4Purchased = false;
    boolean mIsDownload_first,mIsDownload_second,mIsDownload_third;
    
    private ProgressDialog mProgressDialog,mDialog;
    final int DIALOG_DOWNLOAD_PROGRESS = 0;
    String progressString = "";
    int track_position;
    String audio_tracks_name, audio_names;
    int app_number;
    String audio_tracks_serever_urls;
    long filesize;
    Typeface des_typeface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display d = getWindowManager().getDefaultDisplay();
		Height = d.getHeight();
		Width = d.getWidth();
		
		purchaseAmazon = new PurchaseAmazon(PurchaseAmazonActivity.this,this);
		purchaseAmazon.onCreate();
		setContentView(R.layout.shazziemoreapplayout);
		applist = (ListView) findViewById(R.id.applist);
		mDialog = new ProgressDialog(this);
		mDialog.setMessage("Please Wait...");
			// buyButton.setOnClickListener(this);
		adapter = new PurchaseItemAdapter();
		purchaseitemslist  = new ArrayList<purchaseitemclass>();
		purchaseitemslist.add(new purchaseitemclass(R.drawable.find_your_soulmate,"", "Find Your Soul Mate", "£1.99", getResources().getString(R.string.find_your_soulmate)));
		purchaseitemslist.add(new purchaseitemclass(R.drawable.be_wealthy,"", "Be Wealthy Now", "£1.99", getResources().getString(R.string.be_wealthy)));
		purchaseitemslist.add(new purchaseitemclass(R.drawable.love_your_body,"", "LoveYourself Love  Your Body", "£1.99", getResources().getString(R.string.love_your_body)));
		des_typeface = Typeface.createFromAsset(getAssets(), "OPENSANS-LIGHT_1.TTF");
		applist.setAdapter(adapter);
		 
	
	   //adapter.notifyDataSetChanged();
	}
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
	protected void onStart() {
	    // TODO Auto-generated method stub
	    super.onStart();
	    purchaseAmazon.onStart();
	}
	@Override
	protected void onResume() {
	    // TODO Auto-generated method stub
	    super.onResume();
	    purchaseAmazon.onResume();
	    if(!mDialog.isShowing()){
	        mDialog.show();
	    }
	    if(checkFileExist(Constants.AUDIO_NAME_FIRST, Constants.AUDIO_NAME_FIRST_SIZE)){
            mIsDownload_first = true;
        }
        if(checkFileExist(Constants.AUDIO_NAME_SECOND, Constants.AUDIO_NAME_SECOND_SIZE)){
            mIsDownload_second = true;
        }
        if(checkFileExist(Constants.AUDIO_NAME_THIRD, Constants.AUDIO_NAME_THIRD_SIZE)){
            mIsDownload_third = true;
        }
        adapter.notifyDataSetChanged();
	}

	@Override
	protected void onPause() {
	    // TODO Auto-generated method stub
	    super.onPause();
	    purchaseAmazon.onPause();
	}
	public class PurchaseItemAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return purchaseitemslist.size();
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
					vi = LayoutInflater.from(PurchaseAmazonActivity.this);
					v = vi.inflate(
							R.layout.applist_item_layout, null);
					}
				
				ImageView app_image = (ImageView) v.findViewById(R.id.app_image);
				final Button app_purchase_button = (Button) v.findViewById(R.id.app_purchase_button);
				 app_purchase_button.setTransformationMethod(null);
				TextView app_name = (TextView) v.findViewById(R.id.app_name);
				TextView app_ammount = (TextView) v.findViewById(R.id.app_ammount);
				TextView app_desciption = (TextView) v.findViewById(R.id.app_desciption);
				app_desciption.setTypeface(des_typeface);
				app_image.setImageResource(purchaseitemslist.get(position).getIcon_id());
				app_name.setText(purchaseitemslist.get(position).getProduct_app_name());
				app_ammount.setText(purchaseitemslist.get(position).getProduct_app_ammount());
				app_desciption.setText(purchaseitemslist.get(position).getProduct_app_description());
				
				LinearLayout.LayoutParams app_imageLP = (LayoutParams) app_image.getLayoutParams();
				app_imageLP.height = (int) Math.round(Width *0.4);
				app_imageLP.width = (int) Math.round(Width *0.4);
				app_image.setLayoutParams(app_imageLP);

                if (position == 0) {
                    if (level2Purchased) {
                        app_purchase_button.setText("Download");
                        if (mIsDownload_first)
                            app_purchase_button.setText("Play");
                    }
                    else {
                        app_purchase_button.setText("Buy");
                    }
                 }
                if (position == 1) {
                    if (level3Purchased) {
                        app_purchase_button.setText("Download");
                        if (mIsDownload_second)
                            app_purchase_button.setText("Play");
                    } else {
                        app_purchase_button.setText("Buy");
                    }
                 }
                if (position == 2) {
                    if (level4Purchased) {
                        app_purchase_button.setText("Download");
                        if (mIsDownload_third)
                            app_purchase_button.setText("Play");
                    } else {
                        app_purchase_button.setText("Buy");
                    }
                 }
	          /*  Log.e("check", "..."+level2Purchased+";"+level3Purchased+";"+level4Purchased
	                    +";"+mIsDownload_first+";"+mIsDownload_second+";"+mIsDownload_third +";"+position);  */
				app_purchase_button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
	                        amazonClick(position);
	                       
					}
				});
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
				return v;
		}
	}

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void onPurchaseState(boolean level2ProductAvailable, boolean level3ProductAvailable,
            boolean level4ProductAvailable, boolean level2Purchased, boolean level3Purchased,
            boolean level4Purchased) {
        // TODO Auto-generated method stub
        this.level2ProductAvailable = level2ProductAvailable;
        this.level3ProductAvailable = level3ProductAvailable;
        this.level4ProductAvailable = level4ProductAvailable;
        this.level2Purchased = level2Purchased;
        this.level3Purchased = level3Purchased;
        this.level4Purchased = level4Purchased;
        if(mDialog.isShowing()){
            mDialog.dismiss();
        }
        adapter.notifyDataSetChanged();
        Log.e("Purcahse", level2ProductAvailable+";"+level3ProductAvailable+";"+level4ProductAvailable
                +";;"+level2Purchased+":"+level3Purchased+":"+level4Purchased);
    }
    

    @Override
    public void onPurchaseSuccess(String sku) {
        // TODO Auto-generated method stub
        Log.e("onPurchaseSuccess", "..."+sku);
        if(sku.equalsIgnoreCase(Constants.ITEM_SKU_AMAZON_FIRST)){
            audio_names = Constants.AUDIO_NAME_FIRST;
            audio_tracks_serever_urls =Constants.AUDIO_NAME_FIRST_LINK;
            audio_tracks_name =Constants.AUDIO_NAME_FIRST;
            filesize = Constants.AUDIO_NAME_FIRST_SIZE;
            app_number = 2;
        }
        else if(sku.equalsIgnoreCase(Constants.ITEM_SKU_AMAZON_SECOND)){
            audio_names = Constants.AUDIO_NAME_SECOND;
            audio_tracks_serever_urls =Constants.AUDIO_NAME_SECOND_LINK;
            audio_tracks_name =Constants.AUDIO_NAME_SECOND;
            filesize = Constants.AUDIO_NAME_SECOND_SIZE;
            app_number = 3;
        }
        else if(sku.equalsIgnoreCase(Constants.ITEM_SKU_AMAZON_THIRD)){
            audio_names = Constants.AUDIO_NAME_THIRD;
            audio_tracks_serever_urls = Constants.AUDIO_NAME_THIRD_LINK;
            audio_tracks_name = Constants.AUDIO_NAME_THIRD;
            filesize = Constants.AUDIO_NAME_THIRD_SIZE;
            app_number = 4;
        }
        check_existance();
    }
    /**
     * Click evenyt for buy button
     * @param position: listview id postion
     */
    public void amazonClick(int position){
        //call for Amazon In app Purcahse
        switch (position) {
            case 0:
                if(level2ProductAvailable){
                    if(!level2Purchased){
                        PurchasingService.purchase(MySku.LEVEL2.getSku());
                    }
                    else {
                       // Downlaod code
                        
                        audio_names = Constants.AUDIO_NAME_FIRST;
                        audio_tracks_serever_urls =Constants.AUDIO_NAME_FIRST_LINK;
                        audio_tracks_name =Constants.AUDIO_NAME_FIRST;
                        filesize = Constants.AUDIO_NAME_FIRST_SIZE;
                        app_number = 2;
                        check_existance();
                       // new DownloadFileAsync().execute(audio_tracks_serever_urls);
                        //Toast.makeText(getApplicationContext(), "Download", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Product Not Available", Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                if(level3ProductAvailable){
                    if(!level3Purchased){
                        PurchasingService.purchase(MySku.LEVEL3.getSku());
                    }
                    else {
                        audio_names = Constants.AUDIO_NAME_SECOND;
                        audio_tracks_serever_urls =Constants.AUDIO_NAME_SECOND_LINK;
                        audio_tracks_name =Constants.AUDIO_NAME_SECOND;
                        filesize = Constants.AUDIO_NAME_SECOND_SIZE;
                        app_number = 3;
                        check_existance();
                       // new DownloadFileAsync().execute(audio_tracks_serever_urls);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Product Not Available", Toast.LENGTH_LONG).show();
                }
                break;
            case 2:
                if(level4ProductAvailable){
                    if(!level4Purchased){
                        PurchasingService.purchase(MySku.LEVEL4.getSku());
                    }
                    else {
                        audio_names = Constants.AUDIO_NAME_THIRD;
                        audio_tracks_serever_urls = Constants.AUDIO_NAME_THIRD_LINK;
                        audio_tracks_name = Constants.AUDIO_NAME_THIRD;
                        filesize = Constants.AUDIO_NAME_THIRD_SIZE;
                        app_number = 4;
                        check_existance();
                       // new DownloadFileAsync().execute(audio_tracks_serever_urls);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Product Not Available", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }

    }
    
    HttpURLConnection connection;

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            try {
                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(new File(
                        Environment.getExternalStorageDirectory()
                                + getResources().getString(R.string.sd_card_dir_name)
                        , audio_tracks_name));

                byte data[] = new byte[1024];
                int count = 0;
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
            progressString = progress[0];

        }

        @Override
        protected void onPostExecute(String unused) {
            if (mProgressDialog.isShowing())
            {
                mProgressDialog.setProgress(0);

                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            }
            if (progressString.equalsIgnoreCase("100"))
            {
                Intent intent = new Intent(PurchaseAmazonActivity.this, AudioPlayerDownloadFile.class);
                intent.putExtra("FILE_NAME_ONLY", "" + audio_names);
                intent.putExtra("POSITION", app_number);
                intent.putExtra("FILE_NAME", "" + audio_tracks_name);
                startActivity(intent);
            }
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS: // we set this to 0
                mProgressDialog = new ProgressDialog(this);
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

    public void check_existance(){
        //folder name check
        
         File folder = new File(Environment.getExternalStorageDirectory()
                    + getResources().getString(R.string.sd_card_dir_name));
            Log.i("foldername", "" + folder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            File folder1 = new File(Environment.getExternalStorageDirectory()
                    +getResources().getString(R.string.sd_card_dir_name) + "/"
                    + audio_names);
            Log.i("folder1name", "" + folder);
            Log.e("check existance", "..."+!folder1.exists()+";"+
                    audio_tracks_serever_urls+";;;;"+audio_tracks_name+"...."+filesize);
            if (!folder1.exists()) {
                if (isNetworkAvailable(PurchaseAmazonActivity.this)) {
                    new DownloadFileAsync().execute(audio_tracks_serever_urls);
                 } else {
                    Toast.makeText(PurchaseAmazonActivity.this,
                            "Please check your Internet connection.", Toast.LENGTH_SHORT)
                            .show();
                }
            } else {
                File folder1_ = new File(Environment.getExternalStorageDirectory()
                        + getResources().getString(R.string.sd_card_dir_name) + "/"
                        + audio_tracks_name);
                Log.i("File size", "" + folder1.length());
                if (folder1_.length() >= filesize) {
                    Intent intent = new Intent(PurchaseAmazonActivity.this,
                            AudioPlayerDownloadFile.class);
                    intent.putExtra("FILE_NAME", "" + audio_tracks_name);
                    intent.putExtra("FILE_NAME_ONLY", "" + audio_tracks_name);
                    startActivity(intent);
                } else {
                    if (isNetworkAvailable(PurchaseAmazonActivity.this)) {
                        folder1.delete();
                        new DownloadFileAsync()
                                .execute(audio_tracks_serever_urls);
                     } else {
                        Toast.makeText(PurchaseAmazonActivity.this,
                                "Please check your Internet connection.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        
       }
    private Boolean checkFileExist(String audio_tracks_name, long filessizes){
        File folder1_ = new File(Environment.getExternalStorageDirectory()
                + getResources().getString(R.string.sd_card_dir_name) + "/"
                + audio_tracks_name);
        if (folder1_.length() >= filessizes) {
            return true;
        } 
        return false;
    }

}
