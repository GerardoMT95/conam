-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		ALTERS											%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- apposggio id per spostamento
ALTER TABLE cnm_t_allegato ADD COLUMN objectid_spostamento_acta VARCHAR(300);

-- per nuovo index custom model
ALTER TABLE cnm_d_tipo_allegato ADD COLUMN index_type VARCHAR(100);



