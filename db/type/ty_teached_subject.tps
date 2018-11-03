CREATE OR REPLACE TYPE ty_teached_subject AS OBJECT
(
  id                          NUMBER,
  subject_id                  NUMBER,
  teacher_id                  NUMBER,
  subject_name                VARCHAR2(255),
  subject_description         VARCHAR2(255),
  teacher_name                VARCHAR2(255),
  teacher_introduction        VARCHAR2(255),
  teacher_subject_description VARCHAR2(255),
  active                      VARCHAR2(1),

  CONSTRUCTOR FUNCTION ty_teached_subject RETURN SELF AS RESULT
)
;
/
