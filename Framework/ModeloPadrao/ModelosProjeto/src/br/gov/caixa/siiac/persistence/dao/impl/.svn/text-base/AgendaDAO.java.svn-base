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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProduto;
import br.gov.caixa.siiac.persistence.dao.IAgendaDAO;
import br.gov.caixa.siiac.persistence.dao.IPreferenciasUsuarioCategoriaProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IPreferenciasUsuarioDAO;
import br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.util.Utilities;

@Repository
@Scope("prototype")
public class AgendaDAO extends GenericDAO<Contrato> implements IAgendaDAO {
	
	private IPreferenciasUsuarioDAO preferenciasUsuarioDAO;
	private IPreferenciasUsuarioCategoriaProdutoDAO preferenciasUsuarioCategoriaProdutoDAO;
	private IProdutoUsuarioDAO produtoUsuarioDAO;
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	
	public AgendaDAO() {
		super(Contrato.class);
	}
	
	@Autowired
	public void setPreferenciasUsuarioDAO(IPreferenciasUsuarioDAO preferenciasUsuarioDAO) {
		this.preferenciasUsuarioDAO = preferenciasUsuarioDAO;
	}
	
	@Autowired
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}

	public IPreferenciasUsuarioCategoriaProdutoDAO getPreferenciasUsuarioCategoriaProdutoDAO() {
		return preferenciasUsuarioCategoriaProdutoDAO;
	}

	public void setPreferenciasUsuarioCategoriaProdutoDAO(
			IPreferenciasUsuarioCategoriaProdutoDAO preferenciasUsuarioCategoriaProdutoDAO) {
		this.preferenciasUsuarioCategoriaProdutoDAO = preferenciasUsuarioCategoriaProdutoDAO;
	}

	@Autowired
	public void setProdutoUsuarioDAO(IProdutoUsuarioDAO produtoUsuarioDAO) {
		this.produtoUsuarioDAO = produtoUsuarioDAO;
	}
	
	public List<Contrato> getListAgenda(boolean filtrarPreferenciais, IUsuario userLogado, Integer first, Integer rows, Map<String, Integer> order, List<Short> listUnidadesAbrangencia) {
		Query q = getListAgenda(filtrarPreferenciais, userLogado, false, order, listUnidadesAbrangencia);
		q.setFirstResult(first);
		q.setMaxResults(rows);
		
		@SuppressWarnings("unchecked")
		List<Contrato> listContrato = q.list();
		return listContrato;
	}
	
	public Integer getListAgendaCount(boolean filtrarPreferenciais, IUsuario userLogado, List<Short> listUnidadesAbrangencia) {
		Query q = getListAgenda(filtrarPreferenciais, userLogado, true, null, listUnidadesAbrangencia);
		return ((Long) q.uniqueResult()).intValue();
	}
	
	public Query getListAgenda(boolean filtrarPreferenciais, IUsuario userLogado, boolean count, Map<String, Integer> order, List<Short> listUnidadesAbrangencia) {
		List<String> listProdutoUsuarioByCoProduto = matrizAbrangenciaBO.getListAbrangenciaProduto(userLogado.getMatricula(), userLogado.getPerfis().get(0));
		Map<String, Object> param = new HashMap<String, Object>();
		String sql = "";
		if (count) {
			sql = "SELECT COUNT(o) FROM Contrato o WHERE o.icPrazoVerificacao = :icPrazoVerificacao AND o.icAgendaGerada = :icAgendaGerada ";
		} else {
			sql = "SELECT o FROM Contrato o WHERE o.icPrazoVerificacao = :icPrazoVerificacao AND o.icAgendaGerada = :icAgendaGerada ";
		}
		param.put("icPrazoVerificacao", Contrato.PRAZO_VERIFICACAO_ID_CONTRATO_A_VERIFICAR);
		param.put("icAgendaGerada", Contrato.AGENDA_GERADA_ID_GERACAO_OK);
		
		if (filtrarPreferenciais) {			
			if (preferenciasUsuarioDAO.hasPreferencial(userLogado.getMatricula()) || preferenciasUsuarioCategoriaProdutoDAO.hasPreferencial(userLogado.getMatricula()) || preferenciasUsuarioDAO.hasServicoPreferencial(userLogado.getMatricula())) {
				List<String> listPreferenciasUsuarioByCoProduto = preferenciasUsuarioDAO.listProdutoPreferencial(userLogado.getMatricula());
				List<String> listPreferenciasServicoByCoProduto = preferenciasUsuarioDAO.listServicoPreferencial(userLogado.getMatricula());
				
				if(!listPreferenciasUsuarioByCoProduto.isEmpty()){
					sql += " AND o.produto.coProduto IN (:list1) ";
					param.put("list1", listPreferenciasUsuarioByCoProduto);
				}
				if(!listPreferenciasServicoByCoProduto.isEmpty()){
					sql += " AND o.nuContrato IN (:list2) ";
					param.put("list2", listPreferenciasServicoByCoProduto);
				}
				
			} else {
				if (produtoUsuarioDAO.getCountProdutoUsuario(userLogado.getMatricula()) > 0) {
					if(listProdutoUsuarioByCoProduto != null && !listProdutoUsuarioByCoProduto.isEmpty()){
						sql += " AND o.produto.coProduto IN (:list3) ";
						param.put("list3", listProdutoUsuarioByCoProduto);
					}
				}
			}
		} else {
			if (produtoUsuarioDAO.getCountProdutoUsuario(userLogado.getMatricula()) > 0) {
				if(listProdutoUsuarioByCoProduto != null && !listProdutoUsuarioByCoProduto.isEmpty()){
					sql += " AND o.produto.coProduto IN (:list4) ";
					param.put("list4", listProdutoUsuarioByCoProduto);
				}
			}
		}
		
		if (Utilities.notEmpty(listUnidadesAbrangencia)) {
			sql += " AND (o.nuUnidade, o.nuNatural) IN (SELECT u.id.nuUnidade, u.id.nuNatural FROM Unidade u WHERE u.id.nuUnidade IN (:listUnidades))";
			param.put("listUnidades", listUnidadesAbrangencia);
		}
		
		String sufixoOrder = "";
		if (order != null) {
			for (String s : order.keySet()) {
				if (order.get(s) != null) {
					if (order.get(s) == 1) {
						sufixoOrder = " ASC";
					} else {
						sufixoOrder = " DESC";
					}
					if (s.equals("sgContrato")) {
						sql += " ORDER BY o.produto.categoriaProduto.sgCategoria" + sufixoOrder;
					}
					if (s.equals("prazo")) {
						sql += " ORDER BY o.qtPrazoVerificacao" + sufixoOrder;
					}
					if (s.equals("produto")) {
						sql += " ORDER BY o.produto.coProduto " + sufixoOrder;
					}
					if (s.equals("contrato")) {
						sql += " ORDER BY o.nuContrato" + sufixoOrder;
					}
					if (s.equals("noCliente")) {
						sql += " ORDER BY o.noCliente" + sufixoOrder;
					}
					if (s.equals("cpfCnpj")) {
						sql += " ORDER BY o.nuIdentificadorCliente" + sufixoOrder;
					}
					if (s.equals("valor")) {
						sql += " ORDER BY o.vrNominalContrato" + sufixoOrder;
					}
					break;
				}
			}
		}
		Query q = getSession().createQuery(sql);
		q.setProperties(param);
		return q;
	}
	
	public Integer getQuantidadeContratoByPrazo(Integer nuContrato, Character estado) {
		StringBuffer sbSQL = new StringBuffer();
		if (estado == ResultadoApontamentoProduto.ESTADO_VERIFICACAO_APONTAMENTO_ID_NAO_VERIFICADA) {
			sbSQL.append("SELECT COUNT(o) FROM");
			sbSQL.append("  VerificacaoContrato o");
			sbSQL.append(" WHERE o.contrato.nuContrato = :contrato");
			sbSQL.append("  AND o.icEstadoVerificacao = :estado");
		} else {
			sbSQL.append("SELECT COUNT(o) FROM");
			sbSQL.append("  VerificacaoContratoParecer o ");
			sbSQL.append(" WHERE o.nuContrato = :contrato ");
			sbSQL.append("  AND o.icEstadoVerificacao = :estado");
		}
		Query q = getSession().createQuery(sbSQL.toString());
		q.setParameter("contrato", nuContrato);
		q.setParameter("estado", estado);
		return ((Long) q.uniqueResult()).intValue();
	}
	
	public boolean existeAgendaContrato(Integer nuContrato) {
		Criteria criteria = getCriteria().add(Restrictions.eq("nuContrato", nuContrato)).add(Restrictions.eq("icAgendaGerada", Contrato.AGENDA_GERADA_ID_GERACAO_OK)).setProjection(Projections.rowCount());
		
		return ((Integer) criteria.uniqueResult()).intValue() > 0;
	}
}