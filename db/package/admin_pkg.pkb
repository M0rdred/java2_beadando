CREATE OR REPLACE PACKAGE BODY admin_pkg AS

  PROCEDURE activate_person(p_person_id IN NUMBER
                           ,p_active    IN VARCHAR2) IS
  BEGIN
  
    IF p_person_id IS NOT NULL
       AND length(p_active) = 1
    THEN
      UPDATE person p SET p.is_active = p_active WHERE p.id = p_person_id;
    ELSE
      raise_application_error(-20001,
                              'Problems occured with the given parameters');
    END IF;
  
  END activate_person;

  PROCEDURE enable_teacher(p_teacher_id IN NUMBER
                          ,p_enable     IN VARCHAR2) IS
  BEGIN
    UPDATE person p SET p.is_teacher = p_enable WHERE p.id = p_teacher_id;
  END enable_teacher;

  PROCEDURE enable_subject(p_subject_id IN NUMBER
                          ,p_enable     IN VARCHAR2) IS
  BEGIN
    UPDATE subject s SET s.active = p_enable WHERE s.id = p_subject_id;
  END enable_subject;

  PROCEDURE modify_password(p_user_id      IN NUMBER
                           ,p_new_password IN VARCHAR2) IS
  BEGIN
  
    UPDATE person p SET p.password = p_new_password WHERE p.id = p_user_id;
  
  END modify_password;

END admin_pkg;
/
