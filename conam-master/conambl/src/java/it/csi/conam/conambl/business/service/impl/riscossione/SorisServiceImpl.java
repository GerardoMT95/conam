/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.riscossione;

import it.csi.conam.conambl.business.service.riscossione.SorisService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.repositories.CnmTRecordRepository;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author riccardo.bova
 * @date 15 apr 2019
 */
@Service
public class SorisServiceImpl implements SorisService {

	@Autowired
	private CnmTRecordRepository cnmTRecordRepository;

	@Autowired
	private UtilsDate utilsDate;

	private BigDecimal HUNDRED = new BigDecimal("100");

	@Override
	@Transactional
	public InputStream creaTracciato(CnmTFile cnmTFile) {
		InputStream inputStream = null;
		Integer n1 = 0, n2 = 0, n3 = 0, n4 = 0, n5 = 0;
		BigDecimal totale = BigDecimal.ZERO;
		List<CnmTRecord> records = cnmTRecordRepository.findByCnmTFileOrderByCodicePartitaAscOrdineAsc(cnmTFile);

		DecimalFormat df = new DecimalFormat("#");

		try {

			File tempFile = File.createTempFile("tracciato", ".tmp");

			StringBuilder builder = new StringBuilder();

			// NO
			builder.append("N0");
			builder.append("73348");
			builder.append(LocalDate.now().format(DateTimeFormatter.ofPattern("uuuuMMdd")));
			builder.append("SD");
			builder.append(StringUtils.rightPad("", 10, " "));
			builder.append(StringUtils.rightPad("", 10, "0"));
			builder.append(StringUtils.rightPad("", 253, " "));
			builder.append("\n");
			// N1
			builder.append("N1");
			builder.append("073348");
			builder.append("01");
			builder.append("4");
			builder.append("0000");
			builder.append("01");
			builder.append("1");
			builder.append("0000");
			builder.append("0");
			builder.append(StringUtils.rightPad("", 18, " "));
			builder.append(" ");
			builder.append("00");
			builder.append(" ");
			builder.append(StringUtils.rightPad("", 245, " "));
			builder.append("\n");
			FileUtils.writeStringToFile(tempFile, builder.toString(), Charset.defaultCharset());
			n1++;

			// N2
			builder = new StringBuilder();

			Map<String, ArrayList<Integer>> mapN2 = new HashMap<>();
			Map<String, ArrayList<Integer>> mapN3 = new HashMap<>();
			Map<String, ArrayList<Integer>> mapN4 = new HashMap<>();

			for (CnmTRecord e : records) {
				if (Constants.ID_TIPO_RECORD_N2.equals(e.getCnmDTipoRecord().getIdTipoRecord())) {
					mapN2.put(e.getCodicePartita(), new ArrayList<Integer>());
				} else if (Constants.ID_TIPO_RECORD_N3.equals(e.getCnmDTipoRecord().getIdTipoRecord())) {
					mapN3.put(e.getCodicePartita(), new ArrayList<Integer>());
				} else if (Constants.ID_TIPO_RECORD_N4.equals(e.getCnmDTipoRecord().getIdTipoRecord())) {
					mapN4.put(e.getCodicePartita(), new ArrayList<Integer>());
				} else
					throw new IllegalArgumentException("Tipo tributo non riconosciuto");
			}

			for (CnmTRecord e : records) {
				if (Constants.ID_TIPO_RECORD_N2.equals(e.getCnmDTipoRecord().getIdTipoRecord())) {
					if (mapN2.containsKey(e.getCodicePartita())) {
						mapN2.get(e.getCodicePartita()).add(e.getIdRecord());
					} else
						throw new IllegalArgumentException("Codice Partita non trovato");

				} else if (Constants.ID_TIPO_RECORD_N3.equals(e.getCnmDTipoRecord().getIdTipoRecord())) {
					if (mapN3.containsKey(e.getCodicePartita())) {
						mapN3.get(e.getCodicePartita()).add(e.getIdRecord());
					} else
						throw new IllegalArgumentException("Codice Partita non trovato");
				} else if (Constants.ID_TIPO_RECORD_N4.equals(e.getCnmDTipoRecord().getIdTipoRecord())) {
					if (mapN4.containsKey(e.getCodicePartita())) {
						mapN4.get(e.getCodicePartita()).add(e.getIdRecord());
					} else
						throw new IllegalArgumentException("Codice Partita non trovato");
				} else
					throw new IllegalArgumentException("Tipo tributo non riconosciuto");
			}

			for (Map.Entry<String, ArrayList<Integer>> entry : mapN2.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				if (entry.getValue().size() > 0) {
					for (Integer idN2 : entry.getValue()) {
						CnmTRecord eN2 = cnmTRecordRepository.findOne(idN2);
						n2 = this.addRigaN2(builder, eN2, n2);
						builder.append("\n");

						if (mapN3.containsKey(entry.getKey())) {
							List<Integer> rigaN3 = mapN3.get(entry.getKey());
							if (rigaN3.size() > 0) {
								for (Integer id : rigaN3) {
									CnmTRecord eN3 = cnmTRecordRepository.findOne(id);
									n3 = this.addRigaN3(builder, eN3, n3);
									builder.append("\n");
								}
							}

						}

						if (mapN4.containsKey(entry.getKey())) {
							List<Integer> rigaN4 = mapN4.get(entry.getKey());
							if (rigaN4.size() > 0) {
								for (Integer id : rigaN4) {
									CnmTRecord eN4 = cnmTRecordRepository.findOne(id);
									CnmTRecordN4 imposta = eN4.getCnmTRecordN4();
									BigDecimal cImposta = imposta.getImposta().multiply(HUNDRED);
									totale = totale.add(cImposta);
									n4 = this.addRigaN4(builder, imposta, n4, df, eN4);
									builder.append("\n");
								}

							}

						}
					}

				}

			}

			FileUtils.writeStringToFile(tempFile, builder.toString(), Charset.defaultCharset(), true);

			// N5
			Integer totaleN2N3N4 = n2 + n3 + n4;
			builder = new StringBuilder();
			builder.append("N5");
			builder.append("073348");
			builder.append("01");
			builder.append(StringUtils.leftPad("" + totaleN2N3N4.toString(), 7, "0"));
			builder.append(StringUtils.leftPad("" + n2, 7, "0"));
			builder.append(StringUtils.leftPad("" + n3, 7, "0"));
			builder.append(StringUtils.leftPad("" + n4, 7, "0"));

			String impostaTotale = df.format(totale);
			builder.append(StringUtils.leftPad(impostaTotale, 15, "0")); // totale
																			// imposta
			builder.append(StringUtils.rightPad("", 237, " "));
			builder.append("\n");
			n5++;

			// N9
			Integer totaleN = n1 + n2 + n3 + n4 + n5;
			builder.append("N9");
			builder.append("73348");
			builder.append(StringUtils.leftPad("" + totaleN.toString(), 7, "0"));
			builder.append(StringUtils.leftPad("" + n1, 7, "0"));
			builder.append(StringUtils.leftPad("" + n2, 7, "0"));
			builder.append(StringUtils.leftPad("" + n3, 7, "0"));
			builder.append(StringUtils.leftPad("" + n4, 7, "0"));
			builder.append(StringUtils.leftPad("" + n5, 7, "0"));
			builder.append(StringUtils.rightPad("", 241, " "));

			FileUtils.writeStringToFile(tempFile, builder.toString(), Charset.defaultCharset(), true);

			inputStream = new FileInputStream(tempFile);

		} catch (IOException e) {
			throw new RuntimeException("Errore durante la creazione del tracciato", e);
		}

		return inputStream;
	}

