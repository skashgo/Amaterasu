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
package br.gov.caixa.siiac.ws;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.gov.caixa.siiac.bo.IAtualizaPrazoVerificacaoBO;
import br.gov.caixa.siiac.bo.IEnviaNotificacaoBO;
import br.gov.caixa.siiac.bo.IGeraAgendaBO;
import br.gov.caixa.siiac.bo.IInsereLogBO;
import br.gov.caixa.siiac.bo.impl.batch.AtualizaPrazoVerificacaoBO;
import br.gov.caixa.siiac.bo.impl.batch.GeraAgendaBO;
import br.gov.caixa.siiac.model.LogVO;
import br.gov.caixa.siiac.util.AppContext;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@WebService
public class AcessoPentahoWS {
	
	private static boolean isRunning = false;
	private static Integer nuRotinaEmExecucao;
	public static final Integer ROTINA_GERA_AGENDA = 1; 
	public static final Integer ROTINA_ATUALIZA_PRAZO = 2; 
	public static final Integer ROTINA_ENVIA_NOTIFICACAO = 3; 
	
	private transient IGeraAgendaBO geraAgendaBO;
	private transient IAtualizaPrazoVerificacaoBO atualizaPrazoVerificacaoBO;
	private transient IEnviaNotificacaoBO enviaNotificacaoBO;
	private transient IInsereLogBO insereLogBO;
	
	@WebMethod
	public void executarRotinasWS() throws Exception {
		if(geraAgendaBO == null){
			geraAgendaBO = (IGeraAgendaBO) AppContext.getApplicationContext().getBean("geraAgendaBO");
		}
		if(atualizaPrazoVerificacaoBO == null) {
			atualizaPrazoVerificacaoBO = (IAtualizaPrazoVerificacaoBO) AppContext.getApplicationContext().getBean("atualizaPrazoVerificacaoBO");
		}
		if(enviaNotificacaoBO == null) {
			enviaNotificacaoBO = (IEnviaNotificacaoBO) AppContext.getApplicationContext().getBean("enviaNotificacaoBO");
		}
		if(insereLogBO == null) {
			insereLogBO = (IInsereLogBO) AppContext.getApplicationContext().getBean("insereLogBO");
		}
		
		if(isRunning){
			LogCEF.error("Não foi possível executar. A rotina já está em execução.");
			throw new Exception("Não foi possível executar. A rotina já está em execução.");
		}
		
		new Thread() {
			@Override
			public void run() {
				LogVO logVO = new LogVO();
				isRunning = true;
				try {
					nuRotinaEmExecucao = ROTINA_GERA_AGENDA;
					geraAgendaBO.geraAgendaTodosContratos(logVO);
					nuRotinaEmExecucao = ROTINA_ATUALIZA_PRAZO;
					atualizaPrazoVerificacaoBO.atualizaPrazoVerificao(null);
					
				} catch (Exception e) {
					LogCEF.error(e.getMessage());

					try {
						logVO.setTsImportacao(new Date());
						logVO.setIcStatusImportado(false);
						logVO.setCoTerminoImportacao("Falha ao Gerar Agenda.");
						insereLogBO.insereLog(logVO, "[SIIAC] ERRO - Geração Agenda");
					} catch (Exception ex) {
						LogCEF.error(ex.getMessage());
					}
				} finally {
					try {
						logVO.setTsImportacao(new Date());
						logVO.setIcStatusImportado(true);
						logVO.setCoTerminoImportacao("Gera&ccedil;&atilde;o de agenda realizada com sucesso.");
						insereLogBO.insereLog(logVO, "[SIIAC] SUCESSO - Geração Agenda");
					} catch (Exception ex) {
						LogCEF.error(ex.getMessage());
					}
				}
				
				try {
					nuRotinaEmExecucao = ROTINA_ENVIA_NOTIFICACAO;
					enviaNotificacaoBO.enviaNotificacao();
				} catch (Exception e) {
					LogCEF.error(e.getMessage());
				}
				isRunning = false;
			}
		}.start();		
	}
	
	
	@WebMethod
	public void gerarAgendaWS() {
		LogCEF.debug("Chamada ao WebService gerarAgendaWS ");
		if(geraAgendaBO == null){
			geraAgendaBO = (IGeraAgendaBO) AppContext.getApplicationContext().getBean("geraAgendaBO");
		}
		try {
			LogVO logVO = new LogVO();
			geraAgendaBO.geraAgendaTodosContratos(logVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public void atualizaPrazoVerificacaoWS() {
		LogCEF.debug("Chamada ao WebService atualizaPrazoVerificacaoWS ");
		if(atualizaPrazoVerificacaoBO == null) {
			atualizaPrazoVerificacaoBO = (IAtualizaPrazoVerificacaoBO) AppContext.getApplicationContext().getBean("atualizaPrazoVerificacaoBO");
		}
		atualizaPrazoVerificacaoBO.atualizaPrazoVerificao(null);
	}
	
	
	
	@WebMethod
	public void obtemVerificacao() {
		LogCEF.debug("Chamada ao WebService obtemVerificacao ");
		if(enviaNotificacaoBO == null) {
			enviaNotificacaoBO = (IEnviaNotificacaoBO) AppContext.getApplicationContext().getBean("enviaNotificacaoBO");
		}
		enviaNotificacaoBO.enviaNotificacao();
	}


	public static boolean isRunning() {
		return isRunning;
	}
	
	public static String getLogRotinaExecucao() {
		if(Utilities.empty(nuRotinaEmExecucao)){
			return "";
		}else if(ROTINA_GERA_AGENDA.equals(nuRotinaEmExecucao)){
			return "Rotina de geração de agenda em execução.<br/>Tempo de execução: " + GeraAgendaBO.getTempoExecucao() + "<br/>" +
			  GeraAgendaBO.getExecuted() + " de " + GeraAgendaBO.getTotal() + " - " + GeraAgendaBO.getExecutedPercentage() + " executado.";
		}else if(ROTINA_ATUALIZA_PRAZO.equals(nuRotinaEmExecucao)){
			return "Rotina de atualização de prazos em execução.<br/>Tempo de execução: " + AtualizaPrazoVerificacaoBO.getTempoExecucao() + "<br/>" +
			  AtualizaPrazoVerificacaoBO.getExecuted() + " de " + AtualizaPrazoVerificacaoBO.getTotal() + " - " + AtualizaPrazoVerificacaoBO.getExecutedPercentage() + " executado.";
		}else if(ROTINA_ENVIA_NOTIFICACAO.equals(nuRotinaEmExecucao)){
			return "Rotina de envio de notificações em execução.";
		}
		return "";
	}
	
}
