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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.VinculacoesChecklistVO;
import br.gov.caixa.siiac.model.domain.Apontamento;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.BlocoChecklist;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacao;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.model.domain.Restricao;
import br.gov.caixa.siiac.model.domain.RestricaoApontamento;
import br.gov.caixa.siiac.persistence.dao.IApontamentoChecklistProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IBlocoChecklistProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IItemVerificacaoChecklistProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IRestricaoApontamentoDAO;
import br.gov.caixa.siiac.persistence.dao.IVinculacoesChecklistDAO;

/**
 * @author GIS Consult
 *
 */
@Repository
@Scope("prototype")
public class VinculacoesChecklistDAO extends GenericDAO<ChecklistServicoVerificacaoProduto> implements IVinculacoesChecklistDAO {

	public VinculacoesChecklistDAO() {
		super(ChecklistServicoVerificacaoProduto.class);
	}
	
	/**
	 * Bloco --------------------------------------------------------------------------------------------------
	 */
	@Autowired
	private IBlocoChecklistProdutoDAO blocoChecklistProdutoDAO;
	
	@Autowired
	private IItemVerificacaoChecklistProdutoDAO itemVerificacaoChecklistProdutoDAO;
	
	@Autowired
	private IApontamentoChecklistProdutoDAO apontamentoChecklistProdutoDAO;
	
	@Autowired
	private IRestricaoApontamentoDAO restricaoApontamentoDAO;
	
	public void deleteVinculacaoBloco(BlocoChecklistProduto bloco){
		blocoChecklistProdutoDAO.delete(bloco);
	}
	
	public void updateVinculacaoBloco(BlocoChecklistProduto bloco){
		blocoChecklistProdutoDAO.merge(bloco);
	}
	
	public List<BlocoChecklistProduto> getListVinculacaoBloco(Integer idChecklist){
		DetachedCriteria crit = DetachedCriteria.forClass(BlocoChecklistProduto.class)
							.add(Restrictions.eq("checklistServicoVerificacaoProduto.nuChecklistServicoVerificacaoProduto", idChecklist))
							.addOrder(Order.asc("numeroDeOrdem"));
		return crit.getExecutableCriteria(getSession()).list();
	}
	
	public boolean saveBloco(Integer idBloco, Integer idChecklist, Integer cont) {
		boolean inserted = false;
		BlocoChecklistProduto blocoVinculacao = new BlocoChecklistProduto();
		
		
		BlocoChecklistProduto blocoBD = getBloco(idBloco, idChecklist);
		BlocoChecklistProduto objectBack = new BlocoChecklistProduto();
		Integer order = null;
		if(blocoBD != null){
			Integer id = blocoBD.getNuBlocoChecklistProduto();
			objectBack = blocoChecklistProdutoDAO.findById(id);
			order = objectBack.getNumeroDeOrdem();
			blocoVinculacao = blocoBD;
		}else{
			ChecklistServicoVerificacaoProduto checklist = new ChecklistServicoVerificacaoProduto();
			checklist.setNuChecklistServicoVerificacaoProduto(idChecklist);
			
			BlocoChecklist bloco = new BlocoChecklist();
			bloco.setNuBlocoChecklist(idBloco);
			
			blocoVinculacao.setChecklistServicoVerificacaoProduto(checklist);
			blocoVinculacao.setBlocoChecklist(bloco);
			
			blocoVinculacao.setDescricao("");
			blocoVinculacao.setIcDesabilita(Boolean.FALSE);
			blocoVinculacao.setIcObrigatorio(Boolean.FALSE);
			inserted = true;
		}
		blocoVinculacao.setDescricao("teste" + cont);
		blocoVinculacao.setNumeroDeOrdem(cont);
		System.out.println(order);
		
		
		objectBack.setNumeroDeOrdem(order);
		blocoChecklistProdutoDAO.merge(blocoVinculacao);
		return inserted;
	}
	
	public Integer getBlocoLastPosition(Integer idChecklist) {
		DetachedCriteria crit = DetachedCriteria.forClass(BlocoChecklistProduto.class)
							.add(Restrictions.eq("checklistServicoVerificacaoProduto.nuChecklistServicoVerificacaoProduto", idChecklist))
							.setProjection(Projections.max("numeroDeOrdem"));
		Object retorno = crit.getExecutableCriteria(getSession()).uniqueResult();
		return (retorno == null ? 0 : Integer.valueOf(retorno.toString())); 
	}
	
	public BlocoChecklistProduto getBloco(Integer idBloco, Integer idChecklist) {
		Criteria crit = getSession().createCriteria(BlocoChecklistProduto.class)
				.add(Restrictions.eq("checklistServicoVerificacaoProduto.nuChecklistServicoVerificacaoProduto", idChecklist))
				.add(Restrictions.eq("blocoChecklist.nuBlocoChecklist", idBloco));
		return (BlocoChecklistProduto) crit.uniqueResult();
	}
	/**
	 * Item --------------------------------------------------------------------------------------------------- 
	 */
	
