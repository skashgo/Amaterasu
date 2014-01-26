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
package br.gov.caixa.siiac.persistence.dao;

import java.util.List;

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
public interface IVinculacoesChecklistDAO extends IGenericDAO<ChecklistServicoVerificacaoProduto> {
	
	void deleteVinculacaoBloco(BlocoChecklistProduto bloco);
	void updateVinculacaoBloco(BlocoChecklistProduto bloco);
	List<BlocoChecklistProduto> getListVinculacaoBloco(Integer idChecklist);
	boolean saveBloco(Integer idBloco, Integer idChecklist, Integer cont);
	Integer getBlocoLastPosition(Integer idChecklist);
	BlocoChecklistProduto getBloco(Integer idBloco, Integer idChecklist);
	
	void deleteVinculacaoItem(ItemVerificacaoChecklistProduto item);
	void updateVinculacaoItem(ItemVerificacaoChecklistProduto item);
	List<ItemVerificacaoChecklistProduto> getListVinculacaoItem(Integer idVinculacaoBloco);
	boolean saveItem(Integer idItem, Integer idVinculacaoBloco, Integer cont);
	Integer getItemLastPosition(Integer idVinculacaoBloco);
	ItemVerificacaoChecklistProduto getItem(Integer idItem, Integer idVinculacaoBloco);
	
	void deleteVinculacaoApontamento(ApontamentoChecklistProduto apontamento);
	void updateVinculacaoApontamento(ApontamentoChecklistProduto apontamento);
	List<ApontamentoChecklistProduto> getListVinculacaoApontamento(Integer idVinculacaoItem);
	boolean saveApontamento(Integer idApontamento, Integer idVinculacaoItem, Integer cont);
	Integer getApontamentoLastPosition(Integer idVinculacaoItem);
	ApontamentoChecklistProduto getApontamento(Integer idApontamento, Integer idVinculacaoItem);
	
	void deleteVinculacaoRestricao(RestricaoApontamento restricao);
	void updateVinculacaoRestricao(RestricaoApontamento restricao);
	List<RestricaoApontamento> getListVinculacaoRestricao(Integer idVinculacaoApontamento);
	boolean saveRestricao(Integer idApontamento, Integer idVinculacaoApontamento);
	RestricaoApontamento getRestricao(Integer idRestricao, Integer idVinculacaoApontamento);
	
	Integer getHierarquiaPaiId(VinculacoesChecklistVO objeto, Integer idChecklist);
}
