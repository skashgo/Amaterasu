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
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IPreferenciaUsuarioGerenteBO;
import br.gov.caixa.siiac.bo.IPreferenciaUsuarioServicoBO;
import br.gov.caixa.siiac.bo.IPreferenciasUsuarioBO;
import br.gov.caixa.siiac.bo.IPreferenciasUsuarioCategoriaProdutoBO;
import br.gov.caixa.siiac.facade.IPreferenciaUsuarioFacade;
import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioGerente;
import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioServico;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuario;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuarioCategoriaProduto;

/**
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class PreferenciaUsuarioFacade implements IPreferenciaUsuarioFacade {
	
	private transient IPreferenciasUsuarioBO preferenciaUsuarioBO;
	private transient IPreferenciaUsuarioServicoBO preferenciaUsuarioServicoBO;
	private transient IPreferenciasUsuarioCategoriaProdutoBO preferenciaUsuarioCategoriaProdutoBO;
	private transient IPreferenciaUsuarioGerenteBO preferenciaUsuarioGerenteBO;
	
	@Autowired
	public void setPreferenciaUsuarioBO(IPreferenciasUsuarioBO preferenciaUsuarioBO) {
		this.preferenciaUsuarioBO = preferenciaUsuarioBO;
	}
	
	@Autowired
	public void setPreferenciaUsuarioServicoBO(IPreferenciaUsuarioServicoBO preferenciaUsuarioServicoBO) {
		this.preferenciaUsuarioServicoBO = preferenciaUsuarioServicoBO;
	}
	
	@Autowired
	public void setPreferenciaUsuarioCategoriaProdutoBO(
			IPreferenciasUsuarioCategoriaProdutoBO preferenciaUsuarioCategoriaProdutoBO) {
		this.preferenciaUsuarioCategoriaProdutoBO = preferenciaUsuarioCategoriaProdutoBO;
	}
	
	@Autowired
	public void setPreferenciaUsuarioGerenteBO(
			IPreferenciaUsuarioGerenteBO preferenciaUsuarioGerenteBO) {
		this.preferenciaUsuarioGerenteBO = preferenciaUsuarioGerenteBO;
	}

	@Transactional
	public void salvar(String matricula, List<PreferenciasUsuario> produtos, List<PreferenciaUsuarioServico> servicos, List<PreferenciasUsuarioCategoriaProduto> categorias, List<PreferenciaUsuarioGerente> gerentes) {
		this.preferenciaUsuarioBO.salvaLista(matricula, produtos);
		this.preferenciaUsuarioServicoBO.salvarLista(matricula, servicos);
		this.preferenciaUsuarioCategoriaProdutoBO.salvaLista(matricula, categorias);
		this.preferenciaUsuarioGerenteBO.salvarLista(matricula, gerentes);
	}
	
}
