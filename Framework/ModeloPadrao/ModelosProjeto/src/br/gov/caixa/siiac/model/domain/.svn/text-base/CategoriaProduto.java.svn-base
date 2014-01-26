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
package br.gov.caixa.siiac.model.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "iactb046_categoria_produto", schema = "iacsm001")
public class CategoriaProduto implements java.io.Serializable {
	
	private static final long serialVersionUID = 109247103L;
	private int nuCategoriaProduto;
	private String noCategoriaProduto;
	private String sgCategoria;
	private boolean icAtivo = true;
	private List<Produto> listProduto;
	private List<PreferenciasUsuarioCategoriaProduto> preferenciasUsuarioCategoriaProduto = new ArrayList<PreferenciasUsuarioCategoriaProduto>();
	
	public CategoriaProduto() {
	}
	
	public CategoriaProduto(int nuCategoriaProduto, String noCategoriaProduto) {
		this.nuCategoriaProduto = nuCategoriaProduto;
		this.noCategoriaProduto = noCategoriaProduto;
	}
	
	@Id
	@Column(name = "nu_categoria_produto", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq026_categoria_produto")
	@SequenceGenerator(name = "iacsq026_categoria_produto", sequenceName = "iacsm001.iacsq026_categoria_produto")
	public int getNuCategoriaProduto() {
		return this.nuCategoriaProduto;
	}
	
	public void setNuCategoriaProduto(int nuCategoriaProduto) {
		this.nuCategoriaProduto = nuCategoriaProduto;
	}
	
	@Column(name = "no_categoria_produto", nullable = false, length = 70)
	public String getNoCategoriaProduto() {
		return this.noCategoriaProduto;
	}
	
	public void setNoCategoriaProduto(String noCategoriaProduto) {
		this.noCategoriaProduto = noCategoriaProduto;
	}

	@Column(name = "sg_categoria", length = 2)
	public String getSgCategoria() {
		return sgCategoria;
	}

	public void setSgCategoria(String sgCategoria) {
		this.sgCategoria = sgCategoria;
	}

	@Column(name = "ic_ativo")
	public boolean isIcAtivo() {
		return icAtivo;
	}

	public void setIcAtivo(boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
	
	@OneToMany(mappedBy = "categoriaProduto", fetch = FetchType.LAZY)
	public List<Produto> getListProduto() {
		return listProduto;
	}

	public void setListProduto(List<Produto> listProduto) {
		this.listProduto = listProduto;
	}
	
	@OneToMany(mappedBy = "categoriaProduto", fetch = FetchType.LAZY)
	public List<PreferenciasUsuarioCategoriaProduto> getPreferenciasUsuarioCategoriaProduto() {
		return preferenciasUsuarioCategoriaProduto;
	}
	
	public void setPreferenciasUsuarioCategoriaProduto(List<PreferenciasUsuarioCategoriaProduto> preferenciasUsuarioCategoriaProduto) {
		this.preferenciasUsuarioCategoriaProduto = preferenciasUsuarioCategoriaProduto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (icAtivo ? 1231 : 1237);
		result = prime * result + ((noCategoriaProduto == null) ? 0 : noCategoriaProduto.hashCode());
		result = prime * result + nuCategoriaProduto;
		result = prime * result + ((sgCategoria == null) ? 0 : sgCategoria.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaProduto other = (CategoriaProduto) obj;
		if (icAtivo != other.icAtivo)
			return false;
		if (noCategoriaProduto == null) {
			if (other.noCategoriaProduto != null)
				return false;
		} else if (!noCategoriaProduto.equals(other.noCategoriaProduto))
			return false;
		if (nuCategoriaProduto != other.nuCategoriaProduto)
			return false;
		if (sgCategoria == null) {
			if (other.sgCategoria != null)
				return false;
		} else if (!sgCategoria.equals(other.sgCategoria))
			return false;
		return true;
	}
	
	
}
