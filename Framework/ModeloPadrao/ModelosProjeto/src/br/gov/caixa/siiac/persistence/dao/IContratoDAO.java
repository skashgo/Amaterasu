
package br.gov.caixa.siiac.persistence.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.ContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.view.mb.pagination.ParamPagination;

public interface IContratoDAO extends IGenericDAO<Contrato> {


	public List<Contrato> getListFiltro(Contrato contrato, BigDecimal... faixa);
	List<Contrato> filtra(ContratoVO contratoVO, List<String> produtos, ParamPagination paramPagination);
	
	public List<Contrato> getLikeCoContrato(String coContrato, List<Short> unidades, List<String> produtos);
	
	List<Contrato> findByCoContrato(String coContrato) throws DAOException;
	
	List<Contrato> findByUnidade(Short nuUnidade) throws DAOException;
	
	public String getQuantidadeVerificacoesAvGestaoSuretCategoriaProduto(Integer categoria, Date data);
	
	public String getQuantidadeVerificacoesVencendoCategoriaProduto(Integer categoria);

	/**
	 * @param contratoVO
	 * @param listCoProdutoAbrangencia
	 * @param paramPagination
	 * @return
	 */
	public int simpleFilterCount(ContratoVO contratoVO, List<String> listCoProdutoAbrangencia, ParamPagination paramPagination);
	/**
	 * @param contratoVO
	 * @param listCoProdutoAbrangencia
	 * @param paramPagination
	 * @return
	 */
	public int advancedFilterCount(ContratoVO contratoVO, List<String> listCoProdutoAbrangencia, ParamPagination paramPagination);
	
}