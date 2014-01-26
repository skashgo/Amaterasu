package br.gov.caixa.siiac.persistence.dao;

import java.math.BigDecimal;
import java.util.List;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Garantia;

public interface IGarantiaDAO extends IGenericDAO<Garantia> {
	
	public List<Garantia> getListFiltro(Garantia garantia, Boolean pesquisaMostraInativos, BigDecimal[] valor, List<Short> listUnidadesAbrangencia, List<String> listCoProdutoAbrangencia);
	
	public void inativar(Garantia garantia);

	public boolean existGarantia(Integer coGarantia, Integer nuGarantia);
	
	List<Garantia> getAllGarantiasInContrato(Contrato contrato) throws DAOException;

	public List<Garantia> getListFiltroSimples(String pesquisaString, Boolean pesquisaMostraInativos, List<Short> listUnidadesAbrangencia, List<String> listCoProdutoAbrangencia);
}