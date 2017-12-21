package com.harati.hrmsuite.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

/**
 * Created by Sameer on 10/17/2017.
 */

public class ProgressHelper {
    Context context;
    ProgressDialog progress ;

    public ProgressHelper(Context context) {
        this.context = context;
    }

    public void showProgressDialog(String message) {

        Log.i("","showProgressDialog");
        progress = new ProgressDialog(context);
        if(!progress.isShowing()){
            SpannableString titleMsg= new SpannableString("Processing");
            titleMsg.setSpan(new ForegroundColorSpan(Color.BLACK),0,titleMsg.length(),0);
            progress.setTitle(titleMsg);
            progress.setMessage(message);
            progress.setCancelable(false);
            progress.setIndeterminate(true);
            progress.show();
        }
    }

    public void hideProgressDialog() {
        Log.i("","hideProgressDialog");
        if(progress !=null){
            if(progress.isShowing()){
                progress.dismiss();
            }
        }

    }
}
