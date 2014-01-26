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

import javax.mail.internet.AddressException;

import br.gov.caixa.siiac.exception.SIIACRuntimeException;
import br.gov.caixa.siiac.model.ResumoParecerVO;
import br.gov.caixa.siiac.model.TagListaItemDataValidadeVO;
import br.gov.caixa.siiac.model.domain.Parecer;
import br.gov.caixa.siiac.security.IUsuario;


public interface IResumoParecerBO {
	
	String getResumoParecer(Integer nuVerificacaoContrato);

	String getResumoPreviaParecerConformidadesSemParecer(Integer nuVerificacaoContrato);
	
	String getResumoPreviaParecerInconformidadesSemParecer(Integer nuVerificacaoContrato);
	
	String getResumoPreviaParecerConformidadesComParecer(Integer nuVerificacaoContrato);
	
	String getResumoPreviaParecerInconformidadesComParecer(Integer nuVerificacaoContrato);
	
	String getResumoPreviaParecerItemDataValidade(Integer nuVerificacaoContrato);
	
	public List<TagListaItemDataValidadeVO> getListaItensComDataValidade(Integer nuVerificacaoContrato);
	
	public String getListaItemDataValidadeHTML(List<TagListaItemDataValidadeVO> conformidades);
	
}