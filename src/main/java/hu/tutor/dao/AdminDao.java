package hu.tutor.dao;

import java.util.List;
import java.util.Set;

import hu.tutor.model.Subject;
import hu.tutor.model.TeachedSubject;
import hu.tutor.model.Teacher;
import hu.tutor.model.User;
import hu.tutor.util.ActiveParameter;

public interface AdminDao {
	public List<Teacher> getTeachersAwaitingValidation();

	public List<Subject> getSubjectsAwaitingValidation();

	public void activatePerson(Integer personId, ActiveParameter active);

	public void enableTeacher(Integer teacherId, ActiveParameter enable);

	public void enableSubject(Integer subjectId, ActiveParameter enable);

	public Set<User> getAllUsers();

	public void modifyPassword(Integer userId, String newPassword);

	public List<TeachedSubject> listTeachedSubjects();

	public void activateTeachedSubject(Integer subjectId, Integer teacherId, ActiveParameter active);
}
