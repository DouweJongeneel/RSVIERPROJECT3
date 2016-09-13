/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.session;

import com.adm.entities.Adrestype;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Albert
 */
@Stateless
public class AdrestypeFacade extends AbstractFacade<Adrestype> {

	@PersistenceContext(unitName = "workshop3PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public AdrestypeFacade() {
		super(Adrestype.class);
	}
	
}
