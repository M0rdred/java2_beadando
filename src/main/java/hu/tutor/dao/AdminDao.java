package hu.tutor.dao;

import java.util.List;

import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;
import hu.tutor.util.ActiveParameter;

public interface AdminDao {
	public List<Teacher> getTeachersAwaitingValidation();

	public List<Subject> getSubjectsAwaitingValidation();

	public void activatePerson(Integer personId, ActiveParameter active);

	public void enableTeacher(Integer teacherId, ActiveParameter enable);
}
