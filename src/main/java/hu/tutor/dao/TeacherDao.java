package hu.tutor.dao;

import java.util.List;

import hu.tutor.model.Subject;

public interface TeacherDao {
	public void saveNewSubjectForTeacher(Integer teacherId, Integer subjectId);

	public void deleteSubjectFromTeacher(Integer teacherId, Integer subjectId);

	public void becomeTeacher(Integer userId);

	public void endTeaching(Integer teacherId);

	public List<Subject> getSubjectsOfTeacher(Integer teacherId);
}
