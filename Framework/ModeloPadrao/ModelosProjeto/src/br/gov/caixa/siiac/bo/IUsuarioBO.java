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
import br.gov.caixa.siiac.model.domain.Usuario;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.util.FilterBase;

/**
 * @author GisConsult
 *
 */
public interface IUsuarioBO {
	/**
	 * Busca uma lista de usuários de acordo com os parâmetros passados.
	 * @param usuario
	 * @param matriculaLogada
	 * @param ativo
	 * @param nome
	 * @param unidade
	 * @param matricula
	 * @param perfil
	 * @param fazConsulta
	 * @return
	 * @throws SIIACException
	 */
	public List<Usuario> getListUsuario(IUsuario usuario, String matriculaLogada, boolean ativo, String nome, String unidade, String matricula, String perfil, boolean fazConsulta, List<Integer> listPerfisUsuario) throws SIIACException;
	public List<Usuario> getListUsuario(IUsuario usuario, String matriculaLogada, FilterBase filtro, boolean fazConsulta, List<Integer> listPerfisUsuario) throws SIIACException;

	/**
	 * Grava o usuário com os valores passados.
	 * @param usuario
	 * @param matricula
	 * @param perfil
	 * @param alterar
	 * @return
	 * @throws SIIACException
	 */
	public boolean gravarUsuario(IUsuario usuario, String matricula, short perfil, boolean alterar, Short nuUnidade) throws SIIACException;
	
	/**
	 * Altera o status do usuário parâmetro.
	 * @param matr
	 * @param ativar
	 * @throws SIIACException
	 */
	public void ativarDesativarUsuario(String matr, boolean ativar) throws SIIACException;
	
	/**
	 * Busca o usuário pela matrícula
	 * @param matricula
	 * @return
	 * @throws SIIACException
	 */
	public Usuario getUsuarioByMatricula(String matricula) throws SIIACException;
	
	/**
	 * Verifica se existe unidade.
	 * @param nuUnidade
	 * @return
	 */
	public boolean existeUnidade(String nuUnidade);
	
	/**
	 * Verifica se existe usuário (na tabel de usuário) para a matrícula passada.
	 * @param matricula
	 * @return
	 * @throws SIIACException
	 */
	public boolean seExisteUsuario(String matricula)throws SIIACException;

	/**
	 * @param pesquisaString
	 * @param pesquisaMostraInativos
	 * @return
	 */
	public List<Usuario> getListUsuarioFiltroSimples(IUsuario usuario, String pesquisaString, Boolean pesquisaMostraInativos) throws SIIACException;
	public List<Usuario> getListUsuarioFiltroSimples(IUsuario usuario, FilterBase filtro) throws SIIACException;

	public void inicializaUsuario(Usuario usuario);
	
	/**
	 * Busca a unidade do usuário cadastrado no banco. Caso seja NULL, o sistema irá buscar no AD
	 * @param matricula
	 * @return
	 */
	public Short getUnidadeByUsuario(String matricula)  throws SIIACException;
}
