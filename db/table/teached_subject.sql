CREATE TABLE TEACHED_SUBJECT (
  subject_id  NUMBER NOT NULL,
  teacher_id  NUMBER NOT NULL,
  description NUMBER NOT NULL,
  active      VARCHAR2(1) default 'N' NOT NULL,
  mod_user    VARCHAR2(300) NOT NULL,
  created_on  TIMESTAMP(6) NOT NULL,
  last_mod    TIMESTAMP(6) NOT NULL,
  dml_flag    VARCHAR2(1) NOT NULL,
  version     NUMBER NOT NULL
);
/
