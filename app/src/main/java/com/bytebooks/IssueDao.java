package com.bytebooks;

import androidx.room.Dao;
import androidx.room.Insert;


@Dao
public interface IssueDao {
    @Insert
    long issue(IssueEntity issueofuser);

}
