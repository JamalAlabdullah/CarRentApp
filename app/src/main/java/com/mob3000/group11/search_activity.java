package com.mob3000.group11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.function.IntToDoubleFunction;

public class search_activity extends AppCompatActivity {

    Button search_button;
    Button dateButton, timeButton, dateButton1, timeButton1;
    TextView dateView, timeView, timeView1,dateView1;;
    FirebaseAuth mAuth;

    private SharedPreferences bookingPref;
    private String bookingPrefFile =  "com.mob3000.group11.booking";

    Calendar pickupCalendar = Calendar.getInstance();
    Calendar dropoffCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        // *********** Shared Preference **************
        bookingPref = getSharedPreferences(bookingPrefFile, MODE_PRIVATE);


        search_button = findViewById(R.id.search_button);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        dateView = findViewById(R.id.dateView);
        timeView = findViewById(R.id.timeView);
        dateButton1 = findViewById(R.id.dateButton1);
        timeButton1 = findViewById(R.id.timeButton1);
        dateView1 = findViewById(R.id.dateView1);
        timeView1 = findViewById(R.id.timeView1);

        mAuth.signOut();

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go2bookingsearch(view);
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }

        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });

        dateButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton1();
            }
        });
        timeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton1();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = bookingPref.edit();

        long pickupMil = pickupCalendar.getTimeInMillis();
        long dropofMil = dropoffCalendar.getTimeInMillis();
        editor.putLong("pickupMil", pickupMil);
        editor.putLong("dropoffMil", dropofMil);
        editor.apply();

    }

    private void handleTimeButton1() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.i("State", "onRestart" + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                dropoffCalendar.set(10, hour); //************************************************
                dropoffCalendar.set(12, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                timeView1.setText(dateText);
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();
    }

    private void handleDateButton1() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                dropoffCalendar.set(year, month, date); //***************************************************
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                dateView1.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.i("State", "onRestart" + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                pickupCalendar.set(10, hour); //************************************************
                pickupCalendar.set(12, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                timeView.setText(dateText);
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();
    }

    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                pickupCalendar.set(year, month, date); //***************************************************
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                dateView.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    public void go2bookingsearch(View view) {

        if(checkDates()) {
            Intent search_intent = new Intent(this,booking_activity.class);
            startActivity(search_intent);
        } else {
            Toast wrongDate = Toast.makeText(getApplicationContext(), "You should choose Pick-up and return date", Toast.LENGTH_LONG);
            wrongDate.show();
        }
    }

    private boolean checkDates(){ // ******** Check pickup is before dropoff

        Calendar today = Calendar.getInstance();

        if (pickupCalendar.after(today) && dropoffCalendar.after(today) && pickupCalendar.compareTo(dropoffCalendar) < 0) {
            return true;
        }
        return  false;

    } //************************ check dates chosen ******************



    // this function for menubar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bar_with_log_ut, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    // this function to activate the item we choose from the menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.contact_us){
            Intent intent = new Intent(search_activity.this,Contact.class);
            startActivity(intent);
            return true;
        }if (id == R.id.about_us){
            Intent intent = new Intent(search_activity.this,AboutUs.class);
            startActivity(intent);
            return true;

        }if (id == R.id.log_out){
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(search_activity.this,MainActivity.class);
            startActivity(intent);
            return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
