package com.example.misturae.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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

    //Gel
    @Query("SELECT EXISTS(SELECT * FROM user_table WHERE id = :id)")
    boolean isRowIsExist(String id);
}