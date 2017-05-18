
package com.Arkenea.SleepEasilyMeditatation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

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

import com.android.glenn_library.playstore_purchase.util.IabHelper;
import com.android.glenn_library.playstore_purchase.util.IabResult;
import com.android.glenn_library.playstore_purchase.util.Inventory;
import com.android.glenn_library.playstore_purchase.util.Purchase;

public class PurchasePlayActivity extends Activity {
    IabHelper mHelper;
    int app_no;
   
    private ProgressDialog mProgressDialog;
    final int DIALOG_DOWNLOAD_PROGRESS = 0;
    String progressString = "";

    ListView applist;
    ArrayList<purchaseitemclass> purchaseitemslist;
    PurchaseItemAdapter adapter;
    int Height, Width;

    String auidio_name, audio_tracks_serever_urls, audio_tracks_name;
    long filessizes;
    Typeface des_typeface;
    boolean mIsPurcahsed_BW,mIsPurcahsed_LYB,mIsPurcahsed_FYS;
    boolean mIsDownload_BW,mIsDownload_LYB,mIsDownload_FYS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display d = getWindowManager().getDefaultDisplay();
        Height = d.getHeight();
        Width = d.getWidth();

        mHelper = new IabHelper(this, Constants.BASE_64_KEY);

        mHelper.startSetup(new
                IabHelper.OnIabSetupFinishedListener() {
                    public void onIabSetupFinished(IabResult result)
                    {
                        if (!result.isSuccess()) {
                            Log.d("In App", "In-app Billing setup failed: " +result);
                        } else {
                            Log.d("In App", "In-app Billing is set up OK");
                            mHelper.queryInventoryAsync(mGotInventoryListener);
                        }
                    }
                });

        setContentView(R.layout.shazziemoreapplayout);
        applist = (ListView) findViewById(R.id.applist);
        // buyButton.setOnClickListener(this);
        adapter = new PurchaseItemAdapter();
        purchaseitemslist = new ArrayList<purchaseitemclass>();
        purchaseitemslist.add(new purchaseitemclass(R.drawable.find_your_soulmate, "", "Find Your Soul Mate",
                "£1.99", getResources().getString(R.string.find_your_soulmate)));
        purchaseitemslist.add(new purchaseitemclass(R.drawable.be_wealthy, "",
                "Be Wealthy Now", "£1.99", getResources().getString(
                        R.string.be_wealthy)));
        purchaseitemslist.add(new purchaseitemclass(R.drawable.love_your_body, "", "Love Yourself Love  Your Body",
                "£1.99", getResources().getString(R.string.love_your_body)));
        des_typeface = Typeface.createFromAsset(getAssets(), "OPENSANS-LIGHT_1.TTF");
       
        applist.setAdapter(adapter);
        
