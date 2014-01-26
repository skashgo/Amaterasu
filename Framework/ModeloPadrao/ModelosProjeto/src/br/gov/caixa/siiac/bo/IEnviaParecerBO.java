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

import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Parecer;
import br.gov.caixa.siiac.model.domain.ParecerId;
import br.gov.caixa.siiac.model.domain.TemplateParecerDestinatarios;
import br.gov.caixa.siiac.util.Anexo;

/**
 * @author GIS Consult
 * 
 */
public interface IEnviaParecerBO {

	/**
	 * Conveniado
	 * 
	 * @param nuContrato
	 * @return
	 */
	public String[] getConveniado(Integer nuConvenio);

	/**
	 * Empregado responsável pelo parecer
	 * 
	 * @param nuResponsavelVerificacao
	 * @return
	 */
	public String[] getEmpregadoResponsavelParecer(String nuResponsavelVerificacao);
	
	/**
	 * Gerente da Unidade responsável pelo contrato
	 * @param nuUnidadeResponsavelContrato
	 * @return
	 */
	public String[] getEmpregadoResponsavelUnidade(Short nuUnidadeResponsavelContrato);	
	
	/**
	 * Gerente que assina o parecer
	 * @param nuUnidadeResponsavelContrato
	 * @return
	 */
	public String[] getGerenteAssinaParecer(String coGerenteParecer);
	
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
	public String[] getUnidEmpreRespParecer(String nuMatriculaRespParecer);
	
	/**
	 * Unidade responsável pelo contrato
	 * @param nuUnidadeResponsavelContrato
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
	 * Vendedor
	 * @param nuUnidadeRespContrato
	 * @return
	 */
	public String[] getVendedor(Contrato contrato);
	
	/**
	 * Método responsável por enviar o e-mail para as unidades
	 * @param nuParecer
	 */
	public void enviaParecer(ParecerId nuParecer);
	
	
	
	public String[] getEmail(Integer coTemplateParecerDestinatario, Contrato contrato, String coGerenteParecer, String coResponsavelParecer, String coResponsavelVerificacao);
	
	public List<Anexo> constroiListaAnexo(Parecer parecer);
	
	public String getAssuntoEmail(Parecer parecer);
	
	public String getCorpoEmail(Parecer parecer, List<String> nomesDestinatarios, List<String> nomesCopias);
	
	public boolean validaEmail(String email);
}
