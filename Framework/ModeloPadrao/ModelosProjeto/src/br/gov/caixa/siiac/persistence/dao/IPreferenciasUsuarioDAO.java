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
package br.gov.caixa.siiac.persistence.dao;

import java.util.List;

import br.gov.caixa.siiac.model.domain.GerenteUnidade;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuario;

/**
 * @author GisConsult
 *
 */
public interface IPreferenciasUsuarioDAO extends IGenericDAO<PreferenciasUsuario>{

	/**
	 * 
	 * Metodo que deleta todos os produtos preferenciais que 
	 * estao presentes no banco de dados e 
	 * nao estao mais presentes na lista atual em mem�ria.
	 * @param matricula
	 * @param produtos
	 */
	void deleteAll(String matricula, List<PreferenciasUsuario> produtos);

	/**
	 * 
	 * Metodo que insere todos os produtos preferenciais que 
	 * nao estao presentes no banco de dados e 
	 * estao presentes na lista atual em mem�ria.
	 * @param matricula
	 * @param produtos
	 */
	void saveAll(String matricula, List<PreferenciasUsuario> produtos);
	
	/**
	 * Verifica se determinada matrícula possui ou não produto preferencial
	 * @param matricula
	 * @return
	 */
	boolean hasPreferencial(String matricula);
	boolean hasServicoPreferencial(String matricula);
	/**
	 * retorna os produtos preferenciais
	 * @param matricula
	 * @return
	 */
	List<String> listProdutoPreferencial(String matricula);
	
	public List<PreferenciasUsuario> getAll(String matricula, String categorias);

	/**
	 * retorna uma lista de servico preferencial
	 * @param matricula
	 * @return
	 */
	List<String> listServicoPreferencial(String matricula);
	/**
	 * Retorna uma lista de Gestores
	 * @param nuUnidade - Numero da unidade.
	 * @return
	 */
	public List<GerenteUnidade> getListGerentesUnidade(Short nuUnidade);
}
