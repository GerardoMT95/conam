-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		DEFAULT VALUES									%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

ALTER TABLE cnm_t_documento ALTER COLUMN data_ora_insert SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE cnm_t_richiesta ALTER COLUMN data_ora_insert SET DEFAULT CURRENT_TIMESTAMP;