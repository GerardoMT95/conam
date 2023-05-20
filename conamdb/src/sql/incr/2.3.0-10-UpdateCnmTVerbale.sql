-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- possibili stati in cnm_d_stato_manauale
INSERT INTO cnm_d_stato_manuale (id_stato_manuale, desc_stato_manuale) VALUES (0, 'NON DEFINITO');
INSERT INTO cnm_d_stato_manuale (id_stato_manuale, desc_stato_manuale) VALUES (1, 'NON DI COMPETENZA');
INSERT INTO cnm_d_stato_manuale (id_stato_manuale, desc_stato_manuale) VALUES (2, 'RISCOSSIONE DEVOLUTA PER COMPETENZA');

-- campo in cnm_t_verbale = 0
update cnm_t_verbale set id_stato_manuale = 0 where id_stato_manuale is null;