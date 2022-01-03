package com.mob3000.group11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Random;

public class OrderInformation extends AppCompatActivity {
    TextView ordernummer;
    private static final String TAG = "OrderInformation";
    TextView showPickUp;

    /* ######################################3333 */
    Calendar pickupCal = Calendar.getInstance();
    Calendar dropoffCal = Calendar.getInstance();

    private SharedPreferences bookingPref;
    private String bookingPrefFile =  "com.mob3000.group11.booking";
/* #######################################################3 */



    //---------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);

        // to put the title in the center  of menu bar.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ordernummer= findViewById(R.id.ordernummer);





        /*
        search1.dateView.;
        showPickUp = findViewById(R.id.showPickUpDate);
       showPickUp.setText(dateText);

         */

        // Here we get the content of the Edit text (input from the user)
        Intent intentphone = getIntent();
        String phonemessage = intentphone.getStringExtra(Confirmation.PHONE_MESSAGE);
        Intent intentemail = getIntent();
        String emailmessage = intentemail.getStringExtra(Confirmation.EMAIL_MESSAGE);

        Intent intentname = getIntent();
        String namemessage = intentname.getStringExtra(Confirmation.NAME_MESSAGE);

        // *********** Shared Preference **************
        bookingPref = getSharedPreferences(bookingPrefFile, MODE_PRIVATE);

        long pickupMil = bookingPref.getLong("pickupMil", 7258028399000L);
        long dropoffMil = bookingPref.getLong("dropoffMil", 7258114799000L);
//  ********************************************************************************************


        // Here we show the result of Edit text to show it in output text
        TextView textViewname = findViewById(R.id.fullnamebook);
        textViewname.setText(namemessage);

        TextView textViewphone = findViewById(R.id.phonebook);
        textViewphone.setText(phonemessage);
        TextView textViewemail = findViewById(R.id.emailbook);
        textViewemail.setText(emailmessage);

        Random random = new Random();
        int rand = random.nextInt(14891-1)+10455;
        ordernummer.setText(Integer.toString(rand));

        /*
        TextView textPickUpDate = findViewById(R.id.showPickUpDate);
        textPickUpDate.setText(search_activity.handleGetDateButton());
        TextView textReturnDate = findViewById(R.id.showReturnDate);
        textReturnDate.setText(emailmessage);
         */

        TextView textPickUpDate = findViewById(R.id.showPickUpDate);
        TextView textReturnDate = findViewById(R.id.showReturnDate);
        pickupCal.setTimeInMillis(pickupMil);
        dropoffCal.setTimeInMillis(dropoffMil);
        String pickupString = pickupCal.getTime().toString();
        String dropoffString = dropoffCal.getTime().toString();

        textPickUpDate.setText(pickupString);
        textReturnDate.setText(dropoffString);






    }








    //---------------------------------------------------------------------------
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar_with_log_ut, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //---------------------------------------------------------------------------
    @Override
    // this function to activate the item we choose from the menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.contact_us) {
            Intent intent = new Intent(OrderInformation.this, Contact.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.about_us) {
            Intent intent = new Intent(OrderInformation.this, AboutUs.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.log_out) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(OrderInformation.this, MainActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

}