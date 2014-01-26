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

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.jasperreports.engine.JRException;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IBlocoChecklistProdutoBO;
import br.gov.caixa.siiac.bo.IChecklistBO;
import br.gov.caixa.siiac.exception.ReportErrorCreateDatasourceException;
import br.gov.caixa.siiac.exception.ReportFinalNullException;
import br.gov.caixa.siiac.exception.ReportInvalidPathException;
import br.gov.caixa.siiac.model.ChecklistVO;
import br.gov.caixa.siiac.model.domain.ApontamentoChecklistProduto;
import br.gov.caixa.siiac.model.domain.BlocoChecklist;
import br.gov.caixa.siiac.model.domain.BlocoChecklistProduto;
import br.gov.caixa.siiac.model.domain.ChecklistServicoVerificacaoProduto;
import br.gov.caixa.siiac.model.domain.ItemVerificacaoChecklistProduto;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.RestricaoApontamento;
import br.gov.caixa.siiac.persistence.dao.IChecklistDAO;
import br.gov.caixa.siiac.persistence.dao.IVinculacoesChecklistDAO;
import br.gov.caixa.siiac.report.RelatorioChecklist;
import br.gov.caixa.siiac.util.FilterBase;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;

@Service()
@Scope("prototype")
public class ChecklistBO implements IChecklistBO {

	private IBlocoChecklistProdutoBO blocoChecklistProdutoBO;
	private IVinculacoesChecklistDAO vinculacoesChecklistDAO;

	
	private transient IChecklistDAO checklistDAO;
	
	@Autowired
	public void setChecklistDAO(IChecklistDAO checklistDAO) {
		this.checklistDAO = checklistDAO;
	}
	
	@Autowired
	public void setBlocoChecklistProdutoBO(IBlocoChecklistProdutoBO blocoChecklistProdutoBO) {
		this.blocoChecklistProdutoBO = blocoChecklistProdutoBO;
	}
	
	@Autowired
	public void setVinculacoesChecklistDAO(IVinculacoesChecklistDAO vinculacoesChecklistDAO) {
		this.vinculacoesChecklistDAO = vinculacoesChecklistDAO;
	}
	
	@Transactional
	public List<ChecklistServicoVerificacaoProduto> filter(FilterBase filtro) throws DAOException {
		
		Short operacao = filtro.getShort("operacao");
		String codProduto = filtro.getString("codProduto");
		String filtroProduto = filtro.getString("filtroProduto");
		String situacao = filtro.getString("situacao");
		Integer codServicoVerificacao = filtro.getInt("codServicoVerificacao");
		Date dataInicio = filtro.getDate("dataInicio");
		Date dataFim = filtro.getDate("dataFim");
		boolean revogado = filtro.getBoolean("revogado");
		Short nuCanal = filtro.getShort("nuCanal");
		
		return checklistDAO.filter(operacao, codProduto, filtroProduto, situacao,
				codServicoVerificacao, dataInicio, dataFim, revogado, nuCanal);
	}

	@Transactional
	public ChecklistServicoVerificacaoProduto findById(Integer id) {
		ChecklistServicoVerificacaoProduto ch = checklistDAO.findById(id);
		Hibernate.initialize(ch.getBlocoChecklistProdutoList());

		List<BlocoChecklistProduto> blocos = ch.getBlocoChecklistProdutoList();
		for(BlocoChecklistProduto bloco : blocos) {
			Hibernate.initialize(bloco.getItemVerificacaoChecklistProdutoList());
			List<ItemVerificacaoChecklistProduto> itens = bloco.getItemVerificacaoChecklistProdutoList();
			for(ItemVerificacaoChecklistProduto item : itens) {
				Hibernate.initialize(item.getApontamentoChecklistProdutoList());
			}
		}
		
		return ch;
	}

	@Transactional
	public List<ChecklistServicoVerificacaoProduto> getAll() {
		return checklistDAO.findAll();
	}

	@Transactional
	public ChecklistServicoVerificacaoProduto merge(
			ChecklistServicoVerificacaoProduto checkListServVerif) throws DAOException{
		
		return checklistDAO.merge(checkListServVerif);
	}

	@Transactional
	public void delete(ChecklistServicoVerificacaoProduto checklist)
			throws DAOException {
		checklistDAO.delete(checklist);
	}
	
	@Transactional
	public List<ChecklistServicoVerificacaoProduto> findByChecklist(ChecklistServicoVerificacaoProduto checklist) throws DAOException {
		return checklistDAO.findByChecklist(checklist);
	}
	