	public void deleteVinculacaoItem(ItemVerificacaoChecklistProduto item){
		itemVerificacaoChecklistProdutoDAO.delete(item);
	}

	public void updateVinculacaoItem(ItemVerificacaoChecklistProduto item){
		itemVerificacaoChecklistProdutoDAO.merge(item);
	}
	
	public List<ItemVerificacaoChecklistProduto> getListVinculacaoItem(Integer idVinculacaoBloco){
		DetachedCriteria crit = DetachedCriteria.forClass(ItemVerificacaoChecklistProduto.class)
							.add(Restrictions.eq("blocoChecklistProduto.nuBlocoChecklistProduto", idVinculacaoBloco))
							.addOrder(Order.asc("numeroDeOrdem"));
		return crit.getExecutableCriteria(getSession()).list();
	}

	public boolean saveItem(Integer idItem, Integer idVinculacaoBloco, Integer cont) {
		boolean inserted = false;
		ItemVerificacaoChecklistProduto itemVinculacao = new ItemVerificacaoChecklistProduto();
				
		ItemVerificacaoChecklistProduto itemBD = getItem(idItem, idVinculacaoBloco);
		if(itemBD != null){
			itemVinculacao = itemBD;
		}else{
			BlocoChecklistProduto bloco = new BlocoChecklistProduto();
			bloco.setNuBlocoChecklistProduto(idVinculacaoBloco);
			
			ItemVerificacao itemVerificacao = new ItemVerificacao();
			itemVerificacao.setNuItemVerificacao(idItem);
			
			itemVinculacao.setBlocoChecklistProduto(bloco);
			itemVinculacao.setItemVerificacao(itemVerificacao);
			
			itemVinculacao.setDeItemVerificacaoChecklistProduto("");
			inserted = true;
		}
		itemVinculacao.setNumeroDeOrdem(cont);
		
		itemVerificacaoChecklistProdutoDAO.merge(itemVinculacao);
		return inserted;
	}
	
	public ItemVerificacaoChecklistProduto getItem(Integer idItem, Integer idVinculacaoBloco){
		DetachedCriteria crit = DetachedCriteria.forClass(ItemVerificacaoChecklistProduto.class)
							.add(Restrictions.eq("blocoChecklistProduto.nuBlocoChecklistProduto", idVinculacaoBloco))
							.add(Restrictions.eq("itemVerificacao.nuItemVerificacao", idItem));
		return (ItemVerificacaoChecklistProduto) crit.getExecutableCriteria(getSession()).uniqueResult();
	}
	
	public Integer getItemLastPosition(Integer idVinculacaoBloco) {
		DetachedCriteria crit = DetachedCriteria.forClass(ItemVerificacaoChecklistProduto.class)
							.add(Restrictions.eq("blocoChecklistProduto.nuBlocoChecklistProduto", idVinculacaoBloco))
							.setProjection(Projections.max("numeroDeOrdem"));
		Object retorno = crit.getExecutableCriteria(getSession()).uniqueResult();
		return (retorno == null ? 0 : Integer.valueOf(retorno.toString())); 
	}
	
	
	/**
	 * Apontamento ----------------------------------------------------------------------------------------------------------------
	 */
	public void deleteVinculacaoApontamento(ApontamentoChecklistProduto apontamento){
		apontamentoChecklistProdutoDAO.delete(apontamento);
	}

	public void updateVinculacaoApontamento(ApontamentoChecklistProduto apontamento){
		apontamentoChecklistProdutoDAO.merge(apontamento);
	}
	
	public List<ApontamentoChecklistProduto> getListVinculacaoApontamento(Integer idVinculacaoItem){
		DetachedCriteria crit = DetachedCriteria.forClass(ApontamentoChecklistProduto.class)
							.add(Restrictions.eq("itemVerificacaoChecklistProduto.nuItemVerificacaoChecklistProduto", idVinculacaoItem))
							.addOrder(Order.asc("numeroDeOrdem"));
		return crit.getExecutableCriteria(getSession()).list();
	}

	public boolean saveApontamento(Integer idApontamento, Integer idVinculacaoItem, Integer cont) {
		boolean inserted = false;
		ApontamentoChecklistProduto apontamentoVinculacao = new ApontamentoChecklistProduto();
		
		ApontamentoChecklistProduto apontamentoBD = getApontamento(idApontamento, idVinculacaoItem);
		if(apontamentoBD != null){
			apontamentoVinculacao = apontamentoBD;
		}else{
			ItemVerificacaoChecklistProduto item = new ItemVerificacaoChecklistProduto();
			item.setNuItemVerificacaoChecklistProduto(idVinculacaoItem);
			
			Apontamento apontamento = new Apontamento();
			apontamento.setNuApontamentoChecklist(idApontamento);
			
			apontamentoVinculacao.setItemVerificacaoChecklistProduto(item);
			apontamentoVinculacao.setApontamento(apontamento);
			
			apontamentoVinculacao.setDescricao("");
			apontamentoVinculacao.setIcNaoAplica(Boolean.FALSE);
			inserted = true;
		}
		apontamentoVinculacao.setNumeroDeOrdem(cont);
		
		apontamentoChecklistProdutoDAO.merge(apontamentoVinculacao);
		return inserted;
	}
	
