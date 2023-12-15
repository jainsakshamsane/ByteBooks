package com.bytebooks;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int userid;

    private String name;
    private String mobile;
    private String email;
    private String password;
    private String age;
    private String address;

    // The constructor should match the table structure defined by the entity
    public UserEntity(String name, String mobile, String email, String password, String age, String address) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.age = age;
        this.address = address;
    }
//    public UserEntity(int name) {
//        this.userid = name;
//
//    }

    public UserEntity() {
//        this.password = password;
//        this.userid = userid;
    }
    // Getters and setters for each attribute


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}