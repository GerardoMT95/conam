-- ENCODING
SET client_encoding TO 'WIN1258';



-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDStrutturaActa
INSERT INTO cnm_d_struttura_acta VALUES (1, 'F', 'Fascicolo');
INSERT INTO cnm_d_struttura_acta VALUES (2, 'V', 'Volume');
INSERT INTO cnm_d_struttura_acta VALUES (3, 'D', 'Dossier');



-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmCProprieta ambiente di TEST
INSERT INTO cnm_c_proprieta VALUES (1, 'ACTA_ID_AOO', '276');										
INSERT INTO cnm_c_proprieta VALUES (2, 'ACTA_CF', 'ZNLFRZ59L25H620O');								
INSERT INTO cnm_c_proprieta VALUES (3, 'ACTA_ID_STRUTTURA', '1108');									
INSERT INTO cnm_c_proprieta VALUES (4, 'ACTA_ID_NODO', '1194');										
INSERT INTO cnm_c_proprieta VALUES (5, 'INDEX_REPOSITORY', 'primary');								
INSERT INTO cnm_c_proprieta VALUES (6, 'INDEX_USERNAME', 'admin@conam');							
INSERT INTO cnm_c_proprieta VALUES (7, 'INDEX_PASSWORD', 'conam');									
INSERT INTO cnm_c_proprieta VALUES (8, 'ACTA_CODE_FRUITORE', 'CONAM');								
INSERT INTO cnm_c_proprieta VALUES (9, 'IS_DOQUI_DIRECT', 'TRUE');									
INSERT INTO cnm_c_proprieta VALUES (10, 'ACTA_SERVER', 'tst-applogic.reteunitaria.piemonte.it');	
INSERT INTO cnm_c_proprieta VALUES (11, 'ACTA_CONTEXT', '/actasrv/');								
INSERT INTO cnm_c_proprieta VALUES (12, 'ACTA_PORT', '80');											
INSERT INTO cnm_c_proprieta VALUES (13, 'ACTA_IS_WS', 'TRUE');	

-- properties index e schedulatore									
INSERT INTO cnm_c_proprieta VALUES (14, 'INDEX_ENDPOINT', 'http://tst-applogic.reteunitaria.piemonte.it/ecmenginews-exp02/services/EcmEngineManagement?wsdl');
INSERT INTO cnm_c_proprieta VALUES (15, 'SCHEDULED_TOACTA_FIXED_RATE', '60000');
INSERT INTO cnm_c_proprieta VALUES (16, 'SCHEDULED_TOACTA_POOL_SIZE', '1');
INSERT INTO cnm_c_proprieta VALUES (17, 'INDEX_FRUITORE', 'conam');								
INSERT INTO cnm_c_proprieta VALUES (18, 'INDEX_CUSTOM_MODEL', 'conam:model');	

-- properties per apimanager
INSERT INTO cnm_c_proprieta VALUES (19, 'APIMANAGER_OAUTHURL', 'https://tst-api-piemonte.ecosis.csi.it/token');
INSERT INTO cnm_c_proprieta VALUES (20, 'APIMANAGER_CONSUMERKEY', 'hCE3PaxXi1n8jrKM__yf7IBnrB4a');
INSERT INTO cnm_c_proprieta VALUES (21, 'APIMANAGER_CONSUMERSECRET', 'MzFG7GUx7sr1EFcyahAcP8n6c6Ya');
INSERT INTO cnm_c_proprieta VALUES (22, 'APIMANAGER_USER', 'User');					
INSERT INTO cnm_c_proprieta VALUES (23, 'APIMANAGER_PASSWORD', 'mypass');	

INSERT INTO cnm_c_proprieta VALUES (24, 'APIMANAGER_URL', 'http://tst-api-piemonte.ecosis.csi.it/documentale/acaris-test-');
INSERT INTO cnm_c_proprieta VALUES (25, 'APIMANAGER_ENABLED', 'TRUE');

INSERT INTO cnm_c_proprieta VALUES (26, 'ACTA_SIGN_STEP_BYPASS_FLAG', '0001000');

-- Test
INSERT INTO cnm_c_proprieta VALUES (27, 'APIMANAGER_URL_END', '/v1');

-- Produzione
-- INSERT INTO cnm_c_proprieta VALUES (27, 'APIMANAGER_URL_END', '-rp03/v1');



-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDTipoDocumento
INSERT INTO cnm_d_tipo_documento VALUES (22, 'CONAM_DOC', 'Documento di Contenzioso Amministrativo', 'conam', 12, 8, 'MODULO', 'LETTERA', NULL, NULL, true, 'Ufficio Contenzioso Amministrativo');
INSERT INTO cnm_d_tipo_documento VALUES (27, 'CONAM_ACTA', 'Documento Conam su ACTA', 'CONAM-2019', 12, 4, NULL, NULL, 3, 1, true, 'Ufficio Contenzioso Amministrativo');
INSERT INTO cnm_d_tipo_documento VALUES (29, 'CONAM_A1', 'Documento Conam su ACTA no cartaceo', NULL, 12, 4, NULL, NULL, 3, 1, false, 'Ufficio Contenzioso Amministrativo');
INSERT INTO cnm_d_tipo_documento VALUES (30, 'CONAM_A2', 'Documento di Conam su ACTA firma omessa', NULL, 12, 8, NULL, NULL, 3, 1, false, 'Ufficio Contenzioso Amministrativo');

