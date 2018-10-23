package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.Teacher;
import hu.tutor.model.User;
import hu.tutor.view.error.AccessDeniedView;

@SpringView(name = AccountView.ACCOUNT_VIEW_NAME)
public class AccountView extends VerticalLayout implements View {

	private static final long serialVersionUID = -259083151677613427L;
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
		this.user = (User) VaadinSession.getCurrent().getAttribute("user");
		if (this.user == null) {
			this.getUI().getNavigator().navigateTo(AccessDeniedView.ACCESS_DENIED_VIEW_NAME);
			return;
		}

		Link logoutLink = new Link("Kijelentkezés", new ExternalResource("#!" + LogoutView.LOGOUT_VIEW_NAME));
		Link searchLink = new Link("Keresés", new ExternalResource("#!" + SearchView.SEARCH_VIEW_NAME));

		TabSheet tabSheet = new TabSheet();

		tabSheet.addTab(this.userAccountForm, "Fiók");
		this.userAccountForm.init(this.user);

		if (this.user.isTeacher()) {
			tabSheet.addTab(this.teacherAccountForm, "Tanár");
			this.teacherAccountForm.setTeacher((Teacher) this.user);
			this.teacherAccountForm.init();
		}

		if (this.user.isAdmin()) {
			this.adminAccountForm.init();
			tabSheet.addTab(this.adminAccountForm, "Adminisztrátor");
		}

		this.addComponents(new HorizontalLayout(logoutLink, searchLink));
		this.addComponent(tabSheet);

	}

}
