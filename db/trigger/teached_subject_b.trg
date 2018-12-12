CREATE OR REPLACE TRIGGER teached_subject_trg
  BEFORE INSERT OR UPDATE ON teached_subject
  FOR EACH ROW
BEGIN
  IF inserting
  THEN
    :new.id         := teached_subject_sq.nextval;
    :new.created_on := SYSDATE;
    :new.last_mod   := SYSDATE;
    :new.dml_flag   := 'I';
    :new.mod_user   := USER;
    :new.version    := 1;
  ELSIF updating
  THEN
    :new.last_mod := SYSDATE;
    :new.dml_flag := 'U';
    :new.mod_user := USER;
    :new.version  := :old.version + 1;
  END IF;
END;
/
