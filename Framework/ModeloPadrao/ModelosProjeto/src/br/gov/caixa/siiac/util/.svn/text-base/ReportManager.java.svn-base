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
package br.gov.caixa.siiac.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import net.sf.jasperreports.engine.util.JRLoader;
import br.gov.caixa.siiac.exception.ReportErrorCreateDatasourceException;
import br.gov.caixa.siiac.exception.ReportFinalNullException;
import br.gov.caixa.siiac.exception.ReportInvalidPathException;


/**
 * 
 * Classe responsável por manipular relatórios (baseado na API JasperReport).<br/>
 * Abstrai a complexidade da API JasperReport na criação e manipulação de relatórios.<br/>
 * Possui uma lista interna de relatórios processados. Cada elemento desta lista é um objeto do tipo JasperPrint. <br/>
 * Uma fonte de dados associada com uma arquivo .jasper, após processados resulta em um objeto JasperPrint, que representa um relatório.<br/>
 * <br/>
 * Um objeto da classe ReportManager permite adicionar um par Fonte de Dados/Arquivo .Jasper, na lista de interna de relatórios.
 * <br/>
 * Este par é processado e transformado em um objeto do tipo JasperPrint, onde este é armazenado na lista interna de relatórios.
 * <br/>
 * 
 * A fonte de dados (Lista de Beans) já deve conter os dados desejados (dados filtrados), não havendo necessidade de passagem de parâmetros.<br/>
 * <br/>
 *
 * Um objeto desta classe possui a funcionalidade de unir vários relatórios, pois em relatórios complexos nem sempre é possível compor em um único<br/>
 * relatório os dados da forma desejada. Assim, criamos individualmente cada relatório, adicionando-os na lista interna, em seguida<br/>
 * gerando um único relatório resultado da união de todos.
 * <br/>
 * <br/>
 * O relatório final unificado, pode ser obtido em formato PDF (array de bytes) ou pode ser salvo em um caminho especificado.
 * <br/>
 * <br/>
 * Exemplo de utilização desta classe:
 * <br>
 * 
 * ReportManager rpm=new ReportManager();<br/>
 * <br/>
 * //Repita a rotina abaixo para quantos relatório deseje criar
 * List<Cliente> list=getListClientes();<br/>
 * String pathFileJasper=getFileJasper();<br/>
 * rpm.addReport(pathFileJasper,list);<br/>
 *  
 * <br/>
 * byte[] pdfBytes= rpm.exportToPDF();<br/>
 * 
 */
public class ReportManager
{
	/**
	 * Lista que armazena os relatórios processados
	 */
	private List<JasperPrint> list=null;
	
	/**
	 * Armazena o relatório final, resultado da união da lista de relatórios
	 */
	private JasperPrint finalReport=null;

