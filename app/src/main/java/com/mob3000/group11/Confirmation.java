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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class Confirmation extends AppCompatActivity {
    public static final String NAME_MESSAGE = "com.mob3000.group11.name.MESSAGE";
    public static final String PHONE_MESSAGE = "com.mob3000.group11.phone.MESSAGE";
    public static final String EMAIL_MESSAGE = "com.mob3000.group11.email.MESSAGE";
    //public static final String ORDER_MESSAGE = "com.mob3000.group11.order.MESSAGE";


    private EditText full_name;
    private EditText enter_email;
    private EditText enter_phone;
    CheckBox checkBox;
    Button btn_confirm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);


        // to put the title in the center  of menu bar.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        full_name = findViewById(R.id.full_name);
        enter_email = findViewById(R.id.enter_email);
        enter_phone = findViewById(R.id.enter_phone);
        checkBox = findViewById(R.id.checkBox);

        btn_confirm = findViewById(R.id.btn_confirm);




        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (error()) {
                    gotoOrderInformation(view);
                }
            }
        });
    }

      // Checking checkbox validation

    private boolean error() {
        if (full_name.getText().toString().length() == 0) {
            Toast.makeText(Confirmation.this, "Please enter your name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (enter_email.getText().toString().length() == 0) {
            Toast.makeText(Confirmation.this, "Please enter your email", Toast.LENGTH_LONG).show();
            return false;
        }
        if (enter_phone.getText().toString().length() == 0) {
            Toast.makeText(Confirmation.this, "Please enter your mobile number", Toast.LENGTH_LONG).show();
            return false;
        }
     else if (!checkBox.isChecked()) {
            Toast.makeText(Confirmation.this, "Please select our Terms & Conditions", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    public void gotoOrderInformation(View view){

      Intent intent = new Intent(Confirmation.this, OrderInformation.class);
        String namemessage = full_name.getText().toString();
        intent.putExtra(NAME_MESSAGE,namemessage);

        String emailmessage = enter_email.getText().toString();
        intent.putExtra(PHONE_MESSAGE,emailmessage);

        String phonemessage = enter_phone.getText().toString();
        intent.putExtra(EMAIL_MESSAGE,phonemessage);


        startActivity(intent);



    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bar_with_log_ut, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    // this function to activate the item we choose from the menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.contact_us){
            Intent intent = new Intent(Confirmation.this,Contact.class);
            startActivity(intent);
            return true;
        }if (id == R.id.about_us){
            Intent intent = new Intent(Confirmation.this,AboutUs.class);
            startActivity(intent);
            return true;
        }if (id == R.id.log_out){
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(Confirmation.this,MainActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }


}