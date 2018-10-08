-- search_pkg head
CREATE OR REPLACE PACKAGE search_pkg AS
  FUNCTION search(p_searcher_id  NUMBER,
                  p_subject_name VARCHAR2,
                  p_teacher_name VARCHAR2,
                  p_max_distance NUMBER) RETURN ty_search_result_table;
END search_pkg;
/
