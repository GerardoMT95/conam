SET client_encoding TO 'WIN1258';

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  required,
  ordine,
  riga
)
VALUES
(
  10,
  14,
  2,
  'Organo giurisdizionale',
  50,
  TRUE,
  1,
  0
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  scale,
  required,
  ordine,
  riga
)
VALUES
(
  14,
  14,
  1,
  'Importo a titolo di sanzione',
  8,
  1,
  TRUE,
  5,
  2
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  scale,
  required,
  ordine,
  riga
)
VALUES
(
  15,
  14,
  1,
  'Importo per spese processuali',
  8,
  1,
  FALSE,
  6,
  2
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  scale,
  required,
  ordine,
  riga
)
VALUES
(
  17,
  22,
  1,
  'Importo pagato',
  8,
  1,
  TRUE,
  2,
  0
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  required,
  ordine,
  riga
)
VALUES
(
  7,
  7,
  3,
  'Data di pagamento',
  TRUE,
  1,
  1
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  required,
  ordine,
  riga
)
VALUES
(
  9,
  7,
  2,
  'Conto corrente di versamento',
  50,
  TRUE,
  3,
  2
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  scale,
  required,
  ordine,
  riga
)
VALUES
(
  8,
  7,
  1,
  'Importo pagato',
  8,
  1,
  TRUE,
  2,
  1
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  required,
  ordine,
  riga
)
VALUES
(
  18,
  22,
  2,
  'Conto corrente di versamento',
  50,
  TRUE,
  4,
  1
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  required,
  ordine,
  riga
)
VALUES
(
  16,
  22,
  3,
  'Data di pagamento',
  TRUE,
  1,
  0
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  required,
  ordine,
  riga
)
VALUES
(
  6,
  5,
  4,
  'Notificata',
  FALSE,
  1,
  0
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  required,
  ordine,
  riga
)
VALUES
(
  3,
  5,
  3,
  'Data di notifica',
  TRUE,
  2,
  0
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  required,
  ordine,
  riga
)
VALUES
(
  1,
  5,
  1,
  'Numero raccomandata',
  6,
  FALSE,
  3,
  1
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  required,
  ordine,
  riga
)
VALUES
(
  2,
  5,
  3,
  'Data di spedizione',
  FALSE,
  4,
  1
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  scale,
  required,
  ordine,
  riga
)
VALUES
(
  4,
  5,
  1,
  'Importo spese di notifica',
  8,
  1,
  FALSE,
  5,
  2
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  required,
  ordine,
  riga,
  id_elenco
)
VALUES
(
  12,
  14,
  5,
  'Esito disposizione',
  30,
  TRUE,
  3,
  1,
  2
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  required,
  ordine,
  riga
)
VALUES
(
  13,
  14,
  3,
  'Data disposizione',
  TRUE,
  2,
  0
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  required,
  ordine,
  riga
)
VALUES
(
  11,
  14,
  1,
  'Numero disposizione',
  6,
  TRUE,
  4,
  1
);

INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  required,
  ordine,
  riga,
  id_elenco
)
VALUES
(
  5,
  5,
  5,
  'Modalità',
  30,
  FALSE,
  6,
  2,
  1
);
