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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.IBuildTemplateParecerBO;
import br.gov.caixa.siiac.bo.IEnviaParecerBO;
import br.gov.caixa.siiac.bo.ILogEnvioParecerBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.LogEnvioParecer;
import br.gov.caixa.siiac.model.domain.LogEnvioParecerId;
import br.gov.caixa.siiac.model.domain.Parecer;
import br.gov.caixa.siiac.model.domain.ParecerId;
import br.gov.caixa.siiac.model.domain.TemplateParecerDestinatarios;
import br.gov.caixa.siiac.persistence.dao.IEnviaParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IPropriedadesDAO;
import br.gov.caixa.siiac.util.Anexo;
import br.gov.caixa.siiac.util.AppContext;
import br.gov.caixa.siiac.util.DefinedLanguage;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.siiac.util.LogCEFUtil;
import br.gov.caixa.siiac.util.MailSender;
import br.gov.caixa.siiac.util.PropriedadesEmail;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 */
@Service
@Scope("prototype")
public class EnviaParecerBO implements IEnviaParecerBO {
	
	private IEnviaParecerDAO enviaParecerDAO;
	private IParecerDAO parecerDAO;
	private IPropriedadesDAO propriedadesDAO;
	private IBuildTemplateParecerBO buildTemplateParecerBO;
	private IUnidadeBO unidadeBO;
	
	private ILogEnvioParecerBO envioParecerBO;
	
	private String dominio = "";
	
	//
	// List<String> nomesDestinatarios = new ArrayList<String>();
	// List<String> nomesCopias = new ArrayList<String>();
	
	@Autowired
	public void setEnviaParecerDAO(IEnviaParecerDAO enviaParecerDAO) {
		this.enviaParecerDAO = enviaParecerDAO;
	}
	
	@Autowired
	public void setEnvioParecerBO(ILogEnvioParecerBO envioParecerBO) {
		this.envioParecerBO = envioParecerBO;
	}
	
	@Autowired
	public void setParecerDAO(IParecerDAO parecerDAO) {
		this.parecerDAO = parecerDAO;
	}
	
	@Autowired
	public void setPropriedadesDAO(IPropriedadesDAO propriedadesDAO) {
		this.propriedadesDAO = propriedadesDAO;
	}
	
	@Autowired
	public void setUnidadeBO(IUnidadeBO unidadeBO) {
		this.unidadeBO = unidadeBO;
	}
	
	@Autowired
	public void setBuildTemplateParecerBO(IBuildTemplateParecerBO buildTemplateParecerBO) {
		this.buildTemplateParecerBO = buildTemplateParecerBO;
	}
	
	@Transactional
	public String[] getConveniado(Integer nuConvenio) {
		LogCEF.debug("getConveniado(" + LogCEFUtil.retornaValor(nuConvenio) + ") INICIANDO");
		if (nuConvenio != null && nuConvenio > 0) {
			LogCEF.debug("getConveniado(" + LogCEFUtil.retornaValor(nuConvenio) + ") TERMINANDO");
			return enviaParecerDAO.getConveniado(nuConvenio);
		} else {
			return null;
		}
	}
	
	@Transactional
	public String[] getEmpregadoResponsavelParecer(String nuResponsavelVerificacao) {
		LogCEF.debug("getEmpregadoResponsavelParecer(" + LogCEFUtil.retornaValor(nuResponsavelVerificacao) + ") INICIANDO");
		if (Utilities.notEmpty(nuResponsavelVerificacao)) {
			String[] empregado = new String[2];
			empregado[0] = nuResponsavelVerificacao;
			
			// Buscando o nome do Empregado Responsável pelo Parecer
			empregado[1] = enviaParecerDAO.getNomeByEmpregado(FormatUtil.formatMatriculaInteiro(nuResponsavelVerificacao));
			
			LogCEF.debug("getEmpregadoResponsavelParecer(" + LogCEFUtil.retornaValor(nuResponsavelVerificacao) + ") TERMINANDO");
			return empregado;
		}
		
		return null;
	}
	
