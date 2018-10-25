CREATE OR REPLACE PACKAGE BODY teacher_pkg AS
  PROCEDURE save_new_subject_for_teacher(p_teacher_id IN NUMBER
                                        ,p_subject_id IN NUMBER) IS
  BEGIN
    NULL;
  END;

  PROCEDURE delete_subject_from_teacher(p_teacher_id IN NUMBER
                                       ,p_subject_id IN NUMBER) IS
  BEGIN
    NULL;
  END;

  PROCEDURE become_teacher(p_user_id IN NUMBER) IS
  BEGIN
    NULL;
  END;

  PROCEDURE end_teaching(p_teacher_id IN NUMBER) IS
  BEGIN
    NULL;
  END;

  PROCEDURE get_subjects_of_teacher(p_teacher_id   IN NUMBER
                                   ,p_subject_list OUT ty_subject_l) IS
  BEGIN
    NULL;
  END;

END teacher_pkg;
