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
package br.gov.caixa.siiac.bo.impl.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.mail.Mail;
import br.gov.caixa.mail.MessageMail;
import br.gov.caixa.mail.SendBackgroundMail;
import br.gov.caixa.siiac.bo.IInsereLogBO;
import br.gov.caixa.siiac.model.LogVO;
import br.gov.caixa.siiac.model.domain.LogImportacao;
import br.gov.caixa.siiac.persistence.dao.ILogImportacaoDAO;
import br.gov.caixa.siiac.persistence.dao.IPropriedadesDAO;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.siiac.util.LogCEFUtil;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class InsereLogBO implements IInsereLogBO {
	private ILogImportacaoDAO logImportacaoDAO;
	private IPropriedadesDAO propriedadesDAO;
	
	@Autowired
	public void setLogImportacaoDAO(ILogImportacaoDAO logImportacaoDAO) {
		this.logImportacaoDAO = logImportacaoDAO;
	}
	
	@Autowired
	public void setPropriedadesDAO(IPropriedadesDAO propriedadesDAO) {
		this.propriedadesDAO = propriedadesDAO;
	}
	
	@Transactional
	public void insereLog(LogVO logVO, String subject) throws Exception {
		LogCEF.debug("insereLog(" + LogCEFUtil.retornaValor(logVO) + ", " + LogCEFUtil.retornaValor(subject) + ") INICIANDO");
		if (logVO != null) {
			try {
				LogImportacao logImportacao = new LogImportacao();
				logImportacao.setDtReferencia(logVO.getDtReferencia());
				logImportacao.setDtGeracao(logVO.getDtGeracao());
				logImportacao.setSgSistemaGerador(logVO.getSgSistemaGerador());
				if(logVO.getQtRegistros() != null){
					logImportacao.setQtRegistros(String.valueOf(logVO.getQtRegistros()));
				}
				if(logVO.getQtRegistrosIncluidos() != null){
					logImportacao.setQtRegistrosIncluidos(String.valueOf(logVO.getQtRegistrosIncluidos()));
				}
				if(logVO.getQtRegistrosAlterados() != null){
					logImportacao.setQtRegistrosAlterados(String.valueOf(logVO.getQtRegistrosAlterados()));
				}
				logImportacao.setTsImportacao(logVO.getTsImportacao());
				logImportacao.setNoArquivoImportado(logVO.getNoArquivoImportado());
				logImportacao.setIcStatusImportacao(logVO.getIcStatusImportado());
				logImportacao.setCoTerminoImportacao(logVO.getCoTerminoImportacao());
				logImportacaoDAO.merge(logImportacao);
				enviaEmail(logVO, subject);
				
				LogCEF.debug("insereLog(" + LogCEFUtil.retornaValor(logVO) + ", " + LogCEFUtil.retornaValor(subject) + ") TERMINANDO");
			} catch (Exception e) {
				throw new Exception("Erro ao gravar log. >>" + e.getMessage());
			}
		}
	}
	
	@Transactional
	private String criaMensagem(LogVO logVO) {
		
		LogCEF.debug("criaMensagem(" + LogCEFUtil.retornaValor(logVO) + ") INICIANDO");
		
		StringBuilder message = new StringBuilder();
		if (logVO.getIcStatusImportado()) {
			message.append("O processamento foi finalizado com <strong>SUCESSO</strong>.");
		} else {
			message.append("O processamento foi finalizado com <strong>FALHA</strong>.");
		}
		message.append("<br/>");
		message.append("<br/>");
		
		if (logVO.getNoArquivoImportado() != null) {
			message.append("Nome do arquivo: ");
			message.append(logVO.getNoArquivoImportado());
			message.append("<br/>");
		}
		
		if (logVO.getDtReferencia() != null) {
			message.append("Data do movimento: ");
			message.append(ConvertUtils.dateFormat(logVO.getDtReferencia()));
			message.append("<br/>");
		}
		
		if (logVO.getDtGeracao() != null) {
			message.append("Data da gera&ccedil;&atilde;o do arquivo: ");
			message.append(ConvertUtils.dateFormat(logVO.getDtGeracao()));
			message.append("<br/>");
		}
		
		if (logVO.getQtRegistros() != null) {
			message.append("Quantidade de registros processados: ");
			message.append(logVO.getQtRegistros());
			message.append("<br/>");
		}
		
		if (logVO.getQtRegistrosIncluidos() != null) {
			message.append("Quantidade de registros inseridos: ");
			message.append(logVO.getQtRegistrosIncluidos());
			message.append("<br/>");
		}
		
		if (logVO.getQtRegistrosAlterados() != null) {
			message.append("Quantidade de registros alterados: ");
			message.append(logVO.getQtRegistrosAlterados());
			message.append("<br/>");
		}
		
		if (logVO.getSgSistemaGerador() != null) {
			message.append("Sigla do sistema de origem: ");
			message.append(logVO.getSgSistemaGerador());
			message.append("<br/>");
		}
		
		if (logVO.getCoTerminoImportacao() != null) {
			message.append("Descri&ccedil;&atilde;o do processamento: ");
			message.append(logVO.getCoTerminoImportacao());
			message.append("<br/>");
		}
		message.append("<br/>");
		
		LogCEF.debug("criaMensagem(" + LogCEFUtil.retornaValor(logVO) + ") TERMINANDO");
		return message.toString();
	}
	
	@Transactional
	private void enviaEmail(LogVO logVO, String subject) throws Exception {
		LogCEF.debug("enviaEmail(" + LogCEFUtil.retornaValor(logVO) + ", " + LogCEFUtil.retornaValor(subject) + ") INICIANDO");
		
		String userName = propriedadesDAO.getPropriedade("mail.usuario");
		String password = propriedadesDAO.getPropriedade("mail.senha");
		String server = propriedadesDAO.getPropriedade("mail.server");
		String port = propriedadesDAO.getPropriedade("mail.portserver");
		List<String> destinatarios = propriedadesDAO.getPropriedades("importacao.aviso");
		
		MessageMail messageMail = null;
		SendBackgroundMail sendMailb = null;
		try {
			sendMailb = new SendBackgroundMail(userName, password, server, port);
			String message = criaMensagem(logVO);
			messageMail = new MessageMail(message, subject);
			for (String destinatario : destinatarios) {
				messageMail.getListMails().add(new Mail(destinatario));
			}
			sendMailb.setMessageMail(messageMail);
			sendMailb.sendAll();
			
			LogCEF.debug("enviaEmail(" + LogCEFUtil.retornaValor(logVO) + ", " + LogCEFUtil.retornaValor(subject) + ") TERMINANDO");
		} catch (Exception erro) {
			throw new Exception("Erro ao enviar email: >>" + erro.getMessage());
		}
	}
}