-- necessari per CDU_18
INSERT INTO cnm_d_tipo_documento VALUES (32, 'CONAM_DOC_SIGNED', 'Documento di Contenzioso Amministrativo Firmato', 'conam', 12, 2, 'MODULO', 'LETTERA', NULL, NULL, false, NULL);
INSERT INTO cnm_d_tipo_documento VALUES (37, 'CONAM_ACTA_SIGNED', 'Documento Conam su ACTA Firmato', 'CONAM-2019', 12, 2, NULL, NULL, 3, 1, false, NULL);
INSERT INTO cnm_d_tipo_documento VALUES (39, 'CONAM_A1_SIGNED', 'Documento Conam su ACTA no cartaceo Firmato', NULL, 12, 2, NULL, NULL, 3, 1, false, NULL);
INSERT INTO cnm_d_tipo_documento VALUES (40, 'CONAM_A2_SIGNED', 'Documento di Conam su ACTA firma omessa Firmato', NULL, 12, 2, NULL, NULL, 3, 1, false, NULL);




-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDTipoFornitore
INSERT INTO cnm_d_tipo_fornitore VALUES (2, 'INDEX', 'Index', NULL, NULL, NULL);
INSERT INTO cnm_d_tipo_fornitore VALUES (1, 'ACTA', 'Acta', 'RP201209 Regione Piemonte - Agg. 09/2012', '-26/77/91/68/-124/16/21/62/-123/-105/-119/-18/26/72/53/108', 'RP201209'); 






-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmRTipoDocPadreFiglio 
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (27, 22);
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (29, 22);
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (30, 22);



-- necessari per CDU_18
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (27, 32);
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (29, 32);
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (30, 32);
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (37, 22);
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (39, 22);
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (40, 22);
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (37, 32);
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (39, 32);
INSERT INTO cnm_r_tipo_doc_padre_figlio VALUES (40, 32);








-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDStatoRichiesta
INSERT INTO cnm_d_stato_richiesta VALUES (1, 'INVIATA', 'Inviata al fornitore');
INSERT INTO cnm_d_stato_richiesta VALUES (2, 'EVASA', 'Evasa dal fornitore');
INSERT INTO cnm_d_stato_richiesta VALUES (3, 'ERRATA', 'Errata');
INSERT INTO cnm_d_stato_richiesta VALUES (4, 'IN_ELAB', 'In Elaborazione');





-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDTipoOperazione
INSERT INTO cnm_d_tipo_operazione VALUES (1, 'INS', 'Inserimento');
INSERT INTO cnm_d_tipo_operazione VALUES (2, 'MOD', 'Modifica');
INSERT INTO cnm_d_tipo_operazione VALUES (3, 'CAN', 'Cancellazione');
INSERT INTO cnm_d_tipo_operazione VALUES (4, 'CON', 'Consultazione');





-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDServizio
INSERT INTO cnm_d_servizio VALUES (10, 'ARCHRPINS', 'Archivio Struttura Regionale - Inserimento', 1, 1);
INSERT INTO cnm_d_servizio VALUES (11, 'ARCHRPCON', 'Archivio Struttura Regionale - Consultazione', 1, 4);
INSERT INTO cnm_d_servizio VALUES (20, 'PROTRPINS', 'Protocollo Struttura Regionale - Inserimento', 1, 1);
INSERT INTO cnm_d_servizio VALUES (21, 'PROTRPCON', 'Protocollo Struttura Regionale - Consultazione', 1, 4);
INSERT INTO cnm_d_servizio VALUES (30, 'ARCHGEINS', 'Archivio Generico - Inserimento', 2, 1);
INSERT INTO cnm_d_servizio VALUES (31, 'ARCHGECON', 'Archivio Generico - Consultazione', 2, 4);
INSERT INTO cnm_d_servizio VALUES (32, 'ARCHGECAN', 'Archivio Generico - Cancellazione', 2, 3);
INSERT INTO cnm_d_servizio VALUES (22, 'PROTRPASS', 'Protocollo S. R. - Associa documento a protocollo', 1, 2);
INSERT INTO cnm_d_servizio VALUES (33, 'ARCHGERIC', 'Archivio Generico - Modifica stato richiesta', 2, 2);
INSERT INTO cnm_d_servizio VALUES (23, 'PROTRPINSF', 'Protocollo Struttura Regionale - Ins Doc Fisico', 1, 1);
INSERT INTO cnm_d_servizio VALUES (34, 'CONSALLIND', 'Consultazione allegato Index', 2, 4);
INSERT INTO cnm_d_servizio VALUES (35, 'ARCHINSALL', 'Archivio Struttura Regionale - Aggiungi allegato', 1, 1);

-- nuovo servizio spostamento
INSERT INTO cnm_d_servizio VALUES (24, 'PROTRPSPO', 'Protocollo Struttura Regionale - Spostamento', 1, 1);

-- nuovo servizio "copia"
INSERT INTO cnm_d_servizio VALUES (25, 'PROTRPCOP', 'Protocollo Struttura Regionale - Copia Incolla', 1, 1);


