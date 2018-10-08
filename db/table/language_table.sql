CREATE TABLE LANGUAGE (
  id         NUMBER NOT NULL,
  code       VARCHAR2(5) NOT NULL,
  name       VARCHAR2(20) NOT NULL,
  mod_user   VARCHAR2(300) NOT NULL,
  created_on TIMESTAMP(6) NOT NULL,
  last_mod   TIMESTAMP(6) NOT NULL,
  dml_flag   VARCHAR2(1) NOT NULL,
  version    NUMBER NOT NULL
);
/
