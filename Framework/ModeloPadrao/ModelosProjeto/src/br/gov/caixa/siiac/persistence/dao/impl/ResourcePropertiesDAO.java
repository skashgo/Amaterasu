package br.gov.caixa.siiac.persistence.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.exception.SIIACRuntimeException;
import br.gov.caixa.siiac.model.domain.Propriedades;
import br.gov.caixa.siiac.persistence.dao.IResourcePropertiesDAO;

@Repository
@Scope("prototype")
public class ResourcePropertiesDAO extends GenericDAO<Propriedades> implements IResourcePropertiesDAO {
	
	/**
	 * ResourceProperties
	 * @throws DAOException
	 */
	public ResourcePropertiesDAO() throws DAOException {
		super(Propriedades.class);
	}
	
	/**
	 * getPropriedade
	 */
	public String getPropriedade(String chave, String grupo) throws DAOException {
		Criteria c = getCriteria()
					.add(Restrictions.eq("noPropriedade", chave))
					.add(Restrictions.eq("noGrupo", grupo));
		
		Propriedades props= (Propriedades) c.uniqueResult();
		
		if (props==null)
			throw new SIIACRuntimeException("Erro ao obter propriedade da tabela, o retorno foi nulo para propriedade: "+chave);
			
		String retorno =props.getNoValor(); 

		return retorno;
	}
	
}
