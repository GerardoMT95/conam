/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service.impl;

import it.csi.conam.conambl.integration.doqui.utils.DateFormat;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

import java.util.Date;


public class CommonManagementServiceImpl  {

//	public static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".integration";
//	private static Logger log = Logger.getLogger(LOGGER_PREFIX);
	private static Logger log = Logger.getLogger(CommonManagementServiceImpl.class);	
	
	
	private static int CACHE_TIME = 60*60*1000; //[MS]  1 ora sola di cache
	//private static int CACHE_TIME = 60*1000; //[MS]

	private Date lastCacheTime = null;

	public Date getLastCacheTime() {
		return lastCacheTime;
	}

	public void setLastCacheTime(Date lastCacheTime) {
		this.lastCacheTime = lastCacheTime;
	}
	
	public boolean isCacheTimeExpired(){
		String method = "isCacheTimeExpired";
		boolean expired = false;
		Date now = new Date(System.currentTimeMillis());
		if(log.isDebugEnabled()){
			log.debug(method + ". now           = " + DateFormat.format(now, DateFormat.DATE_FORMAT_DDMMYY_HHMMSS));
			log.debug(method + ". lastCacheTime = " + DateFormat.format(lastCacheTime.getTime(), DateFormat.DATE_FORMAT_DDMMYY_HHMMSS) );
			log.debug(method + ". CACHE_TIME    = " + CACHE_TIME + " [ms]");
		}
		
		if(now.getTime() > lastCacheTime.getTime() + CACHE_TIME) {
			log.debug(method + ". cache time expired: refreshing cache");
			expired = true;
			lastCacheTime = new Date(System.currentTimeMillis());
		}
		else{
			log.debug(method + ". cache time still valid");
		}
		
		return expired;
	}
	
	public void init() {
		String method = "init";
		try{
			setLastCacheTime(new Date(System.currentTimeMillis()));
			if(log.isDebugEnabled())	{
				log.debug(method + ". lastCacheTime            =  " + DateFormatUtils.format(getLastCacheTime(), "dd/MM/yyyy HH:mm:ss"));		
			}	
		}
		catch(Exception e){
			log.error(method + ". Exception " + e);
			throw new RuntimeException();
		}
	}
	

}
