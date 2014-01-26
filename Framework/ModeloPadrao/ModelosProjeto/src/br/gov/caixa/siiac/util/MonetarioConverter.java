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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("converterMonetario")
public class MonetarioConverter implements Converter {
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")) {
			if(!value.contains(",")){
				value = new StringBuffer(value).insert(value.length() - 2, ",").toString();
			}
			
			value = value.replaceAll("\\.", "").replaceAll(",", ".").replace("R", "").replace("$", "").replace(" ", "").replace("¤", "");
			
			return new BigDecimal(value);
		}
		
		return null;
	}
	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			if(value instanceof BigDecimal) {
				BigDecimal bg = (BigDecimal) value;
				
				Locale local = new Locale("pt", "BR");
				
				DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(local);
				return df.format(bg);
			}
		}
		
		return "";
	}
}
