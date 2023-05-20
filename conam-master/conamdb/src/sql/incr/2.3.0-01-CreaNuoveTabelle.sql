-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		NEW TABLES										%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

CREATE TABLE "cnm_t_mail_inviata" (
	"id_mail" SERIAL NOT NULL,
	"id_calendario" INTEGER NOT NULL,
	"data_invio" TIMESTAMP NOT NULL,
	"destinatario" VARCHAR(250) NOT NULL,
	"soggetto" VARCHAR(3000) NOT NULL,
	"testo" VARCHAR(3000) NOT NULL,
	"esito" VARCHAR(50) NOT NULL,
	"info" VARCHAR(500) NOT NULL
);

CREATE TABLE cnm_c_batch_email (
	id_parametro_email SERIAL NOT NULL,
	nome_parametro_email VARCHAR(50) NOT NULL,
	valore_parametro_email VARCHAR(3000) NOT NULL,
	descrizione_parametro_email VARCHAR(250) NOT NULL
);

CREATE TABLE cnm_d_stato_manuale (
	id_stato_manuale NUMERIC(2,0) NOT NULL,
	desc_stato_manuale VARCHAR(50) NOT NULL
);




-- scenario 7 ordinanza annullamento
CREATE TABLE cnm_r_ordinanza_figlio
(	id_ordinanza           	INTEGER  NOT NULL
,	id_ordinanza_figlio     INTEGER  NOT NULL
);




-- scenario 5 sollecito piano rate
CREATE TABLE cnm_r_sollecito_sogg_rata
( id_sollecito		        INTEGER                   NOT NULL
, id_ordinanza_verb_sog     INTEGER                   NOT NULL
, id_rata                   INTEGER                   NOT NULL
, id_user_insert            NUMERIC(3)                NOT NULL
, data_ora_insert           TIMESTAMP WITH TIME ZONE  NOT NULL
, id_user_update            NUMERIC(3)
, data_ora_update           TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cnm_d_tipo_sollecito
( id_tipo_sollecito    NUMERIC(2)    NOT NULL
, desc_tipo_sollecito  VARCHAR(100)  NOT NULL
);

