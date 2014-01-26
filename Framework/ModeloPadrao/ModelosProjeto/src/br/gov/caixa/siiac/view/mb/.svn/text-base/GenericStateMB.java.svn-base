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
package br.gov.caixa.siiac.view.mb;

/**
 * @author GIS Consult
 * Classe para controle de estados das telas
 */
abstract class GenericStateMB {
	private static final Integer MODO_INICIO = 0;
	private static final Integer MODO_CADASTRO = 1;
	private static final Integer MODO_ALTERA = 2;
	private static final Integer MODO_VISUALIZA = 3;
	private static final Integer MODO_FILTRO = 4;
	private transient Integer currentState;
	
	/**
	 * Seta o Estado atual (currentState) para MODO_INICIO.
	 */
	public void setModoInicio() {
		currentState = MODO_INICIO;
	}
	
	/**
	 * Seta o Estado atual (currentState) para MODO_CADASTRO.
	 */
	public void setModoCadastro() {
		currentState = MODO_CADASTRO;
	}
	
	/**
	 * Seta o Estado atual (currentState) para MODO_ALTERA.
	 */
	public void setModoAltera() {
		currentState = MODO_ALTERA;
	}
	
	/**
	 * Seta o Estado atual (currentState) para MODO_VISUALIZA.
	 */
	public void setModoVisualiza() {
		currentState = MODO_VISUALIZA;
	}
	
	/**
	 * Seta o Estado atual (currentState) para MODO_FILTRO.
	 */
	public void setModoFiltro() {
		currentState = MODO_FILTRO;
	}
	
	/**
	 * Verifica se o Estado atual (currentState) est� setado no
	 * MODO_INICIO.
	 * @return true ou false
	 */
	public boolean isModoInicio() {
		return currentState == MODO_INICIO;
	}
	
	/**
	 * Verifica se o Estado atual (currentState) est� setado no
	 * MODO_CADASTRO.
	 * @return true ou false
	 */
	public boolean isModoCadastro() {
		return currentState == MODO_CADASTRO;
	}
	
	/**
	 * Verifica se o Estado atual (currentState) est� setado no
	 * MODO_ALTERA.
	 * @return true ou false
	 */
	public boolean isModoAltera() {
		return currentState == MODO_ALTERA;
	}
	
	/**
	 * Verifica se o Estado atual (currentState) est� setado no
	 * MODO_VISUALIZA.
	 * @return true ou false
	 */
	public boolean isModoVisualiza() {
		return currentState == MODO_VISUALIZA;
	}
	
	/**
	 * Verifica se o Estado atual (currentState) est� setado no
	 * MODO_FILTRO.
	 * @return true ou false
	 */
	public boolean isModoFiltro() {
		return currentState == MODO_FILTRO;
	}
	
	public Integer getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}
}
