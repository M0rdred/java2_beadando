CREATE OR REPLACE TRIGGER teached_subject_hist_trg
  AFTER INSERT OR UPDATE ON teached_subject
  FOR EACH ROW
BEGIN
  IF deleting THEN
    INSERT INTO teached_subject_history
      (subject_id,
       teacher_id,
       description,
       active,
       mod_user,
       created_on,
       last_mod,
       dml_flag,
       version)
    VALUES
      (:old.subject_id,
       :old.teacher_id,
       :old.description,
       :old.active,
       USER,
       :old.created_on,
       SYSDATE,
       'D',
       :old.version);
  ELSE
    INSERT INTO teached_subject_history
      (subject_id,
       teacher_id,
       description,
       active,
       mod_user,
       created_on,
       last_mod,
       dml_flag,
       version)
    VALUES
      (:new.subject_id,
       :new.teacher_id,
       :new.description,
       :new.active,
       USER,
       :new.created_on,
       SYSDATE,
       'D',
       :new.version);
  END IF;
END;
/
