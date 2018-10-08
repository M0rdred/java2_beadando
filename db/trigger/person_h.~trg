CREATE OR REPLACE TRIGGER person_hist_trg
  AFTER DELETE OR UPDATE OR INSERT ON person
  FOR EACH ROW
BEGIN
  IF deleting THEN
    INSERT INTO person_history
      (id,
       first_name,
       last_name,
       birth_date,
       address_id,
       is_teacher,
       is_admin,
       introduction,
       is_active,
       mod_user,
       created_on,
       last_mod,
       dml_flag,
       version)
    VALUES
      (:old.id,
       :old.first_name,
       :old.last_name,
       :old.birth_date,
       :old.address_id,
       :old.is_teacher,
       :old.is_admin,
       :old.introduction,
       :old.is_active,
       USER,
       :old.created_on,
       SYSDATE,
       'D',
       :old.version);
  ELSE
    INSERT INTO person_history
      (id,
       first_name,
       last_name,
       birth_date,
       address_id,
       is_teacher,
       is_admin,
       introduction,
       is_active,
       mod_user,
       created_on,
       last_mod,
       dml_flag,
       version)
    VALUES
      (:new.id,
       :new.first_name,
       :new.last_name,
       :new.birth_date,
       :new.address_id,
       :new.is_teacher,
       :new.is_admin,
       :new.introduction,
       :new.is_active,
       :new.mod_user,
       :new.created_on,
       :new.last_mod,
       :new.dml_flag,
       :new.version);
  END IF;
END;
/
