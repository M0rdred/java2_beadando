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

  PROCEDURE get_all_teached_subjects(p_teached_subjects OUT SYS_REFCURSOR) IS
    v_results ty_teached_subject_table;
  BEGIN
  
    SELECT ty_teached_subject(id                          => ts.id,
                              subject_id                  => ts.subject_id,
                              teacher_id                  => ts.teacher_id,
                              subject_name                => s.name,
                              subject_description         => s.description,
                              teacher_name                => p.last_name || ' ' ||
                                                             p.first_name,
                              teacher_introduction        => p.introduction,
                              teacher_subject_description => ts.description,
                              active                      => ts.active)
      BULK COLLECT
      INTO v_results
      FROM teached_subject ts
      JOIN subject s
        ON s.id = ts.subject_id
      JOIN person p
        ON p.id = ts.teacher_id;
  
    OPEN p_teached_subjects FOR
      SELECT * FROM TABLE(CAST(v_results AS ty_teached_subject_table));
  
  END get_all_teached_subjects;

  PROCEDURE activate_teached_subject(p_subject_id IN NUMBER
                                    ,p_teacher_id IN NUMBER
                                    ,p_active     IN VARCHAR2) IS
  BEGIN
  
    UPDATE teached_subject t
       SET t.active = p_active
     WHERE t.subject_id = p_subject_id
       AND t.teacher_id = p_teacher_id;
  
  END activate_teached_subject;

END admin_pkg;
/
