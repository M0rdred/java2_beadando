package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Binder;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.Address;
import hu.tutor.model.User;
import hu.tutor.service.UserService;
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
	private TextField addressField;
	private TextField cityField;
	private TextField zipField;
	private TextArea introductionField;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public void init(User user) {
		this.user = user;

		this.addComponent(this.getFormLayout());
	}

	private Component getFormLayout() {

		this.user = (User) VaadinSession.getCurrent().getAttribute("user");
		this.address = this.user.getAddress();

		FormLayout formLayout = new FormLayout();

		this.userNameField = new TextField("Felhasználónév:");
		this.userNameField.setReadOnly(true);
		this.lastNameField = new TextField("Vezetéknév:");
		this.firstNameField = new TextField("Keresztnév:");
		this.emailField = new TextField("Email:");
		this.phoneField = new PhoneField("Telefon:");
		this.addressField = new TextField("Cím:");
		this.cityField = new TextField("Város:");
		this.zipField = new TextField("Irányítószám:");
		this.introductionField = new TextArea("Bemutatkozás:");

		this.setUserBinder();
		this.setAddressBinder();

		Button saveButton = new Button();
		saveButton.setCaption("Mentés");
		saveButton.addClickListener(event -> {
			this.user = this.userBinder.getBean();
			this.user.setAddress(this.addressBinder.getBean());

			this.userService.updateUser(this.user);
		});

		formLayout.addComponent(this.userNameField);
		formLayout.addComponent(this.lastNameField);
		formLayout.addComponent(this.firstNameField);
		formLayout.addComponent(this.emailField);
		formLayout.addComponent(this.phoneField);
		formLayout.addComponent(this.addressField);
		formLayout.addComponent(this.cityField);
		formLayout.addComponent(this.zipField);
		formLayout.addComponent(this.introductionField);

		formLayout.setSpacing(true);
		formLayout.setMargin(true);

		if (!this.user.isTeacher()) {
			Button btnBecomeTeacher = new Button();
			btnBecomeTeacher.setCaption("Tanár szeretnék lenni");
			btnBecomeTeacher.addClickListener(event -> this.userService.becomeTeacher(this.user.getId()));
			this.addComponent(btnBecomeTeacher);
		}

		Panel formPanel = new Panel("Személyes adatok");
		formPanel.setContent(formLayout);

		VerticalLayout vertical = new VerticalLayout(formPanel, saveButton);

		this.setSizeFull();

		return vertical;
	}

	private void setAddressBinder() {
		this.addressBinder = new Binder<>();

		this.addressBinder.bind(this.addressField, Address::getStreet, Address::setStreet);
		this.addressBinder.bind(this.cityField, Address::getCity, Address::setCity);
		this.addressBinder.bind(this.zipField, Address::getZip, Address::setZip);

		if (this.address == null) {
			this.addressBinder.setBean(new Address());
		} else {
			this.addressBinder.setBean(this.address);
		}
	}

	private void setUserBinder() {
		this.userBinder = new Binder<>();

		this.userBinder.bind(this.userNameField, User::getUserName, User::setUserName);
		this.userBinder.bind(this.lastNameField, User::getLastName, User::setLastName);
		this.userBinder.bind(this.firstNameField, User::getFirstName, User::setFirstName);
		this.userBinder.bind(this.emailField, User::getEmail, User::setEmail);
		this.userBinder.bind(this.phoneField, User::getPhone, User::setPhone);
		this.userBinder.bind(this.introductionField, User::getIntroduction, User::setIntroduction);

		this.userBinder.setBean(this.user);
	}
}
