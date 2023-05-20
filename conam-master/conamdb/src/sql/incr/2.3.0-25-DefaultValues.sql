-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		FOREIGN KEYS									%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- scenario 1
ALTER TABLE cnm_t_scritto_difensivo ALTER COLUMN data_ora_insert SET DEFAULT CURRENT_TIMESTAMP;
--
--ALTER TABLE cnm_r_scritto_illecito ALTER COLUMN data_ora_insert SET DEFAULT CURRENT_TIMESTAMP;
 