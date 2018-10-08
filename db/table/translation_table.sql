CREATE TABLE TRANSLATION (
  id         NUMBER NOT NULL,
  language   NUMBER NOT NULL,
  text       VARCHAR2(4000) NOT NULL,
  mod_user   VARCHAR2(300) NOT NULL,
  created_on TIMESTAMP(6) NOT NULL,
  last_mod   TIMESTAMP(6) NOT NULL,
  dml_flag   VARCHAR2(1) NOT NULL,
  version    NUMBER NOT NULL
);
/
