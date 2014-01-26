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

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Unidade;

/**
 * @author GisConsult
 *
 */
public interface IUsuarioADBO {
	
	/**
	 * Valida se existe usuário no ActiveDirectory.
	 * @param matricula
	 * @return
	 * @throws SIIACException
	 */
	public boolean existeUsuarioAD(String matricula) throws SIIACException;

	/**
	 * Busca o nome do usuário no ActiveDirectory.
	 * @param matricula
	 * @return
	 * @throws SIIACException
	 */
	public String getNomeUsuario(String matricula) throws SIIACException;

	/**
	 * Busca o id da unidade do usuário no ActiveDirectory.
	 * @param matricula
	 * @return
	 * @throws SIIACException
	 */
	public Short getNuUnidade(String matricula) throws SIIACException;
	
	/**
	 * Verifica se existe um usuario no AD com a 
	 * matricula passada como parametro que esteja vinculado a uma das unidades passadas. 
	 * @param matricula
	 * @param vinculadas
	 * @return
	 */
	public boolean verificaUnidade(String matricula, List<Unidade> vinculadas);

	/**
	 * Valida se existe usuários na unidade parâmetro.
	 * @param nuUnidade
	 * @return
	 */
	public boolean existeUsuarioUnidade(String nuUnidade);
	
	/**
	 * 
	 * Retorna a matricula passada como parâmetro formatada.
	 * É feita a validação se a matrícula parâmetro tem até 7 caracteres apenas e se é fiel ao padrão x999999.
	 * @param value
	 * @return
	 * @throws SIIACException
	 */
	public String getMatriculaFormatada(String value) throws SIIACException;
}
