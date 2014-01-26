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
package br.gov.caixa.siiac.bo.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IBuildTemplateNotificacaoBO;
import br.gov.caixa.siiac.exception.SIIACRuntimeException;
import br.gov.caixa.siiac.util.FormatUtil;
import br.gov.caixa.siiac.util.metalanguage.ProcessMetaLanguage;

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
public class BuildTemplateNotificacaoBO implements IBuildTemplateNotificacaoBO {

	private List<String> listTags = new ArrayList<String>();
	private List<String> listAllTags = new ArrayList<String>();

	private ProcessMetaLanguage pml = null;

	public BuildTemplateNotificacaoBO() {
	}

	public String processShow(String text) {
		String html = "<body style=\"font-size: 10px;\">";
		html += text;
		html += "</body>";
		return FormatUtil.parseHtmlCharacters(getPml().processTagsShow(html));
	}

	public String process(String text) {
		return FormatUtil.parseHtmlCharacters(getPml().processTags(text));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.gov.caixa.siiac.bo.IBuildTemplateParecer#getListTags()
	 */
	public List<String> getListTags() {
		return listTags;
	}

	public String getListAllTagstoString() {
		String r = "";
		for (String s : listAllTags) {
			r = r + s + "#";
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

	public void setDefinedLanguageNotificacao(Object dl) {
		if (dl == null)
			throw new SIIACRuntimeException(
					"Objeto dl (DefinedLanguage) é nulo.");

		this.pml = new ProcessMetaLanguage(dl, "tagdefinitionNotificacao.lng");

		listAllTags = getPml().getListAllTags();
		listTags = getPml().getListTags();

	}

	public ProcessMetaLanguage getPml() {
		if (pml == null)
			throw new SIIACRuntimeException(
					"Objeto pml (ProcessMetaLanguage) é nulo.");
		return pml;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.gov.caixa.siiac.bo.IBuildTemplateParecerBO#generateReport(java.lang
	 * .String)
	 */
	public byte[] generateReport(String text, String caminhoPastaImagens)
			throws Exception {
		String cabecalho = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		cabecalho += "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
		cabecalho += "<head></head>";
		String html = cabecalho + "<body style=\"font-size: 10px;\">";
		html += this.process(text);
		html = html.replace("src=\"../../images/", caminhoPastaImagens)
				.replace("{<img", "<img").replace("/>}", "/>");
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
	
	public String generateHtml(String text)throws Exception {
		
				
		String cabecalho = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		cabecalho += "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
		cabecalho += "<head></head>";
		String html = cabecalho + "<body style=\"font-size: 12px;\">";
//		html = html.replace("src=\"../../images/", path + System.getProperty("file.separator")).replace("{<img", "<img").replace("/>}", "/>");
		html += this.process(text);
		html += "</body>";
		html += "</html>";
		
		return html;
	}

}
