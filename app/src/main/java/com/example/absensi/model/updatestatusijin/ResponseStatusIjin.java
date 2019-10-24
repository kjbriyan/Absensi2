package com.example.absensi.model.updatestatusijin;


import com.google.gson.annotations.SerializedName;


public class ResponseStatusIjin{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}