	private Integer addRigaN2(StringBuilder builder, CnmTRecord e, Integer n2) {
		CnmTRecordN2 trasgressore = e.getCnmTRecordN2();
		n2++;
		builder.append("N2"); // tipo record
		builder.append("073348"); // codice iscrizione comune
		builder.append("01"); // progressivo minuta
		builder.append(e.getCodicePartita()); // codice
												// partita
		builder.append(StringUtils.rightPad(trasgressore.getIdentificativoSoggetto(), 16, " ")); // codice
																									// fiscale
		builder.append(StringUtils.rightPad("", 8, "0")); // numero
															// contribuente
		builder.append("00"); // codice di controllo numero
								// contribuente
		builder.append("000000"); // codice indirizzo

		String indirizzo = trasgressore.getIndirizzo();

		indirizzo = Normalizer.normalize(indirizzo, Normalizer.Form.NFD);
		String resultIndirizzo = indirizzo.replaceAll("[^\\x00-\\x7F]", "");

		// indirizzo
		builder.append(StringUtils.rightPad(resultIndirizzo, 30, " "));
		// civico
		String civico = "0";
		if (trasgressore.getNumCivico() != null)
			civico = trasgressore.getNumCivico().intValue() + "";
		builder.append(StringUtils.leftPad("" + civico, 5, "0"));
		// lettera numero civico
		builder.append(StringUtils.leftPad(StringUtils.defaultString(trasgressore.getLetteraNumCivico()), 2, " "));
		// km
		builder.append(StringUtils.rightPad("", 6, "0"));
		// cap
		builder.append(StringUtils.leftPad(trasgressore.getCap(), 5, "0"));
		// codice belfiore
		builder.append(StringUtils.leftPad(trasgressore.getCodBelfioreComune(), 4, " "));
		// località
		builder.append(StringUtils.rightPad(trasgressore.getLocalitaFrazione(), 21, " "));

		// codice indirizzo
		builder.append("000000");
		// indirizzo
		builder.append(StringUtils.rightPad("", 30, " "));
		// civico
		builder.append(StringUtils.rightPad("", 5, "0"));
		// lettera numero civico
		builder.append("  ");
		// km
		builder.append(StringUtils.rightPad("", 6, "0"));
		// cap
		builder.append(StringUtils.leftPad("", 5, "0"));
		// codice belfiore
		builder.append(StringUtils.leftPad("", 4, " "));
		// località
		builder.append(StringUtils.leftPad("", 21, " "));
		builder.append(trasgressore.getSocieta());
		if (BigDecimal.ONE.equals(trasgressore.getSocieta())) {
			builder.append(StringUtils.rightPad(trasgressore.getCognome(), 24, " "));
			builder.append(StringUtils.rightPad(trasgressore.getNome(), 20, " "));
			builder.append(trasgressore.getSesso());
			builder.append(trasgressore.getDataNascita());
			builder.append(StringUtils.leftPad(trasgressore.getCodBelfioreNascita(), 4, " "));
			builder.append(StringUtils.leftPad("", 19, " "));
		} else {
			builder.append(StringUtils.rightPad(trasgressore.getDenomSocieta(), 76, " "));
		}
		builder.append(trasgressore.getPresenzaObbligato()); // C/N
		builder.append(StringUtils.leftPad("", 4, " ")); // filler

		return n2;
	}

