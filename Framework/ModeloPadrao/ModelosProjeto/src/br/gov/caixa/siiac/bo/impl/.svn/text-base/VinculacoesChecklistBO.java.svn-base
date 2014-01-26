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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IApontamentoChecklistProdutoBO;
import br.gov.caixa.siiac.bo.IBlocoChecklistProdutoBO;
import br.gov.caixa.siiac.bo.IItemVerificacaoChecklistProdutoBO;
import br.gov.caixa.siiac.bo.IVinculacoesChecklistBO;
import br.gov.caixa.siiac.model.ChecklistVO;
import br.gov.caixa.siiac.model.VinculacoesChecklistVO;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.model.domain.RestricaoApontamento;
import br.gov.caixa.siiac.persistence.dao.IVinculacoesChecklistDAO;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class VinculacoesChecklistBO implements IVinculacoesChecklistBO {
	
	@Autowired
	private IVinculacoesChecklistDAO vinculacoesChecklistDAO;
	
	@Autowired
	private IBlocoChecklistProdutoBO blocoChecklistProdutoBO;
	
	@Autowired
	private IItemVerificacaoChecklistProdutoBO itemVerificacaoChecklistProdutoBO;
	
	@Autowired
	private IApontamentoChecklistProdutoBO apontamentoChecklistProdutoBO;
	
	public void deleteNotInList(VinculacoesChecklistVO objeto) {
		if(objeto.isNivelBloco()){
			for(BlocoChecklistProduto bloco : vinculacoesChecklistDAO.getListVinculacaoBloco(objeto.getPai())){
				if(!objeto.getVinculadas().contains(bloco.getBlocoChecklist().getNuBlocoChecklist())) {
					vinculacoesChecklistDAO.deleteVinculacaoBloco(bloco);
					objeto.addDeletado(bloco.getBlocoChecklist().getNuBlocoChecklist());
				}
			}
			
		}
		else if(objeto.isNivelItem()){
			for(ItemVerificacaoChecklistProduto item : vinculacoesChecklistDAO.getListVinculacaoItem(objeto.getPai())){
				if(!objeto.getVinculadas().contains(item.getItemVerificacao().getNuItemVerificacao())) {
					vinculacoesChecklistDAO.deleteVinculacaoItem(item);
					objeto.addDeletado(item.getItemVerificacao().getNuItemVerificacao());
				}
			}
			
		}
		else if(objeto.isNivelApontamento()){
			for(ApontamentoChecklistProduto apontamento : vinculacoesChecklistDAO.getListVinculacaoApontamento(objeto.getPai())){
				if(!objeto.getVinculadas().contains(apontamento.getApontamento().getNuApontamentoChecklist())) {
					vinculacoesChecklistDAO.deleteVinculacaoApontamento(apontamento);
					objeto.addDeletado(apontamento.getApontamento().getNuApontamentoChecklist());
				}
			}
			
		}
		else if(objeto.isNivelRestricao()){
			for(RestricaoApontamento restricao : vinculacoesChecklistDAO.getListVinculacaoRestricao(objeto.getPai())){
				if(!objeto.getVinculadas().contains(restricao.getRestricao().getNuRestricao())) {
					vinculacoesChecklistDAO.deleteVinculacaoRestricao(restricao);
					objeto.addDeletado(restricao.getRestricao().getNuRestricao());
				}
			}
			
		}
	}
	
	public void save(VinculacoesChecklistVO objeto) {
		this.save(objeto, 1);
	}
	
	public void save(VinculacoesChecklistVO objeto, Integer cont){
		if(objeto.isNivelBloco()){
			for(Integer bloco : objeto.getVinculadas()){
				boolean inserted = vinculacoesChecklistDAO.saveBloco(bloco, objeto.getPai(), cont);
				if(inserted){
					objeto.addInserido(bloco);
				}
				cont++;
			}
		}
		else if(objeto.isNivelItem()){
			for(Integer item : objeto.getVinculadas()){
				boolean inserted = vinculacoesChecklistDAO.saveItem(item, objeto.getPai(), cont);
				if(inserted){
					objeto.addInserido(item);
				}
				cont++;
			}
		}
		else if(objeto.isNivelApontamento()){
			for(Integer apontamento : objeto.getVinculadas()){
				boolean inserted = vinculacoesChecklistDAO.saveApontamento(apontamento, objeto.getPai(), cont);
				if(inserted){
					objeto.addInserido(apontamento);
				}
				cont++;
			}
		}
		else if(objeto.isNivelRestricao()){
			for(Integer restricao : objeto.getVinculadas()){
				boolean inserted = vinculacoesChecklistDAO.saveRestricao(restricao, objeto.getPai());
				if(inserted){
					objeto.addInserido(restricao);
				}
			}
		}
	}
	
	public void setHierarquiaPais(VinculacoesChecklistVO objeto) {
		if(objeto.isNivelItem()){
			BlocoChecklistProduto bloco = blocoChecklistProdutoBO.findById(objeto.getPai());
			objeto.setMapHierarquiaBloco(bloco.getBlocoChecklist().getNuBlocoChecklist());
		}
		else if(objeto.isNivelApontamento()){
			ItemVerificacaoChecklistProduto item = itemVerificacaoChecklistProdutoBO.findById(objeto.getPai());
			objeto.setMapHierarquiaItem(item.getItemVerificacao().getNuItemVerificacao());			
			objeto.setMapHierarquiaBloco(item.getBlocoChecklistProduto().getBlocoChecklist().getNuBlocoChecklist());			
		}
		else if(objeto.isNivelRestricao()){
			ApontamentoChecklistProduto apontamento = apontamentoChecklistProdutoBO.findById(objeto.getPai());
			objeto.setMapHierarquiaApontamento(apontamento.getApontamento().getNuApontamentoChecklist());			
			objeto.setMapHierarquiaItem(apontamento.getItemVerificacaoChecklistProduto().getItemVerificacao().getNuItemVerificacao());			
			objeto.setMapHierarquiaBloco(apontamento.getItemVerificacaoChecklistProduto().getBlocoChecklistProduto().getBlocoChecklist().getNuBlocoChecklist());
		}
	}
	
	public Integer adjustHierarquiaReplicas(VinculacoesChecklistVO objeto, Integer idChecklist) {
		if(objeto.isNivelItem()){
			BlocoChecklistProduto bloco = vinculacoesChecklistDAO.getBloco(objeto.getMapHierarquiaBloco(), idChecklist);
			if(bloco == null){
				Integer cont = vinculacoesChecklistDAO.getBlocoLastPosition(idChecklist) + 1;
				vinculacoesChecklistDAO.saveBloco(objeto.getMapHierarquiaBloco(), idChecklist, cont);
				bloco = vinculacoesChecklistDAO.getBloco(objeto.getMapHierarquiaBloco(), idChecklist);
			}
			return bloco.getNuBlocoChecklistProduto();
		}
		else if(objeto.isNivelApontamento()){
			BlocoChecklistProduto bloco = vinculacoesChecklistDAO.getBloco(objeto.getMapHierarquiaBloco(), idChecklist);
			if(bloco == null){
				Integer cont = vinculacoesChecklistDAO.getBlocoLastPosition(idChecklist) + 1;
				vinculacoesChecklistDAO.saveBloco(objeto.getMapHierarquiaBloco(), idChecklist, cont);
				bloco = vinculacoesChecklistDAO.getBloco(objeto.getMapHierarquiaBloco(), idChecklist);
			}	
			
			ItemVerificacaoChecklistProduto item = vinculacoesChecklistDAO.getItem(objeto.getMapHierarquiaItem(), bloco.getNuBlocoChecklistProduto());
			if(item == null){
				Integer cont = vinculacoesChecklistDAO.getItemLastPosition(bloco.getNuBlocoChecklistProduto()) + 1;
				vinculacoesChecklistDAO.saveItem(objeto.getMapHierarquiaItem(), bloco.getNuBlocoChecklistProduto(), cont);
				item = vinculacoesChecklistDAO.getItem(objeto.getMapHierarquiaItem(), bloco.getNuBlocoChecklistProduto());
			}
			return item.getNuItemVerificacaoChecklistProduto();
					
		}
		else if(objeto.isNivelRestricao()){
			BlocoChecklistProduto bloco = vinculacoesChecklistDAO.getBloco(objeto.getMapHierarquiaBloco(), idChecklist);
			if(bloco == null){
				Integer cont = vinculacoesChecklistDAO.getBlocoLastPosition(idChecklist) + 1;
				vinculacoesChecklistDAO.saveBloco(objeto.getMapHierarquiaBloco(), idChecklist, cont);
				bloco = vinculacoesChecklistDAO.getBloco(objeto.getMapHierarquiaBloco(), idChecklist);
			}	
			
			ItemVerificacaoChecklistProduto item = vinculacoesChecklistDAO.getItem(objeto.getMapHierarquiaItem(), bloco.getNuBlocoChecklistProduto());
			if(item == null){
				Integer cont = vinculacoesChecklistDAO.getItemLastPosition(bloco.getNuBlocoChecklistProduto()) + 1;
				vinculacoesChecklistDAO.saveItem(objeto.getMapHierarquiaItem(), bloco.getNuBlocoChecklistProduto(), cont);
				item = vinculacoesChecklistDAO.getItem(objeto.getMapHierarquiaItem(), bloco.getNuBlocoChecklistProduto());
			}
			
			ApontamentoChecklistProduto apontamento = vinculacoesChecklistDAO.getApontamento(objeto.getMapHierarquiaApontamento(), item.getNuItemVerificacaoChecklistProduto());
			if(apontamento == null) {
				Integer cont = vinculacoesChecklistDAO.getApontamentoLastPosition(item.getNuItemVerificacaoChecklistProduto()) + 1;
				vinculacoesChecklistDAO.saveApontamento(objeto.getMapHierarquiaApontamento(), item.getNuItemVerificacaoChecklistProduto(), cont);
				apontamento = vinculacoesChecklistDAO.getApontamento(objeto.getMapHierarquiaApontamento(), item.getNuItemVerificacaoChecklistProduto());
			}
			return apontamento.getNuApontamentoChecklistProduto();
		}
		return null;
	}
	
	public Integer getHierarquiaPaiId(VinculacoesChecklistVO objeto, Integer idChecklist){
		return vinculacoesChecklistDAO.getHierarquiaPaiId(objeto, idChecklist);
	}
	
	public void deleteReplicas(VinculacoesChecklistVO objeto, Integer idPai) {
		if(objeto.isNivelItem()){
			for(Integer idItem : objeto.getDeletados()){
				ItemVerificacaoChecklistProduto item = vinculacoesChecklistDAO.getItem(idItem, idPai);
				if(item != null) {
					vinculacoesChecklistDAO.deleteVinculacaoItem(item);
				}
			}
		}
		else if(objeto.isNivelApontamento()){
			for(Integer idApontamento : objeto.getDeletados()){
				ApontamentoChecklistProduto apontamento = vinculacoesChecklistDAO.getApontamento(idApontamento, idPai);
				if(apontamento != null) {
					vinculacoesChecklistDAO.deleteVinculacaoApontamento(apontamento);
				}
			}
		}
		else if(objeto.isNivelRestricao()){
			for(Integer idRestricao : objeto.getDeletados()){
				RestricaoApontamento restricao = vinculacoesChecklistDAO.getRestricao(idRestricao, idPai);
				if(restricao != null) {
					vinculacoesChecklistDAO.deleteVinculacaoRestricao(restricao);
				}
			}
		}
	}	

	public void saveReplicas(VinculacoesChecklistVO objeto, Integer idPai) {
		if(objeto.isNivelBloco()){
			Integer cont = vinculacoesChecklistDAO.getBlocoLastPosition(idPai) + 1;
			for(Integer idBloco : objeto.getInseridos()){
				BlocoChecklistProduto bloco = vinculacoesChecklistDAO.getBloco(idBloco, idPai);
				if(bloco == null){
					vinculacoesChecklistDAO.saveBloco(idBloco, idPai, cont);
					cont++;
				}
			}
		}
		else if(objeto.isNivelItem()){
			Integer cont = vinculacoesChecklistDAO.getItemLastPosition(idPai) + 1;
			for(Integer idItem : objeto.getInseridos()){
				ItemVerificacaoChecklistProduto item = vinculacoesChecklistDAO.getItem(idItem, idPai);
				if(item == null){
					vinculacoesChecklistDAO.saveItem(idItem, idPai, cont);
					cont++;
				}
			}
		}
		else if(objeto.isNivelApontamento()){
			Integer cont = vinculacoesChecklistDAO.getApontamentoLastPosition(idPai) + 1;
			for(Integer idApontamento : objeto.getInseridos()){
				ApontamentoChecklistProduto apontamento = vinculacoesChecklistDAO.getApontamento(idApontamento, idPai);
				if(apontamento == null){
					vinculacoesChecklistDAO.saveApontamento(idApontamento, idPai, cont);
					cont++;
				}
			}
		}
		else if(objeto.isNivelRestricao()){
			for(Integer idRestricao : objeto.getInseridos()){
				RestricaoApontamento restricao = vinculacoesChecklistDAO.getRestricao(idRestricao, idPai);
				if(restricao == null){
					vinculacoesChecklistDAO.saveRestricao(idRestricao, idPai);
				}
			}
		}
	}
	
	public void reordenarObjetos(VinculacoesChecklistVO objeto, Integer idPai){
		if(objeto.isNivelBloco()){
			Integer cont = 1;
			for(Integer bloco : objeto.getVinculadas()){
				vinculacoesChecklistDAO.saveBloco(bloco, idPai, cont);
				cont++;
			}
		}
		else if(objeto.isNivelItem()){
			Integer cont = 1;
			for(Integer item : objeto.getVinculadas()){
				vinculacoesChecklistDAO.saveItem(item, idPai, cont);
				cont++;
			}
		}
		else if(objeto.isNivelApontamento()){
			Integer cont = 1;
			for(Integer apontamento : objeto.getVinculadas()){
				vinculacoesChecklistDAO.saveApontamento(apontamento, idPai, cont);
				cont++;
			}
		}
	}
	
	public boolean deveReordenar(VinculacoesChecklistVO objeto, Integer idPai){
		if(objeto.isNivelBloco()){
			List<BlocoChecklistProduto> list = vinculacoesChecklistDAO.getListVinculacaoBloco(idPai);
			
			if(list != null && list.size() == objeto.getVinculadas().size()){
				boolean reorder = true;
				for(BlocoChecklistProduto bloco : list){
					if(!objeto.getVinculadas().contains(bloco.getBlocoChecklist().getNuBlocoChecklist())){
						reorder = false;
						break;
					}
				}
				return reorder;
			}
		}
		else if(objeto.isNivelItem()){
			List<ItemVerificacaoChecklistProduto> list = vinculacoesChecklistDAO.getListVinculacaoItem(idPai);
			
			if(list != null && list.size() == objeto.getVinculadas().size()){
				boolean reorder = true;
				for(ItemVerificacaoChecklistProduto item : list){
					if(!objeto.getVinculadas().contains(item.getItemVerificacao().getNuItemVerificacao())){
						reorder = false;
						break;
					}
				}
				return reorder;
			}
		}
		else if(objeto.isNivelApontamento()){
			List<ApontamentoChecklistProduto> list = vinculacoesChecklistDAO.getListVinculacaoApontamento(idPai);
			
			if(list != null && list.size() == objeto.getVinculadas().size()){
				boolean reorder = true;
				for(ApontamentoChecklistProduto apontamento : list){
					if(!objeto.getVinculadas().contains(apontamento.getApontamento().getNuApontamentoChecklist())){
						reorder = false;
						break;
					}
				}
				return reorder;
			}
		}
		return false;
	}
	
	public void ajustarOrdem(VinculacoesChecklistVO objeto, Integer idPai) {
		if(objeto.isNivelBloco()){
			List<BlocoChecklistProduto> list = vinculacoesChecklistDAO.getListVinculacaoBloco(idPai);
			
			if(list != null && !list.isEmpty()){
				Integer cont = 1;
				for(BlocoChecklistProduto bloco : list){
					vinculacoesChecklistDAO.saveBloco(bloco.getBlocoChecklist().getNuBlocoChecklist(), idPai, cont);
					cont++;
				}
			}
		}
		else if(objeto.isNivelItem()){
			List<ItemVerificacaoChecklistProduto> list = vinculacoesChecklistDAO.getListVinculacaoItem(idPai);
			
			if(list != null && !list.isEmpty()){
				Integer cont = 1;
				for(ItemVerificacaoChecklistProduto item : list){
					vinculacoesChecklistDAO.saveItem(item.getItemVerificacao().getNuItemVerificacao(), idPai, cont);
					cont++;
				}
			}
		}
		else if(objeto.isNivelApontamento()){
			List<ApontamentoChecklistProduto> list = vinculacoesChecklistDAO.getListVinculacaoApontamento(idPai);
			
			if(list != null && !list.isEmpty()){
				Integer cont = 1;
				for(ApontamentoChecklistProduto apontamento : list){
					vinculacoesChecklistDAO.saveApontamento(apontamento.getApontamento().getNuApontamentoChecklist(), idPai, cont);
					cont++;
				}
			}
		}
	}

	public boolean isChecklistPublicado(Integer idChecklist) {
		ChecklistServicoVerificacaoProduto checklist = vinculacoesChecklistDAO.findById(idChecklist);
		return checklist != null && checklist.getIcSituacao().trim().equals(ChecklistVO.CHKLST_SITUACAO_PUBLICADO);
	}
	
	public void delete(Object objeto) {
		if(objeto instanceof BlocoChecklistProduto) {
			BlocoChecklistProduto bloco = (BlocoChecklistProduto) objeto;
			vinculacoesChecklistDAO.deleteVinculacaoBloco(bloco);
		
		} else if(objeto instanceof ItemVerificacaoChecklistProduto) {
			ItemVerificacaoChecklistProduto item = (ItemVerificacaoChecklistProduto) objeto;
			vinculacoesChecklistDAO.deleteVinculacaoItem(item);
		
		} else if(objeto instanceof ApontamentoChecklistProduto) {
			ApontamentoChecklistProduto apontamento = (ApontamentoChecklistProduto) objeto;
			vinculacoesChecklistDAO.deleteVinculacaoApontamento(apontamento);
		
		} else {
			RestricaoApontamento restricao = (RestricaoApontamento) objeto;
			vinculacoesChecklistDAO.deleteVinculacaoRestricao(restricao);
		}
	}

	public void merge(Object objeto) {
		if(objeto instanceof BlocoChecklistProduto) {
			BlocoChecklistProduto bloco = (BlocoChecklistProduto) objeto;
			vinculacoesChecklistDAO.updateVinculacaoBloco(bloco);
		
		} else if(objeto instanceof ItemVerificacaoChecklistProduto) {
			ItemVerificacaoChecklistProduto item = (ItemVerificacaoChecklistProduto) objeto;
			vinculacoesChecklistDAO.updateVinculacaoItem(item);
		
		} else if(objeto instanceof ApontamentoChecklistProduto) {
			ApontamentoChecklistProduto apontamento = (ApontamentoChecklistProduto) objeto;
			vinculacoesChecklistDAO.updateVinculacaoApontamento(apontamento);
		
		} else {
			RestricaoApontamento restricao = (RestricaoApontamento) objeto;
			vinculacoesChecklistDAO.updateVinculacaoRestricao(restricao);
		}
	}
	
	public BlocoChecklistProduto getBloco(Integer idBloco, Integer idChecklist) {
		return vinculacoesChecklistDAO.getBloco(idBloco, idChecklist);
	}
	
	public ItemVerificacaoChecklistProduto getItem(Integer idItem, Integer idVinculacaoBloco) {
		return vinculacoesChecklistDAO.getItem(idItem, idVinculacaoBloco);
	}
	
	public ApontamentoChecklistProduto getApontamento(Integer idApontamento, Integer idVinculacaoItem) {
		return vinculacoesChecklistDAO.getApontamento(idApontamento, idVinculacaoItem);
	}
	
	public RestricaoApontamento getRestricao(Integer idRestricao, Integer idVinculacaoApontamento) {
		return vinculacoesChecklistDAO.getRestricao(idRestricao, idVinculacaoApontamento);
	}
	
	public Integer getBlocoLastPosition(Integer idChecklist){
		return vinculacoesChecklistDAO.getBlocoLastPosition(idChecklist);
	}
}