alter table cnm_t_notifica drop constraint ak_t_notifica_01;

-- Da eseguire qualora esiste l'indice
--drop index ak_t_notifica_01;

alter table cnm_t_notifica alter column numero_raccomandata drop not null;