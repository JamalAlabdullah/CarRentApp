package com.mob3000.group11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnSignIn, btnSignUp; //handlers
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // to put the title in the center  of menu bar.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title);



        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go2signIn(view);

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go2singUp(view);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(MainActivity.this,signin_activity.class));
        }
    }




    public void go2signIn(View view) {

        Intent intentSignIn = new Intent(this, signin_activity.class);
        startActivity(intentSignIn);

    }

    public void go2singUp(View view) {

        Intent intentSignUp = new Intent(this, signup_activity.class);
        startActivity(intentSignUp);
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
            Intent intent = new Intent(MainActivity.this,Contact.class);
            startActivity(intent);
            return true;
        }if (id == R.id.about_us){
            Intent intent = new Intent(MainActivity.this,AboutUs.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

