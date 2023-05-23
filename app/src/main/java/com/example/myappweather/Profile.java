package com.example.myappweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    Button logoutbtn;
    TextView nametv, emailtv, citytv;
    DatabaseReference dbref;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nametv = findViewById(R.id.username);
        emailtv = findViewById(R.id.useremail);
        citytv = findViewById(R.id.usercity);
        logoutbtn = findViewById(R.id.button2);

        mauth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference().child("users").child(mauth.getUid());
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namee = snapshot.child("name").getValue().toString();
                String cityy = snapshot.child("cn").getValue().toString();
                String emaill = snapshot.child("email").getValue().toString();

                nametv.setText(namee);
                citytv.setText(cityy);
                emailtv.setText(emaill);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.signOut();
                startActivity(new Intent(Profile.this, Login.class));
            }
        });



    }
}