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

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.ArquivoImagemParecer;
import br.gov.caixa.siiac.persistence.dao.IArquivoImagemParecerDAO;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class ArquivoImagemParecerDAO extends GenericDAO<ArquivoImagemParecer> implements IArquivoImagemParecerDAO {
	
	public ArquivoImagemParecerDAO() {
		super(ArquivoImagemParecer.class);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IArquivoImagemParecerDAO#getArquivoImagemParecer(java.lang.Integer)
	 */
	public ArquivoImagemParecer getArquivoImagemParecer(Integer nuVerificacaoContrato) {
		Criteria crit = getCriteria()
							.createAlias("parecer", "parecer")
							.createAlias("parecer.verificacaoContratoParecer", "verificacaoContratoParecer")
							.add(Restrictions.eq("verificacaoContratoParecer.nuVerificacaoContrato", nuVerificacaoContrato));
		return (ArquivoImagemParecer) crit.uniqueResult();
	}

	
	public byte[] getArquivoImagemParecerSQL(Integer nuParecer, Short anoParecer, Short nuUnidade, Short nuNatural) {
		
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT im_parecer ");
		var1.append("FROM   iacsm001.iactb051_arquivo_imagem_parecer ");
		var1.append("WHERE  nu_parecer = :nuParecer ");
		var1.append("       AND aa_parecer = :aaParecer ");
		var1.append("       AND nu_unidade = :nuUnidade ");
		var1.append("       AND nu_natural = :nuNatural ");
		
		Query q = getSession().createSQLQuery(var1.toString());
		q.setParameter("nuParecer", nuParecer);
		q.setParameter("aaParecer", anoParecer);
		q.setParameter("nuUnidade", nuUnidade);
		q.setParameter("nuNatural", nuNatural);
		
		return (byte[]) q.uniqueResult();
	}
	
	
}
