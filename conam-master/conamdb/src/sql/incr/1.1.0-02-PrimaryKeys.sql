ALTER TABLE dt_limiti_amministrativi
	ADD CONSTRAINT  pk_limiti_amministrativi PRIMARY KEY ( record );
ALTER TABLE dt_stati_esteri_istat
	ADD CONSTRAINT  pk_stati_esteri_istat PRIMARY KEY ( record );

ALTER TABLE dt_limiti_amministrativi_div
	ADD CONSTRAINT  pk_limiti_amministrativi_div PRIMARY KEY ( id_comune );
ALTER TABLE dt_stati_esteri_istat_div
	ADD CONSTRAINT  pk_stati_esteri_istat_div PRIMARY KEY ( id_stato );
ALTER TABLE dt_stati_esteri_ministero_div
	ADD CONSTRAINT  pk_stati_esteri_ministero_div PRIMARY KEY ( id_stato_ministero );
ALTER TABLE dt_assoc_esteri_ministero_div
	ADD CONSTRAINT  pk_assoc_esteri_ministero_div PRIMARY KEY ( id_stato_istat, id_stato_ministero );

ALTER TABLE cnm_c_field
	ADD CONSTRAINT  pk_c_field PRIMARY KEY (id_field);
ALTER TABLE cnm_c_field_type
	ADD CONSTRAINT  pk_c_field_type PRIMARY KEY (id_field_type);
ALTER TABLE cnm_c_immagine
	ADD CONSTRAINT  pk_c_immagine PRIMARY KEY (id_immagine);
ALTER TABLE cnm_c_immagine_report
	ADD CONSTRAINT  pk_c_immagine_report PRIMARY KEY (id_report,id_immagine);
ALTER TABLE cnm_c_parametro
	ADD CONSTRAINT  pk_c_parametro PRIMARY KEY (id_parametro);
ALTER TABLE cnm_c_report
	ADD CONSTRAINT  pk_c_report PRIMARY KEY (id_report);
ALTER TABLE cnm_c_schedulatore
	ADD CONSTRAINT  pk_c_schedulatore PRIMARY KEY (id_schedulatore);
ALTER TABLE cnm_d_ambito
	ADD CONSTRAINT  pk_d_ambito PRIMARY KEY (id_ambito);
ALTER TABLE cnm_d_articolo
	ADD CONSTRAINT  pk_d_articolo PRIMARY KEY (id_articolo);
ALTER TABLE cnm_d_categoria_allegato
	ADD CONSTRAINT  pk_d_categoria_allegato PRIMARY KEY (id_categoria_allegato);
ALTER TABLE cnm_d_comma
	ADD CONSTRAINT  pk_d_comma PRIMARY KEY (id_comma);
ALTER TABLE cnm_d_comune
	ADD CONSTRAINT  pk_d_comune PRIMARY KEY (id_comune);
ALTER TABLE cnm_d_elemento_elenco
	ADD CONSTRAINT  pk_d_elemento_elenco PRIMARY KEY (id_elemento_elenco);
ALTER TABLE cnm_d_elenco
	ADD CONSTRAINT  pk_d_elenco PRIMARY KEY (id_elenco);
ALTER TABLE cnm_d_ente
	ADD CONSTRAINT  pk_d_ente PRIMARY KEY (id_ente);
ALTER TABLE cnm_d_lettera
	ADD CONSTRAINT  pk_d_lettera PRIMARY KEY (id_lettera);
ALTER TABLE cnm_d_messaggio
	ADD CONSTRAINT  pk_d_messaggio PRIMARY KEY (id_messaggio);
ALTER TABLE cnm_d_modalita_notifica
	ADD CONSTRAINT  pk_d_modalita_notifica PRIMARY KEY (id_modalita_notifica);
ALTER TABLE cnm_d_nazione
	ADD CONSTRAINT  pk_d_nazione PRIMARY KEY (id_nazione);
ALTER TABLE cnm_d_norma
	ADD CONSTRAINT  pk_d_norma PRIMARY KEY (id_norma);
