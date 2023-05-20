ALTER TABLE cnm_c_field
  ADD CONSTRAINT  ak_c_field_01 UNIQUE (id_tipo_allegato,ordine,inizio_validita);
ALTER TABLE cnm_c_field_type
  ADD CONSTRAINT  ak_c_field_type_01 UNIQUE (desc_field_type);
ALTER TABLE cnm_c_immagine
  ADD CONSTRAINT  ak_c_immagine_01 UNIQUE (cod_immagine);
ALTER TABLE cnm_c_immagine
  ADD CONSTRAINT  ak_c_immagine_02 UNIQUE (desc_immagine);
ALTER TABLE cnm_c_parametro
  ADD CONSTRAINT  ak_c_parametro_01 UNIQUE ( desc_parametro, inizio_validita);
ALTER TABLE cnm_c_report
  ADD CONSTRAINT  ak_c_report_01 UNIQUE (cod_report);
ALTER TABLE cnm_c_report
  ADD CONSTRAINT  ak_c_report_02 UNIQUE (desc_report);
ALTER TABLE cnm_c_schedulatore
  ADD CONSTRAINT  ak_c_schedulatore_01 UNIQUE (ip);
ALTER TABLE cnm_d_ambito
  ADD CONSTRAINT  ak_d_ambito_01 UNIQUE (desc_ambito,eliminato,data_ora_insert);
ALTER TABLE cnm_d_ambito
  ADD CONSTRAINT  ak_d_ambito_02 UNIQUE (acronimo,eliminato,data_ora_insert);
ALTER TABLE cnm_d_articolo
  ADD CONSTRAINT  ak_d_articolo_01 UNIQUE (desc_articolo,id_ente_norma,inizio_validita);
ALTER TABLE cnm_d_categoria_allegato
  ADD CONSTRAINT  ak_d_categoria_allegato_01 UNIQUE (desc_categoria_allegato);
ALTER TABLE cnm_d_comma
  ADD CONSTRAINT  ak_d_comma_01 UNIQUE (desc_comma,id_articolo,inizio_validita);
ALTER TABLE cnm_d_comune
  ADD CONSTRAINT  ak_d_comune_01 UNIQUE (cod_istat_comune,id_provincia,inizio_validita);
ALTER TABLE cnm_d_comune
  ADD CONSTRAINT  ak_d_comune_02 UNIQUE (cod_belfiore_comune,id_provincia,inizio_validita);
ALTER TABLE cnm_d_comune
  ADD CONSTRAINT  ak_d_comune_03 UNIQUE (denom_comune,id_provincia,inizio_validita);
ALTER TABLE cnm_d_elemento_elenco
  ADD CONSTRAINT  ak_d_elemento_elenco_01 UNIQUE (desc_elemento_elenco,id_elenco);
ALTER TABLE cnm_d_elenco
  ADD CONSTRAINT  ak_d_elenco_01 UNIQUE (desc_elenco);
ALTER TABLE cnm_d_ente
  ADD CONSTRAINT  ak_d_ente_01 UNIQUE (desc_ente);
ALTER TABLE cnm_d_gruppo
  ADD CONSTRAINT  ak_d_gruppo_01 UNIQUE (desc_gruppo);
ALTER TABLE cnm_d_lettera
  ADD CONSTRAINT  ak_d_lettera_01 UNIQUE (lettera,id_comma,inizio_validita);
ALTER TABLE cnm_d_messaggio
  ADD CONSTRAINT  ak_d_messaggio_01 UNIQUE (cod_messaggio,desc_messaggio);
ALTER TABLE cnm_d_modalita_notifica
  ADD CONSTRAINT  ak_d_modalita_notifica_01 UNIQUE (desc_modalita_notifica);
ALTER TABLE cnm_d_norma
  ADD CONSTRAINT  ak_d_norma_01 UNIQUE (riferimento_normativo,id_ambito,eliminato,data_ora_insert);
ALTER TABLE cnm_d_provincia
  ADD CONSTRAINT  ak_d_provincia_01 UNIQUE (cod_provincia,id_regione);
ALTER TABLE cnm_d_provincia
  ADD CONSTRAINT  ak_d_provincia_02 UNIQUE (denom_provincia,id_regione);
ALTER TABLE cnm_d_provincia
  ADD CONSTRAINT  ak_d_provincia_03 UNIQUE (id_regione,sigla_provincia);
ALTER TABLE cnm_d_ruolo
  ADD CONSTRAINT  ak_d_profilo_01 UNIQUE (desc_ruolo,inizio_validita);
ALTER TABLE cnm_d_ruolo_soggetto
  ADD CONSTRAINT  ak_d_ruolo_soggetto_01 UNIQUE (desc_ruolo_soggetto);
