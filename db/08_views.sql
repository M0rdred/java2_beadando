CREATE OR REPLACE VIEW subjects_waiting_validation_vw AS
SELECT s.id, s.name, l.name language, t.text, s.active
  FROM subject s
  JOIN translation t
    ON t.id = s.description
    JOIN language l
    ON l.id = t.language
 WHERE s.active = 'N';
/
