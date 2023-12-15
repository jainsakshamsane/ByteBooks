package com.bytebooks;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "issue_table")
public class IssueEntity {

    @PrimaryKey(autoGenerate = true)
    private int issueid;

    private int userid;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // The constructor should match the table structure defined by the entity
    public IssueEntity(String message,int userid) {
      this.message = message;
      this.userid = userid;
    }

    // Getters and setters for each attribute

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userId) {
        this.userid = userId;
    }

    public int getIssueid() {
        return issueid;
    }

    public void setIssueid(int issueid) {
        this.issueid = issueid;
    }
}
