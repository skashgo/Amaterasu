package br.gov.caixa.siiac.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IResourcePropertiesBO;

@Service()
public class AgendadorTarefas implements ServletContextListener {
	
	public static Scheduler scheduler;
	public static final String grupo = "EnvioDeEmailProgramado";
	private transient IResourcePropertiesBO resourcePropertiesBO;
	private static final Logger LOG = Logger.getLogger("AÇÃO : ");
	
	/**
	 * init
	 * @throws HibernateException
	 */
	protected void init() throws HibernateException {
		try {

			ApplicationContext ctx = AppContext.getApplicationContext();  
	        this.resourcePropertiesBO = (IResourcePropertiesBO) ctx.getBean("resourcePropertiesBO"); 
			
			StdSchedulerFactory schedFactory = new StdSchedulerFactory();
			LOG.info("---------------- Criar Agendador - SIIAC ----------------");
			AgendadorTarefas.scheduler = schedFactory.getScheduler();
			// abrindo a sessao do hibernate
			
			String intervaloUpdate = resourcePropertiesBO.getPropriedade("atualizaChecklist", "thread");
			if (intervaloUpdate == null || intervaloUpdate.trim().equals("")) {
				LOG.info("[SIIAC] A configuração para atualizar o checklist não está definida na tabela de propriedades.\n" + "Não haverá atualização de checklist!!!!!");
			} else {
				JobDetail job = new JobDetail("[SIIAC] Atualização diária automática", Scheduler.DEFAULT_GROUP, AtualizaChecklist.class);
				CronTrigger updateTrigger = new CronTrigger("[SIIAC] Atualização diária automática", Scheduler.DEFAULT_GROUP, "[SIIAC] Atualização diária automática", Scheduler.DEFAULT_GROUP, intervaloUpdate);
				java.util.Date ft = scheduler.scheduleJob(job, updateTrigger);
				LOG.info(job.getFullName() + "[SIIAC] Foi programado para funcionar em: " + ft + " e repete baseado na expressão: " + updateTrigger.getCronExpression());
			}
			
			// iniciando o agendador de tarefas, executara todas os jobs de acordo com as configuracoes dos triggers.
			AgendadorTarefas.scheduler.start();
		} catch (Exception e) {
			LogCEF.error("[SIIAC] Erro ao tentar iniciar o Agendador de Tarefas." + e.getMessage());
		}
	}
	
	/**
	 * contextInitialized
	 */
	public void contextInitialized(ServletContextEvent ce) {
		try {
			init();
		} catch (Exception ex) {
			LogCEF.error(ex.getMessage());			
		}
	}
	
	/**
	 * contextInitialized
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		restart();
	}
	
	/**
	 * restart
	 */
	public void restart() {
		try {
			AgendadorTarefas.scheduler.shutdown(true);
		} catch (Exception ex) {
			LogCEF.error("[SIIAC] Erro ao tentar reiniciar o Agendador de Tarefas");
			LogCEF.error(ex.getMessage());
		}
	}
}