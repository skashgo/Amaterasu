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
package br.gov.caixa.siiac.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.model.domain.UnidadeId;
import br.gov.caixa.siiac.persistence.dao.IUnidadeDAO;

/**
 * @author GisConsult
 *
 */
@Service
@Scope("prototype")
public class UnidadeBO implements IUnidadeBO {
	
	private transient IUnidadeDAO unidadeDAO;
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	
	@Autowired
	public void setUnidadeDAO(IUnidadeDAO unidadeDAO) {
		this.unidadeDAO = unidadeDAO;
	}
	
	@Autowired
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}

	@Transactional
	public Unidade getUnidade(Short nuUnidade, Integer nuNatural) {
		return unidadeDAO.findById(new UnidadeId(nuUnidade, nuNatural));
	}
	
	@Transactional
	public Unidade getUnidade(Short nuUnidade) {
		Criteria crit = unidadeDAO.getCriteria().add(Restrictions.eq("id.nuUnidade", nuUnidade)).addOrder(Order.asc("id.nuNatural")).setMaxResults(1);
		List<Unidade> list = unidadeDAO.findByCriteria(crit);
		return (list != null && !list.isEmpty() ? list.get(0) : null);
	}
	
	/**
	 * Metodo que retorna a lista com todas
	 */
	public List<Unidade> getTodasUnidadesVinculadasHierarquia(Short codUnidade, Short perfil) {
		List<Unidade> lista = new ArrayList<Unidade>();
		List<Unidade> filhas = findUnidadesFilhas(codUnidade, perfil);
		for (Unidade u : filhas) {
			if (!lista.contains(u)) {
				lista.add(u);
				lista.addAll(getTodasUnidadesVinculadasHierarquia(u.getId().getNuUnidade(), perfil));
			}
		}
		return lista;
	}
	
	/**
	 * @param numeroUnidadeVinculadora
	 * @return List<Unidade>
	 */
	public List<Unidade> findUnidadesFilhas(Short idUnidadeVinculadora, Short perfil) {
		return unidadeDAO.getUnidadesFilhas(idUnidadeVinculadora, perfil);
	}
	
	@Transactional
	public List<Unidade> getAll() throws DAOException {
		return unidadeDAO.getAllOrderByNuUnidade();
	}
	
	@Transactional
	public List<Unidade> getLikeNuUnidade(String nuUnidade) {
		Short nuPerfil = SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
		Short nuUnidadeLogado = SegurancaUsuario.getInstance().getUsuario().getUnidade();
		List<Short> listUnidadesPermitidas = matrizAbrangenciaBO.getListAbrangenciaUnidades(nuPerfil, nuUnidadeLogado);
		List<Unidade> list = unidadeDAO.getLikeNuUnidade(retiraZeroEsquerda(nuUnidade), listUnidadesPermitidas);
		return list;
	}
	
	 /**
	 * Retira os zeros a esquerda da String
	 * @param value
	 * @return
	 */
	private String retiraZeroEsquerda(String value){
		if(value == null || value.equals("null") || value.equals("")){
			return "";
		}
		Integer retorno = Integer.parseInt(value);
		if(retorno==0){
			return "";
		}
		return retorno.toString();
	}
	
	@Transactional
	public Integer getNuNaturalUnidade(Short nuUnidade) {
		if(nuUnidade != null) {
			return unidadeDAO.getNuNaturalUnidade(nuUnidade);
		}
		
		return null;
	}
	
	@Transactional
	public String getNomeUnidadeSuperiorPrimeiroNivel(Short nuUnidade)
	{
		return unidadeDAO.getNomeUnidadeSuperiorPrimeiroNivel(nuUnidade);
	}
	
	@Transactional
	public String getNomeUnidadeSuperiorSegundoNivel(Short nuUnidade)
	{
		return unidadeDAO.getNomeUnidadeSuperiorSegundoNivel(nuUnidade);
	}
	
	@Transactional
	public String getNomeUnidadeByUnidade(Short nuUnidade)
	{
		return unidadeDAO.getNomeUnidadeByUnidade(nuUnidade);
	}
	
	@Transactional
	public String getEstadoByUnidade(Short nuUnidade)
	{
		return unidadeDAO.getEstadoByUnidade(nuUnidade);
	}
	
	@Transactional
	public String getSiglaTipoUnidadeByUnidade(Short nuUnidade)
	{
		return unidadeDAO.getSiglaUnidadeByUnidade(nuUnidade);
	}
	
	@Transactional
	public String getSiglaNomeUnidadeByEmpregado(Integer nuMatricula)
	{
		return unidadeDAO.getSiglaNomeUnidadeByEmpregado(nuMatricula);		
	}
	
	@Transactional
	public Unidade getUnidadeByEmpregado(String coResponsavel) {
		return unidadeDAO.getUnidadeByEmpregado(coResponsavel);
	}
	
}
