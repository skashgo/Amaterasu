/**
 * EnviaParecerWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.caixa.siiac.ws.client;

public class EnviaParecerWSServiceLocator extends org.apache.axis.client.Service implements br.gov.caixa.siiac.ws.client.EnviaParecerWSService {

    public EnviaParecerWSServiceLocator() {
    }


    public EnviaParecerWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EnviaParecerWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EnviaParecerWSPort
    private java.lang.String EnviaParecerWSPort_address = "http://PC-DEV-COLEBRUSCO:8080/SIIAC-WS/EnviaParecerWS";

    public java.lang.String getEnviaParecerWSPortAddress() {
        return EnviaParecerWSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EnviaParecerWSPortWSDDServiceName = "EnviaParecerWSPort";

    public java.lang.String getEnviaParecerWSPortWSDDServiceName() {
        return EnviaParecerWSPortWSDDServiceName;
    }

    public void setEnviaParecerWSPortWSDDServiceName(java.lang.String name) {
        EnviaParecerWSPortWSDDServiceName = name;
    }

    public br.gov.caixa.siiac.ws.client.EnviaParecerWS getEnviaParecerWSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EnviaParecerWSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEnviaParecerWSPort(endpoint);
    }

    public br.gov.caixa.siiac.ws.client.EnviaParecerWS getEnviaParecerWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            br.gov.caixa.siiac.ws.client.EnviaParecerWSBindingStub _stub = new br.gov.caixa.siiac.ws.client.EnviaParecerWSBindingStub(portAddress, this);
            _stub.setPortName(getEnviaParecerWSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEnviaParecerWSPortEndpointAddress(java.lang.String address) {
        EnviaParecerWSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (br.gov.caixa.siiac.ws.client.EnviaParecerWS.class.isAssignableFrom(serviceEndpointInterface)) {
                br.gov.caixa.siiac.ws.client.EnviaParecerWSBindingStub _stub = new br.gov.caixa.siiac.ws.client.EnviaParecerWSBindingStub(new java.net.URL(EnviaParecerWSPort_address), this);
                _stub.setPortName(getEnviaParecerWSPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("EnviaParecerWSPort".equals(inputPortName)) {
            return getEnviaParecerWSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.siiac.caixa.gov.br/", "EnviaParecerWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.siiac.caixa.gov.br/", "EnviaParecerWSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EnviaParecerWSPort".equals(portName)) {
            setEnviaParecerWSPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
