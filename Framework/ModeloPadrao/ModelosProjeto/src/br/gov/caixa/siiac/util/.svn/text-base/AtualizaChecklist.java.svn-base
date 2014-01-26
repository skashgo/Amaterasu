package br.gov.caixa.siiac.util;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IChecklistBO;

public class AtualizaChecklist implements Job {
	
	private transient IChecklistBO checklistBO;
	private static final Logger LOG = Logger.getLogger(AtualizaChecklist.class);
	
	/**
	 * execute
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ApplicationContext ctx = AppContext.getApplicationContext();
		this.checklistBO = (IChecklistBO) ctx.getBean("checklistBO");
		
		LOG.info("[SIIAC] <JOB Atualizacao checklist: begin>");
		
		try {
			checklistBO.updateSituacaoChecklist();
		} catch (DAOException e) {
			LogCEF.error("[SIIAC] Erro em executar rotina de atualização de situação dos checklist " + e.getMessage());
		}
		
		LOG.info("[SIIAC] <JOB Atualizacao checklist: end>");
	}
	
}
