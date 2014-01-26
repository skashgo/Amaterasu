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
package br.gov.caixa.siiac.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author GIS Consult
 *
 */
public class VinculacoesChecklistVO implements ConstantsVinculacoesChecklistVO {

	/**
	 * Esse valor serve para armazenar qual a ação que está sendo executada.
	 * Sendo:
	 * 	1 - Drag and Drop (ACTION_DRAG_DROP);
	 *  2 - Delete (ACTION_DELETE);
	 *  3 - Insert (ACTION_INSERT);
	 *  4 - Insert (ACTION_UPDATE);
	 */
	private Integer action;
	
	/**
	 * Esse valor serve para armazenar qual o id do checklist principal que está sendo trabalhado.
	 */
	private Integer idChecklistPrincipal;
	
	/**
	 * Esse valor serve pra levar em consideração qual é o nível que está sendo trabalhado.
	 * Sendo:
	 * 	* 1 - Item (NIVEL_ITEM)
	 *  * 2 - Apontamento (NIVEL_APONTAMENTO)
	 *  * 3 - Restrição (NIVEL_RESTRICAO)
	 */
	private Integer nivelHierararquia;
	
	/**
	 * Esse valor serve para controlar qual o id do pai das vinculações a serem trabalhas.
	 * Sendo:
	 * 	Se nivelHierarquia for = NIVEL_ITEM => id do bloco
	 * 	Se nivelHierarquia for = NIVEL_APONTAMENTO => id do item
	 * 	Se nivelHierarquia for = NIVEL_RESTRICAO => id do apontamento
	 */
	private Integer idPai;
	
	/**
	 * Essa String contempla o id de todos os itens vinculados separados por vírgula, 
	 * na ordem correta do primeiro ao último.
	 * 	Ex: 30,14,180,32,44
	 */
	private String vinculadasStr;
	
	/**
	 * Lista de Integer contendo os ids após a separação da vinculadasStr
	 */
	private List<Integer> vinculadas; 
	
	/**
	 * Estes objetos devem ser preenchidos pela regra de negócio,
	 * após deleção/inserção das vinculações do checklist principal,
	 * 
	 */
	private List<Integer> deletados = new ArrayList<Integer>();
	private List<Integer> inseridos = new ArrayList<Integer>();
	private Map<String, Integer> mapHierarquia = new HashMap<String, Integer>();
	
	public Integer getChecklistPrincipal(){
		return this.idChecklistPrincipal;
	}
	
	public void setChecklistPrincipal(Integer idChecklistPrincipal) {
		this.idChecklistPrincipal = idChecklistPrincipal;
	}	

	public Integer getPai(){
		return this.idPai;
	}
	
	public void setPai(Integer idPai) {
		this.idPai = idPai;
	}	
		
	public void setVinculadasStr(String vinculadasStr) {
		this.vinculadasStr = vinculadasStr;
		vinculadas = new ArrayList<Integer>();
		StringTokenizer tokenizer = new StringTokenizer(vinculadasStr, ",");
		while(tokenizer.hasMoreTokens()){
			vinculadas.add(Integer.valueOf(tokenizer.nextToken().replace(" ", "")));
		}
	}
	
	public void setActionDragDrop(){
		this.action = ACTION_DRAG_DROP;
	}
	
	public boolean isActionDrapDrop(){
		return this.action.equals(ACTION_DRAG_DROP);
	}
	
	public void setActionDelete(){
		this.action = ACTION_DELETE;
	}
	
	public boolean isActionDelete(){
		return this.action.equals(ACTION_DELETE);
	}
	
	public void setActionInsert(){
		this.action = ACTION_INSERT;
	}
	
	public boolean isActionInsert(){
		return this.action.equals(ACTION_INSERT);
	}

	public void setActionUpdate(){
		this.action = ACTION_UPDATE;
	}
	
	public boolean isActionUpdate(){
		return this.action.equals(ACTION_UPDATE);
	}
	
	public void setNivelBloco(){
		this.nivelHierararquia = NIVEL_BLOCO;
	}
	
	public boolean isNivelBloco(){
		return this.nivelHierararquia.equals(NIVEL_BLOCO);
	}
	
	public void setNivelItem(){
		this.nivelHierararquia = NIVEL_ITEM;
	}
	
	public boolean isNivelItem(){
		return this.nivelHierararquia.equals(NIVEL_ITEM);
	}
	
	public void setNivelApontamento(){
		this.nivelHierararquia = NIVEL_APONTAMENTO;
	}
	
	public boolean isNivelApontamento(){
		return this.nivelHierararquia.equals(NIVEL_APONTAMENTO);
	}
	
	public void setNivelRestricao(){
		this.nivelHierararquia = NIVEL_RESTRICAO;
	}
	
	public boolean isNivelRestricao(){
		return this.nivelHierararquia.equals(NIVEL_RESTRICAO);
	}
	
	public List<Integer> getVinculadas(){
		return vinculadas;
	}
	
	public List<Integer> getDeletados(){
		return this.deletados;
	}
	
	public void addDeletado(Integer value){
		if(this.deletados == null){
			deletados = new ArrayList<Integer>();
		}
		deletados.add(value);
	}

	public List<Integer> getInseridos(){
		return this.inseridos;
	}
	
	public void addInserido(Integer value){
		if(this.inseridos == null){
			inseridos = new ArrayList<Integer>();
		}
		inseridos.add(value);
	}
	
	public void setMapHierarquiaBloco(Integer value){
		if(mapHierarquia == null){
			mapHierarquia = new HashMap<String, Integer>();
		}
		mapHierarquia.put(HIERARQUIA_BLOCO, value);
	}
	
	public Integer getMapHierarquiaBloco(){
		return mapHierarquia != null && mapHierarquia.containsKey(HIERARQUIA_BLOCO) ? mapHierarquia.get(HIERARQUIA_BLOCO) : null;
	}
	
	public void setMapHierarquiaItem(Integer value){
		if(mapHierarquia == null){
			mapHierarquia = new HashMap<String, Integer>();
		}
		mapHierarquia.put(HIERARQUIA_ITEM, value);
	}
	
	public Integer getMapHierarquiaItem(){
		return mapHierarquia != null && mapHierarquia.containsKey(HIERARQUIA_ITEM) ? mapHierarquia.get(HIERARQUIA_ITEM) : null;
	}
	
	public void setMapHierarquiaApontamento(Integer value){
		if(mapHierarquia == null){
			mapHierarquia = new HashMap<String, Integer>();
		}
		mapHierarquia.put(HIERARQUIA_APONTAMENTO, value);
	}
	
	public Integer getMapHierarquiaApontamento(){
		return mapHierarquia != null && mapHierarquia.containsKey(HIERARQUIA_APONTAMENTO) ? mapHierarquia.get(HIERARQUIA_APONTAMENTO) : null;
	}
}

interface ConstantsVinculacoesChecklistVO{
	Integer ACTION_DRAG_DROP = 1;
	Integer ACTION_DELETE = 2;
	Integer ACTION_INSERT = 3;
	Integer ACTION_UPDATE = 4; //do Ajuda
	
	Integer NIVEL_BLOCO = 1;
	Integer NIVEL_ITEM = 2;
	Integer NIVEL_APONTAMENTO = 3;
	Integer NIVEL_RESTRICAO = 4;
	
	String HIERARQUIA_BLOCO = "HIERARQUIA_BLOCO";
	String HIERARQUIA_ITEM = "HIERARQUIA_ITEM";
	String HIERARQUIA_APONTAMENTO = "HIERARQUIA_APONTAMENTO";
}
