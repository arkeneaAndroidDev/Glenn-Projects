package com.android.glenn_library;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class LoadingDialog {
	Dialog loading_dialog;
	public LoadingDialog(Context context) {
		super();
   loading_dialog = new Dialog(context);
		
		loading_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		loading_dialog.getWindow().setSoftInputMode(
		 WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		loading_dialog.setCancelable(false);
		loading_dialog.setCanceledOnTouchOutside(false);
		loading_dialog.getWindow().setBackgroundDrawableResource(R.drawable.tranperent);
		loading_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		loading_dialog.setContentView(R.layout.loading_dialog);
		ProgressBar loading_dialog_prgbar = (ProgressBar) loading_dialog.findViewById(R.id.progressBar);
	      
	      
	}
public void show(){
	loading_dialog.show();
}

public void dismiss(){
	loading_dialog.dismiss();
}

}
