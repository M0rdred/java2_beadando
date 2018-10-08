-- subject_pkg body
CREATE OR REPLACE PACKAGE BODY subject_pkg IS

  PROCEDURE new_subject(p_name        VARCHAR2,
                        p_description VARCHAR2) IS
    subject_already_exists_ex EXCEPTION;
    v_subject_count NUMBER;
  BEGIN
    IF p_name IS NOT NULL
       AND length(p_name) > 3
    THEN
      SELECT COUNT(*)
        INTO v_subject_count
        FROM subject s
       WHERE s.name = p_name;
      IF v_subject_count > 0
      THEN
        -- raise exception subject already exists
        RAISE subject_already_exists_ex;
      END IF;
    
      IF p_description IS NOT NULL
         AND length(p_description) > 5
      THEN
        SET TRANSACTION NAME 'new_subject';
        INSERT INTO translation
          (LANGUAGE,
           text)
        VALUES
          (100,
           p_description);
      
        INSERT INTO subject
          (NAME,
           description)
        VALUES
          (p_name,
           translation_sq.currval);
        COMMIT;
      END IF;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  EXCEPTION
    WHEN subject_already_exists_ex THEN
      raise_application_error(-20001, 'Subject name already exists in database.');
  END;

  PROCEDURE modify_subject(p_id          NUMBER,
                           p_active      VARCHAR2,
                           p_description VARCHAR2) IS
  BEGIN
    IF p_id IS NOT NULL
    THEN
      SET TRANSACTION NAME 'modify_subject';
      UPDATE subject s
         SET s.active = p_active
       WHERE s.id = p_id;
      UPDATE translation t
         SET t.text = p_description
       WHERE t.id = (SELECT s.description
                       FROM subject s
                      WHERE s.id = p_id);
      COMMIT;
    END IF;
  END;

  PROCEDURE delete_subject(p_id NUMBER) IS
  BEGIN
    IF p_id IS NOT NULL
    THEN
      UPDATE subject s
         SET s.active = 'N'
       WHERE s.id = p_id;
    END IF;
  END;

END subject_pkg;
/