      //  adapter.notifyDataSetChanged();
    }

    
    @Override
    protected void onResume() {
    	super.onResume();
    	 if(checkFileExist(Constants.AUDIO_NAME_FIRST, Constants.AUDIO_NAME_FIRST_SIZE)){
             mIsDownload_FYS = true;
         }
         if(checkFileExist(Constants.AUDIO_NAME_SECOND, Constants.AUDIO_NAME_SECOND_SIZE)){
             mIsDownload_BW = true;
         }
         if(checkFileExist(Constants.AUDIO_NAME_THIRD, Constants.AUDIO_NAME_THIRD_SIZE)){
             mIsDownload_LYB = true;
         }
    	adapter.notifyDataSetChanged();
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
                    vi = LayoutInflater.from(PurchasePlayActivity.this);
                    v = vi.inflate(
                            R.layout.applist_item_layout, null);
                }

                ImageView app_image = (ImageView) v.findViewById(R.id.app_image);
                Button app_purchase_button = (Button) v.findViewById(R.id.app_purchase_button);
                app_purchase_button.setTransformationMethod(null);
                TextView app_name = (TextView) v.findViewById(R.id.app_name);
                TextView app_ammount = (TextView) v.findViewById(R.id.app_ammount);
                TextView app_desciption = (TextView) v.findViewById(R.id.app_desciption);
                app_desciption.setTypeface(des_typeface);

                app_image.setImageResource(purchaseitemslist.get(position).getIcon_id());
                app_name.setText(purchaseitemslist.get(position).getProduct_app_name());
                app_ammount.setText(purchaseitemslist.get(position).getProduct_app_ammount());
                app_desciption
                        .setText(purchaseitemslist.get(position).getProduct_app_description());

                LinearLayout.LayoutParams app_imageLP = (LayoutParams) app_image.getLayoutParams();
                app_imageLP.height = (int) Math.round(Width * 0.4);
                app_imageLP.width = (int) Math.round(Width * 0.4);
                app_image.setLayoutParams(app_imageLP);
                if (position == 0) {
                    if (mIsPurcahsed_FYS) {
                        app_purchase_button.setText("Download");
                        if (mIsDownload_FYS)
                            app_purchase_button.setText("Play");
                    }
                    else {
                        app_purchase_button.setText("Buy");
                    }
                 }
                if (position == 1) {
                    if (mIsPurcahsed_BW) {
                        app_purchase_button.setText("Download");
                        if (mIsDownload_BW)
                            app_purchase_button.setText("Play");
                    } else {
                        app_purchase_button.setText("Buy");
                    }
                 }
                if (position == 2) {
                    if (mIsPurcahsed_LYB) {
                        app_purchase_button.setText("Download");
                        if (mIsDownload_LYB)
                            app_purchase_button.setText("Play");
                    } else {
                        app_purchase_button.setText("Buy");
                    }
                 }
                app_purchase_button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Here call of purchase
                        switch (position) {
                            case 0:
                                auidio_name = Constants.AUDIO_NAME_FIRST;
                                audio_tracks_serever_urls = Constants.AUDIO_NAME_FIRST_LINK;
                                audio_tracks_name = Constants.AUDIO_NAME_FIRST;
                                filessizes = Constants.AUDIO_NAME_FIRST_SIZE;
                                app_no = 2;
                                if(mIsPurcahsed_FYS)
                                    check_existance();
                                else
                                mHelper.launchPurchaseFlow(PurchasePlayActivity.this, Constants.SKU_FYS, 10001,
                                        mPurchaseFinishedListener, "mypurchasetoken");
                                break;
                            case 1:
                                auidio_name = Constants.AUDIO_NAME_SECOND;
                                audio_tracks_serever_urls = Constants.AUDIO_NAME_SECOND_LINK;
                                audio_tracks_name = Constants.AUDIO_NAME_SECOND;
                                filessizes = Constants.AUDIO_NAME_SECOND_SIZE;
                                app_no = 2;
                                if(mIsPurcahsed_BW)
                                    check_existance();
                                else
                                mHelper.launchPurchaseFlow(PurchasePlayActivity.this, Constants.SKU_BW, 10001,
                                        mPurchaseFinishedListener, "mypurchasetoken");
                                break;
                            case 2:
                                auidio_name = Constants.AUDIO_NAME_THIRD;
                                audio_tracks_serever_urls = Constants.AUDIO_NAME_THIRD_LINK;
                                audio_tracks_name = Constants.AUDIO_NAME_THIRD;
                                filessizes = Constants.AUDIO_NAME_THIRD_SIZE;
                                app_no = 3;
                                if(mIsPurcahsed_LYB)
                                    check_existance();
                                else 
                                mHelper.launchPurchaseFlow(PurchasePlayActivity.this, Constants.SKU_LYB, 10001,
                                        mPurchaseFinishedListener, "mypurchasetoken");
                                break;

                            default:
                                break;
                        }

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            return v;
        }
    }

    public IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                Purchase purchase)
        {
            if (result.isFailure()) {
                Log.i("Purchase finish listener", "fail " + result.getMessage());
                if (result.getMessage().toString().contains("Item Already Owned")) {
                    check_existance();
                }
                return;
            } else if (purchase.getSku().equals(Constants.SKU_FYS)) {
                Log.i("Purchase finish listener", "success " + result);

                mIsPurcahsed_BW = true;
                check_existance();
            } else if (purchase.getSku().equals(Constants.SKU_BW)) {
                Log.i("Purchase finish listener", "success " + result);
                mIsPurcahsed_FYS = true;
                check_existance();
            } else if (purchase.getSku().equals(Constants.SKU_LYB)) {
                Log.i("Purchase finish listener", "success " + result);
                mIsPurcahsed_LYB = true;
                check_existance();
            }
            adapter.notifyDataSetChanged();
        }
    };

    
    
    @Override
    public void onBackPressed() {
        onDestroy();
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null)
            mHelper.dispose();
        mHelper = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data)
    {

        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            if (requestCode == 1001) {
                int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
                String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
                String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

                if (resultCode == RESULT_OK) {
                    try {
                        JSONObject jo = new JSONObject(purchaseData);
                        String sku = jo.getString("productId");
                        Log.i("Result", "You have bought the " + sku
                                + ". Excellent choice, adventurer!");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            // super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void check_existance() {
        // folder name check

        File folder = new File(Environment.getExternalStorageDirectory()
                + getResources().getString(R.string.sd_card_dir_name));
        Log.i("foldername", "" + folder);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File folder1 = new File(Environment.getExternalStorageDirectory()
                + getResources().getString(R.string.sd_card_dir_name) + "/"
                + auidio_name);
        Log.i("folder1name", "" + folder);
        if (!folder1.exists()) {
            if (isNetworkAvailable(PurchasePlayActivity.this)) {
                new DownloadFileAsync().execute(audio_tracks_serever_urls);
            } else {
                Toast.makeText(PurchasePlayActivity.this,
                        "Please check your Internet connection.", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            File folder1_ = new File(Environment.getExternalStorageDirectory()
                    + getResources().getString(R.string.sd_card_dir_name) + "/"
                    + audio_tracks_name);
            Log.i("File size", "" + folder1.length());
            if (folder1_.length() >= filessizes) {
                Intent intent = new Intent(PurchasePlayActivity.this,
                        AudioPlayerDownloadFile.class);
                intent.putExtra("FILE_NAME", "" + audio_tracks_name);
                intent.putExtra("FILE_NAME_ONLY", "" + audio_tracks_name);
                startActivity(intent);
            } else {
                if (isNetworkAvailable(PurchasePlayActivity.this)) {
                    folder1.delete();
                    new DownloadFileAsync()
                            .execute(audio_tracks_serever_urls);
                } else {
                    Toast.makeText(PurchasePlayActivity.this,
                            "Please check your Internet connection.",
                            Toast.LENGTH_SHORT).show();
                }
            }
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
                Intent intent = new Intent(PurchasePlayActivity.this, AudioPlayerDownloadFile.class);
                intent.putExtra("FILE_NAME_ONLY", "" + auidio_name);
                intent.putExtra("POSITION", app_no);
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
    
 
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (mHelper == null) return;
            if (result.isFailure()) {
                return;
            }
            Purchase purchase_one = inventory.getPurchase(Constants.SKU_FYS);
            mIsPurcahsed_FYS = (purchase_one != null && verifyDeveloperPayload(purchase_one));
            Purchase purchase_two = inventory.getPurchase(Constants.SKU_BW);
            mIsPurcahsed_BW = (purchase_two != null && verifyDeveloperPayload(purchase_two));
            Purchase purchase_three = inventory.getPurchase(Constants.SKU_LYB);
            mIsPurcahsed_LYB = (purchase_three != null && verifyDeveloperPayload(purchase_three));
            Log.e("Premium", "...."+mIsPurcahsed_FYS+";"+mIsPurcahsed_BW+":"+mIsPurcahsed_LYB);
            adapter.notifyDataSetChanged();
            
        }
    };


/** Verifies the developer payload of a purchase. */
boolean verifyDeveloperPayload(Purchase p) {
    String payload = p.getDeveloperPayload();

    /*
     * TODO: verify that the developer payload of the purchase is correct. It will be
     * the same one that you sent when initiating the purchase.
     *
     * WARNING: Locally generating a random string when starting a purchase and
     * verifying it here might seem like a good approach, but this will fail in the
     * case where the user purchases an item on one device and then uses your app on
     * a different device, because on the other device you will not have access to the
     * random string you originally generated.
     *
     * So a good developer payload has these characteristics:
     *
     * 1. If two different users purchase an item, the payload is different between them,
     *    so that one user's purchase can't be replayed to another user.
     *
     * 2. The payload must be such that you can verify it even when the app wasn't the
     *    one who initiated the purchase flow (so that items purchased by the user on
     *    one device work on other devices owned by the user).
     *
     * Using your own server to store and verify developer payloads across app
     * installations is recommended.
     */

    return true;
}
  private Boolean checkFileExist(String audio_tracks_name, long filessizes){
      File folder1_ = new File(Environment.getExternalStorageDirectory()
              + getResources().getString(R.string.sd_card_dir_name) + "/"
              + audio_tracks_name);
      Log.e("Filesize", ""+filessizes+":"+folder1_.length());
      if (folder1_.length() >= filessizes) {
          return true;
      } 
      return false;
  }
}
