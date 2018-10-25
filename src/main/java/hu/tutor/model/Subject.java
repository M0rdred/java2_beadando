package hu.tutor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

// @formatter:off
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(
				name = "getSubjectsOfTeacher", 
				procedureName = "teacher_pkg.get_subjects_of_teacher", 
				resultClasses = Subject.class, 
				parameters = {
					@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_subject_list", mode = ParameterMode.REF_CURSOR, type = void.class) 
					}),
		@NamedStoredProcedureQuery(
				name = "deleteSubjectFromTeacher", 
				procedureName = "teacher_pkg.delete_subject_from_teacher", 
				parameters = {
					@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_subject_id", mode = ParameterMode.IN, type = Integer.class) 
					}) ,
		@NamedStoredProcedureQuery(
				name = "saveNewSubjectForTeacher", 
				procedureName = "teacher_pkg.save_new_subject_for_teacher", 
				parameters = {
					@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_subject_id", mode = ParameterMode.IN, type = Integer.class) 
					}) 
		})
//@formatter:on
@Entity
@Table(name = "subject")
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Subject other = (Subject) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
