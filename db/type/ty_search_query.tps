CREATE OR REPLACE TYPE search_query AS OBJECT
(
  id           NUMBER,
  subject_name VARCHAR2(255),
  eacher_name  VARCHAR2(255),
  max_distance NUMBER,

  CONSTRUCTOR FUNCTION search_query RETURN SELF AS RESULT
)
;
/
