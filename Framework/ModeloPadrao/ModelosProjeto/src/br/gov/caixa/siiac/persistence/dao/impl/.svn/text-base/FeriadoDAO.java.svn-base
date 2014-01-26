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

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.Feriado;
import br.gov.caixa.siiac.persistence.dao.IFeriadoDAO;

@Repository
@Scope("prototype")
public class FeriadoDAO extends GenericDAO<Feriado> implements IFeriadoDAO {
	
	public FeriadoDAO() {
		super(Feriado.class);
	}
	
	public boolean isFeriado(Date data, Integer unidadeNatural, Short unidade) {
		SQLQuery q = getSession().createSQLQuery("SELECT count(*) FROM icosm001.iacvw011_feriado WHERE nu_natural = " + unidadeNatural + " AND nu_unidade = " + unidade + " AND dt_feriado = '" +getDate(data) + "'");
		BigInteger res = (BigInteger) q.uniqueResult();
		return res.intValue() > 0;
	}
	
	public Integer obtemFeriadosUnidade(Short unidade, Integer natural, Date data1, Date data2) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT");
		sbSQL.append("  count(*) ");
		sbSQL.append("FROM");
		sbSQL.append("  ( ");
		sbSQL.append("  SELECT");
		sbSQL.append("    data ");
		sbSQL.append("  FROM");
		sbSQL.append("    generate_series('" + getDate(data1) + "', '" + getDate(data2) + "', interval '1 day') data ");
		sbSQL.append("  WHERE date_part('dow', data) IN (0, 6) ");
		sbSQL.append("  UNION ");
		sbSQL.append("  SELECT");
		sbSQL.append("    dt_feriado AS data ");
		sbSQL.append("  FROM");
		sbSQL.append("    icosm001.iacvw011_feriado ");
		sbSQL.append("  WHERE nu_unidade = " + unidade);
		sbSQL.append("    AND nu_natural = " + natural);
		sbSQL.append("    AND dt_feriado BETWEEN '" + getDate(data1) + "' and '" +getDate(data2) + "'");
		sbSQL.append("  ) AS datas");
		
		SQLQuery q = getSession().createSQLQuery(sbSQL.toString());
		BigInteger res = (BigInteger) q.uniqueResult();
		return res.intValue();
	}
	
	private String getDate(Date data){
		return new SimpleDateFormat("yyyy-MM-dd").format(data);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IFeriadoDAO#getNextFeriados(java.lang.Short, java.lang.Integer)
	 */
	public List<String> getNextFeriados(Short unidade, Integer nuNatural, Integer qtdDias) {
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT CAST(dt_feriado AS VARCHAR) ");
		sql.append("FROM   icosm001.iacvw011_feriado ");
		sql.append("WHERE  nu_natural = :nu_natural ");
		sql.append("       AND nu_unidade = :nu_unidade ");
		sql.append("       AND dt_feriado BETWEEN current_date AND date(current_date + interval '" + qtdDias + " days') ");
		sql.append("UNION ");
		sql.append("SELECT CAST(dt_feriado AS VARCHAR) ");
		sql.append("FROM   icosm001.iacvw011_feriado feriado ");
		sql.append("       LEFT JOIN icosm001.iacvw003_unidade unidade ");
		sql.append("              ON unidade.sg_localizacao = feriado.sg_uf_l22 ");
		sql.append("WHERE  co_abrna_ggrfa_f03 = 'E' ");
		sql.append("       AND unidade.nu_natural = :nu_natural ");
		sql.append("       AND unidade.nu_unidade = :nu_unidade ");
		sql.append("       AND dt_feriado BETWEEN current_date AND date(current_date + interval '" + qtdDias + " days') ");
		sql.append("UNION ");
		sql.append("SELECT CAST(dt_feriado AS VARCHAR) ");
		sql.append("FROM   icosm001.iacvw011_feriado ");
		sql.append("WHERE  co_abrna_ggrfa_f03 = 'N' ");
		sql.append("       AND dt_feriado BETWEEN current_date AND date(current_date + interval '" + qtdDias + " days') ");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setShort("nu_unidade", unidade);
		query.setInteger("nu_natural", unidade);
//		query.setString("days", qtdDias);
		
		return query.list();
	}
}