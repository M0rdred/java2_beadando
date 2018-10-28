CREATE OR REPLACE VIEW subjects_waiting_validation_vw AS
SELECT s.id
      ,s.name
      ,s.description
      ,s.active
  FROM subject s
 WHERE s.active = 'N';
/
