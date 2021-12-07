package com.example.seniorsafety;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.etPhoneNumber=(EditText)findViewById(R.id.phoneNumberRegister);
        etPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }
}