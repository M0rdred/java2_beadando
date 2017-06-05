package hu.tutor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.server.VaadinSession;

import hu.tutor.dao.UserDao;
import hu.tutor.model.User;

@Service("authService")
public class AuthService {

	@Autowired
	private UserDao userDao;

	public boolean isAuthenticUser(String userName, String password) {
		User user = this.userDao.getUserByUserName(userName);
		if (user.getPassword().equals(password)) {
			VaadinSession.getCurrent().setAttribute("user", user);
			return true;
		}
		return false;
	}

	public boolean checkIfUserLoggedIn() {
		if (VaadinSession.getCurrent().getAttribute("user") != null) {
			return true;
		} else {
			return false;
		}
	}

}
