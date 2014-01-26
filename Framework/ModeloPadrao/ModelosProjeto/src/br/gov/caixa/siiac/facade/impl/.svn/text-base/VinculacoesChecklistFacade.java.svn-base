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
package br.gov.caixa.siiac.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IChecklistBO;
import br.gov.caixa.siiac.bo.IVinculacoesChecklistBO;
import br.gov.caixa.siiac.facade.IVinculacoesChecklistFacade;
import br.gov.caixa.siiac.model.VinculacoesChecklistVO;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.model.domain.RestricaoApontamento;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class VinculacoesChecklistFacade implements IVinculacoesChecklistFacade {

	@Autowired
	private IVinculacoesChecklistBO vinculacoesChecklistBO;

	@Autowired
	private IChecklistBO checklistBO;
	
	@Transactional
	public Integer executarRotinaDragDropVinculacoes(VinculacoesChecklistVO objeto, List<ChecklistServicoVerificacaoProduto> checklists){
		
		// Apenas reordenar o objeto.checklistPrincipal e checklists 
		if(objeto.isNivelBloco()){
			// Reordenar principal
			// Se o checklist estiver publicado, ele é revogado e duplicado. Após isso, as mudanças são feitas no novo Checklist.
			if(vinculacoesChecklistBO.isChecklistPublicado(objeto.getChecklistPrincipal())){
				objeto.setChecklistPrincipal(checklistBO.duplicateChecklist(objeto.getChecklistPrincipal()));
			}
			vinculacoesChecklistBO.reordenarObjetos(objeto, objeto.getChecklistPrincipal());
			
			
			for(ChecklistServicoVerificacaoProduto checklist : checklists){
				Integer idChecklist = checklist.getNuChecklistServicoVerificacaoProduto();
				if(vinculacoesChecklistBO.deveReordenar(objeto, idChecklist)){
					//Se o checklist estiver publicado, ele é revogado e duplicado. Após isso, as mudanças são feitas no novo Checklist.
					if(vinculacoesChecklistBO.isChecklistPublicado(idChecklist)){
						idChecklist = checklistBO.duplicateChecklist(idChecklist);
					}
					
					vinculacoesChecklistBO.reordenarObjetos(objeto, idChecklist);
				}
			}
			return objeto.getChecklistPrincipal();
		}
		
		//Se o checklist estiver publicado, ele é revogado e duplicado. Após isso, as mudanças são feitas no novo Checklist.
		if(vinculacoesChecklistBO.isChecklistPublicado(objeto.getChecklistPrincipal())){
			objeto.setChecklistPrincipal(checklistBO.duplicateChecklist(objeto.getChecklistPrincipal()));
			vinculacoesChecklistBO.setHierarquiaPais(objeto);
			objeto.setPai(vinculacoesChecklistBO.getHierarquiaPaiId(objeto, objeto.getChecklistPrincipal()));
		}
		vinculacoesChecklistBO.deleteNotInList(objeto);
		vinculacoesChecklistBO.save(objeto);
		
		if(checklists != null && !checklists.isEmpty()){
			vinculacoesChecklistBO.setHierarquiaPais(objeto);
			for(int i = 0; i < checklists.size(); i++){
				Integer idChecklist = checklists.get(i).getNuChecklistServicoVerificacaoProduto();
				Integer idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
				if((objeto.getInseridos() != null && !objeto.getInseridos().isEmpty())
					|| (objeto.getDeletados() != null && !objeto.getDeletados().isEmpty())
					|| (vinculacoesChecklistBO.deveReordenar(objeto, idPai))){
				
					//Se o checklist estiver publicado, ele é revogado e duplicado. Após isso, as mudanças são feitas no novo Checklist.
					if(vinculacoesChecklistBO.isChecklistPublicado(idChecklist)){
						idChecklist = checklistBO.duplicateChecklist(idChecklist);
					}
					
					idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
					
					if(idPai == null){
						if(objeto.getInseridos() != null && !objeto.getInseridos().isEmpty()){
							idPai = vinculacoesChecklistBO.adjustHierarquiaReplicas(objeto, idChecklist);
						}
					}else if(objeto.getDeletados() != null && !objeto.getDeletados().isEmpty()){
						vinculacoesChecklistBO.deleteReplicas(objeto, idPai);
					}
	
					if(objeto.getInseridos() != null && !objeto.getInseridos().isEmpty()){
						vinculacoesChecklistBO.saveReplicas(objeto, idPai);
					}
					
					if(vinculacoesChecklistBO.deveReordenar(objeto, idPai)){
						vinculacoesChecklistBO.reordenarObjetos(objeto, idPai);
					}else{
						vinculacoesChecklistBO.ajustarOrdem(objeto, idPai);
					}
				}
					
			}
		}

		return objeto.getChecklistPrincipal();
		
	}

	@Transactional
	public Integer executarRotinaDeleteVinculacoes(VinculacoesChecklistVO objeto, List<ChecklistServicoVerificacaoProduto> checklists) {
		if(objeto.isNivelBloco()) {
			List<Integer> ids = objeto.getVinculadas();
			
			if(ids != null && !ids.isEmpty()) {
				Integer idBloco = ids.get(0);
								
				if(idBloco != null) {
					BlocoChecklistProduto bl = vinculacoesChecklistBO.getBloco(idBloco, objeto.getChecklistPrincipal());
					
					if(bl != null) {
						if(vinculacoesChecklistBO.isChecklistPublicado(objeto.getChecklistPrincipal())){
							objeto.setChecklistPrincipal(checklistBO.duplicateChecklist(objeto.getChecklistPrincipal()));
							objeto.setPai(objeto.getChecklistPrincipal());
							
							bl = vinculacoesChecklistBO.getBloco(idBloco, objeto.getChecklistPrincipal());
						}
						
						vinculacoesChecklistBO.delete(bl);
						vinculacoesChecklistBO.ajustarOrdem(objeto, objeto.getPai());
					}
					
					if(checklists != null && checklists.size() != 0) {
						Integer idChecklist;
						for(ChecklistServicoVerificacaoProduto ch : checklists) {
							idChecklist = ch.getNuChecklistServicoVerificacaoProduto();
							BlocoChecklistProduto blocoVinculacao = vinculacoesChecklistBO.getBloco(idBloco, ch.getNuChecklistServicoVerificacaoProduto());
							
							if(blocoVinculacao != null) {
								if(vinculacoesChecklistBO.isChecklistPublicado(idChecklist)){
									idChecklist = checklistBO.duplicateChecklist(idChecklist);
									blocoVinculacao = vinculacoesChecklistBO.getBloco(idBloco, idChecklist);
								}
								
								vinculacoesChecklistBO.delete(blocoVinculacao);
								vinculacoesChecklistBO.ajustarOrdem(objeto, idChecklist);
							}
						}
					}
				}
			}
		} else if(objeto.isNivelItem()) {
			List<Integer> ids = objeto.getVinculadas();
					
			if(ids != null && !ids.isEmpty()) {
				Integer idItem = ids.get(0);
				
				if(idItem != null) {
					ItemVerificacaoChecklistProduto it = vinculacoesChecklistBO.getItem(idItem, objeto.getPai());
					
					if(it != null) {
						if(vinculacoesChecklistBO.isChecklistPublicado(objeto.getChecklistPrincipal())){
							objeto.setChecklistPrincipal(checklistBO.duplicateChecklist(objeto.getChecklistPrincipal()));
							vinculacoesChecklistBO.setHierarquiaPais(objeto);
							objeto.setPai(vinculacoesChecklistBO.getHierarquiaPaiId(objeto, objeto.getChecklistPrincipal()));
							
							it = vinculacoesChecklistBO.getItem(idItem, objeto.getPai());
						}
						vinculacoesChecklistBO.delete(it);
						vinculacoesChecklistBO.ajustarOrdem(objeto, objeto.getPai());
						
						vinculacoesChecklistBO.setHierarquiaPais(objeto);
					}
					
					if(checklists != null && !checklists.isEmpty()) {
						Integer idChecklist;
						Integer idPai;
						for(ChecklistServicoVerificacaoProduto ch : checklists) {
							idChecklist = ch.getNuChecklistServicoVerificacaoProduto();
							idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
							
							if(idPai != null) {
								ItemVerificacaoChecklistProduto itemVinculacao = vinculacoesChecklistBO.getItem(idItem, idPai);
								
								if(itemVinculacao != null) {
									if(vinculacoesChecklistBO.isChecklistPublicado(idChecklist)) {
										idChecklist = checklistBO.duplicateChecklist(idChecklist);
										idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
										
										itemVinculacao = vinculacoesChecklistBO.getItem(idItem, idPai);
									}
									
									vinculacoesChecklistBO.delete(itemVinculacao);
									vinculacoesChecklistBO.ajustarOrdem(objeto, idPai);
								}
							}
						}
					}				
				}
			}
		} else if(objeto.isNivelApontamento()) {
			List<Integer> ids = objeto.getVinculadas();
			
			if(ids != null && !ids.isEmpty()) {
				Integer idApontamento = ids.get(0);
								
				ApontamentoChecklistProduto apontamentoVinculacao = vinculacoesChecklistBO.getApontamento(idApontamento, objeto.getPai());
				
				if(apontamentoVinculacao != null) {
					if(vinculacoesChecklistBO.isChecklistPublicado(objeto.getChecklistPrincipal())) {
						objeto.setChecklistPrincipal(checklistBO.duplicateChecklist(objeto.getChecklistPrincipal()));
						vinculacoesChecklistBO.setHierarquiaPais(objeto);
						objeto.setPai(vinculacoesChecklistBO.getHierarquiaPaiId(objeto, objeto.getChecklistPrincipal()));
						
						apontamentoVinculacao = vinculacoesChecklistBO.getApontamento(idApontamento, objeto.getPai());
					}
					vinculacoesChecklistBO.delete(apontamentoVinculacao);
					vinculacoesChecklistBO.ajustarOrdem(objeto, objeto.getPai());
					
					vinculacoesChecklistBO.setHierarquiaPais(objeto);
				}
				
				if(checklists != null && !checklists.isEmpty()) {
					Integer idChecklist;
					Integer idPai;
					
					for(ChecklistServicoVerificacaoProduto ch : checklists) {
						idChecklist = ch.getNuChecklistServicoVerificacaoProduto();
						idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
						
						if(idPai != null) {
							ApontamentoChecklistProduto aptoVinculacao = vinculacoesChecklistBO.getApontamento(idApontamento, idPai);
							
							if(aptoVinculacao != null) {
								if(vinculacoesChecklistBO.isChecklistPublicado(idChecklist)) {
									idChecklist = checklistBO.duplicateChecklist(idChecklist);
									idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
									
									aptoVinculacao = vinculacoesChecklistBO.getApontamento(idApontamento, idPai);
								}
								
								vinculacoesChecklistBO.delete(aptoVinculacao);
								vinculacoesChecklistBO.ajustarOrdem(objeto, idPai);								
							}
						}
					}
				}
			}
		} else {
			List<Integer> ids = objeto.getVinculadas();
			
			if(ids != null && !ids.isEmpty()) {
				Integer idRestricao = ids.get(0);
				
				if(idRestricao != null) {
					RestricaoApontamento restricaoVinculacao = vinculacoesChecklistBO.getRestricao(idRestricao, objeto.getPai());
					
					if(restricaoVinculacao != null) {
						if(vinculacoesChecklistBO.isChecklistPublicado(objeto.getChecklistPrincipal())) {
							objeto.setChecklistPrincipal(checklistBO.duplicateChecklist(objeto.getChecklistPrincipal()));
							objeto.setPai(vinculacoesChecklistBO.getHierarquiaPaiId(objeto, objeto.getChecklistPrincipal()));
							
							restricaoVinculacao = vinculacoesChecklistBO.getRestricao(idRestricao, objeto.getPai());
						}
						
						vinculacoesChecklistBO.delete(restricaoVinculacao);
						vinculacoesChecklistBO.ajustarOrdem(objeto, objeto.getPai());
					}
				}
				
				if(checklists != null && !checklists.isEmpty()) {
					Integer idChecklist;
					Integer idPai;
					
					for(ChecklistServicoVerificacaoProduto ch : checklists) {	
						idChecklist = ch.getNuChecklistServicoVerificacaoProduto();
						idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
						
						if(idPai != null) {
							RestricaoApontamento restricaoVinculacao = vinculacoesChecklistBO.getRestricao(idRestricao, idPai);
							
							if(restricaoVinculacao != null) {
								if(vinculacoesChecklistBO.isChecklistPublicado(idChecklist)) {
									idChecklist = checklistBO.duplicateChecklist(idChecklist);
									idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
									
									restricaoVinculacao = vinculacoesChecklistBO.getRestricao(idRestricao, idPai);
								}
								
								vinculacoesChecklistBO.delete(restricaoVinculacao);
								vinculacoesChecklistBO.ajustarOrdem(objeto, idPai);
							}
						}
					}
				}
			}
		}
		
		return objeto.getChecklistPrincipal();
	}

	@Transactional
	public Integer executarRotinaUpdateVinculacoes(VinculacoesChecklistVO objeto, List<ChecklistServicoVerificacaoProduto> checklists) {
		if(objeto.isNivelBloco()) {
			List<Integer> ids = objeto.getVinculadas();
			
			if(ids != null && !ids.isEmpty()) {
				Integer idBloco = ids.get(0);
								
				if(idBloco != null) {
					//aqui grava o registro atual (da tela)
					BlocoChecklistProduto bl = vinculacoesChecklistBO.getBloco(idBloco, objeto.getChecklistPrincipal());
					
					if (bl != null) {
						if(vinculacoesChecklistBO.isChecklistPublicado(objeto.getChecklistPrincipal())){
							objeto.setChecklistPrincipal(checklistBO.duplicateChecklist(objeto.getChecklistPrincipal()));
							objeto.setPai(objeto.getChecklistPrincipal());
							
							bl = vinculacoesChecklistBO.getBloco(idBloco, objeto.getChecklistPrincipal());
						}
						
						vinculacoesChecklistBO.merge(bl);
					}
					
					if (checklists != null && checklists.size() != 0) {
						Integer idChecklist;
						for(ChecklistServicoVerificacaoProduto ch : checklists) {
							idChecklist = ch.getNuChecklistServicoVerificacaoProduto();
							BlocoChecklistProduto blocoVinculacao = vinculacoesChecklistBO.getBloco(idBloco, ch.getNuChecklistServicoVerificacaoProduto());
							
							if(blocoVinculacao != null) {
								blocoVinculacao.setDescricao(bl.getDescricao());
								blocoVinculacao.setIcDesabilita(bl.getIcDesabilita());
								blocoVinculacao.setIcObrigatorio(bl.getIcObrigatorio());
								blocoVinculacao.setIcPrejudicado(bl.getIcPrejudicado());
								
								if(vinculacoesChecklistBO.isChecklistPublicado(idChecklist)){
									idChecklist = checklistBO.duplicateChecklist(idChecklist);
									blocoVinculacao = vinculacoesChecklistBO.getBloco(idBloco, idChecklist);
								}
								
								vinculacoesChecklistBO.merge(blocoVinculacao);
								vinculacoesChecklistBO.ajustarOrdem(objeto, idChecklist);
							}
						}
					}
				}
			}
		} else if(objeto.isNivelItem()) {
			List<Integer> ids = objeto.getVinculadas();
					
			if(ids != null && !ids.isEmpty()) {
				Integer idItem = ids.get(0);
				
				if(idItem != null) {
					ItemVerificacaoChecklistProduto it = vinculacoesChecklistBO.getItem(idItem, objeto.getPai());
					
					if(it != null) {
						if(vinculacoesChecklistBO.isChecklistPublicado(objeto.getChecklistPrincipal())){
							objeto.setChecklistPrincipal(checklistBO.duplicateChecklist(objeto.getChecklistPrincipal()));
							vinculacoesChecklistBO.setHierarquiaPais(objeto);
							objeto.setPai(vinculacoesChecklistBO.getHierarquiaPaiId(objeto, objeto.getChecklistPrincipal()));
							
							it = vinculacoesChecklistBO.getItem(idItem, objeto.getPai());
						}
						vinculacoesChecklistBO.merge(it);
						
						vinculacoesChecklistBO.setHierarquiaPais(objeto);
					}
					
					if(checklists != null && !checklists.isEmpty()) {
						Integer idChecklist;
						Integer idPai;
						for(ChecklistServicoVerificacaoProduto ch : checklists) {
							idChecklist = ch.getNuChecklistServicoVerificacaoProduto();
							idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
							
							if(idPai != null) {
								ItemVerificacaoChecklistProduto itemVinculacao = vinculacoesChecklistBO.getItem(idItem, idPai);
								
								if(itemVinculacao != null) {
									itemVinculacao.setDeItemVerificacaoChecklistProduto(it.getDeItemVerificacaoChecklistProduto());
									
									if(vinculacoesChecklistBO.isChecklistPublicado(idChecklist)) {
										idChecklist = checklistBO.duplicateChecklist(idChecklist);
										idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
										
										itemVinculacao = vinculacoesChecklistBO.getItem(idItem, idPai);
									}
									
									itemVinculacao.setIcDesabilitaItem(it.getIcDesabilitaItem());
									
									vinculacoesChecklistBO.merge(itemVinculacao);
									vinculacoesChecklistBO.ajustarOrdem(objeto, idPai);
								}
							}
						}
					}				
				}
			}
		} else if(objeto.isNivelApontamento()) {
			List<Integer> ids = objeto.getVinculadas();
			
			if(ids != null && !ids.isEmpty()) {
				Integer idApontamento = ids.get(0);
								
				ApontamentoChecklistProduto apt = vinculacoesChecklistBO.getApontamento(idApontamento, objeto.getPai());
				
				if(apt != null) {
					if(vinculacoesChecklistBO.isChecklistPublicado(objeto.getChecklistPrincipal())) {
						objeto.setChecklistPrincipal(checklistBO.duplicateChecklist(objeto.getChecklistPrincipal()));
						vinculacoesChecklistBO.setHierarquiaPais(objeto);
						objeto.setPai(vinculacoesChecklistBO.getHierarquiaPaiId(objeto, objeto.getChecklistPrincipal()));
						
						apt = vinculacoesChecklistBO.getApontamento(idApontamento, objeto.getPai());
					}
					vinculacoesChecklistBO.merge(apt);
										
					vinculacoesChecklistBO.setHierarquiaPais(objeto);
				}
				
				if(checklists != null && !checklists.isEmpty()) {
					Integer idChecklist;
					Integer idPai;
					
					for(ChecklistServicoVerificacaoProduto ch : checklists) {
						idChecklist = ch.getNuChecklistServicoVerificacaoProduto();
						idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
						
						if(idPai != null) {
							ApontamentoChecklistProduto aptoVinculacao = vinculacoesChecklistBO.getApontamento(idApontamento, idPai);
							
							if(aptoVinculacao != null) {
								aptoVinculacao.setDescricao(apt.getDescricao());
								aptoVinculacao.setIcNaoAplica(apt.getIcNaoAplica());
								
								if(vinculacoesChecklistBO.isChecklistPublicado(idChecklist)) {
									idChecklist = checklistBO.duplicateChecklist(idChecklist);
									idPai = vinculacoesChecklistBO.getHierarquiaPaiId(objeto, idChecklist);
									
									aptoVinculacao = vinculacoesChecklistBO.getApontamento(idApontamento, idPai);
								}
								
								vinculacoesChecklistBO.merge(aptoVinculacao);
								vinculacoesChecklistBO.ajustarOrdem(objeto, idPai);								
							}
						}
					}
				}
			}
		}
		
		return objeto.getChecklistPrincipal();
	}
	
	@Transactional
	public Integer saveBlocoNoChecklist(VinculacoesChecklistVO objeto, List<ChecklistServicoVerificacaoProduto> checklists) {
		if(objeto.getVinculadas() != null && !objeto.getVinculadas().isEmpty()){
			Integer idBloco = objeto.getVinculadas().get(0);
			if(vinculacoesChecklistBO.isChecklistPublicado(objeto.getChecklistPrincipal())){
				objeto.setChecklistPrincipal(checklistBO.duplicateChecklist(objeto.getChecklistPrincipal()));
				objeto.setPai(objeto.getChecklistPrincipal());
			}
			vinculacoesChecklistBO.save(objeto, vinculacoesChecklistBO.getBlocoLastPosition(objeto.getChecklistPrincipal()) + 1);			
			
			if(checklists != null && !checklists.isEmpty()) {
				for(int i = 0; i < checklists.size(); i++) {
					Integer idChecklist = checklists.get(i).getNuChecklistServicoVerificacaoProduto();
					BlocoChecklistProduto bloco = vinculacoesChecklistBO.getBloco(idBloco, idChecklist);
					if(bloco == null){
						if(vinculacoesChecklistBO.isChecklistPublicado(idChecklist)){
							idChecklist = checklistBO.duplicateChecklist(idChecklist);
						}
						vinculacoesChecklistBO.saveReplicas(objeto, idChecklist);
					}
				}
			}
		}
		return objeto.getChecklistPrincipal();
	}
}