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
import br.gov.caixa.siiac.model.domain.ItemVerificacao;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;

/**
 * @author GisConsult
 * 
 */
public interface IServicoVerificacaoBO {

	/**
	 * Busca todos os servicos de verificacao ativos.
	 * 
	 * @return todos os servicos de verificacao ativos no banco
	 */
	List<ServicoVerificacao> getAllAtivos();

	/**
	 * Busca todos os servicos de verificacao inativos.
	 * 
	 * @return todos os servicos de verificacao inativos no banco
	 */
	List<ServicoVerificacao> getAllInativos();

	/**
	 * Persiste determinado servico de verificacao
	 * 
	 * @param servicoVerificacao
	 */
	void salvar(ServicoVerificacao servicoVerificacao);

	/**
	 * Busca determinado servico de verificacao.
	 * 
	 * @param servicoVerificacao
	 * @return uma lista que devera conter apenas um item
	 */
	List<ServicoVerificacao> get(ServicoVerificacao servicoVerificacao);

	/**
	 * Busca determinado servico de verificacao.
	 * @param nuServicoVerificacaoCombo
	 * @return
	 */
	ServicoVerificacao get(Integer nuServicoVerificacaoCombo);

	/**
	 * Verifica se determinado servico de verificacao ja esta contido no banco
	 * de dados.
	 * 
	 * @param servicoVerificacao
	 * @return true se o servico de verificacao ja existe ou false caso n�o
	 *         exista
	 */
	boolean jaExiste(ServicoVerificacao servicoVerificacao);
	
	/**
	 * Busca uma lista de servicos de verificacao.
	 * @param servicoVerificacao
	 * @param cadastro
	 * @return lista de servicos de verificacao
	 */
	List<ServicoVerificacao> realizaConsulta(ServicoVerificacao servicoVerificacao, boolean cadastro);
	
	/**
	 * Ativa determinado servico de verificacao
	 * 
	 * @param servicoVerificacao
	 */
	void ativar(ServicoVerificacao servicoVerificacao);

	/**
	 * Inativa determinado servico de verificacao
	 * 
	 * @param servicoVerificacao
	 */
	void inativar(ServicoVerificacao servicoVerificacao);

	/**
	 * Atualiza o servico de verificacao.
	 * 
	 * @param servicoVerificacao
	 */
	void update(ServicoVerificacao servicoVerificacao);

	/**
	 * Retorna a lista de ServicoVerificacao que nao esteja vinculado ao produto
	 * parametro.
	 * 
	 * @param produto
	 * @return lista de servicos
	 */
	List<ServicoVerificacao> getAllServicoNotProduto(Produto produto);

	/**
	 * Valida se o Servicoverificacao esta ativo na base.
	 * @param nuServicoVerificacaoCombo
	 * @return
	 */
	boolean isAtivo(Integer nuServicoVerificacaoCombo);
	
	public List<ServicoVerificacao> getAllServicoProduto(Produto produto);

	/**
	 * Retorna a lista de ServicoVerificacao que tem seu produto relacionado com usuario e que nao esta marcado como servico preferencial.
	 * @param matricula
	 * @return
	 */
	List<ServicoVerificacao> getAllNaoPreferenciais(String matricula);
	

	/**
	 * @param pesquisaString
	 * @param pesquisaMostraInativos
	 * @return
	 */
	List<ServicoVerificacao> getListFiltro(String pesquisaString, Boolean pesquisaMostraInativos) throws SIIACException;
}
