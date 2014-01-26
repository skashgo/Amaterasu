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

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.ICategoriaProdutoBO;
import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.bo.IServicoVerificacaoBO;
import br.gov.caixa.siiac.bo.IServicoVerificacaoProdutoBO;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.util.Utilities;

@Service()
@Scope("request")
public class ServicoProdutoMB extends AbstractMB {


	/**
	 * Mensagens
	 */
	private static final String MSGS_SP = "msgsServicoProduto";

	private static final String MVSP0001 = "MVSP0001";
	private static final String MVSP0002 = "MVSP0002";
	
	public static final Character TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS_SITUACAO = '0'; 
	public static final Character TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS = '1'; 
	public static final Character TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO = '2'; 
	public static final Character TIPO_ACAO_PRODUTO_VERIFICACAO_ID_ALTERACAO_DADOS = '3'; 
	public static final Character TIPO_ACAO_PRODUTO_VERIFICACAO_ID_ALTERACAO_SITUACAO = '4';
	public static final Character TIPO_ACAO_PRODUTO_VERIFICACAO_ID_VERIFICACAO_PREVENTIVA = '5';
	
	public static final String TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_INCLUSAO_ALTERACAO_DADOS_SITUACAO = "Aplicável nas inclusões, alterações cadastrais e alterações de situação";
	public static final String TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_INCLUSAO_ALTERACAO_DADOS = "Aplicável nas inclusões e nas alterações cadastrais";
	public static final String TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_INCLUSAO = "Aplicável apenas nas inclusões";
	public static final String TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_ALTERACAO_DADOS = "Aplicável apenas nas alterações cadastrais";
	public static final String TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_ALTERACAO_SITUACAO = "Aplicável apenas nas alterações de situação";
	public static final String TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_VERIFICACAO_PREVENTIVA = "Aplicável nas verificações preventivas";
	
	/**
	 * Variaveis
	 */
	private transient IProdutoBO produtoBO;
	private transient ICategoriaProdutoBO categoriaProdutoBO;
	private transient IServicoVerificacaoProdutoBO servicoVerificacaoProdutoBO;
	private transient IServicoVerificacaoBO servicoVerificacaoBO;
	private Produto produto;
	private String txtCategoria;
	private List<ServicoVerificacaoProduto> listServicoVerificacaoProduto;

	private ServicoVerificacaoProduto servicoVerificacaoProduto;
	private List<SelectItem> selectItemAcaoServico;
	private List<SelectItem> selectItemServicos = new ArrayList<SelectItem>();
	private Integer nuServicoVerificacaoCombo;
	
	@Autowired
	public void setProdutoBO(IProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}
	
	@Autowired
	public void setCategoriaProdutoBO(ICategoriaProdutoBO categoriaProdutoBO) {
		this.categoriaProdutoBO = categoriaProdutoBO;
	}
	
	@Autowired
	public void setServicoVerificacaoProdutoBO(IServicoVerificacaoProdutoBO servicoVerificacaoProdutoBO) {
		this.servicoVerificacaoProdutoBO = servicoVerificacaoProdutoBO;
		fillCampos();
	}
	
	@Autowired
	public void setServicoVerificacaoBO(IServicoVerificacaoBO servicoVerificacaoBO) {
		this.servicoVerificacaoBO = servicoVerificacaoBO;
	}
	
	public ServicoProdutoMB(){
		setModoInicio();
	}
	

	//Methods Orquestradores da tela
	/**
	 * Orquestra a ManagedBean de acordo com seu estado atual.
	 */
	private void fillCampos(){
		if(isModoInicio()){
			fillCamposProduto();
			fillListServicosProduto();
		}else if(isModoCadastro()){
			this.servicoVerificacaoProduto = new ServicoVerificacaoProduto();
			this.nuServicoVerificacaoCombo = null;
			this.servicoVerificacaoProduto.setProduto(produto);
			fillListServicos();
		}else if(isModoAltera()){
			this.nuServicoVerificacaoCombo = servicoVerificacaoProduto.getServicoVerificacao().getNuServicoVerificacao();			
			fillListServicos();
		}
		
	}
	
