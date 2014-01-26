package br.gov.caixa.siiac.persistence.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.ContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.persistence.dao.IContratoDAO;
import br.gov.caixa.siiac.view.mb.pagination.ParamPagination;
import br.gov.caixa.util.Utilities;

@Repository
@Scope("prototype")
public class ContratoDAO extends GenericDAO<Contrato> implements IContratoDAO {

	public ContratoDAO() {
		super(Contrato.class);
	}

	public List<Contrato> getListFiltro(Contrato contrato, BigDecimal... faixa) {
		Criteria c = getCriteria();
		c.createAlias("detalhesContrato", "dcAlias", Criteria.LEFT_JOIN);

		return findByCriteria(c);
	}

	@SuppressWarnings("unchecked")
	public List<Contrato> getLikeCoContrato(String coContrato, List<Short> unidades, List<String> produtos) {
		if(produtos!= null && produtos.isEmpty()){
			return new ArrayList<Contrato>();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("FROM Contrato c WHERE c.coContrato like :coContrato AND c.icAtivo = true ");
		param.put("coContrato", coContrato + "%");
		if(unidades != null){
			sbSQL.append(" AND c.nuUnidade IN (:unidades) ");
			param.put("unidades", unidades);
		}
		if(produtos != null){
			sbSQL.append(" AND c.produto.coProduto IN (:produtos) ");
			param.put("produtos", produtos);
		}
		Query q = getSession().createQuery(sbSQL.toString());
		q.setProperties(param);
		return q.list();
	}
	
	public List<Contrato> filtra(ContratoVO contratoVO, List<String> produtos, ParamPagination paramPagination){
		if(produtos!= null && produtos.isEmpty()){
			return new ArrayList<Contrato>();
		}
		return getQueryFiltra(contratoVO, produtos, paramPagination, false).list();
	}
	public Query getQueryFiltra(ContratoVO contratoVO, List<String> produtos, ParamPagination paramPagination, boolean count) {
		StringBuffer hql = new StringBuffer();
		if(count){
			hql.append("SELECT COUNT(c) ");
		}else{
			hql.append("SELECT c ");
		}
		hql.append("FROM Contrato c, Produto _p where c.produto = _p ");
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if(Utilities.notEmpty(contratoVO.getCoUsuario())) {
			hql.append(" AND c.produto.coProduto in(select p.coProduto from Produto p inner join p.produtosUsuario pu" 
						+ " on p.coProduto = pu.produto.coProduto where pu.usuario.coUsuario = :coUsuario)");
			parametros.put("coUsuario", contratoVO.getCoUsuario());
		}
		
		if(Utilities.notEmpty(contratoVO.getUnidades())) {
			hql.append(" AND c.nuUnidade IN (:unidades) ");
			parametros.put("unidades", contratoVO.getUnidades());
		}
		
		if(produtos != null && produtos.size() != 0){
			hql.append(" AND c.produto.coProduto IN (:produtos) ");
			parametros.put("produtos", produtos);
		}
		
		if(Utilities.notEmpty(contratoVO.getNuUnidade())) {
			hql.append(" AND c.nuUnidade = :nuUnidade");
			parametros.put("nuUnidade", contratoVO.getNuUnidade());
		}
		
		if(contratoVO.isSimpleFilter()){
			this.simpleFilter(hql, parametros, contratoVO);
		}else{
			this.advancedFilter(hql, parametros, contratoVO);
		}

		hql.append(" and c.icAtivo = :icAtivo");
		if (Utilities.notEmpty(contratoVO.getMostraInativos()) && contratoVO.getMostraInativos().equals(Boolean.TRUE)) {
			parametros.put("icAtivo", Boolean.FALSE);
		}else{
			parametros.put("icAtivo", Boolean.TRUE);
		}

		if (paramPagination.getOrder() != null && !count) {
			for (String s : paramPagination.getOrder().keySet()) {
				String order = paramPagination.getOrder().get(s);
				if (order != null) {
					if (s.equals("unidade")) {
						hql.append(" ORDER BY c.nuUnidade " + order);
					}
					if (s.equals("produto")) {
						hql.append(" ORDER BY c.produto.coProduto " + order);
					}
					if (s.equals("contrato")) {
						hql.append(" ORDER BY c.coContrato " + order);
					}
					if (s.equals("cliente")) {
						hql.append(" ORDER BY c.noCliente " + order);
					}
					if (s.equals("identificador")) {
						hql.append(" ORDER BY c.nuIdentificadorCliente " + order);
					}
					if (s.equals("dtInclusao")) {
						hql.append(" ORDER BY c.dtContrato " + order);
					}
					if (s.equals("valor")) {
						hql.append(" ORDER BY c.vrNominalContrato " + order);
					}
					if (s.equals("situacao")) {
						hql.append(" ORDER BY c.icSituacaoContaContrato " + order);
					}
					
					hql.append(", c.nuContrato " + order);
					
					break;
				}
			}
		}
		Session session = getSession();
		Query query = session.createQuery(hql.toString());

		query.setProperties(parametros);
		if (!count) {
			query.setFirstResult(paramPagination.getFirstResult());
			query.setMaxResults(paramPagination.getMaxResults());
		}
		return query;
	}
	
	private void advancedFilter(StringBuffer hql, Map<String, Object> parametros, ContratoVO contratoVO) {
		BigDecimal valorInicial = null;
		BigDecimal valorFinal = null;

		if (Utilities.notEmpty(contratoVO.getFaixa()) && contratoVO.getFaixa().size() == 2) {
			valorInicial = contratoVO.getFaixa().get(0);
			valorFinal = contratoVO.getFaixa().get(1);
		}
		
		if (contratoVO.getContrato().getNuUnidade() != null	&& !contratoVO.getContrato().getNuUnidade().toString().equals("")) {
			hql.append(" and c.nuUnidade = :nuUnidade");
			parametros.put("nuUnidade", contratoVO.getContrato().getNuUnidade());
		}
		
		if (contratoVO.getContrato().getProduto().getCoProduto() != null && !contratoVO.getContrato().getProduto().getCoProduto().equals("")) {
			hql.append(" and c.produto.coProduto = :coProduto");
			parametros.put("coProduto", contratoVO.getContrato().getProduto().getCoProduto());
		}
		
		if (contratoVO.getCampoOperacao() != null && contratoVO.getCampoOperacao() > 0) {
			hql.append(" and c.produto.nuOperacao = :nuOperacao");
			parametros.put("nuOperacao", contratoVO.getCampoOperacao());
		}
		
		if (contratoVO.getContrato().getIcContaAbertaLote() != null	&& !contratoVO.getContrato().getIcContaAbertaLote().equals(false)) {
			hql.append(" and c.icContaAbertaLote = :icContaAbertaLote");
			parametros.put("icContaAbertaLote", contratoVO.getContrato().getIcContaAbertaLote());
		}
		
		if (contratoVO.getContrato().getCoContrato() != null && !contratoVO.getContrato().getCoContrato().equals("")) {
			hql.append(" and c.coContrato like :coContrato");
			parametros.put("coContrato", contratoVO.getContrato().getCoContrato());
		}
		
		if (valorInicial != null && valorFinal != null) {
			hql.append(" and c.vrNominalContrato between :valorInicial and :valorFinal");
			parametros.put("valorInicial", valorInicial);
			parametros.put("valorFinal", valorFinal);
		}
		
		if (contratoVO.getContrato().getDtContrato() != null) {
			hql.append(" and c.dtContrato = :dtContrato");
			parametros.put("dtContrato", contratoVO.getContrato().getDtContrato());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getCoCliente())) {
			hql.append(" and c.coCliente = :coCliente");
			parametros.put("coCliente", contratoVO.getContrato().getCoCliente());
		}
		
		if (contratoVO.getContrato().getNoCliente() != null	&& !contratoVO.getContrato().getNoCliente().equals("")) {
			hql.append(" and c.noCliente ilike :noCliente");
			parametros.put("noCliente", "%"+contratoVO.getContrato().getNoCliente()+"%");
		}
		
		if (contratoVO.getContrato().getIcPfPj() != null && !contratoVO.getContrato().getIcPfPj().equals("")) {
			hql.append(" and c.icPfPj = :icPfPj");
			parametros.put("icPfPj", contratoVO.getContrato().getIcPfPj());
		}
		
		if (contratoVO.getContrato().getIcTipoIndentificadorCliente() != null && !contratoVO.getContrato().getIcTipoIndentificadorCliente().equals("")) {
			hql.append(" and c.icTipoIndentificadorCliente = :icTipoIndentificadorCliente");
			parametros.put("icTipoIndentificadorCliente", contratoVO.getContrato().getIcTipoIndentificadorCliente());
		}
		
		if (contratoVO.getContrato().getNuIdentificadorCliente() != null && 
				!contratoVO.getContrato().getNuIdentificadorCliente().trim().equals("")) {
			hql.append(" and c.nuIdentificadorCliente = :nuIdentificadorCliente");
			parametros.put("nuIdentificadorCliente", contratoVO.getContrato().getNuIdentificadorCliente());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getCoAvaliacaoSiric())) {
			hql.append(" and c.coAvaliacaoSiric = :coAvaliacaoSiric");
			parametros.put("coAvaliacaoSiric", contratoVO.getContrato().getCoAvaliacaoSiric());
		}
		
