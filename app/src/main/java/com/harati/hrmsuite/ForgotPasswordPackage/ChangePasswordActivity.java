package com.harati.hrmsuite.ForgotPasswordPackage;

import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.harati.hrmsuite.Helper.NetworkManager;
import com.harati.hrmsuite.Helper.ProgressHelper;
import com.harati.hrmsuite.Helper.ResponseModel;
import com.harati.hrmsuite.R;
import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;
import com.harati.hrmsuite.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{
    EditText oldPasswordEdit;
    EditText newPasswordEdit;
    EditText confirmPasswordEdit;
    Button updatePassword;
    ProgressBar progress;
    CoordinatorLayout coordinatorLayout;
    UserSessionManager userSessionManager;
    private ApiInterface apiInterface;
    ProgressHelper progressHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressHelper = new ProgressHelper(ChangePasswordActivity.this);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        userSessionManager= new UserSessionManager(getApplicationContext());
        apiInterface= RetrofitClient.getApiService();
        coordinatorLayout= (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        oldPasswordEdit= (EditText)findViewById(R.id.oldPassword);
        newPasswordEdit= (EditText)findViewById(R.id.newPassword);
        confirmPasswordEdit= (EditText)findViewById(R.id.confirmPassword);
        updatePassword= (Button) findViewById(R.id.updatePassword);
        progress= (ProgressBar) findViewById(R.id.progressBar);
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "Lato-Light.ttf");
        oldPasswordEdit.setTypeface(typeface);
        newPasswordEdit.setTypeface(typeface);
        confirmPasswordEdit.setTypeface(typeface);
        updatePassword.setTypeface(typeface);
        updatePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.updatePassword:
                doProcess();
                break;
        }

    }


    public void doProcess(){
        assert oldPasswordEdit != null;
        assert newPasswordEdit != null;
        assert confirmPasswordEdit != null;
        String hrm_old_password = oldPasswordEdit.getText().toString();
        String hrm_new_password = newPasswordEdit.getText().toString();
        String hrm_confirm_password = confirmPasswordEdit.getText().toString();

        if (hrm_old_password.isEmpty()&&!hrm_new_password.isEmpty()&&!hrm_confirm_password.isEmpty()) {
            oldPasswordEdit.setError("This field cannot be left empty");
            oldPasswordEdit.requestFocus();
        }
        else if(!hrm_old_password.isEmpty()&&hrm_new_password.isEmpty()&&!hrm_confirm_password.isEmpty()){
            newPasswordEdit.setError("This field cannot be left empty");
            newPasswordEdit.requestFocus();
        }
        else if(!hrm_old_password.isEmpty()&&!hrm_new_password.isEmpty()&&hrm_confirm_password.isEmpty()){
            confirmPasswordEdit.setError("This field cannot be left empty");
            confirmPasswordEdit.requestFocus();
        }else if(hrm_old_password.isEmpty()&&hrm_new_password.isEmpty()&&hrm_confirm_password.isEmpty()){
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please fill all the above field", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }else if(!hrm_old_password.isEmpty()&&!hrm_new_password.equals(hrm_confirm_password)){
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "New password field and confirm password filed must contain same password", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }else {
            progress.setVisibility(View.VISIBLE);
            updatePassword.setVisibility(View.GONE);
            processReset(hrm_old_password,hrm_new_password);
        }

    }

    public void processReset(final String oldPassword, final String newPassword) {
        if(NetworkManager.isConnected(getApplicationContext())) {
            progressHelper.showProgressDialog("Changing your Password");
            Call<ResponseModel> call = apiInterface.updatePassword(userSessionManager.getKeyUsercode(),userSessionManager.getKeyAccessToken(),oldPassword,newPassword);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    progressHelper.hideProgressDialog();
                    if(response.isSuccessful()){
                    if (response.body().getMsgTitle().equals("Success")) {
                        progress.setVisibility(View.GONE);
                        updatePassword.setVisibility(View.VISIBLE);
                        oldPasswordEdit.setText("");
                        newPasswordEdit.setText("");
                        confirmPasswordEdit.setText("");
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                        snackbar.show();

                    } else {
                        progress.setVisibility(View.GONE);
                        updatePassword.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        updatePassword.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(progress, "Error in server end", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    progressHelper.hideProgressDialog();
                    progress.setVisibility(View.GONE);
                    updatePassword.setVisibility(View.VISIBLE);
                    oldPasswordEdit.setText("");
                    newPasswordEdit.setText("");
                    confirmPasswordEdit.setText("");
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, t.getMessage().toString(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            });
        }
        else {
            progress.setVisibility(View.GONE);
            updatePassword.setVisibility(View.VISIBLE);
            oldPasswordEdit.setText("");
            newPasswordEdit.setText("");
            confirmPasswordEdit.setText("");
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
