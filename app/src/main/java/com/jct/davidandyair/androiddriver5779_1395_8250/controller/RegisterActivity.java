package com.jct.davidandyair.androiddriver5779_1395_8250.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FactoryBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.IBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

public class RegisterActivity extends AppCompatActivity {


    private EditText fName;
    private EditText lName;
    private EditText id;
    private EditText phoneNumber;
    private EditText emailAddress;
    private EditText creditCardNumber;
    private EditText password;
    private Button button;
    private IBackend backend;
    private Driver driver;
    private AsyncTask<Driver, Driver, Driver> asyncTask;

    //TODO:is it necessary?
    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
    }


    /*
     *Check that all fields have been filled in,
     *and check the integrity of the email and password
     */
    private Boolean checkDetailsIntegrity()
    {
        Boolean formIsComplete = true;
        if(TextUtils.isEmpty(fName.getText()))
        {
            fName.setError(getText(R.string.empty_field));
            formIsComplete = false;
        }
        if(TextUtils.isEmpty(lName.getText()))
        {
            lName.setError(getText(R.string.empty_field));
            formIsComplete = false;
        }
        if(TextUtils.isEmpty(id.getText()))
        {
            id.setError(getText(R.string.empty_field));
            formIsComplete = false;
        }
        if(TextUtils.isEmpty(phoneNumber.getText()))
        {
            phoneNumber.setError(getText(R.string.empty_field));
            formIsComplete = false;
        }
        if(TextUtils.isEmpty(emailAddress.getText()))
        {
            emailAddress.setError(getText(R.string.empty_field));
            formIsComplete = false;
        }
        if(TextUtils.isEmpty(creditCardNumber.getText()))
        {
            creditCardNumber.setError(getText(R.string.empty_field));
            formIsComplete = false;
        }
        if(TextUtils.isEmpty(password.getText()))
        {
            password.setError(getText(R.string.empty_field));
            formIsComplete = false;
        }
        //If one of the fields is empty
        if(!formIsComplete)
            return false;

        //Check the integrity of mail via regex
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress.getText()).matches())
        {
            emailAddress.setError(getText(R.string.error_invalid_email));
            return false;
        }

        if(password.getText().length() < 6)
        {
            password.setError("Password must contain at least 6 characters");
            return false;
        }

        if(id.getText().length() != 9)
        {
            id.setError("invalid ID");
            return false;
        }
        return true;
    }

    /*
     *
     */
    private void findViews()
    {
        fName = findViewById(R.id.first_name);
        lName = findViewById(R.id.last_name);
        id = findViewById(R.id.id);
        phoneNumber = findViewById(R.id.phone_number);
        emailAddress = findViewById(R.id.email);
        creditCardNumber = findViewById(R.id.credit_card);
        password = findViewById(R.id.register_password);
        button = findViewById(R.id.button);
        backend = FactoryBackend.getBackend();
        asyncTask = new AsyncTask<Driver, Driver, Driver>() {
            @Override
            protected Driver doInBackground(Driver... drivers) {
                backend.addDriver(drivers[0], null);
                return null;
            }
        };

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        checkDetailsIntegrity();

                        driver = new Driver();

                        driver.setFirstName(fName.getText().toString());
                        driver.setLastName(lName.getText().toString());
                        driver.setId(Integer.parseInt(id.getText().toString()));
                        driver.setPhoneNumber(phoneNumber.getText().toString());
                        driver.setEmailAddress(emailAddress.getText().toString());
                        driver.setPassword(password.getText().toString());
                        driver.setCreditCardNumber(Integer.parseInt(creditCardNumber.getText().toString()));
                        asyncTask.execute(driver);

                        //TODO: we need to push the information into our backend!!! using asynctask

                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        //Clear all the other activities
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );
    }
}
