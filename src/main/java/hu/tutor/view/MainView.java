package hu.tutor.view;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import hu.tutor.model.Teacher;
import hu.tutor.model.User;
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

		Teacher teacher = new Teacher();
		User user = userService.getUserById(new Integer(1));
		if (user instanceof Teacher) {
			teacher = (Teacher) user;
		}
		label.setValue(teacher.getTeachedSubjects().get(0).getName());
		// user.setFirstName("a");
		// user.setLastName("b");
		// user.setEmail("a");
		// user.setUserName("b");
		// user.setPassword("a");
		// userService.saveUser(user);
		addComponent(label);
	}

}
