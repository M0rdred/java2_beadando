package hu.tutor.view;

import org.vaadin.risto.formsender.FormSender;

import com.vaadin.data.Binder;
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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.User;

@SuppressWarnings({ "serial" })
@SpringView(name = RegistrationView.REGISTER_VIEW_NAME)
public class RegistrationView extends FormLayout implements View {

	protected static final String REGISTER_VIEW_NAME = "register";

	private Label errorLabel;

	private String defaultWidth = "200px";

	@Override
	public void enter(ViewChangeEvent event) {
		final TextField userNameField = new TextField("Felhasználónév: ");
		final PasswordField passwordField = new PasswordField("Jelszó: ");
		final PasswordField passwordAgainField = new PasswordField("Jelszó ismét: ");
		final TextField firstNameField = new TextField("Keresztnév: ");
		final TextField lastNameField = new TextField("Vezetéknév: ");
		final CheckBox teacherCheckBox = new CheckBox("Tanár: ");

		Button registerButton = new Button("Regisztráció");
		Button cancelButton = new Button("Mégse");

		userNameField.setWidth(defaultWidth);
		userNameField.setRequiredIndicatorVisible(true);
		userNameField.setIcon(VaadinIcons.USER);

		passwordField.setWidth(defaultWidth);
		passwordField.setRequiredIndicatorVisible(true);
		passwordField.setIcon(VaadinIcons.KEY);

		passwordAgainField.setWidth(defaultWidth);
		passwordAgainField.setRequiredIndicatorVisible(true);
		passwordAgainField.setIcon(VaadinIcons.KEY);

		firstNameField.setWidth(defaultWidth);
		firstNameField.setRequiredIndicatorVisible(true);

		lastNameField.setWidth(defaultWidth);
		lastNameField.setRequiredIndicatorVisible(true);

		this.errorLabel = new Label(
				String.format("<div style='color:red;'>%s</div>", "Hibás felhasználónév vagy jelszó"),
				ContentMode.HTML);
		this.errorLabel.setVisible(false);
		this.errorLabel.setSizeUndefined();

		// Binder<User> userBinder = new Binder<>();
		// userBinder.bind(firstNameField, User::setFirstName,
		// User::getFirstName);

		VerticalLayout loginLayout = new VerticalLayout();
		loginLayout.setSpacing(true);
		loginLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		loginLayout.addComponents(this.errorLabel, userNameField, passwordField, passwordAgainField, firstNameField,
				lastNameField, teacherCheckBox, registerButton, cancelButton);
		loginLayout.setSpacing(true);
		loginLayout.setMargin(true);

		registerButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				User user = new User();
				user.setFirstName(firstNameField.getValue());
				user.setLastName(lastNameField.getValue());
				user.setTeacher(teacherCheckBox.getValue());
				FormSender formSender = new FormSender();

			}
		});

		cancelButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

			}
		});

		addComponent(loginLayout);
		setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
	}

	public void setVisibleErrorLabel(boolean b) {
		errorLabel.setVisible(b);
	}

}
