
package com.arkenea.magicfairground;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.WindowManager;

import com.android.glenn_library.AllBooksActivity;
import com.android.glenn_library.classes.Ebookdata;

public class EbooksActivity extends AllBooksActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        ebook_bg_image.setBackgroundResource(R.drawable.backgroundimage);
        folder_name = getResources().getString(R.string.sd_card_dir_name);
        ebook_list_view.setBackgroundResource(R.drawable.box);
        ebook_list_view.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()), 
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()), 0, 
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
        ebook_list.clear();

        Resources res = getResources();
        String[] ebook_name = res.getStringArray(R.array.ebook_names);
        String[] ebook_pdf_name = res.getStringArray(R.array.ebook_pdf_names);
        ebook_list.add(new Ebookdata("eBooks", "", false));
        for (int i = 0; i < ebook_name.length; i++) {
            ebook_list.add(new Ebookdata("" + ebook_name[i].trim(), "" + ebook_pdf_name[i].trim(),
                    false));
        }
        callAdapter();
        setPlayStoreType(true);
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}
