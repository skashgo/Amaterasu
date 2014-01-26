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
package br.gov.caixa.siiac.bo;

import java.util.List;

import br.gov.caixa.siiac.model.domain.Produto;
import br.gov.caixa.siiac.model.domain.Unidade;

public interface IMatrizAbrangenciaBO {
	
	List<Unidade> getListUnidadesVinculadasRN010C(Short nuUnidade);
	
	List<Unidade> getListUnidadesVinculadasRN010E(Short nuUnidade);
	
	List<Unidade> getListUnidadesVinculadasRN010F(Short nuUnidade);
	
	List<Unidade> getListUnidadesVinculadasHierarquica(Short unidade);
	
	List<Short> getListUnidadesVinculadasRN010ENuUnidade(Short nuUnidade);
	
	List<Short> getListUnidadesVinculadasRN010CNuUnidade(Short nuUnidade);
	
	List<Short> getListUnidadesVinculadasRN010FNuUnidade(Short nuUnidade);

	List<Short> getListUnidadesVinculadasHierarquicaNuUnidade(Short unidade);
	/**
	 *  Retorna uma lista de codigo de produtos que o usuario pode acessar
	 *  Retorna null quando o usuario não tem filtro de produtos ou seja, ele acessa todos os produtos <br>
	 * @param matricula
	 * @param nuPerfil
	 * @return
	 */
	List<String> getListAbrangenciaProduto(String matricula, Short nuPerfil);
	
	List<Integer> getListAbrangenciaCategoriaProduto(String matricula, Short nuPerfil);

	/**
	 * Retorna uma lista de codigo de unidades que estão na alcada do perfil do usurio logado (unidade do usuario e vinculadas)
	 * Retorna null quando o usuario não tem filtro de unidade ou seja, ele tem acesso a todas as unidades 
	 * @param nuPerfil
	 * @param nuUnidade
	 * @return
	 */
	List<Short> getListAbrangenciaUnidades(Short nuPerfil, Short nuUnidade);	
	List<Unidade> getListAbrangenciaUnidadesObj(Short nuPerfil, Short nuUnidade);

	/**
	 *  Retorna uma lista de produtos que o usuario pode acessar
	 *  Retorna null quando o usuario não tem filtro de produtos ou seja, ele acessa todos os produtos <br>
	 * @param matricula
	 * @param nuPerfil
	 * @return
	 */
	List<Produto> getListAbrangenciaProdutoObj(String matricula, Short nuPerfil);
}
