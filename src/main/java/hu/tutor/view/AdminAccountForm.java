package hu.tutor.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
public class AdminAccountForm extends VerticalLayout {
	public AdminAccountForm() {
		addComponent(new Label("Admin account"));
	}
}
