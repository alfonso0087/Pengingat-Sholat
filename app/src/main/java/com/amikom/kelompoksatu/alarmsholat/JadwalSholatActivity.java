package com.amikom.kelompoksatu.alarmsholat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amikom.kelompoksatu.alarmsholat.Api.ApiService;
import com.amikom.kelompoksatu.alarmsholat.Api.ApiUrl;
import com.amikom.kelompoksatu.alarmsholat.Model.ModelJadwal;

import java.text.DateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JadwalSholatActivity extends AppCompatActivity {

    private TextView waktusubuh, waktudzuhur, waktuashar, waktumaghrib, waktuisya;
    private FloatingActionButton fab_refresh;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_sholat);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);

        getSupportActionBar().setTitle("Jadwal Sholat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        waktusubuh = findViewById(R.id.waktusubuh);
        waktudzuhur = findViewById(R.id.waktudzuhur);
        waktuashar = findViewById(R.id.waktuashar);
        waktumaghrib = findViewById(R.id.waktumaghrib);
        waktuisya = findViewById(R.id.waktuisya);
        fab_refresh = findViewById(R.id.fab_refresh);

        getJadwal();

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJadwal();
            }
        });

        final ImageButton ringsubuh = findViewById(R.id.ringsubuh);
        ringsubuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, 3);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, 58);
                Toast.makeText(JadwalSholatActivity.this, "Alarm subuh diaktifkan.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        final ImageButton ringdzuhur = findViewById(R.id.ringdzuhur);
        ringdzuhur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, 11);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, 41);
                Toast.makeText(JadwalSholatActivity.this, "Alarm dzuhur diaktifkan.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        final ImageButton ringashar = findViewById(R.id.ringashar);
        ringashar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, 13);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, 57);
                Toast.makeText(JadwalSholatActivity.this, "Alarm ashar diaktifkan.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        final ImageButton ringmaghrib = findViewById(R.id.ringmaghrib);
        ringmaghrib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, 18);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, 3);
                Toast.makeText(JadwalSholatActivity.this, "Alarm maghrib diaktifkan.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        final ImageButton ringisya = findViewById(R.id.ringisya);
        ringisya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, 19);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, 15);
                Toast.makeText(JadwalSholatActivity.this, "Alarm isya diaktifkan.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getJadwal(){

        progressDialog = new ProgressDialog(JadwalSholatActivity.this);
        progressDialog.setMessage("Silakan tunggu...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ModelJadwal> call = apiService.getJadwal();

        call.enqueue(new Callback<ModelJadwal>() {
            @Override
            public void onResponse(Call<ModelJadwal> call, Response<ModelJadwal> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()){
                    waktusubuh.setText(response.body().getItems().get(0).getFajr());
                    waktudzuhur.setText(response.body().getItems().get(0).getDhuhr());
                    waktuashar.setText(response.body().getItems().get(0).getAsr());
                    waktumaghrib.setText(response.body().getItems().get(0).getMaghrib());
                    waktuisya.setText(response.body().getItems().get(0).getIsha());
                } else {

                }
            }

            @Override
            public void onFailure(Call<ModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(JadwalSholatActivity.this, "Silakan coba kembali...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
