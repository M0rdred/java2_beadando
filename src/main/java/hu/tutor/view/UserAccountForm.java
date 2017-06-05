package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Binder;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.User;
import hu.tutor.service.UserService;

@SpringComponent
@Scope(scopeName = "prototype")
public class UserAccountForm extends HorizontalLayout {

	private User user;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public UserAccountForm() {

		user = (User) VaadinSession.getCurrent().getAttribute("user");

		GridLayout dataGrid = new GridLayout();

		dataGrid.setColumns(2);
		dataGrid.setRows(9);

		dataGrid.setCaption("Szem�lyes adatok");

		Label userNameLabel = new Label("Felhaszn�l�n�v:");
		TextField userNameField = new TextField();
		userNameField.setReadOnly(true);
		Label lastNameLabel = new Label("Vezet�kn�v:");
		TextField lastNameField = new TextField();
		Label firstNameLabel = new Label("Keresztn�v:");
		TextField firstNameField = new TextField();
		Label emailLabel = new Label("Email:");
		TextField emailField = new TextField();
		Label phoneLabel = new Label("Telefon:");
		TextField phoneField = new TextField();
		Label addressLabel = new Label("C�m:");
		TextField addressField = new TextField();
		Label cityLabel = new Label("V�ros:");
		TextField cityField = new TextField();
		Label zipLabel = new Label("Ir�ny�t�sz�m:");
		TextField zipField = new TextField();
		Label introductionLabel = new Label("Bemutatkoz�s:");
		TextArea introductionField = new TextArea();

		Binder<User> dataBinder = new Binder<>();
		dataBinder.forField(userNameField).bind(User::getUserName, User::setUserName);
		dataBinder.forField(lastNameField).bind(User::getLastName, User::setLastName);
		dataBinder.forField(firstNameField).bind(User::getFirstName, User::setFirstName);
		dataBinder.forField(emailField).bind(User::getEmail, User::setEmail);
		dataBinder.forField(phoneField).bind(User::getPhone, User::setPhone);
		dataBinder.forField(addressField).bind(User::getAddress, User::setAddress);
		dataBinder.forField(cityField).bind(User::getCity, User::setCity);
		dataBinder.forField(zipField).bind(User::getZip, User::setZip);
		dataBinder.forField(introductionField).bind(User::getIntroduction, User::setIntroduction);

		dataBinder.setBean(user);
		/*
		 * userNameField.setValue(user.getUserName());
		 * lastNameField.setValue(user.getLastName());
		 * firstNameField.setValue(user.getFirstName());
		 * emailField.setValue(user.getEmail());
		 * phoneField.setValue(user.getPhone());
		 * addressField.setValue(user.getAddress());
		 * cityField.setValue(user.getCity()); zipField.setValue(user.getZip());
		 * introductionField.setValue(user.getIntroduction());
		 */
		Button saveButton = new Button();
		saveButton.setCaption("Ment�s");
		saveButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				/*
				 * user.setFirstName(firstNameField.getValue());
				 * user.setLastName(lastNameField.getValue());
				 * user.setEmail(emailField.getValue());
				 * user.setPhone(phoneField.getValue());
				 * user.setAddress(addressField.getValue());
				 * user.setCity(cityField.getValue());
				 * user.setZip(zipField.getValue());
				 * user.setIntroduction(introductionField.getValue());
				 */
				user = dataBinder.getBean();
				userService.updateUser(user);
			}
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

		addComponent(new VerticalLayout(dataGrid, saveButton));
		
		if (user.getClass() == User.class) {
			Button btnBecomeTeacher = new Button();
			btnBecomeTeacher.setCaption("Tan�r szeretn�k lenni");
			btnBecomeTeacher.addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					userService.becomeTeacher(user.getId());
				}
			});
			addComponent(btnBecomeTeacher);
		}

	}

	public void setUser(User user) {
		this.user = user;
	}
}
