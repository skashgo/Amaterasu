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
import java.util.Map;

import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.security.IUsuario;

public interface IAgendaBO {
	
	/**
	 * Verifica se o contrao possui verificaçao parcial
	 * @param contrato
	 * @return
	 */
	boolean isVerificacaoParcial(Contrato contrato);
	
	/**
	 * Valida se o contrato que aparece em tela para ser verificado, realmente possui agenda de verificação
	 * @param nuContrato
	 * @return
	 */
	boolean existeAgendaContrato(Integer nuContrato);
	
	/**
	 * @param filtrarPreferenciais
	 * @param rows 
	 * @param first 
	 * @param order 
	 * @param matriculaUserLogado
	 * @param unidadeUserLogado
	 * @param regionalConformidade
	 * @param verificadorConformidade
	 * @return
	 */
	List<Contrato> getListaContratoAgenda(boolean filtrarPreferenciais, IUsuario userLogado, Integer first, Integer rows, Map<String, Integer> order);
	Integer getListaContratoAgendaCount(boolean filtrarPreferenciais, IUsuario userLogado);
	public boolean hasVerificacaoContratoAVerificar(Contrato contrato);
	public VerificacaoContratoVO hasOnlyOneVerificacaoContratoAVerificar(Contrato contrato);
}