package hu.tutor.view;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DescriptionGenerator;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.Editor;

import hu.tutor.model.Subject;
import hu.tutor.model.TeachedSubject;
import hu.tutor.model.Teacher;
import hu.tutor.model.User;
import hu.tutor.service.AdminService;
import hu.tutor.service.SubjectService;
import hu.tutor.service.UserService;
import hu.tutor.util.ActiveParameter;
import hu.tutor.view.component.PasswordChangeWindow;

@SpringComponent
@Scope(scopeName = "prototype")
public class AdminAccountForm extends VerticalLayout {

	private static final long serialVersionUID = 4555961834490687443L;

	@Autowired
	private UserService userService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private ApplicationContext context;

	private Grid<Teacher> teacherGrid;
	private Grid<Subject> subjectGrid;
	private Grid<User> userGrid;
	private Grid<TeachedSubject> teachedSubjectsGrid;

	public void init() {
		this.addComponent(this.getViewLayout());
	}

	private Component getViewLayout() {
		Accordion accordion = new Accordion();

		accordion.addTab(this.createAwaitingTeachersLayout()).setCaption("Jóváhagyásra váró tanárok");
		accordion.addTab(this.createAwaitingSubjectsLayout()).setCaption("Jóváhagyásra váró tantárgyak");
		accordion.addTab(this.createAllUsersLayout()).setCaption("Minden felhasználó");
		accordion.addTab(this.createAllSubjectsLayout()).setCaption("Minden oktatott tantárgy");

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

		this.teacherGrid.addComponentColumn(this::createEnableTeacherButton).setCaption("Aktiválás");
		this.teacherGrid.addColumn(Teacher::getFullName).setDescriptionGenerator(Teacher::getFullName)
				.setCaption("Név");
		this.teacherGrid.addColumn(Teacher::getIntroduction).setDescriptionGenerator(Teacher::getIntroduction)
				.setCaption("Bemutatkozás").setEditorBinding(
						editorBinder.bind(introductionEditorField, Teacher::getIntroduction, Teacher::setIntroduction));
		this.teacherGrid.addColumn(Teacher::getUserName).setDescriptionGenerator(Teacher::getUserName)
				.setCaption("Felhasználónév");
		this.teacherGrid.addColumn(Teacher::getFullAddress).setDescriptionGenerator(Teacher::getFullAddress)
				.setCaption("Cím");
		this.teacherGrid.addColumn(Teacher::getPhone).setDescriptionGenerator(Teacher::getPhone)
				.setCaption("Telefonszám");
		this.teacherGrid.addColumn(Teacher::getEmail).setDescriptionGenerator(Teacher::getEmail)
				.setCaption("Email cím");
		this.teacherGrid.addColumn(Teacher::getTeachedSubjects)
				.setDescriptionGenerator(
						t -> t.getTeachedSubjects().stream().map(Subject::getName).collect(Collectors.joining(", ")))
				.setCaption("Oktatott tárgyak");

		this.teacherGrid.setSizeFull();

		this.refreshTeacherGrid();

		return this.teacherGrid;
	}

	private Button createEnableTeacherButton(Teacher teacher) {
		Button enableButton = new Button();
		enableButton.setIcon(VaadinIcons.CHECK_SQUARE_O);

		enableButton.addClickListener(e -> {
			this.adminService.enableTeacher(teacher.getId(), ActiveParameter.YES);
			this.refreshTeacherGrid();
		});

		return enableButton;
	}

	private void refreshTeacherGrid() {
		this.teacherGrid.setItems(this.adminService.getTeachersAwaitingValidation());
	}

