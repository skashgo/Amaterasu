#PATH[src/main/java/br/com/amaterasu/util/PathFramework]
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amaterasu.util;

/**
 *
 * @author Maykon
 */
public class PathFramework {

    /**
     * retorna o path Framework (locaizado na mesma pasta do Amaterasu.jar)
     *
     * @return
     */
    public static String pathFramework() {
        return IConstants.DIR_FRAMEWORK + IConstants.barra;
    }

    /**
     * retorna o path da pasta raiz localizado dentro da pasta Framework
     *
     * @param modelo
     * @return
     */
    public static String pathFrameworkRaiz(String modelo) {
        return IConstants.DIR_FRAMEWORK + IConstants.barra + modelo + IConstants.barra + IConstants.DIR_RAIZ + IConstants.barra;
    }

    /**
     * retorna o path do arquivo pacotes.txt. Utilizado para definir a estrutura
     * de pacotes do projeto.
     *
     * @param modelo
     * @return
     */
    public static String pathFrameworkPacotes(String modelo) {
        return IConstants.DIR_FRAMEWORK + IConstants.barra + modelo + IConstants.barra + IConstants.FILE_PACOTES;
    }

    /**
     * retorna o path do arquivo especificacao.txt. Utilizado para definir as
     * tecnologias utilizadas no modelo
     *
     * @param modelo
     * @return
     */
    public static String pathFrameworkEspecificacao(String modelo) {
        return IConstants.DIR_FRAMEWORK + IConstants.barra + modelo + IConstants.barra + IConstants.FILE_ESPECIFICACAO;
    }

    /**
     * retorna o path da pasta ArquivosModelo. Utilizado para definir os
     * arquivos que serão copiados para o projeto
     *
     * @param modelo
     * @return
     */
    public static String pathFrameworkModelosProjeto(String modelo) {
        return IConstants.DIR_FRAMEWORK + IConstants.barra + modelo + IConstants.barra + IConstants.DIR_MODELOS_PROJETO + IConstants.barra;
    }

    /**
     * retorna o path da pasta CRUDsModelo. Utilizado para definir os arquivos
     * que serão utilizados na geração de CRUD
     *
     * @param modelo
     * @return
     */
    public static String pathFrameworkModelosCRUD(String modelo) {
        return IConstants.DIR_FRAMEWORK + IConstants.barra + modelo + IConstants.barra + IConstants.DIR_MODELOS_CRUD + IConstants.barra;
    }

    /**
     * retorna o path da pasta componentes do crud. Utilizado para definir os
     * arquivos que serão utilizados na geração de CRUD
     *
     * @param modeloFramework
     * @return
     */
    public static String pathFrameworkComponentes(String modeloFramework, String modeloCrud) {
        return IConstants.DIR_FRAMEWORK + IConstants.barra + modeloFramework + IConstants.barra + IConstants.DIR_MODELOS_CRUD + IConstants.barra + modeloCrud + IConstants.barra + IConstants.DIR_COMPONENTES + IConstants.barra;
    }
}
