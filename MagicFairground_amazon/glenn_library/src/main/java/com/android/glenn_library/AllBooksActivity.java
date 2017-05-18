package com.android.glenn_library;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.glenn_library.classes.Ebookdata;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class AllBooksActivity extends BaseActivity {
    public ImageView ebook_bg_image;

    public ArrayList<Ebookdata> ebook_list;
    public ListView ebook_list_view;
    public String folder_name = "";
    ebookListAdapter adapter;
    private boolean GooglePlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allbooks_layout);
        ebook_bg_image = (ImageView) findViewById(R.id.ebook_bg_image);
        ebook_list_view = (ListView) findViewById(R.id.ebook_list_view);

        RelativeLayout.LayoutParams Lp = (RelativeLayout.LayoutParams) ebook_list_view.getLayoutParams();
        Lp.topMargin = (int) Math.round(height * 0.27);
        //Lp.bottomMargin = (int) Math.round(height * 0.15);
       // Lp.width = (int) Math.round(width * 0.60);
        Lp.leftMargin = (int) Math.round(width * 0.03);
        Lp.rightMargin = (int) Math.round(width * 0.03);
        ebook_list_view.setLayoutParams(Lp);

        ebook_list = new ArrayList<Ebookdata>();
        adapter = new ebookListAdapter();

    }

    public void setPlayStoreType(boolean playStoreType){
        GooglePlay = playStoreType;
        Log.i("Google PlayStore" , ""+GooglePlay);
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
                    vi = LayoutInflater.from(AllBooksActivity.this);
                    v = vi.inflate(
                            R.layout.ebook_item_layout, null);
                }
                LinearLayout purchase_history_list_item_container = (LinearLayout) v.findViewById(R.id.ebook_list_item_container);
                TextView purchase_history_list_item_order_no_txt = (TextView) v.findViewById(R.id.ebook_list_item_txt);
                ImageView ebook_list_item_icon = (ImageView) v.findViewById(R.id.ebook_list_item_icon);
                purchase_history_list_item_order_no_txt.setText("" + ebook_list.get(position).getBook_name());

                if (position == 0) {

                    purchase_history_list_item_container.setPadding(0, 0, 0, 10);
                } else {
                    purchase_history_list_item_container.setPadding(50, 0, 0, 0);
                }

                if (ebook_list.get(position).isSelected()) {
                    ebook_list_item_icon.setImageResource(R.drawable.all_ebooks_h);
                    purchase_history_list_item_order_no_txt.setTextColor(Color.parseColor("#000000"));
                } else {
                    ebook_list_item_icon.setImageResource(R.drawable.ebook);
                    //purchase_history_list_item_order_no_txt.setTextColor(Color.parseColor("#ffffff"));
                    try {
                        XmlResourceParser xrp = getResources().getXml(R.xml.txtcolor);
                        ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
                        purchase_history_list_item_order_no_txt.setTextColor(csl);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                /*purchase_history_list_item_container.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            String filename = ebook_list.get(position).getBook_url().trim();
                            File existing_file = new File(Environment.getExternalStorageDirectory() + folder_name + "/" + filename);

                            existing_file.delete();


                            CopyAssets(filename);
                            try {
                                for (int i = 0; i < ebook_list.size(); i++) {
                                    ebook_list.get(i).setSelected(false);
                                }

                                ebook_list.get(position).setSelected(true);
                                File file1 = new File(Environment.getExternalStorageDirectory() + folder_name + "/" + filename);
                                Uri path1 = Uri.fromFile(file1);
                                Log.i("url is", "" + Uri.parse(Environment.getExternalStorageDirectory() + folder_name + "/" + filename));
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(
                                        path1,
                                        "application/pdf");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Intent chooserIntent = Intent.createChooser(intent, "Open File");
                                startActivity(chooserIntent);
                            } catch (ActivityNotFoundException e) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(AllBooksActivity.this);
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.adobe.reader&feature=more_from_developer#?t=W251bGwsMSwxLDEwMiwiY29tLmFkb2JlLnJlYWRlciJd"));
                                        startActivity(i);
                                    }
                                });
                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alert.setTitle("No PDF viewer available");
                                alert.setMessage("Do you want to download now?");
                                alert.show();
                            } catch (Exception otherException) {
                                Toast.makeText(AllBooksActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                });*/


                purchase_history_list_item_container.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            String filename = ebook_list.get(position).getBook_url().trim();
                            File existing_file = new File(Environment.getExternalStorageDirectory() + folder_name + "/" + filename);

                            existing_file.delete();
                            for (int i = 0; i < ebook_list.size(); i++) {
                                ebook_list.get(i).setSelected(false);
                            }

                            ebook_list.get(position).setSelected(true);

                            adapter.notifyDataSetChanged();
                            CopyReadAssets(filename);
                        }
                    }
                });
            }catch(Exception e){
                e.printStackTrace();
            }

                return v;
            }
        }

    public void callAdapter() {
        adapter = new ebookListAdapter();
        ebook_list_view.setAdapter(adapter);
    }

    private void CopyReadAssets(String pdfFile)
    {
        AssetManager assetManager = getAssets();
        Log.i("file name", "" + pdfFile);
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), pdfFile+".pdf");
        try
        {
            in = assetManager.open(pdfFile+".pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(
                    Uri.parse("file://" + getFilesDir() + "/" + pdfFile + ".pdf"),
                    "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent chooserIntent = Intent.createChooser(intent, "Open File");
            startActivity(chooserIntent);
        } catch (ActivityNotFoundException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(AllBooksActivity.this);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if(GooglePlay) {//google playStore pdf download link
                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.adobe.reader&feature=more_from_developer#?t=W251bGwsMSwxLDEwMiwiY29tLmFkb2JlLnJlYWRlciJd"));
                    }else{//amazon pdf download link
                        i.setData(Uri.parse("https://www.amazon.com/Adobe-Acrobat-Reader-Reader-more/dp/B004SD5GZ4/ref=sr_1_1?s=mobile-apps&ie=UTF8&qid=1479549404&sr=1-1&keywords=adobe+pdf+reader"));
                    }
                    startActivity(i);
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.setTitle("No PDF viewer available");
            alert.setMessage("Do you want to download now?");
            alert.show();
        } catch (Exception otherException) {
            Toast.makeText(AllBooksActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
        }
    }


    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;

        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
