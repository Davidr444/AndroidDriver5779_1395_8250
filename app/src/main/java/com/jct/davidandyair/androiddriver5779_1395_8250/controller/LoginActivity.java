package com.jct.davidandyair.androiddriver5779_1395_8250.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private String userName;
    private String password;

    private boolean checkIdentity(String usN, String p)
    {
        //needs to check on the firebase whether the details are correct!

        return true;
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
        enter = findViewById(R.id.button);
        signIn = findViewById(R.id.label);

        enter.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         userName = usName.getText().toString();
                                         password = pssd.getText().toString();

                                         if (checkIdentity(userName, password))
                                         {
                                             Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                             startActivity(intent);
                                         }

                                         else return; // needs to print any problem message

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
