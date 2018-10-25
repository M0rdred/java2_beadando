package hu.tutor.service;

import java.util.List;

import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;

public interface AdminService {
	public List<Teacher> getTeachersAwaitingValidation();

	public List<Subject> getSubjectsAwaitingValidation();

	public void activatePerson(Integer personId);

	public void enableTeacher(Integer teacherId);
}
