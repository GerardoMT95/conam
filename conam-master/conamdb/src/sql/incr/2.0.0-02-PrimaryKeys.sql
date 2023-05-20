-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		PRIMARY KEYS									%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

ALTER TABLE cnm_c_proprieta ADD CONSTRAINT pk_c_proprieta PRIMARY KEY (id_proprieta);
ALTER TABLE cnm_d_servizio ADD CONSTRAINT pk_d_servizio PRIMARY KEY (id_servizio);
ALTER TABLE cnm_d_stato_richiesta ADD CONSTRAINT pk_d_stato_richiesta PRIMARY KEY (id_stato_richiesta);
ALTER TABLE cnm_d_struttura_acta ADD CONSTRAINT pk_d_struttura_acta PRIMARY KEY (id_struttura_acta);
ALTER TABLE cnm_d_tipo_documento ADD CONSTRAINT pk_d_tipo_documento PRIMARY KEY (id_tipo_documento);
ALTER TABLE cnm_d_tipo_fornitore ADD CONSTRAINT pk_d_tipo_fornitore PRIMARY KEY (id_tipo_fornitore);
ALTER TABLE cnm_d_tipo_operazione ADD CONSTRAINT pk_d_tipo_operazione PRIMARY KEY (id_tipo_operazione);
ALTER TABLE cnm_r_tipo_doc_padre_figlio ADD CONSTRAINT pk_r_tipo_doc_padre_figlio PRIMARY KEY (id_tipo_doc_padre, id_tipo_doc_figlio);
ALTER TABLE cnm_r_richiesta_documento ADD CONSTRAINT pk_r_richiesta_documento PRIMARY KEY (id_richiesta, id_documento);
ALTER TABLE cnm_t_documento ADD CONSTRAINT pk_t_documento PRIMARY KEY (id_documento);
ALTER TABLE cnm_t_richiesta ADD CONSTRAINT pk_t_richiesta PRIMARY KEY (id_richiesta);
ALTER TABLE csi_log_audit ADD CONSTRAINT pk_csi_log_audit PRIMARY KEY (id_log);