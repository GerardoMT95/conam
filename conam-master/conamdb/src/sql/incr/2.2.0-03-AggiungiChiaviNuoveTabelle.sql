-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		ADD CONSTRAINTS										%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%



ALTER TABLE cnm_d_stato_pregresso ADD CONSTRAINT pk_d_stato_pregresso PRIMARY KEY (id_stato_pregresso);

ALTER TABLE cnm_d_stato_pregresso ADD CONSTRAINT  ak_d_stato_pregresso_01 UNIQUE (desc_stato_pregresso);