	@Transactional
	public String[] getEmpregadoResponsavelUnidade(Short nuUnidadeResponsavelContrato) {
		LogCEF.debug("getEmpregadoResponsavelUnidade(" + LogCEFUtil.retornaValor(nuUnidadeResponsavelContrato) + ") INICIANDO");
		if (Utilities.notEmpty(nuUnidadeResponsavelContrato)) {
			String[] empregado = new String[2];
			empregado = enviaParecerDAO.getEmpregadoResponsavelUnidade(nuUnidadeResponsavelContrato);
			if (empregado == null) {
				return null;
			}
			empregado[0] = FormatUtil.formatMatricula(Integer.parseInt(empregado[0]));
			
			LogCEF.debug("getEmpregadoResponsavelUnidade(" + LogCEFUtil.retornaValor(nuUnidadeResponsavelContrato) + ") TERMINANDO");
			return empregado;
		}
		
		return null;
	}
	
	@Transactional
	public String[] getGerenteAssinaParecer(String coGerenteParecer) {
		LogCEF.debug("getGerenteAssinaParecer(" + LogCEFUtil.retornaValor(coGerenteParecer) + ") INICIANDO");
		if (Utilities.notEmpty(coGerenteParecer)) {
			String[] gerente = new String[2];
			gerente[0] = coGerenteParecer;
			
			// Buscando o nome do Empregado Responsável pelo Parecer
			gerente[1] = enviaParecerDAO.getNomeByEmpregado(FormatUtil.formatMatriculaInteiro(coGerenteParecer));
			
			LogCEF.debug("getGerenteAssinaParecer(" + LogCEFUtil.retornaValor(coGerenteParecer) + ") TERMINANDO");
			return gerente;
		}
		
		return null;
	}
	
	@Transactional
	public String[] getUnidAliBensImoveisVincUnidRespContrato(Short nuUnidadeResponsavelContrato) {
		LogCEF.debug("getUnidAliBensImoveisVincUnidRespContrato(" + LogCEFUtil.retornaValor(nuUnidadeResponsavelContrato) + ") INICIANDO");
		if (nuUnidadeResponsavelContrato != null && nuUnidadeResponsavelContrato > 0) {
			LogCEF.debug("getGerenteAssinaParecer(" + LogCEFUtil.retornaValor(nuUnidadeResponsavelContrato) + ") TERMINANDO");
			return enviaParecerDAO.getUnidAliBensImoveisVincUnidRespContrato(nuUnidadeResponsavelContrato);
		} else
			return null;
	}
	
	@Transactional
	public String[] getUnidEmpreRespParecer(String nuMatriculaRespParecer) {
		LogCEF.debug("getUnidEmpreRespParecer(" + LogCEFUtil.retornaValor(nuMatriculaRespParecer) + ") INICIANDO");
		if (Utilities.notEmpty(nuMatriculaRespParecer)) {
			LogCEF.debug("getUnidEmpreRespParecer(" + LogCEFUtil.retornaValor(nuMatriculaRespParecer) + ") TERMINANDO");
			return enviaParecerDAO.getUnidEmpreRespParecer(FormatUtil.formatMatriculaInteiro(nuMatriculaRespParecer));
		} else
			return null;
	}
	
	@Transactional
	public String[] getUnidadeResponsavelContrato(Short nuUnidadeRespContrato) {
		LogCEF.debug("getUnidadeResponsavelContrato(" + LogCEFUtil.retornaValor(nuUnidadeRespContrato) + ") INICIANDO");
		if (nuUnidadeRespContrato != null && nuUnidadeRespContrato > 0) {
			LogCEF.debug("getUnidadeResponsavelContrato(" + LogCEFUtil.retornaValor(nuUnidadeRespContrato) + ") TERMINANDO");
			return enviaParecerDAO.getUnidadeResponsavelContrato(nuUnidadeRespContrato);
		} else
			return null;
	}
	
