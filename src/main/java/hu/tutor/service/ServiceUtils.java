package hu.tutor.service;

public class ServiceUtils {
	public static UserService getUserService() {
		return new UserServiceImpl();
	}
}
