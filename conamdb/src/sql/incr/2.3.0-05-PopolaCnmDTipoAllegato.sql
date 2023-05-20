-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDTipoAllegato

-- scenario7
INSERT INTO cnm_d_tipo_allegato (id_tipo_allegato, desc_tipo_allegato, id_categoria_allegato, id_utilizzo_allegato, index_type)
VALUES (34, 'Ordinanza di annullamento - archiviazione', 2, 4, 'ordinanzaArchiviazione');
INSERT INTO cnm_d_tipo_allegato (id_tipo_allegato, desc_tipo_allegato, id_categoria_allegato, id_utilizzo_allegato, index_type)
VALUES (35, 'Ordinanza di annullamento - ingiunzione', 2, 4, 'ordinanzaIngiunzionePagamento');

-- scenario5
INSERT INTO cnm_d_tipo_allegato (id_tipo_allegato, desc_tipo_allegato, id_categoria_allegato, id_utilizzo_allegato, index_type)
VALUES (36, 'Lettera Sollecito Rate', 4, 5, 'letteraSollecito');
INSERT INTO cnm_d_tipo_allegato (id_tipo_allegato, desc_tipo_allegato, id_categoria_allegato, id_utilizzo_allegato, index_type)
VALUES (37, 'Bollettino Sollecito Rate', 4, 5, 'bollettinoOrdinanza');