package hu.tutor.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
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

	public TeacherAccountForm() {
		teacher = (Teacher) VaadinSession.getCurrent().getAttribute("user");
	}

	@PostConstruct
	private void createAllSubjectsUI() {

		Label lblNewSubject = new Label("Új tárgy felvétele");

		ComboBox<Subject> allSubjectsDropDown = new ComboBox<>();

		Button teachSubjectButton = new Button();
		teachSubjectButton.setCaption("Tárgy tanítása");
		teachSubjectButton.setEnabled(false);
		teachSubjectButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (teacher.getTeachedSubjects().contains(allSubjectsDropDown.getSelectedItem().get())) {
					Notification.show("Már tanítod ezt a tantárgyat", Notification.TYPE_WARNING_MESSAGE);
				} else {
					userService.saveNewSubjectForTeacher(teacher.getId(),
							allSubjectsDropDown.getSelectedItem().get().getId());
				}
			}
		});

		allSubjectsDropDown.setItems(subjectService.getAllSubjects());
		allSubjectsDropDown.setItemCaptionGenerator(Subject::getName);
		allSubjectsDropDown.addValueChangeListener(new ValueChangeListener<Subject>() {

			@Override
			public void valueChange(ValueChangeEvent<Subject> event) {
				teachSubjectButton.setEnabled(true);
			}
		});

		addComponent(new VerticalLayout(lblNewSubject, allSubjectsDropDown, teachSubjectButton));
	}

	@PostConstruct
	private void createOwnSubjectsUI() {

		Label ownSubjectsLabel = new Label("Saját tantárgyaim:");
		Label subjectDescriptionLabel = new Label("Tantárgy leírása");

		ComboBox<Subject> teachedSubjects = new ComboBox<>();
		TextArea descriptionArea = new TextArea();

		Button modifyOwnSubjectButton = new Button();
		modifyOwnSubjectButton.setCaption("Leírás mentése");
		modifyOwnSubjectButton.setEnabled(false);

		Button btnDeleteSubject = new Button();
		btnDeleteSubject.setCaption("Tárgy tanításának befejezése");
		btnDeleteSubject.setEnabled(false);

		teachedSubjects.setItems(teacher.getTeachedSubjects());
		teachedSubjects.setItemCaptionGenerator(Subject::getName);
		teachedSubjects.addSelectionListener(new SingleSelectionListener<Subject>() {

			@Override
			public void selectionChange(SingleSelectionEvent<Subject> event) {
				if (event.getSelectedItem().get() != null) {
					descriptionArea.setValue(event.getSelectedItem().get().getDescription());
					modifyOwnSubjectButton.setEnabled(false);
					btnDeleteSubject.setEnabled(true);
				} else {
					descriptionArea.setValue("");
					modifyOwnSubjectButton.setEnabled(false);
					btnDeleteSubject.setEnabled(false);
				}
			}
		});

		descriptionArea.addValueChangeListener(new ValueChangeListener<String>() {

			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				modifyOwnSubjectButton.setEnabled(true);
			}
		});

		modifyOwnSubjectButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO saját tantárgy leírása
			}
		});

		btnDeleteSubject.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				userService.deleteSubjectFromTeacher(teacher.getId(), teachedSubjects.getSelectedItem().get().getId());
			}
		});

		addComponent(new VerticalLayout(new HorizontalLayout(ownSubjectsLabel, teachedSubjects),
				new HorizontalLayout(subjectDescriptionLabel, descriptionArea),
				new HorizontalLayout(modifyOwnSubjectButton, btnDeleteSubject)));
	}
}
