CREATE OR REPLACE TYPE ty_search_result AS OBJECT
(
  id                           NUMBER,
  first_name                   VARCHAR2(40),
  last_name                    VARCHAR2(40),
  country                      VARCHAR2(100),
  zip                          NUMBER,
  city                         VARCHAR2(100),
  street                       VARCHAR2(200),
  house_number                 VARCHAR2(40),
  phone_number                 VARCHAR2(255),
  email                        VARCHAR2(255),
  introduction                 VARCHAR2(4000),
  subject_name                 VARCHAR2(100),
  subject_description          VARCHAR2(4000),
  personal_subject_description VARCHAR2(4000),
  distance                     NUMBER,

  CONSTRUCTOR FUNCTION ty_search_result RETURN SELF AS RESULT
)
;
/
