package com.jct.davidandyair.androiddriver5779_1395_8250.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usName;
    private EditText pssd;
    private TextView signIn;
    private Button enter;
    private String mail;
    private String password;

    /*
     *
     */
    private boolean checkIdentity(String usN, String p)
    {
        //TODO: needs to check on the firebase whether the details are correct!

        return true;
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

    /*
     *
     */
    private void findViews()
    {
        usName = findViewById(R.id.username);
        pssd = findViewById(R.id.password);
        enter = findViewById(R.id.enter);
        signIn = findViewById(R.id.label);

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


                                         if (checkIdentity(mail, password))
                                         {
                                             Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                             //Clear all the other activities
                                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                             startActivity(intent);
                                         }

                                         else
                                         {
                                             TextView incorrect_details = findViewById(R.id.incorrect_details);
                                             incorrect_details.setVisibility(View.VISIBLE);
                                             return;
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