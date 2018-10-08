-- adress and history
CREATE OR REPLACE TRIGGER adress_trg
  BEFORE INSERT OR UPDATE ON adress
  FOR EACH ROW
BEGIN
  IF inserting THEN
    IF :new.id IS NULL THEN
      :new.id := adress_sq.nextval;
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

CREATE OR REPLACE TRIGGER adress_hist_trg
  AFTER DELETE OR UPDATE OR INSERT ON adress
  FOR EACH ROW
BEGIN
  IF deleting THEN
    INSERT INTO adress_history
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
    INSERT INTO adress_history
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

-- language and history
CREATE OR REPLACE TRIGGER language_trg
  BEFORE INSERT OR UPDATE ON LANGUAGE
  FOR EACH ROW
BEGIN
  IF inserting THEN
    IF :new.id IS NULL THEN
      :new.id := language_sq.nextval;
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
  IF inserting THEN
    IF :new.id IS NULL THEN
      :new.id := language_sq.nextval;
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

-- person and history
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
       adress_id,
       teacher,
       admin,
       introduction,
       active,
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
       :old.adress_id,
       :old.teacher,
       :old.admin,
       :old.introduction,
       :old.active,
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
       adress_id,
       teacher,
       admin,
       introduction,
       active,
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
       :new.adress_id,
       :new.teacher,
       :new.admin,
       :new.introduction,
       :new.active,
       :new.mod_user,
       :new.created_on,
       :new.last_mod,
       :new.dml_flag,
       :new.version);
  END IF;
END;
/

-- subject and history
CREATE OR REPLACE TRIGGER subject_trg
  BEFORE INSERT OR UPDATE ON subject
  FOR EACH ROW
BEGIN
  IF inserting THEN
    IF :new.id IS NULL THEN
      :new.id := subject_sq.nextval;
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

  IF inserting THEN
    IF :new.id IS NULL THEN
      :new.id := subject_sq.nextval;
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

-- teached subject and history
CREATE OR REPLACE TRIGGER teached_subject_trg
  BEFORE INSERT OR UPDATE ON teached_subject
  FOR EACH ROW
BEGIN
  IF inserting THEN
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

-- translation and history
CREATE OR REPLACE TRIGGER translation_trg
  BEFORE INSERT OR UPDATE ON translation
  FOR EACH ROW
BEGIN
  IF inserting THEN
    IF :new.id IS NULL THEN
      :new.id := translation_sq.nextval;
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
