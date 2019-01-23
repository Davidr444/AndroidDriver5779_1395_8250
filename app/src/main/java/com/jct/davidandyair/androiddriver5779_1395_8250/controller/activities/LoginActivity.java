package com.jct.davidandyair.androiddriver5779_1395_8250.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FactoryBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.IBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText usName;
    private EditText pssd;
    private TextView signIn;
    private Button enter;
    private String mail;
    private String password;
    private IBackend backend;

    @Nullable
    private Driver checkIdentity(String mail, String p){
        List<Driver> drivers = backend.getDrivers(null);

        for (Driver driver:drivers) {
            if(driver.getEmailAddress().equals(mail) && driver.getPassword().equals(p))
                return driver;
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();

        usName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(usName, InputMethodManager.SHOW_IMPLICIT);
    }

    private void findViews()
    {
        usName = findViewById(R.id.username);
        pssd = findViewById(R.id.password);
        enter = findViewById(R.id.enter);
        signIn = findViewById(R.id.label);
        backend = FactoryBackend.getBackend();

        enter.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                         mail = usName.getText().toString();
                                         password = pssd.getText().toString();

                                         //Check integrity
                                         Boolean formIsComplete = true;
                                         if(TextUtils.isEmpty(mail))
                                         {
                                             usName.setError(getText(R.string.error_empty_mail));
                                             formIsComplete = false;
                                         }
                                         if(TextUtils.isEmpty(password))
                                         {
                                             pssd.setError(getText(R.string.error_empty_password));
                                             formIsComplete = false;
                                         }
                                         //If one of the fields is empty
                                         if(!formIsComplete)
                                             return;

                                         Driver dr = checkIdentity(mail, password); // dr is the driver who logged in
                                         if (dr != null)
                                         {
                                             Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                             //Clear all the other activities
                                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                             intent.putExtra("driver",dr);
                                             startActivity(intent);
                                         }

                                         else
                                         {
                                             TextView incorrect_details = findViewById(R.id.incorrect_details);
                                             incorrect_details.setVisibility(View.VISIBLE);
                                         }
                                     }
                                 }

        );

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}