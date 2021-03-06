package hu.tutor.view;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ErrorLevel;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.User;
import hu.tutor.service.TeacherService;
import hu.tutor.service.UserService;
import hu.tutor.util.VaadinUtil;

@SuppressWarnings({ "serial" })
@SpringView(name = RegistrationView.REGISTER_VIEW_NAME)
public class RegistrationView extends FormLayout implements View {

	protected static final String REGISTER_VIEW_NAME = "registration";

	private String defaultWidth = "200px";

	@Autowired
	private UserService userService;

	@Autowired
	private TeacherService teacherService;

	@Override
	public void enter(ViewChangeEvent event) {
		final TextField userNameField = new TextField("Felhasználónév: ");
		final PasswordField passwordField = new PasswordField("Jelszó: ");
		final PasswordField passwordAgainField = new PasswordField("Jelszó ismét: ");
		final TextField emailField = new TextField("Email: ");
		final TextField firstNameField = new TextField("Keresztnév: ");
		final TextField lastNameField = new TextField("Vezetéknév: ");
		final DateField birthDateField = new DateField("Születési idő");
		final CheckBox teacherCheckBox = new CheckBox("Tanár");

		Binder<User> binder = new Binder<>();

		binder.forField(userNameField).withValidator(
				new StringLengthValidator("A felhasználói névnek legalább 3 karakteresnek kell lennie.", 3, 100))
				.bind(User::getUserName, User::setUserName);
		binder.forField(passwordField)
				.withValidator(new StringLengthValidator("A jelszó legalább 6 karakteres kell legyen.", 6, 100))
				.withValidator(
						Validator.from(v -> StringUtils.equals(passwordField.getValue(), passwordAgainField.getValue()),
								"A két megadott jelszó nem egyezik meg", ErrorLevel.ERROR))
				.bind(User::getPassword, User::setPassword);
		binder.forField(emailField).withValidator(new EmailValidator("Nem helyes email cím.")).bind(User::getEmail,
				User::setEmail);
		binder.forField(firstNameField)
				.withValidator(
						new StringLengthValidator("A keresztnévnek legalább 3 karakteresnek kell lennie.", 3, 100))
				.bind(User::getFirstName, User::setFirstName);
		binder.forField(lastNameField)
				.withValidator(
						new StringLengthValidator("A vezetéknévnek legalább 3 karakteresnek kell lennie.", 3, 100))
				.bind(User::getLastName, User::setLastName);
		binder.forField(birthDateField)
				.withValidator(new DateRangeValidator("A minimum korhatár 8 év", null, LocalDate.now().minusYears(8)))
				.bind(User::getBirthDate, User::setBirthDate);

		binder.setBean(new User());

		Button registerButton = new Button("Regisztráció");
		Button cancelButton = new Button("Mégse");

		userNameField.setWidth(this.defaultWidth);
		userNameField.setRequiredIndicatorVisible(true);
		userNameField.setIcon(VaadinIcons.USER);

		passwordField.setWidth(this.defaultWidth);
		passwordField.setRequiredIndicatorVisible(true);
		passwordField.setIcon(VaadinIcons.KEY);

		passwordAgainField.setWidth(this.defaultWidth);
		passwordAgainField.setRequiredIndicatorVisible(true);
		passwordAgainField.setIcon(VaadinIcons.KEY);

		emailField.setWidth(this.defaultWidth);
		emailField.setRequiredIndicatorVisible(true);

		firstNameField.setWidth(this.defaultWidth);
		firstNameField.setRequiredIndicatorVisible(true);

		lastNameField.setWidth(this.defaultWidth);
		lastNameField.setRequiredIndicatorVisible(true);

		birthDateField.setWidth(this.defaultWidth);
		birthDateField.setRequiredIndicatorVisible(true);

		VerticalLayout loginLayout = new VerticalLayout();
		loginLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		loginLayout.addComponents(userNameField, passwordField, passwordAgainField, emailField, lastNameField,
				firstNameField, birthDateField, teacherCheckBox, registerButton, cancelButton);
		loginLayout.setSpacing(true);
		loginLayout.setMargin(true);

		registerButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (this.validateData()) {
					User user = binder.getBean();

					RegistrationView.this.userService.saveUser(user);

					if (teacherCheckBox.getValue()) {
						RegistrationView.this.teacherService.becomeTeacher(user.getId());
					}

					RegistrationView.this.getUI().getNavigator().navigateTo(MainView.MAIN_VIEW_NAME);
					Notification.show("Sikeres regisztráció", Notification.Type.HUMANIZED_MESSAGE);
				} else {
					Notification.show("Regisztrációs hiba", "Kérem ellenőrizze az adatokat.",
							Notification.Type.ERROR_MESSAGE);
				}
			}

			private boolean validateData() {
				return passwordField.getValue().equals(passwordAgainField.getValue()) && !binder.validate().hasErrors();
			}

		});
		registerButton.addStyleName(VaadinUtil.THEME_BUTTON_STYLE);

		cancelButton.addClickListener(e -> this.getUI().getNavigator().navigateTo(MainView.MAIN_VIEW_NAME));
		cancelButton.addStyleName(VaadinUtil.BORDERED_BUTTON_STYLE);

		Label titleLabel = new Label("Regisztráció");
		titleLabel.addStyleName("h3");
		HorizontalLayout titleLayout = new HorizontalLayout(titleLabel);
		titleLayout.addStyleName("tutor-menuline");
		titleLayout.setHeight("60%");
		titleLayout.setSizeFull();
		titleLayout.setComponentAlignment(titleLabel, Alignment.TOP_LEFT);

		this.addComponents(titleLayout, loginLayout);
		this.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
		this.setMargin(true);
		this.setSizeFull();
		this.addStyleName("tutor-registration-view");
	}

}
