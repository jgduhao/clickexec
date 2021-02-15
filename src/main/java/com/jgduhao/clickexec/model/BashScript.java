package com.jgduhao.clickexec.model;

import javax.persistence.*;

/**
 * 执行命令
 * @author hao
 *
 */
@Entity
@Table(name = "bash_script")
public class BashScript {
	
	/**
	 * 主键Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "script_id")
	private long scriptId;
	
	/**
	 * 脚本名称
	 */
	@Column(name = "script_name")
	private String scriptName;
	
	/**
	 * 脚本类型 1-单行 2-文件
	 */
	@Column(name = "script_type")
	private int scriptType;
	
	/**
	 * 脚本内容 (脚本类型为单行时)
	 */
	@Column(name = "script_content")
	private String scriptContent;
	
	/**
	 * 脚本文件路径（脚本类型为文件时）
	 */
	@Column(name = "script_file_path")
	private String scriptFilePath;
	
	/**
	 * 脚本排序码
	 */
	@Column(name = "script_order")
	private long scriptOrder;
	
	

	public BashScript() {
		super();
	}

	public long getScriptId() {
		return scriptId;
	}

	public void setScriptId(long scriptId) {
		this.scriptId = scriptId;
	}

	public String getScriptName() {
		return scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	public String getScriptFilePath() {
		return scriptFilePath;
	}

	public void setScriptFilePath(String scriptFilePath) {
		this.scriptFilePath = scriptFilePath;
	}

	public long getScriptOrder() {
		return scriptOrder;
	}

	public void setScriptOrder(long scriptOrder) {
		this.scriptOrder = scriptOrder;
	}
	
	public int getScriptType() {
		return scriptType;
	}

	public void setScriptType(int scriptType) {
		this.scriptType = scriptType;
	}
	
	public String getScriptContent() {
		return scriptContent;
	}

	public void setScriptContent(String scriptContent) {
		this.scriptContent = scriptContent;
	}

	@Override
	public String toString() {
		return "BashScript [scriptId=" + scriptId + ", scriptName=" + scriptName + ", scriptType=" + scriptType
				+ ", scriptContent=" + scriptContent + ", scriptFilePath=" + scriptFilePath + ", scriptOrder="
				+ scriptOrder + "]";
	}

	

	
	
	
	

}
