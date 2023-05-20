SET client_encoding TO 'WIN1258';

-- CNM_D_GRUPPO
INSERT INTO cnm_d_gruppo ( id_gruppo, desc_gruppo )
  VALUES ( 1, 'Istruttori Piemonte' );


-- CNM_T_USER
INSERT INTO cnm_t_user ( id_user, codice_fiscale, id_ruolo, id_gruppo, inizio_validita, fine_validita )
  VALUES ( 1, 'AAAAAA00B77B000F', 1, 1, '2018-12-20', NULL );
INSERT INTO cnm_t_user ( id_user, codice_fiscale, id_ruolo, id_gruppo, inizio_validita, fine_validita )
  VALUES ( 2, 'AAAAAA00A11B000J', 2, NULL, '2018-12-20', NULL );
INSERT INTO cnm_t_user ( id_user, codice_fiscale, id_ruolo, id_gruppo, inizio_validita, fine_validita )
  VALUES ( 3, 'AAAAAA00A11C000K', 3, NULL, '2018-12-20', NULL );
INSERT INTO cnm_t_user ( id_user, codice_fiscale, id_ruolo, id_gruppo, inizio_validita, fine_validita )
  VALUES ( 4, 'AAAAAA00A11E000M', 1, 1, '2018-12-20', NULL );
INSERT INTO cnm_t_user ( id_user, codice_fiscale, id_ruolo, id_gruppo, inizio_validita, fine_validita )
  VALUES ( 5, 'EPAY', 4, NULL, '2019-02-01', NULL );
