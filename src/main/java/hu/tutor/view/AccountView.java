package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Link;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.Administrator;
import hu.tutor.model.Teacher;
import hu.tutor.model.User;

@SpringView(name = AccountView.ACCOUNT_VIEW_NAME)
public class AccountView extends VerticalLayout implements View {

	protected static final String ACCOUNT_VIEW_NAME = "account";
	private User user;

	@Autowired
	private UserAccountForm userAccountForm;
	@Autowired
	private TeacherAccountForm teacherAccountForm;
	@Autowired
	private AdminAccountForm adminAccountForm;

	@Override
	public void enter(ViewChangeEvent event) {

		Link logoutLink = new Link("Kijelentkezés", new ExternalResource("#!logout"));

		user = (User) VaadinSession.getCurrent().getAttribute("user");
		// Label label = new Label(user.getFirstName());
		// addComponent(label);

		TabSheet tabSheet = new TabSheet();

		tabSheet.addTab(userAccountForm, "Fiók");

		if (user.getClass() == Teacher.class) {
			tabSheet.addTab(teacherAccountForm, "Tanár");
		}

		if (user.getClass() == Administrator.class) {
			tabSheet.addTab(adminAccountForm, "Adminisztrátor");
		}

		addComponent(logoutLink);
		addComponent(tabSheet);

	}

}
