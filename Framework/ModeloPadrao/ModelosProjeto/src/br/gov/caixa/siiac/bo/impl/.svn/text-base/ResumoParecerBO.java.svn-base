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

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IEfetuaVerificacaoBO;
import br.gov.caixa.siiac.bo.IResumoParecerBO;
import br.gov.caixa.siiac.model.ResumoParecerVO;
import br.gov.caixa.siiac.model.TagListaItemDataValidadeVO;
import br.gov.caixa.siiac.persistence.dao.IParecerDAO;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class ResumoParecerBO implements IResumoParecerBO {
	
	private IEfetuaVerificacaoBO efetuaVerificacaoBO;
	private IParecerDAO parecerDAO;

	
	private static final String PARECER_INCONFORME = "INCONFORME";
	private static final String PARECER_CONFORME = "CONFORME";
	
	@Autowired
	public void setEfetuaVerificacaoBO(IEfetuaVerificacaoBO efetuaVerificacaoBO){
		this.efetuaVerificacaoBO = efetuaVerificacaoBO;
	}
	
	@Autowired
	public void setParecerDAO(IParecerDAO parecerDAO){
		this.parecerDAO = parecerDAO;
	}
	
	@Transactional
	public String getResumoParecer(Integer nuVerificacaoContrato){
		StringBuilder retorno = new StringBuilder();		
		
		/*
		 * Apresenta resultado do parecer (CONFORME ou INCONFORME). 
		 */
		Boolean isConforme = this.efetuaVerificacaoBO.isConforme(nuVerificacaoContrato); 
		retorno.append("<h2>");
		retorno.append("<strong>");
		retorno.append("O parecer será emitido ");
		retorno.append(isConforme ? PARECER_CONFORME : PARECER_INCONFORME);
		retorno.append(".");
		retorno.append("</strong>");
		retorno.append("</h2>");

		/*
		 * Apresenta o resumo do parecer - INCONFORMIDADES
		 * Só apresenta essa lista se o parecer for INCONFORME
		 */
		if(! isConforme){
			retorno.append(getResumoPreviaParecerInconformidadesSemParecer(nuVerificacaoContrato));
		}
		
		retorno.append(getResumoPreviaParecerConformidadesSemParecer(nuVerificacaoContrato));
		
		
		return retorno.toString();
	}
	
	public String getResumoPreviaParecerConformidades(List<ResumoParecerVO> conformidades, Boolean emitirParecer) {
		StringBuilder retorno = new StringBuilder();
		if(conformidades == null || conformidades.isEmpty()){
			return "";
		}
		
		// Usada para controlar quantos ul e li devem ser fechados ao final do for. 
		// 1 se a última coisa adicionada foi bloco, 2 para item e 3 para apontamento 
		Integer lastNivel = 0; 
		
		Integer idBlocoAnterior = null;
		Integer idItemAnterior = null;
		Integer idApontamentoAnterior = null;
		boolean possuiObsItem = false;
		if (!emitirParecer)
		{
			retorno.append("<ul style='list-style-type: disc;'>");
			for(ResumoParecerVO vo : conformidades) {
				if(! vo.getIdBloco().equals(idBlocoAnterior)) {
					if(Utilities.notEmpty(idBlocoAnterior)) {
						if(lastNivel.equals(3)){
							retorno.append("</ul>");
							retorno.append("</li>");
						}

						if (possuiObsItem)
						{
							retorno.append("</li>");
							retorno.append("</ul>");
							retorno.append("</li>");
						}
						
						if (Utilities.empty(idApontamentoAnterior) && Utilities.empty(idItemAnterior))
						{
							retorno.append("</li>");
						}
					}
					retorno.append("<li>");
					retorno.append("<span>");
					retorno.append(vo.getNoBloco());
					retorno.append("</span>");
					if(Utilities.notEmpty(vo.getObsBloco())){
						retorno.append("<br/>");
						retorno.append("<i>");
						retorno.append("Obs: ");
						retorno.append(vo.getObsBloco());
						retorno.append("</i>");
					}
					idItemAnterior = null;
					lastNivel = 1;
				}
				
				if(Utilities.notEmpty(vo.getObsItem()) || Utilities.notEmpty(vo.getObsApontameto())){
					if(! vo.getIdItem().equals(idItemAnterior)){
						if(Utilities.notEmpty(idItemAnterior) && vo.getIdBloco().equals(idBlocoAnterior)) {
							retorno.append("</ul>");
							retorno.append("</li>");
						}else if(Utilities.empty(idItemAnterior)){
							retorno.append("<ul style='list-style-type: circle;'>");
						}
						retorno.append("<li>");
						retorno.append("<span>");
						retorno.append(vo.getNoItem());
						retorno.append("</span>");
						if(Utilities.notEmpty(vo.getObsItem())){
							retorno.append("<br/>");
							retorno.append("<i>");
							retorno.append("Obs: ");
							retorno.append(vo.getObsItem());
							retorno.append("</i>");
						}
						possuiObsItem = true;
						lastNivel = 2;
						idItemAnterior = vo.getIdItem();
						idApontamentoAnterior = null;
					}
					
					if(Utilities.notEmpty(vo.getObsApontameto())){
						if(Utilities.empty(idApontamentoAnterior)){
							retorno.append("<ul style='list-style-type: square;'>");
						} else {
							retorno.append("</li>");
						}
						retorno.append("<li>");
						retorno.append("<span>");
						retorno.append(vo.getNoApontameto());
						retorno.append("</span>");
						if(Utilities.notEmpty(vo.getObsApontameto())){
							retorno.append("<br/>");
							retorno.append("<i>");
							retorno.append("Obs: ");
							retorno.append(vo.getObsApontameto());
							retorno.append("</i>");
						}
						retorno.append("</li>");
	
						lastNivel = 3;
						idApontamentoAnterior = vo.getIdApontamento();
					}
				}
	
				idBlocoAnterior = vo.getIdBloco();
			}
			
			for(int i = 0; i < lastNivel; i++){
				if(! lastNivel.equals(3) || i > 0){
					retorno.append("</li>");
				}
				retorno.append("</ul>");

			}
			
		} else {
			retorno.append("<table>");
			for(ResumoParecerVO vo : conformidades) {
				if(! vo.getIdBloco().equals(idBlocoAnterior)) {
					
					retorno.append("<tr>");
					retorno.append("<td colspan=\"25\">");
					retorno.append("<span>");
					retorno.append("<b>•</b> ");
					retorno.append(vo.getNoBloco());
					retorno.append("</span>");
					if(Utilities.notEmpty(vo.getObsBloco())){
						retorno.append("<br/>");
						retorno.append("<i>");
						retorno.append("Obs: ");
						retorno.append(vo.getObsBloco());
						retorno.append("</i>");
					}
					retorno.append("</td>");
					retorno.append("</tr>");

					idItemAnterior = null;
					lastNivel = 1;
				}
				
				if(Utilities.notEmpty(vo.getObsItem()) || Utilities.notEmpty(vo.getObsApontameto())){
					if(! vo.getIdItem().equals(idItemAnterior)){
						
						retorno.append("<tr>");
						retorno.append("<td></td>");
						retorno.append("<td colspan=\"24\">");
						
						retorno.append("<span>");
						retorno.append("<b>-</b> ");
						retorno.append(vo.getNoItem());
						retorno.append("</span>");
						if(Utilities.notEmpty(vo.getObsItem())){
							retorno.append("<br/>");
							retorno.append("<i>");
							retorno.append("Obs: ");
							retorno.append(vo.getObsItem());
							retorno.append("</i>");
						}
						retorno.append("</td>");
						retorno.append("</tr>");
						lastNivel = 2;
						idItemAnterior = vo.getIdItem();
						idApontamentoAnterior = null;
					}
					
					if(Utilities.notEmpty(vo.getObsApontameto())){

						retorno.append("<tr>");
						retorno.append("<td colspan=\"2\"></td>");
						retorno.append("<td colspan=\"23\">");
					
						retorno.append("<span>");
						retorno.append("<b>+</b> ");
						retorno.append(vo.getNoApontameto());
						retorno.append("</span>");
						if(Utilities.notEmpty(vo.getObsApontameto())){
							retorno.append("<br/>");
							retorno.append("<i>");
							retorno.append("Obs: ");
							retorno.append(vo.getObsApontameto());
							retorno.append("</i>");
						}
						retorno.append("</td>");
						retorno.append("</tr>");

						lastNivel = 3;
						idApontamentoAnterior = vo.getIdApontamento();
					}
				}

				idBlocoAnterior = vo.getIdBloco();
			}
			
			retorno.append("</table>");
		}
				
		return retorno.toString();

	}
	
	public String getListaItemDataValidadeHTML(List<TagListaItemDataValidadeVO> conformidades) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder retorno = new StringBuilder();
		if(conformidades == null || conformidades.isEmpty()){
			return "";
		}
		
		// Usada para controlar quantos ul e li devem ser fechados ao final do for. 
		// 1 se a última coisa adicionada foi bloco, 2 para item e 3 para apontamento 
		Integer lastNivel = 0; 
		
		Integer idBlocoAnterior = null;
		Integer idItemAnterior = null;
		Integer idApontamentoAnterior = null;
		boolean possuiObsItem = false;
		
		retorno.append("<table>");
		for(TagListaItemDataValidadeVO vo : conformidades) {
			if(! vo.getIdBloco().equals(idBlocoAnterior)) {
				
				retorno.append("<tr>");
				retorno.append("<td colspan=\"25\">");
				retorno.append("<span>");
				retorno.append("<b>•</b> ");
				retorno.append(vo.getNoBloco());
				retorno.append("</span>");
				if(Utilities.notEmpty(vo.getObsBloco())){
					retorno.append("<br/>");
					retorno.append("<i>");
					retorno.append("Obs: ");
					retorno.append(vo.getObsBloco());
					retorno.append("</i>");
				}
				retorno.append("</td>");
				retorno.append("</tr>");

				idItemAnterior = null;
				lastNivel = 1;
			}
			
			if(! vo.getIdItem().equals(idItemAnterior)){
				
				retorno.append("<tr>");
				retorno.append("<td></td>");
				retorno.append("<td colspan=\"24\">");
				
				retorno.append("<span>");
				retorno.append("<b>-</b> ");
				retorno.append(vo.getNoItem());
				retorno.append(" - VÁLIDO ATÉ ");
				retorno.append(sdf.format(vo.getDtValidade()));
				retorno.append("</span>");
				if(Utilities.notEmpty(vo.getObsItem())){
					retorno.append("<br/>");
					retorno.append("<i>");
					retorno.append("Obs: ");
					retorno.append(vo.getObsItem());
					retorno.append("</i>");
				}
				retorno.append("</td>");
				retorno.append("</tr>");
				lastNivel = 2;
				idItemAnterior = vo.getIdItem();
				idApontamentoAnterior = null;
			}
				

			idBlocoAnterior = vo.getIdBloco();
		}
		
		retorno.append("</table>");
			
		return retorno.toString();

	}

	public String getResumoPreviaParecerInconformidades(List<ResumoParecerVO> inconformidades, Boolean emitirParecer) {	
		StringBuilder retorno = new StringBuilder();
		if(inconformidades == null || inconformidades.isEmpty()){
			return "";
		}
		
		// Usada para controlar quantos ul e li devem ser fechados ao final do for. 
		// 1 se a última coisa adicionada foi bloco, 2 para item e 3 para apontamento 
		Integer lastNivel = 0; 
		
		Integer idBlocoAnterior = null;
		Integer idItemAnterior = null;
		Integer idApontamentoAnterior = null;
		
		
		if (!emitirParecer)
		{
			retorno.append("<ul style='list-style-type: disc;'>");
			for(ResumoParecerVO vo : inconformidades) {
				if(! vo.getIdBloco().equals(idBlocoAnterior)) {
					if(Utilities.notEmpty(idBlocoAnterior)) {
						if(lastNivel.equals(3)){
							retorno.append("</ul>");
							retorno.append("</li>");
						}
						retorno.append("</ul>");
						retorno.append("</li>");
					}
					retorno.append("<li>");
					retorno.append("<span>");
					retorno.append(vo.getNoBloco());
					retorno.append("</span>");
					if(Utilities.notEmpty(vo.getObsBloco())){
						retorno.append("<br/>");
						retorno.append("<i>");
						retorno.append(vo.getObsBloco());
						retorno.append("</i>");
					}
					retorno.append("<ul style='list-style-type: circle;'>");
					lastNivel = 1;
				}
				
				if(! vo.getIdItem().equals(idItemAnterior)){
					if(Utilities.notEmpty(idItemAnterior) && vo.getIdBloco().equals(idBlocoAnterior)) {
						retorno.append("</ul>");
						retorno.append("</li>");
					}
					retorno.append("<li>");
					retorno.append("<span>");
					retorno.append(vo.getNoItem());
					retorno.append("</span>");
					if(Utilities.notEmpty(vo.getObsItem())){
						retorno.append("<br/>");
						retorno.append("<i>");
						retorno.append(vo.getObsItem());
						retorno.append("</i>");
					}
					retorno.append("<ul style='list-style-type: square;'>");
					lastNivel = 2;
				}
				
				retorno.append("<li>");
				retorno.append("<span>");
				retorno.append(vo.getNoApontameto());
				retorno.append("</span>");
				if(Utilities.notEmpty(vo.getObsApontameto())){
					retorno.append("<br/>");
					retorno.append("<i>");
					retorno.append(vo.getObsApontameto());
					retorno.append("</i>");
				}
				retorno.append("</li>");
				lastNivel = 3;

				idItemAnterior = vo.getIdItem();
				idBlocoAnterior = vo.getIdBloco();
			}

			for(int i = 0; i < lastNivel; i++){
				if(! lastNivel.equals(3) || i > 0){
					retorno.append("</li>");
				}
				retorno.append("</ul>");
			}
			
		} else {
			
			retorno.append("<table>");
			for(ResumoParecerVO vo : inconformidades) {
				if(! vo.getIdBloco().equals(idBlocoAnterior)) {
					
					retorno.append("<tr>");
					retorno.append("<td colspan=\"25\">");
					retorno.append("<span>");
					retorno.append("<b>•</b> ");
					retorno.append(vo.getNoBloco());
					retorno.append("</span>");
					if(Utilities.notEmpty(vo.getObsBloco())){
						retorno.append("<br/>");
						retorno.append("<i>");
						retorno.append("Obs: ");
						retorno.append(vo.getObsBloco());
						retorno.append("</i>");
					}
					retorno.append("</td>");
					retorno.append("</tr>");

					idItemAnterior = null;
					lastNivel = 1;
				}
				

				if(! vo.getIdItem().equals(idItemAnterior)){
					
					retorno.append("<tr>");
					retorno.append("<td></td>");
					retorno.append("<td colspan=\"24\">");
					
					retorno.append("<span>");
					retorno.append("<b>-</b> ");
					retorno.append(vo.getNoItem());
					retorno.append("</span>");
					if(Utilities.notEmpty(vo.getObsItem())){
						retorno.append("<br/>");
						retorno.append("<i>");
						retorno.append("Obs: ");
						retorno.append(vo.getObsItem());
						retorno.append("</i>");
					}
					retorno.append("</td>");
					retorno.append("</tr>");
					lastNivel = 2;
					idItemAnterior = vo.getIdItem();
					idApontamentoAnterior = null;
				}
				
				retorno.append("<tr>");
				retorno.append("<td colspan=\"2\"></td>");
				retorno.append("<td colspan=\"23\">");
			
				retorno.append("<span>");
				retorno.append("<b>+</b> ");
				retorno.append(vo.getNoApontameto());
				retorno.append("</span>");
				if(Utilities.notEmpty(vo.getObsApontameto())){
					retorno.append("<br/>");
					retorno.append("<i>");
					retorno.append("Obs: ");
					retorno.append(vo.getObsApontameto());
					retorno.append("</i>");
				}
				retorno.append("</td>");
				retorno.append("</tr>");

				lastNivel = 3;
				idApontamentoAnterior = vo.getIdApontamento();


				idBlocoAnterior = vo.getIdBloco();
			}
			
			retorno.append("</table>");
		}
		
		return retorno.toString();
	}

	public String getResumoPreviaParecerConformidadesSemParecer(Integer nuVerificacaoContrato) {
		StringBuilder retorno = new StringBuilder();
		List<ResumoParecerVO> conformidades = this.parecerDAO.getResumoAntesParecerVOList(nuVerificacaoContrato, true);

		if(conformidades == null || conformidades.isEmpty()){
			return "";
		}
		
		retorno.append("<fieldset>");


		retorno.append(getResumoPreviaParecerConformidades(conformidades, false));

		retorno.append("</fieldset>");
		return retorno.toString();
	}

	public String getResumoPreviaParecerInconformidadesSemParecer(Integer nuVerificacaoContrato) {
		StringBuilder retorno = new StringBuilder();
		retorno.append("<fieldset>");
		
		List<ResumoParecerVO> inconformidades = this.parecerDAO.getResumoAntesParecerVOList(nuVerificacaoContrato, false);

		retorno.append(getResumoPreviaParecerInconformidades(inconformidades, false));

		retorno.append("</fieldset>");
		return retorno.toString();
	}

	public String getResumoPreviaParecerConformidadesComParecer(Integer nuVerificacaoContrato) {

		StringBuilder retorno = new StringBuilder();
		
		List<ResumoParecerVO> conformidades = this.parecerDAO.getResumoDepoisParecerVOList(nuVerificacaoContrato, true);

		retorno.append(getResumoPreviaParecerConformidades(conformidades, true));
		
		return retorno.toString();
	}

	public String getResumoPreviaParecerInconformidadesComParecer(Integer nuVerificacaoContrato) {
		StringBuilder retorno = new StringBuilder();

		List<ResumoParecerVO> inconformidades = this.parecerDAO.getResumoDepoisParecerVOList(nuVerificacaoContrato, false);

		retorno.append(getResumoPreviaParecerInconformidades(inconformidades, true));

		return retorno.toString();
	}
	
	@Transactional
	public String getResumoPreviaParecerItemDataValidade(Integer nuVerificacaoContrato) {
		StringBuilder retorno = new StringBuilder();
		
		List<TagListaItemDataValidadeVO> itens = this.parecerDAO.getListaItensComDataValidade(nuVerificacaoContrato);
		
		retorno.append(getListaItemDataValidadeHTML(itens));
		
		return retorno.toString();
	}
	
	/* (non-Javadoc)
	 * @see br.gov.caixa.siiac.bo.IParecerBO#getListaItensComDataValidade(java.lang.Integer)
	 */
	public List<TagListaItemDataValidadeVO> getListaItensComDataValidade(Integer nuVerificacaoContrato) {
		return parecerDAO.getListaItensComDataValidade(nuVerificacaoContrato);
	}
}
