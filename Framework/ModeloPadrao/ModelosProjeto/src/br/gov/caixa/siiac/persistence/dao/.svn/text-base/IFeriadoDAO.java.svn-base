package br.gov.caixa.siiac.persistence.dao;

import java.util.Date;
import java.util.List;

import br.gov.caixa.siiac.model.domain.Feriado;

public interface IFeriadoDAO extends IGenericDAO<Feriado> {

	/**
	 * @param time
	 * @param unidade
	 * @param unidade2 
	 * @return
	 */
	boolean isFeriado(Date data, Integer unidadeNatural, Short unidade);

	/**
	 * @param unidade
	 * @param natural
	 * @param menorData
	 * @param dataSistema
	 * @return
	 */
	Integer obtemFeriadosUnidade(Short unidade, Integer natural, Date data1, Date data2);

	/**
	 * @param unidade
	 * @param qUANTIDADE_DIAS
	 * @return
	 */
	List<String> getNextFeriados(Short unidade, Integer nuNatural, Integer qtdDias);
	 
}