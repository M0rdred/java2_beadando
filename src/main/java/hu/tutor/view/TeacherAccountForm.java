package hu.tutor.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
public class TeacherAccountForm extends VerticalLayout {
	public TeacherAccountForm() {
		addComponent(new Label("Teacher account"));
		
		
	}
}
