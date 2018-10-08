-- subject_pkg head
CREATE OR REPLACE PACKAGE subject_pkg IS
  PROCEDURE new_subject(p_name        VARCHAR2,
                        p_description VARCHAR2);
  PROCEDURE modify_subject(p_id          NUMBER,
                           p_active      VARCHAR2,
                           p_description VARCHAR2);
  PROCEDURE delete_subject(p_id NUMBER);

END subject_pkg;
/
