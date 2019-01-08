package com.jct.davidandyair.androiddriver5779_1395_8250.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

public class RegisterActivity extends AppCompatActivity {


    private EditText fName;
    private EditText lName;
    private EditText id;
    private EditText phoneNumber;
    private EditText emailAddress;
    private EditText creditCardNumber;
    private Button button;

    private Driver driver;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
    }

    private void findViews()
    {
        fName = findViewById(R.id.first_name);
        lName = findViewById(R.id.last_name);
        id = findViewById(R.id.id);
        phoneNumber = findViewById(R.id.phone_number);
        emailAddress = findViewById(R.id.email);
        creditCardNumber = findViewById(R.id.credit_card);
        button = findViewById(R.id.button);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        driver = new Driver();

                        driver.setFirstName(fName.getText().toString());
                        driver.setLastName(lName.getText().toString());
                        driver.setId(Integer.parseInt(id.getText().toString()));
                        driver.setPhoneNumber(phoneNumber.getText().toString());
                        driver.setEmailAddress(emailAddress.getText().toString());
                        driver.setCreditCardNumber(Integer.parseInt(creditCardNumber.getText().toString()));

                        // we need to push the information into our backend!!! using asynctask


                    }
                }
        );
    }
}
