package com.example.misturae.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserDatabase INSTANCE;

    public static UserDatabase getDbInstance(Context context) {

        if(INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "UserDatabase").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
