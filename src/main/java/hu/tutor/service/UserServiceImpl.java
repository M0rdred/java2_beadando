package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hu.tutor.dao.UserDaoImpl;
import hu.tutor.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDaoImpl userDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<User> listUsers() {
		try {
			return this.userDao.getAllActiveUsers();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public User getUserById(Integer id) {
		try {
			return this.userDao.findUser(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public User saveUser(User user) {
		try {
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));

			return this.userDao.saveUser(user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public User updateUser(User user) {
		try {
			return this.userDao.updateUser(user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void deleteUser(Integer id) {
		try {
			this.userDao.deleteUser(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public User loadUserByUserName(String userName) {
		try {
			return this.userDao.getUserByUserName(userName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

}
