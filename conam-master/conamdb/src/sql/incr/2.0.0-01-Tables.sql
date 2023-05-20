-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		NEW TABLES										%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%





-----------------------------
-- TABELLE CONFIGURAZIONE ---
-----------------------------


-- CnmCProprieta
CREATE TABLE cnm_c_proprieta (
    id_proprieta NUMERIC(4,0) NOT NULL,
    nome VARCHAR(250) NOT NULL,
    valore VARCHAR(250) NOT NULL
);



-----------------------------
-- TABELLE DATI -------------
-----------------------------


-- CnmDStrutturaActa
CREATE TABLE cnm_d_struttura_acta
(
  id_struttura_acta INTEGER NOT NULL,
  cod_struttura_acta VARCHAR(2) NOT NULL,
  descr_struttura_acta VARCHAR(100) NOT NULL
);



-- CnmDTipoDocumento
CREATE TABLE cnm_d_tipo_documento
(
  id_tipo_documento NUMERIC(4,0) NOT NULL, 
  cod_tipo_documento VARCHAR(20),
  descr_tipo_documento VARCHAR(50),
  root VARCHAR(50),
  id_vital_record_code_type INTEGER,
  id_stato_efficacia INTEGER,
  desc_formadoc_entrata VARCHAR(30),
  desc_formadoc_uscita VARCHAR(30),
  id_struttura_acta_root INTEGER,
  id_struttura_acta_folder INTEGER,
  cartaceo boolean NOT NULL,
  collocazione_cartacea VARCHAR(100)
);



-- CnmDTipoFornitore
CREATE TABLE cnm_d_tipo_fornitore
(
  id_tipo_fornitore NUMERIC(4,0) NOT NULL,
  cod_tipo_fornitore VARCHAR(20),
  descr_tipo_fornitore VARCHAR(20),
  repository VARCHAR(100),
  application_key VARCHAR(100),
  cod_ente VARCHAR(20)
);




-- CnmDStatoRichiesta
CREATE TABLE cnm_d_stato_richiesta
(
  id_stato_richiesta NUMERIC(4,0) NOT NULL, -- Inserita...
  cod_stato_richiesta VARCHAR(10),
  descr_stato_richiesta VARCHAR(100)
);




-- CnmDTipoOperazione
CREATE TABLE cnm_d_tipo_operazione
(
  id_tipo_operazione NUMERIC(4,0) NOT NULL, -- Inserimento...
  cod_tipo_operazione VARCHAR(10),
  descr_tipo_operazione VARCHAR(20)
);



-- CnmDServizio
CREATE TABLE cnm_d_servizio
(
  id_servizio NUMERIC(4,0) NOT NULL,
  cod_servizio VARCHAR(10),
  descr_servizio VARCHAR(50),
  id_tipo_fornitore NUMERIC(4,0) NOT NULL,
  id_tipo_operazione NUMERIC(4,0) NOT NULL
);





-----------------------------
-- TABELLE ONLINE -----------
-----------------------------


--	CnmTDocumento
CREATE TABLE cnm_t_documento (
	id_documento SERIAL NOT NULL,
	id_tipo_documento NUMERIC(4,0) NOT NULL,
	identificativo_archiviazione VARCHAR(50),
	protocollo_fornitore VARCHAR(20),
	identificativo_fornitore VARCHAR(250),
	identificativo_entita_fruitore VARCHAR(250),
	folder VARCHAR(500),
	classificazione_id VARCHAR(300),
	registrazione_id VARCHAR(300),
	objectidclassificazione VARCHAR(300),
	objectiddocumento VARCHAR(300),
	id_user_insert NUMERIC(3,0) NOT NULL,
	data_ora_insert TIMESTAMP,
	id_user_update NUMERIC(3,0),
	data_ora_update TIMESTAMP
);




--	CnmTRichiesta
CREATE TABLE cnm_t_richiesta (
	id_richiesta SERIAL NOT NULL,
	id_fruitore NUMERIC(4,0) NOT NULL,
	id_stato_richiesta NUMERIC(4,0) NOT NULL,
	id_servizio NUMERIC(4,0) NOT NULL,
	id_user_insert NUMERIC(3,0) NOT NULL,
	data_ora_insert TIMESTAMP,
	id_user_update NUMERIC(3,0),
	data_ora_update TIMESTAMP
);







-----------------------------
-- TABELLE RELAZIONI --------
-----------------------------

--	CnmRTipoDocPadreFiglio
CREATE TABLE cnm_r_tipo_doc_padre_figlio (
	id_tipo_doc_padre NUMERIC(4,0) NOT NULL,
	id_tipo_doc_figlio NUMERIC(4,0) NOT NULL
);





--	CnmRRichiestaDocumento
CREATE TABLE cnm_r_richiesta_documento (
	id_richiesta INTEGER NOT NULL,
	id_documento INTEGER NOT NULL
);





-----------------------------
-- TABELLA LOG AUDIT --------
-----------------------------

-- CsiLogAudit
CREATE TABLE csi_log_audit
(
  id_log SERIAL NOT NULL,
  data_ora TIMESTAMP NOT NULL,
  id_app VARCHAR(100) NOT NULL,
  ip_address VARCHAR(40),
  utente VARCHAR(100) NOT NULL,
  operazione VARCHAR(100) NOT NULL,
  ogg_oper VARCHAR(500) NOT NULL,
  key_oper VARCHAR(500) NOT NULL
);







