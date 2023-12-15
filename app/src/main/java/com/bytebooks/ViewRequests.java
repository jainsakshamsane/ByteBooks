package com.bytebooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewRequests extends AppCompatActivity {
        RecyclerView recyclerView;
        ImageView imageView2;

//        private YourRecyclerViewAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.view_bookrequests);
            recyclerView = findViewById(R.id.recyclerView);
            imageView2 = findViewById(R.id.imageView2);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ViewRequests.this, AddBooks.class);
                    startActivity(intent);
                }
            });
//
//            adapter = new YourRecyclerViewAdapter();
//            recyclerView.setAdapter(adapter);
        }

    }

