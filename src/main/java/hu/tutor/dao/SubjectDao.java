package hu.tutor.dao;

import java.util.List;

import hu.tutor.model.Subject;

public interface SubjectDao {
	public Subject getSubject(Integer subjectId);

	public List<Subject> getAllSubjects(boolean onlyActive);

	public void saveNewSubject(Subject subject);

	public void modifySubject(Subject subject);

	public void deleteSubject(Integer subjectId);
}
