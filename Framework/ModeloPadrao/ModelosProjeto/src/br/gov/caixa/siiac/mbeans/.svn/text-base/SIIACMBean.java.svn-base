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
package br.gov.caixa.siiac.mbeans;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import br.gov.caixa.siiac.util.ServicoVerificacaoProdutoUsados;

/**
 *Responsável por mostrar ou executar comandos de gerenciamento da aplicação.<br>
 *Exemplo: listar valores para verificação do funcionamento de uma funcionalidade,<br>
 *sessões abertas, recursos abertos etc, configurar valores a quente tais como: habilitar debug,<br>
 *habilitar estatisticas etc.
 * 
 * 
 * 
 * @author Caixa Econômica Federal
 * */
@Component
@ManagedResource(description="MBeans responsável por várias operações de gerência da apllicação SIIAC.", objectName="SIIAC:name=SIIACMBean")
public class SIIACMBean 
{
	/**
	 * Mostra todos os IDs de serviço verificação contrato/produto usados/bloqueados no momento.
	 * 
	 * @return
	 */
	@ManagedOperation(description="Mostra todos os serviços de verificação contrato/produto bloqueados.")
	public String getListServVerProdutoBloqueados()
	{
		return ServicoVerificacaoProdutoUsados.listToString();
	}
	
	
	/**
	 * Permite a liberação de um serviço verificação através do session ID referente a ele.
	 * 
	 * @param sessionId
	 */
	@ManagedOperation(description="Libera serviço verificação pelo  ID da respectiva session.")
	public void releasedServVerProdutoBloqueados(String sessionId)
	{
		ServicoVerificacaoProdutoUsados.releasedlock(sessionId);
	}
	
}