	@Transactional
	public void adicionaBlocoNoChecklist(List<BlocoChecklist> blocos, Integer codBloco, ChecklistServicoVerificacaoProduto checklist) {
		
	}
	
	@Transactional
	public void updateSituacaoChecklist() throws DAOException {
		List<ChecklistServicoVerificacaoProduto> listCheckListAprovado = checklistDAO.findByDataIniSituacao(ChecklistVO.CHKLST_SITUACAO_AUTORIZADO, new Date());
		for (ChecklistServicoVerificacaoProduto checklist : listCheckListAprovado) {
			ChecklistServicoVerificacaoProduto checkListPublicado = checklistDAO.findBySituacaoServicoVerificacaoProduto(ChecklistVO.CHKLST_SITUACAO_PUBLICADO, 
					checklist.getServicoVerificacaoProduto());
			if (checkListPublicado != null) {
				checkListPublicado.setIcRevogado(true);
				checkListPublicado.setDataFim(obtemDataAnteriorA(checklist.getDataInicio()));
				checklistDAO.merge(checkListPublicado);
			}
			checklist.setIcSituacao(ChecklistVO.CHKLST_SITUACAO_PUBLICADO);
			checklistDAO.merge(checklist);
		}
	}
	
	@Transactional
	public void revogarChecklist(Integer id) throws DAOException {
		ChecklistServicoVerificacaoProduto checklist = checklistDAO.findById(id);
		checklist.setIcRevogado(true);
		checklist.setDataFim(new Date());
		checklistDAO.merge(checklist);
	}
	
	@Transactional
	public List<ChecklistServicoVerificacaoProduto> getAllNotIn(Integer cod, String situacao) throws DAOException {
		return checklistDAO.getAllNotIn(cod, situacao.trim().toUpperCase());
	}
	
	@Transactional
	public Integer duplicateChecklist(Integer idChecklist) {
		ChecklistServicoVerificacaoProduto checklistVelho = checklistDAO.findById(idChecklist);
		
		// Criando o novo checklist com os dados do antigo
		ChecklistServicoVerificacaoProduto checklistNovo = new ChecklistServicoVerificacaoProduto();
		checklistNovo.setIcSituacao(checklistVelho.getIcSituacao());
		checklistNovo.setDataFim(checklistVelho.getDataFim());
		checklistNovo.setServicoVerificacaoProduto(checklistVelho.getServicoVerificacaoProduto());
		checklistNovo.setDataInicio(new Date());
		checklistDAO.save(checklistNovo);

		// Revogando o checklist velho
		checklistVelho.setIcRevogado(Boolean.TRUE);
		GregorianCalendar date = new GregorianCalendar();
		date.add(GregorianCalendar.DATE, -1);
		checklistVelho.setDataFim(date.getTime());
		checklistDAO.merge(checklistVelho);
		
		//DuplicateFilhos
		this.duplicateFilhosChecklist(checklistNovo, idChecklist);
		return checklistNovo.getNuChecklistServicoVerificacaoProduto();
	}
	
