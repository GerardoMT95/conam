-- Scompatto
DELETE FROM dt_stati_esteri_istat_div;
DELETE FROM dt_stati_esteri_ministero_div;
DELETE FROM dt_assoc_esteri_ministero_div;
DELETE FROM dt_limiti_amministrativi_div;

INSERT INTO dt_stati_esteri_istat_div
            ( id_stato
            , id_stato_prev
            , id_stato_next
            , istat_stato
            , desc_stato
            , d_start
            , d_stop
            , r_status
            , cod_continente
            , desc_continente
            )
  SELECT  TO_NUMBER(SUBSTR(record,4,20),'99999999999999999999')
        , TO_NUMBER(SUBSTR(record,44,20),'99999999999999999999')
        , TO_NUMBER(SUBSTR(record,24,20),'99999999999999999999')
        , TRIM(SUBSTR(record,1,3))
        , TRIM(SUBSTR(record,84,100))
        , TO_DATE(SUBSTR(record,64,10),'DD/MM/YYYY')
        , CASE WHEN SUBSTR(record,74,10) = '00/00/0000' THEN NULL ELSE TO_DATE(SUBSTR(record,74,10),'DD/MM/YYYY') END
        , TRIM(SUBSTR(record,212,1))
        , TRIM(SUBSTR(record,184,3))
        , TRIM(SUBSTR(record,187,25))
    FROM  dt_stati_esteri_istat;
  INSERT INTO dt_stati_esteri_ministero_div
              ( id_stato_ministero
              , codice
              , stato
              , territorio
              , d_start
              , d_stop
              , r_status
              , continente
              )
    SELECT  TO_NUMBER(SUBSTR(record,1,20),'99999999999999999999')
          , TRIM(SUBSTR(record,144,4))
          , TRIM(SUBSTR(record,44,50))
          , TRIM(SUBSTR(record,94,50))
          , TO_DATE(SUBSTR(record,148,10),'DD/MM/YYYY')
          , CASE WHEN SUBSTR(record,158,10) = '00/00/0000' THEN NULL ELSE TO_DATE(SUBSTR(record,158,10),'DD/MM/YYYY') END
          , TRIM(SUBSTR(record,302,1))
          , TRIM(SUBSTR(record,21,23))
      FROM  dt_stati_esteri_ministero;
  INSERT INTO dt_assoc_esteri_ministero_div
              ( id_stato_istat
              , id_stato_ministero
              , istat_stato
              , codice_belfiore
              )
    SELECT  TO_NUMBER(SUBSTR(record,1,20),'99999999999999999999')
          , TO_NUMBER(SUBSTR(record,24,20),'99999999999999999999')
          , SUBSTR(record,21,3)
          , TRIM(SUBSTR(record,44,4))
      FROM  dt_assoc_esteri_ministero;
  INSERT INTO dt_limiti_amministrativi_div
              ( id_comune
              , id_comune_prev
              , id_comune_next
              , istat_comune
              , cod_catasto
              , desc_comune
              , cap
              , d_start
              , d_stop
              , r_status
              , istat_provincia
              , desc_provincia
              , sigla_prov
              , istat_regione
              , desc_regione
              , cod_stato
              , desc_stato
              , altitudine
              , superficie_hm2
              , istat_zona_altimetrica
              , desc_zona_altimetrica
              )
    SELECT  TO_NUMBER(SUBSTR(record,1,20),'99999999999999999999')
          , TO_NUMBER(SUBSTR(record,41,20),'99999999999999999999')
          , TO_NUMBER(SUBSTR(record,21,20),'99999999999999999999')
          , TRIM(SUBSTR(record,86,6))
          , TRIM(SUBSTR(record,82,4))
          , TRIM(SUBSTR(record,92,62))
          , TRIM(SUBSTR(record,154,5))
          , TO_DATE(SUBSTR(record,61,10),'DD/MM/YYYY')
          , CASE WHEN SUBSTR(record,71,10) = '00/00/0000' THEN NULL ELSE TO_DATE(SUBSTR(record,71,10),'DD/MM/YYYY') END
          , TRIM(SUBSTR(record,81,1))
          , TRIM(SUBSTR(record,199,3))
          , TRIM(SUBSTR(record,202,100))
          , TRIM(SUBSTR(record,302,4))
          , TRIM(SUBSTR(record,507,2))
          , TRIM(SUBSTR(record,509,100))
          , TRIM(SUBSTR(record,609,3))
          , TRIM(SUBSTR(record,612,64))
          , TO_NUMBER(SUBSTR(record,159,20),'99999999999999999999')
          , TO_NUMBER(SUBSTR(record,179,20),'99999999999999999999')
          , TRIM(SUBSTR(record,306,1))
          , TRIM(SUBSTR(record,307,200))
      FROM  dt_limiti_amministrativi;

-- Carico nelle tabelle definitive
SELECT AggiornaNazioni();
SELECT AggiornaNazioniMinistero();
SELECT AggiornaRegioni();
SELECT AggiornaProvince();
SELECT AggiornaComuni();
