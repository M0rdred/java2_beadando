package hu.tutor.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

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
						}) ,
		@NamedStoredProcedureQuery(
				name = "enableTeacher", 
				procedureName = "admin_pkg.enable_teacher", 
				parameters = {
						@StoredProcedureParameter(name = "p_teacher_id", mode = ParameterMode.IN, type = Integer.class),
						@StoredProcedureParameter(name = "p_enable", mode = ParameterMode.IN, type = String.class) 
						}) 		
		})
//@formatter:on
public class Teacher extends User {

	@Getter
	@Setter
	@Transient
	private List<TeachedSubject> teachedSubjects;

}
