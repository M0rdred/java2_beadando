CREATE TABLE address (
  id           NUMBER NOT NULL,
  country      VARCHAR2(100) NOT NULL,
  city         VARCHAR2(100) NOT NULL,
  street       VARCHAR2(200) NOT NULL,
  house_number VARCHAR2(40) NOT NULL,
  mod_user     VARCHAR2(300) NOT NULL,
  created_on   TIMESTAMP(6) NOT NULL,
  last_mod     TIMESTAMP(6) NOT NULL,
  dml_flag     VARCHAR2(1) NOT NULL,
  version      NUMBER NOT NULL
);
/
