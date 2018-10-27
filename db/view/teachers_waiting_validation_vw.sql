CREATE OR REPLACE VIEW teachers_waiting_validation_vw AS
  SELECT *
    FROM person p
   WHERE p.role = 'teacher'
     AND p.is_teacher = 'N';
/
