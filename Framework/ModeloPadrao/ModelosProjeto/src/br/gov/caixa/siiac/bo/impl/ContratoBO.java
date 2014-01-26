package br.gov.caixa.siiac.bo.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IContratoBO;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.ContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.DetalhesContrato;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.TrilhaHistorico;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.persistence.dao.IContratoDAO;
import br.gov.caixa.siiac.persistence.dao.IDetalhesContratoDAO;
import br.gov.caixa.siiac.persistence.dao.IProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO;
import br.gov.caixa.siiac.persistence.dao.ITrilhaHistoricoDAO;
import br.gov.caixa.siiac.persistence.dao.IUnidadeDAO;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.util.FilterBase;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.siiac.util.Valida;
import br.gov.caixa.siiac.view.mb.ContratoMB;
import br.gov.caixa.siiac.view.mb.pagination.ParamPagination;

@Service
@Scope("prototype")
public class ContratoBO implements IContratoBO {
	
	/**
	 * O método verificaCamposAlterados verifica os campos que podem ser alterados
	 * na classe Contrato. Somente os campos que não são readOnly podem sofrer alteração.
	 */
	
	private static final String MASCARA_DATA = "dd/MM/yyyy";
	private IContratoDAO contratoDAO;
	private IDetalhesContratoDAO detalhesContratoDAO;
	private IProdutoUsuarioDAO produtoUsuarioDAO;
	private IUnidadeDAO unidadeDAO;
	private IUnidadeBO unidadeBO;
	private IProdutoDAO produtoDAO;
	private ITrilhaHistoricoDAO trilhaHistoricoDAO;
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	
	@Autowired
	public void setContratoDAO(IContratoDAO contratoDAO) {
		this.contratoDAO = contratoDAO;
	}
	
	@Autowired
	public void setDetalhesContratoDAO(IDetalhesContratoDAO detalhesContratoDAO) {
		this.detalhesContratoDAO = detalhesContratoDAO;
	}
	
	@Autowired
	public void setProdutoUsuarioDAO(IProdutoUsuarioDAO produtoUsuarioDAO) {
		this.produtoUsuarioDAO = produtoUsuarioDAO;
	}
	
	@Autowired
	public void setUnidadeBO(IUnidadeBO unidadeBO) {
		this.unidadeBO = unidadeBO;
	}
	
	@Autowired
	public void setUnidadeDAO(IUnidadeDAO unidadeDAO) {
		this.unidadeDAO = unidadeDAO;
	}
	
	@Autowired
	public void setProdutoDAO(IProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}

	@Autowired
	public void setTrilhaHistoricoDAO(ITrilhaHistoricoDAO trilhaHistoricoDAO) {
		this.trilhaHistoricoDAO = trilhaHistoricoDAO;
	}
	
