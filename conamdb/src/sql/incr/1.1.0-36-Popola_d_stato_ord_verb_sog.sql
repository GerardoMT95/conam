SET client_encoding TO 'WIN1258';

INSERT INTO cnm_d_stato_ord_verb_sog
(
  id_stato_ord_verb_sog,
  desc_stato_ord_verb_sog
)
VALUES
(
  1,
  'Ingiunzione'
);

INSERT INTO cnm_d_stato_ord_verb_sog
(
  id_stato_ord_verb_sog,
  desc_stato_ord_verb_sog
)
VALUES
(
  2,
  'Archiviato'
);

INSERT INTO cnm_d_stato_ord_verb_sog
(
  id_stato_ord_verb_sog,
  desc_stato_ord_verb_sog,
  id_elemento_elenco
)
VALUES
(
  3,
  'Ricorso accolto',
  5
);

INSERT INTO cnm_d_stato_ord_verb_sog
(
  id_stato_ord_verb_sog,
  desc_stato_ord_verb_sog,
  id_elemento_elenco
)
VALUES
(
  4,
  'Ricorso parzialmente accolto',
  6
);

INSERT INTO cnm_d_stato_ord_verb_sog
(
  id_stato_ord_verb_sog,
  desc_stato_ord_verb_sog,
  id_elemento_elenco
)
VALUES
(
  5,
  'Ricorso respinto',
  7
);

INSERT INTO cnm_d_stato_ord_verb_sog
(
  id_stato_ord_verb_sog,
  desc_stato_ord_verb_sog,
  id_elemento_elenco
)
VALUES
(
  6,
  'Ricorso inammissibile',
  8
);

INSERT INTO cnm_d_stato_ord_verb_sog
(
  id_stato_ord_verb_sog,
  desc_stato_ord_verb_sog
)
VALUES
(
  8,
  'Pagato online'
);

INSERT INTO cnm_d_stato_ord_verb_sog
(
  id_stato_ord_verb_sog,
  desc_stato_ord_verb_sog
)
VALUES
(
  7,
  'Pagato offline'
);

INSERT INTO cnm_d_stato_ord_verb_sog
(
  id_stato_ord_verb_sog,
  desc_stato_ord_verb_sog
)
VALUES
(
  9,
  'Riscosso tramite SORIS'
);
