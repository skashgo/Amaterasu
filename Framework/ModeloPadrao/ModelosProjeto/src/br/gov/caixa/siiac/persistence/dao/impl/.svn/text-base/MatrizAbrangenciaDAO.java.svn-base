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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.persistence.dao.IMatrizAbrangenciaDAO;

/**
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class MatrizAbrangenciaDAO extends GenericDAO<Unidade> implements IMatrizAbrangenciaDAO {
	
	private static final Log LOG = LogFactory.getLog(MatrizAbrangenciaDAO.class);
	
	public MatrizAbrangenciaDAO() throws SIIACException {
		super(Unidade.class);
	}
	
	/**
	 * - SQL para ser utilizado quando, dada uma unidade, deseja-se obter suas vinculadas de acordo com a RN010C (vinculação GIRET)
	 * - No resultado, a própria Unidade é acrescentada
	 * @param nuUnidade
	 * @return
	 */
	public List<Unidade> getListUnidadesVinculadasRN010C(Short nuUnidade) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT * FROM icosm001.iacvw003_unidade WHERE nu_unidade IN (");
		sbSQL.append("WITH listaVinculadas(nu_unidade) AS ( ");
		sbSQL.append("WITH RECURSIVE teste(idPai) AS ( ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    icosm001.IACVW004_VINCULACAO_UNIDADE u ");
		sbSQL.append("  WHERE ");
		sbSQL.append("    (");
		sbSQL.append("      NU_UNDE_VNCLRA_U24 = ").append(nuUnidade);
		sbSQL.append("    ) ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 4 ");
		sbSQL.append("    AND nu_processo_u27 IN (8703, 8737, 8768) ");
		sbSQL.append("  UNION ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    teste t ");
		sbSQL.append("    , icosm001.IACVW004_VINCULACAO_UNIDADE u2 ");
		sbSQL.append("  WHERE u2.NU_UNDE_VNCLRA_U24 = t.idPai ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 4 ");
		sbSQL.append("    AND nu_processo_u27 IN (8703, 8737, 8768) ");
		sbSQL.append("  ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct idPai ");
		sbSQL.append("FROM");
		sbSQL.append("  teste ");
		sbSQL.append("UNION ");
		sbSQL.append("SELECT ").append(nuUnidade);
		sbSQL.append("   ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct nu_unidade ");
		sbSQL.append("FROM");
		sbSQL.append("  listaVinculadas ");
		sbSQL.append("ORDER BY");
		sbSQL.append("  nu_unidade )");
		Query q = getSession().createSQLQuery(sbSQL.toString()).addEntity(Unidade.class);
		return q.list();
	}
	
	/**
	 * - SQL para ser utilizado quando, dada uma unidade, deseja-se obter suas vinculadas de acordo com a RN010E (vinculação CGC)
	 * - No resultado, a própria Unidade é acrescentada
	 * @param nuUnidade
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Unidade> getListUnidadesVinculadasRN010E(Short nuUnidade) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT * FROM icosm001.iacvw003_unidade WHERE nu_unidade IN (");
		sbSQL.append("WITH listaVinculadas(nu_unidade) AS ( ");
		sbSQL.append("WITH RECURSIVE teste(idPai) AS ( ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    icosm001.IACVW004_VINCULACAO_UNIDADE u ");
		sbSQL.append("  WHERE ");
		sbSQL.append("    (");
		sbSQL.append("      NU_UNDE_VNCLRA_U24 = ").append(nuUnidade);
		sbSQL.append("    ) ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 1 ");
		sbSQL.append("  UNION ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    teste t ");
		sbSQL.append("    , icosm001.IACVW004_VINCULACAO_UNIDADE u2 ");
		sbSQL.append("  WHERE u2.NU_UNDE_VNCLRA_U24 = t.idPai ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 1 ");
		sbSQL.append("  ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct idPai ");
		sbSQL.append("FROM");
		sbSQL.append("  teste ");
		sbSQL.append("UNION ");
		sbSQL.append("SELECT ").append(nuUnidade);
		sbSQL.append("  ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct nu_unidade ");
		sbSQL.append("FROM");
		sbSQL.append("  listaVinculadas ");
		sbSQL.append("ORDER BY");
		sbSQL.append("  nu_unidade )");
		
		Query q = getSession().createSQLQuery(sbSQL.toString()).addEntity(Unidade.class);
		return q.list();
	}
	
	/**
	 * - SQL para ser utilizado quando, dada uma unidade, deseja-se obter suas vinculadas de acordo com a RN010C (vinculação GIRET)
	 * - No resultado, a própria Unidade é acrescentada
	 * @param nuUnidade
	 * @return
	 */
	public List<Short> getListUnidadesVinculadasRN010CNuUnidade(Short nuUnidade) {
		StringBuffer sbSQL = new StringBuffer();
		
		sbSQL.append("WITH listaVinculadas(nu_unidade) AS ( ");
		sbSQL.append("WITH RECURSIVE teste(idPai) AS ( ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    icosm001.IACVW004_VINCULACAO_UNIDADE u ");
		sbSQL.append("  WHERE ");
		sbSQL.append("    (");
		sbSQL.append("      NU_UNDE_VNCLRA_U24 = ").append(nuUnidade);
		sbSQL.append("    ) ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 4 ");
		sbSQL.append("    AND nu_processo_u27 IN (8703, 8737, 8768) ");
		sbSQL.append("  UNION ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    teste t ");
		sbSQL.append("    , icosm001.IACVW004_VINCULACAO_UNIDADE u2 ");
		sbSQL.append("  WHERE u2.NU_UNDE_VNCLRA_U24 = t.idPai ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 4 ");
		sbSQL.append("    AND nu_processo_u27 IN (8703, 8737, 8768) ");
		sbSQL.append("  ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct idPai ");
		sbSQL.append("FROM");
		sbSQL.append("  teste ");
		sbSQL.append("UNION ");
		sbSQL.append("SELECT ").append(nuUnidade);
		sbSQL.append("   ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct CAST(NU_UNIDADE AS smallint) ");
		sbSQL.append("FROM");
		sbSQL.append("  listaVinculadas ");
		sbSQL.append("ORDER BY");
		sbSQL.append("  nu_unidade");
		Query q = getSession().createSQLQuery(sbSQL.toString());
		return q.list();
	}
	
	/**
	 * - SQL para ser utilizado quando, dada uma unidade, deseja-se obter suas vinculadas de acordo com a RN010E (vinculação CGC)
	 * - No resultado, a própria Unidade é acrescentada
	 * @param nuUnidade
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Short> getListUnidadesVinculadasRN010ENuUnidade(Short nuUnidade) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("WITH listaVinculadas(nu_unidade) AS ( ");
		sbSQL.append("WITH RECURSIVE teste(idPai) AS ( ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    icosm001.IACVW004_VINCULACAO_UNIDADE u ");
		sbSQL.append("  WHERE ");
		sbSQL.append("    (");
		sbSQL.append("      NU_UNDE_VNCLRA_U24 = ").append(nuUnidade);
		sbSQL.append("    ) ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 1 ");
		sbSQL.append("  UNION ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    teste t ");
		sbSQL.append("    , icosm001.IACVW004_VINCULACAO_UNIDADE u2 ");
		sbSQL.append("  WHERE u2.NU_UNDE_VNCLRA_U24 = t.idPai ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 1 ");
		sbSQL.append("  ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct idPai ");
		sbSQL.append("FROM");
		sbSQL.append("  teste ");
		sbSQL.append("UNION ");
		sbSQL.append("SELECT ").append(nuUnidade);
		sbSQL.append("  ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct CAST(NU_UNIDADE AS smallint) ");
		sbSQL.append("FROM");
		sbSQL.append("  listaVinculadas ");
		sbSQL.append("ORDER BY");
		sbSQL.append("  nu_unidade");
		
		Query q = getSession().createSQLQuery(sbSQL.toString());
		return q.list();
	}
	
	/**
	 * SQL para ser utilizado quando, dada uma unidade, deseja-se obter suas vinculadas hierarquicamente
	 * No resultado, a própria Unidade é acrescentada
	 */
	public List<Short> getListUnidadesVinculadasHierarquicaNuUnidade(Short unidade) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("WITH listaVinculadas(nu_unidade) AS ( ");
		sbSQL.append("WITH RECURSIVE teste(idPai) AS ( ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    icosm001.IACVW004_VINCULACAO_UNIDADE u ");
		sbSQL.append("  WHERE ");
		sbSQL.append("    (");
		sbSQL.append("      NU_UNDE_VNCLRA_U24 = " + unidade);
		sbSQL.append("    ) ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 1 ");
		sbSQL.append("  UNION ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    teste t ");
		sbSQL.append("    , icosm001.IACVW004_VINCULACAO_UNIDADE u2 ");
		sbSQL.append("  WHERE u2.NU_UNDE_VNCLRA_U24 = t.idPai ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 1 ");
		sbSQL.append("  ) ");
		sbSQL.append("SELECT ");
		sbSQL.append("  distinct idPai ");
		sbSQL.append("FROM");
		sbSQL.append("  teste ");
		sbSQL.append("UNION ");
		sbSQL.append("SELECT ");
		sbSQL.append(unidade + " ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct  CAST(NU_UNIDADE AS smallint) ");
		sbSQL.append("FROM");
		sbSQL.append("  listaVinculadas ");
		sbSQL.append("ORDER BY");
		sbSQL.append("  nu_unidade");
		
		Query q = getSession().createSQLQuery(sbSQL.toString());
		return q.list();
	}
	
	/**
	 * SQL para ser utilizado quando, dada uma unidade, deseja-se obter suas vinculadas hierarquicamente
	 * No resultado, a própria Unidade é acrescentada
	 */
	public List<Unidade> getListUnidadesVinculadasHierarquica(Short unidade) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT * FROM icosm001.iacvw003_unidade WHERE nu_unidade IN (");
		sbSQL.append("WITH listaVinculadas(nu_unidade) AS ( ");
		sbSQL.append("WITH RECURSIVE teste(idPai) AS ( ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    icosm001.IACVW004_VINCULACAO_UNIDADE u ");
		sbSQL.append("  WHERE ");
		sbSQL.append("    (");
		sbSQL.append("      NU_UNDE_VNCLRA_U24 = " + unidade);
		sbSQL.append("    ) ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 1 ");
		sbSQL.append("  UNION ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    teste t ");
		sbSQL.append("    , icosm001.IACVW004_VINCULACAO_UNIDADE u2 ");
		sbSQL.append("  WHERE u2.NU_UNDE_VNCLRA_U24 = t.idPai ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 1 ");
		sbSQL.append("  ) ");
		sbSQL.append("SELECT ");
		sbSQL.append("  distinct idPai ");
		sbSQL.append("FROM");
		sbSQL.append("  teste ");
		sbSQL.append("UNION ");
		sbSQL.append("SELECT ");
		sbSQL.append(unidade + " ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct nu_unidade ");
		sbSQL.append("FROM");
		sbSQL.append("  listaVinculadas ");
		sbSQL.append("ORDER BY");
		sbSQL.append("  nu_unidade)");
		
		Query q = getSession().createSQLQuery(sbSQL.toString()).addEntity(Unidade.class);
		return q.list();
	}
	
	/** SQL para ser utilizado quando, dada uma unidade, deseja-se obter suas vinculadas de acordo com a RN010F (vinculação GILIE)
	    No resultado, a própria Unidade é acrescentada
	*/
	public List<Unidade> getListUnidadesVinculadasRN010F(Short nuUnidade) {
		
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT * FROM icosm001.iacvw003_unidade WHERE nu_unidade IN (");
		sbSQL.append("WITH listaVinculadas(nu_unidade) AS ( ");
		sbSQL.append("WITH RECURSIVE teste(idPai) AS ( ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    icosm001.IACVW004_VINCULACAO_UNIDADE u ");
		sbSQL.append("  WHERE ");
		sbSQL.append("    (");
		sbSQL.append("      NU_UNDE_VNCLRA_U24 = " + nuUnidade);
		sbSQL.append("    ) ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 4 ");
		sbSQL.append("    AND nu_processo_u27 IN (87, 8724) ");
		sbSQL.append("  UNION ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    teste t ");
		sbSQL.append("    , icosm001.IACVW004_VINCULACAO_UNIDADE u2 ");
		sbSQL.append("  WHERE u2.NU_UNDE_VNCLRA_U24 = t.idPai ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 4 ");
		sbSQL.append("    AND nu_processo_u27 IN (87, 8724) ");
		sbSQL.append("  ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct idPai ");
		sbSQL.append("FROM");
		sbSQL.append("  teste ");
		sbSQL.append("UNION ");
		sbSQL.append("SELECT ");
		sbSQL.append(nuUnidade + " ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct nu_unidade ");
		sbSQL.append("FROM");
		sbSQL.append("  listaVinculadas ");
		sbSQL.append("ORDER BY");
		sbSQL.append("  nu_unidade)");
		
		Query q = getSession().createSQLQuery(sbSQL.toString()).addEntity(Unidade.class);
		return q.list();
	}
	
	/** SQL para ser utilizado quando, dada uma unidade, deseja-se obter suas vinculadas de acordo com a RN010F (vinculação GILIE)
	    No resultado, a própria Unidade é acrescentada
	*/
	public List<Short> getListUnidadesVinculadasRN010FNuUnidade(Short nuUnidade) {
		
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("WITH listaVinculadas(nu_unidade) AS ( ");
		sbSQL.append("WITH RECURSIVE teste(idPai) AS ( ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    icosm001.IACVW004_VINCULACAO_UNIDADE u ");
		sbSQL.append("  WHERE ");
		sbSQL.append("    (");
		sbSQL.append("      NU_UNDE_VNCLRA_U24 = <numero_unidade_base>");
		sbSQL.append("    ) ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 4 ");
		sbSQL.append("    AND nu_processo_u27 IN (87, 8724) ");
		sbSQL.append("  UNION ");
		sbSQL.append("  SELECT");
		sbSQL.append("    NU_UNIDADE ");
		sbSQL.append("  FROM");
		sbSQL.append("    teste t ");
		sbSQL.append("    , icosm001.IACVW004_VINCULACAO_UNIDADE u2 ");
		sbSQL.append("  WHERE u2.NU_UNDE_VNCLRA_U24 = t.idPai ");
		sbSQL.append("    AND NU_TP_VINCULO_U22 = 4 ");
		sbSQL.append("    AND nu_processo_u27 IN (87, 8724) ");
		sbSQL.append("  ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct idPai ");
		sbSQL.append("FROM");
		sbSQL.append("  teste ");
		sbSQL.append("UNION ");
		sbSQL.append("SELECT");
		sbSQL.append("  <numero_unidade_base> ) ");
		sbSQL.append("SELECT");
		sbSQL.append("  distinct nu_unidade ");
		sbSQL.append("FROM");
		sbSQL.append("  listaVinculadas ");
		sbSQL.append("ORDER BY");
		sbSQL.append("  nu_unidade");
		
		Query q = getSession().createSQLQuery(sbSQL.toString());
		return q.list();
	}
	
	public boolean existeRestricaoProduto(String matricula) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT");
		sbSQL.append("  Count(*) ");
		sbSQL.append("FROM");
		sbSQL.append("  iacsm001.iactb038_produto_usuario ");
		sbSQL.append("WHERE co_usuario = '" + matricula + "'");
		
		Query q = getSession().createSQLQuery(sbSQL.toString());
		
		return ((BigInteger) q.uniqueResult()).intValue() > 0;
	}
	
	public List<String> listProdutosAutorizados(String matricula) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT");
		sbSQL.append("  co_produto ");
		sbSQL.append("FROM");
		sbSQL.append("  iacsm001.iactb038_produto_usuario ");
		sbSQL.append("WHERE co_usuario = '" + matricula + "'");
		
		Query q = getSession().createSQLQuery(sbSQL.toString());
		return q.list();
	}
	
	public List<Integer> listCategoriasProdutosAutorizados(String matricula) {
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT cat.nu_categoria_produto ");
		sql.append("FROM   iacsm001.iactb038_produto_usuario usr ");
		sql.append("       inner join iacsm001.iactb001_produto prod ");
		sql.append("               ON prod.co_produto = usr.co_produto ");
		sql.append("       inner join iacsm001.iactb046_categoria_produto cat ");
		sql.append("               ON cat.nu_categoria_produto = prod.nu_categoria_produto ");
		sql.append("WHERE  usr.co_usuario = :matricula ");
		
		Query q = getSession().createSQLQuery(sql.toString());
		q.setParameter("matricula", matricula);
		
		return q.list();
	}
	
	public List<String> listProdutosAutorizadosGestorProduto(String matricula) {
		if(matricula==null || matricula.equals(""))return new ArrayList<String>();
		
		Integer matriculaInt = Integer.parseInt(matricula.substring(1));
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT" );
		sbSQL.append("  distinct(co_produto) AS codigoProduto " );
		sbSQL.append("FROM" );
		sbSQL.append("  iacsm001.iactb038_produto_usuario " );
		sbSQL.append("WHERE co_usuario = '" + matricula + "'" );
		sbSQL.append("UNION " );
		sbSQL.append("SELECT" );
		sbSQL.append("  distinct(tbProduto.co_produto) AS codigoProduto " );
		sbSQL.append("FROM" );
		sbSQL.append("  icosm001.iacvw013_gestores_produto tbGestao INNER JOIN" );
		sbSQL.append("  iacsm001.iactb001_produto tbProduto " );
		sbSQL.append("ON tbGestao.nu_produto_o10 = tbProduto.nu_operacao " );
		sbSQL.append("WHERE tbGestao.nu_matricula_h01 = " + matriculaInt);
		
		Query q = getSession().createSQLQuery(sbSQL.toString());
		return q.list();
	}
	
	public List<Integer> listProdutosAutorizadosGestorCategoriaProduto(String matricula) {
		if(matricula==null || matricula.equals(""))return new ArrayList<Integer>();
		
		Integer matriculaInt = Integer.parseInt(matricula.substring(1));
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT DISTINCT(cat.nu_categoria_produto) AS codigoCategoria ");
		sql.append("FROM   iacsm001.iactb038_produto_usuario usr ");
		sql.append("       inner join iacsm001.iactb001_produto prod ");
		sql.append("               ON usr.co_produto = prod.co_produto ");
		sql.append("       inner join iacsm001.iactb046_categoria_produto cat ");
		sql.append("               ON prod.nu_categoria_produto = cat.nu_categoria_produto ");
		sql.append("WHERE  usr.co_usuario = :matricula ");
		sql.append("UNION ");
		sql.append("SELECT DISTINCT(cat.nu_categoria_produto) AS codigoCategoria ");
		sql.append("FROM   icosm001.iacvw013_gestores_produto tbGestao ");
		sql.append("       inner join iacsm001.iactb001_produto tbProduto ");
		sql.append("               ON tbGestao.nu_produto_o10 = tbProduto.nu_operacao ");
		sql.append("       inner join iacsm001.iactb046_categoria_produto cat ");
		sql.append("               ON tbProduto.nu_categoria_produto = cat.nu_categoria_produto ");
		sql.append("WHERE  tbGestao.nu_matricula_h01 = :nuMatricula ");
		
		Query q = getSession().createSQLQuery(sql.toString());
		q.setParameter("matricula", matricula);
		q.setParameter("nuMatricula", matriculaInt);
		
		return q.list();
	}
	
	public List<String> listAllProdutosAutorizados() {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT");
		sbSQL.append("  co_produto ");
		sbSQL.append("FROM");
		sbSQL.append("  iacsm001.iactb038_produto_usuario ");
		
		Query q = getSession().createSQLQuery(sbSQL.toString());
		return q.list();
	}
	
	public boolean isUnidadeGiret(Short nuUnidade) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT");
		sbSQL.append("  Count(*) ");
		sbSQL.append("FROM");
		sbSQL.append("  icosm001.iacvw004_vinculacao_unidade vinUnid INNER JOIN");
		sbSQL.append("  icosm001.iacvw003_unidade unid ");
		sbSQL.append("ON unid.nu_unidade = vinUnid.nu_unidade ");
		sbSQL.append("WHERE vinUnid.nu_unde_vnclra_u24 = 5401 ");
		sbSQL.append("  AND vinUnid.nu_tp_vinculo_u22 = 1 ");
		sbSQL.append("  AND unid.nu_tp_unidade_u21 IN (14, 49) ");
		sbSQL.append("  AND vinUnid.nu_unidade = " + nuUnidade);
		
		Query q = getSession().createSQLQuery(sbSQL.toString());
		return ((BigInteger) q.uniqueResult()).intValue() > 0;
	}

	public List<Produto> listProdutosAutorizadosGestorProdutoObj(String matricula) {
		if(matricula==null || matricula.equals(""))return new ArrayList<Produto>();
		
		Integer matriculaInt = Integer.parseInt(matricula.substring(1));
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT * FROM iacsm001.iactb001_produto WHERE co_produto IN (");
		sbSQL.append("SELECT" );
		sbSQL.append("  distinct(co_produto) AS codigoProduto " );
		sbSQL.append("FROM" );
		sbSQL.append("  iacsm001.iactb038_produto_usuario " );
		sbSQL.append("WHERE co_usuario = '" + matricula + "'" );
		sbSQL.append("UNION " );
		sbSQL.append("SELECT" );
		sbSQL.append("  distinct(tbProduto.co_produto) AS codigoProduto " );
		sbSQL.append("FROM" );
		sbSQL.append("  icosm001.iacvw013_gestores_produto tbGestao INNER JOIN" );
		sbSQL.append("  iacsm001.iactb001_produto tbProduto " );
		sbSQL.append("ON tbGestao.nu_produto_o10 = tbProduto.nu_operacao " );
		sbSQL.append("WHERE tbGestao.nu_matricula_h01 = " + matriculaInt);
		sbSQL.append(" )");
		
		Query q = getSession().createSQLQuery(sbSQL.toString()).addEntity(Produto.class);
		return q.list();
	}

	public List<Produto> listProdutosAutorizadosObj(String matricula) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("SELECT");
		sbSQL.append("  * ");
		sbSQL.append("FROM");
		sbSQL.append("  iacsm001.iactb038_produto_usuario ");
		sbSQL.append("WHERE co_usuario = '" + matricula + "'");
		
		Query q = getSession().createSQLQuery(sbSQL.toString()).addEntity(Produto.class);
		return q.list();
	}
}
