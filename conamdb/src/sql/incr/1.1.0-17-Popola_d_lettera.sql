SET client_encoding TO 'WIN1258';

INSERT INTO cnm_d_lettera
(
  id_lettera,
  lettera,
  id_comma,
  importo_ridotto,
  scadenza_importi,
  desc_illecito,
  inizio_validita,
  id_user_insert
)
VALUES
(
  1,
  'LETTERA',
  1,
  500.00,
  '2021-07-16',
  'xcvbn',
  '2019-08-06',
  0
);

INSERT INTO cnm_d_lettera
(
  id_lettera,
  lettera,
  id_comma,
  importo_ridotto,
  scadenza_importi,
  desc_illecito,
  inizio_validita,
  fine_validita,
  id_user_insert
)
VALUES
(
  2,
  'LETTEST',
  2,
  100.00,
  '2019-09-30',
  'Illecito di Test',
  '2019-08-29',
  '2019-09-30',
  0
);

INSERT INTO cnm_d_lettera
(
  id_lettera,
  lettera,
  id_comma,
  importo_ridotto,
  scadenza_importi,
  desc_illecito,
  inizio_validita,
  fine_validita,
  eliminato,
  id_user_insert
)
VALUES
(
  3,
  'NO LETTERA',
  3,
  23.00,
  '2019-09-07',
  'illecito9',
  '2019-08-29',
  '2019-09-07',
  TRUE,
  0
);

INSERT INTO cnm_d_lettera
(
  id_lettera,
  lettera,
  id_comma,
  sanzione_minima,
  sanzione_massima,
  importo_ridotto,
  scadenza_importi,
  desc_illecito,
  inizio_validita,
  id_user_insert
)
VALUES
(
  4,
  'A',
  4,
  12.00,
  12.00,
  12.00,
  '2019-05-04',
  '21',
  '2019-04-23',
  0
);

INSERT INTO cnm_d_lettera
(
  id_lettera,
  lettera,
  id_comma,
  importo_ridotto,
  scadenza_importi,
  desc_illecito,
  inizio_validita,
  id_user_insert
)
VALUES
(
  5,
  '1',
  5,
  12.00,
  '2019-04-12',
  'ADS',
  '2019-04-23',
  0
);

INSERT INTO cnm_d_lettera
(
  id_lettera,
  lettera,
  id_comma,
  importo_ridotto,
  scadenza_importi,
  desc_illecito,
  inizio_validita,
  id_user_insert
)
VALUES
(
  6,
  'B',
  6,
  450.00,
  '2019-11-03',
  'SVERSAMENTO LIQUAMI NON TRATTATI IN TORRENTI',
  '2019-06-06',
  0
);

INSERT INTO cnm_d_lettera
(
  id_lettera,
  lettera,
  id_comma,
  sanzione_minima,
  sanzione_massima,
  importo_ridotto,
  scadenza_importi,
  desc_illecito,
  inizio_validita,
  id_user_insert
)
VALUES
(
  7,
  'NO LETTERA',
  7,
  20000.00,
  80000.00,
  20000.00,
  '2019-09-28',
  'Norme in materia ambientale. Attuazione della direttiva 2014/52/UE del Parlamento europeo e del Consiglio, del 16 aprile 2014, che modifica la direttiva 2011/92/UE, concernente la valutazione dell''impatto ambientale di determinati progetti pubblici e privati, ai sensi degli articoli 1 e 14 della legge 9 luglio 2015, n. 114.',
  '2019-06-20',
  0
);

INSERT INTO cnm_d_lettera
(
  id_lettera,
  lettera,
  id_comma,
  importo_ridotto,
  scadenza_importi,
  desc_illecito,
  inizio_validita,
  id_user_insert
)
VALUES
(
  8,
  'NO LETTERA',
  8,
  1200.00,
  '2020-08-31',
  'Norme a tutela dei bacini idrici di competenza Regionale',
  '2019-07-04',
  0
);
