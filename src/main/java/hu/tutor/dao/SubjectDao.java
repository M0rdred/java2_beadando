package hu.tutor.dao;

import java.util.List;

import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;

public interface SubjectDao {
	public Subject getSubject(Integer subjectId);

	public List<Subject> getAllSubjects();

	public void saveNewSubject(Subject subject);

	public List<Subject> getSubjectsOfTeacher(Teacher teacher);
}
