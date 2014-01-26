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
package br.gov.caixa.siiac.persistence.dao;

import java.util.List;

import br.gov.caixa.siiac.model.ContratoVO;
import br.gov.caixa.siiac.model.FiltroVerificacaoPreventivaVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.view.mb.pagination.ParamPagination;

/**
 * @author GIS Consult
 *
 */
public interface IVerificacaoPreventivaDAO extends IGenericDAO<Contrato> 
{
	List<Contrato> filtra(ContratoVO contratoVO, List<String> produtos, List<String> servicos, List<String> listProdutosAbrangentes, ParamPagination paramPagination);

	/**
	 * @param usuarioLogado
	 * @param short1
	 * @param pesquisaString
	 * @param produtosPreferenciais
	 * @param param
	 * @return
	 */
	int filtraCount(ContratoVO contratoVO, List<String> produtos, List<String> servicos, List<String> listProdutosAbrangentes, ParamPagination paramPagination);
	
	List<Integer> getListNuConveniadosByNome(String nomeConveniado);
	
	public boolean permitirExclusao(Integer nuContrato);
}