	@Transactional
	public void duplicateFilhosChecklist(ChecklistServicoVerificacaoProduto checklistNew, Integer idVelho){
		List<BlocoChecklistProduto> blocosVelho = vinculacoesChecklistDAO.getListVinculacaoBloco(idVelho);
		
		for(BlocoChecklistProduto blocoOld : blocosVelho){
			BlocoChecklistProduto blocoNew = new BlocoChecklistProduto();
			blocoNew.setBlocoChecklist(blocoOld.getBlocoChecklist());
			blocoNew.setDescricao(blocoOld.getDescricao());
			blocoNew.setIcDesabilita(blocoOld.getIcDesabilita());
			blocoNew.setIcPrejudicado(blocoOld.getIcPrejudicado());
			blocoNew.setIcObrigatorio(blocoOld.getIcObrigatorio());
			blocoNew.setNumeroDeOrdem(blocoOld.getNumeroDeOrdem());
			
			blocoNew.setChecklistServicoVerificacaoProduto(checklistNew);
			checklistNew.getBlocoChecklistProdutoList().add(blocoNew);
		
			Hibernate.initialize(blocoOld.getItemVerificacaoChecklistProdutoList());
			for(ItemVerificacaoChecklistProduto itemOld : blocoOld.getItemVerificacaoChecklistProdutoList()){
				ItemVerificacaoChecklistProduto itemNew = new ItemVerificacaoChecklistProduto();
				itemNew.setDeItemVerificacaoChecklistProduto(itemOld.getDeItemVerificacaoChecklistProduto());
				itemNew.setItemVerificacao(itemOld.getItemVerificacao());
				itemNew.setNumeroDeOrdem(itemOld.getNumeroDeOrdem());
				itemNew.setIcDesabilitaItem(itemOld.getIcDesabilitaItem());
				
				itemNew.setBlocoChecklistProduto(blocoNew);
				blocoNew.getItemVerificacaoChecklistProdutoList().add(itemNew);
				
				Hibernate.initialize(itemOld.getApontamentoChecklistProdutoList());
				for(ApontamentoChecklistProduto apontamentoOld : itemOld.getApontamentoChecklistProdutoList()){
					ApontamentoChecklistProduto apontamentoNew = new ApontamentoChecklistProduto();
					apontamentoNew.setApontamento(apontamentoOld.getApontamento());
					apontamentoNew.setDescricao(apontamentoOld.getDescricao());
					apontamentoNew.setIcNaoAplica(apontamentoOld.getIcNaoAplica());
					apontamentoNew.setNumeroDeOrdem(apontamentoOld.getNumeroDeOrdem());
					
					apontamentoNew.setItemVerificacaoChecklistProduto(itemNew);
					itemNew.getApontamentoChecklistProdutoList().add(apontamentoNew);
					
					Hibernate.initialize(apontamentoOld.getRestricaoApontamento());
					for(RestricaoApontamento restricaoOld : apontamentoOld.getRestricaoApontamento()){
						RestricaoApontamento restricaoNew = new RestricaoApontamento();
						restricaoNew.setRestricao(restricaoOld.getRestricao());
						
						restricaoNew.setApontamentoChecklistProduto(apontamentoNew);
						apontamentoNew.getRestricaoApontamento().add(restricaoNew);
					}
				}
			}
		}
		
		vinculacoesChecklistDAO.merge(checklistNew);
	}

	@Transactional
	public boolean validaDataInicio(ChecklistServicoVerificacaoProduto checklist) {
		Criteria crit = checklistDAO.getCriteria()
							.add(Restrictions.eq("nuChecklistServicoVerificacaoProduto", checklist.getNuChecklistServicoVerificacaoProduto()))
							.add(Restrictions.eq("dataInicio", checklist.getDataInicio()));
		List result = checklistDAO.findByCriteria(crit);
		return result != null && !result.isEmpty();
	}

	@Transactional
	public void replaceChecklist(ChecklistServicoVerificacaoProduto checklistNew, Integer idVelho) {
		List<BlocoChecklistProduto> blocos = vinculacoesChecklistDAO.getListVinculacaoBloco(checklistNew.getNuChecklistServicoVerificacaoProduto());
		for(BlocoChecklistProduto bloco : blocos){
			vinculacoesChecklistDAO.deleteVinculacaoBloco(bloco);
		}
		duplicateFilhosChecklist(checklistNew, idVelho);
		
	}
	
	@Transactional
	public List<ChecklistServicoVerificacaoProduto> simpleFilter(FilterBase filtro) {
		Criteria c = checklistDAO.getCriteria();
		
		String pesquisa = filtro.getString("pesquisaSimples");
		
		if(Utilities.notEmpty(pesquisa)){
			c.createAlias("servicoVerificacaoProduto", "svp");
			c.createAlias("svp.servicoVerificacao", "sv");
			c.createAlias("svp.produto", "p");
			
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.ilike("sv.noServicoVerificacao", pesquisa, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("p.noProduto", pesquisa, MatchMode.ANYWHERE));
			
			c.add(disjunction);
			
			Pattern patternMask = Pattern.compile("[0-9]{4}[-]?[0-9]{3}");
			if(patternMask.matcher(pesquisa).matches()){
				disjunction.add(Restrictions.eq("p.coProduto", pesquisa.replace("-","")));
			}
		}
		
		
		return checklistDAO.findByCriteria(c);
	}
	
	@Transactional
	public List<ChecklistServicoVerificacaoProduto> simpleFilter() {
		FilterBase filtro = new FilterBase();
		return simpleFilter(filtro);
	}
	
	@Transactional
	public boolean podeAutorizar(ChecklistServicoVerificacaoProduto checklist) {
		List<ChecklistServicoVerificacaoProduto> busca = null;
		boolean situacao = false;
		try {
			busca = checklistDAO.buscaChecklistAutorizadoOuPublicado(checklist);
			if(busca != null) {
				situacao = busca.size() == 1 ? false : true;
			}
		} catch(DAOException ex) {
			LogCEF.error(ex);
		}
		
		return situacao;
	}
	
