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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.gov.caixa.siiac.model.domain.Contrato;

/**
 * @author GIS Consult
 * Classe de filtragem avançada
 * 
 */
public class FilterBase {
	
	private Map<String, Object> filtros;
	
	public FilterBase() {
		filtros = new HashMap<String, Object>(0);
	}
	

	/**
	 * Esse método adiciona um objeto ao map.<br/>
	 * Deverá ser passado um objeto e seu nome<br/
	 * @param nome - Recebe o nome do objeto que será armazenado no map
	 * @param objeto - Recebe o objeto a ser armazenado no map.
	 */
	public void addToFilter(String nome, Object objeto){
		if(filtros != null){
			filtros.put(nome, objeto);
		}
	}
	
	/**
	 * Retorna o objeto que possui aquele nome/chave <br/>
	 * já convertido para Date
	 * @param nome - nome/chave do objeto
	 * @return Objeto adicionado ao map que possui o nome passado.
	 */
	public Date getDate(String nome){
		Date data;
		try{
		data = (Date) filtros.get(nome);
		} catch (ClassCastException c ){
			data = null;
		}
		return data;
	}
	
	/**
	 * Retorna o objeto que possui aquele nome/chave <br/>
	 * já convertido para String
	 * @param nome - nome/chave do objeto
	 * @return Objeto adicionado ao map que possui o nome passado.
	 */
	public String getString(String nome){
		String string;
		try{
			string = (String) filtros.get(nome);
		} catch(ClassCastException c){
			string = null;
		}
		return string;
	}
	
	/**
	 * Retorna o objeto que possui aquele nome/chave <br/>
	 * já convertido para Integer
	 * @param nome - nome/chave do objeto
	 * @return Objeto adicionado ao map que possui o nome passado.
	 */
	public Integer getInt(String nome){
		Integer inteiro;
		try{
			inteiro = (Integer) filtros.get(nome);
		} catch(ClassCastException c){
			try{
				inteiro = Integer.parseInt((String) filtros.get(nome));
			} catch(NumberFormatException e){
			inteiro = null;
			}
		}
		return inteiro;
	}
	
	/**
	 * Retorna o objeto que possui aquele nome/chave <br/>
	 * já convertido para Short
	 * @param nome - nome/chave do objeto
	 * @return Objeto adicionado ao map que possui o nome passado.
	 */
	public Short getShort(String nome){
		Short smallint;
		try{
			smallint = (Short) filtros.get(nome);
		}catch(ClassCastException c){
			try{
			smallint = Short.parseShort((String)filtros.get(nome));
			}catch(NumberFormatException e){
				smallint = null;
			}
		}
		return smallint;
	}
	
	/**
	 * Retorna o objeto que possui aquele nome/chave <br/>
	 * já convertido para Character
	 * @param nome - nome/chave do objeto
	 * @return Objeto adicionado ao map que possui o nome passado.
	 */
	public Character getCharacter(String nome)
	{
		Character character;
		try{
			character = (Character) filtros.get(nome);
		} catch(ClassCastException c)
		{
			character = null;
		}
		
		return character;
	}
	
	/**
	 * Retorna o objeto que possui aquele nome/chave <br/>
	 * já convertido para Boolean
	 * @param nome - nome/chave do objeto
	 * @return Objeto adicionado ao map que possui o nome passado.
	 */
	public Boolean getBoolean(String nome){
		Boolean booleano;
		try{
			if(filtros.get(nome) == null){
				return false;
			}
			booleano = (Boolean) filtros.get(nome);
			
		} catch(ClassCastException c) {
			booleano = false;
		}
		return booleano;
	}
	
	/**
	 * Retorna o objeto que possui aquele nome/chave <br/>
	 * já convertido para Big Decimal
	 * @param nome - nome/chave do objeto
	 * @return Objeto adicionado ao map que possui o nome passado.
	 */
	public BigDecimal getBigDecimal(String nome){
		BigDecimal bigDecimal;
		try{
			bigDecimal = (BigDecimal) filtros.get(nome);
		} catch(ClassCastException  c){
			bigDecimal = null;
		}
		return bigDecimal;
	}
	
	/**
	 * Retorna o objeto que possui aquele nome/chave <br/>
	 * já convertido para Contrato
	 * @param <b>Nome</b> - nome/chave do objeto
	 * @return Objeto adicionado ao map que possui o nome passado.
	 */
	public Contrato getContrato(String nome){
		Contrato contrato;
		try{
			contrato = (Contrato) filtros.get(nome);
			
		} catch(ClassCastException c) {
			contrato = null;
		}
		return contrato;
	}
	
	
	
	/**
	 * Limpa todos os itens do filtro.<br/>
	 * cria uma nova instancia  do objeto Map.
	 */
	public void limparFiltros(){
		filtros = new HashMap<String, Object>(0);
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest(); 
		HttpSession session = request.getSession(); 
		session.removeAttribute("filtro");
	}
	
	/**
	 * Remove um objeto do Map de filtragem<br/>
	 * 
	 * @param nome - Nome/Chave do objeto a ser removido.
	 */
	public void removerItem(String nome){
		filtros.remove(nome);
	}
	
	public Map<String, Object> getFiltro(){
		return filtros;
	}
	
	/**
	 * Método responsável por indicar que o tipo de pesquisa que está
	 * sendo realizado é <b>AVANÇADO</b> 
	 */
	public void setModoAvancado()
	{
		addToFilter(MODO_PESQUISA, MODO_AVANCADO);
	}
	
	/**
	 * Método responsável por indicar que o tipo de pesquisa que está
	 * sendo realizado é <b>SIMPLES</b>
	 */
	public void setModoSimples()
	{
		addToFilter(MODO_PESQUISA, MODO_SIMPLES);
	}
	
	/**
	 * Método responsável por verificar que o modo do filtro atual
	 * @return <b>TRUE</b> - Indica que o filtro é avançado<br/>
	 * 		   <b>FALSE</b> - Indica que o filtro é simples
	 */
	public boolean isModoAvancado()
	{
		return getCharacter(MODO_PESQUISA) == null ? false : getCharacter(MODO_PESQUISA).equals(MODO_AVANCADO);
	}
	
	//-------------------------------------------------------
	//CONSTANTES REPRESENTATIVAS DOS MODOS DE PESQUISA
	public static final Character MODO_AVANCADO = '1';
	public static final Character MODO_SIMPLES = '2';
	
	public static final String MODO_PESQUISA = "modoPesquisa";
	

}