	public ApontamentoChecklistProduto getApontamento(Integer idApontamento, Integer idVinculacaoItem){
		DetachedCriteria crit = DetachedCriteria.forClass(ApontamentoChecklistProduto.class)
							.add(Restrictions.eq("itemVerificacaoChecklistProduto.nuItemVerificacaoChecklistProduto", idVinculacaoItem))
							.add(Restrictions.eq("apontamento.nuApontamentoChecklist", idApontamento));
		return (ApontamentoChecklistProduto) crit.getExecutableCriteria(getSession()).uniqueResult();
	}
	
	public Integer getApontamentoLastPosition(Integer idVinculacaoItem) {
		DetachedCriteria crit = DetachedCriteria.forClass(ApontamentoChecklistProduto.class)
							.add(Restrictions.eq("itemVerificacaoChecklistProduto.nuItemVerificacaoChecklistProduto", idVinculacaoItem))
							.setProjection(Projections.max("numeroDeOrdem"));
		Object retorno = crit.getExecutableCriteria(getSession()).uniqueResult();
		return (retorno == null ? 0 : Integer.valueOf(retorno.toString()));
	}
	
	
	/**
	 * Restricao ----------------------------------------------------------------------------------
	 */
	public void deleteVinculacaoRestricao(RestricaoApontamento restricao){
		restricaoApontamentoDAO.delete(restricao);
	}

	public void updateVinculacaoRestricao(RestricaoApontamento restricao){
		restricaoApontamentoDAO.merge(restricao);
	}
	
	public List<RestricaoApontamento> getListVinculacaoRestricao(Integer idVinculacaoApontamento){
		DetachedCriteria crit = DetachedCriteria.forClass(RestricaoApontamento.class)
				.add(Restrictions.eq("apontamentoChecklistProduto.nuApontamentoChecklistProduto", idVinculacaoApontamento));
		return crit.getExecutableCriteria(getSession()).list();
	}

	public boolean saveRestricao(Integer idRestricao, Integer idVinculacaoApontamento) {
		boolean inserted = false;
		RestricaoApontamento restricaoVinculacao = new RestricaoApontamento();
		
		
		RestricaoApontamento restricaoBD = getRestricao(idRestricao, idVinculacaoApontamento);
		if(restricaoBD != null){
			restricaoVinculacao = restricaoBD;
		}else{
			ApontamentoChecklistProduto apontamento = new ApontamentoChecklistProduto();
			apontamento.setNuApontamentoChecklistProduto(idVinculacaoApontamento);
			
			Restricao restricao = new Restricao();
			restricao.setNuRestricao(idRestricao);
			
			restricaoVinculacao.setApontamentoChecklistProduto(apontamento);
			restricaoVinculacao.setRestricao(restricao);
			inserted = true;
		}
		
		restricaoApontamentoDAO.merge(restricaoVinculacao);
		return inserted;
	}
		
	public RestricaoApontamento getRestricao(Integer idRestricao, Integer idVinculacaoApontamento){
		DetachedCriteria crit = DetachedCriteria.forClass(RestricaoApontamento.class)
							.add(Restrictions.eq("apontamentoChecklistProduto.nuApontamentoChecklistProduto", idVinculacaoApontamento))
							.add(Restrictions.eq("restricao.nuRestricao", idRestricao));
		return (RestricaoApontamento) crit.getExecutableCriteria(getSession()).uniqueResult();
	}

	public Integer getHierarquiaPaiId(VinculacoesChecklistVO objeto, Integer idChecklist){
		DetachedCriteria crit = DetachedCriteria.forClass(BlocoChecklistProduto.class);
		crit.add(Restrictions.eq("checklistServicoVerificacaoProduto.nuChecklistServicoVerificacaoProduto", idChecklist));
		crit.add(Restrictions.eq("blocoChecklist.nuBlocoChecklist", objeto.getMapHierarquiaBloco()));
		crit.setProjection(Projections.property("nuBlocoChecklistProduto"));
		
		if(!objeto.isNivelItem()){
			crit.createAlias("itemVerificacaoChecklistProdutoList", "item");
			crit.add(Restrictions.eq("item.itemVerificacao.nuItemVerificacao", objeto.getMapHierarquiaItem()));
			crit.setProjection(Projections.property("item.nuItemVerificacaoChecklistProduto"));
			
			if(!objeto.isNivelApontamento()){
				crit.createAlias("item.apontamentoChecklistProdutoList", "apontamento");
				crit.add(Restrictions.eq("apontamento.apontamento.nuApontamentoChecklist", objeto.getMapHierarquiaApontamento()));
				crit.setProjection(Projections.property("apontamento.nuApontamentoChecklistProduto"));
			}
		}
		Object retorno = crit.getExecutableCriteria(getSession()).uniqueResult();
		return (retorno == null ? null : Integer.valueOf(retorno.toString()));
	}
}
