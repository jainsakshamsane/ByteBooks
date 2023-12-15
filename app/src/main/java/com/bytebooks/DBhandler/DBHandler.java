package com.bytebooks.DBhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bytebooks.Models.bookdetailsmodel;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {


    private static final String DB_NAME = "bytebooks";
    private static final int DB_VERSION = 3;
    private static final String TABLE_USER = "user";
    private static final String USERID_COL = "userid";
    private static final String NAME_COL = "name";
    private static final String MOBILE_COL = "mobile";
    private static final String EMAIL_COL = "email";
    private static final String PASSWORD_COL = "password";
    private static final String AGE_COL = "age";
    private static final String ADDRESS_COL = "address";
    private static final String CREATED_AT_COL = "created_at";



    private static final String TABLE_ADMIN = "admin";
    private static final String ADMINID_COL = "admin_id";
    private static final String ADMIN_NAME_COL = "name";
    private static final String ADMIN_MOBILE_COL = "mobile";
    private static final String ADMIN_EMAIL_COL = "email";
    private static final String ADMIN_PASSWORD_COL = "password";
    private static final String ADMIN_ADDRESS_COL = "address";
    private static final String ADMIN_CREATED_AT_COL = "created_at";



    private static final String TABLE_BOOKS = "books";
    private static final String BOOK_ID = "book_id";
    private static final String BOOK_ADMIN_ID = "admin_id";
    private static final String BOOK_NAME = "bookname";
    private static final String AUTHOR_NAME = "authorname";
    private static final String BOOK_PRICE = "book_price";
    private static final String BOOK_PDF = "pdf";
    private static final String BOOK_CREATED_AT_COL = "uploaded_at";



    private static final String TABLE_ISSUE = "issues";
    private static final String ISSUE_ID = "issue_id";
    private static final String ISSUE_USER_ID = "userid";
    private static final String ISSUE_MESSAGE = "message";
    private static final String ISSUE_CREATED_AT_COL = "created_at";




    private static final String TABLE_BOOK_REQUESTS = "book_request";
    private static final String REQUEST_ID = "request_id";
    private static final String REQUEST_USER_ID = "userid";
    private static final String BOOKNAME_REQUEST = "book_name";
    private static final String AUTHOR_REQUEST = "author";
    private static final String STATUS_REQUEST = "status";
    private static final String REQUEST_CREATED_AT_COL = "created_at";




    private static final String TABLE_CHANGE_PASSWORD = "change_password";
    private static final String PASSWORD_USER_ID = "userid";
    private static final String OLD_PASSWORD = "oldpassword";
    private static final String NEW_PASSWORD = "newpassword";
    private static final String PASSWORD_CREATED_AT_COL = "created_at";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_USER + " ("
                + USERID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + MOBILE_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + PASSWORD_COL + " TEXT,"
                + AGE_COL + " TEXT,"
                + ADDRESS_COL + " TEXT,"
                + CREATED_AT_COL + " TEXT)";
        db.execSQL(query);

        String query1 = "CREATE TABLE " + TABLE_ADMIN + " ("
                + ADMINID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ADMIN_NAME_COL + " TEXT,"
                + ADMIN_MOBILE_COL + " TEXT,"
                + ADMIN_EMAIL_COL + " TEXT,"
                + ADMIN_PASSWORD_COL + " TEXT,"
                + ADMIN_ADDRESS_COL + " TEXT,"
                + ADMIN_CREATED_AT_COL + " TEXT)";
        db.execSQL(query1);

        String query3 = "CREATE TABLE " + TABLE_BOOKS + " ("
                + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOOK_ADMIN_ID + " TEXT,"
                + BOOK_NAME + " TEXT,"
                + AUTHOR_NAME + " TEXT,"
                + BOOK_PRICE + " TEXT,"
                + BOOK_PDF + " TEXT,"
                + BOOK_CREATED_AT_COL + " TEXT)";
        db.execSQL(query3);

        String query4 = "CREATE TABLE " + TABLE_ISSUE + " ("
                + ISSUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ISSUE_USER_ID + " TEXT,"
                + ISSUE_MESSAGE + " TEXT,"
                + ISSUE_CREATED_AT_COL + " TEXT)";
        db.execSQL(query4);

        String query5 = "CREATE TABLE " + TABLE_BOOK_REQUESTS + " ("
                + REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + REQUEST_USER_ID + " TEXT,"
                + BOOKNAME_REQUEST + " TEXT,"
                + AUTHOR_REQUEST + " TEXT,"
                + STATUS_REQUEST + " TEXT,"
                + REQUEST_CREATED_AT_COL + " TEXT)";
        db.execSQL(query5);

        String query6 = "CREATE TABLE " + TABLE_CHANGE_PASSWORD + " ("
                + PASSWORD_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OLD_PASSWORD + " TEXT,"
                + NEW_PASSWORD + " TEXT,"
                + PASSWORD_CREATED_AT_COL + " TEXT)";
        db.execSQL(query6);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISSUE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK_REQUESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANGE_PASSWORD);
        onCreate(db);
    }

    public void registeruser(String name, String mobile, String email, String password, String age, String address, String dates) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(MOBILE_COL, mobile);
        values.put(EMAIL_COL, email);
        values.put(PASSWORD_COL, password);
        values.put(AGE_COL, age);
        values.put(ADDRESS_COL, address);
        values.put(CREATED_AT_COL, dates);
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public String loginuser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String userRole = null;
        String sql = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "' ;";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            userRole = cursor.getString(cursor.getColumnIndexOrThrow("userid"));
        }
        return userRole;
    }

    public void registeradmin(String name, String mobile, String email, String password, String address, String datess) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ADMIN_NAME_COL, name);
        values.put(ADMIN_MOBILE_COL, mobile);
        values.put(ADMIN_EMAIL_COL, email);
        values.put(ADMIN_PASSWORD_COL, password);
        values.put(ADMIN_ADDRESS_COL, address);
        values.put(ADMIN_CREATED_AT_COL, datess);
        db.insert(TABLE_ADMIN, null, values);
        db.close();
    }

    public String loginadmin(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String adminRole = null;
        String sql = "SELECT * FROM admin WHERE email = '" + email + "' AND password = '" + password + "' ;";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            adminRole = cursor.getString(cursor.getColumnIndexOrThrow("admin_id"));
        }
        return adminRole;
    }

    public long insertbooks(String adminid, String name, String authorname, String price,  String pdf, String datesss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_ADMIN_ID, adminid);
        values.put(BOOK_NAME, name);
        values.put(AUTHOR_NAME, authorname);
        values.put(BOOK_PRICE, price);
        values.put(BOOK_PDF, pdf);
        values.put(BOOK_CREATED_AT_COL, datesss);

        long result = db.insert(TABLE_BOOKS, null, values);
        db.close();
        return result;
    }

    public List<bookdetailsmodel> getAllBookData() {
        String sql = "SELECT * FROM " + TABLE_BOOKS ;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<bookdetailsmodel> storebook = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String bookname = cursor.getString(cursor.getColumnIndexOrThrow(BOOK_NAME));
                String authorname = cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR_NAME));
                String pdf = cursor.getString(cursor.getColumnIndexOrThrow(BOOK_PDF));

                storebook.add(new bookdetailsmodel(bookname, authorname, pdf));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return storebook;
    }

    public long insertrequest(String bookrname, String authorrname, String datessss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AUTHOR_REQUEST, authorrname);
        values.put(BOOKNAME_REQUEST, bookrname);
        values.put(REQUEST_CREATED_AT_COL, datessss);

        long result = db.insert(TABLE_BOOK_REQUESTS, null, values);
        db.close();
        return result;
    }


































