package com.mob3000.group11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.internal.DiskLruCache;


import org.w3c.dom.Document;

import java.util.ArrayList;

public class Reservation extends AppCompatActivity {

    ImageView imgMakeRes;
    TextView pricMakeRes;
    TextView seatMakeRes;
    TextView girMakeRes;
    TextView brandMakeRes;
    private static final String TAG = "Reservation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        // to put the title in the center  of menu bar.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        // set the information we have from booking class to show them in Reservation
        brandMakeRes = (TextView) findViewById(R.id.brandMakeReservation);
        Intent brandIntent= getIntent();
        String brandData = brandIntent.getStringExtra("brandMessage");
        brandMakeRes.setText(brandData);

        seatMakeRes = (TextView) findViewById(R.id.seatMakeReservation);
        Intent seatIntent= getIntent();
        String seatData = seatIntent.getStringExtra("seatsMessage");
        seatMakeRes.setText(seatData);

        girMakeRes = (TextView) findViewById(R.id.girMakeReservation);
        Intent gearIntent= getIntent();
        String gearData = gearIntent.getStringExtra("gearMessage");
        girMakeRes.setText(gearData);

        pricMakeRes = (TextView) findViewById(R.id.pricMakereservation);
        Intent priceIntent= getIntent();
        String priceData = priceIntent.getStringExtra("priceMessage");
        pricMakeRes.setText(priceData);

        imgMakeRes = (ImageView) findViewById(R.id.imgMakeReservation);
        Intent imageIntent= getIntent();
        String imageData = imageIntent.getStringExtra("imageMessage");

        Glide.with(this).load(imageData).into(imgMakeRes);


    }

    // ----------------------------------------------------------------------------
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bar_with_log_ut, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void goToConfirmation(View view) {
        Intent intent = new Intent(Reservation.this, Confirmation.class);
        startActivity(intent);
    }
    // this function to activate the item we choose from the menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.contact_us){
            Intent intent = new Intent(Reservation.this,Contact.class);
            startActivity(intent);
            return true;
        }if (id == R.id.about_us){
            Intent intent = new Intent(Reservation.this,AboutUs.class);
            startActivity(intent);
            return true;
        }if (id == R.id.log_out){
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(Reservation.this,MainActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}