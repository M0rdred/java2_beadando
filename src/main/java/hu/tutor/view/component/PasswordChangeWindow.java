package hu.tutor.view.component;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import hu.tutor.model.User;
import hu.tutor.service.AdminService;
import hu.tutor.util.VaadinUtil;

@SpringComponent
public class PasswordChangeWindow extends Window {

	private static final long serialVersionUID = -5355345054168152101L;

	private AdminService adminService;
	private Integer userId;
	private Binder<User> passBinder;
	private TextField newPassTextfield;

	public void init(AdminService adminService, Integer userId) {
		this.adminService = adminService;
		this.userId = userId;

		this.createContent();
	}

	private void createContent() {
		this.setCaption("Új jelszó beállítása");
		this.setResizable(false);
		this.setClosable(false);
		this.setModal(true);

		this.setWidth("20%");
		this.setHeight("25%");

		this.addStyleName("password-window");

		VerticalLayout vertical = new VerticalLayout();
		this.newPassTextfield = new TextField("Új jelszó");
		this.newPassTextfield.focus();
		this.newPassTextfield.setWidth("100%");

		Button savePassButton = new Button("Mentés");
		Button cancelButton = new Button("Mégse");

		savePassButton.addStyleName(VaadinUtil.THEME_BUTTON_STYLE);
		cancelButton.addStyleName(VaadinUtil.BORDERED_BUTTON_STYLE);

		savePassButton.addClickListener(e -> {
			this.adminService.modifyPassword(this.userId, this.passBinder.getBean().getPassword());
			this.close();
			Notification.show("Jelszó sikeresen megváltoztatva", Type.HUMANIZED_MESSAGE);
		});
		cancelButton.addClickListener(e -> this.close());

		HorizontalLayout actionLayout = new HorizontalLayout(savePassButton, cancelButton);
		actionLayout.setComponentAlignment(savePassButton, Alignment.BOTTOM_LEFT);
		actionLayout.setComponentAlignment(cancelButton, Alignment.BOTTOM_LEFT);
		actionLayout.setHeight("100%");

		vertical.addComponents(this.newPassTextfield, actionLayout);
		vertical.setSizeFull();

		this.setContent(vertical);

		this.createBinder();
	}

	private void createBinder() {
		this.passBinder = new Binder<>();

		this.passBinder.forField(this.newPassTextfield)
				.withValidator(new StringLengthValidator("A jelszó legalább 6 karakteres kell legyen.", 6, 100))
				.bind(User::getPassword, User::setPassword);

		this.passBinder.setBean(new User());
	}
}
