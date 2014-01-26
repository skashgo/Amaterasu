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

import java.util.List;

import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioServico;

/**
 * @author GisConsult
 *
 */
public interface IPreferenciaUsuarioServicoBO {

	/**
	 * Lista todos os Servicos Preferenciais do usuario em questão.
	 * @param matricula
	 * @return
	 */
	List<PreferenciaUsuarioServico> getAll(String matricula);

	/**
	 * Sincroniza a lista de servicos preferenciais do usuario (deleta os removidos e adiciona os inseridos).
	 * @param matricula
	 * @param servicos
	 */
	void salvarLista(String matricula, List<PreferenciaUsuarioServico> servicos);
}
