package com.example.absensi.model.Login;


import com.google.gson.annotations.SerializedName;


public class ResponseLogin{

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	@SerializedName("tipe")
	private String tipe;

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public String getTipe() {
		return tipe;
	}

	public String getStatus(){
		return status;
	}


}