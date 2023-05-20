-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		FOREIGN KEYS									%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

ALTER TABLE cnm_t_mail_inviata ADD CONSTRAINT  fk_t_mail_inviata_01 FOREIGN KEY (id_calendario) 		REFERENCES cnm_t_calendario(id_calendario);

ALTER TABLE cnm_t_verbale ADD CONSTRAINT fk_t_verbale_07 FOREIGN KEY (id_stato_manuale) REFERENCES cnm_d_stato_manuale(id_stato_manuale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;


-- scenario 7 ordinanza annullata
ALTER TABLE cnm_r_ordinanza_figlio
ADD CONSTRAINT  fk_r_ordinanza_figlio_01 FOREIGN KEY (id_ordinanza) REFERENCES cnm_t_ordinanza(id_ordinanza) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_ordinanza_figlio
ADD CONSTRAINT  fk_r_ordinanza_figlio_02 FOREIGN KEY (id_ordinanza_figlio) REFERENCES cnm_t_ordinanza(id_ordinanza) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;


-- scenario 5 sollecito piano rate
ALTER TABLE cnm_r_sollecito_sogg_rata
	ADD CONSTRAINT  fk_r_sollecito_sogg_rata_01 FOREIGN KEY (id_ordinanza_verb_sog) REFERENCES cnm_r_ordinanza_verb_sog(id_ordinanza_verb_sog) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_sollecito_sogg_rata
	ADD CONSTRAINT  fk_r_sollecito_sogg_rata_02 FOREIGN KEY (id_rata) REFERENCES cnm_t_rata(id_rata) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_r_sollecito_sogg_rata
	ADD CONSTRAINT  fk_t_sollecito_sogg_rata_03 FOREIGN KEY (id_sollecito) REFERENCES cnm_t_sollecito(id_sollecito) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
	
ALTER TABLE cnm_d_tipo_sollecito
  	ADD CONSTRAINT  ak_d_tipo_sollecito_01 UNIQUE (desc_tipo_sollecito);
 