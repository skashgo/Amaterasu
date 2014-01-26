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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.VerificacaoContratoVO;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.Contrato;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProduto;
import br.gov.caixa.siiac.model.domain.ResultadoApontamentoProdutoParecer;
import br.gov.caixa.siiac.model.domain.ServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.VerificacaoContrato;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoObservacoes;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoObservacoesParecer;
import br.gov.caixa.siiac.model.domain.VerificacaoContratoParecer;
import br.gov.caixa.siiac.persistence.dao.IApontamentoChecklistProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IBlocoChecklistProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IChecklistDAO;
import br.gov.caixa.siiac.persistence.dao.IEfetuaVerificacaoDAO;
import br.gov.caixa.siiac.persistence.dao.IItemVerificacaoChecklistProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IResultadoApontamentoProdutoDAO;
import br.gov.caixa.siiac.persistence.dao.IResultadoApontamentoProdutoParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoObservacaoDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoObservacaoParecerDAO;
import br.gov.caixa.siiac.persistence.dao.IVerificacaoContratoParecerDAO;
import br.gov.caixa.util.Utilities;

@Service
public class EfetuaVerificacaoBO implements IEfetuaVerificacaoBO {
	
	private IEfetuaVerificacaoDAO efetuaVerificacaoDAO;
	private IVerificacaoContratoObservacaoParecerDAO verificacaoContratoObservacaoParecerDAO;
	private IVerificacaoContratoObservacaoDAO verificacaoContratoObservacaoDAO;
	private IResultadoApontamentoProdutoParecerDAO resultadoApontamentoProdutoParecerDAO;
	private IResultadoApontamentoProdutoDAO resultadoApontamentoProdutoDAO;
	private IApontamentoChecklistProdutoDAO apontamentoChecklistProdutoDAO;
	private IBlocoChecklistProdutoDAO blocoChecklistProdutoDAO;
	private IVerificacaoContratoDAO verificacaoContratoDAO;
	private IItemVerificacaoChecklistProdutoDAO itemVerificacaoChecklistProdutoDAO;
	private IVerificacaoContratoParecerDAO verificacaoContratoParecerDAO;
	private IChecklistDAO checklistDAO;
	
