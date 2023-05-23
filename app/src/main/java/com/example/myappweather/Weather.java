package com.example.myappweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Weather extends AppCompatActivity {
    TextView cityy, status, temp, home, feeltemp;
    ImageView bckg, icon;
    RelativeLayout rl;
    ProgressBar pb;
    RecyclerView rcv;
    String userid;
    ArrayList<wmodel> ArrayList;
    wadapter Wadapter;
    FirebaseUser Uuser;
    DatabaseReference dbref;
    String cityname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityy = findViewById(R.id.city);
        status = findViewById(R.id.status);
        temp = findViewById(R.id.temp);
        bckg = findViewById(R.id.bckg);
        icon = findViewById(R.id.icon);
        home = findViewById(R.id.hometxt);
        feeltemp = findViewById(R.id.feeltemp);
        rl = findViewById(R.id.rl);
        pb = findViewById(R.id.pb);
        rcv = findViewById(R.id.rcv);
        ArrayList = new ArrayList<>();
        Wadapter = new wadapter(this, ArrayList);
        rcv.setAdapter(Wadapter);

        Uuser = FirebaseAuth.getInstance().getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("users");
        userid = Uuser.getUid();

        dbref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if(userprofile!=null)
                {
                    cityname = userprofile.cn;
                    System.out.println(cityname);
                    getWeatherInfo(cityname);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getWeatherInfo(cityname);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Weather.this, MainActivity.class));
                finish();
            }
        });


    }
    private void getWeatherInfo(String citymame){
        String url = "http://api.weatherapi.com/v1/forecast.json?key=261fe9ea537646d09e1143429221912&q="+citymame+"&days=1&aqi=yes&alerts=yes";

        cityy.setText(citymame);
        RequestQueue requestQueue = Volley.newRequestQueue(Weather.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                pb.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);
                ArrayList.clear();

                try {
                    String temparature = response.getJSONObject("current").getString("temp_c");
                    String feels = response.getJSONObject("current").getString("feelslike_c");
                    temp.setText(temparature+"°C");
                    feeltemp.setText(feels+"°C");
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionicon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionicon)).into(icon);
                    status.setText(condition);

                    if(isDay==1){
                        Picasso.get().load("https://images.unsplash.com/photo-1566219054926-af3c56e9a53f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Njh8fG1vcm5pbmclMjBza3l8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60").into(bckg);
                    }else {
                        Picasso.get().load("https://images.pexels.com/photos/2908971/pexels-photo-2908971.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1").into(bckg);
                    }
                    JSONObject forecastobj = response.getJSONObject("forecast");
                    JSONObject forecast0 = forecastobj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hrarray = forecast0.getJSONArray("hour");
                    for(int i=0; i<hrarray.length(); i++)
                    {
                        JSONObject hrobj = hrarray.getJSONObject(i);
                        String time = hrobj.getString("time");
                        String tempara = hrobj.getString("temp_c");
                        String image = hrobj.getJSONObject("condition").getString("icon");
                        String wind = hrobj.getString("wind_kph");
                        ArrayList.add(new wmodel(time, tempara, image, wind));
                    }
                    Wadapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Weather.this, "please enter correct city name", Toast.LENGTH_SHORT).show();
            }

        });
        requestQueue.add(jsonObjectRequest);
    }

}