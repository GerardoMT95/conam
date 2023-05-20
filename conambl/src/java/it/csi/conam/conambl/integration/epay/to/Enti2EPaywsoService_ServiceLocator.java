/**
 * Enti2EPaywsoService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class Enti2EPaywsoService_ServiceLocator extends org.apache.axis.client.Service implements it.csi.conam.conambl.integration.epay.to.Enti2EPaywsoService_Service {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3830244847841234729L;

	public Enti2EPaywsoService_ServiceLocator() {
    }


    public Enti2EPaywsoService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Enti2EPaywsoService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Enti2EPaywsoServiceSOAP
    private java.lang.String Enti2EPaywsoServiceSOAP_address = "http://www.example.org/";

    public java.lang.String getEnti2EPaywsoServiceSOAPAddress() {
        return Enti2EPaywsoServiceSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Enti2EPaywsoServiceSOAPWSDDServiceName = "Enti2EPaywsoServiceSOAP";

    public java.lang.String getEnti2EPaywsoServiceSOAPWSDDServiceName() {
        return Enti2EPaywsoServiceSOAPWSDDServiceName;
    }

    public void setEnti2EPaywsoServiceSOAPWSDDServiceName(java.lang.String name) {
        Enti2EPaywsoServiceSOAPWSDDServiceName = name;
    }

    public it.csi.conam.conambl.integration.epay.to.Enti2EPaywsoService_PortType getEnti2EPaywsoServiceSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Enti2EPaywsoServiceSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEnti2EPaywsoServiceSOAP(endpoint);
    }

    public it.csi.conam.conambl.integration.epay.to.Enti2EPaywsoService_PortType getEnti2EPaywsoServiceSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.csi.conam.conambl.integration.epay.to.Enti2EPaywsoServiceSOAPStub _stub = new it.csi.conam.conambl.integration.epay.to.Enti2EPaywsoServiceSOAPStub(portAddress, this);
            _stub.setPortName(getEnti2EPaywsoServiceSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEnti2EPaywsoServiceSOAPEndpointAddress(java.lang.String address) {
        Enti2EPaywsoServiceSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.csi.conam.conambl.integration.epay.to.Enti2EPaywsoService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.csi.conam.conambl.integration.epay.to.Enti2EPaywsoServiceSOAPStub _stub = new it.csi.conam.conambl.integration.epay.to.Enti2EPaywsoServiceSOAPStub(new java.net.URL(Enti2EPaywsoServiceSOAP_address), this);
                _stub.setPortName(getEnti2EPaywsoServiceSOAPWSDDServiceName());
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
        if ("Enti2EPaywsoServiceSOAP".equals(inputPortName)) {
            return getEnti2EPaywsoServiceSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywsosrv", "Enti2EPaywsoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywsosrv", "Enti2EPaywsoServiceSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Enti2EPaywsoServiceSOAP".equals(portName)) {
            setEnti2EPaywsoServiceSOAPEndpointAddress(address);
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
