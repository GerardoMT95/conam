/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web.serializer;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author riccardo.bova
 * @date 16 mar 2018
 */
public class CustomDateSerializer extends JsonSerializer<LocalDate> {

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter FORMATTER_SORIS = DateTimeFormatter.ofPattern("ddMMyy");
	public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("ddMMyyyy");
	public static final Locale LOCALE_HUNGARIAN = new Locale("it", "IT");
	public static final TimeZone LOCAL_TIME_ZONE = TimeZone.getTimeZone("Europe/Paris");

	@Override
	public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
		if (date == null) {
			jsonGenerator.writeNull();
		} else {
			jsonGenerator.writeString(date.format(FORMATTER));
		}

	}

}
