package com.bytebooks;


import androidx.room.Dao;
import androidx.room.Insert;


@Dao
public interface PasswordDao {
    @Insert
    long psword(PasswordEntity passkochange);

}

