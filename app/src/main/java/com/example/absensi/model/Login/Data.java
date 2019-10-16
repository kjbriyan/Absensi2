package com.example.absensi.model.Login;


import com.google.gson.annotations.SerializedName;


public class Data{

	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("photo")
	private String photo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("email_verified_at")
	private String emailVerifiedAt;

	@SerializedName("id_location")
	private String idLocation;

	@SerializedName("type")
	private String type;

	@SerializedName("id_leader")
	private String idLeader;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("id_position")
	private String idPosition;

	@SerializedName("imei")
	private String imei;

	@SerializedName("id")
	private String id;

	@SerializedName("tipe")
	private String tipe;

	@SerializedName("email")
	private String email;

	public String getNoHp(){
		return noHp;
	}

	public String getPhoto(){
		return photo;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public String getIdLocation(){
		return idLocation;
	}

	public String getType(){
		return type;
	}

	public String getIdLeader(){
		return idLeader;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getName(){
		return name;
	}

	public String getIdPosition(){
		return idPosition;
	}

	public String getImei(){
		return imei;
	}

	public String getId(){
		return id;
	}

	public String getTipe(){
		return tipe;
	}

	public String getEmail(){
		return email;
	}


}