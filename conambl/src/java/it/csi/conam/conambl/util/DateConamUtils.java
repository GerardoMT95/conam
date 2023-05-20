/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//import com.sun.jmx.snmp.Timestamp;

/**
 * @author riccardo.bova
 * @date 29 nov 2018
 */
public class DateConamUtils {

	public static Integer getDayBetweenDates(Date date1, Date date2) {
		date1 = getStartOfTheDay(date1);
		date2 = getStartOfTheDay(date2);

		LocalDate dataPromemoria = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dataUdienza = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		long noOfDaysBetween = ChronoUnit.DAYS.between(dataPromemoria, dataUdienza);
		Integer daysBetween = (int) Math.floor(noOfDaysBetween);

		return daysBetween;
	}

	public static Date convertToDate(LocalDateTime dateToConvert) {
	    return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date convertToDate(LocalDate dateToConvert) {
		Date result = null;
		if (dateToConvert != null)
			result = java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		return result;
	}

	
	public static Date addDaysToDate(Date date, Integer days) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date subtractDaysToDate(Date date, Integer days) {
		return addDaysToDate(date,(-1)*days);
	}
	

	public static Date subtractDaysToDate(Timestamp datetime, Integer days) {
        Date date=new Date(datetime.getTime()); 
		return subtractDaysToDate(date,days);
	}
	
	public static Date getStartOfTheDay(Date inputDate) {
		Calendar date = new GregorianCalendar();
		date.setTime(inputDate);
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	}
	public static Date getEndOfTheDay(Date inputDate) {
		Calendar date = new GregorianCalendar();
		date.setTime(inputDate);
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);
		date.set(Calendar.MILLISECOND, 999);
		return date.getTime();
	}
}
