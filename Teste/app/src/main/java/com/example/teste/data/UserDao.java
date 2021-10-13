package com.example.teste.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user_table ORDER BY ID ASC")
    List<User> getAll();

    @Query("SELECT ID FROM user_table ORDER BY ID ASC")
    List<User> getIDS();

    @Query("SELECT COUNT(*) FROM user_table")
    Integer getTamanho();

    @Insert
    void insertUsers(User... users);

    @Delete
    void delete(User user);
}