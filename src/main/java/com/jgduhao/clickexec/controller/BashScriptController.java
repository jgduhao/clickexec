package com.jgduhao.clickexec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgduhao.clickexec.consts.ScriptError;
import com.jgduhao.clickexec.exception.BashScriptException;
import com.jgduhao.clickexec.model.BashScript;
import com.jgduhao.clickexec.service.BashScriptService;
import com.jgduhao.clickexec.vo.ReturnVO;

@RestController
@RequestMapping("/bashScript")
public class BashScriptController {
	
	@Autowired
	private BashScriptService bashScriptService;
	
	@ExceptionHandler({Exception.class})
	public ReturnVO<BashScript> handlerException(Exception e){
		if(e instanceof BashScriptException) {
			BashScriptException be = (BashScriptException) e;
			return new ReturnVO<BashScript>(be.getError());
		}
		return new ReturnVO<BashScript>(ScriptError.InnerError);
	}
	
	@PostMapping("/getAllList")
	public ReturnVO<List<BashScript>> getAllList(){
		return new ReturnVO<List<BashScript>>(ScriptError.Success,
				bashScriptService.getScriptAllList());
	}
	
	@PostMapping("/getOne")
	public ReturnVO<BashScript> getOne(@RequestParam(value = "scriptId") long scriptId){
		return new ReturnVO<BashScript>(ScriptError.Success,
				bashScriptService.getOneScriptById(scriptId).orElseThrow(() -> new BashScriptException(ScriptError.ScriptDataNotExists)));
	}
	
	@PostMapping("/save")
	public ReturnVO<BashScript> saveScript(@RequestBody BashScript script){
		return new ReturnVO<BashScript>(ScriptError.Success, bashScriptService.saveScript(script));
	}
	
	@PostMapping("/deleteOne")
	public ReturnVO<BashScript> deleteOne(@RequestParam(value = "scriptId") long scriptId,@RequestParam(value = "delFile") boolean delFile){
		bashScriptService.deleteScript(scriptId, delFile);
		return new ReturnVO<BashScript>(ScriptError.Success);
	}
	
	@PostMapping("/execute")
	public ReturnVO<BashScript> executeScript(@RequestParam(value = "scriptId") long scriptId){
		bashScriptService.executeScript(scriptId);
		return new ReturnVO<BashScript>(ScriptError.Success);
	}
	
	

}
