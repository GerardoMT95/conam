SET client_encoding TO 'WIN1258';

-- CNM_D_AMBITO
INSERT INTO cnm_d_ambito (id_ambito, desc_ambito, acronimo, eliminato, id_user_insert, data_ora_insert, id_user_update, data_ora_update)
  VALUES( 7, 'Aree naturali', 'PAR', NULL, 2, '2019-04-15 10:28:23.718', NULL, NULL);


-- CNM_D_NORMA
INSERT INTO cnm_d_norma (id_norma, riferimento_normativo, id_ambito, eliminato, id_user_insert, data_ora_insert, id_user_update, data_ora_update)
  VALUES( 7, 'LR 19/2009', 7, true, 2, '2019-04-15 10:30:18.452', 2, '2019-04-15 10:31:27.492');


-- CNM_R_ENTE_NORMA
INSERT INTO cnm_r_ente_norma ( id_ente_norma, id_ente, id_norma, inizio_validita, fine_validita, eliminato, id_user_insert, data_ora_insert, id_user_update, data_ora_update )
  VALUES ( 7, 1, 7, '2019-04-15', NULL, TRUE, 2, '2019-04-15 10:30:18.452', 2, '2019-04-15 10:31:27.492' );


-- CNM_D_ARTICOLO
INSERT INTO cnm_d_articolo (id_articolo, desc_articolo, id_ente_norma, inizio_validita, fine_validita, eliminato, id_user_insert, data_ora_insert, id_user_update, data_ora_update)
  VALUES( 7, '55', 7, '2019-04-15', NULL, true, 2, '2019-04-15 10:30:18.452', 2, '2019-04-15 10:31:27.492');


-- CNM_D_COMMA
INSERT INTO cnm_d_comma (id_comma, desc_comma, id_articolo, inizio_validita, fine_validita, eliminato, id_user_insert, data_ora_insert, id_user_update, data_ora_update)
  VALUES( 7, 'NO COMMA', 7, '2019-04-15', NULL, true, 2, '2019-04-15 10:30:18.452', 2, '2019-04-15 10:31:27.492');


-- CNM_D_LETTERA
INSERT INTO cnm_d_lettera (id_lettera, lettera, id_comma, sanzione_minima, sanzione_massima, importo_ridotto, scadenza_importi, desc_illecito, inizio_validita, fine_validita, eliminato, id_user_insert, data_ora_insert, id_user_update, data_ora_update)
  VALUES( 7, 'NO LETTERA', 7, 2500.00, 25000.00, 2500.00, '2030-06-29', 'Testo unico sulla tutela delle aree naturali e della biodiversità.', '2019-04-15', NULL, true, 2, '2019-04-15 10:30:18.452', 2, '2019-04-15 10:31:27.492');
