/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author riccardo.bova
 * @date 24 ott 2018
 */
@Component
public class Config {

	@Value("${stadoc_service_endpoint_url}")
	private String stadocServiceEndpointUrl;
	@Value("${epay_service_endpoint_url}")
	private String epayServiceEndpointUrl;

	@Value("${send_allegati_acta}")
	private Boolean sendAllegatiToActa;

	@Value("${soris_sposta_doc}")
	private Boolean sorisSpostaDoc;

	public String getStadocServiceEndpointUrl() {
		return stadocServiceEndpointUrl;
	}

	public void setStadocServiceEndpointUrl(String stadocServiceEndpointUrl) {
		this.stadocServiceEndpointUrl = stadocServiceEndpointUrl;
	}

	public String getEpayServiceEndpointUrl() {
		return epayServiceEndpointUrl;
	}

	public void setEpayServiceEndpointUrl(String epayServiceEndpointUrl) {
		this.epayServiceEndpointUrl = epayServiceEndpointUrl;
	}

	public Boolean getSendAllegatiToActa() {
		return sendAllegatiToActa;
	}

	public void setSendAllegatiToActa(Boolean sendAllegatiToActa) {
		this.sendAllegatiToActa = sendAllegatiToActa;
	}

	public Boolean getSorisSpostaDoc() {
		return sorisSpostaDoc;
	}

	public void setSorisSpostaDoc(Boolean sorisSpostaDoc) {
		this.sorisSpostaDoc = sorisSpostaDoc;
	}
}