	@Transactional
	public String[] getUnidadeSuperiorHierarquicaUnidRespContrato(Short nuUnidadeRespContrato) {
		LogCEF.debug("getUnidadeSuperiorHierarquicaUnidRespContrato(" + LogCEFUtil.retornaValor(nuUnidadeRespContrato) + ") INICIANDO");
		if (nuUnidadeRespContrato != null && nuUnidadeRespContrato > 0) {
			LogCEF.debug("getUnidadeSuperiorHierarquicaUnidRespContrato(" + LogCEFUtil.retornaValor(nuUnidadeRespContrato) + ") TERMINANDO");
			return enviaParecerDAO.getUnidadeSuperiorHierarquicaUnidRespContrato(nuUnidadeRespContrato);
		} else
			return null;
	}
	
	@Transactional
	public String[] getUnidConfVincUnidRespContrato(Short nuUnidadeResponsavelContrato) {
		LogCEF.debug("getUnidConfVincUnidRespContrato(" + LogCEFUtil.retornaValor(nuUnidadeResponsavelContrato) + ") INICIANDO");
		if (nuUnidadeResponsavelContrato != null && nuUnidadeResponsavelContrato > 0) {
			LogCEF.debug("getUnidConfVincUnidRespContrato(" + LogCEFUtil.retornaValor(nuUnidadeResponsavelContrato) + ") TERMINANDO");
			return enviaParecerDAO.getUnidConfVincUnidRespContrato(nuUnidadeResponsavelContrato);
		} else
			return null;
	}
	
	@Transactional
	public String[] getVendedor(Contrato contrato) {
		LogCEF.debug("getVendedor(" + LogCEFUtil.retornaValor(contrato) + ") INICIANDO");
		if (Utilities.notEmpty(contrato) && Utilities.notEmpty(contrato.getDetalhesContrato()) && Utilities.notEmpty(contrato.getDetalhesContrato().getNoCampoLivre8()) && Utilities.notEmpty(contrato.getDetalhesContrato().getNoVendedor())) {
			String[] vendedor = new String[2];
			if (contrato.getIcTipoVerificacaoContrato().equals(Contrato.TIPO_VERIFICACAO_CONTRATO_ID_VERIFICACAO_PREVENTIVA)) {
				vendedor[0] = contrato.getDetalhesContrato().getNoCampoLivre8();
				vendedor[1] = contrato.getDetalhesContrato().getNoVendedor();
			}
			
			LogCEF.debug("getVendedor(" + LogCEFUtil.retornaValor(contrato) + ") TERMINANDO");
			return vendedor;
		}
		
		return null;
	}
	
	@Transactional
	public String aplicaMascaraEmail(Integer matricula) {
		LogCEF.debug("aplicaMascaraEmail(" + LogCEFUtil.retornaValor(matricula) + ") INICIANDO");
		return "C" + ConvertUtils.padZerosLeft(matricula.toString(), 6) + getDominio();
	}
	
