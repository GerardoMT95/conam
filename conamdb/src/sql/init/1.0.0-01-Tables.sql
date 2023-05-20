CREATE TABLE dt_limiti_amministrativi
( record  VARCHAR(675)  NOT NULL
);

CREATE TABLE dt_stati_esteri_istat
( record  VARCHAR(213)  NOT NULL
);

CREATE TABLE dt_stati_esteri_ministero
( record  VARCHAR(305)  NOT NULL
);

CREATE TABLE dt_assoc_esteri_ministero
( record  VARCHAR(55)  NOT NULL
);

CREATE TABLE cnm_c_field
( id_field          NUMERIC(5)    NOT NULL
, id_tipo_allegato  NUMERIC(2)    NOT NULL
, id_field_type     NUMERIC(2)    NOT NULL
, label             VARCHAR(100)  NOT NULL
, max_length        NUMERIC(3)
, scale             NUMERIC(2)
, required          BOOLEAN       NOT NULL
, ordine            NUMERIC(3)    NOT NULL
, riga              NUMERIC(2)    NOT NULL
, id_elenco         NUMERIC(2)
, inizio_validita   DATE          NOT NULL
, fine_validita     DATE 
);

CREATE TABLE cnm_c_field_type
( id_field_type     NUMERIC(2)    NOT NULL
, desc_field_type   VARCHAR(20)   NOT NULL 
);

CREATE TABLE cnm_c_immagine
( id_immagine   NUMERIC(3)    NOT NULL
, cod_immagine  VARCHAR(10)   NOT NULL
, desc_immagine VARCHAR(100)  NOT NULL
, immagine      BYTEA         NOT NULL
);

CREATE TABLE cnm_c_immagine_report
( id_report   NUMERIC(3)  NOT NULL
, id_immagine NUMERIC(3)  NOT NULL
);

CREATE TABLE cnm_c_parametro
( id_parametro      NUMERIC(4)      NOT NULL
, desc_parametro    VARCHAR(200)    NOT NULL
, valore_string     VARCHAR(1000)
, valore_number     NUMERIC
, valore_date       DATE
, valore_boolean    BOOLEAN
, inizio_validita   DATE            NOT NULL
, fine_validita     DATE
);

