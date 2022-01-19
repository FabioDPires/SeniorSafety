package com.example.seniorsafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText etPhoneNumber, etName, etEmail, etPassword, etPasswordConfirm;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.etPhoneNumber = (EditText) findViewById(R.id.phoneNumberRegister);
        //etPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        this.etName = (EditText) findViewById(R.id.nameRegister);
        this.etEmail = (EditText) findViewById(R.id.emailRegister);
        this.etPassword = (EditText) findViewById(R.id.passwordRegister);
        this.etPasswordConfirm = (EditText) findViewById(R.id.passwordConfirm);
        this.registerButton = (Button) findViewById(R.id.registerButton);

        this.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString().trim();
                final String password=etPassword.getText().toString();
                final String passwordConfirm=etPasswordConfirm.getText().toString();
                final String name=etName.getText().toString();
                final String phoneNumber=etPhoneNumber.getText().toString().trim();
                boolean invalidInfo=false;

                if(TextUtils.isEmpty(name)){
                    etName.setError("Please provide your name");
                    invalidInfo=true;
                }
                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Please provide your email");
                    invalidInfo=true;
                }

                if(TextUtils.isEmpty(phoneNumber)){
                    etPhoneNumber.setError("Please provide your phone number");
                }

                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Please provide your password");
                    invalidInfo=true;
                }

                if(TextUtils.isEmpty(passwordConfirm)){
                    etPasswordConfirm.setError("Please confirm your password");
                    invalidInfo=true;
                }

                if(password.length()<6){
                    etPassword.setError("Password must be at least 6 characters long");
                    return;
                }

                if(!password.equals(passwordConfirm)){
                    etPassword.setError("Passwords must match");
                    etPasswordConfirm.setError("Passwords must match");
                    invalidInfo=true;
                }

                if(invalidInfo){
                    return;
                }

                firebaseAuth=FirebaseAuth.getInstance();
                firestore=FirebaseFirestore.getInstance();

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            userID = user.getUid();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            user.updateProfile(profileUpdates);
                            //DocumentReference documentReference = firestore.collection("users").document(userID);
                            /*Map<String,Object> user = new HashMap<>();
                            user.put("name",name);
                            user.put("email", email);
                            user.put("phoneNumber", phoneNumber);
                            user.put("DOB", stringDOB);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            });*/
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                        }else{
                            Toast.makeText(RegisterActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private String getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(calendar.getTime());
        return dateString;
    }
}