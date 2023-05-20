-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		ALTERNATE KEYS									%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

ALTER TABLE cnm_c_proprieta ADD UNIQUE (nome);

-- per documenti multitipo da spostare
ALTER TABLE cnm_t_allegato  DROP CONSTRAINT  ak_t_allegato_02;
ALTER TABLE cnm_t_allegato  DROP CONSTRAINT  ak_t_allegato_03;

