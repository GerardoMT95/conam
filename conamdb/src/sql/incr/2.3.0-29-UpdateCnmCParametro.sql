-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmCParametro

-- scneario5
UPDATE cnm_c_parametro
SET valore_string = 'Sollecito di pagamento piano di rateizzazione protocollo n. %s (d.i. %s)'
WHERE id_parametro = 37;

