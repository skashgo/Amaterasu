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
package br.gov.caixa.siiac.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.gov.caixa.siiac.bo.IEnviaParecerBO;
import br.gov.caixa.siiac.model.domain.ParecerId;
import br.gov.caixa.siiac.util.AppContext;
import br.gov.caixa.siiac.util.LogCEF;

/**
 * @author GIS Consult
 *
 */
@WebService
public class EnviaParecerWS {
	
	private static IEnviaParecerBO enviaParecerBO;
	
	private Thread thread = null;
	private List<ParecerId> listParecer = new ArrayList<ParecerId>();

	@WebMethod
	public void enviaParecer(Integer nuParecer, Short aaParecer, Short nuUnidade, Short nuNatural)
	{
		if(enviaParecerBO == null){
			enviaParecerBO = (IEnviaParecerBO) AppContext.getApplicationContext().getBean("enviaParecerBO");
		}

		addListParecer(nuParecer, aaParecer, nuUnidade, nuNatural);
	}
	
	public void initializeThread() {
		if(thread == null || ! thread.isAlive()) {
			thread = new Thread() {
				@Override
				public void run() {
					while(true){
						try {
							LogCEF.debug("Tamanho: " + listParecer.size());
							
							if (listParecer != null && !listParecer.isEmpty())
							{
								enviaParecerBO.enviaParecer(listParecer.get(0));
								listParecer.remove(0);
							} else {
								Thread.sleep(10000l);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			thread.start();
		}
	}
	
	public void addListParecer(Integer nuParecer, Short aaParecer, Short nuUnidade, Short nuNatural)
	{
		if (listParecer == null)
			listParecer = new ArrayList<ParecerId>();
		
		listParecer.add(new ParecerId(nuParecer, aaParecer, nuUnidade, nuNatural));
				
		initializeThread();
	}
	
}