	/**
	 * Constrói um ReportManager.
	 */
	public ReportManager()
	{
		this.list=new ArrayList<JasperPrint>();
	}
	
	
	/**
	 * Adiciona um relatório a lista de relatórios.<br/>
	 * Recebe um arquivo .jasper com o layout do relatório desejado, juntamente com uma lista de objetos (Beans) que <br/>
	 * preencherá o arquivo .jasper. <br/>
	 * Este beans deve conter atributos com os mesmos nomes usados na criação do arquivo .jasper, encapsulados em gets/sets <br/>
	 * <br/>
	 * 
	 * @param pathReport: Caminho do arquivo .jasper que contêm o relatório desejado.
	 * @param listBeans: Lista de objetos (Beans) que preencherá o relatório informado em pathReport.
	 * @throws ReportInvalidPathException: Exception que ocorre quando pathReport for nulo, vazio ou o caminho for inválido. 
	 * @throws ReportErrorCreateDatasourceException: Exception que ocorre quando listBeans for nulo, vazio ou não pode ser processado para geração do relatório. 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void addReport(String pathReport,List listBeans) throws ReportInvalidPathException,ReportErrorCreateDatasourceException
	{
		addReport(pathReport, null, listBeans);
	}

	/**
	 * Adiciona um relatório a lista de relatórios.<br/>
	 * Recebe um arquivo .jasper com o layout do relatório desejado, juntamente com uma lista de objetos (Beans) que <br/>
	 * preencherá o arquivo .jasper. <br/>
	 * Este beans deve conter atributos com os mesmos nomes usados na criação do arquivo .jasper, encapsulados em gets/sets <br/>
	 * <br/>
	 * 
	 * @param pathReport: Caminho do arquivo .jasper que contêm o relatório desejado.
	 * @param parameters: Lista de parametros a serem passados para o relatório.
	 * @param listBeans: Lista de objetos (Beans) que preencherá o relatório informado em pathReport.
	 * @throws ReportInvalidPathException: Exception que ocorre quando pathReport for nulo, vazio ou o caminho for inválido. 
	 * @throws ReportErrorCreateDatasourceException: Exception que ocorre quando listBeans for nulo, vazio ou não pode ser processado para geração do relatório. 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void addReport(String pathReport,Map parameters, List listBeans) throws ReportInvalidPathException,ReportErrorCreateDatasourceException
	{
		if (pathReport==null || pathReport.equals(""))
			throw new ReportInvalidPathException("O caminho informado para o relatório é inválido: "+pathReport);

		File file=new File(pathReport);
		
		if (file==null || !file.exists())
			throw new ReportInvalidPathException("O caminho informado para o relatório é inválido: "+pathReport);
		
		if (listBeans==null || listBeans.size()==0)
			throw new ReportErrorCreateDatasourceException("A fonte de dados para criação do relatório é inválida ou esta vazia.");

		JRBeanCollectionDataSource jrBean=null;
		try
		{
			jrBean=new JRBeanCollectionDataSource(listBeans);
		}
		catch (Exception erro)
		{
			throw new ReportErrorCreateDatasourceException("Erro ao processar fonte de dados");
		}
		
		JasperReport jasper=null;
		JasperPrint print=null;
		
		try 
		{
			jasper = (JasperReport) JRLoader.loadObject(pathReport);
			print = JasperFillManager.fillReport(jasper,parameters,jrBean);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new ReportErrorCreateDatasourceException("Erro ao criar relatório com os parâmetros informados");
		}
		
		if (print==null)
			throw new ReportErrorCreateDatasourceException("O relatório gerado para ser adicionado na lista é nulo.");

		list.add(print);
	}
	@SuppressWarnings("rawtypes")
	public void addReport(String pathReport,Map parameters, Object[] bean) throws ReportErrorCreateDatasourceException, ReportInvalidPathException
	{
		if (pathReport==null || pathReport.equals(""))
			throw new ReportInvalidPathException("O caminho informado para o relatório é inválido: "+pathReport);
		
		File file=new File(pathReport);
		
		if (file==null || !file.exists())
			throw new ReportInvalidPathException("O caminho informado para o relatório é inválido: "+pathReport);
		
		if (bean==null || bean.length==0)
			throw new ReportErrorCreateDatasourceException("A fonte de dados para criação do relatório é inválida ou esta vazia.");
		
		JRBeanArrayDataSource jrBean=null;
		try
		{
			jrBean=new JRBeanArrayDataSource(bean);
		}
		catch (Exception erro)
		{
			throw new ReportErrorCreateDatasourceException("Erro ao processar fonte de dados");
		}
		
		JasperReport jasper=null;
		JasperPrint print=null;
		
		try 
		{
			jasper = (JasperReport) JRLoader.loadObject(pathReport);
			print = JasperFillManager.fillReport(jasper,parameters,jrBean);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new ReportErrorCreateDatasourceException("Erro ao criar relatório com os parâmetros informados");
		}
		
		if (print==null)
			throw new ReportErrorCreateDatasourceException("O relatório gerado para ser adicionado na lista é nulo.");
		
		list.add(print);
	}


	
	/**
	 * Adiciona um relatório a lista de relatórios.<br/>
	 * Recebe um arquivo .jasper com o layout do relatório desejado, juntamente com uma lista de objetos (Beans) que <br/>
	 * preencherá o arquivo .jasper. <br/>
	 * Este beans deve conter atributos com os mesmos nomes usados na criação do arquivo .jasper, encapsulados em gets/sets <br/>
	 * <br/>
	 * 
	 * @param pathReport: Caminho do arquivo .jasper que contêm o relatório desejado.
	 * @param listBeans: Lista de objetos (Beans) que preencherá o relatório informado em pathReport.
	 * @throws ReportInvalidPathException: Exception que ocorre quando pathReport for nulo, vazio ou o caminho for inválido. 
	 * @throws ReportErrorCreateDatasourceException: Exception que ocorre quando listBeans for nulo, vazio ou não pode ser processado para geração do relatório. 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void addReport(String pathReport,Map parametros, Connection con) throws ReportInvalidPathException,ReportErrorCreateDatasourceException
	{
		if (pathReport==null || pathReport.equals(""))
			throw new ReportInvalidPathException("O caminho informado para o relatório é inválido: "+pathReport);

		File file=new File(pathReport);
		
		if (file==null || !file.exists())
			throw new ReportInvalidPathException("O caminho informado para o relatório é inválido: "+pathReport);
		
	
		JasperReport jasper=null;
		JasperPrint print=null;
		
		try 
		{
			jasper = (JasperReport) JRLoader.loadObject(pathReport);
			print = JasperFillManager.fillReport(jasper,parametros,con);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReportErrorCreateDatasourceException("Erro ao criar relatório com os parâmetros informados");
		}
		
		if (print==null)
			throw new ReportErrorCreateDatasourceException("O relatório gerado para ser adicionado na lista é nulo.");

		list.add(print);
	}

	
	
	/**
	 * Varre a lista interna de relatórios, realizando a união dos mesmos.
	 * @throws ReportFinalNullException: Ocorre quando
	 */
	@SuppressWarnings("unchecked")
	private void mergeReports() throws ReportFinalNullException
	{
		if (list==null || list.size()==0)
			throw new ReportFinalNullException("não é possível realizar a união dos relatórios da lista. A lista de relatórios é nula ou esta vazia.");
			
		finalReport=list.get(0);
		
		for (int i=1;i<list.size();i++)
		{
			List<JRPrintPage> listP=list.get(i).getPages();
			
			for (int j=0;j<listP.size();j++)
				finalReport.addPage(listP.get(j));
		}
	}
	
