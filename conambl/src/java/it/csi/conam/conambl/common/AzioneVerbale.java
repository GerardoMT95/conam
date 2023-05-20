/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

import it.csi.conam.conambl.integration.entity.CnmDMessaggio;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.vo.AzioneVO;
import it.csi.conam.conambl.vo.common.MessageVO;



public enum AzioneVerbale {
	
	ACQUISISCI(1L, "Acquisisci", Constants.STATO_VERBALE_ACQUISITO, true, ErrorCode.WARNING_VERBALE_ACQUISISCI, null), //
	ACQUISISCI_CON_PAGAMENTO(2L, "Acquisisci con Pagamento", Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, true, ErrorCode.WARNING_VERBALE_ACQUISISCI, null), //
	ACQUISISCI_CON_SCRITTI(3L, "Acquisisci con scritti difensivi", Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI, true, ErrorCode.WARNING_VERBALE_ACQUISISCI, null), //
	ARCHIVIATO_IN_AUTOTUTELA(4L, "Archivia in Autotutela", Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA, false, null, null),//
	ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO(5L, "Improcedibile per mancata identificazione", Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO, false, ErrorCode.WARNING_VERBALE_SOGGETTO_SENZA_CF, ErrorCode.CONFIRM_VERBALE_SOGGETTO_SENZA_CF), //
	IN_ATTESA_VERIFICA_PAGAMENTO(6L, "In attesa verifica di pagamento", Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO, false, ErrorCode.WARNING_VERBALE_VERIFICA_PAGAMENTO, ErrorCode.CONFIRM_VERBALE_VERIFICA_PAGAMENTO); //
		
	private final Long id;
	private final String azione;
	private final Long idStatoAvanzabile;
	private final boolean listaIstruttori;
	
	// 20210908 PP - in caso di verbale con soggetti senza CF, imposto i messaggi da visualizzare per l'azione "Archivia per mancanza CF soggetto"
	private final String warningMessageCode;
	private final String confirmMessageCode;

	AzioneVerbale(Long id, String azione, Long idStatoAvanzabile, boolean listaIstruttori,
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

	public static AzioneVO getAzioneVerbaleByIdStato(Long idStato, String numeroVerbale, CnmDMessaggioRepository cnmDMessaggioRepository) {
		if (idStato == null)
			return null;
		for (AzioneVerbale a : AzioneVerbale.values()) {
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


	public String getWarningMessageCode() {
		return warningMessageCode;
	}

	public String getConfirmMessageCode() {
		return confirmMessageCode;
	}

	public boolean isListaIstruttori() {
		return listaIstruttori;
	}
	


}
