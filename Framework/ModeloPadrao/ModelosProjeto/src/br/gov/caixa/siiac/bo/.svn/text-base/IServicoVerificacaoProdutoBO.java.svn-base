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

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;

public interface IServicoVerificacaoProdutoBO {

	/**
	 * Retorna o ServicoVerificacaoProduto de acordo com o id do ServicoVerificacaoProduto.
	 * @param nuServicoVerificacaoProduto
	 * @return
	 */
	ServicoVerificacaoProduto get(Integer nuServicoVerificacaoProduto);
	
	/**
	 * Retorna o ServicoVerificacaoProduto de acordo com o produto e o servicoVerificacao.
	 * @param nuServicoVerificacaoProduto
	 * @return
	 */
	ServicoVerificacaoProduto get(Produto prod, ServicoVerificacao sv);


	/**
	 * Retorna a lista de ServicoVerificacao de acordo com o produto passado como parametro.
	 * @param produto
	 * @return lista de ServicoVerificacao
	 */
	List<ServicoVerificacaoProduto> getAll(Produto produto);


	/**
	 * @param servicoVerificacaoProduto
	 */
	void salvar(ServicoVerificacaoProduto servicoVerificacaoProduto);


	/**
	 * Metodo que exclui o servicoVerificacaoProduto (altera sua dt_fim_vinculacao para a data atual do sistema)
	 * @param servicoVerificacaoProduto
	 */
	void excluir(ServicoVerificacaoProduto servicoVerificacaoProduto);
	
}
