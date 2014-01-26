package br.gov.caixa.siiac.persistence.dao;

import java.util.Date;
import java.util.List;

import br.gov.caixa.siiac.model.domain.Contrato;

public interface IAtualizaPrazoVerificacaoDAO extends IGenericDAO<Contrato> {

	/**
	 * Marca os contratos que não tiverem verificações a serem realizadas como OK.
	 * @param nuContrato
	 */
	void updateContratoVerificadoComoOk(Integer nuContrato);

	@SuppressWarnings("rawtypes")
	List getContratoNaoVerificado(Integer nuContrato);
	
	Date getMenorDataLimiteServicoVerificacao(Integer nuContrato);

	/**
	 * @param object
	 * @param qtPrazo
	 */
	void updatePrazoContratoNaoVerificado(Integer nuContrato, Short qtPrazo);
}