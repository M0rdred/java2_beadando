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

  PROCEDURE enable_teacher(p_teacher_id IN NUMBER) IS
  BEGIN
    NULL;
  END enable_teacher;

END admin_pkg;
/