ALTER TABLE cnm_d_origine
	ADD CONSTRAINT  pk_d_origine PRIMARY KEY (id_origine);
ALTER TABLE cnm_d_provincia
	ADD CONSTRAINT  pk_d_provincia PRIMARY KEY (id_provincia);
ALTER TABLE cnm_d_regione
	ADD CONSTRAINT  pk_d_regione PRIMARY KEY (id_regione);
ALTER TABLE cnm_d_ruolo
	ADD CONSTRAINT  pk_d_profilo PRIMARY KEY (id_ruolo);
ALTER TABLE cnm_d_ruolo_soggetto
	ADD CONSTRAINT  pk_d_ruolo_soggetto PRIMARY KEY (id_ruolo_soggetto);
ALTER TABLE cnm_d_stato_allegato
	ADD CONSTRAINT  pk_d_stato_allegato PRIMARY KEY (id_stato_allegato);
ALTER TABLE cnm_d_stato_file
	ADD CONSTRAINT  pk_d_stato_file PRIMARY KEY (id_stato_file);
ALTER TABLE cnm_d_stato_ord_verb_sog
	ADD CONSTRAINT  pk_d_stato_ord_verb_sog PRIMARY KEY (id_stato_ord_verb_sog);
ALTER TABLE cnm_d_stato_ordinanza
	ADD CONSTRAINT  pk_d_stato_ordinanza PRIMARY KEY (id_stato_ordinanza);
ALTER TABLE cnm_d_stato_piano_rate
	ADD CONSTRAINT  pk_d_stato_piano_rate PRIMARY KEY (id_stato_piano_rate);
ALTER TABLE cnm_d_stato_rata
	ADD CONSTRAINT  pk_d_stato_rata PRIMARY KEY (id_stato_rata);
ALTER TABLE cnm_d_stato_record
	ADD CONSTRAINT  pk_d_stato_record PRIMARY KEY (id_stato_record);
ALTER TABLE cnm_d_stato_riscossione
	ADD CONSTRAINT  pk_d_stato_riscossione PRIMARY KEY (id_stato_riscossione);
ALTER TABLE cnm_d_stato_sollecito
	ADD CONSTRAINT  pk_d_stato_sollecito PRIMARY KEY (id_stato_sollecito);
ALTER TABLE cnm_d_stato_verbale
	ADD CONSTRAINT  pk_d_stato_verbale PRIMARY KEY (id_stato_verbale);
ALTER TABLE cnm_d_tipo_allegato
	ADD CONSTRAINT  pk_d_tipo_allegato PRIMARY KEY (id_tipo_allegato);
ALTER TABLE cnm_d_tipo_file
	ADD CONSTRAINT  pk_d_tipo_file PRIMARY KEY (id_tipo_file);
ALTER TABLE cnm_d_tipo_messaggio
	ADD CONSTRAINT  pk_d_tipo_messaggio PRIMARY KEY (id_tipo_messaggio);
ALTER TABLE cnm_d_tipo_ordinanza
	ADD CONSTRAINT  pk_d_tipo_ordinanza PRIMARY KEY (id_tipo_ordinanza);
ALTER TABLE cnm_d_tipo_record
	ADD CONSTRAINT  pk_d_tipo_record PRIMARY KEY (id_tipo_record);
ALTER TABLE cnm_d_tipo_tributo
	ADD CONSTRAINT  pk_d_tipo_tributo PRIMARY KEY (id_tipo_tributo);
ALTER TABLE cnm_d_use_case
	ADD CONSTRAINT  pk_d_use_case PRIMARY KEY (id_use_case);
ALTER TABLE cnm_d_utilizzo_allegato
	ADD CONSTRAINT  pk_d_utilizzo_allegato PRIMARY KEY (id_utilizzo_allegato);
ALTER TABLE cnm_r_allegato_ord_verb_sog
	ADD CONSTRAINT  pk_r_allegato_ord_verb_sog PRIMARY KEY (id_allegato,id_ordinanza_verb_sog);
