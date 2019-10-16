package com.example.absensi.network;

import com.example.absensi.model.Login.ResponseLogin;
import com.example.absensi.model.checkin.ResponseCheckin;
import com.example.absensi.model.checkout.Responseout;

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

    @FormUrlEncoded
    @POST("api/checkin")
    Call<ResponseCheckin> CheckIn(@Field("time_in") String time,
                                  @Field("lat") String lat,
                                  @Field("long") String lonng,
                                  @Field("id_status") String idstatus,
                                  @Field("keterangan") String ket,
                                  @Field("id_user") String iduser
    );

    @FormUrlEncoded
    @POST("api/checkout")
    Call<Responseout> Checkout(@Field("time_out") String time,
                               @Field("lat") String lat,
                               @Field("long") String lonng,
                               @Field("id_status") String idstatus,
                               @Field("keterangan") String ket,
                               @Field("id_user") String iduser
    );


}
