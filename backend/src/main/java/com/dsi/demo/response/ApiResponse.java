package com.dsi.demo.response;


import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public abstract class ApiResponse implements Serializable {

	private Long timestamp;
	protected Boolean success;

	protected Integer status;

	protected String message;


	public ApiResponse(Boolean success){
		this.timestamp = new Date().getTime();
		this.success = success;

	}


}
