package com.harati.hrmsuite.Retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.harati.hrmsuite.R;
import com.harati.hrmsuite.Retrofit.ApiService;
import com.harati.hrmsuite.Retrofit.Contact;
import com.harati.hrmsuite.Retrofit.ContactList;
import com.harati.hrmsuite.Retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sameer on 8/7/2017.
 */

public class LoginTestActivity extends AppCompatActivity {
    ArrayList<Contact> contactList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrm_login);
        ApiService api = RetrofitClient.getApiService();
        Call<ContactList> call = api.getMyJSON();

        call.enqueue(new Callback<ContactList>() {
            @Override
            public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                //Dismiss Dialog
//                dialog.dismiss();

                if(response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */

                    contactList = response.body().getContacts();

                    Log.e("contactList========= ",response.toString()+"-------"+ contactList.get(0).getAddress());
                    /**
                     * Binding that List to Adapter
                     */
//                    adapter = new MyContactAdapter(LoginTestActivity.this, contactList);
//                    listView.setAdapter(adapter);

                } else {
//                    Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ContactList> call, Throwable t) {
//                dialog.dismiss();
            }
        });

}
}
