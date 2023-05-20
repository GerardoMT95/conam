/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web.serializer;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author riccardo.bova
 * @date 16 mar 2018
 */
public class CustomDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	@Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
		String dateAsString = jsonParser.getText();
		if (StringUtils.isEmpty(dateAsString))
			return null;
		return LocalDateTime.parse(dateAsString, CustomDateTimeSerializer.FORMATTER);
	}
}
