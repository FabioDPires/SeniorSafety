package com.example.seniorsafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextView tvCreateAccount;
    private EditText etEmail;
    private EditText etPassword;
    private Button buttonLoggin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.tvCreateAccount = (TextView) findViewById(R.id.textViewRegister);
        this.etEmail = (EditText) findViewById(R.id.email);
        this.etPassword = (EditText) findViewById(R.id.password);
        this.buttonLoggin = (Button) findViewById(R.id.login_button);
        this.firebaseAuth = FirebaseAuth.getInstance();

        this.buttonLoggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String providedEmail = etEmail.getText().toString().trim();
                String providedPassword = etPassword.getText().toString();
                boolean missingCredentials = false;
                if (TextUtils.isEmpty(providedEmail)) {
                    etEmail.setError("Please provide an email");
                    missingCredentials = true;
                }
                if (TextUtils.isEmpty(providedPassword)) {
                    etPassword.setError("Please provide your password");
                    missingCredentials = true;
                }
                if (missingCredentials) {
                    return;
                }

                signIn(providedEmail, providedPassword);
            }
        });

        this.tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivityIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerActivityIntent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = this.firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent skipLogin = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(skipLogin);
        }
    }

    private void signIn(String email, String password) {
        this.firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}