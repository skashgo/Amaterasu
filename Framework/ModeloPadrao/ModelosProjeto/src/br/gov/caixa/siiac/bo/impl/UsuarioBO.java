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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.exceptions.DAOException;
import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.bo.IUnidadeBO;
import br.gov.caixa.siiac.bo.IUsuarioADBO;
import br.gov.caixa.siiac.bo.IUsuarioBO;
import br.gov.caixa.siiac.bo.ListaPerfil;
import br.gov.caixa.siiac.exception.SIIACException;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.model.domain.UnidadeId;
import br.gov.caixa.siiac.model.domain.Usuario;
import br.gov.caixa.siiac.persistence.dao.IUnidadeDAO;
import br.gov.caixa.siiac.persistence.dao.IUsuarioDAO;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.util.FilterBase;
import br.gov.caixa.util.Utilities;

/**
 * @author GisConsult
 *
 */

@Service
@Scope("prototype")
public class UsuarioBO implements IUsuarioBO {
	private static final Log LOG = LogFactory.getLog(UsuarioBO.class);
	private transient IUsuarioDAO usuarioDAO;
	private transient IUsuarioADBO usuarioADBO;
	private transient IUnidadeDAO unidadeDAO;
	private transient IUnidadeBO unidadeBO;
	private IMatrizAbrangenciaBO matrizAbrangenciaBO;
		
	@Autowired
	public void setUnidadeBO(IUnidadeBO unidadeBO) {
		this.unidadeBO = unidadeBO;
	}
	
