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
package br.gov.caixa.siiac.view.mb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.ICanalComercialProdutoBO;
import br.gov.caixa.siiac.bo.ICategoriaProdutoBO;
import br.gov.caixa.siiac.bo.IContratoBO;
import br.gov.caixa.siiac.bo.ILogEnvioParecerBO;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.EnvioParecerVO;
import br.gov.caixa.siiac.model.EnvioParecerVOId;
import br.gov.caixa.siiac.model.LogEnvioParecerVO;
import br.gov.caixa.siiac.model.domain.CanalComercializacaoProduto;
import br.gov.caixa.siiac.model.domain.CategoriaProduto;
import br.gov.caixa.siiac.model.domain.LogEnvioParecer;
import br.gov.caixa.siiac.model.domain.LogEnvioParecerId;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.util.Anexo;
import br.gov.caixa.siiac.util.CanalComercializacaoConverter;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 */
@Service()
@Scope("request")
public class LogParecerMB extends AbstractMB {
	
	// Filtros
	private String filtroSimples;
	private String filtroProduto;
	private String filtroCodProduto;
	private String filtroUnidade;
	private String filtroCodUnidade;
	private String filtroUnidadeVerificacao;
	private String filtroCodUnidadeVerificacao;
	private Integer filtroCampoCategoria;
	private String filtroCanalComercializacao;
	private String filtroCodUsuarioResp;
	private Integer filtroParecer;
	private String filtroCodContrato;
	private Integer filtroNuConveniado;
	private Date filtroInicio;
	private Date filtroFim;
	private boolean filtroSomenteErro;
	
	private List<SelectItem> listProdutos = new ArrayList<SelectItem>();
	private List<SelectItem> listUnidades = new ArrayList<SelectItem>();
	private List<SelectItem> listUnidadesVerificacao = new ArrayList<SelectItem>();
	private List<SelectItem> listCanalComercializacao = new ArrayList<SelectItem>();
	private List<SelectItem> listCategoria = new ArrayList<SelectItem>();
	
	private boolean showModalReenvia;
	
	private boolean valido;
	private String dominio;
	
	// Variáveis utilizadas para reenvio do Parecer
	private String de;
	private String para;
	private String copia;
	private String assunto;
	private String conteudo;
	
	private ILogEnvioParecerBO logEnvioParecerBO;
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	private IUnidadeBO unidadeBO;
	private ICategoriaProdutoBO categoriaProdutoBO;
	private ICanalComercialProdutoBO canalComercialProdutoBO;
	private IContratoBO contratoBO;
	// Objeto Anexo
	private List<Anexo> anexos = new ArrayList<Anexo>();
	private LogEnvioParecerVO logVO;
	private LogEnvioParecer logParecer;
	
	// Lista com os históricos
	private List<LogEnvioParecer> listaLogEnvioParecer = new ArrayList<LogEnvioParecer>();
	
	// Lista com os históricos que serão apresentados na grid
	private List<LogEnvioParecerVO> listaDataTable = new ArrayList<LogEnvioParecerVO>();
	
	@PostConstruct
	public void init() throws DAOException {
		setModoInicio();
		
		loadListUnidades(listUnidades, filtroUnidade, false);
		loadListUnidades(listUnidadesVerificacao, filtroUnidadeVerificacao, true);
		loadListCategoria();
		loadListCanal();
		loadListProdutos();
		
		// Faz a busca das listas de pareceres
		doBuscaRegistros();
	}
	
	@Autowired
	public void setLogEnvioParecerBO(ILogEnvioParecerBO logEnvioParecerBO) {
		this.logEnvioParecerBO = logEnvioParecerBO;
	}
	
