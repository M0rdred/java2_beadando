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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(Integer id) {
		return this.userDao.findUser(id);
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub

	}

}
