package br.gov.caixa.siiac.bo.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IGarantiaBO;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.Garantia;
import br.gov.caixa.siiac.persistence.dao.IGarantiaDAO;

@Service
public class GarantiaBO implements IGarantiaBO {
	
	private IGarantiaDAO garantiaDAO;
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	
	@Transactional
	public List<Garantia> getListFiltro(Garantia garantia, Boolean inclusiveExcluida, BigDecimal[] valor) {
		String matricula = SegurancaUsuario.getInstance().getUsuario().getMatricula();
		Short nuUnidade = SegurancaUsuario.getInstance().getUsuario().getUnidade();
		Short perfilUsuario = SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
		List<String> listCoProdutoAbrangencia = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, perfilUsuario);
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(perfilUsuario, nuUnidade);
		
		List<Garantia> list = garantiaDAO.getListFiltro(garantia, inclusiveExcluida, valor, listUnidadesAbrangencia, listCoProdutoAbrangencia);
		return list;
	}
	
	@Transactional
	public List<Garantia> getListFiltroSimples(String pesquisaString, Boolean pesquisaMostraInativos) {
		String matricula = SegurancaUsuario.getInstance().getUsuario().getMatricula();
		Short nuUnidade = SegurancaUsuario.getInstance().getUsuario().getUnidade();
		Short perfilUsuario = SegurancaUsuario.getInstance().getUsuario().getPerfis().get(0);
		List<String> listCoProdutoAbrangencia = matrizAbrangenciaBO.getListAbrangenciaProduto(matricula, perfilUsuario);
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(perfilUsuario, nuUnidade);
		
		List<Garantia> list = garantiaDAO.getListFiltroSimples(pesquisaString, pesquisaMostraInativos, listUnidadesAbrangencia, listCoProdutoAbrangencia);
		return list;
	}
	
	@Transactional
	public Garantia save(Garantia garantia) {
		return garantiaDAO.merge(garantia);
	}
	
	@Transactional
	public void inativar(Garantia garantia) {
		garantia.setIcAtivo(false);
		garantiaDAO.inativar(garantia);
	}
	
	@Autowired
	public void setGarantiaDAO(IGarantiaDAO garantiaDAO) {
		this.garantiaDAO = garantiaDAO;
	}
	
	@Autowired
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}
	
	@Transactional
	public boolean existGarantia(Integer coGarantia, Integer nuGarantia) {
		return garantiaDAO.existGarantia(coGarantia, nuGarantia);
	}
	
	@Transactional
	public List<Garantia> getAllGarantiasInContrato(Contrato contrato) throws DAOException {
		return garantiaDAO.getAllGarantiasInContrato(contrato);
	}
	
	@Transactional
	public Garantia findById(Integer nuGarantia){
		return garantiaDAO.findById(nuGarantia);
	}
}