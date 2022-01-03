package com.mob3000.group11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirestoreAdapter extends FirestoreRecyclerAdapter<Cars,FirestoreAdapter.myViewHolder> {

    private OnListItemClick onListItemClick;
    private Context context;
    private ArrayList<Cars> listCars;


    public FirestoreAdapter(@NonNull FirestoreRecyclerOptions<Cars> options,OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick=onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Cars cars) {
        holder.textpri_ce.setText(cars.getPriceWithLabel());
        holder.textbra_nd.setText(cars.getBrand());
        holder.textg_ir.setText(cars.getGir());
        holder.textsea_ts.setText(cars.getSeatsWithLabel());
        Glide.with(holder.img.getContext()).load(cars.getImgurl()).into(holder.img);


    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_items,
                parent, false);
        return new myViewHolder(view);

    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        CircleImageView img;
        TextView textpri_ce,textbra_nd,textg_ir,textsea_ts;

        public myViewHolder( View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img1);
            textpri_ce= (TextView) itemView.findViewById(R.id.textprice);
            textbra_nd= (TextView)itemView.findViewById(R.id.textbrand);
            textg_ir= (TextView)itemView.findViewById(R.id.textgir);
            textsea_ts= (TextView)itemView.findViewById(R.id.textseats);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());

        }
    }
    public interface OnListItemClick{

        void onItemClick(Cars snapshot, int position);

    }

}
