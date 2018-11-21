ALTER TABLE teached_subject add CONSTRAINT fk_teached_subject_teacher foreign key(teacher_id) references person(id);
ALTER TABLE teached_subject add CONSTRAINT fk_teached_subject_subject foreign key(subject_id ) references subject(id);
/
