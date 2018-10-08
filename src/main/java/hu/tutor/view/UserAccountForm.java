package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Binder;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.Address;
import hu.tutor.model.User;
import hu.tutor.service.UserService;
import hu.tutor.view.component.PhoneField;

@SpringComponent
@Scope(scopeName = "prototype")
public class UserAccountForm extends HorizontalLayout {

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

		GridLayout dataGrid = new GridLayout();

		dataGrid.setColumns(2);
		dataGrid.setRows(9);

		dataGrid.setCaption("Személyes adatok");

		Label userNameLabel = new Label("Felhasználónév:");
		this.userNameField = new TextField();
		this.userNameField.setReadOnly(true);
		Label lastNameLabel = new Label("Vezetéknév:");
		this.lastNameField = new TextField();
		Label firstNameLabel = new Label("Keresztnév:");
		this.firstNameField = new TextField();
		Label emailLabel = new Label("Email:");
		this.emailField = new TextField();
		Label phoneLabel = new Label("Telefon:");
		this.phoneField = new PhoneField();
		Label addressLabel = new Label("Cím:");
		this.addressField = new TextField();
		Label cityLabel = new Label("Város:");
		this.cityField = new TextField();
		Label zipLabel = new Label("Irányítószám:");
		this.zipField = new TextField();
		Label introductionLabel = new Label("Bemutatkozás:");
		this.introductionField = new TextArea();

		this.setUserBinder();
		this.setAddressBinder();
		/*
		 * userNameField.setValue(user.getUserName());
		 * lastNameField.setValue(user.getLastName());
		 * firstNameField.setValue(user.getFirstName());
		 * emailField.setValue(user.getEmail()); phoneField.setValue(user.getPhone());
		 * addressField.setValue(user.getAddress()); cityField.setValue(user.getCity());
		 * zipField.setValue(user.getZip());
		 * introductionField.setValue(user.getIntroduction());
		 */
		Button saveButton = new Button();
		saveButton.setCaption("Mentés");
		saveButton.addClickListener(event -> {
			/*
			 * user.setFirstName(firstNameField.getValue());
			 * user.setLastName(lastNameField.getValue());
			 * user.setEmail(emailField.getValue()); user.setPhone(phoneField.getValue());
			 * user.setAddress(addressField.getValue()); user.setCity(cityField.getValue());
			 * user.setZip(zipField.getValue());
			 * user.setIntroduction(introductionField.getValue());
			 */
			this.user = this.userBinder.getBean();
			this.user.setAddress(this.addressBinder.getBean());

			this.userService.updateUser(this.user);
		});

		dataGrid.addComponent(userNameLabel);
		dataGrid.addComponent(this.userNameField);
		dataGrid.addComponent(lastNameLabel);
		dataGrid.addComponent(this.lastNameField);
		dataGrid.addComponent(firstNameLabel);
		dataGrid.addComponent(this.firstNameField);
		dataGrid.addComponent(emailLabel);
		dataGrid.addComponent(this.emailField);
		dataGrid.addComponent(phoneLabel);
		dataGrid.addComponent(this.phoneField);
		dataGrid.addComponent(addressLabel);
		dataGrid.addComponent(this.addressField);
		dataGrid.addComponent(cityLabel);
		dataGrid.addComponent(this.cityField);
		dataGrid.addComponent(zipLabel);
		dataGrid.addComponent(this.zipField);
		dataGrid.addComponent(introductionLabel);
		dataGrid.addComponent(this.introductionField);

		dataGrid.setSpacing(true);

		if (!this.user.isTeacher()) {
			Button btnBecomeTeacher = new Button();
			btnBecomeTeacher.setCaption("Tanár szeretnék lenni");
			btnBecomeTeacher.addClickListener(event -> this.userService.becomeTeacher(this.user.getId()));
			this.addComponent(btnBecomeTeacher);
		}

		return new VerticalLayout(dataGrid, saveButton);
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
