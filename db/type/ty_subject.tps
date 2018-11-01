CREATE OR REPLACE TYPE ty_subject AS OBJECT
(
  id          NUMBER,
  NAME        VARCHAR2(100),
  description VARCHAR2(4000),
  active      VARCHAR2(1),
  mod_user    VARCHAR2(300),
  created_on  TIMESTAMP(6),
  last_mod    TIMESTAMP(6),
  dml_flag    VARCHAR2(1),
  version     NUMBER,

  CONSTRUCTOR FUNCTION ty_subject RETURN SELF AS RESULT
)
;
/
