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

import br.gov.caixa.siiac.model.domain.VerificacaoContratoObservacoes;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoObservacaoDAO;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class VerificacaoContratoObservacaoDAO extends GenericDAO<VerificacaoContratoObservacoes> implements IVerificacaoContratoObservacaoDAO {
	
	public VerificacaoContratoObservacaoDAO() {
		super(VerificacaoContratoObservacoes.class);
	}
	
	public VerificacaoContratoObservacoes findById_Fonte(Integer id, Character fonte, Integer vc) {
		String sql = "";
		if (fonte == VerificacaoContratoObservacoes.FONTE_OBSERVACAO_APONTAMENTO_ID_BLOCO) {
			sql = "SELECT o FROM VerificacaoContratoObservacoes o WHERE o.blocoChecklistProduto.nuBlocoChecklistProduto = :id AND o.verificacaoContrato.nuVerificacaoContrato = :vc AND o.icFonte = :fonte";
		} else {
			sql = "SELECT o FROM VerificacaoContratoObservacoes o WHERE o.itemVerificacaoChecklistProduto.nuItemVerificacaoChecklistProduto = :id AND o.verificacaoContrato.nuVerificacaoContrato = :vc AND o.icFonte = :fonte";
		}
		Query q = getSession().createQuery(sql);
		q.setParameter("id", id);
		q.setParameter("vc", vc);
		q.setParameter("fonte", fonte);
		return (VerificacaoContratoObservacoes) q.uniqueResult();
	}
	
	public List<VerificacaoContratoObservacoes> findByNuVerificacaoContrato(Integer nuVerificacaoContrato) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("verificacaoContrato.nuVerificacaoContrato", nuVerificacaoContrato));
		return crit.list();
	}
}