package com.example.misturae.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.misturae.ActivityDrink;
import com.example.misturae.R;
import com.example.misturae.data.User;
import com.example.misturae.data.UserDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.MyViewHolder> {

    ArrayList<String> id;
    ArrayList<String> img;
    ArrayList<String> title;
    ArrayList<String> subtitle;
    Context context;

    public AdapterCategory(Context context, ArrayList<String> id, ArrayList<String> img, ArrayList<String> title, ArrayList<String> subtitle) {
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

        if(checkId(id.get(position)))
            holder.iconFav.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

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
                    view.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    saveNewUser(id.get(position));
                    Toast.makeText(context, "Success =)", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    view.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    removeUser(id.get(position));
                    Toast.makeText(context, "Deleted =(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveNewUser(String IDs) {
        UserDatabase db  = UserDatabase.getDbInstance(context);
        User user = new User();
        user.ID = IDs;
        db.userDao().insertUsers(user);
    }

    private void removeUser(String IDs) {
        UserDatabase db  = UserDatabase.getDbInstance(context);
        User user = new User();
        user.ID = IDs;
        db.userDao().delete(user);
    }

    boolean checkId(String id) {
        UserDatabase db  = UserDatabase.getDbInstance(context);
        return db.userDao().isRowIsExist(id);
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