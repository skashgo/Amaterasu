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

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.domain.Unidade;

/**
 * @author GisConsult
 *
 */
public interface IUnidadeBO {

	/**
	 * Retorna a unidade que contem nuUnidade e nuNatural passadas como parametro.
	 * @param nuUnidade
	 * @param nuNatural
	 * @return
	 */
	Unidade getUnidade(Short nuUnidade, Integer nuNatural); 
	
	/**
	 * Retorna a unidade que contem apenas nuUnidade, portanto busca a unidade que tenha o menor nuNatural.
	 * @param nuUnidade
	 * @param nuNatural
	 * @return
	 */
	Unidade getUnidade(Short nuUnidade); 

	/**
	 * Retorna a lista de todas as unidades filhas da Unidade, utilizando de toda a hierarquia ate seu ultimo nivel.
	 * @param codUnidade codigo da unidade pai
	 * @param perfil perfil do usuario logado
	 * @return
	 */
	public List<Unidade> getTodasUnidadesVinculadasHierarquia(Short codUnidade, Short perfil);
	
	/**
	 * Retorna a lista de unidades filhas diretas (primeiro nivel) da unidade passada como parametro.
	 * @param codUnidade codigo da unidade pai
	 * @param perfil perfil do usuario logado
	 * @return
	 */
	public List<Unidade> findUnidadesFilhas(Short codUnidade, Short perfil);
	
	List<Unidade> getAll() throws DAOException;

	/**
	 * Retorna uma lista de unidade filtrando pelo nuUnidade usando like
	 * @param valueOf
	 * @return
	 */
	List<Unidade> getLikeNuUnidade(String nuUnidade);
	
	/**
	 * Busca o n�mero natural da unidade desejada.
	 * @param nuUnidade
	 * @return número natural da unidade
	 */
	Integer getNuNaturalUnidade(Short nuUnidade);
	
	public String getNomeUnidadeSuperiorPrimeiroNivel(Short nuUnidade);
	
	public String getNomeUnidadeSuperiorSegundoNivel(Short nuUnidade);
		
	public String getNomeUnidadeByUnidade(Short nuUnidade);
	
	public String getEstadoByUnidade(Short nuUnidade);
	
	public String getSiglaTipoUnidadeByUnidade(Short nuUnidade);
	
	public String getSiglaNomeUnidadeByEmpregado(Integer nuMatricula);

	/**
	 * Busca a unidade do empregado passado como parâmetro.
	 * 	Ex: coResponsavel = 'c000001'. Busca na view icosm001.iacw002_empregado_caixa qual a unidade do empregado com nu_matricula = 1.
	 * 	
	 * @param coResponsavel
	 * @return
	 */
	Unidade getUnidadeByEmpregado(String coResponsavel);
}