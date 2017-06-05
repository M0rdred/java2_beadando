package hu.tutor.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = LogoutView.LOGOUT_VIEW_NAME)
public class LogoutView extends VerticalLayout implements View {
	protected static final String LOGOUT_VIEW_NAME = "logout";

	@Override
	public void enter(ViewChangeEvent event) {
		getUI().getNavigator().navigateTo(MainView.MAIN_VIEW_NAME);
		invalidateSession();
	}

	private void invalidateSession() {
		// VaadinSession.getCurrent().close();
		VaadinSession.getCurrent().setAttribute("user", null);
	}

}