	private Component createAwaitingSubjectsLayout() {
		this.subjectGrid = new Grid<>();

		TextField introductionEditorField = new TextField();

		Editor<Subject> subjectEditor = this.subjectGrid.getEditor();
		subjectEditor.setEnabled(true);
		subjectEditor.setBuffered(true);
		subjectEditor.setSaveCaption("Mentés");
		subjectEditor.setCancelCaption("Mégse");
		subjectEditor.addSaveListener(e -> {
			this.subjectService.modifySubject(e.getBean());
			Notification.show("Sikeres mentés", Type.HUMANIZED_MESSAGE);
		});

		Binder<Subject> editorBinder = subjectEditor.getBinder();

		this.subjectGrid.addComponentColumn(this::createEnableSubjectButton).setCaption("Aktiválás").setWidth(100);
		this.subjectGrid.addColumn(Subject::getName).setDescriptionGenerator(Subject::getName).setCaption("Név")
				.setId("name");
		this.subjectGrid.addColumn(Subject::getDescription).setDescriptionGenerator(Subject::getDescription)
				.setCaption("Leírás")
				.setEditorBinding(
						editorBinder.bind(introductionEditorField, Subject::getDescription, Subject::setDescription))
				.setId("desc");

		this.subjectGrid.setSizeFull();
		this.subjectGrid.getColumn("name").setExpandRatio(1);
		this.subjectGrid.getColumn("desc").setExpandRatio(2);

		this.refreshSubjectGrid();

		return this.subjectGrid;
	}

	private Button createEnableSubjectButton(Subject subject) {
		Button enableButton = new Button();
		enableButton.setIcon(VaadinIcons.CHECK_SQUARE_O);

		enableButton.addClickListener(e -> {
			this.adminService.enableSubject(subject.getId(), ActiveParameter.YES);
			this.refreshSubjectGrid();
		});

		return enableButton;
	}

	private void refreshSubjectGrid() {
		this.subjectGrid.setItems(this.adminService.getSubjectsAwaitingValidation());
	}

	private Component createAllUsersLayout() {
		this.userGrid = new Grid<>();

		TextField introductionEditorField = new TextField();

		Editor<User> userEditor = this.userGrid.getEditor();
		userEditor.setEnabled(true);
		userEditor.setBuffered(true);
		userEditor.setSaveCaption("Mentés");
		userEditor.setCancelCaption("Mégse");
		userEditor.addSaveListener(e -> {
			this.userService.updateUser(e.getBean());
			Notification.show("Sikeres mentés", Type.HUMANIZED_MESSAGE);
		});

		Binder<User> editorBinder = userEditor.getBinder();

		this.userGrid.addComponentColumn(this::createEnableUserButton).setSortable(false).setCaption("Aktiválás");
		this.userGrid.addComponentColumn(this::createDisableUserButton).setSortable(false).setCaption("Deaktiválás");
		this.userGrid.addComponentColumn(this::createNewPasswordButton).setSortable(false).setCaption("Új jelszó");
		this.userGrid.addColumn(User::getFullName).setDescriptionGenerator(User::getFullName).setCaption("Név");
		this.userGrid.addColumn(User::getIntroduction).setDescriptionGenerator(User::getIntroduction, ContentMode.TEXT)
				.setCaption("Bemutatkozás").setEditorBinding(
						editorBinder.bind(introductionEditorField, User::getIntroduction, User::setIntroduction));
		this.userGrid.addColumn(User::getUserName).setDescriptionGenerator(User::getUserName)
				.setCaption("Felhasználónév");
		this.userGrid.addColumn(User::getFullAddress).setDescriptionGenerator(User::getFullAddress).setCaption("Cím");
		this.userGrid.addColumn(User::getPhone).setDescriptionGenerator(User::getPhone).setCaption("Telefonszám");
		this.userGrid.addColumn(User::getEmail).setDescriptionGenerator(User::getEmail).setCaption("Email cím");
		this.userGrid.addComponentColumn(u -> this.createGridCheckbox(u.getIsActive()))
				.setDescriptionGenerator((DescriptionGenerator<User>) u -> u.getIsActive() ? "Aktív" : "Inaktív")
				.setSortable(false).setCaption("Aktív");

		this.userGrid.setSizeFull();

		this.refreshUserGrid();

		return this.userGrid;
	}

	private Button createEnableUserButton(User user) {
		Button enableButton = new Button();
		enableButton.setIcon(VaadinIcons.CHECK_SQUARE_O);

		enableButton.addClickListener(e -> {
			this.adminService.activatePerson(user.getId(), ActiveParameter.YES);
			this.refreshUserGrid();
		});

		enableButton.setEnabled(!user.getIsActive());
		return enableButton;
	}

	private Button createDisableUserButton(User user) {
		Button disableButton = new Button();
		disableButton.setIcon(VaadinIcons.CLOSE_CIRCLE_O);

		disableButton.addClickListener(e -> {
			this.adminService.activatePerson(user.getId(), ActiveParameter.NO);
			this.refreshUserGrid();
		});

		disableButton.setEnabled(user.getIsActive());
		return disableButton;
	}

