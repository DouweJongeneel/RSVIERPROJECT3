/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.session;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.*;

/**
 *
 * @author Albert
 */
public abstract class AbstractFacade<T> {

	private Class<T> entityClass;

	public AbstractFacade() {
	}

	public AbstractFacade(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	public T create(T entity) {
	//Houd bij op welke field-validator in de entity iets mis gaat
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
    if(constraintViolations.size() > 0){
        Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
        while(iterator.hasNext()){
            ConstraintViolation<T> cv = iterator.next();
            System.out.println(cv.getRootBeanClass().getName()+"."+cv.getPropertyPath() + " " +cv.getMessage());
        }
    }else{
        getEntityManager().persist(entity);
    }
		
		getEntityManager().persist(entity);
		return entity;
	}

	public void edit(T entity) {
		getEntityManager().merge(entity);
	}

	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public void flush() {getEntityManager().flush();}

	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	public List<T> withNamedQuery(String namedQuery, String parameter, String variable){
      Query query = getEntityManager().createNamedQuery(namedQuery, entityClass);
	  query.setParameter(parameter, variable);
	  return query.getResultList();
	}
	
	public List<T> findAll() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	public List<T> findRange(int[] range) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0] + 1);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public int count() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}
}
