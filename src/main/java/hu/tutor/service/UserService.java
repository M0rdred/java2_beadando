package hu.tutor.service;

import java.util.List;

import hu.tutor.model.User;

public interface UserService {
	public List<User> listUsers();

	public User getUserById(Integer id);

	public User saveUser(User user);

	public User updateUser(User user);

	public void deleteUser(Integer id);
}
