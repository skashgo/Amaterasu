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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.model.domain.Unidade;

@Component("converterUnidade")
public class UnidadeConverter implements Converter {
	
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		ApplicationContext ctx = AppContext.getApplicationContext();
		IUnidadeBO unidadeBO = (IUnidadeBO) ctx.getBean("unidadeBO");
		
		String[] ids = value.split("-");
		Short nuUnidade = Short.parseShort(ids[0]);
		Integer nuNatural = Integer.parseInt(ids[1]);
		Unidade unidade = unidadeBO.getUnidade(nuUnidade, nuNatural);
		
		return unidade;
	}
	
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String codUnidConcat = "";
		
		if(value instanceof Unidade) {
			Unidade unid = (Unidade) value;
			codUnidConcat = unid.getId().getNuUnidade()+"-"+unid.getId().getNuNatural();
		}
	
		return codUnidConcat;
	}
}