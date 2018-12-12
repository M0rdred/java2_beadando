-- teached_subject_pkg head
CREATE OR REPLACE PACKAGE teached_subject_pkg AS
  PROCEDURE pick_up_subject(p_subject_id  NUMBER
                           ,p_teacher_id  NUMBER);
  PROCEDURE activate_subject(p_subject_id NUMBER
                            ,p_teacher_id NUMBER
                            ,p_active     VARCHAR2);
  PROCEDURE modify_subject_description(p_subject_id  NUMBER
                                      ,p_teacher_id  NUMBER
                                      ,p_description VARCHAR2);
  PROCEDURE get_subject_description(p_subject_id  NUMBER
                                   ,p_teacher_id  NUMBER
                                   ,p_description OUT VARCHAR2);
END teached_subject_pkg;
/
