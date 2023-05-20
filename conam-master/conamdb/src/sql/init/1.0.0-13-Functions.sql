CREATE OR REPLACE
FUNCTION PulisciDenominazione ( pDenomComune  VARCHAR(100)
                              ) RETURNS VARCHAR(100) AS
$BODY$
DECLARE
  sDenomComune  cnm_d_comune.denom_comune%TYPE;
  sCarattere    VARCHAR(1);
BEGIN
  sDenomComune = ' ';
  FOR i IN 1 .. LENGTH(pDenomComune)
  LOOP
    sCarattere = SUBSTR(pDenomComune,i,1);
    IF ASCII(sCarattere) = 9562 THEN
      sDenomComune = sDenomComune || CHR(200);
    ELSIF ASCII(sCarattere) = 9556 THEN
      sDenomComune = sDenomComune || CHR(201);
    ELSIF ASCII(sCarattere) = 9516 THEN
      sDenomComune = sDenomComune || CHR(194);
    ELSIF ASCII(sCarattere) = 9600 THEN
      sDenomComune = sDenomComune || 'SS';
    ELSIF ASCII(sCarattere) = 9560 THEN
      sDenomComune = sDenomComune || CHR(212);
    ELSIF ASCII(sCarattere) = 9567 THEN
      sDenomComune = sDenomComune || CHR(199);
    ELSIF ASCII(sCarattere) = 9577 THEN
      sDenomComune = sDenomComune || CHR(202);
    ELSIF ASCII(sCarattere) = 3616 THEN
      sDenomComune = sDenomComune || 'A''';
    ELSIF ASCII(sCarattere) = 3619 THEN
      sDenomComune = sDenomComune || 'A';
    ELSIF ASCII(sCarattere) = 3625 THEN
      sDenomComune = sDenomComune || 'E''';
    ELSIF ASCII(sCarattere) = 3641 THEN
      sDenomComune = sDenomComune || 'U''';
    ELSE
      sDenomComune = sDenomComune || sCarattere;
    END IF;
  END LOOP;
  -------
  RETURN TRIM(sDenomComune);
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



CREATE OR REPLACE
FUNCTION GetNextIdNazione()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdNazione  cnm_d_nazione.id_nazione%TYPE;
BEGIN
  SELECT  COALESCE(MAX(id_nazione),0) + 1
    INTO  nIdNazione
    FROM  cnm_d_nazione;
  ------------
  RETURN nIdNazione;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION GetNextIdRegione()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdRegione  cnm_d_regione.id_regione%TYPE;
BEGIN
  SELECT  COALESCE(MAX(id_regione),0) + 1
    INTO  nIdRegione
    FROM  cnm_d_regione;
  ------------
  RETURN nIdRegione;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION GetNextIdProvincia()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdProvincia  cnm_d_provincia.id_provincia%TYPE;
BEGIN
  SELECT  COALESCE(MAX(id_provincia),0) + 1
    INTO  nIdProvincia
    FROM  cnm_d_provincia;
  ------------
  RETURN nIdProvincia;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION GetNextIdComune()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdComune   cnm_d_comune.id_comune%TYPE;
BEGIN
  SELECT  COALESCE(MAX(id_comune),0) + 1
    INTO  nIdComune
    FROM  cnm_d_comune;
  ------------
  RETURN nIdComune;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION GetNextIdSnazione()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdSnazione  cnm_s_nazione.id_s_nazione%TYPE;
BEGIN
  SELECT  COALESCE(MAX(id_s_nazione),0) + 1
    INTO  nIdSnazione
    FROM  cnm_s_nazione;
  ------------
  RETURN nIdSnazione;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION GetNextIdSregione()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdSregione   cnm_s_regione.id_s_regione%TYPE;
BEGIN
  SELECT  COALESCE(MAX(id_s_regione),0) + 1
    INTO  nIdSregione
    FROM  cnm_s_regione;
  ------------
  RETURN nIdSregione;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION GetNextIdSprovincia()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdSprovincia   cnm_s_provincia.id_s_provincia%TYPE;
BEGIN
  SELECT  COALESCE(MAX(id_s_provincia),0) + 1
    INTO  nIdSprovincia
    FROM  cnm_s_provincia;
  ------------
  RETURN nIdSprovincia;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION GetNextIdScomune()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdScomune  cnm_s_comune.id_s_comune%TYPE;
