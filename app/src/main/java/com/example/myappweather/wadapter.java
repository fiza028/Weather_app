package com.example.myappweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class wadapter extends RecyclerView.Adapter<wadapter.ViewHolder> {

    private Context context;
    private ArrayList<wmodel>arrayList;

    public wadapter(Context context, ArrayList<wmodel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public wadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wrv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull wadapter.ViewHolder holder, int position) {

        wmodel mod = arrayList.get(position);
        holder.temp.setText(mod.getTemparature()+"°C");
        holder.wind.setText(mod.getWindspeed()+"km/h");
        Picasso.get().load("http:".concat(mod.getIcon())).into(holder.img);
        SimpleDateFormat ip = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat op = new SimpleDateFormat("hh:mm aa");
        try
        {
            Date d = ip.parse(mod.getTime());
            holder.time.setText(op.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView time, temp, wind;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            temp = itemView.findViewById(R.id.tempa);
            wind = itemView.findViewById(R.id.wind);
            img = itemView.findViewById(R.id.state);
        }
    }
}
