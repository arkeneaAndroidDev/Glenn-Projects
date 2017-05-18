package com.android.glenn_library;

/*import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.BasicHttpParams;*/

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class MySocialMediaDialog {
    public Dialog custom_dialog;
    Context mycontext;
    public LinearLayout facebook_share_container, twitter_share_container, instagram_share_container, youtube_share_container, mailing_share_container;
    public ImageView close_dialog_btn;
    MyTextView facebook_social_txt, twitter_social_txt, instagram_social_txt, youtube_social_txt, mailing_social_txt;
    EditText first_name_edt;
    EditText last_name_edt;
    EditText email_edt;
    int contact_id = 0,listId=0;
    String status="";
    LoadingDialog loading;


    public MySocialMediaDialog(Context context) {
        super();
        mycontext = context;
        custom_dialog = new Dialog(context, R.style.MyCustomTheme);
        loading = new LoadingDialog(mycontext);
        custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        custom_dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        custom_dialog.setCancelable(false);
        custom_dialog.setCanceledOnTouchOutside(false);
        custom_dialog.getWindow().setBackgroundDrawableResource(R.drawable.tranperent);
        custom_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        custom_dialog.setContentView(R.layout.social_media_dialog_layout);
        facebook_share_container = (LinearLayout) custom_dialog.findViewById(R.id.facebook_social_container);
        twitter_share_container = (LinearLayout) custom_dialog.findViewById(R.id.twitter_social_container);
        instagram_share_container = (LinearLayout) custom_dialog.findViewById(R.id.instagram_social_container);
        youtube_share_container = (LinearLayout) custom_dialog.findViewById(R.id.youtube_social_container);
        mailing_share_container = (LinearLayout) custom_dialog.findViewById(R.id.mailing_social_container);
        close_dialog_btn = (ImageView) custom_dialog.findViewById(R.id.close_dialog_btn);

        facebook_social_txt = (MyTextView) custom_dialog.findViewById(R.id.facebook_social_txt);
        twitter_social_txt = (MyTextView) custom_dialog.findViewById(R.id.twitter_social_txt);
        instagram_social_txt = (MyTextView) custom_dialog.findViewById(R.id.instagram_social_txt);
        youtube_social_txt = (MyTextView) custom_dialog.findViewById(R.id.youtube_social_txt);
        mailing_social_txt = (MyTextView) custom_dialog.findViewById(R.id.mailing_social_txt);


        try {
            XmlResourceParser xrp = mycontext.getResources().getXml(R.xml.dialog_txtcolor);
            ColorStateList csl = ColorStateList.createFromXml(mycontext.getResources(), xrp);
            facebook_social_txt.setTextColor(csl);
            twitter_social_txt.setTextColor(csl);
            instagram_social_txt.setTextColor(csl);
            youtube_social_txt.setTextColor(csl);
            mailing_social_txt.setTextColor(csl);

        } catch (Exception e) {
            e.printStackTrace();
        }


        facebook_share_container.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "https://facebook.com/glennharrold.fanpage";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mycontext.startActivity(i);
                dismiss();

            }
        });

        twitter_share_container.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/GlennHarrold";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mycontext.startActivity(i);
                dismiss();
            }
        });

        instagram_share_container.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "http://instagram.com/glenn_harrold";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mycontext.startActivity(i);
                dismiss();
            }
        });

        youtube_share_container.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/user/doctorofthemind/videos";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mycontext.startActivity(i);
                dismiss();
            }
        });

        mailing_share_container.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog icontact_dialog = new Dialog(mycontext);

                icontact_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                icontact_dialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                icontact_dialog.setCancelable(false);
                icontact_dialog.setCanceledOnTouchOutside(false);
                icontact_dialog.getWindow().setBackgroundDrawableResource(R.drawable.tranperent);
                icontact_dialog.setContentView(R.layout.icontact_layout);
                first_name_edt = (EditText) icontact_dialog.findViewById(R.id.first_name_edt);
                last_name_edt = (EditText) icontact_dialog.findViewById(R.id.last_name_edt);
                email_edt = (EditText) icontact_dialog.findViewById(R.id.email_edt);
                Button add_btn = (Button) icontact_dialog.findViewById(R.id.add_btn);
                Button cancel_btn = (Button) icontact_dialog.findViewById(R.id.cancel_btn);
                add_btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!first_name_edt.getText().toString().trim().equalsIgnoreCase("")) {
                            if (!last_name_edt.getText().toString().trim().equalsIgnoreCase("")) {
                                if (!email_edt.getText().toString().isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email_edt.getText().toString()).matches()) {
                                    createicontact(email_edt.getText().toString(), first_name_edt.getText().toString().trim(), last_name_edt.getText().toString().trim());
                                    icontact_dialog.dismiss();
                                } else {
                                    Toast.makeText(mycontext, "Please enter valid email address.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mycontext, "Please enter last name.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mycontext, "Please enter first name.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancel_btn.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        icontact_dialog.dismiss();
                    }
                });

                icontact_dialog.show();
                Window window = icontact_dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dismiss();
            }
        });


        close_dialog_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show() {
        custom_dialog.show();
    }

    public void dismiss() {
        custom_dialog.dismiss();
    }

    private void createicontact(String email, String firstName, String lastName) {
        String iConnectUrlToCall = "https://" + mycontext.getResources().getString(R.string.icontact_host_name)
                + "/icp/a/" + mycontext.getResources().getString(R.string.icontact_account_id)
                + "/c/" + mycontext.getResources().getString(R.string.icontact_cilent_folder_id) + "/contacts";
        String[] contactInput = {iConnectUrlToCall, email, firstName, lastName};
        Log.e("iConnectUrl", "" + iConnectUrlToCall);
        new AsynkAddContact().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, contactInput);
    }

    public class ConnectionClass {

        private static final int CONNECTION_TIMEOUT = 50000;
        Context mContext;
        Handler mHandler;
        HttpURLConnection connection = null;
        JSONArray json_data = new JSONArray();
        JSONObject json_input = new JSONObject();

        public ConnectionClass(Context context) {
            super();
            this.mContext = context;
            mHandler = new Handler(Looper.getMainLooper());
        }

        public HttpURLConnection createConnection(final boolean showTimeout,String type, String... f_url) {

            URL url = null;

            try {
                if(type.equalsIgnoreCase("addContact")) {
                    json_input.put("email", f_url[1]);
                    json_input.put("firstName", "" + f_url[2]);
                    json_input.put("lastName", "" + f_url[3]);
                    json_data.put(json_input);
                    url = new URL("https://app.icontact.com/icp/a/1220660/c/18036/contacts");
                }else{
                     json_input = new JSONObject();

                    json_input.put("listId", mycontext.getResources().getString(R.string.icontact_listid));
                    json_input.put("contactId", contact_id);
                    json_input.put("status", "normal");
                    json_data.put(json_input);
                    Log.i("Request", "" + json_data.toString());
                    url = new URL("https://app.icontact.com/icp/a/1220660/c/18036/subscriptions");
                }
                Log.i("Request", "" + json_data.toString());

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                connection.setRequestProperty("Content-Language", "en-US");

                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setReadTimeout(CONNECTION_TIMEOUT);
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);

                String username = "sales@hypnosisaudio.com";
                String password = "Arkenea@123";
                final String credentials = username + ":" + password;
                String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                connection.setRequestProperty("Authorization", string);
                connection.setRequestProperty("Accept", "application/json");// this is a hardcoded value
                connection.setRequestProperty("Content-Type", "application/json");// this is a hardcoded value
                connection.setRequestProperty("API-Version", "2.2");// this is a hardcoded value
                connection.setRequestProperty("API-AppId", /*"aoHJEqdf5ioHjtaJPx21rwCFyuw82wsm" +*/ mycontext.getResources().getString(R.string.icontact_app_id));
                connection.setRequestProperty("API-Username",/* "sales@hypnosisaudio.com" +*/ mycontext.getResources().getString(R.string.icontact_user_name));
                connection.setRequestProperty("API-Password", "Arkenea@123" /*mycontext.getResources().getString(R.string.icontact_password)*/);
                //Instantiate a POST HTTP method

                String auth = new String(Base64.encode(("sales@hypnosisaudio.com:Vd6gMZetEN").getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));
                connection.setRequestProperty("Authorization", "Basic " + auth);


                connection.connect();

            } catch (SocketTimeoutException socketException) {
                socketException.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return connection;
        }

        public String fetchOutput(InputStream is) {
            // InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            try {

                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                    Log.e( response +"httpurlconnection", "./" + connection.getResponseCode());
                }
                rd.close();
            } catch (Exception e) {
                response = response.append("NO_DATA");
                e.printStackTrace();
            }


            return response.toString();
        }

        public void setData(OutputStream op, String Inputdata) {
            DataOutputStream wr = new DataOutputStream(op);
            try {
                wr.writeBytes(Inputdata);
                wr.flush();
                wr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    class AsynkAddContact extends AsyncTask<String, String, String> {
        String Error_msg = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.show();
        }

        @Override
        protected String doInBackground(String... f_url) {
            String str = null;
            HttpURLConnection httpURLConnection = null;
            try {
                ConnectionClass connectionClass = new ConnectionClass(mycontext);
                httpURLConnection = connectionClass.createConnection(true,"addContact", f_url);
                connectionClass.setData(httpURLConnection.getOutputStream(), connectionClass.json_data.toString());
                str = connectionClass.fetchOutput(httpURLConnection.getInputStream());
                Log.e("STr:",str);
                JSONObject json = new JSONObject(str);
                JSONArray jarray = json.getJSONArray("contacts");
                JSONObject jsonitem = jarray.getJSONObject(0);
                contact_id = jsonitem.getInt("contactId");
                status=jsonitem.getString("status");
                Log.e("Data:",jsonitem.toString());
               // listId=jsonitem.getInt("listId");
                Log.e("Contactid:",contact_id+"");
                Log.e("OutputString", "Output:" + contact_id);




            } catch (Exception e) {
                e.printStackTrace();
                contact_id = 0;
                return "NO_DATA";
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }

            }

            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("StringAdd", "Result" + result);
            loading.dismiss();
            if (contact_id != 0) {
                new AsynkSubscribeContact().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
            }
        }
    }


    class AsynkSubscribeContact extends AsyncTask<String, String, String> {
        String Error_msg = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.show();
        }

        @Override
        protected String doInBackground(String... f_url) {
            String str = null;
            HttpURLConnection httpURLConnection = null;
            try {
                JSONArray json_data = new JSONArray();
                JSONObject json_input = new JSONObject();

                json_input.put("listId", mycontext.getResources().getString(R.string.icontact_listid));
                json_input.put("contactId", contact_id);
                json_input.put("status", "normal");
                json_data.put(json_input);
               // Log.i("Request", "" + json_data.toString());
                String[] contactInput = {"7702", ""+contact_id, "normal"};

                ConnectionClass connectionClass = new ConnectionClass(mycontext);
                httpURLConnection = connectionClass.createConnection(true,"", contactInput);
                connectionClass.setData(httpURLConnection.getOutputStream(), connectionClass.json_data.toString());
                str = connectionClass.fetchOutput(httpURLConnection.getInputStream());
                Log.e("Data", "Output:" + str);

                JSONObject json = new JSONObject(str);
                Log.e("Data", "Output:" + json);


            } catch (Exception e) {
                e.printStackTrace();
                return "NO_DATA";
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }

            }

            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("String", "Result" + result);
            loading.dismiss();
            try {
                JSONObject json = new JSONObject(result);
                if(json.has("subscriptions")){
                    Toast.makeText(mycontext, "Your email Id is Added Successfully.", Toast.LENGTH_SHORT).show();
                    JSONArray subArr=json.getJSONArray("subscriptions");
                    for(int i=0;i<subArr.length();i++){
                        JSONObject jo=subArr.getJSONObject(i);
                        String subscriptionId=jo.getString("subscriptionId");
                    }

                }
                else
                    Toast.makeText(mycontext, "Error occured. Please try again.", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}