	private Button createNewPasswordButton(User user) {
		Button newPassButton = new Button(VaadinIcons.EDIT);

		newPassButton.addClickListener(e -> this.openNewPasswordWindow(user));

		return newPassButton;
	}

	private CheckBox createGridCheckbox(Boolean active) {
		CheckBox checkBox = new CheckBox();
		checkBox.setReadOnly(true);

		checkBox.setValue(Boolean.TRUE.equals(active));

		return checkBox;
	}

	private void openNewPasswordWindow(User user) {
		PasswordChangeWindow window = this.context.getBean(PasswordChangeWindow.class);
		window.init(this.adminService, user.getId());

		UI.getCurrent().addWindow(window);
	}

	private void refreshUserGrid() {
		this.userGrid.setItems(this.adminService.getAllUsers());
	}

	private Component createAllSubjectsLayout() {
		this.teachedSubjectsGrid = new Grid<>();

		this.teachedSubjectsGrid.addComponentColumn(this::createEnableSubjectButton).setSortable(false)
				.setCaption("Aktiválás");
		this.teachedSubjectsGrid.addComponentColumn(this::createDisableSubjectButton).setSortable(false)
				.setCaption("Deaktiválás");
		this.teachedSubjectsGrid.addColumn(TeachedSubject::getSubjectName)
				.setDescriptionGenerator(TeachedSubject::getSubjectName).setCaption("Tantárgy neve");
		this.teachedSubjectsGrid.addColumn(TeachedSubject::getSubjectDescription)
				.setDescriptionGenerator(TeachedSubject::getSubjectDescription).setCaption("Tantárgy leírása");
		this.teachedSubjectsGrid.addColumn(TeachedSubject::getTeacherName)
				.setDescriptionGenerator(TeachedSubject::getTeacherName).setCaption("Tanár neve");
		this.teachedSubjectsGrid.addColumn(TeachedSubject::getTeachedIntroduction)
				.setDescriptionGenerator(TeachedSubject::getTeachedIntroduction).setCaption("Tanár bemutatkozása");
		this.teachedSubjectsGrid.addColumn(TeachedSubject::getTeachedSubjectDescription)
				.setDescriptionGenerator(TeachedSubject::getTeachedSubjectDescription).setCaption("Tanár leírása");
		this.teachedSubjectsGrid.addComponentColumn(s -> this.createGridCheckbox(s.getActive()))
				.setDescriptionGenerator(
						(DescriptionGenerator<TeachedSubject>) s -> s.getActive() ? "Aktív" : "Inaktív")
				.setSortable(false).setCaption("Aktív");
		this.teachedSubjectsGrid.setSizeFull();

		this.refreshTeachedSubjectsGrid();

		return this.teachedSubjectsGrid;
	}

	private Button createEnableSubjectButton(TeachedSubject subject) {
		Button enableButton = new Button();
		enableButton.setIcon(VaadinIcons.CHECK_SQUARE_O);

		enableButton.addClickListener(e -> {
			this.adminService.activateTeachedSubject(subject.getSubjectId(), subject.getTeacherId(),
					ActiveParameter.YES);
			this.refreshTeachedSubjectsGrid();
		});

		enableButton.setEnabled(!subject.getActive());
		return enableButton;
	}

	private Button createDisableSubjectButton(TeachedSubject subject) {
		Button disableButton = new Button();
		disableButton.setIcon(VaadinIcons.CLOSE_CIRCLE_O);

		disableButton.addClickListener(e -> {
			this.adminService.activateTeachedSubject(subject.getSubjectId(), subject.getTeacherId(),
					ActiveParameter.NO);
			this.refreshTeachedSubjectsGrid();
		});

		disableButton.setEnabled(subject.getActive());
		return disableButton;
	}

	private void refreshTeachedSubjectsGrid() {
		List<TeachedSubject> teachedSubjects = this.adminService.listTeachedSubjects();

		// TODO remove try, it's only for debug
		try {
			System.err.println(
					new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(teachedSubjects));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.teachedSubjectsGrid.setItems(teachedSubjects);
	}

}
