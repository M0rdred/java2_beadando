-- teached_subject_pkg body
CREATE OR REPLACE PACKAGE BODY teached_subject_pkg AS
  PROCEDURE pick_up_subject(p_subject_id  NUMBER
                           ,p_teacher_id  NUMBER
                           ,p_description VARCHAR2) IS
    l_count NUMBER;
  BEGIN
    IF p_subject_id IS NOT NULL
       AND p_teacher_id IS NOT NULL
       AND p_description IS NOT NULL
    THEN
    
      SELECT COUNT(*)
        INTO l_count
        FROM teached_subject ts
       WHERE ts.subject_id = p_subject_id
         AND ts.teacher_id = p_teacher_id;
    
      IF l_count > 0
      THEN
        raise_application_error(-20001,
                                'The given person has already teaching this subject');
      ELSE
      
        INSERT INTO teached_subject
          (subject_id
          ,teacher_id
          ,description)
        VALUES
          (p_subject_id
          ,p_teacher_id
          ,p_description);
      END IF;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

  PROCEDURE activate_subject(p_subject_id NUMBER
                            ,p_teacher_id NUMBER
                            ,p_active     VARCHAR2) IS
  BEGIN
    IF p_subject_id IS NOT NULL
       AND p_teacher_id IS NOT NULL
       AND p_active IS NOT NULL
    THEN
      UPDATE teached_subject ts
         SET ts.active = p_active
       WHERE ts.subject_id = p_subject_id
         AND ts.teacher_id = p_teacher_id;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

  PROCEDURE modify_subject_description(p_subject_id  NUMBER
                                      ,p_teacher_id  NUMBER
                                      ,p_description VARCHAR2) IS
  BEGIN
    IF p_subject_id IS NOT NULL
       AND p_teacher_id IS NOT NULL
       AND p_description IS NOT NULL
    THEN
      UPDATE teached_subject ts
         SET ts.description = p_description
       WHERE ts.subject_id = p_subject_id
         AND ts.teacher_id = p_teacher_id;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;
END teached_subject_pkg;
/
