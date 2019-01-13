package com.jct.davidandyair.androiddriver5779_1395_8250.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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

    private boolean checkIdentity(String usN, String p)
    {
        //TODO: needs to check on the firebase whether the details are correct!

        return false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
    }

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

                                         //Check the integrity of mail via regex
                                         if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                                         {
                                             usName.setError(getText(R.string.error_invalid_email));
                                             return;
                                         }

                                         if (checkIdentity(mail, password))
                                         {
                                             Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
