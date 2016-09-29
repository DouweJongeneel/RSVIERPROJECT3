/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.web.controllers;

import com.adm.entities.Klant;
import com.adm.session.KlantFacade;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Albert
 *
 * Deze class regelt het maken, ophalen en verwijderen van sessievariabelen
 * tevens het in en uitloggen
 */
@ManagedBean(name = "sessionController", eager = true)
@SessionScoped
public class SessionController implements Serializable {

	private String password;
	private String userName;
	private static String basePath;

	@EJB
	KlantFacade klantFacade;

	public String moveToLogin() {
		return "/pages/klant/login";
	}

	public String checkLogin() {
		List<Klant> tempKlant = klantFacade.withNamedQuery("Klant.findByEmailPassword", new String[]{"email", "password"}, new String[]{userName, password});

		if (tempKlant.size() == 1) {
			naarSessieVariabele("klant", tempKlant.get(0));
			
			password = null;
			userName = null;
			
			return "/pages/klant/klantProfile";
		} else {
			tempKlant = null;
		}
		return null;
	}

	public String logout() {
		naarSessieVariabele("klant", new Klant());
		System.out.println(((Klant)findBean("klant")).getEmail());
		verwijderSessieVariabele("klantenLijst");
		return "/pages/home?faces-redirect=true";
	}

	/**
	 *
	 * Utility Methoden
	 *
	 */
	//Haal een sessie-object op
	public static Object findBean(String key) {
		return getSessionMap().get(key);
	}
//
//	public static Object containsBean(String key) {
//		return getSessionMap().containsKey(key);
//	}

	//Schrijf een object naar de sessie toe
	public static void naarSessieVariabele(String key, Object value) {
		getSessionMap().put(key, value);
	}

	//Verwijder een object uit de sessie
	public static void verwijderSessieVariabele(String key) {

		getSessionMap().remove(key);
	}

	private static Map<String, Object> getSessionMap() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		return sessionMap;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public static String getBasePath() {
		return basePath;
	}

	public static void setBasePath(String basePath) {
		SessionController.basePath = basePath;
	}

}
