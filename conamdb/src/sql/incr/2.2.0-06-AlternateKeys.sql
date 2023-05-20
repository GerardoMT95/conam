-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		ALTERNATE KEYS		                              %%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
ALTER TABLE cnm_t_verbale
	ADD CONSTRAINT  fk_t_verbale_06 FOREIGN KEY (id_stato_pregresso) REFERENCES cnm_d_stato_pregresso(id_stato_pregresso) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE cnm_t_residenza
  DROP CONSTRAINT  ak_t_residenza_01;

ALTER TABLE cnm_t_residenza
  ADD CONSTRAINT  ak_t_residenza_01 UNIQUE (id_soggetto, id_verbale);
