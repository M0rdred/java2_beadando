package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings({ "serial", "unchecked" })
@SpringView(name = MainView.MAIN_VIEW_NAME)
public class MainView extends VerticalLayout implements View {

	protected static final String MAIN_VIEW_NAME = "";

	@Autowired
	private ApplicationContext ctx;

	private Label label;

	@Override
	public void enter(ViewChangeEvent event) {
		label = new Label();
		label.setValue("Hello World!");
		addComponent(label);
	}

}
