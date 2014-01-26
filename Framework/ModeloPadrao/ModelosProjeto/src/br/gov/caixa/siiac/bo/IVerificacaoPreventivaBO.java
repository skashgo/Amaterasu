/**
 * Copyright (c) 2009-2011 Caixa Econ�mica Federal. Todos os direitos
 * reservados.
 * 
 * Caixa Econ�mica Federal - SIIAC - Sistema Integrado de Acompanhamento da Conformidade
 * 
 * Este programa de computador foi desenvolvido sob demanda da CAIXA e est�
 * protegido por leis de direitos autorais e tratados internacionais. As
 * condi��es de c�pia e utiliza��o do todo ou partes dependem de autoriza��o da
 * empresa. C�pias n�o s�o permitidas sem expressa autoriza��o. N�o pode ser
 * comercializado ou utilizado para prop�sitos particulares.
 * 
 * Uso exclusivo da Caixa Econ�mica Federal. A reprodu��o ou distribui��o n�o
 * autorizada deste programa ou de parte dele, resultar� em puni��es civis e
 * criminais e os infratores incorrem em san��es previstas na legisla��o em
 * vigor.
 * 
 * Hist�rico do Subversion:
 * 
 * LastChangedRevision:  
 * LastChangedBy:  
 * LastChangedDate: 
 * 
 * HeadURL: 
 * 
 */
package br.gov.caixa.siiac.bo;


import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.exception.ReportErrorCreateDatasourceException;
import br.gov.caixa.siiac.exception.ReportFinalNullException;
import br.gov.caixa.siiac.exception.ReportInvalidPathException;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.FiltroVerificacaoPreventivaVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.util.FilterBase;
import br.gov.caixa.siiac.view.mb.pagination.ParamPagination;

/**
 * @author GIS Consult
 *
 */
public interface IVerificacaoPreventivaBO 
{
	public Contrato merge(Contrato c);
	public void delete(Contrato c);
	public List<Contrato> findAll();
	public List<Contrato> getListVerificacaoPreventivaFiltroSimples(String pesquisa)  throws SIIACException ;
	List<Contrato> simpleFilter(IUsuario usuarioLogado, Short perfilUsuario, String pesquisaString, Boolean produtosPreferenciais, ParamPagination paramPagination) throws DAOException;
	List<Contrato> simpleFilter(IUsuario usuarioLogado, Short perfilUsuario, FilterBase filtro, ParamPagination paramPagination) throws DAOException;

	List<Contrato> advancedFilter(IUsuario usuarioLogado, Short perfilUsuario, FiltroVerificacaoPreventivaVO filtro, ParamPagination paramPagination) throws DAOException;
	
	/**
	 * Faz a busca de Verificações preventivas <br/>
	 * utilizando uma classe de filtro generica
	 * @param usuarioLogado - Usuario Logado no sistema
	 * @param perfilUsuario - Perfil do usuario logado
	 * @param filtro - Objeto de filtro contentendo os valores dos filtros.
	 * @param paramPagination - Objeto referente ao uso de paginação no banco.
	 * @return Lista contendo os contratos de acordo com os parâmetros passados
	 * @throws DAOException
	 */
	List<Contrato> advancedFilter(IUsuario usuarioLogado, Short perfilUsuario, FilterBase filtro, ParamPagination paramPagination) throws DAOException;
	
	public byte[] geraRelatorio(Map param, String tipoRelatorio, String caminhoRelatorio, String matricula, List verificacao) throws DAOException, ReportInvalidPathException, ReportErrorCreateDatasourceException, ReportFinalNullException, JRException;
	/**
	 * @param usuarioLogado
	 * @param short1
	 * @param pesquisaString
	 * @param produtosPreferenciais
	 * @param param
	 * @return
	 */
	public int simpleFilterCount(IUsuario usuarioLogado, Short short1, String pesquisaString, Boolean produtosPreferenciais, ParamPagination param);
	
	public int simpleFilterCount(IUsuario usuarioLogado, Short short1, FilterBase filtro, ParamPagination param);
	/**
	 * @param usuarioLogado
	 * @param perfilUserLogado
	 * @param filtroVO
	 * @param param
	 * @return
	 */
	public int advancedFilterCount(IUsuario usuarioLogado, Short perfilUserLogado, FiltroVerificacaoPreventivaVO filtroVO, ParamPagination param);
	
	/**
	 * Faz a contagem de Verificações preventivas <br/>
	 * utilizando uma classe de filtro generica com paginação no banco
	 * @param usuarioLogado - Usuario Logado no sistema
	 * @param perfilUsuario - Perfil do usuario logado
	 * @param filtro - Objeto de filtro contentendo os valores dos filtros.
	 * @param paramPagination - Objeto referente ao uso de paginação no banco.
	 * @return Número de registros que atendem as condições de filtragem. 
	 * @throws DAOException
	 */
	public int advancedFilterCount(IUsuario usuarioLogado, Short perfilUserLogado, FilterBase filtroVO, ParamPagination param);

	/**
	 * Método responsável por verificar se a Verificação Preventiva passada como parâmetro,
	 * é passível de exclusão.
	 * Para que o método retorne <b>true</b>, a Verifcação Preventiva precisa estar na
	 * situação <b>Não Verificada</b> e <b>NÃO</b> possuir nenhum parecer gerado.
	 * @param nuContrato
	 * @return <b>true</b> - Exibe o ícone <br/> <b>false</b> - Não exibe o ícone
	 */
	public boolean permitirExclusao(Integer nuContrato);
}
