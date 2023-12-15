package com.bytebooks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long insert(UserEntity user);

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    UserEntity login(String email, String password);

    @Query("SELECT * FROM user_table WHERE userid = :userId")
    UserEntity getcontact(int userId);


    @Update
    void updatePassword(UserEntity user);
}


