package hu.tutor.service;

import java.util.List;

import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;
import hu.tutor.model.User;
import hu.tutor.util.ActiveParameter;

public interface AdminService {
	public List<Teacher> getTeachersAwaitingValidation();

	public List<Subject> getSubjectsAwaitingValidation();

	public void activatePerson(Integer personId, ActiveParameter active);

	public void enableTeacher(Integer teacherId, ActiveParameter enable);

	public void enableSubject(Integer subjectId, ActiveParameter enable);

	public List<User> getAllUsers();

	public void modifyPassword(Integer userId, String rawPassword);
}
