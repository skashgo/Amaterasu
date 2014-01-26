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
 *
 */
public interface IMessageCEF {
	String MSGS = "msgsCef";
	/** Confirma a operacao? */
	String MA001 = "MA001";
	/** Confirma a inclusao? */
	String MA002 = "MA002";
	/** Confirma a alteracao? */
	String MA003 = "MA003";
	/** Confirma a inativacao? */
	String MA004 = "MA004";
	/** Confirma a exclusao? */
	String MA005 = "MA005";
	/** Confirma a ativacao? */
	String MA006 = "MA006";
	/** Deseja replicar a alteracao em outros checklists cadastrados? */
	String MA007 = "MA007";
	/** Checklist publicado e em uso. Confirma a alteracao? */
	String MA008 = "MA008";
	/**  */
	String MA009 = "MA009";
	/** Projeto de Checklist já cadastrado para este produto e serviço de verificação. Deseja substituir? */
	String MA010 = "MA010";
	/** Não serão possíveis alterações posteriores. Deseja concluir a verificação e emitir parecer? */
	String MA011 = "MA011";
	/** Existem documentos não marcados como “conforme”. Caso confirme, os mesmos serão automaticamente marcados como “conformes” para a emissão do parecer. Deseja concluir a verificação e emitir parecer? */
	String MA012 = "MA012";
	/** Deseja replicar a alteração em outros templates cadastrados? */
	String MA013 = "MA013";
	/** Prazo em dias. */
	String MH001 = "MH001";
	
	/** operacao realizada com sucesso. */
	String MN001 = "MN001";
	/** Nao foi possivel realizar a operacao. <<descricao do log da operacao>>. */
	String MN002 = "MN002";
	/** operacao cancelada. */
	String MN003 = "MN003";
	/** Usuario nao cadastrado no sistema. */
	String MN004 = "MN004";
	/** Usuario ou senha inv�lidos. */
	String MN005 = "MN005";
	/** Usuario inativo. Para acessar o sistema o Usuario precisa ser ativado. */
	String MN006 = "MN006";
	/** Dados incluidos com sucesso. */
	String MN007 = "MN007";
	/** Dados alterados com sucesso. */
	String MN008 = "MN008";
	/** Dados excluidos com sucesso. */
	String MN009 = "MN009";
	/** Informe pelos menos um dos campos para realizar a consulta. */
	String MN010 = "MN010";
	/** inativacao efetuada. */
	String MN011 = "MN011";
	/** ativacao efetuada. */
	String MN012 = "MN012";
	/** Item inativo. Para esta operacao e necessario ativar. */
	String MN013 = "MN013";
	/** Usuario nao pertence a unidade vinculada. Entre em contato com o Gestor do Sistema. */
	String MN014 = "MN014";
	/** Item ja cadastrado. Consulte todos os itens - ativos e inativos. */
	String MN015 = "MN015";
	/** Nao existem dados para esta consulta. */
	String MN016 = "MN016";
	/** Checklist revogado. Para esta operacao e necessario cadastrar novo checklist. */
	String MN017 = "MN017";
	/** Checklist ja cadastrado para este produto e servico de verificacao. */
	String MN018 = "MN018";
	/** Não é possível importar a verificação anterior. O modelo de checklist foi alterado. */
	String MN027 = "MN027";
	/** Item já cadastrado. */
	String MN023 = "MN023";
	/** Não é possível importar verificação anterior pois existem documentos vencidos. */
	String MN028 = "MN028";
	/** Informação já publicada. Deseja continuar? */
	String MN032 = "MN032";
	/** Informação já cadastrada. */
	String MN033 = "MN033";
	
	
	
	/** Unidade informada n\u00E3o foi encontrada. */
	String XX017 = "XX017";
	/** Unidade informada n\u00E3o possui usu\u00E1rios cadastrados.  */
	String XX018 = "XX018";
	/** Dados obrigat\u00F3rios n\u00E3o informados */
	String XX019 = "XX019";
	/** Voc\u00EA n\u00E3o pode atribuir um perfil a voc\u00EA mesmo. */
	String XX020 = "XX020";
	/** Este Usu\u00E1rio n\u00E3o pertence a esta unidade. Processo cancelado. */
	String XX021 = "XX021";
	/** Usu\u00E1rio n\u00E3o encontrado. */
	String XX022 = "XX022";
	/** Usu\u00E1rio j\u00E1 est\u00E1 cadastrado no sistema. */
	String XX023 = "XX023";
	/** Campo Opera\u00E7\u00E3o e Modalidade precisam ser preenchidos. */
	String XX024 = "XX024";
	/**SELECIONE PELO MENOS UMA GARANTIA PARA REALIZAR INVENTARIO. */
	String MSGGAR0001 = "MSGGAR0001";
	/** Ambos os valores, de início e de final, devem ser preenchidos.*/
	String MSGGAR0002 = "MSGGAR0002";
	/** Favor selecionar um Contrato.*/
	String MSGGAR0003 = "MSGGAR0003";
}
