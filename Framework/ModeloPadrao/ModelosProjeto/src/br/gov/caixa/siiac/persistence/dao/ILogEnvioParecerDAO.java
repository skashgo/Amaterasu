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
import br.gov.caixa.siiac.model.LogEnvioParecerVO;
import br.gov.caixa.siiac.model.domain.LogEnvioParecer;
import br.gov.caixa.siiac.model.domain.LogEnvioParecerId;

/**
 * @author GIS Consult
 *
 */
public interface ILogEnvioParecerDAO extends IGenericDAO<LogEnvioParecer> {

	/**
	 * Método que retorna a lista dos pareceres enviados com as seguintes
	 * informações: <br/>
	 * nu_parecer, <br/>
	 * aa_parecer, <br/>
	 * nu_unidade, <br/>
	 * nu_natural, <br/>
	 * dt_parecer,<br/>
	 * co_produto, <br/>
	 * co_responsavel_parecer,<br/> 
	 * no_parecer, <br/>
	 * ic_situacao_envio_parecer<br/><br/>
	 * 
	 * @return Lista do tipo LogEnvioParecerVO contendo os pareceres enviados.
	 */
	List<LogEnvioParecerVO> getListaVO(String filtroSimples);
	
	/**
	 * Método responsável por realizar a busca do tipo Avançada
	 * @param log
	 * @return
	 */
	List<LogEnvioParecerVO> getListaAvancadoVO(LogEnvioParecerVO log);
}
