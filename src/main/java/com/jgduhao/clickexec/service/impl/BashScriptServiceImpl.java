package com.jgduhao.clickexec.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgduhao.clickexec.consts.ScriptConsts;
import com.jgduhao.clickexec.consts.ScriptError;
import com.jgduhao.clickexec.dao.BashScriptDao;
import com.jgduhao.clickexec.exception.BashScriptException;
import com.jgduhao.clickexec.model.BashScript;
import com.jgduhao.clickexec.service.BashScriptService;

@Service
public class BashScriptServiceImpl implements BashScriptService {
	
	private Log log = LogFactory.getLog(BashScriptServiceImpl.class);
	
	@Autowired
	private BashScriptDao bashScriptDao;

	@Override
	public List<BashScript> getScriptAllList() {
		return bashScriptDao.findAll();
	}

	@Override
	public Optional<BashScript> getOneScriptById(long scriptId) {
		return bashScriptDao.findById(scriptId);
	}

	@Override
	public BashScript saveScript(BashScript script) {
		return bashScriptDao.save(script);
	}

	@Override
	public void executeScript(long scriptId) {
		BashScript script = bashScriptDao.findById(scriptId).orElseThrow(()-> new BashScriptException(ScriptError.ScriptDataNotExists));
		int status = ScriptConsts.SCRIPT_EXECUTE_OTHER_ERR;
		switch(script.getScriptType()) {
		case ScriptConsts.SCRIPT_TYPE_LINE:
			status = runCommand(script.getScriptContent());
			break;
		case ScriptConsts.SCRIPT_TYPE_FILE:
			if(!new File(script.getScriptFilePath()).exists()) {
				throw new BashScriptException(ScriptError.ScriptFileNotExists);
			}
			status = runCommand(script.getScriptFilePath());
			break;
		}
		if(status != ScriptConsts.SCRIPT_EXECUTE_SUCCESS) {
			throw new BashScriptException(ScriptError.ScriptExecuteError);
		}
	}
	
	/**
	 * 运行脚本
	 * @param cmd
	 * @return
	 */
	private int runCommand(String cmd) {
		String[] cmds = {"/bin/sh", "-c", cmd};
		try {
			ProcessBuilder pbuilder = new ProcessBuilder(cmds);
			pbuilder.redirectErrorStream(true);  
			final Process process = pbuilder.start();
			
			Thread outputThread = new Thread(() -> {
				try(BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));){
					String line = "";
					while(!Thread.currentThread().isInterrupted() && (line = br.readLine()) != null) {
						log.info(line);
					}
				} catch(IOException e) {
					log.error(e);
				}
				log.info("output end");
			});
			outputThread.start();
			
			boolean waitStatus = process.waitFor(ScriptConsts.SCRIPT_TIMOUT, TimeUnit.SECONDS);
			int status = ScriptConsts.SCRIPT_EXECUTE_OTHER_ERR;
			if(waitStatus) {
				status = process.waitFor();
			}
			outputThread.interrupt();
			if(process.isAlive()) {
				process.destroy();
			}
			if(!waitStatus) {
				throw new BashScriptException(ScriptError.ScriptExecuteTimeout);
			}
			return status;
		} catch (IOException e) {
			log.error(e);
		} catch (InterruptedException e) {
			log.error(e);
		}
		return ScriptConsts.SCRIPT_EXECUTE_OTHER_ERR;
	}

	@Override
	public void deleteScript(long scriptId, boolean delFile) {
		BashScript script = bashScriptDao.findById(scriptId).orElseThrow(()-> new BashScriptException(ScriptError.ScriptDataNotExists));
		String scriptFilePath = script.getScriptFilePath();
		bashScriptDao.deleteById(scriptId);
		if(delFile && ScriptConsts.SCRIPT_TYPE_FILE == script.getScriptType()) {
			File scriptFile = new File(scriptFilePath);
			if(scriptFile.exists()) {
				scriptFile.delete();
			}
		}
	}

}
