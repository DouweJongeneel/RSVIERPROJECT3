/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.web.controllers;

import com.adm.entities.Klant;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Albert
 */
@ManagedBean(name = "sessionController", eager = true)
@SessionScoped
public class SessionController implements Serializable{

	private Klant klant;

	public SessionController(){}
	
	public String moveToLogin() {
		return "login";
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public Klant getKlant() {
		return klant;
	}
}