	private Integer addRigaN3(StringBuilder builder, CnmTRecord e, Integer n3) {
		CnmTRecordN3 coobbligati = e.getCnmTRecordN3();
		n3++;
		builder.append("N3"); // tipo record
		builder.append("073348"); // codice iscrizione comune
		builder.append("01"); // progressivo minuta
		builder.append(e.getCodicePartita()); // codice
												// partita
		builder.append(StringUtils.rightPad(coobbligati.getIdentificativoSoggetto(), 16, " ")); // codice
																								// fiscale
		// builder.append(StringUtils.rightPad("", 6, "0")); // numero
		// contribuente
		// builder.append("00"); // codice di controllo numero
		// contribuente
		builder.append("000000"); // codice indirizzo
		// indirizzo
		builder.append(StringUtils.rightPad(coobbligati.getIndirizzo(), 30, " "));
		// civico
		String civico = "0";
		if (coobbligati.getNumCivico() != null)
			civico = coobbligati.getNumCivico().intValue() + "";
		builder.append(StringUtils.leftPad("" + civico, 5, "0"));
		// lettera numero civico
		builder.append(StringUtils.rightPad(StringUtils.defaultString(coobbligati.getLetteraNumCivico()), 2, " "));
		// km
		builder.append(StringUtils.rightPad("", 6, "0"));
		// cap
		builder.append(StringUtils.leftPad(coobbligati.getCap(), 5, "0"));
		// codice belfiore
		builder.append(StringUtils.rightPad(coobbligati.getCodBelfioreComune(), 4, " "));
		// località
		builder.append(StringUtils.rightPad(coobbligati.getLocalitaFrazione(), 21, " "));

		// codice indirizzo
		builder.append("000000");
		// indirizzo
		builder.append(StringUtils.rightPad("", 30, " "));
		// civico
		builder.append(StringUtils.rightPad("", 5, "0"));
		// lettera numero civico
		builder.append("  ");
		// km
		builder.append(StringUtils.rightPad("", 6, "0"));
		// cap
		builder.append(StringUtils.leftPad("", 5, "0"));
		// codice belfiore
		builder.append(StringUtils.leftPad("", 4, " "));
		// località
		builder.append(StringUtils.leftPad("", 21, " "));
		builder.append(coobbligati.getSocieta());
		if (BigDecimal.ONE.equals(coobbligati.getSocieta())) {
			builder.append(StringUtils.rightPad(coobbligati.getCognome(), 24, " "));
			builder.append(StringUtils.rightPad(coobbligati.getNome(), 20, " "));
			builder.append(coobbligati.getSesso());
			builder.append(coobbligati.getDataNascita());
			builder.append(StringUtils.rightPad(coobbligati.getCodBelfioreNascita(), 4, " "));
			builder.append(StringUtils.leftPad("", 19, " "));
		} else {
			builder.append(StringUtils.rightPad(coobbligati.getDenomSocieta(), 76, " "));
		}
		builder.append(StringUtils.leftPad("", 15, " ")); // filler

		return n3;
	}

