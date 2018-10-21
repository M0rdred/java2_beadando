package hu.tutor.view;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.Editor;

import hu.tutor.model.Teacher;
import hu.tutor.service.UserService;

@SpringComponent
@Scope(scopeName = "prototype")
public class AdminAccountForm extends VerticalLayout {

	private static final long serialVersionUID = 4555961834490687443L;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public void init() {
		this.addComponent(this.getViewLayout());
	}

	private Component getViewLayout() {
		Accordion accordion = new Accordion();

		accordion.addTab(this.createAwaitingTeachersLayout()).setCaption("Jóváhagyásra váró tanárok");

		accordion.setSizeFull();

		return accordion;
	}

	private Component createAwaitingTeachersLayout() {
		Grid<Teacher> teacherGrid = new Grid<>();

		TextField introductionEditorField = new TextField();

		Editor<Teacher> teacherEditor = teacherGrid.getEditor();
		teacherEditor.setEnabled(true);
		teacherEditor.setBuffered(true);
		teacherEditor.setSaveCaption("Mentés");
		teacherEditor.setCancelCaption("Mégse");
		teacherEditor.addSaveListener(e -> {
			this.userService.updateUser(e.getBean());
			Notification.show("Sikeres mentés", Type.HUMANIZED_MESSAGE);
		});

		Binder<Teacher> editorBinder = teacherEditor.getBinder();

		teacherGrid.addComponentColumn(this::createEnableTeacherButton);
		teacherGrid.addColumn(Teacher::getFullName).setCaption("Név");
		teacherGrid.addColumn(Teacher::getIntroduction).setCaption("Bemutatkozás").setEditorBinding(
				editorBinder.bind(introductionEditorField, Teacher::getIntroduction, Teacher::setIntroduction));
		teacherGrid.addColumn(Teacher::getUserName).setCaption("Felhasználónév");
		teacherGrid.addColumn(t -> t.getAddress().getFullAddress()).setCaption("Cím");
		teacherGrid.addColumn(Teacher::getPhone).setCaption("Telefonszám");
		teacherGrid.addColumn(Teacher::getEmail).setCaption("Email cím");
		teacherGrid.addColumn(Teacher::getTeachedSubjects).setCaption("Oktatott tárgyak");

		teacherGrid.setSizeFull();

		Teacher teacher = (Teacher) this.userService.getUserById(1);
		teacherGrid.setItems(Arrays.asList(teacher));

		return teacherGrid;
	}

	private Button createEnableTeacherButton(Teacher teacher) {
		Button enableButton = new Button();
		enableButton.setIcon(VaadinIcons.CHECK_SQUARE_O);

		enableButton.addClickListener(e -> Notification.show("Not yet implemented", Type.WARNING_MESSAGE));

		return enableButton;
	}
}
