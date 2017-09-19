package com.harati.hrmsuite.ForgotPasswordPackage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.harati.hrmsuite.Helper.NetworkManager;
import com.harati.hrmsuite.Helper.ResponseModel;
import com.harati.hrmsuite.R;
import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;
import com.harati.hrmsuite.Retrofit.RetrofiltClient.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordRecoveryActivity extends AppCompatActivity {
    EditText hrm_email;
    TextInputLayout email;
    Button hrm_reset_btn;
    TextView back;
    ApiInterface apiInterface;
    ProgressBar progress;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        apiInterface = RetrofitClient.getApiService();
        coordinatorLayout= (CoordinatorLayout)findViewById(R.id.main);
        hrm_reset_btn = (Button) findViewById(R.id.hrm_reset_btn);
        progress= (ProgressBar)findViewById(R.id.progressBar);
        hrm_email = (EditText) findViewById(R.id.hrm_email);
        email = (TextInputLayout) findViewById(R.id.email);
        back= (TextView) findViewById(R.id.back);
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "Lato-Light.ttf");
        hrm_email.setTypeface(typeface);
        hrm_reset_btn.setTypeface(typeface);
        email.setErrorEnabled(true);
        hrm_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hrm_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doProcess();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

    }
    public void doProcess(){
        assert hrm_email != null;
        String hrm_email_string = hrm_email.getText().toString();

        if (hrm_email_string.isEmpty()) {
            email.setError("This field cannot be left empty");
            hrm_email.requestFocus();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(hrm_email.getText().toString()).matches()){
            email.setError("Enter email must be in email pattern."+"\n"+ "eg. example@example.com");
            hrm_email.requestFocus();
        }else {
            progress.setVisibility(View.VISIBLE);
            hrm_reset_btn.setVisibility(View.GONE);
            processReset(hrm_email_string);
        }

    }

    public void processReset(String email) {
        if(NetworkManager.isConnected(getApplicationContext())) {
            Call<ResponseModel> call = apiInterface.reset(email);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(response.isSuccessful()){
                    if (response.body().getMsgTitle().equals("Success")) {
                        progress.setVisibility(View.GONE);
                        hrm_reset_btn.setVisibility(View.VISIBLE);
                        hrm_email.setText("");
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                        snackbar.show();

                    } else {
                        progress.setVisibility(View.GONE);
                        hrm_reset_btn.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        hrm_reset_btn.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(progress, "Error in server end", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    hrm_reset_btn.setVisibility(View.VISIBLE);
                    hrm_email.setText("");
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, t.getMessage().toString(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            });

        }
        else {
            progress.setVisibility(View.GONE);
            hrm_reset_btn.setVisibility(View.VISIBLE);
            hrm_email.setText("");
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
