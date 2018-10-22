package hu.tutor.view;

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
import hu.tutor.service.AdminService;
import hu.tutor.service.UserService;

@SpringComponent
@Scope(scopeName = "prototype")
public class AdminAccountForm extends VerticalLayout {

	private static final long serialVersionUID = 4555961834490687443L;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	private AdminService adminService;

	private Grid<Teacher> teacherGrid;

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
		this.teacherGrid = new Grid<>();

		TextField introductionEditorField = new TextField();

		Editor<Teacher> teacherEditor = this.teacherGrid.getEditor();
		teacherEditor.setEnabled(true);
		teacherEditor.setBuffered(true);
		teacherEditor.setSaveCaption("Mentés");
		teacherEditor.setCancelCaption("Mégse");
		teacherEditor.addSaveListener(e -> {
			this.userService.updateUser(e.getBean());
			Notification.show("Sikeres mentés", Type.HUMANIZED_MESSAGE);
		});

		Binder<Teacher> editorBinder = teacherEditor.getBinder();

		this.teacherGrid.addComponentColumn(this::createEnableTeacherButton);
		this.teacherGrid.addColumn(Teacher::getFullName).setCaption("Név");
		this.teacherGrid.addColumn(Teacher::getIntroduction).setCaption("Bemutatkozás").setEditorBinding(
				editorBinder.bind(introductionEditorField, Teacher::getIntroduction, Teacher::setIntroduction));
		this.teacherGrid.addColumn(Teacher::getUserName).setCaption("Felhasználónév");
		this.teacherGrid.addColumn(Teacher::getFullAddress).setCaption("Cím");
		this.teacherGrid.addColumn(Teacher::getPhone).setCaption("Telefonszám");
		this.teacherGrid.addColumn(Teacher::getEmail).setCaption("Email cím");
		this.teacherGrid.addColumn(Teacher::getTeachedSubjects).setCaption("Oktatott tárgyak");

		this.teacherGrid.setSizeFull();

		this.refreshTeacherGrid();

		return this.teacherGrid;
	}

	private Button createEnableTeacherButton(Teacher teacher) {
		Button enableButton = new Button();
		enableButton.setIcon(VaadinIcons.CHECK_SQUARE_O);

		enableButton.addClickListener(e -> {
			this.adminService.enableTeacher(teacher);
			this.refreshTeacherGrid();
		});

		return enableButton;
	}

	private void refreshTeacherGrid() {
		this.teacherGrid.setItems(this.adminService.getTeachersAwaitingValidation());
	}
}
