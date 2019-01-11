package hu.tutor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
// @formatter:off
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
			name = "listTeachedSubjects",
			procedureName = "admin_pkg.get_all_teached_subjects",
			resultClasses = TeachedSubject.class, 
			parameters = {
					@StoredProcedureParameter(name = "p_teached_subjects", mode = ParameterMode.REF_CURSOR,	type = void.class)
			}),
	@NamedStoredProcedureQuery(
			name = "getSubjectsOfTeacher", 
			procedureName = "teacher_pkg.get_subjects_of_teacher", 
			resultClasses = TeachedSubject.class, 
			parameters = {
				@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name = "p_subject_list", mode = ParameterMode.REF_CURSOR, type = void.class) 
				}),
	@NamedStoredProcedureQuery(
			name = "pickUpSubject", 
			procedureName = "teached_subject_pkg.pick_up_subject", 
			parameters = {
				@StoredProcedureParameter(name = "p_subject_id", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class)
				}),
	@NamedStoredProcedureQuery(
			name = "activateTeachedSubject",
			procedureName = "admin_pkg.activate_teached_subject",
			parameters = {
					@StoredProcedureParameter(name = "p_subject_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_active", mode = ParameterMode.IN, type = String.class)
			}),
	@NamedStoredProcedureQuery(
			name = "modifySubjectDescription",
			procedureName = "teached_subject_pkg.modify_subject_description",
			parameters = {
					@StoredProcedureParameter(name = "p_subject_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_description", mode = ParameterMode.IN, type = String.class)
			}),
	@NamedStoredProcedureQuery(
			name = "getSubjectDescription",
			procedureName = "teached_subject_pkg.get_subject_description",
			parameters = {
					@StoredProcedureParameter(name = "p_subject_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_description", mode = ParameterMode.OUT, type = String.class)
			})
})
//@formatter:on
@Table(name = "teached_subject")
@Entity
public class TeachedSubject {

	@Id
	private Integer id;
	@Column(name = "subject_id")
	private Integer subjectId;
	@Column(name = "teacher_id")
	private Integer teacherId;
	@Column(name = "subject_name")
	private String subjectName;
	@Column(name = "subject_description")
	private String subjectDescription;
	@Column(name = "teacher_name")
	private String teacherName;
	@Column(name = "teacher_introduction")
	private String teacherIntroduction;
	@Column(name = "teacher_subject_description")
	private String teachedSubjectDescription;
	@Type(type = "yes_no")
	@Column(name = "active")
	private Boolean active = false;

}
