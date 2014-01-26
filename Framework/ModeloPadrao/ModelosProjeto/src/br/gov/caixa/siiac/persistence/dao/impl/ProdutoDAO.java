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
package br.gov.caixa.siiac.persistence.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.ProdutoVO;
import br.gov.caixa.siiac.model.domain.CategoriaProduto;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuario;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.persistence.dao.IProdutoDAO;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

/**
 * @author GisConsult
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class ProdutoDAO extends GenericDAO<Produto> implements IProdutoDAO {
	private static final Log LOG = LogFactory.getLog(ProdutoDAO.class);
	private static final String ERRO_GRAVAR_PRODUTO = "Erro ao gravar o produto: %s";
	@Autowired
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	/**
	 * @param persistenceClass
	 */
	public ProdutoDAO() {
		super(Produto.class);
	}

	/**
	 * getNovoUsuario
	 * @return
	 */
	private Produto getNovoProduto() {
		return new Produto();
	}
	
	/**
	 * getPesquisaProduto
	 * 
	 * @param campoNome         => Pesquisar por Nome  
	 * @param mostrarInativos		=> Pesquisar por Situacao (ativa / inativa)
	 * @param campoOperacao		=> Pesquisar por Opera��o
	 * @param campoModalidade	=> Pesquisar por Modalidade
	 * @param campoSiglaSistema => Pesquisar por Sigla Sistema
	 * @param campoTipoPessoa	=> Pesquisar por Tipo de Pessoa (Fisica / Juridica)
	 * @param campoValorLimite  => Pesquisar por Valor Limite
	 * 
	 * @return List <Produto>
	 */
	@SuppressWarnings("unchecked")
	public List<Produto> getPesquisaProduto(String campoNome, 
											Boolean mostrarInativos, 
											String campoOperacao, 
											String campoModalidade, 
											String campoSiglaSistema, 
											BigDecimal campoValorLimite,
											String campoTipoPessoa, 
											String campoNuCategoriaProduto, 
											boolean fazConsulta) throws SIIACException {
		Criteria cri = getCriteria();
		
		if (fazConsulta) {
			if (Utilities.notEmpty(campoNome)) {
				cri.add(Restrictions.ilike("noProduto", campoNome, MatchMode.ANYWHERE ));
			}

			if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
				cri.add(Restrictions.eq("icAtivo", Boolean.FALSE));
			}else{
				cri.add(Restrictions.eq("icAtivo", Boolean.TRUE));
			}

			if (Utilities.notEmpty(campoOperacao)) {
				if (!campoOperacao.equals("0000")) {
					cri.add(Restrictions.eq("nuOperacao", Short.parseShort(campoOperacao)));
				}
			}

			if (Utilities.notEmpty(campoModalidade)) {
				if (!campoModalidade.equals("000")) {
					cri.add(Restrictions.eq("nuModalidade", Short.parseShort(campoModalidade)));
				}
			}
			
			if (Utilities.notEmpty(campoSiglaSistema)) {
				cri.add(Restrictions.ilike("sgSistemaOrigem", campoSiglaSistema, MatchMode.ANYWHERE));
			}

			if (Utilities.notEmpty(campoTipoPessoa)) {
				if (!campoTipoPessoa.equals("0")) {
					cri.add(Restrictions.eq("icPfPj", campoTipoPessoa));
				}
			}
			
			if (Utilities.notEmpty(campoValorLimite)) {
				cri.add(Restrictions.eq("vrLimiteVerificacao", campoValorLimite));
			}

			if (Utilities.notEmpty(campoNuCategoriaProduto)) {
				if (!campoNuCategoriaProduto.equals("0")) {
					cri.createAlias("categoriaProduto", "cp");
					cri.add(Restrictions.eq("cp.nuCategoriaProduto", Integer.parseInt(campoNuCategoriaProduto)));
				}
			}
		} else { 
			cri.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}

		cri.addOrder(Order.asc("noProduto"));
		
		return cri.list();
	}

	/**
	 * gravarProduto
	 * @param	(String)coProduto 		-> codigo do Produto a ser gravado
	 * @param 	(String)noProduto 		-> Nome do Produto 
	 * @param 	(Short)nuModalidade 	-> Numero da Modalidade
	 * @param 	(Short)nuOperacao 		-> Numero da Operação
	 * @param 	(String)sgSistemaOrigem -> Sigla do Sistema de Origem
	 * @return	boolean					-> true salvou / false erro 
	 */
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
		boolean resultado = false;
		
		try { 
		    Produto pro = new Produto();
		    pro.setCoProduto(coProduto);
			pro.setNoProduto(noProduto);
			pro.setNoOperacao(noOperacao);
			pro.setNoModalidade(noModalidade);
			
		    //if (altera == false) {
				pro.setIcPfPj(icPfPj);
				pro.setNuModalidade(nuModalidade);
				pro.setNuOperacao(nuOperacao);
				pro.setIcAtivo(true);
		    //}
		    
			pro.setSgSistemaOrigem(sgSistemaOrigem);
			pro.setVrLimiteVerificacao(vrLimiteVerificacao);
			pro.setCategoriaProduto(new CategoriaProduto());
			pro.getCategoriaProduto().setNuCategoriaProduto(nuCategoriaProduto);
			
	    	merge(pro);
			resultado = true;
		} catch (Exception e) {
			LogCEF.error(String.format(ERRO_GRAVAR_PRODUTO, e.getMessage()));
		}

		return resultado;
	}
	
	/**
	 * ativarDesativarProduto
	 * 
	 * @param coProduto - codigo do Produto
	 * @param ativar - true = para ativar o usuario / false = desativar
	 */
	public void ativarDesativarProduto(String coProduto, boolean ativar) throws SIIACException {
		try {
			Produto p = findById(coProduto);
			p.setIcAtivo(ativar);
			merge(p);
		}catch (Exception e) {
			if (ativar == true) {
				throw new SIIACException("Erro em ativar: " + e.getMessage());
			} else {	
				throw new SIIACException("Erro em desativar: " + e.getMessage());
			}
		}
	}

	public List<Produto> findByNome(String nome) {
		Criteria c = getCriteria();
		c.add(Restrictions.ilike("noProduto", nome, MatchMode.ANYWHERE))
		.add(Restrictions.eq("icAtivo", true));
		return findByCriteria(c);
	}
	
	public List<Produto> findByOperacao(Short operacao, List<String> produtosAbrangentes) {
		Criteria c = getCriteria();
		c.add(Restrictions.eq("nuOperacao", operacao));
		if(produtosAbrangentes != null){
			c.add(Restrictions.in("coProduto", produtosAbrangentes));
		}
		c.add(Restrictions.eq("icAtivo", true));
		return findByCriteria(c);
	}
	
	public List<Produto> findByNomeAndOperacao(String nome, Short operacao) {
		Criteria c = getCriteria();
		c.add(Restrictions.eq("nuOperacao", operacao))
		.add(Restrictions.ilike("noProduto", nome, MatchMode.ANYWHERE))
		.add(Restrictions.eq("icAtivo", true)).list();
		return findByCriteria(c);
	}

	@SuppressWarnings("unchecked")
	public List<Produto> getAllNaoPreferenciaisFromProdutoUsuario(String matricula, Short nuPerfil) {
		List<String> produtosAbrangentes = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, nuPerfil);
		if(produtosAbrangentes != null && produtosAbrangentes.isEmpty()){
			return new ArrayList<Produto>();
		}
		String produtos = "";
		if(produtosAbrangentes != null){
			produtos = " AND o.coProduto IN (:list) ";
		}
		Query q = getSession()
				.createQuery("FROM Produto o " +
					" WHERE o.icAtivo = true " +
					" AND o.coProduto NOT IN (SELECT p.produto.coProduto FROM PreferenciasUsuario p WHERE p.usuario.coUsuario = :usuario) " +
					produtos)
				.setString("usuario", matricula);
		if(produtosAbrangentes != null){
			q.setParameterList("list", produtosAbrangentes);
		}
		return q.list();
	}

	public List<Produto> getAllNaoPreferenciaisFromProduto(String matricula, Short nuPerfil) {
		List<String> produtosAbrangentes = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, nuPerfil);
		if(produtosAbrangentes != null && produtosAbrangentes.isEmpty()){
			return new ArrayList<Produto>();
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PreferenciasUsuario.class)
					.add(Restrictions.eq("id.coUsuario", matricula))
					.setProjection(Projections.property("id.coProduto"));
		if(produtosAbrangentes != null){
			dc.add(Restrictions.in("produto.coProduto", produtosAbrangentes));
		}
		return findByCriteria(
						getCriteria()
							.add(Restrictions.eq("icAtivo", Boolean.TRUE))
							.add(Subqueries.propertyNotIn("coProduto", dc)));	
	}
	
	public List<Produto> getProdutosAtivosOrdenadosByCodLikeProduto(String coProduto,  List<String> produtoAbrangentes) {
		Criteria c = getCriteria();
		c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		c.addOrder(Order.asc("coProduto"));
		c.add(Restrictions.like("coProduto", coProduto, MatchMode.START));
		if(produtoAbrangentes != null){
			c.add(Restrictions.in("coProduto", produtoAbrangentes));
		}
		return findByCriteria(c);
	}
	
	public List<Produto> getProdutosAtivosOrdenadosByCodLikeProdutoByCategoria(String coProduto, Integer nuCategoria,  List<String> produtoAbrangentes) {
		Criteria c = getCriteria();
		c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		c.add(Restrictions.like("coProduto", coProduto, MatchMode.START));
		c.addOrder(Order.asc("coProduto"));
		c.add(Restrictions.eq("categoriaProduto.nuCategoriaProduto", nuCategoria));
		if(produtoAbrangentes != null && produtoAbrangentes.size() != 0){
			c.add(Restrictions.in("coProduto", produtoAbrangentes));
		}
		return findByCriteria(c);
	}
	
	public List<Produto> getProdutosByListCoProduto(List<String> produtoAbrangentes) {
		if(produtoAbrangentes != null && produtoAbrangentes.isEmpty()){
			return new ArrayList<Produto>();
		}
		Criteria c = getCriteria();
		c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		c.addOrder(Order.asc("coProduto"));
		if(produtoAbrangentes != null){
			c.add(Restrictions.in("coProduto", produtoAbrangentes));
		}
		return findByCriteria(c);
	}
	
	public ProdutoVO getProdutoSIICO(String nuOperacao) {
		
		SQLQuery query = getSession().createSQLQuery(
						"select nu_produto, no_comercial_prdto, co_ultima_situacao, sg_sistema from icosm001.iacvw012_produtos_siico where nu_produto = :nuProduto");
		
		query.setParameter("nuProduto", Short.parseShort(nuOperacao.replaceAll("[^0-9]", "")));
		
		query.addEntity(ProdutoVO.class);
		
		return (ProdutoVO) query.uniqueResult();
	}
	
	public boolean existeProdutoSIICO(String nuOperacao) {
		
		SQLQuery query = getSession().createSQLQuery(
						"select * from icosm001.iacvw012_produtos_siico where nu_produto = :nuOperacao ");
		
		query.setParameter("nuOperacao", Short.parseShort(nuOperacao.replaceAll("[^0-9]", "")));
				
		return !query.list().isEmpty();
	}
}
