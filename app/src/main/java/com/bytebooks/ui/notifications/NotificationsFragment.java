package com.bytebooks.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bytebooks.AddBooks;
import com.bytebooks.DBhandler.DBHandler;
import com.bytebooks.IssueDao;
import com.bytebooks.IssueEntity;
import com.bytebooks.PasswordChange;
import com.bytebooks.R;
import com.bytebooks.UserDao;
import com.bytebooks.UserEntity;
import com.bytebooks.UserRegister;
import com.bytebooks.Userlogin;
import com.bytebooks.databinding.FragmentNotificationsBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationsFragment extends Fragment {

    EditText issuetext, booknamerequest, authornamerequest;
    Button issuebtn, adminrequestbtn;
    ImageView passwordpagenavigate;
    int userid;

    DBHandler dbHandler;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        issuetext = root.findViewById(R.id.issuetext);
        issuebtn = root.findViewById(R.id.issuebtn);
        booknamerequest = root.findViewById(R.id.booknamerequest);
        authornamerequest = root.findViewById(R.id.authornamerequest);
        adminrequestbtn = root.findViewById(R.id.requestadminbtn);
        passwordpagenavigate = root.findViewById(R.id.changepasswordbtn);

        issuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
                userid = sharedPreferences.getInt("userid",-1);


                String messages = issuetext.getText().toString();

                IssueEntity newIssue = new IssueEntity(messages,userid);
                issue(newIssue);

                issuetext.setText("");

                Toast.makeText(getContext(), "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
            }
        });

        adminrequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String bookkirequest = booknamerequest.getText().toString();
                    String authorkirequest = authornamerequest.getText().toString();

                    long times = System.currentTimeMillis();
                    SimpleDateFormat datessss = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                    String formatssss = datessss.format(new Date(times));

                    long result = dbHandler.insertrequest(bookkirequest, authorkirequest, formatssss);

                    if (result != -1) {
                        Toast.makeText(getContext(), "Book Request Submitted", Toast.LENGTH_SHORT).show();

                        booknamerequest.setText("");
                        authornamerequest.setText("");
                    } else {
                        Toast.makeText(getContext(), "Failed to request book", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        passwordpagenavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PasswordChange.class);
                startActivity(intent);
            }
        });

        return root;
    }
    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    private void issue(IssueEntity issuess) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                IssueDao issueDao = com.bytebooks.UserDatabase.getInstance(getContext()).issueDao();

                issueDao.issue(issuess);
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}