/**
 * StadocStadocServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class StadocStadocServiceLocator extends org.apache.axis.client.Service implements it.csi.conam.conambl.integration.beans.StadocStadocService {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8689134040412488399L;

	public StadocStadocServiceLocator() {
    }


    public StadocStadocServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StadocStadocServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for stadocStadoc
    private java.lang.String stadocStadoc_address = "http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc";

    public java.lang.String getstadocStadocAddress() {
        return stadocStadoc_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String stadocStadocWSDDServiceName = "stadocStadoc";

    public java.lang.String getstadocStadocWSDDServiceName() {
        return stadocStadocWSDDServiceName;
    }

    public void setstadocStadocWSDDServiceName(java.lang.String name) {
        stadocStadocWSDDServiceName = name;
    }

    public it.csi.conam.conambl.integration.beans.StadocStadoc_PortType getstadocStadoc() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(stadocStadoc_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getstadocStadoc(endpoint);
    }

    public it.csi.conam.conambl.integration.beans.StadocStadoc_PortType getstadocStadoc(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.csi.conam.conambl.integration.beans.StadocStadocSoapBindingStub _stub = new it.csi.conam.conambl.integration.beans.StadocStadocSoapBindingStub(portAddress, this);
            _stub.setPortName(getstadocStadocWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setstadocStadocEndpointAddress(java.lang.String address) {
        stadocStadoc_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.csi.conam.conambl.integration.beans.StadocStadoc_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.csi.conam.conambl.integration.beans.StadocStadocSoapBindingStub _stub = new it.csi.conam.conambl.integration.beans.StadocStadocSoapBindingStub(new java.net.URL(stadocStadoc_address), this);
                _stub.setPortName(getstadocStadocWSDDServiceName());
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
        if ("stadocStadoc".equals(inputPortName)) {
            return getstadocStadoc();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "stadocStadocService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "stadocStadoc"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("stadocStadoc".equals(portName)) {
            setstadocStadocEndpointAddress(address);
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
