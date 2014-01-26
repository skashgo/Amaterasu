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

import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;

public interface IEfetuaVerificacaoDAO extends IGenericDAO<VerificacaoContrato> {
	
	/**
	 * @param verificacaoContratoVO
	 * @return
	 */
	ChecklistServicoVerificacaoProduto getMontaArvoreChecklist(VerificacaoContratoVO verificacaoContratoVO);

	/**
	 * @param nuVerificacaoContrato
	 */
	Object[] validaParaParecer(Integer nuVerificacaoContrato);

	/**
	 * Atualiza todos os apontamentos desta verificação que estiverem com estado de não verificado, para o estado de conforme.
	 * @param nuVerificacaoContrato
	 */
	void setApontamentoNaoVerificadoToConforme(Integer nuVerificacaoContrato);

	/**
	 * Insere itens na tabela de observações (iacsm001.iactb056_vrfco_cntro_obsro) que não estavam na tabela para marcá-los como conforme.
	 * @param nuVerificacaoContrato
	 */
	void insertItemConformeTabelaObservacoes(Integer nuVerificacaoContrato);
	
	/**
	 * Atualiza itens na tabela de observações (iacsm001.iactb056_vrfco_cntro_obsro) que não estavam marcados como conforme.
	 * @param nuVerificacaoContrato
	 */
	void updateItemConformeTabelaObservacoes(Integer nuVerificacaoContrato);

	/**
	 * @param nuVerificacaoContrato
	 * @return
	 */
	Boolean isConforme(Integer nuVerificacaoContrato);
	
	Integer validaDataItemImportaVerificacao(Integer nuVerificacaoContrato);
	
	Integer validaCheckListImportaVerificacao(Integer nuVerificacaoProduto);

	/**
	 * @param nuVerificacaoContrato
	 * @return
	 */
	List validaDataObrigatoria(Integer nuVerificacaoContrato);

	/**
	 * Verifica se o bloco prejudicado possui algum apontamento inconforme. Caso positivo esta verificação será prejudicada.
	 * @param nuBlocoChecklistProduto
	 * @param nuVerificacaoContrato
	 * @return
	 */
	boolean isVerificacaoPrejudicada(Integer nuBlocoChecklistProduto, Integer nuVerificacaoContrato);
	
	/**
	 * Verifica se o parecer da verificação foi emitido como uma análise prejudicada, ou seja, 
	 * que o bloco marcado como análise prejudicada possui alguma inconformidade.
	 * @param nuVerificacaoContrato
	 * @return
	 */
	boolean isVerificacaoParecerPrejudicada(Integer nuVerificacaoContrato);

	/**
	 * @param nuVerificacaoContrato
	 * @return
	 */
	Integer buscaPrimeiroBlocoPrejudicado(Integer nuVerificacaoContrato);
}