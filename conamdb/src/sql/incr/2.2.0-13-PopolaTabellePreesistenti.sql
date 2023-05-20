-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDMessaggio
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('DATORDNPR', 'Attenzione: ricontrollare le date inserite per l''ordinanza in quanto non sono compatibili con i fascicoli pregressi.', 1);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('CONFRECPRO', 'Si vuole continuare con il recupero della documentazione individuata tramite il protocollo %s?', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SAVESTORPR', 'Si sta aggiornando lo stato dell''ordinanza pregressa %s da %s a %s', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SIGNERRORM', 'Il documento che si sta cercando di caricare presenta difformità su una o più firme.', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('PRODISPGIU', 'La disposizione del Giudice può essere inserita una sola volta. Prima di concludere il processo, assicurarsi di averla selezionata e caricata.', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('UPDSTATOOK', 'Aggiornamento di stato completato', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SRCHPROT03', 'Attenzione! Il numero di protocollo inserito non è presente su DoQui ACTA. Si consiglia di verificare il dato e ripetere la ricerca.', 2);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SPAGIMPDAT', 'Attenzione! Si sta aggiornando lo stato del sollecito in %s senza aver valorizzato entrambi i campi Importo e Data di pagamento. Sicuro di voler continuare?', 0);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('ENDRECPREG', 'Dal %s non è più possibile recuperare la documentazione pregressa', 0);
UPDATE cnm_d_messaggio SET desc_messaggio='Attenzione! Si è verificato un errore di comunicazione verso la componente DoQui ACTA. Si prega di riprovare o di contattare l''assistenza'  
 WHERE cod_messaggio = 'DOQUIND06';
