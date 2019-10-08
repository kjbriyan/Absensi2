package com.example.absensi.network;

import com.example.absensi.model.Login.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Apiinterface {

    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseLogin> getUser(@Field("no_hp") String username,
                                @Field("imei") String password
                                );

}
