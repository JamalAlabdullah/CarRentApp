package com.mob3000.group11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup_activity extends AppCompatActivity {

    Button btnRegister;
    TextInputEditText regEmail,regPass,confPass;


    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // to put the title in the center  of menu bar.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRegister = findViewById(R.id.btnRegister);
        regEmail = findViewById(R.id.regEmail);
        regPass = findViewById(R.id.regPass);
        confPass = findViewById(R.id.confPass);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();
            }
        });
    }
    private  void createUser(){
        String email = regEmail.getText().toString();

        String password = regPass.getText().toString();
        String confPassword = confPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            regEmail.setError("Email can not be empty");
            regEmail.requestFocus();

        }else if (TextUtils.isEmpty(password)){
            regPass.setError("Password can not be empty");
            regPass.requestFocus();

        }else if(!confPassword.equals(password)){
            regPass.setError("Password does not match. Try again");
            regPass.requestFocus();

        }else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(signup_activity.this,"Registration is successful",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(signup_activity.this,signin_activity.class));
                    }else{
                        Toast.makeText(signup_activity.this,"Registration error" +
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // this function for menubar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    // this function to activate the item we choose from the menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.contact_us){
            Intent intent = new Intent(signup_activity.this,Contact.class);
            startActivity(intent);
            return true;
        }if (id == R.id.about_us){
            Intent intent = new Intent(signup_activity.this,AboutUs.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




















}