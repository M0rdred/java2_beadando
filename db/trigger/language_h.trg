CREATE OR REPLACE TRIGGER language_hist_trg
  AFTER DELETE OR UPDATE OR INSERT ON LANGUAGE
  FOR EACH ROW
BEGIN
  IF deleting THEN
    INSERT INTO language_history
      (id, code, NAME, mod_user, created_on, last_mod, dml_flag, version)
    VALUES
      (:old.id,
       :old.code,
       :old.name,
       USER,
       :old.created_on,
       SYSDATE,
       'D',
       :old.version);
  ELSE
    INSERT INTO language_history
      (id, code, NAME, mod_user, created_on, last_mod, dml_flag, version)
    VALUES
      (:new.id,
       :new.code,
       :new.name,
       :new.mod_user,
       :new.created_on,
       :new.last_mod,
       :new.dml_flag,
       :new.version);
  END IF;
END;
/
