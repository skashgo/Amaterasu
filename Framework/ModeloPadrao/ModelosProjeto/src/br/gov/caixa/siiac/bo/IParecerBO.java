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

import br.gov.caixa.siiac.exception.SIIACRuntimeException;
import br.gov.caixa.siiac.model.domain.Parecer;
import br.gov.caixa.siiac.security.IUsuario;


public interface IParecerBO {
	
	/**
	 * @param i
	 * @param j
	 * @param k
	 * @param l
	 * @return
	 */
	Parecer findById(Integer nuParecer, Short anoParecer, Short nuUnidade, Short nuNatural);
	
	/**
	 * Realiza todo o processo de inserção do parecer:
	 *    - move registros das tabelas sem parecer (iacsm001.iactb018_verificacao_contrato, 
	 *    											iacsm001.iactb013_rslto_aptmnto_produto e 
	 *    											iacsm001.iactb056_vrfco_cntro_obsro)
	 *    			para as tabelas com parecer    (iacsm001.iactb055_vrfco_cntro_prcr,
	 *    											iacsm001.iactb057_rslto_aptmnto_prdto_pr e
	 *    											iacsm001.iactb058_vrfco_cntro_obsro_prcr)
	 *    
	 *    - gera número do parecer inserindo registro na tabela iacsm001.iactb017_cntrle_numero_parecer
	 *    - insere registro na tabela iacsm001.iactb016_parecer
	 *    - gera HTML do parecer e insere na tabela iacsm001.iactb051_arquivo_imagem_parecer
	 * @param nuVerificacaoContrato
	 * @param usuario
	 * @param nuMatriculaGerenteUnidadeSelecionado
	 * @param caminhoPastaImagens
	 */
	Parecer geraParecer(Integer nuVerificacaoContrato, 
					 IUsuario usuario, 
					 Integer nuMatriculaGerenteUnidadeSelecionado, 
					 String caminhoPastaImagens, Boolean... parecer) throws SIIACRuntimeException;
	
	/**
	 * Executa a chamada da rotina que enviará o e-mail com o parecer, chamando o Web-Service. 
	 * @param parecer
	 * @throws SIIACRuntimeException
	 */
	void executaEnviaParecer(Parecer parecer) throws SIIACRuntimeException;
	
}