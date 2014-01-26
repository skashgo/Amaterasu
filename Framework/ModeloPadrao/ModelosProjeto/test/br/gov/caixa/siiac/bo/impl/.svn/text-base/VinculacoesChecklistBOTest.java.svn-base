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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siiac.bo.IVinculacoesChecklistBO;
import br.gov.caixa.siiac.facade.IVinculacoesChecklistFacade;
import br.gov.caixa.siiac.model.VinculacoesChecklistVO;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.persistence.dao.IChecklistDAO;

/**
 *
 * @author GisConsult
 */
public class VinculacoesChecklistBOTest extends TestWithSpring {
	
	@Autowired
	private IVinculacoesChecklistBO instance;

	@Autowired
	private IVinculacoesChecklistFacade facade;
	
	@Autowired
	private IChecklistDAO dao;
	
	@Test
	public void testSave(){
		System.out.println("save");
		try {
			VinculacoesChecklistVO objeto = new VinculacoesChecklistVO();
			objeto.setNivelApontamento();
			objeto.setPai(1);
			objeto.setVinculadasStr("3");
			instance.save(objeto);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDeleteBlocos(){
		
		try {
			ChecklistServicoVerificacaoProduto ch = dao.findById(2);
			List<ChecklistServicoVerificacaoProduto> lista = new ArrayList<ChecklistServicoVerificacaoProduto>();
			lista.add(ch);
			System.out.println("Quantidade de elementos : " + lista.size());
			
			VinculacoesChecklistVO objeto = new VinculacoesChecklistVO();
			objeto.setNivelBloco();
			objeto.setPai(1);
			objeto.setChecklistPrincipal(1);
			objeto.setVinculadasStr("1");
			
			facade.executarRotinaDeleteVinculacoes(objeto, lista);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDeleteItens(){
		try {
			ChecklistServicoVerificacaoProduto ch = dao.findById(2);
			List<ChecklistServicoVerificacaoProduto> lista = new ArrayList<ChecklistServicoVerificacaoProduto>();
			lista.add(ch);
			
			VinculacoesChecklistVO objeto = new VinculacoesChecklistVO();
			objeto.setNivelItem();
			objeto.setPai(1);
			objeto.setChecklistPrincipal(1);
			objeto.setVinculadasStr("1");
			
			facade.executarRotinaDeleteVinculacoes(objeto, lista);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDeleteApontamentos() {
		try {
			ChecklistServicoVerificacaoProduto ch = dao.findById(2);
			List<ChecklistServicoVerificacaoProduto> lista = new ArrayList<ChecklistServicoVerificacaoProduto>();
			lista.add(ch);
			
			VinculacoesChecklistVO objeto = new VinculacoesChecklistVO();
			objeto.setNivelApontamento();
			objeto.setPai(1);
			objeto.setChecklistPrincipal(1);
			objeto.setVinculadasStr("5");
			
			facade.executarRotinaDeleteVinculacoes(objeto, lista);
		} catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
	@Test
	public void testDeleteRestricao() {
		try {
			ChecklistServicoVerificacaoProduto ch = dao.findById(2);
			List<ChecklistServicoVerificacaoProduto> lista = new ArrayList<ChecklistServicoVerificacaoProduto>();
			lista.add(ch);
			
			VinculacoesChecklistVO objeto = new VinculacoesChecklistVO();
			objeto.setNivelRestricao();
			objeto.setPai(1);
			objeto.setChecklistPrincipal(1);
			objeto.setVinculadasStr("5");
			
			facade.executarRotinaDeleteVinculacoes(objeto, lista);
		} catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
}
