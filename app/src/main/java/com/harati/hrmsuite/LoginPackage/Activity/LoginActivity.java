package com.harati.hrmsuite.LoginPackage.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.harati.hrmsuite.ForgotPasswordPackage.PasswordRecoveryActivity;
import com.harati.hrmsuite.Helper.NetworkManager;
import com.harati.hrmsuite.Helper.ResponseModel;
import com.harati.hrmsuite.LoginPackage.LoginModel.LoginModel;
import com.harati.hrmsuite.MainPackage.Acitivity.MainActivity;
import com.harati.hrmsuite.R;
import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;
import com.harati.hrmsuite.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 8/9/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText hrm_username, hrm_password;
    TextInputLayout username, password;
    Button hrm_login_btn;
    ApiInterface apiInterface;
    UserSessionManager userSessionManager;
    ProgressBar progress;
    TextView resetPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrm_login);
        apiInterface = RetrofitClient.getApiService();
        userSessionManager = new UserSessionManager(getApplicationContext());
        hrm_login_btn = (Button) findViewById(R.id.hrm_login_btn);
        hrm_username = (EditText) findViewById(R.id.hrm_username);
        hrm_password = (EditText) findViewById(R.id.hrm_password);
        username = (TextInputLayout) findViewById(R.id.username);
        password = (TextInputLayout) findViewById(R.id.password);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        resetPassword = (TextView) findViewById(R.id.resetPassword);
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "Lato-Light.ttf");
        hrm_username.setTypeface(typeface);
        hrm_password.setTypeface(typeface);
        hrm_login_btn.setTypeface(typeface);
        hrm_login_btn.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        if (userSessionManager.isUserLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("title", "");
            startActivity(intent);
            finish();
        }

        username.setErrorEnabled(true);
        password.setErrorEnabled(true);
        hrm_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                username.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hrm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hrm_login_btn:
                doLoginProcess();
                break;
            case R.id.resetPassword:
                startActivity(new Intent(LoginActivity.this, PasswordRecoveryActivity.class));
                break;
        }


    }

    public void doLoginProcess() {
        assert hrm_username != null;
        String hrm_username_string = hrm_username.getText().toString();
        assert hrm_password != null;
        String hrm_password_string = hrm_password.getText().toString();
        if (hrm_username_string.isEmpty() && hrm_password_string.isEmpty()) {
            username.setError("This field cannot be left empty");
            hrm_username.requestFocus();
            password.setError("This field cannot be left empty");
            hrm_password.requestFocus();
        } else if (hrm_username_string.isEmpty()) {
            username.setError("This field cannot be left empty");
            hrm_username.requestFocus();
        } else if (hrm_password_string.isEmpty()) {
            password.setError("This field cannot be left empty");
            hrm_password.requestFocus();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(hrm_username.getText().toString()).matches()) {
            username.setError("Username must be in email pattern." + "\n" + "eg. example@example.com");
            hrm_username.requestFocus();
        } else {
            progress.setVisibility(View.VISIBLE);
            hrm_login_btn.setVisibility(View.GONE);
            authenticateLogin(hrm_username_string, hrm_password_string);
        }
    }

    public void authenticateLogin(String username, final String password) {
        if (NetworkManager.isConnected(getApplicationContext())) {
            Call<LoginModel> call = apiInterface.authenticate(username, password);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getMsgTitle().equals("Success")) {
                            Log.d("data---", response.body().getAccess_token());
                            userSessionManager.saveUserInformation(response.body().getUserCode(), response.body().getAccess_token());
                            userSessionManager.setKeyLanguage("English");
                            final String usercode = response.body().getUserCode();
                            final String accessToken = response.body().getAccess_token();
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                        while (refreshedToken == null)//this is used to get firebase token until its null so it will save you from null pointer exeption
                                        {
                                            refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                        }
                                        Log.d("Firbase id login", "Refreshed token: " + refreshedToken);
                                        registerToken(usercode, accessToken, refreshedToken);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            thread.start();
                            Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("title", "");
                            startActivity(intent);
                            finish();

                        } else {
                            progress.setVisibility(View.GONE);
                            hrm_login_btn.setVisibility(View.VISIBLE);
                            Snackbar snackbar = Snackbar.make(progress, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                    } else {
                        progress.setVisibility(View.GONE);
                        hrm_login_btn.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(progress, "Error in server end", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    hrm_login_btn.setVisibility(View.VISIBLE);
                    Snackbar snackbar = Snackbar.make(progress, t.getMessage(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
//                    Toast.makeText(LoginActivity.this,  t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    try {
                        Log.d("",t.getMessage());
                    }catch (Exception e){
//                        Log.d("",t.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        } else {
            progress.setVisibility(View.GONE);
            hrm_login_btn.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(progress, "No Internet Connection", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }

    public void registerToken(final String userCode, final String accessToken, final String firebasToken) {
        Log.d("success", "hapyyy-----");
        Call<ResponseModel> call = apiInterface.saveFirebaseToken(userCode, accessToken, firebasToken);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful())
                    Log.d("success", "hapyyy");
                else
                    Log.d("unSuccess", "unHapyyy");
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d("failed", "sad");
            }
        });

    }
}
