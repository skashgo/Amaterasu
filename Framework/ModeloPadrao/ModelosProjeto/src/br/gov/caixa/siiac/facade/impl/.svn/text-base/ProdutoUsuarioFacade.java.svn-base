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
package br.gov.caixa.siiac.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IProdutoUsuarioBO;
import br.gov.caixa.siiac.facade.IProdutoUsuarioFacade;
import br.gov.caixa.siiac.model.domain.ProdutoUsuario;

/**
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class ProdutoUsuarioFacade implements IProdutoUsuarioFacade {
	
	private transient IProdutoUsuarioBO produtoUsuarioBO;
	
	@Autowired
	public void setProdutoUsuarioBO(IProdutoUsuarioBO produtoUsuarioBO) {
		this.produtoUsuarioBO = produtoUsuarioBO;
	}
	
	public void salvar(String matricula, List<ProdutoUsuario> produtos) {
		this.produtoUsuarioBO.saveAllProdutosDaLista(matricula, produtos);
		this.produtoUsuarioBO.deleteAllProdutosNaoExistentesNaLista(matricula, produtos);
		if(produtos != null && !produtos.isEmpty()){
			this.produtoUsuarioBO.adjustAllProdutosPreferenciais(matricula);
			this.produtoUsuarioBO.adjustAllServicosPreferenciais(matricula);
		}
	}
	
}
