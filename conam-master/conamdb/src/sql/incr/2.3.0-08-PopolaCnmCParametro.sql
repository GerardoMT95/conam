-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmCParametro

-- scneario7
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (23, 'Direzione', 'Direzione Risorse Finanziarie e Patrimonio', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (24, 'Settore', 'Settore Politiche Fiscali e Contenzioso Ammistrativo', NULL, NULL, NULL, '2019-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (25, 'Dirigente del settore', 'Fabrizio Zanella', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (26, 'Sede Ente', 'Corso Regina Margherita, 153 bis;10122  Torino;Tel.011.4321324 ', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (27, 'Oggetto lettera annullamento', 'Determinazione di annullamento Prog. n. %s del %s in relazione all’ordinanza Prog. n. %s del %s nei confronti', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (28, 'Organo accertatore', 'Organo accertatore;sedime + denominazione + n. civico;cap + comune + (PROV)', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (29, 'Corpo lettera annullamento', 'Si trasmette in allegato copia conforme del provvedimento relativo all’oggetto.', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (30, 'Saluti lettera annullamento', 'Cordiali saluti.', NULL, NULL, NULL, '2021-03-01', NULL);

-- scenario 5
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (31, 'Oggetto lettera sollecito rate parziale', 'Sollecito pagamento rate – determinazione di ingiunzione n. %s del %s', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (32, 'Corpo (pt1) lettera sollecito rate parziale', 'Da una verifica contabile risulta che abbia provveduto al pagamento delle rate relative alla determinazione in oggetto sino alla data del %s, che la somma da versare ammonta a € %s e rimangono ancora da pagare n. %s rate.', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (33, 'Corpo (pt2) lettera sollecito rate parziale', 'Si invita pertanto al pagamento della somma sopra indicata entro il termine di dieci giorni dal ricevimento della presente. In caso di difetto si procederà all’avvio di esecuzione forzata per il recupero di quanto dovuto con ulteriore aggravio di spese e interessi a norma di legge.', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (34, 'Oggetto lettera sollecito rate n.p.', 'Sollecito per rate non pervenute – Determinazione di ingiunzione n. %s del %s.', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (35, 'Corpo (pt1) lettera sollecito rate n.p.', 'Su richiesta di rateizzazione pervenuta via pec in data _______ dal sig. __________, era stato concesso il pagamento rateale ai sensi dell’art. 26 della Legge 689/81.', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (36, 'Corpo (pt2) lettera sollecito rate n.p.', 'A tutt’oggi non risulta pervenuto alcun versamento relativo alle istanze di rateizzazione in oggetto. Pertanto, se non provvederanno entro 10 giorni dal ricevimento della presente al versamento delle rate omesse, il settore scrivente dovrà ricorrere alle necessarie procedure di recupero dell’intero credito in un’unica soluzione, mediante riscossione coattiva.', NULL, NULL, NULL, '2021-03-01', NULL);
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (37, 'Oggetto pagamento sollecito rate', 'Sollecito di pagamento piano di rateizzazione protocollo n. ', NULL, NULL, NULL, '2021-03-01', NULL);
