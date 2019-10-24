package com.example.absensi.model.ijin;


import com.google.gson.annotations.SerializedName;


public class Data{

	@SerializedName("acc")
	private String acc;

	@SerializedName("id_leader")
	private String idLeader;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("id_user")
	private String idUser;

	public String getAcc(){
		return acc;
	}

	public String getIdLeader(){
		return idLeader;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public String getIdUser(){
		return idUser;
	}
}