package hu.tutor.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = MainView.MAIN_VIEW_NAME)
public class MainView extends VerticalLayout implements View {

	private static final long serialVersionUID = -891813713655196146L;

	public static final String MAIN_VIEW_NAME = "main";

	@Override
	public void enter(ViewChangeEvent event) {
		Link registerLink = new Link("Regisztráció", new ExternalResource("#!" + RegistrationView.REGISTER_VIEW_NAME));
		Link loginLink = new Link("Bejelentkezés", new ExternalResource("#!" + LoginView.LOGIN_VIEW_NAME));
		Link searchLink = new Link("Keresés", new ExternalResource("#!" + SearchView.SEARCH_VIEW_NAME));

		registerLink.addStyleName("white");

		Label welcomeLabel = new Label("Üdvözöljük a magánoktatók apróhirdetési weboldalán!");
		welcomeLabel.addStyleName("h1");

		HorizontalLayout horizontal = new HorizontalLayout(registerLink, loginLink, searchLink);
		horizontal.addStyleName("tutor-menuline");
		horizontal.setComponentAlignment(registerLink, Alignment.MIDDLE_CENTER);
		horizontal.setComponentAlignment(loginLink, Alignment.MIDDLE_CENTER);
		horizontal.setComponentAlignment(searchLink, Alignment.MIDDLE_CENTER);
		horizontal.setSizeFull();

		HorizontalLayout backgroundLayout = new HorizontalLayout();
		backgroundLayout.addStyleName("tutor-background");
		backgroundLayout.setSizeFull();

		this.addComponents(horizontal, welcomeLabel, backgroundLayout);
		this.setExpandRatio(backgroundLayout, 1F);
		this.addStyleName("tutor-main");
		this.getComponent(0).setHeight("30px");

		this.setSizeFull();

		this.setComponentAlignment(welcomeLabel, Alignment.MIDDLE_CENTER);

	}
}
