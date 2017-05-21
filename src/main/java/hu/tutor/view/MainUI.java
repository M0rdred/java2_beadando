package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@SpringUI
@Widgetset("hu.tutor.view.TutorWidgetSet")
public class MainUI extends UI {

	@Autowired
	private ViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest request) {
		Navigator navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
		navigator.navigateTo(MainView.MAIN_VIEW_NAME);
	}

}
