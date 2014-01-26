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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

@Entity
@Table(name = "iactb004_contrato", schema = "iacsm001")
@TrailClass(fonte = "CONTRATO")
public class Contrato implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String SITUACAO_CONTRATO_ID_NAOCT = "NC";
	
	public static final String TIPO_IDENTIFICADOR_NIS_ID = "NIS";
	public static final String TIPO_IDENTIFICADOR_CPF_ID = "CPF";
	public static final String TIPO_IDENTIFICADOR_CNPJ_ID = "CNP";
	
	public static final String TIPO_IDENTIFICADOR_NIS_LABEL = "NIS";
	public static final String TIPO_IDENTIFICADOR_CPF_LABEL = "CPF";
	public static final String TIPO_IDENTIFICADOR_CNPJ_LABEL = "CNPJ";
	
	public static final String TIPO_PESSOA_FISICA_ID = "PF";
	public static final String TIPO_PESSOA_JURIDICA_ID = "PJ";
	
	public static final String TIPO_PESSOA_FISICA_LABEL = "PF";
	public static final String TIPO_PESSOA_JURIDICA_LABEL = "PJ";
	
	public static final String SITUACAO_CONTRATO_ID_ATIVO = "AT";
	public static final String SITUACAO_CONTRATO_ID_VNCDO = "VN";
	public static final String SITUACAO_CONTRATO_ID_INDME = "IN";
	public static final String SITUACAO_CONTRATO_ID_LQDCO = "EL";
	public static final String SITUACAO_CONTRATO_ID_EECRR = "EE";	
	public static final String SITUACAO_CONTRATO_ID_LQDDO = "LQ";
	public static final String SITUACAO_CONTRATO_ID_IRRGL = "IR";
	
	public static final String SITUACAO_CONTRATO_LABEL_ATIVO = "ATIVO";
	public static final String SITUACAO_CONTRATO_LABEL_VNCDO = "VENCIDO";
	public static final String SITUACAO_CONTRATO_LABEL_INDME = "INADIMPLENTE";
	public static final String SITUACAO_CONTRATO_LABEL_LQDCO = "EM LIQUIDAÇÃO";
	public static final String SITUACAO_CONTRATO_LABEL_EECRR = "EM ENCERRAMENTO";
	public static final String SITUACAO_CONTRATO_LABEL_LQDDO = "LIQUIDADO";
	public static final String SITUACAO_CONTRATO_LABEL_IRRGL = "IRREGULAR";
	
	public static final Character AGENDA_GERADA_ID_GERACAO_OK = '0';
	public static final Character AGENDA_GERADA_ID_INSERIDA = '1';
	public static final Character AGENDA_GERADA_ID_DADOS_ALTERADOS = '2';
	public static final Character AGENDA_GERADA_ID_SITUACAO_ALTERADA = '3';
	public static final Character AGENDA_GERADA_ID_DADOS_SITUACAO_ALTERADA = '4';
	public static final Character AGENDA_GERADA_ID_GERANDO_AGENDA = '5';
	
	public static final String PRAZO_VERIFICACAO_ID_CONTRATO_VERIFICADO = "OK";
	public static final String PRAZO_VERIFICACAO_ID_CONTRATO_VENCIDO = "FP";
	public static final String PRAZO_VERIFICACAO_ID_CONTRATO_VENCENDO_HOJE = "LH";
	public static final String PRAZO_VERIFICACAO_ID_CONTRATO_VENCENDO_1_DIA = "L1";
	public static final String PRAZO_VERIFICACAO_ID_CONTRATO_VENCENDO_2_DIAS = "L2";
	public static final String PRAZO_VERIFICACAO_ID_CONTRATO_A_VERIFICAR = "AV";
	
	public static final Boolean SIMNAO_ID_SIM = true;
	public static final Boolean SIMNAO_ID_NAO = false;
	
	public static final String SIMNAO_LABEL_SIM = "SIM";
	public static final String SIMNAO_LABEL_NAO = "NAO";
	
	public static final String LOCALIZACAO_CONTRATO_ID_CASA_FORTE = "CF";
	public static final String LOCALIZACAO_CONTRATO_ID_RETIRADA_CASA_FORTE = "RF";
	public static final String LOCALIZACAO_CONTRATO_ID_ENCAMINHADA_LIQUIDACAO = "GI";
	
	public static final String LOCALIZACAO_CONTRATO_LABEL_CASA_FORTE = "Casa Forte";
	public static final String LOCALIZACAO_CONTRATO_LABEL_RETIRADA_CASA_FORTE = "Retirada da Casa Forte";
	public static final String LOCALIZACAO_CONTRATO_LABEL_ENCAMINHADA_LIQUIDACAO = "Encaminhada para liquidação";
	
	public static final Character TIPO_VERIFICACAO_CONTRATO_ID_CONTRATO = '1';
	public static final Character TIPO_VERIFICACAO_CONTRATO_ID_VERIFICACAO_PREVENTIVA = '2';
	
	private Integer nuContrato;
	private String coContrato;
	private Short nuOperacao;
	private Short nuModalidade;
	private Produto produto = new Produto();
	private Short nuUnidade;
	private Integer nuNatural;
	private Date dtContrato;
	private String coCliente;
	private String noCliente;
	private String icPfPj;
	private String icTipoIndentificadorCliente;
	private String nuIdentificadorCliente;
	private String coAvaliacaoSiric;
	private Boolean icAvaliacaoSiricAntiga;
	private Date dtInclusao;
	private Boolean icContaAbertaLote;
	private Boolean icAtivo;
	private List<Garantia> garantias = new ArrayList<Garantia>(0);
	private DetalhesContrato detalhesContrato = new DetalhesContrato();
	private String icSituacaoContaContrato;
	private Short nuCanalComercializacao;
	private BigDecimal vrNominalContrato;
	private Character icAgendaGerada = AGENDA_GERADA_ID_INSERIDA;
	private String icPrazoVerificacao;
	private String coAvaliacaoSiricTomador;
	private Character icTipoContrato;
	private Date dtNascimentoCliente;
	private List<Parecer> parecerList = new ArrayList<Parecer>();
	private List<ResultadoApontamentoProduto> resultadoApontamentoProdutoList = new ArrayList<ResultadoApontamentoProduto>();
	private List<VerificacaoContrato> verificacaoContratoList = new ArrayList<VerificacaoContrato>();
	private Short qtPrazoVerificacao;
	private Character icTipoVerificacaoContrato;
	
	public Contrato() {
	}
	
	public Contrato(Integer nuContrato, Produto produto, Short nuUnidade, Integer nuNatural, Date dtContrato, BigDecimal vrNominalContrato, String coCliente, String nuIdentificadorCliente, Date dtInclusao, Boolean icAtivo, String coAvaliacaoSiricTomador) {
		this.nuContrato = nuContrato;
		this.produto = produto;
		this.nuUnidade = nuUnidade;
		this.nuNatural = nuNatural;
		this.dtContrato = dtContrato;
		this.vrNominalContrato = vrNominalContrato;
		this.coCliente = coCliente;
		this.nuIdentificadorCliente = nuIdentificadorCliente;
		this.dtInclusao = dtInclusao;
		this.icAtivo = icAtivo;
		this.coAvaliacaoSiricTomador = coAvaliacaoSiricTomador;
	}
	
	public Contrato(Integer nuContrato, String coContrato, Short nuOperacao, Short nuModalidade, Produto produto, Short nuUnidade, Integer nuNatural, Date dtContrato, BigDecimal vrNominalContrato, String coCliente, String noCliente, String icPfPj, String icTipoIndentificadorCliente, String nuIdentificadorCliente, String nuAvaliacaoSiric, Boolean icAvaliacaoSiricAntiga, Date dtInclusao, Boolean icContaAbertaLote, Boolean icAtivo, List<Garantia> garantias, DetalhesContrato detalhesContrato, String coAvaliacaoSiricTomador, Character icTipoContrato) {
		this.nuContrato = nuContrato;
		this.coContrato = coContrato;
		this.nuOperacao = nuOperacao;
		this.nuModalidade = nuModalidade;
		this.produto = produto;
		this.nuUnidade = nuUnidade;
		this.nuNatural = nuNatural;
		this.dtContrato = dtContrato;
		this.vrNominalContrato = vrNominalContrato;
		this.coCliente = coCliente;
		this.noCliente = noCliente;
		this.icPfPj = icPfPj;
		this.icTipoIndentificadorCliente = icTipoIndentificadorCliente;
		this.nuIdentificadorCliente = nuIdentificadorCliente;
		this.coAvaliacaoSiric = nuAvaliacaoSiric;
		this.icAvaliacaoSiricAntiga = icAvaliacaoSiricAntiga;
		this.dtInclusao = dtInclusao;
		this.icContaAbertaLote = icContaAbertaLote;
		this.icAtivo = icAtivo;
		this.coAvaliacaoSiricTomador = coAvaliacaoSiricTomador;
		this.garantias = garantias;
		this.detalhesContrato = detalhesContrato;
		this.icTipoContrato = icTipoContrato;
	}
	
	@Id
	@Column(name = "nu_contrato", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "iacsq016_contrato")
	@SequenceGenerator(name = "iacsq016_contrato", sequenceName = "iacsm001.iacsq016_contrato")
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public Integer getNuContrato() {
		return this.nuContrato;
	}
	
	public void setNuContrato(Integer nuContrato) {
		this.nuContrato = nuContrato;
	}
	
	@Column(name = "co_contrato_conta", length = 26)
	@TrailLog(deleteID = true, insertID = true, updateID = true)
	public String getCoContrato() {
		return this.coContrato;
	}
	
	public void setCoContrato(String coContrato) {
		this.coContrato = coContrato;
	}
	
	@Column(name = "nu_operacao")
	@TrailLog
	public Short getNuOperacao() {
		return this.nuOperacao;
	}
	
	public void setNuOperacao(Short nuOperacao) {
		this.nuOperacao = nuOperacao;
	}
	
	@Column(name = "nu_modalidade")
	@TrailLog
	public Short getNuModalidade() {
		return this.nuModalidade;
	}
	
	public void setNuModalidade(Short nuModalidade) {
		this.nuModalidade = nuModalidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "co_produto", nullable = false)
	public Produto getProduto() {
		return this.produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@Transient
	@TrailLog
	public String getProdutoTrilha(){
		return this.produto.getCoProduto();
	}
	
	@Column(name = "nu_unidade_rspnl_contrato", nullable = false)
	@TrailLog
	public Short getNuUnidade() {
		return this.nuUnidade;
	}
	
	public void setNuUnidade(Short nuUnidade) {
		this.nuUnidade = nuUnidade;
	}
	
	@Column(name = "nu_natural_undde_rspnl_contrato", nullable = false)
	@TrailLog
	public Integer getNuNatural() {
		return this.nuNatural;
	}
	
	public void setNuNatural(Integer nuNatural) {
		this.nuNatural = nuNatural;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_contrato", nullable = true, length = 13)
	@TrailLog
	public Date getDtContrato() {
		return this.dtContrato;
	}
	
	public void setDtContrato(Date dtContrato) {
		this.dtContrato = dtContrato;
	}
	
	@Column(name = "vr_nominal_contrato", precision = 16)
	@TrailLog
	public BigDecimal getVrNominalContrato() {
		return this.vrNominalContrato;
	}
	
	public void setVrNominalContrato(BigDecimal vrNominalContrato) {
		this.vrNominalContrato = vrNominalContrato;
	}
	
	@Column(name = "co_cliente", nullable = true, length = 13)
	@TrailLog
	public String getCoCliente() {
		return this.coCliente;
	}
	
	public void setCoCliente(String coCliente) {
		this.coCliente = coCliente;
	}
	
	@Column(name = "no_cliente", length = 70)
	@TrailLog
	public String getNoCliente() {
		return this.noCliente;
	}
	
	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}
	
	@Column(name = "ic_pf_pj", length = 2)
	@TrailLog
	public String getIcPfPj() {
		return this.icPfPj;
	}
	
	public void setIcPfPj(String icPfPj) {
		this.icPfPj = icPfPj;
	}
	
	@Column(name = "ic_tipo_indentificador_cliente", length = 3)
	@TrailLog
	public String getIcTipoIndentificadorCliente() {
		return this.icTipoIndentificadorCliente;
	}
	
	public void setIcTipoIndentificadorCliente(String icTipoIndentificadorCliente) {
		this.icTipoIndentificadorCliente = icTipoIndentificadorCliente;
	}
	
	@Column(name = "nu_identificador_cliente", nullable = false)
	@TrailLog
	public String getNuIdentificadorCliente() {
		return this.nuIdentificadorCliente;
	}
	
	public void setNuIdentificadorCliente(String nuIdentificadorCliente) {
		this.nuIdentificadorCliente = nuIdentificadorCliente;
	}
	
	@Column(name = "co_avaliacao_siric", length = 11)
	@TrailLog
	public String getCoAvaliacaoSiric() {
		return this.coAvaliacaoSiric;
	}
	
	public void setCoAvaliacaoSiric(String coAvaliacaoSiric) {
		this.coAvaliacaoSiric = coAvaliacaoSiric;
	}
	
	@Column(name = "ic_avaliacao_siric_antiga")
	@TrailLog
	public Boolean getIcAvaliacaoSiricAntiga() {
		return this.icAvaliacaoSiricAntiga;
	}
	
	public void setIcAvaliacaoSiricAntiga(Boolean icAvaliacaoSiricAntiga) {
		this.icAvaliacaoSiricAntiga = icAvaliacaoSiricAntiga;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_inclusao", nullable = false, length = 13)
	@TrailLog
	public Date getDtInclusao() {
		return this.dtInclusao;
	}
	
	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}
	
	@Column(name = "ic_conta_aberta_lote")
	@TrailLog
	public Boolean getIcContaAbertaLote() {
		return this.icContaAbertaLote;
	}
	
	public void setIcContaAbertaLote(Boolean icContaAbertaLote) {
		this.icContaAbertaLote = icContaAbertaLote;
	}
	
	@Column(name = "ic_ativo", nullable = false)
	@TrailLog
	public Boolean getIcAtivo() {
		return this.icAtivo;
	}
	
	public void setIcAtivo(Boolean icAtivo) {
		this.icAtivo = icAtivo;
	}
	
	@Column(name = "co_avaliacao_siric_tomador", length = 11)
	@TrailLog
	public String getCoAvaliacaoSiricTomador() {
		return this.coAvaliacaoSiricTomador;
	}
	
	public void setCoAvaliacaoSiricTomador(String coAvaliacaoSiricTomador) {
		this.coAvaliacaoSiricTomador = coAvaliacaoSiricTomador;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato")
	public List<Garantia> getGarantias() {
		return this.garantias;
	}
	
	public void setGarantias(List<Garantia> garantias) {
		this.garantias = garantias;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	@TrailLog(inner = true)
	public DetalhesContrato getDetalhesContrato() {
		return this.detalhesContrato;
	}
	
	public void setDetalhesContrato(DetalhesContrato detalhesContrato) {
		this.detalhesContrato = detalhesContrato;
	}
	
	@Column(name = "ic_situacao_conta_contrato")
	@TrailLog
	public String getIcSituacaoContaContrato() {
		return icSituacaoContaContrato;
	}
	
	public void setIcSituacaoContaContrato(String icSituacaoContaContrato) {
		this.icSituacaoContaContrato = icSituacaoContaContrato;
	}
	
	@Column(name = "nu_canal_cmrco", nullable = true)
	@TrailLog
	public Short getNuCanalComercializacao() {
		return nuCanalComercializacao;
	}
	
	public void setNuCanalComercializacao(Short nuCanalCmrco) {
		this.nuCanalComercializacao = nuCanalCmrco;
	}
	
	@Column(name = "ic_agenda_gerada", nullable = false)
	@TrailLog
	public Character getIcAgendaGerada() {
		return icAgendaGerada;
	}
	
	public void setIcAgendaGerada(Character icAgendaGerada) {
		this.icAgendaGerada = icAgendaGerada;
	}
	
	@Column(name = "ic_prazo_verificacao")
	@TrailLog
	public String getIcPrazoVerificacao() {
		return icPrazoVerificacao;
	}
	
	public void setIcPrazoVerificacao(String icPrazoVerificacao) {
		this.icPrazoVerificacao = icPrazoVerificacao;
	}
	
	@OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY)
	public List<Parecer> getParecerList() {
		return parecerList;
	}
	
	public void setParecerList(List<Parecer> parecerList) {
		this.parecerList = parecerList;
	}
	
	@OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY)
	public List<ResultadoApontamentoProduto> getResultadoApontamentoProdutoList() {
		return resultadoApontamentoProdutoList;
	}
	
	public void setResultadoApontamentoProdutoList(List<ResultadoApontamentoProduto> resultadoApontamentoProdutoList) {
		this.resultadoApontamentoProdutoList = resultadoApontamentoProdutoList;
	}
	
	@OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY)
	public List<VerificacaoContrato> getVerificacaoContratoList() {
		return verificacaoContratoList;
	}
	
	public void setVerificacaoContratoList(List<VerificacaoContrato> verificacaoContratoList) {
		this.verificacaoContratoList = verificacaoContratoList;
	}

	@Column(name = "ic_tipo_contrato", nullable=false)
	@TrailLog
	public Character getIcTipoContrato() {
		return icTipoContrato;
	}

	public void setIcTipoContrato(Character icTipoContrato) {
		this.icTipoContrato = icTipoContrato;
	}

	@Column(name = "dt_nscmo_cliente", nullable=true)
	@TrailLog
	public Date getDtNascimentoCliente() {
		return dtNascimentoCliente;
	}

	public void setDtNascimentoCliente(Date dtNascimentoCliente) {
		this.dtNascimentoCliente = dtNascimentoCliente;
	}
	
	@Column(name = "qt_prazo_verificacao")
	@TrailLog
	public Short getQtPrazoVerificacao() {
		return qtPrazoVerificacao;
	}

	public void setQtPrazoVerificacao(Short qtPrazoVerificacao) {
		this.qtPrazoVerificacao = qtPrazoVerificacao;
	}

	@Column(name = "ic_tipo_verificacao_contrato")
	@TrailLog
	public Character getIcTipoVerificacaoContrato() {
		return icTipoVerificacaoContrato;
	}

	public void setIcTipoVerificacaoContrato(Character icTipoVerificacaoContrato) {
		this.icTipoVerificacaoContrato = icTipoVerificacaoContrato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuContrato == null) ? 0 : nuContrato.hashCode());
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
		Contrato other = (Contrato) obj;
		if (nuContrato == null) {
			if (other.nuContrato != null)
				return false;
		} else if (!nuContrato.equals(other.nuContrato))
			return false;
		return true;
	}

	
}
