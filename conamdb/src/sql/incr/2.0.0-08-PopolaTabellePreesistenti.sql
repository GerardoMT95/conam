-- ENCODING
SET client_encoding TO 'WIN1258';




-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- nuovo stato allegato (per allegati da spostare in conam)
INSERT INTO cnm_d_stato_allegato VALUES (7, 'Protocollato in altra struttura');





-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- nuovi parametri in cnm_c_parametro richiesti per il Lotto I (per ambiente di test, cambier√† solo codice_ambiente per altri ambienti)
INSERT INTO cnm_c_parametro VALUES (17, 'codice_prodotto', 'CONAM', NULL, NULL, NULL, E'2020-07-01', NULL);
INSERT INTO cnm_c_parametro VALUES (18, 'codice_linea_cliente', 'RP-01', NULL, NULL, NULL, E'2020-07-01', NULL);
INSERT INTO cnm_c_parametro VALUES (19, 'codice_ambiente', 'TEST', NULL, NULL, NULL, E'2020-07-01', NULL);
INSERT INTO cnm_c_parametro VALUES (20, 'codice_unita_installazione', 'CONAMBL', NULL, NULL, NULL, E'2020-07-01', NULL);





-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- nuovo utente (999) e ruolo da usare durante il task schedulato
INSERT INTO cnm_d_ruolo VALUES (5, 'SYSTEM', '2020-06-30', NULL);
INSERT INTO cnm_t_user VALUES (999, 'SYSTEM', NULL, NULL, 5, NULL, '2020-07-01', NULL);





-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- nuovi type in cnm_d_tipo_allegato
UPDATE cnm_d_tipo_allegato SET index_type = 'allegatoLetteraComparsa' WHERE id_tipo_allegato = 28;
UPDATE cnm_d_tipo_allegato SET index_type = 'bollettinoOrdinanza' WHERE id_tipo_allegato = 21;
UPDATE cnm_d_tipo_allegato SET index_type = 'bollettinoRateizzazione' WHERE id_tipo_allegato = 18;
UPDATE cnm_d_tipo_allegato SET index_type = 'comunicazioniAllaCancelleria' WHERE id_tipo_allegato = 25;
UPDATE cnm_d_tipo_allegato SET index_type = 'comunicazioniDallaCancelleria' WHERE id_tipo_allegato = 24;
UPDATE cnm_d_tipo_allegato SET index_type = 'controdeduzione' WHERE id_tipo_allegato = 9;
UPDATE cnm_d_tipo_allegato SET index_type = 'convocazioneDiAudizione' WHERE id_tipo_allegato = 27;
UPDATE cnm_d_tipo_allegato SET index_type = 'disposizioniDelGiudice' WHERE id_tipo_allegato = 14;
UPDATE cnm_d_tipo_allegato SET index_type = 'istanzaAutotutelaAccertatore' WHERE id_tipo_allegato = 8;
UPDATE cnm_d_tipo_allegato SET index_type = 'istanzaRateizzazione' WHERE id_tipo_allegato = 26;
UPDATE cnm_d_tipo_allegato SET index_type = 'letteraAccompagnatoriaOrdinanza' WHERE id_tipo_allegato = 19;
UPDATE cnm_d_tipo_allegato SET index_type = 'letteraComparsaCostituzione' WHERE id_tipo_allegato = 23;
UPDATE cnm_d_tipo_allegato SET index_type = 'letteraRateizzazione' WHERE id_tipo_allegato = 17;
UPDATE cnm_d_tipo_allegato SET index_type = 'letteraSollecito' WHERE id_tipo_allegato = 20;
UPDATE cnm_d_tipo_allegato SET index_type = 'opposizioneGiurisdizionale' WHERE id_tipo_allegato = 13;
UPDATE cnm_d_tipo_allegato SET index_type = 'ordinanzaArchiviazione' WHERE id_tipo_allegato = 11;
UPDATE cnm_d_tipo_allegato SET index_type = 'ordinanzaIngiunzionePagamento' WHERE id_tipo_allegato = 12;
UPDATE cnm_d_tipo_allegato SET index_type = 'rapportoTrasmissioneExArt17' WHERE id_tipo_allegato = 1;
UPDATE cnm_d_tipo_allegato SET index_type = 'relataNotifica' WHERE id_tipo_allegato = 5;
UPDATE cnm_d_tipo_allegato SET index_type = 'ricevutaPagamentoVerbale' WHERE id_tipo_allegato = 7;
UPDATE cnm_d_tipo_allegato SET index_type = 'ricevutaPagamentoOrdinanza' WHERE id_tipo_allegato = 22;
UPDATE cnm_d_tipo_allegato SET index_type = 'rilieviSegnaletici' WHERE id_tipo_allegato = 4;
UPDATE cnm_d_tipo_allegato SET index_type = 'scrittiDifensivi' WHERE id_tipo_allegato = 6;
UPDATE cnm_d_tipo_allegato SET index_type = 'verbaleAccertamento' WHERE id_tipo_allegato = 2;
UPDATE cnm_d_tipo_allegato SET index_type = 'verbaleAudizione' WHERE id_tipo_allegato = 10;
UPDATE cnm_d_tipo_allegato SET index_type = 'verbaleConfisca' WHERE id_tipo_allegato = 16;
UPDATE cnm_d_tipo_allegato SET index_type = 'verbaleContestazione' WHERE id_tipo_allegato = 15;
UPDATE cnm_d_tipo_allegato SET index_type = 'verbaleSequestro' WHERE id_tipo_allegato = 3;



