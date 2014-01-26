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

import org.springframework.stereotype.Service;

import br.gov.caixa.siiac.bo.IMatrizAcessoBO;
import br.gov.caixa.siiac.model.domain.Perfil;

@Service
public class MatrizAcessoBO implements IMatrizAcessoBO {
	
	public static boolean acessoModuloMantemUsuario(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			if (paramAcao.equals(TIPO_ACAO_INSERE) || paramAcao.equals(TIPO_ACAO_ALTERA) || paramAcao.equals(TIPO_ACAO_EXCLUI)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE);
			} else if (paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_CENTRAL_ATENDIMENTO_SUPORTE);
			}
		}
		return false;
	}
	
	public static boolean acessoModuloMantemPreferencias(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		return true;
	}
	
	public static boolean acessoModuloMantemInformacao(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			if (paramAcao.equals(TIPO_ACAO_VISUALIZA)
					|| paramAcao.equals(TIPO_ACAO_CONSULTA)
					|| paramAcao.equals(TIPO_ACAO_INSERE)
					|| paramAcao.equals(TIPO_ACAO_ALTERA)
					|| paramAcao.equals(TIPO_ACAO_EXCLUI)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
			}
		}
		return false;
	}
	
	public static boolean acessoModuloMantemProduto(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
		}
		return false;
	}
	
	public static boolean acessoModuloMantemTemplateNotificacao(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
		}
		return false;
	}
	
	public static boolean acessoModuloMantemTemplateParecer(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
		}
		return false;
	}
	
	public static boolean acessoModuloMantemServicoVerificacao(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
		}
		return false;
	}
	
	public static boolean acessoModuloMantemBlocoChecklist(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
		}
		return false;
	}
	
	public static boolean acessoModuloMantemItemVerificacao(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
		}
		return false;
	}
	
	public static boolean acessoModuloMantemApontamento(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
		}
		return false;
	}
	
	public static boolean acessoModuloMantemLogParecer(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE);
		}
		return false;
	}
	
	
	public static boolean acessoModuloMantemRestricao(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
		}
		return false;
	}
	
	public static boolean acessoModuloMantemChecklist(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
		}
		if (paramFuncionalidade.equals(VINCULA_BLOCOS_CHECKLIST) || paramFuncionalidade.equals(VINCULA_ITENS_VERIFICACAO_BLOCOS_CHECKLIST) || paramFuncionalidade.equals(VINCULA_APONTAMENTOS_ITENS_VERIFICACAO_BLOCOS_CHECKLISTS)) {
			if (paramAcao.equals(TIPO_ACAO_INSERE) || paramAcao.equals(TIPO_ACAO_ALTERA) || paramAcao.equals(TIPO_ACAO_EXCLUI) || paramAcao.equals(TIPO_ACAO_VISUALIZA)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
			}
		}
		return false;
	}
	
	public static boolean acessoModuloMantemContrato(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			if (paramAcao.equals(TIPO_ACAO_INSERE) || paramAcao.equals(TIPO_ACAO_ALTERA) || paramAcao.equals(TIPO_ACAO_EXCLUI)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL);
			}
			return true;
		}
		if (paramFuncionalidade.equals(CONSULTA_TRILHA_HISTORICO_CONTRATO)) {
			return true;
		}
		return false;
	}
	
	public static boolean acessoModuloMantemGarantia(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			if (paramAcao.equals(TIPO_ACAO_INSERE)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL);
			}
			if (paramAcao.equals(TIPO_ACAO_ALTERA) || paramAcao.equals(TIPO_ACAO_EXCLUI)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE);
			}
			return true;
		}
		return false;
	}
	
	public static boolean acessoModuloMantemTipoGarantia(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACAO_PERFIL)) {
			return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA);
		}
		return false;
	}
	
	public static boolean acessoModuloVisualiaAgendaVerificacoes(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(VISUALIZA_AGENDA_VERIFICACOES)) {
			if (paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA)) {
				return paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE);
			}
		}
		return false;
	}
	
	public static boolean acessoModuloConsultaVerificacao(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(CONSULTA_VERIFICACAO)) {
			if (paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_AUDITOR) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE);
			}
		}
		if (paramFuncionalidade.equals(VISUALIZA_PARECER)) {
			if (paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_AUDITOR) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE);
			}
		}
		return false;
	}
	
	public static boolean acessoModuloEfetuaServicoVerificacao(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(VISUALIZA_CHECKLIST)){
			if (paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA)){
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_AUDITOR) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL); 
			}
		}
		
		if (paramFuncionalidade.equals(MARCA_INCONFORMIDADES_RESTRICOES)){
			if (paramAcao.equals(TIPO_ACAO_ALTERA)){
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL); 
			}
		}
		
		if (paramFuncionalidade.equals(INCLUI_OBSERVACAO)){
			if (paramAcao.equals(TIPO_ACAO_INSERE)){
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL); 
			}
		}
		
		if (paramFuncionalidade.equals(ALTERA_VERIFICACAO)){
			if (paramAcao.equals(TIPO_ACAO_INSERE)){
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL); 
			}
		}
		
		return false;
	}
	
	public static boolean acessoModuloGeraParecer(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(GERA_PARECER)){
			if (paramAcao.equals(TIPO_ACAO_INSERE)){
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL); 
			}
		}
		return false;
	}

	public static boolean acessoModuloConsultaRelatorios(Short paramPerfil, Integer paramFuncionalidade, Integer paramAcao) {
		//Validação
		if(paramFuncionalidade == null || paramAcao == null || paramPerfil == null){
			return false;
		}
		
		if (paramFuncionalidade.equals(ACOMPANHAMENTO_VERIFICACOES)) {
			if (paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_GESTOR_PRODUTO) || paramPerfil.equals(Perfil.PERFIL_UNIDADE_ATENDIMENTO) || paramPerfil.equals(Perfil.PERFIL_AUDITOR) || paramPerfil.equals(Perfil.PERFIL_CENTRAL_ATENDIMENTO_SUPORTE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL);
			}
		}
		
		if (paramFuncionalidade.equals(ESTRUTURA_COMPLETA_CHECKLIST)) {
			if (paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_GESTOR_PRODUTO) || paramPerfil.equals(Perfil.PERFIL_UNIDADE_ATENDIMENTO) || paramPerfil.equals(Perfil.PERFIL_AUDITOR) || paramPerfil.equals(Perfil.PERFIL_CENTRAL_ATENDIMENTO_SUPORTE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL);
			}
		}
		
		if (paramFuncionalidade.equals(ACOMPANHAMENTO_SENSIBILIZACAO_AV_GESTAO)) {
			if (paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA)) {
				return paramPerfil.equals(Perfil.PERFIL_GESTOR_SISTEMA) || paramPerfil.equals(Perfil.PERFIL_REGIONAL_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE) || paramPerfil.equals(Perfil.PERFIL_GESTOR_PRODUTO) || paramPerfil.equals(Perfil.PERFIL_UNIDADE_ATENDIMENTO) || paramPerfil.equals(Perfil.PERFIL_AUDITOR) || paramPerfil.equals(Perfil.PERFIL_CENTRAL_ATENDIMENTO_SUPORTE) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_NACIONAL) || paramPerfil.equals(Perfil.PERFIL_VERIFICADOR_REGIONAL);
			}
		}
		
		if (paramFuncionalidade.equals(ACOMPANHAMENTO_INCONFORMIDADES)) {
			return paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA);
		}
		
		if (paramFuncionalidade.equals(ACOMPANHAMENTO_SENSIBILIZACAO_AV_SURET)) {
			return paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA);
		}
		
		if (paramFuncionalidade.equals(SITUACAO_CONTRATO)) {
			return paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA);
		}
		
		if (paramFuncionalidade.equals(ESTATISTICAS)) {
			return paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA);
		}
		
		if (paramFuncionalidade.equals(INDICADORES)) {
			return paramAcao.equals(TIPO_ACAO_CONSULTA) || paramAcao.equals(TIPO_ACAO_VISUALIZA);
		}
		return false;
	}
	
}