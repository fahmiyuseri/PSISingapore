package com.fahmiyuseri.psisingapore.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * Created by IRSB on 14/3/2018.
 */

public class HttpAsync extends AsyncTask<String,Void,String> {

    private final Activity mCallBackActivity;
    private Object mCallBackObj;
    private Context mContext;
    private int mRequestCode;
    private String mURLs;
    private ArrayList<List<NameValuePair>> mNameValuePairs;
    private ProgressDialog mDialog;
    private String errorKey ="";
    HttpCallBack httpCallBack;
    public HttpAsync(Activity activity, Object callback, int requestCode) {
        this.mCallBackActivity = activity;
        init(activity, callback, requestCode);
    }
    void init(Context activity, Object callbackClass, int requestCode) {
        this.mCallBackObj = callbackClass;
        this.mContext = activity;
        this.mRequestCode = requestCode;

    }
    public void setURLs(String urls){
        this.mURLs = urls;
    }

    public void setNameValuePairs(ArrayList<List<NameValuePair>> nameValuePairs){
        this.mNameValuePairs = nameValuePairs;
    }
    @Override
    protected void onPreExecute() {
        try{
            if(Util.isNetworkAvailable(this.mContext)){
                if (mCallBackActivity != null) {


                        if (mDialog == null) {
                            mDialog = new ProgressDialog(this.mCallBackActivity);
                        }
                        if (mDialog != null && !mDialog.isShowing()) {
                            mDialog = ProgressDialog.show(this.mCallBackActivity, "", "Loading...Please wait...", true);
                        }

                }
            }
            else{
                Crouton.makeText(mCallBackActivity,
                        "Network connection problem", Style.ALERT).show();
                this.onPostExecute(HttpErrorKey.getErrorResponse(HttpErrorKey.connectKey));
                this.cancel(true);
            }
        }catch(Exception e){
            Log.e("Error",e.getMessage().toString());
        }

    }
    public void setCallBack(HttpCallBack httpCallBack){
        this.httpCallBack = httpCallBack;
    }
    @Override
    protected String doInBackground(String... strings) {
        return getJSON();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("sada", mURLs);
        Log.d("wwwww2", result);
        Log.d("wwwww2", errorKey);

        try{
            if(mDialog != null && mDialog.isShowing()){
                mDialog.dismiss();
                mDialog = null;
            }

            if (mCallBackActivity != null && !errorKey.equals(""))
            {
                if (errorKey.equals(HttpErrorKey.timeoutKey))
                {
                    Crouton.cancelAllCroutons();
                    Crouton.makeText(mCallBackActivity,
                            "Server could not be reached or too slow",
                            Style.ALERT).show();
                }
                else
                {
                    Crouton.cancelAllCroutons();
                    Crouton.makeText(mCallBackActivity,
                            "Network connection problem", Style.ALERT).show();
                }
                //result = errorKey;
            }


            HttpCallBack xmlCallBack = (HttpCallBack) this.mCallBackObj;
            xmlCallBack.onReceiveHttpCallBack(result, this.mRequestCode);

        } catch(IllegalArgumentException iae){
        } catch(Exception e){
        } }


    public String getJSON() {
        String sResponse=null;
        HttpURLConnection c = null;
        try {
            URL u = new URL(mURLs);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(30000);
            c.setReadTimeout(30000);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    sResponse = sb.toString();
                    return sResponse;
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            errorKey = HttpErrorKey.connectKey;
            sResponse = errorKey;

        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            errorKey = HttpErrorKey.connectKey;
            sResponse = errorKey;
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    //errorKey = HttpErrorKey.connectKey;
                   // sResponse = errorKey;
                }
            }
        }


        return sResponse;
    }
}
