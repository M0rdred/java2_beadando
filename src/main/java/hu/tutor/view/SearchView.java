package hu.tutor.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.SearchResult;
import hu.tutor.model.Subject;
import hu.tutor.model.User;
import hu.tutor.service.SearchService;
import hu.tutor.service.SubjectService;

@SpringView(name = SearchView.SEARCH_VIEW_NAME)
@Scope("prototype")
public class SearchView extends VerticalLayout implements View {

	private static final long serialVersionUID = 9132068016374956428L;

	public static final String SEARCH_VIEW_NAME = "search";

	private User user;
	private Grid<SearchResult> resultGrid;

	@Autowired
	private SearchService searchService;

	@Autowired
	private SubjectService subjectService;

	@Override
	public void enter(ViewChangeEvent event) {
		this.user = (User) VaadinSession.getCurrent().getAttribute("user");
		HorizontalLayout linkLayout = new HorizontalLayout(this.createMainPageLink());
		if (this.isAuthorizedAccess()) {
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

		subjectComboBox.setItems(this.subjectService.getAllSubjects());
		subjectComboBox.setItemCaptionGenerator(Subject::getName);
		subjectComboBox.setWidth("100%");

		teacherNameField.setWidth("80%");

		distanceField.setWidth("80%");

		submitButton.addClickListener(e -> {
			Optional<Subject> optionalSelected = subjectComboBox.getSelectedItem();
			Integer userId = this.user != null ? this.user.getId() : null;
			Integer distance = null;

			try {
				distance = this.convertDistance(distanceField);

				List<SearchResult> results = this.searchService.doSearch(userId,
						optionalSelected.isPresent() ? optionalSelected.get().getName() : "",
						teacherNameField.getValue(), distance);

				if (results == null) {
					results = new ArrayList<>();
				}

				this.resultGrid.setItems(results);

			} catch (ValidationException ex) {
				Notification.show("Nem szám", Type.ERROR_MESSAGE);
			}
		});

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

	private Integer convertDistance(TextField distanceField) {
		String value = distanceField.getValue();
		if (value == null || value.isEmpty()) {
			return null;
		}

		ValidationResult result = new RegexpValidator("Nem szám", "\\d*").apply(value, new ValueContext(distanceField));
		if (result.isError()) {
			throw new ValidationException();
		} else {
			distanceField.setComponentError(null);
			return Integer.valueOf(value);
		}
	}

	private Component createResultLayout() {
		Panel panel = new Panel("Találatok");
		VerticalLayout layout = new VerticalLayout();

		this.resultGrid = new Grid<>();

		this.resultGrid.addColumn(SearchResult::getFullName).setCaption("Név");
		this.resultGrid.addColumn(SearchResult::getIntroduction).setCaption("Bemutatkozás");
		this.resultGrid.addColumn(SearchResult::getPersonalSubjectDescription).setCaption("Személyes tantárgy leírás");

		if (this.isAuthorizedAccess()) {
			this.resultGrid.addColumn(SearchResult::getCity).setCaption("Város");
			this.resultGrid.addColumn(SearchResult::getStreet).setCaption("Utca");
			this.resultGrid.addColumn(SearchResult::getHouseNumber).setCaption("Házszám");
			this.resultGrid.addColumn(SearchResult::getFormattedPhoneNumber).setCaption("Telefonszám");
			this.resultGrid.addColumn(SearchResult::getEmail).setCaption("Email");
			this.resultGrid.addColumn(SearchResult::getDistance).setCaption("Távolság");
		}

		this.resultGrid.setSizeFull();
		layout.addComponent(this.resultGrid);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();

		panel.setSizeFull();
		panel.setContent(layout);

		return panel;
	}

	private boolean isAuthorizedAccess() {
		return this.user != null;
	}

}
