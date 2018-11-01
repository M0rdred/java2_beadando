package hu.tutor.model.search;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
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
// @formatter:off
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(
				name = "loadSearchResults", 
				procedureName = "search_pkg.search", 
				resultClasses = SearchResult.class, 
				parameters = {
						@StoredProcedureParameter(name = "p_searcher_id", mode = ParameterMode.IN, type = Integer.class),
						@StoredProcedureParameter(name = "p_subject_name", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "p_teacher_name", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "p_max_distance", mode = ParameterMode.IN, type = Integer.class),
						@StoredProcedureParameter(name = "p_result_list", mode = ParameterMode.REF_CURSOR, type = void.class) 
					}),
		@NamedStoredProcedureQuery(
				name = "saveResultDistance", 
				procedureName = "distance_pkg.save_result_distance", 
				parameters = {
						@StoredProcedureParameter(name = "p_city_from", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "p_city_to", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "p_distance", mode = ParameterMode.IN, type = Integer.class)
					}) 
		})
//@formatter:on
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
	@Column(name = "zip")
	private Integer zip;
	@Column(name = "city")
	private String city;
	@Column(name = "street")
	private String street;
	@Column(name = "house_number")
	private String houseNumber;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "email")
	private String email;
	@Column(name = "introduction")
	private String introduction;
	@Column(name = "subject_name")
	private String subjectName;
	@Column(name = "subject_description")
	private String subjectDescription;
	@Column(name = "personal_subject_description")
	private String personalSubjectDescription;
	@Column(name = "distance")
	private Integer distance;

	public String getFullName() {
		return this.lastName + " " + this.firstName;
	}

	public String getFormattedPhoneNumber() {
		return this.phoneNumber.replaceFirst("(\\d{2})(\\d{3})(\\d+)", "+$1/$2 $3");
	}
}
