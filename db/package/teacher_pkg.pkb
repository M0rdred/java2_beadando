CREATE OR REPLACE PACKAGE teacher_pkg AS
  PROCEDURE save_new_subject_for_teacher(p_teacher_id IN NUMBER
                                        ,p_subject_id IN NUMBER);
  PROCEDURE delete_subject_from_teacher(p_teacher_id IN NUMBER
                                       ,p_subject_id IN NUMBER);
  PROCEDURE become_teacher(p_user_id IN NUMBER);
  PROCEDURE end_teaching(p_teacher_id IN NUMBER);
  PROCEDURE get_subjects_of_teacher(p_teacher_id   IN NUMBER
                                   ,p_subject_list OUT SYS_REFCURSOR);
END teacher_pkg;
/
CREATE OR REPLACE PACKAGE BODY teacher_pkg AS
  PROCEDURE save_new_subject_for_teacher(p_teacher_id IN NUMBER
                                        ,p_subject_id IN NUMBER) IS
  BEGIN
  
    INSERT INTO teached_subject
      (subject_id
      ,teacher_id
      ,active)
    VALUES
      (p_subject_id
      ,p_teacher_id
      ,'Y');
  
  END save_new_subject_for_teacher;

  PROCEDURE delete_subject_from_teacher(p_teacher_id IN NUMBER
                                       ,p_subject_id IN NUMBER) IS
  BEGIN
    DELETE teached_subject ts
     WHERE ts.teacher_id = p_teacher_id
       AND ts.subject_id = p_subject_id;
  END delete_subject_from_teacher;

  PROCEDURE become_teacher(p_user_id IN NUMBER) IS
  BEGIN
    UPDATE person p SET p.role = 'teacher' WHERE p.id = p_user_id;
  END become_teacher;

  PROCEDURE end_teaching(p_teacher_id IN NUMBER) IS
  BEGIN
    UPDATE person p
       SET p.role       = 'user'
          ,p.is_teacher = 'N'
     WHERE p.id = p_teacher_id;
  END end_teaching;

  PROCEDURE get_subjects_of_teacher(p_teacher_id   IN NUMBER
                                   ,p_subject_list OUT SYS_REFCURSOR) IS
    v_subject_list ty_teached_subject_table;
  BEGIN
    SELECT ty_teached_subject(id                          => NULL,
                              subject_id                  => t.subject_id,
                              teacher_id                  => t.teacher_id,
                              subject_name                => s.name,
                              subject_description         => s.description,
                              teacher_name                => p.last_name || ' ' ||
                                                             p.first_name,
                              teacher_introduction        => p.introduction,
                              teacher_subject_description => t.description,
                              active                      => t.active)
      BULK COLLECT
      INTO v_subject_list
      FROM teached_subject t
      JOIN subject s
        ON s.id = t.subject_id
      JOIN person p
        ON p.id = t.teacher_id
     WHERE t.teacher_id = p_teacher_id
       AND t.active = 'Y'
       AND p.is_teacher = 'Y';
  
    OPEN p_subject_list FOR
      SELECT * FROM TABLE(CAST(v_subject_list AS ty_subject_table));
  END get_subjects_of_teacher;

END teacher_pkg;
/
