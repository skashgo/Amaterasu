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
package br.gov.caixa.siiac.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Transient;

import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 */
public class FormatUtil {
	public static String formatMatricula(Integer matricula) {
		if (matricula == null || matricula == 0) {
			return "";
		}
		String mat = "c";
		for (int i = String.valueOf(matricula).length(); i < 6; i++) {
			mat += 0;
		}
		return mat + matricula;
	}
	
	public static Integer formatMatriculaInteiro(String matricula) {
		if (Utilities.empty(matricula)) {
			return null;
		}
		Matcher matcher = Pattern.compile("[^0-9]").matcher(matricula);
		return Integer.parseInt(matcher.replaceAll(""));
	}
	
	public static Short formatMatriculaShort(String matricula) {
		if (Utilities.empty(matricula)) {
			return null;
		}
		Matcher matcher = Pattern.compile("[^0-9]").matcher(matricula);
		return Short.parseShort(matcher.replaceAll(""));
	}
	
	public static String parseHtmlCharacters(String html) {
		html = html.replace("iexcl; ", "iexcl;&nbsp;");
		html = html.replace("cent; ", "cent;&nbsp;");
		html = html.replace("pound; ", "pound;&nbsp;");
		html = html.replace("curren; ", "curren;&nbsp;");
		html = html.replace("yen; ", "yen;&nbsp;");
		html = html.replace("brvbar; ", "brvbar;&nbsp;");
		html = html.replace("sect; ", "sect;&nbsp;");
		html = html.replace("uml; ", "uml;&nbsp;");
		html = html.replace("copy; ", "copy;&nbsp;");
		html = html.replace("laquo; ", "laquo;&nbsp;");
		html = html.replace("not; ", "not;&nbsp;");
		html = html.replace("shy; ", "shy;&nbsp;");
		html = html.replace("reg; ", "reg;&nbsp;");
		html = html.replace("macr; ", "macr;&nbsp;");
		html = html.replace("plusmn; ", "plusmn;&nbsp;");
		html = html.replace("sup2; ", "sup2;&nbsp;");
		html = html.replace("sup3; ", "sup3;&nbsp;");
		html = html.replace("acute; ", "acute;&nbsp;");
		html = html.replace("micro; ", "micro;&nbsp;");
		html = html.replace("para; ", "para;&nbsp;");
		html = html.replace("middot; ", "middot;&nbsp;");
		html = html.replace("cedil; ", "cedil;&nbsp;");
		html = html.replace("sup1; ", "sup1;&nbsp;");
		html = html.replace("ordm; ", "ordm;&nbsp;");
		html = html.replace("raquo; ", "raquo;&nbsp;");
		html = html.replace("frac14; ", "frac14;&nbsp;");
		html = html.replace("frac12; ", "frac12;&nbsp;");
		html = html.replace("frac34; ", "frac34;&nbsp;");
		html = html.replace("iquest; ", "iquest;&nbsp;");
		html = html.replace("times; ", "times;&nbsp;");
		html = html.replace("divide; ", "divide;&nbsp;");
		html = html.replace("tilde; ", "tilde;&nbsp;");
		html = html.replace("grave; ", "grave;&nbsp;");
		html = html.replace("amp; ", "amp;&nbsp;");
		html = html.replace("rdf; ", "rdf;&nbsp;");
		html = html.replace("circ; ", "circ;&nbsp;");
		html = html.replace("deg; ", "deg;&nbsp;");
		
		// html = html.replace(" ", "&nbsp;");
		
		return html;
	}
	
	public static String getArrayToString(Object[] array){
		String retorno = "";
		for (Object object : array) {
			if(!retorno.equals("")){
				retorno += ", ";
			}
			retorno += object.toString();
		}
		return retorno;
	}
	
	public static String getListToString(List list){
		String retorno = "";
		for (Object object : list) {
			if(!retorno.equals("")){
				retorno += ", ";
			}
			retorno += object.toString();
		}
		return retorno;
	}
	
	public static String getNomeParecerPDF(Integer nuParecer, Short aaParecer, Short nuUnidade, Short nuNatural) {
		return PA + UNDERLINE + numeroFormatado(Integer.parseInt(nuUnidade.toString()), 4) + UNDERLINE + numeroFormatado(nuParecer, 5) + UNDERLINE + aaParecer;
	}
	
	private static final String UNDERLINE = "_";
	private static final String PA = "PA";
	
	private static String numeroFormatado(Integer numero, Integer qtdZeros) {
		String retorno = "";
		
		String aux = numero.toString();
		
		for (int m = 0; m < (qtdZeros - aux.length()); m++) {
			retorno += "0";
		}
		
		retorno += numero;
		
		return retorno;
	}
}
