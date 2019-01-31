package com.jct.davidandyair.androiddriver5779_1395_8250.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private CheckBox rememberMe;

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

        loadSharedPreferences();

        usName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(usName, InputMethodManager.SHOW_IMPLICIT);
    }

    private void findViews()
    {
        usName = findViewById(R.id.username);
        pssd = findViewById(R.id.password);
        rememberMe = findViewById(R.id.remember_me);
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
                                             if(rememberMe.isChecked())
                                                 saveSharedPreferences();
                                             else
                                                 clearSharedPreferences();

                                             Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                             //Clear all the other activities
                                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                             intent.putExtra("driver",dr);
                                             startActivity(intent);
                                             finish();
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

    //region Shared Preference
    private void saveSharedPreferences()
    {
        try
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String mail = usName.getText().toString();
            String password = pssd.getText().toString();
            editor.putString("MAIL", mail);
            editor.putString("PASSWORD", password);
            editor.commit();
            Toast.makeText(this, "save name and age Preferences", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "failed to save Preferences", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSharedPreferences()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains("MAIL"))
        {
            usName.setText(sharedPreferences.getString("MAIL", null));
            Toast.makeText(this, "load mail", Toast.LENGTH_SHORT).show();
            rememberMe.setChecked(true);
        }
        if (sharedPreferences.contains("PASSWORD"))
        {
            pssd.setText(sharedPreferences.getString("PASSWORD", null));
            Toast.makeText(this, "load password", Toast.LENGTH_SHORT).show();
            rememberMe.setChecked(true);
        }
    }

    private void clearSharedPreferences()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(this, "clear Preferences", Toast.LENGTH_SHORT).show();
    }
    //endregion
}