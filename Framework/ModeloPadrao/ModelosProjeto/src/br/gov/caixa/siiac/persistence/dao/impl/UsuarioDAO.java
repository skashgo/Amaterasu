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
package br.gov.caixa.siiac.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.gov.caixa.format.ConvertUtils;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Perfil;
import br.gov.caixa.siiac.model.domain.Usuario;
import br.gov.caixa.siiac.persistence.dao.IUsuarioDAO;
import br.gov.caixa.siiac.util.LogCEF;
import br.gov.caixa.util.Utilities;


/**
 * @author GisConsult
 *
 */
@Repository
@Scope("prototype")
public class UsuarioDAO extends GenericDAO<Usuario> implements IUsuarioDAO {
	private static final Log LOG = LogFactory.getLog(UsuarioDAO.class);
	private static final String ERRO_GRAVAR_USUARIO = "Erro ao gravar o usuários: %s";
	public static final int TAMANHO_MATRICULA = 6;
	
	public UsuarioDAO() throws SIIACException {
		super(Usuario.class);
		LOG.debug("Construindo");

	}

	/**
	 * getListUsuario
	 * @throws SIIACException 
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> getListUsuario(String matriculaLogada, boolean mostrarInativos, String nome, String unidade, String matricula, String perfil, 
			boolean fazConsulta, List<Integer> perfisPermitidos) throws SIIACException {
		Criteria cri = getCriteria();
		if(perfisPermitidos != null){
			cri.add(Restrictions.in("perfil.nuPerfil", perfisPermitidos));
		}
		
		if (fazConsulta) {
			
			if (Utilities.notEmpty(matricula)) {
				String matr = matricula.substring(0, 1) + ConvertUtils.padZerosLeft(matricula.replace("_", "").substring(1), TAMANHO_MATRICULA);
				cri.add(Restrictions.ilike("coUsuario", matr, MatchMode.EXACT));
			}
	
			if (Utilities.notEmpty(perfil) && !perfil.equals("0")) {
				cri.add(Restrictions.eq("perfil.nuPerfil", Integer.parseInt(perfil)));
			}
		} 
		
		if(mostrarInativos){
			cri.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			cri.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		cri.add(Restrictions.ne("coUsuario", matriculaLogada));
		return cri.list();
	}
	
	/**
	 * seExisteUsuario
	 */
	public boolean seExisteUsuario(String matricula) throws SIIACException {
		try {
			Query q  = getSession().createQuery("FROM Usuario as u WHERE u.coUsuario = :matr");
		    q.setParameter("matr",  matricula);

		    if (q.uniqueResult() != null) {
				return true;
		    } else {
				return false;
		    }
		} catch (Exception e) {
			throw new SIIACException("Erro ao executar lista de dados do usuario");
		}
	}

	/**
	 * gravarUsuario
	 * 
	 * @param matricula -> matricula do Usuario a ser gravado
	 * @param perfil 	-> perfil a ser modificado
	 * @param matriResp -> matricula do responsavel da gravacao
	 * 
	 */
	public boolean gravarUsuario(String matricula, short perfil, String matriResp, Short nuUnidade, Integer nuNatural) {
		boolean resultado = false;
		
		try {
			Perfil per = new Perfil();
		    per.setNuPerfil(perfil);
		    
		    Usuario usu = new Usuario();
			usu.setCoUsuario(matricula);
			usu.setPerfil(per);
			usu.setNuUnidadeVerificacao(nuUnidade);
			usu.setNuNaturalUnidadeVerificacao(nuNatural);
			usu.setIcAtivo(true);
			usu.setTsInclusaoAlteracao(new Date());
			usu.setCoResponsavel(matriResp);
	    	merge(usu);
			resultado = true;
		} catch (Exception e) {
			LogCEF.error(String.format(ERRO_GRAVAR_USUARIO, e.getMessage()));
		}

		return resultado;
	}

	/**
	 * ativarDesativarUsuario
	 * 
	 * @param matr - matricula do usuario
	 * @param ativar - true = para ativar o usuario / false = desativar
	 */
	public void ativarDesativarUsuario(String matr, boolean ativar) throws SIIACException {
		try {
			Usuario u = findById(matr);
			u.setIcAtivo(ativar);
			merge(u);
		}catch (Exception e) {
			throw new SIIACException("Erro em desativar: " + e.getMessage());
		}
	}

	/**
	 * getUsuarioByMatricula
	 */
	public Usuario getUsuarioByMatricula(String matricula) throws SIIACException {
		    Query q  = getSession().createQuery("FROM Usuario as u WHERE u.coUsuario = :matr");
		    q.setParameter("matr",  matricula);
		return (Usuario) q.uniqueResult();
	}
	
	public Short getUnidadeByUsuario(String matricula) {
		Query q  = getSession().createQuery("SELECT u.nuUnidadeVerificacao FROM Usuario as u WHERE u.coUsuario = :matr");
	    q.setParameter("matr",  matricula);
	    
	    return (Short) q.uniqueResult();
	}
}