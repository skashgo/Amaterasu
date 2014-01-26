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

import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.exception.ReportErrorCreateDatasourceException;
import br.gov.caixa.siiac.exception.ReportFinalNullException;
import br.gov.caixa.siiac.exception.ReportInvalidPathException;
import br.gov.caixa.siiac.model.domain.BlocoChecklist;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.util.FilterBase;

public interface IChecklistBO {

	public void delete(ChecklistServicoVerificacaoProduto checklist)
			throws DAOException;

	/**
	 * Verifica se determinado checklist já existe baseado em seu
	 * servicoVerificacaoProduto e situação.
	 * 
	 * @param checklist
	 * @return lista de checklistServicoVerificacao
	 * @throws DAOException
	 */
	public List<ChecklistServicoVerificacaoProduto> findByChecklist(
			ChecklistServicoVerificacaoProduto checklist) throws DAOException;

	
	/**
	 * Filtra os checklistServicoVerificacaoProduto baseado na operacao do
	 * produto, codigo do produto, codigo do servico de verificacao, situacao,
	 * data de inicio e data final do checklist.
	 * @param filtro - Objeto de filtro contentendo os valores dos filtros.
	 * @return
	 * @throws DAOException
	 */
	public List<ChecklistServicoVerificacaoProduto> filter(FilterBase filter) throws DAOException;

	/**
	 * Busca o checklist com base em seu id.
	 * 
	 * @param id
	 * @return
	 */
	public ChecklistServicoVerificacaoProduto findById(Integer id);

	/**
	 * Retorna todos os ChecklistServicoVerificacaoProduto existentes na base de
	 * dados.
	 * 
	 * @return lista de checklistServicoVerificacaoProduto
	 */
	public List<ChecklistServicoVerificacaoProduto> getAll();

	public ChecklistServicoVerificacaoProduto merge(
			ChecklistServicoVerificacaoProduto checklistSvp)
			throws DAOException;
	
	public void updateSituacaoChecklist() throws DAOException;
	
	/**
	 * Adiciona um BlocoChecklist ao Checklist.
	 * @param blocos
	 * @param codBloco
	 * @param checklist
	 */
	public void adicionaBlocoNoChecklist(List<BlocoChecklist> blocos, Integer codBloco, ChecklistServicoVerificacaoProduto checklist);
	
	/**
	 * Revoga o checklist passado como parâmetro.
	 * @param id
	 * @throws DAOException
	 */
	public void revogarChecklist(Integer id) throws DAOException;
	
	public List<ChecklistServicoVerificacaoProduto> getAllNotIn(Integer cod, String situacao) throws DAOException;
	
	public Integer duplicateChecklist(Integer idChecklist);
	
	public void duplicateFilhosChecklist(ChecklistServicoVerificacaoProduto checklistNew, Integer idVelho);

	/**
	 * Valida se a data inicio digitada é a mesma que no banco de dados
	 * @return
	 */
	public boolean validaDataInicio(ChecklistServicoVerificacaoProduto checklist);
	
	public void replaceChecklist(ChecklistServicoVerificacaoProduto checklistNew, Integer idVelho);

	/**
	 * @param pesquisaString
	 * @param pesquisaMostraInativos
	 * @return
	 */
	public List<ChecklistServicoVerificacaoProduto> simpleFilter(FilterBase filtro);
	public List<ChecklistServicoVerificacaoProduto> simpleFilter();
	
	/**
	 * Verifica se o checklist pode ser autorizado
	 * @param checklist a ser pesquisado
	 * @return true se pode ser autorizado ou false caso contrário
	 */
	boolean podeAutorizar(ChecklistServicoVerificacaoProduto checklist);
	
	/**
	 * Verifica se o checklist pode ser publicado
	 * @param checklist a ser pesquisado
	 * @return true se pode ser publicado ou false caso contrário
	 */
	boolean podePublicar(ChecklistServicoVerificacaoProduto checklist);
	
	/**
	 * Publica um checklist
	 * @param checklist a ser publicado
	 */
	void publica(ChecklistServicoVerificacaoProduto checklist);

	/**
	 * Autoriza um checklist
	 * @param checklist a ser autorizado
	 */
	void autoriza(ChecklistServicoVerificacaoProduto checklist);

	/**
	 * Valida a regra (Para ser autorizado, um checklist deve ter pelo menos um bloco, cada bloco com pelo menos um item e cada item com pelo menos um apontamento) 
	 * @param checklist 
	 * @return
	 */
	public boolean validaChecklistParaAutorizacao(ChecklistServicoVerificacaoProduto checklist);

	/**
	 * Este método é utilizado para retornar o checklist para o estado de PROJETO.
	 * @param id
	 */
	public void retornarParaProjeto(Integer idChecklist);

	/**
	 * @param coProduto
	 * @param nuServicoVerificacao
	 * @return
	 */
	public boolean existByProdutoEServicoVerificacao(String coProduto, Integer nuServicoVerificacao, Date date);

	/**
	 * @param caminhoRelatorio
	 * @param matricula
	 * @param nuChecklistServicoVerificacaoProduto
	 * @return
	 * @throws JRException 
	 * @throws ReportFinalNullException 
	 * @throws ReportErrorCreateDatasourceException 
	 * @throws ReportInvalidPathException 
	 * @throws DAOException 
	 */
	public byte[] geraRelatorio(String caminhoRelatorio, String matricula, Integer nuChecklistServicoVerificacaoProduto) throws DAOException, ReportInvalidPathException, ReportErrorCreateDatasourceException, ReportFinalNullException, JRException;

	/**
	 * @param produtos
	 * @return
	 */
	public List<ChecklistServicoVerificacaoProduto> getChecklistsByProdutoabrangentes(List<Produto> produtos);
	
}