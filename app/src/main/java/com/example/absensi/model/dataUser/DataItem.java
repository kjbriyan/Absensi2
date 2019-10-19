package com.example.absensi.model.dataUser;


import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("id_leader")
	private String idLeader;

	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("name")
	private String name;

	@SerializedName("id_position")
	private String idPosition;

	@SerializedName("photo")
	private String photo;

	@SerializedName("location")
	private String location;

	@SerializedName("id")
	private int id;

	@SerializedName("id_location")
	private String idLocation;

	@SerializedName("position")
	private String position;

	@SerializedName("longitude")
	private String longitude;

	public String getIdLeader(){
		return idLeader;
	}

	public String getNoHp(){
		return noHp;
	}

	public String getLatitude(){
		return latitude;
	}

	public String getName(){
		return name;
	}

	public String getIdPosition(){
		return idPosition;
	}

	public String getPhoto(){
		return photo;
	}

	public String getLocation(){
		return location;
	}

	public int getId(){
		return id;
	}

	public String getIdLocation(){
		return idLocation;
	}

	public String getPosition(){
		return position;
	}

	public String getLongitude(){
		return longitude;
	}
}