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
package br.gov.caixa.siiac.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.TrilhaHistorico;
import br.gov.caixa.siiac.util.annotation.TrailClass;
import br.gov.caixa.siiac.util.annotation.TrailLog;

/**
 * @author GIS Consult
 *
 */
@Service
@Scope("prototype")
public class TrilhaHistoricoUtil {
	private static final String ACAO_INCLUSAO = "INCLUSAO";
	private static final String ACAO_ALTERACAO = "ALTERACAO";
	private static final String ACAO_EXCLUSAO = "EXCLUSAO";
	
	private static TrilhaHistoricoUtil i = null;
	
	private TrilhaHistoricoUtil() {
	}
	
	public static TrilhaHistoricoUtil i() {
		if (i == null) {
			i = new TrilhaHistoricoUtil();
		}
		i.listClass.clear();
		return i;
	}
	
	private List<Class> listClass = new ArrayList<Class>();
	
	public boolean hasTrilha(Object object) {
		if (object == null) {
			return false;
		}
		return object.getClass().isAnnotationPresent(TrailClass.class);
	}
	
	public TrilhaHistorico getTrilhaForSave(Object objeto) throws SIIACException {
		if (objeto == null) {
			throw new SIIACException("Parametro nulo para getTrilhaForSave");
		}
		if(!hasTrilha(objeto)){
			return null;
		}
		try {
			return getTrilha(objeto, ACAO_INCLUSAO);
		} catch (Exception e) {
			throw new SIIACException(e.getMessage());
		}
	}
	
	public TrilhaHistorico getTrilhaForDelete(Object objeto) throws SIIACException {
		if (objeto == null) {
			throw new SIIACException("Parametro nulo para getTrilhaForSave");
		}
		if(!hasTrilha(objeto)){
			return null;
		}
		try {
			return getTrilha(objeto, ACAO_EXCLUSAO);
		} catch (Exception e) {
			throw new SIIACException(e.getMessage());
		}
	}
	
	public TrilhaHistorico getTrilhaForMerge(Object objetoAntes, Object objetoDepois) throws SIIACException {
		if(!hasTrilha(objetoDepois)){
			return null;
		}
		try {
			CamposAlterados camposAlterados = camposAlterados(objetoAntes, objetoDepois);
			return getTrilha(objetoDepois, ACAO_ALTERACAO, camposAlterados.antes, camposAlterados.depois);
		} catch (Exception e) {
			throw new SIIACException(e.getMessage());
		}
	}
	
