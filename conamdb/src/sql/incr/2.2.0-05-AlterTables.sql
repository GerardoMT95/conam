-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		ALTERS											%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- aggiunto stato pregresso alla tabella cnm_t_verbale
ALTER TABLE cnm_t_verbale ADD COLUMN id_stato_pregresso  NUMERIC(2,0) NOT NULL DEFAULT 1;

	
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmTResidenza
ALTER TABLE cnm_t_residenza ADD COLUMN id_verbale INTEGER default 0;

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmTAllegato
ALTER TABLE cnm_t_allegato ADD COLUMN flag_documento_pregresso BOOLEAN NOT NULL DEFAULT 'false';