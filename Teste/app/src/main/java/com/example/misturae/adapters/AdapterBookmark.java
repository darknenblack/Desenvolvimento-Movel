package com.example.misturae.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misturae.ActivityDrink;
import com.example.misturae.R;
import com.example.misturae.data.User;
import com.example.misturae.data.UserDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterBookmark extends RecyclerView.Adapter<AdapterBookmark.MyViewHolder> {

    ArrayList<String> id;
    ArrayList<String> nome;
    ArrayList<String> img;
    ArrayList<String> subtitle;
    Context context;

    public AdapterBookmark(Context context, ArrayList<String> id, ArrayList<String> nome, ArrayList<String> img, ArrayList<String> subtitle) {
        this.context = context;
        this.id = id;
        this.nome = nome;
        this.img = img;
        this.subtitle = subtitle;
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
        holder.vid.setText(subtitle.get(position));
        holder.vnome.setText(nome.get(position));

        Picasso.get().load(img.get(position)).into(holder.imagedrink);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityDrink.class);
                intent.putExtra("desc2bookmark", id.get(position));
                context.startActivity(intent);
            }
        });

        holder.iconFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    view.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);

                    removeUser(id.get(position));
                    id.remove(position);
                    nome.remove(position);
                    img.remove(position);
                    subtitle.remove(position);

                    Toast.makeText(context, "Deleted =(", Toast.LENGTH_SHORT).show();
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, id.size());
                } catch (Exception e) {
                }
            }
        });
    }

    private void removeUser(String IDs) {
        UserDatabase db = UserDatabase.getDbInstance(context);
        User user = new User();
        user.ID = IDs;
        db.userDao().delete(user);
    }

    @Override
    public int getItemCount() {

        return img.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vid, vnome;// init the item view's
        ImageView imagedrink, iconFav;
        ConstraintLayout LayoutFavoritos;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            vid = (TextView) itemView.findViewById(R.id.desc2bookmark);
            vnome = (TextView) itemView.findViewById(R.id.nome2bookmark);
            imagedrink = (ImageView) itemView.findViewById(R.id.image2bookmark);
            iconFav = itemView.findViewById(R.id.ivFavCardHome_favScreen);
        }
    }
}
