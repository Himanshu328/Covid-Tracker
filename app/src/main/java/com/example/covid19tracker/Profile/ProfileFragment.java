package com.example.covid19tracker.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19tracker.LoginForm.ForgetPassword.ForgetPassword;
import com.example.covid19tracker.LoginForm.SharedPreferences.SharedPrefManager;
import com.example.covid19tracker.R;

public class ProfileFragment extends Fragment {


    private TextView name,email,mobile,gender,age;
    private String user,email1,mobile_no,gen,age1;

    private SharedPrefManager sharedPrefManager;
    private SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "Shared pref";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view =  inflater.inflate(R.layout.fragment_profile, container, false);

       // Here notify the fragment that it should participate in options menu handling.
        setHasOptionsMenu(true);

        name = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.profileEmail);
        mobile = view.findViewById(R.id.profileMobile);
        gender = view.findViewById(R.id.profileGender);
        age = view.findViewById(R.id.profileAge);

        sharedPrefManager = new SharedPrefManager(view.getContext());
        sharedPreferences = view.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        email1 = sharedPreferences.getString(KEY_EMAIL,"");
        user = sharedPreferences.getString(KEY_NAME,"");
        mobile_no = sharedPreferences.getString(KEY_MOBILE,"");
        gen = sharedPreferences.getString(KEY_GENDER,"");
        age1 = sharedPreferences.getString(KEY_AGE,"");

        name.setText(user);
        email.setText(email1);
        mobile.setText(mobile_no);
        gender.setText(gen);
        age.setText(age1);
        return view;


    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logoutUser){
            sharedPrefManager.logoutUser();
            Toast.makeText(view.getContext(),"Logout Success",Toast.LENGTH_LONG).show();
        }else if(item.getItemId() == R.id.forgetUserPassword){
            startActivity(new Intent(view.getContext(), ForgetPassword.class));
        }

        return super.onOptionsItemSelected(item);
    }
}