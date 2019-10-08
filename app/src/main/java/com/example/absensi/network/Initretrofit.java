package com.example.absensi.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Initretrofit {
    private static final String URL = "http://192.168.1.13/dku_pro/public/";
    private static final String BASE_URL_IMAGE = URL + "asset/foto_produk/";
    private static final String BASE_URL_KTP = URL + "asset/foto_ktp/";

//    private static final String URL = "http://192.168.1.13";
//    private static final String BASE_URL = URL + "/kakarentalllll/";
//    private static final String BASE_URL_IMAGE =BASE_URL+ "asset/foto_produk/";
//
//    private static final String BASE_URL_KTP = BASE_URL +"asset/foto_ktp/";

//    public static Retrofit initRetrofit() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        return retrofit;


    public static Retrofit initRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Apiinterface getInstance() {
        return initRetrofit().create(Apiinterface.class);
    }

    public static String getIMG_URL() {
        String IMAGE = "";
        IMAGE = BASE_URL_IMAGE;
        return IMAGE;
    }

    public static String getKtp() {
        String KTP = "";
        KTP = BASE_URL_KTP;
        return KTP;
    }
}

