/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.util;

import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public interface UtilsDate {
	

	default Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	default BigInteger getYear(Date date) {
		Calendar calendar = dateToCalendar(date);
		return calendar != null ? BigInteger.valueOf(calendar.get(Calendar.YEAR)) : null;
	}
	
	
	default Date asDate(LocalDate localDate) {
		if (localDate == null)
			return null;
		return Date.from(localDate.atStartOfDay().atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toInstant());
	}

	default Date asDate(LocalDateTime localDateTime) {
		if (localDateTime == null)
			return null;
		return Date.from(localDateTime.atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toInstant());
	}

	default LocalDate asLocalDate(Date date) {
		if (date == null)
			return null;
		return Instant.ofEpochMilli(date.getTime()).atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toLocalDate();
	}

	default LocalDateTime asLocalDateTime(Date date) {
		if (date == null)
			return null;
		return Instant.ofEpochMilli(date.getTime()).atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toLocalDateTime();
	}

	default Timestamp asTimeStamp(LocalDateTime localDateTime) {
		if (localDateTime == null)
			return null;
		return new Timestamp(Date.from(localDateTime.atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toInstant()).getTime());
	}

	default XMLGregorianCalendar asXMLGregorianCalendar(Calendar cal) {
		if (cal == null)
			return null;
		XMLGregorianCalendar xgc = getInstanceCalendar();
		xgc.setYear(cal.get(Calendar.YEAR));
		xgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
		xgc.setMonth(cal.get(Calendar.MONTH) + 1);
		xgc.setTime(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		return xgc;
	}

	default XMLGregorianCalendar asXMLGregorianCalendar(Timestamp timeStamp) {
		if (timeStamp == null)
			return null;
		LocalDateTime ldt = timeStamp.toLocalDateTime();
		XMLGregorianCalendar xgc = getInstanceCalendar();
		xgc.setYear(ldt.getYear());
		xgc.setDay(ldt.getDayOfMonth());
		xgc.setMonth(ldt.getMonthValue());
		xgc.setTime(ldt.getHour(), ldt.getMinute(), ldt.getSecond());
		return xgc;
	}

	default XMLGregorianCalendar asXMLGregorianCalendar(Date date) {
		if (date == null)
			return null;
		LocalDate ldt = asLocalDate(date);
		XMLGregorianCalendar xgc = getInstanceCalendar();
		xgc.setYear(ldt.getYear());
		xgc.setDay(ldt.getDayOfMonth());
		xgc.setMonth(ldt.getMonthValue());
		return xgc;
	}

	default XMLGregorianCalendar getInstanceCalendar() {
		XMLGregorianCalendar xgc = null;
		try {
			xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		} catch (DatatypeConfigurationException e) {

		}
		return xgc;
	}

	default Date add30DaysToDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 30);
		date = calendar.getTime();
		return date;
	}

	default Date getDate(String _data, String format) {
		Date data = null;
		try {
			if (_data == null) {

				return null;
			}

			if (format == null || format.trim().equals("")) {

				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			data = sdf.parse(_data);

			if (data != null && data.before(new Date(new GregorianCalendar(1880, Calendar.JANUARY, 1).getTimeInMillis()))) {

				return null;
			}
			if (data != null && data.after(new Date(new GregorianCalendar(2100, Calendar.JANUARY, 1).getTimeInMillis()))) {

				return null;
			}
		} catch (Exception e) {

		}

		return data;
	}

}
