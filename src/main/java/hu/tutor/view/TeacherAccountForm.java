package hu.tutor.view;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
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
		this.addComponents(this.createEndTeachingButton(), this.createAllSubjectsPanel(), this.createOwnSubjectsPanel(),
				this.createNewSubjectPanel());
	}

	private Component createEndTeachingButton() {
		Button endTeachingButton = new Button("Oktatás befejezése");
		endTeachingButton.addClickListener(e -> this.userService.endTeaching(this.teacher.getId()));

		return endTeachingButton;
	}

	private Component createAllSubjectsPanel() {

		Panel allSubjectsPanel = new Panel("Új tárgy felvétele");

		ComboBox<Subject> lstAllSubjects = new ComboBox<>();

		Button btnTeachSubject = new Button();
		btnTeachSubject.setCaption("Tárgy tanítása");
		btnTeachSubject.setEnabled(false);
		btnTeachSubject.addClickListener(event -> {
			if (this.teacher.getTeachedSubjects().contains(lstAllSubjects.getSelectedItem().get())) {
				Notification.show("Már tanítod ezt a tantárgyat", Type.ERROR_MESSAGE);
			} else {
				this.userService.saveNewSubjectForTeacher(this.teacher.getId(),
						lstAllSubjects.getSelectedItem().get().getId());
			}
		});

		lstAllSubjects.setItems(this.subjectService.getAllSubjects());
		lstAllSubjects.setItemCaptionGenerator(Subject::getName);
		lstAllSubjects.addValueChangeListener(event -> btnTeachSubject.setEnabled(true));

		allSubjectsPanel.setContent(new VerticalLayout(lstAllSubjects, btnTeachSubject));

		return allSubjectsPanel;
	}

	private Component createOwnSubjectsPanel() {

		Panel ownSubjectsPanel = new Panel("Saját tantárgyaim");

		Label lblSubjectDescription = new Label("Tantárgy leírása");

		ComboBox<Subject> lstTeachedSubjects = new ComboBox<>();
		TextArea txtDescriptionArea = new TextArea();

		Button btnModifyOwnSubject = new Button();
		btnModifyOwnSubject.setCaption("Leírás mentése");
		btnModifyOwnSubject.setEnabled(false);

		Button btnDeleteSubject = new Button();
		btnDeleteSubject.setCaption("Tárgy tanításának befejezése");
		btnDeleteSubject.setEnabled(false);

		lstTeachedSubjects.setItems(this.teacher.getTeachedSubjects());
		lstTeachedSubjects.setItemCaptionGenerator(Subject::getName);
		lstTeachedSubjects.addSelectionListener(event -> {
			Optional<Subject> optionalSelectedSubject = event.getSelectedItem();
			if (optionalSelectedSubject.isPresent()) {
				txtDescriptionArea.setValue(optionalSelectedSubject.get().getDescription());
			} else {
				txtDescriptionArea.setValue("");
			}

			btnDeleteSubject.setEnabled(optionalSelectedSubject.isPresent());
			btnModifyOwnSubject.setEnabled(false);
		});

		txtDescriptionArea.addValueChangeListener(event -> btnModifyOwnSubject.setEnabled(true));

		btnModifyOwnSubject.addClickListener(event -> {
			// TODO saját tantárgy leírása
		});

		btnDeleteSubject.addClickListener(event -> this.userService.deleteSubjectFromTeacher(this.teacher.getId(),
				lstTeachedSubjects.getSelectedItem().get().getId()));

		// @formatter:off
		ownSubjectsPanel.setContent(
				new VerticalLayout(
						lstTeachedSubjects,
						new HorizontalLayout(lblSubjectDescription, txtDescriptionArea),
						new HorizontalLayout(btnModifyOwnSubject, btnDeleteSubject)
						)
				);
		// @formatter:on

		return ownSubjectsPanel;
	}

	private Component createNewSubjectPanel() {
		Panel newSubjectPanel = new Panel("Új tantárgy regisztrálása");

		TextField txtNewSubjectName = new TextField("Új tantárgy neve:");

		TextArea txtNewSubjectDescription = new TextArea("Új tantárgy leírása:");

		Button btnSaveNewSubject = new Button();

		btnSaveNewSubject.setCaption("Új tantárgy mentése");
		btnSaveNewSubject.addClickListener(event -> {
			Subject subject = new Subject();
			subject.setName(txtNewSubjectName.getValue());
			subject.setDescription(txtNewSubjectDescription.getValue());
			this.subjectService.saveNewSubject(subject);
		});

		FormLayout formLayout = new FormLayout();

		formLayout.setSpacing(true);

		formLayout.addComponent(txtNewSubjectName);
		formLayout.addComponent(txtNewSubjectDescription);
		formLayout.addComponent(btnSaveNewSubject);

		newSubjectPanel.setContent(formLayout);

		return newSubjectPanel;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
