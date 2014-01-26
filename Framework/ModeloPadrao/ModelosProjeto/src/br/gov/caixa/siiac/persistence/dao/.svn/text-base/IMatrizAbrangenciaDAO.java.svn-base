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
package br.gov.caixa.siiac.persistence.dao;

import java.util.List;

import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.Unidade;

/**
 * @author GisConsult
 *
 */
public interface IMatrizAbrangenciaDAO extends IGenericDAO<Unidade> {
	
	List<Unidade> getListUnidadesVinculadasRN010C(Short nuUnidade);
	
	List<Unidade> getListUnidadesVinculadasRN010E(Short nuUnidade);
	
	List<Unidade> getListUnidadesVinculadasRN010F(Short nuUnidade);
	
	List<Unidade> getListUnidadesVinculadasHierarquica(Short unidade);
	
	List<Short> getListUnidadesVinculadasRN010ENuUnidade(Short nuUnidade);
	
	List<Short> getListUnidadesVinculadasRN010CNuUnidade(Short nuUnidade);
	
	List<Short> getListUnidadesVinculadasRN010FNuUnidade(Short nuUnidade);
	
	List<Short> getListUnidadesVinculadasHierarquicaNuUnidade(Short unidade);
	
	boolean existeRestricaoProduto(String matricula);
	
	List<String> listProdutosAutorizados(String matricula);
	
	List<Integer> listCategoriasProdutosAutorizados(String matricula);
	
	List<String> listProdutosAutorizadosGestorProduto(String matricula);
	
	List<Integer> listProdutosAutorizadosGestorCategoriaProduto(String matricula);
	
	List<String> listAllProdutosAutorizados();

	boolean isUnidadeGiret(Short nuUnidade);

	List<Produto> listProdutosAutorizadosGestorProdutoObj(String matricula);

	List<Produto> listProdutosAutorizadosObj(String matricula);
}
