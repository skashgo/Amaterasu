package br.gov.caixa.siiac.persistence.dao;

import java.util.Date;
import java.util.List;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProdutoParecer;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer;

public interface IGeraAgendaDAO extends IGenericDAO<Contrato> {

	/**
	 * Busca um contrato a gerar agenda de acordo com o nuContrato parâmetro. 
	 * Os campos retornados dentro de um Object[]
	 * 	{
	 * 		[0] = nu_contrato
	 * 		[1] = ic_agenda_gerada
	 * 		[2] = ic_tipo_verificacao_contrato
	 * 		[3] = nu_unidade_rspnl_contrato
	 * 		[4] = nu_natural_undde_rspnl_contrato
	 * 		[5] = dt_contrato
	 * 		[6] = co_produto
	 *  }
	 * @param nuContrato
	 * @return
	 */
	Object[] getContratoGerarAgenda(Integer nuContrato) throws Exception;
	
	/**
	 * Busca a lista de contratos que deverão ter sua agenda gerada.
	 * Os campos retornados dentro de um Object[]
	 * 	{
	 * 		[0] = nu_contrato
	 * 		[1] = ic_agenda_gerada
	 * 		[2] = ic_tipo_verificacao_contrato
	 * 		[3] = nu_unidade_rspnl_contrato
	 * 		[4] = nu_natural_undde_rspnl_contrato
	 * 		[5] = dt_contrato
	 * 		[6] = co_produto
	 *  }
	 * @return
	 */
	List<Object[]> getContratosAGerarAgenda() throws Exception;
	

	/**
	 * Busca o ServicoVerificacaoProduto parâmetro e que possua checklist.
	 * Os campos retornados dentro de um Object[]
	 * 	{
	 * 		[0] = nu_servico_verificacao_produto
	 * 		[1] = nu_chklst_srvco_vrfco_produto
	 * 		[2] = pz_verificacao
	 *  }
	 * @param nuServicoVerificacaoProduto
	 * @return
	 */
	Object[] getServicoVerificacaoProdutoGerarAgenda(Integer nuServicoVerificacaoProduto) throws SIIACException, Exception;
	
	/**
	 * Busca a lista de ServicoVerificacaoProduto de ação de inclusão do contrato parâmetro.
	 * Os campos retornados dentro de um Object[]
	 * 	{
	 * 		[0] = nu_servico_verificacao_produto
	 * 		[1] = nu_chklst_srvco_vrfco_produto
	 * 		[2] = pz_verificacao
	 *  }
	 * @param contrato
	 * @return
	 */
	List<Object[]> getServicosByContratoInclusao(String coProduto, Date dtContrato) throws SIIACException, Exception;


	/**
	 * Busca a lista de ServicoVerificacaoProduto de ação de verificação preventiva do contrato parâmetro.
	 * Os campos retornados dentro de um Object[]
	 * 	{
	 * 		[0] = nu_servico_verificacao_produto
	 * 		[1] = nu_chklst_srvco_vrfco_produto
	 * 		[2] = pz_verificacao
	 *  }
	 * @param contrato
	 * @return
	 */
	List<Object[]> getServicosByContratoVerificacaoPreventiva(String coProduto, Date dtContrato) throws SIIACException, Exception;
	
	/**
	 * Busca a lista de ServicoVerificacaoProduto de ação alteração que ainda não possuem verificação para o contrato parâmetro.
	 * @param contrato
	 * @return
	 */
	List<Object[]> getServicosSemVerificacaoByContratoAlteracao(Integer nuContrato, String coProduto, Date dtContrato, Character icAgendaGerada) throws SIIACException, Exception;
	
	/**
	 * Busca a lista de ServicoVerificacaoProduto de ação alteração que possuem verificação sem parecer para o contrato parâmetro.
	 * @param contrato
	 * @return
	 */
	List<Object[]> getServicosVerificacaoSemParecerByContratoAlteracao(Integer nuContrato, String coProduto, Character icAgendaGerada) throws SIIACException, Exception;

	/**
	 * Busca a lista de ServicoVerificacaoProduto de ação alteração que possuem verificação com parecer para o contrato parâmetro.
	 * @param contrato
	 * @return
	 */
	List<Object[]> getServicosVerificacaoComParecerByContratoAlteracao(Integer nuContrato, String coProduto, Character icAgendaGerada) throws SIIACException, Exception;

	/**
	 * Marca a verificação como "Não Verificada". 
	 *  @param verificacao
	 */
	void marcaVerificacaoComoNaoVerificada(Integer nuContrato, Integer nuServicoVerificacaoProduto);
	
	/**
	 * Marca os apontamentos da verificação como "Não Verificado". 
	 *  @param verificacao
	 */
	void marcaApontamentoVerificacaoComoNaoVerificado(Integer nuContrato, Integer nuServicoVerificacaoProduto);
	
	/**
	 * Apaga as observações da verificação. 
	 *  @param verificacao
	 */
	void apagaObservacoesVerificacao(Integer nuContrato, Integer nuServicoVerificacaoProduto);

	/**
	 * Atualiza o campo icUltimaHierarquia para FALSE da verificação anterior (parâmetro)
	 * @param verificacaoContratoParecer
	 */
	void atualizaUltimaHierarquiaVerificacaoAnterior(Integer nuContrato, Integer nuServicoVerificacaoProduto);

	/**
	 * Atualiza o contrato para marcar que sua agenda já foi gerada com sucesso.
	 * @param contrato
	 */
	void updateContratoAgendaGerada(Integer nuContrato);

	/**
	 * 
	 */
	Integer insereVerificacao(Integer nuContrato, Integer nuServicoVerificacaoProduto, Short nuUnidade, Integer nuNatural, Date dtInclusao, Date dtLimiteVerificacao, String icEstadoVerificacao, Integer nuVerificacaoContratoPai, Integer nuChecklist);


	/**
	 * Insere apontamentos da verificação.
	 * @param verificacao
	 * @param contrato
	 */
	void insereResultadoApontamento(Integer nuVerificacaoContrato, Integer nuContrato, Integer nuChecklist);
	
	/**
	 * @return
	 */
	Integer insereVerificacaoReplica(Integer nuContrato, Integer nuServicoVerificacaoProduto, Date dtInclusao, Date dtLimiteVerificacao, String icEstadoVerificacao);

	/**
	 * Insere apontamentos da verificação baseados na verificação anterior que possua parecer (nuContrato + nuServicoVerificacaoProduto).
	 * @param verificacao
	 * @param contrato
	 */
	void insereResultadoApontamentoReplica(Integer nuVerificacaoContrato, Integer nuContrato, Integer nuServicoVerificacaoProduto);

	/**
	 * Retorna o maior prazo de verificação cadastrado para ServicoVerificacaoProduto
	 * @return
	 */
	Integer getMaiorPrazoServicoVerificacao();

}