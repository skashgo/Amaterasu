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
package br.gov.caixa.siiac.util.metalanguage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.gov.caixa.siiac.exception.SIIACRuntimeException;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
public class ProcessMetaLanguage 
{
	private String SEPARADOR=";";
	private String MAIOR=">";
	private String MENOR="<";
	private String MAIOR_HTML="&gt;";
	private String MENOR_HTML="&lt;";
	private String CHAVE_ABRE_HTML="{";
	private String CHAVE_FECHA_HTML="}";
	
	
	private Object dl=null;
	private List<String> listAllTags=new ArrayList<String>();
	private List<String> listTags=new ArrayList<String>();
	private List<String> listSamples=new ArrayList<String>();
	
	public ProcessMetaLanguage(Object dl, String arquivo)
	{
		if (dl==null)
			throw new SIIACRuntimeException("Parâmetro DefinedLanguage não pode ser nulo.");
		
		this.dl=dl;
		loadTagFile(arquivo);
	}
	
	public String process(String text)
	{
		if (text==null || text.equals(""))
			throw new SIIACRuntimeException("Parâmetro text não pode ser nulo ou vazio.");
		
		text=text.replace(MENOR_HTML, MENOR);
		text=text.replace(MAIOR_HTML,MAIOR);
		
		return text;
	}
	
	public String processTags(String text)
	{
		return replateTag(process(text));
	}
	
	public String processTagsShow(String text)
	{
		return replateTagShow(process(text));
	}
	
	/**
	 * Executa a substituição das tags contidas no texto (@param text) pelo valor padrão definido no arquivo utilizado para 'prévia' do texto.
	 * @param text
	 * @return
	 */
	public String replateTagShow(String text)
	{
		for (String s:listSamples)
		{
			String[] ss=s.split(";");

			if (ss[0].startsWith(CHAVE_ABRE_HTML) && ss[0].endsWith(CHAVE_FECHA_HTML))
				text=text.replace(ss[0], ss[0].replace(CHAVE_ABRE_HTML, "").replace(CHAVE_FECHA_HTML, ""));
			else
				text=text.replace(ss[0], ss[1]);
		}
		
		return text;
	}
	
	/**
	 * Executa a substituição das tags contidas no texto (@param text) pelo valor resultado dos métodos correspondentes.
	 * @param text
	 * @return
	 */
	private String replateTag(String text)
	{
		for (String s:listSamples)
		{
			String[] ss=s.split(SEPARADOR);
			
			
			if (text.contains(ss[0]))
			{

				if (ss[0].startsWith(CHAVE_ABRE_HTML) && ss[0].endsWith(CHAVE_FECHA_HTML))
					text=text.replace(ss[0], ss[0].replace(CHAVE_ABRE_HTML, "").replace(CHAVE_FECHA_HTML, ""));
				else
					text=text.replace(ss[0],executeExternalMethod(tagForMethod(ss[0])));
			}
		}
		
		return text;
	}
	
	/**
	 * Executa u método de acordo com seu nome passado como parâmetro na classe 'dl' (DefinedLanguage)
	 * @param method
	 * @return
	 */
	private String executeExternalMethod(String method)
	{
	    try
	    {
		      Method mthd=dl.getClass().getMethod(method);
		      Object methodReturn = mthd.invoke(dl);
		      if(Utilities.empty(methodReturn))
		    	  return "";
		      else
		    	  return (String) methodReturn;
	    }
	    catch (Exception erro)
	    {
	    	LogCEF.error(erro.getMessage());
	    	LogCEF.error(erro.getCause());
	    	// TODO: Verificar se este printStackTrace deve ficar aqui
	    	erro.printStackTrace();
	    	
	    	throw new SIIACRuntimeException(erro, "Método para obtenção do valor da tag não pode ser executado. Este método pode não existir na classe: "+dl.getClass().getSimpleName() + ". Nome do método: " + method);
	    }
	}
	
	/**
	 * Realiza a leitura do arquivo com as tags.
	 * @param arquivo
	 */
	private void loadTagFile(String arquivo)
	{
		try
		{
			BufferedReader rd = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(arquivo)));
			
			if (rd==null || !rd.ready())
				throw new SIIACRuntimeException("Arquivo de configuração de tags (" + arquivo + ") não encontrado ou vazio.");
			
			String line="";
			while ((line = rd.readLine()) != null)
			{
				if (line.equals("") || !line.contains(SEPARADOR) || !line.contains("<") || !line.contains(">"))
					throw new SIIACRuntimeException("Arquivo de configuração de tags (" + arquivo + ") mal formado. Exemplo de uma linha: nome;<nome>. Cada tag deve estar em uma linha.");
				
				String nome=line.split(SEPARADOR)[0];
				String tag=line.split(SEPARADOR)[1];
				String exemplo=line.split(SEPARADOR)[2];
				
				listTags.add(nome);
				listAllTags.add(nome+SEPARADOR+tag);
				listSamples.add(tag+SEPARADOR+exemplo);
			}
			
			//Ordenando a lista de Tags de forma Alfabética Crescente
			Collections.sort(listTags);
			Collections.sort(listAllTags);
			Collections.sort(listSamples);
		}
		catch (Exception erro)
		{
			 throw new SIIACRuntimeException(erro,"Erro ao carregar arquivo de configuração de tags (" + arquivo + "): "+erro.getMessage());
		}
		

	}
	
	/**
	 * Retorna o nome do método de acordo com a tag
	 * 	Ex: ano_parecer => getAnoParecer
	 * 		sigla_tipo_unidade_destinataria => getSiglaTipoUnidadeDestinataria 
	 * @param str
	 * @return
	 */	
	private String tagForMethod(String str)
	{
		String ret="";
		
		str=str.replace(MAIOR,"");
		str=str.replace(MENOR,"");
		
		if (str.startsWith("_") || str.endsWith("_"))
			throw new SIIACRuntimeException("A tag não pode terminar nem iniciar com o caracter '_'");
		
		if (str.contains("_"))
		{
			String[] s=str.split("_");
			
			String itemF="get";
			for (String item:s)
			{
				item=item.substring(0,1).toUpperCase()+item.substring(1);
				itemF=itemF+item;
			}
			
			ret=itemF;
		}
		else
		{
			ret="get"+str.substring(0,1).toUpperCase()+str.substring(1);
		}
		
		return ret;
	}

	public void setListAllTags(List<String> listAllTags) {
		this.listAllTags = listAllTags;
	}

	public List<String> getListAllTags() {
		return listAllTags;
	}

	public void setListTags(List<String> listTags) {
		this.listTags = listTags;
	}

	public List<String> getListTags() {
		return listTags;
	}

	public void setListSamples(List<String> listSamples) {
		this.listSamples = listSamples;
	}

	public List<String> getListSamples() {
		return listSamples;
	}
}
