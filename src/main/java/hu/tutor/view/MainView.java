package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import hu.tutor.service.UserService;

@SuppressWarnings({ "serial", "unchecked" })
@SpringView(name = MainView.MAIN_VIEW_NAME)
public class MainView extends HorizontalLayout implements View {

	protected static final String MAIN_VIEW_NAME = "main";

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	private Label label;

	@Override
	public void enter(ViewChangeEvent event) {
		label = new Label();
		label.setValue(userService.getUserById(new Integer(1)).getFirstName());
		addComponent(label);
	}

}
