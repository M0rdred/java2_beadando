package hu.tutor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// @formatter:off
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(
				name = "deleteSubjectFromTeacher", 
				procedureName = "teacher_pkg.delete_subject_from_teacher", 
				parameters = {
					@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_subject_id", mode = ParameterMode.IN, type = Integer.class) 
					}),
		@NamedStoredProcedureQuery(
				name = "saveNewSubjectForTeacher", 
				procedureName = "teacher_pkg.save_new_subject_for_teacher", 
				parameters = {
					@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_subject_id", mode = ParameterMode.IN, type = Integer.class) 
					}),
		@NamedStoredProcedureQuery(
				name = "enableSubject", 
				procedureName = "admin_pkg.enable_subject", 
				parameters = {
					@StoredProcedureParameter(name = "p_subject_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_enable", mode = ParameterMode.IN, type = String.class) 
					}) 
		})
//@formatter:on
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_sequence")
	@SequenceGenerator(name = "subject_sequence", sequenceName = "subject_sq")
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Type(type = "yes_no")
	@Column(name = "active")
	private boolean active;

}