BEGIN
  SELECT  COALESCE(MAX(id_s_comune),0) + 1
    INTO  nIdScomune
    FROM  cnm_s_comune;
  ------------
  RETURN nIdScomune;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION GetIdNazione  ( pCodIstatNazione  CHARACTER VARYING
              )
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdNazione  cnm_d_nazione.id_nazione%TYPE;
BEGIN
  SELECT  id_nazione
    INTO  nIdNazione
    FROM  cnm_d_nazione
    WHERE cod_istat_nazione = pCodIstatNazione;
  ------------
  RETURN nIdNazione;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION GetIdRegione ( pCodRegione CHARACTER VARYING
                      )
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdRegione  cnm_d_regione.id_regione%TYPE;
BEGIN
  SELECT  id_regione
    INTO  nIdRegione
    FROM  cnm_d_regione
    WHERE cod_regione = pCodRegione;
  ------------
  RETURN nIdRegione;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION GetIdProvincia ( pCodProvincia CHARACTER VARYING
                        )
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nIdProvincia  cnm_d_provincia.id_provincia%TYPE;
BEGIN
  SELECT  id_provincia
    INTO  nIdProvincia
    FROM  cnm_d_provincia
    WHERE cod_provincia = pCodProvincia;
  ------------
  RETURN nIdProvincia;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;




CREATE OR REPLACE
FUNCTION AggiornaNazione  ( pIdStato          NUMERIC
                          , pIdStatoPrev      NUMERIC
                          , pIdStatoNext      NUMERIC
                          , pCodIstatNazione  CHARACTER VARYING
                          , pDenomNazione     CHARACTER VARYING
                          , pInizioValidita   DATE
                          , pFineValidita     DATE
                          )
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nRecs           cnm_d_nazione.id_nazione%TYPE;
  sDenomNazione   cnm_d_nazione.denom_nazione%TYPE;
