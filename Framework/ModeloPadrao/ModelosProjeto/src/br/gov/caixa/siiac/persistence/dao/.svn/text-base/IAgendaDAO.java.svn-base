package br.gov.caixa.siiac.persistence.dao;

import java.util.List;
import java.util.Map;

import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.security.IUsuario;

public interface IAgendaDAO extends IGenericDAO<Contrato> {
	
	/**
	 *  retorna uma lista de Contrato (linhas das tabelas)
	 * @param filtrarPreferenciais
	 * @param rows 
	 * @param first 
	 * @param order 
	 * @param listCoProdutoAbrangencia
	 * @param listUnidadesAbrangencia
	 * @return
	 */
	List<Contrato> getListAgenda(boolean filtrarPreferenciais, IUsuario userLogado, Integer first, Integer rows, Map<String, Integer> order, List<Short> listUnidadesAbrangencia);
	
	/**
	 * Retorna a quantidade de contratos com o prazo definido pelo parametro
	 * @param nuContrato
	 */
	Integer getQuantidadeContratoByPrazo(Integer nuContrato, Character estado);

	/**
	 * Valida se o contrato que aparece em tela para ser verificado, realmente possui agenda de verificação.
	 * @param nuContrato
	 * @return
	 */
	boolean existeAgendaContrato(Integer nuContrato);
	public Integer getListAgendaCount(boolean filtrarPreferenciais, IUsuario userLogado, List<Short> listUnidadesAbrangencia);
}