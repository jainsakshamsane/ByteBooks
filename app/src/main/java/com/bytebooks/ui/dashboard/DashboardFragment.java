package com.bytebooks.ui.dashboard;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bytebooks.R;
import com.bytebooks.Selector;
import com.bytebooks.UserDao;
import com.bytebooks.UserDatabase;
import com.bytebooks.UserEntity;
import com.bytebooks.Userlogging;
import com.bytebooks.Userlogin;
import com.bytebooks.databinding.FragmentDashboardBinding;

import java.lang.ref.WeakReference;

public class DashboardFragment extends Fragment {

    private TextView userkaname, userkaaddress, userkaphonenumber;
    private ImageView logoutprofilebtn;
    private FragmentDashboardBinding binding;
    private UserDao userDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userkaname = root.findViewById(R.id.userkaname);
        userkaaddress = root.findViewById(R.id.userkaaddress);
        userkaphonenumber = root.findViewById(R.id.userkaphonenumber);
        logoutprofilebtn = root.findViewById(R.id.logoutprofilebtn);



        // Get userId from SharedPreferences
        SharedPreferences sharedpreferences = requireContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
        int userId = sharedpreferences.getInt("userid", 0);

        getcontact(userId);
        logoutprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });

        return root;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

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
        builder.setPositiveButton("Yes", (dialog, which) -> {
            SharedPreferences sharedpreferences = requireContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(requireContext(), Selector.class);
            startActivity(intent);
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show();
            requireActivity().finish(); // Finish the current activity
        });

        builder.show();
    }

    private void getcontact(int user) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                UserDao userDao = com.bytebooks.UserDatabase.getInstance(getContext()).userDao();
                Log.e("userid", String.valueOf(user));
                UserEntity users = userDao.getcontact(user);

                userkaname.setText(users.getName());
                userkaaddress.setText(users.getAddress());
                userkaphonenumber.setText(users.getMobile());
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
