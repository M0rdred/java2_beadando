package hu.tutor.view.error;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class ErrorViewProvider implements ViewProvider {

	private static final long serialVersionUID = -2296873226964794310L;

	@Override
	public String getViewName(String viewAndParameters) {
		if (viewAndParameters.contains(NotFoundView.NOT_FOUND_VIEW_NAME)) {
			return NotFoundView.NOT_FOUND_VIEW_NAME;
		} else if (viewAndParameters.contains(AccessDeniedView.ACCESS_DENIED_VIEW_NAME)) {
			return AccessDeniedView.ACCESS_DENIED_VIEW_NAME;
		}
		return null;
	}

	@Override
	public View getView(String viewName) {
		switch (viewName) {
		case NotFoundView.NOT_FOUND_VIEW_NAME:
			return new NotFoundView();

		case AccessDeniedView.ACCESS_DENIED_VIEW_NAME:
			return new AccessDeniedView();

		default:
			throw new RuntimeException("Ismeretlen view: " + viewName);
		}
	}

}
