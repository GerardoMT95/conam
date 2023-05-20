-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		PRIMARY KEYS									%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- scenario1
ALTER TABLE cnm_t_scritto_difensivo ADD CONSTRAINT  pk_t_scritto_difensivo PRIMARY KEY (id_scritto_difensivo);
ALTER TABLE cnm_d_modalita_caricamento ADD CONSTRAINT  pk_d_modalita_caricamento PRIMARY KEY (id_modalita_caricamento);
--
--ALTER TABLE cnm_r_scritto_illecito ADD CONSTRAINT  pk_r_scritto_illecito PRIMARY KEY (id_scritto_illecito);
--ALTER TABLE cnm_r_scritto_illecito ADD CONSTRAINT  ak_r_scritto_illecito_01 UNIQUE (id_scritto_difensivo,id_lettera);

-- scenario 8
ALTER TABLE cnm_t_acconto ADD CONSTRAINT pk_t_acconto PRIMARY KEY (id_acconto);
--ALTER TABLE cnm_t_acconto ADD CONSTRAINT fk_t_acconto FOREIGN KEY (id_ordinanza) REFERENCES cnm_t_ordinanza(id_ordinanza) ON DELETE CASCADE ON UPDATE CASCADE;