package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.Address;
import hu.tutor.model.User;
import hu.tutor.model.search.SearchQuery;
import hu.tutor.service.SearchService;
import hu.tutor.service.TeacherService;
import hu.tutor.service.UserService;
import hu.tutor.util.VaadinUtil;
import hu.tutor.view.component.PhoneField;

@SpringComponent
@Scope(scopeName = "prototype")
public class UserAccountForm extends VerticalLayout {

	private static final long serialVersionUID = 7214911148324254712L;

	private User user;
	private Address address;

	private Binder<User> userBinder;
	private Binder<Address> addressBinder;

	private TextField userNameField;
	private TextField lastNameField;
	private TextField firstNameField;
	private TextField emailField;
	private PhoneField phoneField;
	private TextField countryField;
	private TextField zipField;
	private TextField cityField;
	private TextField streetField;
	private TextField houseNumberField;
	private TextArea introductionField;
	private Grid<SearchQuery> queryGrid;

	@Autowired
	private UserService userService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SearchService searchService;

	public void init(User user) {
		this.user = user;

		this.addComponent(this.getFormLayout());
	}

	private Component getFormLayout() {
		VerticalLayout vertical = new VerticalLayout();
		this.user = (User) VaadinSession.getCurrent().getAttribute("user");

		if (this.user == null) {
			return vertical;
		}

		this.address = this.user.getAddress();

		FormLayout formLayout = new FormLayout();

		this.userNameField = new TextField("Felhasználónév:");
		this.userNameField.setReadOnly(true);
		this.lastNameField = new TextField("Vezetéknév:");
		this.firstNameField = new TextField("Keresztnév:");
		this.emailField = new TextField("Email:");
		this.phoneField = new PhoneField("Telefon:");
		this.countryField = new TextField("Ország:");
		this.zipField = new TextField("Irányítószám:");
		this.cityField = new TextField("Város:");
		this.streetField = new TextField("Cím:");
		this.houseNumberField = new TextField("Házszám:");
		this.introductionField = new TextArea("Bemutatkozás:");

		this.setUserBinder();
		this.setAddressBinder();

		Button saveButton = new Button();
		saveButton.setCaption("Mentés");
		saveButton.addClickListener(event -> {
			BinderValidationStatus<User> userValidation = this.userBinder.validate();
			BinderValidationStatus<Address> addressValidation = this.addressBinder.validate();

			if (userValidation.isOk() && addressValidation.isOk()) {
				this.user = this.userBinder.getBean();
				this.user.setAddress(this.addressBinder.getBean());

				this.userService.updateUser(this.user);
				Notification.show("Adatok sikeresen mentve");
			}
		});
		saveButton.addStyleName(VaadinUtil.THEME_BUTTON_STYLE);

		formLayout.addComponent(this.userNameField);
		formLayout.addComponent(this.lastNameField);
		formLayout.addComponent(this.firstNameField);
		formLayout.addComponent(this.emailField);
		formLayout.addComponent(this.phoneField);
		formLayout.addComponent(this.countryField);
		formLayout.addComponent(this.zipField);
		formLayout.addComponent(this.cityField);
		formLayout.addComponent(this.streetField);
		formLayout.addComponent(this.houseNumberField);
		formLayout.addComponent(this.introductionField);

		formLayout.setSpacing(true);
		formLayout.setMargin(true);

		if (!this.user.getIsTeacher()) {
			Button btnBecomeTeacher = new Button();
			btnBecomeTeacher.setCaption("Tanár szeretnék lenni");
			btnBecomeTeacher.addClickListener(event -> this.teacherService.becomeTeacher(this.user.getId()));
			btnBecomeTeacher.addStyleName(VaadinUtil.THEME_BUTTON_STYLE);
			this.addComponent(btnBecomeTeacher);
		}

		Panel formPanel = new Panel("Személyes adatok");
		formPanel.setContent(formLayout);

		this.queryGrid = new Grid<>();
		this.queryGrid.addComponentColumn(this::createExecuteButton).setCaption("Futtatás");
		this.queryGrid.addComponentColumn(this::createDeleteButton).setCaption("Törlés");
		this.queryGrid.addColumn(SearchQuery::getSubjectName).setCaption("Tantárgy neve");
		this.queryGrid.addColumn(SearchQuery::getTeacherName).setCaption("Tanár neve");
		this.queryGrid.addColumn(SearchQuery::getMaxDistance).setCaption("Maximum távolság");

		this.refreshQueryGrid();

		this.queryGrid.setSizeFull();

		HorizontalLayout queryLayout = new HorizontalLayout(this.queryGrid);
		queryLayout.setSizeFull();
		Panel searchQueriesPanel = new Panel("Mentett keresések");
		searchQueriesPanel.addStyleName("saved-queries-panel");
		searchQueriesPanel.setContent(queryLayout);
		searchQueriesPanel.setSizeFull();
		HorizontalLayout horizontal = new HorizontalLayout(formPanel, searchQueriesPanel);
		horizontal.setSizeFull();

		vertical.addComponents(horizontal, saveButton);

		this.setSizeFull();

		return vertical;
	}

