/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

public enum TipoAllegato {
	RAPPORTO_TRASMISSIONE(1, "RAPTRASM", false), //
	VERBALE_ACCERTAMENTO(2, "VERBACC", false), //
	VERBALE_SEQUESTRO(3, "VERBSEQ", false), //
	RILIEVI_SEGNALETICI(4, "RILSEGN", false), //
	RELATA_NOTIFICA(5, "RELNOT", false), //
	SCRITTI_DIFENSIVI(6, "SCRITDIF", false), //
	RICEVUTA_PAGAMENTO_VERBALE(7, "RICPAGVERB", false), //
	ISTANZA_AUTOTUTELA(8, "ISTAUTO", false), //
	CONTRODEDUZIONE(9, "CONTRDED", false), //
	VERBALE_AUDIZIONE(10, "VERBAUDI", false), //
	ORDINANZA_ARCHIVIAZIONE(11, "ORDARCH", false), //
	ORDINANZA_INGIUNZIONE_PAGAMENTO(12, "ORDING", false), //
	OPPOSIZIONE_GIURISDIZIONALE(13, "OPPGIUR", false), //
	DISPOSIZIONE_DEL_GIUDICE(14, "DISPGIUD", false), //
	VERBALE_DI_CONTESTAZIONE(15, "VERBCOST", false), //
	VERBALE_DI_CONFISCA(16, "VERBCONF", false), //
	LETTERA_RATEIZZAZIONE(17, "LETRAT", false), //
	BOLLETTINI_RATEIZZAZIONE(18, "BOLRAT", false), //
	LETTERA_ORDINANZA(19, "LETORD", false), //
	LETTERA_SOLLECITO(20, "LETSOL", false), //
	BOLLETTINI_ORDINANZA_SOLLECITO(21, "BOLORD", false), //
	RICEVUTA_PAGAMENTO_ORDINANZA(22, "RICPAGORD", true), //
	COMPARSA(23, "COMPRS", false), //
	COMUNICAZIONI_DALLA_CANCELLERIA(24, "COMDACANC", false), //
	COMUNICAZIONI_ALLA_CANCELLERIA(25, "COMACANC", false), //
	ISTANZA_RATEIZZAZIONE(26, "ISTRAT", false), //
	CONVOCAZIONE_AUDIZIONE(27, "CONVAU", false), //
	COMPARSA_ALLEGATO(28, "COMALL", false),
	EMAIL_VERBALE(29, "EMAILVERB", true),
	EMAIL_ISTRUTTORIA(30, "EMAILISTR", true),
	EMAIL_GIURISDIZIONALE_ORD(31, "EMAILGIURORD", true),
	EMAIL_GIURISDIZIONALE_ORD_SOGG(32, "EMAILGIURORDS", true),
	EMAIL_RATEIZZAZIONE(33, "EMAILRATEIZ", true),
	ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE(34, "ORDANNARC", false),
	ORDINANZA_ANNULLAMENTO_INGIUNZIONE(35, "ORDANNING", false),
	LETTERA_SOLLECITO_RATE(36, "LETSOLRAT", false), //
	BOLLETTINI_ORDINANZA_SOLLECITO_RATE(37, "BOLSOLRAT", false), //
	ISTANZA_ALLEGATO(38, "ISTALL", false),
	ORDINANZA_ACCONTO(39, "ORDACC", false),

	COMUNICAZIONI_VARIE(40, "COMVARIE", true);

	private final long id;
	private final String tipoDocumento;
	private final boolean allegabileMultiplo;

	TipoAllegato(long id, String tipoDocumento, boolean allegabileMultiplo) {
		this.id = id;
		this.tipoDocumento = tipoDocumento;
		this.allegabileMultiplo = allegabileMultiplo;
	}

	public long getId() {
		return id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public boolean isAllegabileMultiplo() {
		return allegabileMultiplo;
	}

	public static TipoAllegato getTipoAllegatoByTipoDocumento(String tipoDocumento) {
		if (tipoDocumento == null)
			return null;
		for (TipoAllegato a : TipoAllegato.values())
			if (a.getTipoDocumento().equals(tipoDocumento))
				return a;

		throw new IllegalArgumentException("tipo allegato non trovato");
	}

	public static String getTipoDocumentoByIdTipoDocumento(Long idTipoDocumento) {
		TipoAllegato t = getTipoDocumentoById(idTipoDocumento);
		if (t == null)
			throw new IllegalArgumentException("tipo allegato non trovato");

		return t.getTipoDocumento();
	}

	public static TipoAllegato getTipoDocumentoById(Long id) {
		if (id == null)
			return null;
		for (TipoAllegato a : TipoAllegato.values())
			if (new Long(a.getId()).equals(id))
				return a;

		throw new IllegalArgumentException("tipo allegato non trovato");
	}

}
