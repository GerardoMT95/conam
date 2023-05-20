SET client_encoding TO 'UTF8';

-- commento test con accentate: à è é ì ò ù @

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	1,
	'MAIL_FROM',
	'conamtest_noreply@csi.it',
	'Indirizzo e-mail da cui verrano spedite le comunicazioni'
);


-- Parametri Mail Udienza
INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	10,
	'MAIL_UDIENZA_ADVANCE_DAY',
	'20',
	'Giorni anticipo invio mail Udienza'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	11,
	'MAIL_UDIENZA_OBJECT_AUTORITA_GIUDIZIARIA',
	'Convocazione udienza presso:',
	'Label descrizione dell�autorit� giudiziaria (campo autorit� giudiziaria)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	12,
	'MAIL_UDIENZA_OBJECT_GIORNO',
	'giorno:',
	'Label data e l�ora della udienza (campo data e ora udienza)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	13,
	'MAIL_UDIENZA_BODY_AUTORITA_GIUDIZIARIA',
	'Convocazione udienza presso:',
	'Label descrizione dell�autorit� giudiziaria (campo autorit� giudiziaria)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	14,
	'MAIL_UDIENZA_BODY_GIORNO',
	'Giorno:',
	'Label data e l�ora dell�udienza (campi data e ora udienza)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	15,
	'MAIL_UDIENZA_BODY_GIUDICE',
	'Giudice competente:',
	'Label riferimenti al giudice (campi nome e cognome del giudice)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	16,
	'MAIL_UDIENZA_BODY_LUOGO',
	'Luogo udienza:',
	'Label riferimenti al luogo dell�udienza (campi comune, provincia, via, civico)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	17,
	'MAIL_UDIENZA_BODY_NOTE',
	'Note:',
	'Label eventuali note (campo note)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	18,
	'MAIL_UDIENZA_CRON',
	'0 0 4 ? * *',
	'Cron Expression per inviare le email Udienza'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	19,
	'MAIL_UDIENZA_ENABLE',
	'true',
	'Abilitazione batch udienza'
);




-- Parametri Mail Documentazione
INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	20,
	'MAIL_DOCUMENTAZIONE_ADVANCE_DAY',
	'20',
	'Giorni anticipo invio mail Documentazione'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	21,
	'MAIL_DOCUMENTAZIONE_OBJECT_GIORNO',
	'Deposito documentazione per udienza del:',
	'Label data e l�ora della udienza (campo data e ora udienza)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	22,
	'MAIL_DOCUMENTAZIONE_BODY_GIORNO',
	'Deposito documentazione per udienza del:',
	'Label data e l�ora dell�udienza (campi data e ora udienza)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	23,
	'MAIL_DOCUMENTAZIONE_BODY_AUTORITA_GIUDIZIARIA',
	'presso:',
	'Label descrizione dell�autorit� giudiziaria (campo autorit� giudiziaria)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	24,
	'MAIL_DOCUMENTAZIONE_BODY_GIUDICE',
	'Giudice competente:',
	'Label riferimenti al giudice (campi nome e cognome del giudice)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	25,
	'MAIL_DOCUMENTAZIONE_BODY_LUOGO',
	'Luogo udienza:',
	'Label riferimenti al luogo dell�udienza (campi comune, provincia, via, civico)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	26,
	'MAIL_DOCUMENTAZIONE_BODY_NOTE',
	'Note:',
	'Label eventuali note (campo note)'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	27,
	'MAIL_DOCUMENTAZIONE_CRON',
	'0 0 4 ? * *',
	'Cron Expression per inviare le email Documentazione'
);

INSERT INTO cnm_c_batch_email (
	id_parametro_email,
	nome_parametro_email,
	valore_parametro_email,
	descrizione_parametro_email
) VALUES (
	28,
	'MAIL_DOCUMENTAZIONE_ENABLE',
	'true',
	'Abilitazione batch Documentazione'
);