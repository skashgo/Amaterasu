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

import java.util.List;
import java.util.Map;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProduto;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoObservacoes;

public interface IEfetuaVerificacaoBO {
	
	/**
	 * Busca o nó da raiz (checklistServicoVerificacaoProduto) e atualiza as listas de bloco, item, apontamento
	 * @param verificacaoContratoVO
	 * @return
	 */
	ChecklistServicoVerificacaoProduto getMontaArvoreChecklist(VerificacaoContratoVO verificacaoContratoVO);
	
	ChecklistServicoVerificacaoProduto checklistByNuServico(Integer nuServico);
	
	boolean existeChecklistByNuServico(Integer nuServico);
	
	/**
	 * busca a observação de um verificação de contrato (item/bloco)
	 * @param idBloco
	 * @param observacaoBloco
	 * @param verificacaoContratoVO nuVerificacaoContrato
	 * @return
	 */
	VerificacaoContratoObservacoes getVerificacaoContratoObservacoes(Integer id, String observacaoBloco, VerificacaoContratoVO verificacaoContratoVO);
	
	/**
	 * Salva observação de um Bloco/Item
	 * @param verificacaoContratoObservacoes
	 */
	void saveBlocoItem(VerificacaoContratoObservacoes verificacaoContratoObservacoes) throws SIIACException;
	
	/**
	 * Salva observação de um apontamento
	 * @param ResultadoApontamentoProduto
	 */
	void saveApontamento(ResultadoApontamentoProduto resultadoApontamentoProduto) throws SIIACException;
	
	/**
	 * Busca um ResultadoApontamentoProduto pelo id
	 * @param verificacaoContratoVO 
	 * @param id
	 * @return
	 */
	Map<Integer, ResultadoApontamentoProduto> getResultadoApontamentoProduto(VerificacaoContrato verificacaoContrato, VerificacaoContratoVO verificacaoContratoVO);
	
	/**
	 * Busca um ApontamentoChecklistProduto por id
	 * @param idApontamentoCheclistProduto
	 * @return
	 */
	ApontamentoChecklistProduto getApontamentoChecklistProduto(Integer idApontamentoCheclistProduto);
	
	/**
	 * Busca um BlocoChecklistProduto por id
	 * @param id
	 * @return
	 */
	BlocoChecklistProduto findBlocoChecklistProdutoById(Integer id);
	
	/**
	 * Busca uma VerificacaoContrato por id
	 * @param nuVerificacaoContrato
	 * @return
	 */
	VerificacaoContrato findVerificacaoContratoById(Integer nuVerificacaoContrato);
	
	/**
	 * Busca um ItemVerificacaoChecklistProduto por id
	 * @param id
	 * @return
	 */
	ItemVerificacaoChecklistProduto findItemVerificacaoChecklistProdutoById(Integer id);
	
	/**
	 * exclui a observação de um bloco/item
	 * @param verificacaoContratoObservacoes
	 */
	void deleteBlocoItem(VerificacaoContratoObservacoes verificacaoContratoObservacoes) throws SIIACException;
	
	/**
	 * clona um vericacaoContrao (ou seja busca um vericacaoContratoParecer e copia todos seus atributos para um objeto verificacaoContrato)
	 * @param verificacaoContratoVO
	 * @return
	 */
	VerificacaoContrato clone(VerificacaoContratoVO verificacaoContratoVO);
	
	/**
	 * Cria uma VerificacaoContrato a partir de uma VerificacaoContratoParecer que esteja INCONFORME.
	 * @param verificacaoContrato
	 * @return
	 */
	VerificacaoContrato criaVerificacaoContratoFromVerificacaoContratoParecer(VerificacaoContrato verificacaoContrato, VerificacaoContratoVO verificacaoContratoVO);

	/**
	 * @param nuVerificacaoContrato
	 * @return 0 = verificação está ok
	 * 		   1 = data de validade do item é obrigatório, porém está NULL
	 * 		   2 = possui apontamentos não verificados
	 * 		   3 = não possui template conforme
	 * 		   4 = não possui template inconforme
	 */
	String validaParaParecer(Integer nuVerificacaoContrato);

	/**
	 * @param nuVerificacaoContrato
	 * @return
	 */
	Boolean isConforme(Integer nuVerificacaoContrato);
	
	/**
	 * 
	 * @param blocoChecklistProduto
	 */
	void iniciaVerificacaoContratoObservacoes (BlocoChecklistProduto blocoChecklistProduto);
	
	/**
	 * 
	 * @param nuVerificacaoContrato
	 * @return
	 */
	Integer validaDataItemImportaVerificacao(Integer nuVerificacaoContrato);
	
	Integer buscaChecklistAtualImportaVerificacao(Integer nuVerificacaoProduto);

	/**
	 * @param nuVerificacaoContrato
	 */
	void setNaoVerificadoToConforme(Integer nuVerificacaoContrato);

	/**
	 * Salva informações do cabeçalho da verificação - VerificacaoContrato
	 * @param verificacaoContrato
	 */
	void saveVerificacaoContratoCabecalho(VerificacaoContrato verificacaoContrato);

	/**
	 * @param nuServicoVerificacaoProduto
	 * @return
	 */
	Integer buscaChecklistVerificacaoAntiga(Integer nuServicoVerificacaoProduto);

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
	 */
	Integer buscaPrimeiroBlocoPrejudicado(Integer nuVerificacaoContrato);
}