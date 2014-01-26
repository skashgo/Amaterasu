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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IAgendaBO;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IVerificacaoContratoBO;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.persistence.dao.IAgendaDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoDAO;
import br.gov.caixa.siiac.security.IUsuario;

@Service
public class AgendaBO implements IAgendaBO {
	
	private IAgendaDAO agendaDAO;
	private IVerificacaoContratoDAO verificacaoContratoDAO;
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
	private IVerificacaoContratoBO verificacaoContratoBO;
	
	@Autowired
	public void setAgendaDAO(IAgendaDAO agendaDAO) {
		this.agendaDAO = agendaDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoDAO(IVerificacaoContratoDAO verificacaoContratoDAO) {
		this.verificacaoContratoDAO = verificacaoContratoDAO;
	}
	
	@Autowired	
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}

	@Autowired
	public void setVerificacaoContratoBO(IVerificacaoContratoBO verificacaoContratoBO) {
		this.verificacaoContratoBO = verificacaoContratoBO;
	}
	
	@Transactional
	public boolean isVerificacaoParcial(Contrato contrato) {
		return situacaoContrao(contrato).equals(VerificacaoContratoBO.SITUACAO_CONTRATO_VERIFICACAO_VERIFICACAO_PARCIAL);
	}
	

	public String situacaoContrao(Contrato contrato) {
		return verificacaoContratoBO.obtemSituacaoContrato(contrato.getNuContrato());
	}
	
	@Transactional
	public boolean existeAgendaContrato(Integer nuContrato) {
		return this.agendaDAO.existeAgendaContrato(nuContrato);
	}
	
	@Transactional
	public List<Contrato> getListaContratoAgenda(boolean filtrarPreferenciais, IUsuario userLogado, Integer first, Integer rows, Map<String, Integer> order) {
		Short nuUnidade = userLogado.getUnidade();
		Short perfilUsuario = userLogado.getPerfis().get(0); 
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(perfilUsuario, nuUnidade);
		return agendaDAO.getListAgenda(filtrarPreferenciais, userLogado, first, rows, order, listUnidadesAbrangencia);
	}
	
	@Transactional
	public Integer getListaContratoAgendaCount(boolean filtrarPreferenciais, IUsuario userLogado) {
		Short nuUnidade = userLogado.getUnidade();
		Short perfilUsuario = userLogado.getPerfis().get(0); 
		List<Short> listUnidadesAbrangencia = matrizAbrangenciaBO.getListAbrangenciaUnidades(perfilUsuario, nuUnidade);
		return agendaDAO.getListAgendaCount(filtrarPreferenciais, userLogado, listUnidadesAbrangencia);
	}
	
	@Transactional
	public boolean hasVerificacaoContratoAVerificar(Contrato contrato) {
		List<VerificacaoContratoVO> listVO = verificacaoContratoDAO.listVerificacaoByContrato(contrato);
		if (listVO.size() > 0) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public VerificacaoContratoVO hasOnlyOneVerificacaoContratoAVerificar(Contrato contrato) {
		List<VerificacaoContratoVO> listVO = verificacaoContratoDAO.listVerificacaoByContrato(contrato);
		if (listVO.size() == 1) {
			return listVO.get(0);
		}
		return null;
	}
}