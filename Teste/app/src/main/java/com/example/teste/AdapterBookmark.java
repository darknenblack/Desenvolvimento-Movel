package com.example.teste;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterBookmark extends RecyclerView.Adapter<AdapterBookmark.MyViewHolder>{

    ArrayList<String> id;
    ArrayList<String> nome;
    ArrayList<String> img;
    Context context;

    public AdapterBookmark(Context context, ArrayList<String> id, ArrayList<String> nome, ArrayList<String> img) {
        this.context = context;
        this.id = id;
        this.nome = nome;
        this.img = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rowlayout_favoritos, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.vid.setText(id.get(position));
        holder.vnome.setText(nome.get(position));
        Picasso.get().load(img.get(position)).into(holder.imagedrink);

        holder.LayoutFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TelaDrink.class);
                intent.putExtra("desc2bookmark", id.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return img.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vid, vnome;// init the item view's
        ImageView imagedrink;
        ConstraintLayout LayoutFavoritos;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            vid = (TextView) itemView.findViewById(R.id.desc2bookmark);
            vnome = (TextView) itemView.findViewById(R.id.nome2bookmark);
            imagedrink = (ImageView) itemView.findViewById(R.id.image2bookmark);
            LayoutFavoritos =itemView.findViewById(R.id.layoutFavoritos);

        }
    }
}
