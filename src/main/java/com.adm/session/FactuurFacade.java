/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.session;

import com.adm.entities.Factuur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Albert
 */
@Stateless
public class FactuurFacade extends AbstractFacade<Factuur> {

	@PersistenceContext(unitName = "workshop3PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public FactuurFacade() {
		super(Factuur.class);
	}
	
}
