package hu.tutor.service;

import java.util.List;

import hu.tutor.model.Subject;

public interface SubjectService {
	public Subject getSubjectById(Integer id);

	public List<Subject> getAllSubjects();

	public void saveNewSubject(Subject subject);

	public void modifySubject(Subject subject);

	public void deleteSubject(Integer subjectId);

}
