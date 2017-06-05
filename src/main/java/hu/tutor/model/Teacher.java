package hu.tutor.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
@DiscriminatorValue("teacher")
public class Teacher extends User {

	// @JoinTable(name = "teached_subjects", joinColumns = @JoinColumn(name =
	// "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	@Transient
	private List<Subject> teachedSubjects;

	public List<Subject> getTeachedSubjects() {
		return teachedSubjects;
	}

	public void setTeachedSubjects(List<Subject> teachedSubjects) {
		this.teachedSubjects = teachedSubjects;
	}

}
