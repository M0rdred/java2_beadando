package hu.tutor.model;

import java.time.LocalDate;

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
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

@Data
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	private boolean isAdmin;

	@Type(type = "yes_no")
	@Column(name = "is_teacher")
	private boolean isTeacher;

	public String getFullName() {
		return this.lastName + " " + this.firstName;
	}

	public String getFullAddress() {
		return this.address != null ? this.address.getFullAddress() : "";
	}

}
