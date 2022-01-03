package com.mob3000.group11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPass;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // to put the title in the center  of menu bar.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        emailEditText = findViewById(R.id.EmailAddress);
        resetPass = findViewById(R.id.btn_forgotpassword);
        progressBar =  findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPsw();
            }
        });



    }
    private void resetPsw() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please provide valid email");
            emailEditText.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ForgotPassword.this, signin_activity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(ForgotPassword.this, "Please enter the email registered", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