	@Autowired
	public void setUsuarioDAO(IUsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	
	@Autowired
	public void setUsuarioADBO(IUsuarioADBO usuarioADBO) {
		this.usuarioADBO = usuarioADBO;
	}
	
	@Autowired
	public void setUnidadeDAO(IUnidadeDAO unidadeDAO) {
		this.unidadeDAO = unidadeDAO;
	}
	
	@Autowired
	public void setMatrizAbrangenciaBO(IMatrizAbrangenciaBO matrizAbrangenciaBO) {
		this.matrizAbrangenciaBO = matrizAbrangenciaBO;
	}

	/**
	 * getListUsuario
	 * @param matriculaLogada
	 * @param situacao
	 * @param nome
	 * @param unidade
	 * @param matricula
	 * @param perfil
	 * @return
	 * @throws SIIACException 
	 */
	@Transactional
	@Deprecated
	public List<Usuario> getListUsuario(IUsuario usuario, String matriculaLogada, boolean ativo, String nome, String unidade, String matricula, String perfil, boolean fazConsulta, List<Integer> perfisPermitidos) throws SIIACException {
		List<Usuario> lis = new ArrayList<Usuario>();
		try {
			List<Unidade> unidadesPermitidas = null;
			Short nuPerfil = usuario.getPerfis().get(0);
			Short nuUnidade = usuario.getUnidade();
			unidadesPermitidas = matrizAbrangenciaBO.getListAbrangenciaUnidadesObj(nuPerfil, nuUnidade);
			
			for(Usuario u : usuarioDAO.getListUsuario(matriculaLogada, ativo, nome, unidade, matricula, perfil, fazConsulta, perfisPermitidos)){
				
				Short nuUnidadeUsuario = null;
				if(u.getNuUnidadeVerificacao() == null){
					nuUnidadeUsuario = usuarioADBO.getNuUnidade(u.getCoUsuario());
				}else{
					nuUnidadeUsuario = u.getNuUnidadeVerificacao();
				}
				String nomeUsuario = usuarioADBO.getNomeUsuario(u.getCoUsuario());

				if(Utilities.notEmpty(unidade)){
					if(!Short.valueOf(unidade).equals(nuUnidadeUsuario)){
						continue;
					}
				}
				if(Utilities.notEmpty(unidadesPermitidas)){
					Unidade uni = unidadeBO.getUnidade(nuUnidadeUsuario);
					if(!unidadesPermitidas.contains(uni)){
						continue;
					}
				}
				if(Utilities.notEmpty(nome)){
					if(!nomeUsuario.toLowerCase().contains(nome.toLowerCase())){
						continue;
					}
				}
				
				lis.add(u);
			}

		} catch (SIIACException e) {
			throw new SIIACException(e.getMessage());
		}
			
		return lis;
	}
	
	@Transactional
	public List<Usuario> getListUsuario(IUsuario usuario, String matriculaLogada, FilterBase filtro, boolean fazConsulta, List<Integer> listPerfisPermitidos) throws SIIACException {
		List<Usuario> lis = new ArrayList<Usuario>();
		try {
			List<Unidade> unidadesPermitidas = null;
			Short nuPerfil = usuario.getPerfis().get(0);
			Short nuUnidade = usuario.getUnidade();
			unidadesPermitidas = matrizAbrangenciaBO.getListAbrangenciaUnidadesObj(nuPerfil, nuUnidade);
			
			for(Usuario u : usuarioDAO.getListUsuario(matriculaLogada, filtro.getBoolean("pesquisaMostraInativos"),
					filtro.getString("filtroNome"), filtro.getString("filtroUnidade"), 
					filtro.getString("filtroMatricula"), filtro.getString("filtroPerfil"), 
					fazConsulta, listPerfisPermitidos)){
				
				Short nuUnidadeUsuario = usuarioADBO.getNuUnidade(u.getCoUsuario());
				String nomeUsuario = usuarioADBO.getNomeUsuario(u.getCoUsuario());

				if(Utilities.notEmpty(filtro.getString("filtroUnidade"))){
					if(!Short.valueOf(filtro.getString("filtroUnidade")).equals(nuUnidadeUsuario)){
						continue;
					}
				}
				if(Utilities.notEmpty(unidadesPermitidas)){
					Unidade uni = unidadeBO.getUnidade(nuUnidadeUsuario);
					if(!unidadesPermitidas.contains(uni)){
						continue;
					}
				}
				if(Utilities.notEmpty(filtro.getString("filtroNome"))){
					if(!nomeUsuario.toLowerCase().contains(filtro.getString("filtroNome").toLowerCase())){
						continue;
					}
				}
				
				lis.add(u);
			}

		} catch (SIIACException e) {
			throw new SIIACException(e.getMessage());
		}
			
		return lis;
	}

	/**
	 * gravarUsuario
	 * @param matricula
	 * @param perfil
	 * @param alterar
	 * @return
	 * @throws DAOException
	 * @throws siiacException
	 */
	@Transactional
	public boolean gravarUsuario(IUsuario usuario, String matricula, short perfil, boolean alterar, Short nuUnidade) throws SIIACException {
		LOG.info("Gravando Usuario");

		//Verificando se a matricula do usuario logado e a mesma digitada no campo.
		//A matricula e gravada em minusculo na base de dados
		String matriculaMin = matricula.toLowerCase();
		
		if(String.valueOf(usuario.getMatricula()).equals(matriculaMin)){
			throw new SIIACException("XX020");
		} else if (matriculaMin.trim().equals("")) {
			throw new SIIACException("XX019");
		} else {
			
			List<Unidade> vinculadas = null;
			if(usuario.contemPerfil(ListaPerfil.PERFIL_REGIONAL_CONFORMIDADE)){
				Set<Unidade> unidadesNaoRepetidas = new HashSet<Unidade>();
				vinculadas = new ArrayList<Unidade>();
				unidadesNaoRepetidas.add(unidadeDAO.findById(new UnidadeId(usuario.getUnidade(), usuario.getNuNatural())));
				unidadesNaoRepetidas.addAll(matrizAbrangenciaBO.getListUnidadesVinculadasRN010C(usuario.getUnidade()));
				unidadesNaoRepetidas.addAll(matrizAbrangenciaBO.getListUnidadesVinculadasRN010E(usuario.getUnidade()));
				
				vinculadas.addAll(unidadesNaoRepetidas);
			}
			//Se o usuario logado tiver perfil de Gestor de Sistema, ele pode trabalhar com qualquer usuario de qualquer unidade e perfil.
			if (!usuario.contemPerfil(ListaPerfil.PERFIL_GESTOR_SISTEMA) && 
					!usuarioADBO.verificaUnidade(matriculaMin, vinculadas)) {
				throw new SIIACException("MN014");
			}
			
			if (!alterar) {
				if (usuarioDAO.seExisteUsuario(matriculaMin)) { //se a matricula ja existir
					throw new SIIACException("XX023");
				}
			}
			
			if(!usuarioADBO.existeUsuarioAD(matriculaMin)) {
				throw new SIIACException("XX022");
			}
			
			//Verificando se a unidade digitada pelo usuário é a mesma do AD
			boolean unidadeAD = false;
			if (nuUnidade != null)
			{
				if (nuUnidade.equals(usuarioADBO.getNuUnidade(matricula)))
				{
					nuUnidade = null;
					unidadeAD = true;
				} else {
					unidadeAD = false;
				}
			} else {
				unidadeAD = false;
			}
			
			
			Integer nuNatural = null;
			if (!unidadeAD)
			{
				//Deve buscar o número natural da unidade
				if (nuUnidade != null && nuUnidade > 0)
				{
					nuNatural = unidadeDAO.getNuNaturalUnidade(nuUnidade);
				}
			}
			
			
			return usuarioDAO.gravarUsuario(matriculaMin, perfil, String.valueOf(usuario.getMatricula()), nuUnidade, nuNatural);
		}
	}

	/**
	 * getUsuarioByMatricula
	 * @param matricula
	 * @return
	 * @throws SIIACException
	 */
	@Transactional
	public Usuario getUsuarioByMatricula(String matricula) throws SIIACException {
		Criteria crit = usuarioDAO.getCriteria();
		crit.add(Restrictions.eq("coUsuario", matricula));
		List<Usuario> lis = usuarioDAO.findByCriteria(crit);
		
		if(lis == null || lis.isEmpty()){
			return null;
		}
//		inicializaUsuario(lis.get(0));
		return lis.get(0);
	}

	/**
	 * ativarDesativarUsuario
	 * @param perfilUsuario
	 * @throws SIIACException 
	 */
	@Transactional
	public void ativarDesativarUsuario(String matr, boolean ativar) throws SIIACException {
		usuarioDAO.ativarDesativarUsuario(matr, ativar);
	}

	@Transactional
	public boolean existeUnidade(String nuUnidade) {
		return unidadeDAO.existUnidade(nuUnidade);
	}
	
	@Transactional
	public boolean seExisteUsuario(String matricula) throws SIIACException {
		return usuarioDAO.seExisteUsuario(matricula);
	}

	@Transactional
	@Deprecated
	public List<Usuario> getListUsuarioFiltroSimples(IUsuario usuario, String pesquisa, Boolean mostrarInativos) throws SIIACException {
		List<Unidade> unidadesPermitidas = null;
		List<Integer> perfisPermitidos = null;
		Short nuPerfil = usuario.getPerfis().get(0);
		Short nuUnidade = usuario.getUnidade();
		unidadesPermitidas = matrizAbrangenciaBO.getListAbrangenciaUnidadesObj(nuPerfil, nuUnidade);
		if (usuario.contemPerfil(ListaPerfil.PERFIL_REGIONAL_CONFORMIDADE)) {
			perfisPermitidos = new ArrayList<Integer>();
			perfisPermitidos.add(Integer.valueOf(String.valueOf(ListaPerfil.PERFIL_VERIFICADOR_CONFORMIDADE)));
			perfisPermitidos.add(Integer.valueOf(String.valueOf(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO)));
		}
		
		
		Criteria c = usuarioDAO.getCriteria();
		if (Utilities.notEmpty(mostrarInativos) && mostrarInativos.equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		if(perfisPermitidos != null){
			c.add(Restrictions.in("perfil.nuPerfil", perfisPermitidos));
		}
		c.add(Restrictions.ne("coUsuario", usuario.getMatricula()));
		List<Usuario> lis = new ArrayList<Usuario>();
			for(Usuario u : usuarioDAO.findByCriteria(c)){
				Short nuUnidadeUsuario = usuarioADBO.getNuUnidade(u.getCoUsuario());
				String nomeUsuario = usuarioADBO.getNomeUsuario(u.getCoUsuario());
				
				String matr = "";
				try{
					matr = usuarioADBO.getMatriculaFormatada(pesquisa);
				} catch (Exception e) {
					matr = "";
				}

				if(Utilities.notEmpty(unidadesPermitidas)){
					Unidade uni = unidadeBO.getUnidade(nuUnidadeUsuario);
					if(!unidadesPermitidas.contains(uni)){
						continue;
					}
				}
				
				if(!u.getCoUsuario().equals(matr) && !nomeUsuario.toLowerCase().contains(pesquisa.toLowerCase())) {
					continue;
				}
				
				lis.add(u);
			}
		return lis;		
	}
	
	@Transactional
	public List<Usuario> getListUsuarioFiltroSimples(IUsuario usuario, FilterBase filtro) throws SIIACException {
		List<Unidade> unidadesPermitidas = null;
		List<Integer> perfisPermitidos = null;
		Short nuPerfil = usuario.getPerfis().get(0);
		Short nuUnidade = usuario.getUnidade();
		unidadesPermitidas = matrizAbrangenciaBO.getListAbrangenciaUnidadesObj(nuPerfil, nuUnidade);
		if (usuario.contemPerfil(ListaPerfil.PERFIL_REGIONAL_CONFORMIDADE)) {
			perfisPermitidos = new ArrayList<Integer>();
			perfisPermitidos.add(Integer.valueOf(String.valueOf(ListaPerfil.PERFIL_VERIFICADOR_CONFORMIDADE)));
			perfisPermitidos.add(Integer.valueOf(String.valueOf(ListaPerfil.PERFIL_UNIDADE_ATENDIMENTO)));
		}
		
		
		Criteria c = usuarioDAO.getCriteria();
		if (Utilities.notEmpty(filtro.getBoolean("pesquisaMostraInativos")) && filtro.getBoolean("pesquisaMostraInativos").equals(Boolean.TRUE)) {
			c.add(Restrictions.eq("icAtivo", Boolean.FALSE));
		}else{
			c.add(Restrictions.eq("icAtivo", Boolean.TRUE));
		}
		
		if(perfisPermitidos != null){
			c.add(Restrictions.in("perfil.nuPerfil", perfisPermitidos));
		}
		c.add(Restrictions.ne("coUsuario", usuario.getMatricula()));
		List<Usuario> lis = new ArrayList<Usuario>();
			for(Usuario u : usuarioDAO.findByCriteria(c)){
				Short nuUnidadeUsuario = usuarioADBO.getNuUnidade(u.getCoUsuario());
				String nomeUsuario = usuarioADBO.getNomeUsuario(u.getCoUsuario());
				
				String matr = "";
				try{
					matr = usuarioADBO.getMatriculaFormatada(filtro.getString("pesquisaString"));
				} catch (Exception e) {
					matr = "";
				}

				if(Utilities.notEmpty(unidadesPermitidas)){
					Unidade uni = unidadeBO.getUnidade(nuUnidadeUsuario);
					if(!unidadesPermitidas.contains(uni)){
						continue;
					}
				}
				
				if(!u.getCoUsuario().equals(matr) && !nomeUsuario.toLowerCase().contains(filtro.getString("pesquisaString").toLowerCase())) {
					continue;
				}
				
				lis.add(u);
			}
		return lis;		
	}
	
	public void inicializaUsuario(Usuario usuario)
	{
		Hibernate.initialize(usuario.getProdutoUsuarioList());
	}
	
	@Transactional
	public Short getUnidadeByUsuario(String matricula) throws SIIACException {
		
		Short nuUnidade = usuarioDAO.getUnidadeByUsuario(matricula); 
		if (nuUnidade != null && nuUnidade > 0)
		{
			return nuUnidade;
		}
		
		return usuarioADBO.getNuUnidade(matricula);
	}
	
}
