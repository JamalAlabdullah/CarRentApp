package com.mob3000.group11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.Serializable;
import java.util.ArrayList;

public class booking_activity extends AppCompatActivity implements FirestoreAdapter.OnListItemClick {
    // Here is initializing for Firestore, recyclerview and Adapter.
    FirebaseFirestore firebaseFirestore;
    RecyclerView mFirestoreList;
    FirestoreAdapter adapter;

    //--------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        // calling function  adapterWithRecycle() which contains adapter and recyclerview----
        adapterWithRecycle();

        //--------------------------------------------------------------------------
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Here every Spinner takes its own functio.
        // we call function for the spinner to find Id and set adapters.
        priceSpinner();
        brandSpinner();
        girSpinner();
        seatsSpinner();
    }

    //--------------------------------------------------------------------------
    // onStop and onStart for recycle view
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    //--------------------------------------------------------------------------
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //--------------------------------------------------------------------------
    // In this function we set Query to present all the cars from Cars collection in Firestore
    // and connect them to Adapter
    protected void adapterWithRecycle() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.recview);
        Query query = firebaseFirestore.collection("cars");
        FirestoreRecyclerOptions<Cars> options = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(query, Cars.class)
                .build();
        adapter = new FirestoreAdapter(options, this);
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);
    }

    //--------------------------------------------------------------------------
    // Here we load the cars info to the intent by using putExtra
    @Override
    public void onItemClick(Cars snapshot, int position) {
        Intent intent = new Intent(booking_activity.this, Reservation.class);
        intent.putExtra("imageMessage", snapshot.getImgurl());
        intent.putExtra("priceMessage", String.valueOf(snapshot.getPriceWithLabel()));
        intent.putExtra("brandMessage", snapshot.getBrand());
        intent.putExtra("gearMessage", snapshot.getGir());
        intent.putExtra("seatsMessage", String.valueOf(snapshot.getSeatsWithLabel()));
        startActivity(intent);

    }

    //----------------------------------------------------------------------------------------------
    // this function for menubar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar_with_log_ut, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //--------------------------------------------------------------------------
    @Override
    // this function to activate the item we choose from the menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.contact_us) {
            Intent intent = new Intent(booking_activity.this, Contact.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.about_us) {
            Intent intent = new Intent(booking_activity.this, AboutUs.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.log_out) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(booking_activity.this, MainActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    //--------------------------------------------------------------------------
    // Here is the price spinner function where we set a query which will be compatible with what
    // the user choose in the spinner items. the same is for all the spinner
    protected void priceSpinner() {
        String[] priceItems = {"Low to high", "High to low"};
        AutoCompleteTextView autoCoText_price;
        ArrayAdapter<String> arrayAdapter_price;
        autoCoText_price = (AutoCompleteTextView) findViewById(R.id.autoCoText_price);
        arrayAdapter_price = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, priceItems);
        autoCoText_price.setAdapter(arrayAdapter_price);
        autoCoText_price.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String priceItem = parent.getItemAtPosition(position).toString();
                if (priceItem.equals("Low to high")) {
                    Query price_queryASC = firebaseFirestore.collection("cars")
                            .orderBy("price", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<Cars> options = new FirestoreRecyclerOptions.Builder<Cars>()
                            .setQuery(price_queryASC, Cars.class)
                            .build();
                    adapter.updateOptions(options);
                } else {
                    Query price_queryDESC = firebaseFirestore.collection("cars")
                            .orderBy("price", Query.Direction.DESCENDING);
                    FirestoreRecyclerOptions<Cars> DSCoptions = new FirestoreRecyclerOptions.Builder<Cars>()
                            .setQuery(price_queryDESC, Cars.class)
                            .build();
                    adapter.updateOptions(DSCoptions);
                }
            }
        });
    }

    //--------------------------------------------------------------------------
    protected void brandSpinner() {
        String[] brandItems = {"Ford", "BMW", "Audi", "Honda", "Nissan",
                "Toyota", "Wolksvagen", "Tesla", "Mazda", "Fiat"};
        AutoCompleteTextView autoCoText_brand;
        ArrayAdapter<String> arrayAdapter_brand;
        autoCoText_brand = (AutoCompleteTextView) findViewById(R.id.autoCoText_brand);
        arrayAdapter_brand = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, brandItems);
        autoCoText_brand.setAdapter(arrayAdapter_brand);
        autoCoText_brand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String brandItem = parent.getItemAtPosition(position).toString();
                switch (brandItem) { case ("Ford"): fordQuery();break;
                                    case ("BMW"): bmWQuery();break;
                    case ("Audi"):
                        audQuery();
                        break;
                    case ("Honda"):
                        hondQuery();
                        break;
                    case ("Nissan"):
                        nissQuery();
                        break;
                    case ("Toyota"):
                        toyotQuery();
                        break;
                    case ("Wolksvagen"):
                        wolksQuery();
                        break;
                    case ("Tesla"):
                        teslQuery();
                        break;
                    case ("Mazda"):
                        mazdQuery();
                        break;
                    case ("Fiat"):
                        fiatQueryy();
                }
            }
        });
    }

    //--------------------------------------------------------------------------
    // We divided the process og choosing the item to separated function.
    //Because there are many car items. In evey function here we check the name if the car after
    // the user choose the item from brand spinner.
    protected void fiatQueryy() {
        Query fiatQuery = firebaseFirestore.collection("cars")
                .whereEqualTo("brand", "Fiat");
        FirestoreRecyclerOptions<Cars> fiatOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(fiatQuery, Cars.class)
                .build();
        adapter.updateOptions(fiatOptions);

    }    //--------------------------------------------------------------------------

    protected void mazdQuery() {
        Query mazdaQuery = firebaseFirestore.collection("cars")
                .whereEqualTo("brand", "Mazda");
        FirestoreRecyclerOptions<Cars> mazOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(mazdaQuery, Cars.class)
                .build();
        adapter.updateOptions(mazOptions);

    }    //--------------------------------------------------------------------------

    protected void teslQuery() {
        Query teslaQuery = firebaseFirestore.collection("cars")
                .whereEqualTo("brand", "Tesla");
        FirestoreRecyclerOptions<Cars> tesOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(teslaQuery, Cars.class)
                .build();
        adapter.updateOptions(tesOptions);

    }    //--------------------------------------------------------------------------

    protected void wolksQuery() {
        Query volksQuery = firebaseFirestore.collection("cars")
                .whereEqualTo("brand", "Wolksvagen");
        FirestoreRecyclerOptions<Cars> volkOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(volksQuery, Cars.class)
                .build();
        adapter.updateOptions(volkOptions);

    }    //--------------------------------------------------------------------------

    protected void toyotQuery() {
        Query toyotaQuery = firebaseFirestore.collection("cars")
                .whereEqualTo("brand", "Toyota");
        FirestoreRecyclerOptions<Cars> toyOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(toyotaQuery, Cars.class)
                .build();
        adapter.updateOptions(toyOptions);

    }    //--------------------------------------------------------------------------

    protected void nissQuery() {
        Query nissanQuery = firebaseFirestore.collection("cars")
                .whereEqualTo("brand", "Nissan");
        FirestoreRecyclerOptions<Cars> nissOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(nissanQuery, Cars.class)
                .build();
        adapter.updateOptions(nissOptions);

    }    //--------------------------------------------------------------------------

    protected void hondQuery() {
        Query hondaQuery = firebaseFirestore.collection("cars")
                .whereEqualTo("brand", "Honda");
        FirestoreRecyclerOptions<Cars> hondOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(hondaQuery, Cars.class)
                .build();
        adapter.updateOptions(hondOptions);

    }    //--------------------------------------------------------------------------

    protected void audQuery() {
        Query audiQuery = firebaseFirestore.collection("cars")
                .whereEqualTo("brand", "Audi");
        FirestoreRecyclerOptions<Cars> audiOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(audiQuery, Cars.class)
                .build();
        adapter.updateOptions(audiOptions);
    }    //--------------------------------------------------------------------------

    protected void bmWQuery() {
        Query bmwQuery = firebaseFirestore.collection("cars")
                .whereEqualTo("brand", "BMW");
        FirestoreRecyclerOptions<Cars> bmwOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(bmwQuery, Cars.class)
                .build();
        adapter.updateOptions(bmwOptions);

    }    //--------------------------------------------------------------------------

    protected void fordQuery() {
        Query fordQuery = firebaseFirestore.collection("cars")
                .whereEqualTo("brand", "Ford");
        FirestoreRecyclerOptions<Cars> fordOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                .setQuery(fordQuery, Cars.class)
                .build();
        adapter.updateOptions(fordOptions);

    }

    //--------------------------------------------------------------------------
    protected void girSpinner() {
        String[] girItems = {"Automat", "Manual"};
        AutoCompleteTextView autoCoText_gir;
        ArrayAdapter<String> arrayAdapter_gir;
        autoCoText_gir = (AutoCompleteTextView) findViewById(R.id.autoCoText_gir);
        arrayAdapter_gir = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, girItems);
        autoCoText_gir.setAdapter(arrayAdapter_gir);
        autoCoText_gir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String automatItem = parent.getItemAtPosition(position).toString();
                if (automatItem.equals("Automat")) {
                    Query automatQuery = firebaseFirestore.collection("cars")
                            .whereEqualTo("gir", "Automat");
                    FirestoreRecyclerOptions<Cars> options = new FirestoreRecyclerOptions.Builder<Cars>()
                            .setQuery(automatQuery, Cars.class)
                            .build();
                    adapter.updateOptions(options);
                } else {
                    Query query = firebaseFirestore.collection("cars")
                            .whereEqualTo("gir", "Manual");
                    FirestoreRecyclerOptions<Cars> options = new FirestoreRecyclerOptions.Builder<Cars>()
                            .setQuery(query, Cars.class)
                            .build();
                    adapter.updateOptions(options);
                }
            }
        });
    }

    //--------------------------------------------------------------------------
    protected void seatsSpinner() {
        String[] seatsItems = {"2 Seats", "4 Seats", "5 Seats",};
        AutoCompleteTextView autoCoText_seats;
        ArrayAdapter<String> arrayAdapter_seats;
        autoCoText_seats = (AutoCompleteTextView) findViewById(R.id.autoCoText_seats);
        arrayAdapter_seats = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, seatsItems);
        autoCoText_seats.setAdapter(arrayAdapter_seats);
        autoCoText_seats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String seatItem = parent.getItemAtPosition(position).toString();
                switch (seatItem) {
                    case ("2 Seats"):
                        Query twoSeatQuery = firebaseFirestore.collection("cars")
                                .whereEqualTo("seats", 2);
                        FirestoreRecyclerOptions<Cars> twoOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                                .setQuery(twoSeatQuery, Cars.class)
                                .build();
                        adapter.updateOptions(twoOptions);
                        break;
                    case ("4 Seats"):
                        Query fourSeatQuery = firebaseFirestore.collection("cars")
                                .whereEqualTo("seats", 4);
                        FirestoreRecyclerOptions<Cars> fourSeatOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                                .setQuery(fourSeatQuery, Cars.class)
                                .build();
                        adapter.updateOptions(fourSeatOptions);
                        break;
                    case ("5 Seats"):
                        Query fifeSeatQuery = firebaseFirestore.collection("cars")
                                .whereEqualTo("seats", 5);
                        FirestoreRecyclerOptions<Cars> fifeSeatOptions = new FirestoreRecyclerOptions.Builder<Cars>()
                                .setQuery(fifeSeatQuery, Cars.class)
                                .build();
                        adapter.updateOptions(fifeSeatOptions);
                        break;
                }
            }
        });
    }


}