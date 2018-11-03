CREATE OR REPLACE PACKAGE admin_pkg AS

  PROCEDURE activate_person(p_person_id IN NUMBER
                           ,p_active    IN VARCHAR2);
  PROCEDURE enable_teacher(p_teacher_id IN NUMBER
                          ,p_enable     IN VARCHAR2);
  PROCEDURE enable_subject(p_subject_id IN NUMBER
                          ,p_enable     IN VARCHAR2);
  PROCEDURE modify_password(p_user_id      IN NUMBER
                           ,p_new_password IN VARCHAR2);
  PROCEDURE get_all_teached_subjects(p_time             IN NUMBER
                                    ,p_teached_subjects OUT SYS_REFCURSOR);
  PROCEDURE activate_teached_subject(p_subject_id IN NUMBER
                                    ,p_teacher_id IN NUMBER
                                    ,p_active     IN VARCHAR2);

END admin_pkg;
/