CREATE TABLE cnm_c_report
( id_report         NUMERIC(3)                NOT NULL
, cod_report        VARCHAR(20)               NOT NULL
, desc_report       VARCHAR(100)              NOT NULL
, jrXml             XML                       NOT NULL
, jasper            BYTEA
, data_ora_insert   TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update    NUMERIC(3)                NOT NULL
, data_ora_update   TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_c_schedulatore
( id_schedulatore   SERIAL                    NOT NULL
, ip                VARCHAR(20)               NOT NULL
, ultimo_ping       TIMESTAMP WITH TIME ZONE  NOT NULL
, leader            BOOLEAN                   NOT NULL
, data_ora_insert   TIMESTAMP WITH TIME ZONE  NOT NULL
);

CREATE TABLE cnm_d_ambito
( id_ambito         SERIAL                    NOT NULL
, desc_ambito       VARCHAR(200)              NOT NULL 
, acronimo          VARCHAR(5)
, eliminato         BOOLEAN
, id_user_insert    NUMERIC(3)                NOT NULL
, data_ora_insert   TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update    NUMERIC(3)
, data_ora_update   TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_d_articolo
( id_articolo       SERIAL                    NOT NULL
, desc_articolo     VARCHAR(100)              NOT NULL
, id_ente_norma     INTEGER                   NOT NULL
, inizio_validita   DATE                      NOT NULL
, fine_validita     DATE
, eliminato         BOOLEAN
, id_user_insert    NUMERIC(3)                NOT NULL
, data_ora_insert   TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update    NUMERIC(3)
, data_ora_update   TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_d_categoria_allegato
( id_categoria_allegato     NUMERIC(2)    NOT NULL
, desc_categoria_allegato   VARCHAR(100)  NOT NULL
);

CREATE TABLE cnm_d_comma
( id_comma          SERIAL                    NOT NULL
, desc_comma        VARCHAR(100)              NOT NULL
, id_articolo       INTEGER                   NOT NULL
, inizio_validita   DATE                      NOT NULL
, fine_validita     DATE
, eliminato         BOOLEAN
, id_user_insert    NUMERIC(3)                NOT NULL
, data_ora_insert   TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update    NUMERIC(3)
, data_ora_update   TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_d_comune
( id_comune           NUMERIC(8)    NOT NULL
, cod_istat_comune    VARCHAR(6)
, cod_belfiore_comune VARCHAR(4)
, denom_comune        VARCHAR(100)  NOT NULL
, id_provincia        NUMERIC(7)    NOT NULL
, inizio_validita     DATE          NOT NULL
, fine_validita       DATE
, dt_id_comune        NUMERIC(20)   NOT NULL
, dt_id_comune_prev   NUMERIC(20)
, dt_id_comune_next   NUMERIC(20)
);

CREATE TABLE cnm_d_elemento_elenco
( id_elemento_elenco    NUMERIC(6)    NOT NULL
, desc_elemento_elenco  VARCHAR(100)  NOT NULL
, id_elenco             NUMERIC(2)    NOT NULL
);

CREATE TABLE cnm_d_elenco
( id_elenco   NUMERIC(2)    NOT NULL
, desc_elenco VARCHAR(100)  NOT NULL
);

CREATE TABLE cnm_d_ente
( id_ente     NUMERIC(2)    NOT NULL
, desc_ente   VARCHAR(50)   NOT NULL
);

CREATE TABLE cnm_d_lettera
( id_lettera        SERIAL                    NOT NULL
, lettera           VARCHAR(10)               NOT NULL
, id_comma          INTEGER                   NOT NULL
, sanzione_minima   NUMERIC(8,2)
, sanzione_massima  NUMERIC(8,2)
, importo_ridotto   NUMERIC(10,2)             NOT NULL
, scadenza_importi  DATE                      NOT NULL
, desc_illecito     VARCHAR(1000)             NOT NULL
, inizio_validita   DATE                      NOT NULL
, fine_validita     DATE
, eliminato         BOOLEAN
, id_user_insert    NUMERIC(3)                NOT NULL
, data_ora_insert   TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update    NUMERIC(3)
, data_ora_update   TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_d_messaggio
( id_messaggio      SERIAL        NOT NULL
, cod_messaggio     VARCHAR(10)   NOT NULL
, desc_messaggio    VARCHAR(200)  NOT NULL
, id_tipo_messaggio NUMERIC(2)    NOT NULL
);

CREATE TABLE cnm_d_nazione
( id_nazione            NUMERIC(3)    NOT NULL
, cod_istat_nazione     VARCHAR(3)    NOT NULL
, denom_nazione         VARCHAR(100)  NOT NULL
, inizio_validita       DATE          NOT NULL
, fine_validita         DATE
, dt_id_stato           NUMERIC(20)   NOT NULL
, dt_id_stato_prev      NUMERIC(20)
, dt_id_stato_next      NUMERIC(20)
);

CREATE TABLE cnm_d_modalita_notifica
( id_modalita_notifica    NUMERIC(2)  NOT NULL
, desc_modalita_notifica  VARCHAR(50) NOT NULL
);

CREATE TABLE cnm_d_norma
( id_norma              SERIAL                    NOT NULL
, riferimento_normativo VARCHAR(100)              NOT NULL
, id_ambito             INTEGER                   NOT NULL
, eliminato             BOOLEAN
, id_user_insert        NUMERIC(3)                NOT NULL
, data_ora_insert       TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update        NUMERIC(3)
, data_ora_update       TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_d_provincia
( id_provincia      NUMERIC(7)    NOT NULL
, cod_provincia     VARCHAR(3)    NOT NULL
, denom_provincia   VARCHAR(100)  NOT NULL
, sigla_provincia   VARCHAR(2)
, id_regione        NUMERIC(6)    NOT NULL
, inizio_validita   DATE          NOT NULL
, fine_validita     DATE
);

CREATE TABLE cnm_d_regione
(
	id_regione            NUMERIC(6)  NOT NULL ,
	cod_regione           VARCHAR(3)  NOT NULL ,
	denom_regione         VARCHAR(100)  NOT NULL ,
	id_nazione            NUMERIC(3)  NOT NULL ,
	inizio_validita       DATE  NOT NULL ,
	fine_validita         DATE
);

CREATE TABLE cnm_d_ruolo
(
	id_ruolo              NUMERIC(2)  NOT NULL ,
	desc_ruolo            VARCHAR(100)  NOT NULL ,
	inizio_validita       DATE   NOT NULL ,
	fine_validita         DATE
);

CREATE TABLE cnm_d_ruolo_soggetto
(
	id_ruolo_soggetto     NUMERIC(2)    NOT NULL ,
	desc_ruolo_soggetto   VARCHAR(50)   NOT NULL 
);

CREATE TABLE cnm_d_stato_allegato
( id_stato_allegato   NUMERIC(2)    NOT NULL
, desc_stato_allegato VARCHAR(100)  NOT NULL
);

CREATE TABLE cnm_d_stato_file
( id_stato_file   NUMERIC(2)    NOT NULL
, desc_stato_file VARCHAR(100)  NOT NULL 
);

CREATE TABLE cnm_d_stato_ord_verb_sog
( id_stato_ord_verb_sog     NUMERIC(2)    NOT NULL
, desc_stato_ord_verb_sog   VARCHAR(50)   NOT NULL
, id_elemento_elenco        NUMERIC(6)
);

CREATE TABLE cnm_d_stato_ordinanza
(
	id_stato_ordinanza    NUMERIC(2)  NOT NULL ,
	desc_stato_ordinanza  VARCHAR(50)  NOT NULL 
);

CREATE TABLE cnm_d_stato_piano_rate
( id_stato_piano_rate   NUMERIC(2)    NOT NULL
, desc_stato_piano_rate VARCHAR(100)  NOT NULL
);

CREATE TABLE cnm_d_stato_rata
( id_stato_rata     NUMERIC(2)  NOT NULL
, desc_stato_rata   VARCHAR(50) NOT NULL
);

CREATE TABLE cnm_d_stato_riscossione
( id_stato_riscossione    NUMERIC(2)  NOT NULL
, desc_stato_riscossione  VARCHAR(50) NOT NULL
);

CREATE TABLE cnm_d_stato_sollecito
( id_stato_sollecito    NUMERIC(2)    NOT NULL
, desc_stato_sollecito  VARCHAR(100)  NOT NULL
);

CREATE TABLE cnm_d_stato_verbale
(
	id_stato_verbale      NUMERIC(2)  NOT NULL ,
	desc_stato_verbale    VARCHAR(50)  NOT NULL 
);

CREATE TABLE cnm_d_tipo_allegato
( id_tipo_allegato        NUMERIC(2)    NOT NULL
, desc_tipo_allegato      VARCHAR(100)  NOT NULL
, id_categoria_allegato   NUMERIC(2)    NOT NULL
, id_utilizzo_allegato    NUMERIC(2)    NOT NULL 
);

CREATE TABLE cnm_d_tipo_file
( id_tipo_file    NUMERIC(2)     NOT NULL
, desc_tipo_file  VARCHAR(100)  NOT NULL
);

CREATE TABLE cnm_d_tipo_messaggio
(
	id_tipo_messaggio     NUMERIC(2)  NOT NULL ,
	desc_tipo_messaggio   VARCHAR(50)  NOT NULL 
);

CREATE TABLE cnm_d_tipo_ordinanza
(
	id_tipo_ordinanza     NUMERIC(2)  NOT NULL ,
	desc_tipo_ordinanza   VARCHAR(30)  NOT NULL 
);

CREATE TABLE cnm_d_tipo_record
( id_tipo_record          NUMERIC(3)    NOT NULL
, desc_tipo_record        VARCHAR(100)  NOT NULL
, nome_tabella_specifica  VARCHAR(30)
);

CREATE TABLE cnm_d_tipo_tributo
( id_tipo_tributo   NUMERIC(2)    NOT NULL
, cod_tipo_tributo  VARCHAR(4)    NOT NULL
, desc_tipo_tributo VARCHAR(100)  NOT NULL
);

CREATE TABLE cnm_d_use_case
(
	id_use_case           NUMERIC(3)  NOT NULL ,
	cod_use_case          VARCHAR(20)  NOT NULL ,
	desc_use_case         VARCHAR(100)  NOT NULL 
);

CREATE TABLE cnm_d_utilizzo_allegato
( id_utilizzo_allegato    NUMERIC(2)    NOT NULL
, desc_utilizzo_allegato  VARCHAR(100)  NOT NULL
);

CREATE TABLE cnm_r_allegato_ord_verb_sog
(
	id_allegato           INTEGER  NOT NULL ,
	id_ordinanza_verb_sog  INTEGER  NOT NULL ,
	id_user_insert        NUMERIC(3)  NOT NULL ,
	data_ora_insert       TIMESTAMP WITH TIME ZONE   NOT NULL 
);

CREATE TABLE cnm_r_allegato_ordinanza
(
	id_allegato           INTEGER  NOT NULL ,
	id_ordinanza          INTEGER  NOT NULL ,
	id_user_insert        NUMERIC(3)  NOT NULL ,
	data_ora_insert       TIMESTAMP WITH TIME ZONE   NOT NULL 
);

CREATE TABLE cnm_r_allegato_piano_rate
( id_allegato       INTEGER                   NOT NULL
, id_piano_rate     INTEGER                   NOT NULL
, id_user_insert    NUMERIC(3)                NOT NULL
, data_ora_insert   TIMESTAMP WITH TIME ZONE  NOT NULL
);

CREATE TABLE cnm_r_allegato_sollecito
( id_allegato     INTEGER                   NOT NULL
, id_sollecito    INTEGER                   NOT NULL
, id_user_insert  NUMERIC(3)                NOT NULL
, data_ora_insert TIMESTAMP WITH TIME ZONE  NOT NULL
);

CREATE TABLE cnm_r_allegato_verbale
(
	id_allegato           INTEGER  NOT NULL ,
	id_verbale            INTEGER  NOT NULL ,
	id_user_insert        NUMERIC(3)  NOT NULL ,
	data_ora_insert       TIMESTAMP WITH TIME ZONE   NOT NULL 
);

CREATE TABLE cnm_r_allegato_verb_sog
( id_allegato           INTEGER                   NOT NULL
, id_verbale_soggetto   INTEGER                   NOT NULL
, id_user_insert        NUMERIC(3)                NOT NULL
, data_ora_insert       TIMESTAMP WITH TIME ZONE  NOT NULL
);

CREATE TABLE cnm_r_ente_norma
( id_ente_norma         SERIAL                    NOT NULL
, id_ente               NUMERIC(2)                NOT NULL
, id_norma              INTEGER                   NOT NULL
, inizio_validita       DATE                      NOT NULL
, fine_validita         DATE
, eliminato             BOOLEAN
, id_user_insert        NUMERIC(3)                NOT NULL
, data_ora_insert       TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update        NUMERIC(3)
, data_ora_update       TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_r_funzionario_istruttore
(
	id_funzionario_istruttore  SERIAL  NOT NULL ,
	id_verbale            INTEGER  NOT NULL ,
	id_user               NUMERIC(3)  NOT NULL ,
	inizio_assegnazione   DATE   NOT NULL ,
	fine_assegnazione     DATE
);

CREATE TABLE cnm_r_ordinanza_verb_sog
( id_ordinanza_verb_sog   SERIAL                    NOT NULL
, id_ordinanza            INTEGER                   NOT NULL
, id_verbale_soggetto     INTEGER                   NOT NULL
, id_stato_ord_verb_sog   NUMERIC(2)
, cod_posizione_debitoria VARCHAR(50)
, cod_iuv                 VARCHAR(16)
, importo_pagato          NUMERIC(10,2)
, data_pagamento          DATE
, cod_avviso              VARCHAR(50)
, cod_esito_lista_carico  VARCHAR(5)
, id_user_insert          NUMERIC(3)                NOT NULL
, data_ora_insert         TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update          NUMERIC(3)
, data_ora_update         TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_d_utilizzo
(
	id_utilizzo     NUMERIC(2)    NOT NULL,
	desc_utilizzo   VARCHAR(50)   NOT NULL 
);

CREATE TABLE cnm_r_notifica_ordinanza
( id_ordinanza  INTEGER NOT NULL
, id_notifica   INTEGER NOT NULL
);

CREATE TABLE cnm_r_notifica_piano_rate
( id_piano_rate INTEGER NOT NULL
, id_notifica   INTEGER NOT NULL
);

CREATE TABLE cnm_r_notifica_sollecito
( id_sollecito  INTEGER  NOT NULL
, id_notifica   INTEGER  NOT NULL
);

CREATE TABLE cnm_r_sogg_rata
( id_ordinanza_verb_sog     INTEGER                   NOT NULL
, id_rata                   INTEGER                   NOT NULL
, cod_posizione_debitoria   VARCHAR(50)
, cod_iuv                   VARCHAR(16)
, importo_pagato            NUMERIC(10,2)
, data_pagamento            DATE
, cod_avviso                VARCHAR(50)
, cod_esito_lista_carico    VARCHAR(5)
, id_stato_rata             NUMERIC(2)                NOT NULL
, id_user_insert            NUMERIC(3)                NOT NULL
, data_ora_insert           TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update            NUMERIC(3)
, data_ora_update           TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_r_tipo_allegato_so
( id_stato_ordinanza  NUMERIC(2)  NOT NULL
, id_tipo_allegato    NUMERIC(2)  NOT NULL 
);

CREATE TABLE cnm_r_tipo_allegato_sv
(
	id_tipo_allegato      NUMERIC(2)  NOT NULL ,
	id_stato_verbale      NUMERIC(2)  NOT NULL 
);

CREATE TABLE cnm_r_use_case_ruolo
(
	id_use_case_ruolo     NUMERIC(4)  NOT NULL ,
	id_ruolo              NUMERIC(2)  NOT NULL ,
	id_use_case           NUMERIC(3)  NOT NULL ,
	inizio_validita       DATE   NOT NULL ,
	fine_validita         DATE
);

CREATE TABLE cnm_r_user_ente
(
	id_user_ente      NUMERIC(4)  NOT NULL,
	id_utilizzo       NUMERIC(2)  NOT NULL,
	id_user           NUMERIC(3)  NOT NULL,
	id_ente           NUMERIC(2)  NOT NULL,
	inizio_validita   DATE        NOT NULL,
	fine_validita     DATE
);

CREATE TABLE cnm_r_verbale_illecito
( id_verbale_illecito   SERIAL                    NOT NULL
, id_verbale            INTEGER                   NOT NULL
, id_lettera            INTEGER                   NOT NULL
, id_user_insert        NUMERIC(3)                NOT NULL
, data_ora_insert       TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update        NUMERIC(3)
, data_ora_update       TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_r_verbale_soggetto
( id_verbale_soggetto SERIAL                    NOT NULL
, id_verbale          INTEGER                   NOT NULL
, id_soggetto         INTEGER                   NOT NULL
, id_ruolo_soggetto   NUMERIC(2)                NOT NULL
, note                VARCHAR(1000)
, id_user_insert      NUMERIC(3)                NOT NULL
, data_ora_insert     TIMESTAMP WITH TIME ZONE  NOT NULL
);

CREATE TABLE cnm_s_comune
( id_s_comune         NUMERIC(8)    NOT NULL
, id_comune           NUMERIC(8)    NOT NULL
, cod_istat_comune    VARCHAR(6)
, cod_belfiore_comune VARCHAR(4)
, denom_comune        VARCHAR(100)  NOT NULL
, id_provincia        NUMERIC(7)    NOT NULL
, inizio_validita     DATE          NOT NULL
, fine_validita       DATE          NOT NULL
, dt_id_comune        NUMERIC(20)   NOT NULL
, dt_id_comune_prev   NUMERIC(20)
, dt_id_comune_next   NUMERIC(20)
);

CREATE TABLE cnm_s_nazione
( id_s_nazione          NUMERIC(3)    NOT NULL
, id_nazione            NUMERIC(3)    NOT NULL
, cod_istat_nazione     VARCHAR(3)    NOT NULL
, cod_belfiore_nazione  VARCHAR(4)
, denom_nazione         VARCHAR(100)  NOT NULL
, inizio_validita       DATE          NOT NULL
, fine_validita         DATE          NOT NULL
, dt_id_stato           NUMERIC(20)   NOT NULL
, dt_id_stato_prev      NUMERIC(20)
, dt_id_stato_next      NUMERIC(20)
);

CREATE TABLE cnm_s_provincia
(
	id_s_provincia        NUMERIC(7)  NOT NULL ,
	id_provincia          NUMERIC(7)  NOT NULL ,
	cod_provincia         VARCHAR(3)  NOT NULL ,
	denom_provincia       VARCHAR(100)  NOT NULL ,
	sigla_provincia       VARCHAR(2),
	id_regione            NUMERIC(6)  NOT NULL ,
	inizio_validita       DATE  NOT NULL ,
	fine_validita         DATE  NOT NULL 
);

CREATE TABLE cnm_s_regione
(
	id_s_regione          NUMERIC(6)  NOT NULL ,
	id_regione            NUMERIC(6)  NOT NULL ,
	cod_regione           VARCHAR(3)  NOT NULL ,
	denom_regione         VARCHAR(100)  NOT NULL ,
	id_nazione            NUMERIC(3)  NOT NULL ,
	inizio_validita       DATE  NOT NULL ,
	fine_validita         DATE  NOT NULL 
);

CREATE TABLE cnm_s_stato_ordinanza
(
	id_s_stato_ordinanza  SERIAL  NOT NULL ,
	id_ordinanza          SERIAL,
	id_stato_ordinanza    NUMERIC(2),
	id_user_insert        NUMERIC(10)  NOT NULL ,
	data_ora_insert       TIMESTAMP WITH TIME ZONE   NOT NULL 
);

CREATE TABLE cnm_s_stato_verbale
(
	id_s_stato_verbale    SERIAL  NOT NULL ,
	id_verbale            INTEGER  NOT NULL ,
	id_stato_verbale      NUMERIC(2)  NOT NULL ,
	id_user_insert        NUMERIC(3)  NOT NULL ,
	data_ora_insert       TIMESTAMP WITH TIME ZONE   NOT NULL 
);

CREATE TABLE cnm_t_allegato
( id_allegato           SERIAL                    NOT NULL
, nome_file             VARCHAR(100)              NOT NULL
, id_tipo_allegato      NUMERIC(2)                NOT NULL
, id_stato_allegato     NUMERIC(2)                NOT NULL
, id_index              VARCHAR(50)
, id_acta               VARCHAR(50)
, numero_protocollo     VARCHAR(50)
, data_ora_protocollo   TIMESTAMP WITH TIME ZONE
, id_user_insert        NUMERIC(3)                NOT NULL
, data_ora_insert       TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update        NUMERIC(3)
, data_ora_update       TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_allegato_field
( id_allegato_field   SERIAL                    NOT NULL
, id_allegato         INTEGER                   NOT NULL
, id_field            NUMERIC(5)                NOT NULL
, valore_number       NUMERIC(20,5)
, valore_string       VARCHAR(500)
, valore_data         DATE
, valore_data_ora     TIMESTAMP WITH TIME ZONE
, valore_boolean      BOOLEAN
, id_user_insert      NUMERIC(3)                NOT NULL
, data_ora_insert     TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update      NUMERIC(3)
, data_ora_update     TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_file
( id_file         SERIAL                    NOT NULL
, id_tipo_file    NUMERIC(2)                NOT NULL
, id_stato_file   NUMERIC(2)                NOT NULL
, nome_file       VARCHAR(100)              NOT NULL
, versione        VARCHAR(2)
, file_intero     TEXT
, id_user_insert  NUMERIC(3)                NOT NULL
, data_ora_insert TIMESTAMP WITH TIME ZONE  NOT NULL
);

CREATE TABLE cnm_t_file_ritorno
( id_file_ritorno SERIAL                    NOT NULL
, nome_file       VARCHAR(100)              NOT NULL
, id_record       INTEGER
, data_ora_insert TIMESTAMP WITH TIME ZONE  NOT NULL
);

CREATE TABLE cnm_t_notifica
( id_notifica             SERIAL                    NOT NULL
, id_modalita_notifica    NUMERIC(2)
, numero_raccomandata     NUMERIC(6)                NOT NULL
, data_spedizione         DATE
, data_notifica           DATE
, importo_spese_notifica  NUMERIC(10,2)
, id_user_insert          NUMERIC(3)                NOT NULL
, data_ora_insert         TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update          NUMERIC(3)
, data_ora_update         TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_ordinanza
( id_ordinanza              SERIAL                    NOT NULL
, num_determinazione        VARCHAR(50)               NOT NULL
, data_determinazione       DATE                      NOT NULL
, id_tipo_ordinanza         NUMERIC(2)                NOT NULL
, data_ordinanza            DATE                      NOT NULL
, importo_ordinanza         NUMERIC(10,2)
, id_stato_ordinanza        NUMERIC(2)                NOT NULL
, numero_protocollo         VARCHAR(50)
, data_ora_protocollo       TIMESTAMP WITH TIME ZONE
, cod_messaggio_epay        VARCHAR(50)
, data_scadenza_ordinanza   DATE
, id_user_insert            NUMERIC(3)                NOT NULL
, data_ora_insert           TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update            NUMERIC(3)
, data_ora_update           TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_persona
( id_persona                    SERIAL                    NOT NULL
, sesso                         VARCHAR(1)
, data_nascita                  DATE
, id_nazione_nascita            NUMERIC(3)
, id_comune_nascita             NUMERIC(8)
, denom_comune_nascita_estero   VARCHAR(100)
, qualifica                     VARCHAR(100)
, id_user_insert                NUMERIC(3)                NOT NULL
, data_ora_insert               TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update                NUMERIC(3)
, data_ora_update               TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_piano_rate
( id_piano_rate               SERIAL                    NOT NULL
, numero_rate                 NUMERIC(3)                NOT NULL
, importo_sanzione            NUMERIC(10,2)             NOT NULL
, importo_spese_processuali   NUMERIC(10,2)
, importo_spese_notifica      NUMERIC(10,2)
, id_stato_piano_rate         NUMERIC(2)                NOT NULL
, id_index                    VARCHAR(50)
, numero_protocollo           VARCHAR(50)
, data_ora_protocollo         TIMESTAMP WITH TIME ZONE
, cod_messaggio_epay          VARCHAR(50)
, id_user_insert              NUMERIC(3)                NOT NULL
, data_ora_insert             TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update              NUMERIC(3)
, data_ora_update             TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_rata
( id_rata               SERIAL                    NOT NULL
, id_piano_rate         INTEGER                   NOT NULL
, numero_rata           NUMERIC(3)                NOT NULL
, importo_rata          NUMERIC(10,2)             NOT NULL
, data_scadenza         DATE
, id_user_insert        NUMERIC(3)                NOT NULL
, data_ora_insert       TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update        NUMERIC(3)
, data_ora_update       TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_record
( id_record       SERIAL                    NOT NULL
, id_file         INTEGER                   NOT NULL
, id_tipo_record  NUMERIC(3)                NOT NULL
, id_riscossione  INTEGER
, codice_partita  VARCHAR(14)
, ordine          INTEGER                   NOT NULL
, id_user_insert  NUMERIC(3)                NOT NULL
, data_ora_insert TIMESTAMP WITH TIME ZONE  NOT NULL
);

CREATE TABLE cnm_t_record_N2
( id_record_n2            SERIAL      NOT NULL
, id_record               INTEGER     NOT NULL
, identificativo_soggetto VARCHAR(16)
, indirizzo               VARCHAR(30)
, num_civico              NUMERIC(5)
, lettera_num_civico      VARCHAR(2)
, cap                     VARCHAR(5)
, cod_belfiore_comune     VARCHAR(4)
, localita_frazione       VARCHAR(21)
, societa                 NUMERIC(1)
, cognome                 VARCHAR(24)
, nome                    VARCHAR(20)
, sesso                   VARCHAR(1)
, data_nascita            VARCHAR(8)
, denom_societa           VARCHAR(76)
, presenza_obbligato      VARCHAR(1)
);

CREATE TABLE cnm_t_record_N3
( id_record_n3            SERIAL      NOT NULL
, id_record               INTEGER     NOT NULL
, identificativo_soggetto VARCHAR(16)
, indirizzo               VARCHAR(30)
, num_civico              NUMERIC(5)
, lettera_num_civico      VARCHAR(2)
, cap                     VARCHAR(5)
, cod_belfiore_comune     VARCHAR(4)
, localita_frazione       VARCHAR(21)
, societa                 NUMERIC(1)
, cognome                 VARCHAR(24)
, nome                    VARCHAR(20)
, sesso                   VARCHAR(1)
, data_nascita            VARCHAR(8)
, denom_societa           VARCHAR(76)
);

CREATE TABLE cnm_t_record_N4
( id_record_n4              SERIAL      NOT NULL
, id_record                 INTEGER     NOT NULL
, id_tipo_tributo           NUMERIC(2)  NOT NULL
, anno_tributo              NUMERIC(4)
, imposta                   NUMERIC(10)
, data_decorrenza_interessi VARCHAR(8)
, titolo                    VARCHAR(12)
);

CREATE TABLE cnm_t_residenza
( id_residenza                    SERIAL                    NOT NULL
, id_soggetto                     INTEGER                   NOT NULL
, residenza_estera                BOOLEAN                   NOT NULL
, id_nazione_residenza            NUMERIC(3)
, id_comune_residenza             NUMERIC(8)
, denom_comune_residenza_estero   VARCHAR(100)
, indirizzo_residenza             VARCHAR(100)
, numero_civico_residenza         VARCHAR(20)
, cap_residenza                   VARCHAR(5)
, id_user_insert                  NUMERIC(3)                NOT NULL
, data_ora_insert                 TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update                  NUMERIC(3)
, data_ora_update                 TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_riscossione
( id_riscossione          SERIAL                    NOT NULL
, id_ordinanza_verb_sog   INTEGER                   NOT NULL
, importo_sanzione        NUMERIC(10,2)
, importo_riscosso        NUMERIC(10,2)
, importo_spese_notifica  NUMERIC(10,2)
, importo_spese_legali    NUMERIC(10,2)
, id_stato_riscossione    NUMERIC(2)                NOT NULL
, id_user_insert          NUMERIC(3)                NOT NULL
, data_ora_insert         TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update          NUMERIC(3)
, data_ora_update         TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_societa
( id_societa                SERIAL                    NOT NULL
, id_nazione_sede           NUMERIC(3)
, id_comune_sede            NUMERIC(8)
, denom_comune_sede_estero  VARCHAR(100)
, indirizzo_sede            VARCHAR(100)
, numero_civico_sede        VARCHAR(20)
, cap_sede                  VARCHAR(5)
, id_user_insert            NUMERIC(3)                NOT NULL
, data_ora_insert           TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update            NUMERIC(3)
, data_ora_update           TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_soggetto
( id_soggetto               SERIAL                    NOT NULL
, id_persona                INTEGER
, id_societa                INTEGER
, id_stas                   NUMERIC(10)
, codice_fiscale            VARCHAR(16)
, cognome                   VARCHAR(50)
, nome                      VARCHAR(50)
, codice_fiscale_giuridico  VARCHAR(16)
, partita_iva               VARCHAR(11)
, ragione_sociale           VARCHAR(1000)
, id_user_insert            NUMERIC(3)                NOT NULL
, data_ora_insert           TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update            NUMERIC(3)
, data_ora_update           TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_sollecito
( id_sollecito              SERIAL                    NOT NULL
, id_ordinanza_verb_sog     INTEGER                   NOT NULL
, numero_protocollo         VARCHAR(50)
, data_scadenza_rata        DATE
, importo_sollecito         NUMERIC(10,2)             NOT NULL
, maggiorazione             NUMERIC(10,2)
, id_stato_sollecito        NUMERIC(2)                NOT NULL
, data_pagamento            DATE
, importo_pagato            NUMERIC(10,2)
, cod_avviso                VARCHAR(50)
, cod_esito_lista_carico    VARCHAR(5)
, cod_iuv                   VARCHAR(16)
, cod_posizione_debitoria   VARCHAR(50)
, cod_messaggio_epay        VARCHAR(50)
, id_user_insert            NUMERIC(3)                NOT NULL
, data_ora_insert           TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update            NUMERIC(3)
, data_ora_update           TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_t_user
( id_user           NUMERIC(3)    NOT NULL
, codice_fiscale    VARCHAR(16)   NOT NULL
, id_ruolo          NUMERIC(2)    NOT NULL
, id_gruppo         NUMERIC(3)
, inizio_validita   DATE          NOT NULL
, fine_validita     DATE
);

CREATE TABLE cnm_t_verbale
( id_verbale            SERIAL                    NOT NULL
, num_verbale           VARCHAR(50)               NOT NULL
, id_stato_verbale      NUMERIC(2)                NOT NULL
, data_ora_violazione   TIMESTAMP WITH TIME ZONE  NOT NULL
, id_comune_violazione  NUMERIC(8)                NOT NULL
, localita_violazione   VARCHAR(200)
, data_ora_accertamento TIMESTAMP WITH TIME ZONE  NOT NULL
, id_ente_accertatore   NUMERIC(2)                NOT NULL
, importo_verbale       NUMERIC(10,2)             NOT NULL
, numero_protocollo     VARCHAR(50)
, data_ora_protocollo   TIMESTAMP WITH TIME ZONE
, contestato            BOOLEAN
, id_user_insert        NUMERIC(3)                NOT NULL
, data_ora_insert       TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update        NUMERIC(3)
, data_ora_update       TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_d_gruppo
(
	id_gruppo     NUMERIC(3)    NOT NULL,
	desc_gruppo   VARCHAR(100)  NOT NULL
);

CREATE TABLE cnm_t_calendario
( id_calendario                 SERIAL                    NOT NULL
, cognome_funzionario_sanzion   VARCHAR(50)               NOT NULL
, nome_funzionario_sanzion      VARCHAR(50)               NOT NULL
, id_gruppo                     NUMERIC(3)                NOT NULL
, inizio_udienza                TIMESTAMP WITH TIME ZONE  NOT NULL
, fine_udienza                  TIMESTAMP WITH TIME ZONE  NOT NULL
, tribunale                     VARCHAR(100)              NOT NULL
, cognome_giudice               VARCHAR(50)               NOT NULL
, nome_giudice                  VARCHAR(50)               NOT NULL
, id_comune_udienza             NUMERIC(8)                NOT NULL
, indirizzo_udienza             VARCHAR(100)              NOT NULL
, numero_civico_udienza         VARCHAR(20)
, cap_udienza                   VARCHAR(5)
, note                          VARCHAR(1000)
, colore                        VARCHAR(20)
, id_user_insert                NUMERIC(3)                NOT NULL
, data_ora_insert               TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update                NUMERIC(3)
, data_ora_update               TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_tmp_file
( id_tmp_file   SERIAL        NOT NULL
, record        VARCHAR(1000) NOT NULL
, ordine        NUMERIC(3)
);
