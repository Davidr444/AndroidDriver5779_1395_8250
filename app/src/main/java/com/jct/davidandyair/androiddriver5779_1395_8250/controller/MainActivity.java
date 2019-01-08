package com.jct.davidandyair.androiddriver5779_1395_8250.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;

public class MainActivity extends AppCompatActivity {

    private EditText usName;
    private EditText pssd;
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
        setContentView(R.layout.activity_main);

        findViews();
    }

    private void findViews()
    {
        usName = findViewById(R.id.editText);
        pssd = findViewById(R.id.editText2);
        enter = findViewById(R.id.button);

        enter.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         userName = usName.getText().toString();
                                         password = pssd.getText().toString();

                                         checkIdentity(userName, password);
                                         //change activity ETC...
                                     }
                                 }

        );

    }
}
