package com.bytebooks.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bytebooks.Adapters.BookAdapter;
import com.bytebooks.DBhandler.DBHandler;
import com.bytebooks.Models.bookdetailsmodel;
import com.bytebooks.R;
import com.bytebooks.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    DBHandler dbHandler;
    RecyclerView recyclerView1;

    private FragmentHomeBinding binding;
    private VideoView videoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView1 = root.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView1.setHasFixedSize(true);

        dbHandler = new DBHandler(requireContext());

        List<bookdetailsmodel> bookDetailsModels = dbHandler.getAllBookData();

        if (bookDetailsModels.size() > 0) {
            BookAdapter bookAdapter = new BookAdapter(bookDetailsModels, getContext());
            recyclerView1.setAdapter(bookAdapter);
        } else {
            Toast.makeText(getContext(), "There is no book yet", Toast.LENGTH_SHORT).show();
        }
        // Find the VideoView in the layout
        videoView = root.findViewById(R.id.videoView);


        // Set the video path (replace with your video resource or URL)
        String videoPath = "android.resource://" + requireActivity().getPackageName() + "/" + R.raw.homepagevideo;
        videoView.setVideoPath(videoPath);

        // Start playing the video
        videoView.start();

        // Set a completion listener to restart the video when it completes
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Restart the video
                videoView.start();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
