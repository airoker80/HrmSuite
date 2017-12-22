package com.harati.hrmsuite.Activity.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.harati.hrmsuite.LoginPackage.Activity.LoginActivity;
import com.harati.hrmsuite.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup register_option_rg;
    RadioButton company_rb,user_rb;
    EditText hrm_username_signup, hrm_password_signup;
    Button hrm_register_btn;
    TextView loginUser;
    String isUser="N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_register);
        hrm_username_signup = (EditText) findViewById(R.id.hrm_username_signup);
        hrm_password_signup = (EditText) findViewById(R.id.hrm_password_signup);

        hrm_register_btn = (Button) findViewById(R.id.hrm_register_btn);
        register_option_rg = (RadioGroup) findViewById(R.id.register_option_rg);

        loginUser = (TextView) findViewById(R.id.loginUser);

        hrm_register_btn.setOnClickListener(this);
        loginUser.setOnClickListener(this);
        register_option_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.company_rb:
                        isUser = "N";
                        break;
                    case R.id.user_rb:
                        isUser="Y";
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginUser:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.hrm_register_btn:
                checkRegisterFields(hrm_username_signup.getText().toString(),hrm_password_signup.getText().toString());
                break;
        }
    }
    private void checkRegisterFields(String username,String password){
        if (username.equals("")|password.equals("")){
            if (username.equals("")){
                hrm_username_signup.setError("Please Enter this Field");
            }else if (password.equals("")){
                hrm_password_signup.setError("Please Enter this Field");
            }
        }else {
            Intent intent = new Intent(this,AfterRegistration.class);
            intent.putExtra("isUser",isUser);
            startActivity(intent);
        }
    }
}
