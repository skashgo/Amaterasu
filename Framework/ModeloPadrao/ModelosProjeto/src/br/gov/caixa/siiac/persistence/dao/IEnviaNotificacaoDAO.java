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

import java.util.Date;
import java.util.List;

import br.gov.caixa.siiac.model.EnviaNotificacaoVO;

/**
 * @author GIS Consult
 *
 */
public interface IEnviaNotificacaoDAO {

	/**
	 * <b>RN090</b> – O sistema diariamente, após a atualização da agenda de
	 * verificação da unidade, identifica os serviços de verificação cujos
	 * prazos de notificação da unidade responsável pela verificação vencem em D
	 * e gera mensagem de alerta a ser encaminhada a unidade responsável pela
	 * verificação conforme template com cópia ao usuário responsável pela
	 * unidade e seu substituto eventual.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090
	 */
	public List<EnviaNotificacaoVO> obtemVerificacoesRN090(Date data);

	/**
	 * <b>RN090A</b> – O sistema agrupa os serviços de verificação cujos prazos de
	 * notificação da unidade superior hierárquica de 1º nível das unidades
	 * responsáveis pela verificação vencem em D e gera mensagem de alerta a ser
	 * encaminhada a unidade superior hierárquica conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090A
	 */
	public List<EnviaNotificacaoVO> obtemVerificacoesRN090A(Date data);

	/**
	 * <b>RN090B</b> – O sistema agrupa os serviços de verificação cujos prazos de
	 * notificação da unidade superior hierárquica de 2º nível das unidades
	 * responsáveis pela verificação vencem em D e gera mensagem de alerta a ser
	 * encaminhada a unidade superior hierárquica de 2º nível conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090B
	 */
	public List<EnviaNotificacaoVO> obtemVerificacoesRN090B(Date data);

	/**
	 * <b>RN090C</b> - O sistema agrupa os serviços de verificação que sensibilizarão o
	 * AV Gestão em D por unidade superior hierárquica das unidades responsáveis
	 * pela verificação e gera mensagem informando as unidades superiores
	 * hierárquicas de 1º e 2º níveis conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090C
	 */
	public List<EnviaNotificacaoVO> obtemVerificacoesRN090C(Date data, Short nivel);

	/**
	 * <b>RN090D</b> – O sistema agrupa os serviços de verificação que sensibilizarão o
	 * AV SURET por unidade de conformidade de vinculação das unidades
	 * responsáveis pela verificação e gera mensagem a ser encaminhada a unidade
	 * de conformidade de vinculação conforme template.
	 * 
	 * @return lista de verificacações de acordo com a regra de negócio RN090D
	 */
	public List<EnviaNotificacaoVO> obtemVerificacoesRN090D(Date data);
	
	
	public List<String> getEmailErro(String grupo);
	
}
