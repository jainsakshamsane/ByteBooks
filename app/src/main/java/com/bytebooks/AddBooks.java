package com.bytebooks;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bytebooks.DBhandler.DBHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddBooks extends AppCompatActivity {

    EditText bookname, authorname, bookprice;
    Button bookpdfbtn, submitbookbtn, requestviewbtnadmin;
    ImageView logoutbookbtn;

    DBHandler dbHandler;

    private static final int REQUEST_CODE_PICK_PDF = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbooks);

        dbHandler = new DBHandler(this);

        bookname = findViewById(R.id.bookname);
        authorname = findViewById(R.id.authorname);
        bookprice = findViewById(R.id.bookprice);
        bookpdfbtn = findViewById(R.id.bookpdfbtn);
        submitbookbtn = findViewById(R.id.submitbookbtn);
        logoutbookbtn = findViewById(R.id.logoutbookbtn);
        requestviewbtnadmin = findViewById(R.id.requestviewbtnadmin);

        bookpdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPdfFile();
            }
        });

        submitbookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
                String userId = sharedPreferences.getString("admin_id", "");

                if (!userId.isEmpty()) {
                    String nameofbook = bookname.getText().toString();
                    String nameofauthor = authorname.getText().toString();
                    String priceofbook = bookprice.getText().toString();
                    String selectedPdfPath = bookpdfbtn.getText().toString();

                    long times = System.currentTimeMillis();
                    SimpleDateFormat datesss = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                    String formatsss = datesss.format(new Date(times));

                    long result = dbHandler.insertbooks(userId, nameofbook, nameofauthor, priceofbook, selectedPdfPath, formatsss);

                    if (result != -1) {
                        Toast.makeText(AddBooks.this, "Book Added", Toast.LENGTH_SHORT).show();

                        bookname.setText("");
                        authorname.setText("");
                        bookprice.setText("");
                    } else {
                        Toast.makeText(AddBooks.this, "Failed to add book details", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddBooks.this, "Admin ID not found. Please log in.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logoutbookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("my preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();

                AlertDialog.Builder builder = new AlertDialog.Builder(AddBooks.this);

                // Set the message show for the Alert time
                builder.setMessage("Do you want to exit ?");

                // Set Alert Title
                builder.setTitle("Logout");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "No" button click
                        dialog.dismiss(); // Dismiss the dialog to stay on the same page
                    }
                });

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    Intent intent = new Intent(view.getContext(), Selector.class);
                    startActivity(intent);
                    Toast.makeText(AddBooks.this, "Logged out", Toast.LENGTH_SHORT).show();
                    finish();
                });
                builder.show();
            }
        });

        requestviewbtnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBooks.this, ViewRequests.class);
                startActivity(intent);
            }
        });
    }

    private void pickPdfFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(Intent.createChooser(intent, "Select a PDF file"), REQUEST_CODE_PICK_PDF);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_PDF && resultCode == RESULT_OK && data != null) {
            Uri selectedPdfUri = data.getData();
            if (selectedPdfUri != null) {
                // Update the button text with the selected PDF file path
                String selectedPdfPath = getFilePathFromUri(selectedPdfUri);
                bookpdfbtn.setText(selectedPdfPath);
            }
        }
    }

    private String getFilePathFromUri(Uri uri) {
        String filePath;
        String[] projection = {OpenableColumns.DISPLAY_NAME};
        try {
            // Query the content resolver to get the file name
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                filePath = cursor.getString(columnIndex);
                cursor.close();
            } else {
                filePath = uri.getLastPathSegment();
            }
        } catch (Exception e) {
            e.printStackTrace();
            filePath = uri.getLastPathSegment();
        }

        return filePath;
    }
}
