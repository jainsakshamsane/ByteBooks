package com.bytebooks;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bytebooks.ui.home.HomeFragment;

@Database(entities = {UserEntity.class, IssueEntity.class, PasswordEntity.class}, version = 11, exportSchema = false)

public abstract class UserDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "user_database";
    private static UserDatabase instance;

    public abstract UserDao userDao();

    public abstract IssueDao issueDao();
    public abstract PasswordDao passwordDao();


    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}