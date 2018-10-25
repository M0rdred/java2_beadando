package hu.tutor.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "person")
@DiscriminatorValue("teacher")
//@formatter:off
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(
				name = "endTeaching", 
				procedureName = "teacher_pkg.end_teaching", 
				parameters = {
						@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class) 
						}),
		@NamedStoredProcedureQuery(
				name = "becomeTeacher", 
				procedureName = "teacher_pkg.become_teacher", 
				parameters = {
						@StoredProcedureParameter(name = "p_user_id", mode = ParameterMode.IN, type = Integer.class) 
						}) 
		})
//@formatter:on
public class Teacher extends User {

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "teached_subject", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	private List<Subject> teachedSubjects;

	public List<Subject> getTeachedSubjects() {
		return this.teachedSubjects;
	}

	public void setTeachedSubjects(List<Subject> teachedSubjects) {
		this.teachedSubjects = teachedSubjects;
	}

}
