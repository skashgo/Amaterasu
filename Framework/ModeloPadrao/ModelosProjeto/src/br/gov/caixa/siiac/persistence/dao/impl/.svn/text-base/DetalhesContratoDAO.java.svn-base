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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.CaixaAqui;
import br.gov.caixa.siiac.model.domain.DetalhesContrato;
import br.gov.caixa.siiac.persistence.dao.IDetalhesContratoDAO;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class DetalhesContratoDAO extends GenericDAO<DetalhesContrato> implements IDetalhesContratoDAO {
	
	public DetalhesContratoDAO() {
		super(DetalhesContrato.class);
	}
	
	public boolean existeNuConveniado(Integer nuConveniado) {
		Criteria c = getSession().createCriteria(CaixaAqui.class);
		
		c.add(Restrictions.eq("id.nuConvenioC23", nuConveniado));
		
		List l = c.list();
		if(l != null && !l.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	public String getNomeByNuConveniado(Integer nuConveniado) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT" );
		sbSQL.append("  caixaaqui0_.no_fantasia" );
		sbSQL.append("  , caixaaqui0_.no_empresa " );
		sbSQL.append("FROM" );
		sbSQL.append("  icosm001.iacvw009_caixa_aqui caixaaqui0_ " );
		sbSQL.append("WHERE caixaaqui0_.nu_convenio_c23=" + nuConveniado );
		sbSQL.append(" limit 1");
		Query q = getSession().createSQLQuery(sbSQL.toString());
		Object result = q.uniqueResult();
		String nomeEmpresa = "", nomeFantasia = "";
		if (result != null) {
			Object[] obj = (Object[]) result;
			nomeEmpresa = obj[1] == null ? "" : String.valueOf(obj[1]);
			nomeFantasia = obj[0] == null ? "" : String.valueOf(obj[0]);
		}
		
		if (Utilities.notEmpty(nomeFantasia))
			return nomeFantasia;
		else 
			return nomeEmpresa;
	}
}