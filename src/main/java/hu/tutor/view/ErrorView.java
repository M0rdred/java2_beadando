package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.service.UserService;

@SpringView(name = ErrorView.ERROR_VIEW_NAME)
public class ErrorView extends VerticalLayout implements View {

	private static final long serialVersionUID = -9159887005598675973L;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	protected static final String ERROR_VIEW_NAME = "404";

	@Autowired
	private ApplicationContext ctx;

	@Override
	public void enter(ViewChangeEvent event) {
		this.setSizeFull();

		Label errorLabel = new Label("Sajnos a keresett oldal nem található");
		errorLabel.addStyleName("h1");

		Link mainUiLink = new Link("Vissza a főoldalra", new ExternalResource("#!" + MainView.MAIN_VIEW_NAME));

		this.addComponents(new VerticalLayout(errorLabel, mainUiLink));

		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
	}
}
