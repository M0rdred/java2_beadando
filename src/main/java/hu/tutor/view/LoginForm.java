package hu.tutor.view;

import org.vaadin.risto.formsender.FormSender;
import org.vaadin.risto.formsender.widgetset.client.shared.Method;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class LoginForm extends VerticalLayout {

	private Label errorLabel;

	public LoginForm() {
		final TextField userNameField = new TextField("Felhaszn�l�n�v: ");
		final PasswordField passwordField = new PasswordField("Jelsz�:");
		Button loginButton = new Button("Bel�p�s");

		userNameField.setWidth("200px");
		userNameField.setIcon(VaadinIcons.USER);

		passwordField.setWidth("200px");
		passwordField.setIcon(VaadinIcons.KEY);

		this.errorLabel = new Label(
				String.format("<div style='color:red;'>%s</div>", "Hib�s felhaszn�l�n�v vagy jelsz�"),
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
				FormSender formSender = new FormSender();
				formSender.setFormAction(
						VaadinServlet.getCurrent().getServletContext().getContextPath() + "/j_spring_security_check");
				formSender.setFormMethod(Method.POST);
				formSender.addValue("username", userNameField.getValue());
				formSender.addValue("password", passwordField.getValue());
				formSender.setFormTarget("_top");
				formSender.extend(getUI());
				formSender.submit();
			}
		});

		addComponent(loginLayout);
		setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
	}

	public void setVisibleErrorLabel(boolean b) {
		errorLabel.setVisible(b);
	}

}