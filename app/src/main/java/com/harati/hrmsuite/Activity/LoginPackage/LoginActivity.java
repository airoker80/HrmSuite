package com.harati.hrmsuite.Activity.LoginPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.harati.hrmsuite.Activity.MainPackage.MainActivity;
import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;
import com.harati.hrmsuite.Retrofit.RetrofiltClient.LoginRetrofitClient;
import com.harati.hrmsuite.Activity.LoginPackage.LoginModel.LoginModel;
import com.harati.hrmsuite.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sameer on 8/9/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText hrm_username,hrm_password;
    Button hrm_login_btn;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrm_login);
        apiInterface = LoginRetrofitClient.getLoginApiService();
        hrm_login_btn=(Button)findViewById(R.id.hrm_login_btn);
        hrm_username=(EditText)findViewById(R.id.hrm_username);
        hrm_password=(EditText)findViewById(R.id.hrm_password);
        hrm_login_btn.setOnClickListener(this);
    }
    public  void authenticateLogin(String email,String password){
        Call<LoginModel> call = apiInterface.authenticate(email,password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.toString().equals("Success")){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });

        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        String hrm_username_string = hrm_username.getText().toString();
        String hrm_password_string = hrm_password.getText().toString();
        authenticateLogin(hrm_username_string,hrm_password_string);
    }
}