		if (contratoVO.getContrato().getIcAvaliacaoSiricAntiga() != null && !contratoVO.getContrato().getIcAvaliacaoSiricAntiga().equals(false)) {
			hql.append(" and c.icAvaliacaoSiricAntiga = :icAvaliacaoSiricAntiga");
			parametros.put("icAvaliacaoSiricAntiga", contratoVO.getContrato().getIcAvaliacaoSiricAntiga());
		}
		
		if (contratoVO.getContrato().getDetalhesContrato().getDtVencimentoContrato() != null) {
			hql.append(" and c.detalhesContrato.dtVencimentoContrato = :dtVencimentoContrato");
			parametros.put("dtVencimentoContrato", contratoVO.getContrato().getDetalhesContrato().getDtVencimentoContrato());
		}
		
		if (contratoVO.getContrato().getDetalhesContrato().getDtUltimaRenovacao() != null) {
			hql.append(" and c.detalhesContrato.dtUltimaRenovacao = :dtUltimaRenovacao");
			parametros.put("dtUltimaRenovacao", contratoVO.getContrato().getDetalhesContrato().getDtUltimaRenovacao());
		}
		
		if (contratoVO.getContrato().getDetalhesContrato().getDtLiquidacaoContrato() != null) {
			hql.append(" and c.detalhesContrato.dtLiquidacaoContrato = :dtLiquidacaoContrato");
			parametros.put("dtLiquidacaoContrato", contratoVO.getContrato().getDetalhesContrato().getDtLiquidacaoContrato());
		}
		
