-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		PRIMARY KEYS									%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

ALTER TABLE cnm_t_mail_inviata ADD CONSTRAINT pk_t_mail_inviata PRIMARY KEY (id_mail);
ALTER TABLE cnm_c_batch_email ADD CONSTRAINT pk_c_batch_email PRIMARY KEY (id_parametro_email);

ALTER TABLE cnm_d_stato_manuale ADD CONSTRAINT pk_d_stato_manuale PRIMARY KEY (id_stato_manuale);
ALTER TABLE cnm_d_stato_manuale ADD CONSTRAINT ak_d_stato_manuale UNIQUE (desc_stato_manuale);


-- scenario 7 ordinanza annullata
ALTER TABLE cnm_r_ordinanza_figlio
ADD CONSTRAINT  pk_r_ordinanza_figlio PRIMARY KEY (id_ordinanza,id_ordinanza_figlio);


-- scenario 5 sollecito piano rate
ALTER TABLE cnm_r_sollecito_sogg_rata
	ADD CONSTRAINT  pk_r_sollecito_sogg_rata PRIMARY KEY ( id_sollecito, id_ordinanza_verb_sog, id_rata );

ALTER TABLE cnm_d_tipo_sollecito
	ADD CONSTRAINT  pk_d_tipo_sollecito PRIMARY KEY (id_tipo_sollecito);