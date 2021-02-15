package com.jgduhao.clickexec.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jgduhao.clickexec.model.BashScript;

public interface BashScriptDao extends JpaRepository<BashScript, Long> {

}
