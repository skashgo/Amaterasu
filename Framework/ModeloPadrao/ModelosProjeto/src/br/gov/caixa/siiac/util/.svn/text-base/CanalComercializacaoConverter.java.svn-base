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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.caixa.siiac.bo.ICanalComercialProdutoBO;
import br.gov.caixa.siiac.model.domain.CanalComercializacaoProduto;

/**
 * @author GIS Consult
 *
 */
@Component("conversorCanal")
public class CanalComercializacaoConverter implements Converter{

	private ICanalComercialProdutoBO canalBO;
	
	@Autowired
	public void setCanalBO(ICanalComercialProdutoBO canalBO) {
		this.canalBO = canalBO;
	}
	
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if(value != null) {
			Short id = new Short(value);
			return canalBO.findById(id);
		}
		
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value != null && !value.toString().equals("")) {
			CanalComercializacaoProduto canal = canalBO.findById(new Short(value.toString()));
			if(canal != null) {
				return canal.getNoCanalCmrco();
			}
		}
		
		return "";
	}
}
