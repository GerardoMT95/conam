SET client_encoding TO 'WIN1258';

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  31,
  'EPAYND02',
  'Attenzione! Si verificato un errore durante l''invio dei dati relativi alla creazione dei bollettini del pagamento. ',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  42,
  'REQCON03',
  'Non trovato',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  43,
  'NOTDATA01',
  'Impossibile salvare una notifica con una data di spedizione superiore alla data di notifica. Ricontrollare le date prima del salvataggio',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  12,
  'STAS01',
  'Il codice fiscale inserito non è valido',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  11,
  'INSVERB01',
  'Il numero verbale è già stato inserito ',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  17,
  'MODVERB01',
  'Il numero verbale è già stato inserito ',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  20,
  'SVALL01',
  'La data di notifica non corrisponde alla data di accertamento del verbale',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  19,
  'INSSOG02',
  'Il soggetto è già collegato al verbale',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  21,
  'INSLEG01',
  'La legge digitata esiste già nel sistema',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  22,
  'INSAMB01',
  'L''ambito digitato esiste già nel sistema',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  23,
  'CALRATE01',
  'L''importo minimo di ogni singola rata deve essere 15,49',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  24,
  'CALRATE02',
  'L''importo della prima rata è superiore all''importo totale',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  25,
  'CALRATE04',
  'L''importo della prima rata è minore di 15,49 ',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  26,
  'CALRATE05',
  'L''importo dell''ultima rata è minore di 15,49 ',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  27,
  'CALRATE03',
  'L''importo dell''ultima rata è superiore all''importo totale',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  34,
  'INSORD01',
  'Attenzione! Esiste già un ordinanza con lo stesso numero determinazione',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  38,
  'NOTNUMR01',
  'Il numero raccomandata notifica è già presente sul database!',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  4,
  'STADOCMT01',
  'Attenzione! Non è possibile estrarre il formato del documento ',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  5,
  'STADOCMT02',
  'Attenzione! Non è possibile estrarre il formato del documento ',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  39,
  'STADOCND04',
  'Attenzione! Il servizio di protocollazione non è al momento disponibile.',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  3,
  'STADOCND03',
  'Attenzione! Il servizio di eliminazione di un documento non è al momento disponibile',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  13,
  'INSSOG01',
  'Non è possibile inserire il soggetto in quanto è già associato al verbale ',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  18,
  'INSSOG03',
  'Il codice fiscale dell''utente è già censito sul db. Verificare i criteri di ricerca ',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  29,
  'STADOCND01',
  'Attenzione! Il servizio di salvataggio documento non è al momento disponibile. Riprovare tra qualche minuto',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  32,
  'EPAYND01',
  'Attenzione! Il sistema per la creazione dei bollettini non è al momemto disponibile. Riprovare tra qualche minuto',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  35,
  'DOCSOL01',
  'Attenzione! Il bollettino del sollecito è in fase di generazione. Riprovare tra qualche minuto',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  36,
  'STADOCND02',
  'Attenzione! Il documento che si è cercato di scaricare non al momento recuperabile causa un errore verso la componente di recupero!',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  33,
  'DOCORD01',
  'Attenzione! Il bollettino dell''ordinanza è in fase di generazione. Riprovare tra qualche minuto',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  30,
  'DOCRATE01',
  'Attenzione! I bollettini  del piano sono in fase di generazione. Riprovare tra qualche minuto',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  1,
  'VOCON06',
  'Non trovato',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  2,
  'RESCON08',
  'Non trovato',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  6,
  'RESCON12',
  'Non trovato',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  7,
  'RESCON09',
  'Non trovato',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  8,
  'RESCON11',
  'Non trovato',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  9,
  'VOCON02',
  'Non trovato',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  10,
  'REQCON06',
  'Non trovato',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  14,
  'RESCON10',
  'Non trovato',
  1
);

INSERT INTO cnm_d_messaggio
(
  id_messaggio,
  cod_messaggio,
  desc_messaggio,
  id_tipo_messaggio
)
VALUES
(
  15,
  'REQCON04',
  'Non trovato',
  1
);
