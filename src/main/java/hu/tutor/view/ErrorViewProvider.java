package hu.tutor.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class ErrorViewProvider implements ViewProvider {

	private static final long serialVersionUID = -2296873226964794310L;

	@Override
	public String getViewName(String viewAndParameters) {
		return ErrorView.ERROR_VIEW_NAME;
	}

	@Override
	public View getView(String viewName) {
		return new ErrorView();
	}

}
