package hu.tutor.dao;

import java.util.List;

import hu.tutor.model.Subject;

public interface SubjectDao {
	public Subject getSubject(Integer subjectId);

	public List<Subject> getAllSubjects();

	public void saveNewSubject(Subject subject);
}
