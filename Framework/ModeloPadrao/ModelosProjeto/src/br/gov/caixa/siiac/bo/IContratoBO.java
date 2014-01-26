
package br.gov.caixa.siiac.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.util.FilterBase;
import br.gov.caixa.siiac.view.mb.pagination.ParamPagination;

public interface IContratoBO {

//	List<Contrato> getListFiltro(Contrato contrato, IUsuario usuarioLogado, Short perfilUsuario, BigDecimal... faixaValorNominal) throws DAOException;

	Contrato save(Contrato contrato) throws DAOException;
	
	Contrato findById(Integer nuContrato) throws DAOException;
	
	boolean validaContrato(Contrato contrato);
	
	List<Contrato> getLikeCoContrato(String valueOf, Short nuUnidade);
	
	List<Produto> getLikeProduto(String value);
	
	List<Produto> getLikeProdutoByCategoria(String value, Integer categoria);

	/**
	 * @param pesquisaString
	 * @param paramPagination 
	 * @return
	 */
	List<Contrato> simpleFilter(IUsuario usuarioLogado, Short perfilUsuario, String pesquisaString, Boolean mostrarInativos, ParamPagination paramPagination) throws DAOException;
	List<Contrato> advancedFilter(Contrato contrato, Short campoOperacao, IUsuario usuarioLogado, Short perfilUsuario, 
									Boolean mostrarInativos, ParamPagination paramPagination, BigDecimal... faixaValorNominal) throws DAOException;
	
	public List<Contrato> advancedFilter(FilterBase filtro, IUsuario usuarioLogado, Short perfilUsuario,  ParamPagination paramPagination) throws DAOException;
	
	Character verificaCamposAlteradosAgenda(Contrato contratoAntigo, Contrato novoContrato);
	
	List<Contrato> findByUnidade(Short nuUnidade);
	
	public void inicializaLista(Contrato contrato);
	
	public String getQuantidadeVerificacoesAvGestaoSuretCategoriaProduto(Integer categoria, Date data);
	
	public String getQuantidadeVerificacoesVencendoCategoriaProduto(Integer categoria);
	

	/**
	 * @param usuarioLogado
	 * @param perfilUserLogado
	 * @param pesquisaString
	 * @param pesquisaMostraInativos
	 * @param paramPagination 
	 * @return
	 */
	int simpleFilterCount(IUsuario usuarioLogado, Short perfilUserLogado, String pesquisaString, Boolean pesquisaMostraInativos, ParamPagination paramPagination);

	/**
	 * @param usuarioLogado
	 * @param perfilUserLogado
	 * @param pesquisaString
	 * @param pesquisaMostraInativos
	 * @return
	 */
	int advancedFilterCount(Contrato contrato, Short campoOperacao, IUsuario usuarioLogado, Short perfilUsuario, Boolean mostrarInativos, ParamPagination paramPagination, BigDecimal... faixaValorNominal);
	public int advancedFilterCount(FilterBase filtro, IUsuario usuarioLogado, Short perfilUsuario,  ParamPagination paramPagination);
}