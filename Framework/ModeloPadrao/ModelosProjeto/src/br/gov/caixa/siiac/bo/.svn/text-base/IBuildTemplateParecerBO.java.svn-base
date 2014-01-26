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

/**
 * @author GIS Consult
 *
 */
public interface IBuildTemplateParecerBO 
{

	public List<String> getListTags();
	public List<String> getListAllTags();
	public String getListAllTagstoString();
	public String processShow(String text);
	public String process(String text);
	public void setDefinedLanguage(Object dl);
	/**
	 * Gera o PDF e o retorna o array de bytes com o restultado.
	 * @param text
	 * @param caminhoPastaImagens
	 * @return
	 */
	public byte[] generateReport(String text, String caminhoPastaImagens) throws Exception ;
	
	/**
	 * Retorna o arquivo PDF do Parecer gerado.
	 * @param nuVerificacaoContrato
	 * @return
	 */
	public byte[] getPDFParecer(Integer nuVerificacaoContrato);
	
	public String getNomeParecer(Integer nuVerificacaoContrato);
}
