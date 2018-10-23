CREATE OR REPLACE TYPE ty_search_result AS OBJECT
(
  id                           NUMBER,
  first_name                   VARCHAR2(40),
  last_name                    VARCHAR2(40),
  country                      VARCHAR2(100),
  city                         VARCHAR2(100),
  street                       VARCHAR2(200),
  house_number                 VARCHAR2(40),
  introduction                 VARCHAR2(4000),
  subject_name                 VARCHAR2(100),
  subject_description          VARCHAR2(4000),
  personal_subject_description VARCHAR2(4000),
  distance                     VARCHAR2(40),

  CONSTRUCTOR FUNCTION ty_search_result RETURN SELF AS RESULT
)
;
/
