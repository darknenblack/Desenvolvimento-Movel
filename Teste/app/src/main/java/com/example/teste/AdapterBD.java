package com.example.teste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teste.data.User;

import java.util.List;

public class AdapterBD extends RecyclerView.Adapter<AdapterBD.MyViewHolder> {

    private Context context;
    private List<User> userList;

    public AdapterBD(Context context) {
        this.context = context;
    }

    public void setUserList(List<User> userList2) {
        userList = userList2;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterBD.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBD.MyViewHolder holder, int position) {
        holder.tvFirstName.setText(this.userList.get(position).ID);
    }

    @Override
    public int getItemCount() {
        return  userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvFirstName;

        public MyViewHolder(View view) {
            super(view);
            tvFirstName = view.findViewById(R.id.tvFirstName);
        }
    }
}
