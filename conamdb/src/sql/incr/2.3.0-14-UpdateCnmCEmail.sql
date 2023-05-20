SET client_encoding TO 'UTF8';

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Indirizzo e-mail da cui verrano spedite le comunicazioni'
where
	id_parametro_email = 1;

-- Parametri Mail Udienza
UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Giorni anticipo invio mail Udienza'
where
	id_parametro_email = 10;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label descrizione dell''autorità giudiziaria (campo autorità giudiziaria)'
where
	id_parametro_email = 11;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label data e l''ora della udienza (campo data e ora udienza)'
where
	id_parametro_email = 12;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label descrizione dell''autorità giudiziaria (campo autorità giudiziaria)'
where
	id_parametro_email = 13;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label data e l''ora dell''udienza (campi data e ora udienza)'
where
	id_parametro_email = 14;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label riferimenti al giudice (campi nome e cognome del giudice)'
where
	id_parametro_email = 15;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label riferimenti al luogo dell''udienza (campi comune, provincia, via, civico)'
where
	id_parametro_email = 16;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label eventuali note (campo note)'
where
	id_parametro_email = 17;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Cron Expression per inviare le email Udienza'
where
	id_parametro_email = 18;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Abilitazione batch udienza'
where
	id_parametro_email = 19;

-- Parametri Mail Documentazione

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Giorni anticipo invio mail Documentazione'
where
	id_parametro_email = 20;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label data e l''ora della udienza (campo data e ora udienza)'
where
	id_parametro_email = 21;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label data e l''ora dell''udienza (campi data e ora udienza)'
where
	id_parametro_email = 22;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label descrizione dell''autorità giudiziaria (campo autorità giudiziaria)'
where
	id_parametro_email = 23;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label riferimenti al giudice (campi nome e cognome del giudice)'
where
	id_parametro_email = 24;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label riferimenti al luogo dell''udienza (campi comune, provincia, via, civico)'
where
	id_parametro_email = 25;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Label eventuali note (campo note)'
where
	id_parametro_email = 26;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Cron Expression per inviare le email Documentazione'
where
	id_parametro_email = 27;

UPDATE cnm_c_batch_email SET
	descrizione_parametro_email = 'Abilitazione batch Documentazione'
where
	id_parametro_email = 28;
	
-- VARIAZIONE VALORE E-MAIL
UPDATE cnm_c_batch_email SET
	valore_parametro_email = 'conamtest_noreply@csi.it'
where
	id_parametro_email = 1;