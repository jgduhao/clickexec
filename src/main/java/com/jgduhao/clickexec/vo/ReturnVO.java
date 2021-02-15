package com.jgduhao.clickexec.vo;

import com.jgduhao.clickexec.consts.ScriptError;

public class ReturnVO<T> {
	
	private String status;
	
	private String message;
	
	private T rstData;

	public ReturnVO() {
		super();
	}

	public ReturnVO(ScriptError error, T rstData) {
		super();
		this.status = error.getErrorCode();
		this.message = error.getErrorMsg();
		this.rstData = rstData;
	}
	
	public ReturnVO(ScriptError error) {
		this.status = error.getErrorCode();
		this.message = error.getErrorMsg();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getRstData() {
		return rstData;
	}

	public void setRstData(T rstData) {
		this.rstData = rstData;
	}

	@Override
	public String toString() {
		return "ReturnVO [status=" + status + ", message=" + message + ", rstData=" + rstData + "]";
	}
	
	
	

}