	/**
	 * Preenche os campos do produto de acordo com o passado como parametro.
	 */
	private void fillCamposProduto(){
		if(Utilities.empty(produto)){
			String coProduto = (String)((HttpServletRequest)getExternalContext().getRequest()).getAttribute("campoNomeProduto");
			this.produto = produtoBO.findById(coProduto);
		}
		this.txtCategoria = categoriaProdutoBO.findById(produto.getCategoriaProduto().getNuCategoriaProduto()).getNoCategoriaProduto();
	}
	
	/**
	 * Preenche a lista de servicos vinculados ao produto.
	 */
	private void fillListServicosProduto(){
		this.listServicoVerificacaoProduto = servicoVerificacaoProdutoBO.getAll(produto);
	}
	
	/**
	 * Preenche o comboBox de servicos que nao estao vinculados ao produto.
	 */
	private void fillListServicos() {
		List<ServicoVerificacao> list = servicoVerificacaoBO.getAllServicoNotProduto(produto);
		selectItemServicos.clear();
		selectItemServicos.add(new SelectItem(0, "Selecione..."));
		if(list != null && !list.isEmpty()){
			for(ServicoVerificacao sv : list){
				selectItemServicos.add(new SelectItem(sv.getNuServicoVerificacao(), sv.getNoServicoVerificacao()));		
			}
		}
	}

	// --- Actions ---
	/**
	 * Valida a insercao/alteracao de um Servico vinculado ao Produto
	 */
	public Boolean doValida() {
		if (Utilities.empty(servicoVerificacaoProduto) || Utilities.empty(servicoVerificacaoProduto.getProduto())) {
			error(MSGS, MN002);
			warn( "Serviço de Verificação obrigatório.");
			return false;
		}else if(Utilities.empty(nuServicoVerificacaoCombo) || nuServicoVerificacaoCombo.equals(0)) {
			error(MSGS, MN002);
			warn( "Nome do Serviço de Verificação obrigatório.");
			return false;
		}else if(Utilities.empty(servicoVerificacaoProduto.getPzVerificacao())) {
			error(MSGS, MN002);
			warn( "Número do prazo obrigatório.");
			return false;
		}else if(servicoVerificacaoProduto.getPzVerificacao() < 1 || servicoVerificacaoProduto.getPzVerificacao() > 365){
			error(MSGS_SP, MVSP0001);
			return false;
		}else if(servicoVerificacaoProduto.getPzAvgestao() != null && (servicoVerificacaoProduto.getPzAvgestao() < 1 || servicoVerificacaoProduto.getPzAvgestao() > 365)){
			error(MSGS_SP, MVSP0001);
			return false;
		}else if(Utilities.empty(servicoVerificacaoProduto.getPzNotificacaoNivel1())) {
			error(MSGS, MN002);
			info( "Prazo de notificação obrigatório.");
			return false;
		}else if(servicoVerificacaoProduto.getPzNotificacaoNivel1() < 1 || servicoVerificacaoProduto.getPzNotificacaoNivel1() > 365) {
			error(MSGS_SP, MVSP0001);
			return false;
		}else if (isModoCadastro() && !servicoVerificacaoBO.isAtivo(nuServicoVerificacaoCombo)) {
		}else if(Utilities.notEmpty(servicoVerificacaoProduto.getPzNotificacaoUnidadeResponsavel()) && (servicoVerificacaoProduto.getPzNotificacaoUnidadeResponsavel() < 1 || servicoVerificacaoProduto.getPzNotificacaoUnidadeResponsavel() > 365)) {
			error(MSGS_SP, MVSP0001);
			return false;
		}else if (isModoCadastro() && !servicoVerificacaoBO.isAtivo(nuServicoVerificacaoCombo)) {
			error(MSGS_SP, MVSP0002);
			return false;
		}else if(Utilities.empty(servicoVerificacaoProduto.getIcTipoAcaoServico())) {
			error(MSGS, MN002);
			warn("Tipo de ação aplicável obrigatório.");
			return false;
		}
		
		// nao obrigatorios, porem se digitados devem ser entre 1 e 365.
		else if(Utilities.notEmpty(servicoVerificacaoProduto.getPzAvsuret()) && 
				(servicoVerificacaoProduto.getPzAvsuret() < 1 || servicoVerificacaoProduto.getPzAvsuret() > 365)){
			error(MSGS_SP, MVSP0001);
			return false;
		}else if(Utilities.notEmpty(servicoVerificacaoProduto.getPzNotificacaoNivel2()) && 
				(servicoVerificacaoProduto.getPzNotificacaoNivel2() < 1 || servicoVerificacaoProduto.getPzNotificacaoNivel2() > 365)){
			error(MSGS_SP, MVSP0001);
			return false;
		}
		return true;
	}

