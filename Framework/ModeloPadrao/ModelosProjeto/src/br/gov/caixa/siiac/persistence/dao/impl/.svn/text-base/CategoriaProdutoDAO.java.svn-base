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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.model.EnviaNotificacaoVO;
import br.gov.caixa.siiac.model.domain.CategoriaProduto;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.persistence.dao.ICategoriaProdutoDAO;

@Repository
public class CategoriaProdutoDAO extends GenericDAO<CategoriaProduto> implements ICategoriaProdutoDAO {
	
	@Autowired
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	
	public CategoriaProdutoDAO() {
		super(CategoriaProduto.class);
	}
	
	public List<CategoriaProduto> getListFiltro(String pesquisaString, boolean pesquisaMostraInativos) {
		pesquisaString = "%" + pesquisaString + "%";
		Query q = getSession().createQuery("FROM CategoriaProduto cp WHERE (cp.noCategoriaProduto ilike :nome OR cp.sgCategoria ilike :sigla) AND cp.icAtivo = :ativo ORDER BY cp.noCategoriaProduto");
		q.setParameter("nome", pesquisaString);
		q.setParameter("sigla", pesquisaString);
		q.setParameter("ativo", !pesquisaMostraInativos);
		
		return q.list();
	}

	public boolean exist(CategoriaProduto categoriaProduto) {
		Query q = getSession().createQuery("FROM CategoriaProduto cp WHERE cp.noCategoriaProduto ilike :nome AND cp.nuCategoriaProduto <> :id");
		q.setParameter("nome", categoriaProduto.getNoCategoriaProduto());
		q.setParameter("id", categoriaProduto.getNuCategoriaProduto());
		return q.list().size() > 0;
	}
	
	public List<CategoriaProduto> getCategoriasUsuario(String matricula, Short nuPerfil) {
		
		List<Integer> categoriasAbrangentes = matrizAbrangenciaBO.getListAbrangenciaCategoriaProduto(matricula, nuPerfil);
		
		if(categoriasAbrangentes != null && categoriasAbrangentes.isEmpty()){
			return new ArrayList<CategoriaProduto>();
		}
		
		String categorias = "";
		if(categoriasAbrangentes != null){
			categorias = " AND c.nu_categoria_produto IN (:list) ";
		}
		
		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT DISTINCT( c.* ) ");
		var1.append("FROM   iacsm001.iactb001_produto AS p ");
		var1.append("       inner join iacsm001.iactb046_categoria_produto AS c ");
		var1.append("               ON p.nu_categoria_produto = c.nu_categoria_produto ");
		var1.append("WHERE  c.ic_ativo = TRUE ");
		var1.append("       AND NOT EXISTS (SELECT nu_categoria_produto ");
		var1.append("                       FROM   iacsm001.iactb064_preferencia_usro_ctgra ");
		var1.append("                       WHERE  co_usuario = :matricula) ");
		var1.append(categorias);

		SQLQuery query = getSession().createSQLQuery(var1.toString());
		query.setParameter("matricula", matricula);
		
		if(categoriasAbrangentes != null){
			query.setParameterList("list", categoriasAbrangentes);
		}
		
		query.addEntity(CategoriaProduto.class);
		
		return (List<CategoriaProduto>) query.list();
	}
	
	public List<CategoriaProduto> getCategoriasProdutos(String matricula, Short nuPerfil) {
		
		List<Integer> categoriasAbrangentes = matrizAbrangenciaBO.getListAbrangenciaCategoriaProduto(matricula, nuPerfil);
		
		if(categoriasAbrangentes != null && categoriasAbrangentes.isEmpty()){
			return new ArrayList<CategoriaProduto>();
		}
		
		String sql = "select DISTINCT(c.*) from iacsm001.iactb046_categoria_produto as c inner join iacsm001.iactb001_produto as p on c.nu_categoria_produto = p.nu_categoria_produto where c.ic_ativo = true AND c.nu_categoria_produto not in (select pr.nu_categoria_produto from iacsm001.iactb064_preferencia_usro_ctgra as pr where pr.co_usuario = :coUsuario)";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("coUsuario", matricula);
		
		query.addEntity(CategoriaProduto.class);
		
		
		return (List<CategoriaProduto>) query.list();
	}
}