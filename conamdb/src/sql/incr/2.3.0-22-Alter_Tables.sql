-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		ALTER TABLES									%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
ALTER TABLE cnm_r_verbale_soggetto ADD recidivo bool NULL DEFAULT false;

ALTER TABLE cnm_t_ordinanza ADD data_fine_validita DATE NULL default NULL;

ALTER TABLE cnm_t_rata ADD data_fine_validita DATE NULL default NULL;