package com.jgduhao.clickexec.exception;

import com.jgduhao.clickexec.consts.ScriptError;

public class BashScriptException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6671719947646974402L;
	
	private ScriptError error;

	public BashScriptException() {
		super();
	}
	
	public BashScriptException(ScriptError error) {
		this.error = error;
	}
	
	public String getErrorCode() {
		return error.getErrorCode();
	}
	
	public String getErrorMsg() {
		return error.getErrorMsg();
	}
	
	public ScriptError getError() {
		return error;
	}
	
	

}
