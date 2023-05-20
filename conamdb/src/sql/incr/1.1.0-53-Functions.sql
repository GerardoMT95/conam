CREATE OR REPLACE FUNCTION DT_GetCodBelfioreNazione ( pCodIstatNazione  IN  CHARACTER VARYING
                                                    , pDataRiferimento  IN  DATE
                                                    ) RETURNS CHARACTER VARYING AS
$BODY$
DECLARE
  sCodBelfiore  cnm_d_nazione.cod_belfiore_nazione%TYPE;
BEGIN
  SELECT  assoc.codice_belfiore
    INTO STRICT sCodBelfiore
    FROM  dt_assoc_esteri_ministero_div   assoc
        , dt_stati_esteri_ministero_div   minist
    WHERE assoc.id_stato_ministero = minist.id_stato_ministero
      AND assoc.istat_stato = pCodIstatNazione
      AND COALESCE(pDataRiferimento,TO_DATE('31/12/9999','DD/MM/YYYY')) BETWEEN minist.d_start AND COALESCE(minist.d_stop,TO_DATE('31/12/9999','DD/MM/YYYY'));
  ------------
  RETURN sCodBelfiore;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    SELECT  cod_belfiore
      INTO  sCodBelfiore
      FROM  ( SELECT  assoc.codice_belfiore   cod_belfiore
                FROM  dt_assoc_esteri_ministero_div   assoc
                    , dt_stati_esteri_ministero_div   minist
                WHERE assoc.id_stato_ministero = minist.id_stato_ministero
                  AND assoc.istat_stato = pCodIstatNazione
                ORDER BY  COALESCE(minist.d_stop,TO_DATE('31/12/9999','DD/MM/YYYY'))
            )     t
      LIMIT 1;
  RETURN sCodBelfiore;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


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
    ELSIF ASCII(sCarattere) = 216 THEN
      IF ASCII(SUBSTR(pDenomComune,i+1,1)) = 169
          AND ASCII(SUBSTR(pDenomComune,i+2,1)) = 226
          AND ASCII(SUBSTR(pDenomComune,i+3,1)) = 8364
          AND ASCII(SUBSTR(pDenomComune,i+4,1)) = 166   THEN
        sDenomComune = sDenomComune || 'E''';
      END IF;
    ELSIF ASCII(sCarattere) = 1577 THEN
      IF ASCII(SUBSTR(pDenomComune,i+1,1)) = 8230 THEN
        sDenomComune = sDenomComune || 'E''';
      END IF;
    ELSIF ASCII(sCarattere) = 63 THEN
        sDenomComune = sDenomComune || 'A';
    ELSIF ASCII(sCarattere) = 1600 THEN
      sDenomComune = sDenomComune || 'U';
    ELSIF ASCII(sCarattere) = 8222 THEN
      sDenomComune = sDenomComune || 'D';
    ELSIF ASCII(sCarattere) IN (169,226,8364,166,8222,8230) THEN
      NULL;
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
FUNCTION AggiornaNazione  ( pIdStato            IN  NUMERIC
                          , pIdStatoPrev        IN  NUMERIC
                          , pIdStatoNext        IN  NUMERIC
                          , pCodIstatNazione    IN  CHARACTER VARYING
                          , pCodBelfioreNazione IN  CHARACTER VARYING
                          , pDenomNazione       IN  CHARACTER VARYING
                          , pInizioValidita     IN  DATE
                          , pFineValidita       IN  DATE
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
        , cod_belfiore_nazione = pCodBelfioreNazione
        , denom_nazione = sDenomNazione
        , inizio_validita = pInizioValidita
        , fine_validita = pFineValidita
        , dt_id_stato = pIdStato
        , dt_id_stato_prev = pIdStatoPrev
        , dt_id_stato_next = pIdStatoNext
      WHERE dt_id_stato = pIdStato
        AND id_origine = 1;
    GET DIAGNOSTICS nRecs = ROW_COUNT;
    IF nRecs = 0 THEN
      UPDATE  cnm_s_nazione
        SET cod_istat_nazione = pCodIstatNazione
          , cod_belfiore_nazione = pCodBelfioreNazione
          , denom_nazione = sDenomNazione
          , inizio_validita = pInizioValidita
          , fine_validita = pFineValidita
          , dt_id_stato = pIdStato
          , dt_id_stato_prev = pIdStatoPrev
          , dt_id_stato_next = pIdStatoNext
        WHERE dt_id_stato = pIdStato
          AND id_origine = 1;
      --------
      GET DIAGNOSTICS nRecs = ROW_COUNT;
      IF nRecs = 0 THEN
        INSERT INTO cnm_d_nazione
                    ( id_nazione
                    , cod_istat_nazione
                    , cod_belfiore_nazione
                    , denom_nazione
                    , inizio_validita
                    , fine_validita
                    , dt_id_stato
                    , dt_id_stato_prev
                    , dt_id_stato_next
                    , id_origine
                    )
          VALUES  ( GetNextIdNazione()
                  , pCodIstatNazione
                  , pCodBelfioreNazione
                  , sDenomNazione
                  , pInizioValidita
                  , pFineValidita
                  , pIdStato
                  , pIdStatoPrev
                  , pIdStatoNext
                  , 1
                  );
      END IF;
    END IF;
  ELSE
    SELECT  COUNT(1)
      INTO  nRecs
      FROM  cnm_d_nazione
      WHERE dt_id_stato = pIdStatoPrev
        AND id_origine = 1
        AND cod_istat_nazione = pCodIstatNazione
        AND COALESCE(cod_belfiore_nazione,' ') = COALESCE(pCodBelfioreNazione,' ')
        AND denom_nazione = sDenomNazione
        AND inizio_validita = pInizioValidita
        AND COALESCE(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = COALESCE(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
    IF nRecs = 0 THEN
      -- Ha un precedente che quindi vado a spostare nello storico
      INSERT INTO cnm_s_nazione
                  ( id_s_nazione
                  , id_nazione
                  , cod_istat_nazione
                  , cod_belfiore_nazione
                  , denom_nazione
                  , inizio_validita
                  , fine_validita
                  , dt_id_stato
                  , dt_id_stato_prev
                  , dt_id_stato_next
                  , id_origine
                  )
        SELECT  GetNextIdSnazione()
              , id_nazione
              , cod_istat_nazione
              , cod_belfiore_nazione
              , denom_nazione
              , inizio_validita
              , fine_validita
              , dt_id_stato
              , dt_id_stato_prev
              , dt_id_stato_next
              , 1
          FROM  cnm_d_nazione
          WHERE dt_id_stato = pIdStatoPrev;
      -- Aggiorno i dati nell'attuale
      UPDATE  cnm_d_nazione
        SET cod_istat_nazione = pCodIstatNazione
          , cod_belfiore_nazione = pCodBelfioreNazione
          , denom_nazione = sDenomNazione
          , inizio_validita = pInizioValidita
          , fine_validita = pFineValidita
          , dt_id_stato = pIdStato
          , dt_id_stato_prev = pIdStatoPrev
          , dt_id_stato_next = pIdStatoNext
        WHERE dt_id_stato = pIdStatoPrev
          AND id_origine = 1;
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
  rec           RECORD;
  nRetCode      SMALLINT;
  sCodBelfiore  cnm_d_nazione.cod_belfiore_nazione%TYPE;
BEGIN
  FOR rec IN  SELECT  id_stato
                    , id_stato_prev
                    , id_stato_next
                    , istat_stato
                    , desc_stato
                    , d_start
                    , d_stop
                FROM  dt_stati_esteri_istat_div
                ORDER BY  id_stato
  LOOP
    sCodBelfiore = DT_GetCodBelfioreNazione(rec.istat_stato, rec.d_stop);
    nRetCode = AggiornaNazione( rec.id_stato, rec.id_stato_prev, rec.id_stato_next, rec.istat_stato, sCodBelfiore, rec.desc_stato, rec.d_start, rec.d_stop);
  END LOOP;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



CREATE OR REPLACE
FUNCTION AggiornaNazioneMinistero ( pIdStato            IN  NUMERIC
                                  , pCodBelfioreNazione IN  CHARACTER VARYING
                                  , pDenomNazione       IN  CHARACTER VARYING
                                  , pInizioValidita     IN  DATE
                                  , pFineValidita       IN  DATE
                                  ) RETURNS SMALLINT AS
$BODY$
DECLARE
  nRecs           cnm_d_nazione.id_nazione%TYPE;
  sDenomNazione   cnm_d_nazione.denom_nazione%TYPE;
BEGIN
  sDenomNazione = PulisciDenominazione(pDenomNazione);
  ------
  UPDATE  cnm_d_nazione
    SET cod_belfiore_nazione = pCodBelfioreNazione
      , denom_nazione = sDenomNazione
      , inizio_validita = pInizioValidita
      , fine_validita = pFineValidita
    WHERE dt_id_stato = pIdStato
      AND id_origine = 2;
    GET DIAGNOSTICS nRecs = ROW_COUNT;
  IF nRecs = 0 THEN
    UPDATE  cnm_s_nazione
      SET cod_belfiore_nazione = pCodBelfioreNazione
        , denom_nazione = sDenomNazione
        , inizio_validita = pInizioValidita
        , fine_validita = pFineValidita
      WHERE dt_id_stato = pIdStato
        AND id_origine = 2;
    --------
    GET DIAGNOSTICS nRecs = ROW_COUNT;
    IF nRecs = 0 THEN
      INSERT INTO cnm_d_nazione
                  ( id_nazione
                  , cod_belfiore_nazione
                  , denom_nazione
                  , inizio_validita
                  , fine_validita
                  , dt_id_stato
                  , id_origine
                  )
        VALUES  ( GetNextIdNazione()
                , pCodBelfioreNazione
                , sDenomNazione
                , pInizioValidita
                , pFineValidita
                , pIdStato
                , 2
                );
    END IF;
  END IF;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



CREATE OR REPLACE
FUNCTION AggiornaNazioniMinistero()
  RETURNS SMALLINT AS
$BODY$
DECLARE
  rec           RECORD;
  nRetCode      SMALLINT;
  sCodBelfiore  cnm_d_nazione.cod_belfiore_nazione%TYPE;
BEGIN
  FOR rec IN  SELECT  id_stato_ministero
                    , codice
                    , territorio
                    , d_start
                    , d_stop
                FROM  dt_stati_esteri_ministero_div
                WHERE codice NOT IN ( SELECT  codice_belfiore
                                        FROM  dt_assoc_esteri_ministero_div
                                    )
                ORDER BY  id_stato_ministero
  LOOP
    nRetCode = AggiornaNazioneMinistero( rec.id_stato_ministero, rec.codice, rec.territorio, rec.d_start, rec.d_stop);
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
        AND COALESCE(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = COALESCE(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
    IF nRecs = 0 THEN
      SELECT  COUNT(1)
        INTO  nRecs
        FROM  cnm_s_regione
        WHERE cod_regione = pCodRegione
          AND denom_regione = sDenomRegione
          AND id_nazione = pIdNazione
          AND inizio_validita = pInizioValidita
          AND COALESCE(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = COALESCE(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
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
  FOR rec IN  SELECT  cod_istat_regione                                                                               cod_istat_regione
                    , denom_regione                                                                                   denom_regione
                    , cod_istat_nazione                                                                               cod_istat_nazione
                    , inizio_validita                                                                                 inizio_validita
                    , CASE WHEN fine_validita = TO_DATE('31/12/9999','DD/MM/YYYY') THEN NULL ELSE fine_validita END   fine_validita
                FROM  ( SELECT  istat_regione                                             cod_istat_regione
                              , desc_regione                                              denom_regione
                              , cod_stato                                                 cod_istat_nazione
                              , MIN(d_start)                                              inizio_validita
                              , MAX(COALESCE(d_stop,TO_DATE('31/12/9999','DD/MM/YYYY')))  fine_validita
                          FROM  dt_limiti_amministrativi_div
                          GROUP BY  istat_regione
                                  , desc_regione
                                  , cod_stato
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
  sDenomProvincia = PulisciDenominazione(pDenomProvincia);
  ------
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
        AND COALESCE(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = COALESCE(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
    IF nRecs = 0 THEN
      SELECT  COUNT(1)
        INTO  nRecs
        FROM  cnm_s_provincia
        WHERE cod_provincia = pCodProvincia
          AND denom_provincia = sDenomProvincia
          AND sigla_provincia = pSiglaProvincia
          AND id_regione = pIdRegione
          AND inizio_validita = pInizioValidita
          AND COALESCE(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = COALESCE(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
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
  FOR rec IN  SELECT  cod_istat_provincia                                                                             cod_istat_provincia
                    , TRANSLATE(denom_provincia,'╔','E')                                                              denom_provincia
                    , sigla_provincia                                                                                 sigla_provincia
                    , cod_istat_regione                                                                               cod_istat_regione
                    , inizio_validita                                                                                 inizio_validita
                    , CASE WHEN fine_validita = TO_DATE('31/12/9999','DD/MM/YYYY') THEN NULL ELSE fine_validita END   fine_validita
                FROM  ( SELECT  istat_provincia                                           cod_istat_provincia
                              , desc_provincia                                            denom_provincia
                              , sigla_prov                                                sigla_provincia
                              , istat_regione                                             cod_istat_regione
                              , MIN(d_start)                                              inizio_validita
                              , MAX(COALESCE(d_stop,TO_DATE('31/12/9999','DD/MM/YYYY')))  fine_validita
                          FROM  dt_limiti_amministrativi_div
                          GROUP BY  istat_provincia
                                  , desc_provincia
                                  , sigla_prov
                                  , istat_regione
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
            AND COALESCE(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = COALESCE(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
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
              AND COALESCE(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = COALESCE(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
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
  FOR rec IN  SELECT  id_comune
                    , id_comune_prev
                    , id_comune_next
                    , istat_comune
                    , cod_catasto
                    , desc_comune
                    , istat_provincia
                    , d_start
                    , d_stop
                FROM  dt_limiti_amministrativi_div
                WHERE r_status = '0'
                ORDER BY  d_start
  LOOP
    nRetCode = AggiornaComune( rec.id_comune, rec.id_comune_prev, rec.id_comune_next, rec.istat_comune, rec.cod_catasto, rec.desc_comune, GetIdProvincia(rec.istat_provincia), rec.d_start, rec.d_stop);
  END LOOP;
  -- Poi elaboro i modificati
  FOR rec IN  SELECT  id_comune
                    , id_comune_prev
                    , id_comune_next
                    , istat_comune
                    , cod_catasto
                    , desc_comune
                    , istat_provincia
                    , d_start
                    , d_stop
                FROM  dt_limiti_amministrativi_div
                WHERE r_status = '2'
                ORDER BY  d_start
  LOOP
    nRetCode = AggiornaComune( rec.id_comune, rec.id_comune_prev, rec.id_comune_next, rec.istat_comune, rec.cod_catasto, rec.desc_comune, GetIdProvincia(rec.istat_provincia), rec.d_start, rec.d_stop);
  END LOOP;
  -- Poi elaboro gli attivi
  FOR rec IN  SELECT  id_comune
                    , id_comune_prev
                    , id_comune_next
                    , istat_comune
                    , cod_catasto
                    , desc_comune
                    , istat_provincia
                    , d_start
                    , d_stop
                FROM  dt_limiti_amministrativi_div
                WHERE r_status = '1'
                ORDER BY  d_start
  LOOP
    nRetCode = AggiornaComune( rec.id_comune, rec.id_comune_prev, rec.id_comune_next, rec.istat_comune, rec.cod_catasto, rec.desc_comune, GetIdProvincia(rec.istat_provincia), rec.d_start, rec.d_stop);
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



CREATE OR REPLACE
FUNCTION GeiIdRecord  ( pCodicePartita  VARCHAR
                      ) RETURNS INTEGER AS
$BODY$
DECLARE
  nIdRecord   cnm_t_record.id_record%TYPE;
BEGIN
  SELECT  id_record
    INTO  nIdRecord
    FROM  cnm_t_record
    WHERE codice_partita = pCodicePartita
      AND id_tipo_record = 1;
  -------
  RETURN nIdRecord;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION geiidrecord(character varying)
  OWNER TO conam;



CREATE OR REPLACE
FUNCTION ScompattaRecord  ( pRecord                 IN  VARCHAR
                          , pIdTipoRecord           OUT NUMERIC
                          , pCodicePartita          OUT VARCHAR
                          , pCodiceFiscale          OUT VARCHAR
                          , pCodEnteCreditore       OUT NUMERIC
                          , pCodEntrata             OUT VARCHAR
                          , pTipoCodEntrata         OUT VARCHAR
                          , pImportoCarico          OUT NUMERIC
                          , pChiaveInfoDaAnnullare  OUT VARCHAR
                          , pChiaveInfoCorrettiva   OUT VARCHAR
                          , pTipoEvento             OUT VARCHAR
                          ) AS
$BODY$
DECLARE
BEGIN
  pIdTipoRecord = NULL;
  pCodiceFiscale = NULL;
  pCodEnteCreditore = NULL;
  pCodEntrata = NULL;
  pTipoCodEntrata = NULL;
  pImportoCarico = NULL;
  pChiaveInfoDaAnnullare = NULL;
  pChiaveInfoCorrettiva = NULL;
  pTipoEvento = NULL;
  IF SUBSTR(pRecord,1,3) = 'FR1' THEN
    pIdTipoRecord = 4;
    pCodicePartita = TRIM(SUBSTR(pRecord,29,14));
    pCodiceFiscale = TRIM(SUBSTR(pRecord,156,16));
    pCodEnteCreditore = TO_NUMBER(SUBSTR(pRecord,14,5));
    pCodEntrata = TRIM(SUBSTR(pRecord,151,4));
    pTipoCodEntrata = TRIM(SUBSTR(pRecord,155,1));
  ELSIF SUBSTR(pRecord,1,3) = 'FR2' THEN
    pIdTipoRecord = 5;
    pCodicePartita = TRIM(SUBSTR(pRecord,29,14));
    pCodiceFiscale = TRIM(SUBSTR(pRecord,189,16));
    pCodEnteCreditore = TO_NUMBER(SUBSTR(pRecord,14,5));
    pCodEntrata = TRIM(SUBSTR(pRecord,184,4));
    pTipoCodEntrata = TRIM(SUBSTR(pRecord,188,1));
  ELSIF SUBSTR(pRecord,1,3) = 'FR3' THEN
    pIdTipoRecord = 6;
    pCodicePartita = TRIM(SUBSTR(pRecord,29,14));
    pCodiceFiscale = TRIM(SUBSTR(pRecord,156,16));
    pCodEnteCreditore = TO_NUMBER(SUBSTR(pRecord,14,5));
    pCodEntrata = TRIM(SUBSTR(pRecord,151,4));
    pTipoCodEntrata = TRIM(SUBSTR(pRecord,155,1));
    pImportoCarico = TO_NUMBER(SUBSTR(pRecord,180,15));
  ELSIF SUBSTR(pRecord,1,3) = 'FR4' THEN
    pIdTipoRecord = 7;
    pCodicePartita = TRIM(SUBSTR(pRecord,29,14));
    pCodEnteCreditore = TO_NUMBER(SUBSTR(pRecord,14,5));
    pCodEntrata = TRIM(SUBSTR(pRecord,151,4));
    pTipoCodEntrata = TRIM(SUBSTR(pRecord,155,1));
    pImportoCarico = TO_NUMBER(SUBSTR(pRecord,171,15));
  ELSIF SUBSTR(pRecord,1,3) = 'FR7' THEN
    pIdTipoRecord = 8;
    pCodicePartita = TRIM(SUBSTR(pRecord,29,14));
    pCodEnteCreditore = TO_NUMBER(SUBSTR(pRecord,14,5));
    pChiaveInfoDaAnnullare = TRIM(SUBSTR(pRecord,19,153));
    pChiaveInfoCorrettiva = TRIM(SUBSTR(pRecord,172,153));
    pTipoEvento = TRIM(SUBSTR(pRecord,325,1));
  END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



CREATE OR REPLACE
FUNCTION RitornoSoris ( pNomeFile IN  VARCHAR
                      ) RETURNS SMALLINT AS
$BODY$
DECLARE
  rec             RECORD;
  campi           RECORD;
  nIdFileRitorno  cnm_t_file_ritorno.id_file_ritorno%TYPE;
  nIdRecord       cnm_t_record.id_record%TYPE;
BEGIN
  INSERT INTO cnm_t_file_ritorno ( nome_file )
    VALUES ( pNomeFile )
    RETURNING id_file_ritorno INTO nIdFileRitorno;
  ---------
  FOR rec IN  SELECT  record
                FROM  cnm_tmp_file
                ORDER BY  id_tmp_file
  LOOP
    SELECT  *
      INTO  campi
      FROM  ScompattaRecord(rec.record);
    nIdRecord = GeiIdRecord(campi.pCodicePartita);
    INSERT INTO cnm_t_record_ritorno
                ( id_file_ritorno
                , record
                , id_record
                , id_tipo_record
                , codice_fiscale
                , cod_ente_creditore
                , cod_entrata
                , tipo_cod_entrata
                , importo_carico
                , chiave_info_da_annullare
                , chiave_info_correttiva
                , tipo_evento
                , id_stato_record
                )
      VALUES  ( nIdFileRitorno
              , rec.record
              , nIdRecord
              , campi.pIdTipoRecord
              , campi.pCodiceFiscale
              , campi.pCodEnteCreditore
              , campi.pCodEntrata
              , campi.pTipoCodEntrata
              , campi.pImportoCarico
              , campi.pChiaveInfoDaAnnullare
              , campi.pChiaveInfoCorrettiva
              , campi.pTipoEvento
              , CASE WHEN nIdRecord IS NULL THEN NULL ELSE 1 END
              );
  END LOOP;
  -------
  RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
