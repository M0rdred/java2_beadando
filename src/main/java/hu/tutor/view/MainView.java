package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;

import hu.tutor.service.UserService;

@SpringView(name = MainView.MAIN_VIEW_NAME)
public class MainView extends HorizontalLayout implements View {

	private static final long serialVersionUID = -891813713655196146L;

	public static final String MAIN_VIEW_NAME = "main";

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Override
	public void enter(ViewChangeEvent event) {
		Link registerLink = new Link("Regisztráció", new ExternalResource("#!" + RegistrationView.REGISTER_VIEW_NAME));
		Link loginLink = new Link("Bejelentkezés", new ExternalResource("#!" + LoginView.LOGIN_VIEW_NAME));
		Link searchLink = new Link("Keresés", new ExternalResource("#!" + SearchView.SEARCH_VIEW_NAME));

		this.addComponents(registerLink, loginLink, searchLink);
		this.setMargin(true);
		this.addStyleName("tutor-background");

	}
}
