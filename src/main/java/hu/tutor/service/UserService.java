package hu.tutor.service;

import java.util.List;

import hu.tutor.model.Subject;
import hu.tutor.model.User;

public interface UserService {
	public List<User> listUsers();

	public User getUserById(Integer id);

	public User getUserByUserName(String userName);

	public User saveUser(User user);

	public User updateUser(User user);

	public void deleteUser(Integer id);

	public void saveSubjectOfTeacher(Subject subject);

	public void saveNewSubjectForTeacher(Integer teacherId, Integer subjectId);

	public void deleteSubjectFromTeacher(Integer teacherId, Integer subjectId);
}
