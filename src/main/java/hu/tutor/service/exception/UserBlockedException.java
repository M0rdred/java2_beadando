package hu.tutor.service.exception;

public class UserBlockedException extends SecurityException {

	public UserBlockedException() {
		super();
	};

	public UserBlockedException(String message) {
		super(message);
	};

	public UserBlockedException(Throwable cause) {
		super(cause);
	};

	public UserBlockedException(String message, Throwable cause) {
		super(message, cause);
	};

}
