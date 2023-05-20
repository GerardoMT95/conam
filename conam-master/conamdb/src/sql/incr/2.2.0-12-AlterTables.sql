-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		ALTERS											%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmTOrdinanza
ALTER TABLE cnm_t_ordinanza ADD COLUMN flag_documento_pregresso BOOLEAN NOT NULL DEFAULT 'false';
-- CnmTPianoRate
ALTER TABLE cnm_t_piano_rate ADD COLUMN flag_documento_pregresso BOOLEAN NOT NULL DEFAULT 'false';
-- CnmTSollecito
ALTER TABLE cnm_t_sollecito ADD COLUMN flag_documento_pregresso BOOLEAN NOT NULL DEFAULT 'false';
-- CnmTDocumento
ALTER TABLE cnm_t_documento ALTER COLUMN identificativo_entita_fruitore TYPE VARCHAR(500);
