-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		FOREIGN KEYS									%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- scenario 1
ALTER TABLE cnm_t_scritto_difensivo	ADD CONSTRAINT  fk_t_scritto_difensivo_01 
FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) 
MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE cnm_t_scritto_difensivo	ADD CONSTRAINT  fk_t_scritto_difensivo_02 
FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) 
MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE cnm_t_scritto_difensivo	ADD CONSTRAINT  fk_t_scritto_difensivo_03 
FOREIGN KEY (id_allegato) REFERENCES cnm_t_allegato(id_allegato) 
MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE cnm_t_scritto_difensivo	ADD CONSTRAINT  fk_t_scritto_difensivo_04
FOREIGN KEY (id_ambito) REFERENCES cnm_d_ambito(id_ambito) 
MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE cnm_t_scritto_difensivo	ADD CONSTRAINT  fk_t_scritto_difensivo_05
FOREIGN KEY (id_ente) REFERENCES cnm_d_ente(id_ente) 
MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE cnm_t_scritto_difensivo	ADD CONSTRAINT  fk_t_scritto_difensivo_06
FOREIGN KEY (id_modalita_caricamento) REFERENCES cnm_d_modalita_caricamento(id_modalita_caricamento) 
MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

--

--ALTER TABLE cnm_r_scritto_illecito ADD CONSTRAINT  fk_r_scritto_illecito_01 
--FOREIGN KEY (id_user_insert) REFERENCES cnm_t_user(id_user) 
--MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

--ALTER TABLE cnm_r_scritto_illecito ADD CONSTRAINT  fk_r_scritto_illecito_02
--FOREIGN KEY (id_user_update) REFERENCES cnm_t_user(id_user) 
--MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

--ALTER TABLE cnm_r_scritto_illecito ADD CONSTRAINT  fk_r_scritto_illecito_03
--FOREIGN KEY (id_scritto_difensivo) REFERENCES cnm_t_scritto_difensivo(id_scritto_difensivo) 
--MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

--ALTER TABLE cnm_r_scritto_illecito ADD CONSTRAINT  fk_r_scritto_illecito_04
--FOREIGN KEY (id_lettera) REFERENCES cnm_d_lettera(id_lettera) 
--MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;



--- scenario 8
ALTER TABLE cnm_t_acconto ADD CONSTRAINT fk_t_acconto FOREIGN KEY (id_ordinanza) REFERENCES cnm_t_ordinanza(id_ordinanza) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE cnm_t_acconto ADD CONSTRAINT fk_t_acconto_02 FOREIGN KEY (id_soggetto) REFERENCES cnm_t_soggetto(id_soggetto) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE cnm_t_acconto ADD CONSTRAINT fk_t_acconto_03 FOREIGN KEY (id_allegato) REFERENCES cnm_t_allegato(id_allegato) ON DELETE CASCADE ON UPDATE CASCADE;