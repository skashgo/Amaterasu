package br.gov.caixa.siiac.model.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.gov.caixa.siiac.model.GarantiaVO;
import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb022_garantia", schema = "iacsm001")
@TrailClass(fonte = "GARANTIA")
public class Garantia extends GarantiaVO implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer nuGarantia;
	private Contrato contrato = new Contrato();
	private Integer coGarantia;
	private String deGarantia;
	private TipoGarantia tipoGarantia = new TipoGarantia();
	private BigDecimal vrGarantia;
	private String icUltimaLocalizacao;
	private Date dtUltimaLocalizacao;
	private String coUsuarioSolicitante;
	private String coResponsavelUltimaLocalizacao;
	private String icLocalizacaoUltimoInventari;
	private Date dtUltimoInventarioGarantia;
	private String coResponsavelInventario;
	private boolean icAtivo = true;
	
	public Garantia() {
	}
	
	public Garantia(Integer nuGarantia, Contrato contrato, TipoGarantia nuTipoGarantia, BigDecimal vrGarantia, String icUltimaLocalizacao, String icLocalizacaoUltimoInventari, Date dtUltimoInventarioGarantia, String coResponsavelInventario) {
		this.nuGarantia = nuGarantia;
		this.contrato = contrato;
		this.tipoGarantia = nuTipoGarantia;
		this.vrGarantia = vrGarantia;
		this.icUltimaLocalizacao = icUltimaLocalizacao;
		this.icLocalizacaoUltimoInventari = icLocalizacaoUltimoInventari;
		this.dtUltimoInventarioGarantia = dtUltimoInventarioGarantia;
		this.coResponsavelInventario = coResponsavelInventario;
	}
	
	public Garantia(Integer nuGarantia, Contrato contrato, Integer coGarantia, String deGarantia, 
			TipoGarantia nuTipoGarantia, BigDecimal vrGarantia, String icUltimaLocalizacao, Date dtUltimaLocalizacao, 
			String coUsuarioSolicitante, String coRspnlUltimaLocalizacao, String icLocalizacaoUltimoInventari, 
			Date dtUltimoInventarioGarantia, String coResponsavelInventario, boolean icAtivo) {
		this.nuGarantia = nuGarantia;
		this.contrato = contrato;
		this.coGarantia = coGarantia;
		this.deGarantia = deGarantia;
		this.tipoGarantia = nuTipoGarantia;
		this.vrGarantia = vrGarantia;
		this.icUltimaLocalizacao = icUltimaLocalizacao;
		this.dtUltimaLocalizacao = dtUltimaLocalizacao;
		this.coUsuarioSolicitante = coUsuarioSolicitante;
		this.coResponsavelUltimaLocalizacao = coRspnlUltimaLocalizacao;
		this.icLocalizacaoUltimoInventari = icLocalizacaoUltimoInventari;
		this.dtUltimoInventarioGarantia = dtUltimoInventarioGarantia;
		this.coResponsavelInventario = coResponsavelInventario;
		this.icAtivo = icAtivo;
	}
	
	public Garantia(String deGarantia) {
		this.deGarantia = deGarantia;
	}
	
	@Id
	@Column(name = "nu_garantia", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq017_garantia")
	@SequenceGenerator(name = "iacsq017_garantia", sequenceName = "iacsm001.iacsq017_garantia")
	@TrailLog(insertID = true, updateID = true, deleteID = true)
	public Integer getNuGarantia() {
		return this.nuGarantia;
	}
	
	public void setNuGarantia(Integer nuGarantia) {
		this.nuGarantia = nuGarantia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nu_contrato", nullable = false)
	public Contrato getContrato() {
		return this.contrato;
	}
	
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	@Transient
	@TrailLog(name = "nu_contrato", deleteID = true, insertID = true, updateID = true)
	public Integer getContratoTrilha(){
		return this.contrato.getNuContrato();
	}
	
	@Column(name = "co_garantia", nullable = false)
	@TrailLog
	public Integer getCoGarantia() {
		return this.coGarantia;
	}
	
	public void setCoGarantia(Integer coGarantia) {
		this.coGarantia = coGarantia;
	}
	
	@Column(name = "de_garantia")
	@TrailLog
	public String getDeGarantia() {
		return this.deGarantia;
	}
	
	public void setDeGarantia(String deGarantia) {
		this.deGarantia = deGarantia;
	}
	
	@JoinColumn(name = "nu_tipo_garantia", referencedColumnName = "nu_tipo_garantia", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	public TipoGarantia getTipoGarantia() {
		return this.tipoGarantia;
	}
	
	public void setTipoGarantia(TipoGarantia nuTipoGarantia) {
		this.tipoGarantia = nuTipoGarantia;
	}
	
	@Transient
	@TrailLog(name = "nu_tipo_garantia")
	public Integer getTipoGarantiaTrilha(){
		return tipoGarantia.getNuTipoGarantia();
	}
	
	@Column(name = "vr_garantia", nullable = false, precision = 16)
	@TrailLog
	public BigDecimal getVrGarantia() {
		return this.vrGarantia;
	}
	
	public void setVrGarantia(BigDecimal vrGarantia) {
		this.vrGarantia = vrGarantia;
	}
	
	@Column(name = "ic_ultima_localizacao", length = 2)
	@TrailLog
	public String getIcUltimaLocalizacao() {
		return this.icUltimaLocalizacao;
	}
	
	public void setIcUltimaLocalizacao(String icUltimaLocalizacao) {
		this.icUltimaLocalizacao = icUltimaLocalizacao;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_ultima_localizacao", length = 13)
	@TrailLog
	public Date getDtUltimaLocalizacao() {
		return this.dtUltimaLocalizacao;
	}
	
	public void setDtUltimaLocalizacao(Date dtUltimaLocalizacao) {
		this.dtUltimaLocalizacao = dtUltimaLocalizacao;
	}
	
	@Column(name = "co_usuario_solicitante", length = 7)
	@TrailLog
	public String getCoUsuarioSolicitante() {
		return this.coUsuarioSolicitante;
	}
	
	public void setCoUsuarioSolicitante(String coUsuarioSolicitante) {
		this.coUsuarioSolicitante = coUsuarioSolicitante;
	}
	
	@Column(name = "co_rspnl_ultima_localizacao", length = 7)
	@TrailLog
	public String getCoResponsavelUltimaLocalizacao() {
		return this.coResponsavelUltimaLocalizacao;
	}
	
	public void setCoResponsavelUltimaLocalizacao(String coRspnlUltimaLocalizacao) {
		this.coResponsavelUltimaLocalizacao = coRspnlUltimaLocalizacao;
	}
	
	@Column(name = "ic_localizacao_ultimo_inventari", length = 2)
	@TrailLog
	public String getIcLocalizacaoUltimoInventari() {
		return this.icLocalizacaoUltimoInventari;
	}
	
	public void setIcLocalizacaoUltimoInventari(String icLocalizacaoUltimoInventari) {
		this.icLocalizacaoUltimoInventari = icLocalizacaoUltimoInventari;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_ultimo_inventario_garantia", length = 13)
	@TrailLog
	public Date getDtUltimoInventarioGarantia() {
		return this.dtUltimoInventarioGarantia;
	}
	
	public void setDtUltimoInventarioGarantia(Date dtUltimoInventarioGarantia) {
		this.dtUltimoInventarioGarantia = dtUltimoInventarioGarantia;
	}
	
	@Column(name = "co_responsavel_inventario", length = 7)
	@TrailLog
	public String getCoResponsavelInventario() {
		return this.coResponsavelInventario;
	}
	
	public void setCoResponsavelInventario(String coResponsavelInventario) {
		this.coResponsavelInventario = coResponsavelInventario;
	}
	
	@Column(name = "ic_ativo")
	@TrailLog
	public boolean isIcAtivo() {
		return icAtivo;
	}
	
	public void setIcAtivo(boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
}