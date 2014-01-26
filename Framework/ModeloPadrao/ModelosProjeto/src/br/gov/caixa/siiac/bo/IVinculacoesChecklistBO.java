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
package br.gov.caixa.siiac.bo;

import br.gov.caixa.siiac.model.VinculacoesChecklistVO;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.model.domain.RestricaoApontamento;

/**
 * @author GIS Consult
 *
 */
public interface IVinculacoesChecklistBO {

	/**
	 * Método genérico para deleção dos seguintes tipos de objetos:
	 * BLOCO_CHECKLIST_PRODUTO, ITEM_VERIFICACAO_CHECKLIST_PRODUTO,
	 * RESTRICAO_APONTAMENTO
	 * 
	 * @param objeto
	 */
	void delete(Object objeto);

	void merge(Object objeto);
	
	void deleteNotInList(VinculacoesChecklistVO objeto);
	
	BlocoChecklistProduto getBloco(Integer idBloco, Integer idChecklist);
	
	ItemVerificacaoChecklistProduto getItem(Integer idItem, Integer idVinculacaoBloco);
	
	ApontamentoChecklistProduto getApontamento(Integer idApontamento, Integer idVinculacaoItem);
	
	RestricaoApontamento getRestricao(Integer idRestricao, Integer idVinculacaoApontamento);
	
	void save(VinculacoesChecklistVO objeto);

	void save(VinculacoesChecklistVO objeto, Integer cont);
	
	void setHierarquiaPais(VinculacoesChecklistVO objeto);
	
	/**
	 * Este método cria toda a hierarquia da árvore dentro de um checklist para replicar alguma vinculação caso o a vinculação necessária não exista.
	 * Por exemplo: Foi inserido a restrição R no apontamento X, vinculado ao item Y vinculado ao bloco B.
	 * 	Para replicar no outro checklist - caso essa hierarquia completa não exista - ela é criada
	 * 		(Vincular o bloco B ao checklist, vincular o item Y ao bloco B, vincular o apontamento X ao item Y).  
	 * @param objeto
	 * @param idChecklist
	 * @return
	 */
	Integer adjustHierarquiaReplicas(VinculacoesChecklistVO objeto, Integer idChecklist);
	
	/**
	 * Método que busca qual o id da vinculação em outro checklist.
	 * Por exemplo:
	 * 	Supondo que a restrição R fosse inserida no apontamento X fosse vinculado ao item Y vinculado ao bloco B.
	 * 	Este método buscará qual o id de vinculação do apontamento X com o item Y filho do bloco B dentro do checklist parâmetro. 
	 * @param objeto
	 * @param idChecklist
	 * @return
	 */
	Integer getHierarquiaPaiId(VinculacoesChecklistVO objeto, Integer idChecklist);

	/**
	 * Exclui réplicas de objetos em outros checklists.
	 * @param objeto
	 * @param idPai
	 */
	void deleteReplicas(VinculacoesChecklistVO objeto, Integer idPai);

	/**
	 * Salva réplicas de objetos em outros checklists.
	 * @param objeto
	 * @param idPai
	 */
	void saveReplicas(VinculacoesChecklistVO objeto, Integer idPai);
	
	/**
	 * Reordena os objetos de acordo com a ordem que eles foram preenchidos na lista contida no objeto VinculacoesChecklistVO.
	 * @param objeto
	 * @param idPai
	 */
	void reordenarObjetos(VinculacoesChecklistVO objeto, Integer idPai);
	
	/**
	 * Valida se o nível em questão do objeto VinculacoesChecklistVO deve ser reordenado.
	 * @param objeto
	 * @param idPai
	 * @return
	 */
	boolean deveReordenar(VinculacoesChecklistVO objeto, Integer idPai);
	
	/**
	 * Ajusta a ordem dos objetos de acordo com o VinculacoesChecklistVO parâmetro.
	 * 	Ex: Supondo que no VinculacoesChecklistVO parâmetro, o nível é de apontamentos vinculados a um item em específico.
	 * 		Esta rotina irá reorganizar estes apontamentos de modo que a ordem fique corretamente em 1,2,3... 
	 * 			em vez de 1,2,4 caso o 3 tenha sido excluído, por exemplo.
	 * @param objeto
	 * @param idPai
	 */
	void ajustarOrdem(VinculacoesChecklistVO objeto, Integer idPai);

	/**
	 * Retorna se o checklist parâmetro está publicado.
	 * @param idChecklist
	 * @return
	 */
	boolean isChecklistPublicado(Integer idChecklist);
	
	/**
	 * Retorna a maior posição de um bloco dentro de um checklist.
	 *  Ex: Se um checklist possui três blocos, o retorno será 3.
	 * @param idChecklist
	 * @return
	 */
	Integer getBlocoLastPosition(Integer idChecklist);
}
