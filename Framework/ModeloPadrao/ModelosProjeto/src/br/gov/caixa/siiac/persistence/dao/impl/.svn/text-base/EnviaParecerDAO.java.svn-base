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

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Parecer;
import br.gov.caixa.siiac.persistence.dao.IEnviaParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IParecerDAO;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class EnviaParecerDAO extends GenericDAO<Parecer> implements IEnviaParecerDAO {
	
	public EnviaParecerDAO() {
		super(Parecer.class);
	}
	
	private IParecerDAO parecerDAO;
	
	
	@Autowired
	public void setParecerDAO(IParecerDAO parecerDAO) {
		this.parecerDAO = parecerDAO;
	}


	/**
	 * Método responsável por obter o e-mail do Conveniado através do número do contrato.
	 * @param nuContrato
	 */
	public String[] getConveniado(Integer nuConvenio) {
		
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT caixa.no_email, caixa.no_empresa AS email ");
		var1.append("FROM   icosm001.iacvw009_caixa_aqui caixa ");
		var1.append("WHERE  caixa.nu_convenio_c23 = :nuConvenio  LIMIT 1");
		
		SQLQuery query = getSession().createSQLQuery(var1.toString());
		query.setParameter("nuConvenio", nuConvenio);
		
		Object[] retorno = (Object[]) query.uniqueResult();
		if (retorno != null)
		{
			String[] valores = new String[2];
			valores[0] = retorno[0].toString();
			valores[1] = retorno[1].toString();
			
			return valores;
		}
		
		return null;
	}

	
	public String[] getEmpregadoResponsavelUnidade(Short nuUnidadeResponsavelContrato) {
		
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT u.nu_matricula_h01, e.no_pessoa ");
		var1.append("FROM   icosm001.iacvw001_rspnl_unidade AS u ");
		var1.append("INNER JOIN icosm001.iacw002_empregado_caixa AS e ");
		var1.append("ON u.nu_matricula_h01 = e.nu_matricula ");
		var1.append("WHERE  u.nu_unidade = :nuUnidadeResponsavelContrato  LIMIT 1");
		
		SQLQuery query = getSession().createSQLQuery(var1.toString());
		query.setParameter("nuUnidadeResponsavelContrato", nuUnidadeResponsavelContrato);
		
		Object[] retorno = (Object[]) query.uniqueResult();
		if (retorno != null)
		{
			String[] valores = new String[2];
			valores[0] = retorno[0].toString();
			valores[1] = retorno[1].toString();
			
			return valores;
		}
		
		return null;
	}
	
	
	public String[] getUnidAliBensImoveisVincUnidRespContrato(Short nuUnidadeResponsavelContrato)
	{
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT c.co_comunicacao, u.no_unidade  ");
		var1.append("FROM   icosm001.iacvw004_vinculacao_unidade AS v ");
		var1.append("       inner join icosm001.iacvw003_unidade AS u ");
		var1.append("               ON u.nu_unidade = v.nu_unde_vnclra_u24 ");
		var1.append("       inner join icosm001.iacvw006_caixa_postal_unidade AS c ");
		var1.append("               ON u.nu_unidade = c.nu_unidade_v03 ");
		var1.append("WHERE  v.nu_tp_vinculo_u22 = 4 ");
		var1.append("       AND u.nu_tp_unidade_u21 IN ( 14, 49 ) ");
		var1.append("       AND v.nu_unidade = :nuUnidadeResponsavelContrato ");
		var1.append("       AND v.nu_processo_u27 IN ( 87, 8724 ) LIMIT 1");
		
		SQLQuery query = getSession().createSQLQuery(var1.toString());
		query.setParameter("nuUnidadeResponsavelContrato", nuUnidadeResponsavelContrato);
		
		Object[] retorno = (Object[]) query.uniqueResult();
		if (retorno != null)
		{
			String[] valores = new String[2];
			valores[0] = retorno[0].toString();
			valores[1] = retorno[1].toString();
			
			return valores;
		}
		
		return null;
	}


	public String[] getUnidConfVincUnidRespContrato(Short nuUnidadeResponsavelContrato)
	{
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT c.co_comunicacao AS email, u.no_unidade ");
		var1.append("FROM   icosm001.iacvw004_vinculacao_unidade vinUnid ");
		var1.append("       INNER JOIN icosm001.iacvw006_caixa_postal_unidade AS c ");
		var1.append("               ON vinUnid.nu_unde_vnclra_u24 = c.nu_unidade_v03 ");
		var1.append("INNER JOIN icosm001.iacvw003_unidade AS u ON u.nu_unidade = c.nu_unidade_v03 ");
		var1.append("WHERE  ( nu_tp_vinculo_u22 = 4 ) ");
		var1.append("       AND nu_processo_u27 IN ( 8703, 8737, 8768 ) ");
		var1.append("       AND vinUnid.nu_unidade = :nuUnidadeResponsavelContrato  LIMIT 1");
		
		SQLQuery query = getSession().createSQLQuery(var1.toString());
		query.setParameter("nuUnidadeResponsavelContrato", nuUnidadeResponsavelContrato);
		
		Object[] retorno = (Object[]) query.uniqueResult();
		if (retorno != null)
		{
			String[] valores = new String[2];
			valores[0] = retorno[0].toString();
			valores[1] = retorno[1].toString();
			
			return valores;
		}
		
		return null;
	}
	
	public String[] getUnidEmpreRespParecer(Integer nuMatriculaRespParecer) {
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT caixa.co_comunicacao, u.no_unidade ");
		var1.append("FROM   icosm001.iacvw006_caixa_postal_unidade AS caixa ");
		var1.append("       inner join icosm001.iacw002_empregado_caixa AS e ");
		var1.append("               ON caixa.nu_unidade_v03 = e.nu_unidade ");
		var1.append("INNER JOIN icosm001.iacvw003_unidade AS u ON u.nu_unidade = e.nu_unidade ");
		var1.append("WHERE  e.nu_matricula = :nuMatriculaRespParecer  LIMIT 1");
		
		SQLQuery query = getSession().createSQLQuery(var1.toString());
		query.setParameter("nuMatriculaRespParecer", nuMatriculaRespParecer);
		
		Object[] retorno = (Object[]) query.uniqueResult();
		if (retorno != null)
		{
			String[] valores = new String[2];
			valores[0] = retorno[0].toString();
			valores[1] = retorno[1].toString();
			
			return valores;
		}
		
		return null;
	}
	
	public String[] getUnidadeResponsavelContrato(Short nuUnidadeRespContrato){
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT caixa.co_comunicacao, u.no_unidade ");
		var1.append("FROM   icosm001.iacvw006_caixa_postal_unidade AS caixa ");
		var1.append("INNER JOIN icosm001.iacvw003_unidade AS u ON u.nu_unidade = caixa.nu_unidade_v03 ");
		var1.append("WHERE  caixa.nu_unidade_v03 = :nuUnidadeRespContrato  LIMIT 1");
		
		SQLQuery query = getSession().createSQLQuery(var1.toString());
		query.setParameter("nuUnidadeRespContrato", nuUnidadeRespContrato);
		
		Object[] retorno = (Object[]) query.uniqueResult();
		if (retorno != null)
		{
			String[] valores = new String[2];
			valores[0] = retorno[0].toString();
			valores[1] = retorno[1].toString();
			
			return valores;
		}
		
		return null;
	}
	
	public String[] getUnidadeSuperiorHierarquicaUnidRespContrato(Short nuUnidadeRespContrato){
		
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT caixa.co_comunicacao AS email, u.no_unidade ");
		var1.append("FROM   icosm001.iacvw004_vinculacao_unidade vinUnid ");
		var1.append("       inner join icosm001.iacvw006_caixa_postal_unidade AS caixa ");
		var1.append("               ON caixa.nu_unidade_v03 = vinUnid.nu_unde_vnclra_u24 ");
		var1.append(" INNER JOIN icosm001.iacvw003_unidade as u ");
		var1.append(" ON u.nu_unidade = vinUnid.nu_unde_vnclra_u24 ");
		var1.append("WHERE  ( vinUnid.nu_unidade = :nuUnidadeRespContrato ) ");
		var1.append("       AND nu_tp_vinculo_u22 = 1 LIMIT 1");
		
		SQLQuery query = getSession().createSQLQuery(var1.toString());
		query.setParameter("nuUnidadeRespContrato", nuUnidadeRespContrato);
		
		Object[] retorno = (Object[]) query.uniqueResult();
		if (retorno != null)
		{
			String[] valores = new String[2];
			valores[0] = retorno[0].toString();
			valores[1] = retorno[1].toString();
			
			return valores;
		}
		
		return null;
		
	}
	
	public String getNomeByEmpregado(Integer empregado){
		if (empregado != null && empregado > 0)
		{
			StringBuffer  var1 = new StringBuffer();
			var1.append("SELECT no_pessoa FROM icosm001.iacw002_empregado_caixa WHERE nu_matricula = :empregado LIMIT 1");
			
			SQLQuery query = getSession().createSQLQuery(var1.toString());
			query.setParameter("empregado", empregado);
			
			return (String) query.uniqueResult();
		}
		
		return "";
	}
	
}
