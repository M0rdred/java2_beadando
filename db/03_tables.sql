CREATE TABLE PERSON (
  id           NUMBER NOT NULL,
  first_name   VARCHAR2(40) NOT NULL,
  last_name    VARCHAR2(40) NOT NULL,
  birth_date   DATE NOT NULL,
  adress_id    NUMBER NOT NULL,
  teacher      VARCHAR2(1) default 'N',
  admin        VARCHAR2(1) default 'N',
  active       VARCHAR2(1) default 'N',
  introduction NUMBER,
  role		   VARCHAR2(255),
  mod_user     VARCHAR2(300) NOT NULL,
  created_on   TIMESTAMP(6) NOT NULL,
  last_mod     TIMESTAMP(6) NOT NULL,
  dml_flag     VARCHAR2(1) NOT NULL,
  version      NUMBER NOT NULL
);

CREATE TABLE PERSON_HISTORY 
       AS (
   SELECT * FROM PERSON 
   WHERE 1 = 2
);


CREATE TABLE SUBJECT (
  id          NUMBER NOT NULL,
  name        VARCHAR2(100) NOT NULL,
  description NUMBER NOT NULL,
  active      VARCHAR2(1) default 'N' NOT NULL,
  mod_user    VARCHAR2(300) NOT NULL,
  created_on  TIMESTAMP(6) NOT NULL,
  last_mod    TIMESTAMP(6) NOT NULL,
  dml_flag    VARCHAR2(1) NOT NULL,
  version     NUMBER NOT NULL
);


CREATE TABLE SUBJECT_HISTORY 
       AS (
   SELECT * FROM SUBJECT 
   WHERE 1 = 2
);


CREATE TABLE ADRESS (
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


CREATE TABLE ADRESS_HISTORY 
       AS (
   SELECT * FROM ADRESS 
   WHERE 1 = 2
);


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


CREATE TABLE TEACHED_SUBJECT_HISTORY 
       AS (
   SELECT * FROM TEACHED_SUBJECT 
   WHERE 1 = 2
);


CREATE TABLE CITY_DISTANCE (
  city_from VARCHAR2(100) NOT NULL,
  city_to   VARCHAR2(100) NOT NULL,
  distance  NUMBER NOT NULL,
  last_mod  TIMESTAMP(2) NOT NULL
);


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


CREATE TABLE LANGUAGE_HISTORY 
       AS (
   SELECT * FROM LANGUAGE 
   WHERE 1 = 2
);


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


CREATE TABLE TRANSLATION_HISTORY 
       AS (
   SELECT * FROM TRANSLATION 
   WHERE 1 = 2
);


-- temp table for search
CREATE GLOBAL TEMPORARY TABLE TEMP
(
  text CLOB
)
ON COMMIT DELETE ROWS;


