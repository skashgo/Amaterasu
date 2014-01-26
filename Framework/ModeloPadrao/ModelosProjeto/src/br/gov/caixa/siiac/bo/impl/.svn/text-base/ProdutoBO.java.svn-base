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

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IProdutoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.ProdutoVO;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.persistence.dao.IProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO;
import br.gov.caixa.siiac.util.FilterBase;
import br.gov.caixa.util.Utilities;

/**
 * @author GisConsult
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class ProdutoBO implements IProdutoBO {

	private transient IProdutoDAO produtoDAO;
	private transient IProdutoUsuarioDAO produtoUsuarioDAO;
	
	@Autowired
	public void setProdutoDAO(IProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}
	
	@Autowired
	public void setProdutoUsuarioDAO(IProdutoUsuarioDAO produtoUsuarioDAO) {
		this.produtoUsuarioDAO = produtoUsuarioDAO;
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IProdutoBO#findById(java.lang.Short)
	 */
	@Transactional
	public Produto findById(String codProduto) {
		return produtoDAO.findById(codProduto);
	}
	
	@Transactional
	public Produto findEagerById(String codProduto) {
		
		Produto produto=produtoDAO.findById(codProduto);
		produtoDAO.initialize(produto.getServicoVerificacaoProdutoList());
		return produto;
	}
	

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IProdutoBO#findByNome(java.lang.String)
	 */
	@Transactional
	public List<Produto> findByNome(String nome) {
		return produtoDAO.findByNome(nome);
	}
	
	@Transactional
	public List<Produto> getListProduto() {
		Criteria crit = produtoDAO.getCriteria();
		crit.addOrder(Order.asc("coProduto"));
		return produtoDAO.findByCriteria(crit);
	}
	
	@Transactional
	public List<Produto> getListProdutoByCategoria(Integer nuCategoria) {
		
		Criteria c = produtoDAO.getCriteria();
		
		c.add(Restrictions.eq("nuCategoriaProduto", nuCategoria));
		
		return produtoDAO.findByCriteria(c);
	}

	/**
	 * getPesquisaProduto
	 * 
	 * @param campoNome         => Pesquisar por Nome  
	 * @param mostrarInativos		=> Pesquisar por Situacao (ativa / inativa)
	 * @param campoOperacao		=> Pesquisar por Operacao
	 * @param campoModalidade	=> Pesquisar por Modalidade
	 * @param campoSiglaSistema => Pesquisar por Sigla Sistema
	 * @param campoTipoPessoa	=> Pesquisar por Tipo de Pessoa (Fisica / Juridica)
	 * @param campoValorLimite  => Pesquisar por Valor Limite
	 * 
	 * @return List <Produto>
	 * @throws SIIACException 	 */
	@Transactional
	@Deprecated
	public List<Produto> getPesquisaProduto(String	campoNome, 
											boolean mostrarInativos, 
											String campoOperacao,
											String campoModalidade,	
											String campoSiglaSistema, 
											BigDecimal campoValorLimite,
											String campoTipoPessoa, 
											String campoNuCategoriaProduto, 
			 								boolean fazConsulta) throws SIIACException {
		return produtoDAO.getPesquisaProduto(campoNome, mostrarInativos, campoOperacao, campoModalidade, campoSiglaSistema, campoValorLimite, campoTipoPessoa, campoNuCategoriaProduto, fazConsulta);
	}
	
	@Transactional
	public List<Produto> getPesquisaProduto(FilterBase filtro, boolean fazConsulta) throws SIIACException {
		return produtoDAO.getPesquisaProduto(filtro.getString("campoNomeProduto"), 
				filtro.getBoolean("pesquisaMostraInativos"), filtro.getString("campoOperacao"), 
				filtro.getString("campoModalidade"), filtro.getString("campoSiglaSistema"), 
				filtro.getBigDecimal("campoValorLimite"), filtro.getString("campoTipoPessoa"), 
				filtro.getString("campoCategoriaProduto"), fazConsulta);
	}

	/**
	 * gravarProduto
	 * @param	(String)coProduto 		-> codigo do Produto a ser gravado
	 * @param 	(String)noProduto 		-> Nome do Produto 
	 * @param 	(Short)nuModalidade 	-> Numero da Modalidade
	 * @param 	(Short)nuOperacao 		-> Numero da Opera��o
	 * @param 	(String)sgSistemaOrigem -> Sigla do Sistema de Origem
	 * @return	boolean					-> true salvou / false erro 
	 */
	@Transactional
	public boolean gravarProduto(String coProduto, 
								String noProduto, 
								short nuModalidade, 
								short nuOperacao, 
								String sgSistemaOrigem, 
 	 						    BigDecimal vrLimiteVerificacao, 
							    String icPfPj, 
							    int nuCategoriaProduto, 
								boolean icAtivo,
								boolean altera,
								String noOperacao,
								String noModalidade) {
		return produtoDAO.gravarProduto(coProduto, noProduto, nuModalidade, nuOperacao, sgSistemaOrigem, vrLimiteVerificacao, icPfPj, nuCategoriaProduto, icAtivo, altera, noOperacao, noModalidade);
	}

	@Transactional
	public void ativarDesativarProduto(String coProduto, boolean ativar) throws SIIACException {
		produtoDAO.ativarDesativarProduto(coProduto, ativar);
	}
	
	@Transactional
	public List<Produto> findByOperacao(Short operacao, List<String> produtosAbrangentes) {
		return produtoDAO.findByOperacao(operacao, produtosAbrangentes);
	}
	
	@Transactional
	public List<Produto> findByNomeAndOperacao(String nome, Short operacao) {
		return produtoDAO.findByNomeAndOperacao(nome, operacao);
	}

	@Transactional
	public List<Produto> getAllNaoPreferenciais(String matricula, Short nuPerfil){
		// Se esse valor for zero, significa que o usuário não possui nenhum produto na tabela de ProdutoUsuario, portanto possui todos os Produtos.
		Integer count = this.produtoUsuarioDAO.getCountProdutoUsuario(matricula);
		if(count > 0){
			return this.produtoDAO.getAllNaoPreferenciaisFromProdutoUsuario(matricula, nuPerfil);
		}else{
			return this.produtoDAO.getAllNaoPreferenciaisFromProduto(matricula, nuPerfil);
		}
	}

	@Transactional
	@Deprecated
	public List<Produto> getListProdutoFiltroSimples(String pesquisa, Boolean mostrarInativos) throws SIIACException {
		
		Criteria c = produtoDAO.getCriteria();
		
		if(Utilities.notEmpty(pesquisa)){
			
			Disjunction disjuction = Restrictions.disjunction();
			
			if(pesquisa != null && (pesquisa.length() == 8)){
				
				Pattern patternMask = Pattern.compile("[0-9]{4}[-]?[0-9]{3}");
				if(patternMask.matcher(pesquisa).matches()){
					disjuction.add(Restrictions.eq("coProduto", pesquisa.replace("-","")));
				}
			}
			disjuction.add(Restrictions.ilike("noProduto", pesquisa, MatchMode.ANYWHERE));
			disjuction.add(Restrictions.ilike("coProduto", pesquisa, MatchMode.ANYWHERE));
			
			try {
				
				disjuction.add(Restrictions.eq("nuOperacao", Short.parseShort(pesquisa)));
			} catch (Exception e)
			{
				e.getMessage();
			}
			
				
			
			c.add(disjuction);
		}
		c.addOrder(Order.asc("noProduto"));
		
		if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		return produtoDAO.findByCriteria(c);
	}
	
	@Transactional
	public List<Produto> getListProdutoFiltroSimples(FilterBase filtro) throws SIIACException {
		
		Criteria c = produtoDAO.getCriteria();
		
		if(Utilities.notEmpty(filtro.getString("pesquisaString"))){
			
			Disjunction disjuction = Restrictions.disjunction();
			
			if(filtro.getString("pesquisaString") != null && (filtro.getString("pesquisaString").length() == 8)){
				
				Pattern patternMask = Pattern.compile("[0-9]{4}[-]?[0-9]{3}");
				if(patternMask.matcher(filtro.getString("pesquisaString")).matches()){
					disjuction.add(Restrictions.eq("coProduto", filtro.getString("pesquisaString").replace("-","")));
				}
			}
			disjuction.add(Restrictions.ilike("noProduto", filtro.getString("pesquisaString"), 
					MatchMode.ANYWHERE));
			
			disjuction.add(Restrictions.ilike("coProduto", filtro.getString("pesquisaString"), 
					MatchMode.ANYWHERE));
			
			try {
				
				disjuction.add(Restrictions.eq("nuOperacao", Short.parseShort(filtro.getString("pesquisaString"))));
			} catch (Exception e)
			{
				e.getMessage();
			}
			
				
			
			c.add(disjuction);
		}
		c.addOrder(Order.asc("noProduto"));
		
		if (Utilities.notEmpty(filtro.getString("pesquisaMostraInativos")) 
				&& filtro.getString("pesquisaMostraInativos").equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		return produtoDAO.findByCriteria(c);
	}
	
	@Transactional
	public ProdutoVO getProdutoSIICO(String nuOperacao) {
		return produtoDAO.getProdutoSIICO(nuOperacao);
	}
	
	@Transactional
	public boolean existeProdutoSIICO(String nuOperacao)
	{
		return produtoDAO.existeProdutoSIICO(nuOperacao);
	}
	
	/**
	 * Método que verifica se o produto que se deseja cadastrar está ativo ou
	 * inativo na base.
	 * Caso exista na base, irá retornar true, caso não exista, irá retornar false
	 * @param noProduto
	 * @param coProduto
	 * @param ativo
	 * @return
	 */
	@Transactional
	public boolean verificaProdutoAtivoInativo(String coProduto, boolean ativo)
	{
		Criteria c = produtoDAO.getCriteria();
		c.add(Restrictions.ilike("coProduto", coProduto));
		c.add(Restrictions.eq("icAtivo", ativo));
				
		return !c.list().isEmpty();
	}
	
	/**
	 * Método que verifica se o nome do produto já exista na base
	 * @param noProduto
	 * @return
	 */
	@Transactional
	public boolean verificaNomeProduto(String noProduto)
	{
		Criteria c = produtoDAO.getCriteria();
		c.add(Restrictions.ilike("noProduto", noProduto));		
		
		return !c.list().isEmpty();
	}

	@Transactional
	public List<Produto> getProdutosByListCoProduto(List<String> produtoAbrangentes) {
		return produtoDAO.getProdutosByListCoProduto(produtoAbrangentes);
	}
}
