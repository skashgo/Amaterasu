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
package br.gov.caixa.siiac.view.mb.pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;

import org.richfaces.component.html.HtmlDataTable;
import org.richfaces.component.html.HtmlDatascroller;

import br.gov.caixa.siiac.model.domain.Contrato;

/**
 * @author GIS Consult
 */
public class PaginationOnDemand extends Observable {
	private DataModel value;
	private HtmlDataTable binding = new HtmlDataTable();
	private HtmlDatascroller datascroller = new HtmlDatascroller();
	private int firstResult, previousValue = -1;
	private Integer totalListSize;
	private Map<String, String> order = new HashMap<String, String>();
	private boolean isOrder;
	private List listItens = new ArrayList();
	private ParamPagination param = new ParamPagination();
	
	public PaginationOnDemand() {
		
	}
	
	public void setListItens(List listItens, int count) {
		this.listItens = listItens;
		this.totalListSize = count;
	}
	
	public void atualizaList() {
		previousValue = -1;
		getValue();
	}
	
	public DataModel getValue() {
		firstResult = binding.getRows() * (datascroller.getPage() - 1);
		if (firstResult != previousValue || isOrder) {
			setChanged();
			notifyObservers("UPDATE_LIST");
			previousValue = firstResult;
			isOrder = false;
			if (totalListSize == null) {
				totalListSize = 0;
			}
		}
		
		value = new PagedDataModel(listItens, totalListSize);
				
		return value;
	}
	
	public ParamPagination getParam() {
		param.setFirstResult(firstResult);
		param.setMaxResults(binding.getRows());
		param.setOrder(order);
		return param;
	}
	
	public void setValue(DataModel dataModel) {
		this.value = dataModel;
	}
	
	public void sort(ActionEvent evt) {
		String id = evt.getComponent().getId();
		isOrder = true;
		sort(id);
		getValue();
	}
	
	private void sort(String nome) {
		if (order.get(nome) == null || order.get(nome).equals("")) {
			order.put(nome, "ASC");
		} else {
			if (order.get(nome).equals("ASC")) {
				order.put(nome, "DESC");
			} else {
				order.put(nome, "ASC");
			}
		}
		for (String s : order.keySet()) {
			if (!s.equals(nome)) {
				order.put(s, null);
			}
		}
	}
	
	public Map<String, String> getOrder() {
		return order;
	}
	
	public void setOrder(Map<String, String> order) {
		this.order = order;
	}
	
	public HtmlDataTable getBinding() {
		return binding;
	}
	
	public void setBinding(HtmlDataTable dataTable) {
		this.binding = dataTable;
	}
	
	public HtmlDatascroller getDatascroller() {
		return datascroller;
	}
	
	public void setDatascroller(HtmlDatascroller datascroller) {
		this.datascroller = datascroller;
	}
}