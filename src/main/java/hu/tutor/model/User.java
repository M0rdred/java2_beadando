package hu.tutor.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import hu.tutor.model.search.SearchQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("user")
// @formatter:off
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
			name = "modifyPassword", 
			procedureName = "admin_pkg.modify_password", 
			parameters = {
					@StoredProcedureParameter(name = "p_user_id", mode = ParameterMode.IN, type = Integer.class),
					@StoredProcedureParameter(name = "p_new_password", mode = ParameterMode.IN, type = String.class) 
					})
})
//@formatter:on
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_sequence")
	@SequenceGenerator(name = "person_sequence", sequenceName = "person_sq")
	@Column(name = "id")
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	private Address address;

	@Column(name = "introduction")
	private String introduction;

	@Column(name = "password")
	private String password;

	@Column(name = "birth_date")
	private LocalDate birthDate;

	@Type(type = "yes_no")
	@Column(name = "is_admin")
	private Boolean isAdmin = false;

	@Type(type = "yes_no")
	@Column(name = "is_teacher")
	private Boolean isTeacher = false;

	@Type(type = "yes_no")
	@Column(name = "is_active")
	private Boolean isActive = false;

	@OneToMany(mappedBy = "owner", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<SearchQuery> queries;

	public Set<SearchQuery> getSearchQueries() {
		return Collections.unmodifiableSet(this.queries);
	}

	public void addSearchQuery(SearchQuery query) {
		if (this.queries.contains(query)) {
			return;
		} else {
			query.setOwner(this);
			this.queries.add(query);
		}
	}

	public void removeSeachQuery(SearchQuery query) {
		if (!this.queries.contains(query)) {
			return;
		}

		this.queries.remove(query);
		query.setOwner(null);
	}

	public String getFullName() {
		return this.lastName + " " + this.firstName;
	}

	public String getFullAddress() {
		return this.address != null ? this.address.getFullAddress() : "";
	}

}
