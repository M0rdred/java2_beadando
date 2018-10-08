CREATE OR REPLACE TRIGGER person_trg
  BEFORE INSERT OR UPDATE ON person
  FOR EACH ROW
BEGIN
  IF inserting THEN
    IF :new.id IS NULL THEN
      :new.id := person_sq.nextval;
    END IF;
  
    :new.created_on := SYSDATE;
    :new.last_mod   := SYSDATE;
    :new.dml_flag   := 'I';
    :new.mod_user   := USER;
    :new.version    := 1;
  ELSIF updating THEN
    :new.last_mod := SYSDATE;
    :new.dml_flag := 'U';
    :new.mod_user := USER;
    :new.version  := :old.version + 1;
  END IF;
END;
/