	@Autowired
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}
	
	@Autowired
	public void setUnidadeBO(IUnidadeBO unidadeBO) {
		this.unidadeBO = unidadeBO;
	}
	
	@Autowired
	public void setCanalComercialProdutoBO(ICanalComercialProdutoBO canalComercialProdutoBO) {
		this.canalComercialProdutoBO = canalComercialProdutoBO;
	}
	
	@Autowired
	public void setContratoBO(IContratoBO contratoBO) {
		this.contratoBO = contratoBO;
	}
	
	@Autowired
	public void setCategoriaProdutoBO(ICategoriaProdutoBO categoriaProdutoBO) {
		this.categoriaProdutoBO = categoriaProdutoBO;
	}
	
	/**
	 * Método responsável por abrir o modal de Reenviar o Parecer
	 * 
	 * @param evt
	 * @throws DAOException
	 */
	public void doAbreModalReenviaParecer(ActionEvent evt) throws DAOException {
		
		showModalReenvia = true;
		
		anexos = new ArrayList<Anexo>();
		
		for (LogEnvioParecerVO log : listaDataTable) {
			if (log.isReenviar()) {
				byte[] arquivo = logEnvioParecerBO.getPDFParecer(log.getNuParecer(), log.getAaParecer(), log.getNuUnidade(), log.getNuNatural());
				
				anexos.add(new Anexo(FormatUtil.getNomeParecerPDF(log.getNuParecer(), log.getAaParecer(), log.getNuUnidade(), log.getNuNatural()), arquivo));
			}
		}
	}
	
	/**
	 * Método responsável por fechar o modal de Reenviar o Parecer
	 * 
	 * @param evt
	 */
	public void doCancelar(ActionEvent evt) {
		showModalReenvia = false;
	}
	
	private void limparCampos() {
		de = "";
		para = "";
		copia = "";
		assunto = "";
		conteudo = "";
		anexos = new ArrayList<Anexo>();
	}
	
	public void doFazNada(ActionEvent evt) {
	}
	
	/**
	 * Método responsável por buscar os dados na tabela de Log dos Pareceres
	 */
	private void doBuscaRegistros() {
		listaDataTable = new ArrayList<LogEnvioParecerVO>();
		listaDataTable = logEnvioParecerBO.getListaVO(filtroSimples);
	}
	
	/**
	 * Método responsável por buscas as informações de um parecer e apresentar na tela.
	 * 
	 * @throws DAOException
	 */
	public void doMostraDetalhes(ActionEvent evt) throws DAOException {
		logVO = new LogEnvioParecerVO();
		logVO = (LogEnvioParecerVO) getRequestMap().get("log");
		
		// Preenchendo o objeto ID
		LogEnvioParecerId id = new LogEnvioParecerId(logVO.getNuParecer(), logVO.getAaParecer(), logVO.getNuUnidade(), logVO.getNuNatural());
		
		logParecer = new LogEnvioParecer();
		logParecer = logEnvioParecerBO.findById(id);
		
	}
	
	public void doDownloadPDF(ActionEvent evt) throws DAOException, IOException {
		logVO = new LogEnvioParecerVO();
		logVO = (LogEnvioParecerVO) getRequestMap().get("log");
		
		byte[] arquivo = logEnvioParecerBO.getPDFParecer(logVO.getNuParecer(), logVO.getAaParecer(), logVO.getNuUnidade(), logVO.getNuNatural());
		
		downloadPDF(arquivo, FormatUtil.getNomeParecerPDF(logVO.getNuParecer(), logVO.getAaParecer(), logVO.getNuUnidade(), logVO.getNuNatural()));
	}
	
	/**
	 * Método auxiliar para fazer o download do PDF
	 * 
	 * @param bs
	 * @param nome
	 * @throws IOException
	 */
	private void downloadPDF(byte[] bs, String nome) throws IOException {
		getResponse().setHeader("Pragma", "");
		getResponse().setHeader("Cache-Control", "");
		getResponse().setHeader("Expires", "");
		getResponse().setCharacterEncoding("UTF-8");
		getResponse().setContentType("application/download");
		getResponse().setHeader("Content-disposition", "attachment; filename=\"" + nome + ".pdf\"");
		getResponse().getOutputStream().write(bs);
		
		getFacesContext().responseComplete();
	}
	
	public void validaPareceres(ActionEvent evt) {
		valido = false;
		
		for (LogEnvioParecerVO vo : listaDataTable) {
			if (vo.isReenviar()) {
				valido = true;
				break;
			}
		}
	}
	
	public void doConsultarClick(ActionEvent evt) {
		if (isModoInicio()) {
			
			listaDataTable = new ArrayList<LogEnvioParecerVO>();
			listaDataTable = logEnvioParecerBO.getListaVO(filtroSimples);
			
			if (listaDataTable.isEmpty()) {
				warn(MSGS, MN016);
			}
			
		} else {
			
			if (!Utilities.empty(filtroInicio) || !Utilities.empty(filtroFim)) {
				// Validando as datas
				if (Utilities.empty(filtroInicio)) {
					error("Data inicial do período de verificação não pode ser nula");
					return;
				}
				
				if (Utilities.empty(filtroFim)) {
					error("Data final do período de verificação não pode ser nula");
					return;
				}
				
				// Comparando Datas entre si
				if (filtroFim.before(filtroInicio)) {
					error("Data final do período de verificação não pode ser menor que a data inicial");
					return;
				}
			}
				
			logVO = new LogEnvioParecerVO();
			
			// Preenchendo o objeto VO
			if (Utilities.notEmpty(filtroCodUnidade))
				logVO.setNuUnidadeFiltro(Short.parseShort(filtroCodUnidade));
			
			if (Utilities.notEmpty(filtroCodUnidadeVerificacao))
				logVO.setNuUnidadeVerificacaoFiltro(Short.parseShort(filtroCodUnidadeVerificacao));
			
			if (Utilities.notEmpty(filtroCanalComercializacao))
				logVO.setNuCanalFiltro(Short.parseShort(filtroCanalComercializacao));
			
			logVO.setCoProdutoFiltro(filtroCodProduto);
			logVO.setCoUsuarioRespFiltro(filtroCodUsuarioResp);
			logVO.setNuParecerFiltro(filtroParecer);
			logVO.setCoContratoFiltro(filtroCodContrato);
			logVO.setNuConveniadoFiltro(filtroNuConveniado);
			logVO.setInicioFiltro(filtroInicio);
			logVO.setFimFiltro(filtroFim);
			logVO.setSomenteComErroFiltro(filtroSomenteErro);
			
			listaDataTable = new ArrayList<LogEnvioParecerVO>();
			listaDataTable = logEnvioParecerBO.getListaAvancadoVO(logVO);
			
			if (listaDataTable.isEmpty()) {
				warn(MSGS, MN016);
			}
			
		}
	}
	
	public void doReenviar(ActionEvent evt) {
		try {
			// Validando os campos:
			if (doValida()) {
				
				if (!validateEmail(montaEmailUnico(de))) {
					error(getMessage(MSGS, MN002));
					warn("E-mail do campo DE está incorreto.");
					return;
				}
				
				// Transformando os e-mails
				List<String> destinatarios = montaEmail(para);
				
				// Validando destinatários
				if (!validaEmail(destinatarios)) {
					error(getMessage(MSGS, MN002));
					warn("E-mails incorretos no campo PARA.");
					return;
				}
				
				List<String> copias = new ArrayList<String>();
				if (Utilities.notEmpty(copia)) {
					copias = montaEmail(copia);
					
					// Validando destinatários
					if (!validaEmail(destinatarios)) {
						error(getMessage(MSGS, MN002));
						warn("E-mails incorretos no campo copia.");
						return;
					}
				}
				
				// Tudo está válido, agora será feito o envio do e-mail com os pareceres
				EnvioParecerVO logParecer = new EnvioParecerVO();
				List<EnvioParecerVOId> id = new ArrayList<EnvioParecerVOId>();
				for (LogEnvioParecerVO log : listaDataTable) {
					if (log.isReenviar()) {
						id.add(new EnvioParecerVOId(log.getNuParecer(), log.getAaParecer(), log.getNuUnidade(), log.getNuNatural()));
					}
				}
				
				logParecer.setId(id);
				logParecer.setAnexos(anexos);
				logParecer.setDe(de);
				logParecer.setCopias(copias);
				logParecer.setDestinatarios(destinatarios);
				logParecer.setAssunto(assunto);
				logParecer.setConteudo(conteudo);
				
				logEnvioParecerBO.reenvioParecer(logParecer);
				
				info(MSGS, MN007);
				showModalReenvia = false;
				doBuscaRegistros();
			}
		} catch (Exception e) {
			error(getMessage(MSGS, MN002));
			warn(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Método responsável por validar todos os campos obrigatórios no momento do reenvio do Parecer
	 * 
	 * @return
	 */
	private boolean doValida() {
		if (Utilities.empty(de)) {
			error(getMessage(MSGS, MN002));
			warn("Campo DE Obrigatório.");
			return false;
		}
		
		if (Utilities.empty(para)) {
			error(getMessage(MSGS, MN002));
			warn("Campo PARA Obrigatório.");
			return false;
		}
		
		if (Utilities.empty(assunto)) {
			error(getMessage(MSGS, MN002));
			warn("Campo ASSUNTO Obrigatório.");
			return false;
		}
		
		if (Utilities.empty(conteudo)) {
			error(getMessage(MSGS, MN002));
			warn("Corpo do E-mail Obrigatório.");
			return false;
		}
		
		return true;
	}
	
	private boolean validaEmail(List<String> emails) {
		for (String e : emails) {
			if (!validateEmail(e)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Valida um endereço de email pela API JavaMail
	 * 
	 * @param email
	 * @return
	 */
	private static boolean validateEmail(String email) {
		try {
			new InternetAddress(email).validate();
		} catch (AddressException ex) {
			// email inválido
			return false;
		}
		return true;
	}
	
	/**
	 * Método responsável por montar uma lista do tipo String com os e-mails passados como parâmetro.
	 * 
	 * @param emails
	 * @return
	 */
	private List<String> montaEmail(String emails) {
		List<String> listEmails = new ArrayList<String>();
		
		String[] emailsSemFormato = emails.split(",");
		
		for (String e : emailsSemFormato) {
			if (!e.contains("@")) {
				listEmails.add(e + getDominio());
			} else {
				listEmails.add(e);
			}
		}
		
		return listEmails;
	}
	
	private String montaEmailUnico(String email) {
		if (email.contains("@")) {
			return email;
		} else {
			return email + getDominio();
		}
	}
	
	private void loadListCategoria() {
		for (CategoriaProduto cat : categoriaProdutoBO.getListFiltro("", false)) {
			listCategoria.add(new SelectItem(cat.getNuCategoriaProduto(), cat.getNoCategoriaProduto()));
		}
	}
	
	private void loadListCanal() {
		for (CanalComercializacaoProduto can : canalComercialProdutoBO.getListCanalComercial()) {
			listCanalComercializacao.add(new SelectItem(can.getNuCanalCmrco(), can.getNoCanalCmrco()));
		}
	}
	
	public void loadListUnidades(ActionEvent evt) {
		try {
			this.loadListUnidades(listUnidades, filtroUnidade, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadListUnidadesVerificacao(ActionEvent evt) {
		try {
			this.loadListUnidades(listUnidadesVerificacao, filtroUnidadeVerificacao, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadListUnidades(List<SelectItem> listUnidadesFiltro, String filtroUnidade, boolean isVinculadas) throws DAOException {
		
		if (listUnidadesFiltro == null)
			listUnidadesFiltro = new ArrayList<SelectItem>();
		listUnidadesFiltro.clear();
		
		List<Unidade> unidades;
		
		Short nuPerfil = getPerfilUserLogado();
		Short nuUnidade = SegurancaUsuario.getInstance().getUsuario().getUnidade();
		unidades = matrizAbrangenciaBO.getListAbrangenciaUnidadesObj(nuPerfil, nuUnidade);
		if (unidades == null) {
			unidades = unidadeBO.getAll();
		}
		boolean first = Utilities.notEmpty(filtroUnidade);
		String filtroCodUnid = "";
		for (Unidade u : unidades) {
			if (Utilities.notEmpty(filtroUnidade)) {
				Pattern patternNumbers = Pattern.compile("[0-9]*");
				if (patternNumbers.matcher(filtroUnidade).matches()) {
					if (!ConvertUtils.padZerosLeft(String.valueOf(u.getId().getNuUnidade()), 4).contains(filtroUnidade)) {
						continue;
					}
				} else {
					if (!(u.getId().getNuUnidade() + " - " + u.getNomeAbreviado().toUpperCase()).contains(filtroUnidade.replaceFirst("0*", "").toUpperCase())) {
						continue;
					}
				}
			}
			if (first) {
				first = false;
				filtroCodUnid = String.valueOf(u.getId().getNuUnidade());
			}
			listUnidadesFiltro.add(new SelectItem(u.getId().getNuUnidade(), ConvertUtils.padZerosLeft(String.valueOf(u.getId().getNuUnidade()), 4) + " - " + u.getNomeAbreviado()));
			
			if (isVinculadas)
				filtroCodUnidadeVerificacao = filtroCodUnid;
			else
				filtroCodUnidade = filtroCodUnid;
		}
		
	}
	
	public void loadListProdutos(ActionEvent evt) {
		try {
			this.loadListProdutos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadListProdutos() throws DAOException {
		
		List<Produto> produtos;
		
		if (filtroCampoCategoria != null && filtroCampoCategoria > 0) {
			produtos = contratoBO.getLikeProdutoByCategoria("", filtroCampoCategoria);
		} else {
			produtos = contratoBO.getLikeProduto("");
		}
		
		if (listProdutos == null)
			listProdutos = new ArrayList<SelectItem>();
		listProdutos.clear();
		
		boolean first = Utilities.notEmpty(filtroProduto);
		filtroCodProduto = "";
		for (Produto p : produtos) {
			
			// Valida se o valor digitado no filtro é e se ele está contido
			// na concatenação.
			if (Utilities.notEmpty(filtroProduto) && !(p.getOperacaoFormatada() + "-" + p.getModalidadeFormatada() + " " + p.getNoProduto()).contains(filtroProduto)) {
				continue;
			}
			listProdutos.add(new SelectItem(p.getCoProduto(), p.getOperacaoFormatada() + "-" + p.getModalidadeFormatada() + " " + p.getNoProduto()));
			if (first) {
				first = false;
				filtroCodProduto = p.getCoProduto();
			}
		}
		
	}
	
	// -----------------------------------------------------------------------------
	// Variáveis de controle
	
	private Short getPerfilUserLogado() {
		return SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
	}
	
	public boolean getShowConsultarSimples() {
		return isModoInicio();
	}
	
	public boolean getShowConsultarAvancado() {
		return isModoFiltro();
	}
	
	public void doConsultarAvancado(ActionEvent evt) {
		setModoFiltro();
	}
	
	public void doConsultarSimples(ActionEvent evt) {
		setModoInicio();
	}
	
	// Getters e Setters
	public boolean isValido() {
		return valido;
	}
	
	public void setValido(boolean valido) {
		this.valido = valido;
	}
	
	public List<Anexo> getAnexos() {
		return anexos;
	}
	
	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}
	
	public List<LogEnvioParecer> getListaLogEnvioParecer() {
		return listaLogEnvioParecer;
	}
	
	public void setListaLogEnvioParecer(List<LogEnvioParecer> listaLogEnvioParecer) {
		this.listaLogEnvioParecer = listaLogEnvioParecer;
	}
	
	public List<LogEnvioParecerVO> getListaDataTable() {
		return listaDataTable;
	}
	
	public void setListaDataTable(List<LogEnvioParecerVO> listaDataTable) {
		this.listaDataTable = listaDataTable;
	}
	
	public void setLogParecer(LogEnvioParecer logParecer) {
		this.logParecer = logParecer;
	}
	
	public LogEnvioParecer getLogParecer() {
		return logParecer;
	}
	
	public String getDe() {
		return de;
	}
	
	public void setDe(String de) {
		this.de = de;
	}
	
	public String getPara() {
		return para;
	}
	
	public void setPara(String para) {
		this.para = para;
	}
	
	public String getCopia() {
		return copia;
	}
	
	public void setCopia(String copia) {
		this.copia = copia;
	}
	
	public String getAssunto() {
		return assunto;
	}
	
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public String getDominio() {
		if (Utilities.empty(dominio)) {
			dominio = logEnvioParecerBO.getDominio();
		}
		return dominio;
	}
	
	public boolean isShowModalReenvia() {
		return showModalReenvia;
	}
	
	public void setShowModalReenvia(boolean showModalReenvia) {
		this.showModalReenvia = showModalReenvia;
	}
	
	public String getFiltroSimples() {
		return filtroSimples;
	}
	
	public void setFiltroSimples(String filtroSimples) {
		this.filtroSimples = filtroSimples;
	}
	
	public String getFiltroProduto() {
		return filtroProduto;
	}
	
	public void setFiltroProduto(String filtroProduto) {
		this.filtroProduto = filtroProduto;
	}
	
	public String getFiltroUnidade() {
		return filtroUnidade;
	}
	
	public void setFiltroUnidade(String filtroUnidade) {
		this.filtroUnidade = filtroUnidade;
	}
	
	public String getFiltroUnidadeVerificacao() {
		return filtroUnidadeVerificacao;
	}
	
	public void setFiltroUnidadeVerificacao(String filtroUnidadeVerificacao) {
		this.filtroUnidadeVerificacao = filtroUnidadeVerificacao;
	}
	
	public List<SelectItem> getListProdutos() {
		return listProdutos;
	}
	
	public void setListProdutos(List<SelectItem> listProdutos) {
		this.listProdutos = listProdutos;
	}
	
	public List<SelectItem> getListUnidades() {
		return listUnidades;
	}
	
	public void setListUnidades(List<SelectItem> listUnidades) {
		this.listUnidades = listUnidades;
	}
	
	public List<SelectItem> getListUnidadesVerificacao() {
		return listUnidadesVerificacao;
	}
	
	public void setListUnidadesVerificacao(List<SelectItem> listUnidadesVerificacao) {
		this.listUnidadesVerificacao = listUnidadesVerificacao;
	}
	
	public List<SelectItem> getListCanalComercializacao() {
		return listCanalComercializacao;
	}
	
	public void setListCanalComercializacao(List<SelectItem> listCanalComercializacao) {
		this.listCanalComercializacao = listCanalComercializacao;
	}
	
	public List<SelectItem> getListCategoria() {
		return listCategoria;
	}
	
	public void setListCategoria(List<SelectItem> listCategoria) {
		this.listCategoria = listCategoria;
	}
	
	public String getFiltroCodProduto() {
		return filtroCodProduto;
	}
	
	public void setFiltroCodProduto(String filtroCodProduto) {
		this.filtroCodProduto = filtroCodProduto;
	}
	
	public String getFiltroCodUnidade() {
		return filtroCodUnidade;
	}
	
	public void setFiltroCodUnidade(String filtroCodUnidade) {
		this.filtroCodUnidade = filtroCodUnidade;
	}
	
	public String getFiltroCodUnidadeVerificacao() {
		return filtroCodUnidadeVerificacao;
	}
	
	public void setFiltroCodUnidadeVerificacao(String filtroCodUnidadeVerificacao) {
		this.filtroCodUnidadeVerificacao = filtroCodUnidadeVerificacao;
	}
	
	public Integer getFiltroCampoCategoria() {
		return filtroCampoCategoria;
	}
	
	public void setFiltroCampoCategoria(Integer filtroCampoCategoria) {
		this.filtroCampoCategoria = filtroCampoCategoria;
	}
	
	public String getFiltroCanalComercializacao() {
		return filtroCanalComercializacao;
	}
	
	public void setFiltroCanalComercializacao(String filtroCanalComercializacao) {
		this.filtroCanalComercializacao = filtroCanalComercializacao;
	}
	
	public String getFiltroCodUsuarioResp() {
		return filtroCodUsuarioResp;
	}
	
	public void setFiltroCodUsuarioResp(String filtroCodUsuarioResp) {
		this.filtroCodUsuarioResp = filtroCodUsuarioResp;
	}
	
	public Integer getFiltroParecer() {
		return filtroParecer;
	}
	
	public void setFiltroParecer(Integer filtroParecer) {
		this.filtroParecer = filtroParecer;
	}
	
	public String getFiltroCodContrato() {
		return filtroCodContrato;
	}
	
	public void setFiltroCodContrato(String filtroCodContrato) {
		this.filtroCodContrato = filtroCodContrato;
	}
	
	public Integer getFiltroNuConveniado() {
		return filtroNuConveniado;
	}
	
	public void setFiltroNuConveniado(Integer filtroNuConveniado) {
		this.filtroNuConveniado = filtroNuConveniado;
	}
	
	public Date getFiltroInicio() {
		return filtroInicio;
	}
	
	public void setFiltroInicio(Date filtroInicio) {
		this.filtroInicio = filtroInicio;
	}
	
	public Date getFiltroFim() {
		return filtroFim;
	}
	
	public void setFiltroFim(Date filtroFim) {
		this.filtroFim = filtroFim;
	}
	
	public boolean isFiltroSomenteErro() {
		return filtroSomenteErro;
	}
	
	public void setFiltroSomenteErro(boolean filtroSomenteErro) {
		this.filtroSomenteErro = filtroSomenteErro;
	}
}