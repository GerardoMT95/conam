/**
 * StadocStadocSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class StadocStadocSoapBindingStub extends org.apache.axis.client.Stub implements it.csi.conam.conambl.integration.beans.StadocStadoc_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[16];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("testResources");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "testResourcesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("selfCheck");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "ArrayOf_tns2_CalledResource"), it.csi.conam.conambl.integration.beans.CalledResource[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "InvocationNode"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.InvocationNode.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "selfCheckReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("hasSelfCheck");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "hasSelfCheckReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("archiviaDocumentoLogico");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestArchiviaDocumentoLogico"), it.csi.conam.conambl.integration.beans.RequestArchiviaDocumentoLogico.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseArchiviaDocumento"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "archiviaDocumentoLogicoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.ArchiviaDocumentoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "ArchiviaDocumentoException"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("archiviaDocumentoFisico");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestArchiviaDocumentoFisico"), it.csi.conam.conambl.integration.beans.RequestArchiviaDocumentoFisico.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseArchiviaDocumento"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "archiviaDocumentoFisicoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.ArchiviaDocumentoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "ArchiviaDocumentoException"), 
                      true
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("protocollaDocumentoLogico");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestProtocollaDocumentoLogico"), it.csi.conam.conambl.integration.beans.RequestProtocollaDocumentoLogico.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseProtocollaDocumento"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "protocollaDocumentoLogicoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.ProtocollaDocumentoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "ProtocollaDocumentoException"), 
                      true
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("protocollaDocumentoFisico");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestProtocollaDocumentoFisico"), it.csi.conam.conambl.integration.beans.RequestProtocollaDocumentoFisico.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseProtocollaDocumento"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "protocollaDocumentoFisicoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.ProtocollaDocumentoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "ProtocollaDocumentoException"), 
                      true
                     ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("salvaDocumento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestSalvaDocumento"), it.csi.conam.conambl.integration.beans.RequestSalvaDocumento.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseSalvaDocumento"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseSalvaDocumento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "salvaDocumentoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SalvaDocumentoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SalvaDocumentoException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("eliminaDocumento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestEliminaDocumento"), it.csi.conam.conambl.integration.beans.RequestEliminaDocumento.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseEliminaDocumento"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseEliminaDocumento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "eliminaDocumentoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.EliminaDocumentoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "EliminaDocumentoException"), 
                      true
                     ));
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("salvaDocumentoLogico");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestSalvaDocumentoLogico"), it.csi.conam.conambl.integration.beans.RequestSalvaDocumentoLogico.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseSalvaDocumentoLogico"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseSalvaDocumentoLogico.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "salvaDocumentoLogicoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SalvaDocumentoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SalvaDocumentoException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cambiaStatoRichiesta");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestCambiaStatoRichiesta"), it.csi.conam.conambl.integration.beans.RequestCambiaStatoRichiesta.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseCambiaStatoRichiesta"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseCambiaStatoRichiesta.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "cambiaStatoRichiestaReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CambiaStatoRichiestaException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CambiaStatoRichiestaException"), 
                      true
                     ));
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("recuperaRiferimentoDocumentoFisico");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestRecuperaRiferimentoDocumentoFisico"), it.csi.conam.conambl.integration.beans.RequestRecuperaRiferimentoDocumentoFisico.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseRecuperaRiferimentoDocumentoFisico"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseRecuperaRiferimentoDocumentoFisico.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "recuperaRiferimentoDocumentoFisicoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.RecuperaRiferimentoDocumentoFisicoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "RecuperaRiferimentoDocumentoFisicoException"), 
                      true
                     ));
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaDocumento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestRicercaDocumento"), it.csi.conam.conambl.integration.beans.RequestRicercaDocumento.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseRicercaDocumento"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseRicercaDocumento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ricercaDocumentoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.RicercaDocumentoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "RicercaDocumentoException"), 
                      true
                     ));
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("associaDocumentoFisico");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestAssociaDocumentoFisico"), it.csi.conam.conambl.integration.beans.RequestAssociaDocumentoFisico.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseAssociaDocumentoFisico"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseAssociaDocumentoFisico.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "associaDocumentoFisicoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.AssociaDocumentoFisicoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "AssociaDocumentoFisicoException"), 
                      true
                     ));
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("aggiungiAllegato");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestAggiungiAllegato"), it.csi.conam.conambl.integration.beans.RequestAggiungiAllegato.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseAggiungiAllegato"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "aggiungiAllegatoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.AggiungiAllegatoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "AggiungiAllegatoException"), 
                      true
                     ));
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ricercaAllegato");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:stadocStadoc", "RequestRicercaAllegato"), it.csi.conam.conambl.integration.beans.RequestRicercaAllegato.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseRicercaAllegato"));
        oper.setReturnClass(it.csi.conam.conambl.integration.beans.ResponseRicercaAllegato.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ricercaAllegatoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.UnrecoverableException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.CSIException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.SystemException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "fault"),
                      "it.csi.conam.conambl.integration.stadoc.RicercaAllegatoException",
                      new javax.xml.namespace.QName("urn:stadocStadoc", "RicercaAllegatoException"), 
                      true
                     ));
        _operations[15] = oper;

    }

    public StadocStadocSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public StadocStadocSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public StadocStadocSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            /*java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;*/
            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "CalledResource");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.CalledResource.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "InvocationNode");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.InvocationNode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "Outcome");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.Outcome.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "ResourceType");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResourceType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "ArrayOf_tns2_CalledResource");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.CalledResource[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "CalledResource");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://tst-applogic.reteunitaria.piemonte.it/stadocApplStadocWsfad/services/stadocStadoc", "ArrayOf_tns2_InvocationNode");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.InvocationNode[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "InvocationNode");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "AggiungiAllegatoException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.AggiungiAllegatoException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ArchiviaDocumentoException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ArchiviaDocumentoException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "AssociaDocumentoFisicoException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.AssociaDocumentoFisicoException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "CambiaStatoRichiestaException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.CambiaStatoRichiestaException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "CommunicationException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.CommunicationException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "Context");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.Context.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "CSIException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.CSIException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "Documento");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.Documento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "DownloadDocumentoException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.DownloadDocumentoException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "EliminaDocumentoException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.EliminaDocumentoException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "Metadati");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.Metadati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "MetadatiAllegato");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.MetadatiAllegato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ProtocollaDocumentoException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ProtocollaDocumentoException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RecuperaRiferimentoDocumentoFisicoException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RecuperaRiferimentoDocumentoFisicoException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestAggiungiAllegato");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestAggiungiAllegato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestArchiviaDocumentoFisico");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestArchiviaDocumentoFisico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestArchiviaDocumentoLogico");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestArchiviaDocumentoLogico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestAssociaDocumentoFisico");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestAssociaDocumentoFisico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestCambiaStatoRichiesta");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestCambiaStatoRichiesta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestEliminaDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestEliminaDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestProtocollaDocumentoFisico");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestProtocollaDocumentoFisico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestProtocollaDocumentoLogico");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestProtocollaDocumentoLogico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestRecuperaRiferimentoDocumentoFisico");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestRecuperaRiferimentoDocumentoFisico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestRicercaAllegato");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestRicercaAllegato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestRicercaDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestRicercaDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestSalvaDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestSalvaDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RequestSalvaDocumentoLogico");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RequestSalvaDocumentoLogico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseAggiungiAllegato");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseArchiviaDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseAssociaDocumentoFisico");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseAssociaDocumentoFisico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseCambiaStatoRichiesta");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseCambiaStatoRichiesta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseEliminaDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseEliminaDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseProtocollaDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseRecuperaRiferimentoDocumentoFisico");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseRecuperaRiferimentoDocumentoFisico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseRicercaAllegato");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseRicercaAllegato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseRicercaDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseRicercaDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseSalvaDocumento");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseSalvaDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseSalvaDocumentoLogico");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.ResponseSalvaDocumentoLogico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RicercaAllegatoException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RicercaAllegatoException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "RicercaDocumentoException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.RicercaDocumentoException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "SalvaDocumentoException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.SalvaDocumentoException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "Soggetto");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.Soggetto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.SystemException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "UnrecoverableException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.UnrecoverableException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:stadocStadoc", "UserException");
            cachedSerQNames.add(qName);
            cls = it.csi.conam.conambl.integration.beans.UserException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public boolean testResources() throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.CSIException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "testResources"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.InvocationNode selfCheck(it.csi.conam.conambl.integration.beans.CalledResource[] in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.CSIException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "selfCheck"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.InvocationNode) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.InvocationNode) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.InvocationNode.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public boolean hasSelfCheck() throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.CSIException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "hasSelfCheck"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento archiviaDocumentoLogico(it.csi.conam.conambl.integration.beans.RequestArchiviaDocumentoLogico in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.ArchiviaDocumentoException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "archiviaDocumentoLogico"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.ArchiviaDocumentoException) {
              throw (it.csi.conam.conambl.integration.beans.ArchiviaDocumentoException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento archiviaDocumentoFisico(it.csi.conam.conambl.integration.beans.RequestArchiviaDocumentoFisico in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.ArchiviaDocumentoException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "archiviaDocumentoFisico"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.ArchiviaDocumentoException) {
              throw (it.csi.conam.conambl.integration.beans.ArchiviaDocumentoException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento protocollaDocumentoLogico(it.csi.conam.conambl.integration.beans.RequestProtocollaDocumentoLogico in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.ProtocollaDocumentoException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "protocollaDocumentoLogico"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.ProtocollaDocumentoException) {
              throw (it.csi.conam.conambl.integration.beans.ProtocollaDocumentoException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento protocollaDocumentoFisico(it.csi.conam.conambl.integration.beans.RequestProtocollaDocumentoFisico in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.ProtocollaDocumentoException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "protocollaDocumentoFisico"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.ProtocollaDocumentoException) {
              throw (it.csi.conam.conambl.integration.beans.ProtocollaDocumentoException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseSalvaDocumento salvaDocumento(it.csi.conam.conambl.integration.beans.RequestSalvaDocumento in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SalvaDocumentoException, it.csi.conam.conambl.integration.beans.SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "salvaDocumento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseSalvaDocumento) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseSalvaDocumento) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseSalvaDocumento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SalvaDocumentoException) {
              throw (it.csi.conam.conambl.integration.beans.SalvaDocumentoException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseEliminaDocumento eliminaDocumento(it.csi.conam.conambl.integration.beans.RequestEliminaDocumento in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.EliminaDocumentoException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "eliminaDocumento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseEliminaDocumento) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseEliminaDocumento) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseEliminaDocumento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.EliminaDocumentoException) {
              throw (it.csi.conam.conambl.integration.beans.EliminaDocumentoException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseSalvaDocumentoLogico salvaDocumentoLogico(it.csi.conam.conambl.integration.beans.RequestSalvaDocumentoLogico in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SalvaDocumentoException, it.csi.conam.conambl.integration.beans.SystemException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "salvaDocumentoLogico"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseSalvaDocumentoLogico) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseSalvaDocumentoLogico) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseSalvaDocumentoLogico.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SalvaDocumentoException) {
              throw (it.csi.conam.conambl.integration.beans.SalvaDocumentoException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseCambiaStatoRichiesta cambiaStatoRichiesta(it.csi.conam.conambl.integration.beans.RequestCambiaStatoRichiesta in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.CambiaStatoRichiestaException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "cambiaStatoRichiesta"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseCambiaStatoRichiesta) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseCambiaStatoRichiesta) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseCambiaStatoRichiesta.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CambiaStatoRichiestaException) {
              throw (it.csi.conam.conambl.integration.beans.CambiaStatoRichiestaException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseRecuperaRiferimentoDocumentoFisico recuperaRiferimentoDocumentoFisico(it.csi.conam.conambl.integration.beans.RequestRecuperaRiferimentoDocumentoFisico in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.RecuperaRiferimentoDocumentoFisicoException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "recuperaRiferimentoDocumentoFisico"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseRecuperaRiferimentoDocumentoFisico) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseRecuperaRiferimentoDocumentoFisico) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseRecuperaRiferimentoDocumentoFisico.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.RecuperaRiferimentoDocumentoFisicoException) {
              throw (it.csi.conam.conambl.integration.beans.RecuperaRiferimentoDocumentoFisicoException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseRicercaDocumento ricercaDocumento(it.csi.conam.conambl.integration.beans.RequestRicercaDocumento in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.RicercaDocumentoException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "ricercaDocumento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseRicercaDocumento) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseRicercaDocumento) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseRicercaDocumento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.RicercaDocumentoException) {
              throw (it.csi.conam.conambl.integration.beans.RicercaDocumentoException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseAssociaDocumentoFisico associaDocumentoFisico(it.csi.conam.conambl.integration.beans.RequestAssociaDocumentoFisico in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.AssociaDocumentoFisicoException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "associaDocumentoFisico"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseAssociaDocumentoFisico) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseAssociaDocumentoFisico) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseAssociaDocumentoFisico.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.AssociaDocumentoFisicoException) {
              throw (it.csi.conam.conambl.integration.beans.AssociaDocumentoFisicoException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato aggiungiAllegato(it.csi.conam.conambl.integration.beans.RequestAggiungiAllegato in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.AggiungiAllegatoException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "aggiungiAllegato"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.AggiungiAllegatoException) {
              throw (it.csi.conam.conambl.integration.beans.AggiungiAllegatoException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.csi.conam.conambl.integration.beans.ResponseRicercaAllegato ricercaAllegato(it.csi.conam.conambl.integration.beans.RequestRicercaAllegato in0) throws java.rmi.RemoteException, it.csi.conam.conambl.integration.beans.UnrecoverableException, it.csi.conam.conambl.integration.beans.CSIException, it.csi.conam.conambl.integration.beans.SystemException, it.csi.conam.conambl.integration.beans.RicercaAllegatoException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://stadoc.interfacecsi.stadoc.stacore.csi.it", "ricercaAllegato"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {in0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.csi.conam.conambl.integration.beans.ResponseRicercaAllegato) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.csi.conam.conambl.integration.beans.ResponseRicercaAllegato) org.apache.axis.utils.JavaUtils.convert(_resp, it.csi.conam.conambl.integration.beans.ResponseRicercaAllegato.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.UnrecoverableException) {
              throw (it.csi.conam.conambl.integration.beans.UnrecoverableException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.CSIException) {
              throw (it.csi.conam.conambl.integration.beans.CSIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.SystemException) {
              throw (it.csi.conam.conambl.integration.beans.SystemException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.csi.conam.conambl.integration.beans.RicercaAllegatoException) {
              throw (it.csi.conam.conambl.integration.beans.RicercaAllegatoException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
