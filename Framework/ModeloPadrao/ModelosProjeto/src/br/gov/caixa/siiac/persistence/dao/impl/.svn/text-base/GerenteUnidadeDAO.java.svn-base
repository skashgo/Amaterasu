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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SQLQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.GerenteUnidade;
import br.gov.caixa.siiac.persistence.dao.IGerenteUnidadeDAO;

@Repository
@Scope("prototype")
public class GerenteUnidadeDAO extends GenericDAO<GerenteUnidade> implements IGerenteUnidadeDAO {
		
	public GerenteUnidadeDAO() {
		super(GerenteUnidade.class);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGerenteUnidadeDAO#getListGerentesUnidade(java.lang.Short)
	 */
	public List<GerenteUnidade> getListGerentesUnidade(Short nuUnidade) {
		SQLQuery sql = getSession().createSQLQuery("select " +
				"	nu_unidade " +
				"	, nu_matricula " +
				"	, no_pessoa " +
				"	, nu_undde_alcco_u24 " +
				"	, no_funcao " +
				"	, nu_tipo_funcao_h04 " +
				"	, (CASE WHEN (rep.nu_unidade, rep.nu_matricula) IN (SELECT nu_unidade, nu_matricula_h01 AS resultado " +
				"         FROM   icosm001.iacvw001_rspnl_unidade " +
				"         WHERE  ic_eventual = 'S') THEN true " +
				"				                    ELSE false " + 
                "   END) AS eventual  " +
				"from icosm001.iacvw10_gerente_undde rep " +
				"where nu_unidade = :unidade");
		sql.setParameter("unidade", nuUnidade);
		
		sql.addEntity(GerenteUnidade.class);
		return sql.list();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IGerenteUnidadeDAO#findByNuMatricula(java.lang.Integer)
	 */
	public GerenteUnidade findByNuMatricula(String coMatricula) {
		SQLQuery sql = getSession().createSQLQuery("select " +
				"	nu_unidade " +
				"	, nu_matricula " +
				"	, no_pessoa " +
				"	, nu_undde_alcco_u24 " +
				"	, no_funcao " +
				"	, nu_tipo_funcao_h04 " +
				"	, (CASE WHEN (rep.nu_unidade, rep.nu_matricula) IN (SELECT nu_unidade, nu_matricula_h01 AS resultado " +
				"         FROM   icosm001.iacvw001_rspnl_unidade " +
				"         WHERE  ic_eventual = 'S') THEN true " +
				"				                    ELSE false " + 
                "   END) AS eventual  " +
				"from icosm001.iacvw10_gerente_undde rep " +
				"where nu_matricula = :matricula");

	    Matcher matcher = Pattern.compile("[^0-9]").matcher(coMatricula);
		sql.setParameter("matricula", Integer.valueOf(matcher.replaceAll("")));
		
		sql.addEntity(GerenteUnidade.class);
		return (GerenteUnidade) sql.uniqueResult();
	}
	
}