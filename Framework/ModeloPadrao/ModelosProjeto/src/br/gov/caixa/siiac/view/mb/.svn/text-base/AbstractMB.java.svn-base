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
package br.gov.caixa.siiac.view.mb;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import br.gov.caixa.siiac.controller.security.SegurancaUsuario;
import br.gov.caixa.siiac.model.domain.Perfil;
import br.gov.caixa.siiac.security.IUsuario;
import br.gov.caixa.siiac.util.FilterBase;

/**
 * @author GisConsult
 *
 */
public class AbstractMB extends GenericStateMB implements IMessageCEF{
    
	private HttpSession httpSession = null;
	private FilterBase filterBase = null;

	public AbstractMB() 
    {
    	super();
    }

	public HttpSession getHttpSession(){
		if(FacesContext.getCurrentInstance() != null){
			httpSession =  (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		}
		return httpSession;
	}
	
	public HttpServletRequest getRequest(){
		return (HttpServletRequest) getExternalContext().getRequest();
	}
	
	public HttpServletResponse getResponse(){
		return (HttpServletResponse) getExternalContext().getResponse();
	}
	
    // --------------------------------------------------- Convenience Accessors


  /**
   * getApplication
   */
    protected Application getApplication() {

        return FacesContext.getCurrentInstance().getApplication();

    }


  /**
   * getApplicationMap
   * @return
   */
    protected Map getApplicationMap() {

        return getExternalContext().getApplicationMap();

    }


/**
 * getExternalContext
 * @return
 */
    protected ExternalContext getExternalContext() {

        return FacesContext.getCurrentInstance().getExternalContext();

    }

/**
 * getFacesContext
 * @return
 */
    protected FacesContext getFacesContext() {

        return FacesContext.getCurrentInstance();

    }


   /**
    * getLifecycle
    * @return
    */
    protected Lifecycle getLifecycle() {

        String lifecycleId =
            getExternalContext().getInitParameter("javax.faces.LIFECYCLE_ID");  //NOI18N
        if (lifecycleId == null || lifecycleId.length() == 0) {
            lifecycleId = LifecycleFactory.DEFAULT_LIFECYCLE;
        }
        LifecycleFactory lifecycleFactory = (LifecycleFactory)
            FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        return lifecycleFactory.getLifecycle(lifecycleId);

    }


    /**
     * getRequestMap
     * @return
     */
    protected Map getRequestMap() {

        return getExternalContext().getRequestMap();

    }


/**
 * getSessionMap
 * @return
 */
    protected Map getSessionMap() {

        return getExternalContext().getSessionMap();

    }


    // ------------------------------------------------------- Bean Manipulation



    /**
     * getBean
     */
    protected Object getBean(String name) {

        return getApplication().getVariableResolver().resolveVariable(getFacesContext(), name);

    }



    /**
     * setBean
     * @param name
     * @param value
     */
    protected void setBean(String name, Object value) {

        setValue("#{" + name + "}", value); //NOI18N

    }



    /**
     * getValue
     * @param expr
     * @return
     */
    protected Object getValue(String expr) {

        ValueBinding vb = getApplication().createValueBinding(expr);
        return vb.getValue(getFacesContext());

    }


    /**
     * <p>Evaluate the specified value binding expression, and update
     * the value that it points at.</p>
     *
     * @param expr Value binding expression (including delimiters) that
     *  must point at a writeable property
     * @param value New value for the property pointed at by <code>expr</code>
     */
    protected void setValue(String expr, Object value) {

        ValueBinding vb = getApplication().createValueBinding(expr);
        vb.setValue(getFacesContext(), value);

    }


    // -------------------------------------------------- Component Manipulation


    /**
     * <p>Erase previously submitted values for all input components on this
     * page.  This method <strong>MUST</strong> be called if you have bound
     * input components to database columns, and then arbitrarily navigate
     * the underlying <code>RowSet</code> to a different row in an event
     * handler method.</p>
     */
    protected void erase() {

        erase(getFacesContext().getViewRoot());

    }


    /**
     * <p>Private helper method for <code>erase()</code> that recursively
     * descends the component tree and performs the required processing.</p>
     *
     * @param component The component to be erased
     */
    private void erase(UIComponent component) {

        // Erase the component itself (if needed)
        if (component instanceof EditableValueHolder) {
            ((EditableValueHolder) component).setSubmittedValue(null);
        }
        // Process the facets and children of this component
        Iterator kids = component.getFacetsAndChildren();
        while (kids.hasNext()) {
            erase((UIComponent) kids.next());
        }

    }


    // --------------------------------------------------------- Message Methods

    /**
	 * getMessage
	 * @param resourceBundleName
	 * @param chave
	 * @param parametros
	 * @return
	 */
	public static String getMessage(String resourceBundleName, String chave, Object... parametros) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		String msg = ctx.getApplication().getResourceBundle(ctx, resourceBundleName).getString(chave);
		
		if (parametros.length > 0) {
			return MessageFormat.format(msg, parametros);
		} else {
			return msg;
		}
	}

