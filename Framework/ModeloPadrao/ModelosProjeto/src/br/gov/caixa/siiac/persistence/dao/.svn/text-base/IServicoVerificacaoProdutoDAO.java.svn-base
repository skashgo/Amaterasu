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

import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;

/**
 * @author GisConsult
 *
 */
public interface IServicoVerificacaoProdutoDAO extends IGenericDAO<ServicoVerificacaoProduto> {

	/**
	 * Retorna a lista de ServicoVerificacao que tem seu produto relacionado com usuario e que nao esta marcado como servico preferencial. 
	 * @param matricula
	 * @return
	 */
//	List<ServicoVerificacaoProduto> getAll(String matricula);
	
	/**
	 * Retorna o ServicoVerificacaoProduto com base no produto e servico de verificacao passado como parametro.
	 * @param prod
	 * @param sv
	 * @return ServicoVerificacaoProduto
	 */
	ServicoVerificacaoProduto get(Produto prod, ServicoVerificacao sv);
}
