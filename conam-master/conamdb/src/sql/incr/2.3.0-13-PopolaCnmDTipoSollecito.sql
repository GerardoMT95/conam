-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDTipoSollecito

-- scenario5
INSERT INTO cnm_d_tipo_sollecito(id_tipo_sollecito,desc_tipo_sollecito)
VALUES(1,'Sollecito Ordinanza');
INSERT INTO cnm_d_tipo_sollecito(id_tipo_sollecito,desc_tipo_sollecito)
VALUES(2,'Sollecito Piano Rate');

-- FK in cnm_t_sollecito
ALTER TABLE cnm_t_sollecito
	ADD CONSTRAINT  fk_t_sollecito_05 FOREIGN KEY (id_tipo_sollecito) REFERENCES cnm_d_tipo_sollecito(id_tipo_sollecito) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;  