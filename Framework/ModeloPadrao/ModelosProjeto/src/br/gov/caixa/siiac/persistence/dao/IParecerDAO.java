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
package br.gov.caixa.siiac.persistence.dao;

import java.util.List;

import br.gov.caixa.siiac.model.ResumoParecerVO;
import br.gov.caixa.siiac.model.TagListaItemDataValidadeVO;
import br.gov.caixa.siiac.model.domain.Parecer;

/**
 * @author GIS Consult
 *
 */
public interface IParecerDAO extends IGenericDAO<Parecer> {
	
	/**
	 * Busca a lista de resultados da verificação, apresentando seu bloco, item e apontamento em um mesmo registro (objeto da lista).
	 * Assim é possível trabalhar com o nome e a observação de cada nível.
	 * 
	 * Este método busca resultados com foco na tabea iacsm001.iactb013_rslto_aptmnto_produto, ou seja, 
	 * de registros que ainda não estão com seu parecer gerado.
	 * 
	 * 
	 * @param nuVerificacaoContrato código da verificação que se está trabalhando
	 * @param conformes true = apenas resultados conformes
	 * 					false = apenas resultados inconformes
	 * @return
	 * @see br.gov.caixa.siiac.persistence.dao.IParecerDAO#getResumoDepoisParecerVOList(Integer nuVerificacaoContrato, Boolean conformes)
     */
	List<ResumoParecerVO> getResumoAntesParecerVOList(Integer nuVerificacaoContrato, Boolean conformes);
	
	/**
	 * Busca a lista de resultados da verificação, apresentando seu bloco, item e apontamento em um mesmo registro (objeto da lista).
	 * Assim é possível trabalhar com o nome e a observação de cada nível.
	 * 
	 * Este método busca resultados com foco na tabea iacsm001.iactb057_rslto_aptmnto_prdto_pr, ou seja, 
	 * de registros que já estão com seu parecer gerado.
	 * 
	 * 
	 * @param nuVerificacaoContrato código da verificação que se está trabalhando
	 * @param conformes true = apenas resultados conformes
	 * 					false = apenas resultados inconformes
	 * @return
	 * @see br.gov.caixa.siiac.persistence.dao.IParecerDAO#getResumoAntesParecerVOList(Integer nuVerificacaoContrato, Boolean conformes)
     */
	List<ResumoParecerVO> getResumoDepoisParecerVOList(Integer nuVerificacaoContrato, Boolean conformes);

	/**
	 * Busca o último id inserido na base de dados, na tabela iacsm001.iactb017_cntrle_numero_parecer, para a unidade no ano atual.
	 * @param ano
	 * @param nuUnidade
	 * @param nuNatural
	 * @return
	 */
	Integer getLastId(Short aaParecer, Short nuUnidade, Integer nuNatural);
	
	
	public List<TagListaItemDataValidadeVO> getListaItensComDataValidade(Integer nuVerificacaoContrato);
	
}