	@Transactional
	public boolean podePublicar(ChecklistServicoVerificacaoProduto checklist) {
		try {
			return ! checklistDAO.existeChecklistPublicadoDataInicio(checklist);
		} catch(DAOException ex) {
			LogCEF.error(ex);
			return false;
		}
	}
	
	@Transactional
	public void publica(ChecklistServicoVerificacaoProduto checklist) {
		try {
			List<ChecklistServicoVerificacaoProduto> busca = checklistDAO.buscaChecklistPublicado(checklist);
			if(busca.size() == 1) {
				ChecklistServicoVerificacaoProduto chk = busca.get(0);
				chk.setIcRevogado(true);
				chk.setDataFim(obtemDataAnteriorA(checklist.getDataInicio()));
				checklistDAO.merge(chk);
			}
			if(busca.size() < 2) {
				checklist.setIcSituacao(ChecklistVO.CHKLST_SITUACAO_PUBLICADO);
				checklistDAO.merge(checklist);
			}
			checklistDAO.flush();
		} catch(DAOException ex) {
			LogCEF.error(ex);
		}		
	}
	
	/**
	 *  
	 * @param dataAtual
	 * @return uma data com um dia de antecedência em relação
	 * a data atual
	 */
	private Date obtemDataAnteriorA(Date dataAtual) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(dataAtual);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	
	@Transactional
	public void autoriza(ChecklistServicoVerificacaoProduto checklist) {
		checklist.setIcSituacao(ChecklistVO.CHKLST_SITUACAO_AUTORIZADO);
		checklistDAO.merge(checklist);
		checklistDAO.flush();
	}

	public boolean validaChecklistParaAutorizacao(ChecklistServicoVerificacaoProduto checklist) {
		if (checklist.getBlocoChecklistProdutoList().size() < 1) {
			return false;
		} else {
			for (BlocoChecklistProduto b : checklist.getBlocoChecklistProdutoList()) {
				if (b.getItemVerificacaoChecklistProdutoList().size() < 1) {
					return false;
				} else {
					for (ItemVerificacaoChecklistProduto i : b.getItemVerificacaoChecklistProdutoList()) {
						if (i.getApontamentoChecklistProdutoList().size() < 1) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	@Transactional
	public void retornarParaProjeto(Integer idChecklist) {
		ChecklistServicoVerificacaoProduto checklist = this.findById(idChecklist);
		checklist.setIcSituacao(ChecklistVO.CHKLST_SITUACAO_PROJETO);
		checklistDAO.merge(checklist);
		checklistDAO.flush();
	}

	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IChecklistBO#existByProdutoEServicoVerificacao(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public boolean existByProdutoEServicoVerificacao(String coProduto, Integer nuServicoVerificacao, Date date) {
		Criteria crit = checklistDAO.getCriteria();
		crit.createAlias("servicoVerificacaoProduto", "svp");
		crit.createAlias("svp.produto", "p");
		crit.createAlias("svp.servicoVerificacao", "sv");
		
		crit.add(Restrictions.eq("p.coProduto", coProduto));
		crit.add(Restrictions.eq("sv.nuServicoVerificacao", nuServicoVerificacao));
		crit.add(Restrictions.isNotNull("dataInicio"));
		crit.add(Restrictions.le("dataInicio", date));
		crit.add(Restrictions.eq("icSituacao", ChecklistVO.CHKLST_SITUACAO_PUBLICADO));
		crit.add(Restrictions.eq("icRevogado", Boolean.FALSE));
		
		
		List list = this.checklistDAO.findByCriteria(crit);
		return list != null && ! list.isEmpty();  
	}

	@Transactional
	public byte[] geraRelatorio(String caminhoRelatorio, String matricula, Integer nuChecklistServicoVerificacaoProduto) throws DAOException, ReportInvalidPathException, ReportErrorCreateDatasourceException, ReportFinalNullException, JRException {
		Connection conn = checklistDAO.getConnection();
		RelatorioChecklist relatorioChecklist = new RelatorioChecklist();
		return relatorioChecklist.geraRelatorio(caminhoRelatorio, matricula, nuChecklistServicoVerificacaoProduto, conn);
	}

	@Transactional
	public List<ChecklistServicoVerificacaoProduto> getChecklistsByProdutoabrangentes(List<Produto> produtos) {
		return checklistDAO.getChecklistsByProdutoabrangentes(produtos);
	}

	
}