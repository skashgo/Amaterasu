/**
 * Copyright (c) 2009-2011 Caixa Econ�mica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econ�mica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e est�
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condi��es de c�pia e utiliza��o do todo ou partes dependem de autoriza��o da
 * empresa. C�pias n�o s�o permitidas sem expressa autoriza��o. N�o pode ser
 * comercializado ou utilizado para prop�sitos particulares.
 * 
 * Uso exclusivo da Caixa Econ�mica Federal. A reprodu��o ou distribui��o n�o
 * autorizada deste programa ou de parte dele, resultar� em puni��es civis e
 * criminais e os infratores incorrem em san��es previstas na legisla��o em
 * vigor.
 * 
 * Hist�rico do Subversion:
 * 
 * LastChangedRevision:  
 * LastChangedBy:  
 * LastChangedDate: 
 * 
 * HeadURL: 
 * 
 */
package br.gov.caixa.siiac.bo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.ICategoriaProdutoBO;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IPreferenciasUsuarioBO;
import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.bo.IVerificacaoPreventivaBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.exception.ReportErrorCreateDatasourceException;
import br.gov.caixa.siiac.exception.ReportFinalNullException;
import br.gov.caixa.siiac.exception.ReportInvalidPathException;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.ContratoVO;
import br.gov.caixa.siiac.model.FiltroVerificacaoPreventivaVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.DetalhesContrato;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.persistence.dao.IDetalhesContratoDAO;
import br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO;
import br.gov.caixa.siiac.persistence.dao.IResultadoApontamentoProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoPreventivaDAO;
import br.gov.caixa.siiac.report.RelatorioVerificacao;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.util.FilterBase;
import br.gov.caixa.siiac.util.constants.Perfil;
import br.gov.caixa.siiac.view.mb.pagination.ParamPagination;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */

@Service
@Scope("prototype")
public class VerificacaoPreventivaBO implements IVerificacaoPreventivaBO 
{

	private IVerificacaoPreventivaDAO verificacaoPreventivaDAO;
	private IProdutoBO produtoBO;
	private IProdutoUsuarioDAO produtoUsuarioDAO;
	private IResultadoApontamentoProdutoDAO resultadoApontamentoProdutoDAO;
	private IPreferenciasUsuarioBO preferenciasUsuarioBO;
	private ICategoriaProdutoBO categoriaProdutoBO;
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	private IVerificacaoContratoDAO verificacaoContratoDAO;
	private IDetalhesContratoDAO detalhesContratoDAO;
	
	@Autowired
	public void setProdutoBO(IProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}

	@Autowired
	public void setProdutoUsuarioDAO(IProdutoUsuarioDAO produtoUsuarioDAO) {
		this.produtoUsuarioDAO = produtoUsuarioDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoDAO(
			IVerificacaoContratoDAO verificacaoContratoDAO) {
		this.verificacaoContratoDAO = verificacaoContratoDAO;
	}

	@Autowired
	public void setDetalhesContratoDAO(IDetalhesContratoDAO detalhesContratoDAO) {
		this.detalhesContratoDAO = detalhesContratoDAO;
	}

	@Autowired
	public void setResultadoApontamentoProdutoDAO(
			IResultadoApontamentoProdutoDAO resultadoApontamentoProdutoDAO) {
		this.resultadoApontamentoProdutoDAO = resultadoApontamentoProdutoDAO;
	}

	@Autowired
	public void setCategoriaProdutoBO(ICategoriaProdutoBO categoriaProdutoBO) {
		this.categoriaProdutoBO = categoriaProdutoBO;
	}

	@Autowired
	public void setVerificacaoPreventivaDAO(IVerificacaoPreventivaDAO verificacaoPreventivaDAO) {
		this.verificacaoPreventivaDAO = verificacaoPreventivaDAO;
	}
	
	@Autowired	
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}
	
