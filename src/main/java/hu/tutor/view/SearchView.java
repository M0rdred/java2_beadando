package hu.tutor.view;

import org.springframework.context.annotation.Scope;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.Subject;
import hu.tutor.model.User;

@SpringView(name = SearchView.SEARCH_VIEW_NAME)
@Scope("prototype")
public class SearchView extends VerticalLayout implements View {

	private static final long serialVersionUID = 9132068016374956428L;

	public static final String SEARCH_VIEW_NAME = "search";

	private User user;

	@Override
	public void enter(ViewChangeEvent event) {
		this.user = (User) VaadinSession.getCurrent().getAttribute("user");
		HorizontalLayout linkLayout = new HorizontalLayout(this.createMainPageLink());
		if (this.isAnonymousAccess()) {
			linkLayout.addComponent(this.createAccountLink());
			linkLayout.addComponent(this.createLogoutLink());
		}

		this.addComponents(linkLayout, this.createContent());
		this.addStyleName("tutor-background");
	}

	private Component createMainPageLink() {
		return new Link("Főoldal", new ExternalResource("#!" + MainView.MAIN_VIEW_NAME));
	}

	private Component createAccountLink() {
		return new Link("Fiók", new ExternalResource("#!" + AccountView.ACCOUNT_VIEW_NAME));
	}

	private Component createLogoutLink() {
		return new Link("Kijelentkezés", new ExternalResource("#!" + LogoutView.LOGOUT_VIEW_NAME));
	}

	private Component createContent() {
		return new VerticalLayout(this.createSearchLayout(), this.createResultLayout());
	}

	private Component createSearchLayout() {
		Panel panel = new Panel("Keresés");
		VerticalLayout layout = new VerticalLayout();

		ComboBox<Subject> subjectComboBox = new ComboBox<>("Tantárgy");
		TextField teacherNameField = new TextField("Tanár neve");
		TextField distanceField = new TextField("Maximum távolság");
		Button submitButton = new Button("Keresés");

		submitButton.addClickListener(e -> Notification.show("Nem implementált"));

		HorizontalLayout horizontal = new HorizontalLayout(subjectComboBox, teacherNameField, distanceField);
		horizontal.setSizeFull();

		layout.addComponents(horizontal, submitButton);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();

		panel.setSizeFull();

		panel.setContent(layout);

		return panel;
	}

	private Component createResultLayout() {
		Panel panel = new Panel("Találatok");
		VerticalLayout layout = new VerticalLayout();

		Label publicLabel = new Label("Publikus");
		Label privateLabel = new Label("Privát");

		layout.addComponent(publicLabel);
		if (this.isAnonymousAccess()) {
			layout.addComponent(privateLabel);
		}

		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();

		panel.setSizeFull();
		panel.setContent(layout);

		return panel;
	}

	private boolean isAnonymousAccess() {
		return this.user != null;
	}

}
