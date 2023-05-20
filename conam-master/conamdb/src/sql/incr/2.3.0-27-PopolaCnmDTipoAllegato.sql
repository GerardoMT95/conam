-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDTipoAllegato

-- scenario6
INSERT INTO cnm_d_tipo_allegato (id_tipo_allegato, desc_tipo_allegato, id_categoria_allegato, id_utilizzo_allegato, index_type)
VALUES (38, 'Allegato istanza di rateizzazione', 2, 4, 'istanzaRateizzazione');

INSERT INTO cnm_d_tipo_allegato (id_tipo_allegato, desc_tipo_allegato, id_categoria_allegato, id_utilizzo_allegato, index_type)
VALUES (39, 'Acconto Ordinanza', 2, 2, 'ordinanzaArchiviazione');