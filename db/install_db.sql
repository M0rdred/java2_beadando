Prompt Initializing Database

@./sequence/address.seq
@./sequence/language.seq
@./sequence/person.seq
@./sequence/subject.seq
@./sequence/translation.seq

@./table/application_parameters_table.sql
@./table/address_table.sql
@./table/address_table_h.sql
@./table/city_distance_table.sql
@./table/language_table.sql
@./table/language_table_h.sql
@./table/person_table.sql
@./table/person_table_h.sql
@./table/subject_table.sql
@./table/subject_table_h.sql
@./table/teached_subject.sql
@./table/teached_subject_h.sql
@./table/temp_table.sql
@./table/translation_table.sql
@./table/translation_table_h.sql

@./trigger/address_b.trg
@./trigger/address_h.trg
@./trigger/language_b.trg
@./trigger/language_h.trg
@./trigger/person_b.trg
@./trigger/person_h.trg
@./trigger/subject_b.trg
@./trigger/subject_h.trg
@./trigger/teached_subject_b.trg
@./trigger/teached_subject_h.trg
@./trigger/translation_b.trg
@./trigger/translation_h.trg

@./type/ty_search_result.tps
@./type/ty_search_result.tpb
@./type/ty_search_result_table.tps
@./type/ty_subject.tps
@./type/ty_subject.tpb
@./type/ty_subject_table.tps

@./view/subjects_waiting_validation_vw.sql
@./view/teachers_waiting_validation_vw.sql

@./package/admin_pkg.pks
@./package/distance_pkg.pks
@./package/person_pkg.pks
@./package/search_pkg.pks
@./package/teached_subject_pkg.pks
@./package/teacher_pkg.pks

@./package/admin_pkg.pkb
@./package/distance_pkg.pkb
@./package/person_pkg.pkb
@./package/search_pkg.pkb
@./package/teached_subject_pkg.pkb
@./package/teacher_pkg.pkb

@./job/refresh_distance_data.sql

@./data/insert_data.sql

COMMIT;

exit