package com.amikom.kelompoksatu.alarmsholat.Api;

import com.amikom.kelompoksatu.alarmsholat.Model.ModelJadwal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("yogyakarta.json")
    Call<ModelJadwal> getJadwal();
}
