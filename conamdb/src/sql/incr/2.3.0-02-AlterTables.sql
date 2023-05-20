-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%						ALTERS							%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- aggiunta ordinanza annullata alla tabella cnm_t_ordinanza (nullable)
-- ALTER TABLE cnm_t_ordinanza ADD COLUMN id_ordinanza_annullata INTEGER;

-- aggiunto email_giudice alla tabella cnm_t_calendario
ALTER TABLE cnm_t_calendario ADD email_giudice varchar(50) NULL;
-- aggiunto data_invio_promemoria_udienza giudice alla tabella cnm_t_calendario
ALTER TABLE cnm_t_calendario ADD data_invio_promemoria_udienza timestamp(0) NULL;
-- aggiunto data_invio_promemoria_documentazione alla tabella cnm_t_calendario
ALTER TABLE cnm_t_calendario ADD data_invio_promemoria_documentazione timestamp(0) NULL;
-- aggiunto id_stato_manuale alla tabella cnm_t_verbale
ALTER TABLE cnm_t_verbale ADD id_stato_manuale numeric(2) NULL;
ALTER TABLE cnm_t_verbale ALTER COLUMN id_stato_manuale SET DEFAULT 0;

-- scenario 5 sollecito piano rate
ALTER TABLE cnm_r_sollecito_sogg_rata ALTER COLUMN data_ora_insert SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE cnm_t_sollecito ADD data_fine_validita DATE NULL default NULL;
ALTER TABLE cnm_t_sollecito ADD id_tipo_sollecito NUMERIC(2,0) NOT NULL DEFAULT 1;
ALTER TABLE cnm_t_sollecito ALTER COLUMN id_tipo_sollecito DROP DEFAULT;