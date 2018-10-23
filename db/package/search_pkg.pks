-- search_pkg head
CREATE OR REPLACE PACKAGE search_pkg AS
  PROCEDURE search(p_searcher_id  IN NUMBER
                  ,p_subject_name IN VARCHAR2
                  ,p_teacher_name IN VARCHAR2
                  ,p_max_distance IN NUMBER
                  ,p_result_list  OUT SYS_REFCURSOR);
END search_pkg;
/