	/**
	 * Clique do botao 'Cancelar'/'Voltar'.
	 * @param evt
	 */
	public String doVoltar(){
		if(isModoInicio()){
			return "toProduto";
		}else if(isModoCadastro() || isModoAltera()){
			setModoInicio();
			warn(MSGS, MN003);
			fillCampos();
		}
		return "";
	}

	/**
	 * Clique do botao 'Incluir'.
	 * @param evt
	 */
	public String doIncluir(){
		setModoCadastro();
		fillCampos();
		return "";
	}

	/**
	 * Clique do botao 'Alterar' da tabela.
	 * @param evt
	 */
	public String doAlterar(){
		setModoAltera();
		this.servicoVerificacaoProduto = (ServicoVerificacaoProduto) getRequestMap().get("row");
		fillCampos();
		return "";
	}
	
	/**
	 * Clique no botao 'Inserir'/'Alterar'
	 * @return
	 */
	public String doGravar(){
		if(doValida()){
			servicoVerificacaoProduto.setServicoVerificacao(servicoVerificacaoBO.get(nuServicoVerificacaoCombo));
			servicoVerificacaoProdutoBO.salvar(servicoVerificacaoProduto);
			if(isModoCadastro()){
				info(MSGS, MN007);
			}else if(isModoAltera()){
				info(MSGS, MN008);
			}
			setModoInicio();
			fillCampos();
		}
		
		return "";
	}
	
	/**
	 * Clique no botao 'Excluir' da tabela.
	 * @return
	 */
	public String doExcluir(){
		this.servicoVerificacaoProduto = (ServicoVerificacaoProduto) getRequestMap().get("row");
		this.servicoVerificacaoProdutoBO.excluir(servicoVerificacaoProduto);
		info(MSGS, MN009);
		fillCampos();
		return "";
	}

	// --- Getters and Setters ---
	public List<ServicoVerificacaoProduto> getListServicoVerificacaoProduto() {
		return listServicoVerificacaoProduto;
	}

