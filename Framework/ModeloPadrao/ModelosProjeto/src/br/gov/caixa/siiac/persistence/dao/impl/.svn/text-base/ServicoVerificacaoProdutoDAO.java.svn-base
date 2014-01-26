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
package br.gov.caixa.siiac.persistence.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ServicoVerificacao;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.persistence.dao.IServicoVerificacaoProdutoDAO;

/**
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class ServicoVerificacaoProdutoDAO extends GenericDAO<ServicoVerificacaoProduto> implements IServicoVerificacaoProdutoDAO {

	public ServicoVerificacaoProdutoDAO() {
		super(ServicoVerificacaoProduto.class);
	}
	
	public ServicoVerificacaoProduto get(Produto prod, ServicoVerificacao sv) {
		Criteria c = getCriteria();
		c.add(Restrictions.eq("produto", prod))
		.add(Restrictions.eq("servicoVerificacao", sv));
		ServicoVerificacaoProduto svp = null;
		
		if(c.list().size() == 1) {
			svp = (ServicoVerificacaoProduto) c.list().get(0);
		}
		
		return svp;
	}
}
