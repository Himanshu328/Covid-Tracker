package com.example.covid19tracker.LoginForm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19tracker.LoginForm.ForgetPassword.ForgetPassword;
import com.example.covid19tracker.LoginForm.Registration.RegistrationActivity;
import com.example.covid19tracker.LoginForm.SharedPreferences.SharedPrefManager;
import com.example.covid19tracker.MainActivity;
import com.example.covid19tracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email, password;
    private Button login;
    private TextView createNew, forgetPassword;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;

    private SharedPrefManager sharedPrefManager;
    private String TAG = "tag";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginUser);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginButton);
        createNew = findViewById(R.id.createNew);
        forgetPassword = findViewById(R.id.loginForgetPassword);
        progressBar = findViewById(R.id.progressBar);

        sharedPrefManager = new SharedPrefManager(this);

        //Initialize FirebaseFirestore fireStore
        fireStore = FirebaseFirestore.getInstance();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        resume();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAdd = String.valueOf(email.getText());
                String pass = String.valueOf(password.getText());

                if (emailAdd.isEmpty()) {
                    email.setError("Please enter email Address");
                    email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAdd).matches()) {
                    email.setError("Please enter valid email");
                    email.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError("please enter password");
                    password.requestFocus();
                } else if (pass.length() < 8) {
                    password.setError("Password Must contains at least 8 Character");
                    password.requestFocus();
                } else {
                    checkUserDetails(emailAdd, pass);
                    progressBar.setVisibility(View.VISIBLE);
                    login.setVisibility(View.INVISIBLE);
                    sharedPrefManager.getEmail(emailAdd);
                    sharedPrefManager.createUserLoginSession(emailAdd, pass);
                    getData(emailAdd);
                }
            }
        });

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                progressBar.setVisibility(View.INVISIBLE);
                login.setVisibility(View.VISIBLE);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
            }
        });
    }


    private void checkUserDetails(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "User not exists", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "login failed");
                    progressBar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getMessage());
            }
        });
    }

    private void getData(String email) {
        DocumentReference documentReference = fireStore.collection("User").document(email);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                String name, email, mobile_no, gender, age;
                name = value.getString("Name");
                email = value.getString("Email");
                mobile_no = value.getString("Mobile no");
                age = value.getString("Age");
                gender = value.getString("Gender");
                sharedPrefManager.getUserDetails(name, email, mobile_no, age, gender);

            }
        });
    }

    private void resume() {
        if (sharedPrefManager.isUserLoggedIn() == true) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