		if (contratoVO.getContrato().getDetalhesContrato().getDtInadimplenciaContrato() != null) {
			hql.append(" and c.detalhesContrato.dtInadimplenciaContrato = :dtInadimplenciaContrato");
			parametros.put("dtInadimplenciaContrato", contratoVO.getContrato().getDetalhesContrato().getDtInadimplenciaContrato());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNuIdentificadorEmpreendimento()))
		{
			hql.append(" and c.detalhesContrato.nuIdentificadorEmpreendimento = :nuIdentificadorEmpreendimento");
			parametros.put("nuIdentificadorEmpreendimento", contratoVO.getContrato().getDetalhesContrato().getNuIdentificadorEmpreendimento());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNuIdentificadorVendedor()))
		{
			hql.append(" and c.detalhesContrato.nuIdentificadorVendedor = :nuIdentificadorVendedor");
			parametros.put("nuIdentificadorVendedor", contratoVO.getContrato().getDetalhesContrato().getNuIdentificadorVendedor());
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoEmpreendimento()))
		{
			hql.append(" and c.detalhesContrato.noEmpreendimento LIKE :noEmpreendimento");
			parametros.put("noEmpreendimento", "%" + contratoVO.getContrato().getDetalhesContrato().getNoEmpreendimento() + "%");
		}
		
		if (Utilities.notEmpty(contratoVO.getContrato().getDetalhesContrato().getNoVendedor()))
		{
			hql.append(" and c.detalhesContrato.noVendedor LIKE :noVendedor");
			parametros.put("noVendedor", "%" + contratoVO.getContrato().getDetalhesContrato().getNoVendedor() + "%");
		}
		
		if (contratoVO.getContrato().getIcSituacaoContaContrato() != null
				&& !contratoVO.getContrato().getIcSituacaoContaContrato().equals("")) {
			hql.append(" and c.icSituacaoContaContrato = :icSituacaoContaContrato");
			parametros.put("icSituacaoContaContrato", contratoVO.getContrato().getIcSituacaoContaContrato());
		}
		
		if(contratoVO.getContrato().getNuCanalComercializacao() != null
				&& !contratoVO.getContrato().getNuCanalComercializacao().toString().equals("")) {
			hql.append(" and c.nuCanalComercializacao = :nuCanalComercializacao");
			parametros.put("nuCanalComercializacao", contratoVO.getContrato().getNuCanalComercializacao());
		}
	}
	
	private void simpleFilter(StringBuffer hql, Map<String, Object> parametros, ContratoVO contratoVO) {

		if(Utilities.notEmpty(contratoVO.getPesquisaSimples())){

			hql.append("AND (");
			
			// Contrato
			hql.append(" c.coContrato ILIKE :coContrato");
			parametros.put("coContrato", "%"+contratoVO.getPesquisaSimples()+"%");
			
			// Unidade
			if(Pattern.compile("[0-9]{1,4}").matcher(contratoVO.getPesquisaSimples()).matches()){
				hql.append(" OR c.nuUnidade = :nuUnidade");
				parametros.put("nuUnidade", Short.parseShort(contratoVO.getPesquisaSimples()));
			}
			
			// Identificador do Cliente
			String pesquisaIdentificadorCliente = contratoVO.getPesquisaSimples()
													.replace(".", "")
													.replace(" ", "")
													.replace(",", "")
													.replace("_", "")
													.replace("-", "");
			if(pesquisaIdentificadorCliente.length() <=  15){
				hql.append(" OR c.nuIdentificadorCliente = :nuIdentificadorCliente");
				parametros.put("nuIdentificadorCliente", pesquisaIdentificadorCliente);
			}
			
			
			// Nome do cliente
			hql.append(" OR c.noCliente ILIKE :noCliente");
			parametros.put("noCliente", "%"+contratoVO.getPesquisaSimples()+"%");
			
			
			// Produto
			hql.append(" OR _p.noProduto ILIKE :noProduto");
			parametros.put("noProduto", "%"+contratoVO.getPesquisaSimples()+"%");
			if(Pattern.compile("[0-9]{4}[-]?[0-9]{3}").matcher(contratoVO.getPesquisaSimples()).matches()){
				hql.append(" OR c.produto.coProduto = :noProduto");
				parametros.put("noProduto", contratoVO.getPesquisaSimples().replace("-", ""));
			}
			
			hql.append(" ) ");
			
		}
	}
	
	public List<Contrato> findByCoContrato(String coContrato) throws DAOException {
		Criteria c = getCriteria();
		c.add(Restrictions.ilike("coContrato", coContrato, MatchMode.ANYWHERE));
		
		return c.list();
	}
	
	public List<Contrato> findByUnidade(Short nuUnidade) throws DAOException {
		Criteria c = getCriteria();
		c.add(Restrictions.eq("nuUnidade", nuUnidade));
		
		return c.list();
	}
	
	public String getQuantidadeVerificacoesAvGestaoSuretCategoriaProduto(Integer categoria, Date data)
	{
		String sql = "select CAST(count(*) as varchar(100)) from iacsm001.iactb004_contrato as c inner join iacsm001.iactb001_produto as p on c.co_produto = p.co_produto "
				+ " inner join iacsm001.iactb018_verificacao_contrato as v on v.nu_contrato = c.nu_contrato "
				+ " where p.nu_categoria_produto = :nuCategoria and v.ic_estado_verificacao = 'NV' and current_date = :data";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuCategoria", categoria);
		query.setParameter("data", data);
		return (String) query.uniqueResult();
	}
	
	public String getQuantidadeVerificacoesVencendoCategoriaProduto(Integer categoria)
	{
		String sql = "select CAST(count(*) as varchar(100)) from iacsm001.iactb004_contrato as c inner join iacsm001.iactb001_produto as p on c.co_produto = p.co_produto "
				+ " inner join iacsm001.iactb018_verificacao_contrato as v on v.nu_contrato = c.nu_contrato "
				+ " where p.nu_categoria_produto = :nuCategoria and v.ic_estado_verificacao = 'NV' and v.dt_limite_verificacao = current_date";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("nuCategoria", categoria);
		return (String) query.uniqueResult();
	}

	public int simpleFilterCount(ContratoVO contratoVO, List<String> listCoProdutoAbrangencia, ParamPagination paramPagination){
		Long result = (Long) getQueryFiltra(contratoVO, listCoProdutoAbrangencia, paramPagination, true).uniqueResult();
		return result.intValue();
	}

	public int advancedFilterCount(ContratoVO contratoVO, List<String> listCoProdutoAbrangencia, ParamPagination paramPagination){
		Long result = (Long) getQueryFiltra(contratoVO, listCoProdutoAbrangencia, paramPagination, true).uniqueResult();
		return result.intValue();
	}
}