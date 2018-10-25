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
