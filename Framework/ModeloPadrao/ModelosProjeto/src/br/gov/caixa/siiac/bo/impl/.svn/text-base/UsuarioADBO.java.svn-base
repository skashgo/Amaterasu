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
import java.util.regex.Pattern;

import javax.naming.NamingException;

import org.jboss.logging.appender.RegexEventEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.IUsuarioADBO;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.security.ActiveDirectoryManager;
import br.gov.caixa.util.Utilities;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class UsuarioADBO implements IUsuarioADBO {
	private transient IUnidadeBO unidadeBO;
	
	@Autowired
	public void setUnidadeBO(IUnidadeBO unidadeBO) {
		this.unidadeBO = unidadeBO;
	}

	public boolean existeUsuarioAD(String matricula) throws SIIACException {
		try{
			return Utilities.notEmpty(ActiveDirectoryManager.getInstance().getUserBasicAttributes(matricula, ActiveDirectoryManager.UID));
		} catch(NamingException e){
			throw new SIIACException("MN004");
		}
	}

	public String getNomeUsuario(String matricula) throws SIIACException {
		try{
			return ActiveDirectoryManager.getInstance().getUserBasicAttributes(matricula, ActiveDirectoryManager.DISPLAY_NAME);
		} catch(NamingException e){
			throw new SIIACException("MN004");
		}
	}
	
	public Short getNuUnidade(String matricula) throws SIIACException {
		try{

			// Recupera a unidade física do AD (extensionAttribute10). Caso for vazia, busca a unidade lógica (extensionAttribute1).
			String strUnidade = ActiveDirectoryManager.getInstance().getUserBasicAttributes(matricula, ActiveDirectoryManager.EXTENSION_ATTRIBUTE_10);
			if(strUnidade == null || strUnidade.equals("") || strUnidade.length() <= 5){
				strUnidade = ActiveDirectoryManager.getInstance().getUserBasicAttributes(matricula, ActiveDirectoryManager.EXTENSION_ATTRIBUTE_1);
			}
			Short retorno = Short.valueOf(strUnidade.substring(5));
			
			Assert.isTrue(retorno != 0);
			return retorno;
		} catch(Exception e){
			throw new SIIACException("XX025");
		}
	}

	public boolean verificaUnidade(String matricula, List<Unidade> vinculadas) {
		try{
			if(!existeUsuarioAD(matricula)){
				return false;
			}
			
			Unidade unidade = unidadeBO.getUnidade(getNuUnidade(matricula));
			return vinculadas.contains(unidade);
		} catch (SIIACException e){
			return false;
		}
	}

	public boolean existeUsuarioUnidade(String nuUnidade) {
		try {
			Unidade unidade = unidadeBO.getUnidade(Short.valueOf(nuUnidade));
			String concatUni = unidade.getSgUfL22() + " - " + ConvertUtils.padZerosLeft(unidade.getId().getNuUnidade().toString(), 4);//Ex: SP - 0001;
			return ActiveDirectoryManager.getInstance().existUser(ActiveDirectoryManager.EXTENSION_ATTRIBUTE_1, concatUni);
		} catch (NamingException e) {
			return false;
		}
	}
	
	public String getMatriculaFormatada(String value) throws SIIACException {
		if(value == null || value.length() > 7) throw new SIIACException("Matrícula maior do que permitido.");
		
		Pattern patternLetters = Pattern.compile("[a-zA-Z]");
		Pattern patternNumbers = Pattern.compile("[0-9]*");
		if(!patternLetters.matcher(value.substring(0,1)).matches()
				|| !patternNumbers.matcher(value.substring(1)).matches()){
			throw new SIIACException("Matrícula inválida.");
		}
		
		return value.substring(0,1) + ConvertUtils.padZerosLeft(value.substring(1), 6);		
	}
}
