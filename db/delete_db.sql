Prompt Deleting Database

DROP TRIGGER address_hist_trg;
DROP TRIGGER address_trg;
DROP TRIGGER language_hist_trg;
DROP TRIGGER language_trg;
DROP TRIGGER person_hist_trg;
DROP TRIGGER person_trg;
DROP TRIGGER subject_hist_trg;
DROP TRIGGER subject_trg;
DROP TRIGGER teached_subject_hist_trg;
DROP TRIGGER teached_subject_trg;
DROP TRIGGER translation_hist_trg;
DROP TRIGGER translation_trg;

DROP SEQUENCE address_sq;
DROP SEQUENCE language_sq;
DROP SEQUENCE person_sq;
DROP SEQUENCE subject_sq;
DROP SEQUENCE translation_sq;

DROP PACKAGE address_pkg;
DROP PACKAGE distance_pkg;
DROP PACKAGE person_pkg;
DROP PACKAGE search_pkg;
DROP PACKAGE subject_pkg;
DROP PACKAGE teached_subject_pkg;

DROP VIEW subjects_waiting_validation_vw;

DROP TABLE address;
DROP TABLE address_history;
DROP TABLE application_parameters;
DROP TABLE city_distance;
DROP TABLE language;
DROP TABLE language_history;
DROP TABLE person;
DROP TABLE person_history;
DROP TABLE subject;
DROP TABLE subject_history;
DROP TABLE teached_subject;
DROP TABLE teached_subject_history;
DROP TABLE temp;
DROP TABLE translation;
DROP TABLE translation_history;

DROP TYPE ty_search_result_table;
DROP TYPE ty_search_result;
DROP TYPE ty_subject_table;
DROP TYPE ty_subject;

BEGIN
dbms_scheduler.drop_job(job_name => 'refresh_distance_data');
END;
/