/**
 * Copyright (c) 2009-2011 Caixa Econômica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econômica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e está
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condições de cópia e utilização do todo ou partes dependem de autorização da
 * empresa. Cópias não são permitidas sem expressa autorização. Não pode ser
 * comercializado ou utilizado para propósitos particulares.
 * 
 * Uso exclusivo da Caixa Econômica Federal. A reprodução ou distribuição não
 * autorizada deste programa ou de parte dele, resultará em punições civis e
 * criminais e os infratores incorrem em sanções previstas na legislação em
 * vigor.
 * 
 * Histórico do Subversion:
 * 
 * LastChangedRevision:  
 * LastChangedBy:  
 * LastChangedDate: 
 * 
 * HeadURL: 
 * 
 */
package br.gov.caixa.siiac.persistence.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Id;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.TrilhaHistorico;
import br.gov.caixa.siiac.persistence.dao.IGenericDAO;
import br.gov.caixa.siiac.util.TrilhaHistoricoUtil;


public abstract class GenericDAO<T> implements IGenericDAO<T> 
{
	private transient Class<T> persistenceClass;
	
	private transient SessionFactory sessionFactory;
	
	private Session session;
	
	public 	GenericDAO(Class<T> persistenceClass)
	{
		this.persistenceClass=persistenceClass;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}

	@Autowired
	public SessionFactory getSessionFActory() {
		return this.sessionFactory;
	}
	
	public Criteria getCriteria()
	{
		return this.sessionFactory.getCurrentSession().createCriteria(persistenceClass);
	}
	
	public T findById(Object id) 
	{
		return (T) sessionFactory.getCurrentSession().get(persistenceClass,(Serializable) id);
	}

	public List<T> findAll() 
	{
		Criteria crit=this.sessionFactory.getCurrentSession().createCriteria(persistenceClass);
		return crit.list();
	}

	public List<T> findByCriteria(Criteria crit) 
	{
		return crit.list();
	}

	public T merge(T object) 
	{
		return insertUpdate(null, object, true);
	}

	public void delete(T object)
	{
		TrilhaHistorico t = null;
		if(TrilhaHistoricoUtil.i().hasTrilha(object)){
			try {
				t = TrilhaHistoricoUtil.i().getTrilhaForDelete(object);
			} catch (SIIACException e) {
				e.printStackTrace();
			}
		}
		this.sessionFactory.getCurrentSession().delete(object);
		if(t != null)
			this.sessionFactory.getCurrentSession().save(t);
	}

	public void refresh(T object)
	{
		this.sessionFactory.getCurrentSession().refresh(object);
	}

	public void detached(T object) 
	{
		this.sessionFactory.getCurrentSession().evict(object);
	}

	public void save(T object) 
	{
		this.sessionFactory.getCurrentSession().save(object);
		if(TrilhaHistoricoUtil.i().hasTrilha(object)){
			try {
				TrilhaHistorico t = TrilhaHistoricoUtil.i().getTrilhaForSave(object);
				if(t != null)
				this.sessionFactory.getCurrentSession().save(t);
			} catch (SIIACException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveOrUpdate(T object) 
	{
		insertUpdate(null, object, false);
	}

	public void initialize(Object object) 
	{
		Hibernate.initialize(object);
	}

	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public void flush(){
		this.getSession().flush();
	}
	
	private T insertUpdate(Object objectBack, Object object, boolean merge) {
		boolean gravarlog = TrilhaHistoricoUtil.i().hasTrilha(object);
		T objetoRetorno = null;
		if (gravarlog && objectBack == null) {
			for (Method m : object.getClass().getMethods()) {
				if (m.isAnnotationPresent(Id.class)) {
					try {
						Object id = m.invoke(object);
						if(id != null){
							Session s = sessionFactory.getCurrentSession();
							s.beginTransaction();
							s.setFlushMode(FlushMode.COMMIT);
							objectBack = sessionFactory.getCurrentSession().get(persistenceClass,(Serializable) id);
							s.getTransaction().commit();
							if (s.isOpen()) s.close();
						}
						break;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		TrilhaHistorico t = null;
		if (gravarlog) {
			try {
				if (objectBack != null) {
					t = TrilhaHistoricoUtil.i().getTrilhaForMerge(objectBack, object);
				}
			} catch (SIIACException e) {
				e.printStackTrace();
			}
		}
		if (merge) {
			objetoRetorno = (T) this.sessionFactory.getCurrentSession().merge(object);
		} else {
			this.sessionFactory.getCurrentSession().saveOrUpdate(object);
			objetoRetorno = (T) object;
		}
		if (gravarlog) {
			try {
				if (objectBack == null) {
					t = TrilhaHistoricoUtil.i().getTrilhaForSave(objetoRetorno);
				}
			} catch (SIIACException e) {
				e.printStackTrace();
			}
		}
		if (t != null)
			this.sessionFactory.getCurrentSession().save(t);
		
		return objetoRetorno;
	}
	
	public T mergeObjectBackAndNewTrilha(T objectBack, T object){
		return insertUpdate(objectBack, object, true);
	}
	
	public void saveTrilha(TrilhaHistorico t){
		if (t != null)
			this.sessionFactory.getCurrentSession().save(t);
	}
}