	@Autowired
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}

	@Transactional
	public Contrato save(Contrato contrato) {
		Unidade unidade = unidadeDAO.findByNuUnidade(contrato.getNuUnidade());
		if(unidade != null) {
			contrato.setNuUnidade(unidade.getId().getNuUnidade());
			contrato.setNuNatural(unidade.getId().getNuNatural());
		}
		contrato.setNuOperacao(contrato.getProduto().getNuOperacao());
		contrato.setNuModalidade(contrato.getProduto().getNuModalidade());
		if (contrato.getDetalhesContrato() != null) {
			if (contrato.getDetalhesContrato().getDtVencimentoContrato() != null || contrato.getDetalhesContrato().getVrLiquidoContrato() != null || contrato.getDetalhesContrato().getDtUltimaRenovacao() != null || contrato.getDetalhesContrato().getDtLiquidacaoContrato() != null || contrato.getDetalhesContrato().getDtInadimplenciaContrato() != null) {
				
				DetalhesContrato dc = new DetalhesContrato(contrato.getDetalhesContrato().getDtVencimentoContrato(), 
						contrato.getDetalhesContrato().getVrLiquidoContrato(), 
						contrato.getDetalhesContrato().getDtUltimaRenovacao(), 
						contrato.getDetalhesContrato().getDtLiquidacaoContrato(), 
						contrato.getDetalhesContrato().getDtInadimplenciaContrato());
				dc.setNuIdentificadorVendedor(contrato.getDetalhesContrato().getNuIdentificadorVendedor());
				dc.setNuIdentificadorEmpreendimento(contrato.getDetalhesContrato().getNuIdentificadorEmpreendimento());
				dc.setNoEmpreendimento(contrato.getDetalhesContrato().getNoEmpreendimento());
				dc.setNoVendedor(contrato.getDetalhesContrato().getNoVendedor());
				dc.setNuConveniado(contrato.getDetalhesContrato().getNuConveniado());
				contrato.setDetalhesContrato(null);
				contrato = contratoDAO.merge(contrato);
				dc.setNuContrato(contrato.getNuContrato());
				detalhesContratoDAO.merge(dc);
				return contrato;
			} else {
				contrato.setDetalhesContrato(null);
				return contratoDAO.merge(contrato);
			}
		} else {
			return contratoDAO.merge(contrato);
		}
	}

	@Transactional
	public Contrato findById(Integer nuContrato) throws DAOException {
		return contratoDAO.findById(nuContrato);
	}
	
	@Transactional
	public boolean validaContrato(Contrato contrato) {
		
		// Validando Unidade
		if (contrato.getNuUnidade() == null) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Unidade é obrigatório.";
			return false;
		}
		
		if(!validaUnidade(contrato.getNuUnidade())) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Unidade digitada é inválida ou não permitida para este usuário.";
			return false;
		}
		
		// Validando Produto
		if (contrato.getProduto().getCoProduto() == null || contrato.getProduto().getCoProduto().equals("")) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Produto é obrigatório.";
			return false;
		}else{
			if(getLikeProduto(contrato.getProduto().getCoProduto()).isEmpty()){
				ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Produto não é válido.";
				return false;
			}
		}
		
		//Validando coContrato
		if(contrato.getCoContrato() == null || contrato.getCoContrato().trim().equals("")) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Contrato é obrigatório.";
			return false;
		} 
		
		//Validando Valor
		if(contrato.getDetalhesContrato() != null) {
			if(contrato.getDetalhesContrato().getVrLiquidoContrato() == null || 
					contrato.getDetalhesContrato().getVrLiquidoContrato().equals(BigDecimal.ZERO)) {
				ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Valor é obrigatório.";
				return false;
			}
		}
		
		//Validando Valor Nominal
		if(contrato.getVrNominalContrato() == null || contrato.getVrNominalContrato().equals(BigDecimal.ZERO)) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Valor Nominal é obrigatório.";
			return false;
		}
		
		// Validando regra VrLiquidoContrato
		if (contrato.getVrNominalContrato().compareTo(contrato.getDetalhesContrato().getVrLiquidoContrato()) < 0) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Valor deve ser menor que Valor Nominal.";
			return false;
		}
		
		//Validando contrato duplicado
		if(contrato.getNuContrato() == null && contrato.getCoContrato() != null) {
			try {
				List<Contrato> contratosDoBanco = contratoDAO.findByCoContrato(contrato.getCoContrato());
				if(!contratosDoBanco.isEmpty()) {
					ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Item já cadastrado. Consulte todos os itens - ativos e inativos.";
					return false;
				}
			} catch(DAOException ex) {
				LogCEF.error(ex);
			}
		}
	
		// Validando Situacao
		if (contrato.getIcSituacaoContaContrato() == null || contrato.getIcSituacaoContaContrato().equals("")) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Situação é obrigatório.";
			return false;
		}
		
		// Validando DataContrato
		if (contrato.getDtContrato() == null) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Data Contrato é obrigatório.";
			return false;
		}	
		
		// Validando TipoPessoa e TipoIdentificador
		if (contrato.getIcPfPj() == null || contrato.getIcPfPj().equals("")) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo PF/PJ é obrigatório.";
			return false;
		} else {
			if (contrato.getIcPfPj().equals(Contrato.TIPO_PESSOA_FISICA_ID)) {
				if (contrato.getIcTipoIndentificadorCliente() == null || contrato.getIcTipoIndentificadorCliente().equals("")) {
					ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Tipo Identificador é obrigatório.";
					return false;
				}
			}
		}
		
		// Validando NuIdentificadorCliente
		if (contrato.getNuIdentificadorCliente() == null || contrato.getNuIdentificadorCliente().toString().equals("")) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Identificação Cliente é obrigatório.";
			return false;
		}else{
			if(contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CNPJ_ID)){
				if(!Valida.isValidCNPJ(contrato.getNuIdentificadorCliente())){
					ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "CNPJ Inválido!";
					return false;
				}
			}else{
				if(contrato.getIcTipoIndentificadorCliente().equals(Contrato.TIPO_IDENTIFICADOR_CPF_ID)){
					if(!Valida.isValidCPF(contrato.getNuIdentificadorCliente())){
						ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "CPF Inválido!";
						return false;
					}
				}
			}
		}

		// Validando regra dtContrato
		if(contrato.getNuContrato() == null) {
			if (contrato.getDtContrato().compareTo(dataAtualDoSistema()) > 0) {
				ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Data Contrato deve ser menor ou igual a data atual do sistema.";
				return false;
			}
		}
		
		// Validando regra dtVencimento -- Validação só é feita se Data Vencimento não for null pois este deixou de ser obrigatório em 23/03/2012 
		if (contrato.getDetalhesContrato().getDtVencimentoContrato() != null
				&& contrato.getDetalhesContrato().getDtVencimentoContrato().compareTo(contrato.getDtContrato()) <= 0) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Data Vencimento deve ser maior que Data Contrato.";
			return false;
		}
		
		// Validando regra DtLiquidacao
		if (contrato.getDetalhesContrato().getDtLiquidacaoContrato() != null) {
			if (contrato.getDetalhesContrato().getDtLiquidacaoContrato().compareTo(contrato.getDtContrato()) <= 0) {
				ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Data Liquidação deve ser maior que Data Contrato.";
				return false;
			}
		}
		
		// Validando regra DtUltimaRenovacao
		if (contrato.getDetalhesContrato().getDtUltimaRenovacao() != null) {
			if (contrato.getNuContrato() != null) {
				Contrato contratoAnterior = contratoDAO.findById(contrato.getNuContrato());
				Date dtVencimentoAnterior = contratoAnterior.getDetalhesContrato().getDtVencimentoContrato();
				if (dtVencimentoAnterior != null) {
					if (contrato.getDetalhesContrato().getDtUltimaRenovacao().compareTo(dtVencimentoAnterior) < 0) {
						ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Data Renovação deve ser maior que Data Vencimento anterior.";
						return false;
					}
				}
			} else {
				// Validação só é feita se Data Vencimento não for null pois este deixou de ser obrigatório em 23/03/2012 
				if (contrato.getDetalhesContrato().getDtVencimentoContrato() != null
					&& contrato.getDetalhesContrato().getDtVencimentoContrato().compareTo(contrato.getDetalhesContrato().getDtUltimaRenovacao()) > 0) {
					ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Data Vencimento deve ser menor que Data Renovação.";
					return false;
				}
			}
		}
		
		// Validando regra1 DtInadimplencia
		if (contrato.getDetalhesContrato().getDtInadimplenciaContrato() != null) {
			if (contrato.getDetalhesContrato().getDtInadimplenciaContrato().compareTo(contrato.getDtContrato()) <= 0) {
				ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Data Inadimplência deve ser maior que Data Contrato.";
				return false;
			}
		}
		
		// Validando regra2 DtInadimplencia
		if (contrato.getDetalhesContrato().getDtInadimplenciaContrato() != null) {
			// Validação só é feita se Data Vencimento não for null pois este deixou de ser obrigatório em 23/03/2012 
			if (contrato.getDetalhesContrato().getDtVencimentoContrato() != null
				&& contrato.getDetalhesContrato().getDtInadimplenciaContrato().compareTo(contrato.getDetalhesContrato().getDtVencimentoContrato()) > 0) {
				ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Data Inadimplência deve ser menor ou igual a Data Vencimento.";
				return false;
			}
		}
		
		if(contrato.getDetalhesContrato().getNuConveniado() != null) {
			if(!detalhesContratoDAO.existeNuConveniado(contrato.getDetalhesContrato().getNuConveniado())) {
				ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Código do Conveniado não existe.";
				return false;
			}
		}
		
		if(contrato.getNuCanalComercializacao() == null || contrato.getNuCanalComercializacao().toString().equals("")) {
			ContratoMB.MENSAGEM_CAMPO_VALIDACAO = "Campo Tipo Canal de Comercialização é obrigatório.";
			return false;
		}
		return true;
	}
	
	/**
	 * @param nuUnidade
	 * @return
	 */
	private boolean validaUnidade(Short nuUnidade) {
		boolean isValida = false;
		List<Unidade> unidadesValidas = unidadeBO.getLikeNuUnidade("");
		for(Unidade un : unidadesValidas) {
			if(un.getId().getNuUnidade().equals(Short.valueOf(nuUnidade))) {
				isValida = true;
			}
		}
		return isValida;
	}

	public Date dataAtualDoSistema() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataAtual = new Date();
		try {
			dataAtual = sdf.parse(sdf.format(dataAtual));
		} catch (ParseException ex) {
			LogCEF.error(ex);
		}
		
		return dataAtual;
	}
	
	@Transactional
	public List<Contrato> getLikeCoContrato(String coContrato, Short nuUnidade) {
		String matricula =SegurancaUsuario.getInstance().getUsuario().getMatricula();
		Short nuPerfil = SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
		List<String> listCoProdutoAbrangencia = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, nuPerfil);
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(nuPerfil, nuUnidade);
		return contratoDAO.getLikeCoContrato(coContrato, listUnidadesAbrangencia, listCoProdutoAbrangencia);
	}
	
	@Transactional
	public List<Contrato> getListFiltro(Contrato contrato, IUsuario usuarioLogado, Short perfilUsuario, ParamPagination paramPagination, BigDecimal... faixa) throws DAOException {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setAdvancedFilter();
		contratoVO.setContrato(contrato);
		contratoVO.addAllFaixa(faixa);
		
		String matricula =SegurancaUsuario.getInstance().getUsuario().getMatricula();
		Short nuPerfil = SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
		Short nuUnidade = SegurancaUsuario.getInstance().getUsuario().getUnidade();
		List<String> listCoProdutoAbrangencia = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, nuPerfil);
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(nuPerfil, nuUnidade);
		contratoVO.setUnidades(listUnidadesAbrangencia);
		
		return contratoDAO.filtra(contratoVO, listCoProdutoAbrangencia, paramPagination);
	}
	
	@Transactional
	public List<Produto> getLikeProduto(String coProduto) {
		IUsuario usuarioLogado = SegurancaUsuario.getInstance().getUsuario();
		List<Produto> produtos = new ArrayList<Produto>();
		
		List<String> produtoAbrangentes = matrizAbrangenciaBO.getListAbrangenciaProduto(usuarioLogado.getMatricula(), usuarioLogado.getPerfis().get(0));
	
		produtos = produtoDAO.getProdutosAtivosOrdenadosByCodLikeProduto(coProduto, produtoAbrangentes);
		return produtos;
	}
	
	@Transactional
	public List<Produto> getLikeProdutoByCategoria(String coProduto, Integer nuCategoria) {
		IUsuario usuarioLogado = SegurancaUsuario.getInstance().getUsuario();
		List<Produto> produtos = new ArrayList<Produto>();
		
		List<String> produtoAbrangentes = matrizAbrangenciaBO.getListAbrangenciaProduto(usuarioLogado.getMatricula(), usuarioLogado.getPerfis().get(0));
		produtos = produtoDAO.getProdutosAtivosOrdenadosByCodLikeProdutoByCategoria(coProduto, nuCategoria, produtoAbrangentes);
		
		return produtos;
	}

	@Transactional
	public List<Contrato> simpleFilter(IUsuario usuarioLogado, Short perfilUsuario, String pesquisa, Boolean mostrarInativos, ParamPagination paramPagination) throws DAOException {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setSimpleFilter();
		contratoVO.setPesquisaSimples(pesquisa);
		contratoVO.setMostraInativos(mostrarInativos);
		return this.filter(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}

	@Transactional
	@Deprecated
	public List<Contrato> advancedFilter(Contrato contrato, Short campoOperacao, IUsuario usuarioLogado, Short perfilUsuario, Boolean mostrarInativos, ParamPagination paramPagination, BigDecimal... faixa) throws DAOException {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setAdvancedFilter();
		contratoVO.setContrato(contrato);
		contratoVO.addAllFaixa(faixa);
		contratoVO.setCampoOperacao(campoOperacao);
		contratoVO.setMostraInativos(mostrarInativos);
		
		return this.filter(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}
	
	@Transactional
	public List<Contrato> advancedFilter(FilterBase filtro, IUsuario usuarioLogado, Short perfilUsuario,  ParamPagination paramPagination) throws DAOException {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setAdvancedFilter();
		contratoVO.setContrato(filtro.getContrato("contrato"));
		if(contratoVO.getContrato()==null)contratoVO.setContrato(new Contrato());
		contratoVO.addAllFaixa(filtro.getBigDecimal("valorInicial"), 
				filtro.getBigDecimal("valorFinal") );
		contratoVO.setCampoOperacao(filtro.getShort("campoOperacao"));
		contratoVO.setMostraInativos(filtro.getBoolean("mostratInativos"));
		
		return this.filter(contratoVO, perfilUsuario, usuarioLogado, paramPagination);
	}
	
	private List<Contrato> filter(ContratoVO contratoVO, Short perfilUsuario, IUsuario usuarioLogado, ParamPagination paramPagination) throws DAOException{
		
		String matricula = usuarioLogado.getMatricula();
		Short nuUnidade = usuarioLogado.getUnidade();
		List<String> listCoProdutoAbrangencia = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, perfilUsuario);
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(perfilUsuario, nuUnidade);
		contratoVO.setUnidades(listUnidadesAbrangencia);
		return contratoDAO.filtra(contratoVO, listCoProdutoAbrangencia, paramPagination);
	}
	
	public List<Contrato> findByUnidade(Short nuUnidade) {
		try {
			return contratoDAO.findByUnidade(nuUnidade);
		} catch(DAOException ex) {
			LogCEF.error(ex);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IContratoBO#verificaCamposAlteradosAgenda(br.gov.caixa.siiac.model.domain.Contrato, br.gov.caixa.siiac.model.domain.Contrato)
	 */
	public Character verificaCamposAlteradosAgenda(Contrato contratoAntigo,
			Contrato novoContrato) {
		/*
		 * var bCodigoClienteAlterado = (codigoClienteAntigo <> codigoCliente)
		 * var bTipoPessoaAlterado = (tipoPessoaAntigo <> tipoPessoa) var
		 * bModalidadeAlterada = (modalidadeAntiga <> modalidade) var
		 * bCanalAlterado = (canalAntigo <> canal)
		 * 
		 * return AGENDA_GERADA_ID_DADOS_ALTERADOS
		 * 
		 * var bSituacaoAlterada = (situacaoAntiga <> situacao)
		 * 
		 * return AGENDA_GERADA_ID_SITUACAO_ALTERADA
		 * 
		 * AMBOS
		 * 
		 * return AGENDA_GERADA_ID_DADOS_SITUACAO_ALTERADA
		 */
		
		Character id;
		
		boolean dadosAlterados = false;
		boolean situacaoAlterada = false;
		
		
		if (contratoAntigo.getCoCliente() != null && novoContrato.getCoCliente() != null)
			if (contratoAntigo.getCoCliente().compareTo(novoContrato.getCoCliente()) != 0)
				dadosAlterados = true;
		
		if (contratoAntigo.getIcTipoIndentificadorCliente() != null && novoContrato.getIcTipoIndentificadorCliente() != null)
			if (contratoAntigo.getIcTipoIndentificadorCliente().compareTo(novoContrato.getIcTipoIndentificadorCliente()) != 0)
				dadosAlterados = true;
		
		if (contratoAntigo.getNuCanalComercializacao() != null && novoContrato.getNuCanalComercializacao() != null)
			if (contratoAntigo.getNuCanalComercializacao().compareTo(novoContrato.getNuCanalComercializacao()) != 0)
				dadosAlterados = true;
		
		if (contratoAntigo.getNuModalidade() != null && novoContrato.getNuModalidade() != null)
			if (contratoAntigo.getNuModalidade().compareTo(novoContrato.getNuModalidade()) != 0)
				dadosAlterados = true;
		
		
		if (!contratoAntigo.getIcSituacaoContaContrato().equalsIgnoreCase(novoContrato.getIcSituacaoContaContrato()))
			situacaoAlterada = true;
		
		
		if (dadosAlterados && !situacaoAlterada)
			id = Contrato.AGENDA_GERADA_ID_DADOS_ALTERADOS;
		else if (!dadosAlterados && situacaoAlterada)
			id = Contrato.AGENDA_GERADA_ID_SITUACAO_ALTERADA;
		else if (dadosAlterados && situacaoAlterada)
			id = Contrato.AGENDA_GERADA_ID_DADOS_SITUACAO_ALTERADA;
		else
			id = novoContrato.getIcAgendaGerada();
		
		return id;
	}

	@Transactional
	public void inicializaLista(Contrato contrato)
	{
		contratoDAO.refresh(contrato);
		Hibernate.initialize(contrato.getGarantias());
	}
	
	@Transactional
	public String getQuantidadeVerificacoesAvGestaoSuretCategoriaProduto(Integer categoria, Date data)
	{
		return contratoDAO.getQuantidadeVerificacoesAvGestaoSuretCategoriaProduto(categoria, data);
	}
	
	@Transactional
	public String getQuantidadeVerificacoesVencendoCategoriaProduto(Integer categoria)
	{
		return contratoDAO.getQuantidadeVerificacoesVencendoCategoriaProduto(categoria);
	}
	
	@Transactional
	public int simpleFilterCount(IUsuario usuarioLogado, Short perfilUserLogado, String pesquisaString, Boolean pesquisaMostraInativos, ParamPagination paramPagination) {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setSimpleFilter();
		contratoVO.setPesquisaSimples(pesquisaString);
		contratoVO.setMostraInativos(pesquisaMostraInativos);
		String matricula = usuarioLogado.getMatricula();
		Short nuUnidade = usuarioLogado.getUnidade();
		List<String> listCoProdutoAbrangencia = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, perfilUserLogado);
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(perfilUserLogado, nuUnidade);
		contratoVO.setUnidades(listUnidadesAbrangencia);
		return contratoDAO.simpleFilterCount(contratoVO, listCoProdutoAbrangencia, paramPagination);
	}
	
	@Transactional
	@Deprecated
	public int advancedFilterCount(Contrato contrato, Short campoOperacao, IUsuario usuarioLogado, Short perfilUsuario, Boolean mostrarInativos, ParamPagination paramPagination, BigDecimal... faixaValorNominal) {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setAdvancedFilter();
		contratoVO.setContrato(contrato);
		contratoVO.addAllFaixa(faixaValorNominal);
		contratoVO.setCampoOperacao(campoOperacao);
		contratoVO.setMostraInativos(mostrarInativos);
		String matricula = usuarioLogado.getMatricula();
		Short nuUnidade = usuarioLogado.getUnidade();
		List<String> listCoProdutoAbrangencia = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, perfilUsuario);
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(perfilUsuario, nuUnidade);
		contratoVO.setUnidades(listUnidadesAbrangencia);
		return contratoDAO.advancedFilterCount(contratoVO, listCoProdutoAbrangencia, paramPagination);
	}
	
	@Transactional
	public int advancedFilterCount(FilterBase filtro, IUsuario usuarioLogado, Short perfilUsuario,  ParamPagination paramPagination) {
		ContratoVO contratoVO = new ContratoVO();
		contratoVO.setAdvancedFilter();
		
		contratoVO.setContrato(filtro.getContrato("contrato"));
		if(contratoVO.getContrato()==null)contratoVO.setContrato(new Contrato());
		contratoVO.addAllFaixa(filtro.getBigDecimal("valorInicial"), 
				filtro.getBigDecimal("valorFinal") );
		contratoVO.setCampoOperacao(filtro.getShort("campoOperacao"));
		contratoVO.setMostraInativos(filtro.getBoolean("mostratInativos"));
		
		String matricula = usuarioLogado.getMatricula();
		Short nuUnidade = usuarioLogado.getUnidade();
		
		List<String> listCoProdutoAbrangencia = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, perfilUsuario);
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(perfilUsuario, nuUnidade);
		
		contratoVO.setUnidades(listUnidadesAbrangencia);
		return contratoDAO.advancedFilterCount(contratoVO, listCoProdutoAbrangencia, paramPagination);
	}
}