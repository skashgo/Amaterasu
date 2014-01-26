package br.gov.caixa.siiac.bo;

import java.math.BigDecimal;
import java.util.List;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Garantia;

public interface IGarantiaBO {
	
	public List<Garantia> getListFiltro(Garantia garantia, Boolean inclusiveExcluida, BigDecimal[] valor);
	
	public Garantia save(Garantia garantia);
	
	public void inativar(Garantia garantia);
	
	public boolean existGarantia(Integer coGarantia, Integer nuGarantia);
	
	List<Garantia> getAllGarantiasInContrato(Contrato contrato) throws DAOException;
	
	public List<Garantia> getListFiltroSimples(String pesquisaString, Boolean pesquisaMostraInativos);
	
	Garantia findById(Integer nuGarantia);
}