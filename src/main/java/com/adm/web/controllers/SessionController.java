/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.web.controllers;

import com.adm.entities.Klant;
import com.adm.session.KlantFacade;
import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Albert
 */
@ManagedBean(name = "sessionController", eager = true)
@SessionScoped
public class SessionController implements Serializable {

	private String password;
	private String userName;

	@EJB
	KlantFacade klantFacade;

	public String moveToLogin() {
		return "login";
	}

	public String checkLogin(){
		System.out.println(userName);
		System.out.println(password);
	
		Klant tempKlant = klantFacade.withNamedQuery("Klant.findByEmail", userName).get(0);
		
		if (password.equals(tempKlant.getPassword())) {
			naarSessieVariabele("klant", tempKlant);
			return "klantProfile";
		}else{
			tempKlant = null;
		}
		return null;
	}
	
	/**
	 *
	 * Utility Methoden
	 *
	 */
	//Haal een sessie-object op
	public static Object findBean(String key) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		return sessionMap.get(key);
	}

	//Schrijf een object naar de sessie toe
	public static void naarSessieVariabele(String key, Object value) {
		//Set klant als Sessie variabele
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put(key, value);
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}
	
	public String getUserName(){
		return userName;
	}
	
}
