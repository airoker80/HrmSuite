package com.harati.hrmsuite.Activity.Register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.harati.hrmsuite.R;

public class AfterRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_registration);
        if (getIntent().getStringExtra("isUser").equals("Y")){

        }
    }
}