	private Integer addRigaN4(StringBuilder builder, CnmTRecordN4 imposta, Integer n4, DecimalFormat df, CnmTRecord e) {
		n4++;

		builder.append("N4"); // tipo record
		builder.append("073348"); // codice iscrizione comune
		builder.append("01"); // progressivo minuta
		builder.append(e.getCodicePartita());

		builder.append(imposta.getAnnoTributo());
		builder.append(imposta.getCnmDTipoTributo().getCodTipoTributo());

		BigDecimal cImposta = imposta.getImposta().multiply(HUNDRED);

		builder.append(StringUtils.leftPad("", 13, "0")); // imponibile
		builder.append(StringUtils.leftPad(df.format(cImposta), 13, "0"));
		builder.append("00");
		builder.append(StringUtils.leftPad(imposta.getDataDecorrenzaInteressi() != null ? imposta.getDataDecorrenzaInteressi() : "", 8, "0"));
		builder.append("00");
		builder.append(StringUtils.leftPad("", 75, " "));

		builder.append("S");
		builder.append("OR");

		builder.append(StringUtils.rightPad(imposta.getTitolo(), 12, " "));
		builder.append(utilsDate.asLocalDate(e.getCnmTRiscossione().getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getDataDeterminazione()).format(CustomDateSerializer.FORMATTER_SORIS));
		builder.append(StringUtils.leftPad("", 12, " "));

		builder.append(StringUtils.leftPad("", 20, " "));
		builder.append(StringUtils.leftPad("", 13, "0"));
		builder.append(" ");
		builder.append(StringUtils.leftPad("", 78, " "));

		return n4;
	}

	public static void main(String[] args) {

		String indirizzo = "AQP/452";
		indirizzo = Normalizer.normalize(indirizzo, Normalizer.Form.NFD);
		String resultIndirizzo = indirizzo.replaceAll("[^\\x00-\\x7F]", "");
		System.out.println("resultIndirizzo :: " + resultIndirizzo);

	}
}
