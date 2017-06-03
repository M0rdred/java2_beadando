package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.UserDaoHibernateImpl;
import hu.tutor.model.User;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDaoHibernateImpl userDao;

	@Override
	public List<User> listUsers() {
		return this.userDao.getAllUsers();
	}

	@Override
	public User getUserById(Integer id) {
		return this.userDao.findUser(id);
	}

	@Override
	public User saveUser(User user) {
		return this.userDao.saveUser(user);
	}

	@Override
	public User updateUser(User user) {
		return this.userDao.updateUser(user);
	}

	@Override
	public void deleteUser(Integer id) {
		this.userDao.deleteUser(id);
	}

}
