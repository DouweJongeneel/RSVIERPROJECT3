/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Klant;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Albert
 */
@Stateless
public class KlantFacade extends AbstractFacade<Klant> {

	@PersistenceContext(unitName = "com.mycompany_rsvierproject3_war_1.0-SNAPSHOTPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public KlantFacade() {
		super(Klant.class);
	}
	
}
