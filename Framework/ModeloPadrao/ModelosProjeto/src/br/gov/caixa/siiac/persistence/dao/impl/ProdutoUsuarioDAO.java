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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.model.domain.PreferenciaUsuarioServico;
import br.gov.caixa.siiac.model.domain.PreferenciasUsuario;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.ProdutoUsuario;
import br.gov.caixa.siiac.model.domain.Usuario;
import br.gov.caixa.siiac.persistence.dao.IPreferenciaUsuarioServicoDAO;
import br.gov.caixa.siiac.persistence.dao.IPreferenciasUsuarioDAO;
import br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO;
import br.gov.caixa.siiac.util.LogCEF;

/**
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class ProdutoUsuarioDAO extends GenericDAO<ProdutoUsuario> implements IProdutoUsuarioDAO {
	private static final Log LOG = LogFactory.getLog(ProdutoUsuarioDAO.class);

	private static final String ERRO_EXCLUIR_PRODUTO_USUARIO = "Erro ao Excluir o Produto do Usuario: %s";
	private static final String ERRO_GRAVAR_PRODUTO_USUARIO = "Erro ao Gravar o Produto do Usuario: %s";

	/**
	 * @param persistenceClass
	 */
	public ProdutoUsuarioDAO() {
		super(ProdutoUsuario.class);
	}
	
	private IPreferenciasUsuarioDAO preferenciasUsuarioDAO;
	private IPreferenciaUsuarioServicoDAO preferenciaUsuarioServicoDAO;

	public List<ProdutoUsuario> getAllAssociados(String matricula) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("usuario.coUsuario", matricula));
		
		return findByCriteria(crit);
	}
	
	public List<ProdutoUsuario> getAllAssociadosLikeProduto(String matricula, String coProduto, List<String> produtoAbrangentes) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("usuario.coUsuario", matricula));
		crit.add(Restrictions.like("produto.coProduto", coProduto, MatchMode.START));
		crit.add(Restrictions.in("produto.coProduto", produtoAbrangentes));
		return findByCriteria(crit);
	}
	
	public List<ProdutoUsuario> getAllAssociadosLikeProdutoByCategoria(String matricula, String coProduto, Integer nuCategoria, List<String> produtoAbrangentes) {
		
		SQLQuery q = getSession().createSQLQuery("select pu.* from iacsm001.iactb038_produto_usuario as pu inner join iacsm001.iactb001_produto as p on pu.co_produto = p.co_produto where pu.co_usuario = :matricula AND p.nu_categoria_produto = :nuCategoria AND pu.co_produto ilike :coProduto AND p.coProduto IN (:produtos)");
		q.setParameter("matricula", matricula);
		q.setParameter("nuCategoria", nuCategoria);
		q.setParameter("coProduto", "%" + coProduto + "%");
		q.setParameterList("produtos", produtoAbrangentes);
		
		q.addEntity(ProdutoUsuario.class);
		
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> getAllNaoAssociados(String matricula) {
		DetachedCriteria dc = DetachedCriteria.forClass(ProdutoUsuario.class)
								.add(Restrictions.eq("usuario.coUsuario", matricula))
								.setProjection(Projections.property("produto.coProduto"));
		
		Criteria criteria = getSession().createCriteria(Produto.class)
						.add(Restrictions.eq("icAtivo", Boolean.TRUE))
						.add(Subqueries.propertyNotIn("coProduto", dc));
		return criteria.list();
	}
	
	public void limpaProdutosUsuario(String matricula) {
		for(ProdutoUsuario pu : findByCriteria(getSession().createCriteria(PreferenciasUsuario.class).add(Restrictions.eq("usuario.coUsuario", matricula)))){
			delete(pu);
		}
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO#saveProdutosUsuario(java.util.List)
	 */
	public void limpaProdutosUsuarioAssociados(String matricula) {
		for(ProdutoUsuario pu : findByCriteria(getCriteria().add(Restrictions.eq("usuario.coUsuario", matricula)))){
			delete(pu);
		}
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO#deleteAll(java.lang.String, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public void deleteAllProdutosNaoExistentesNaLista(String matricula, List<ProdutoUsuario> produtos) {
		Session session = getSession();

		List<String> codigos = getCriteria()
								.add(Restrictions.eq("usuario.coUsuario", matricula))
								.setProjection(Projections.property("produto.coProduto"))
								.list();

		for(String codProd : codigos) {
			boolean contain = false;
			for(ProdutoUsuario pu : produtos) {
				if(pu.getProduto().getCoProduto().equals(codProd)) {
					contain = true;
					break;
				}
			}
			
			if(!contain){
				for(ProdutoUsuario pu : findByCriteria(getCriteria().add(Restrictions.eq("usuario.coUsuario", matricula)).add(Restrictions.eq("produto.coProduto", codProd)))){
					delete(pu);
				}
			}
			
		}
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.persistence.dao.IProdutoUsuarioDAO#saveAllProdutosDaLista(java.lang.String, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public void saveAllProdutosDaLista(String matricula, List<ProdutoUsuario> produtos) {
		Session session = getSession();
		
		
		List<String> codigos = getCriteria()
								.add(Restrictions.eq("usuario.coUsuario", matricula))
								.setProjection(Projections.property("produto.coProduto"))
								.list();
		
		for(ProdutoUsuario pu : produtos) {
			boolean contain = false;
			for(String codProd : codigos) {
				if(pu.getProduto().getCoProduto().equals(codProd)) {			
					contain = true;				
					break;
				}
			}

			if(!contain){
				merge(pu);
				session.flush();
			}
		}
	}

	public boolean deletarProduto(String matricula, String coProduto) {
		boolean resultado = false;
		try {
		    Usuario usu = new Usuario();
		    usu.setCoUsuario(matricula);
						
		    Produto pro = new Produto();
		    pro.setCoProduto(coProduto);
			
		    ProdutoUsuario pru = new ProdutoUsuario();
		    pru.setUsuario(usu);
		    pru.setProduto(pro);
	    	delete(pru);
			resultado = true;
		} catch (Exception e) {
			LogCEF.error(String.format(ERRO_EXCLUIR_PRODUTO_USUARIO, e.getMessage()));
		}
		return resultado;
	}

	public boolean gravarProduto(String matricula, String coProduto) {
		boolean resultado = false;
		try {
			Usuario usu = new Usuario();
			usu.setCoUsuario(matricula);
			
		    Produto pro = new Produto();
		    pro.setCoProduto(coProduto);
			
		    ProdutoUsuario pru = new ProdutoUsuario();
		    pru.setUsuario(usu);
		    pru.setProduto(pro);
		    
	    	merge(pru);
	    	resultado = true;
		} catch (Exception e) {
			LogCEF.error(String.format(ERRO_GRAVAR_PRODUTO_USUARIO, e.getMessage()));
		}
		return resultado;
	}

	public void adjustAllProdutosPreferenciais(String matricula) {
		Session session = getSession();
		String sql = "SELECT * FROM iacsm001.iactb035_preferencias_usuario " +
				"WHERE (co_usuario, co_produto) NOT IN " +
				"	(Select co_usuario, co_produto FROM iacsm001.iactb038_produto_usuario where co_usuario = :usuario)";
		SQLQuery q = session.createSQLQuery(sql).addEntity(PreferenciasUsuario.class);
		q.setString("usuario", matricula);
		for(Object o : q.list()){
			PreferenciasUsuario pu = (PreferenciasUsuario) o;
			preferenciasUsuarioDAO.delete(pu);
		}
				
	}

	public void adjustAllServicosPreferenciais(String matricula) {
		Session session = getSession();
		String sql = ""
			+ "SELECT * FROM   iacsm001.iactb041_preferencia_usro_srvco "
			+ "WHERE  ( nu_servico_verificacao, co_usuario ) NOT IN "
			+ "              (SELECT tb003.nu_servico_verificacao, "
			+ "               tb038.co_usuario "
			+ "                                                  FROM "
			+ "              iacsm001.iactb003_servico_vrfco_produto tb003 "
			+ "              INNER JOIN iacsm001.iactb038_produto_usuario tb038 "
			+ "                ON tb038.co_produto = tb003.co_produto "
			+ "                                                  WHERE "
			+ "              tb038.co_usuario = :usuario) ";
		SQLQuery q = session.createSQLQuery(sql).addEntity(PreferenciaUsuarioServico.class);
		q.setString("usuario", matricula);
		for(Object o : q.list()){
			PreferenciaUsuarioServico pus = (PreferenciaUsuarioServico) o;
			preferenciaUsuarioServicoDAO.delete(pus);
		}
	}

	public Integer getCountProdutoUsuario(String matricula) {
		Criteria c = getCriteria()
					.add(Restrictions.eq("usuario.coUsuario", matricula))
					.setProjection(Projections.rowCount());
		return ((Integer) c.uniqueResult()).intValue();
	}
	
	public Integer getCountProdutoUsuarioByCategoria(String matricula, int nuCategoria) {

		SQLQuery q = getSession().createSQLQuery("select CAST(COUNT(*) as integer) from iacsm001.iactb038_produto_usuario as pu inner join iacsm001.iactb001_produto as p on pu.co_produto = p.co_produto where pu.co_usuario = :matricula AND p.nu_categoria_produto = :nuCategoria");
		q.setParameter("matricula", matricula);
		q.setParameter("nuCategoria", nuCategoria);
		return (Integer) q.uniqueResult();
	}
	
	public List<String> listProdutoUsuarioByCoProduto(String matriculaUserLogado){
		Query q = getSession().createQuery("SELECT o.produto.coProduto FROM ProdutoUsuario o WHERE o.usuario.coUsuario =:matricula");
		q.setParameter("matricula", matriculaUserLogado);
		return q.list();
	}
}
