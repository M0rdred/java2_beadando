package hu.tutor.view.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.service.UserService;
import hu.tutor.view.MainView;

@SpringView(name = NotFoundView.NOT_FOUND_VIEW_NAME)
public class NotFoundView extends VerticalLayout implements View {

	private static final long serialVersionUID = -9159887005598675973L;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public static final String NOT_FOUND_VIEW_NAME = "404";

	@Override
	public void enter(ViewChangeEvent event) {
		this.setSizeFull();

		Label errorLabel = new Label("Sajnos a keresett oldal nem található");
		errorLabel.addStyleName("h1");

		Link mainUiLink = new Link("Vissza a főoldalra", new ExternalResource("#!" + MainView.MAIN_VIEW_NAME));

		this.addComponents(new VerticalLayout(errorLabel, mainUiLink));

		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		this.addStyleName("tutor-background");
	}
}
