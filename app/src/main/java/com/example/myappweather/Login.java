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

public class Login extends AppCompatActivity {

    EditText id, pass;
    TextView regbtn;
    Button loginbtn;
    String idinput, passinput;
    FirebaseAuth mauth;
    FirebaseUser currentsuer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.id);
        pass = findViewById(R.id.pass);
        loginbtn = findViewById(R.id.loginbtn);
        regbtn = findViewById(R.id.regbtn);
        mauth = FirebaseAuth.getInstance();
        currentsuer = FirebaseAuth.getInstance().getCurrentUser();


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idinput = id.getText().toString();
                passinput = pass.getText().toString();
                if(TextUtils.isEmpty(idinput)){
                    id.setError("Email cnanot be empty");
                    id.requestFocus();
                }
                else if(TextUtils.isEmpty(passinput)){
                    pass.setError("Password cannot be empty");
                    pass.requestFocus();
                }
                else{
                    mauth.signInWithEmailAndPassword(idinput, passinput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(Login.this, "Logged in succesfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, MainActivity.class));
                            }else
                            {
                                Toast.makeText(Login.this, "Login error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });


        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });



    }
}