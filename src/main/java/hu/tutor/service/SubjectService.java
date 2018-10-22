package hu.tutor.service;

import java.util.List;

import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;

public interface SubjectService {
	public Subject getSubjectById(Integer id);

	public List<Subject> getAllSubjects();

	public void saveNewSubject(Subject subject);

	public List<Subject> getSubjectsOfTeacher(Teacher teacher);
}
