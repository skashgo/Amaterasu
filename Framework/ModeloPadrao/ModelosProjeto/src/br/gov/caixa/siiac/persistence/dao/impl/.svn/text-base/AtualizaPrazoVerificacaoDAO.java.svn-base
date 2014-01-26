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

import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.persistence.dao.IAtualizaPrazoVerificacaoDAO;

@Repository
@Scope("prototype")
public class AtualizaPrazoVerificacaoDAO extends GenericDAO<Contrato> implements IAtualizaPrazoVerificacaoDAO {
	
	public AtualizaPrazoVerificacaoDAO() {
		super(Contrato.class);
	}

	
	public void updateContratoVerificadoComoOk(Integer nuContrato) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE iacsm001.IACTB004_CONTRATO c SET ic_prazo_verificacao = 'OK' "); 
		sql.append(" WHERE NOT EXISTS (SELECT nu_verificacao_contrato FROM iacsm001.IACTB018_VERIFICACAO_CONTRATO v WHERE c.nu_contrato = v.nu_contrato) ");
		if(nuContrato != null){
			sql.append(" AND nu_contrato = " + nuContrato);
		}
		SQLQuery q = getSession().createSQLQuery(sql.toString());
		q.executeUpdate();
		this.flush();
		
	}	

	@SuppressWarnings("rawtypes")
	public List getContratoNaoVerificado(Integer nuContrato) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT" );
		sbSQL.append("   c.nu_contrato nu_contrato " );
		sbSQL.append(" , c.nu_unidade_rspnl_contrato nu_unidade " );
		sbSQL.append(" , c.nu_natural_undde_rspnl_contrato nu_natural " );
		sbSQL.append(" FROM iacsm001.IACTB004_CONTRATO c " );
		sbSQL.append(" WHERE EXISTS (SELECT nu_verificacao_contrato FROM iacsm001.IACTB018_VERIFICACAO_CONTRATO v WHERE c.nu_contrato = v.nu_contrato) ");
		
		if(nuContrato != null) {
			sbSQL.append(" AND c.nu_contrato = " );
			sbSQL.append(nuContrato);
			sbSQL.append(" ");
		}
//		sbSQL.append(" GROUP BY contrato.nu_contrato, contrato.ic_prazo_verificacao" );
		
		SQLQuery q = getSession().createSQLQuery(sbSQL.toString());
		return q.list();
	}
	
	public Date getMenorDataLimiteServicoVerificacao(Integer nuContrato) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select min(dt_limite_verificacao)");
		sql.append(" from iacsm001.IACTB018_VERIFICACAO_CONTRATO");
		sql.append(" where nu_contrato = :nu_contrato ");
		SQLQuery q = getSession().createSQLQuery(sql.toString());
		q.setParameter("nu_contrato", nuContrato);
		
		return (Date) q.uniqueResult();
	}


	public void updatePrazoContratoNaoVerificado(Integer nuContrato, Short qtPrazo) {
		String sql = "UPDATE iacsm001.iactb004_contrato SET qt_prazo_verificacao = " + String.valueOf(qtPrazo) + " WHERE nu_contrato = " + nuContrato;
		
		SQLQuery query = getSession().createSQLQuery(sql);
		
		query.executeUpdate();
		this.flush();
	}

}