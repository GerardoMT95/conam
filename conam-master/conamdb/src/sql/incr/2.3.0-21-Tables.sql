-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		NEW TABLES										%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- scenario1
CREATE TABLE cnm_t_scritto_difensivo
( id_scritto_difensivo            SERIAL                    NOT NULL
, nome_file   					  VARCHAR(100)				NOT NULL
, num_verbale_accertamento		  VARCHAR(100)	
, nome		  					  VARCHAR(50)	
, cognome		  				  VARCHAR(50)	
, ragione_sociale				  VARCHAR(1000)	
, objectid_acta					  VARCHAR(300)	
, numero_protocollo				  VARCHAR(50)
, data_ora_protocollo             TIMESTAMP WITH TIME ZONE  NOT NULL
, id_modalita_caricamento         NUMERIC(2,0)              NOT NULL
, flag_associato                  BOOLEAN                   NOT NULL
, id_allegato                     INTEGER                   NOT NULL
, id_ente                         INTEGER                   NOT NULL
, id_ambito                       INTEGER                   NOT NULL
, id_norma                        INTEGER
, id_articolo                     INTEGER
, id_comma                        INTEGER
, id_lettera                      INTEGER
, id_user_insert                  NUMERIC(3)                NOT NULL
, data_ora_insert                 TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update                  NUMERIC(3)
, data_ora_update                 TIMESTAMP WITH TIME ZONE
);


CREATE TABLE cnm_d_modalita_caricamento
( id_modalita_caricamento    NUMERIC(2)    NOT NULL
, desc_modalita_caricamento  VARCHAR(100)  NOT NULL
);



--
--CREATE TABLE cnm_r_scritto_illecito
--( id_scritto_illecito   SERIAL                    NOT NULL
--, id_scritto_difensivo  INTEGER                   NOT NULL
--, id_lettera            INTEGER                   NOT NULL
--, id_user_insert        NUMERIC(3)                NOT NULL
--, data_ora_insert       TIMESTAMP WITH TIME ZONE  NOT NULL
--, id_user_update        NUMERIC(3)
--, data_ora_update       TIMESTAMP WITH TIME ZONE
--);


CREATE TABLE cnm_t_acconto (
	id_acconto serial NOT NULL,
	id_ordinanza int4 NOT NULL,
	importo_acconto numeric(10,2) NULL,
	data_pagamento_acconto date NULL,
	conto_corrente_versamento varchar(100) NULL,
	id_allegato int4 NULL,
	id_soggetto int4 NULL
);

