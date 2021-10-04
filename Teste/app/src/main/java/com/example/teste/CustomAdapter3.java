package com.example.teste;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.MyViewHolder> {

    //O Card deve receber: Imagem, título, sub-título.
    ArrayList<String> id;
    ArrayList<String> img;
    ArrayList<String> title;
    ArrayList<String> subtitle;
    Context context;

    public CustomAdapter3(Context context, ArrayList<String> id, ArrayList<String> img, ArrayList<String> title, ArrayList<String> subtitle) {
        this.context = context;
        this.id = id;
        this.img = img;
        this.title = title;
        this.subtitle = subtitle;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Picasso.get().load(img.get(position)).into(holder.imageDrink);
        holder.textTitle.setText(title.get(position));
        holder.textSubTitle.setText(subtitle.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TelaDrink.class);
                intent.putExtra("desc2bookmark", id.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textSubTitle;
        ImageView imageDrink, iconFav, iconShare;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageDrink = itemView.findViewById(R.id.ivImageCardHome);
            textTitle = itemView.findViewById(R.id.tvNameCardHome);
            textSubTitle = itemView.findViewById(R.id.tvTypeCardHome);
            iconFav = itemView.findViewById(R.id.ivFavCardHome);
            iconShare = itemView.findViewById(R.id.ivShareCardHome);
        }
    }
}