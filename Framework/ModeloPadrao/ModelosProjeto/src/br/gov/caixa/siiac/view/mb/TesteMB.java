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
package br.gov.caixa.siiac.view.mb;

import javax.faces.event.ActionEvent;

import org.richfaces.model.TreeNodeImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author GIS Consult
 *
 */
@Service()
@Scope("request")
public class TesteMB extends AbstractMB {
	private String docString;
	
	public String getDocString() {
		return docString;
	}
	
	public void setDocString(String docString) {
		this.docString = docString;
	}
	
	public void doGravar(ActionEvent evt) {
		
	}
	
	public void doCancelar(ActionEvent evt) {
		
	}
	
	private TreeNodeImpl<String> stationRoot = new TreeNodeImpl<String>();
	private TreeNodeImpl<String> stationNodes = new TreeNodeImpl<String>();
	private String[] kickRadioFeed = { "Hall & Oates - Kiss On My List", "David Bowie - Let's Dance", "Lyn Collins - Think (About It)", "Kim Carnes - Bette Davis Eyes", "KC & the Sunshine Band - Give It Up" };
	
	public TreeNodeImpl<String> getStationNodes() {
		stationRoot.setData("KickRadio");
		stationNodes.addChild(0, stationRoot);
		for (int i = 0; i < kickRadioFeed.length; i++) {
			TreeNodeImpl<String> child = new TreeNodeImpl<String>();
			child.setData(kickRadioFeed[i]);
			stationRoot.addChild(i, child);
		}
		return stationNodes;
	}
	
	public void setStationNodes(TreeNodeImpl<String> stationNodes) {
		this.stationNodes = stationNodes;
	}
	
}