	private TrilhaHistorico getTrilha(Object objeto, String acao) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return getTrilha(objeto, acao, "", "");
	}
	
	private TrilhaHistorico getTrilha(Object objeto, String acao, String alteracaoAntes, String alteracaoDepois) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String strIP = VariableScope.i().getRemoteAddr();
		TrilhaHistorico trilhaHistorico = new TrilhaHistorico();
		trilhaHistorico.setNuTrilhaHistorico(UUID.randomUUID().toString());
		trilhaHistorico.setTsAcao(new Date());
		trilhaHistorico.setIcAcao(acao);
		trilhaHistorico.setCoResponsavelAcao(SegurancaUsuario.getInstance().getUsuario().getMatricula());
		trilhaHistorico.setNuIpUsuario(strIP);
		trilhaHistorico.setDeCamposVelhos(alteracaoAntes);
		trilhaHistorico.setDeCamposNovos(alteracaoDepois);
		trilhaHistorico.setDeItem(String.valueOf(getIdClass(objeto, acao)));
		trilhaHistorico.setNoAcao(objeto.getClass().getAnnotation(TrailClass.class).fonte());
		return trilhaHistorico;
	}
	
	/**
	 * Este metodo retorna o id da classe. Para descobrir o id foi usado reflection para buscar o campo que possui a annotation @TrailLog para a acao especificada
	 * @param classe
	 * @param acao
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private Object getIdClass(Object classe, String acao) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String retorno = "";
		for (Method method : classe.getClass().getMethods()) {
			if (acao.equals(ACAO_INCLUSAO)) {
				if (method.isAnnotationPresent(TrailLog.class) && method.getAnnotation(TrailLog.class).insertID()) {
					retorno += "[" + getNomeCampo(method) + "=" + method.invoke(classe) + "]";
				}
			} else if (acao.equals(ACAO_EXCLUSAO)) {
				if (method.isAnnotationPresent(TrailLog.class) && method.getAnnotation(TrailLog.class).deleteID()) {
					retorno += "[" + getNomeCampo(method) + "=" + method.invoke(classe) + "]";
				}
			} else {
				if (method.isAnnotationPresent(TrailLog.class) && method.getAnnotation(TrailLog.class).updateID()) {
					retorno += "[" + getNomeCampo(method) + "=" + method.invoke(classe) + "]";
				}
			}
		}
		return retorno;
	}
	
	/**
	 * Busca o nome do campo que será gravado na trilha de log.<br>
	 * @param m (Method)
	 * @return: retorna o valor do parametro 'name' da annotation @TrailLog, <br>
	 * caso não exista, retorna o parametro 'name' da annotation @Column, <br>
	 * caso não exista, retorna o nome do metodo 
	 */
	private String getNomeCampo(Method m) {
		String preCampo = "";
		if (listClass.size() > 1) {
			preCampo = listClass.get(listClass.size() - 1).getName() + ".";
		}
		String campo = preCampo + m.getName();
		if (!m.getAnnotation(TrailLog.class).name().equals("")) {
			campo = preCampo + m.getAnnotation(TrailLog.class).name();
		} else {
			if (m.getAnnotation(Column.class) != null && !m.getAnnotation(Column.class).name().equals("")) {
				campo = preCampo + m.getAnnotation(Column.class).name();
			}
		}
		return campo;
	}
	
	/**
	 * Este metodo compara todos os atributos dos dois objetos que possuem as annotation Trail, e retorna os campos antes de alterar e depois de alterado 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	private CamposAlterados camposAlterados(Object classeAntiga, Object classeNova) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		CamposAlterados camposAlterados = new CamposAlterados();
		if (classeAntiga == null || classeNova == null) {
			return camposAlterados;
		}
		//verifica se esta classe já foi comparada (este recurso evita que entre em loop infinito)
		if (listClass.contains(classeAntiga.getClass())) {
			return camposAlterados;
		}
		listClass.add(classeAntiga.getClass());
		
		//Se os dois objetos são diferentes de null, deve compara-los e gravar somente o que for diferente entre eles
		for (Method m : classeNova.getClass().getMethods()) {
			if (m.isAnnotationPresent(TrailLog.class)) {
				if (m.getAnnotation(TrailLog.class).inner()) {
					CamposAlterados c = camposAlterados(classeAntiga.getClass().getMethod(m.getName()).invoke(classeAntiga), m.invoke(classeNova));
					camposAlterados.antes += c.antes;
					camposAlterados.depois += c.depois;
				}
				String valueNova = m.invoke(classeNova) == null ? "" : toString(m.invoke(classeNova));
				String valueAntiga = classeAntiga.getClass().getMethod(m.getName()).invoke(classeAntiga) == null ? "" : toString(classeAntiga.getClass().getMethod(m.getName()).invoke(classeAntiga));
				if (!valueNova.equals(valueAntiga)) {
					String campoNovo = getNomeCampo(m);
					String campoAntigo = getNomeCampo(classeAntiga.getClass().getMethod(m.getName()));
					camposAlterados.antes += "[" + campoAntigo + "=" + valueAntiga + "]";
					camposAlterados.depois += "[" + campoNovo + "=" + valueNova + "]";
				}
			}
		}
		return camposAlterados;
	}
	
	/**
	 * Este metodo formata e converte o objeto para string. Se Date retorna formatado (dd-MM-yyyy) 
	 * @param invoke
	 * @return
	 */
	private String toString(Object invoke) {
		if (invoke instanceof Date) {
			return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(invoke);
		}
		if (invoke instanceof BigDecimal) {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			return df.format(((BigDecimal) invoke).doubleValue());
		}
		return invoke.toString();
	}
}

class CamposAlterados {
	String antes = "";
	String depois = "";
}