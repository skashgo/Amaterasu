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

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Usuario;

/**
 * @author GisConsult
 *
 */
public interface IUsuarioDAO extends IGenericDAO<Usuario> {
	/**
	 * seExisteUsuario
	 * @param matricula
	 * @return
	 * @throws DAOException
	 */
	public boolean seExisteUsuario(String matricula) throws SIIACException;
	
	/**
	 * gravarUsuario
	 * 
	 * @param matricula
	 * @param perfil
	 * @param matriResp
	 * @return
	 * @throws DAOException
	 */
	public boolean gravarUsuario(String matricula, short perfil, String matriResp, Short nuUnidade, Integer nuNatural);
	
	/**
	 * getListUsuario
	 * @param matriculaLogada
	 * @param situacao
	 * @param nome
	 * @param unidade
	 * @param matricula
	 * @param perfil
	 * @param unidadesPermitidas lista de unidades permitidas para a busca (se esse valor for null, nao e feito o filtro de unidades)
	 * @param listPerfisPermitidos lista de perfis permitidos para a busca (se esse valor for null, nao e feito o filtro de perfis)
	 * @return
	 * @throws DAOException
	 * @throws SIIACException 
	 */
	public List<Usuario> getListUsuario(String matriculaLogada, boolean ativo, String nome, String unidade, String matricula, String perfil, boolean fazConsulta, List<Integer> listPerfisPermitidos)throws SIIACException;
	
	/**
	 * ativarDesativarUsuario
	 * 
	 * @param matri - matricula do usuario
	 * @param ativar - true = ativa o usuario / false = desativa
	 * @throws DAOException
	 */
	public void ativarDesativarUsuario(String matr, boolean ativar) throws SIIACException;
	
	/**
	 * getUsuarioByMatricula
	 * @param matricula
	 * @return
	 * @throws DAOException
	 */
	public Usuario getUsuarioByMatricula(String matricula)throws SIIACException;
	
	
	/**
	 * Busca a unidade do usuário cadastrado no banco. Caso seja NULL, o sistema irá buscar no AD
	 * @param matricula
	 * @return
	 */
	public Short getUnidadeByUsuario(String matricula);

}
