package com.example.teste.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    @NonNull()
    @PrimaryKey
    @ColumnInfo(name = "ID")
    public String ID;

    public String getID(){
        return this.ID;
    }
}