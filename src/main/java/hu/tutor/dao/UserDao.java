package hu.tutor.dao;

import java.util.List;

import hu.tutor.model.Subject;
import hu.tutor.model.User;

public interface UserDao {

	public User findUser(Integer id);

	public List<User> getAllUsers();

	public User saveUser(User user);

	public User updateUser(User user);

	public void deleteUser(Integer id);

	public List<Subject> getSubjectsOfTeacher(Integer teacherId);
	
	public User getUserByUserName(String userName);

}