-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- nuovi messaggi in cnm_d_messaggio
INSERT INTO cnm_d_messaggio ("cod_messaggio", "desc_messaggio", "id_tipo_messaggio") VALUES ('SAVEDOC01', 'Il documento %s Ë stato salvato e associato al verbale %s. Salva anche il documento master per fare in modo che sia inserito definitivamente nel fascicolo di Conam.', 0);


-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- nuove tipologie allegati cnm_d_tipo_allegato
INSERT INTO "cnm_d_tipo_allegato" ("id_tipo_allegato", "desc_tipo_allegato", "id_categoria_allegato", "id_utilizzo_allegato", "index_type") VALUES (29, 'Email verbale', 1, 1, NULL);
INSERT INTO "cnm_d_tipo_allegato" ("id_tipo_allegato", "desc_tipo_allegato", "id_categoria_allegato", "id_utilizzo_allegato", "index_type") VALUES (30, 'Email istruttoria', 2, 1, NULL);
INSERT INTO "cnm_d_tipo_allegato" ("id_tipo_allegato", "desc_tipo_allegato", "id_categoria_allegato", "id_utilizzo_allegato", "index_type") VALUES (31, 'Email giurisdizionale ordinanza', 3, 2, NULL);
INSERT INTO "cnm_d_tipo_allegato" ("id_tipo_allegato", "desc_tipo_allegato", "id_categoria_allegato", "id_utilizzo_allegato", "index_type") VALUES (32, 'Email giurisdizionale ordinanza soggetto', 3, 4, NULL);
INSERT INTO "cnm_d_tipo_allegato" ("id_tipo_allegato", "desc_tipo_allegato", "id_categoria_allegato", "id_utilizzo_allegato", "index_type") VALUES (33, 'Email rateizzazione', 4, 5, NULL);

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- relazioni tra nuove tipologie allegati e stato ordinanza in cnm_r_tipo_allegato_so
INSERT INTO "cnm_r_tipo_allegato_so" ("id_stato_ordinanza", "id_tipo_allegato") VALUES (5, 31);
INSERT INTO "cnm_r_tipo_allegato_so" ("id_stato_ordinanza", "id_tipo_allegato") VALUES (2, 31);
INSERT INTO "cnm_r_tipo_allegato_so" ("id_stato_ordinanza", "id_tipo_allegato") VALUES (4, 31);
INSERT INTO "cnm_r_tipo_allegato_so" ("id_stato_ordinanza", "id_tipo_allegato") VALUES (4, 32);
INSERT INTO "cnm_r_tipo_allegato_so" ("id_stato_ordinanza", "id_tipo_allegato") VALUES (3, 32);
INSERT INTO "cnm_r_tipo_allegato_so" ("id_stato_ordinanza", "id_tipo_allegato") VALUES (2, 32);
INSERT INTO "cnm_r_tipo_allegato_so" ("id_stato_ordinanza", "id_tipo_allegato") VALUES (5, 32);


