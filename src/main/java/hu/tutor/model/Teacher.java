package hu.tutor.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "person")
@DiscriminatorValue("teacher")
public class Teacher extends User {

	@ManyToMany
	@JoinTable(name = "teached_subjects", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
//	@Transient
	private List<Subject> teachedSubjects;

	public List<Subject> getTeachedSubjects() {
		return this.teachedSubjects;
	}

	public void setTeachedSubjects(List<Subject> teachedSubjects) {
		this.teachedSubjects = teachedSubjects;
	}

}
