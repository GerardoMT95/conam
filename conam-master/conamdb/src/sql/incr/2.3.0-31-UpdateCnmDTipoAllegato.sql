-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDTipoAllegato

-- jira 156
UPDATE cnm_d_tipo_allegato
SET id_categoria_allegato = 3
WHERE id_tipo_allegato = 38;
