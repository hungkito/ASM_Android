package com.example.asm_android_hung_t2009a.adapter;

import android.app.Activity;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asm_android_hung_t2009a.R;
import com.example.asm_android_hung_t2009a.model.Weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<Weather> weatherList;

    public WeatherAdapter(Activity activity, List<Weather> weatherList){
        this.activity = activity;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= activity.getLayoutInflater();
        View itemView =inflater.inflate(R.layout.item_weathers,parent,false);
        HourHolder holder = new HourHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HourHolder vh = (HourHolder) holder;
        Weather weather = weatherList.get(position);
        vh.tvTime.setText(convertTime(weather.getDateTime()));
        vh.tvItem.setText(weather.getTemperature().getValue()+"");
        String url="";
        if (weather.getWeatherIcon() < 10){
            url = "https://developer.accuweather.com/sites/default/files/0" + weather.getWeatherIcon() + "-s.png";
        }else {
            url = "https://developer.accuweather.com/sites/default/files/" + weather.getWeatherIcon() + "-s.png";
        }
        Glide.with(activity).load(url).into(vh.icon);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    private static class HourHolder extends RecyclerView.ViewHolder {

        private TextView tvTime;
        private ImageView icon;
        private TextView tvItem;

        public HourHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            tvItem = (TextView) itemView.findViewById(R.id.tvTem);
        }
    }

    public String convertTime(String inputTime) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = inFormat.parse(inputTime);
        }catch (ParseException | java.text.ParseException e){
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("ha");
        String goal = outFormat.format(date);
        return goal;
    }
}
