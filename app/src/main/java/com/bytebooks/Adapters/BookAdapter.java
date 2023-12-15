package com.bytebooks.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytebooks.Models.bookdetailsmodel;
import com.bytebooks.R;

import java.io.File;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<bookdetailsmodel> detailsofbook;
    private Context context;

    public BookAdapter(List<bookdetailsmodel> detailsofbook, Context context) {
        this.context = context;
        this.detailsofbook = detailsofbook;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.books_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final bookdetailsmodel bookdetailsss = detailsofbook.get(position);
        holder.booknamefromdatabase.setText(bookdetailsss.getBookkaname());
        holder.authornamefromdatabase.setText(bookdetailsss.getAuthorkaname());

        holder.bookpdfphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf(bookdetailsss.getPdfurl());
            }
        });
    }

    private void openPdf(String pdfUrl) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle exception when no PDF viewer is installed
            // You can prompt the user to install a PDF viewer from the Play Store
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return detailsofbook.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView booknamefromdatabase;
        TextView authornamefromdatabase;
        ImageView bookpdfphoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            booknamefromdatabase = itemView.findViewById(R.id.booknamefromdatabase);
            authornamefromdatabase = itemView.findViewById(R.id.authornamefromdatabase);
            bookpdfphoto = itemView.findViewById(R.id.bookpdfphoto);
        }
    }
}
