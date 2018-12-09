CREATE OR REPLACE TRIGGER search_query_hist_trg
  AFTER DELETE OR UPDATE OR INSERT ON search_query
  FOR EACH ROW
BEGIN
  IF deleting
  THEN
    INSERT INTO search_query_history
      (id
      ,subject_name
      ,teacher_name
      ,max_distance
      ,owner_id
      ,mod_user
      ,created_on
      ,last_mod
      ,dml_flag
      ,version)
    VALUES
      (:old.id
      ,:old.subject_name
      ,:old.teacher_name
      ,:old.max_distance
      ,:old.owner_id
      ,USER
      ,:old.created_on
      ,SYSDATE
      ,'D'
      ,:old.version);
  ELSE
    INSERT INTO search_query_history
      (id
      ,subject_name
      ,teacher_name
      ,max_distance
      ,owner_id
      ,mod_user
      ,created_on
      ,last_mod
      ,dml_flag
      ,version)
    VALUES
      (:new.id
      ,:new.subject_name
      ,:new.teacher_name
      ,:new.max_distance
      ,:new.owner_id
      ,:new.mod_user
      ,:new.created_on
      ,:new.last_mod
      ,:new.dml_flag
      ,:new.version);
  END IF;
END;
/
