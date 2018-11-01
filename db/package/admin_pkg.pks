CREATE OR REPLACE PACKAGE admin_pkg AS

  PROCEDURE activate_person(p_person_id IN NUMBER
                           ,p_active    IN VARCHAR2);
  PROCEDURE enable_teacher(p_teacher_id IN NUMBER
                          ,p_enable     IN VARCHAR2);
  PROCEDURE enable_subject(p_subject_id IN NUMBER
                          ,p_enable     IN VARCHAR2);
  PROCEDURE modify_password(p_user_id      IN NUMBER
                           ,p_new_password IN VARCHAR2);

END admin_pkg;
/