    /**
     * <p>Enqueue a global <code>FacesMessage</code> (not associated
     * with any particular componen) containing the specified summary text
     * and a message severity level of <code>FacesMessage.SEVERITY_INFO</code>.
     * </p>
     *
     * @param summary Summary text for this message
     */
    protected void info(String summary) {
    	
    	getFacesContext().addMessage(null,
    			new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary));
    	
    }
    
    protected void info(String msgs, String value, Object ... param) {
    	info(getMessage(msgs, value, param));
    }

    /**
     * <p>Enqueue a <code>FacesMessage</code> associated with the
     * specified component, containing the specified summary text
     * and a message severity level of <code>FacesMessage.SEVERITY_INFO</code>.
     * </p>
     *
     * @param component Component with which this message is associated
     * @param summary Summary text for this message
     */
    protected void info(UIComponent component, String summary) {
        getFacesContext().addMessage(component.getClientId(getFacesContext()),
          new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }


    /**
     * <p>Enqueue a global <code>FacesMessage</code> (not associated
     * with any particular componen) containing the specified summary text
     * and a message severity level of <code>FacesMessage.SEVERITY_WARN</code>.
     * </p>
     *
     * @param summary Summary text for this message
     */
    protected void warn(String summary) {
        getFacesContext().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));
    }

    protected void warn(String msgs, String value, Object ... param) {
    	warn(getMessage(msgs, value, param));
    }

    /**
     * <p>Enqueue a <code>FacesMessage</code> associated with the
     * specified component, containing the specified summary text
     * and a message severity level of <code>FacesMessage.SEVERITY_WARN</code>.
     * </p>
     *
     * @param component Component with which this message is associated
     * @param summary Summary text for this message
     */
    protected void warn(UIComponent component, String summary) {

        getFacesContext().addMessage(component.getClientId(getFacesContext()),
          new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));

    }


    /**
     * <p>Enqueue a global <code>FacesMessage</code> (not associated
     * with any particular componen) containing the specified summary text
     * and a message severity level of <code>FacesMessage.SEVERITY_ERROR</code>.
     * </p>
     *
     * @param summary Summary text for this message
     */
    protected void error(String summary) {
        getFacesContext().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, summary));
    }
    
    protected void error(String msgs, String value, Object ... param) {
    	error(getMessage(msgs, value, param));
    }

    /**
     * <p>Enqueue a <code>FacesMessage</code> associated with the
     * specified component, containing the specified summary text
     * and a message severity level of <code>FacesMessage.SEVERITY_ERROR</code>.
     * </p>
     *
     * @param component Component with which this message is associated
     * @param summary Summary text for this message
     */
    protected void error(UIComponent component, String summary) {

        getFacesContext().addMessage(component.getClientId(getFacesContext()),
          new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));

    }


    /**
     * <p>Enqueue a global <code>FacesMessage</code> (not associated
     * with any particular componen) containing the specified summary text
     * and a message severity level of <code>FacesMessage.SEVERITY_FATAL</code>.
     * </p>
     *
     * @param summary Summary text for this message
     */
    protected void fatal(String summary) {

        getFacesContext().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, null));

    }

    protected void fatal(String msgs, String value, Object ... param) {
    	fatal(getMessage(msgs, value, param));
    }
    
    /**
     * <p>Enqueue a <code>FacesMessage</code> associated with the
     * specified component, containing the specified summary text
     * and a message severity level of <code>FacesMessage.SEVERITY_FATAL</code>.
     * </p>
     *
     * @param component Component with which this message is associated
     * @param summary Summary text for this message
     */
    protected void fatal(UIComponent component, String summary) {

        getFacesContext().addMessage(component.getClientId(getFacesContext()),
          new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, null));

    }

    // ------------------------------------------------------- Phase Processing



    /**
     * <p>Skip any remaining request processing lifecycle phases for the
     * current request, and go immediately to <em>Render Response</em>
     * phase.  This method is typically invoked when you want to throw
     * away input values provided by the user, instead of processing them.</p>
     */
    protected void renderResponse() {

        getFacesContext().renderResponse();

    }


    /**
     * <p>Skip any remaining request processing lifecycle phases for the
     * current request, including <em>Render Response</em> phase.  This is
     * appropriate if you have completed the response through some means
     * other than JavaServer Faces rendering.</p>
     */
    protected void responseComplete() {

        getFacesContext().responseComplete();

    }

    /**
     * findComponent
     * @param c
     * @param id
     * @return
     */
    private UIComponent findComponent(UIComponent c, String id) {
		if (id.equals(c.getId())) {
			return c;
		}
		Iterator<UIComponent> kids = c.getFacetsAndChildren();
		while (kids.hasNext()) {
			UIComponent found = findComponent(kids.next(), id);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

    /**
     * reRenderAll
     * @param componentId
     */
	protected void reRenderAll(String componentId) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();

		UIComponent c = findComponent(root, componentId);

		c.getChildren().clear();
	}
	
	protected IUsuario getUsuarioLogado(){
		return SegurancaUsuario.getInstance().getUsuario();
	}

	public boolean isGestorSistema() {
		return SegurancaUsuario.getInstance().verificaPerfil(Perfil.PERFIL_GESTOR_SISTEMA);
	}
	
	public boolean isRegionalConformidade() {
		return SegurancaUsuario.getInstance().verificaPerfil(Perfil.PERFIL_REGIONAL_CONFORMIDADE);
	}
	
	public boolean isVerificadorConformidade() {
		return SegurancaUsuario.getInstance().verificaPerfil(Perfil.PERFIL_VERIFICADOR_CONFORMIDADE);
	}
	
	public boolean isGestorProduto() {
		return SegurancaUsuario.getInstance().verificaPerfil(Perfil.PERFIL_GESTOR_PRODUTO);
	}
	
	public boolean isUnidadeAtendimento() {
		return SegurancaUsuario.getInstance().verificaPerfil(Perfil.PERFIL_UNIDADE_ATENDIMENTO);
	}
	
	public boolean isAuditor() {
		return SegurancaUsuario.getInstance().verificaPerfil(Perfil.PERFIL_AUDITOR);
	}
	
	public boolean isCentralAtendimentoSuporte() {
		return SegurancaUsuario.getInstance().verificaPerfil(Perfil.PERFIL_CENTRAL_ATENDIMENTO_SUPORTE);
	}
	
	public boolean isVerificadorRegional() {
		return SegurancaUsuario.getInstance().verificaPerfil(Perfil.PERFIL_VERIFICADOR_REGIONAL);
	}

	public boolean isVerificadorNacional() {
		return SegurancaUsuario.getInstance().verificaPerfil(Perfil.PERFIL_VERIFICADOR_NACIONAL);
	}
	
	public String getCaminhoRelatorio() {		
		return ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getServletContext().getRealPath("//reports//");
	}
	
	public void downloadRelatorio() throws IOException {
		byte[] b = (byte[])getSession().getAttribute("file"); 
		String nomeArquivo = (String) getSession().getAttribute("nomeFile");
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
		if (b != null) {
			response.getOutputStream().write(b);
			response.setHeader("Pragma", "");
			response.setHeader("Cache-Control", "");
			response.setHeader("Expires", "");
			
			response.setHeader("Content-disposition", "attachment; filename=" + nomeArquivo);
			response.setContentType("application/octet-stream");
			responseComplete();
		}
	}
	
	public HttpSession getSession(){
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		return request.getSession();
	}
	
	public void addRelatorioSessao(byte[] b, String nomeArquivo){
		getSession().setAttribute("file", b);
		getSession().setAttribute("nomeFile", nomeArquivo);
	}
	/**
	 * Retorna a instancia do objeto de filtragem<br/>
	 * Se não existir um objeto ele é criado.
	 * @return Objeto que guarda os parâmetros de filtragem.
	 */
	public FilterBase getFilterBase(){
		if(filterBase == null){
		filterBase = new FilterBase();
		}
		return filterBase;
	}
	

	
	/**
	 * Metodo que busca o filtro atual na sessão.
	 */
	public void getFilterInSession(Class classe){
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest(); 
		HttpSession session = request.getSession(); 
		filterBase = (FilterBase) session.getAttribute(classe.getName());
	}
	
	/**
	 * Metodo que coloca o filtro atual na sessão.
	 */
	public void putFilterBase(Class classe){
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest(); 
		HttpSession session = request.getSession(); 
		session.setAttribute(classe.getName(), filterBase);
	}
	
	
	
}
