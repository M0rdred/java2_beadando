package hu.tutor.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = SearchView.SEARCH_VIEW_NAME)
public class SearchView extends VerticalLayout implements View {

	private static final long serialVersionUID = 9132068016374956428L;

	public static final String SEARCH_VIEW_NAME = "search";

	@Override
	public void enter(ViewChangeEvent event) {
		this.addComponents(this.createMainPageLink(), this.createContent());
		this.addStyleName("tutor-background");
	}

	private Component createMainPageLink() {
		Link mainPageLink = new Link("Főoldal", new ExternalResource("#!" + MainView.MAIN_VIEW_NAME));

		return mainPageLink;
	}

	private Component createContent() {
		return new VerticalLayout(this.createSearchLayout(), this.createResultLayout());
	}

	private Component createSearchLayout() {
		return new Label("Kereső panel");
	}

	private Component createResultLayout() {
		return new Label("Eredmény panel");
	}

}
