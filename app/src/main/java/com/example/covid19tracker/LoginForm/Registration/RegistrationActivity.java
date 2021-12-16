package com.example.covid19tracker.LoginForm.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.covid19tracker.LoginForm.SharedPreferences.SharedPrefManager;
import com.example.covid19tracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText username,mobileNo,age,password,email;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button register;

    private FirebaseFirestore fireStore;
    private FirebaseAuth mAuth;

    private SharedPrefManager sharedPrefManager;
    private final String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.registerUser);
        mobileNo = findViewById(R.id.registerMobile);
        age = findViewById(R.id.registerAge);
        password = findViewById(R.id.registerPassword);
        radioGroup = findViewById(R.id.radioGroup);
        email = findViewById(R.id.registerEmail);
        register = findViewById(R.id.registerButton);

        username.setFocusableInTouchMode(true);
        mobileNo.setFocusableInTouchMode(true);
        age.setFocusableInTouchMode(true);
        password.setFocusableInTouchMode(true);
        email.setFocusableInTouchMode(true);

        // Initialize sharedPrefManager
        sharedPrefManager = new SharedPrefManager(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firestore
        fireStore = FirebaseFirestore.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(username.getText());
                String mobile = String.valueOf(mobileNo.getText());
                String age1 = String.valueOf(age.getText());
                String pass = String.valueOf(password.getText());
                String email1 = String.valueOf(email.getText());

                if(name.isEmpty()){
                    username.setError("Please enter your name");
                    username.requestFocus();
                }else if(mobile.isEmpty()){
                    mobileNo.setError("Please enter your Mobile number");
                    mobileNo.requestFocus();
                }else if(!Patterns.PHONE.matcher(mobile).matches()){
                    mobileNo.setError("Please enter valid Mobile number");
                    mobileNo.requestFocus();
                }else if(age1.isEmpty()){
                    age.setError("Please enter your age");
                    age.requestFocus();
                }if(email1.isEmpty()){
                    email.setError("Please enter your Email Address");
                    username.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                    email.setError("Please enter valid email Address");
                    email.requestFocus();
                }else if(pass.isEmpty()){
                    password.setError("Please enter Password");
                    password.requestFocus();
                }else if(pass.length()<8){
                    password.setError("Password must contains at least 8 character");
                    password.requestFocus();
                }else if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(RegistrationActivity.this,"Please select your gender",Toast.LENGTH_LONG).show();
                }else {
                    radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    String gender = String.valueOf(radioButton.getText());
                    //Toast.makeText(RegistrationActivity.this,"Gender: "+gender,Toast.LENGTH_LONG).show();
                    sharedPrefManager.getEmail(email1);
                    registerRecord(name, email1, mobile, age1, pass, gender);
                    signIn(email1, pass);
                }
            }
        });
    }


    private void signIn(String email,String pass){
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG,"sign In Success");
                }else {
                    Log.d(TAG,"Sign in Failed");
                }
            }
        });
    }

    private void registerRecord(String name,String email,String mobile,String age,String password,String gender)
    {
        DocumentReference documentReference = fireStore.collection("User").document(email);
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Email",email);
        user.put("Mobile no",mobile);
        user.put("Age",age);
        user.put("Password",password);
        user.put("Gender",gender);

        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Dialog("Success","Registration Successful");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this,"error:"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Dialog(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok",null);
        builder.create();
        builder.show();
    }
}