ALTER TABLE cnm_r_allegato_ordinanza
	ADD CONSTRAINT  pk_r_allegato_ordinanza PRIMARY KEY (id_allegato,id_ordinanza);
ALTER TABLE cnm_r_allegato_piano_rate
	ADD CONSTRAINT  pk_r_allegato_piano_rate PRIMARY KEY (id_allegato,id_piano_rate);
ALTER TABLE cnm_r_allegato_sollecito
	ADD CONSTRAINT  pk_r_allegato_sollecito PRIMARY KEY (id_allegato,id_sollecito);
ALTER TABLE cnm_r_allegato_verbale
	ADD CONSTRAINT  pk_r_allegato_verbale PRIMARY KEY (id_allegato,id_verbale);
ALTER TABLE cnm_r_allegato_verb_sog
	ADD CONSTRAINT  pk_r_allegato_verb_sog_01 PRIMARY KEY (id_allegato,id_verbale_soggetto);
ALTER TABLE cnm_r_ente_norma
	ADD CONSTRAINT  pk_r_ente_norma PRIMARY KEY (id_ente_norma);
ALTER TABLE cnm_r_funzionario_istruttore
	ADD CONSTRAINT  pk_r_funzionario_istruttore PRIMARY KEY (id_funzionario_istruttore);
ALTER TABLE cnm_r_notifica_ordinanza
	ADD CONSTRAINT  pk_r_notifica_ordinanza PRIMARY KEY (id_ordinanza,id_notifica);
ALTER TABLE cnm_r_notifica_piano_rate
	ADD CONSTRAINT  pk_r_notifica_piano_rate PRIMARY KEY (id_piano_rate,id_notifica);
ALTER TABLE cnm_r_notifica_sollecito
	ADD CONSTRAINT  pk_r_notifica_sollecito PRIMARY KEY (id_sollecito,id_notifica);
ALTER TABLE cnm_r_ordinanza_verb_sog
	ADD CONSTRAINT  pk_r_ordinanza_verb_sog PRIMARY KEY (id_ordinanza_verb_sog);
ALTER TABLE cnm_d_utilizzo
	ADD CONSTRAINT  pk_d_utilizzo PRIMARY KEY (id_utilizzo);
ALTER TABLE cnm_r_sogg_rata
	ADD CONSTRAINT  pk_r_sogg_rata PRIMARY KEY ( id_ordinanza_verb_sog, id_rata );
ALTER TABLE cnm_r_tipo_allegato_so
	ADD CONSTRAINT  pk_r_tipo_allegato_so PRIMARY KEY (id_stato_ordinanza,id_tipo_allegato);
ALTER TABLE cnm_r_tipo_allegato_sv
	ADD CONSTRAINT  pk_r_tipo_allegato_sv PRIMARY KEY (id_tipo_allegato,id_stato_verbale);
ALTER TABLE cnm_r_use_case_ruolo
	ADD CONSTRAINT  pk_r_use_case_ruolo PRIMARY KEY (id_use_case_ruolo);
ALTER TABLE cnm_r_user_ente
	ADD CONSTRAINT  pk_r_user_ente PRIMARY KEY (id_user_ente);
ALTER TABLE cnm_r_verbale_illecito
	ADD CONSTRAINT  pk_r_verbale_illecito PRIMARY KEY (id_verbale_illecito);
ALTER TABLE cnm_r_verbale_soggetto
	ADD CONSTRAINT  pk_r_verbale_soggetto PRIMARY KEY (id_verbale_soggetto);
ALTER TABLE cnm_s_comune
	ADD CONSTRAINT  pk_s_comune PRIMARY KEY (id_s_comune);
ALTER TABLE cnm_s_nazione
	ADD CONSTRAINT  pk_s_nazione PRIMARY KEY (id_s_nazione);
ALTER TABLE cnm_s_provincia
	ADD CONSTRAINT  pk_s_provincia PRIMARY KEY (id_s_provincia);
