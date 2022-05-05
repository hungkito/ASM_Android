package com.example.asm_android_hung_t2009a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.asm_android_hung_t2009a.adapter.WeatherAdapter;
import com.example.asm_android_hung_t2009a.model.Weather;
import com.example.asm_android_hung_t2009a.network.APIManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvHour;
    private TextView tvItem, tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvItem = (TextView) findViewById(R.id.tvItem);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
    }

    private void getHours() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIManager.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIManager service = retrofit.create(APIManager.class);
        service.gethour().enqueue(new Callback<List<Weather>>() {
            @Override
            public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {
                if (response.body() == null) return;

                List<Weather> weatherList = response.body();
                WeatherAdapter adapter = new WeatherAdapter(MainActivity.this, weatherList);
                rvHour.setAdapter(adapter);

                Weather weather = weatherList.get(0);
                tvItem.setText(weather.getTemperature().getValue().intValue() + "");
                tvStatus.setText(weather.getIconPhrase());
            }

            @Override
            public void onFailure(Call<List<Weather>> call, Throwable t) {

            }
        });
    }
}