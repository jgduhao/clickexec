package com.jgduhao.clickexec.consts;

public enum ScriptError {
	
	ScriptFileNotExists("scriptFileNotExists", "脚本文件不存在"),
	ScriptDataNotExists("scriptDataNotExists", "脚本记录不存在"),
	ScriptExecuteError("scriptExecuteError","脚本文件执行失败"),
	InnerError("error","系统内部错误"),
	Success("success","执行成功");
	
	private String errorCode;
	private String errorMsg;
	private ScriptError(String errorCode,String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

}
