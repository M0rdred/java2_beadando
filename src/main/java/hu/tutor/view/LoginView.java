package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.security.AuthService;
import hu.tutor.service.exception.UserBlockedException;
import hu.tutor.util.VaadinUtil;

@SpringView(name = LoginView.LOGIN_VIEW_NAME)
public class LoginView extends VerticalLayout implements View {

	private static final long serialVersionUID = -9159887005598675973L;

	@Autowired
	private AuthService authService;

	private Label errorLabel;

	protected static final String LOGIN_VIEW_NAME = "login";

	@Override
	public void enter(ViewChangeEvent event) {

		final TextField userNameField = new TextField("Felhasználónév: ");
		final PasswordField passwordField = new PasswordField("Jelszó:");

		Button loginButton = new Button("Belépés");
		Button cancelButton = new Button("Mégse");

		loginButton.addStyleName(VaadinUtil.THEME_BUTTON_STYLE);
		cancelButton.addStyleName(VaadinUtil.BORDERED_BUTTON_STYLE);

		userNameField.setWidth("200px");
		userNameField.setIcon(VaadinIcons.USER);
		userNameField.focus();

		passwordField.setWidth("200px");
		passwordField.setIcon(VaadinIcons.KEY);

		this.errorLabel = new Label(
				String.format("<div style='color:red;'>%s</div>", "Hibás felhasználónév vagy jelszó"),
				ContentMode.HTML);
		this.errorLabel.setVisible(false);
		this.errorLabel.setSizeUndefined();
		VerticalLayout loginLayout = new VerticalLayout();
		loginLayout.setSpacing(true);
		loginLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		loginLayout.addComponents(this.errorLabel, userNameField, passwordField,
				new HorizontalLayout(loginButton, cancelButton));
		loginLayout.setSpacing(true);
		loginLayout.setMargin(true);

		if (this.authService.checkIfUserLoggedIn()) {
			this.getUI().getNavigator().navigateTo(AccountView.ACCOUNT_VIEW_NAME);
		}

		loginButton.addClickListener(e -> this.login(userNameField.getValue(), passwordField.getValue()));

		loginButton.setClickShortcut(KeyCode.ENTER);

		cancelButton.addClickListener(e -> this.getUI().getNavigator().navigateTo(MainView.MAIN_VIEW_NAME));

		this.addComponent(loginLayout);
		this.addStyleName("tutor-login-view");
		this.setSizeFull();
		this.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);

	}

	private void login(String userName, String password) {
		try {
			if (LoginView.this.authService.isAuthenticUserCredentials(userName, password)) {
				LoginView.this.getUI().getNavigator().navigateTo(AccountView.ACCOUNT_VIEW_NAME);
			} else {
				Notification.show("Bejelenkezési hiba", "Rossz felhasználónév vagy jelszó", Type.ERROR_MESSAGE);
			}
		} catch (UserBlockedException e) {
			Notification.show("Bejelenkezési hiba", e.getMessage(), Type.ERROR_MESSAGE);
		}
	}
}
