package com.example.covid19tracker.LoginForm.ForgetPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.covid19tracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

public class ForgetPassword extends AppCompatActivity {

    private TextInputEditText forgetPassword,confirmPassword;
    private Button update;

    private static final String PREF_NAME = "Shared pref";
    private static final String KEY_EMAIL = "email";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetPassword = findViewById(R.id.forgetPassword);
        confirmPassword = findViewById(R.id.cForgetPassword);
        update = findViewById(R.id.forgetBTN);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL,"");


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = String.valueOf(forgetPassword.getText());
                String cPassword = String.valueOf(confirmPassword.getText());
                if(password.isEmpty()){
                    forgetPassword.setError("Please enter password");
                    forgetPassword.requestFocus();
                }else if(cPassword.isEmpty()){
                    confirmPassword.setError("please enter confirm password");
                    confirmPassword.requestFocus();
                }else if(password.length()<6){
                    forgetPassword.setError("Password must contains at least 8 character");
                    forgetPassword.requestFocus();
                }
                else if(!cPassword.equals(password)){
                    confirmPassword.setError("confirm password is not equal to password");
                    confirmPassword.requestFocus();
                } else {
                    forgetPassword(email);
                }

            }
        });
    }

    private void forgetPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(ForgetPassword.this, "Link is sent on your email,please check your email id", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}