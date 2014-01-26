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
package br.gov.caixa.siiac.bo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.ILogEnvioParecerBO;
import br.gov.caixa.siiac.model.EnvioParecerVO;
import br.gov.caixa.siiac.model.EnvioParecerVOId;
import br.gov.caixa.siiac.model.LogEnvioParecerVO;
import br.gov.caixa.siiac.model.domain.LogEnvioParecer;
import br.gov.caixa.siiac.model.domain.LogEnvioParecerId;
import br.gov.caixa.siiac.persistence.dao.IArquivoImagemParecerDAO;
import br.gov.caixa.siiac.persistence.dao.ILogEnvioParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IPropriedadesDAO;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.siiac.util.LogCEFUtil;
import br.gov.caixa.siiac.util.MailSender;
import br.gov.caixa.siiac.util.PropriedadesEmail;

/**
 * @author GIS Consult
 */
@Service
@Scope("prototype")
public class LogEnvioParecerBO implements ILogEnvioParecerBO {
	
	private ILogEnvioParecerDAO logEnvioParecerDAO;
	private IArquivoImagemParecerDAO arquivoImagemParecerDAO;
	private IPropriedadesDAO propriedadesDAO;
	
	@Autowired
	public void setLogEnvioParecerDAO(ILogEnvioParecerDAO logEnvioParecerDAO) {
		this.logEnvioParecerDAO = logEnvioParecerDAO;
	}
	
	@Autowired
	public void setPropriedadesDAO(IPropriedadesDAO propriedadesDAO) {
		this.propriedadesDAO = propriedadesDAO;
	}
	
	@Autowired
	public void setArquivoImagemParecerDAO(IArquivoImagemParecerDAO arquivoImagemParecerDAO) {
		this.arquivoImagemParecerDAO = arquivoImagemParecerDAO;
	}
	
	public void gravarLog(LogEnvioParecer log) {
		logEnvioParecerDAO.saveOrUpdate(log);
	}
	
	@Transactional
	public List<LogEnvioParecerVO> getListaVO(String filtroSimples) {
		return logEnvioParecerDAO.getListaVO(filtroSimples);
	}
	
	@Transactional
	public LogEnvioParecer findById(LogEnvioParecerId id) throws DAOException {
		return logEnvioParecerDAO.findById(id);
	}
	
	@Transactional
	public byte[] getPDFParecer(Integer nuParecer, Short anoParecer, Short nuUnidade, Short nuNatural) {
		
		return arquivoImagemParecerDAO.getArquivoImagemParecerSQL(nuParecer, anoParecer, nuUnidade, nuNatural);
	}
	
	@Transactional
	public String getDominio() {
		String dominio = "";
		
		dominio = propriedadesDAO.getPropriedade("mail.dominio.destinatarios");
		
		return dominio;
	}
	
	@Transactional
	public void reenvioParecer(EnvioParecerVO log) {
		
		LogCEF.debug("reenvioParecer(" + LogCEFUtil.retornaValor(log) + ") INICIANDO");
		
		try {
			
			// Buscando as propriedades necessários para o envio de e-mails
			PropriedadesEmail propriedades = new PropriedadesEmail();
			propriedades.setUsuario(propriedadesDAO.getPropriedade("mail.usuario"));
			propriedades.setSenha(propriedadesDAO.getPropriedade("mail.usuario"));
			propriedades.setServidor(propriedadesDAO.getPropriedade("mail.server"));
			propriedades.setPorta(propriedadesDAO.getPropriedade("mail.portserver"));
			
			// Método que recebe duas listas do tipo String e as transforma em dois
			// vetores do tipo InternetAddress
			propriedades.setDestinatarios(log.getDestinatarios(), log.getCopias());
			propriedades.setRemetente(log.getDe());
			
			// Preenchendo a lista de anexos
			propriedades.setAnexos(log.getAnexos());
			
			// Assunto e corpo do e-mail
			propriedades.setAssunto(log.getAssunto());
			propriedades.setCorpo(log.getConteudo());
			
			MailSender email = new MailSender();
			email.sendEmail(propriedades);
			
			for (EnvioParecerVOId id : log.getId()) {
				LogEnvioParecer logEnvio = new LogEnvioParecer();
				logEnvio.setDtEnvioParecer(new Date());
				logEnvio.setIcSituacaoEnvioParecer(true);
				logEnvio.setDeEnvioParecer(descricacaoLog("Parecer enviado com sucesso!", log.getDestinatarios(), log.getCopias()));
				logEnvio.setId(new LogEnvioParecerId(id.getNuParecer(), id.getAaParecer(), id.getNuUnidade(), id.getNuNatural()));
				this.gravarLog(logEnvio);
			}
			
		} catch (Exception e) {
			
			for (EnvioParecerVOId id : log.getId()) {
				LogEnvioParecer logEnvio = new LogEnvioParecer();
				logEnvio.setDtEnvioParecer(new Date());
				logEnvio.setIcSituacaoEnvioParecer(false);
				logEnvio.setDeEnvioParecer(descricacaoLog(e.getMessage(), log.getDestinatarios(), log.getCopias()));
				logEnvio.setId(new LogEnvioParecerId(id.getNuParecer(), id.getAaParecer(), id.getNuUnidade(), id.getNuNatural()));
				this.gravarLog(logEnvio);
			}
		}
		
		LogCEF.debug("reenvioParecer(" + LogCEFUtil.retornaValor(log) + ") TERMINANDO");
	}
	
	private String descricacaoLog(String inicio, List<String> nomesDestinatarios, List<String> nomesCopias) {
		StringBuffer sbf = new StringBuffer();
		
		sbf.append(inicio);
		sbf.append("<br/>");
		sbf.append("Destinatários:<br/>");
		
		for (String string : nomesDestinatarios) {
			sbf.append("- " + string);
			sbf.append("<br/>");
		}
		
		sbf.append("Cópias:<br/>");
		
		for (String string : nomesCopias) {
			sbf.append("- " + string);
			sbf.append("<br/>");
		}
		
		return sbf.toString();
	}
	
	@Transactional
	public List<LogEnvioParecerVO> getListaAvancadoVO(LogEnvioParecerVO log) {
		return logEnvioParecerDAO.getListaAvancadoVO(log);
	}
}
