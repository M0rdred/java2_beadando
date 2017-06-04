package hu.tutor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.UserDao;
import hu.tutor.model.User;

@Service("authService")
public class AuthService {

	@Autowired
	private UserDao userDao;

	public boolean isAuthenticUser(String userName, String password) {
		User user = this.userDao.getUserByUserName(userName);
		if (user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

}
