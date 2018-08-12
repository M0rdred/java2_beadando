package hu.tutor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;
import hu.tutor.service.SubjectService;
import hu.tutor.service.UserService;

@SpringComponent
@Scope(scopeName = "prototype")
@SuppressWarnings("serial")
public class TeacherAccountForm extends VerticalLayout {

	private Teacher teacher;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("subjectServiceImpl")
	private SubjectService subjectService;

	public void initView() {
		createAllSubjectsUI();
		createOwnSubjectsUI();
		createNewSubjectUI();
	}

	private void createAllSubjectsUI() {

		Label lblNewSubject = new Label("Új tárgy felvétele");

		ComboBox<Subject> lstAllSubjects = new ComboBox<>();

		Button btnTeachSubject = new Button();
		btnTeachSubject.setCaption("Tárgy tanítása");
		btnTeachSubject.setEnabled(false);
		btnTeachSubject.addClickListener(event -> {
			if (teacher.getTeachedSubjects().contains(lstAllSubjects.getSelectedItem().get())) {
				Notification.show("Már tanítod ezt a tantárgyat", Type.ERROR_MESSAGE);
			} else {
				userService.saveNewSubjectForTeacher(teacher.getId(), lstAllSubjects.getSelectedItem().get().getId());

			}
		});

		lstAllSubjects.setItems(subjectService.getAllSubjects());
		lstAllSubjects.setItemCaptionGenerator(Subject::getName);
		lstAllSubjects.addValueChangeListener(event -> btnTeachSubject.setEnabled(true));

		addComponent(new VerticalLayout(lblNewSubject, lstAllSubjects, btnTeachSubject));
	}

	private void createOwnSubjectsUI() {

		Label lblOwnSubjects = new Label("Saját tantárgyaim:");
		Label lblSubjectDescription = new Label("Tantárgy leírása");

		ComboBox<Subject> lstTeachedSubjects = new ComboBox<>();
		TextArea txtDescriptionArea = new TextArea();

		Button btnModifyOwnSubject = new Button();
		btnModifyOwnSubject.setCaption("Leírás mentése");
		btnModifyOwnSubject.setEnabled(false);

		Button btnDeleteSubject = new Button();
		btnDeleteSubject.setCaption("Tárgy tanításának befejezése");
		btnDeleteSubject.setEnabled(false);

		lstTeachedSubjects.setItems(teacher.getTeachedSubjects());
		lstTeachedSubjects.setItemCaptionGenerator(Subject::getName);
		lstTeachedSubjects.addSelectionListener(event -> {
			if (event.getSelectedItem().get() != null) {
				txtDescriptionArea.setValue(event.getSelectedItem().get().getDescription());
				btnModifyOwnSubject.setEnabled(false);
				btnDeleteSubject.setEnabled(true);
			} else {
				txtDescriptionArea.setValue("");
				btnModifyOwnSubject.setEnabled(false);
				btnDeleteSubject.setEnabled(false);
			}
		});

		txtDescriptionArea.addValueChangeListener(event -> btnModifyOwnSubject.setEnabled(true));

		btnModifyOwnSubject.addClickListener(event -> {
			// TODO saj�t tant�rgy le�r�sa
		});

		btnDeleteSubject.addClickListener(event -> userService.deleteSubjectFromTeacher(teacher.getId(),
				lstTeachedSubjects.getSelectedItem().get().getId()));

		addComponent(new VerticalLayout(new HorizontalLayout(lblOwnSubjects, lstTeachedSubjects),
				new HorizontalLayout(lblSubjectDescription, txtDescriptionArea),
				new HorizontalLayout(btnModifyOwnSubject, btnDeleteSubject)));
	}

	private void createNewSubjectUI() {
		Label lblNewSubjectName = new Label("Új tantárgy neve:");
		TextField txtNewSubjectName = new TextField();

		Label lblNewSubjectDescription = new Label("Új tantárgy leírása:");
		TextArea txtNewSubjectDescription = new TextArea();

		Button btnSaveNewSubject = new Button();
		btnSaveNewSubject.setCaption("Új tantárgy mentése");
		btnSaveNewSubject.addClickListener(event -> {
			Subject subject = new Subject();
			subject.setName(txtNewSubjectName.getValue());
			subject.setDescription(txtNewSubjectDescription.getValue());
			subjectService.saveNewSubject(subject);
		});

		GridLayout grid = new GridLayout(2, 3);
		grid.addComponent(lblNewSubjectName);
		grid.addComponent(txtNewSubjectName);
		grid.addComponent(lblNewSubjectDescription);
		grid.addComponent(txtNewSubjectDescription);
		grid.addComponent(btnSaveNewSubject);
		addComponent(grid);
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
