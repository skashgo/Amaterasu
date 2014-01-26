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
package br.gov.caixa.siiac.bo.impl.batch;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IGeraAgendaBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.LogVO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.persistence.dao.IFeriadoDAO;
import br.gov.caixa.siiac.persistence.dao.IGeraAgendaDAO;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.siiac.util.LogCEFUtil;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class GeraAgendaBO implements IGeraAgendaBO {

	private static final Integer CONTRATO_NU_CONTRATO = 0;
	private static final Integer CONTRATO_IC_AGENDA_GERADA = 1;
	private static final Integer CONTRATO_IC_TIPO_VERIFICACAO_CONTRATO = 2;
	private static final Integer CONTRATO_NU_UNIDADE = 3;
	private static final Integer CONTRATO_NU_NATURAL = 4;
	private static final Integer CONTRATO_DT_CONTRATO = 5;
	private static final Integer CONTRATO_CO_PRODUTO = 6;

	private static final Integer SVP_NU_SERVICO_VERIFICACAO_PRODUTO = 0;
	private static final Integer SVP_NU_CHKLST_SRVCO_VRFCO_PRODUTO = 1;
	private static final Integer SVP_PZ_VERIFICACAO = 2;
	
	private transient IGeraAgendaDAO geraAgendaDAO;
	private transient IFeriadoDAO feriadoDAO;
	
	private LogVO logVO;
	private void setLogVO(LogVO logVO){
		this.logVO = logVO;
	}
	
	
	@Autowired
	public void setGeraAgendaDAO(IGeraAgendaDAO geraAgendaDAO) {
		this.geraAgendaDAO = geraAgendaDAO;
	}
	
	@Autowired
	public void setFeriadoDAO(IFeriadoDAO feriadoDAO) {
		this.feriadoDAO = feriadoDAO;
	}
	
	/**
	 * Métodos que podem ser acessado de forma externa
	 */
	@Transactional
	public void geraAgendaVerificacaoPreventiva(Integer nuContrato, Integer nuServicoVerificacaoProduto) throws Exception {
		
		LogCEF.debug("geraAgendaVerificacaoPreventiva(" + LogCEFUtil.retornaValor(nuContrato) + ", " +
				LogCEFUtil.retornaValor(nuServicoVerificacaoProduto) + ") INICIANDO");
		
		this.setLogVO(new LogVO());
		Object[] contrato = this.geraAgendaDAO.getContratoGerarAgenda(nuContrato);
		Object[] svp = this.geraAgendaDAO.getServicoVerificacaoProdutoGerarAgenda(nuServicoVerificacaoProduto);

		if(Utilities.notEmpty(contrato) && Utilities.notEmpty(svp)){
			this.gerar(contrato, svp);
		}
		
		LogCEF.debug("geraAgendaVerificacaoPreventiva(" + LogCEFUtil.retornaValor(nuContrato) + ", " +
				LogCEFUtil.retornaValor(nuServicoVerificacaoProduto) + ") TERMINANDO");
	}
	@Transactional
	public void geraAgendaUmContrato(Integer nuContrato) throws Exception {
		
		LogCEF.debug("geraAgendaUmContrato(" + LogCEFUtil.retornaValor(nuContrato) + ") INICIANDO");
		
		this.setLogVO(new LogVO());
		Object[] contrato = this.geraAgendaDAO.getContratoGerarAgenda(nuContrato);
		if(Utilities.notEmpty(contrato)){
			this.gerar(contrato);
		}
		
		LogCEF.debug("geraAgendaUmContrato(" + LogCEFUtil.retornaValor(nuContrato) + ") TERMINANDO");
	}	
	@Transactional
	public void geraAgendaTodosContratos(LogVO logVO) throws Exception {
		LogCEF.debug("geraAgendaTodosContratos(" + LogCEFUtil.retornaValor(logVO) + ") INICIANDO");
		LogCEF.info("---------------- [GERAÇÃO_AGENDA] Início da rotina ----------------");
		dataInicio = new Date();
		this.setLogVO(logVO);
		qtdDiasPrazo = this.geraAgendaDAO.getMaiorPrazoServicoVerificacao();
		List<Object[]> contratos = this.geraAgendaDAO.getContratosAGerarAgenda();
		this.gerar(contratos);
		LogCEF.info("---------------- [GERAÇÃO_AGENDA] Final da rotina ----------------");
		LogCEF.info("Tempo de execução: " + getTempoExecucao());
		LogCEF.debug("geraAgendaTodosContratos(" + LogCEFUtil.retornaValor(logVO) + ") TERMINANDO");
	}
	
	/**
	 * Métodos orquestradores, que apenas organizam quais métodos irão trabalhar e em qual ordem.
	 */
	private void gerar(List<Object[]> lista) throws Exception {
		
		LogCEF.debug("gerar [List<Object[]>](" + LogCEFUtil.retornaValor(lista) + ") INICIANDO");
		
		if(lista == null || lista.isEmpty()){
			return;
		}
		QTD_TOTAL = Double.valueOf(lista.size());
		QTD_PROCESSADO = 0d;
		for(Object[] contrato : lista) {
			QTD_PROCESSADO++;
			
			try {
				this.gerar(contrato);
				LogCEF.debug("gerar [List<Object[]>](" + LogCEFUtil.retornaValor(lista) + ") TERMINANDO");
			} catch (Exception e) {
				LogCEF.error("Erro ao gerar agenda do contrato nº " + contrato[CONTRATO_NU_CONTRATO]);
				e.printStackTrace();
				throw e;
			}
		}
	}	
	private void gerar(Object[] contrato) throws Exception {
		LogCEF.debug("gerar [Object[]](" + LogCEFUtil.retornaValor(contrato) + ") INICIANDO");
		this.gerar(contrato, null);
		LogCEF.debug("gerar [Object[]](" + LogCEFUtil.retornaValor(contrato) + ") TERMINANDO");
	}	
	private void gerar(Object[] contrato, Object[] svp) throws Exception{
		LogCEF.debug("Gera agenda do contrato de nº " + contrato[CONTRATO_NU_CONTRATO] + " INICIANDO");
		
		// Filtra inserção e alteração
		if(Utilities.notEmpty(contrato[CONTRATO_IC_AGENDA_GERADA]) && contrato[CONTRATO_IC_AGENDA_GERADA].equals(Contrato.AGENDA_GERADA_ID_INSERIDA)){
			
			List<Object[]> servicos = null;
			
			
			// Quando for verificação preventiva inserida pelo sistema, o serviço a ser trabalho foi um parâmetro selecionado pelo usuário.
			if(Utilities.notEmpty(svp)){
				servicos = new ArrayList<Object[]>();
				servicos.add(svp);
			}
			// Quando for verificação preventiva inserida pela importação, os serviços a serem trabalhados serão aqueles vinculados ao produto 
			// 	   com estado ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_VERIFICACAO_PREVENTIVA
			else if(contrato[CONTRATO_IC_TIPO_VERIFICACAO_CONTRATO] != null && 
					contrato[CONTRATO_IC_TIPO_VERIFICACAO_CONTRATO].equals(Contrato.TIPO_VERIFICACAO_CONTRATO_ID_VERIFICACAO_PREVENTIVA)){
				servicos = this.geraAgendaDAO.getServicosByContratoVerificacaoPreventiva((String) contrato[CONTRATO_CO_PRODUTO], (Date) contrato[CONTRATO_DT_CONTRATO]);
			} 
			// Quando for contrato, os serviços a serem trabalhados são aqueles vinculados ao produto 
			//     com estado ServicoProdutoMB.TIPO_ACAO_PRODUTO_VERIFICACAO_LABEL_INCLUSAO_ALTERACAO_DADOS_SITUACAO
			else {
				servicos = this.geraAgendaDAO.getServicosByContratoInclusao((String) contrato[CONTRATO_CO_PRODUTO], (Date) contrato[CONTRATO_DT_CONTRATO]);
				
			}	
			
			for(Object[] servico : servicos) {
				try{
					this.insereVerificacoesByContrato(contrato, servico);
				} catch (SIIACException siiacexception) {
					LogCEF.error(siiacexception.getMessage());
				}
			}
		}else{
			// Insere serviços de contratos alterados, que ainda não possuem verificação.
			List<Object[]> servicos = this.geraAgendaDAO.getServicosSemVerificacaoByContratoAlteracao( (Integer) contrato[CONTRATO_NU_CONTRATO], (String) contrato[CONTRATO_CO_PRODUTO], (Date) contrato[CONTRATO_DT_CONTRATO], (Character) contrato[CONTRATO_IC_AGENDA_GERADA]);
			for(Object[] servico : servicos){
				try{
					this.insereVerificacoesByContrato(contrato, servico);
				} catch (SIIACException siiacexception) {
					
				}
			}
			
			// Atualiza verificações sem parecer
			servicos = this.geraAgendaDAO.getServicosVerificacaoSemParecerByContratoAlteracao((Integer) contrato[CONTRATO_NU_CONTRATO], (String) contrato[CONTRATO_CO_PRODUTO], (Character) contrato[CONTRATO_IC_AGENDA_GERADA]);
			for(Object[] servico : servicos){
				this.geraAgendaDAO.marcaVerificacaoComoNaoVerificada((Integer) contrato[CONTRATO_NU_CONTRATO], (Integer) servico[SVP_NU_SERVICO_VERIFICACAO_PRODUTO]);
				this.geraAgendaDAO.marcaApontamentoVerificacaoComoNaoVerificado((Integer) contrato[CONTRATO_NU_CONTRATO], (Integer) servico[SVP_NU_SERVICO_VERIFICACAO_PRODUTO]);
				this.geraAgendaDAO.apagaObservacoesVerificacao((Integer) contrato[CONTRATO_NU_CONTRATO], (Integer) servico[SVP_NU_SERVICO_VERIFICACAO_PRODUTO]);
				addContRegistrosAlterados();
			}
			

			// Atualiza verificações com parecer
			servicos = this.geraAgendaDAO.getServicosVerificacaoComParecerByContratoAlteracao((Integer) contrato[CONTRATO_NU_CONTRATO], (String) contrato[CONTRATO_CO_PRODUTO], (Character) contrato[CONTRATO_IC_AGENDA_GERADA]);
			for(Object[] servico : servicos){
				Integer nuVerificacaoContrato = this.insereVerificacaoReplica(contrato, servico);
//				VerificacaoContratoParecer verificacaoContratoParecer = this.geraAgendaDAO.getVerificacaoComParecerByContratoServico(contrato, servico);
//				VerificacaoContrato verificacaoContrato = this.insereVerificacaoReplica(verificacaoContratoParecer);
				this.geraAgendaDAO.insereResultadoApontamentoReplica(nuVerificacaoContrato, (Integer) contrato[CONTRATO_NU_CONTRATO], (Integer) servico[SVP_NU_SERVICO_VERIFICACAO_PRODUTO]);
				this.geraAgendaDAO.atualizaUltimaHierarquiaVerificacaoAnterior((Integer) contrato[CONTRATO_NU_CONTRATO], (Integer) servico[SVP_NU_SERVICO_VERIFICACAO_PRODUTO]);
//				
//				List<ResultadoApontamentoProdutoParecer> resultadoApontamentos = this.geraAgendaDAO.getResultadoApontamentosPorVerificacaoParecer(verificacaoContratoParecer);
//				for(ResultadoApontamentoProdutoParecer resultado : resultadoApontamentos){
//					this.insereResultadoApontamentoReplica(verificacaoContrato, resultado);
//				}
//				
				addContRegistrosAlterados();
			}
		}
		
		this.geraAgendaDAO.updateContratoAgendaGerada( (Integer) contrato[CONTRATO_NU_CONTRATO]);
		LogCEF.debug("Gera agenda do contrato de nº " + contrato[CONTRATO_NU_CONTRATO] + " TERMINANDO");
	}

	/**
	 * Organiza a inserção de um verificação com seus apontamentos, além de adicionar '1' ao contRegistrosInseridos.
	 * @param contrato
	 * @param svp
	 * @throws SIIACException
	 * @throws Exception
	 */
	private void insereVerificacoesByContrato(Object[] contrato, Object[] svp) throws SIIACException, Exception {
		LogCEF.debug("insereVerificacoesByContrato(" + LogCEFUtil.retornaValor(contrato) + ", " +
				LogCEFUtil.retornaValor(svp) + ") INICIANDO");
		Integer nuVerificacaoContrato = this.insereVerificacao(contrato, svp);
		if(contrato[CONTRATO_IC_TIPO_VERIFICACAO_CONTRATO] != null && contrato[CONTRATO_IC_TIPO_VERIFICACAO_CONTRATO].equals(Contrato.TIPO_VERIFICACAO_CONTRATO_ID_CONTRATO)) {
			this.geraAgendaDAO.insereResultadoApontamento(nuVerificacaoContrato, (Integer) contrato[CONTRATO_NU_CONTRATO], (Integer) svp[SVP_NU_CHKLST_SRVCO_VRFCO_PRODUTO]);
		}
		
		addContRegistrosInseridos();
		LogCEF.debug("insereVerificacoesByContrato(" + LogCEFUtil.retornaValor(contrato) + ", " +
				LogCEFUtil.retornaValor(svp) + ") TERMINANDO");
	}

	/**
	 * Insere uma verificação
	 * @param contrato
	 * @param svp
	 */
	private Integer insereVerificacao(Object[] contrato, Object[] svp) throws SIIACException, Exception {
		LogCEF.debug("insereVerificacao(" + LogCEFUtil.retornaValor(contrato) + ", " + LogCEFUtil.retornaValor(svp) + ") INICIANDO");
		
		Date dtInclusao = new Date();
		Date dtLimiteVerificacao = getDataLimiteVerificacao(dtInclusao, ((Integer) svp[SVP_PZ_VERIFICACAO]), (Integer) contrato[CONTRATO_NU_NATURAL], (Short) contrato[CONTRATO_NU_UNIDADE]);
		
		LogCEF.debug("insereVerificacao(" + LogCEFUtil.retornaValor(contrato) + ", " + LogCEFUtil.retornaValor(svp) + ") TERMINANDO");
		
		return this.geraAgendaDAO.insereVerificacao((Integer) contrato[CONTRATO_NU_CONTRATO], (Integer) svp[SVP_NU_SERVICO_VERIFICACAO_PRODUTO], (Short) contrato[CONTRATO_NU_UNIDADE], (Integer) contrato[CONTRATO_NU_NATURAL], dtInclusao, dtLimiteVerificacao, VerificacaoContratoVO.VERIFICACAO_NAO_VERIFICADO, null, (Integer) svp[SVP_NU_CHKLST_SRVCO_VRFCO_PRODUTO]);
	}
	
	/**
	 * Insere uma réplica de uma verificação que já possuia parecer.
	 * @param verificacaoContratoParecer
	 * @return
	 * @throws SIIACException
	 * @throws Exception
	 */
	private Integer insereVerificacaoReplica(Object[] contrato, Object[] svp) throws SIIACException, Exception {
		
		LogCEF.debug("insereVerificacaoReplica(" + LogCEFUtil.retornaValor(contrato) + ", " + LogCEFUtil.retornaValor(svp) + ") INICIANDO");
		
		Date dtInclusao = new Date();
		Date dtLimiteVerificacao = getDataLimiteVerificacao(dtInclusao, ((Integer) svp[SVP_PZ_VERIFICACAO]), (Integer) contrato[CONTRATO_NU_NATURAL], (Short) contrato[CONTRATO_NU_UNIDADE]);
		
		LogCEF.debug("insereVerificacaoReplica(" + LogCEFUtil.retornaValor(contrato) + ", " + LogCEFUtil.retornaValor(svp) + ") TERMINANDO");
		return this.geraAgendaDAO.insereVerificacaoReplica((Integer) contrato[CONTRATO_NU_CONTRATO], (Integer) svp[SVP_NU_SERVICO_VERIFICACAO_PRODUTO], dtInclusao, dtLimiteVerificacao, VerificacaoContratoVO.VERIFICACAO_NAO_VERIFICADO);
		
//		VerificacaoContrato verificacaoContrato = new VerificacaoContrato();
//		verificacaoContrato.setChecklist(this.checklistDAO.findById(verificacaoContratoParecer.getNuChecklistServicoVerificacaoProduto()));
//		verificacaoContrato.setContrato(this.contratoBO.findById(verificacaoContratoParecer.getNuContrato()));
//		verificacaoContrato.setDtInclusaoVerificacao(new Date());
//		verificacaoContrato.setServicoVerificacaoProduto(this.servicoVerificacaoProdutoDAO.findById(verificacaoContratoParecer.getNuServicoVerificacaoProduto()));
//		verificacaoContrato.setDtLimiteVerificacao(getDataLimiteVerificacao(verificacaoContrato.getDtInclusaoVerificacao(), verificacaoContrato.getServicoVerificacaoProduto().getPzVerificacao(), verificacaoContratoParecer.getNuNaturalUnidadeResponsavelVerificacao(), verificacaoContratoParecer.getNuUnidadeResponsavelVerificacao()));
//		verificacaoContrato.setIcEstadoVerificacao(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_NAO_VERIFICADA);
//		verificacaoContrato.setIcSuspensa(verificacaoContratoParecer.getIcSuspensa());
//		verificacaoContrato.setIcUltimaHierarquia(Boolean.TRUE);
//		verificacaoContrato.setNuNaturalUnidadeResponsavelVerificacao(verificacaoContratoParecer.getNuNaturalUnidadeResponsavelVerificacao());
//		verificacaoContrato.setNuUnidadeResponsavelVerificacao(verificacaoContratoParecer.getNuUnidadeResponsavelVerificacao());
//		verificacaoContrato.setNuVerificacaoContratoPai(verificacaoContratoParecer.getNuVerificacaoContrato());
//		
//		this.verificacaoContratoDAO.saveOrUpdate(verificacaoContrato);
//		return verificacaoContrato;
	}

//	/**
//	 * Insere réplica dos apontamentos de uma verificação que já possuía parecer.
//	 * @param apt
//	 * @param svp
//	 * @param contrato
//	 */
//	private ResultadoApontamentoProduto insereResultadoApontamentoReplica(
//					VerificacaoContrato verificacaoContrato, 
//					ResultadoApontamentoProdutoParecer resultadoApontamentoParecer) {
//		ResultadoApontamentoProduto resultado = new ResultadoApontamentoProduto();
//		resultado.setApontamentoChecklistProduto(this.apontamentoChecklistProdutoDAO.findById(resultadoApontamentoParecer.getNuApontamentoChecklistProduto()));
//		resultado.setContrato(verificacaoContrato.getContrato());
//		resultado.setCoResponsavelResultado("");
//		resultado.setIcResultadoApontamentoChecklist(ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA);
//		resultado.setVerificacaoContrato(verificacaoContrato);
//		
//		this.resultadoApontamentoProdutoDAO.saveOrUpdate(resultado);
//		return resultado;
//	}
	
	/**
	 * Calcula a data limite da verificação, de acordo com a data da inclusão da verificação e o prazo em dias que essa verificação pode ter.
	 * 
	 * Este método realiza um cálculo de dias, considerando apenas os dias úteis. 
	 * 
	 * @param dtInclusaoVerificacao
	 * @param pzVerificacao
	 * @return
	 */
	private Date getDataLimiteVerificacao(Date dtInclusaoVerificacao, Integer pzVerificacao, Integer unidadeNatural, Short unidade) {
		
		LogCEF.debug("insereVerificacaoReplica("
				+ LogCEFUtil.retornaValor(dtInclusaoVerificacao) + ", "
				+ LogCEFUtil.retornaValor(pzVerificacao) + ", "
				+ LogCEFUtil.retornaValor(unidadeNatural) + ", "
				+ LogCEFUtil.retornaValor(unidade) + ") INICIANDO");
		
		Calendar calendario = Calendar.getInstance();
		Utilities.ignoreTime(dtInclusaoVerificacao);
		calendario.setTime(dtInclusaoVerificacao);

		List<String> feriadosUnidade = null;
		if(feriadosMap.containsKey(unidade)){
			feriadosUnidade = feriadosMap.get(unidade);
		}else{
			feriadosUnidade = feriadoDAO.getNextFeriados(unidade, unidadeNatural, getPrazoABuscarFeriados());
			feriadosMap.put(unidade, feriadosUnidade);
		}
		Long contador = 0l;
		while(contador < pzVerificacao){
			calendario.add(Calendar.DATE, 1);
			if(calendario.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
				calendario.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && 
				!feriadosUnidade.contains(sdf.format(calendario.getTime()))){
				contador++;
			}			
		}		
		
		LogCEF.debug("insereVerificacaoReplica("
				+ LogCEFUtil.retornaValor(dtInclusaoVerificacao) + ", "
				+ LogCEFUtil.retornaValor(pzVerificacao) + ", "
				+ LogCEFUtil.retornaValor(unidadeNatural) + ", "
				+ LogCEFUtil.retornaValor(unidade) + ") TERMINANDO");
		return calendario.getTime();
	}

	/**
	 * Adiciona '1' ao contador de registros inseridos. 
	 */
	private void addContRegistrosInseridos(){
		if(logVO.getQtRegistrosIncluidos() == null){
			logVO.setQtRegistrosIncluidos(1);
		}else{
			logVO.setQtRegistrosIncluidos(logVO.getQtRegistrosIncluidos() + 1);
		}
	}

	/**
	 * Adiciona '1' ao contador de registros alterados. 
	 */
	private void addContRegistrosAlterados(){
		if(logVO.getQtRegistrosAlterados() == null){
			logVO.setQtRegistrosAlterados(1);
		}else{
			logVO.setQtRegistrosAlterados(logVO.getQtRegistrosAlterados() + 1);
		}
	}

	public static String getTempoExecucao() {
		final long FATOR_SEGUNDO = 1000;
		final long FATOR_MINUTO = FATOR_SEGUNDO * 60;
		final long FATOR_HORA = FATOR_MINUTO * 60;
		
		long tempo = new Date().getTime() - dataInicio.getTime();
		long horas, minutos, segundos, milesimos;
		
		horas = tempo / FATOR_HORA;
		minutos = (tempo % FATOR_HORA) / FATOR_MINUTO;
		segundos = (tempo % FATOR_MINUTO) / FATOR_SEGUNDO;
		milesimos = tempo % FATOR_SEGUNDO;
		
		return horas + " horas, " + minutos + " minutos, " + segundos + " segundos e " + milesimos + " milesimos";
	}
	
	/**
	 * Retorna o valor prazo a buscar os feriados. Este valor é preenchido com o valor do maior prazo de serviço de verificação cadastrado na base.
	 * @return
	 */
	Integer getPrazoABuscarFeriados() {
		return (Utilities.empty(qtdDiasPrazo) ? QUANTIDADE_DIAS_PADRAO : (qtdDiasPrazo * 2));
	}
	
	Map<Short, List<String>> feriadosMap = new HashMap<Short, List<String>>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Integer QUANTIDADE_DIAS_PADRAO = 365;
	Integer qtdDiasPrazo;
	
	public static Double QTD_TOTAL;
	public static Double QTD_PROCESSADO;
	
	public static Date dataInicio;

	public static String getExecuted() {
		if(Utilities.empty(QTD_PROCESSADO)){
			return "0";
		}
		return NumberFormat.getIntegerInstance().format(QTD_PROCESSADO);
	}
	
	public static String getTotal() {
		if(Utilities.empty(QTD_TOTAL)){
			return "0";
		}
		return NumberFormat.getIntegerInstance().format(QTD_TOTAL);
	}
	
	public static String getExecutedPercentage() {
		if(Utilities.empty(QTD_PROCESSADO) || Utilities.empty(QTD_TOTAL)){
			return new DecimalFormat(" ##0.00 %").format(0d);
		}
		return new DecimalFormat(" ##0.00 %").format(QTD_PROCESSADO / QTD_TOTAL);
	}
	
}
