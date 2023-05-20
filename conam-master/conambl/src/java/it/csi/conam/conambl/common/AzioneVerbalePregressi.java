/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

import it.csi.conam.conambl.integration.entity.CnmDMessaggio;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.vo.AzioneVO;
import it.csi.conam.conambl.vo.common.MessageVO;

public enum AzioneVerbalePregressi {
	ACQUISISCI(1L, "Acquisisci", Constants.STATO_VERBALE_ACQUISITO, false, null, null), //
	ACQUISISCI_CON_PAGAMENTO(2L, "Acquisisci con Pagamento", Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, false, null, null), //
	ACQUISISCI_CON_SCRITTI(3L, "Acquisisci con scritti difensivi", Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI, false, null, null), //
	ARCHIVIATO_IN_AUTOTUTELA(4L, "Archivia in Autotutela", Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA, false, null, null),//
	TERMINE_RECUPERO_PREGRESSO(5L, "Recupero pregresso terminato", Constants.STATO_VERBALE_FINE_PREGRESSO, true, null, null),//
	ORDINANZA(6L, "Ordinanza", Constants.STATO_VERBALE_ORDINANZA, false, null, null),//
	IN_ATTESA_VERIFICA_PAGAMENTO(7L, "In attesa verifica di pagamento", Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO, false, ErrorCode.WARNING_VERBALE_VERIFICA_PAGAMENTO, ErrorCode.CONFIRM_VERBALE_VERIFICA_PAGAMENTO), //
	ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO(8L, "Improcedibile per mancata identificazione", Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO, false, ErrorCode.WARNING_VERBALE_SOGGETTO_SENZA_CF, ErrorCode.CONFIRM_VERBALE_SOGGETTO_SENZA_CF); //
	
	private final Long id;
	private final String azione;
	private final Long idStatoAvanzabile;
	private final boolean listaIstruttori;

	// 20210924 PP - in caso di verbale con soggetti senza CF, imposto i messaggi da visualizzare per l'azione "Archivia per mancanza CF soggetto"
	private final String warningMessageCode;
	private final String confirmMessageCode;
	
	AzioneVerbalePregressi(Long id, String azione, Long idStatoAvanzabile, boolean listaIstruttori,
			String warningMessageCode, String confirmMessageCode) {
		this.id = id;
		this.azione = azione;
		this.idStatoAvanzabile = idStatoAvanzabile;
		this.listaIstruttori = listaIstruttori;
		this.warningMessageCode = warningMessageCode;
		this.confirmMessageCode = confirmMessageCode;
	}

	public Long getId() {
		return id;
	}





	public String getWarningMessageCode() {
		return warningMessageCode;
	}

	public String getConfirmMessageCode() {
		return confirmMessageCode;
	}

	public static AzioneVO getAzioneVerbaleByIdStato(Long idStato, String numeroVerbale, CnmDMessaggioRepository cnmDMessaggioRepository) {
		if (idStato == null)
			return null;
		for (AzioneVerbalePregressi a : AzioneVerbalePregressi.values()) {
			if (a.idStatoAvanzabile.equals(idStato)) {
				AzioneVO azione = new AzioneVO();
				azione.setDenominazione(a.azione);
				azione.setId(a.getId());
				azione.setListaIstruttoriEnable(a.isListaIstruttori());
				if(a.getWarningMessageCode()!=null) {
					CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(a.getWarningMessageCode());
					String msg=cnmDMessaggio.getDescMessaggio();
					azione.setWarningMessage(new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio()));
				}
				if(a.getConfirmMessageCode()!=null) {
					CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(a.getConfirmMessageCode());
					String msg=cnmDMessaggio.getDescMessaggio();
					msg = String.format(msg, numeroVerbale);
					azione.setConfirmMessage(new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio()));
				}
				return azione;
			}
		}

		return null;
	}


	public boolean isListaIstruttori() {
		return listaIstruttori;
	}
}
