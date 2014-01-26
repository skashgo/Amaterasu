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
package br.gov.caixa.siiac.report;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.exception.ReportErrorCreateDatasourceException;
import br.gov.caixa.siiac.exception.ReportFinalNullException;
import br.gov.caixa.siiac.exception.ReportInvalidPathException;
import br.gov.caixa.siiac.util.ReportManager;

/**
 * @author GIS Consult
 *
 */
public class RelatorioChecklist {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public byte[] geraRelatorio(String caminhoRelatorio, String matricula, Integer idChecklist, Connection conn) throws DAOException, ReportInvalidPathException, ReportErrorCreateDatasourceException, ReportFinalNullException, JRException {
		ReportManager report = new ReportManager();
		String barra = System.getProperty("file.separator");
		// preenche o mapa de parâmetros
		Map parametros = new HashMap();
		String path_image = caminhoRelatorio.replace("reports", "images");
		parametros.put("path_image", path_image + barra + "caixa_logo.jpg");
		parametros.put("matricula", matricula);
		parametros.put("cod_checklist", idChecklist);
		// criando os dados que serão passados ao datasource
		caminhoRelatorio += barra + "relatorio_checklist.jasper";
		report.addReport(caminhoRelatorio, parametros, conn);
		return report.exportToPDF();
	}
}
