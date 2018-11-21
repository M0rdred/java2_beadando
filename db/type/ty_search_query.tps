CREATE OR REPLACE TYPE ty_search_query AS OBJECT
(
  id           NUMBER,
  subject_name VARCHAR2(255),
  eacher_name  VARCHAR2(255),
  max_distance NUMBER,

  CONSTRUCTOR FUNCTION ty_search_query RETURN SELF AS RESULT
)
;
/
