package com.example.absensi.network;

import com.example.absensi.model.Login.ResponseLogin;
import com.example.absensi.model.checkin.ResponseCheckin;
import com.example.absensi.model.checkout.Responseout;
import com.example.absensi.model.dataUser.ResponseDataUser;
import com.example.absensi.model.dataijin.ResponseijinLead;
import com.example.absensi.model.ijin.ResponseIjin;
import com.example.absensi.model.updatestatusijin.ResponseStatusIjin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Apiinterface {

    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseLogin> getUser(@Field("no_hp") String username,
                                @Field("imei") String password
                                );

    @FormUrlEncoded
    @POST("api/approve")
    Call<ResponseStatusIjin> updatestatus(@Field("id_reason") String id,
                                          @Field("acc") String acc
    );


    @GET("api/getuser/{id}")
    Call<ResponseDataUser> getDataUser(@Path("id") String iduser
    );

    @GET("api/getleader/{id}")
    Call<ResponseijinLead> getDataIjin(@Path("id") String idlead
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

    @FormUrlEncoded
    @POST("api/reason")
    Call<ResponseIjin> ijin(@Field("id_user") String iduser,
                            @Field("id_leader") String idled,
                            @Field("keterangan") String ket,
                            @Field("acc") String acc,
                            @Field("lat") String lat,
                            @Field("long") String lonng,
                            @Field("date_created") String date

    );



}
