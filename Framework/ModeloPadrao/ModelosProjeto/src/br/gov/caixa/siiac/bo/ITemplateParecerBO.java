/**
 * Copyright (c) 2009-2011 Caixa Econ�mica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econ�mica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e est�
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condi��es de c�pia e utiliza��o do todo ou partes dependem de autoriza��o da
 * empresa. C�pias n�o s�o permitidas sem expressa autoriza��o. N�o pode ser
 * comercializado ou utilizado para prop�sitos particulares.
 * 
 * Uso exclusivo da Caixa Econ�mica Federal. A reprodu��o ou distribui��o n�o
 * autorizada deste programa ou de parte dele, resultar� em puni��es civis e
 * criminais e os infratores incorrem em san��es previstas na legisla��o em
 * vigor.
 * 
 * Hist�rico do Subversion:
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
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.TemplateParecer;
import br.gov.caixa.siiac.model.domain.TemplateParecerDestinatarios;
import br.gov.caixa.siiac.util.FilterBase;

/**
 * @author GIS Consult
 *
 */
public interface ITemplateParecerBO 
{
	public void merge(TemplateParecer tp);
	public List<TemplateParecer> findAll();
	public List<TemplateParecer> getListTemplateParecerFiltroSimples(String pesquisa, Boolean mostrarInativos)  throws SIIACException ;
	
	public List<TemplateParecer> getListTemplateParecerFiltroSimples(FilterBase filtro)  throws SIIACException ;

	
	public List<TemplateParecer> getListTemplateParecerFiltroAvancado(String codProduto, String tipoParecer, String codServicoVerificacao, Boolean mostrarInativos) throws SIIACException;
	public List<TemplateParecer> getListTemplateParecerFiltroAvancado(FilterBase filtro) throws SIIACException;
	public TemplateParecer findById(Integer id) ;

	public TemplateParecer findByServicoVerificacaoProdutoAndResultado(
										ServicoVerificacaoProduto servicoVerificacaoProduto,
										Boolean resultado);

	/**
	 * Altera o status do template parâmetro.
	 * @param idTemplate
	 * @param ativar
	 * @throws SIIACException
	 */
	public void ativarDesativarTemplate(Integer idTemplate, boolean ativar) throws SIIACException;
	
	public Boolean exist(TemplateParecer templateParecer) throws SIIACException;

	public List<Integer> getDestinatariosEnviarEmailId(Integer nuTemplateParecer);
	public List<Integer> getDestinatariosEnviarEmailComCopiaId(Integer nuTemplateParecer);
}
