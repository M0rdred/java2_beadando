package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.vaadin.risto.formsender.FormSender;
import org.vaadin.risto.formsender.widgetset.client.shared.Method;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.User;
import hu.tutor.security.AuthService;
import hu.tutor.service.UserService;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings({ "serial", "unchecked" })
@SpringView(name = LoginView.LOGIN_VIEW_NAME)
public class LoginView extends VerticalLayout implements View {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	private AuthService authService;

	private Label errorLabel;

	protected static final String LOGIN_VIEW_NAME = "login";

	@Autowired
	private ApplicationContext ctx;

	private Label label;

	@Override
	public void enter(ViewChangeEvent event) {

		final TextField userNameField = new TextField("Felhasználónév: ");
		final PasswordField passwordField = new PasswordField("Jelszó:");
		Button loginButton = new Button("Belépés");

		userNameField.setWidth("200px");
		userNameField.setIcon(VaadinIcons.USER);

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
		loginLayout.addComponents(this.errorLabel, userNameField, passwordField, loginButton);
		loginLayout.setSpacing(true);
		loginLayout.setMargin(true);

		loginButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				/*
				 * FormSender formSender = new FormSender();
				 * formSender.setFormAction(
				 * VaadinServlet.getCurrent().getServletContext().getContextPath
				 * () + "/j_spring_security_check");
				 * formSender.setFormMethod(Method.POST);
				 * formSender.addValue("username", userNameField.getValue());
				 * formSender.addValue("password", passwordField.getValue());
				 * formSender.setFormTarget("_top"); formSender.extend(getUI());
				 * formSender.submit();
				 */

				login(userNameField.getValue(), passwordField.getValue());
			}

			private void login(String userName, String password) {
				if (authService.isAuthenticUser(userName, password)) {
					VaadinSession.getCurrent().setAttribute("user", userService.getUserByUserName(userName));
					getUI().getNavigator().navigateTo(AccountView.ACCOUNT_VIEW_NAME);
				} else {
					Notification.show("Bejelenkezési hiba", "Rossz felhasználónév vagy jelszó", Type.ERROR_MESSAGE);
				}
			}
		});

		addComponent(loginLayout);
		setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);

	}
}
