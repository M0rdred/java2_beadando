CREATE OR REPLACE TRIGGER translation_hist_trg
  AFTER DELETE OR UPDATE OR INSERT ON translation
  FOR EACH ROW
BEGIN
  IF deleting THEN
    INSERT INTO translation_history
      (id,
       LANGUAGE,
       text,
       mod_user,
       created_on,
       last_mod,
       dml_flag,
       version)
    VALUES
      (:old.id,
       :old.language,
       :old.text,
       USER,
       :old.created_on,
       SYSDATE,
       'D',
       :old.version);
  ELSE
    INSERT INTO translation_history
      (id,
       LANGUAGE,
       text,
       mod_user,
       created_on,
       last_mod,
       dml_flag,
       version)
    VALUES
      (:new.id,
       :new.language,
       :new.text,
       :new.mod_user,
       :new.created_on,
       :new.last_mod,
       :new.dml_flag,
       :new.version);
  END IF;
END;
/
