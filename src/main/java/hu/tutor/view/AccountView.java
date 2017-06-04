package hu.tutor.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

import hu.tutor.model.User;

public class AccountView extends HorizontalLayout implements View {

	protected static final String ACCOUNT_VIEW_NAME = "account";
	private User user;

	@Override
	public void enter(ViewChangeEvent event) {

		Link logoutLink = new Link("Kijelentkezés", new ExternalResource("#!logout"));

		user = (User) VaadinSession.getCurrent().getAttribute("user");
		Label label = new Label(user.getFirstName());
		addComponent(label);
	}

}
