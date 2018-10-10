package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@SpringUI
@Widgetset("hu.tutor.view.TutorWidgetSet")
@Title(MainUI.APP_TITLE)
public class MainUI extends UI {

	public static final String APP_TITLE = "Tutor Search";

	@Autowired
	private ViewProvider viewProvider;

	@Autowired
	private ErrorViewProvider errorViewProvider;

	@Override
	protected void init(VaadinRequest request) {
		Navigator navigator = new Navigator(this, this);
		navigator.addProvider(this.viewProvider);
		navigator.setErrorProvider(this.errorViewProvider);
		navigator.navigateTo(MainView.MAIN_VIEW_NAME);
	}

}
