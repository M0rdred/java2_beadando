package hu.tutor.view;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.tutor.model.Subject;
import hu.tutor.model.TeachedSubject;
import hu.tutor.model.Teacher;
import hu.tutor.service.SubjectService;
import hu.tutor.service.TeachedSubjectService;
import hu.tutor.service.TeacherService;
import hu.tutor.util.VaadinUtil;

@SpringComponent
@Scope(scopeName = "prototype")
@SuppressWarnings("serial")
public class TeacherAccountForm extends VerticalLayout {

	private Teacher teacher;

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private TeachedSubjectService teachedSubjectService;

	private ComboBox<Subject> lstAllSubjects;
	private ComboBox<TeachedSubject> lstOwnSubjects;

	public void init() {
		this.addComponents(this.createEndTeachingButton(), this.createAllSubjectsPanel(), this.createOwnSubjectsPanel(),
				this.createNewSubjectPanel());
	}

	private Component createEndTeachingButton() {
		Button endTeachingButton = new Button("Oktatás befejezése");
		endTeachingButton.addClickListener(e -> this.teacherService.endTeaching(this.teacher.getId()));
		endTeachingButton.addStyleName(VaadinUtil.BORDERED_BUTTON_STYLE);

		return endTeachingButton;
	}

	private Component createAllSubjectsPanel() {

		Panel allSubjectsPanel = new Panel("Új tárgy felvétele");

		this.lstAllSubjects = new ComboBox<>();
		this.lstAllSubjects.setEmptySelectionAllowed(false);

		Button btnTeachSubject = new Button();
		btnTeachSubject.setCaption("Tárgy tanítása");
		btnTeachSubject.setEnabled(false);
		btnTeachSubject.addClickListener(event -> {
			if (this.teacher.getTeachedSubjects().stream().filter(
					s -> StringUtils.equals(s.getSubjectName(), this.lstAllSubjects.getSelectedItem().get().getName()))
					.findFirst().isPresent()) {
				Notification.show("Már tanítod ezt a tantárgyat", Type.ERROR_MESSAGE);
			} else {
				this.teachedSubjectService.pickUpSubject(this.teacher.getId(),
						this.lstAllSubjects.getSelectedItem().get().getId());
				this.refreshOwnSubjectsList();

				Notification.show("Tantárgy tanítása megkezdve");
			}

			this.lstAllSubjects.setSelectedItem(null);
		});
		btnTeachSubject.addStyleName(VaadinUtil.THEME_BUTTON_STYLE);

		this.lstAllSubjects.setItemCaptionGenerator(Subject::getName);
		this.lstAllSubjects
				.addSelectionListener(event -> btnTeachSubject.setEnabled(event.getSelectedItem().isPresent()));

		this.refreshAllSubjectsList();

		allSubjectsPanel.setContent(new VerticalLayout(this.lstAllSubjects, btnTeachSubject));

		return allSubjectsPanel;
	}

	private Component createOwnSubjectsPanel() {

		Panel ownSubjectsPanel = new Panel("Saját tantárgyaim");

		this.lstOwnSubjects = new ComboBox<>();
		TextArea txtDescriptionArea = new TextArea("Tantárgy leírása");
		TextArea txtOwnDescriptionArea = new TextArea("Tantárgy saját leírása");
		txtDescriptionArea.setSizeFull();
		txtOwnDescriptionArea.setSizeFull();

		Button btnModifyOwnSubject = new Button();
		btnModifyOwnSubject.setCaption("Leírás mentése");
		btnModifyOwnSubject.setEnabled(false);
		btnModifyOwnSubject.addStyleName(VaadinUtil.THEME_BUTTON_STYLE);

		Button btnDeleteSubject = new Button();
		btnDeleteSubject.setCaption("Tárgy tanításának befejezése");
		btnDeleteSubject.setEnabled(false);
		btnDeleteSubject.addStyleName(VaadinUtil.BORDERED_BUTTON_STYLE);

		this.refreshOwnSubjectsList();
		this.lstOwnSubjects.setItemCaptionGenerator(TeachedSubject::getSubjectName);
		this.lstOwnSubjects.setEmptySelectionAllowed(false);
		this.lstOwnSubjects.addSelectionListener(event -> {
			Optional<TeachedSubject> optionalSelectedSubject = event.getSelectedItem();
			if (optionalSelectedSubject.isPresent()) {
				txtDescriptionArea.setValue(optionalSelectedSubject.get().getSubjectDescription());
				String subjectDescription = this.teachedSubjectService
						.getSubjectDescription(optionalSelectedSubject.get().getSubjectId(), this.teacher.getId());

				if (subjectDescription == null) {
					subjectDescription = "";
				}

				txtOwnDescriptionArea.setValue(subjectDescription);
			} else {
				txtDescriptionArea.setValue("");
			}

			btnDeleteSubject.setEnabled(optionalSelectedSubject.isPresent());
			btnModifyOwnSubject.setEnabled(false);
		});

		txtDescriptionArea.setReadOnly(true);
		txtOwnDescriptionArea.addValueChangeListener(event -> btnModifyOwnSubject.setEnabled(true));

		btnModifyOwnSubject.addClickListener(event -> {
			this.teachedSubjectService.modifySubjectDescription(this.teacher.getId(),
					this.lstOwnSubjects.getSelectedItem().get().getSubjectId(), txtOwnDescriptionArea.getValue());
		});

		btnDeleteSubject.addClickListener(event -> {
			this.teacherService.deleteSubjectFromTeacher(this.teacher.getId(),
					this.lstOwnSubjects.getSelectedItem().get().getSubjectId());
			this.refreshOwnSubjectsList();
			this.lstOwnSubjects.setValue(null);

			Notification.show("Tantárgy oktatása befejezve");
		});

		HorizontalLayout descriptionLayout = new HorizontalLayout(txtDescriptionArea, txtOwnDescriptionArea);
		descriptionLayout.setSizeFull();

		// @formatter:off
		ownSubjectsPanel.setContent(
				new VerticalLayout(
						this.lstOwnSubjects,
						descriptionLayout,
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

			try {
				this.subjectService.saveNewSubject(subject);

				this.refreshAllSubjectsList();
				Notification.show("Új tantárgy sikeresen regisztrálva");
			} catch (DataIntegrityViolationException ex) {
				Notification.show("Már létezik ilyen nevű tantárgy a rendszerben", Type.WARNING_MESSAGE);
			}
		});
		btnSaveNewSubject.addStyleName(VaadinUtil.THEME_BUTTON_STYLE);

		FormLayout formLayout = new FormLayout();

		formLayout.setSpacing(true);
		formLayout.setMargin(true);

		formLayout.addComponent(txtNewSubjectName);
		formLayout.addComponent(txtNewSubjectDescription);
		formLayout.addComponent(btnSaveNewSubject);

		newSubjectPanel.setContent(formLayout);

		return newSubjectPanel;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	private void refreshAllSubjectsList() {
		this.lstAllSubjects.setItems(this.subjectService.getAllSubjects(false));
	}

	private void refreshOwnSubjectsList() {
		List<TeachedSubject> subjectsOfTeacher = this.teacherService.getSubjectsOfTeacher(this.teacher.getId());
		this.teacher.setTeachedSubjects(subjectsOfTeacher);
		this.lstOwnSubjects.setItems(subjectsOfTeacher);
	}

}
