package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.UI;

import hu.tutor.view.error.ErrorViewProvider;

@SuppressWarnings("serial")
@SpringUI
@Widgetset("hu.tutor.view.TutorWidgetSet")
@Title(MainUI.APP_TITLE)
@PreserveOnRefresh
@Theme("tutortheme")
public class MainUI extends UI {

	public static final String APP_TITLE = "Tutor Search Project";

	@Autowired
	private ViewProvider viewProvider;

	@Autowired
	private ErrorViewProvider errorViewProvider;

	@Autowired
	private SpringNavigator navigator;

	@Override
	protected void init(VaadinRequest request) {
		this.navigator.init(this, this);
		this.navigator.addProvider(this.viewProvider);
		this.navigator.setErrorProvider(this.errorViewProvider);
		this.navigator.navigateTo(MainView.MAIN_VIEW_NAME);
	}

}
