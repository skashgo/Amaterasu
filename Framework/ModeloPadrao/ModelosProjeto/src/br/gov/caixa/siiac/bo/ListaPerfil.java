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
package br.gov.caixa.siiac.bo;

/**
 * @author GisConsult
 *
 */
public class ListaPerfil {
	/** PERFIL_GESTOR_SISTEMA 					= 1*/
	public static final Short PERFIL_GESTOR_SISTEMA 					= 1;
	/** PERFIL_REGIONAL_CONFORMIDADE 				= 2*/
	public static final Short PERFIL_REGIONAL_CONFORMIDADE 				= 2;
	/** PERFIL_VERIFICADOR_CONFORMIDADE 			= 3*/
	public static final Short PERFIL_VERIFICADOR_CONFORMIDADE 			= 3;
	/** PERFIL_GESTOR_PRODUTO 					= 4*/
	public static final Short PERFIL_GESTOR_PRODUTO 					= 4;
	/** PERFIL_UNIDADE_ATENDIMENTO				= 5*/
	public static final Short PERFIL_UNIDADE_ATENDIMENTO				= 5;
	/** PERFIL_AUDITOR 							= 6*/
	public static final Short PERFIL_AUDITOR 							= 6;
	/** PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE 	= 7*/
	public static final Short PERFIL_CENTRAL_DE_ATENDIMENTO_E_SUPORTE 	= 7;
	/** PERFIL_VERIFICADOR_NACIONAL  =			 	8*/
	public static final Short PERFIL_VERIFICADOR_NACIONAL= 8;
	/** PERFIL_VERIFICADOR_REGIONAL  = 				9*/
	public static final Short PERFIL_VERIFICADOR_REGIONAL 	= 9;
	
	public static final String PERFIL_NOME_GESTOR_SISTEMA 					= "Gestor Sistema";
	public static final String PERFIL_NOME_REGIONAL_CONFORMIDADE 			= "Regional Conformidade";
	public static final String PERFIL_NOME_VERIFICADOR_CONFORMIDADE 		= "Verificador Conformidade";
	public static final String PERFIL_NOME_GESTOR_PRODUTO 					= "Gestor Produto";
	public static final String PERFIL_NOME_UNIDADE_ATENDIMENTO				= "Unidade Atendimento";
	public static final String PERFIL_NOME_AUDITOR 							= "Auditor";
	public static final String PERFIL_NOME_CENTRAL_DE_ATENDIMENTO_E_SUPORTE = "Central de Atendimento e Suporte";
	public static final String PERFIL_NOME_VERIFICADOR_NACIONAL				= "Verificador Nacional";
	public static final String PERFIL_NOME_VERIFICADOR_REGIONAL 			= "Verificador Regional";
	
	public static final int SITUACAO_ATIVO   = 1;
	public static final int SITUACAO_INATIVO = 0;
}