	/**
	 * 
	 * @return array de bytes contendo o PDF exportado.
	 * @throws ReportFinalNullException: ocorre quando o PDF não pode ser gerado
	 */
	public byte[] exportToPDF() throws ReportFinalNullException
	{
		mergeReports();
		correctPageNumbers(finalReport);
		
		if (finalReport==null || finalReport.getPages().size()==0)
			throw new ReportFinalNullException("O relatório final é nulo ou vazio, ocorrreu erro na união da lista de relatórios.");
		
		try 
		{
			byte[] bytes= JasperExportManager.exportReportToPdf(finalReport);
			
			if (bytes==null || bytes.length==0)
				throw new ReportFinalNullException("Erro na exportação do relatório para o formato PDF. O conteúdo exportado é vazio.");
			
			return bytes;
		}
		catch (Exception e) 
		{
			throw new ReportFinalNullException("Erro na exportação do relatório para o formato PDF.");
		}
		
	}
	/**
	 * 
	 * @return array de bytes contendo o xls exportado.
	 * @throws ReportFinalNullException: ocorre quando o xls não pode ser gerado
	 */
	public byte[] exportToXLS() throws ReportFinalNullException
	{
		mergeReports();
		correctPageNumbers(finalReport);
		
		if (finalReport==null || finalReport.getPages().size()==0)
			throw new ReportFinalNullException("O relatório final é nulo ou vazio, ocorrreu erro na união da lista de relatórios.");
		
		try 
		{
			  ByteArrayOutputStream output = new ByteArrayOutputStream();  
              JRXlsExporter xls = new JRXlsExporter();  

              xls.setParameter(JRXlsExporterParameter.JASPER_PRINT, finalReport);  
              xls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,output);  
              xls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);  
              xls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);  
      //      xls.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);  
              xls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET,Integer.decode("65000"));  
              xls.setParameter(JRXlsExporterParameter.IS_IMAGE_BORDER_FIX_ENABLED, Boolean.TRUE);  
              xls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);  
              xls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);  
              xls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);  

              xls.exportReport();  
              byte[] bytes = output.toByteArray();
			
			if (bytes==null || bytes.length==0)
				throw new ReportFinalNullException("Erro na exportação do relatório para o formato XSL. O conteúdo exportado é vazio.");
			
			return bytes;
		}
		catch (Exception e) 
		{
			throw new ReportFinalNullException("Erro na exportação do relatório para o formato XSL.");
		}
		
	}
	
	/**
	 * Faz a atualização das páginas do relatório, deixando as páginas na sequencia correta, pois se <br/>
	 * este ajuste não fosse feito, para cada relatório da lista, a contagem de páginas seria reiniciada.
	 * @param jasperPrintMain: representa o relatório final, resultado da união da lista de relatórios.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void correctPageNumbers(JasperPrint jasperPrintMain) 
	{

        List<JRPrintPage> listPages = jasperPrintMain.getPages();

        int currentPageIndex = 1;
        for (JRPrintPage currentPage : listPages) 
        {
            List listElements = currentPage.getElements();

            for (Object element : listElements) 
            {
                if (element instanceof JRTemplatePrintText) 
                {
                    JRTemplatePrintText templatePrintText = (JRTemplatePrintText) element;

                    // set currrent page
                    if (templatePrintText.getKey() != null &&
                            templatePrintText.getKey().equalsIgnoreCase("pageNumber")) 
                    {
                        templatePrintText.setText("página - " + String.valueOf(currentPageIndex));
                    }
                }
            }
            
            currentPageIndex++;
        }
	}
}
