package com.bytebooks.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.bytebooks.R;

public class ImageSliderAdapter extends PagerAdapter {

    private Context context;
    private int[] imageArray;

    public ImageSliderAdapter(Context context, int[] imageArray) {
        this.context = context;
        this.imageArray = imageArray;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;  // Infinite loop
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.image_slider_item, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView imageView = itemView.findViewById(R.id.imageView);
        int imagePosition = position % imageArray.length;  // To handle the infinite loop
        Glide.with(context).load(imageArray[imagePosition]).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