	public void setPreferenciasUsuarioBO(
			IPreferenciasUsuarioBO preferenciasUsuarioBO) {
		this.preferenciasUsuarioBO = preferenciasUsuarioBO;
	}


	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IVerificacaoPreventivaBO#merge(br.gov.caixa.siiac.model.domain.Contrato)
	 */
	@Transactional
	public Contrato merge(Contrato c) {
		// TODO Auto-generated method stub
		verificacaoPreventivaDAO.saveOrUpdate(c);
		return c;
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IVerificacaoPreventivaBO#findAll()
	 */
	public List<Contrato> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IVerificacaoPreventivaBO#getListVerificacaoPreventivaFiltroSimples(java.lang.String)
	 */
	public List<Contrato> getListVerificacaoPreventivaFiltroSimples(String pesquisa) throws SIIACException {

		Criteria c = verificacaoPreventivaDAO.getCriteria();
		
		/*
		 * pesquisar nos seguintes campos: 
		 * - unidade 
		 * - produto 
		 * - número identificador 
		 * - avaliação siric
		 */
		
		if(Utilities.notEmpty(pesquisa)){
			Disjunction disjuction = Restrictions.disjunction();
			
			
			
			disjuction.add(Restrictions.eq("", ""));
			
			c.add(disjuction);
		}
		
		return verificacaoPreventivaDAO.findByCriteria(c);
	}
	
	
	@Transactional
	@Deprecated
	public List<Contrato> simpleFilter(IUsuario usuarioLogado, Short perfilUsuario, String pesquisaString, Boolean produtosPreferenciais, ParamPagination paramPagination) throws DAOException {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setSimpleFilter();
		contratoVO.setPesquisaSimples(pesquisaString);
		contratoVO.setProdutosPreferenciais(produtosPreferenciais);
		return this.filter(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}
	
	@Transactional
	public List<Contrato> simpleFilter(IUsuario usuarioLogado, Short perfilUsuario, FilterBase filtro, ParamPagination paramPagination) throws DAOException {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setSimpleFilter();
		contratoVO.setPesquisaSimples(filtro.getString("pesquisaString"));
		contratoVO.setProdutosPreferenciais(filtro.getBoolean("produtosPreferenciais"));
		return this.filter(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}
	
	private List<Contrato> filter(ContratoVO contratoVO, Short perfilUsuario, IUsuario usuarioLogado, ParamPagination paramPagination) throws DAOException{
		
		List<String> produtos = new ArrayList<String>();		
		List<String> servicos = new ArrayList<String>();		
		
		//Variável que define se irá buscar produtos preferenciais ou não
		if (contratoVO.getProdutosPreferenciais())
		{	
			produtos = preferenciasUsuarioBO.listProdutoPreferencial(usuarioLogado.getMatricula());
			servicos = preferenciasUsuarioBO.listServicoPreferencial(usuarioLogado.getMatricula());
		}
		
		//Verificando se existe Produtos Vinculados
		Integer qtdProdutosUsuario = produtoUsuarioDAO.getCountProdutoUsuario(usuarioLogado.getMatricula());
		if (qtdProdutosUsuario == 0)
		{
			contratoVO.setExisteProdutosUsuario(false);
		} else {
			contratoVO.setExisteProdutosUsuario(true);
		}
		
		String matricula = usuarioLogado.getMatricula();
		Short nuUnidade = usuarioLogado.getUnidade();
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(perfilUsuario, nuUnidade);
		contratoVO.setUnidades(listUnidadesAbrangencia);
		Short nuPerfil = usuarioLogado.getPerfis().get(0);
		List<String> listProdutosAbrangentes = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, nuPerfil);
		return verificacaoPreventivaDAO.filtra(contratoVO, produtos, servicos, listProdutosAbrangentes, paramPagination);
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IVerificacaoPreventivaBO#advancedFilter(br.gov.caixa.siiac.security.IUsuario, java.lang.Short, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.util.Date, java.util.Date, java.util.Date, java.lang.Boolean, java.lang.Boolean)
	 */
	@Transactional
	@Deprecated
	public List<Contrato> advancedFilter(IUsuario usuarioLogado, Short perfilUsuario, FiltroVerificacaoPreventivaVO filtro, ParamPagination paramPagination) throws DAOException {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setAdvancedFilter();
		
		Contrato contrato = new Contrato();
		Produto produto = new Produto();
		DetalhesContrato detalhes = new DetalhesContrato();
		
		if (Utilities.notEmpty(filtro.getCodUnidade()))
			contrato.setNuUnidade(Short.parseShort(filtro.getCodUnidade()));
			
		produto.setCoProduto(filtro.getCodProduto());
		contrato.setProduto(produto);
		contrato.setCoContrato(filtro.getFiltroContrato());
		contrato.setNuIdentificadorCliente(filtro.getNuIdentificadorClienteFiltro());
		contrato.setIcSituacaoContaContrato(filtro.getCodSituacao());
		contrato.setCoAvaliacaoSiric(filtro.getFiltroAvalSiric());
		contrato.setCoAvaliacaoSiricTomador(filtro.getFiltroAvalCliente());
		if (Utilities.notEmpty(filtro.getCodCanalComercializacao()))
			contrato.setNuCanalComercializacao(Short.parseShort(filtro.getCodCanalComercializacao()));
		
		contrato.setIcSituacaoContaContrato(filtro.getCodSituacao());

		if (Utilities.notEmpty(filtro.getFiltroOperacao()))
			contrato.setNuOperacao(Short.parseShort(filtro.getFiltroOperacao()));		
		
		contrato.setCoCliente(filtro.getFiltroCodCliente());
		contrato.setNoCliente(filtro.getFiltroNomeCliente());
		contrato.setIcPfPj(filtro.getIcPfPj());
		contrato.setIcTipoIndentificadorCliente(filtro.getIcTipoIndentificadorCliente());
		contrato.setNuIdentificadorCliente(filtro.getNuIdentificadorClienteFiltro());
		
		if (Utilities.notEmpty(filtro.getCodConveniado()))
			detalhes.setNuConveniado(filtro.getCodConveniado());
		
		detalhes.setNuIdentificadorEmpreendimento(filtro.getFiltroCodEmpreendimento());
		detalhes.setNoEmpreendimento(filtro.getFiltroNomeEmpreendimento());
		detalhes.setNoCampoLivre5(filtro.getFiltroCodPrimeiroCoObrigado());
		detalhes.setNuIdentificadorVendedor(filtro.getFiltroCodVendedor());
		detalhes.setNoVendedor(filtro.getFiltroNomeVendedor());
		detalhes.setNoCampoLivre7(filtro.getFiltroCodSegundoCoObrigado());
		detalhes.setNoCampoLivre4(filtro.getFiltroNomePrimeiroCoObrigado());
		detalhes.setNoCampoLivre6(filtro.getFiltroNomeSegundoCoObrigado());
		detalhes.setNoCampoLivre1(filtro.getFiltroNumeroDamp());
		if (Utilities.notEmpty(filtro.getDataRessarcimentoDamp()))
			detalhes.setNoCampoLivre2(new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).format(filtro.getDataRessarcimentoDamp()));
		
		if (Utilities.notEmpty(filtro.getValorRessarcimento()))
			detalhes.setNoCampoLivre3(filtro.getValorRessarcimento().toString());
		
		contrato.setDetalhesContrato(detalhes);
		
		contratoVO.setContrato(contrato);
		contratoVO.setProdutosPreferenciais(filtro.isProdutosPreferenciais());
		if (Utilities.notEmpty(filtro.getCodServicoVerificacao()))
			contratoVO.setNuServicoVerificacao(Integer.parseInt(filtro.getCodServicoVerificacao()));
		
		if (Utilities.notEmpty(filtro.getFiltroUsuarioResp()))
			contratoVO.setCoUsuario(filtro.getFiltroUsuarioResp());
		
		contratoVO.setDataInclusaoInicio(filtro.getFiltroDataIniInclusao());
		contratoVO.setDataInclusaoFim(filtro.getFiltroDataFimInclusao());
		contratoVO.setDataVerificacaoInicio(filtro.getFiltroDataIniVerificacao());
		contratoVO.setDataVerificacaoFim(filtro.getFiltroDataFimVerificacao());
		contratoVO.setValorInicialFiltro(filtro.getValorInicialFiltro());
		contratoVO.setValorFinalFiltro(filtro.getValorFinalFiltro());
		contratoVO.setIcSuspensa(filtro.isSomenteSuspensas());
		contratoVO.setIcSituacaoContrato(filtro.getIcSituacaoContrato());
		
		//Buscando códigos dos conveniados que possuem o nome digitado
		if (Utilities.notEmpty(filtro.getNomeConveniado())){
			List<Integer> conveniados = verificacaoPreventivaDAO.getListNuConveniadosByNome(filtro.getNomeConveniado());
			if (conveniados == null || conveniados.isEmpty())
			{
				List<Integer> vazia = new ArrayList<Integer>();
				vazia.add(0);
				contratoVO.setNuConveniados(vazia);
			} else {
				contratoVO.setNuConveniados(verificacaoPreventivaDAO.getListNuConveniadosByNome(filtro.getNomeConveniado()));			
			}
		}
		
		return this.filter(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}
	
	@Transactional
	public List<Contrato> advancedFilter(IUsuario usuarioLogado, Short perfilUsuario, FilterBase filtro, ParamPagination paramPagination) throws DAOException {
		ContratoVO contratoVO = fillContratoFilter(filtro);
		
		return this.filter(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}
	
	@Transactional
	@SuppressWarnings("rawtypes")
	public byte[] geraRelatorio(Map param, String tipoRelatorio, String caminhoRelatorio, String matricula, List verificacao) throws DAOException, ReportInvalidPathException, ReportErrorCreateDatasourceException, ReportFinalNullException, JRException {
		RelatorioVerificacao relatorioVerificacao = new RelatorioVerificacao();
		return relatorioVerificacao.geraRelatorio(param, tipoRelatorio, caminhoRelatorio, matricula, verificacao);
	}

	@Transactional
	public void delete(Contrato c) {
		
		//Excluir primeiramente os resultados de apontamentos
		resultadoApontamentoProdutoDAO.delete(c.getNuContrato());
		
		//Excluindo dados da tabela de verificação contrato
		verificacaoContratoDAO.delete(c.getNuContrato());
		
		//Excluindo dados da tabela de Detalhes Contrato
		detalhesContratoDAO.delete(c.getDetalhesContrato());
				
		verificacaoPreventivaDAO.delete(c);
	}
		
	@Transactional
	public boolean permitirExclusao(Integer nuContrato) {
		return verificacaoPreventivaDAO.permitirExclusao(nuContrato) && 
		(SegurancaUsuario.getInstance().verificaPerfil(Short.parseShort(Perfil.PERFIL_GESTOR_SISTEMA)) || 
				SegurancaUsuario.getInstance().verificaPerfil(Short.parseShort(Perfil.PERFIL_REGIONAL_CONFORMIDADE)));
	}
	
	@Transactional
	@Deprecated
	public int simpleFilterCount(IUsuario usuarioLogado, Short perfilUsuario, String pesquisaString, Boolean produtosPreferenciais, ParamPagination paramPagination) {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setSimpleFilter();
		contratoVO.setPesquisaSimples(pesquisaString);
		contratoVO.setProdutosPreferenciais(produtosPreferenciais);
		return this.filterCount(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}
	
	@Transactional
	public int simpleFilterCount(IUsuario usuarioLogado, Short perfilUsuario, FilterBase filtro, ParamPagination paramPagination) {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setSimpleFilter();
		contratoVO.setPesquisaSimples(filtro.getString("pesquisaString"));
		contratoVO.setProdutosPreferenciais(filtro.getBoolean("produtosPreferenciais"));
		return this.filterCount(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}
	
	private int filterCount(ContratoVO contratoVO, Short perfilUsuario, IUsuario usuarioLogado, ParamPagination paramPagination){
		
		List<String> produtos = new ArrayList<String>();		
		List<String> servicos = new ArrayList<String>();		
		
		//Variável que define se irá buscar produtos preferenciais ou não
		if (contratoVO.getProdutosPreferenciais())
		{	
			produtos = preferenciasUsuarioBO.listProdutoPreferencial(usuarioLogado.getMatricula());
			servicos = preferenciasUsuarioBO.listServicoPreferencial(usuarioLogado.getMatricula());
		}
		
		//Verificando se existe Produtos Vinculados
		Integer qtdProdutosUsuario = produtoUsuarioDAO.getCountProdutoUsuario(usuarioLogado.getMatricula());
		if (qtdProdutosUsuario == 0)
		{
			contratoVO.setExisteProdutosUsuario(false);
		} else {
			contratoVO.setExisteProdutosUsuario(true);
		}
		
		String matricula = usuarioLogado.getMatricula();
		Short nuUnidade = usuarioLogado.getUnidade();
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(perfilUsuario, nuUnidade);
		contratoVO.setUnidades(listUnidadesAbrangencia);
		Short nuPerfil = usuarioLogado.getPerfis().get(0);
		List<String> listProdutosAbrangentes = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, nuPerfil);
		return verificacaoPreventivaDAO.filtraCount(contratoVO, produtos, servicos, listProdutosAbrangentes, paramPagination);
	}
	
	@Transactional
	@Deprecated
	public int advancedFilterCount(IUsuario usuarioLogado, Short perfilUsuario, FiltroVerificacaoPreventivaVO filtro, ParamPagination paramPagination) {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setAdvancedFilter();
		
		Contrato contrato = new Contrato();
		Produto produto = new Produto();
		DetalhesContrato detalhes = new DetalhesContrato();
		
		if (Utilities.notEmpty(filtro.getCodUnidade()))
			contrato.setNuUnidade(Short.parseShort(filtro.getCodUnidade()));
			
		produto.setCoProduto(filtro.getCodProduto());
		contrato.setProduto(produto);
		contrato.setCoContrato(filtro.getFiltroContrato());
		contrato.setNuIdentificadorCliente(filtro.getNuIdentificadorClienteFiltro());
		contrato.setIcSituacaoContaContrato(filtro.getCodSituacao());
		contrato.setCoAvaliacaoSiric(filtro.getFiltroAvalSiric());
		contrato.setCoAvaliacaoSiricTomador(filtro.getFiltroAvalCliente());
		if (Utilities.notEmpty(filtro.getCodCanalComercializacao()))
			contrato.setNuCanalComercializacao(Short.parseShort(filtro.getCodCanalComercializacao()));
		
		contrato.setIcSituacaoContaContrato(filtro.getCodSituacao());

		if (Utilities.notEmpty(filtro.getFiltroOperacao()))
			contrato.setNuOperacao(Short.parseShort(filtro.getFiltroOperacao()));		
		
		contrato.setCoCliente(filtro.getFiltroCodCliente());
		contrato.setNoCliente(filtro.getFiltroNomeCliente());
		contrato.setIcPfPj(filtro.getIcPfPj());
		contrato.setIcTipoIndentificadorCliente(filtro.getIcTipoIndentificadorCliente());
		contrato.setNuIdentificadorCliente(filtro.getNuIdentificadorClienteFiltro());
		
		if (Utilities.notEmpty(filtro.getCodConveniado()))
			detalhes.setNuConveniado(filtro.getCodConveniado());
		
		detalhes.setNuIdentificadorEmpreendimento(filtro.getFiltroCodEmpreendimento());
		detalhes.setNoEmpreendimento(filtro.getFiltroNomeEmpreendimento());
		detalhes.setNoCampoLivre5(filtro.getFiltroCodPrimeiroCoObrigado());
		detalhes.setNuIdentificadorVendedor(filtro.getFiltroCodVendedor());
		detalhes.setNoVendedor(filtro.getFiltroNomeVendedor());
		detalhes.setNoCampoLivre7(filtro.getFiltroCodSegundoCoObrigado());
		detalhes.setNoCampoLivre4(filtro.getFiltroNomePrimeiroCoObrigado());
		detalhes.setNoCampoLivre6(filtro.getFiltroNomeSegundoCoObrigado());
		detalhes.setNoCampoLivre1(filtro.getFiltroNumeroDamp());
		if (Utilities.notEmpty(filtro.getDataRessarcimentoDamp()))
			detalhes.setNoCampoLivre2(new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).format(filtro.getDataRessarcimentoDamp()));
		
		if (Utilities.notEmpty(filtro.getValorRessarcimento()))
			detalhes.setNoCampoLivre3(filtro.getValorRessarcimento().toString());
		
		contrato.setDetalhesContrato(detalhes);
		
		contratoVO.setContrato(contrato);
		contratoVO.setProdutosPreferenciais(filtro.isProdutosPreferenciais());
		if (Utilities.notEmpty(filtro.getCodServicoVerificacao()))
			contratoVO.setNuServicoVerificacao(Integer.parseInt(filtro.getCodServicoVerificacao()));
		
		if (Utilities.notEmpty(filtro.getFiltroUsuarioResp()))
			contratoVO.setCoUsuario(filtro.getFiltroUsuarioResp());
		
		contratoVO.setDataInclusaoInicio(filtro.getFiltroDataIniInclusao());
		contratoVO.setDataInclusaoFim(filtro.getFiltroDataFimInclusao());
		contratoVO.setDataVerificacaoInicio(filtro.getFiltroDataIniVerificacao());
		contratoVO.setDataVerificacaoFim(filtro.getFiltroDataFimVerificacao());
		contratoVO.setValorInicialFiltro(filtro.getValorInicialFiltro());
		contratoVO.setValorFinalFiltro(filtro.getValorFinalFiltro());
		contratoVO.setIcSuspensa(false);
		contratoVO.setIcSituacaoContrato(filtro.getIcSituacaoContrato());
		
		//Buscando códigos dos conveniados que possuem o nome digitado
		if (Utilities.notEmpty(filtro.getNomeConveniado())){
			contratoVO.setNuConveniados(verificacaoPreventivaDAO.getListNuConveniadosByNome(filtro.getNomeConveniado()));
		}
		
		return this.filterCount(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}

	
	@Transactional
	public int advancedFilterCount(IUsuario usuarioLogado, Short perfilUsuario, FilterBase filtro, ParamPagination paramPagination) {
		ContratoVO contratoVO = fillContratoFilter(filtro);
		return this.filterCount(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}
	
	private ContratoVO fillContratoFilter(FilterBase filtro){
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setAdvancedFilter();
		
		Contrato contrato = new Contrato();
		Produto produto = new Produto();
		DetalhesContrato detalhes = new DetalhesContrato();
		
		if (Utilities.notEmpty(filtro.getString("codUnidade")))
			contrato.setNuUnidade(filtro.getShort("codUnidade"));
			
		produto.setCoProduto(filtro.getString("codProduto"));
		contrato.setProduto(produto);
		contrato.setCoContrato(filtro.getString("filtroContrato"));
		contrato.setNuIdentificadorCliente(filtro.getString("verificacaoPreventivaNuIdentificadorCliente"));
		contrato.setIcSituacaoContaContrato(filtro.getString("codSituacao"));
		contrato.setCoAvaliacaoSiric(filtro.getString("filtroAvalSiric"));
		contrato.setCoAvaliacaoSiricTomador(filtro.getString("filtroAvalCliente"));
		if (Utilities.notEmpty(filtro.getString("codCanalComercializacao")))
			contrato.setNuCanalComercializacao(filtro.getShort("codCanalComercializacao"));
		
		contrato.setIcSituacaoContaContrato(filtro.getString("codSituacao"));

		if (Utilities.notEmpty(filtro.getShort("filtroOperacao")))
			contrato.setNuOperacao(filtro.getShort("filtroOperacao"));		
		
		contrato.setCoCliente(filtro.getString("filtroCodCliente"));
		contrato.setNoCliente(filtro.getString("filtroNomeCliente"));
		contrato.setIcPfPj(filtro.getString("verificacaoPreventivaIcPfPj"));
		contrato.setIcTipoIndentificadorCliente(filtro.getString("verificacaoPreventivaIcTipoIndentificadorCliente"));
		contrato.setNuIdentificadorCliente(filtro.getString("verificacaoPreventivaNuIdentificadorCliente"));
		
		if (Utilities.notEmpty(filtro.getString("codConveniado")))
			detalhes.setNuConveniado(filtro.getInt("codConveniado"));
		
		detalhes.setNuIdentificadorEmpreendimento(filtro.getString("filtroCodEmpreendimento"));
		detalhes.setNoEmpreendimento(filtro.getString("filtroNomeEmpreendimento"));
		detalhes.setNoCampoLivre5(filtro.getString("filtroNomePrimeiroCoObrigado"));
		detalhes.setNuIdentificadorVendedor(filtro.getString("filtroCodVendedor"));
		detalhes.setNoVendedor(filtro.getString("filtroNomeVendedor"));
		detalhes.setNoCampoLivre7(filtro.getString("filtroCodSegundoCoObrigado"));
		detalhes.setNoCampoLivre4(filtro.getString("filtroNomePrimeiroCoObrigado"));
		detalhes.setNoCampoLivre6(filtro.getString("filtroNomeSegundoCoObrigado"));
		detalhes.setNoCampoLivre1(filtro.getString("filtroNumeroDamp"));
		if (Utilities.notEmpty(filtro.getString("dataRessarcimentoDamp")))
			detalhes.setNoCampoLivre2(new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).format(filtro.getShort("dataRessarcimentoDamp")));
		
		if (Utilities.notEmpty(filtro.getString("valorRessarcimento")))
			detalhes.setNoCampoLivre3(filtro.getString("valorRessarcimento").toString());
		
		contrato.setDetalhesContrato(detalhes);
		
		contratoVO.setContrato(contrato);
		contratoVO.setProdutosPreferenciais(filtro.getBoolean("produtosPreferenciais"));
		if (Utilities.notEmpty(filtro.getShort("codServicoVerificacao")))
			contratoVO.setNuServicoVerificacao(filtro.getInt("codServicoVerificacao"));
		
		if (Utilities.notEmpty(filtro.getString("filtroUsuarioResp")))
			contratoVO.setCoUsuario(filtro.getString("filtroUsuarioResp"));
		
		contratoVO.setDataInclusaoInicio(filtro.getDate("filtroDataIniInclusao"));
		contratoVO.setDataInclusaoFim(filtro.getDate("filtroDataFimInclusao"));
		contratoVO.setDataVerificacaoInicio(filtro.getDate("filtroDataIniVerificacao"));
		contratoVO.setDataVerificacaoFim(filtro.getDate("filtroDataFimVerificacao"));
		contratoVO.setValorInicialFiltro(filtro.getBigDecimal("valorInicialFiltro"));
		contratoVO.setValorFinalFiltro(filtro.getBigDecimal("valorFinalFiltro"));
		contratoVO.setIcSuspensa(false);
		contratoVO.setIcSituacaoContrato(filtro.getString("codSituacaoVerificacao"));
		
		//Buscando códigos dos conveniados que possuem o nome digitado
		if (Utilities.notEmpty(filtro.getString("filtroNomeConveniado"))){
			List<Integer> conveniados = verificacaoPreventivaDAO.getListNuConveniadosByNome(filtro.getString("filtroNomeConveniado"));
			if (conveniados == null || conveniados.isEmpty())
			{
				List<Integer> vazia = new ArrayList<Integer>();
				vazia.add(0);
				contratoVO.setNuConveniados(vazia);
			} else {
				contratoVO.setNuConveniados(verificacaoPreventivaDAO.getListNuConveniadosByNome(filtro.getString("filtroNomeConveniado")));			
			}
		}
		return contratoVO;
	}
	
	
}
