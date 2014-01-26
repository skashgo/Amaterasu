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

/**
 * @author GIS Consult
 *
 */
public interface IBuildTemplateNotificacaoBO {

	public List<String> getListTags();
	public List<String> getListAllTags();
	public String getListAllTagstoString();
	public String processShow(String text);
	public String process(String text);
	public void setDefinedLanguageNotificacao(Object dl);
	/**
	 * Gera o PDF e o retorna o array de bytes com o restultado.
	 * @param text
	 * @param caminhoPastaImagens
	 * @return
	 */
	public byte[] generateReport(String text, String caminhoPastaImagens) throws Exception ;
	
	public String generateHtml(String text) throws Exception ;

}
