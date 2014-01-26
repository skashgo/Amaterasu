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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IAtualizaPrazoVerificacaoBO;
import br.gov.caixa.siiac.persistence.dao.IAtualizaPrazoVerificacaoDAO;
import br.gov.caixa.siiac.persistence.dao.IFeriadoDAO;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.siiac.util.LogCEFUtil;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class AtualizaPrazoVerificacaoBO implements IAtualizaPrazoVerificacaoBO {
	private static final Integer CONTRATO_NU_CONTRATO = 0;
	private static final Integer CONTRATO_NU_UNIDADE = 1;
	private static final Integer CONTRATO_NU_NATURAL = 2;
	
	private IAtualizaPrazoVerificacaoDAO atualizaPrazoVerificacaoDAO;
	private IFeriadoDAO feriadoDAO;
	
	@Autowired
	public void setAtualizaPrazoVerificacaoDAO(IAtualizaPrazoVerificacaoDAO atualizaPrazoVerificacaoDAO) {
		this.atualizaPrazoVerificacaoDAO = atualizaPrazoVerificacaoDAO;
	}
	
	@Autowired
	public void setFeriadoDAO(IFeriadoDAO feriadoDAO) {
		this.feriadoDAO = feriadoDAO;
	}
	
	@SuppressWarnings("all")
	@Transactional
	public void atualizaPrazoVerificao(Integer nuContrato) {
		
		LogCEF.debug("atualizaPrazoVerificao(" + LogCEFUtil.retornaValor(nuContrato) + ") INICIANDO");
		
		dataInicio = new Date();
		this.atualizaPrazoVerificacaoDAO.updateContratoVerificadoComoOk(nuContrato);
		List list = atualizaPrazoVerificacaoDAO.getContratoNaoVerificado(nuContrato);

		QTD_TOTAL = Double.valueOf(list.size());
		QTD_PROCESSADO = 0d;
		for (Object o : list) {
			QTD_PROCESSADO++;
			Object[] obj = (Object[]) o;
			try {
				Short qtPrazo = qtPrazoVerificacao(obj);
				this.atualizaPrazoVerificacaoDAO.updatePrazoContratoNaoVerificado( (Integer) obj[CONTRATO_NU_CONTRATO], qtPrazo);
				
				LogCEF.debug("atualizaPrazoVerificao(" + LogCEFUtil.retornaValor(nuContrato) + ") TERMINANDO");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param c
	 * @return
	 */
	private Short qtPrazoVerificacao(Object[] obj) {
		
		LogCEF.debug("qtPrazoVerificacao(" + LogCEFUtil.retornaValor(obj) + ") INICIANDO");
		
		Date menorData = atualizaPrazoVerificacaoDAO.getMenorDataLimiteServicoVerificacao((Integer) obj[CONTRATO_NU_CONTRATO]);
		Calendar dataSistema = Calendar.getInstance();
		dataSistema.set(Calendar.HOUR, 0);
		dataSistema.set(Calendar.MINUTE, 0);
		dataSistema.set(Calendar.SECOND, 0);
		dataSistema.set(Calendar.MILLISECOND, 0);
		dataSistema.set(Calendar.AM_PM, Calendar.AM);

		
		Short unidade = (Short) obj[CONTRATO_NU_UNIDADE];
		Integer natural = (Integer) obj[CONTRATO_NU_NATURAL];;
		Integer varQtdFeriados = null;
		if (menorData == null) {
			return null;
		} else {
			Calendar data1 = Calendar.getInstance(), data2 = Calendar.getInstance();
			
			if (dataSistema.getTime().compareTo(menorData) > 0) {
				data1.setTime(menorData);
				data1.add(Calendar.DATE, 1);
				
				data2.setTime(dataSistema.getTime());
			} else {

				data1.setTime(dataSistema.getTime());
				data1.add(Calendar.DATE, 1);
				
				data2.setTime(menorData);
			}
			
			varQtdFeriados = feriadoDAO.obtemFeriadosUnidade(unidade, natural, data1.getTime(), data2.getTime());
		}
		Short varQtdDias = 0;
		if (dataSistema.after(menorData)) {
			Long varQtdDiasMillis = (dataSistema.getTimeInMillis() - menorData.getTime());
			varQtdDias = (short) (varQtdDiasMillis / (1000 * 60 * 60 * 24));

			varQtdDias = (short) (varQtdDias - varQtdFeriados);
		} else {
			Calendar menorDataCal = Calendar.getInstance();
			menorDataCal.setTime(menorData);
			Long varQtdDiasMillis = (menorDataCal.getTimeInMillis() - dataSistema.getTimeInMillis());
			varQtdDias = (short) (varQtdDiasMillis / (1000 * 60 * 60 * 24));

			varQtdDias = (short) (varQtdDias - varQtdFeriados);
		}
		
		
		LogCEF.debug("qtPrazoVerificacao(" + LogCEFUtil.retornaValor(obj) + ") TERMINANDO");
		return varQtdDias;
	}
	
	public static Double QTD_TOTAL;
	public static Double QTD_PROCESSADO;
	
	public static Date dataInicio;

	public static String getTempoExecucao() {
		
		LogCEF.debug("getTempoExecucao() INICIANDO");
		
		final long FATOR_SEGUNDO = 1000;
		final long FATOR_MINUTO = FATOR_SEGUNDO * 60;
		final long FATOR_HORA = FATOR_MINUTO * 60;
		
		long tempo = new Date().getTime() - dataInicio.getTime();
		long horas, minutos, segundos, milesimos;
		
		horas = tempo / FATOR_HORA;
		minutos = (tempo % FATOR_HORA) / FATOR_MINUTO;
		segundos = (tempo % FATOR_MINUTO) / FATOR_SEGUNDO;
		milesimos = tempo % FATOR_SEGUNDO;
		
		LogCEF.debug("getTempoExecucao() TERMINANDO");
		return horas + " horas, " + minutos + " minutos, " + segundos + " segundos e " + milesimos + " milesimos";
	}
	
	public static String getExecuted() {
		LogCEF.debug("getExecuted() INICIANDO");
		if(Utilities.empty(QTD_PROCESSADO)){
			return "0";
		}
		
		LogCEF.debug("getExecuted() TERMINANDO");
		return NumberFormat.getIntegerInstance().format(QTD_PROCESSADO);
	}
	
	public static String getTotal() {
		LogCEF.debug("getTotal() INICIANDO");
		if(Utilities.empty(QTD_TOTAL)){
			return "0";
		}
		
		LogCEF.debug("getTotal() TERMINANDO");
		return NumberFormat.getIntegerInstance().format(QTD_TOTAL);
	}
	
	public static String getExecutedPercentage() {
		LogCEF.debug("getExecutedPercentage() INICIANDO");
		if(Utilities.empty(QTD_PROCESSADO) || Utilities.empty(QTD_TOTAL)){
			return new DecimalFormat(" ##0.00 %").format(0d);
		}
		
		LogCEF.debug("getExecutedPercentage() TERMINANDO");
		return new DecimalFormat(" ##0.00 %").format(QTD_PROCESSADO / QTD_TOTAL);
	}
	
}
