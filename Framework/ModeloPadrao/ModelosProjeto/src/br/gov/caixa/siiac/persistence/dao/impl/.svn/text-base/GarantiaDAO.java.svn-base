package br.gov.caixa.siiac.persistence.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Garantia;
import br.gov.caixa.siiac.persistence.dao.IGarantiaDAO;
import br.gov.caixa.util.Utilities;

@Repository
public class GarantiaDAO extends GenericDAO<Garantia> implements IGarantiaDAO {
	
	public GarantiaDAO() {
		super(Garantia.class);
	}
	
	public List<Garantia> getListFiltro(Garantia garantia, Boolean inclusiveExcluida, BigDecimal[] valor, List<Short> listUnidadesAbrangencia, List<String> listCoProdutoAbrangencia) {
		if(listCoProdutoAbrangencia != null && listCoProdutoAbrangencia.isEmpty()){
			return new ArrayList<Garantia>();
		}
		Criteria c = getCriteria();
		
		if (garantia == null) {
			garantia = new Garantia();
		}
		
		
		if (Utilities.notEmpty(inclusiveExcluida) && inclusiveExcluida.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		if (garantia.getContrato() != null) {
			c.createAlias("contrato", "c");
			if(Utilities.notEmpty(listCoProdutoAbrangencia)){
				c.createAlias("c.produto", "p");
				c.add(Restrictions.in("p.coProduto", listCoProdutoAbrangencia));
			}
			if(Utilities.notEmpty(listUnidadesAbrangencia)){
				c.add(Restrictions.in("c.nuUnidade", listUnidadesAbrangencia));
			}
			if (garantia.getContrato().getNuUnidade() != null && garantia.getContrato().getNuUnidade() != 0) {
				c.add(Restrictions.eq("c.nuUnidade", garantia.getContrato().getNuUnidade()));
			}
			if (garantia.getContrato().getCoContrato() != null && !garantia.getContrato().getCoContrato().equals("")) {
				c.add(Restrictions.eq("c.coContrato", garantia.getContrato().getCoContrato()));
			}
		}
		if (garantia.getTipoGarantia() != null && garantia.getTipoGarantia().getNuTipoGarantia() != null  && garantia.getTipoGarantia().getNuTipoGarantia() != 0) {
			c.createAlias("tipoGarantia", "tg");
			c.add(Restrictions.eq("tg.nuTipoGarantia", garantia.getTipoGarantia().getNuTipoGarantia()));
		}
		if (garantia.getCoGarantia() != null && !garantia.getCoGarantia().equals("")) {
			c.add(Restrictions.eq("coGarantia", garantia.getCoGarantia()));
		}
		if (valor != null) {
			if (valor[0] != null && valor[0] != BigDecimal.ZERO) {
				c.add(Restrictions.ge("vrGarantia", valor[0]));
			}
			if (valor[1] != null && valor[1] != BigDecimal.ZERO) {
				c.add(Restrictions.le("vrGarantia", valor[1]));
			}
		}
		if (garantia.getIcUltimaLocalizacao() != null && !garantia.getIcUltimaLocalizacao().equals("")) {
			c.add(Restrictions.eq("icUltimaLocalizacao", garantia.getIcUltimaLocalizacao()));
		}
		if (garantia.getIcLocalizacaoUltimoInventari() != null && !garantia.getIcLocalizacaoUltimoInventari().equals("")) {
			c.add(Restrictions.eq("icLocalizacaoUltimoInventari", garantia.getIcLocalizacaoUltimoInventari()));
		}
		if (garantia.getDtUltimoInventarioGarantia() != null) {
			c.add(Restrictions.eq("dtUltimoInventarioGarantia", garantia.getDtUltimoInventarioGarantia()));
		}
		
		return findByCriteria(c);
	}
	
	public List<Garantia> getListFiltroSimples(String pesquisaString, Boolean pesquisaMostraInativos, List<Short> listUnidadesAbrangencia, List<String> listCoProdutoAbrangencia) {
		if(listCoProdutoAbrangencia != null && listCoProdutoAbrangencia.isEmpty()){
			return new ArrayList<Garantia>();
		}
		Criteria c = getCriteria();
		c.createAlias("contrato", "c");
		if (Utilities.notEmpty(pesquisaString)) {
			c.createAlias("tipoGarantia", "tipo");
			if (Utilities.notEmpty(pesquisaString)) {
				
				Disjunction disjuction = Restrictions.disjunction();
				
				if (isNumeric(pesquisaString)) {
					disjuction.add(Restrictions.eq("c.nuUnidade", Short.parseShort(pesquisaString)));
					disjuction.add(Restrictions.eq("coGarantia", Integer.parseInt(pesquisaString)));
				}
				disjuction.add(Restrictions.eq("c.coContrato", pesquisaString));
				disjuction.add(Restrictions.ilike("tipo.noTipoGarantia", pesquisaString, MatchMode.ANYWHERE));
				
				c.add(disjuction);
			}
		}
		if(Utilities.notEmpty(listCoProdutoAbrangencia) && !listCoProdutoAbrangencia.isEmpty()){
			c.createAlias("c.produto", "p");
			c.add(Restrictions.in("p.coProduto", listCoProdutoAbrangencia));
		}
		if(Utilities.notEmpty(listUnidadesAbrangencia)){
			c.add(Restrictions.in("c.nuUnidade", listUnidadesAbrangencia));
		}
		
		
		if (Utilities.notEmpty(pesquisaMostraInativos) && pesquisaMostraInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		return findByCriteria(c);
	}
	
	/**
	 * @param pesquisaString
	 * @return
	 */
	private boolean isNumeric(String pesquisaString) {
		boolean isNumeric = true;
		for (int i = 0; i < pesquisaString.length(); i++) {
			if (!Character.isDigit(pesquisaString.charAt(i))) {
				isNumeric = false;
				break;
			}
		}
		return isNumeric;
	}
	
	public void inativar(Garantia garantia) {
		merge(garantia);
	}
	
	public boolean existGarantia(Integer coGarantia, Integer nuGarantia) {
		Criteria c = getCriteria();
		if (coGarantia != null) {
			c.add(Restrictions.eq("coGarantia", coGarantia));
		}
		if (nuGarantia != null) {
			c.add(Restrictions.ne("coGarantia", coGarantia));
		}
		return findByCriteria(c).size() > 0;
	}
	
	public List<Garantia> getAllGarantiasInContrato(Contrato contrato) throws DAOException {
		Criteria c = getCriteria();
		
		c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		c.add(Restrictions.eq("contrato", contrato));
		
		return c.list();
	}
}