SET client_encoding TO 'WIN1258';

-- CNM_D_UTILIZZO
INSERT INTO cnm_d_utilizzo ( id_utilizzo, desc_utilizzo ) VALUES ( 1, 'Normativa' );
INSERT INTO cnm_d_utilizzo ( id_utilizzo, desc_utilizzo ) VALUES ( 2, 'Ente accertatore' );


-- CNM_R_USER_ENTE
INSERT INTO cnm_r_user_ente (id_user_ente, id_utilizzo, id_user, id_ente, inizio_validita, fine_validita) VALUES(1, 1, 1, 1, '2018-12-20', NULL);
INSERT INTO cnm_r_user_ente (id_user_ente, id_utilizzo, id_user, id_ente, inizio_validita, fine_validita) VALUES(2, 1, 2, 1, '2018-12-20', NULL);
INSERT INTO cnm_r_user_ente (id_user_ente, id_utilizzo, id_user, id_ente, inizio_validita, fine_validita) VALUES(5, 2, 1, 1, '2018-12-20', NULL);
INSERT INTO cnm_r_user_ente (id_user_ente, id_utilizzo, id_user, id_ente, inizio_validita, fine_validita) VALUES(6, 2, 2, 1, '2018-12-20', NULL);
INSERT INTO cnm_r_user_ente (id_user_ente, id_utilizzo, id_user, id_ente, inizio_validita, fine_validita) VALUES(4, 1, 3, 1, '2018-12-20', NULL);
INSERT INTO cnm_r_user_ente (id_user_ente, id_utilizzo, id_user, id_ente, inizio_validita, fine_validita) VALUES(3, 1, 3, 2, '2018-12-20', NULL);
INSERT INTO cnm_r_user_ente (id_user_ente, id_utilizzo, id_user, id_ente, inizio_validita, fine_validita) VALUES(7, 2, 3, 3, '2018-12-20', NULL);
INSERT INTO cnm_r_user_ente (id_user_ente, id_utilizzo, id_user, id_ente, inizio_validita, fine_validita) VALUES(8, 1, 4, 1, '2018-12-20', NULL);
INSERT INTO cnm_r_user_ente (id_user_ente, id_utilizzo, id_user, id_ente, inizio_validita, fine_validita) VALUES(9, 2, 4, 1, '2018-12-20', NULL);
