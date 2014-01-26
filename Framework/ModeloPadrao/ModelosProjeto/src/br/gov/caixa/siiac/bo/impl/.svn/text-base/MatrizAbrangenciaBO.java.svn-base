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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siiac.bo.IMatrizAbrangenciaBO;
import br.gov.caixa.siiac.model.domain.Perfil;
import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.Unidade;
import br.gov.caixa.siiac.persistence.dao.IMatrizAbrangenciaDAO;

@Service
public class MatrizAbrangenciaBO implements IMatrizAbrangenciaBO {
	
	private IMatrizAbrangenciaDAO matrizAbrangenciaDAO;
	
	@Autowired
	public void setMatrizAcessoDAO(IMatrizAbrangenciaDAO matrizAbrangenciaDAO) {
		this.matrizAbrangenciaDAO = matrizAbrangenciaDAO;
	}
	
	@Transactional
	public List<Unidade> getListUnidadesVinculadasRN010C(Short nuUnidade) {
		return matrizAbrangenciaDAO.getListUnidadesVinculadasRN010C(nuUnidade);
	}
	
	@Transactional
	public List<Unidade> getListUnidadesVinculadasRN010E(Short nuUnidade) {
		return matrizAbrangenciaDAO.getListUnidadesVinculadasRN010E(nuUnidade);
	}
	
	@Transactional
	public List<Unidade> getListUnidadesVinculadasRN010F(Short nuUnidade) {
		return matrizAbrangenciaDAO.getListUnidadesVinculadasRN010E(nuUnidade);
	}
	
	@Transactional
	public List<Unidade> getListUnidadesVinculadasHierarquica(Short unidade) {
		return matrizAbrangenciaDAO.getListUnidadesVinculadasHierarquica(unidade);
	}
	
	@Transactional
	public List<Short> getListUnidadesVinculadasRN010ENuUnidade(Short nuUnidade) {
		return matrizAbrangenciaDAO.getListUnidadesVinculadasRN010ENuUnidade(nuUnidade);
	}
	
	@Transactional
	public List<Short> getListUnidadesVinculadasRN010CNuUnidade(Short nuUnidade) {
		return matrizAbrangenciaDAO.getListUnidadesVinculadasRN010CNuUnidade(nuUnidade);
	}
	
	@Transactional
	public List<Short> getListUnidadesVinculadasRN010FNuUnidade(Short nuUnidade) {
		return matrizAbrangenciaDAO.getListUnidadesVinculadasRN010ENuUnidade(nuUnidade);
	}
	
	@Transactional
	public List<Short> getListUnidadesVinculadasHierarquicaNuUnidade(Short unidade) {
		return matrizAbrangenciaDAO.getListUnidadesVinculadasHierarquicaNuUnidade(unidade);
	}
	
	@Transactional
	public List<String> getListAbrangenciaProduto(String matricula, Short nuPerfil) {
		if (nuPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) 
				|| nuPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) 
				|| nuPerfil.equals(Perfil.PERFIL_AUDITOR)) {
			return null;
		}
		if(nuPerfil.equals(Perfil.PERFIL_GESTOR_PRODUTO)){
			return matrizAbrangenciaDAO.listProdutosAutorizadosGestorProduto(matricula);
		}
		if (!matrizAbrangenciaDAO.existeRestricaoProduto(matricula)) {
			return null;
		}
		List<String> listProdutosAutorizados = matrizAbrangenciaDAO.listProdutosAutorizados(matricula);
		return listProdutosAutorizados;
	}
	
	@Transactional
	public List<Produto> getListAbrangenciaProdutoObj(String matricula, Short nuPerfil) {
		if (nuPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) 
				|| nuPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) 
				|| nuPerfil.equals(Perfil.PERFIL_AUDITOR)) {
			return null;
		}
		if(nuPerfil.equals(Perfil.PERFIL_GESTOR_PRODUTO)){
			return matrizAbrangenciaDAO.listProdutosAutorizadosGestorProdutoObj(matricula);
		}
		if (!matrizAbrangenciaDAO.existeRestricaoProduto(matricula)) {
			return null;
		}
		List<Produto> listProdutosAutorizados = matrizAbrangenciaDAO.listProdutosAutorizadosObj(matricula);
		return listProdutosAutorizados;
	}
	
	@Transactional
	public List<Integer> getListAbrangenciaCategoriaProduto(String matricula, Short nuPerfil) {
		if (nuPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) 
				|| nuPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) 
				|| nuPerfil.equals(Perfil.PERFIL_AUDITOR)) {
			return null;
		}
		if(nuPerfil.equals(Perfil.PERFIL_GESTOR_PRODUTO)){
			return matrizAbrangenciaDAO.listProdutosAutorizadosGestorCategoriaProduto(matricula);
		}
		if (!matrizAbrangenciaDAO.existeRestricaoProduto(matricula)) {
			return null;
		}
		List<Integer> listCategoriasProdutosAutorizados = matrizAbrangenciaDAO.listCategoriasProdutosAutorizados(matricula);
		return listCategoriasProdutosAutorizados;
	}
	
	@Transactional
	public List<Short> getListAbrangenciaUnidades(Short nuPerfil, Short nuUnidade) {
		if (nuPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) 
				|| nuPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) 
				|| nuPerfil.equals(Perfil.PERFIL_CENTRAL_ATENDIMENTO_SUPORTE) 
				|| nuPerfil.equals(Perfil.PERFIL_AUDITOR)) {
			return null;
		}
		if (nuPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) 
				|| nuPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL)) {
			if (matrizAbrangenciaDAO.isUnidadeGiret(nuUnidade)) {
				return matrizAbrangenciaDAO.getListUnidadesVinculadasRN010CNuUnidade(nuUnidade);
			} else {
				return matrizAbrangenciaDAO.getListUnidadesVinculadasHierarquicaNuUnidade(nuUnidade);
			}
		}
		if (nuPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE)) {
			return matrizAbrangenciaDAO.getListUnidadesVinculadasHierarquicaNuUnidade(nuUnidade);
		}
		if (nuPerfil.equals(Perfil.PERFIL_UNIDADE_ATENDIMENTO)) {
			return matrizAbrangenciaDAO.getListUnidadesVinculadasHierarquicaNuUnidade(nuUnidade);
		}
		return null;
	}
	
	@Transactional
	public List<Unidade> getListAbrangenciaUnidadesObj(Short nuPerfil, Short nuUnidade) {
		if (nuPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) 
				|| nuPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) 
				|| nuPerfil.equals(Perfil.PERFIL_CENTRAL_ATENDIMENTO_SUPORTE) 
				|| nuPerfil.equals(Perfil.PERFIL_AUDITOR)) {
			return null;
		}
		if (nuPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) 
				|| nuPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL)) {
			if (matrizAbrangenciaDAO.isUnidadeGiret(nuUnidade)) {
				return matrizAbrangenciaDAO.getListUnidadesVinculadasRN010C(nuUnidade);
			} else {
				return matrizAbrangenciaDAO.getListUnidadesVinculadasHierarquica(nuUnidade);
			}
		}
		if (nuPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE)) {
			return matrizAbrangenciaDAO.getListUnidadesVinculadasHierarquica(nuUnidade);
		}
		if (nuPerfil.equals(Perfil.PERFIL_UNIDADE_ATENDIMENTO)) {
			return matrizAbrangenciaDAO.getListUnidadesVinculadasHierarquica(nuUnidade);
		}
		return null;
	}
}