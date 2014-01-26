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
package br.gov.caixa.siiac.bo.impl;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xhtmlrenderer.pdf.ITextRenderer;

import br.gov.caixa.siiac.bo.IBuildTemplateParecerBO;
import br.gov.caixa.siiac.bo.ITemplateParecerBO;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.TemplateParecer;
import br.gov.caixa.siiac.util.DefinedLanguage;
import br.gov.caixa.siiac.util.metalanguage.ProcessMetaLanguage;

/**
 * @author GIS Consult
 *
 */
public class TemplateParecerBOTest extends TestWithSpring
{
	@Autowired
	private ITemplateParecerBO templateParecerBO;
	
	@Autowired
	private IBuildTemplateParecerBO buildTemplateParecerBO;
	

	@Test
	public void testaGravacaoTemplateParecer()
	{
		try
		{
			TemplateParecer tp=new TemplateParecer();
			
			tp.setIcTipoParecer(true);
			tp.setNoAssuntoParecer("Teste assunto");
			tp.setNoReferenciaParecer("Teste referenciua");
			tp.setTextoParecer("sdasdasdasdasd");
			
			ServicoVerificacaoProduto svp=new ServicoVerificacaoProduto();
			svp.setNuServicoVerificacaoProduto(new Integer(10));
			tp.setServicoVerificacaoProduto(svp);
			templateParecerBO.merge(tp);
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testaGetCanonicalName()
	{
		try
		{
			System.out.println("Res: "+new DefinedLanguage().getClass().getCanonicalName());
			System.out.println("Res: "+new DefinedLanguage().getClass().getSimpleName()); 
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void process()
	{
		try
		{
			buildTemplateParecerBO.process();
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void testMethodsName()
	{
		try
		{
			DefinedLanguage dl =new DefinedLanguage();
			Method[] m=dl.getClass().getMethods();
			
			for (Method mm:m)
			{
				System.out.println(mm.getName());
			}
			
	      Method mthd=dl.getClass().getMethod("getNomeTomador");
	      String output=(String)mthd.invoke(dl);		
	      System.out.println(output);

	      BufferedReader rd = new BufferedReader(new InputStreamReader(dl.getClass().getResourceAsStream("tagdefinition.lng")));
	      
	      System.out.println( rd.readLine());
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			fail();
		}
		
	}	
	
	@Test
	public void testaProcessMetaLanguage()
	{
		try
		{
			ProcessMetaLanguage pml=new ProcessMetaLanguage(new DefinedLanguage());
			
			for (String s:pml.getListAllTags())
				System.out.println(s);
			
			for (String s:pml.getListTags())
				System.out.println(s);
			
			
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			fail();
		}
		
	}		
	
	@Test
	public void testaTagForMethod()
	{
		try
		{
			ProcessMetaLanguage pml=new ProcessMetaLanguage(new DefinedLanguage());
			pml.processTags("_nome_tomador_numero_teste_");
			
			
			
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			fail();
		}
		
	}		
	
	@Test
	public void testaHTMLToPDF()
	{
		try
		{
	        String File_To_Convert = "test.htm";
	        String url = new File(File_To_Convert).toURI().toURL().toString();
	        System.out.println(""+url);
	        String HTML_TO_PDF = "ConvertedFile.pdf";
	        OutputStream os = new FileOutputStream(HTML_TO_PDF);       
	        ITextRenderer renderer = new ITextRenderer();
	        renderer.set Document(url);      
	        renderer.layout();
	        renderer.createPDF(os);        
	        os.close();

			
			
		}
		catch (Exception erro)
		{
			erro.printStackTrace();
			fail();
		}
		
	}		
}
