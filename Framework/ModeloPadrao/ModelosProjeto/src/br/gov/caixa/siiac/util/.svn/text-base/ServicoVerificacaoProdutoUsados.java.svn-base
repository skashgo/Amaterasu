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

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.siiac.controller.listeners.PhaseListenerControllVerificacaoContrato;
import br.gov.caixa.siiac.model.ListaServVerifProdutoUserVO;

/**
 * Classe responsável por controlar uma lista global de IDs de serviço verificação contrato/produto. <br>
 * Cada item da lista armazena session IDs,IDS do serviço verificação e user IDs. Para cada serviço solicitado e que não esteja em uso,<br>
 * este é inserido na lista, juntamente com o ID da session e o ID do usuário (matrícula). Quando um <br>
 * serviço não esta mais sendo usado, o ID do serviço é retirado  da lista. O método para obtenção do
 * bloqueio é sincronizado para evitar problemas de condição de corrida que deixaria o acesso inconsistente.<br>
 * O usuário é armazenado somente para efeitos de log e auditoria, o ID da session é usado, pois na liberação <br>
 * do bloqueio dependendo do caso não temos mais o ID do serviço, desta forma a liberação é feita pelo ID da session.<br>
 *  O session ID também é usado em requisições posteriores aa obtenção do bloqueio, para verificar se o ID do serviço,<br>
 *  esta liberado para o session ID atual.
 * 
 * 
 * @see PhaseListenerControllVerificacaoContrato
 * @see ListaServVerifProdutoUserVO
 * @author Caixa Econômica Federal
 *
 */
public class ServicoVerificacaoProdutoUsados 
{
	/**
	 * Atributo estático usado como key para armazenar a URL do usuário na session
	 */
	public static String URL_SESSION_ESCOLHE_VERIFICACAO="URL_SESSION_ESCOLHE_VERIFICACAO";
	
	/**
	 * Lista global para armazenamento dos IDs de serviços usados, para controle de acesso
	 */
	private static List<ListaServVerifProdutoUserVO> list=new ArrayList<ListaServVerifProdutoUserVO>();
	


	/**
	 * Realiza a busca pelo ID do serviço a ser acessado, caso não seja encontrado, é inserido na<br>
	 * lista e o acesso liberado.<br>
	 * <br>
	 * Caso seja encontrado na lista, é porque esta bloqueado, neste caso é verificado se esta bloqueado <br>
	 * para a session do usuário, se sim, o acesso é liberado.<br>
	 * Caso contrário, o ID esta bloqueado e para outra session (outro usuário), neste caso o acesso é bloqueado.
	 * 
	 * @param id - ID de indentificação único para um serviço verificação produto/contrato.
	 * @param userID - ID de indentificação do usuário para o qual o serviço esta bloqueado, contém a matrícula do usuário.
	 * @param userSessionID - ID da session do JBoss criada para a sessão do usuário
	 * @return Retorna true caso o ID esteja liberado, e false caso esteja bloqeuado.
	 */
	public synchronized static boolean  getLock(int id,String userID,String userSessionID)
	{
		ListaServVerifProdutoUserVO vNow=null;
		
		for (ListaServVerifProdutoUserVO ls:list)
		{
			if (ls.getID()==id)
			{
				vNow=ls;
				break;
			}
		}
		
		if (vNow==null)
		{
			list.add(new ListaServVerifProdutoUserVO(userID,id,userSessionID));
			return true;
		}
		else
		{
			if (vNow.getUserSessionID().equals(userSessionID))
			{
				return true;
			}
			else
				return false;
			
		}
	}
	
	/**
	 * Libera o acesso do ID do serviço através da session.<br>
	 * Neste caso todos os IDs de serviço da mesma session são liberados.
	 * 
	 * 03/07/2012
	 * O contador do for (i) está sendo decrementado para que seja possível
	 * remover todos itens do list que possuem o userSessionID passado como parâmetro.
	 * 
	 * @param userSessionID
	 */
	public static void releasedlock(String userSessionID)
	{
		for (int i=0;i<list.size();i++)
		{
			ListaServVerifProdutoUserVO ls=list.get(i);
			
			if (ls.getUserSessionID().equals(userSessionID))
			{
				list.remove(i);
				i--;
			}
		}
		
	}
	
	/**
	 * Verifica se existe algum bloqueio para a sessiona atual
	 * 
	 * @param userSessionID
	 * @return
	 */
	public static boolean  isLock(String userSessionID)
	{
		for (ListaServVerifProdutoUserVO ls:list)
		{
			if (ls.getUserSessionID().equals(userSessionID))
			{
				return true;
			}
		}
		return false;
	}	

	public static String listToString()
	{
		String ret="";
		
		for (ListaServVerifProdutoUserVO ls:list)
		{
			ret=ret+"ID Serv. Verif.: "+ls.getID() + " | ID Usuário(Matrícula): "+ls.getUserID()+ " | ID Session: " +ls.getUserSessionID() + "\n";
		}
		return ret;
	}
}
