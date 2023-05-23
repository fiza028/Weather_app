package com.example.myappweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button btn, btn2, btns;
    TextView profilebtn, marqueetxt;
    ImageView btn1;
    EditText edittext;
    FirebaseAuth mauth;
    LottieAnimationView lottie2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        marqueetxt = findViewById(R.id.marquee);
        marqueetxt.setSelected(true);
//        btn2 = findViewById(R.id.button3);
//        btns = findViewById(R.id.button2);
      //  lottie2 = findViewById(R.id.lottie);
       mauth = FirebaseAuth.getInstance();
       edittext = findViewById(R.id.editText);

        btn1 = findViewById(R.id.current);
        btn2 = findViewById(R.id.sbtn);
        profilebtn = findViewById(R.id.person);


     //  lottie2.animate().setDuration(2500).setStartDelay(2900);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        }, 4500);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Weather.class));
                finish();
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String searchcity = edittext.getText().toString();
                Intent i = new Intent(MainActivity.this, Searchweather.class);
                i.putExtra("key", searchcity);
                startActivity(i);
            }
        });

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Profile.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mauth.getCurrentUser();
        if(user == null)
        {
            startActivity(new Intent(MainActivity.this, Login.class));
        }

    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.login) {
//            Intent i = new Intent(MainActivity.this, login.class);
//            startActivity(i);
//            finish();
//            return true;
//        }
//        if(id == R.id.reg) {
//            Intent i = new Intent(MainActivity.this, register.class);
//            startActivity(i);
//            finish();
//            return true;
//        }
//
//        return true;
//    }
}