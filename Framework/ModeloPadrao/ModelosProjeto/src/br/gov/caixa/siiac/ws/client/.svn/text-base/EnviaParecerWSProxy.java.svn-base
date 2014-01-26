package br.gov.caixa.siiac.ws.client;

public class EnviaParecerWSProxy implements br.gov.caixa.siiac.ws.client.EnviaParecerWS {
  private String _endpoint = null;
  private br.gov.caixa.siiac.ws.client.EnviaParecerWS enviaParecerWS = null;
  
  public EnviaParecerWSProxy() {
    _initEnviaParecerWSProxy();
  }
  
  public EnviaParecerWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initEnviaParecerWSProxy();
  }
  
  private void _initEnviaParecerWSProxy() {
    try {
      enviaParecerWS = (new br.gov.caixa.siiac.ws.client.EnviaParecerWSServiceLocator()).getEnviaParecerWSPort();
      if (enviaParecerWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)enviaParecerWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)enviaParecerWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (enviaParecerWS != null)
      ((javax.xml.rpc.Stub)enviaParecerWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public br.gov.caixa.siiac.ws.client.EnviaParecerWS getEnviaParecerWS() {
    if (enviaParecerWS == null)
      _initEnviaParecerWSProxy();
    return enviaParecerWS;
  }
  
  public void enviaParecer(java.lang.Integer arg0, java.lang.Short arg1, java.lang.Short arg2, java.lang.Short arg3) throws java.rmi.RemoteException{
    if (enviaParecerWS == null)
      _initEnviaParecerWSProxy();
    enviaParecerWS.enviaParecer(arg0, arg1, arg2, arg3);
  }
  
  
}