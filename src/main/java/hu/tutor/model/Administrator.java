package hu.tutor.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "person")
@DiscriminatorValue("admin")
// @formatter:off
@NamedStoredProcedureQuery(
		name = "activatePerson", 
		procedureName = "admin_pkg.activate_person", 
		parameters = {
				@StoredProcedureParameter(name = "p_person_id", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name = "p_active", mode = ParameterMode.IN, type = String.class) 
				}) 
//@formatter:on
public class Administrator extends User {

}