ALTER TABLE cnm_s_regione
	ADD CONSTRAINT  pk_s_regione PRIMARY KEY (id_s_regione);
ALTER TABLE cnm_s_stato_ordinanza
	ADD CONSTRAINT  pk_s_stato_ordinanza PRIMARY KEY (id_s_stato_ordinanza);
ALTER TABLE cnm_s_stato_verbale
	ADD CONSTRAINT  pk_s_stato_verbale PRIMARY KEY (id_s_stato_verbale);
ALTER TABLE cnm_t_allegato
	ADD CONSTRAINT  pk_t_allegato PRIMARY KEY (id_allegato);
ALTER TABLE cnm_t_allegato_field
	ADD CONSTRAINT  pk_t_allegato_field PRIMARY KEY (id_allegato_field);
ALTER TABLE cnm_t_file
	ADD CONSTRAINT  pk_t_file PRIMARY KEY (id_file);
ALTER TABLE cnm_t_file_ritorno
	ADD CONSTRAINT  pk_t_file_ritorno PRIMARY KEY (id_file_ritorno);
ALTER TABLE cnm_t_notifica
	ADD CONSTRAINT  pk_t_notifica PRIMARY KEY (id_notifica);
ALTER TABLE cnm_t_ordinanza
	ADD CONSTRAINT  pk_t_ordinanza PRIMARY KEY (id_ordinanza);
ALTER TABLE cnm_t_persona
	ADD CONSTRAINT  pk_t_persona PRIMARY KEY (id_persona);
ALTER TABLE cnm_t_piano_rate
	ADD CONSTRAINT  pk_t_piano_rate PRIMARY KEY ( id_piano_rate );
ALTER TABLE cnm_t_rata
	ADD CONSTRAINT  pk_t_rata PRIMARY KEY (id_rata);
ALTER TABLE cnm_t_record
	ADD CONSTRAINT  pk_t_record PRIMARY KEY (id_record);
ALTER TABLE cnm_t_record_N2
	ADD CONSTRAINT  pk_t_record_N2 PRIMARY KEY (id_record_n2);
ALTER TABLE cnm_t_record_N3
	ADD CONSTRAINT  pk_t_record_N3 PRIMARY KEY (id_record_n3);
ALTER TABLE cnm_t_record_N4
	ADD CONSTRAINT  pk_t_record_N4 PRIMARY KEY (id_record_n4);
ALTER TABLE cnm_t_record_ritorno
	ADD CONSTRAINT  pk_t_record_file_ritorno PRIMARY KEY (id_record_ritorno);
ALTER TABLE cnm_t_residenza
	ADD CONSTRAINT  pk_t_residenza PRIMARY KEY (id_residenza);
ALTER TABLE cnm_t_riscossione
	ADD CONSTRAINT  pk_t_riscossione PRIMARY KEY (id_riscossione);
ALTER TABLE cnm_t_societa
	ADD CONSTRAINT  pk_t_societa PRIMARY KEY (id_societa);
ALTER TABLE cnm_t_soggetto
	ADD CONSTRAINT  pk_t_soggetto PRIMARY KEY (id_soggetto);
ALTER TABLE cnm_t_user
	ADD CONSTRAINT  pk_t_user PRIMARY KEY (id_user);
ALTER TABLE cnm_t_verbale
	ADD CONSTRAINT  pk_t_verbale PRIMARY KEY (id_verbale);
ALTER TABLE cnm_d_gruppo
	ADD CONSTRAINT  pk_d_gruppo PRIMARY KEY (id_gruppo);
ALTER TABLE cnm_t_calendario
	ADD CONSTRAINT  pk_t_calendario PRIMARY KEY (id_calendario);
ALTER TABLE cnm_t_sollecito
	ADD CONSTRAINT  pk_t_sollecito PRIMARY KEY (id_sollecito);
ALTER TABLE cnm_tmp_file
	ADD CONSTRAINT  pk_tmp_file PRIMARY KEY (id_tmp_file);
