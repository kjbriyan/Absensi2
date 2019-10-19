package com.example.absensi.model.dataUser;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class ResponseDataUser{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public List<DataItem> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}