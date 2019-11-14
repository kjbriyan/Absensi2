package com.example.absensi.model.dataijin;


import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("acc")
	private String acc;

	@SerializedName("id_leader")
	private int idLeader;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("id_reason")
	private String idReason;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("id_user")
	private int idUser;
	@SerializedName("name")
	private String name;

	public String getName() {
		return name;
	}

	public String getAcc(){
		return acc;
	}

	public int getIdLeader(){
		return idLeader;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public String getIdReason(){
		return idReason;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public int getIdUser(){
		return idUser;
	}
}