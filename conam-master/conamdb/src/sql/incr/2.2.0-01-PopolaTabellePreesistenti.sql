-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDUseCase
INSERT INTO "cnm_d_use_case" ("id_use_case", "cod_use_case", "desc_use_case") VALUES (23, 'CONAM20', 'Gestione pregresso inserimento');
INSERT INTO "cnm_d_use_case" ("id_use_case", "cod_use_case", "desc_use_case") VALUES (24, 'CONAM21', 'Gestione pregresso ricerca');

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmRUseCaseRuolo
-- NB: modificare la data fine validita con quella richiesta
INSERT INTO "cnm_r_use_case_ruolo" ("id_use_case_ruolo", "id_ruolo", "id_use_case", "inizio_validita", "fine_validita") 
VALUES (37, 2, 23, '2020-09-03', '2021-09-03');
INSERT INTO "cnm_r_use_case_ruolo" ("id_use_case_ruolo", "id_ruolo", "id_use_case", "inizio_validita", "fine_validita") 
VALUES (38, 2, 24, '2020-09-03', '2021-09-03');



-- %%%%%%%%%%%%%	GestionePregresso: ricercaProtocolloActa	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDMessaggio
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SRCHPROT01', 'Attenzione: il numero di protocollo inserito non appartiene a documentazione pregressa, in quanto la data limite pregresso è %s, mentre la data di protocollo registrata risulta essere %s.', 2);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SRCHPROT02', 'Attenzione: i documenti associati al protocollo cercato sono già  presenti nella base dati Conam.', 2);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('STATOVERIN', 'Stato del verbale non compatibile con questo servizio.', 1);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('VERDATEPRE', 'Esistono delle incompatibilità che non consentono il salvataggio del verbale: %s. <br>Per proseguire è necessario prima correggere le incompatibilità.', 1);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('DATACCNPR', 'Data accertamento non compatibile con fascicoli pregressi.', 1);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SAVESTAPR', 'Si sta aggiornando lo stato del fascicolo pregresso %s da %s a %s', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('STOPLAVPR', 'Attenzione: fase di lavorazione fascicoli pregressi conclusa', 2);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SAVSTPOK1', 'Aggiornamento del fascicolo da %s a %s completato con successo e fascicolo pregresso creato sulla nuova struttura archivistica di DoQui ACTA.', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SAVSTPOK2', 'Aggiornamento del fascicolo da %s a %s completato con successo.', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SAVSTPOK3', 'Aggiornamento del fascicolo completato con successo. Il fascicolo ora può essere lavorato con le funzionalità delle altre voci di menu.', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('STVERPRWAR', 'Attenzione! Gli stati del fascicolo pregresso sono disponibili in un apposito elenco. Una volta confermato non sarà possibile tornare allo stato precedente.', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('STORDPRWAR', 'Attenzione! Gli stati dell''ordinanza pregressa sono disponibili in un apposito elenco. Una volta confermato non sarà possibile tornare allo stato precedente.', 0);


-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDParametro
-- NB: modificare il campo valore_date con la "vera" data discriminante
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, 
valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (21,'Data discriminante gestione pregresso',null,
null,TO_DATE('2015-09-01','YYYY-MM-DD'),null,TO_DATE('2020-09-01','YYYY-MM-DD'),null);

-- NB: modificare il campo valore_date con la "vera" data termine recupero pregresso
INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, 
valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (22,'Data termine recupero',null,
null,TO_DATE('2020-12-31','YYYY-MM-DD'),null,TO_DATE('2020-09-01','YYYY-MM-DD'),null);


-- STADOC to DOQUI
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
UPDATE cnm_d_messaggio 
SET cod_messaggio = REPLACE(cod_messaggio,'STADOC','DOQUI');