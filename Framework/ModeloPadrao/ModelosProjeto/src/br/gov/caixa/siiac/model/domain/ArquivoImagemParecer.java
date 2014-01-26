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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb051_arquivo_imagem_parecer", schema = "iacsm001")
@TrailClass(fonte = "PARECER")
public class ArquivoImagemParecer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected ArquivoImagemParecerId arquivoImagemParecerId;
	private byte[] imParecer;
	private Parecer parecer;
	
	public ArquivoImagemParecer() {
	}
	
	public ArquivoImagemParecer(ArquivoImagemParecerId arquivoImagemParecerId) {
		this.arquivoImagemParecerId = arquivoImagemParecerId;
	}
	
	public ArquivoImagemParecer(ArquivoImagemParecerId arquivoImagemParecerId, byte[] imParecer) {
		this.arquivoImagemParecerId = arquivoImagemParecerId;
		this.imParecer = imParecer;
	}
	
	public ArquivoImagemParecer(int nuParecer, Short aaParecer, Short nuUnidade, int nuNatural) {
		this.arquivoImagemParecerId = new ArquivoImagemParecerId(nuParecer, aaParecer, nuUnidade, nuNatural);
	}
	
	@EmbeddedId
	@TrailLog(inner = true)
	public ArquivoImagemParecerId getArquivoImagemParecerPK() {
		return arquivoImagemParecerId;
	}
	
	public void setArquivoImagemParecerPK(ArquivoImagemParecerId arquivoImagemParecerId) {
		this.arquivoImagemParecerId = arquivoImagemParecerId;
	}
	
	@Column(name = "im_parecer")
	public byte[] getImParecer() {
		return imParecer;
	}
	
	public void setImParecer(byte[] imParecer) {
		this.imParecer = imParecer;
	}
	
	@JoinColumns({ 
		@JoinColumn(name = "nu_parecer", referencedColumnName = "nu_parecer", insertable = false, updatable = false), 
		@JoinColumn(name = "aa_parecer", referencedColumnName = "aa_parecer", insertable = false, updatable = false), 
		@JoinColumn(name = "nu_unidade", referencedColumnName = "nu_unidade", insertable = false, updatable = false), 
		@JoinColumn(name = "nu_natural", referencedColumnName = "nu_natural", insertable = false, updatable = false) })
	@ManyToOne
	public Parecer getParecer() {
		return parecer;
	}
	
	public void setParecer(Parecer parecer) {
		this.parecer = parecer;
	}
	
	@Transient
	@TrailLog(name = "im_parecer")
	public String getArquivoParecerTrilha() {
		if (getImParecer() != null) {
			return "com arquivo";
		} else {
			return "sem arquivo";
		}
	}
	
	@Transient
	public String getDescricaoParecer(){
		return PA + UNDERLINE + numeroFormatado(Integer.parseInt(this.getParecer().getParecerId().getNuUnidade().toString()),4) + UNDERLINE + numeroFormatado(this.getParecer().getParecerId().getNuParecer(), 5) + UNDERLINE + this.getParecer().getParecerId().getAaParecer();
	}
	
	private static final String UNDERLINE = "_";
	private static final String PA = "PA";
	
	@Transient
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
}
