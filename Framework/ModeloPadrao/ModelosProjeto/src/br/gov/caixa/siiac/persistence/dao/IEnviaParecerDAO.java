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

import br.gov.caixa.siiac.model.domain.Parecer;

/**
 * @author GIS Consult
 *
 */
public interface IEnviaParecerDAO  extends IGenericDAO<Parecer> {
		
	/**
	 * Conveniado
	 * @param nuContrato
	 * @return
	 */
	public String[] getConveniado(Integer nuConvenio);
	
	
	/**
	 * Gerente da Unidade responsável pelo contrato
	 * @param nuUnidadeResponsavelContrato
	 * @return
	 */
	public String[] getEmpregadoResponsavelUnidade(Short nuUnidadeResponsavelContrato);
	
	/**
	 * Unidade de alienação de bens móveis e imóveis de vinculação da unidade responsável pelo contrato
	 * @param nuUnidadeResponsavelContrato
	 * @return
	 */
	public String[] getUnidAliBensImoveisVincUnidRespContrato(Short nuUnidadeResponsavelContrato);	

	/**
	 * Unidade de conformidade de vinculação da unidade responsável pelo contrato
	 * @param nuUnidadeResponsavelContrato
	 * @return
	 */
	public String[] getUnidConfVincUnidRespContrato(Short nuUnidadeResponsavelContrato);
	
	/**
	 * Unidade do Empregado responsável pelo parecer
	 * @param nuUnidadeResponsavelContrato
	 * @return
	 */
	public String[] getUnidEmpreRespParecer(Integer nuMatriculaRespParecer);	
	
	/**
	 * Unidade responsável pelo contrato
	 * @param nuUnidadeRespContrato
	 * @return
	 */
	public String[] getUnidadeResponsavelContrato(Short nuUnidadeRespContrato);	
	
	/**
	 * Unidade superior hierárquica da unidade responsável pelo contrato
	 * @param nuUnidadeResponsavelContrato
	 * @return
	 */
	public String[] getUnidadeSuperiorHierarquicaUnidRespContrato(Short nuUnidadeRespContrato);	
	
	/**
	 * Método que busca o nome do empregado através da sua matrícula
	 * @param empregado
	 * @return
	 */
	public String getNomeByEmpregado(Integer empregado);
}
