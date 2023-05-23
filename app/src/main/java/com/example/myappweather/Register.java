package com.example.myappweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText cityname, email, password, name;
    Button regbtn;
    TextView loginbtn;
    String citynametxt, emailtxt, passtxt, nametxt;
    FirebaseAuth mauth;

     DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        cityname = findViewById(R.id.cityname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        name = findViewById(R.id.name);
        regbtn = findViewById(R.id.registerbtn);
        loginbtn = findViewById(R.id.loginbtn);
        mauth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference("users");
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();

            }

            private void createUser() {
                citynametxt = cityname.getText().toString();
                emailtxt = email.getText().toString();
                passtxt = password.getText().toString();
                nametxt = name.getText().toString();
                if(TextUtils.isEmpty(emailtxt)){
                    email.setError("Email cnanot be empty");
                    email.requestFocus();
                }
                else if(TextUtils.isEmpty(passtxt)){
                    password.setError("Password cannot be empty");
                    password.requestFocus();
                }
                else if(TextUtils.isEmpty(citynametxt)){
                    cityname.setError("Cityname cannot be empty");
                    cityname.requestFocus();
                }
                else{
                    mauth.createUserWithEmailAndPassword(emailtxt, passtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                User us = new User(nametxt, citynametxt, emailtxt, passtxt);
                                dbref.child(mauth.getCurrentUser().getUid()).setValue(us).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(Register.this, Login.class));
                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(Register.this, "Registration Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });



                }
            }

        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

    }
}