//
//    public String getUserName(String userID) {
//        String userType = "";
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM user WHERE userID = '" + userID + "';";
//        Log.e("User ki wo id", sql);
//        Cursor cursor = db.rawQuery(sql, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                userType = cursor.getString(cursor.getColumnIndexOrThrow("name"));
//            }
//            cursor.close();
//        }
//        return userType;
//    }
//
//    public String getCurrentBalance(String userID) {
//        String balance = "";
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM user WHERE userID = '" + userID + "';";
//        Log.e("User ki wo balance", sql);
//        Cursor cursor = db.rawQuery(sql, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                balance = cursor.getString(cursor.getColumnIndexOrThrow("current_balance"));
//            }
//            cursor.close();
//        }
//        return balance;
//    }
//
//    public long insertCardDetails(String userId, String cardNumber, String expiryDate) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(CARD_USER_ID, userId);
//        values.put(COLUMN_CARD_NUMBER, cardNumber);
//        values.put(COLUMN_EXPIRY_DATE, expiryDate);
//
//        long result = db.insert(TABLE_CARDS, null, values);
//        db.close();
//        return result;
//    }
//
//    public String getCardNumber(String userID) {
//        String userType = "";
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM cards WHERE userID = '" + userID + "';";
//        Log.e("User ki wo id", sql);
//        Cursor cursor = db.rawQuery(sql, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                userType = cursor.getString(cursor.getColumnIndexOrThrow("card_number"));
//            }
//            cursor.close();
//        }
//        return userType;
//    }
//
//    public String getCardExpiryDate(String userID) {
//        String userType = "";
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM cards WHERE userID = '" + userID + "';";
//        Log.e("User ki wo id", sql);
//        Cursor cursor = db.rawQuery(sql, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                userType = cursor.getString(cursor.getColumnIndexOrThrow("expiry_date"));
//            }
//            cursor.close();
//        }
//        return userType;
//    }
//
//    public void addstocks(String stockname, String stockprice, String orgname) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(STOCK_NAME, stockname);
//        values.put(STOCK_PRICE, stockprice);
//        values.put(ORG_NAME, orgname);
//        db.insert(TABLE_STOCKS, null, values);
//        db.close();
//    }
//
//    public List<allstocksmodel> getAllStockData() {
//        String sql = "SELECT * FROM " + TABLE_STOCKS ;
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        List<allstocksmodel> storestocks = new ArrayList<>();
//
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{});
//
//        if (cursor.moveToFirst()) {
//            do {
//                String name = cursor.getString(cursor.getColumnIndexOrThrow(STOCK_NAME));
//                String price = cursor.getString(cursor.getColumnIndexOrThrow(STOCK_PRICE));
//
//                storestocks.add(new allstocksmodel(name, price));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return storestocks;
//    }
}
