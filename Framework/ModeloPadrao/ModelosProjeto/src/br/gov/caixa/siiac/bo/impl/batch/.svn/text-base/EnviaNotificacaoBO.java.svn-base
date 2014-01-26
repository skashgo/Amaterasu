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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IBuildTemplateNotificacaoBO;
import br.gov.caixa.siiac.bo.IEnviaNotificacaoBO;
import br.gov.caixa.siiac.bo.ILogEnviaNotificacaoBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.EnviaNotificacaoVO;
import br.gov.caixa.siiac.model.ModelLogNotificacao;
import br.gov.caixa.siiac.model.domain.LogEnviaNotificacao;
import br.gov.caixa.siiac.model.domain.LogEnviaNotificacaoId;
import br.gov.caixa.siiac.model.domain.TemplateNotificacao;
import br.gov.caixa.siiac.model.domain.UnidadeNotificadaVerificacao;
import br.gov.caixa.siiac.persistence.dao.IEnviaNotificacaoDAO;
import br.gov.caixa.siiac.persistence.dao.IPropriedadesDAO;
import br.gov.caixa.siiac.persistence.dao.IServicoVerificacaoProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.ITemplateNotificacaoDAO;
import br.gov.caixa.siiac.persistence.dao.IUnidadeNotificadaVerificacaoDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoDAO;
import br.gov.caixa.siiac.util.AppContext;
import br.gov.caixa.siiac.util.DefinedLanguageNotificacao;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.siiac.util.LogCEFUtil;
import br.gov.caixa.siiac.util.MailSender;
import br.gov.caixa.siiac.util.PropriedadesEmail;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */

@Service
@Scope("prototype")
public class EnviaNotificacaoBO implements IEnviaNotificacaoBO{

	private static final String ERRO_NAO_POSSUI_EMAIL = "A Unidade não possúi e-mail: ";
	private EnviaNotificacaoVO enviaNotificacao;
	private TemplateNotificacao templateNotificacao;
	private LogEnviaNotificacao logEnviaNotificacao;
	private Date dataAtual;
	private IEnviaNotificacaoDAO enviaNotificacaoDAO;
	private ITemplateNotificacaoDAO templateNotificacaoDAO;
	private IServicoVerificacaoProdutoDAO servicoVerificacaoProdutoDAO;
	private IVerificacaoContratoDAO verificacaoContratoDAO;
	private IPropriedadesDAO propriedadesDAO;
	private IBuildTemplateNotificacaoBO buildTemplateNotificacaoBO;
	private IUnidadeNotificadaVerificacaoDAO unidadeNotificadaVerificacaoDAO;
	List<EnviaNotificacaoVO> listaEnviaNotificacaoVO;
	private ILogEnviaNotificacaoBO logEnviaNotificacaoBO;
	
	MailSender mailSender = new MailSender();
	
	@Autowired
	public void setEnviaNotificacaoDAO(IEnviaNotificacaoDAO enviaNotificacaoDAO) {
		this.enviaNotificacaoDAO = enviaNotificacaoDAO;
	}

	@Autowired
	public void setPropriedadesDAO(IPropriedadesDAO propriedadesDAO) {
		this.propriedadesDAO = propriedadesDAO;
	}
	
