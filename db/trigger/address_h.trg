CREATE OR REPLACE TRIGGER address_hist_trg
  AFTER DELETE OR UPDATE OR INSERT ON address
  FOR EACH ROW
BEGIN
  IF deleting THEN
    INSERT INTO address_history
      (id,
       country,
       city,
       street,
       house_number,
       mod_user,
       created_on,
       last_mod,
       dml_flag,
       version)
    VALUES
      (:old.id,
       :old.country,
       :old.city,
       :old.street,
       :old.house_number,
       USER,
       :old.created_on,
       SYSDATE,
       'D',
       :old.version);
  ELSE
    INSERT INTO address_history
      (id,
       country,
       city,
       street,
       house_number,
       mod_user,
       created_on,
       last_mod,
       dml_flag,
       version)
    VALUES
      (:new.id,
       :new.country,
       :new.city,
       :new.street,
       :new.house_number,
       :new.mod_user,
       :new.created_on,
       :new.last_mod,
       :new.dml_flag,
       :new.version);
  END IF;
END;
/
