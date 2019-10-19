package com.example.absensi.model.checkout;


import com.google.gson.annotations.SerializedName;


public class Responseout{

	@SerializedName("id_status")
	private String idStatus;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("id")
	private int id;

	@SerializedName("lat")
	private String lat;

	@SerializedName("long")
	private String jsonMemberLong;

	@SerializedName("time_out")
	private String timeOut;

	public String getIdStatus(){
		return idStatus;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public String getIdUser(){
		return idUser;
	}

	public int getId(){
		return id;
	}

	public String getLat(){
		return lat;
	}

	public String getJsonMemberLong(){
		return jsonMemberLong;
	}

	public String getTimeOut(){
		return timeOut;
	}
}