	private Button createDeleteButton(SearchQuery query) {
		Button deleteButton = new Button(VaadinIcons.CLOSE_CIRCLE_O);
		deleteButton.addClickListener(e -> {
			this.user.removeSeachQuery(query);
			this.userService.updateUser(this.user);

			this.refreshQueryGrid();
		});
		deleteButton.addStyleName(VaadinUtil.BORDERED_BUTTON_STYLE);

		return deleteButton;
	}

	private Button createExecuteButton(SearchQuery query) {
		Button execButton = new Button(VaadinIcons.SEARCH);

		execButton.addClickListener(e -> {
			UI.getCurrent().getSession().setAttribute(VaadinUtil.VAADIN_SESSION_QUERY_NAME, query);
			UI.getCurrent().getNavigator().navigateTo(SearchView.SEARCH_VIEW_NAME);
		});
		execButton.addStyleName(VaadinUtil.THEME_BUTTON_STYLE);

		return execButton;
	}

	private void setAddressBinder() {
		this.addressBinder = new Binder<>();

		this.addressBinder.forField(this.countryField)
				.withValidator(
						new StringLengthValidator("Az ország nevének legalább 3 karakteresnek kell lennie", 3, 100))
				.bind(Address::getCountry, Address::setCountry);
		this.addressBinder.forField(this.houseNumberField)
				.withValidator(new StringLengthValidator("A házszám nem lehet üres", 1, 100))
				.bind(Address::getHouseNumber, Address::setHouseNumber);
		this.addressBinder.forField(this.streetField)
				.withValidator(new StringLengthValidator("Az utcanév nem lehet üres", 1, 100))
				.bind(Address::getStreet, Address::setStreet);
		this.addressBinder.forField(this.cityField)
				.withValidator(new StringLengthValidator("A város neve nem lehet üres", 1, 100))
				.bind(Address::getCity, Address::setCity);
		this.addressBinder.forField(this.zipField)
				.withValidator(new RegexpValidator("Nem megfelelő irányítószám", "[1-9]\\d{3}", true))
				.bind(Address::getZip, Address::setZip);

		if (this.address == null) {
			this.addressBinder.setBean(new Address());
		} else {
			this.addressBinder.setBean(this.address);
		}
	}

	private void setUserBinder() {
		this.userBinder = new Binder<>();

		this.userBinder.bind(this.userNameField, User::getUserName, User::setUserName);
		this.userBinder.forField(this.lastNameField)
				.withValidator(
						new StringLengthValidator("A vezetéknévnek legalább 3 karakteresnek kell lennie.", 3, 100))
				.bind(User::getLastName, User::setLastName);
		this.userBinder.forField(this.firstNameField)
				.withValidator(
						new StringLengthValidator("A keresztnévnek legalább 3 karakteresnek kell lennie.", 3, 100))
				.bind(User::getFirstName, User::setFirstName);
		this.userBinder.forField(this.emailField).withValidator(new EmailValidator("Nem helyes email cím."))
				.bind(User::getEmail, User::setEmail);
		this.userBinder.forField(this.phoneField)
				.withValidator(new StringLengthValidator("Helytelen telefonszám", 11, 12))
				.bind(User::getPhone, User::setPhone);
		this.userBinder.forField(this.introductionField).bind(User::getIntroduction, User::setIntroduction);

		this.userBinder.setBean(this.user);
	}

	private void refreshQueryGrid() {
		this.user = this.userService.getUserById(this.user.getId());
		this.queryGrid.setItems(this.user.getQueries());
	}
}