	public void setListServicoVerificacaoProduto(List<ServicoVerificacaoProduto> listServicoVerificacaoProduto) {
		this.listServicoVerificacaoProduto = listServicoVerificacaoProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getTxtCategoria() {
		return txtCategoria;
	}

	public void setTxtCategoria(String txtCategoria) {
		this.txtCategoria = txtCategoria;
	}
	
	public ServicoVerificacaoProduto getServicoVerificacaoProduto() {
		return servicoVerificacaoProduto;
	}

	public void setServicoVerificacaoProduto(ServicoVerificacaoProduto servicoVerificacaoProduto) {
		this.servicoVerificacaoProduto = servicoVerificacaoProduto;
	}

	public List<SelectItem> getSelectItemServicos() {
		return selectItemServicos;
	}

	public void setSelectItemServicos(List<SelectItem> selectItemServicos) {
		this.selectItemServicos = selectItemServicos;
	}
	
	public Integer getNuServicoVerificacaoCombo() {
		return nuServicoVerificacaoCombo;
	}

	public void setNuServicoVerificacaoCombo(Integer nuServicoVerificacaoCombo) {
		this.nuServicoVerificacaoCombo = nuServicoVerificacaoCombo;
	}
	
	public String getCoProdutoFormatado(){
		if (produto.getCoProduto().length() > 4) {
			return produto.getCoProduto().substring(0, 4) + "-" + produto.getCoProduto().substring(4);
		} else {
			return "";
		}
	}

	/**
	 * Verifica se deve ser mostrado o BreadCrumb inicial 
	 */
	public boolean isShowBreadInicio(){
		return isModoInicio();
	}

	/**
	 * Verifica se deve ser mostrado o BreadCrumb de cadastro 
	 */
	public boolean isShowBreadNovo(){
		return isModoCadastro();
	}

	/**
	 * Verifica se deve ser mostrado o BreadCrumb de alteracao 
	 */
	public boolean isShowBreadAlterar(){
		return isModoAltera();
	}

	/**
	 * Valida se deve ser mostrado o campo de situacao como ATIVO
	 * @return
	 */
	public boolean isShowSituacaoAtivo(){
		return Utilities.notEmpty(produto) && produto.isIcAtivo();
	}

	/**
	 * Valida se deve ser mostrado o campo de situacao como INATIVO
	 * @return
	 */
	public boolean isShowSituacaoInativo(){
		return Utilities.notEmpty(produto) && !produto.isIcAtivo();
	}

	/**
	 * Valida se deve ser mostrado o painel com os detalhes do produto
	 * @return
	 */
	public boolean isShowPanelProdutoTable(){
		return isModoInicio();
	}

	/**
	 * Valida se deve ser mostrado o formul�rio para vinculacao de servico ao produto.
	 * @return
	 */
	public boolean isShowPanelCadastro(){
		return isModoCadastro() || isModoAltera();
	}

	/**
	 * Valida se deve ser mostrado o comboBox com a lista de servicos para vincular ao produto.
	 * @return
	 */
	public boolean isShowComboServicoVerificacao(){
		return isModoCadastro();
	}

	/**
	 * Valida se deve ser mostrado o label com o servico 
	 * @return
	 */
	public boolean isShowLabelServicoVerificacao(){
		return isModoAltera();
	}

	/**
	 * Valida se deve ser mostrado o botao 'Salvar' (inserir)
	 * @return
	 */
	public boolean isShowButtonInserir(){
		return isModoCadastro();
	}

	/**
	 * Valida se deve ser mostrado o botao 'Salvar' (alterar)
	 * @return
	 */
	public boolean isShowButtonAlterar(){
		return isModoAltera();
	}

	/**
	 * Valida se deve ser mostrado o botao 'Voltar' (cancelar)
	 * @return
	 */
	public boolean isShowButtonVoltarCad(){
		return isModoCadastro() || isModoAltera();
	}
	
	public List<SelectItem> getSelectItemAcaoServico() {
		if(selectItemAcaoServico == null) {
			selectItemAcaoServico = new ArrayList<SelectItem>();
			selectItemAcaoServico.add(new SelectItem(TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS_SITUACAO,
					TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_INCLUSAO_ALTERACAO_DADOS_SITUACAO));
			selectItemAcaoServico.add(new SelectItem(TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO_ALTERACAO_DADOS,
					TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_INCLUSAO_ALTERACAO_DADOS));
			selectItemAcaoServico.add(new SelectItem(TIPO_ACAO_PRODUTO_VERIFICACAO_ID_INCLUSAO,
					TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_INCLUSAO));
			selectItemAcaoServico.add(new SelectItem(TIPO_ACAO_PRODUTO_VERIFICACAO_ID_ALTERACAO_DADOS,
					TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_ALTERACAO_DADOS));
			selectItemAcaoServico.add(new SelectItem(TIPO_ACAO_PRODUTO_VERIFICACAO_ID_ALTERACAO_SITUACAO,
					TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_ALTERACAO_SITUACAO));
			selectItemAcaoServico.add(new SelectItem(TIPO_ACAO_PRODUTO_VERIFICACAO_ID_VERIFICACAO_PREVENTIVA,
					TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_VERIFICACAO_PREVENTIVA));
		}
		
		return selectItemAcaoServico;
	}
	
	public void setContaAbertaLoteString(String conta) {
		if(servicoVerificacaoProduto != null) {
			servicoVerificacaoProduto.setIcContaAbertaLote(conta.trim().equals("S") ? true : false);
		}
	}
	
	public String getContaAbertaLoteString() {
		if(servicoVerificacaoProduto != null) {
			if(servicoVerificacaoProduto.getIcContaAbertaLote() != null) {
				return servicoVerificacaoProduto.getIcContaAbertaLote().equals(Boolean.TRUE) ? "S" : "N";
			}
		}
		return "";
	}
}
