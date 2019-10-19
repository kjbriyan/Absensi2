package com.example.absensi.model.checkin;


import com.google.gson.annotations.SerializedName;

public class ResponseCheckin{

	@SerializedName("id_status")
	private String idStatus;

	@SerializedName("time_in")
	private String timeIn;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("id")
	private int id;

	@SerializedName("lat")
	private String lat;

	@SerializedName("long")
	private String jsonMemberLong;

	public String getIdStatus(){
		return idStatus;
	}

	public String getTimeIn(){
		return timeIn;
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
}