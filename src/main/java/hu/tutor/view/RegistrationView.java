package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.Teacher;
import hu.tutor.model.User;
import hu.tutor.service.UserService;

@SuppressWarnings({ "serial" })
@SpringView(name = RegistrationView.REGISTER_VIEW_NAME)
public class RegistrationView extends FormLayout implements View {

	protected static final String REGISTER_VIEW_NAME = "register";

	private Label errorLabel;

	private String defaultWidth = "200px";

	@Autowired
	UserService userService;

	@Override
	public void enter(ViewChangeEvent event) {
		final TextField userNameField = new TextField("Felhaszn�l�n�v: ");
		final PasswordField passwordField = new PasswordField("Jelsz�: ");
		final PasswordField passwordAgainField = new PasswordField("Jelsz� ism�t: ");
		final TextField emailField = new TextField("Email: ");
		final TextField firstNameField = new TextField("Keresztn�v: ");
		final TextField lastNameField = new TextField("Vezet�kn�v: ");
		final CheckBox teacherCheckBox = new CheckBox("Tan�r: ");

		Binder<User> binder = new Binder<>();
		binder.forField(userNameField).withValidator(
				new StringLengthValidator("A felhaszn�l�i n�vnek legal�bb 3 karakteresnek kell lennie.", 3, 100))
				.bind(User::getUserName, User::setUserName);
		binder.forField(passwordField)
				.withValidator(new StringLengthValidator("A jelsz� legal�bb 6 karakteres kell legyen.", 6, 100))
				.bind(User::getPassword, User::setPassword);
		binder.forField(emailField).withValidator(new EmailValidator("Nem helyes email cim.")).bind(User::getEmail,
				User::setEmail);
		binder.forField(firstNameField)
				.withValidator(
						new StringLengthValidator("A keresztn�vnek legal�bb 3 karakteresnek kell lennie.", 3, 100))
				.bind(User::getFirstName, User::setFirstName);
		binder.forField(lastNameField)
				.withValidator(
						new StringLengthValidator("A vezet�kn�vnek legal�bb 3 karakteresnek kell lennie.", 3, 100))
				.bind(User::getLastName, User::setLastName);

		Button registerButton = new Button("Regisztr�ci�");
		Button cancelButton = new Button("M�gse");

		userNameField.setWidth(defaultWidth);
		userNameField.setRequiredIndicatorVisible(true);
		userNameField.setIcon(VaadinIcons.USER);

		passwordField.setWidth(defaultWidth);
		passwordField.setRequiredIndicatorVisible(true);
		passwordField.setIcon(VaadinIcons.KEY);

		passwordAgainField.setWidth(defaultWidth);
		passwordAgainField.setRequiredIndicatorVisible(true);
		passwordAgainField.setIcon(VaadinIcons.KEY);

		emailField.setWidth(defaultWidth);
		emailField.setRequiredIndicatorVisible(true);

		firstNameField.setWidth(defaultWidth);
		firstNameField.setRequiredIndicatorVisible(true);

		lastNameField.setWidth(defaultWidth);
		lastNameField.setRequiredIndicatorVisible(true);

		this.errorLabel = new Label(
				String.format("<div style='color:red;'>%s</div>", "Hib�s felhaszn�l�n�v vagy jelsz�"),
				ContentMode.HTML);
		this.errorLabel.setVisible(false);
		this.errorLabel.setSizeUndefined();

		// Binder<User> userBinder = new Binder<>();
		// userBinder.bind(firstNameField, User::setFirstName,
		// User::getFirstName);

		VerticalLayout loginLayout = new VerticalLayout();
		loginLayout.setSpacing(true);
		loginLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		loginLayout.addComponents(this.errorLabel, userNameField, passwordField, passwordAgainField, emailField,
				firstNameField, lastNameField, teacherCheckBox, registerButton, cancelButton);
		loginLayout.setSpacing(true);
		loginLayout.setMargin(true);

		registerButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (validateData()) {
					User user;
					if (teacherCheckBox.getValue()) {
						user = new Teacher();
					} else {
						user = new User();
					}
					user.setUserName(userNameField.getValue());
					user.setFirstName(firstNameField.getValue());
					user.setLastName(lastNameField.getValue());
					user.setPassword(passwordField.getValue());
					user.setEmail(emailField.getValue());

					userService.saveUser(user);
					getUI().getNavigator().navigateTo(MainView.MAIN_VIEW_NAME);
					Notification.show("Sikeres regisztr�ci�", Notification.TYPE_HUMANIZED_MESSAGE);
				} else {
					Notification.show("Regisztr�ci�s hiba", "K�rem ellen�rizze az adatokat.",
							Notification.TYPE_ERROR_MESSAGE);
				}
			}

			private boolean validateData() {
				if (!passwordField.getValue().equals(passwordAgainField.getValue()) || binder.validate().hasErrors()) {
					return false;
				}

				return true;
			}

		});

		cancelButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainView.MAIN_VIEW_NAME);
			}
		});

		addComponent(loginLayout);
		setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
	}

	public void setVisibleErrorLabel(boolean b) {
		errorLabel.setVisible(b);
	}

}
