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

import br.gov.caixa.siiac.model.domain.Unidade;

/**
 * @author GisConsult
 *
 */
public interface IUnidadeDAO extends IGenericDAO<Unidade> {
	public boolean existUnidade(String filtroUnidade);

	/** Retorna a lista de unidades filhas diretas (primeiro nivel) da unidade passada como parametro.
	 * @param idUnidadeVinculadora codigo da unidade pai
	 * @param perfil perfil do usuario logado
	 * @return
	 */
	public List<Unidade> getUnidadesFilhas(Short idUnidadeVinculadora, Short perfil);

	/**
	 * @param nuUnidade
	 * @return
	 */
	public List<Unidade> getLikeNuUnidade(String nuUnidade);

	/**
	 * @param nuUnidade
	 * @param unidadeUsuarioLogado
	 * @return
	 */
	List<Unidade> getLikeNuUnidade(String nuUnidade, List<Short> unidadesPermitidas);
	public Unidade findByNuUnidade(Short nuUnidade);
	
	/**
	 * Busca o número natural da unidade desejada.
	 * @param nuUnidade
	 * @return número natural da unidade
	 */
	public Integer getNuNaturalUnidade(Short nuUnidade);
	
	public Unidade getUnidadeByEmpregado(String coResponsavel);
	
	public String getFuncaoEmpregado(String coResponsavel);
	
	public String getCargoEmpregado(String coResponsavel);

	public String getNomeEmpregado(String coResponsavel);
	
	public String getFuncaoResponsavelUnidade(Short nuUnidade);
	
	/**
	 * Busca a cidade da unidade passada como parâmetro 
	 * @param unidade
	 * @return
	 */
	public String getLocalidade(Short unidade);
	
	public String getNomeFuncaoResponsavelByUnidade(Short nuUnidade);
	
	public String getNomeUnidadeByUnidade(Short nuUnidade);
	
	public String getNomeUnidadeAlienacaoBensImoveisByUnidade(Short nuUnidade);
	
	public String getCodigoUnidadeAlienacaoBensImoveisByUnidade(Short nuUnidade);
	
	public String getSiglaUnidadeAlienacaoBensImoveisByUnidade(Short nuUnidade);
	
	public String getNomeResponsavelSURET(Short nuUnidade);
	
	public String getSiglaUnidadeByUnidade(Short nuUnidade);
	
	public String getNomeUnidadeSuperiorPrimeiroNivel(Short nuUnidade);
	
	public String getNomeUnidadeSuperiorSegundoNivel(Short nuUnidade);
	
	public String getSiglaUnidadeSuperiorPrimeiroNivel(Short nuUnidade);
	
	public String getEstadoByUnidade(Short nuUnidade);
	
	public String getSiglaNomeUnidadeByEmpregado(Integer nuMatricula);
	
	public List<Unidade> getAllOrderByNuUnidade();
	
	public String getCodigoUnidadeSuperiorPrimeiroNivel(Short nuUnidade);
}
