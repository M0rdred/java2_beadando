package hu.tutor.view;

import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@Scope(scopeName = "prototype")
public class AdminAccountForm extends VerticalLayout {

	private static final long serialVersionUID = 4555961834490687443L;

	public AdminAccountForm() {
		addComponent(new Label("Admin account"));
	}
}
