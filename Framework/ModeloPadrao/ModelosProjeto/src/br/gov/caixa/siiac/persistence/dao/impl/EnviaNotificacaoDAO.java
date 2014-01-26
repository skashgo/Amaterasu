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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.commons.logging.Log;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.EnviaNotificacaoVO;
import br.gov.caixa.siiac.persistence.dao.IEnviaNotificacaoDAO;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.siiac.view.mb.TemplateNotificacaoMB;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class EnviaNotificacaoDAO  extends GenericDAO<EnviaNotificacaoVO> implements IEnviaNotificacaoDAO {
	
	public EnviaNotificacaoDAO() throws SIIACException {
		super(EnviaNotificacaoVO.class);
	}

	/**
	 * <b>RN090</b> – O sistema diariamente, após a atualização da agenda de
	 * verificação da unidade, identifica os serviços de verificação cujos
	 * prazos de notificação da unidade responsável pela verificação vencem em D
	 * e gera mensagem de alerta a ser encaminhada a unidade responsável pela
	 * verificação conforme template com cópia ao usuário responsável pela
	 * unidade e seu substituto eventual.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090
	 */
	public List<EnviaNotificacaoVO> obtemVerificacoesRN090(Date data)
	{
		String sql = "select " +
				" nu_verificacao_contrato as codigoVerificacao, " +
				" nu_contrato as codigoContrato, " +
				" nu_servico_verificacao_produto as codigoServicoVerificacao, " +
				" nu_unidade_rspnl_verificacao as unidadeResponsavel, " +
				" nu_natural_undde_rspnl_vrfccao as numeroNaturalUnidadeResponsavel, " +
				" co_rspnl_verificacao as matriculaResponsavel, " +
				" nu_chklst_srvco_vrfco_produto as codigoChecklist " +
				"	from iacsm001.iactb018_verificacao_contrato " +
				"	    where dt_limite_verificacao = :dataParametro  " +
				"         and ic_suspensa != true";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("dataParametro", data);
		
		query.addEntity(EnviaNotificacaoVO.class);
		
		List<EnviaNotificacaoVO> lista = new ArrayList<EnviaNotificacaoVO>();
		lista = query.list();
		
		for (EnviaNotificacaoVO enviaNotificacaoVO : lista) {
			//Obtendo o número do template de notificação
			enviaNotificacaoVO.setNumeroTemplate(getNumeroTemplateNotificacao(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel(), TemplateNotificacaoMB.TIPO_NOTIFICACAO_ID_ALERTA_PRAZO));
			
			//Obtendo o email da unidade
			String emailUnid = getEmailUnidade(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel());
			if (Utilities.notEmpty(emailUnid))
				enviaNotificacaoVO.getEmailUnidade().add(emailUnid);
			
			//Obtendo o responsável pela unidade
			enviaNotificacaoVO.setMatriculaResponsavelUnidade(getResponsavelUnidade(enviaNotificacaoVO.getUnidadeResponsavel(), Boolean.FALSE));
			
			//Obtendo o email do responsável pela unidade
			enviaNotificacaoVO.setEmailResponsavelUnidade(getEmailEmpregado(enviaNotificacaoVO.getMatriculaResponsavelUnidade()));
			if (Utilities.notEmpty(enviaNotificacaoVO.getEmailResponsavelUnidade()))
					enviaNotificacaoVO.getEmailCopia().add(enviaNotificacaoVO.getEmailResponsavelUnidade());
			
			//Obtendo o responsável eventual pela unidade
			enviaNotificacaoVO.setMatriculaEventualResponsavelUnidade(getResponsavelUnidade(enviaNotificacaoVO.getUnidadeResponsavel(), Boolean.TRUE));
			
			//Obtendo o email do responsável eventual pela unidade
			enviaNotificacaoVO.setEmailEventualResponsavelUnidade(getEmailEmpregado(enviaNotificacaoVO.getMatriculaEventualResponsavelUnidade()));
			
			if (Utilities.notEmpty(enviaNotificacaoVO.getEmailEventualResponsavelUnidade()))
				enviaNotificacaoVO.getEmailCopia().add(enviaNotificacaoVO.getEmailEventualResponsavelUnidade());
			
		}
		
		return lista;
	}

	/**
	 * <b>RN090A</b> – O sistema agrupa os serviços de verificação cujos prazos de
	 * notificação da unidade superior hierárquica de 1º nível das unidades
	 * responsáveis pela verificação vencem em D e gera mensagem de alerta a ser
	 * encaminhada a unidade superior hierárquica conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090A
	 */
	public List<EnviaNotificacaoVO> obtemVerificacoesRN090A(Date data)
	{
		String sql = "Select " +
				" nu_verificacao_contrato as codigoVerificacao, " +
				" ver.nu_contrato as codigoContrato, " +
				" ver.nu_servico_verificacao_produto as codigoServicoVerificacao, " +
				" ver.nu_unidade_rspnl_verificacao as unidadeResponsavel, " +
				" ver.nu_natural_undde_rspnl_vrfccao as numeroNaturalUnidadeResponsavel, " +
				" ver.co_rspnl_verificacao as matriculaResponsavel, " +
				" ver.nu_chklst_srvco_vrfco_produto as codigoChecklist " +
				" 	from iacsm001.iactb018_verificacao_contrato ver " +
				"		inner join iacsm001.iactb003_servico_vrfco_produto proSer " +
				"			on proSer.nu_servico_verificacao_produto = ver.nu_servico_verificacao_produto " +
				"   where ((dt_inclusao_verificacao + pz_notificacao_nivel1) = :dataParametro) " +
				"	  and ic_suspensa != true";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("dataParametro", data);

		query.addEntity(EnviaNotificacaoVO.class);

		List<EnviaNotificacaoVO> lista = new ArrayList<EnviaNotificacaoVO>();
		lista = query.list();

		for (EnviaNotificacaoVO enviaNotificacaoVO : lista) {
			
			Short unidadeSuperior = getUnidadeSuperiorNivel1(enviaNotificacaoVO.getUnidadeResponsavel());
			Short unidadeSuperiorNatural = getUnidadeSuperiorNivel1Natural(enviaNotificacaoVO.getUnidadeResponsavel());
			
			if (Utilities.notEmpty(unidadeSuperior) && Utilities.notEmpty(unidadeSuperiorNatural))
			{
				enviaNotificacaoVO.setUnidadeResponsavel(unidadeSuperior);
				enviaNotificacaoVO.setNumeroNaturalUnidadeResponsavel(unidadeSuperiorNatural);
			}
			

			//Obtendo o email da unidade
			String emailUnid = getEmailUnidade(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel());
			if (Utilities.notEmpty(emailUnid))
				enviaNotificacaoVO.getEmailUnidade().add(emailUnid);

			//Obtendo o número do template de notificação
			enviaNotificacaoVO.setNumeroTemplate(getNumeroTemplateNotificacao(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel(), TemplateNotificacaoMB.TIPO_NOTIFICACAO_ID_ALERTA_PRAZO));
			
		}

		return lista;
	}

	/**
	 * <b>RN090B</b> – O sistema agrupa os serviços de verificação cujos prazos de
	 * notificação da unidade superior hierárquica de 2º nível das unidades
	 * responsáveis pela verificação vencem em D e gera mensagem de alerta a ser
	 * encaminhada a unidade superior hierárquica de 2º nível conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090B
	 */
	public List<EnviaNotificacaoVO> obtemVerificacoesRN090B(Date data)
	{
		String sql = "Select " +
		" nu_verificacao_contrato as codigoVerificacao, " +
		" ver.nu_contrato as codigoContrato, " +
		" ver.nu_servico_verificacao_produto as codigoServicoVerificacao, " +
		" ver.nu_unidade_rspnl_verificacao as unidadeResponsavel, " +
		" ver.nu_natural_undde_rspnl_vrfccao as numeroNaturalUnidadeResponsavel, " +
		" ver.co_rspnl_verificacao as matriculaResponsavel, " +
		" ver.nu_chklst_srvco_vrfco_produto as codigoChecklist " +
		" 	from iacsm001.iactb018_verificacao_contrato ver " +
		"		inner join iacsm001.iactb003_servico_vrfco_produto proSer " +
		"			on proSer.nu_servico_verificacao_produto = ver.nu_servico_verificacao_produto " +
		"   where ((dt_inclusao_verificacao + pz_notificacao_nivel1) = :dataParametro) " +
		"	  and ic_suspensa != true";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("dataParametro", data);
		
		query.addEntity(EnviaNotificacaoVO.class);
		
		List<EnviaNotificacaoVO> lista = new ArrayList<EnviaNotificacaoVO>();
		lista = query.list();
		
		for (EnviaNotificacaoVO enviaNotificacaoVO : lista) {
			
			Short unidadeSuperior = getUnidadeSuperiorNivel2(enviaNotificacaoVO.getUnidadeResponsavel());
			Short unidadeSuperiorNatural = getUnidadeSuperiorNivel2Natural(enviaNotificacaoVO.getUnidadeResponsavel());
			
			if (Utilities.notEmpty(unidadeSuperior) && Utilities.notEmpty(unidadeSuperiorNatural))
			{
				enviaNotificacaoVO.setUnidadeResponsavel(unidadeSuperior);
				enviaNotificacaoVO.setNumeroNaturalUnidadeResponsavel(unidadeSuperiorNatural);
			}
			
			//Obtendo o email da unidade
			String emailUnid = getEmailUnidade(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel());
			if (Utilities.notEmpty(emailUnid))
				enviaNotificacaoVO.getEmailUnidade().add(emailUnid);
			
			
			//Obtendo o número do template de notificação
			enviaNotificacaoVO.setNumeroTemplate(getNumeroTemplateNotificacao(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel(), TemplateNotificacaoMB.TIPO_NOTIFICACAO_ID_ALERTA_PRAZO));
			
		}
		
		return lista;
	}

	/**
	 * <b>RN090C</b> - O sistema agrupa os serviços de verificação que sensibilizarão o
	 * AV Gestão em D por unidade superior hierárquica das unidades responsáveis
	 * pela verificação e gera mensagem informando as unidades superiores
	 * hierárquicas de 1º e 2º níveis conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090C
	 */
	public List<EnviaNotificacaoVO> obtemVerificacoesRN090C(Date data, Short nivel)
	{
		String sql = "Select " +
		" nu_verificacao_contrato as codigoVerificacao, " +
		" ver.nu_contrato as codigoContrato, " +
		" ver.nu_servico_verificacao_produto as codigoServicoVerificacao, " +
		" ver.nu_unidade_rspnl_verificacao as unidadeResponsavel, " +
		" ver.nu_natural_undde_rspnl_vrfccao as numeroNaturalUnidadeResponsavel, " +
		" ver.co_rspnl_verificacao as matriculaResponsavel, " +
		" ver.nu_chklst_srvco_vrfco_produto as codigoChecklist " +
		" 	from iacsm001.iactb018_verificacao_contrato ver " +
		"		inner join iacsm001.iactb003_servico_vrfco_produto proSer " +
		"			on proSer.nu_servico_verificacao_produto = ver.nu_servico_verificacao_produto " +
		"   where ((dt_inclusao_verificacao + pz_avgestao) = :dataParametro) " +
		"	  and ic_suspensa != true";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("dataParametro", data);
		
		query.addEntity(EnviaNotificacaoVO.class);
		
		List<EnviaNotificacaoVO> lista = new ArrayList<EnviaNotificacaoVO>();
		lista = query.list();
		
		for (EnviaNotificacaoVO enviaNotificacaoVO : lista) {
			
			Short unidadeSuperior;
			Short unidadeSuperiorNatural;
			
			if (nivel == 1)
			{
				unidadeSuperior = getUnidadeSuperiorNivel1(enviaNotificacaoVO.getUnidadeResponsavel());
				unidadeSuperiorNatural = getUnidadeSuperiorNivel1Natural(enviaNotificacaoVO.getUnidadeResponsavel());
			}
			else
			{
				unidadeSuperior = getUnidadeSuperiorNivel2(enviaNotificacaoVO.getUnidadeResponsavel());
				unidadeSuperiorNatural = getUnidadeSuperiorNivel1Natural(enviaNotificacaoVO.getUnidadeResponsavel());
			}
			
			if (Utilities.notEmpty(unidadeSuperior) && Utilities.notEmpty(unidadeSuperiorNatural))
			{
				enviaNotificacaoVO.setUnidadeResponsavel(unidadeSuperior);
				enviaNotificacaoVO.setNumeroNaturalUnidadeResponsavel(unidadeSuperiorNatural);
			}
			
			//Obtendo o email da unidade
			String emailUnid = getEmailUnidade(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel());
			if (Utilities.notEmpty(emailUnid))
				enviaNotificacaoVO.getEmailUnidade().add(emailUnid);
			
			//Obtendo o número do template de notificação
			if (nivel == 1)
			{
				enviaNotificacaoVO.setNumeroTemplate(getNumeroTemplateNotificacao(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel(), TemplateNotificacaoMB.TIPO_NOTIFICACAO_ID_ALERTA_PRAZO));
			} else {
				enviaNotificacaoVO.setNumeroTemplate(getNumeroTemplateNotificacao(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel(), TemplateNotificacaoMB.TIPO_NOTIFICACAO_ID_ENVIO_AV_GESTAO));
			}
		}
		
		return lista;
	}

	/**
	 * <b>RN090D</b> – O sistema agrupa os serviços de verificação que sensibilizarão o
	 * AV SURET por unidade de conformidade de vinculação das unidades
	 * responsáveis pela verificação e gera mensagem a ser encaminhada a unidade
	 * de conformidade de vinculação conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090D
	 */
	public List<EnviaNotificacaoVO> obtemVerificacoesRN090D(Date data)
	{
		String sql = "Select " +
		" nu_verificacao_contrato as codigoVerificacao, " +
		" ver.nu_contrato as codigoContrato, " +
		" ver.nu_servico_verificacao_produto as codigoServicoVerificacao, " +
		" ver.nu_unidade_rspnl_verificacao as unidadeResponsavel, " +
		" ver.nu_natural_undde_rspnl_vrfccao as numeroNaturalUnidadeResponsavel, " +
		" ver.co_rspnl_verificacao as matriculaResponsavel, " +
		" ver.nu_chklst_srvco_vrfco_produto as codigoChecklist " +
		" 	from iacsm001.iactb018_verificacao_contrato ver " +
		"		inner join iacsm001.iactb003_servico_vrfco_produto proSer " +
		"			on proSer.nu_servico_verificacao_produto = ver.nu_servico_verificacao_produto " +
		"   where ((dt_inclusao_verificacao + pz_avsuret) = :dataParametro) " +
		"	  and ic_suspensa != true";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("dataParametro", data);
		
		query.addEntity(EnviaNotificacaoVO.class);
		
		List<EnviaNotificacaoVO> lista = new ArrayList<EnviaNotificacaoVO>();
		lista = query.list();
		
		for (EnviaNotificacaoVO enviaNotificacaoVO : lista) {
			
			Short unidadeSuperior = getUnidadeConformidade(enviaNotificacaoVO.getUnidadeResponsavel());
			Short unidadeSuperiorNatural = getUnidadeConformidadeNatural(enviaNotificacaoVO.getUnidadeResponsavel());
			
			if (Utilities.notEmpty(unidadeSuperior) && Utilities.notEmpty(unidadeSuperiorNatural))
			{
				enviaNotificacaoVO.setUnidadeResponsavel(unidadeSuperior);
				enviaNotificacaoVO.setNumeroNaturalUnidadeResponsavel(unidadeSuperiorNatural);
			}
			
			//Obtendo o email da unidade
			String emailUnid = getEmailUnidade(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel());
			if (Utilities.notEmpty(emailUnid))
				enviaNotificacaoVO.getEmailUnidade().add(emailUnid);
			
			//Obtendo o número do template de notificação
			enviaNotificacaoVO.setNumeroTemplate(getNumeroTemplateNotificacao(enviaNotificacaoVO.getUnidadeResponsavel(), enviaNotificacaoVO.getNumeroNaturalUnidadeResponsavel(), TemplateNotificacaoMB.TIPO_NOTIFICACAO_ID_ALERTA_PRAZO));
			
		}
		
		return lista;
	}
	
	
	private Integer getNumeroTemplateNotificacao(Short paramNumeroUnidade, Short paramNumeroNatural, String tipoNotificacao)
	{
		Integer retorno = null;
		if (Utilities.notEmpty(paramNumeroUnidade) && Utilities.notEmpty(paramNumeroNatural) && Utilities.notEmpty(tipoNotificacao))
		{
			String sql = "Select " +
				" tmp.nu_template_notificacao as codigoTemplateNotificacao " +
				" from iacsm001.iactb020_template_notificacao tmp " +
				"	inner join icosm001.iacvw005_tipo_unidade tpUni " +
				"		on tpUni.nu_tp_unidade_u21 = tmp.nu_tipo_unidade_notificada" +
				" 	inner join icosm001.iacvw003_unidade uni " +
				"		on tpUni.nu_tp_unidade_u21 = uni.nu_tp_unidade_u21 " +
				" where (uni.nu_unidade = :nuUnidade) " +
				"	and (uni.nu_natural = :nuNatural) " +
				"   and ic_tipo_notificacao = :tipoNotificacao";
		
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("nuUnidade", paramNumeroUnidade);
			query.setParameter("nuNatural", paramNumeroNatural);
			query.setParameter("tipoNotificacao", tipoNotificacao);
						
			if (query.uniqueResult() != null)
				retorno = (Integer)query.uniqueResult();
			else {
				retorno = null;
			}
		} else {
			retorno = null;
		}
		
		return retorno;
	}
	
	
	private String getEmailUnidade(Short paramNumeroUnidade, Short paramNumeroNatural)
	{
		String retorno = null;
		
		if (Utilities.notEmpty(paramNumeroUnidade) && Utilities.notEmpty(paramNumeroNatural))
		{
			String sql = "Select " +
					" co_comunicacao as emailUnidade " +
					" 	from icosm001.iacvw006_caixa_postal_unidade " +
					"		where (nu_unidade_v03 = :nuUnidade) " +
					"		  and (nu_natural_v03 = :nuNatural)";
	
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("nuUnidade", paramNumeroUnidade);
			query.setParameter("nuNatural", paramNumeroNatural);
			
		
			
			if (query.uniqueResult() != null)
				retorno = (String)query.uniqueResult();
			else {
				retorno = null;
			}		
		} else {
			retorno = null;
		}
		
		return retorno;

	}

	private Integer getResponsavelUnidade(Short paramNumeroUnidade, Boolean eventual)
	{
		Integer retorno = null;
		
		if (Utilities.notEmpty(paramNumeroUnidade))
		{
			String sql = "Select " +
					" nu_matricula_h01 as matriculaResponsavel " +
					"	from icosm001.iacvw001_rspnl_unidade " +
					"	where ic_eventual = :eventual " +
					"	  and nu_unidade = :nuUnidade ";
	
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("nuUnidade", paramNumeroUnidade);
			
			if (eventual)
				query.setParameter("eventual", 'S');
			else
				query.setParameter("eventual", 'N');
			
			if (query.uniqueResult() != null)
				retorno = (Integer)query.uniqueResult();
			else {
				retorno = null;
			}	
		} else {
			retorno = null;
		}
		
		return retorno;
	}
	
	private String getEmailEmpregado(Integer paramMatricula)
	{
		String retorno = null;
		
		if (Utilities.notEmpty(paramMatricula))
		{
			String sql = "Select " +
					" nu_matricula as emailEmpregado " +
					" from icosm001.iacw002_empregado_caixa " +
					"	where nu_matricula = :nuMatricula";
	
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("nuMatricula", paramMatricula);
						
			if (query.uniqueResult() != null)
				retorno = FormatUtil.formatMatricula((Integer) query.uniqueResult());
			else {
				retorno = null;
			}	
		} else {
			retorno = null;
		}
		
		return retorno;

	}
	
	private Short getUnidadeSuperiorNivel1(Short paramNumeroUnidade)
	{
		Short unidade = 0;
		
		if (Utilities.notEmpty(paramNumeroUnidade) && paramNumeroUnidade > 0)
		{
			String sql = "Select " +
					" vinUnid.nu_unde_vnclra_u24 as numeroUnidadeSuperior " +
					" from icosm001.iacvw004_vinculacao_unidade vinUnid " +
					"	where (nu_unidade = :nuUnidade) and nu_tp_vinculo_u22 = 1";
	
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("nuUnidade", paramNumeroUnidade);
			
			if (!query.list().isEmpty())
			{
				unidade = (Short) query.uniqueResult();
			}
		}else {
			unidade = null;
		}
		
		return unidade;
	}
	
	private Short getUnidadeSuperiorNivel1Natural(Short paramNumeroUnidade)
	{
		Short unidade = 0;
		
		if (Utilities.notEmpty(paramNumeroUnidade) && paramNumeroUnidade > 0)
		{
			String sql = "Select " +
					" (select min(CAST(nu_natural as smallint)) from icosm001.iacvw003_unidade " +
					"		where nu_unidade = vinUnid.nu_unde_vnclra_u24) as numeroNaturalUnidadeSuperior  " +
					" from icosm001.iacvw004_vinculacao_unidade vinUnid " +
					"	where (nu_unidade = :nuUnidade) and nu_tp_vinculo_u22 = 1";
	
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("nuUnidade", paramNumeroUnidade);
			
			if (!query.list().isEmpty())
			{
				unidade = (Short) query.uniqueResult();
			}
		}else {
			unidade = null;
		}
		
		return unidade;
	}
	
	private Short getUnidadeSuperiorNivel2(Short paramNumeroUnidade)
	{
		Short unidade = 0;
		
		if (Utilities.notEmpty(paramNumeroUnidade) && paramNumeroUnidade > 0)
		{
			String sql = "Select "
				+ " vinUnid.nu_unde_vnclra_u24 as numeroUnidadeSuperior "
				+ "	from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ "		where nu_tp_vinculo_u22 = 1 "
				+ "			and (nu_unidade = ( select vinUnid.nu_unde_vnclra_u24 from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ "								where (nu_unidade = :nuUnidade) and nu_tp_vinculo_u22 = 1)"
				+ " )";
	
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("nuUnidade", paramNumeroUnidade);
			
			if (!query.list().isEmpty())
			{
				unidade = (Short) query.uniqueResult();
			}
		}else {
			unidade = null;
		}
		
		return unidade;
	}
	
	private Short getUnidadeSuperiorNivel2Natural(Short paramNumeroUnidade)
	{
		Short unidade = 0;
		
		if (Utilities.notEmpty(paramNumeroUnidade) && paramNumeroUnidade > 0)
		{
			String sql = "Select "
				+ " (select min(CAST(nu_natural as smallint)) from icosm001.iacvw003_unidade where nu_unidade = vinUnid.nu_unde_vnclra_u24 ) as numeroNaturalUnidadeSuperior"
				+ "	from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ "		where nu_tp_vinculo_u22 = 1 "
				+ "			and (nu_unidade = ( select vinUnid.nu_unde_vnclra_u24 from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ "								where (nu_unidade = :nuUnidade) and nu_tp_vinculo_u22 = 1)"
				+ " )";
	
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("nuUnidade", paramNumeroUnidade);
			
			if (!query.list().isEmpty())
			{
				unidade = (Short) query.uniqueResult();
			}
		}else {
			unidade = null;
		}
		
		return unidade;
	}
	
	private Short getUnidadeConformidade(Short paramNumeroUnidade)
	{
		Short unidade = 0;
		
		if (Utilities.notEmpty(paramNumeroUnidade) && paramNumeroUnidade > 0)
		{
			String sql = "Select "
				+ " vinUnid.nu_unde_vnclra_u24 as numeroUnidadeSuperior from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ "		where (nu_tp_vinculo_u22 = 4)  and nu_processo_u27 in (8703, 8737, 8768)  and (nu_unidade = :nuUnidade)";
	
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("nuUnidade", paramNumeroUnidade);
			
			if (!query.list().isEmpty())
			{
				unidade = (Short) query.uniqueResult();
			}
		}else {
			unidade = null;
		}
		
		return unidade;
	}
	
	private Short getUnidadeConformidadeNatural(Short paramNumeroUnidade)
	{
		Short unidade = 0;
		
		if (Utilities.notEmpty(paramNumeroUnidade) && paramNumeroUnidade > 0)
		{
			String sql = "Select "
				+ " (select min(CAST(nu_natural as smallint)) from icosm001.iacvw003_unidade where nu_unidade = vinUnid.nu_unde_vnclra_u24 ) as numeroNaturalUnidadeSuperior"
				+ "	from icosm001.iacvw004_vinculacao_unidade vinUnid "
				+ "		where (nu_tp_vinculo_u22 = 4)  and nu_processo_u27 in (8703, 8737, 8768)  and (nu_unidade = :nuUnidade)";
	
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("nuUnidade", paramNumeroUnidade);
			
			if (!query.list().isEmpty())
			{
				unidade = (Short) query.uniqueResult();
			}
		}else {
			unidade = null;
		}
		
		return unidade;
	}
	
	

	
	public List<String> getEmailErro(String grupo) {
		
		List<String> list = new ArrayList<String>();
		
		if (Utilities.notEmpty(grupo))
		{
			String sql = "select " + 
				" no_valor_propriedade as caixaPostal " + 
				" from iacsm001.iactb043_propriedades " + 
				" where no_grupo = :grupo";
			
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setParameter("grupo", grupo);
			
			if (!query.list().isEmpty())
			{
				list = (List<String>) query.list();
			}
		} else {
			list = null;
		}
			
		return list;
	}
}
