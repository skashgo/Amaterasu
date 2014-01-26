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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.LogEnvioParecerVO;
import br.gov.caixa.siiac.model.domain.LogEnvioParecer;
import br.gov.caixa.siiac.model.domain.LogEnvioParecerId;
import br.gov.caixa.siiac.persistence.dao.ILogEnvioParecerDAO;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 */
@Repository
@Scope("prototype")
public class LogEnvioParecerDAO extends GenericDAO<LogEnvioParecer> implements ILogEnvioParecerDAO {
	
	public LogEnvioParecerDAO() {
		super(LogEnvioParecer.class);
	}
	
	public List<LogEnvioParecerVO> getListaVO(String filtroSimples) {
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuffer var1 = new StringBuffer();
		var1.append("SELECT p.nu_parecer, ");
		var1.append("       p.aa_parecer, ");
		var1.append("       p.nu_unidade, ");
		var1.append("       p.nu_natural, ");
		var1.append("       p.dt_parecer, ");
		var1.append("       c.co_produto, ");
		var1.append("       p.co_responsavel_parecer, ");
		var1.append("       p.no_parecer, ");
		var1.append("       l.ic_situacao_envio_parecer ");
		var1.append("FROM   iacsm001.iactb016_parecer AS p ");
		var1.append("       INNER JOIN iacsm001.iactb004_contrato AS c ");
		var1.append("               ON p.nu_contrato = c.nu_contrato ");
		var1.append("       INNER JOIN iacsm001.iactb019_template_parecer AS t ");
		var1.append("               ON p.nu_template_parecer = t.nu_template_parecer ");
		var1.append("       INNER JOIN iacsm001.iactb069_log_envio_parecer AS l ");
		var1.append("               ON l.nu_parecer = p.nu_parecer ");
		var1.append("                  AND l.aa_parecer = p.aa_parecer ");
		var1.append("                  AND l.nu_unidade = p.nu_unidade ");
		var1.append("                  AND l.nu_natural = p.nu_natural ");
		
		if (Utilities.notEmpty(filtroSimples))
		{
			var1.append("WHERE  p.no_parecer ILIKE :noParecer ");
			parametros.put("noParecer", "%" + filtroSimples + "%");
			
			try {
				
				Integer nuParecer = Integer.parseInt(filtroSimples);
				
				var1.append("OR  p.nu_parecer = :nuParecer ");
				parametros.put("nuParecer", nuParecer);
				
			} catch (NumberFormatException n){
				
			}
		}		
		
		Query q = getSession().createSQLQuery(var1.toString()).addEntity(LogEnvioParecerVO.class);
		q.setProperties(parametros);
		
		return q.list();
	}
	
	public List<LogEnvioParecerVO> getListaAvancadoVO(LogEnvioParecerVO log) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuffer var1 = new StringBuffer();
		var1.append("SELECT logenviopa0_.nu_unidade, ");
		var1.append("       logenviopa0_.aa_parecer, ");
		var1.append("       logenviopa0_.nu_natural, ");
		var1.append("       logenviopa0_.nu_parecer, ");
		var1.append("       parecer1_.dt_parecer, ");
		var1.append("       logenviopa0_.ic_situacao_envio_parecer, ");
		var1.append("       parecer1_.no_parecer, ");
		var1.append("       parecer1_.co_responsavel_parecer, ");
		var1.append("       contrato3_.co_produto ");
		var1.append("FROM   iacsm001.iactb069_log_envio_parecer logenviopa0_, ");
		var1.append("       iacsm001.iactb016_parecer parecer1_, ");
		var1.append("       iacsm001.iactb055_vrfco_cntro_prcr verificaca2_, ");
		var1.append("       iacsm001.iactb004_contrato contrato3_, ");
		var1.append("       iacsm001.iactb021_detalhes_contrato detalhesco4_ ");
		var1.append("WHERE  parecer1_.nu_parecer = logenviopa0_.nu_parecer ");
		var1.append("       AND verificaca2_.nu_verificacao_contrato = ");
		var1.append("           parecer1_.nu_verificacao_contrato ");
		var1.append("       AND verificaca2_.nu_contrato = contrato3_.nu_contrato ");
		var1.append("       AND contrato3_.nu_contrato = detalhesco4_.nu_contrato ");

		if (Utilities.notEmpty(log.getNuUnidadeFiltro())) {
			var1.append("       AND logenviopa0_.nu_unidade = :nuUnidade ");
			parametros.put("nuUnidade", log.getNuUnidadeFiltro());
		}
		
		if (Utilities.notEmpty(log.getNuUnidadeVerificacaoFiltro())) {
			var1.append("       AND verificaca2_.nu_unidade_rspnl_verificacao = :nuUnidadeRespVerif ");
			parametros.put("nuUnidadeRespVerif", log.getNuUnidadeVerificacaoFiltro());
		}
		
		if (Utilities.notEmpty(log.getNuCanalFiltro())) {
			var1.append("       AND contrato3_.nu_canal_cmrco = :nuCanal ");
			parametros.put("nuCanal", log.getNuCanalFiltro());
		}
		
		if (Utilities.notEmpty(log.getCoProdutoFiltro())) {
			var1.append("       AND contrato3_.co_produto = :coProduto ");
			parametros.put("coProduto", log.getCoProdutoFiltro());
		}
		
		if (Utilities.notEmpty(log.getNuParecerFiltro())) {
			var1.append("       AND parecer1_.nu_parecer = :nuParecer ");
			parametros.put("nuParecer", log.getNuParecerFiltro());
		}
		
		if (Utilities.notEmpty(log.getCoContratoFiltro())) {
			var1.append("       AND contrato3_.co_contrato = :coContrato ");
			parametros.put("coContrato", log.getCoContratoFiltro());
		}
		
		if (Utilities.notEmpty(log.getNuConveniadoFiltro())) {
			var1.append("       AND detalhesco4_.nu_convenio_c23 = :nuConveniado ");
			parametros.put("nuConveniado", log.getNuConveniadoFiltro());
		}
		
		if (Utilities.notEmpty(log.getInicioFiltro()) && Utilities.notEmpty(log.getFimFiltro())) {
			var1.append("       AND verificaca2_.dt_verificacao BETWEEN :dtInicio AND :dtFim ");
			parametros.put("dtInicio", log.getInicioFiltro());
			parametros.put("dtFim", log.getFimFiltro());
		}
		
		if (Utilities.notEmpty(log.isSomenteComErroFiltro()) && log.isSomenteComErroFiltro()) {
			var1.append("       AND logenviopa0_.ic_situacao_envio_parecer = :icSitucacao ");
			parametros.put("icSitucacao", false);
		}
		
		Query query = getSession().createSQLQuery(var1.toString()).addEntity(LogEnvioParecerVO.class);
		
		query.setProperties(parametros);
		
		return (List<LogEnvioParecerVO>) query.list();
	}
}
