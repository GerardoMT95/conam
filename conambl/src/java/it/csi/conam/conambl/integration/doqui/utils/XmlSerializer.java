/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.Reader;

public class XmlSerializer {

	private static XStream xstream = new XStream(new DomDriver());


//	public static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".util";
//	private static Logger log = Logger.getLogger(LOGGER_PREFIX);	
	private static Logger log = Logger.getLogger(XmlSerializer.class);
	
	public XmlSerializer(){

	}
	public static String objectToXml(Object obj){
		String method = "objectToXml";
		String s = null;
		try{
			s= xstream.toXML(obj);
		}
		catch(Exception e){
			log.error(method + ". objectToXml error " + e.getMessage());
		}
		return s;
	}

	public static Object xmlToObject(String xml){
		String method = "xmlToObject";
		Object obj=null;
		try{
		obj  = xstream.fromXML(xml);
		}
		catch(Exception e){
			log.error(method + ". xmlToObject error " + e.getMessage());
		}
		return obj;
	}
	public static Object xmlToObject(InputStream is){
		String method = "xmlToObject";
		Object obj=null;
		try{
			obj = xstream.fromXML(is);
		}
		catch(Exception e){
			log.error(method + ". xmlToObject error " + e.getMessage());
		}
		return obj;
	}
	public static Object xmlToObject(Reader reader){
		String method = "xmlToObject";
		Object obj=null;
		try{
			obj = xstream.fromXML(reader);
		}
		catch(Exception e){
			log.error(method + ". xmlToObject error " + e.getMessage());
		}
		return obj;
	}

}