	/**
	 * Método responsável por buscar os endereços de e-mail de acordo com a escolha do usuário na tela de cadastro do
	 * template do parecer.
	 * 
	 * @param dest
	 * @param parecer
	 * @return
	 */
	@Transactional
	public String[] getEmail(Integer coTemplateParecerDestinatario, Contrato contrato, String coGerenteParecer, String coResponsavelParecer, String coResponsavelVerificacao) {
		LogCEF.debug("getEmail(" + LogCEFUtil.retornaValor(coTemplateParecerDestinatario) + ", " + LogCEFUtil.retornaValor(contrato) + ", " + LogCEFUtil.retornaValor(coGerenteParecer) + ", " + LogCEFUtil.retornaValor(coResponsavelParecer) + ", " + LogCEFUtil.retornaValor(coResponsavelVerificacao) + ") INICIANDO");
		
		try {
			
			String[] email = new String[2];
			switch (coTemplateParecerDestinatario) {
				
				case TemplateParecerDestinatarios.EMAIL_ID_CONVENIADO:
					email = this.getConveniado(contrato.getDetalhesContrato().getNuConveniado());
					break;
				
				case TemplateParecerDestinatarios.EMAIL_ID_EMPREGADO_RESPONSAVEL_PARECER:
					email = this.getEmpregadoResponsavelParecer(coResponsavelVerificacao);
					break;
				
				case TemplateParecerDestinatarios.EMAIL_ID_GERENTE_ASSINA_PARECER:
					email = this.getGerenteAssinaParecer(coGerenteParecer);
					break;
				
				case TemplateParecerDestinatarios.EMAIL_ID_GERENTE_UNIDADE_RESPONSAVEL_CONTRATO:
					email = this.getEmpregadoResponsavelUnidade(contrato.getNuUnidade());
					break;
				
				case TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_ALIENACAO_BENS_MOVEIS_IMOVEIS_UNIDADE_RESPONSAVEL_CONTRATO:
					email = this.getUnidAliBensImoveisVincUnidRespContrato(contrato.getNuUnidade());
					break;
				
				case TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_CONFORMIDADE_UNIDADE_RESPONSAVEL_CONTRATO:
					email = this.getUnidConfVincUnidRespContrato(contrato.getNuUnidade());
					break;
				
				case TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_EMPREGADO_RESPONSAVEL_PARECER:
					email = this.getUnidEmpreRespParecer(coResponsavelParecer);
					break;
				
				case TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_RESPONSAVEL_CONTRATO:
					email = this.getUnidadeResponsavelContrato(contrato.getNuUnidade());
					break;
				
				case TemplateParecerDestinatarios.EMAIL_ID_UNIDADE_SUPERIOR_HIERARQUICA_UNIDADE_RESPONSAVEL_CONTRATO:
					email = this.getUnidadeResponsavelContrato(contrato.getNuUnidade());
					break;
				
				case TemplateParecerDestinatarios.EMAIL_ID_VENDEDOR:
					email = this.getVendedor(contrato);
					break;
				
				default:
					return null;
			}
			
			if (email == null || Utilities.empty(email[0]))
				return null;
			
			if (!email[0].contains("@")) {
				email[0] = email[0] + getDominio();
			}
			
			// convertendo tudo para minúsculo, pois há emails em maiúsculo que vem do SIICO
			email[0] = email[0] != null ? email[0].toLowerCase() : email[0];
			
			if (MailSender.validateEmail(email[0])) {
				LogCEF.debug("getEmail(" + LogCEFUtil.retornaValor(coTemplateParecerDestinatario) + ", " + LogCEFUtil.retornaValor(contrato) + ", " + LogCEFUtil.retornaValor(coGerenteParecer) + ", " + LogCEFUtil.retornaValor(coResponsavelParecer) + ", " + LogCEFUtil.retornaValor(coResponsavelVerificacao) + ") TERMINANDO");
				return email;
			} else
				return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public void enviaParecer(ParecerId parecerId) {
		LogEnvioParecer log = new LogEnvioParecer();
		log.setId(new LogEnvioParecerId(parecerId.getNuParecer(), parecerId.getAaParecer(), parecerId.getNuUnidade(), parecerId.getNuNatural()));
		log.setDtEnvioParecer(new Date());
		
		List<String> destinatarios = new ArrayList<String>();
		List<String> copias = new ArrayList<String>();
		
		LogCEF.debug("enviaParecer(" + LogCEFUtil.retornaValor(parecerId) + ") INICIANDO");
		try {
			// Buscando as informações do Parecer
			Parecer parecer = this.parecerDAO.findById(parecerId);
			
			// Listagem dos destinatários
			if (parecer == null || parecer.getTemplateParecer() == null || parecer.getTemplateParecer().getListaTemplateParecerDestinatarios() == null || parecer.getTemplateParecer().getListaTemplateParecerDestinatarios().isEmpty()) {
				LogCEF.info("Não há destinatários a receber o e-mail do parecer");
				return;
			}
			LogCEF.debug("Enviando e-mail do parecer '" + parecer.getNoParecer() + "'");
			List<TemplateParecerDestinatarios> listaDestinatarios = parecer.getTemplateParecer().getListaTemplateParecerDestinatarios();
			
			List<String> nomesDestinatarios = new ArrayList<String>();
			List<String> nomesCopias = new ArrayList<String>();
			
			/*
			 * Buscando os e-mails e preenchendo duas listas de acordo com o tipo: 1 - normal 2 - cópia
			 */

			for (TemplateParecerDestinatarios templateParecerDestinatarios : listaDestinatarios) {
				String[] emails = getEmail(templateParecerDestinatarios.getTemplateParecerDestinatariosId().getCoDestinatario(), parecer.getContrato(), parecer.getCoGerenteParecer(), parecer.getCoResponsavelParecer(), parecer.getVerificacaoContratoParecer().getCoResponsavelVerificacao());
				
				if (templateParecerDestinatarios.getIcTipoDestinatario().equals(TemplateParecerDestinatarios.EMAIL_TIPO_DESTINATARIO_TO)) {
					if (emails != null) {
						destinatarios.add(emails[0]);
						nomesDestinatarios.add(emails[1]);
					}
				} else {
					if (emails != null) {
						copias.add(emails[0]);
						nomesCopias.add(emails[1]);
					}
				}
			}
			
			// Buscando as propriedades necessários para o envio de e-mails
			PropriedadesEmail propriedades = new PropriedadesEmail();
			propriedades.setUsuario(propriedadesDAO.getPropriedade("mail.usuario"));
			propriedades.setSenha(propriedadesDAO.getPropriedade("mail.usuario"));
			propriedades.setServidor(propriedadesDAO.getPropriedade("mail.server"));
			propriedades.setPorta(propriedadesDAO.getPropriedade("mail.portserver"));
			
			// Método que recebe duas listas do tipo String e as transforma em dois
			// vetores do tipo InternetAddress
			propriedades.setDestinatarios(destinatarios, copias);
			
			// Preenchendo a lista de anexos
			propriedades.setAnexos(constroiListaAnexo(parecer));
			
			// Assunto e corpo do e-mail
			propriedades.setAssunto(getAssuntoEmail(parecer));
			propriedades.setCorpo(getCorpoEmail(parecer, nomesDestinatarios, nomesCopias));
			
			MailSender email = new MailSender();
			email.sendEmail(propriedades);
			
			log.setIcSituacaoEnvioParecer(true);
			log.setDeEnvioParecer(descricacaoLog("Parecer enviado com sucesso!", destinatarios, copias));
			envioParecerBO.gravarLog(log);
			
		} catch (Exception e) {
			log.setIcSituacaoEnvioParecer(false);
			log.setDeEnvioParecer(descricacaoLog(e.getMessage(), destinatarios, copias));
			envioParecerBO.gravarLog(log);
			e.printStackTrace();
		}
		
		LogCEF.debug("enviaParecer(" + LogCEFUtil.retornaValor(parecerId) + ") TERMINANDO");
		
	}
	
	@Transactional
	public List<Anexo> constroiListaAnexo(Parecer parecer) {
		LogCEF.debug("constroiListaAnexo(" + LogCEFUtil.retornaValor(parecer) + ") INICIANDO");
		// Montando a lista de anexos
		Anexo anexo = new Anexo();
		anexo.setArquivo(parecer.getArquivoImagemParecer().getImParecer());
		anexo.setNomeArquivo("PA_" + parecer.getParecerId().getNuUnidade() + "_" + ConvertUtils.padZerosLeft(String.valueOf(parecer.getParecerId().getNuParecer()), 4) + "_" + parecer.getParecerId().getAaParecer() + ".pdf");
		
		List<Anexo> listaAnexo = new ArrayList<Anexo>();
		listaAnexo.add(anexo);
		
		LogCEF.debug("constroiListaAnexo(" + LogCEFUtil.retornaValor(parecer) + ") TERMINANDO");
		return listaAnexo;
	}
	
	@Transactional
	public String getAssuntoEmail(Parecer parecer) {
		LogCEF.debug("getAssuntoEmail(" + LogCEFUtil.retornaValor(parecer) + ") INICIANDO");
		DefinedLanguage dl = (DefinedLanguage) AppContext.getApplicationContext().getBean("definedLanguage");
		dl.setParecer(parecer);
		dl.setUsuario(SegurancaUsuario.getInstance().getUsuario());
		this.buildTemplateParecerBO.setDefinedLanguage(dl);
		
		LogCEF.debug("getAssuntoEmail(" + LogCEFUtil.retornaValor(parecer) + ") TERMINANDO");
		return buildTemplateParecerBO.process(parecer.getTemplateParecer().getNoAssuntoParecer());
	}
	
	@Transactional
	public String getCorpoEmail(Parecer parecer, List<String> nomesDestinatarios, List<String> nomesCopias) {
		LogCEF.debug("getCorpoEmail(" + LogCEFUtil.retornaValor(parecer) + ", " + LogCEFUtil.retornaValor(nomesDestinatarios) + ", " + LogCEFUtil.retornaValor(nomesCopias) + ") INICIANDO");
		StringBuffer html = new StringBuffer();
		html.append("<html>");
		html.append("<head>");
		html.append("<meta http-equiv=\"Content-Type\" content=\"text/html\"; charset=\"utf-8\">");
		html.append("</head>");
		html.append("<body>");
		// Essa mensagem está um pouco diferente do solicitado pelo portal em SM 911, conforme conversado com Welber em
		// 15/08/2012 em viagem a Goiânia.
		html.append("-------- MENSAGEM ENVIADA POR PROCESSO AUTOMÁTICO, FAVOR NÃO RESPONDER. --------");
		html.append("<br/><br/>");
		
		html.append("A ");
		html.append(concatValuesByComma(nomesDestinatarios));
		html.append("<br/>");
		html.append("c/c ");
		html.append(concatValuesByComma(nomesCopias));
		html.append("<br/><br/>");
		
		html.append("1&nbsp;&nbsp;&nbsp;&nbsp;Encaminhamos em anexo parecer PA ");
		html.append(ConvertUtils.padZerosLeft(String.valueOf(parecer.getParecerId().getNuParecer()), 5));
		html.append(" / ");
		html.append(parecer.getParecerId().getAaParecer());
		html.append(" de ");
		html.append(new SimpleDateFormat("dd/MM/yyyy").format(parecer.getDtParecer()));
		html.append(" para providências.");
		html.append("<br/><br/>");
		
		html.append("Att.");
		html.append("<br/><br/>");
		html.append(unidadeBO.getUnidadeByEmpregado(parecer.getCoResponsavelParecer()).getNomeCompleto());
		
		html.append("</body>");
		html.append("</html>");
		
		LogCEF.debug("getCorpoEmail(" + LogCEFUtil.retornaValor(parecer) + ", " + LogCEFUtil.retornaValor(nomesDestinatarios) + ", " + LogCEFUtil.retornaValor(nomesCopias) + ") TERMINANDO");
		return html.toString();
	}
	
	@Transactional
	public boolean validaEmail(String email) {
		LogCEF.debug("validaEmail(" + LogCEFUtil.retornaValor(email) + ") INICIANDO");
		
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		
		// Match the given string with the pattern
		Matcher m = p.matcher(email);
		
		// check whether match is found
		boolean matchFound = m.matches();
		
		LogCEF.debug("validaEmail(" + LogCEFUtil.retornaValor(email) + ") TERMINANDO");
		return matchFound;
	}
	
	/**
	 * Concatena valores da lista parâmetro, separando-os por ',' (vírgula)
	 * 
	 * @param destinatarios
	 * @return
	 */
	private String concatValuesByComma(List<String> destinatarios) {
		LogCEF.debug("concatValuesByComma(" + LogCEFUtil.retornaValor(destinatarios) + ") INICIANDO");
		String nomes = "";
		
		for (String nome : destinatarios) {
			nomes += nome + ", ";
		}
		
		if (Utilities.notEmpty(nomes)) {
			nomes = nomes.substring(0, nomes.length() - 2);
		}
		
		LogCEF.debug("concatValuesByComma(" + LogCEFUtil.retornaValor(destinatarios) + ") TERMINANDO");
		return nomes;
		
	}
	
	@Transactional
	public String getDominio() {
		LogCEF.debug("getDominio() INICIANDO");
		
		if (Utilities.empty(dominio))
			dominio = propriedadesDAO.getPropriedade("mail.dominio.destinatarios");
		
		LogCEF.debug("getDominio() TERMINANDO");
		return dominio;
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
}