ALTER TABLE cnm_d_stato_allegato
  ADD CONSTRAINT  ak_d_stato_allegato_01 UNIQUE (desc_stato_allegato);
ALTER TABLE cnm_d_stato_file
  ADD CONSTRAINT  ak_d_stato_file_01 UNIQUE (desc_stato_file);
ALTER TABLE cnm_d_stato_ord_verb_sog
  ADD CONSTRAINT  ak_d_stato_ord_verb_sog_01 UNIQUE (desc_stato_ord_verb_sog);
ALTER TABLE cnm_d_stato_ordinanza
  ADD CONSTRAINT  ak_d_stato_ordinanza_01 UNIQUE (desc_stato_ordinanza);
ALTER TABLE cnm_d_stato_piano_rate
  ADD CONSTRAINT  ak_d_stato_piano_rate_01 UNIQUE (desc_stato_piano_rate);
ALTER TABLE cnm_d_stato_rata
  ADD CONSTRAINT  ak_d_stato_rata_01 UNIQUE (desc_stato_rata);
ALTER TABLE cnm_d_stato_riscossione
  ADD CONSTRAINT ak_d_stato_riscossione_01 UNIQUE (desc_stato_riscossione);
ALTER TABLE cnm_d_stato_sollecito
  ADD CONSTRAINT  ak_d_stato_sollecito_01 UNIQUE (desc_stato_sollecito);
ALTER TABLE cnm_d_stato_verbale
  ADD CONSTRAINT  ak_d_stato_verbale_01 UNIQUE (desc_stato_verbale);
ALTER TABLE cnm_d_tipo_allegato
  ADD CONSTRAINT  ak_d_tipo_allegato_01 UNIQUE (desc_tipo_allegato,id_categoria_allegato);
ALTER TABLE cnm_d_tipo_file
  ADD CONSTRAINT  ak_d_tipo_file_01 UNIQUE (desc_tipo_file);
ALTER TABLE cnm_d_tipo_messaggio
  ADD CONSTRAINT  ak_d_tipo_messaggio_01 UNIQUE (desc_tipo_messaggio);
ALTER TABLE cnm_d_tipo_ordinanza
  ADD CONSTRAINT  ak_d_tipo_ordinanza_01 UNIQUE (desc_tipo_ordinanza);
ALTER TABLE cnm_d_tipo_record
  ADD CONSTRAINT  ak_d_tipo_record_01 UNIQUE (desc_tipo_record);
ALTER TABLE cnm_d_tipo_tributo
  ADD CONSTRAINT  ak_d_tipo_tributo_01 UNIQUE (cod_tipo_tributo);
ALTER TABLE cnm_d_tipo_tributo
  ADD CONSTRAINT  ak_d_tipo_tributo_02 UNIQUE (desc_tipo_tributo);
ALTER TABLE cnm_d_use_case
  ADD CONSTRAINT  ak_d_use_case_01 UNIQUE (cod_use_case);
ALTER TABLE cnm_d_use_case
  ADD CONSTRAINT  ak_d_use_case_02 UNIQUE (desc_use_case);
ALTER TABLE cnm_d_utilizzo_allegato
  ADD CONSTRAINT  ak_d_utilizzo_allegato_01 UNIQUE (desc_utilizzo_allegato);
ALTER TABLE cnm_r_ente_norma
  ADD CONSTRAINT  ak_r_ente_norma_01 UNIQUE (id_ente,id_norma,inizio_validita);
ALTER TABLE cnm_r_funzionario_istruttore
  ADD CONSTRAINT  ak_r_funzionario_istruttore_01 UNIQUE (id_verbale,id_user,inizio_assegnazione);
ALTER TABLE cnm_r_ordinanza_verb_sog
  ADD CONSTRAINT  ak_r_ordinanza_verb_sog_01 UNIQUE (id_ordinanza,id_verbale_soggetto,id_stato_ord_verb_sog);
ALTER TABLE cnm_d_utilizzo
  ADD CONSTRAINT  ak_d_utilizzo_01 UNIQUE (desc_utilizzo);
ALTER TABLE cnm_r_use_case_ruolo
  ADD CONSTRAINT  ak_r_use_case_ruolo_01 UNIQUE (id_ruolo,id_use_case,inizio_validita);
ALTER TABLE cnm_r_user_ente
  ADD CONSTRAINT  ak_r_user_ente_01 UNIQUE (id_utilizzo,id_user,id_ente,inizio_validita);
ALTER TABLE cnm_r_verbale_illecito
  ADD CONSTRAINT  ak_r_verbale_illecito_01 UNIQUE (id_verbale,id_lettera);
ALTER TABLE cnm_r_verbale_soggetto
  ADD CONSTRAINT  ak_r_verbale_soggetto_01 UNIQUE (id_verbale,id_soggetto);
