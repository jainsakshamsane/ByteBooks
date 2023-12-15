package com.bytebooks;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "password_table")
public class PasswordEntity {

    @PrimaryKey(autoGenerate = true)
    private int passwordid;

    private int userid;
    private String oldpassword;
    private String newpassword;

    public int getPasswordid() {
        return passwordid;
    }

    public void setPasswordid(int passwordid) {
        this.passwordid = passwordid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public PasswordEntity(String oldpassword, String newpassword, int userid) {
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
        this.userid = userid;
    }
}


