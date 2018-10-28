CREATE TABLE PERSON (
  id           NUMBER NOT NULL,
  first_name   VARCHAR2(40) NOT NULL,
  last_name    VARCHAR2(40) NOT NULL,
  birth_date   DATE NOT NULL,
  address_id   NUMBER,
  is_teacher   VARCHAR2(1) default 'N',
  is_admin     VARCHAR2(1) default 'N',
  is_active    VARCHAR2(1) default 'N',
  introduction VARCHAR2(4000),
  role		   VARCHAR2(255),
  user_name	   VARCHAR2(255),
  phone		   VARCHAR2(255),
  email 	   VARCHAR2(255),
  password	   VARCHAR2(255),
  mod_user     VARCHAR2(300) NOT NULL,
  created_on   TIMESTAMP(6) NOT NULL,
  last_mod     TIMESTAMP(6) NOT NULL,
  dml_flag     VARCHAR2(1) NOT NULL,
  version      NUMBER NOT NULL
);
/
