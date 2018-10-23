package hu.tutor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@NamedStoredProcedureQuery(name = "loadSearchResults", procedureName = "search_pkg.search", resultClasses = SearchResult.class, parameters = {
		@StoredProcedureParameter(name = "p_searcher_id", mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "p_subject_name", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "p_teacher_name", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "p_max_distance", mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "p_result_list", mode = ParameterMode.REF_CURSOR, type = void.class) })
@Entity
public class SearchResult implements Serializable {
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "country")
	private String country;
	@Column(name = "city")
	private String city;
	@Column(name = "street")
	private String street;
	@Column(name = "house_number")
	private String houseNumber;
	@Column(name = "introduction")
	private String introduction;
	@Column(name = "subject_name")
	private String subjectName;
	@Column(name = "subject_description")
	private String subjectDescription;
	@Column(name = "personal_subject_description")
	private String personalSubjectDescription;
	@Column(name = "distance")
	private String distance;

	public String getFullName() {
		return this.lastName + " " + this.firstName;
	}
}
