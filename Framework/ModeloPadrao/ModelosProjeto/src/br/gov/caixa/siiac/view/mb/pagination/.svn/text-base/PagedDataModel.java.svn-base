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

/**
 * @author GIS Consult
 *
 */
import java.io.Serializable;
import java.util.List;

import javax.faces.model.DataModel;

public class PagedDataModel extends DataModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int rowIndex = -1;
	
	private int totalNumRows;
	
	private int pageSize;
	
	private List list;
	
	public PagedDataModel() {
		super();
	}
	
	public PagedDataModel(List list, int totalNumRows) {
		super();
		setWrappedData(list);
		this.totalNumRows = totalNumRows;
		this.pageSize = 10;
	}
	
	public PagedDataModel(List list, int totalNumRows, int pageSize) {
		super();
		setWrappedData(list);
		this.totalNumRows = totalNumRows;
		this.pageSize = pageSize;
	}
	
	@Override
	public boolean isRowAvailable() {
		if (list == null)
			return false;
		
		int rowIndex = getRowIndex();
		if (rowIndex >= 0 && rowIndex < list.size())
			return true;
		else
			return false;
	}
	
	@Override
	public int getRowCount() {
		return totalNumRows;
	}
	
	@Override
	public Object getRowData() {
		if (list == null)
			return null;
		else if (!isRowAvailable())
			throw new IllegalArgumentException();
		else {
			int dataIndex = getRowIndex();
			return list.get(dataIndex);
		}
	}
	
	@Override
	public int getRowIndex() {
		if(rowIndex == 0 || pageSize == 0){
			return 0;
		}
		return (rowIndex % pageSize);
	}
	
	@Override
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	
	@Override
	public Object getWrappedData() {
		return list;
	}
	
	@Override
	public void setWrappedData(Object list) {
		this.list = (List) list;
	}
	
}