ALTER TABLE cnm_s_comune
  ADD CONSTRAINT  ak_s_comune_01 UNIQUE (id_comune,cod_istat_comune,cod_belfiore_comune,denom_comune,id_provincia,inizio_validita);
ALTER TABLE cnm_s_nazione
  ADD CONSTRAINT  ak_s_nazione_01 UNIQUE (id_nazione,inizio_validita);
ALTER TABLE cnm_s_provincia
  ADD CONSTRAINT  ak_s_provincia_01 UNIQUE (id_provincia,id_regione,inizio_validita);
ALTER TABLE cnm_s_provincia
  ADD CONSTRAINT  ak_s_provincia_02 UNIQUE (cod_provincia,id_regione,inizio_validita);
ALTER TABLE cnm_s_provincia
  ADD CONSTRAINT  ak_s_provincia_03 UNIQUE (denom_provincia,id_regione,inizio_validita);
ALTER TABLE cnm_s_provincia
  ADD CONSTRAINT  ak_s_provincia_04 UNIQUE (id_regione,sigla_provincia,inizio_validita);
ALTER TABLE cnm_s_regione
  ADD CONSTRAINT  ak_s_regione_01 UNIQUE (id_regione,id_nazione,inizio_validita);
ALTER TABLE cnm_s_regione
  ADD CONSTRAINT  ak_s_regione_02 UNIQUE (cod_regione,id_nazione,inizio_validita);
ALTER TABLE cnm_s_regione
  ADD CONSTRAINT  ak_s_regione_03 UNIQUE (denom_regione,id_nazione,inizio_validita);
ALTER TABLE cnm_t_allegato
  ADD CONSTRAINT  ak_t_allegato_01 UNIQUE (id_index);
ALTER TABLE cnm_t_allegato
  ADD CONSTRAINT  ak_t_allegato_02 UNIQUE (id_acta);
ALTER TABLE cnm_t_allegato
  ADD CONSTRAINT  ak_t_allegato_03 UNIQUE (numero_protocollo);
ALTER TABLE cnm_t_allegato_field
  ADD CONSTRAINT  ak_t_allegato_field_01 UNIQUE (id_allegato,id_field);
ALTER TABLE cnm_t_calendario
  ADD CONSTRAINT  ak_t_calendario_01 UNIQUE (inizio_udienza,fine_udienza,tribunale,cognome_giudice,nome_giudice);
ALTER TABLE cnm_t_notifica
  ADD CONSTRAINT  ak_t_notifica_01 UNIQUE (numero_raccomandata);
ALTER TABLE cnm_t_ordinanza
  ADD CONSTRAINT  ak_t_ordinanza_01 UNIQUE (numero_protocollo);
ALTER TABLE cnm_t_rata
  ADD CONSTRAINT  ak_t_rata_01 UNIQUE ( id_piano_rate, numero_rata );
ALTER TABLE cnm_t_record
  ADD CONSTRAINT  ak_t_record_01 UNIQUE (codice_partita,ordine);
ALTER TABLE cnm_t_record_N2
  ADD CONSTRAINT  ak_t_record_N2_01 UNIQUE (id_record);
ALTER TABLE cnm_t_record_N3
  ADD CONSTRAINT  ak_t_record_N3_01 UNIQUE (id_record);
ALTER TABLE cnm_t_record_N4
  ADD CONSTRAINT  ak_t_record_N4_01 UNIQUE (id_record);
ALTER TABLE cnm_t_residenza
  ADD CONSTRAINT  ak_t_residenza_01 UNIQUE (id_soggetto);
ALTER TABLE cnm_t_soggetto
  ADD CONSTRAINT  ak_t_soggetto_01 UNIQUE (id_persona);
ALTER TABLE cnm_t_riscossione
  ADD CONSTRAINT  ak_t_riscossione_01 UNIQUE (id_ordinanza_verb_sog);
ALTER TABLE cnm_t_soggetto
  ADD CONSTRAINT  ak_t_soggetto_02 UNIQUE (id_societa);
ALTER TABLE cnm_t_soggetto
  ADD CONSTRAINT  ak_t_soggetto_03 UNIQUE (id_stas);
ALTER TABLE cnm_t_sollecito
  ADD CONSTRAINT  ak_t_sollecito_01 UNIQUE (numero_protocollo);
ALTER TABLE cnm_t_user
  ADD CONSTRAINT  ak_t_user_01 UNIQUE (codice_fiscale,inizio_validita);
ALTER TABLE cnm_t_verbale
  ADD CONSTRAINT  ak_t_verbale_01 UNIQUE (num_verbale);
ALTER TABLE cnm_tmp_file
  ADD CONSTRAINT  ak_tmp_file_01 UNIQUE (ordine);
