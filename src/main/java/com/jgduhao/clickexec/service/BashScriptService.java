package com.jgduhao.clickexec.service;

import java.util.List;
import java.util.Optional;

import com.jgduhao.clickexec.model.BashScript;

public interface BashScriptService {
	
	/**
	 * 展示所有脚本
	 * @return
	 */
	public List<BashScript> getScriptAllList(); 
	
	/**
	 * 通过id查询单个脚本
	 * @param scriptId
	 * @return
	 */
	public Optional<BashScript> getOneScriptById(long scriptId);
	
	/**
	 * 保存脚本
	 * @param script
	 * @return
	 */
	public BashScript saveScript(BashScript script);
	
	/**
	 * 执行脚本
	 * @param scriptId
	 * @return
	 */
	public void executeScript(long scriptId);
	
	/**
	 * 删除脚本 
	 * @param srciptId
	 * @param delFile 是否删除脚本文件
	 */
	public void deleteScript(long scriptId, boolean delFile);

}
