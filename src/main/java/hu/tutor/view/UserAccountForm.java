package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Binder;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
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

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public UserAccountForm() {

		this.user = (User) VaadinSession.getCurrent().getAttribute("user");
		this.address = this.user.getAddress();

		GridLayout dataGrid = new GridLayout();

		dataGrid.setColumns(2);
		dataGrid.setRows(9);

		dataGrid.setCaption("Személyes adatok");

		Label userNameLabel = new Label("Felhasználónév:");
		TextField userNameField = new TextField();
		userNameField.setReadOnly(true);
		Label lastNameLabel = new Label("Vezetéknév:");
		TextField lastNameField = new TextField();
		Label firstNameLabel = new Label("Keresztnév:");
		TextField firstNameField = new TextField();
		Label emailLabel = new Label("Email:");
		TextField emailField = new TextField();
		Label phoneLabel = new Label("Telefon:");
		PhoneField phoneField = new PhoneField();
		Label addressLabel = new Label("Cím:");
		TextField addressField = new TextField();
		Label cityLabel = new Label("Város:");
		TextField cityField = new TextField();
		Label zipLabel = new Label("Irányítószám:");
		TextField zipField = new TextField();
		Label introductionLabel = new Label("Bemutatkozás:");
		TextArea introductionField = new TextArea();

		this.userBinder = new Binder<>();
		this.addressBinder = new Binder<>();

		this.userBinder.bind(userNameField, User::getUserName, User::setUserName);
		this.userBinder.bind(lastNameField, User::getLastName, User::setLastName);
		this.userBinder.bind(firstNameField, User::getFirstName, User::setFirstName);
		this.userBinder.bind(emailField, User::getEmail, User::setEmail);
		this.userBinder.bind(phoneField, User::getPhone, User::setPhone);
		this.userBinder.bind(introductionField, User::getIntroduction, User::setIntroduction);

		this.addressBinder.bind(addressField, Address::getStreet, Address::setStreet);
		this.addressBinder.bind(cityField, Address::getCity, Address::setCity);
		this.addressBinder.bind(zipField, Address::getZip, Address::setZip);
		this.addressBinder.bind(addressField, Address::getStreet, Address::setStreet);

		this.userBinder.setBean(this.user);
		this.addressBinder.setBean(this.address);
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
			this.userService.updateUser(this.user);
		});

		dataGrid.addComponent(userNameLabel);
		dataGrid.addComponent(userNameField);
		dataGrid.addComponent(lastNameLabel);
		dataGrid.addComponent(lastNameField);
		dataGrid.addComponent(firstNameLabel);
		dataGrid.addComponent(firstNameField);
		dataGrid.addComponent(emailLabel);
		dataGrid.addComponent(emailField);
		dataGrid.addComponent(phoneLabel);
		dataGrid.addComponent(phoneField);
		dataGrid.addComponent(addressLabel);
		dataGrid.addComponent(addressField);
		dataGrid.addComponent(cityLabel);
		dataGrid.addComponent(cityField);
		dataGrid.addComponent(zipLabel);
		dataGrid.addComponent(zipField);
		dataGrid.addComponent(introductionLabel);
		dataGrid.addComponent(introductionField);

		dataGrid.setSpacing(true);

		this.addComponent(new VerticalLayout(dataGrid, saveButton));

		if (!this.user.isTeacher()) {
			Button btnBecomeTeacher = new Button();
			btnBecomeTeacher.setCaption("Tanár szeretnék lenni");
			btnBecomeTeacher.addClickListener(event -> this.userService.becomeTeacher(this.user.getId()));
			this.addComponent(btnBecomeTeacher);
		}

	}

	public void setUser(User user) {
		this.user = user;
	}
}