	@Autowired
	public void setEfetuaVerificacaoDAO(IEfetuaVerificacaoDAO efetuaVerificacaoDAO) {
		this.efetuaVerificacaoDAO = efetuaVerificacaoDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoObservacaoDAO(IVerificacaoContratoObservacaoDAO verificacaoContratoObservacaoDAO) {
		this.verificacaoContratoObservacaoDAO = verificacaoContratoObservacaoDAO;
	}
	
	@Autowired
	public void setResultadoApontamentoProdutoDAO(IResultadoApontamentoProdutoDAO resultadoApontamentoProdutoDAO) {
		this.resultadoApontamentoProdutoDAO = resultadoApontamentoProdutoDAO;
	}
	
	@Autowired
	public void setApontamentoChecklistProdutoDAO(IApontamentoChecklistProdutoDAO apontamentoChecklistProdutoDAO) {
		this.apontamentoChecklistProdutoDAO = apontamentoChecklistProdutoDAO;
	}
	
	@Autowired
	public void setBlocoChecklistProdutoDAO(IBlocoChecklistProdutoDAO blocoChecklistProdutoDAO) {
		this.blocoChecklistProdutoDAO = blocoChecklistProdutoDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoDAO(IVerificacaoContratoDAO verificacaoContratoDAO) {
		this.verificacaoContratoDAO = verificacaoContratoDAO;
	}
	
	@Autowired
	public void setItemVerificacaoChecklistProdutoDAO(IItemVerificacaoChecklistProdutoDAO itemVerificacaoChecklistProdutoDAO) {
		this.itemVerificacaoChecklistProdutoDAO = itemVerificacaoChecklistProdutoDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoParecerDAO(IVerificacaoContratoParecerDAO verificacaoContratoParecerDAO) {
		this.verificacaoContratoParecerDAO = verificacaoContratoParecerDAO;
	}
	
	@Autowired
	public void setVerificacaoContratoObservacaoParecerDAO(IVerificacaoContratoObservacaoParecerDAO verificacaoContratoObservacaoParecerDAO) {
		this.verificacaoContratoObservacaoParecerDAO = verificacaoContratoObservacaoParecerDAO;
	}
	
	@Autowired
	public void setResultadoApontamentoProdutoParecerDAO(IResultadoApontamentoProdutoParecerDAO resultadoApontamentoProdutoParecerDAO) {
		this.resultadoApontamentoProdutoParecerDAO = resultadoApontamentoProdutoParecerDAO;
	}
	
	@Autowired
	public void setChecklistDAO(IChecklistDAO checklistDAO) {
		this.checklistDAO = checklistDAO;
	}
	
	@Transactional
	public ChecklistServicoVerificacaoProduto getMontaArvoreChecklist(VerificacaoContratoVO verificacaoContratoVO) {
		ChecklistServicoVerificacaoProduto arvore = efetuaVerificacaoDAO.getMontaArvoreChecklist(verificacaoContratoVO);
		if(arvore == null) {
			arvore = checklistByNuServico(verificacaoContratoVO.getNuServicoVerificacaoProduto());
		}
		if (arvore != null) {
			Hibernate.initialize(arvore.getBlocoChecklistProdutoList());
			for (BlocoChecklistProduto b : arvore.getBlocoChecklistProdutoList()) {
				Hibernate.initialize(b.getItemVerificacaoChecklistProdutoList());
				Hibernate.initialize(b.getVerificacaoContratoObservacoesList());
				for (ItemVerificacaoChecklistProduto iv : b.getItemVerificacaoChecklistProdutoList()) {
					Hibernate.initialize(iv.getApontamentoChecklistProdutoList());
					for(ApontamentoChecklistProduto ap : iv.getApontamentoChecklistProdutoList()){
						Hibernate.initialize(ap.getResultadoApontamentoProdutoList());
					}
				}
			}
		}
		return arvore;
	}
	
	@Transactional
	public VerificacaoContratoObservacoes getVerificacaoContratoObservacoes(Integer id, String observacaoFonte, VerificacaoContratoVO vc) {
		VerificacaoContratoObservacoes v = null;
		if (vc.getIcEstadoVerificacao().equals(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_INCONFORME)) {
			VerificacaoContratoObservacoesParecer vp = new VerificacaoContratoObservacoesParecer();
			if (observacaoFonte.equals(BLOCO)) {
				vp = verificacaoContratoObservacaoParecerDAO.findById_Fonte(id, VerificacaoContratoObservacoes.FONTE_OBSERVACAO_APONTAMENTO_ID_BLOCO, vc);
			}
			if (observacaoFonte.equals(ITEM)) {
				vp = verificacaoContratoObservacaoParecerDAO.findById_Fonte(id, VerificacaoContratoObservacoes.FONTE_OBSERVACAO_APONTAMENTO_ID_ITEM, vc);
			}
			if (vp != null) {
				v = clone(vp);
			}
		} else {
			if (observacaoFonte.equals(BLOCO)) {
				v = verificacaoContratoObservacaoDAO.findById_Fonte(id, VerificacaoContratoObservacoes.FONTE_OBSERVACAO_APONTAMENTO_ID_BLOCO, vc.getNuVerificacaoContrato());
			}
			if (observacaoFonte.equals(ITEM)) {
				v = verificacaoContratoObservacaoDAO.findById_Fonte(id, VerificacaoContratoObservacoes.FONTE_OBSERVACAO_APONTAMENTO_ID_ITEM, vc.getNuVerificacaoContrato());
			}
		}
		return v;
	}
	
	private VerificacaoContratoObservacoes clone(VerificacaoContratoObservacoesParecer vp) {
		if (vp == null) {
			return null;
		}
		VerificacaoContratoObservacoes vco = new VerificacaoContratoObservacoes();
		if(Utilities.notEmpty(vp.getNuBlocoChecklistServicoVerificacaoProduto())){
			BlocoChecklistProduto blocoChecklistProduto = new BlocoChecklistProduto();
			blocoChecklistProduto.setNuBlocoChecklistProduto(vp.getNuBlocoChecklistServicoVerificacaoProduto());
			vco.setBlocoChecklistProduto(blocoChecklistProduto);
		}
		vco.setDeObservacao(vp.getDeObservacao());
		vco.setIcConforme(vp.getIcConforme());
		vco.setIcDesabilitado(vp.getIcDesabilitado());
		vco.setIcFonte(vp.getIcFonte());
		vco.setDtValidade(vp.getDtValidade());
		if(Utilities.notEmpty(vp.getNuItemChecklistServicoVerificacaoProduto())){
			ItemVerificacaoChecklistProduto itemVerificacaoChecklistProduto = new ItemVerificacaoChecklistProduto();
			itemVerificacaoChecklistProduto.setNuItemVerificacaoChecklistProduto(vp.getNuItemChecklistServicoVerificacaoProduto());
			vco.setItemVerificacaoChecklistProduto(itemVerificacaoChecklistProduto);
		}
		VerificacaoContrato verificacaoContrato = new VerificacaoContrato();
		verificacaoContrato.setNuVerificacaoContrato(vp.getNuVerificacaoContrato());
		vco.setVerificacaoContrato(verificacaoContrato);
		return vco;
	}
	
	@Transactional
	public Map<Integer, ResultadoApontamentoProduto> getResultadoApontamentoProduto(VerificacaoContrato verificacaoContrato, VerificacaoContratoVO verificacaoContratoVO) {
		Map<Integer, ResultadoApontamentoProduto> listResultadoAP = new HashMap<Integer, ResultadoApontamentoProduto>();
		if (verificacaoContratoVO.getIcEstadoVerificacao().equals(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_INCONFORME)) {
			List<ResultadoApontamentoProdutoParecer> rapp = resultadoApontamentoProdutoParecerDAO.findByIdApontamentoChecklistProduto(verificacaoContrato, verificacaoContratoVO);
			listResultadoAP = clone(rapp);
		} else {
			List<ResultadoApontamentoProduto> rap = resultadoApontamentoProdutoDAO.findByIdApontamentoChecklistProduto(verificacaoContrato);
			for (ResultadoApontamentoProduto resultadoApontamentoProduto : rap) {
				listResultadoAP.put(resultadoApontamentoProduto.getApontamentoChecklistProduto().getNuApontamentoChecklistProduto(), resultadoApontamentoProduto);
			}
		}
		if (listResultadoAP == null) {
			listResultadoAP = new HashMap<Integer, ResultadoApontamentoProduto>();
		}
		return listResultadoAP;
	}
	
	private Map<Integer, ResultadoApontamentoProduto> clone(List<ResultadoApontamentoProdutoParecer> listRap) {
		if (listRap == null) {
			return null;
		}
		Map<Integer, ResultadoApontamentoProduto> lrap = new HashMap<Integer, ResultadoApontamentoProduto>();
		for (ResultadoApontamentoProdutoParecer resultadoApontamentoProdutoParecer : listRap) {
			ResultadoApontamentoProduto rap = new ResultadoApontamentoProduto();
			ApontamentoChecklistProduto nuAptmntoChklstProduto = new ApontamentoChecklistProduto();
			nuAptmntoChklstProduto.setNuApontamentoChecklistProduto(resultadoApontamentoProdutoParecer.getNuApontamentoChecklistProduto());
			rap.setApontamentoChecklistProduto(nuAptmntoChklstProduto);
			Contrato nuContrato = new Contrato();
			nuContrato.setNuContrato(resultadoApontamentoProdutoParecer.getNuContrato());
			rap.setContrato(nuContrato);
			rap.setCoResponsavelResultado(resultadoApontamentoProdutoParecer.getCoResponsavelResultado());
			rap.setDeObservacao(resultadoApontamentoProdutoParecer.getDeObservacao());
			rap.setIcResultadoApontamentoChecklist(resultadoApontamentoProdutoParecer.getIcResultadoApontamentoChecklist());
			VerificacaoContrato verificacaoContrato = new VerificacaoContrato();
			verificacaoContrato.setNuVerificacaoContrato(resultadoApontamentoProdutoParecer.getVerificacaoContrato().getNuVerificacaoContrato());
			rap.setVerificacaoContrato(verificacaoContrato);
			lrap.put(rap.getApontamentoChecklistProduto().getNuApontamentoChecklistProduto(), rap);
		}
		return lrap;
	}
	
	@Transactional
	public ApontamentoChecklistProduto getApontamentoChecklistProduto(Integer idApontamentoCheclistProduto) {
		ApontamentoChecklistProduto acp = apontamentoChecklistProdutoDAO.findById(idApontamentoCheclistProduto);
		if (acp == null) {
			acp = new ApontamentoChecklistProduto();
		}
		return acp;
	}
	
	@Transactional
	public void saveBlocoItem(VerificacaoContratoObservacoes verificacaoContratoObservacoes) throws SIIACException {
		verificacaoContratoObservacaoDAO.saveOrUpdate(verificacaoContratoObservacoes);
	}
	
	@Transactional
	public void saveApontamento(ResultadoApontamentoProduto resultadoApontamentoProduto) throws SIIACException {
		resultadoApontamentoProdutoDAO.saveOrUpdate(resultadoApontamentoProduto);
	}
	
	@Transactional
	public BlocoChecklistProduto findBlocoChecklistProdutoById(Integer id) {
		return blocoChecklistProdutoDAO.findById(id);
	}
	
	@Transactional
	public VerificacaoContrato findVerificacaoContratoById(Integer id) {
		return verificacaoContratoDAO.findById(id);
	}
	
	@Transactional
	public ChecklistServicoVerificacaoProduto checklistByNuServico(Integer nuServico) {
		return verificacaoContratoDAO.checklistByNuServico(nuServico);
	}
	
	@Transactional
	public boolean existeChecklistByNuServico(Integer nuServico)
	{
		return verificacaoContratoDAO.existeChecklistByNuServico(nuServico);
	}
	
	@Transactional
	public ItemVerificacaoChecklistProduto findItemVerificacaoChecklistProdutoById(Integer id) {
		return itemVerificacaoChecklistProdutoDAO.findById(id);
	}
	
	@Transactional
	public void deleteBlocoItem(VerificacaoContratoObservacoes verificacaoContratoObservacoes) throws SIIACException {
		verificacaoContratoObservacaoDAO.delete(verificacaoContratoObservacoes);
	}
	
	@Transactional
	public VerificacaoContrato clone(VerificacaoContratoVO verificacaoContratoVO) {
		if (verificacaoContratoVO == null) {
			return new VerificacaoContrato();
		}
		VerificacaoContratoParecer vcp = verificacaoContratoParecerDAO.findById(verificacaoContratoVO.getNuVerificacaoContrato());
		VerificacaoContrato vc = new VerificacaoContrato();
		ChecklistServicoVerificacaoProduto checklist = checklistDAO.findById(buscaChecklistAtualImportaVerificacao(verificacaoContratoVO.getNuServicoVerificacaoProduto()));
		vc.setChecklist(checklist);
		verificacaoContratoVO.setNuChecklistServicoVerificacaoProduto(checklist.getNuChecklistServicoVerificacaoProduto());
		Contrato c = new Contrato();
		c.setNuContrato(vcp.getNuContrato());
		vc.setContrato(c);
		vc.setCoRspnlVerificacao(vcp.getCoResponsavelVerificacao());
		vc.setDtInclusaoVerificacao(vcp.getDtInclusaoVerificacao());

		// Passou a ser NULL no dia 16/07/2012
		//vc.setDtLimiteVerificacao(vcp.getDtLimiteVerificacao());
		vc.setDtLimiteVerificacao(null);
		
		vc.setDtVerificacao(vcp.getDtVerificacao());
		vc.setIcEstadoVerificacao(VerificacaoContratoParecer.ESTADO_VERIFICACAO_ID_NAO_VERIFICADA);
		vc.setIcSuspensa(vcp.getIcSuspensa());
		vc.setIcUltimaHierarquia(vcp.getIcUltimaHierarquia());
		vc.setNuNaturalUnidadeResponsavelVerificacao(vcp.getNuNaturalUnidadeResponsavelVerificacao());
		vc.setNuUnidadeResponsavelVerificacao(vcp.getNuUnidadeResponsavelVerificacao());
		vc.setNuVerificacaoContratoPai(vcp.getNuVerificacaoContrato());
		ServicoVerificacaoProduto svp = new ServicoVerificacaoProduto();
		svp.setNuServicoVerificacaoProduto(vcp.getNuServicoVerificacaoProduto());
		vc.setServicoVerificacaoProduto(svp);
		return vc;
	}
	
	@Transactional
	public VerificacaoContrato criaVerificacaoContratoFromVerificacaoContratoParecer(VerificacaoContrato verificacaoContrato, VerificacaoContratoVO verificacaoContratoVO) {
		VerificacaoContratoParecer vcp = verificacaoContratoParecerDAO.findById(verificacaoContratoVO.getNuVerificacaoContrato());
		vcp.setIcUltimaHierarquia(false);
		verificacaoContratoParecerDAO.merge(vcp);
		verificacaoContratoDAO.saveOrUpdate(verificacaoContrato);
		
		return verificacaoContrato;
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO#validaParaParecer(java.lang.Integer)
	 */
	@Transactional
	public String validaParaParecer(Integer nuVerificacaoContrato) {
		Object[] validacoes = this.efetuaVerificacaoDAO.validaParaParecer(nuVerificacaoContrato);
		if(validacoes != null && validacoes.length == 4){
			//Caso 0002360 do Mantis CEF-SIIAC. Deve verificar se existe template apenas para o 
			//tipo em que se está emitindo o parecer.
			Boolean isConforme = this.isConforme(nuVerificacaoContrato);
			
			if (isConforme)
			{
				if(Long.parseLong(String.valueOf(validacoes[2])) <= 0){
					return "3";
				}
			} else {
				if(Long.parseLong(String.valueOf(validacoes[3])) <= 0){
					return "4";
				}
			}
			
			// Essa validação deve ser a última, pois ela só serve para fazer uma pergunta para o usuário, ou seja, 
			// pede confirmação dele para realizar uma ação. Diferente das outras que apresentam mensagem de erro bloqueando o usuário de continuar.
			if(Long.parseLong(String.valueOf(validacoes[1])) > 0){
				return "2";
			}
		}
		return "0";
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO#isConforme(java.lang.Integer)
	 */
	@Transactional
	public Boolean isConforme(Integer nuVerificacaoContrato) {
		return this.efetuaVerificacaoDAO.isConforme(nuVerificacaoContrato);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO#setNaoVerificadoToConforme(java.lang.Integer)
	 */
	@Transactional
	public void setNaoVerificadoToConforme(Integer nuVerificacaoContrato) {
		this.efetuaVerificacaoDAO.setApontamentoNaoVerificadoToConforme(nuVerificacaoContrato);
		this.efetuaVerificacaoDAO.insertItemConformeTabelaObservacoes(nuVerificacaoContrato);
		this.efetuaVerificacaoDAO.updateItemConformeTabelaObservacoes(nuVerificacaoContrato);
	}
	
	@Transactional
	public void iniciaVerificacaoContratoObservacoes (BlocoChecklistProduto blocoChecklistProduto)
	{
		Hibernate.initialize(blocoChecklistProduto.getVerificacaoContratoObservacoesList());
	}
	
	@Transactional
    public Integer validaDataItemImportaVerificacao(Integer nuVerificacaoContrato)
	{
		return this.efetuaVerificacaoDAO.validaDataItemImportaVerificacao(nuVerificacaoContrato);
	}
	
	@Transactional
	public Integer buscaChecklistAtualImportaVerificacao(Integer nuVerificacaoProduto)
	{
		return this.efetuaVerificacaoDAO.validaCheckListImportaVerificacao(nuVerificacaoProduto);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO#saveVerificacaoContratoCabecalho(br.gov.caixa.siiac.model.domain.VerificacaoContrato)
	 */
	@Transactional
	public void saveVerificacaoContratoCabecalho(VerificacaoContrato verificacaoContrato) {
		this.verificacaoContratoDAO.saveOrUpdate(verificacaoContrato);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO#buscaChecklistVerificacaoAntiga(java.lang.Integer)
	 */
	@Transactional
	public Integer buscaChecklistVerificacaoAntiga(Integer nuVerificacaoContrato) {
		Criteria crit = verificacaoContratoParecerDAO.getCriteria();
		crit.add(Restrictions.eq("nuVerificacaoContrato", nuVerificacaoContrato));
		crit.setProjection(Projections.property("nuChecklistServicoVerificacaoProduto"));
		List list = verificacaoContratoParecerDAO.findByCriteria(crit);
		if(list != null && ! list.isEmpty()){
			return (Integer) list.get(0);
		}
		return 0;
	}
	
	public static final String ITEM = "2";
	public static final String BLOCO = "1";

	@Transactional
	public List validaDataObrigatoria(Integer nuVerificacaoContrato) {
		return this.efetuaVerificacaoDAO.validaDataObrigatoria(nuVerificacaoContrato);
	}

	@Transactional
	public boolean isVerificacaoPrejudicada(Integer nuBlocoChecklistProduto, Integer nuVerificacaoContrato) {
		return efetuaVerificacaoDAO.isVerificacaoPrejudicada(nuBlocoChecklistProduto, nuVerificacaoContrato);
	}

	@Transactional
	public boolean isVerificacaoParecerPrejudicada(Integer nuVerificacaoContrato) {
		return efetuaVerificacaoDAO.isVerificacaoParecerPrejudicada(nuVerificacaoContrato);
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO#buscaPrimeiroBlocoPrejudicado(java.lang.Integer)
	 */
	@Transactional
	public Integer buscaPrimeiroBlocoPrejudicado(Integer nuVerificacaoContrato) {
		return this.efetuaVerificacaoDAO.buscaPrimeiroBlocoPrejudicado(nuVerificacaoContrato);
	}
}