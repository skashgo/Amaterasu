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
package br.gov.caixa.siiac.bo.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IBuildTemplateParecerBO;
import br.gov.caixa.siiac.exception.SIIACRuntimeException;
import br.gov.caixa.siiac.model.domain.ArquivoImagemParecer;
import br.gov.caixa.siiac.persistence.dao.IArquivoImagemParecerDAO;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.siiac.util.metalanguage.ProcessMetaLanguage;
import br.gov.caixa.util.Utilities;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class BuildTemplateParecerBO implements IBuildTemplateParecerBO
{
	@Autowired
	private IArquivoImagemParecerDAO arquivoImagemParecerDAO;
	
	private List<String> listTags=new ArrayList<String>();
	private List<String> listAllTags=new ArrayList<String>();
	
	private ProcessMetaLanguage pml=null;
	
	
	public BuildTemplateParecerBO()
	{

	}
	
	public String processShow(String text)
	{
		String html = "<body style=\"font-size: 10px;\">";
		html += text;
		html += "</body>";
		return FormatUtil.parseHtmlCharacters(getPml().processTagsShow(html));
	}
	
	public String process(String text)
	{
		return FormatUtil.parseHtmlCharacters(getPml().processTags(text));
	}	
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IBuildTemplateParecer#getListTags()
	 */
	public List<String> getListTags()
	{
		return listTags;
	}

	public String getListAllTagstoString()
	{
		String r="";
		for (String s:listAllTags)
		{
			r=r+s+"#";
		}
		
		return r;
	}

	public void setListTags(List<String> listTags) {
		this.listTags = listTags;
	}


	public List<String> getListAllTags() {
		return this.listAllTags;
	}

	public void setListAllTags(List<String> listAllTags) {
		this.listAllTags = listAllTags;
	}


	public void setDefinedLanguage(Object dl) 
	{
		if (dl==null)
			throw new SIIACRuntimeException("Objeto dl (DefinedLanguage) é nulo.");

		this.pml=new ProcessMetaLanguage(dl, "tagdefinition.lng");
		
		listAllTags=getPml().getListAllTags();
		listTags=getPml().getListTags();
		
	}



	public ProcessMetaLanguage getPml() 
	{
		if (pml==null)
			throw new SIIACRuntimeException("Objeto pml (ProcessMetaLanguage) é nulo.");
		return pml;
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IBuildTemplateParecerBO#generateReport(java.lang.String)
	 */
	public byte[] generateReport(String text, String caminhoPastaImagens) throws Exception {
		String cabecalho = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		cabecalho += "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
		cabecalho += "<head></head>";
		String html = cabecalho + "<body style=\"font-size: 10px;\">";
		html += this.process(text);
		html = html.replace("src=\"../../images/", caminhoPastaImagens).replace("{<img", "<img").replace("/>}", "/>");
		html += "</body>";
		html += "</html>";
		try {
			// Gerando o PDF
			// Criando um ByteArrayInputStram com o byte[] da string que contém
			// o HTML
			ByteArrayInputStream byteArrayLinputStream = new ByteArrayInputStream(
					html.getBytes());

			// Criando o Reader com o conteúdo HTML
			Reader htmlreader = new BufferedReader(new InputStreamReader(
					byteArrayLinputStream));

			Document pdfDocument = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(pdfDocument, baos);
			pdfDocument.open();
			StyleSheet styles = new StyleSheet();
			styles.loadTagStyle("body", "font", "Verdana");
			ArrayList arrayElementList = HTMLWorker.parseToList(htmlreader,
					styles);
			for (int i = 0; i < arrayElementList.size(); ++i) {
				Element e = (Element) arrayElementList.get(i);
				pdfDocument.add(e);
			}

			pdfDocument.close();

			byte[] bs = baos.toByteArray();
			
			return bs;
		} catch (Exception ex) {
			throw ex;
		}
		
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IBuildTemplateParecerBO#getPDFParecer(java.lang.Integer)
	 */
	@Transactional
	public byte[] getPDFParecer(Integer nuVerificacaoContrato) {
		if(Utilities.empty(nuVerificacaoContrato)){
			return null;
		}
		ArquivoImagemParecer arquivoImagemParecer = arquivoImagemParecerDAO.getArquivoImagemParecer(nuVerificacaoContrato);
		if(Utilities.empty(arquivoImagemParecer)){
			return null;
		}
		return arquivoImagemParecer.getImParecer();
	}

	@Transactional
	public String getNomeParecer(Integer nuVerificacaoContrato) {
		String nomeParecer = "";
		
		ArquivoImagemParecer arquivoImagemParecer = arquivoImagemParecerDAO.getArquivoImagemParecer(nuVerificacaoContrato);
		
		nomeParecer = PA + UNDERLINE + numeroFormatado(Integer.parseInt(arquivoImagemParecer.getArquivoImagemParecerPK().getNuUnidade().toString()),4) + UNDERLINE + numeroFormatado(arquivoImagemParecer.getArquivoImagemParecerPK().getNuParecer(), 5) + UNDERLINE + arquivoImagemParecer.getArquivoImagemParecerPK().getAaParecer();
		
		return nomeParecer;
	}

	private static String numeroFormatado(Integer numero, Integer qtdZeros)
    {
        String retorno = "";
        
        String aux = numero.toString();
        
        for (int m = 0; m < (qtdZeros - aux.length()); m++)
        {
            retorno += "0";
        }
        
        retorno += numero;
        
        return retorno;
    }
	
	private static final String UNDERLINE = "_";
	private static final String PA = "PA";
}
