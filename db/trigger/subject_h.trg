CREATE OR REPLACE TRIGGER subject_hist_trg
  AFTER DELETE OR UPDATE OR INSERT ON subject
  FOR EACH ROW
BEGIN
  IF deleting THEN
    INSERT INTO subject_history
      (id,
       NAME,
       description,
       active,
       mod_user,
       created_on,
       last_mod,
       dml_flag,
       version)
    VALUES
      (:old.id,
       :old.name,
       :old.description,
       :old.active,
       USER,
       :old.created_on,
       SYSDATE,
       'D',
       :old.version);
  ELSE
    INSERT INTO subject_history
      (id,
       NAME,
       description,
       active,
       mod_user,
       created_on,
       last_mod,
       dml_flag,
       version)
    VALUES
      (:new.id,
       :new.name,
       :new.description,
       :new.active,
       :new.mod_user,
       :new.created_on,
       :new.last_mod,
       :new.dml_flag,
       :new.version);
  END IF;
END;
/