	@Autowired
	public void setTemplateNotificacaoDAO(
			ITemplateNotificacaoDAO templateNotificacaoDAO) {
		this.templateNotificacaoDAO = templateNotificacaoDAO;
	}
	@Autowired
	public void setServicoVerificacaoProdutoDAO(
			IServicoVerificacaoProdutoDAO servicoVerificacaoProdutoDAO) {
		this.servicoVerificacaoProdutoDAO = servicoVerificacaoProdutoDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoDAO(
			IVerificacaoContratoDAO verificacaoContratoDAO) {
		this.verificacaoContratoDAO = verificacaoContratoDAO;
	}
	
	@Autowired
	public void setBuildTemplateNotificacaoBO(IBuildTemplateNotificacaoBO buildTemplateNotificacaoBO){
		this.buildTemplateNotificacaoBO = buildTemplateNotificacaoBO;
	}
	
	@Autowired
	public void setUnidadeNotificadaVerificacaoDAO(
			IUnidadeNotificadaVerificacaoDAO unidadeNotificadaVerificacaoDAO) {
		this.unidadeNotificadaVerificacaoDAO = unidadeNotificadaVerificacaoDAO;
	}

	@Autowired
	public void setLogEnviaNotificacaoBO(
			ILogEnviaNotificacaoBO logEnviaNotificacaoBO) {
		this.logEnviaNotificacaoBO = logEnviaNotificacaoBO;
	}
	
	@Transactional
	private void salvaLogErro(String e, LogEnviaNotificacao logEnviaNotificacao, Date dataAtual, String sucesso, String erro, Integer contErro, Integer contSucesso, TemplateNotificacao templateNotificacao)
	{
		LogCEF.debug("salvaLogErro(" + LogCEFUtil.retornaValor(e) + ", " +
				LogCEFUtil.retornaValor(logEnviaNotificacao) + ", " +
				LogCEFUtil.retornaValor(dataAtual) + ", " +
				LogCEFUtil.retornaValor(sucesso) + ", " +
				LogCEFUtil.retornaValor(erro) + ", " +
				LogCEFUtil.retornaValor(contErro) + ", " +
				LogCEFUtil.retornaValor(contSucesso) + ", " +
				LogCEFUtil.retornaValor(templateNotificacao) + ") INICIANDO");
		//Registra Log Erro
		LogEnviaNotificacaoId id = new LogEnviaNotificacaoId();
		id.setDataEnvioNotificacao(dataAtual);
		id.setAssuntoNotificacao(templateNotificacao.getNoAssuntoNotificacao());
		logEnviaNotificacao.setId(id);
		logEnviaNotificacao.setDeUnidadeSucesso(sucesso);
		logEnviaNotificacao.setQuantidadeNotificacaoSucesso(contSucesso);
		logEnviaNotificacao.setDeUnidadeErro(erro);
		logEnviaNotificacao.setQuantidadeNotificacaoErro(contErro);
		logEnviaNotificacaoBO.merge(logEnviaNotificacao);
		
		try {
			enviaMensagemErro(templateNotificacao.getNoAssuntoNotificacao(),
					dataAtual, MN002 + e);
			
			LogCEF.debug("salvaLogErro(" + LogCEFUtil.retornaValor(e) + ", " +
					LogCEFUtil.retornaValor(logEnviaNotificacao) + ", " +
					LogCEFUtil.retornaValor(dataAtual) + ", " +
					LogCEFUtil.retornaValor(sucesso) + ", " +
					LogCEFUtil.retornaValor(erro) + ", " +
					LogCEFUtil.retornaValor(contErro) + ", " +
					LogCEFUtil.retornaValor(contSucesso) + ", " +
					LogCEFUtil.retornaValor(templateNotificacao) + ") TERMINANDO");
		} catch (Exception error) {
			LogCEF.error(error.getMessage());
		}
	}
	
	@Transactional
	private void salvaLogSucesso(LogEnviaNotificacao logEnviaNotificacao, Date dataAtual, String sucesso, String erro, Integer contErro, Integer contSucesso, TemplateNotificacao templateNotificacao)
	{
		LogCEF.debug("salvaLogSucesso(" + LogCEFUtil.retornaValor(logEnviaNotificacao) + ", " +
				LogCEFUtil.retornaValor(dataAtual) + ", " +
				LogCEFUtil.retornaValor(sucesso) + ", " +
				LogCEFUtil.retornaValor(erro) + ", " +
				LogCEFUtil.retornaValor(contErro) + ", " +
				LogCEFUtil.retornaValor(contSucesso) + ", " +
				LogCEFUtil.retornaValor(templateNotificacao) + ") INICIANDO");
		try {
			// Registra Log Sucesso
			LogEnviaNotificacaoId id = new LogEnviaNotificacaoId();
			id.setDataEnvioNotificacao(dataAtual);
			id.setAssuntoNotificacao(templateNotificacao.getNoAssuntoNotificacao());
			logEnviaNotificacao.setId(id);
			logEnviaNotificacao.setDeUnidadeSucesso(sucesso);
			logEnviaNotificacao.setQuantidadeNotificacaoSucesso(contSucesso);
			logEnviaNotificacao.setDeUnidadeErro(erro);
			logEnviaNotificacao.setQuantidadeNotificacaoErro(contErro);
			logEnviaNotificacaoBO.merge(logEnviaNotificacao);
			
			LogCEF.debug("salvaLogSucesso(" + LogCEFUtil.retornaValor(logEnviaNotificacao) + ", " +
					LogCEFUtil.retornaValor(dataAtual) + ", " +
					LogCEFUtil.retornaValor(sucesso) + ", " +
					LogCEFUtil.retornaValor(erro) + ", " +
					LogCEFUtil.retornaValor(contErro) + ", " +
					LogCEFUtil.retornaValor(contSucesso) + ", " +
					LogCEFUtil.retornaValor(templateNotificacao) + ") TERMINANDO");
		} catch (Exception error) {
			LogCEF.error(error.getMessage());
		}
	}	
	
	private boolean existeNaList(Integer nuTemplateNotificacao, List<ModelLogNotificacao> listModelLog)
	{
		LogCEF.debug("existeNaList(" + LogCEFUtil.retornaValor(nuTemplateNotificacao) + ", " +
				LogCEFUtil.retornaValor(listModelLog) + ") INICIANDO");
		boolean existe = false;
		
		for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
			
			//Verificando se o template já existe na lista
			if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao())){
				existe = true;
			}
			
		}
		
		LogCEF.debug("existeNaList(" + LogCEFUtil.retornaValor(nuTemplateNotificacao) + ", " +
				LogCEFUtil.retornaValor(listModelLog) + ") TERMINANDO");
		return existe;
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
	@Transactional
	public void obtemVerificacoesRN090() {
		
		LogCEF.debug("obtemVerificacoesRN090() INICIANDO");
		
		List<ModelLogNotificacao> listModelLog = new ArrayList<ModelLogNotificacao>();
		ModelLogNotificacao logNotif = new ModelLogNotificacao();
				
		dataAtual = new Date();
		listaEnviaNotificacaoVO = new ArrayList<EnviaNotificacaoVO>();
		listaEnviaNotificacaoVO = enviaNotificacaoDAO.obtemVerificacoesRN090(dataAtual);
		
		for (EnviaNotificacaoVO enviaNotifVO : listaEnviaNotificacaoVO) {
			templateNotificacao = new TemplateNotificacao();
			templateNotificacao = templateNotificacaoDAO.findById(enviaNotifVO.getNumeroTemplate());

			if (!logEnviaNotificacaoBO.exist(templateNotificacao.getNoAssuntoNotificacao(), dataAtual))
			{
				logEnviaNotificacao = new LogEnviaNotificacao();
				LogEnviaNotificacaoId id = new LogEnviaNotificacaoId();
				id.setDataEnvioNotificacao(dataAtual);
				id.setAssuntoNotificacao(templateNotificacao.getNoAssuntoNotificacao());
				logEnviaNotificacao.setId(id);
				logEnviaNotificacao.setDeUnidadeSucesso("");
				logEnviaNotificacao.setQuantidadeNotificacaoSucesso(0);
				logEnviaNotificacao.setDeUnidadeErro("");
				logEnviaNotificacao.setQuantidadeNotificacaoErro(0);

				logEnviaNotificacaoBO.merge(logEnviaNotificacao);
				
			}
			
			
			//Inserir um registro na tabela iacsm001.iactb039_undde_ntfda_vrfco
			UnidadeNotificadaVerificacao unidadeNotificadaVerificacao = new UnidadeNotificadaVerificacao();
			unidadeNotificadaVerificacao.setServicoVerificacaoProduto(servicoVerificacaoProdutoDAO.findById(enviaNotifVO.getCodigoServicoVerificacao()));
			unidadeNotificadaVerificacao.setVerificacaoContrato(verificacaoContratoDAO.findById(Integer.parseInt(enviaNotifVO.getCodigoVerificacao())));
			unidadeNotificadaVerificacao.setNuUnidadeNotificadaVerificacao(enviaNotifVO.getUnidadeResponsavel());
			unidadeNotificadaVerificacao.setNuNaturalUnidadeNotificadaVerificacao(enviaNotifVO.getNumeroNaturalUnidadeResponsavel());
			unidadeNotificadaVerificacao.setIcNivelUnidadeNoTificada('1');
			unidadeNotificadaVerificacao.setDtEnvioNotificacao(dataAtual);
			unidadeNotificadaVerificacao.setTemplateNotificacao(templateNotificacao);
			unidadeNotificadaVerificacao.setNuNotificacao(UUID.randomUUID().toString());
			
			unidadeNotificadaVerificacaoDAO.merge(unidadeNotificadaVerificacao);
			
			
			//Verificando se a lista do log está vazia, caso não esteja, entra no IF
			if (!listModelLog.isEmpty())
			{
				boolean existe = existeNaList(templateNotificacao.getNuTemplateNotificacao(), listModelLog);
								
				//Se não existe, adicionar
				if (!existe)
				{
					logNotif = new ModelLogNotificacao();
					logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
					logNotif.setContErro(0);
					logNotif.setContSucesso(0);
					logNotif.setErro("");
					logNotif.setSucesso("");
					listModelLog.add(logNotif);
				}
				
			} else {
				logNotif = new ModelLogNotificacao();
				logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
				logNotif.setContErro(0);
				logNotif.setContSucesso(0);
				logNotif.setErro("");
				logNotif.setSucesso("");
				listModelLog.add(logNotif);
			}
			
			
			if (Utilities.notEmpty(enviaNotifVO.getEmailUnidade()) && enviaNotifVO.getEmailUnidade().size() > 0)
			{
				//Enviando o e-mail
				try {
					this.enviaEmail(enviaNotifVO, templateNotificacao.getNoAssuntoNotificacao(), unidadeNotificadaVerificacao);
					
					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContSucesso(modelLogNotificacao.getContSucesso() + 1);
							modelLogNotificacao.setSucesso(modelLogNotificacao.getSucesso() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Sucesso
							salvaLogSucesso(logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);
						}
					}
					
					
				} catch (Exception e) {
					
					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
							modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Erro
							salvaLogErro(e.getMessage(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
						}
					}
					
					
					e.printStackTrace();
				}
			} else {
				
				//Unidade Não possui e-mail.				
				for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
					if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
					{
						modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
						modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
						
						//Registra Log Erro
						salvaLogErro(ERRO_NAO_POSSUI_EMAIL + enviaNotifVO.getUnidadeResponsavel(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
					}
				}
			}
		}
		LogCEF.debug("obtemVerificacoesRN090() TERMINANDO");
	}

	/**
	 * <b>RN090A</b> – O sistema agrupa os serviços de verificação cujos prazos
	 * de notificação da unidade superior hierárquica de 1º nível das unidades
	 * responsáveis pela verificação vencem em D e gera mensagem de alerta a ser
	 * encaminhada a unidade superior hierárquica conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090A
	 */
	@Transactional
	public void obtemVerificacoesRN090A() {
		LogCEF.debug("obtemVerificacoesRN090A() INICIANDO");
		
		List<ModelLogNotificacao> listModelLog = new ArrayList<ModelLogNotificacao>();
		ModelLogNotificacao logNotif = new ModelLogNotificacao();
		
		dataAtual = new Date();
		listaEnviaNotificacaoVO = new ArrayList<EnviaNotificacaoVO>();
		listaEnviaNotificacaoVO = enviaNotificacaoDAO.obtemVerificacoesRN090A(dataAtual);
		

		for (EnviaNotificacaoVO enviaNotifVO : listaEnviaNotificacaoVO) {
			templateNotificacao = new TemplateNotificacao();
			templateNotificacao = templateNotificacaoDAO.findById(enviaNotifVO.getNumeroTemplate());

			if (!logEnviaNotificacaoBO.exist(templateNotificacao.getNoAssuntoNotificacao(), dataAtual))
			{
				logEnviaNotificacao = new LogEnviaNotificacao();
				LogEnviaNotificacaoId id = new LogEnviaNotificacaoId();
				id.setDataEnvioNotificacao(dataAtual);
				id.setAssuntoNotificacao(templateNotificacao.getNoAssuntoNotificacao());
				logEnviaNotificacao.setId(id);
				logEnviaNotificacao.setDeUnidadeSucesso("");
				logEnviaNotificacao.setQuantidadeNotificacaoSucesso(0);
				logEnviaNotificacao.setDeUnidadeErro("");
				logEnviaNotificacao.setQuantidadeNotificacaoErro(0);

				logEnviaNotificacaoBO.merge(logEnviaNotificacao);
				
			}
			

			//Inserir um registro na tabela iacsm001.iactb039_undde_ntfda_vrfco
			UnidadeNotificadaVerificacao unidadeNotificadaVerificacao = new UnidadeNotificadaVerificacao();
			unidadeNotificadaVerificacao.setServicoVerificacaoProduto(servicoVerificacaoProdutoDAO.findById(enviaNotifVO.getCodigoServicoVerificacao()));
			unidadeNotificadaVerificacao.setVerificacaoContrato(verificacaoContratoDAO.findById(Integer.parseInt(enviaNotifVO.getCodigoVerificacao())));
			unidadeNotificadaVerificacao.setNuUnidadeNotificadaVerificacao(enviaNotifVO.getUnidadeResponsavel());
			unidadeNotificadaVerificacao.setNuNaturalUnidadeNotificadaVerificacao(enviaNotifVO.getNumeroNaturalUnidadeResponsavel());
			unidadeNotificadaVerificacao.setIcNivelUnidadeNoTificada('1');
			unidadeNotificadaVerificacao.setDtEnvioNotificacao(dataAtual);
			unidadeNotificadaVerificacao.setTemplateNotificacao(templateNotificacao);
			unidadeNotificadaVerificacao.setNuNotificacao(UUID.randomUUID().toString());
			
			unidadeNotificadaVerificacaoDAO.merge(unidadeNotificadaVerificacao);
			
			//Verificando se a lista do log está vazia, caso não esteja, entra no IF
			if (!listModelLog.isEmpty())
			{
				boolean existe = existeNaList(templateNotificacao.getNuTemplateNotificacao(), listModelLog);
								
				//Se não existe, adicionar
				if (!existe)
				{
					logNotif = new ModelLogNotificacao();
					logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
					logNotif.setContErro(0);
					logNotif.setContSucesso(0);
					logNotif.setErro("");
					logNotif.setSucesso("");
					listModelLog.add(logNotif);
				}
				
			} else {
				logNotif = new ModelLogNotificacao();
				logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
				logNotif.setContErro(0);
				logNotif.setContSucesso(0);
				logNotif.setErro("");
				logNotif.setSucesso("");
				listModelLog.add(logNotif);
			}
			
			if (Utilities.notEmpty(enviaNotifVO.getEmailUnidade()) && enviaNotifVO.getEmailUnidade().size() > 0)
			{
				//Enviando o e-mail
				try {
					this.enviaEmail(enviaNotifVO, templateNotificacao.getNoAssuntoNotificacao(), unidadeNotificadaVerificacao);
					
					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContSucesso(modelLogNotificacao.getContSucesso() + 1);
							modelLogNotificacao.setSucesso(modelLogNotificacao.getSucesso() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Sucesso
							salvaLogSucesso(logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);
						}
					}
				} catch (Exception e) {

					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
							modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Erro
							salvaLogErro(e.getMessage(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
						}
					}
					
					e.printStackTrace();
				}
			} else {
				//Unidade Não possui e-mail.				
				for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
					if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
					{
						modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
						modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
						
						//Registra Log Erro
						salvaLogErro(ERRO_NAO_POSSUI_EMAIL + enviaNotifVO.getUnidadeResponsavel(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
					}
				}
			}
		}
		
		LogCEF.debug("obtemVerificacoesRN090A() TERMINANDO");
	}

	/**
	 * <b>RN090B</b> – O sistema agrupa os serviços de verificação cujos prazos
	 * de notificação da unidade superior hierárquica de 2º nível das unidades
	 * responsáveis pela verificação vencem em D e gera mensagem de alerta a ser
	 * encaminhada a unidade superior hierárquica de 2º nível conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090B
	 */
	@Transactional
	public void obtemVerificacoesRN090B() {
		
		LogCEF.debug("obtemVerificacoesRN090B() INICIANDO");
		
		List<ModelLogNotificacao> listModelLog = new ArrayList<ModelLogNotificacao>();
		ModelLogNotificacao logNotif = new ModelLogNotificacao();
		
		dataAtual = new Date();
		listaEnviaNotificacaoVO = new ArrayList<EnviaNotificacaoVO>();
		listaEnviaNotificacaoVO = enviaNotificacaoDAO.obtemVerificacoesRN090B(dataAtual);
		

		for (EnviaNotificacaoVO enviaNotifVO : listaEnviaNotificacaoVO) {
			templateNotificacao = new TemplateNotificacao();
			templateNotificacao = templateNotificacaoDAO.findById(enviaNotifVO.getNumeroTemplate());

			if (!logEnviaNotificacaoBO.exist(templateNotificacao.getNoAssuntoNotificacao(), dataAtual))
			{
				logEnviaNotificacao = new LogEnviaNotificacao();
				LogEnviaNotificacaoId id = new LogEnviaNotificacaoId();
				id.setDataEnvioNotificacao(dataAtual);
				id.setAssuntoNotificacao(templateNotificacao.getNoAssuntoNotificacao());
				logEnviaNotificacao.setId(id);
				logEnviaNotificacao.setDeUnidadeSucesso("");
				logEnviaNotificacao.setQuantidadeNotificacaoSucesso(0);
				logEnviaNotificacao.setDeUnidadeErro("");
				logEnviaNotificacao.setQuantidadeNotificacaoErro(0);

				logEnviaNotificacaoBO.merge(logEnviaNotificacao);
				
			}
			

			//Inserir um registro na tabela iacsm001.iactb039_undde_ntfda_vrfco
			UnidadeNotificadaVerificacao unidadeNotificadaVerificacao = new UnidadeNotificadaVerificacao();
			unidadeNotificadaVerificacao.setServicoVerificacaoProduto(servicoVerificacaoProdutoDAO.findById(enviaNotifVO.getCodigoServicoVerificacao()));
			unidadeNotificadaVerificacao.setVerificacaoContrato(verificacaoContratoDAO.findById(Integer.parseInt(enviaNotifVO.getCodigoVerificacao())));
			unidadeNotificadaVerificacao.setNuUnidadeNotificadaVerificacao(enviaNotifVO.getUnidadeResponsavel());
			unidadeNotificadaVerificacao.setNuNaturalUnidadeNotificadaVerificacao(enviaNotifVO.getNumeroNaturalUnidadeResponsavel());
			unidadeNotificadaVerificacao.setIcNivelUnidadeNoTificada('1');
			unidadeNotificadaVerificacao.setDtEnvioNotificacao(dataAtual);
			unidadeNotificadaVerificacao.setTemplateNotificacao(templateNotificacao);
			unidadeNotificadaVerificacao.setNuNotificacao(UUID.randomUUID().toString());
			
			unidadeNotificadaVerificacaoDAO.merge(unidadeNotificadaVerificacao);

			//Verificando se a lista do log está vazia, caso não esteja, entra no IF
			if (!listModelLog.isEmpty())
			{
				boolean existe = existeNaList(templateNotificacao.getNuTemplateNotificacao(), listModelLog);
								
				//Se não existe, adicionar
				if (!existe)
				{
					logNotif = new ModelLogNotificacao();
					logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
					logNotif.setContErro(0);
					logNotif.setContSucesso(0);
					logNotif.setErro("");
					logNotif.setSucesso("");
					listModelLog.add(logNotif);
				}
				
			} else {
				logNotif = new ModelLogNotificacao();
				logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
				logNotif.setContErro(0);
				logNotif.setContSucesso(0);
				logNotif.setErro("");
				logNotif.setSucesso("");
				listModelLog.add(logNotif);
			}
			
			if (Utilities.notEmpty(enviaNotifVO.getEmailUnidade()) && enviaNotifVO.getEmailUnidade().size() > 0)
			{
				//Enviando o e-mail
				try {
					this.enviaEmail(enviaNotifVO, templateNotificacao.getNoAssuntoNotificacao(), unidadeNotificadaVerificacao);
					
					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContSucesso(modelLogNotificacao.getContSucesso() + 1);
							modelLogNotificacao.setSucesso(modelLogNotificacao.getSucesso() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Sucesso
							salvaLogSucesso(logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);
						}
					}
				} catch (Exception e) {

					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
							modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Erro
							salvaLogErro(e.getMessage(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
						}
					}
					
					e.printStackTrace();
				}
			} else {
				//Unidade Não possui e-mail.				
				for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
					if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
					{
						modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
						modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
						
						//Registra Log Erro
						salvaLogErro(ERRO_NAO_POSSUI_EMAIL + enviaNotifVO.getUnidadeResponsavel(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
					}
				}
			}
		}
		
		LogCEF.debug("obtemVerificacoesRN090B() TERMINANDO");
	}

	/**
	 * <b>RN090C</b> - O sistema agrupa os serviços de verificação que
	 * sensibilizarão o AV Gestão em D por unidade superior hierárquica das
	 * unidades responsáveis pela verificação e gera mensagem informando as
	 * unidades superiores hierárquicas de 1º e 2º níveis conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090C
	 */
	@Transactional
	public void obtemVerificacoesRN090CNivel1() {
		
		LogCEF.debug("obtemVerificacoesRN090CNivel1() INICIANDO");
		
		dataAtual = new Date();
		listaEnviaNotificacaoVO = new ArrayList<EnviaNotificacaoVO>();
		listaEnviaNotificacaoVO = enviaNotificacaoDAO.obtemVerificacoesRN090C(dataAtual, (short) 1);
		
		List<ModelLogNotificacao> listModelLog = new ArrayList<ModelLogNotificacao>();
		ModelLogNotificacao logNotif = new ModelLogNotificacao();

		for (EnviaNotificacaoVO enviaNotifVO : listaEnviaNotificacaoVO) {
			templateNotificacao = new TemplateNotificacao();
			templateNotificacao = templateNotificacaoDAO.findById(enviaNotifVO.getNumeroTemplate());

			if (!logEnviaNotificacaoBO.exist(templateNotificacao.getNoAssuntoNotificacao(), dataAtual))
			{
				logEnviaNotificacao = new LogEnviaNotificacao();
				LogEnviaNotificacaoId id = new LogEnviaNotificacaoId();
				id.setDataEnvioNotificacao(dataAtual);
				id.setAssuntoNotificacao(templateNotificacao.getNoAssuntoNotificacao());
				logEnviaNotificacao.setId(id);
				logEnviaNotificacao.setDeUnidadeSucesso("");
				logEnviaNotificacao.setQuantidadeNotificacaoSucesso(0);
				logEnviaNotificacao.setDeUnidadeErro("");
				logEnviaNotificacao.setQuantidadeNotificacaoErro(0);

				logEnviaNotificacaoBO.merge(logEnviaNotificacao);
				
			}
			

			//Inserir um registro na tabela iacsm001.iactb039_undde_ntfda_vrfco
			UnidadeNotificadaVerificacao unidadeNotificadaVerificacao = new UnidadeNotificadaVerificacao();
			unidadeNotificadaVerificacao.setServicoVerificacaoProduto(servicoVerificacaoProdutoDAO.findById(enviaNotifVO.getCodigoServicoVerificacao()));
			unidadeNotificadaVerificacao.setVerificacaoContrato(verificacaoContratoDAO.findById(Integer.parseInt(enviaNotifVO.getCodigoVerificacao())));
			unidadeNotificadaVerificacao.setNuUnidadeNotificadaVerificacao(enviaNotifVO.getUnidadeResponsavel());
			unidadeNotificadaVerificacao.setNuNaturalUnidadeNotificadaVerificacao(enviaNotifVO.getNumeroNaturalUnidadeResponsavel());
			unidadeNotificadaVerificacao.setIcNivelUnidadeNoTificada('1');
			unidadeNotificadaVerificacao.setDtEnvioNotificacao(dataAtual);
			unidadeNotificadaVerificacao.setTemplateNotificacao(templateNotificacao);
			unidadeNotificadaVerificacao.setNuNotificacao(UUID.randomUUID().toString());
			
			unidadeNotificadaVerificacaoDAO.merge(unidadeNotificadaVerificacao);
			

			//Verificando se a lista do log está vazia, caso não esteja, entra no IF
			if (!listModelLog.isEmpty())
			{
				boolean existe = existeNaList(templateNotificacao.getNuTemplateNotificacao(), listModelLog);
								
				//Se não existe, adicionar
				if (!existe)
				{
					logNotif = new ModelLogNotificacao();
					logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
					logNotif.setContErro(0);
					logNotif.setContSucesso(0);
					logNotif.setErro("");
					logNotif.setSucesso("");
					listModelLog.add(logNotif);
				}
				
			} else {
				logNotif = new ModelLogNotificacao();
				logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
				logNotif.setContErro(0);
				logNotif.setContSucesso(0);
				logNotif.setErro("");
				logNotif.setSucesso("");
				listModelLog.add(logNotif);
			}
			
			if (Utilities.notEmpty(enviaNotifVO.getEmailUnidade()) && enviaNotifVO.getEmailUnidade().size() > 0)
			{
				//Enviando o e-mail
				try {
					this.enviaEmail(enviaNotifVO, templateNotificacao.getNoAssuntoNotificacao(), unidadeNotificadaVerificacao);
					
					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContSucesso(modelLogNotificacao.getContSucesso() + 1);
							modelLogNotificacao.setSucesso(modelLogNotificacao.getSucesso() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Sucesso
							salvaLogSucesso(logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);
						}
					}
				} catch (Exception e) {

					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
							modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Erro
							salvaLogErro(e.getMessage(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
						}
					}
					
					e.printStackTrace();
				}
			} else {
				//Unidade Não possui e-mail.				
				for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
					if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
					{
						modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
						modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
						
						//Registra Log Erro
						salvaLogErro(ERRO_NAO_POSSUI_EMAIL + enviaNotifVO.getUnidadeResponsavel(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
					}
				}
			}
		}
		
		LogCEF.debug("obtemVerificacoesRN090CNivel1() TERMINANDO");
	}
	
	/**
	 * <b>RN090C</b> - O sistema agrupa os serviços de verificação que
	 * sensibilizarão o AV Gestão em D por unidade superior hierárquica das
	 * unidades responsáveis pela verificação e gera mensagem informando as
	 * unidades superiores hierárquicas de 1º e 2º níveis conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090C
	 */
	@Transactional
	public void obtemVerificacoesRN090CNivel2() {
		
		LogCEF.debug("obtemVerificacoesRN090CNivel2() INICIANDO");
		
		dataAtual = new Date();
		listaEnviaNotificacaoVO = new ArrayList<EnviaNotificacaoVO>();
		listaEnviaNotificacaoVO = enviaNotificacaoDAO.obtemVerificacoesRN090C(dataAtual, (short) 2);
		
		List<ModelLogNotificacao> listModelLog = new ArrayList<ModelLogNotificacao>();
		ModelLogNotificacao logNotif = new ModelLogNotificacao();

		for (EnviaNotificacaoVO enviaNotifVO : listaEnviaNotificacaoVO) {
			templateNotificacao = new TemplateNotificacao();
			templateNotificacao = templateNotificacaoDAO.findById(enviaNotifVO.getNumeroTemplate());

			if (!logEnviaNotificacaoBO.exist(templateNotificacao.getNoAssuntoNotificacao(), dataAtual))
			{
				logEnviaNotificacao = new LogEnviaNotificacao();
				LogEnviaNotificacaoId id = new LogEnviaNotificacaoId();
				id.setDataEnvioNotificacao(dataAtual);
				id.setAssuntoNotificacao(templateNotificacao.getNoAssuntoNotificacao());
				logEnviaNotificacao.setId(id);
				logEnviaNotificacao.setDeUnidadeSucesso("");
				logEnviaNotificacao.setQuantidadeNotificacaoSucesso(0);
				logEnviaNotificacao.setDeUnidadeErro("");
				logEnviaNotificacao.setQuantidadeNotificacaoErro(0);

				logEnviaNotificacaoBO.merge(logEnviaNotificacao);
				
			}
			

			//Inserir um registro na tabela iacsm001.iactb039_undde_ntfda_vrfco
			UnidadeNotificadaVerificacao unidadeNotificadaVerificacao = new UnidadeNotificadaVerificacao();
			unidadeNotificadaVerificacao.setServicoVerificacaoProduto(servicoVerificacaoProdutoDAO.findById(enviaNotifVO.getCodigoServicoVerificacao()));
			unidadeNotificadaVerificacao.setVerificacaoContrato(verificacaoContratoDAO.findById(Integer.parseInt(enviaNotifVO.getCodigoVerificacao())));
			unidadeNotificadaVerificacao.setNuUnidadeNotificadaVerificacao(enviaNotifVO.getUnidadeResponsavel());
			unidadeNotificadaVerificacao.setNuNaturalUnidadeNotificadaVerificacao(enviaNotifVO.getNumeroNaturalUnidadeResponsavel());
			unidadeNotificadaVerificacao.setIcNivelUnidadeNoTificada('1');
			unidadeNotificadaVerificacao.setDtEnvioNotificacao(dataAtual);
			unidadeNotificadaVerificacao.setTemplateNotificacao(templateNotificacao);
			unidadeNotificadaVerificacao.setNuNotificacao(UUID.randomUUID().toString());
			
			unidadeNotificadaVerificacaoDAO.merge(unidadeNotificadaVerificacao);
			

			//Verificando se a lista do log está vazia, caso não esteja, entra no IF
			if (!listModelLog.isEmpty())
			{
				boolean existe = existeNaList(templateNotificacao.getNuTemplateNotificacao(), listModelLog);
								
				//Se não existe, adicionar
				if (!existe)
				{
					logNotif = new ModelLogNotificacao();
					logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
					logNotif.setContErro(0);
					logNotif.setContSucesso(0);
					logNotif.setErro("");
					logNotif.setSucesso("");
					listModelLog.add(logNotif);
				}
				
			} else {
				logNotif = new ModelLogNotificacao();
				logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
				logNotif.setContErro(0);
				logNotif.setContSucesso(0);
				logNotif.setErro("");
				logNotif.setSucesso("");
				listModelLog.add(logNotif);
			}
			
			if (Utilities.notEmpty(enviaNotifVO.getEmailUnidade()) && enviaNotifVO.getEmailUnidade().size() > 0)
			{
				//Enviando o e-mail
				try {
					this.enviaEmail(enviaNotifVO, templateNotificacao.getNoAssuntoNotificacao(), unidadeNotificadaVerificacao);
					
					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContSucesso(modelLogNotificacao.getContSucesso() + 1);
							modelLogNotificacao.setSucesso(modelLogNotificacao.getSucesso() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Sucesso
							salvaLogSucesso(logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);
						}
					}
				} catch (Exception e) {

					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
							modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Erro
							salvaLogErro(e.getMessage(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
						}
					}
					
					e.printStackTrace();
				}
			} else {
				//Unidade Não possui e-mail.				
				for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
					if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
					{
						modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
						modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
						
						//Registra Log Erro
						salvaLogErro(ERRO_NAO_POSSUI_EMAIL + enviaNotifVO.getUnidadeResponsavel(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
					}
				}
			}
			
		}
		
		LogCEF.debug("obtemVerificacoesRN090CNivel2() TERMINANDO");
	}

	/**
	 * <b>RN090D</b> – O sistema agrupa os serviços de verificação que
	 * sensibilizarão o AV SURET por unidade de conformidade de vinculação das
	 * unidades responsáveis pela verificação e gera mensagem a ser encaminhada
	 * a unidade de conformidade de vinculação conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090D
	 */
	@Transactional
	public void obtemVerificacoesRN090D() {
		
		LogCEF.debug("obtemVerificacoesRN090D() INICIANDO");
		
		dataAtual = new Date();
		listaEnviaNotificacaoVO = new ArrayList<EnviaNotificacaoVO>();
		listaEnviaNotificacaoVO = enviaNotificacaoDAO.obtemVerificacoesRN090D(dataAtual);
		
		List<ModelLogNotificacao> listModelLog = new ArrayList<ModelLogNotificacao>();
		ModelLogNotificacao logNotif = new ModelLogNotificacao();

		for (EnviaNotificacaoVO enviaNotifVO : listaEnviaNotificacaoVO) {
			templateNotificacao = new TemplateNotificacao();
			templateNotificacao = templateNotificacaoDAO.findById(enviaNotifVO.getNumeroTemplate());

			if (!logEnviaNotificacaoBO.exist(templateNotificacao.getNoAssuntoNotificacao(), dataAtual))
			{
				logEnviaNotificacao = new LogEnviaNotificacao();
				LogEnviaNotificacaoId id = new LogEnviaNotificacaoId();
				id.setDataEnvioNotificacao(dataAtual);
				id.setAssuntoNotificacao(templateNotificacao.getNoAssuntoNotificacao());
				logEnviaNotificacao.setId(id);
				logEnviaNotificacao.setDeUnidadeSucesso("");
				logEnviaNotificacao.setQuantidadeNotificacaoSucesso(0);
				logEnviaNotificacao.setDeUnidadeErro("");
				logEnviaNotificacao.setQuantidadeNotificacaoErro(0);

				logEnviaNotificacaoBO.merge(logEnviaNotificacao);
				
			}
			

			//Inserir um registro na tabela iacsm001.iactb039_undde_ntfda_vrfco
			UnidadeNotificadaVerificacao unidadeNotificadaVerificacao = new UnidadeNotificadaVerificacao();
			unidadeNotificadaVerificacao.setServicoVerificacaoProduto(servicoVerificacaoProdutoDAO.findById(enviaNotifVO.getCodigoServicoVerificacao()));
			unidadeNotificadaVerificacao.setVerificacaoContrato(verificacaoContratoDAO.findById(Integer.parseInt(enviaNotifVO.getCodigoVerificacao())));
			unidadeNotificadaVerificacao.setNuUnidadeNotificadaVerificacao(enviaNotifVO.getUnidadeResponsavel());
			unidadeNotificadaVerificacao.setNuNaturalUnidadeNotificadaVerificacao(enviaNotifVO.getNumeroNaturalUnidadeResponsavel());
			unidadeNotificadaVerificacao.setIcNivelUnidadeNoTificada('1');
			unidadeNotificadaVerificacao.setDtEnvioNotificacao(dataAtual);
			unidadeNotificadaVerificacao.setTemplateNotificacao(templateNotificacao);
			unidadeNotificadaVerificacao.setNuNotificacao(UUID.randomUUID().toString());
			
			unidadeNotificadaVerificacaoDAO.merge(unidadeNotificadaVerificacao);
			
			

			//Verificando se a lista do log está vazia, caso não esteja, entra no IF
			if (!listModelLog.isEmpty())
			{
				boolean existe = existeNaList(templateNotificacao.getNuTemplateNotificacao(), listModelLog);
								
				//Se não existe, adicionar
				if (!existe)
				{
					logNotif = new ModelLogNotificacao();
					logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
					logNotif.setContErro(0);
					logNotif.setContSucesso(0);
					logNotif.setErro("");
					logNotif.setSucesso("");
					listModelLog.add(logNotif);
				}
				
			} else {
				logNotif = new ModelLogNotificacao();
				logNotif.setNuTemplateNotificacao(templateNotificacao.getNuTemplateNotificacao());
				logNotif.setContErro(0);
				logNotif.setContSucesso(0);
				logNotif.setErro("");
				logNotif.setSucesso("");
				listModelLog.add(logNotif);
			}
			
			if (Utilities.notEmpty(enviaNotifVO.getEmailUnidade()) && enviaNotifVO.getEmailUnidade().size() > 0)
			{
				//Enviando o e-mail
				try {
					this.enviaEmail(enviaNotifVO, templateNotificacao.getNoAssuntoNotificacao(), unidadeNotificadaVerificacao);
					
					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContSucesso(modelLogNotificacao.getContSucesso() + 1);
							modelLogNotificacao.setSucesso(modelLogNotificacao.getSucesso() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Sucesso
							salvaLogSucesso(logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);
						}
					}
				} catch (Exception e) {

					for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
						if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
						{
							modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
							modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
							
							//Registra Log Erro
							salvaLogErro(e.getMessage(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
						}
					}
					
					e.printStackTrace();
				}
			} else {
				//Unidade Não possui e-mail.				
				for (ModelLogNotificacao modelLogNotificacao : listModelLog) {
					if (modelLogNotificacao.getNuTemplateNotificacao().equals(templateNotificacao.getNuTemplateNotificacao()))
					{
						modelLogNotificacao.setContErro(modelLogNotificacao.getContErro() + 1);
						modelLogNotificacao.setErro(modelLogNotificacao.getErro() + enviaNotifVO.getUnidadeResponsavel() + " - " + enviaNotifVO.getNumeroNaturalUnidadeResponsavel() + ";");
						
						//Registra Log Erro
						salvaLogErro(ERRO_NAO_POSSUI_EMAIL + enviaNotifVO.getUnidadeResponsavel(), logEnviaNotificacao, dataAtual, modelLogNotificacao.getSucesso(), modelLogNotificacao.getErro(), modelLogNotificacao.getContErro(), modelLogNotificacao.getContSucesso(), templateNotificacao);							
					}
				}
			}
		}
		
		LogCEF.debug("obtemVerificacoesRN090D() TERMINANDO");
	}
			
	//###############################################################################
	//Getters e Setters
	public EnviaNotificacaoVO getEnviaNotificacao() {
		return enviaNotificacao;
	}
	public void setEnviaNotificacao(EnviaNotificacaoVO enviaNotificacao) {
		this.enviaNotificacao = enviaNotificacao;
	}
	public Date getDataAtual() {
		return dataAtual;
	}
	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

	public TemplateNotificacao getTemplateNotificacao() {
		return templateNotificacao;
	}

	public void setTemplateNotificacao(TemplateNotificacao templateNotificacao) {
		this.templateNotificacao = templateNotificacao;
	}

	public LogEnviaNotificacao getLogEnviaNotificacao() {
		return logEnviaNotificacao;
	}

	public void setLogEnviaNotificacao(LogEnviaNotificacao logEnviaNotificacao) {
		this.logEnviaNotificacao = logEnviaNotificacao;
	}

	public List<EnviaNotificacaoVO> getListaEnviaNotificacaoVO() {
		return listaEnviaNotificacaoVO;
	}

	public void setListaEnviaNotificacaoVO(
			List<EnviaNotificacaoVO> listaEnviaNotificacaoVO) {
		this.listaEnviaNotificacaoVO = listaEnviaNotificacaoVO;
	}
	
	private static final String DB_PROPERTY_ERRO_NOTIFICACAO_GROUP = "notificacao.erro.envio";
	private static final String MN002 = "Não foi possível realizar a operação. ";
	
	@Transactional
	private void enviaEmail(EnviaNotificacaoVO enviaNotificacaoVO, String subject, UnidadeNotificadaVerificacao unidadeNotificadaVerificacao) throws Exception {
		
		LogCEF.debug("enviaEmail(" + LogCEFUtil.retornaValor(enviaNotificacaoVO) + ", " +
				LogCEFUtil.retornaValor(subject) + ", " +
				LogCEFUtil.retornaValor(unidadeNotificadaVerificacao) + " ) INICIANDO");
		
		String dominio = propriedadesDAO.getPropriedade("mail.dominio.destinatarios");
		
		PropriedadesEmail email = new PropriedadesEmail();
		email.setUsuario(propriedadesDAO.getPropriedade("mail.usuario"));
		email.setSenha(propriedadesDAO.getPropriedade("mail.usuario"));
		email.setServidor(propriedadesDAO.getPropriedade("mail.server"));
		email.setPorta(propriedadesDAO.getPropriedade("mail.portserver"));
		
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add(enviaNotificacaoVO.getEmailUnidade().get(0) + dominio);
		
		List<String> copia = new ArrayList<String>();

		mailSender = new MailSender();
		
		try {
			String message = criaMensagem(enviaNotificacaoVO, unidadeNotificadaVerificacao);

			if (!enviaNotificacaoVO.getEmailCopia().isEmpty())
			{
				for (String cc : enviaNotificacaoVO.getEmailCopia()) {
					copia.add(cc + dominio);
				}
				
			}
			
			email.setDestinatarios(destinatarios, copia);
			email.setAssunto(subject);
			email.setCorpo(message);
			
			mailSender.sendEmail(email);
			
			LogCEF.debug("enviaEmail(" + LogCEFUtil.retornaValor(enviaNotificacaoVO) + ", " +
					LogCEFUtil.retornaValor(subject) + ", " +
					LogCEFUtil.retornaValor(unidadeNotificadaVerificacao) + " ) TERMINANDO");
		} catch (Exception erro) {
			erro.printStackTrace();
			throw new Exception("Erro ao enviar email: >> " + erro.getMessage());
		}
	}
	
	@Transactional
	private String criaMensagem(EnviaNotificacaoVO enviaNotificacaoVO, UnidadeNotificadaVerificacao unidadeNotificadaVerificacao) throws Exception
	{
		LogCEF.debug("criaMensagem(" + LogCEFUtil.retornaValor(enviaNotificacaoVO) + ", " +
				LogCEFUtil.retornaValor(unidadeNotificadaVerificacao) + " ) INICIANDO");
		
		StringBuilder message = new StringBuilder();
		DefinedLanguageNotificacao dl = (DefinedLanguageNotificacao) AppContext.getApplicationContext().getBean("definedLanguageNotificacao");
		dl.setNotificacao(unidadeNotificadaVerificacao.getTemplateNotificacao());
		dl.setUsuario(SegurancaUsuario.getInstance().getUsuario());
		dl.setVerificacaoContrato(unidadeNotificadaVerificacao.getVerificacaoContrato());
		
		this.buildTemplateNotificacaoBO.setDefinedLanguageNotificacao(dl);
		
		message.append(this.buildTemplateNotificacaoBO.generateHtml(unidadeNotificadaVerificacao.getTemplateNotificacao().getTextoNotificacao()));
		
		
		LogCEF.debug("criaMensagem(" + LogCEFUtil.retornaValor(enviaNotificacaoVO) + ", " +
				LogCEFUtil.retornaValor(unidadeNotificadaVerificacao) + " ) TERMINANDO");
		
		return message.toString();
	}
	
	@Transactional
	private void enviaMensagemErro(String assunto, Date data, String descricaoErro) throws Exception
	{
		LogCEF.debug("enviaMensagemErro(" + LogCEFUtil.retornaValor(assunto)
				+ ", " + LogCEFUtil.retornaValor(data) + ", "
				+ LogCEFUtil.retornaValor(descricaoErro) + ") INICIANDO");
		
		PropriedadesEmail email = new PropriedadesEmail();
		email.setUsuario(propriedadesDAO.getPropriedade("mail.usuario"));
		email.setSenha(propriedadesDAO.getPropriedade("mail.usuario"));
		email.setServidor(propriedadesDAO.getPropriedade("mail.server"));
		email.setPorta(propriedadesDAO.getPropriedade("mail.portserver"));
		
		List<String> emailErro = enviaNotificacaoDAO.getEmailErro(DB_PROPERTY_ERRO_NOTIFICACAO_GROUP);
		
		String mensagem = "";
		
		for (String erro : emailErro) {
			mensagem = "<html><head></head><body>********************************************************************************************<br/>*  NOTIFICAÇÃO GERADA POR ROTINA AUTOMATICA DO SISTEMA SIIAC  *<br/>********************************************************************************************<br/><br/>1  INFORMAMOS QUE OCORREU UMA FALHA NO ENVIO DA NOTIFICAÇÃO ";
			mensagem += assunto + " NA " + new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).format(data);
			mensagem += "<br/><br/>MOTIVO: ";
			mensagem += descricaoErro;
			mensagem += "<br/><br/>ATENCIOSAMENTE,<br/><br/>EQUIPE DE PRODUÇÃO SIIAC<br/></body></html>";
			
			mailSender = new MailSender();
			
			email.setAssunto("ERRO NO ENVIO DE NOTIFICAÇÃO");
			email.setCorpo(mensagem);			
			
			try {
				
				List<String> destinatarios = new ArrayList<String>();
				destinatarios.add(erro);
				
				email.setDestinatarios(destinatarios, null);
				
				mailSender.sendEmail(email);
				
				LogCEF.debug("enviaMensagemErro(" + LogCEFUtil.retornaValor(assunto)
						+ ", " + LogCEFUtil.retornaValor(data) + ", "
						+ LogCEFUtil.retornaValor(descricaoErro) + ") TERMINANDO");
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Erro ao enviar email: >> " + e.getMessage());
			}
		}
	}

	@Transactional
	public void enviaNotificacao() {
		obtemVerificacoesRN090();
		obtemVerificacoesRN090A();
		obtemVerificacoesRN090B();
		obtemVerificacoesRN090CNivel1();
		obtemVerificacoesRN090CNivel2();
		obtemVerificacoesRN090D();		
	}

}
