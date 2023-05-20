-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		FOREIGN KEYS									%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

ALTER TABLE cnm_t_documento ADD CONSTRAINT  fk_t_documento_01 FOREIGN KEY (id_user_insert) 		REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_documento	ADD CONSTRAINT  fk_t_documento_02 FOREIGN KEY (id_user_update) 		REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_documento ADD CONSTRAINT  fk_t_documento_03 FOREIGN KEY (id_tipo_documento) 	REFERENCES cnm_d_tipo_documento (id_tipo_documento);

ALTER TABLE cnm_t_richiesta ADD CONSTRAINT  fk_t_richiesta_01 FOREIGN KEY (id_user_insert) 		REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_richiesta	ADD CONSTRAINT  fk_t_richiesta_02 FOREIGN KEY (id_user_update) 		REFERENCES cnm_t_user(id_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cnm_t_richiesta ADD CONSTRAINT  fk_t_richiesta_03 FOREIGN KEY (id_stato_richiesta) 	REFERENCES cnm_d_stato_richiesta (id_stato_richiesta);
ALTER TABLE cnm_t_richiesta ADD CONSTRAINT  fk_t_richiesta_04 FOREIGN KEY (id_servizio) 		REFERENCES cnm_d_servizio (id_servizio);

ALTER TABLE cnm_r_richiesta_documento ADD CONSTRAINT fk_r_richiesta_documento_01 FOREIGN KEY (id_richiesta) REFERENCES cnm_t_richiesta (id_richiesta);
ALTER TABLE cnm_r_richiesta_documento ADD CONSTRAINT fk_r_richiesta_documento_02 FOREIGN KEY (id_documento) REFERENCES cnm_t_documento (id_documento);

ALTER TABLE cnm_r_tipo_doc_padre_figlio ADD CONSTRAINT fk_r_tipo_doc_padre_figlio_01 FOREIGN KEY (id_tipo_doc_padre) REFERENCES cnm_d_tipo_documento (id_tipo_documento);
ALTER TABLE cnm_r_tipo_doc_padre_figlio ADD CONSTRAINT fk_r_tipo_doc_padre_figlio_02 FOREIGN KEY (id_tipo_doc_figlio) REFERENCES cnm_d_tipo_documento (id_tipo_documento);


ALTER TABLE cnm_d_servizio ADD CONSTRAINT fk_d_servizio_01 FOREIGN KEY (id_tipo_fornitore) REFERENCES cnm_d_tipo_fornitore (id_tipo_fornitore);
ALTER TABLE cnm_d_servizio ADD CONSTRAINT fk_d_servizio_02 FOREIGN KEY (id_tipo_operazione) REFERENCES cnm_d_tipo_operazione (id_tipo_operazione);


ALTER TABLE cnm_d_tipo_documento ADD CONSTRAINT fk_d_tipo_documento_01 FOREIGN KEY (id_struttura_acta_root) REFERENCES cnm_d_struttura_acta (id_struttura_acta);
ALTER TABLE cnm_d_tipo_documento ADD CONSTRAINT fk_d_tipo_documento_02 FOREIGN KEY (id_struttura_acta_folder) REFERENCES cnm_d_struttura_acta (id_struttura_acta);