package hu.tutor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vaadin.server.VaadinSession;

import hu.tutor.dao.UserDao;
import hu.tutor.model.User;

@Service("authService")
public class AuthService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public boolean isAuthenticUser(String userName, String password) {
		User user = this.userDao.getUserByUserName(userName);

		if (user == null) {
			return false;
		}

		if (this.passwordEncoder.matches(password, user.getPassword())) {
			VaadinSession.getCurrent().setAttribute("user", user);
			return true;
		}
		return false;
	}

	public boolean checkIfUserLoggedIn() {
		return VaadinSession.getCurrent().getAttribute("user") != null;
	}

}
