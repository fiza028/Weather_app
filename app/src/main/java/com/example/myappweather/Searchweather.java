package com.example.myappweather;

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
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Searchweather extends AppCompatActivity {

    TextView cityys, statuss, temps, hh, feeltemp;
    ImageView bckgs, icons, searchs;
    RelativeLayout rls;
    ProgressBar pbs;
    RecyclerView rcvs;
    ArrayList<wmodel>ArrayLists;
    wadapter Wadapters;
    String citynames;
    FirebaseUser us;
    FirebaseAuth mauth;
    DatabaseReference ref;
    String ttt;
    String temparature;
    long maxid=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchweather);

        statuss = findViewById(R.id.status);
        temps = findViewById(R.id.temp);
        bckgs = findViewById(R.id.bckg);
        icons = findViewById(R.id.icon);
        rls = findViewById(R.id.rl);
        pbs = findViewById(R.id.pb);
        rcvs = findViewById(R.id.rcv);
        feeltemp = findViewById(R.id.feeltemp);
        ArrayLists = new ArrayList<>();
        Wadapters = new wadapter(this, ArrayLists);
        cityys = findViewById(R.id.city);
        rcvs.setAdapter(Wadapters);
        //citynames = getIntent().getStringExtra("key");
        hh = findViewById(R.id.hometxt);


        citynames = getIntent().getStringExtra("key");

        getWeatherInfo(citynames);

       // ref = FirebaseDatabase.getInstance().getReference("users");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    maxid = (snapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


     //   System.out.println(ss);
       // System.out.println("hihelo");
        hh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showdata show = new showdata(citynames);
//                ref.child(mauth.getCurrentUser().getUid()).child("searched");
              //  ref.setValue(show);
                startActivity(new Intent(Searchweather.this, MainActivity.class));
                finish();
            }
        });

    }
    private void getWeatherInfo(String citymame){
        String url = "http://api.weatherapi.com/v1/forecast.json?key=261fe9ea537646d09e1143429221912&q="+citymame+"&days=1&aqi=yes&alerts=yes";

        cityys.setText(citymame);
        RequestQueue requestQueue = Volley.newRequestQueue(Searchweather.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pbs.setVisibility(View.GONE);
                rls.setVisibility(View.VISIBLE);
                ArrayLists.clear();

                try {
                     temparature = response.getJSONObject("current").getString("temp_c");
                    String feels = response.getJSONObject("current").getString("feelslike_c");
                    temps.setText(temparature+"°C");
                    feeltemp.setText(feels+"°C");
                    ttt = temps.getText().toString();
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionicon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionicon)).into(icons);
                    statuss.setText(condition);

                    if(isDay==1){
                        Picasso.get().load("https://images.unsplash.com/photo-1566219054926-af3c56e9a53f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Njh8fG1vcm5pbmclMjBza3l8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60").into(bckgs);
                    }else {
                        Picasso.get().load("https://images.pexels.com/photos/2908971/pexels-photo-2908971.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1").into(bckgs);
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
                        ArrayLists.add(new wmodel(time, tempara, image, wind));
                    }
                    Wadapters.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Searchweather.this, "please enter correct city name", Toast.LENGTH_SHORT).show();
            }

        });
        requestQueue.add(jsonObjectRequest);
    }
}