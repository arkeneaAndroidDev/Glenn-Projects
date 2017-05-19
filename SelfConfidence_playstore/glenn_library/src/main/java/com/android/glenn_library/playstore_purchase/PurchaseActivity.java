package com.android.glenn_library.playstore_purchase;

import java.security.PublicKey;

import com.android.glenn_library.BuildConfig;
import com.android.glenn_library.playstore_purchase.util.IabHelper;
import com.android.glenn_library.playstore_purchase.util.IabResult;
import com.android.glenn_library.playstore_purchase.util.Inventory;
import com.android.glenn_library.playstore_purchase.util.Purchase;
import com.android.glenn_library.playstore_purchase.util.Security;
import com.android.vending.billing.IInAppBillingService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;



public class PurchaseActivity extends Activity{
	 public IabHelper mHelper;
	 public static String ITEM_SKU = "";
	static String TAG = "In App Purchase";
	
	
	IInAppBillingService mService;
	 public String base64EncodedPublicKey = 
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkLKGk7/szpIrXlWTH6gnjz4//4BkIe4mRryZsUuR/Q/HLPXv2ozQ7fKL6MgeOAzqIkE31GVGpslIs1yJTR9OJNLHrO67OAMdFaIXW7+cTEkK57UHO6CUcZJWZWifzZy1/Tgk78LHyOXC7yBXlME/HYQ2fAwg6c0VQhAEipumkoQ55XGH42zCwcKx87RFuMgoHm9Nu7lMmG6jxOk3uMpq4045/tx6RuMRd9BNOIJvBivbMXyBn5D1gLj6BapKJHDBdWi37s7P4anBEJNIOR3vlVmULTW3wl39yEC7ei+aAHxbbZO/x6ietmt8PyOwwyQaIIv8ho5Q/B6M0vKPmEOY+wIDAQAB";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
}
	public void init(){
		mHelper = new IabHelper(this, base64EncodedPublicKey);
		
		mHelper.startSetup(new 
		IabHelper.OnIabSetupFinishedListener() {
		public void onIabSetupFinished(IabResult result) 
		{
		if (!result.isSuccess()) {
		Log.d(TAG, "In-app Billing setup failed: " + 
		result);
		} else {             
		 Log.d(TAG, "In-app Billing is set up OK");
		}
		}
		});
	}
	
	
	public IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener 
	= new IabHelper.OnIabPurchaseFinishedListener() {
	public void onIabPurchaseFinished(IabResult result, 
                    Purchase purchase) 
	{
	   if (result.isFailure()) {
	      // Handle error
	      return;
	 }      
	 else if (purchase.getSku().equals(ITEM_SKU)) {
	     consumeItem();
	   // buyButton.setEnabled(false);
	}
	      
   }
};


public static boolean verifyPurchase(String base64PublicKey, 
        String signedData, String signature) {
      if (TextUtils.isEmpty(signedData) || 
              TextUtils.isEmpty(base64PublicKey) ||
              TextUtils.isEmpty(signature)) {
          Log.e(TAG, "Purchase verification failed: missing data.");
          if (BuildConfig.DEBUG) {
              return true;
          }
          return false;
      }

      PublicKey key = Security.generatePublicKey(base64PublicKey);
      return Security.verify(key, signedData, signature);
  }

	public void consumeItem() {
		mHelper.queryInventoryAsync(mReceivedInventoryListener);
	}
		
	IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener 
	   = new IabHelper.QueryInventoryFinishedListener() {
		   public void onQueryInventoryFinished(IabResult result,
		      Inventory inventory) {
	   		   
		      if (result.isFailure()) {
			  // Handle failure
		    	  Log.i("Consume", "Fail");
		      } else {
	                 mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU), 
				mConsumeFinishedListener);
		      }
	    }
	};

	
	public IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
			  new IabHelper.OnConsumeFinishedListener() {
			   public void onConsumeFinished(Purchase purchase, 
		             IabResult result) {

			 if (result.isSuccess()) {
			   	//  clickButton.setEnabled(true);
			 } else {
			         // handle error
			 }
		  }
		};

	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null) mHelper.dispose();
		mHelper = null;
	}
	
	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   if (requestCode == 1001) {
	      int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
	      String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
	      String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

	      if (resultCode == RESULT_OK) {
	         try {
	            JSONObject jo = new JSONObject(purchaseData);
	            String sku = jo.getString("productId");
	            alert("You have bought the " + sku + ". Excellent choice, adventurer!");
	          }
	          catch (JSONException e) {
	             alert("Failed to parse purchase data.");
	             e.printStackTrace();
	          }
	      }
	   }
	}*/
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, 
	     Intent data) 
	{
	      if (!mHelper.handleActivityResult(requestCode, 
	              resultCode, data)) {     
	    	      super.onActivityResult(requestCode, resultCode, data);
	      }
	}

	
}