BEGIN
  sDenomNazione = PulisciDenominazione(pDenomNazione);
  ------
  IF pIdStatoPrev = 0 THEN
    -- Non ha precedenti quindi provo stare nella tabella degli attuali
    UPDATE  cnm_d_nazione
      SET cod_istat_nazione = pCodIstatNazione
        , denom_nazione = sDenomNazione
        , inizio_validita = pInizioValidita
        , fine_validita = pFineValidita
        , dt_id_stato = pIdStato
        , dt_id_stato_prev = pIdStatoPrev
        , dt_id_stato_next = pIdStatoNext
      WHERE dt_id_stato = pIdStato;
    GET DIAGNOSTICS nRecs = ROW_COUNT;
    IF nRecs = 0 THEN
      UPDATE  cnm_s_nazione
        SET cod_istat_nazione = pCodIstatNazione
          , denom_nazione = sDenomNazione
          , inizio_validita = pInizioValidita
          , fine_validita = pFineValidita
          , dt_id_stato = pIdStato
          , dt_id_stato_prev = pIdStatoPrev
          , dt_id_stato_next = pIdStatoNext
        WHERE dt_id_stato = pIdStato;
      --------
      GET DIAGNOSTICS nRecs = ROW_COUNT;
      IF nRecs = 0 THEN
        INSERT INTO cnm_d_nazione
                    ( id_nazione
                    , cod_istat_nazione
                    , denom_nazione
                    , inizio_validita
                    , fine_validita
                    , dt_id_stato
                    , dt_id_stato_prev
                    , dt_id_stato_next
                    )
          VALUES  ( GetNextIdNazione()
                  , pCodIstatNazione
                  , sDenomNazione
                  , pInizioValidita
                  , pFineValidita
                  , pIdStato
                  , pIdStatoPrev
                  , pIdStatoNext
                  );
      END IF;
    END IF;
  ELSE
    SELECT  COUNT(1)
      INTO  nRecs
      FROM  cnm_d_nazione
      WHERE dt_id_stato = pIdStatoPrev
        AND cod_istat_nazione = pCodIstatNazione
        AND denom_nazione = sDenomNazione
        AND inizio_validita = pInizioValidita
        AND NVL(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = NVL(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
    IF nRecs = 0 THEN
      -- Ha un precedente che quindi vado a spostare nello storico
      INSERT INTO cnm_s_nazione
                  ( id_s_nazione
                  , id_nazione
                  , cod_istat_nazione
                  , denom_nazione
                  , inizio_validita
                  , fine_validita
                  , dt_id_stato
                  , dt_id_stato_prev
                  , dt_id_stato_next
                  )
        SELECT  GetNextIdSnazione()
              , id_nazione
              , cod_istat_nazione
              , denom_nazione
              , inizio_validita
              , fine_validita
              , dt_id_stato
              , dt_id_stato_prev
              , dt_id_stato_next
          FROM  cnm_d_nazione
          WHERE dt_id_stato = pIdStatoPrev;
      -- Aggiorno i dati nell'attuale
      UPDATE  cnm_d_nazione
        SET cod_istat_nazione = pCodIstatNazione
          , denom_nazione = sDenomNazione
          , inizio_validita = pInizioValidita
          , fine_validita = pFineValidita
          , dt_id_stato = pIdStato
          , dt_id_stato_prev = pIdStatoPrev
          , dt_id_stato_next = pIdStatoNext
        WHERE dt_id_stato = pIdStatoPrev;
    END IF;
  END IF;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;




CREATE OR REPLACE
FUNCTION AggiornaNazioni()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  rec       RECORD;
  nRetCode  SMALLINT;
BEGIN
  FOR rec IN  SELECT  TO_NUMBER(SUBSTR(record,4,20),'99999999999999999999')                                                                                     id_stato
                    , TO_NUMBER(SUBSTR(record,44,20),'99999999999999999999')                                                                                    id_stato_prev
                    , TO_NUMBER(SUBSTR(record,24,20),'99999999999999999999')                                                                                    id_stato_next
                    , TRIM(SUBSTR(record,1,3))                                                                                                                  cod_nazione
                    , TRIM(SUBSTR(record,84,100))                                                                                                               denom_nazione
                    , TO_DATE(SUBSTR(record,64,10),'DD/MM/YYYY')                                                                                                inizio_validita
                    , DECODE(TO_DATE(SUBSTR(record,74,10),'DD/MM/YYYY'), TO_DATE('00/00/0000','DD/MM/YYYY'),NULL, TO_DATE(SUBSTR(record,74,10),'DD/MM/YYYY'))   fine_validita
                FROM  dt_stati_esteri_istat
                ORDER BY  id_stato
  LOOP
    nRetCode = AggiornaNazione( rec.id_stato, rec.id_stato_prev, rec.id_stato_next, rec.cod_nazione, rec.denom_nazione, rec.inizio_validita, rec.fine_validita);
  END LOOP;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;




CREATE OR REPLACE
FUNCTION AggiornaRegione  ( pCodRegione       CHARACTER VARYING
                          , pDenomRegione     CHARACTER VARYING
                          , pIdNazione        NUMERIC
                          , pInizioValidita   DATE
                          , pFineValidita     DATE
                          )
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nRecs           cnm_d_regione.id_regione%TYPE;
  sDenomRegione   cnm_d_regione.denom_regione%TYPE;
BEGIN
  sDenomRegione = PulisciDenominazione(pDenomRegione);
  --------
  SELECT  COUNT(1)
    INTO  nRecs
    FROM  cnm_d_regione
    WHERE cod_regione = pCodRegione;
  IF nRecs = 0 THEN
    -- Regione mai inserita prima
    INSERT INTO cnm_d_regione ( id_regione, cod_regione, denom_regione, id_nazione, inizio_validita, fine_validita )
      VALUES  ( GetNextIdRegione(), pCodRegione, sDenomRegione, pIdNazione, pInizioValidita, pFineValidita );
  ELSE
    SELECT  COUNT(1)
      INTO  nRecs
      FROM  cnm_d_regione
      WHERE cod_regione = pCodRegione
        AND denom_regione = sDenomRegione
        AND id_nazione = pIdNazione
        AND inizio_validita = pInizioValidita
        AND NVL(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = NVL(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
    IF nRecs = 0 THEN
      SELECT  COUNT(1)
        INTO  nRecs
        FROM  cnm_s_regione
        WHERE cod_regione = pCodRegione
          AND denom_regione = sDenomRegione
          AND id_nazione = pIdNazione
          AND inizio_validita = pInizioValidita
          AND NVL(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = NVL(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
      IF nRecs = 0 THEN
        -- Aggiornamento su una regione esistente
        INSERT INTO cnm_s_regione ( id_s_regione, id_regione, cod_regione, denom_regione, id_nazione, inizio_validita, fine_validita )
          SELECT  GetNextIdSregione(), id_regione, cod_regione, denom_regione, id_nazione, inizio_validita, fine_validita
            FROM  cnm_d_regione
            WHERE cod_regione = pCodRegione;
        UPDATE  cnm_d_regione
          SET denom_regione = sDenomRegione
            , id_nazione = pIdNazione
            , inizio_validita = pInizioValidita
            , fine_validita = pFineValidita
          WHERE cod_regione = pCodRegione;
      END IF;
    END IF;
  END IF;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;




CREATE OR REPLACE
FUNCTION AggiornaRegioni()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  rec               RECORD;
  nRetCode          SMALLINT;
  sCodIstatNazione  cnm_d_nazione.cod_istat_nazione%TYPE;
BEGIN
  FOR rec IN  SELECT  cod_istat_regione                                                               cod_istat_regione
                    , denom_regione                                                                   denom_regione
                    , cod_istat_nazione                                                               cod_istat_nazione
                    , inizio_validita                                                                 inizio_validita
                    , DECODE(fine_validita, TO_DATE('31/12/9999','DD/MM/YYYY'),NULL, fine_validita)   fine_validita
                FROM  ( SELECT  TRIM(SUBSTR(record,507,2))                                                                                                                                                  cod_istat_regione
                              , TRIM(SUBSTR(record,509,100))                                                                                                                                                denom_regione
                              , TRIM(SUBSTR(record,609,3))                                                                                                                                                  cod_istat_nazione
                              , MIN(TO_DATE(SUBSTR(record,61,10),'DD/MM/YYYY'))                                                                                                                             inizio_validita
                              , MAX(DECODE(TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY'), TO_DATE('00/00/0000','DD/MM/YYYY'),TO_DATE('31/12/9999','DD/MM/YYYY'), TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY')))  fine_validita
                          FROM  dt_limiti_amministrativi
                          GROUP BY  cod_istat_regione
                                  , denom_regione
                                  , cod_istat_nazione
                      )   reg
                ORDER BY  cod_istat_regione
                        , inizio_validita
  LOOP
    sCodIstatNazione = rec.cod_istat_nazione;
    IF sCodIstatNazione = '001' THEN
      sCodIstatNazione = '100';
    END IF;
    nRetCode = AggiornaRegione(rec.cod_istat_regione, rec.denom_regione, GetIdNazione(sCodIstatNazione), rec.inizio_validita, rec.fine_validita);
  END LOOP;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



CREATE OR REPLACE
FUNCTION AggiornaProvincia  ( pCodProvincia     CHARACTER VARYING
                            , pDenomProvincia   CHARACTER VARYING
                            , pSiglaProvincia   CHARACTER VARYING
                            , pIdRegione        NUMERIC
                            , pInizioValidita   DATE
                            , pFineValidita     DATE
                            )
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nRecs             cnm_d_provincia.id_provincia%TYPE;
  sDenomProvincia   cnm_d_provincia.denom_provincia%TYPE;
BEGIN
  SELECT  COUNT(1)
    INTO  nRecs
    FROM  cnm_d_provincia
    WHERE cod_provincia = pCodProvincia;
  IF nRecs = 0 THEN
    -- Regione mai inserita prima
    INSERT INTO cnm_d_provincia ( id_provincia, cod_provincia, denom_provincia, sigla_provincia, id_regione, inizio_validita, fine_validita )
      VALUES  ( GetNextIdProvincia(), pCodProvincia, sDenomProvincia, pSiglaProvincia, pIdRegione, pInizioValidita, pFineValidita );
  ELSE
    SELECT  COUNT(1)
      INTO  nRecs
      FROM  cnm_d_provincia
      WHERE cod_provincia = pCodProvincia
        AND denom_provincia = sDenomProvincia
        AND sigla_provincia = pSiglaProvincia
        AND id_regione = pIdRegione
        AND inizio_validita = pInizioValidita
        AND NVL(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = NVL(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
    IF nRecs = 0 THEN
      SELECT  COUNT(1)
        INTO  nRecs
        FROM  cnm_s_provincia
        WHERE cod_provincia = pCodProvincia
          AND denom_provincia = sDenomProvincia
          AND sigla_provincia = pSiglaProvincia
          AND id_regione = pIdRegione
          AND inizio_validita = pInizioValidita
          AND NVL(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = NVL(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
      IF nRecs = 0 THEN
        -- Aggiornamento su una regione esistente
        INSERT INTO cnm_s_provincia ( id_s_provincia, id_provincia, cod_provincia, denom_provincia, sigla_provincia, id_regione, inizio_validita, fine_validita )
          SELECT  GetNextIdSprovincia(), id_provincia, cod_provincia, denom_provincia, sigla_provincia, id_regione, inizio_validita, fine_validita
            FROM  cnm_d_provincia
            WHERE cod_provincia = pCodProvincia;
        UPDATE  cnm_d_provincia
          SET denom_provincia = sDenomProvincia
            , sigla_provincia = pSiglaProvincia
            , id_regione = pIdRegione
            , inizio_validita = pInizioValidita
            , fine_validita = pFineValidita
          WHERE cod_provincia = pCodProvincia;
      END IF;
    END IF;
  END IF;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;




CREATE OR REPLACE
FUNCTION AggiornaProvince()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  rec               RECORD;
  nRetCode          SMALLINT;
BEGIN
  FOR rec IN  SELECT  cod_istat_provincia                                                             cod_istat_provincia
                    , TRANSLATE(denom_provincia,'╔','E')                                              denom_provincia
                    , sigla_provincia                                                                 sigla_provincia
                    , cod_istat_regione                                                               cod_istat_regione
                    , inizio_validita                                                                 inizio_validita
                    , DECODE(fine_validita, TO_DATE('31/12/9999','DD/MM/YYYY'),NULL, fine_validita)   fine_validita
                FROM  ( SELECT  TRIM(SUBSTR(record,199,3))                                                                                                                                                  cod_istat_provincia
                              , TRIM(SUBSTR(record,202,100))                                                                                                                                                denom_provincia
                              , TRIM(SUBSTR(record,302,4))                                                                                                                                                  sigla_provincia
                              , TRIM(SUBSTR(record,507,2))                                                                                                                                                  cod_istat_regione
                              , MIN(TO_DATE(SUBSTR(record,61,10),'DD/MM/YYYY'))                                                                                                                             inizio_validita
                              , MAX(DECODE(TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY'), TO_DATE('00/00/0000','DD/MM/YYYY'),TO_DATE('31/12/9999','DD/MM/YYYY'), TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY')))  fine_validita
                          FROM  dt_limiti_amministrativi
                          GROUP BY  cod_istat_provincia
                                  , denom_provincia
                                  , sigla_provincia
                                  , cod_istat_regione
                      )   prov
                ORDER BY  cod_istat_provincia
                        , inizio_validita
  LOOP
    nRetCode = AggiornaProvincia(rec.cod_istat_provincia, rec.denom_provincia, rec.sigla_provincia, GetIdRegione(rec.cod_istat_regione), rec.inizio_validita, rec.fine_validita);
  END LOOP;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



CREATE OR REPLACE
FUNCTION AggiornaComune ( pIdComune           NUMERIC
                        , pIdComunePrev       NUMERIC
                        , pIdComuneNext       NUMERIC
                        , pCodIstatComune     CHARACTER VARYING
                        , pCodBelfioreComune  CHARACTER VARYING
                        , pDenomComune        CHARACTER VARYING
                        , pIdProvincia        NUMERIC
                        , pInizioValidita     DATE
                        , pFineValidita       DATE
                        )
  RETURNS SMALLINT AS
$BODY$
DECLARE
  nRecs         cnm_d_comune.id_comune%TYPE;
  sDenomComune  cnm_d_comune.denom_comune%TYPE;
BEGIN
  sDenomComune = PulisciDenominazione(pDenomComune);
  -- Se Esiste dt_id_comune negli attivi eseguo l'update e termino
  UPDATE  cnm_d_comune
    SET cod_istat_comune = pCodIstatComune
      , cod_belfiore_comune = pCodBelfioreComune
      , denom_comune = sDenomComune
      , id_provincia = pIdProvincia
      , inizio_validita = pInizioValidita
      , fine_validita = pFineValidita
      , dt_id_comune_prev = pIdComunePrev
      , dt_id_comune_next = pIdComuneNext
    WHERE dt_id_comune = pIdComune;
  GET DIAGNOSTICS nRecs = ROW_COUNT;
  IF nRecs = 0 THEN
  -- Se Esiste dt_id_comune nello storico eseguo l'update e termino
    UPDATE  cnm_s_comune
      SET cod_istat_comune = pCodIstatComune
        , cod_belfiore_comune = pCodBelfioreComune
        , denom_comune = sDenomComune
        , id_provincia = pIdProvincia
        , inizio_validita = pInizioValidita
        , fine_validita = pFineValidita
        , dt_id_comune_prev = pIdComunePrev
        , dt_id_comune_next = pIdComuneNext
      WHERE dt_id_comune = pIdComune;
    --------
    GET DIAGNOSTICS nRecs = ROW_COUNT;
    IF nRecs = 0 THEN
      -- Se Esiste dt_id_comune_prev tra i dt_id_comune degli attivi sposto il record nello storico e aggiorno il record negli attivi
      INSERT INTO cnm_s_comune  ( id_s_comune, id_comune, cod_istat_comune, cod_belfiore_comune, denom_comune, id_provincia, inizio_validita, fine_validita
                                , dt_id_comune, dt_id_comune_prev, dt_id_comune_next
                                )
        SELECT  GetNextIdScomune(), id_comune, cod_istat_comune, pCodBelfioreComune, denom_comune, id_provincia, inizio_validita, fine_validita
              , dt_id_comune, dt_id_comune_prev, dt_id_comune_next
          FROM  cnm_d_comune
          WHERE dt_id_comune = pIdComunePrev;
      GET DIAGNOSTICS nRecs = ROW_COUNT;
      IF nRecs = 0 THEN
        -- Controllo se esiste un record identico tra gli attivi (in quel caso termino)
        SELECT  COUNT(1)
          INTO  nRecs
          FROM  cnm_d_comune
          WHERE COALESCE(cod_istat_comune,' ') = COALESCE(pCodIstatComune,' ')
            AND COALESCE(cod_belfiore_comune,' ') = COALESCE(pCodBelfioreComune,' ')
            AND denom_comune = sDenomComune
            AND id_provincia = pIdProvincia
            AND inizio_validita = pInizioValidita
            AND NVL(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = NVL(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
        IF nRecs = 0 THEN
          -- Controllo se esiste un record identico nello storico (in quel caso termino)
          SELECT  COUNT(1)
            INTO  nRecs
            FROM  cnm_s_comune
            WHERE COALESCE(cod_istat_comune,' ') = COALESCE(pCodIstatComune,' ')
              AND COALESCE(cod_belfiore_comune,' ') = COALESCE(pCodBelfioreComune,' ')
              AND denom_comune = sDenomComune
              AND id_provincia = pIdProvincia
              AND inizio_validita = pInizioValidita
              AND NVL(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = NVL(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
          IF nRecs = 0 THEN
            -- Se esiste un record con almeni istat, denominazione e provincia uguali lo sposto nello storico e aggiorno il record negli attivi
            INSERT INTO cnm_s_comune  ( id_s_comune, id_comune, cod_istat_comune, cod_belfiore_comune, denom_comune, id_provincia, inizio_validita, fine_validita
                                      , dt_id_comune, dt_id_comune_prev, dt_id_comune_next
                                      )
              SELECT  GetNextIdScomune(), id_comune, cod_istat_comune, pCodBelfioreComune, denom_comune, id_provincia, inizio_validita, fine_validita
                    , dt_id_comune, dt_id_comune_prev, dt_id_comune_next
                FROM  cnm_d_comune
                WHERE cod_istat_comune = pCodIstatComune
                  AND denom_comune = sDenomComune
                  AND id_provincia = pIdProvincia;
          GET DIAGNOSTICS nRecs = ROW_COUNT;
          IF nRecs = 0 THEN
            INSERT INTO cnm_d_comune  ( id_comune, cod_istat_comune, cod_belfiore_comune, denom_comune, id_provincia, inizio_validita, fine_validita
                                      , dt_id_comune, dt_id_comune_prev, dt_id_comune_next
                                      )
              VALUES  ( GetNextIdComune(), pCodIstatComune, pCodBelfioreComune, sDenomComune, pIdProvincia, pInizioValidita, pFineValidita
                      , pIdComune, pIdComunePrev, pIdComuneNext
                      );
          ELSE
            UPDATE  cnm_d_comune
              SET inizio_validita = pInizioValidita
                , fine_validita = pFineValidita
                , dt_id_comune = pIdComune
                , dt_id_comune_prev = pIdComunePrev
                , dt_id_comune_next = pIdComuneNext
              WHERE COALESCE(cod_istat_comune,' ') = COALESCE(pCodIstatComune,' ')
                AND COALESCE(cod_belfiore_comune,' ') = COALESCE(pCodBelfioreComune,' ')
                AND denom_comune = sDenomComune
                AND id_provincia = pIdProvincia;
            END IF;
          END IF;
        END IF;
      ELSE
        UPDATE  cnm_d_comune
          SET cod_istat_comune = pCodIstatComune
            , cod_belfiore_comune = pCodBelfioreComune
            , denom_comune = sDenomComune
            , id_provincia = pIdProvincia
            , inizio_validita = pInizioValidita
            , fine_validita = pFineValidita
            , dt_id_comune = pIdComune
            , dt_id_comune_prev = pIdComunePrev
            , dt_id_comune_next = pIdComuneNext
          WHERE dt_id_comune = pIdComunePrev;
      END IF;
    END IF;
  END IF;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;




CREATE OR REPLACE
FUNCTION AggiornaComuni()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  rec       RECORD;
  nRetCode  SMALLINT;
BEGIN
  -- Per primi elaboro i cessati
  FOR rec IN  SELECT  TO_NUMBER(SUBSTR(record,1,20),'99999999999999999999')                                                                                     id_comune
                    , TO_NUMBER(SUBSTR(record,41,20),'99999999999999999999')                                                                                    id_comune_prev
                    , TO_NUMBER(SUBSTR(record,21,20),'99999999999999999999')                                                                                    id_comune_next
                    , TRIM(SUBSTR(record,86,6))                                                                                                                 cod_istat_comune
                    , TRIM(SUBSTR(record,82,4))                                                                                                                 cod_belfiore_comune
                    , TRIM(SUBSTR(record,92,62))                                                                                                                denom_comune
                    , TRIM(SUBSTR(record,199,3))                                                                                                                cod_istat_provincia
                    , TO_DATE(SUBSTR(record,61,10),'DD/MM/YYYY')                                                                                                inizio_validita
                    , DECODE(TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY'), TO_DATE('00/00/0000','DD/MM/YYYY'),NULL, TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY'))   fine_validita
                FROM  dt_limiti_amministrativi
                WHERE TO_NUMBER(SUBSTR(record,81,1),'9') = '0'
                ORDER BY  TO_DATE(SUBSTR(record,61,10),'DD/MM/YYYY')
  LOOP
    nRetCode = AggiornaComune( rec.id_comune, rec.id_comune_prev, rec.id_comune_next, rec.cod_istat_comune, rec.cod_belfiore_comune, rec.denom_comune, GetIdProvincia(rec.cod_istat_provincia), rec.inizio_validita, rec.fine_validita);
  END LOOP;
  -- Poi elaboro i modificati
  FOR rec IN  SELECT  TO_NUMBER(SUBSTR(record,1,20),'99999999999999999999')                                                                                     id_comune
                    , TO_NUMBER(SUBSTR(record,41,20),'99999999999999999999')                                                                                    id_comune_prev
                    , TO_NUMBER(SUBSTR(record,21,20),'99999999999999999999')                                                                                    id_comune_next
                    , TRIM(SUBSTR(record,86,6))                                                                                                                 cod_istat_comune
                    , TRIM(SUBSTR(record,82,4))                                                                                                                 cod_belfiore_comune
                    , TRIM(SUBSTR(record,92,62))                                                                                                                denom_comune
                    , TRIM(SUBSTR(record,199,3))                                                                                                                cod_istat_provincia
                    , TO_DATE(SUBSTR(record,61,10),'DD/MM/YYYY')                                                                                                inizio_validita
                    , DECODE(TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY'), TO_DATE('00/00/0000','DD/MM/YYYY'),NULL, TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY'))   fine_validita
                FROM  dt_limiti_amministrativi
                WHERE TO_NUMBER(SUBSTR(record,81,1),'9') = '2'
                ORDER BY  TO_DATE(SUBSTR(record,61,10),'DD/MM/YYYY')
  LOOP
    nRetCode = AggiornaComune( rec.id_comune, rec.id_comune_prev, rec.id_comune_next, rec.cod_istat_comune, rec.cod_belfiore_comune, rec.denom_comune, GetIdProvincia(rec.cod_istat_provincia), rec.inizio_validita, rec.fine_validita);
  END LOOP;
  -- Poi elaboro gli attivi
  FOR rec IN  SELECT  TO_NUMBER(SUBSTR(record,1,20),'99999999999999999999')                                                                                     id_comune
                    , TO_NUMBER(SUBSTR(record,41,20),'99999999999999999999')                                                                                    id_comune_prev
                    , TO_NUMBER(SUBSTR(record,21,20),'99999999999999999999')                                                                                    id_comune_next
                    , TRIM(SUBSTR(record,86,6))                                                                                                                 cod_istat_comune
                    , TRIM(SUBSTR(record,82,4))                                                                                                                 cod_belfiore_comune
                    , TRIM(SUBSTR(record,92,62))                                                                                                                denom_comune
                    , TRIM(SUBSTR(record,199,3))                                                                                                                cod_istat_provincia
                    , TO_DATE(SUBSTR(record,61,10),'DD/MM/YYYY')                                                                                                inizio_validita
                    , DECODE(TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY'), TO_DATE('00/00/0000','DD/MM/YYYY'),NULL, TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY'))   fine_validita
                FROM  dt_limiti_amministrativi
                WHERE TO_NUMBER(SUBSTR(record,81,1),'9') = '1'
                ORDER BY  TO_DATE(SUBSTR(record,61,10),'DD/MM/YYYY')
  LOOP
    nRetCode = AggiornaComune( rec.id_comune, rec.id_comune_prev, rec.id_comune_next, rec.cod_istat_comune, rec.cod_belfiore_comune, rec.denom_comune, GetIdProvincia(rec.cod_istat_provincia), rec.inizio_validita, rec.fine_validita);
  END LOOP;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


CREATE OR REPLACE
FUNCTION PreparaFileDaScaricare() RETURNS INTEGER AS
$BODY$
DECLARE
  sFileIntero           cnm_t_file.file_intero%TYPE;
  sFileInteroDefinitivo cnm_t_file.file_intero%TYPE;
  nIdx                  INTEGER;
  sRecord               cnm_tmp_file.record%TYPE;
  nOrdine               cnm_tmp_file.ordine%TYPE;
BEGIN
  SELECT  file_intero
    INTO  sFileIntero
    FROM  cnm_t_file
    WHERE id_stato_file = 3;
  DELETE FROM cnm_tmp_file;
  -------
  sRecord = NULL;
  nOrdine = 1;
  FOR nIdx IN 1..LENGTH(sFileIntero)
  LOOP
    IF ASCII(SUBSTR(sFileIntero,nIdx,1)) = 10 THEN
      INSERT INTO cnm_tmp_file ( record, ordine )
        VALUES ( sRecord, nOrdine );
      sRecord = NULL;
      nOrdine = nOrdine + 1;
    ELSE
      IF sRecord IS NULL THEN
        sRecord = SUBSTR(sFileIntero,nIdx,1);
      ELSE
        sRecord = sRecord || SUBSTR(sFileIntero,nIdx,1);
      END IF;
    END IF;
  END LOOP;
  -------
  IF sRecord IS NOT NULL THEN
    INSERT INTO cnm_tmp_file ( record, ordine )
      VALUES ( sRecord, nOrdine );
  END IF;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
