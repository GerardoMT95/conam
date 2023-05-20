-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		GRANTS											%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


-- grants per tutto lo schema a conam_rw
GRANT USAGE ON SCHEMA conam TO conam_rw;
GRANT SELECT,INSERT,UPDATE,DELETE ON ALL TABLES IN SCHEMA conam TO conam_rw;
GRANT SELECT,USAGE ON ALL SEQUENCES IN SCHEMA conam TO conam_rw;
