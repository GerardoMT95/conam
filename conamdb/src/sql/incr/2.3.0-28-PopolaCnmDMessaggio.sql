-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDMessaggio

-- scenario1
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SRCHPROT04', 'Attenzione: il documento selezionato contiene scritti difensivi non ancora associati ad un fascicolo. Per consultare il contenuto del documento, si prega di cliccare sul rispettivo nome nella colonna "Documento Individuato".', 2);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('SOGGISTNOK', 'Attenzione, sono stati selezionati sia soggetti aventi l’istanza di rateizzazione che soggetti privi di istanza di rateizzazione. Per continuare selezionare soggetti omogenei.', 1);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('PROTGIASCR', 'Attenzione, il protocollo inserito individua già uno scritto difensivo caricato da dispositivo.', 1);


