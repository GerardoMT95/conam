ALTER TABLE cnm_c_field
	ADD CONSTRAINT  fk_c_field_01 FOREIGN KEY (id_tipo_allegato) REFERENCES cnm_d_tipo_allegato(id_tipo_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_c_field
	ADD CONSTRAINT  fk_c_field_02 FOREIGN KEY (id_field_type) REFERENCES cnm_c_field_type(id_field_type) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_c_field
	ADD CONSTRAINT  fk_c_field_03 FOREIGN KEY (id_elenco) REFERENCES cnm_d_elenco(id_elenco) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_c_immagine_report
	ADD CONSTRAINT  fk_c_immagine_report_01 FOREIGN KEY (id_report) REFERENCES cnm_c_report(id_report);
ALTER TABLE cnm_c_immagine_report
	ADD CONSTRAINT  fk_c_immagine_report_02 FOREIGN KEY (id_immagine) REFERENCES cnm_c_immagine(id_immagine);
ALTER TABLE cnm_c_report
	ADD CONSTRAINT  fk_c_report_01 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_ambito
	ADD CONSTRAINT  fk_d_ambito_01 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_ambito
	ADD CONSTRAINT  fk_d_ambito_02 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_articolo
	ADD CONSTRAINT  fk_d_articolo_01 FOREIGN KEY (id_ente_norma) REFERENCES cnm_r_ente_norma(id_ente_norma) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_articolo
	ADD CONSTRAINT  fk_d_articolo_02 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_articolo
	ADD CONSTRAINT  fk_d_articolo_03 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_comma
	ADD CONSTRAINT  fk_d_comma_01 FOREIGN KEY (id_articolo) REFERENCES cnm_d_articolo(id_articolo) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_comma
	ADD CONSTRAINT  fk_d_comma_02 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_comma
	ADD CONSTRAINT  fk_d_comma_03 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_comune
	ADD CONSTRAINT  fk_d_comune_01 FOREIGN KEY (id_provincia) REFERENCES cnm_d_provincia(id_provincia) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_lettera
	ADD CONSTRAINT  fk_d_lettera_01 FOREIGN KEY (id_comma) REFERENCES cnm_d_comma(id_comma) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_lettera
	ADD CONSTRAINT  fk_d_lettera_02 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_lettera
	ADD CONSTRAINT  fk_d_lettera_03 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_messaggio
	ADD CONSTRAINT  fk_d_messaggio_01 FOREIGN KEY (id_tipo_messaggio) REFERENCES cnm_d_tipo_messaggio(id_tipo_messaggio) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_nazione
	ADD CONSTRAINT  fk_d_nazione_01 FOREIGN KEY (id_origine) REFERENCES cnm_d_origine(id_origine) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_norma
	ADD CONSTRAINT  fk_d_norma_01 FOREIGN KEY (id_ambito) REFERENCES cnm_d_ambito(id_ambito) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_norma
	ADD CONSTRAINT  fk_d_norma_02 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_norma
	ADD CONSTRAINT  fk_d_norma_03 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_provincia
	ADD CONSTRAINT  fk_d_provincia_01 FOREIGN KEY (id_regione) REFERENCES cnm_d_regione(id_regione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_regione
	ADD CONSTRAINT  fk_d_regione_01 FOREIGN KEY (id_nazione) REFERENCES cnm_d_nazione(id_nazione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_stato_ord_verb_sog
	ADD CONSTRAINT  fk_d_stato_ord_verb_sog_01 FOREIGN KEY (id_elemento_elenco) REFERENCES cnm_d_elemento_elenco(id_elemento_elenco) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_tipo_allegato
	ADD CONSTRAINT  fk_d_tipo_allegato_01 FOREIGN KEY (id_categoria_allegato) REFERENCES cnm_d_categoria_allegato(id_categoria_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_d_tipo_allegato
	ADD CONSTRAINT  fk_d_tipo_allegato_02 FOREIGN KEY (id_utilizzo_allegato) REFERENCES cnm_d_utilizzo_allegato(id_utilizzo_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_ord_verb_sog
	ADD CONSTRAINT  fk_r_allegato_ord_verb_sog_01 FOREIGN KEY (id_allegato) REFERENCES cnm_t_allegato(id_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_ord_verb_sog
	ADD CONSTRAINT  fk_r_allegato_ord_verb_sog_02 FOREIGN KEY (id_ordinanza_verb_sog) REFERENCES cnm_r_ordinanza_verb_sog(id_ordinanza_verb_sog) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_ord_verb_sog
	ADD CONSTRAINT  fk_r_allegato_ord_verb_sog_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_ordinanza
	ADD CONSTRAINT  fk_r_allegato_ordinanza_01 FOREIGN KEY (id_allegato) REFERENCES cnm_t_allegato(id_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_ordinanza
	ADD CONSTRAINT  fk_r_allegato_ordinanza_02 FOREIGN KEY (id_ordinanza) REFERENCES cnm_t_ordinanza(id_ordinanza) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_ordinanza
	ADD CONSTRAINT  fk_r_allegato_ordinanza_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_piano_rate
	ADD CONSTRAINT  fk_r_allegato_piano_rate_01 FOREIGN KEY (id_allegato) REFERENCES cnm_t_allegato(id_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_piano_rate
	ADD CONSTRAINT  fk_r_allegato_piano_rate_02 FOREIGN KEY (id_piano_rate) REFERENCES cnm_t_piano_rate(id_piano_rate) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_piano_rate
	ADD CONSTRAINT  fk_r_allegato_piano_rate_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_sollecito
	ADD CONSTRAINT  fk_r_allegato_sollecito_01 FOREIGN KEY (id_allegato) REFERENCES cnm_t_allegato(id_allegato);
ALTER TABLE cnm_r_allegato_sollecito
	ADD CONSTRAINT  fk_r_allegato_sollecito_02 FOREIGN KEY (id_sollecito) REFERENCES cnm_t_sollecito(id_sollecito);
ALTER TABLE cnm_r_allegato_sollecito
	ADD CONSTRAINT  fk_r_allegato_sollecito_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user);
ALTER TABLE cnm_r_allegato_verbale
	ADD CONSTRAINT  fk_r_allegato_verbale_01 FOREIGN KEY (id_allegato) REFERENCES cnm_t_allegato(id_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_verbale
	ADD CONSTRAINT  fk_r_allegato_verbale_02 FOREIGN KEY (id_verbale) REFERENCES cnm_t_verbale(id_verbale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_verbale
	ADD CONSTRAINT  fk_r_allegato_verbale_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_verb_sog
	ADD CONSTRAINT  fk_r_allegato_verb_sog_01 FOREIGN KEY (id_allegato) REFERENCES cnm_t_allegato(id_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_verb_sog
	ADD CONSTRAINT  fk_r_allegato_verb_sog_02 FOREIGN KEY (id_verbale_soggetto) REFERENCES cnm_r_verbale_soggetto(id_verbale_soggetto) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_allegato_verb_sog
	ADD CONSTRAINT  fk_r_allegato_verb_sog_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_ente_norma
	ADD CONSTRAINT  fk_r_ente_norma_01 FOREIGN KEY (id_ente) REFERENCES cnm_d_ente(id_ente) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_ente_norma
	ADD CONSTRAINT  fk_r_ente_norma_02 FOREIGN KEY (id_norma) REFERENCES cnm_d_norma(id_norma) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_ente_norma
	ADD CONSTRAINT  fk_r_ente_norma_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_ente_norma
	ADD CONSTRAINT  fk_r_ente_norma_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_funzionario_istruttore
	ADD CONSTRAINT  fk_r_funzionario_istruttore_01 FOREIGN KEY (id_verbale) REFERENCES cnm_t_verbale(id_verbale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_funzionario_istruttore
	ADD CONSTRAINT  fk_r_funzionario_istruttore_02 FOREIGN KEY (id_user) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_notifica_ordinanza
	ADD CONSTRAINT  fk_r_notifica_ordinanza_01 FOREIGN KEY (id_ordinanza) REFERENCES cnm_t_ordinanza(id_ordinanza) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_notifica_ordinanza
	ADD CONSTRAINT  fk_r_notifica_ordinanza_02 FOREIGN KEY (id_notifica) REFERENCES cnm_t_notifica(id_notifica) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_notifica_piano_rate
	ADD CONSTRAINT  fk_r_notifica_piano_rate_01 FOREIGN KEY (id_piano_rate) REFERENCES cnm_t_piano_rate(id_piano_rate) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_notifica_piano_rate
	ADD CONSTRAINT  fk_r_notifica_piano_rate_02 FOREIGN KEY (id_notifica) REFERENCES cnm_t_notifica(id_notifica) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_notifica_sollecito
	ADD CONSTRAINT  fk_r_notifica_sollecito_01 FOREIGN KEY (id_sollecito) REFERENCES cnm_t_sollecito(id_sollecito) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_notifica_sollecito
	ADD CONSTRAINT  fk_r_notifica_sollecito_02 FOREIGN KEY (id_notifica) REFERENCES cnm_t_notifica(id_notifica) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_ordinanza_verb_sog
	ADD CONSTRAINT  fk_r_ordinanza_verb_sog_01 FOREIGN KEY (id_ordinanza) REFERENCES cnm_t_ordinanza(id_ordinanza) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_ordinanza_verb_sog
	ADD CONSTRAINT  fk_r_ordinanza_verb_sog_02 FOREIGN KEY (id_verbale_soggetto) REFERENCES cnm_r_verbale_soggetto(id_verbale_soggetto) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_ordinanza_verb_sog
	ADD CONSTRAINT  fk_r_ordinanza_verb_sog_03 FOREIGN KEY (id_stato_ord_verb_sog) REFERENCES cnm_d_stato_ord_verb_sog(id_stato_ord_verb_sog) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_ordinanza_verb_sog
	ADD CONSTRAINT  fk_r_ordinanza_verb_sog_04 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_ordinanza_verb_sog
	ADD CONSTRAINT  fk_r_ordinanza_verb_sog_05 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_sogg_rata
	ADD CONSTRAINT  fk_r_sogg_rata_01 FOREIGN KEY (id_ordinanza_verb_sog) REFERENCES cnm_r_ordinanza_verb_sog(id_ordinanza_verb_sog) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_sogg_rata
	ADD CONSTRAINT  fk_r_sogg_rata_02 FOREIGN KEY (id_rata) REFERENCES cnm_t_rata(id_rata) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_sogg_rata
	ADD CONSTRAINT  fk_t_rata_03 FOREIGN KEY (id_stato_rata) REFERENCES cnm_d_stato_rata(id_stato_rata) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_sogg_rata
	ADD CONSTRAINT  fk_r_sogg_rata_04 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_sogg_rata
	ADD CONSTRAINT  fk_r_sogg_rata_05 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_tipo_allegato_so
	ADD CONSTRAINT  fk_r_tipo_allegato_so_01 FOREIGN KEY (id_stato_ordinanza) REFERENCES cnm_d_stato_ordinanza(id_stato_ordinanza) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_tipo_allegato_so
	ADD CONSTRAINT  fk_r_tipo_allegato_so_02 FOREIGN KEY (id_tipo_allegato) REFERENCES cnm_d_tipo_allegato(id_tipo_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_tipo_allegato_sv
	ADD CONSTRAINT  fk_r_tipo_allegato_sv_01 FOREIGN KEY (id_tipo_allegato) REFERENCES cnm_d_tipo_allegato(id_tipo_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_tipo_allegato_sv
	ADD CONSTRAINT  fk_r_tipo_allegato_sv_02 FOREIGN KEY (id_stato_verbale) REFERENCES cnm_d_stato_verbale(id_stato_verbale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_use_case_ruolo
	ADD CONSTRAINT  fk_r_use_case_ruolo_01 FOREIGN KEY (id_ruolo) REFERENCES cnm_d_ruolo(id_ruolo) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_use_case_ruolo
	ADD CONSTRAINT  fk_r_use_case_ruolo_02 FOREIGN KEY (id_use_case) REFERENCES cnm_d_use_case(id_use_case) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_user_ente
	ADD CONSTRAINT  fk_r_user_ente_01 FOREIGN KEY (id_utilizzo) REFERENCES cnm_d_utilizzo(id_utilizzo) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_user_ente
	ADD CONSTRAINT  fk_r_user_ente_02 FOREIGN KEY (id_user) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_user_ente
	ADD CONSTRAINT  fk_r_user_ente_03 FOREIGN KEY (id_ente) REFERENCES cnm_d_ente(id_ente) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_verbale_illecito
	ADD CONSTRAINT  fk_r_verbale_illecito_01 FOREIGN KEY (id_verbale) REFERENCES cnm_t_verbale(id_verbale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_verbale_illecito
	ADD CONSTRAINT  fk_r_verbale_illecito_02 FOREIGN KEY (id_lettera) REFERENCES cnm_d_lettera(id_lettera) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_verbale_illecito
	ADD CONSTRAINT  fk_r_verbale_illecito_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_verbale_illecito
	ADD CONSTRAINT  fk_r_verbale_illecito_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_verbale_soggetto
	ADD CONSTRAINT  fk_r_verbale_soggetto_02 FOREIGN KEY (id_soggetto) REFERENCES cnm_t_soggetto(id_soggetto) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_verbale_soggetto
	ADD CONSTRAINT  fk_r_verbale_soggetto_01 FOREIGN KEY (id_verbale) REFERENCES cnm_t_verbale(id_verbale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_verbale_soggetto
	ADD CONSTRAINT  fk_r_verbale_soggetto_04 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_verbale_soggetto
	ADD CONSTRAINT  fk_r_verbale_soggetto_03 FOREIGN KEY (id_ruolo_soggetto) REFERENCES cnm_d_ruolo_soggetto(id_ruolo_soggetto) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_comune
	ADD CONSTRAINT  fk_s_comune_01 FOREIGN KEY (id_comune) REFERENCES cnm_d_comune(id_comune) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_comune
	ADD CONSTRAINT  fk_s_comune_02 FOREIGN KEY (id_provincia) REFERENCES cnm_d_provincia(id_provincia) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_nazione
	ADD CONSTRAINT  fk_s_nazione_01 FOREIGN KEY (id_nazione) REFERENCES cnm_d_nazione(id_nazione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_nazione
	ADD CONSTRAINT  fk_s_nazione_02 FOREIGN KEY (id_origine) REFERENCES cnm_d_origine(id_origine) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_provincia
	ADD CONSTRAINT  fk_s_provincia_01 FOREIGN KEY (id_provincia) REFERENCES cnm_d_provincia(id_provincia) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_provincia
	ADD CONSTRAINT  fk_s_provincia_02 FOREIGN KEY (id_regione) REFERENCES cnm_d_regione(id_regione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_regione
	ADD CONSTRAINT  fk_s_regione_01 FOREIGN KEY (id_regione) REFERENCES cnm_d_regione(id_regione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_regione
	ADD CONSTRAINT  fk_s_regione_02 FOREIGN KEY (id_nazione) REFERENCES cnm_d_nazione(id_nazione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_stato_ordinanza
	ADD CONSTRAINT  fk_s_stato_ordinanza_01 FOREIGN KEY (id_ordinanza) REFERENCES cnm_t_ordinanza(id_ordinanza) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_stato_ordinanza
	ADD CONSTRAINT  fk_s_stato_ordinanza_02 FOREIGN KEY (id_stato_ordinanza) REFERENCES cnm_d_stato_ordinanza(id_stato_ordinanza) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_stato_ordinanza
	ADD CONSTRAINT  fk_s_stato_ordinanza_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_stato_verbale
	ADD CONSTRAINT  fk_s_stato_verbale_01 FOREIGN KEY (id_verbale) REFERENCES cnm_t_verbale(id_verbale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_stato_verbale
	ADD CONSTRAINT  fk_s_stato_verbale_02 FOREIGN KEY (id_stato_verbale) REFERENCES cnm_d_stato_verbale(id_stato_verbale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_s_stato_verbale
	ADD CONSTRAINT  fk_s_stato_verbale_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_allegato
	ADD CONSTRAINT  fk_t_allegato_01 FOREIGN KEY (id_tipo_allegato) REFERENCES cnm_d_tipo_allegato(id_tipo_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_allegato
	ADD CONSTRAINT  fk_t_allegato_02 FOREIGN KEY (id_stato_allegato) REFERENCES cnm_d_stato_allegato(id_stato_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_allegato
	ADD CONSTRAINT  fk_t_allegato_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_allegato
	ADD CONSTRAINT  fk_t_allegato_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_allegato_field
	ADD CONSTRAINT  fk_t_allegato_field_01 FOREIGN KEY (id_allegato) REFERENCES cnm_t_allegato(id_allegato) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_allegato_field
	ADD CONSTRAINT  fk_t_allegato_field_02 FOREIGN KEY (id_field) REFERENCES cnm_c_field(id_field) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_allegato_field
	ADD CONSTRAINT  fk_t_allegato_field_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_allegato_field
	ADD CONSTRAINT  fk_t_allegato_field_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_file
	ADD CONSTRAINT  fk_t_file_01 FOREIGN KEY (id_tipo_file) REFERENCES cnm_d_tipo_file(id_tipo_file) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_file
	ADD CONSTRAINT  fk_t_file_02 FOREIGN KEY (id_stato_file) REFERENCES cnm_d_stato_file(id_stato_file) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_file
	ADD CONSTRAINT  fk_t_file_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_ordinanza
	ADD CONSTRAINT  fk_t_ordinanza_01 FOREIGN KEY (id_tipo_ordinanza) REFERENCES cnm_d_tipo_ordinanza(id_tipo_ordinanza) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_ordinanza
	ADD CONSTRAINT  fk_t_ordinanza_02 FOREIGN KEY (id_stato_ordinanza) REFERENCES cnm_d_stato_ordinanza(id_stato_ordinanza) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_ordinanza
	ADD CONSTRAINT  fk_t_ordinanza_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_ordinanza
	ADD CONSTRAINT  fk_t_ordinanza_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_persona
	ADD CONSTRAINT  fk_t_persona_01 FOREIGN KEY (id_nazione_nascita) REFERENCES cnm_d_nazione(id_nazione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_persona
	ADD CONSTRAINT  fk_t_persona_02 FOREIGN KEY (id_comune_nascita) REFERENCES cnm_d_comune(id_comune) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_persona
	ADD CONSTRAINT  fk_t_persona_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_persona
	ADD CONSTRAINT  fk_t_persona_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_piano_rate
	ADD CONSTRAINT  fk_t_piano_rate_01 FOREIGN KEY (id_stato_piano_rate) REFERENCES cnm_d_stato_piano_rate(id_stato_piano_rate) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_piano_rate
	ADD CONSTRAINT  fk_t_piano_rate_02 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_piano_rate
	ADD CONSTRAINT  fk_t_piano_rate_03 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_rata
	ADD CONSTRAINT  fk_t_rata_01 FOREIGN KEY (id_piano_rate) REFERENCES cnm_t_piano_rate(id_piano_rate) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_rata
	ADD CONSTRAINT  fk_t_rata_02 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_rata
	ADD CONSTRAINT  fk_t_rata_03 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_record
	ADD CONSTRAINT  fk_t_record_01 FOREIGN KEY (id_file) REFERENCES cnm_t_file(id_file) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_record
	ADD CONSTRAINT  fk_t_record_02 FOREIGN KEY (id_tipo_record) REFERENCES cnm_d_tipo_record(id_tipo_record) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_record
	ADD CONSTRAINT  fk_t_record_03 FOREIGN KEY (id_riscossione) REFERENCES cnm_t_riscossione(id_riscossione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_record
	ADD CONSTRAINT  fk_t_record_04 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_record_N2
	ADD CONSTRAINT  fk_t_record_N2_01 FOREIGN KEY (id_record) REFERENCES cnm_t_record(id_record) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_record_N3
	ADD CONSTRAINT  fk_t_record_N3_01 FOREIGN KEY (id_record) REFERENCES cnm_t_record(id_record) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_record_N4
	ADD CONSTRAINT  fk_t_record_N4_01 FOREIGN KEY (id_record) REFERENCES cnm_t_record(id_record) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_record_N4
	ADD CONSTRAINT  fk_t_record_N4_02 FOREIGN KEY (id_tipo_tributo) REFERENCES cnm_d_tipo_tributo(id_tipo_tributo) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_record_ritorno
	ADD CONSTRAINT  fk_t_record_ritorno_01 FOREIGN KEY (id_file_ritorno) REFERENCES cnm_t_file_ritorno(id_file_ritorno);
ALTER TABLE cnm_t_record_ritorno
	ADD CONSTRAINT  fk_t_record_ritorno_02 FOREIGN KEY (id_record) REFERENCES cnm_t_record(id_record);
ALTER TABLE cnm_t_record_ritorno
	ADD CONSTRAINT  fk_t_record_ritorno_03 FOREIGN KEY (id_tipo_record) REFERENCES cnm_d_tipo_record(id_tipo_record);
ALTER TABLE cnm_t_record_ritorno
	ADD CONSTRAINT  fk_t_record_ritorno_04 FOREIGN KEY (id_stato_record) REFERENCES cnm_d_stato_record(id_stato_record);
ALTER TABLE cnm_t_residenza
	ADD CONSTRAINT  fk_t_residenza_01 FOREIGN KEY (id_soggetto) REFERENCES cnm_t_soggetto(id_soggetto) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_residenza
	ADD CONSTRAINT  fk_t_residenza_02 FOREIGN KEY (id_nazione_residenza) REFERENCES cnm_d_nazione(id_nazione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_residenza
	ADD CONSTRAINT  fk_t_residenza_03 FOREIGN KEY (id_comune_residenza) REFERENCES cnm_d_comune(id_comune) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_residenza
	ADD CONSTRAINT  fk_t_residenza_04 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_residenza
	ADD CONSTRAINT  fk_t_residenza_05 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_riscossione
	ADD CONSTRAINT  fk_t_riscossione_01 FOREIGN KEY (id_ordinanza_verb_sog) REFERENCES cnm_r_ordinanza_verb_sog(id_ordinanza_verb_sog) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_riscossione
	ADD CONSTRAINT  fk_t_riscossione_02 FOREIGN KEY (id_stato_riscossione) REFERENCES cnm_d_stato_riscossione(id_stato_riscossione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_riscossione
	ADD CONSTRAINT  fk_t_riscossione_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_riscossione
	ADD CONSTRAINT  fk_t_riscossione_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_societa
	ADD CONSTRAINT  fk_t_societa_01 FOREIGN KEY (id_nazione_sede) REFERENCES cnm_d_nazione(id_nazione) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_societa
	ADD CONSTRAINT  fk_t_societa_02 FOREIGN KEY (id_comune_sede) REFERENCES cnm_d_comune(id_comune) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_societa
	ADD CONSTRAINT  fk_t_societa_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_societa
	ADD CONSTRAINT  fk_t_societa_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_soggetto
	ADD CONSTRAINT  fk_t_soggetto_01 FOREIGN KEY (id_persona) REFERENCES cnm_t_persona(id_persona) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_soggetto
	ADD CONSTRAINT  fk_t_soggetto_02 FOREIGN KEY (id_societa) REFERENCES cnm_t_societa(id_societa) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_soggetto
	ADD CONSTRAINT  fk_t_soggetto_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_soggetto
	ADD CONSTRAINT  fk_t_soggetto_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_sollecito
	ADD CONSTRAINT  fk_t_sollecito_01 FOREIGN KEY (id_ordinanza_verb_sog) REFERENCES cnm_r_ordinanza_verb_sog(id_ordinanza_verb_sog) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_sollecito
	ADD CONSTRAINT  fk_t_sollecito_02 FOREIGN KEY (id_stato_sollecito) REFERENCES cnm_d_stato_sollecito(id_stato_sollecito) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_sollecito
	ADD CONSTRAINT  fk_t_sollecito_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_sollecito
	ADD CONSTRAINT  fk_t_sollecito_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_user
	ADD CONSTRAINT  fk_t_user_01 FOREIGN KEY (id_ruolo) REFERENCES cnm_d_ruolo(id_ruolo) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_user
	ADD CONSTRAINT  fk_t_user_02 FOREIGN KEY (id_gruppo) REFERENCES cnm_d_gruppo(id_gruppo) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_verbale
	ADD CONSTRAINT  fk_t_verbale_01 FOREIGN KEY (id_stato_verbale) REFERENCES cnm_d_stato_verbale(id_stato_verbale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_verbale
	ADD CONSTRAINT  fk_t_verbale_04 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_verbale
	ADD CONSTRAINT  fk_t_verbale_05 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_verbale
	ADD CONSTRAINT  fk_t_verbale_03 FOREIGN KEY (id_ente_accertatore) REFERENCES cnm_d_ente(id_ente) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_verbale
	ADD CONSTRAINT  fk_t_verbale_02 FOREIGN KEY (id_comune_violazione) REFERENCES cnm_d_comune(id_comune) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_calendario
	ADD CONSTRAINT  fk_t_calendario_01 FOREIGN KEY (id_gruppo) REFERENCES cnm_d_gruppo(id_gruppo) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_calendario
	ADD CONSTRAINT  fk_t_calendario_02 FOREIGN KEY (id_comune_udienza) REFERENCES cnm_d_comune(id_comune) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_calendario
	ADD CONSTRAINT  fk_t_calendario_03 FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_calendario
	ADD CONSTRAINT  fk_t_calendario_04 FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
