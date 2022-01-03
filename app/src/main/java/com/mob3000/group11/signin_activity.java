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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signin_activity extends AppCompatActivity {
    Button btn_login;
    TextView textView;
    TextInputEditText editTextTextPersonName, editTextTextPassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // to put the title in the center  of menu bar.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        btn_login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LogInUser();
            }
        });
        textView=(TextView) findViewById(R.id.btn_forgotPass);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signin_activity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });


    }
    private void LogInUser() {
        String email = editTextTextPersonName.getText().toString();
        String password = editTextTextPassword.getText().toString();
        if (TextUtils.isEmpty(email)){
            editTextTextPersonName.setError("Email can not be empty");
            editTextTextPersonName.requestFocus();

        }else if (TextUtils.isEmpty(password)){
            editTextTextPassword.setError("Password can not be empty");
            editTextTextPassword.requestFocus();
        }else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(signin_activity .this,"User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(signin_activity.this,search_activity.class));
                    }else{
                        Toast.makeText(signin_activity.this,"SignIn error " +
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //----------------------------------------------------------------
            //----------------------------------------------------------------

        }  //   we add this if statment just for Salah to use it,
        if (TextUtils.equals(email,"user1") && TextUtils.equals(password,"pass123")){
            startActivity(new Intent(signin_activity.this,search_activity.class));
        }
            //----------------------------------------------------------------
            //----------------------------------------------------------------
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
            Intent intent = new Intent(signin_activity.this,Contact.class);
            startActivity(intent);
            return true;
        }if (id == R.id.about_us){
            Intent intent = new Intent(signin_activity.this,AboutUs.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

